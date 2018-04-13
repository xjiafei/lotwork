package com.winterframework.adbox.service;

import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdPublishDevice;
public interface IAdPublishResourceService extends IBaseService<AdPublishDevice> {
	public AdPublishDevice getLastAdPublishDevice(Long deviceId,Long version) throws Exception;
}
