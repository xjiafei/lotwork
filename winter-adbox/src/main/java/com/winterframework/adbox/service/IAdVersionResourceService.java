package com.winterframework.adbox.service;

import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdVersionResource;
public interface IAdVersionResourceService extends IBaseService<AdVersionResource> {
	public Long getLastVersion(Long userId) throws Exception;
	public AdVersionResource getAdVersionResourceByResId(String resourceId,Long userId) throws Exception;
	
	public AdVersionResource getByUserIdAndVersion(Long userId,Long version)throws Exception;
}
