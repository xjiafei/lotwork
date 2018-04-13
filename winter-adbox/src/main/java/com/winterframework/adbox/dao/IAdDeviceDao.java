package com.winterframework.adbox.dao;

import java.util.List;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dao.base.IBaseDao;
import com.winterframework.adbox.entity.AdDevice;

public interface IAdDeviceDao extends IBaseDao<AdDevice>{
	List<AdDevice> getAdDeviceList(Context ctx, Long userId,
			String code, String sort, int startNo, int endNo) throws Exception;
}
