package com.winterframework.adbox.dao;

import com.winterframework.adbox.dao.base.IBaseDao;
import com.winterframework.adbox.entity.AdVersionResource;

public interface IAdVersionResourceDao extends IBaseDao<AdVersionResource>{
	public Long getLastVersion(Long userId) throws Exception;
}
