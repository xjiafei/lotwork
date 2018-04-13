package com.winterframework.adbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.dao.IAdDeviceDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;
import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.entity.AdDevice;
import com.winterframework.adbox.service.IAdDeviceService;
import com.winterframework.adbox.utils.DateUtils;


@Service("adDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdDeviceServiceImpl  extends BaseServiceImpl<IAdDeviceDao,AdDevice> implements IAdDeviceService {
	@Resource(name="adDeviceDaoImpl")
	private IAdDeviceDao dao;
	@Override
	protected IAdDeviceDao getEntityDao() {
		return dao;
	}
	@Override
	public List<AdDevice> getAdDeviceByCode(Context ctx, Long userId,
			String code) throws Exception {
		AdDevice adDevice = new AdDevice();
		adDevice.setUserId(userId);
		adDevice.setCode(code);
		return this.selectListObjByAttribute(ctx, adDevice);
	}
	@Override
	public List<AdDevice> getAdDeviceList(Context ctx, Long userId,
			String code, String sort, int startNo, int endNo) throws Exception {
		return dao.getAdDeviceList(ctx, userId, code, sort, startNo, endNo);
	}
	@Override
	public int save(Context ctx, AdDevice entity, int tag) throws Exception{
		if(null!=entity){
			try{
				if(null==entity.getId()){
					entity.setCreator(ctx.getUserId());
					entity.setGmtCreated(DateUtils.currentDate());
					return getEntityDao().insert(entity); 
				}else{
					return getEntityDao().update(entity);
				}
			}catch(Exception e){
				log.error("save failed.",e);
				throw new Exception(e);
			}
		}
		return 0;
	}
	
	
}
