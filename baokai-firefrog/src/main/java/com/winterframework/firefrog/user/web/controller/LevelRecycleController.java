package com.winterframework.firefrog.user.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle.RecycleIndex;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle.RecycleStatus;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.ApplyLevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.ApplyLevelRecycleResponse;
import com.winterframework.firefrog.user.web.dto.LevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.LevelRecycleResponse;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListResponse;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
 * 类功能说明:  一代回收管理与回收纪录 
 */
@Controller("levelRecycleController")
@RequestMapping("/user")
@SuppressWarnings("unchecked")
public class LevelRecycleController {

	private static final Logger log = LoggerFactory.getLogger(LevelRecycleController.class);	

	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;

	
	/** 
	* @Title: queryLevelRecycleList 
	* @Description: 一代回收用户查询 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryLevelRecycleList")
	@ResponseBody
	public Object queryLevelRecycleList(@RequestBody Request<QueryLevelRecycleListRequest> request) throws Exception {
		log.trace("----- [LevelRecycle] queryLevelRecycleList start 开始查询一代回收用户 -----");
		Response<QueryLevelRecycleListResponse> response = new Response<QueryLevelRecycleListResponse>();			
		if (null != request.getBody()) {
			QueryLevelRecycleListRequest param = request.getBody().getParam();
			try {				
				QueryLevelRecycleListResponse queryResponse = levelRecycleService.queryLevelRecycleList(param);
				response.setResult(queryResponse);
			} catch (Exception e) {
				log.error("查询一代回收信息错误", e);
				throw e;
			}
		}
		log.trace("----- [LevelRecycle] queryLevelRecycleList end 结束查询一代回收用户 -----");
		return response;
	}

	/** 
	 * @Title: queryLevelRecycleHistory 
	 * @Description: 一代回收纪录 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryLevelRecycleHistory")
	@ResponseBody
	public Object queryLevelRecycleHistory(@RequestBody Request<LevelRecycleDTO> request) throws Exception {
		log.info("----- [LevelRecycle] queryLevelRecycleHistory start 开始查询一代回收纪录 -----");
		Response<List<QueryLevelRecycleHistoryResponse>> response = new Response<List<QueryLevelRecycleHistoryResponse>>();
		LevelRecycleDTO param = request.getBody().getParam();
		log.info("userName="+param.getAccount()+" ,operator="+param.getOperator());
		
		int startNo = request.getBody().getPager().getStartNo();
		int endNo =	request.getBody().getPager().getEndNo();	
		log.info("startNo="+startNo+" ,endNo="+endNo);
		param.setStartNo(startNo);
		param.setEndNo(endNo);
		
		PageRequest<LevelRecycleDTO> pageReqeust = (PageRequest<LevelRecycleDTO>) getPageRequest(request);
		pageReqeust.setSearchDo(param);
		response = levelRecycleService.queryLevelRecycleHistory(pageReqeust);		
		response.getBody().getPager().setStartNo(startNo);
		response.getBody().getPager().setEndNo(endNo);
		
		log.info("----- [LevelRecycle] queryLevelRecycleHistory end 结束查询一代回收纪录 -----");		
		return response;
	}
	
	/** 
	 * @Title: checkLevelRecycleHistory 
	 * @Description: 检查一代回收纪录密码修改 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isLevelRecycleFirstLogin")
	@ResponseBody
	public Object isLevelRecycleFirstLogin(@RequestBody Request<QueryLevelRecycleHistoryRequest> request) throws Exception {
		log.debug("----- [LevelRecycle] isLevelRecycleFirstLogin start 开始检查一代回收纪录密码修改 -----");
		String status = "";
		String message = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		QueryLevelRecycleHistoryRequest param = request.getBody().getParam();
		try {
			if(levelRecycleService.isLevelRecycleFirstLogin(param)){
				message = "isLevelRecycleFirstLogin";
			}else{
				message = "notLevelRecycleFirstLogin";
			}
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error isLevelRecycleFirstLogin 检查一代回收纪录密码错误 ..."+e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);		
		log.debug("----- [LevelRecycle] isLevelRecycleFirstLogin end 结束检查一代回收纪录密码修改 -----");		
		return response;
	}
	
	/** 
	 * @Title: applyLevelRecycle 
	 * @Description: 一代回收申请 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyLevelRecycle")
	@ResponseBody
	public Object applyLevelRecycle(@RequestBody Request<ApplyLevelRecycleRequest> request) throws Exception {
		log.info("----- [LevelRecycle] applyLevelRecycle start 开始一代回收用户申请 -----");
		String status = "";
		String message = "";
		Response<ApplyLevelRecycleResponse> response = new Response<ApplyLevelRecycleResponse>();		
		ApplyLevelRecycleResponse applyResponse = new ApplyLevelRecycleResponse();
		ApplyLevelRecycleRequest param = request.getBody().getParam();
		param.setCreateDate(new Date());
		param.setTaskStatus(RecycleStatus.INIT.getIntegerValue());		
		try {
			//检查是否回收执行中
			PageRequest<LevelRecycleDTO> pageReqeust = new PageRequest<LevelRecycleDTO>();
			LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
			levelRecycleDTO.setUserId(param.getUserId());
			levelRecycleDTO.setTaskStatus(RecycleStatus.INIT.getIntegerValue());
			levelRecycleDTO.setTaskStatus2(RecycleStatus.RUNING.getIntegerValue());
			pageReqeust.setSearchDo(levelRecycleDTO);
			Response<List<QueryLevelRecycleHistoryResponse>> resp = levelRecycleService.queryLevelRecycleHistory(pageReqeust);
			List<QueryLevelRecycleHistoryResponse> resultList = resp.getBody().getResult();
			if(resultList!=null&&resultList.size()>0){
				message = "重复申请";
				log.info("一代回收重复申请...");
			}else{
				levelRecycleService.applyLevelRecycle(param);
				log.info("一代回收申请成功...");				
			}
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = "申请错误";
			log.error("一代回收申请错误... Exception:" + e);
			throw e;
		}		
		applyResponse.setStatus(status);
		applyResponse.setMessage(message);
		response.setResult(applyResponse);		
		log.info("----- [LevelRecycle] applyLevelRecycle end  结束处理一代回收申请请求 -----");		
		return response;
	}
	
	/** 
	 * @Title: cleanAwardGroup 
	 * @Description: 清除奖金组
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanAwardGroup")
	@ResponseBody
	public Object cleanAwardGroup(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanAwardGroup start  开始清除奖金组 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanAwardGroup(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanAwardGroup, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanAwardGroup 清除奖金组错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] cleanAwardGroup end  结束清除奖金组 -----");
		return response;		
	}
	
	/** 
	 * @Title: cleanSafeCenter 
	 * @Description: 清理安全中心
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanSafeCenter")
	@ResponseBody
	public Object cleanSafeCenter(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanPersonalinfo start 开始清理安全中心 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanSafeCenter(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanSafeCenter, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanPersonalinfo 清理安全中心错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("end cleanPersonalinfo 结束清理安全中心...");
		return response;		
	}
	
	/** 
	 * @Title: cleanPersonalinfo 
	 * @Description: 清理个人资料
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanPersonalinfo")
	@ResponseBody
	public Object cleanPersonalInfo(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanPersonalinfo start 开始清理个人资料 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanPersonalInfo(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanPersonalinfo, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanPersonalinfo 清理个人资料错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] cleanPersonalinfo end 结束清理个人资料 -----");
		return response;		
	}
	
	/** 
	 * @Title: cleanBindcard 
	 * @Description: 清理银行卡信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanBindCard")
	@ResponseBody
	public Object cleanBindCard(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanBindcard start 开始清理银行卡信息 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanBindCard(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanBindcard, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanBindcard 清理银行卡信息错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] cleanBindcard end 结束清理银行卡信息 -----");
		return response;		
	}
	
	/** 
	 * @Title: cleanOrderHistory 
	 * @Description: 清理投注记录
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanOrderHistory")
	@ResponseBody
	public Object cleanOrderHistory(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanOrderHistory start 开始清理投注记录 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanOrderHistory(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanOrderHistory, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanOrderHistory 清理投注记录错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] cleanOrderHistory end 结束清理投注记录 -----");
		return response;
	}
			
	/** 
	 * @Title: cleanUserMessage 
	 * @Description: 清理站內信
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanUserMessage")
	@ResponseBody
	public Object cleanUserMessage(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] cleanUserMessage start 开始清理站內信 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.cleanUserMessage(param);
			recycleStatus = getRecycleStatus(RecycleIndex.cleanUserMessage, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error cleanUserMessage 清理站內信错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] cleanUserMessage end 结束清理站內信 -----");
		return response;
	}
	
	/** 
	 * @Title: resetPtPassword 
	 * @Description: 重置PT密码
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPtPassword")
	@ResponseBody
	public Object resetPtPassword(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] resetPtPassword start 开始重置PT密码 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.resetPtPassword(param);
			recycleStatus = getRecycleStatus(RecycleIndex.resetPtPassword, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error resetPtPassword 重置PT密码错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] resetPtPassword end 结束重置PT密码 -----");
		return response;
	}
	
	/** 
	 * @Title: resetLoginPassword 
	 * @Description: 重置平台登录密码
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetLoginPassword")
	@ResponseBody
	public Object resetLoginPassword(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] resetLoginPassword start 开始重置平台登录密码 -----");
		String status = "";
		String message = "";
		String recycleStatus = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.resetLoginPassword(param);
			clearUserRedisSession(param.getUserId());
			recycleStatus = getRecycleStatus(RecycleIndex.resetLoginPassword, param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error resetLoginPassword 重置平台登录密码错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setRecycleStatus(recycleStatus);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] resetLoginPassword end 结束重置平台登录密码 -----");
		return response;				
	}
	
	/** 
	 * @Title: resetLoginPassword 
	 * @Description: 重置平台登录密码
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUserRecyclePwdFlag")
	@ResponseBody
	public Object updateUserRecyclePwdFlag(@RequestBody Request<LevelRecycleRequest> request){
		log.info("----- [LevelRecycle] updateUserRecyclePwdFlag start 更新登录密码记录 -----");
		String status = "";
		String message = "";
		Response<LevelRecycleResponse> response = new Response<LevelRecycleResponse>();
		LevelRecycleResponse levelRecycleResponse = new LevelRecycleResponse();
		LevelRecycleRequest param = request.getBody().getParam();
		try {
			levelRecycleService.updateUserRecyclePwdFlag(param);
			status = LevelRecycle.SUCCESS;
		} catch (Exception e) {
			status = LevelRecycle.ERROR;
			message = e.getMessage();
			log.error("error updateUserRecyclePwdFlag 更新登录密码记录 错误..." + e);
		}
		levelRecycleResponse.setStatus(status);
		levelRecycleResponse.setMessage(message);
		response.setResult(levelRecycleResponse);
		log.info("----- [LevelRecycle] updateUserRecyclePwdFlag end 更新登录密码记录 -----");
		return response;				
	}
	
	private PageRequest<?> getPageRequest(Request<?> request) {
		PageRequest<?> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody().getPager().getStartNo(),
				request.getBody().getPager().getEndNo());
		return pageReqeust;
	}
	
	private String getRecycleStatus(RecycleIndex index, LevelRecycleRequest levelRecycleRequest){		
		String recycleStatus = levelRecycleRequest.getRecycleStatus();		
		char[] statusArray = recycleStatus.toCharArray();
		statusArray[index.getIntegerValue()-1] = LevelRecycle.RECYCLE_UNIT_SUCCESS;
		return new String(statusArray);
	}
	
	/**
	 * 回收用户登出
	 * @param userId
	 */
	private void clearUserRedisSession(Long userId) {		
		log.info("----- [LevelRecycle] clearUserRedisSession start 登出回收用户 -----");
		try {
			redisClient.initialPool();
			String key = DigestUtils.md5Hex("ANVO"+userId);
			log.info("key="+key);
			Set<String> sessionIds= redisClient.smembers(key);
			for(String sessionId:sessionIds){
				log.info("sessionId="+sessionId);
				redisClient.del(sessionId);
			}
			redisClient.del(key);
		} catch (Exception e) {
			log.error("clearUserRedisSession error..."+e);
		}
		log.info("----- [LevelRecycle] clearUserRedisSession end 回收用户登出成功 -----");
	}
	
}
