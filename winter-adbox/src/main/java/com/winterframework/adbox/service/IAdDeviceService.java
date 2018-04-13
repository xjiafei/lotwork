package com.winterframework.adbox.service;
import java.util.List;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdDevice;
public interface IAdDeviceService extends IBaseService<AdDevice> {
	public List<AdDevice> getAdDeviceByCode(Context ctx,Long userId,String code) throws Exception;
	public List<AdDevice> getAdDeviceList(Context ctx,Long userId,String code,String sort,int startNo,int endNo) throws Exception;
	public int save(Context ctx,AdDevice entity,int tag) throws Exception;
}
