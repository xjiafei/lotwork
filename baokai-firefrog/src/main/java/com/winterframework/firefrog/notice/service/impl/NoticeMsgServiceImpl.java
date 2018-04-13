package com.winterframework.firefrog.notice.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.mq.FirefogMessage;
import com.winterframework.firefrog.common.util.ImConstants;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.notice.dao.INoticeMsgDao;
import com.winterframework.firefrog.notice.entity.NoticeMqMsg;
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.entity.NoticeMsg.Status;
import com.winterframework.firefrog.notice.entity.UserGroup;
import com.winterframework.firefrog.notice.service.INoticeMsgService;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.firefrog.user.dao.INoticeMsgPushDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserMessageDao;
import com.winterframework.firefrog.user.entity.NoticeMsgPush;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IMessage2Service;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("noticeMsgServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class NoticeMsgServiceImpl implements INoticeMsgService {
	
	private static Logger logger = LoggerFactory.getLogger(NoticeMsgServiceImpl.class);

	@Resource(name = "noticeMsgDao")
	private INoticeMsgDao msgDao;

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;

	@PropertyConfig(value = "mq.queue.admin.message")
	private String QUEUE_NAME;

	@Resource(name= "message2ServiceImpl")
	private IMessage2Service messageService;

	@Resource(name = "userMessageDaoImpl")
	private IUserMessageDao userMessageDao;

    @Resource(name = "globalSensitiveWordServiceImpl")
    private GlobalSensitiveWordService word;
    
    @Resource(name = "noticeMsgPushDaoImpl")
    private INoticeMsgPushDao noticeMsgPushDao;
    
	@Override
	public Page<NoticeMsg> queryMsg(PageRequest<NoticeMsgSearchDto> search) throws Exception {
		Date now = new Date();
		NoticeMsgSearchDto searchDo = search.getSearchDo();
		int sendStatus = searchDo.getSendSatus().intValue();
		searchDo.setSendSatus(null);
		switch (sendStatus) {
		case -1:
			searchDo.setSendSatus(-1L);
			break;
		case 0:
			searchDo.setStatus(2L);
			searchDo.setGmtExpired(now);
			break;
		case 1:
			searchDo.setStatus(2L);
			searchDo.setTimeExpired(now);
			break;
		case 2:
			searchDo.setStatus(3L);
			break;
		case 3:
			searchDo.setStatus(1L);
			break;
		}
		return msgDao.selectMsg(search);
	}

	@Override
	public void modifyMsgStatus(Long id, Long status) throws Exception {
		msgDao.modifyMsgStatus(id, status);
		if (NoticeMsg.Status.getEnum(status.intValue()) == NoticeMsg.Status.REVOCATION) {
			userMessageDao.deleteMessage(id);
		}
	}

	@Override
	public void saveMsg(NoticeMsg msg,RabbitTemplate rabbitTemplate) throws Exception {
		/*AclUser user = new AclUser();
		user.setAccount("admin");
		msg.setOperator(user);*/
		Calendar cal = Calendar.getInstance();
		if (msg.getGmtSended() != null) {
			cal.setTime(msg.getGmtSended());
		}
		msg.setGmtSended(cal.getTime());
		cal.add(Calendar.HOUR_OF_DAY, msg.getEffectHours().intValue());
		msg.setGmtExpired(cal.getTime());
        msg.setContent(word.replace(msg.getContent(), SensitiveWord.Type.message));;
		msg.setTitle(word.replace(msg.getTitle(), SensitiveWord.Type.message));
        msgDao.saveMsg(msg);
		
		//如果状态为sent则添加到MQ队列中
		if(msg.getStatus() == Status.SENT){
			rabbitTemplate.send(QUEUE_NAME,new FirefogMessage(msg));
		}
	}

	@Override
	public NoticeMsg queryMsgDetail(Long id) throws Exception {
		return msgDao.selectMsgDetail(id);
	}

	@Override
	public void sendMsg(Long sendUserId, String sendAccount, Long receiveId,
			String content) throws Exception {
		messageService.sendMessage(sendUserId, sendAccount, receiveId, content);
	}
	
	@Override
	public void sendMsgIntoMq(NoticeMsg msg) throws Exception {
		Date startTime  = new Date();
		List<User> sendUsers = getSendUsers(msg);
		logger.info("sendMsg users:"+sendUsers.size());
		final int QUEUE_COUNT = 5;
		int i = 0;
		for(User user:sendUsers){
			try {
				String queueName = QUEUE_NAME+(i%QUEUE_COUNT);
				logger.debug("queue:"+queueName+",sendMessage:"+user.getId()+":"+msg.getContent());
				NoticeMqMsg noticeMqMsg = new NoticeMqMsg();
				noticeMqMsg.setContent(msg.getContent());
				noticeMqMsg.setMessagePush(msg.getMessagePush());
				noticeMqMsg.setNoticeId(msg.getId());
				noticeMqMsg.setReceiveId(user.getId());
				noticeMqMsg.setSendAccount(ImConstants.ADMIN_ACCOUNT);
				noticeMqMsg.setSendUserId(ImConstants.ADMIN_ID);
				messageService.sendMessageIntoMq(queueName,noticeMqMsg);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		msgDao.modifyMsgStatus(msg.getId(), 2l);
		logger.info(msg.getId() + " sendMsg startTime:" + startTime.getTime() + " " + sendUsers.size());
		logger.info("sendMsg useTime:"+(new Date().getTime()-startTime.getTime())/1000d+"sec");
	}

	public void pushScheduleMsgToMq(RabbitTemplate rabbitTemplate) throws Exception {
		List<NoticeMsg> msgs = msgDao.selectScheduleMsg();
		for (NoticeMsg msg : msgs) {
			rabbitTemplate.send(QUEUE_NAME,new FirefogMessage(msg));

			msgDao.modifyMsgStatus(msg.getId(), 2l);
		}
	}

	private List<User> getSendUsers(NoticeMsg msg) throws Exception {
		Long sendType = Long.valueOf(msg.getSendType().getIndex());
		List<User> sendUser = new ArrayList<User>();
		if (sendType.longValue() == 1) {
			List<UserGroup> recGroups = msg.getRecGroup();
			List<Long> userLvl = new ArrayList<Long>();
			List<Long> vipLvl = new ArrayList<Long>();
			for (UserGroup recGroup : recGroups) {
				if (UserGroup.ALL.equals(recGroup)) {
					sendUser = userCustomerDao.getAllUser();
					break;
				}
				if (UserGroup.TOP_AGENT.equals(recGroup)) {
					userLvl.add(0l);
				}
				for (int i = 1; i <= 5; i++) {
					String name = "AGENT_" + i;
					if (name.equals(recGroup.name())) {
						userLvl.add(Long.valueOf(i));
					}
				}
				if (UserGroup.AGENT_xx.equals(recGroup)) {
					for(int i=6;i<30;i++){
						userLvl.add(Long.valueOf(i));
					}
				}
				if (UserGroup.PASSANGE.equals(recGroup)) {
					userLvl.add(-1l);
				}
				if (UserGroup.VIP.equals(recGroup)) {
					vipLvl.add(1l);
				}
				if (UserGroup.NONVIP.equals(recGroup)) {
					vipLvl.add(0l);
				}

			}
			if (!userLvl.isEmpty() || !vipLvl.isEmpty()) {
				sendUser = userCustomerDao.getUserByUserLvl(userLvl, vipLvl);
			}
		} else {
			List<User> users = msg.getReceivers();
			List<String> userName = new ArrayList<String>();
			for (User user : users) {
				userName.add(user.getUserProfile().getAccount());
			}
			sendUser = userCustomerDao.getUserByUserNames(userName);
		}
		return sendUser;
	}

	@Override
	public void saveNoticeMsgPush(Long receiveId, Long noticeMsgId)
			throws Exception {
		NoticeMsgPush request = new NoticeMsgPush();
		request.setCreateDate(new Date());
		request.setIsRead(0L);
		request.setUserId(receiveId);
		request.setNoticeMsgId(noticeMsgId);
		noticeMsgPushDao.insertNoticeMsgPush(request);
	}
	
}
