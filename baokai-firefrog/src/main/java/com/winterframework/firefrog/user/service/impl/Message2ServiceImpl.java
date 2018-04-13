package com.winterframework.firefrog.user.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.util.ImConstants;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.notice.entity.NoticeMqMsg;
import com.winterframework.firefrog.user.dao.IImGroupDao;
import com.winterframework.firefrog.user.dao.IImGroupMessageDao;
import com.winterframework.firefrog.user.dao.IImGroupUserDao;
import com.winterframework.firefrog.user.dao.INoticeMsgPushDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserMessageDao;
import com.winterframework.firefrog.user.dao.IUserMessageReplyDao;
import com.winterframework.firefrog.user.entity.ImEntitiyVoConvertor;
import com.winterframework.firefrog.user.entity.ImGroup;
import com.winterframework.firefrog.user.entity.ImGroupMessage;
import com.winterframework.firefrog.user.entity.ImGroupUser;
import com.winterframework.firefrog.user.entity.NoticeMsg;
import com.winterframework.firefrog.user.entity.NoticeMsgPush;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserInboxMessage;
import com.winterframework.firefrog.user.service.IMessage2Service;
import com.winterframework.firefrog.user.vo.ImGroupMessageVo;
import com.winterframework.firefrog.user.vo.ImGroupUserVo;
import com.winterframework.firefrog.user.vo.ImGroupVo;
import com.winterframework.firefrog.user.web.dto.MessageMarkAndDeleteRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageQueryRequest;
import com.winterframework.firefrog.user.web.dto.MessageRecipient;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Pager;

@Service("message2ServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class Message2ServiceImpl implements IMessage2Service {
	
	private Logger log = LoggerFactory.getLogger(Message2ServiceImpl.class);

	@Autowired
	private IUserMessageDao userMessageDao;

	@Autowired
	private IUserMessageReplyDao userMessageReplyDao;
	
	@Resource(name="imGroupMessageDaoImpl")
	private IImGroupMessageDao imGroupMessageDao;
	
	@Resource(name="imGroupUserDaoImpl")
	private IImGroupUserDao imGroupUserDao;
	
	@Resource(name="imGroupDaoImpl")
	private IImGroupDao imGroupDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher noticeMsgPublisher;
	
	@Resource(name = "noticeMsgPushDaoImpl")
	private INoticeMsgPushDao noticeMsgPushDao;
	
	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundWithdrawService;
	
	@Override
	public Page<UserInboxMessage> getMessageList(PageRequest<Long> pr) {
		Pager pager = new Pager();
		pager.setStartNo(pr.getPageNo()*pr.getPageSize());
		pager.setEndNo(pager.getStartNo()+pr.getPageSize());
		Long totalCount = imGroupMessageDao.queryUserInboxMessageCount(pr.getSearchDo());
		List<UserInboxMessage> messages = imGroupMessageDao.queryUserInboxMessages(pr.getSearchDo(), pager);
		Page<UserInboxMessage> page = new Page<UserInboxMessage>(pr.getPageNo(),pr.getPageSize(),totalCount.intValue());
		page.setResult(messages);
		return page;
	}

	@Override
	public void markRead(Long userId,MessageMarkAndDeleteRequestDTO[] groupIds) {
		Date now = new Date();
		for(MessageMarkAndDeleteRequestDTO id:groupIds){
			ImGroupUser request = new ImGroupUser();
			request.setLastUpdateDate(now);
			request.setUnreadCount(0L);
			request.setTargetGroupId(id.getId());
			request.setTargetUserId(userId);
			imGroupUserDao.updateImGroupUser(request);
		}
	}

	@Override
	public void deleteMessages(Long userId,MessageMarkAndDeleteRequestDTO[] groupIds) {
		for(MessageMarkAndDeleteRequestDTO id:groupIds){
			deleteMessage(userId, id.getId());
		}
	}

	@Override
	public List<UserInboxMessage> getMessageDetail(long groupId, long userId) {
		Pager pager = new Pager(1, 5);
		List<UserInboxMessage> list = imGroupMessageDao.queryUserInboxMessageDetails(groupId, userId, pager);
		Collections.reverse(list);
		return list;
	}

	@Override
	public int getUnreadMessagesNumber(long userId) {
		return imGroupMessageDao.queryUserInboxMsgTotalUnreadCount(userId).intValue();
	}

	@Override
	public Page<UserInboxMessage> getUnReadMessageList(PageRequest<Long> pr) {
		Pager pager = new Pager();
		pager.setStartNo(pr.getPageNo()*pr.getPageSize());
		pager.setEndNo(pager.getStartNo()+pr.getPageSize());
		Long totalCount = imGroupMessageDao.queryUserInboxMsgTotalUnreadCount(pr.getSearchDo());
		List<UserInboxMessage> messages = imGroupMessageDao.queryUserInboxUnreadMessages(pr.getSearchDo(), pager);
		Page<UserInboxMessage> page = new Page<UserInboxMessage>(pr.getPageNo(),pr.getPageSize(),totalCount.intValue());
		page.setResult(messages);
		return page;
	}

	@Override
	public void deleteMessage(Long userId,Long groupId) {
		Date date = new Date();
		ImGroupUser request = new ImGroupUser();
		request.setLastUpdateDate(date);
		request.setUnreadCount(0L);
		request.setHistoryStartTime(date);
		request.setTargetGroupId(groupId);
		request.setTargetUserId(userId);
		imGroupUserDao.updateImGroupUser(request);
	}

	@Override
	public List<UserInboxMessage> getMessageDetail(long groupId) {
		Pager pager = new Pager(1, 10);
		List<UserInboxMessage> list = imGroupMessageDao.queryAllMessageDetails(groupId, pager);
		Collections.reverse(list);
		return list;
	}
	
	@Override
	public Boolean checkUserTo(String account,Long userId,MessageRecipient[] receivers){
		try {
			Boolean downCheck = true;
			Boolean upCheck = true;
			//將收件者轉換為List
			long[] receiverIds = new long[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				receiverIds[i] = receivers[i].getReceiveId();
			}
			//取得收件者上級id，判斷收件者上級是否為寄送者
			List<User> receiverList = userCustomerDao.queryUserById(receiverIds);
			for(User v : receiverList){
				if(!v.getParent().getId().equals(userId)){
					downCheck = false;
				}
			}
			
			Long userParentId =(long)0;
			//取得寄送者上級id
			long[] receiverId = new long[1];
			receiverId[0] = userId;
			receiverList = userCustomerDao.queryUserById(receiverId);
			for(User v : receiverList){
				userParentId = v.getParent().getId();
				for(int i =0;i<receiverIds.length;i++){
					if(!userParentId.equals(receiverIds[i])){
						upCheck = false;
					}
				}
			}
			if(upCheck || downCheck){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error("(checkUserTo) Error="+e.toString());
			return false;
		}
	}

	@Override
	public ImGroupVo queryOrCreateImGroup(Long... userIds) throws Exception {
		String uesrkey = genUserKey(userIds);
		ImGroup request = new ImGroup();
		request.setTargetGroupKey(uesrkey);
		ImGroup group = imGroupDao.queryImGroup(request);
		if(group==null){
			group = new ImGroup();
			group.setGroupKey(uesrkey);
			group.setCreateDate(new Date());
			imGroupDao.insertImGroup(group);
		}
		ImGroupVo groupVo = ImEntitiyVoConvertor.convertImGroupToVo(group);
		return groupVo;
	}

	@Override
	public ImGroupMessageVo saveImGroupMessage(ImGroupMessageVo messageReq) {
		ImGroupMessage message = ImEntitiyVoConvertor.convertImGroupMessageToEntity(messageReq);
		imGroupMessageDao.insertImGroupMessage(message);
		messageReq.setId(message.getId());
		return messageReq;
	}

	@Override
	public ImGroupUserVo queryOrAddImGroupUser(ImGroupVo group, Long userId) {
		ImGroupUser request = new ImGroupUser();
		request.setTargetUserId(userId);
		request.setTargetGroupId(group.getId());
		ImGroupUser groupUser = imGroupUserDao.queryImGroupUser(request);
		if(groupUser==null){
			Date now = new Date();
			groupUser = new ImGroupUser();
			groupUser.setUserId(userId);
			groupUser.setGroupId(group.getId());
			groupUser.setIsActive(1L);
			groupUser.setCreateDate(now);
			groupUser.setLastUpdateDate(now);
			groupUser.setHistoryStartTime(now);
			groupUser.setUnreadCount(0L);
			imGroupUserDao.insertImGroupUser(groupUser);
		}
		ImGroupUserVo groupUserVo = ImEntitiyVoConvertor.convertImGroupUserToVo(groupUser);
		return groupUserVo;
	}


	@Override
	public void addGroupOtherUserUnReadCount(ImGroupUserVo user, Long count) {
		ImGroupUser request = new ImGroupUser();
		request.setTargetGroupId(user.getGroupId());
		request.setTargetUserId(user.getUserId());
		request.setUnreadCount(count);
		request.setLastUpdateDate(new Date());
		imGroupUserDao.addImGroupUserWithoutOwner(request);
	}
	
	@Override
	public void sendMessage(Long userId,String account,Long receiveId,String content) throws Exception{
	    ImGroupVo group = queryOrCreateImGroup(userId,receiveId);
	    ImGroupUserVo ownUser = queryOrAddImGroupUser(group,userId);
	    queryOrAddImGroupUser(group,receiveId);
	    ImGroupMessageVo msgVo = new ImGroupMessageVo();
		msgVo.setUserId(userId);
	    msgVo.setAccount(account);
		msgVo.setContent(content);
		msgVo.setCreateDate(new Date());
		msgVo.setGroupId(group.getId());
		saveImGroupMessage(msgVo);
		addGroupOtherUserUnReadCount(ownUser,1L);
		noticeMsgPublisher.addMessageToImMq(userId, account,msgVo);
	}

	@Override
	public void sendMessageIntoMq(String queueName, NoticeMqMsg noticeMqMsg)
			throws Exception {
		noticeMsgPublisher.addMessageToMq(queueName, noticeMqMsg);
	}

	@Override
	public void replyMessage(Long groupId,Long userId,String account,String content) throws Exception{
	    ImGroupMessageVo msgVo = new ImGroupMessageVo();
		msgVo.setUserId(userId);
	    msgVo.setAccount(account);
		msgVo.setContent(content);
		msgVo.setCreateDate(new Date());
		msgVo.setGroupId(groupId);
		saveImGroupMessage(msgVo);
		ImGroupUserVo ownUser = new ImGroupUserVo();
		ownUser.setUserId(userId);
		ownUser.setGroupId(groupId);
		addGroupOtherUserUnReadCount(ownUser ,1L);
		noticeMsgPublisher.addMessageToImMq(userId, account,msgVo);
	}
	
	private String genUserKey(Long[] userIds) throws Exception{
		Long key1 = 0L;
		Long key2 = 0L;
		Long key3 = 0L;
		for(Long userId:userIds){
			User userCustomer = null;
			if(userId != 0){
				userCustomer = userCustomerDao.queryUserById(userId);
			}else{
				userCustomer = new User();
				userCustomer.setId(0L);
				userCustomer.setAccount(ImConstants.ADMIN_ACCOUNT);
			}
			key1+=Math.abs(userCustomer.getId());
			key2+=Math.abs(userCustomer.getAccount().hashCode());
			key3+=Math.abs((userCustomer.getId()+userCustomer.getAccount()).hashCode());
		}
		return key1+"-"+key2+"-"+key3;
	}

	@Override
	public Page<UserInboxMessage> queryMessageList(
			PageRequest<MessageQueryRequest> pr) {
		Pager pager = new Pager();
		pager.setStartNo(pr.getPageNo()*pr.getPageSize());
		pager.setEndNo(pager.getStartNo()+pr.getPageSize());
		MessageQueryRequest search = pr.getSearchDo();
		ImGroupMessage request = new ImGroupMessage();
		if(search.getSender()==null){
			request.setTargetReceiverAccount(search.getReceiver());
		}else{
			request.setTargetAccount( search.getSender());
		}		
//		request.setTargetAccount(search.getSender()==null?search.getReceiver():search.getSender());
		request.setTargetCreateDateStart(search.getSendTimeStart());
		request.setTargetCreateDateEnd(search.getSendTimeEnd());
		Long totalCount = imGroupMessageDao.queryAllMessageCount(request);
		List<UserInboxMessage> messages = imGroupMessageDao.queryUserAllMessages(request, pager);
		Page<UserInboxMessage> page = new Page<UserInboxMessage>(pr.getPageNo(),pr.getPageSize(),totalCount.intValue());
		page.setResult(messages);
		return page;
	}

	@Override
	public Page<NoticeMsg> queryUnreadNoticePushMsgList(
			PageRequest<Long> pr) throws Exception {
		Pager pager = new Pager();
		pager.setStartNo(pr.getPageNo()*pr.getPageSize());
		pager.setEndNo(pager.getStartNo()+pr.getPageSize());
		Long totalCount = noticeMsgPushDao.queryUnreadNoticeMsgCount(pr.getSearchDo());
		List<NoticeMsg> messages = noticeMsgPushDao.queryUnreadNoticeMsgs(pr.getSearchDo(),pager);
		Page<NoticeMsg> page = new Page<NoticeMsg>(pr.getPageNo(),pr.getPageSize(),totalCount.intValue());
		page.setResult(messages);
		return page;
	}

	@Override
	public void markNoticeRead(Long userId, MessageMarkAndDeleteRequestDTO[] ids) {
		Date now = new Date();
		for(MessageMarkAndDeleteRequestDTO id:ids){
			NoticeMsgPush request = new NoticeMsgPush();
			request.setIsRead(1L);
			request.setUpdateDate(now);
			request.setTargetId(id.getId());
			request.setTargetUserId(userId);
			noticeMsgPushDao.updateNoticeMsgPush(request );
		}
	}
}
