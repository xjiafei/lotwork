package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.NoticeMsg;
import com.winterframework.firefrog.user.entity.NoticeMsgPush;
import com.winterframework.modules.web.jsonresult.Pager;

public interface INoticeMsgPushDao {

	public Long insertNoticeMsgPush(NoticeMsgPush request);

	public List<NoticeMsg> queryUnreadNoticeMsgs(Long userId, Pager pager);

	public Long queryUnreadNoticeMsgCount(Long userId);

	public Long updateNoticeMsgPush(NoticeMsgPush request);

}

