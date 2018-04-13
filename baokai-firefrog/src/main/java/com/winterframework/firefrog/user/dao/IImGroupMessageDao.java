package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.ImGroupMessage;
import com.winterframework.firefrog.user.entity.UserInboxMessage;
import com.winterframework.modules.web.jsonresult.Pager;

public interface IImGroupMessageDao {

	public List<ImGroupMessage> queryImGroupMessages(ImGroupMessage request,Pager pager);
	
	public Long insertImGroupMessage(ImGroupMessage request);

	public List<UserInboxMessage> queryUserInboxMessages(Long userId, Pager pager);

	public Long queryUserInboxMessageCount(Long userId);

	public Long queryUserInboxMsgTotalUnreadCount(Long userId);

	public List<UserInboxMessage> queryUserInboxUnreadMessages(Long userId, Pager pager);

	public List<UserInboxMessage> queryUserInboxMessageDetails(Long groupId,
			Long userId, Pager pager);

	public Long queryAllMessageCount(ImGroupMessage request);

	public List<UserInboxMessage> queryUserAllMessages(ImGroupMessage request,
			Pager pager);

	public List<UserInboxMessage> queryAllMessageDetails(long groupId,
			Pager pager);
	
}

