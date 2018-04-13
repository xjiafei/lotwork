package com.winterframework.adbox.service;
import java.util.List;

import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdDeviceResource;
public interface IAdDeviceResourceService extends IBaseService<AdDeviceResource> {
	public List<AdDeviceResource> getAdDeviceResourceByDeviceId(Long deviceId,Integer status) throws Exception;
}
