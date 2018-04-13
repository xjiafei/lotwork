package com.winterframework.firefrog.global.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.global.dao.GlobalGrayListDao;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;

@Service("globalGrayListServiceImpl")
public class GlobalGrayListServiceImpl implements IGlobalGrayListService {

	private static final Logger logger = LoggerFactory.getLogger(GlobalGrayListServiceImpl.class);
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	
	@Resource(name = "globalGrayListDaoImpl")
	private GlobalGrayListDao globalGrayListDao;

	public GlobalGrayListVO queryGlobalGrayListByUserId(Long userId) throws Exception{
		return globalGrayListDao.queryGlobalGrayListByUserId(userId);
	}
	
	public Long isGlobalGrayList(Long userId) throws Exception{
		GlobalGrayListVO result =  globalGrayListDao.queryGlobalGrayListByUserId(userId);
		if(result != null){
			if(result.getRiskType() != 1){
				//風險用戶
				return result.getRiskType();
			}else{
				return 0l;
			}
		}else {
			return 0l;
		}
	}
	 
	
	public void saveGlobalGrayList(Long rcvUserId,Long userId){
		try {
			GlobalGrayListVO rcvUser =  globalGrayListDao.queryGlobalGrayListByUserId(rcvUserId);
			GlobalGrayListVO user =  globalGrayListDao.queryGlobalGrayListByUserId(userId);
			Long rcvUserLevel = 0l;
			Long userLevel = 0l;
			if(rcvUser != null){
				rcvUserLevel = rcvUser.getRiskType();
			}
			if(user != null){
				userLevel = user.getRiskType();
			}
			logger.info("轉帳者"+userId+"等級"+userLevel+"，被轉帳者"+rcvUserId+"等級"+rcvUserLevel);
			if((userLevel == 6 ||userLevel == 1 ) && (rcvUserLevel == 0 || rcvUserLevel == 6 || rcvUserLevel == 1) ){
				//轉帳者為沉默用戶、風險沉默用戶，被轉帳者為普通用戶、沉默用戶、風險沉默用戶
				if(rcvUserLevel ==0){
					globalGrayListDao.saveGlobalGrayList(rcvUserId, 7l);
				}else{
					rcvUser.setRiskType(7l);
					globalGrayListDao.updateGlobalGrayList(rcvUser);
				}
			}else if( (userLevel == 8 || userLevel == 7) && (rcvUserLevel == 0 || rcvUserLevel == 1|| rcvUserLevel == 6|| rcvUserLevel == 7) ){
				//轉帳者為風險用戶(高)、風險用戶(中)，被轉帳者為普通用戶、沉默用戶、風險沉默用戶、風險用戶，變更狀態為風險用戶轉帳
				if(rcvUserLevel ==0){
					globalGrayListDao.saveGlobalGrayList(rcvUserId, 8l);
				}else{
					rcvUser.setRiskType(8l);
					globalGrayListDao.updateGlobalGrayList(rcvUser);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("灰名單錯誤:"+e);
		}
		
	}
	
	public void deleteGlobalGrayList(Long userId){
		logger.info("Delete GrayList userId:"+userId);
		globalGrayListDao.deleteGlobalGrayList(userId);
	}
	
	public void updateGlobalGrayList(GlobalGrayListVO globalGrayListVO){
		logger.info("更新灰名單用戶Start");
		logger.info("userId="+globalGrayListVO.getUserId());
		logger.info("riskType="+globalGrayListVO.getRiskType());
		globalGrayListDao.updateGlobalGrayList(globalGrayListVO);
		logger.info("更新灰名單用戶End");
	}
}
