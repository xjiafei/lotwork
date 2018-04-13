package com.winterframework.firefrog.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserMessageDao;
import com.winterframework.firefrog.user.dao.IUserMessageReplyDao;
import com.winterframework.firefrog.user.dao.impl.UserMessageDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserMessage;
import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.entity.MessageTopic.MessageType;
import com.winterframework.firefrog.user.service.IMessageService;
import com.winterframework.firefrog.user.service.IMsgReplyStrategy;
import com.winterframework.firefrog.user.service.IMsgReplyStrategyFactory;
import com.winterframework.firefrog.user.web.dto.MessageMarkAndDeleteRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageQueryRequest;
import com.winterframework.firefrog.user.web.dto.MessageRecipient;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * @ClassName: MessageServiceImpl
 * @Description: 用户消息Service
 * @author Page
 * @author Tod
 * @author Denny
 * @date 2013-4-18 下午4:27:28
 * 
 */
@Service("messageServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class MessageServiceImpl extends BaseManager<UserMessageDaoImpl, UserMessage> implements IMessageService {
	private IMsgReplyStrategyFactory replyStrategyFactory;

	@Autowired
	private IUserMessageDao userMessageDao;

	@Autowired
	private IUserMessageReplyDao userMessageReplyDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "msgReplyStrategyFractory")
	private IMsgReplyStrategyFactory msgReplyStrategyFactory;

	public IMsgReplyStrategyFactory getReplyStrategyFactory() {
		return replyStrategyFactory;
	}

	public void setReplyStrategyFactory(IMsgReplyStrategyFactory replyStrategyFactory) {
		this.replyStrategyFactory = replyStrategyFactory;
	}

	@Override
	public Page<MessageTopic> getMessageList(PageRequest<Long> pr) {

		Page<MessageTopic> page = userMessageDao.getMessageList(pr);

		return page;
	}

	@Override
	public void markRead(MessageMarkAndDeleteRequestDTO[] idsAndFlagParam) {
		List<MessageTopic> messageTopicList = new ArrayList<MessageTopic>();
		MessageTopic mt = null;
		for (MessageMarkAndDeleteRequestDTO param : idsAndFlagParam) {
			mt = new MessageTopic();
			mt.setId(param.getId());
			if (param.getFlag() == 0) {
				// 0是发件人
				mt.setSenderRead(true);
			} else if (param.getFlag() == 1) {
				mt.setReceiverRead(true);
			}
			mt.setReadTime(new Date());
			messageTopicList.add(mt);
		}
		this.batchUpdateMessage(messageTopicList);
	}

	@Override
	public void deleteMessages(MessageMarkAndDeleteRequestDTO[] idsAndFlagParam) {
		List<MessageTopic> messageTopicList = new ArrayList<MessageTopic>();
		MessageTopic mt = null;
		for (MessageMarkAndDeleteRequestDTO param : idsAndFlagParam) {
			mt = new MessageTopic();
			mt.setId(param.getId());
			if (param.getFlag() == 0) {
				// 0是发件人
				mt.setSenderFrom(Long.valueOf(MessageTopic.MessageType.DELETE.getIntegerValue()));
			} else if (param.getFlag() == 1) {
				mt.setReceiverFrom(Long.valueOf(MessageTopic.MessageType.DELETE.getIntegerValue()));
			}
			messageTopicList.add(mt);
		}

		this.batchUpdateMessage(messageTopicList);
	}

	@Override
	public void batchUpdateMessage(List<MessageTopic> messageTopicList) {
		userMessageDao.batchUpdateMessage(messageTopicList);
	}

	@Override
	public List<Message> getMessageDetail(long topicId, long userId) {
		MessageTopic mt = userMessageDao.getMessageTopicByTopicId(topicId, userId);
		
		long fromId = Long.valueOf(MessageTopic.MessageType.DELETE.getIntegerValue());
		if (mt.getSender() != null && mt.getSender().getId() == userId) {
			//如果是发件人
			fromId = mt.getSenderFrom();
		} else {
			//如果是收件人
			fromId = mt.getReceiverFrom();
			if (fromId == mt.getId()) {
				//如果一直没人回复，则从第一条开始
				fromId = 0;
			}
		}
		List<Message> messageDetail = new ArrayList<Message>();
		if (fromId > -1) {
			String[] rpls = StringUtils.split(mt.getMsgRoute(), ",");
			if (fromId == Long.parseLong(rpls[0])) {
				//如果没有删除啥的，那么久获取所有的数据
				fromId = 0;
			}
			List<MessageReply> replys = userMessageReplyDao.getMessageReplyList(topicId, fromId,mt);
			if (fromId == 0) {
				messageDetail.add(mt);
			}
			messageDetail.addAll(replys);
		}
		return messageDetail;
	}

	@Override
	public void sendMessages(long[] receiverIds, long senderId, String senderName, String title, String content)
			throws Exception {
		List<MessageTopic> topics = new ArrayList<MessageTopic>();
		User sender = new User();
		UserProfile profile = new UserProfile();
		sender.setId(senderId);
		profile.setAccount(senderName);
		sender.setUserProfile(profile);
		Date now = new Date();
		log.info("用户数 receiverIds.length = " + receiverIds.length);
		// 得到将要插入topic的ID。
		long endId = userMessageDao.selectNextId(receiverIds.length + 1);
		log.info("endId = " + endId);
		long startId = endId - receiverIds.length;
		log.info("startId = " + startId);
		// 构建发送者主题，只有一个。
		MessageTopic topicSender = new MessageTopic();
		topicSender.setContent(content);
		List<User> receiverList = userCustomerDao.queryUserById(receiverIds);
		if (receiverList != null) {
			log.info("receiverList.size() = " + receiverList.size());
		}
		topicSender.setId(startId);
		topicSender.setReceiver(receiverList);
		topicSender.setSender(sender);
		topicSender.setSenderRead(true);
		topicSender.setReadTime(now);
		topicSender.setSendTime(now);
		topicSender.setTitle(title);
		topicSender.setType(MessageTopic.MessageType.NormalMsg);
		topicSender.setMsgRoute(String.valueOf(startId));
		topicSender.setSenderFrom(startId);
		topics.add(topicSender);

		// 构建接受者主题，有多少接受者写多少个主题。
		long index = startId + 1;
		List<User> receivers = userCustomerDao.queryUserById(receiverIds);
		MessageTopic topicReceiver = null;
		for (User receiver : receivers) {
			topicReceiver = new MessageTopic();
			topicReceiver.setContent(content);
			topicReceiver.setReceiverRead(false);
			topicReceiver.setSender(sender);
			topicSender.setSenderRead(true);
			List<User> recList = new ArrayList<User>();
			recList.add(receiver);
			topicReceiver.setReceiver(recList);
			topicReceiver.setReadTime(now);
			topicReceiver.setSendTime(now);
			topicReceiver.setSenderRead(true);
			topicReceiver.setTitle(title);
			topicReceiver.setType(MessageTopic.MessageType.NormalMsg);
			topicReceiver.setMsgRoute(String.valueOf(index));
			topicReceiver.setReceiverFrom(index);
			topicReceiver.setSenderFrom(Long.valueOf(MessageType.MANYSEND.getIntegerValue()));
			topics.add(topicReceiver);
			topicReceiver.setId(index);
			index++;
		}
		log.info("index = " + index);
		userMessageDao.insertMessage(topics);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void sendMessages(long receiverId, long senderId, String senderName, String title, String content)
			throws Exception {
		List<MessageTopic> topics = new ArrayList<MessageTopic>();
		User sender = new User();
		UserProfile profile = new UserProfile();
		sender.setId(senderId);
		profile.setAccount(senderName);
		sender.setUserProfile(profile);
		long nextId = userMessageDao.selectNextId(1);
		MessageTopic topicSender = new MessageTopic();
		Date now = new Date();
		topicSender.setContent(content);
		List<User> receiverList = new ArrayList<User>();
		User receiver = new User();
		receiver.setId(receiverId);
		receiver.setUserProfile(userCustomerDao.queryUserById(receiverId).getUserProfile());
		receiverList.add(receiver);
		topicSender.setReceiver(receiverList);
		topicSender.setReceiverRead(false);
		topicSender.setSender(sender);
		topicSender.setSenderRead(true);
		topicSender.setReadTime(now);
		topicSender.setSendTime(now);
		topicSender.setTitle(title);
		topicSender.setType(MessageTopic.MessageType.NormalMsg);
		topicSender.setMsgRoute(String.valueOf(nextId));
		topicSender.setSenderFrom(nextId);
		topicSender.setReceiverFrom(nextId);
		topicSender.setId(nextId);
		topics.add(topicSender);
		userMessageDao.insertMessage(topics);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void sendMessages(long[] receiverIds, String title, String content, Long effectHours, Long noticeMsgId)
			throws Exception {
		List<MessageTopic> topics = new ArrayList<MessageTopic>();
		long endId = userMessageDao.selectNextId(receiverIds.length);
		long startId = endId - receiverIds.length + 1;
		User sender = new User();
		UserProfile profile = new UserProfile();
		profile.setAccount("系统管理员");
		sender.setId(-1l);
		sender.setUserProfile(profile);
		for (long receiverId : receiverIds) {
			MessageTopic topicSender = new MessageTopic();
			Date now = new Date();
			topicSender.setContent(content);
			List<User> receiverList = new ArrayList<User>();
			User receiver = new User();
			receiver.setId(receiverId);
			receiver.setUserProfile(userCustomerDao.queryUserById(receiverId).getUserProfile());
			receiverList.add(receiver);
			topicSender.setReceiver(receiverList);
			topicSender.setReceiverRead(false);
			topicSender.setSenderRead(false);
			topicSender.setReadTime(now);
			topicSender.setSendTime(now);
			topicSender.setTitle(title);
			topicSender.setSender(sender);
			if (effectHours != null) {
				topicSender.setEffectHours(DateUtils.addHours(now, effectHours.intValue()));
			}
			topicSender.setNoticeMsgId(noticeMsgId);
			topicSender.setId(startId);
			topicSender.setReceiverFrom(startId);
			topicSender.setMsgRoute(String.valueOf(startId));
			startId++;
			topicSender.setType(MessageTopic.MessageType.SystemMsg);
			topics.add(topicSender);
		}
		userMessageDao.insertMessage(topics);
	}

	@Override
	public void setEntityDao(UserMessageDaoImpl entityDao) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUnreadMessagesNumber(long userId) {
		return userMessageDao.getUnreadMessagesNumber(userId);
	}

	@Override
	public void replyMessage(long replier, long topicId, long parentId, String content) throws Exception {

		MessageTopic topic = userMessageDao.getMessageTopicByTopicId(topicId, replier);

		IMsgReplyStrategy replyStrategy = msgReplyStrategyFactory.getMsgReplyStrategy(topic);
		replyStrategy.replyMessage(replier, topicId, parentId, content);

	}

	@Override
	public Page<MessageTopic> getUnReadMessageList(PageRequest<Long> pr) {
		return userMessageDao.getUnReadMessageList(pr);
	}

	@Override
	public Page<MessageTopic> queryMessageList(PageRequest<MessageQueryRequest> pageRequest) {
		return userMessageDao.selectMessageList(pageRequest);
	}

	@Override
	public void deleteMessage(Long noticeId) {
		userMessageDao.deleteMessage(noticeId);
	}

	@Override
	public List<Message> getMessageDetail(long topicId) {
		MessageTopic mt = userMessageDao.getMessageTopicByTopicId(topicId, 0L);
		List<Message> messageDetail = new ArrayList<Message>();
		List<MessageReply> replys = userMessageReplyDao.getMessageReplyList(topicId, -1L,mt);
		messageDetail.add(mt);
		messageDetail.addAll(replys);
		return messageDetail;
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
			// TODO: handle exception
			log.error("(checkUserTo) Error="+e.toString());
			return false;
		}
		
		
	}

}
