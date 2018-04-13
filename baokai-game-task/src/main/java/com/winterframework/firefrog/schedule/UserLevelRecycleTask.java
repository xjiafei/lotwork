package com.winterframework.firefrog.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.schedule.dto.LevelRecycleRequest;
import com.winterframework.firefrog.user.entity.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycle.RecycleStatus;
import com.winterframework.firefrog.user.service.ILevelRecycleService;

@Service("userLevelRecycleTask")
public class UserLevelRecycleTask {
	
	private Logger logger = LoggerFactory.getLogger(UserLevelRecycleTask.class);
	
	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	
	private static final String REDIS_KEY ="USER_TOKEN_";

	public void execute() {
		logger.info("---begin UserLevelRecycleTask----");
		List<LevelRecycle> recycleUserList = queryUndoRecycleUserList();
		logger.info("recycleUserList size:" + recycleUserList.size());
		for (LevelRecycle recycleUser : recycleUserList) {
			try {
				logger.info("levelRecycleId:" + recycleUser.getId());
				levelRecycleService.updateTaskStatus(RecycleStatus.RUNING, recycleUser.getId());
				executeAction(recycleUser, new CleanAwardGroup());
				executeAction(recycleUser, new CleanSafeCenter());
				executeAction(recycleUser, new CleanPersonalInfo());
				executeAction(recycleUser, new CleanBindCard());
				executeAction(recycleUser, new CleanOrderHistory());
				executeAction(recycleUser, new CleanUserMessage());
				executeAction(recycleUser, new ResetPtPassword());
				executeAction(recycleUser, new ResetLoginPassword());
				logger.info("finish recycleStatus:"+recycleUser.getRecycleStatus());
				if(!LevelRecycle.INIT_RECYCLE_STATUS.equals(recycleUser.getRecycleStatus())){
					recycleUser.setActivityDate(new Date());					
				}
				levelRecycleService.updateRecycleStatus(recycleUser); 
				levelRecycleService.updateTaskStatus(RecycleStatus.FINISH, recycleUser.getId());
			} catch (Exception e) {
				logger.error("levelRecycleId:" + recycleUser.getId(), e);		
				levelRecycleService.updateTaskStatus(RecycleStatus.ERROR, recycleUser.getId());				
			}
		}
		logger.info("---end UserLevelRecycleTask----");
	}

	private List<LevelRecycle> queryUndoRecycleUserList(){
		List<LevelRecycle> recycleUserList = null;
		LevelRecycle levelRecycle = new LevelRecycle();
		levelRecycle.setTaskStatus(RecycleStatus.INIT.getIntegerValue());
		try {
			recycleUserList = levelRecycleService.queryLevelRecycleHistory(levelRecycle);
			if (recycleUserList == null) {
				recycleUserList = new ArrayList<LevelRecycle>();
			}
		} catch (Exception e) {
			logger.error("queryUndoRecycleUserList Error:" + e);
		}
		return recycleUserList;
	}

	private void executeAction(LevelRecycle recycleUser, RecycleAction action) {
		String actionName = action.getClass().getSimpleName();
		logger.info(action.getRecycleStatusIndex() + "." + actionName
				+ " execute start");
		try {
			action.invoke(recycleUser);
			updateActionRecycleStatus(recycleUser, action.getRecycleStatusIndex());
		} catch (Exception e) {
			logger.error(actionName, e);
		}
		logger.info(action.getRecycleStatusIndex() + "." + actionName
				+ " execute end");
	}

	private void updateActionRecycleStatus(LevelRecycle recycleUser, int statusIndex) {
		String recycleStatus = recycleUser.getRecycleStatus();
		if(recycleStatus==null){
			recycleStatus = LevelRecycle.INIT_RECYCLE_STATUS;
		}
		char[] statusArray = recycleStatus.toCharArray();
		statusArray[statusIndex] = '1';
		recycleUser.setRecycleStatus(new String(statusArray));
	}

	private interface RecycleAction {
		public void invoke(LevelRecycle recycleUser) throws Exception;
		public int getRecycleStatusIndex();
	}

	private class CleanAwardGroup implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.cleanAwardGroup(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 0;
		}
	}

	private class CleanSafeCenter implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.cleanSafeCenter(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 1;
		}
	}

	private class CleanPersonalInfo implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.cleanPersonalInfo(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 2;
		}
	}

	private class CleanBindCard implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			//查询银行卡
			String cardNumber = "";
			List<BankCard> bankList = levelRecycleService.queryBoundBankCardList(recycleUser.getUserId(), cardNumber);			
			if(bankList!=null && bankList.size()>0){
				//记录与删除
				for (BankCard bankCard : bankList) {
					levelRecycleService.cleanBindCard(
							bankCard.getId(), 
							recycleUser.getUserId(), 
							bankCard.getBank().getId(), 
							bankCard.getMownecumId());				
				}
			}	
		}

		@Override
		public int getRecycleStatusIndex() {
			return 3;
		}
	}

	private class CleanOrderHistory implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.cleanOrderHistory(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 4;
		}
	}

	private class CleanUserMessage implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.cleanUserMessage(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 5;
		}
	}

	private class ResetPtPassword implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.resetPtPassword(levelRecycleRequest);
		}

		@Override
		public int getRecycleStatusIndex() {
			return 6;
		}
	}

	private class ResetLoginPassword implements RecycleAction {
		@Override
		public void invoke(LevelRecycle recycleUser) throws Exception {
			LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
			levelRecycleRequest.setAccount(recycleUser.getAccount());
			levelRecycleRequest.setOperator(recycleUser.getOperator());
			levelRecycleRequest.setUserId(recycleUser.getUserId());
			levelRecycleService.resetLoginPassword(levelRecycleRequest);
			clearUserRedisSession(recycleUser.getUserId());
			clearUserRedisToken(recycleUser.getAccount());
		}

		@Override
		public int getRecycleStatusIndex() {
			return 7;
		}
	}
	
	/**
	 * 回收用户登出
	 * @param userId
	 */
	private void clearUserRedisSession(Long userId) {		
		logger.info("start clearUserRedisSession 登出回收用户...");
		try {
			redisClient.initialPool();
			String key = DigestUtils.md5Hex("ANVO"+userId);
			logger.info("key="+key);
			Set<String> sessionIds= redisClient.smembers(key);
			for(String sessionId:sessionIds){
				logger.info("sessionId="+sessionId);
				redisClient.del(sessionId);
			}
			redisClient.del(key);
		} catch (Exception e) {
			logger.error("clearUserRedisSession error..."+e);
		}
		logger.info("start clearUserRedisSession 回收用户登出成功...");
	}
	
	/**
	 * 清除手機token資訊
	 * @param userId
	 */
	private void clearUserRedisToken(String userAcct) {		
		logger.info("----- [LevelRecycle] clearUserRedisSession start 清除手機token -----");
		try {
			redisClient.initialPool();
			String key = REDIS_KEY + userAcct;
			logger.info("key="+key);
			redisClient.del(key);
		} catch (Exception e) {
			logger.error("clearUserRedisSession error..."+e);
		}
		logger.info("----- [LevelRecycle] clearUserRedisSession end 清除手機token成功 -----");
	}
	

}
