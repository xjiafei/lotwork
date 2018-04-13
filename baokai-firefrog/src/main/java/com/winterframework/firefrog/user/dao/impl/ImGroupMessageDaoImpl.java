package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DaoPageUtil;
import com.winterframework.firefrog.user.dao.IImGroupMessageDao;
import com.winterframework.firefrog.user.entity.ImGroupMessage;
import com.winterframework.firefrog.user.entity.UserInboxMessage;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("imGroupMessageDaoImpl")
public class ImGroupMessageDaoImpl extends BaseIbatis3Dao<ImGroupMessage> implements IImGroupMessageDao {

	@Override
	public List<ImGroupMessage> queryImGroupMessages(ImGroupMessage request, Pager pager) {
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryImGroupMessages"),request,rowBounds);
	}

	@Override
	public Long insertImGroupMessage(ImGroupMessage request) {
		return (long) this.sqlSessionTemplate.insert(this.getQueryPath("insertImGroupMessage"),request);
	}
	
	@Override
	public List<UserInboxMessage> queryUserInboxMessages(Long userId, Pager pager){
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetUserId(userId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryUserInboxMessages"),request ,rowBounds);
	}
	
	@Override
	public List<UserInboxMessage> queryUserInboxUnreadMessages(Long userId, Pager pager){
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetUserId(userId);
		request.setTargetHasUnreadMsg(true);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryUserInboxMessages"),request ,rowBounds);
	}
	
	@Override
	public List<UserInboxMessage> queryUserInboxMessageDetails(Long groupId,Long userId, Pager pager){
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetUserId(userId);
		request.setTargetGroupId(groupId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryUserInboxMessageDetails"),request ,rowBounds);
	}
	
	@Override
	public Long queryUserInboxMessageCount(Long userId){
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetUserId(userId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryUserInboxMessageCount"),request);
	}

	@Override
	public Long queryUserInboxMsgTotalUnreadCount(Long userId){
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetUserId(userId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryUserInboxMsgTotalUnreadCount"),request);
	}

	@Override
	public Long queryAllMessageCount(ImGroupMessage request) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryAllMessageCount"),request);
	}

	@Override
	public List<UserInboxMessage> queryUserAllMessages(ImGroupMessage request,
			Pager pager) {
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryUserAllMessages"),request ,rowBounds);
	}

	@Override
	public List<UserInboxMessage> queryAllMessageDetails(long groupId,
			Pager pager) {
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		ImGroupMessage request = new ImGroupMessage();
		request.setTargetGroupId(groupId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryAllMessageDetails"),request  ,rowBounds);
	}

}

