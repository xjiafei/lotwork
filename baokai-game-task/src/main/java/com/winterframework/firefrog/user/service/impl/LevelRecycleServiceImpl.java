package com.winterframework.firefrog.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.game.dao.IGameAwardUserGroupDao;
import com.winterframework.firefrog.schedule.dto.GameAwardUserGroupDTO;
import com.winterframework.firefrog.schedule.dto.LevelRecycleRequest;
import com.winterframework.firefrog.schedule.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.user.dao.IImGroupUserDao;
import com.winterframework.firefrog.user.dao.ILevelRecycleDao;
import com.winterframework.firefrog.user.dao.IUserCustomerRecycleDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.ImGroupUser;
import com.winterframework.firefrog.user.entity.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycle.PtResultStatus;
import com.winterframework.firefrog.user.entity.LevelRecycle.RecycleStatus;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.firefrog.user.entity.UserListResult;
import com.winterframework.firefrog.user.entity.UserQueryUserListRequest;
import com.winterframework.firefrog.user.entity.UserRecycleRequest;
import com.winterframework.firefrog.user.entity.UserRecycleResult;
import com.winterframework.firefrog.user.entity.UserResetPwdRequest;
import com.winterframework.firefrog.user.entity.UserResetPwdResult;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 类功能说明: 一代回收
 */
@Service("levelRecycleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class LevelRecycleServiceImpl implements ILevelRecycleService {

	private Logger logger = LoggerFactory
			.getLogger(LevelRecycleServiceImpl.class);

	@Resource(name = "levelRecycleDaoImpl")
	private ILevelRecycleDao levelRecycleDao;

	@Resource(name = "userCustomerRecycleDaoImpl")
	private IUserCustomerRecycleDao userCustomerRecycleDao;

	@Resource(name = "gameAwardUserGroupDaoImpl")
	private IGameAwardUserGroupDao gameAwardUserGroupDao;

	@Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
	
	@Resource(name = "imGroupUserDaoImpl")
	private IImGroupUserDao imGroupUserDao;

	@Resource(name = "httpJsonClientImpl")
	protected IHttpJsonClient httpPtClient;

	@PropertyConfig(value = "pt.url")
	private String ptURL;

	@PropertyConfig(value = "url.pt.user.admin.resetPwd")
	private String ptresetPwd;

	@PropertyConfig(value = "url.pt.user.queryUserList")
	private String ptqueryUserList;
	
	@PropertyConfig(value = "url.pt.user.admin.recycleUser")
	private String ptrecycleUser;

	@Override
	public void cleanAwardGroup(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] cleanAwardGroup start ------");
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,operator="+levelRecycleRequest.getOperator());
		GameAwardUserGroupDTO gameAwardUserGroupDTO = new GameAwardUserGroupDTO();
		gameAwardUserGroupDTO.setUserId(levelRecycleRequest.getUserId());
		gameAwardUserGroupDao
				.updateGameAwardGruopBetType(gameAwardUserGroupDTO);
		logger.info("------ [LevelRecycle] cleanAwardGroup end ------");
	}

	@Override
	public void cleanSafeCenter(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] cleanSafeCenter start ------");
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setId(levelRecycleRequest.getUserId());
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		userCustomerRecycleDao.cleanSafeCenter(userCustomer);
		logger.info("------ [LevelRecycle] cleanSafeCenter end ------");
	}

	@Override
	public void cleanPersonalInfo(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] cleanPersonalInfo start ------");
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		userCustomerRecycleDao.cleanPersonalInfo(userCustomer);
		logger.info("------ [LevelRecycle] cleanPersonalInfo end ------");
	}

	@Override
	public void cleanBindCard(long bindId, long userId, long bankId,
			long mcBankI) throws Exception {
		logger.info("------ [LevelRecycle] cleanBindCard start ------");
		logger.info("userId="+userId+", bindId="+bindId+" ,bankId="+bankId+" ,mcBankI="+mcBankI);
		// 1、添加历史绑卡记录
		this.addCardBindHistoryRecord(2L, userId, bindId);
		// 2、删除绑卡
		bankCardDao.removeBankCard(bindId);
		logger.info("------ [LevelRecycle] cleanBindCard end ------");
	}

	// 添加历史绑卡记录
	private void addCardBindHistoryRecord(long action, long userId, long bindId) {
		logger.info("------ addCardBindHistoryRecord start ------");
		UserBank userBank = bankCardDao.getById(bindId);
		logger.info("BankId="+userBank.getBankId());
		UserCardBindHistory userCardBindHistoryRecord = new UserCardBindHistory();
		BankCard bankCard = new BankCard();
		bankCard.setId(userBank.getBankId());
		bankCard.setAccountHolder(userBank.getBankAccount());
		bankCard.setProvince(userBank.getProvince());
		bankCard.setCity(userBank.getCity());
		bankCard.setSubBranch(userBank.getBranchName());
		bankCard.setMownecumId(userBank.getMcBankId());
		bankCard.setBankCardNo(userBank.getBankNumber());
		userCardBindHistoryRecord.setAction(action);
		userCardBindHistoryRecord.setActionTime(new Date());
		userCardBindHistoryRecord.setUserId(userId);
		userCardBindHistoryRecord.setBankCard(bankCard);
		bankCardDao.addBankCardHistoryRecord(userCardBindHistoryRecord);
		logger.info("------ addCardBindHistoryRecord end ------");
	}

	@Override
	public void cleanOrderHistory(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] cleanOrderHistory start ------");
		logger.info("URL="+ptURL+ptrecycleUser);
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		Response<UserRecycleResult> response = new Response<UserRecycleResult>();
		UserRecycleRequest userRecycleRequest = new UserRecycleRequest();
		userRecycleRequest.setFfId(levelRecycleRequest.getUserId());		
		try {
			response = httpPtClient.invokeHttp(
					ptURL + ptrecycleUser, 
					userRecycleRequest,
					new TypeReference<Response<UserRecycleResult>>(){});
			if(response!=null){
				UserRecycleResult userRecycleResult =  response.getBody().getResult();
				if(null==userRecycleResult 
						|| userRecycleResult.getStatus()== PtResultStatus.FAIL.getIntegerValue()){
					logger.info("ptrecycleUser response="+response);
					throw new Exception("clean PT order history fail!");
				}			
			}else{
				throw new Exception("clean PT order history response null !");
			}
		} catch (Exception e) {
			logger.error("cleanOrderHistory error:"+e);
		}		
		logger.info("------ [LevelRecycle] cleanOrderHistory end ------");
	}

	@Override
	public void resetPtPassword(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] resetPtPassword start ------");
		logger.info("URL="+ptURL+ptrecycleUser);
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		String ptAccount = "";
		Response<UserListResult> userListResponse = new Response<UserListResult>();
		UserQueryUserListRequest userQueryUserListRequest = new UserQueryUserListRequest();
		userQueryUserListRequest.setAccountType(1L);
		userQueryUserListRequest.setPtAccount(levelRecycleRequest.getAccount());
		try {
			userListResponse = httpPtClient.invokeHttp(ptURL + ptqueryUserList,
					userQueryUserListRequest,
					new TypeReference<Response<UserListResult>>(){});
			if (userListResponse != null && userListResponse.getBody().getResult().getUserCustomerList().size()>0) {
				ptAccount = userListResponse.getBody().getResult()
						.getUserCustomerList().get(0).getPtAccount();
				logger.info("ptAccount="+ptAccount);
				Response<UserResetPwdResult> response = new Response<UserResetPwdResult>();
				UserResetPwdRequest resetPwdRequest = new UserResetPwdRequest();
				resetPwdRequest.setPtAccount(ptAccount);
				resetPwdRequest.setUserIp(0L);
				response = httpPtClient.invokeHttp(ptURL + ptresetPwd, resetPwdRequest,
						new TypeReference<Response<UserResetPwdResult>>(){});
				if(null==response.getBody().getResult()
						||response.getBody().getResult().getStatus()==LevelRecycle.PT_CHANGPWD_FAIL){
					logger.info("ptresetPwd result="+response.getBody().getResult());
					throw new Exception("ptresetPwd fail!");
				}
			}else{
				logger.info("No ptAccount user!");
			}
		} catch (Exception e) {
			logger.error("resetPtPassword error:"+e);
		}
		logger.info("------ [LevelRecycle] resetPtPassword end ------");
	}

	@Override
	public void resetLoginPassword(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] resetLoginPassword start ------");
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		userCustomer.setPasswd(DigestUtils.md5Hex(DigestUtils
				.md5Hex(DigestUtils.md5Hex(LevelRecycle.DEFAULT_PASSWD))));
		userCustomerRecycleDao.cleanSafeCenter(userCustomer);
		logger.info("------ [LevelRecycle] resetLoginPassword end ------");
	}

	@Override
	public List<BankCard> queryBoundBankCardList(long userId, String cardNumber)
			throws Exception {
		return bankCardDao.getBoundBankCard(userId, cardNumber);
	}

	@Override
	public void cleanUserMessage(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("------ [LevelRecycle] cleanUserMessage start ------");
		logger.info("userId="+levelRecycleRequest.getUserId()+" ,account="+levelRecycleRequest.getAccount());
		Long userId = levelRecycleRequest.getUserId();
		ImGroupUser request = new ImGroupUser();
		Date now = new Date();
		request.setHistoryStartTime(now);
		request.setUnreadCount(0L);
		request.setLastUpdateDate(now);
		request.setTargetUserId(userId);
		imGroupUserDao.updateImGroupUserByUserId(request);
		logger.info("------ [LevelRecycle] cleanUserMessage end ------");
	}

	@Override
	public boolean isLevelRecycleFirstLogin(
			QueryLevelRecycleHistoryRequest queryLRHistoryRequest)
			throws Exception {
		boolean flag = false;
		String account = queryLRHistoryRequest.getAccount();
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		levelRecycleDTO.setAccount(account);
		levelRecycleDTO.setStartNo(queryLRHistoryRequest.getStartNo());
		levelRecycleDTO.setEndNo(queryLRHistoryRequest.getEndNo());
		levelRecycleDTO.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());
		List<LevelRecycle> levelRecycleList = levelRecycleDao
				.queryLevelRecycleHistory(levelRecycleDTO);
		if (levelRecycleList != null && levelRecycleList.size() > 0) {
			// 检查密码
			UserCustomer userCustomer = new UserCustomer();
			userCustomer.setAccount(account);
			String maskPasswd = DigestUtils.md5Hex(DigestUtils
					.md5Hex(DigestUtils.md5Hex(LevelRecycle.DEFAULT_PASSWD)));
			userCustomer.setPasswd(maskPasswd);
			Long result = userCustomerRecycleDao
					.getCountByAccountPasswd(userCustomer);
			if (result != null && result > 0) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycle levelRecycle)
			throws Exception {
		logger.info("------ [LevelRecycle] queryLevelRecycleHistory start ------");
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		levelRecycleDTO.setAccount(levelRecycle.getAccount());
		levelRecycleDTO.setTaskStatus(levelRecycle.getTaskStatus());
		levelRecycleDTO.setUserId(levelRecycle.getUserId());
		logger.info("------ [LevelRecycle] queryLevelRecycleHistory end ------");
		return levelRecycleDao.queryLevelRecycleHistory(levelRecycleDTO);
	}

	@Override
	public void updateTaskStatus(RecycleStatus taskStatus, Long levelRecycleId) {
		logger.info("------ [LevelRecycle] updateTaskStatus start ------");
		try {
			LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
			levelRecycleDTO.setTaskStatus(taskStatus.getIntegerValue());
			levelRecycleDTO.setId(levelRecycleId);
			levelRecycleDTO.setUpdateDate(new Date());
			levelRecycleDao.updateRecycleStatus(levelRecycleDTO);
		} catch (Exception e) {
			logger.error("updateTaskStatus Error:" + e);
		}
		logger.info("------ [LevelRecycle] updateTaskStatus end ------");
	}

	@Override
	public void updateRecycleStatus(LevelRecycle recycleUser) throws Exception {
		logger.info("------ [LevelRecycle] updateRecycleStatus start ------");
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		levelRecycleDTO.setAccount(recycleUser.getAccount());
		levelRecycleDTO.setId(recycleUser.getId());
		levelRecycleDTO.setRecycleStatus(recycleUser.getRecycleStatus());
		levelRecycleDTO.setUpdateDate(new Date());
		levelRecycleDTO.setActivityDate(recycleUser.getActivityDate());
		levelRecycleDao.updateRecycleStatus(levelRecycleDTO);
		logger.info("------ [LevelRecycle] updateRecycleStatus end ------");
	}

}
