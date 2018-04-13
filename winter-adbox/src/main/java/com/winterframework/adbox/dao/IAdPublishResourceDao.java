package com.winterframework.adbox.dao;

import com.winterframework.adbox.dao.base.IBaseDao;
import com.winterframework.adbox.entity.AdPublishDevice;

public interface IAdPublishResourceDao extends IBaseDao<AdPublishDevice>{
	public AdPublishDevice getLastAdPublishDevice(Long deviceId,Long version) throws Exception;
}
