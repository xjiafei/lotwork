package com.winterframework.firefrog.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpClient;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.user.dao.IGameAwardUserGroupDao;
import com.winterframework.firefrog.user.dao.IImGroupUserDao;
import com.winterframework.firefrog.user.dao.ILevelRecycleDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle.RecycleIndex;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle.RecycleStatus;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserCustomerVo;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupDTO;
import com.winterframework.firefrog.user.entity.ImGroupUser;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserBalanceResult;
import com.winterframework.firefrog.user.entity.UserCustomerRequest;
import com.winterframework.firefrog.user.entity.UserListResult;
import com.winterframework.firefrog.user.entity.UserQueryUserListRequest;
import com.winterframework.firefrog.user.entity.UserRecycleRequest;
import com.winterframework.firefrog.user.entity.UserRecycleResult;
import com.winterframework.firefrog.user.entity.UserResetPwdRequest;
import com.winterframework.firefrog.user.entity.UserResetPwdResult;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.ApplyLevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.LevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListResponse;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 类功能说明: 一代回收
 */
@Service("levelRecycleServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LevelRecycleServiceImpl implements ILevelRecycleService {

	private Logger logger = LoggerFactory.getLogger(LevelRecycleServiceImpl.class);
	private static IPSeeker ipseek = IPSeeker.getInstance();
	
	@Resource(name = "levelRecycleDaoImpl")
	private ILevelRecycleDao levelRecycleDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameAwardUserGroupDaoImpl")
	private IGameAwardUserGroupDao gameAwardUserGroupDao;

	@Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
	
	@Resource(name = "imGroupUserDaoImpl")
	private IImGroupUserDao imGroupUserDao;
	
	@Resource(name = "HttpClientImpl")
	protected IHttpClient httpPtClient;
	
	@PropertyConfig(value = "pt.url")
	private String ptURL;

	@PropertyConfig(value = "url.pt.user.admin.resetPwd")
	private String ptresetPwd;

	@PropertyConfig(value = "url.pt.user.queryUserList")
	private String ptqueryUserList;
	
	@PropertyConfig(value = "url.pt.user.admin.recycleUser")
	private String ptrecycleUser;
	
	@PropertyConfig(value = "url.pt.user.balance")
	private String ptbalance;
	
	private final int USER_LVL_1 = 1;

	@Override
	public Response<List<QueryLevelRecycleHistoryResponse>> queryLevelRecycleHistory(
			PageRequest<LevelRecycleDTO> pageReqeust)
			throws Exception {
		logger.info("----- [LevelRecycle] queryLevelRecycleHistory start -----");
		logger.info("PageNo="+pageReqeust.getPageNo()+" ,PageNumber="+pageReqeust.getPageNumber()
				+" ,Account="+pageReqeust.getSearchDo().getAccount());
		pageReqeust.getSearchDo().setSortColums("CREATE_DATE DESC");
		Page<LevelRecycle> levelRecycleResult = levelRecycleDao.queryLevelRecycleHistory(pageReqeust);
		List<LevelRecycle> recycleList = levelRecycleResult.getResult();
		List<QueryLevelRecycleHistoryResponse> resultList = null;
		if(recycleList!=null){
			resultList = fillLRHistoryResponse(recycleList);			
		}else{
			logger.info("levelRecycleDao.queryLevelRecycleHistory recycleList is null!");
		}
		Response<List<QueryLevelRecycleHistoryResponse>> response = 
				new Response<List<QueryLevelRecycleHistoryResponse>>();
		ResultPager pager = new ResultPager();
		pager.setTotal(levelRecycleResult.getTotalCount());		
		response.setResult(resultList);
		response.setResultPage(pager);		
		logger.info("----- [LevelRecycle] queryLevelRecycleHistory end -----");		
		return response;
	}	

	@Override
	public void applyLevelRecycle(
			ApplyLevelRecycleRequest applyLevelRecycleRequest) throws Exception {
		logger.info("----- [LevelRecycle] applyLevelRecycle start -----");
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		levelRecycleDTO.setAccount(applyLevelRecycleRequest.getAccount());
		levelRecycleDTO.setAvailBal(applyLevelRecycleRequest.getAvailBal());
		levelRecycleDTO.setOperator(applyLevelRecycleRequest.getOperator());
		levelRecycleDTO.setRecycleReason(applyLevelRecycleRequest.getRecycleReason());
		levelRecycleDTO.setTopAgent(applyLevelRecycleRequest.getTopAgent());
		levelRecycleDTO.setUserId(applyLevelRecycleRequest.getUserId());
		levelRecycleDTO.setCreateDate(applyLevelRecycleRequest.getCreateDate());
		levelRecycleDTO.setTaskStatus(applyLevelRecycleRequest.getTaskStatus());
		levelRecycleDTO.setAvailPtBal(applyLevelRecycleRequest.getAvailPtBal());
		levelRecycleDTO.setLastLoginDate(applyLevelRecycleRequest.getLastLoginDate());
		levelRecycleDTO.setLastLoginIp(applyLevelRecycleRequest.getLastLoginIp());
		levelRecycleDTO.setLastLoginAddress(applyLevelRecycleRequest.getLastLoginAddress());
		levelRecycleDao.applyLevelRecycle(levelRecycleDTO);
		logger.info("----- [LevelRecycle] applyLevelRecycle end -----");
	}

	@Override
	public QueryLevelRecycleListResponse queryLevelRecycleList(
			QueryLevelRecycleListRequest queryLevelRecycleListRequest)
			throws Exception {
		logger.trace("----- [LevelRecycle] queryLevelRecycleList start -----");
		logger.trace("account="+queryLevelRecycleListRequest.getAccount());
		QueryLevelRecycleListResponse queryLRListResponse = null;
		User user = userCustomerDao.queryUserByName(queryLevelRecycleListRequest.getAccount());
		if (user != null) {
			//只显示一级用户
			if(user.getUserLevel()==USER_LVL_1){
				queryLRListResponse = new QueryLevelRecycleListResponse();
				queryLRListResponse.setUserId(user.getId());
				queryLRListResponse.setUserLvl(user.getUserLevel());
				queryLRListResponse.setVipLvl(user.getVipLvl());
				if(null!=user.getFund()){
					queryLRListResponse.setAvailBal(user.getFund().getBal());					
				}else{
					logger.info("User.UserFund is null!");
				}
				if(null!=user.getLastLoginLog()){
					queryLRListResponse.setLastLoginDate(user.getLastLoginLog().getLoginDate());
					queryLRListResponse.setLastLoginIp(user.getLastLoginLog().getLoginIP());
					if(null!=user.getLastLoginLog().getLoginIP()){
						queryLRListResponse.setLastLoginAddress(ipseek.getAddress(IPConverter.longToIp(user.getLastLoginLog().getLoginIP())));
					}
				}else{
					logger.info("User.LoginLog is null!");
				}
				if (null!=user.getUserProfile()) {
					queryLRListResponse.setAccount(user.getUserProfile().getAccount());
					queryLRListResponse.setTopAgent(getTopAgent(user.getUserProfile().getUserChain()));
			//		User topAgent = userCustomerDao.queryUserByName(queryLRListResponse.getTopAgent());
			//		queryLRListResponse.setAgentVipLvl(topAgent.getVipLvl());
				}else{
					logger.info("User.UserProfile is null!");
				}
				//pt游戏馀额
				LevelRecycleRequest levelRecycleRequest = new LevelRecycleRequest();
				levelRecycleRequest.setAccount(queryLevelRecycleListRequest.getAccount());
				UserCustomerVo userCustomerVo = this.getPtUserInfo(levelRecycleRequest);
				if(userCustomerVo!=null){
					String ptAccount = userCustomerVo.getPtAccount();
					UserBalanceResult userBalanceResult = this.getPtBalance(ptAccount);
					if(userBalanceResult!=null){
						queryLRListResponse.setAvailPtBal(userBalanceResult.getPtAvailBal()); 						
					}
				}else{
					logger.info("PtUserInfo is null!");
				}
			}else{
				logger.info("Not level_1 user!");
			}
		}else{
			logger.info("userCustomerDao.queryUserByName response.user is null!");
		}
		logger.info("----- [LevelRecycle] queryLevelRecycleList end -----");
		return queryLRListResponse;
	}

	private List<QueryLevelRecycleHistoryResponse> fillLRHistoryResponse(
			List<LevelRecycle> levelRecycleList) {
		List<QueryLevelRecycleHistoryResponse> response = new ArrayList<QueryLevelRecycleHistoryResponse>();

		for (LevelRecycle levelRecycle : levelRecycleList) {
			QueryLevelRecycleHistoryResponse gHistoryResponse = new QueryLevelRecycleHistoryResponse();
			gHistoryResponse.setAccount(levelRecycle.getAccount());
			gHistoryResponse.setActivityDate(levelRecycle.getActivityDate());
			gHistoryResponse.setAvailBal(levelRecycle.getAvailBal());
			gHistoryResponse.setCreateDate(levelRecycle.getCreateDate());
			gHistoryResponse.setOperator(levelRecycle.getOperator());
			gHistoryResponse.setRecycleReason(levelRecycle.getRecycleReason());
			gHistoryResponse.setRecycleStatus(levelRecycle.getRecycleStatus());
			gHistoryResponse.setTaskStatus(levelRecycle.getTaskStatus());
			gHistoryResponse.setTopAgent(levelRecycle.getTopAgent());
			gHistoryResponse.setUserId(levelRecycle.getUserId());
			gHistoryResponse.setId(levelRecycle.getId());
			gHistoryResponse.setAvailPtBal(levelRecycle.getAvailPtBal());
			gHistoryResponse.setLastLoginDate(levelRecycle.getLastLoginDate());
			gHistoryResponse.setLastLoginIp(levelRecycle.getLastLoginIp());
			gHistoryResponse.setLastLoginAddress(levelRecycle.getLastLoginAddress());
			gHistoryResponse.setVipLvl(levelRecycle.getVipLvl());
			response.add(gHistoryResponse);
		}
		return response;
	}

	private String getTopAgent(String userChain) {
		String topAgent = "";
		if (userChain != "" && userChain != null) {
			String[] array = userChain.split("/");
			topAgent = array[1];
		}
		return topAgent;
	}

	@Override
	public void cleanAwardGroup(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] cleanAwardGroup start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,userId="+levelRecycleRequest.getUserId());		
		GameAwardUserGroupDTO gameAwardUserGroupDTO = new GameAwardUserGroupDTO();
		gameAwardUserGroupDTO.setUserId(levelRecycleRequest.getUserId());
		gameAwardUserGroupDTO.setBetType(LevelRecycle.DEFAULT_BETTYPE);
		try {
			gameAwardUserGroupDao.updateGameAwardGruopBetType(gameAwardUserGroupDTO);
			updateRecycleStatus(RecycleIndex.cleanAwardGroup, levelRecycleRequest);
		} catch (Exception e) {
			logger.error("cleanAwardGroup error:"+e);
		}
		logger.info("----- [LevelRecycle] cleanAwardGroup end -----");
	}

	@Override
	public void cleanSafeCenter(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] cleanSafeCenter start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		try {
			userCustomerDao.cleanSafeCenter(userCustomer);
			updateRecycleStatus(RecycleIndex.cleanSafeCenter, levelRecycleRequest);
		} catch (Exception e) {
			logger.error("cleanSafeCenter error:"+e);
		}
		logger.info("----- [LevelRecycle] cleanSafeCenter end -----");
	}

	@Override
	public void cleanPersonalInfo(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] cleanPersonalInfo start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		try {
			userCustomerDao.cleanPersonalInfo(userCustomer);
			updateRecycleStatus(RecycleIndex.cleanPersonalinfo, levelRecycleRequest);
		} catch (Exception e) {
			logger.error("cleanPersonalInfo error:"+e);
		}
		logger.info("----- [LevelRecycle] cleanPersonalInfo end -----");
	}

	@Override
	public void cleanBindCard(LevelRecycleRequest levelRecycleRequest) throws Exception {
		logger.info("----- [LevelRecycle] cleanBindCard start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,userId="+levelRecycleRequest.getUserId());
		try {
			//查询绑定银行
			List<BankCard> bankList = queryBoundBankCardList(levelRecycleRequest.getUserId(), "");
			logger.info("bankList size="+bankList.size());
			if(bankList!=null && bankList.size()>0){
				for (BankCard bankCard : bankList) {
					logger.info("clean bankCard="+bankCard.getBank().getId());
					//添加历史绑卡记录
					this.addCardBindHistoryRecord(2L, levelRecycleRequest.getUserId(), bankCard.getId());
					//删除绑卡
					bankCardDao.removeBankCard(bankCard.getId());	
				}
			}else{
				logger.info("bankList is null!");
			}
			updateRecycleStatus(RecycleIndex.cleanBindcard, levelRecycleRequest);
		} catch (Exception e) {
			logger.error("cleanBindCard error:"+e);
		}
		logger.info("----- [LevelRecycle] cleanBindCard end -----");
	}

	// 添加历史绑卡记录
	private void addCardBindHistoryRecord(long action, long userId, long bindId) {
		UserBank userBank = bankCardDao.getById(bindId);
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
	}

	@Override
	public void cleanOrderHistory(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] cleanOrderHistory start -----");
		logger.info("URL="+ptURL+ptrecycleUser);
		logger.info("recycleId="+levelRecycleRequest.getId()+",userId="+levelRecycleRequest.getUserId());
		
		Response<UserRecycleResult> response = new Response<UserRecycleResult>();
		UserRecycleRequest userRecycleRequest = new UserRecycleRequest();
		userRecycleRequest.setFfId(levelRecycleRequest.getUserId());
		
		try {
			response = httpPtClient.invokeHttp(
					ptURL + ptrecycleUser, 
					userRecycleRequest,
					new TypeReference<Response<UserRecycleResult>>(){});
			if(null!=response&&null!=response.getBody().getResult()){
				logger.info("ptrecycleUser Result.status="+response.getBody().getResult().getStatus());
				if(UserRecycleResult.STATUS_FAIL != response.getBody().getResult().getStatus()){
					updateRecycleStatus(RecycleIndex.cleanOrderHistory, levelRecycleRequest);				
				}else{
					logger.error("cleanPtOrderHistory error："+response.getBody().getResult().getMessage());
					throw new Exception("Reset PT Game Order Fail!");
				}			
			}else{
				throw new Exception("Reset PT game order response null!");
			}
		} catch (Exception e) {
			logger.error("cleanOrderHistory error:"+e);
			throw e;
		}
		logger.info("----- [LevelRecycle] cleanOrderHistory end -----");
	}

	@Override
	public void resetPtPassword(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] resetPtPassword start -----");
		logger.info("URL="+ptURL+ptrecycleUser);
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,account="+levelRecycleRequest.getAccount());
		String ptAccount = "";
		UserQueryUserListRequest userQueryUserListRequest = new UserQueryUserListRequest();
		userQueryUserListRequest.setAccountType(1L);
		userQueryUserListRequest.setPtAccount(levelRecycleRequest.getAccount());
		try {
			UserCustomerVo userCustomerVo = this.getPtUserInfo(levelRecycleRequest);
			if(userCustomerVo!=null){
				ptAccount = userCustomerVo.getPtAccount();
				Response<UserResetPwdResult> response = new Response<UserResetPwdResult>();
				UserResetPwdRequest resetPwdRequest = new UserResetPwdRequest();
				resetPwdRequest.setPtAccount(ptAccount);			
				resetPwdRequest.setUserIp(0L);				
				response = httpPtClient.invokeHttp(
						ptURL + ptresetPwd, 
						resetPwdRequest,
						new TypeReference<Response<UserResetPwdResult>>(){});
				if(null != response.getBody().getResult() 
						&& response.getBody().getResult().getStatus() != LevelRecycle.PT_CHANGPWD_FAIL){
					logger.info("ResetPwd Result.status="+response.getBody().getResult().getStatus());
					updateRecycleStatus(RecycleIndex.resetPtPassword, levelRecycleRequest);
				}else{
					throw new Exception("PT resetPwd Fail!");
				}
			}else{
				logger.info("No Pt Account");
				updateRecycleStatus(RecycleIndex.resetPtPassword, levelRecycleRequest);				
			}
		} catch (Exception e) {
			logger.error("resetPtPassword error:"+e);
			throw new Exception(e);
		}
		logger.info("----- [LevelRecycle] resetPtPassword end -----");
	}

	@Override
	public void resetLoginPassword(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] resetLoginPassword start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,account="+levelRecycleRequest.getAccount());
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setAccount(levelRecycleRequest.getAccount());
		userCustomer.setPasswd(DigestUtils.md5Hex(DigestUtils
				.md5Hex(DigestUtils.md5Hex(LevelRecycle.DEFAULT_PASSWD))));
		try {
			userCustomerDao.cleanSafeCenter(userCustomer);
			updateRecycleStatus(RecycleIndex.resetLoginPassword, levelRecycleRequest);
		} catch (Exception e) {
			logger.error("resetLoginPassword error:"+e);
		}
		logger.info("----- [LevelRecycle] resetLoginPassword end -----");
	}

	@Override
	public List<BankCard> queryBoundBankCardList(long userId, String cardNumber)
			throws Exception {
		return bankCardDao.getBoundBankCard(userId, cardNumber);
	}

	@Override
	public void cleanUserMessage(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		logger.info("----- [LevelRecycle] cleanUserMessage start -----");
		logger.info("recycleId="+levelRecycleRequest.getId()+" ,receiver/sender="+levelRecycleRequest.getUserId());
		Long userId = levelRecycleRequest.getUserId();
		ImGroupUser request = new ImGroupUser();
		Date now = new Date();
		request.setHistoryStartTime(now);
		request.setUnreadCount(0L);
		request.setLastUpdateDate(now);
		request.setTargetUserId(userId);
		imGroupUserDao.updateImGroupUserByUserId(request);
		logger.info("----- [LevelRecycle] cleanUserMessage end -----");
	}

	@Override
	public boolean isLevelRecycleFirstLogin(QueryLevelRecycleHistoryRequest queryLRHistoryRequest)
			throws Exception {
		logger.debug("----- [LevelRecycle] isLevelRecycleFirstLogin start -----");
		boolean flag = false;
		try {
			String account = queryLRHistoryRequest.getAccount();
			LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
			levelRecycleDTO.setAccount(account);
			levelRecycleDTO.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());
			PageRequest<LevelRecycleDTO> pageReqeust = new PageRequest<LevelRecycleDTO>();
			pageReqeust.setSearchDo(levelRecycleDTO);
			Page<LevelRecycle> levelRecycleResult = levelRecycleDao.queryLevelRecycleHistory(pageReqeust);
			List<LevelRecycle> levelRecycleList = levelRecycleResult.getResult();
			if (levelRecycleList != null && levelRecycleList.size() > 0) {
				// 检查密码
				UserCustomer userCustomer = new UserCustomer();
				userCustomer.setAccount(account);
				String maskPasswd = DigestUtils.md5Hex(DigestUtils
						.md5Hex(DigestUtils.md5Hex(LevelRecycle.DEFAULT_PASSWD)));
				userCustomer.setPasswd(maskPasswd);
				Long result = userCustomerDao.getCountByPage(userCustomer);
				if (result != null && result > 0 && !levelRecycleList.get(0).getChangePwd()) {
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("account="+queryLRHistoryRequest.getAccount()+",isLevelRecycleFirstLogin error:"+e);
		}
		logger.trace("isLevelRecycleFirstLogin="+flag);
		logger.trace("----- [LevelRecycle] isLevelRecycleFirstLogin end -----");
		return flag;
	}

	@Override
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(Long userId) {
		logger.info("----- [LevelRecycle] queryRecycleLastHistory start -----");
		logger.info("userId="+userId);
		Response<List<QueryLevelRecycleHistoryResponse>> response = 
				new Response<List<QueryLevelRecycleHistoryResponse>>();
		List<QueryLevelRecycleHistoryResponse> list = null;
		QueryLevelRecycleHistoryResponse result = new QueryLevelRecycleHistoryResponse();
		try {
			LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
			levelRecycleDTO.setUserId(userId);
			levelRecycleDTO.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());			
			PageRequest<LevelRecycleDTO> pageReqeust = new PageRequest<LevelRecycleDTO>();
			pageReqeust.setSearchDo(levelRecycleDTO);
			response = this.queryLevelRecycleHistory(pageReqeust);
			list = response.getBody().getResult();
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		} catch (Exception e) {
			logger.error("queryLevelRecycleHistory error:", e);
		}
		logger.info("----- [LevelRecycle] queryRecycleLastHistory end -----");
		return result;
	}
	
	@Override
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(String account) {
		logger.info("----- [LevelRecycle] queryRecycleLastHistory start -----");
		logger.info("account="+account);
		Response<List<QueryLevelRecycleHistoryResponse>> response = 
				new Response<List<QueryLevelRecycleHistoryResponse>>();
		List<QueryLevelRecycleHistoryResponse> list = null;
		QueryLevelRecycleHistoryResponse result = new QueryLevelRecycleHistoryResponse();
		try {
			LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
			levelRecycleDTO.setAccount(account);
			levelRecycleDTO.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());			
			PageRequest<LevelRecycleDTO> pageReqeust = new PageRequest<LevelRecycleDTO>();
			pageReqeust.setSearchDo(levelRecycleDTO);			
			response = this.queryLevelRecycleHistory(pageReqeust);
			list = response.getBody().getResult();
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		} catch (Exception e) {
			logger.error("queryLevelRecycleHistory", e);
		}
		logger.info("----- [LevelRecycle] queryRecycleLastHistory end -----");
		return result;
	}
	
	private void updateRecycleStatus(RecycleIndex index, LevelRecycleRequest levelRecycleRequest) throws Exception{
		logger.info("----- [LevelRecycle] updateRecycleStatus start -----");
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		
		Long userId = levelRecycleRequest.getUserId();
		String recycleStatus = levelRecycleRequest.getRecycleStatus();
		
		char[] statusArray = recycleStatus.toCharArray();
		statusArray[index.getIntegerValue()-1] = LevelRecycle.RECYCLE_UNIT_SUCCESS;
		logger.info("id="+levelRecycleRequest.getId()+" ,userId="+userId+" ,recycleStatus="+new String(statusArray));
		levelRecycleDTO.setId(levelRecycleRequest.getId());
		levelRecycleDTO.setUserId(userId);
		levelRecycleDTO.setRecycleStatus(new String(statusArray));
		levelRecycleDTO.setUpdateDate(new Date());
		levelRecycleDao.updateRecycleStatus(levelRecycleDTO);
		logger.info("----- [LevelRecycle] updateRecycleStatus end -----");
	}
	
	private UserCustomerVo getPtUserInfo(LevelRecycleRequest levelRecycleRequest){
		logger.info("----- [LevelRecycle] getPtUserInfo start -----");
		logger.info("URL:"+ ptURL + ptqueryUserList);
		Response<UserListResult> userListResponse = null;
		UserCustomerVo userCustomerVo = null;
		UserQueryUserListRequest userQueryUserListRequest = new UserQueryUserListRequest();
		userQueryUserListRequest.setAccountType(1L);
		userQueryUserListRequest.setPtAccount(levelRecycleRequest.getAccount());
		try {
			userListResponse = httpPtClient.invokeHttp(
					ptURL + ptqueryUserList,
					userQueryUserListRequest,
					new TypeReference<Response<UserListResult>>() {
					});
			if(null != userListResponse && userListResponse.getBody().getResult()
					.getUserCustomerList().size()>0){
				userCustomerVo = userListResponse.getBody().getResult().getUserCustomerList().get(0);
			}else{
				logger.info("PtUser not found !");
			}
		} catch (Exception e) {
			logger.error("error getPtUserInfo:"+e);
		}
		logger.info("----- [LevelRecycle] getPtUserInfo end -----");
		return userCustomerVo;		
	}
	
	private UserBalanceResult getPtBalance(String ptAccount){
		logger.info("----- [LevelRecycle] getPtBalance start -----");
		logger.info("URL:"+ ptURL + ptbalance);
		Response<UserBalanceResult> userBalanceResponse = null;
		UserBalanceResult userBalanceResult = null;
		UserCustomerRequest userCustomerRequest = new UserCustomerRequest();
		userCustomerRequest.setPtAccount(ptAccount);
		try {
			userBalanceResponse = httpPtClient.invokeHttp(
					ptURL + ptbalance,
					userCustomerRequest,
					new TypeReference<Response<UserBalanceResult>>() {
					});
			if(null != userBalanceResponse && null != userBalanceResponse.getBody().getResult()){
				userBalanceResult = userBalanceResponse.getBody().getResult();
			}else{
				logger.info("No PtBalance !");
			}
		} catch (Exception e) {
			logger.error("error getPtBalance:"+e);
		}
		logger.info("----- [LevelRecycle] getPtBalance end -----");
		return userBalanceResult;		
	}

	@Override
	public void updateUserRecyclePwdFlag(LevelRecycleRequest levelRecycleRequest)
			throws Exception {
		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		PageRequest<LevelRecycleDTO> pageReqeust = new PageRequest<LevelRecycleDTO>();
		levelRecycleDTO.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());
		levelRecycleDTO.setUserId(levelRecycleRequest.getUserId());
		pageReqeust.setSearchDo(levelRecycleDTO);
		pageReqeust.getSearchDo().setSortColums("CREATE_DATE DESC");
		Page<LevelRecycle> levelRecycleResult = levelRecycleDao.queryLevelRecycleHistory(pageReqeust);
		if(levelRecycleResult!=null &&levelRecycleResult.getResult().size()>0){
			LevelRecycle levelHist = levelRecycleResult.getResult().get(0);
			logger.info("----- id:"+levelHist.getId()+", changePwd:"+levelRecycleRequest.getChangePwd());
			levelHist.setChangePwd(levelRecycleRequest.getChangePwd());
			LevelRecycleDTO updateDto = new LevelRecycleDTO();
			updateDto.setId(levelHist.getId());
			updateDto.setUpdateDate(new Date());
			updateDto.setChangePwd(levelRecycleRequest.getChangePwd());
			levelRecycleDao.updateRecycleStatus(updateDto);
		}
	}
	
}
