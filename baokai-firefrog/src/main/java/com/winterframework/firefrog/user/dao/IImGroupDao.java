package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.ImGroup;

public interface IImGroupDao {
	
	public List<ImGroup> queryImGroups(ImGroup request);
	
	public ImGroup queryImGroup(ImGroup request);

	public Long insertImGroup(ImGroup request);

}

