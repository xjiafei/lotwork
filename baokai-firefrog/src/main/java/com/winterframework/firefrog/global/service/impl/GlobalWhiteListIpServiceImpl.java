package com.winterframework.firefrog.global.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao;
import com.winterframework.firefrog.global.dao.GlobalWhiteListLogDao;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListIpVO;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListLogVO;
import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.service.GlobalWhiteListIpService;
import com.winterframework.firefrog.global.web.controller.GlobalWhiteListController;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListLogStruc;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("globalWhiteListIpServiceImpl")
public class GlobalWhiteListIpServiceImpl implements GlobalWhiteListIpService {

	private static final Logger logger = LoggerFactory.getLogger(GlobalWhiteListController.class);
	
	@Resource(name = "globalWhiteListIpDaoImpl")
	private GlobalWhiteListIpDao globalWhiteListIpDao;
	
	@Resource(name = "globalWhiteListLogDaoImpl")
	private GlobalWhiteListLogDao globalWhiteListLogDao;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	

	
	private static IPSeeker ipseek = IPSeeker.getInstance();
	private static final String USER_TOKEN_REDIS_KEY ="USER_TOKEN_";

	/*
	 * IP 存檔
	 * 紀錄log
	 * */
	@Override
	public Map<String, String> saveAndUpdateGlobalWhiteListIp(GlobalWhiteListIpRequest globalWhiteListIpRequest)
			throws Exception {
		logger.debug("SaveAndUpdateGlobalWhiteListIp : Start");
		Map<String, String> result = new HashMap<String, String>();
		String operator = globalWhiteListIpRequest.getOperator();
		Date operationTime = globalWhiteListIpRequest.getOperationTime();
		//update 使用者session
		List<GlobalWhiteListIpRequest> sessionUpdateRequest = new ArrayList<GlobalWhiteListIpRequest>();
		if(operator == null || operationTime == null){
			logger.error("SaveAndUpdateGlobalWhiteListIp : 資料不足 operator=" + operator + "  operationTime=" + operationTime);
			throw new Exception("SaveAndUpdateGlobalWhiteListIpException"); 
		}
		String[] accuntArr = globalWhiteListIpRequest.getUserAcunt().split(";");
		for(String accunt :accuntArr){
			User user = this.checkAccuntExist(accunt);
			if (user == null){
				result.put("result", "0");
				result.put("msg", "无此账号 : " + accunt);
				return result;
			}
			GlobalWhiteListIpRequest sessreq = new GlobalWhiteListIpRequest();
			sessreq.setIds(user.getId().toString());
			sessreq.setUserAcunt(user.getAccount());
			sessionUpdateRequest.add(sessreq);
		}
		
		String status = "新增";
		int mode = globalWhiteListIpRequest.getMode();
		globalWhiteListIpRequest.setStatus(0L);
		boolean count = this.checkUserIpExist(globalWhiteListIpRequest);
		if(mode == 0 && count){//表示有以前刪除過的資料
			mode = 1;
		}
		GlobalWhiteListIpVO globalWhiteListIpVO = new GlobalWhiteListIpVO();
		globalWhiteListIpVO.setIpAddr(globalWhiteListIpRequest.getIpAddr());		
		globalWhiteListIpVO.setIpAddr_bk(globalWhiteListIpRequest.getIpAddr_bk());
		globalWhiteListIpVO.setUserAccunt(globalWhiteListIpRequest.getUserAcunt());				
		globalWhiteListIpVO.setStatus(1L);
		globalWhiteListIpVO.setUpdateTime(operationTime);
		globalWhiteListIpVO.setUpdateUser(operator);
		globalWhiteListIpVO.setRemark(globalWhiteListIpRequest.getRemark());
		switch (mode) {
		case 0://ADD
			status = "新增";
			logger.info("SaveAndUpdateGlobalWhiteListIp : 新增IP白名單 USER_ACCUNT=" 
			+ globalWhiteListIpVO.getUserAccunt() + " STATUS=" + globalWhiteListIpVO.getStatus()
			+ " REMARK=" + globalWhiteListIpVO.getRemark() + " UPDATE_USER=" + globalWhiteListIpVO.getUpdateUser()
			+ " UPDATE_TIME=" + globalWhiteListIpVO.getUpdateTime() + " IP_ADDR=" + globalWhiteListIpVO.getIpAddr()
			+ " IP_ADDR_BK=" + globalWhiteListIpVO.getIpAddr_bk()
					);
			globalWhiteListIpVO.setCerateTime(operationTime);	
			globalWhiteListIpVO.setCreateUser(operator);
			globalWhiteListIpDao.saveGlobalWhiteListIp(globalWhiteListIpVO);
			break;
		case 1://edit
			status = "修改";
			logger.info("SaveAndUpdateGlobalWhiteListIp : 更新IP白名單 USER_ACCUNT=" 
			+ globalWhiteListIpVO.getUserAccunt() + " STATUS=" + globalWhiteListIpVO.getStatus()
			+ " REMARK=" + globalWhiteListIpVO.getRemark() + " UPDATE_USER=" + globalWhiteListIpVO.getUpdateUser()
			+ " UPDATE_TIME=" + globalWhiteListIpVO.getUpdateTime() + " IP_ADDR=" + globalWhiteListIpVO.getIpAddr()				
			+ " IP_ADDR_BK=" + globalWhiteListIpVO.getIpAddr_bk()
					);
			globalWhiteListIpDao.updateGlobalWhiteListIp(globalWhiteListIpVO);
			break;
		default:
			logger.error("SaveAndUpdateGlobalWhiteListIp : 指定IP白名單異常操作");
			throw new Exception("SaveAndUpdateGlobalWhiteListIpException"); 
			//break;
		}
		//紀錄log
		this.LogRecord(globalWhiteListIpVO, status);
		//更新session
		updateUserCurrentIP(sessionUpdateRequest);
		logger.debug("SaveAndUpdateGlobalWhiteListIp : End");
		result.put("result", "1");//成功
		result.put("msg", "");
		return result;
	}

	/*
	 * 更新用戶 session currentIP 資料
	 * 刪除手機端用戶 Token
	 * */
	@Override
	public boolean updateUserCurrentIP(
			List<GlobalWhiteListIpRequest> requestList) {
		logger.debug("updateUserCurrentIP 更新用戶 session 資料 ");
		
		for(GlobalWhiteListIpRequest req : requestList){			
			String key = DigestUtils.md5Hex("ANVO"+req.getIds().toString());
			String ipList = this.getUserIpPrivileges("", req.getUserAcunt());
			Map<String, String> ip = new HashMap<String, String>();
			ip.put("whiteListIP", ipList);
			logger.debug("updateUserCurrentIP key="+key + " User Accunt=" + req.getUserAcunt() + " User IP=" + ipList);
			try{
				
				Set<String> sessionIds= redisClient.smembers(key);
				for(String sessionId : sessionIds){
					if(redisClient.exists(sessionId)){
						String beforeVal = redisClient.get(sessionId);
						logger.info("sessionIdValBefore="+ beforeVal);
						ObjectMapper mapper = new ObjectMapper();
				        JsonNode root = mapper.readTree(beforeVal);
				        ((ObjectNode) root).with("datas").with("info").put("whiteListIP", ipList);
				        String afterVal = root.toString();
						logger.info("sessionIdValAfter="+ afterVal);
						redisClient.set(sessionId, afterVal);
					}				
				}
			}catch(Exception e){
				logger.debug("GetSessionKeySet Error : key=" + key + " UserID= " + req.getIds());
			}
			//刪除手機用戶 TOKEN
			try{
				String userTokenRedKey = USER_TOKEN_REDIS_KEY+ req.getUserAcunt(); 				
				if(redisClient.exists(userTokenRedKey)){
					redisClient.del(userTokenRedKey);
					logger.debug("updateUserCurrentIP User Token Killed : Key="+ userTokenRedKey);
				}else{
					logger.debug("updateUserCurrentIP User Token Empty : Key="+ userTokenRedKey);
				}
			}catch(Exception e){
				logger.error("updateUserCurrentIP User Token Kill Exception : "+ e);
			}			
		}
		return true;
	}

	/*
	 * 指定IP白名單 : 檢查使用者是否為指定IP白名單用戶
	 * */
	@Override
	public boolean checkUserPrivileges(GlobalWhiteListIpRequest globalWhiteListIpRequest) throws Exception {
		if(globalWhiteListIpRequest.getUserAcunt() == null){
			throw new Exception("CheckGlobalWhiteListIpException"); 
		}else{
			GlobalWhiteListIpVO globalWhiteListIpVO =new GlobalWhiteListIpVO();
			globalWhiteListIpVO.setUserAccunt(globalWhiteListIpRequest.getUserAcunt());
			Long count = globalWhiteListIpDao.countWhiteListData(globalWhiteListIpVO);
			if(count >= 1){
				return true;
			}
		}
		return false;
	}

	/*
	 * 紀錄log
	 * */
	private void LogRecord(GlobalWhiteListIpVO globalWhiteListIpVO, String status ){		
		GlobalWhiteListLogVO globalWhiteListLogVO = new GlobalWhiteListLogVO();
		globalWhiteListLogVO.setWhiteListIP(globalWhiteListIpVO.getIpAddr());
		globalWhiteListLogVO.setAccunt(globalWhiteListIpVO.getUserAccunt());
		globalWhiteListLogVO.setCountry(this.queryCountryMapIP(globalWhiteListIpVO.getIpAddr()));
		globalWhiteListLogVO.setOperator(globalWhiteListIpVO.getUpdateUser());
		globalWhiteListLogVO.setOperationTime(globalWhiteListIpVO.getUpdateTime());
		globalWhiteListLogVO.setStatus(status);		
		logger.debug("LogRecord : logOperatorID=" + globalWhiteListIpVO.getUpdateUser() + " status=" + status + " logOperationTime=" + globalWhiteListIpVO.getUpdateTime());
		globalWhiteListLogDao.saveGlobalWhiteListLog(globalWhiteListLogVO);
	}
	
	@Override
	public Page<GlobalWhiteListIp> queryGlobalWhiteListIp(
			PageRequest<GlobalWhiteListIpRequest> pageRequest) throws Exception {
		GlobalWhiteListIpVO globalWhiteListIpVO = new GlobalWhiteListIpVO();
		globalWhiteListIpVO.setStatus(1L);
		Map<String, Object> filters = new HashMap<String, Object>();
		GlobalWhiteListIpRequest dto = pageRequest.getSearchDo();
		String word = dto.getWord();
		if(!(word == null) && !(word =="")){
			switch (dto.getType()) {
			case 0: //搜尋 user accunt
				filters.put("userAccunt", word);
				globalWhiteListIpVO.setUserAccunt(word);
				break;
			case 1: //搜尋 IP
				filters.put("ipAddr", word);
				globalWhiteListIpVO.setIpAddr(word);
				break;

			default://全部搜尋
				break;
			}
		}	
		int totalCount = (int) globalWhiteListIpDao.countWhiteListData(globalWhiteListIpVO).intValue();
		Page<GlobalWhiteListIp> page = new Page<GlobalWhiteListIp>(pageRequest.getPageNo(), pageRequest.getPageSize(), totalCount);
		
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.put("status", 1L);
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		
		
		List<GlobalWhiteListIpVO> list = globalWhiteListIpDao.queryGlobalWhiteListIp(page,filters,rowBounds);
		List<GlobalWhiteListIp> reList = new ArrayList<GlobalWhiteListIp>();
		for(GlobalWhiteListIpVO globalWhiteListIpVORs : list){
			reList.add(VOConverter.transVO2WhiteListIP(globalWhiteListIpVORs));
		}
		page.setResult(reList);
		return page;
	}

	/*
	 * 依據IP ADDR　取得國家對照
	 * */
	@Override
	public String queryCountryMapIP(String ipAddr) {
		return ipseek.getAddress(ipAddr);
	}

	/*
	 * 取得 LOG 紀錄
	 * */
	@Override
	public List<GlobalWhiteListLogVO> queryGlobalWhiteListLog(PageRequest<GlobalWhiteListIpRequest> pageRequest) throws Exception {
		GlobalWhiteListLogVO globalWhiteListLogVO = new GlobalWhiteListLogVO();
		
		GlobalWhiteListIpRequest dto = pageRequest.getSearchDo();
		if(dto != null){
			String word = dto.getWord();
			if(!(word == null) && !(word =="")){
				switch (dto.getType()) {
				case 0: //搜尋 user accunt
					globalWhiteListLogVO.setAccunt(word);
					break;
				case 1: //搜尋 IP
					globalWhiteListLogVO.setWhiteListIP(word);
					break;

				default://全部搜尋
					break;
				}
			}
		}		
		globalWhiteListLogVO.setPageSize(10);
		List<GlobalWhiteListLogVO> prerRsultList = null;
		try{
			prerRsultList = globalWhiteListLogDao.queryGlobalWhiteListLog(globalWhiteListLogVO);
		}catch(Exception e){
			logger.error("Get GlobalWhiteListLog Error");
			return null;
		}		
		return prerRsultList;
	}

	@Override
	public boolean checkUserIpExist(
			GlobalWhiteListIpRequest globalWhiteListIpRequest) throws Exception {
		GlobalWhiteListIpVO globalWhiteListIpVO = new GlobalWhiteListIpVO();
		globalWhiteListIpVO.setIpAddr(globalWhiteListIpRequest.getIpAddr());
		globalWhiteListIpVO.setStatus(globalWhiteListIpRequest.getStatus());
		Long count = globalWhiteListIpDao.countWhiteListData(globalWhiteListIpVO);	
		if(count > 0){
			return true;
		} else {
			return false;
		}		
	}

	/*
	 * 紀錄log
	 * */
	@Override
	public void deleteGlobalWhiteListIp(GlobalWhiteListIpRequest request) throws Exception {
		String[] ids = request.getIds().split(",");
		List<GlobalWhiteListIpRequest> sessionUpdateRequest = new ArrayList<GlobalWhiteListIpRequest>();
		
		for (String id : ids) {
			GlobalWhiteListIpVO globalWhiteListIpVO = new GlobalWhiteListIpVO();
			globalWhiteListIpVO.setId(Long.parseLong(id));
			globalWhiteListIpVO.setUpdateUser(request.getOperator());
			globalWhiteListIpVO.setUpdateTime(request.getOperationTime());
			globalWhiteListIpDao.deleteGlobalWhiteListIp(globalWhiteListIpVO);
			GlobalWhiteListIpVO logSearchWhiteListIpVO = new GlobalWhiteListIpVO();
			logSearchWhiteListIpVO.setId(Long.parseLong(id));
			GlobalWhiteListIpVO log = globalWhiteListIpDao.queryGlobalWhiteListIpById(logSearchWhiteListIpVO);
			//紀錄log
			this.LogRecord(log, "刪除");
			
			String[] accuntArr = log.getUserAccunt().split(";");
			for(String accunt :accuntArr){
				if(null == accunt){
					continue;
				}
				User user = this.checkAccuntExist(accunt);
				GlobalWhiteListIpRequest sessreq = new GlobalWhiteListIpRequest();
				sessreq.setIds(user.getId().toString());
				sessreq.setUserAcunt(user.getAccount());
				sessionUpdateRequest.add(sessreq);
			}
		}		
		//更新session
		updateUserCurrentIP(sessionUpdateRequest);
	}

	@Override
	public GlobalWhiteListIpStruc queryGlobalWhiteListIpById(
			GlobalWhiteListIpRequest request) throws Exception {		
		GlobalWhiteListIpVO logSearchWhiteListIpVO = new GlobalWhiteListIpVO();
		logSearchWhiteListIpVO.setId(Long.parseLong(request.getIds()));
		GlobalWhiteListIpVO globalWhiteListIpVO = globalWhiteListIpDao.queryGlobalWhiteListIpById(logSearchWhiteListIpVO);
		GlobalWhiteListIpStruc result = new GlobalWhiteListIpStruc();
		result.setId(globalWhiteListIpVO.getId());
		result.setCountry(this.queryCountryMapIP(globalWhiteListIpVO.getIpAddr()));
		result.setIpAddr(globalWhiteListIpVO.getIpAddr());
		result.setUserAcunt(globalWhiteListIpVO.getUserAccunt());
		result.setOperator(globalWhiteListIpVO.getUpdateUser());
		result.setOperationTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(globalWhiteListIpVO.getUpdateTime()) );
		result.setRemark(globalWhiteListIpVO.getRemark());
		return result;
	}

	@Override
	public String getUserIpPrivileges(String longToIp, String userName) {
		GlobalWhiteListIpVO logSearchWhiteListIpVO = new GlobalWhiteListIpVO();
		logSearchWhiteListIpVO.setUserAccunt(userName);
		List<GlobalWhiteListIpVO> resultList = globalWhiteListIpDao.getIpListByUserAccunt(userName);
		//額外過濾因sql 用 like查詢  而造成比對錯誤,  白名單的userName需要完全符合才可以
		for(int i = 0 ; i < resultList.size() ; i++){
			GlobalWhiteListIpVO vo = resultList.get(i);
			String accounts = StringUtils.trimToEmpty(vo.getUserAccunt());
			String[] accountArray = accounts.split(";");
			boolean isMatch = Boolean.FALSE;
			for(String account : accountArray){
				if(userName.equalsIgnoreCase(account)){
					isMatch = Boolean.TRUE;
					break;
				}
			}
			if(isMatch){
				//符合代表比對成功
			} else {
				//不符合代表非白名單的人 要將此筆資料排除
				resultList.remove(i);
				i--;
			}
		}
		
		String ipList = "";
		
		if(resultList.size() > 0){//使用者在限制名單內
			for(GlobalWhiteListIpVO vo : resultList){
				String ip = vo.getIpAddr();
				if(ip == null){
					continue;
				}
				if(ipList.isEmpty()){
					ipList = ip;
				}else{
					ipList = ipList + ";" + ip;
				}
			}
		}
		return ipList;
	}

	@Override
	public User checkAccuntExist(String accunt) {
		return userCustomerDao.getUserByAccount(accunt);
	}
}
