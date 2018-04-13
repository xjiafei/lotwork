package com.winterframework.firefrog.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DaoPageUtil;
import com.winterframework.firefrog.user.dao.INoticeMsgPushDao;
import com.winterframework.firefrog.user.entity.NoticeMsg;
import com.winterframework.firefrog.user.entity.NoticeMsgPush;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("noticeMsgPushDaoImpl")
public class NoticeMsgPushDaoImpl extends BaseIbatis3Dao<NoticeMsgPush> implements INoticeMsgPushDao {

	@Override
	public Long insertNoticeMsgPush(NoticeMsgPush request) {
		return (long) this.sqlSessionTemplate.insert(this.getQueryPath("insert"), request);
	}

	@Override
	public List<NoticeMsg> queryUnreadNoticeMsgs(Long userId,Pager pager){
		RowBounds rowBounds = DaoPageUtil.createRowBounds(pager);
		NoticeMsgPush request = new NoticeMsgPush();
		request.setTargetUserId(userId);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryUnreadNoticeMsgs"), request,rowBounds);
	}

	@Override
	public Long queryUnreadNoticeMsgCount(Long userId) {
		NoticeMsgPush request = new NoticeMsgPush();
		request.setTargetUserId(userId);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("queryUnreadNoticeMsgCount"), request);
	}

	@Override
	public Long updateNoticeMsgPush(NoticeMsgPush request){
		return (long)this.sqlSessionTemplate.update(this.getQueryPath("updateNoticeMsgPush"), request);
	}
}

