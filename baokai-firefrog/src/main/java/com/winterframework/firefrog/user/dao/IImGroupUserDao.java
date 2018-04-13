package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.ImGroupUser;

public interface IImGroupUserDao {

	public List<ImGroupUser> queryImGroupUsers(ImGroupUser request);
	
	public Long queryImGroupUserCount(ImGroupUser request);
	
	public ImGroupUser queryImGroupUser(ImGroupUser request);
	
	public Long insertImGroupUser(ImGroupUser request);
	
	public Long updateImGroupUser(ImGroupUser request);

	public Long addImGroupUserWithoutOwner(ImGroupUser request);

	public Long updateImGroupUserByUserId(ImGroupUser request);
	
}

