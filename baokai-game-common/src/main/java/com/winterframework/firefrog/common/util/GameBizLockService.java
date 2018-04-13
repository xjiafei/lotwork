package com.winterframework.firefrog.common.util;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.entity.GameBizLock;
import com.winterframework.firefrog.game.enums.GameBizOperType;

/**
 * 业务操作锁服务
 * 
 * @ClassName
 * @Description
 * @author ibm 2014年10月16日 
 */
@Service("gameBizLockService") 
public class GameBizLockService {  
	/**加业务锁步骤：
	 * 1.GameBizOperType中添加枚举
	 * 2.需要加业务锁之处添加如下代码
	 * 		GameBizLock bizLock=new GameBizLock(GameBizOperType.CANCEL_ORDER.getCode(),String.valueOf(orderId),String.valueOf(userId));
	 * 		gameBizLockService.lock(bizLock);
	 * 		try{
	 * 
	 * 		}finally{
	 * 			gameBizLockService.unlock(bizLock);
	 * 		}
	 */
	private static final Logger log = LoggerFactory
			.getLogger(GameBizLockService.class); 
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	
	private final String seperator="_SEPERATOR_";
	 
	public void lock(GameBizLock bizLock) throws Exception { 
		
		String bizType=bizLock.getBizType();
		String bizObj=bizLock.getBizObj();
		String user=bizLock.getUser();
		if(bizType==null || bizObj==null || user==null){
			throw new Exception("Lock parameter is invalid.");
		}
		String key=bizType+seperator+bizObj;
		String lockUser="";
		Long lockTime=0L;
		Long nowTime=DateUtils.currentDate().getTime(); 
		String value=user+seperator+nowTime;
		String userAndTime=redisClient.get(key);
		if(userAndTime!=null){
			String[] userAndTimeArr=userAndTime.split(seperator);
			lockUser=userAndTimeArr[0];
			lockTime=Long.valueOf(userAndTimeArr[1]);
			throw new Exception("Object is locked by user("+lockUser+")");
		}
		redisClient.set(key,value,600); //lifecycle:10mins
		log.info("业务锁定(key="+key+";value="+value+")");
	} 
	@Deprecated
	public void unlock(GameBizLock bizLock) throws Exception {
		String bizType=bizLock.getBizType();
		String bizObj=bizLock.getBizObj();
		String user=bizLock.getUser();
		if(bizType==null || bizObj==null || user==null){
			throw new Exception("Unlock parameter is invalid.");
		}
		String key=bizType+seperator+bizObj; 
		redisClient.del(key);  
		
		log.info("业务解锁(key="+key+")");
	}

}
