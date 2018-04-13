/**   
* @Title: BankCardController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: 银行卡管理Controller 
* @author Denny  
* @date 2013-7-1 下午4:44:27 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.dao.vo.UserBankLocked;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.exception.FundBankcardBindLimitException;
import com.winterframework.firefrog.fund.exception.FundBankcardLockedException;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IUserBankLockedService;
import com.winterframework.firefrog.fund.web.dto.BankCardApplyBindRequest;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordRequest;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordResponse;
import com.winterframework.firefrog.fund.web.dto.BankCardBindRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardHistoryStruc;
import com.winterframework.firefrog.fund.web.dto.BankCardLockingRequest;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryBindRecordRequest;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryBindRecordResponse;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryResponse;
import com.winterframework.firefrog.fund.web.dto.BankCardUnbindRequest;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: BankCardController 
* @Description: 银行卡管理Controller
* @author Denny 
* @date 2013-7-1 下午4:44:27 
*  
*/
@Controller("bankCardController")
@RequestMapping("/fund")
public class BankCardController {

	private static final Logger log = LoggerFactory.getLogger(BankCardController.class);

	private final static Long TWO_HOUR = 2 * 60 * 60 * 1000L;

	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;
	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userApprel;

	@Resource(name = "userBankLockedServiceImpl")
	private IUserBankLockedService userBankLockedService;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;
	
	@Resource(name = "globalGrayListServiceImpl")
	private IGlobalGrayListService globalGrayListService;
	
	@Resource(name = "RedisClient")
	private RedisClient rc;

	private String platformName = "firefrog";
	
	private final Long ALIPAY_CARD = 1L;

	/** 
	* @Title: applyBankCardBind 
	* @Description: 申请绑卡
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/applyBankCardBind")
	@ResponseBody
	public Object applyBankCardBind(@RequestBody @ValidRequestBody Request<BankCardApplyBindRequest> request)
			throws Exception {
		String key = "/fund-applyBankCardBind"+request.getHead().getUserId();
		
		log.debug("开始绑卡......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			//鎖定只能一名使用者寫入派獎帳變資料
			if (!rc.acquireLock(key,50000)) {
				log.error("user submit in same time:"+new Date());
				return response;
			} else {
				log.info("start addBindcard");
				BankCardApplyBindRequest req = request.getBody().getParam();
				BankCard bc = DTOConverter.bankCardApplyBindRequest2BankCard(req);
				
				if(bc.getBindcardType()==ALIPAY_CARD){ //是支付寶
					if(!bankCardService.checkAlipayStatus(bc)){ //不可綁卡
						log.error("illegal alipay bindCard");
						throw new Exception();
					}
				}
				
				if(req.getOpeType()!=null && req.getOpeType().equals("update")){
					bankCardService.updateBind(bc);
				}else{
					bankCardService.applyBind(bc);
				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("platform", platformName);
				msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CardBind, map);
				
				//查詢是否為灰名單
				GlobalGrayListVO grayUser = globalGrayListService.queryGlobalGrayListByUserId(req.getUserId());
				if(grayUser != null){
					if(grayUser.getRiskType() == 1){
						grayUser.setRiskType(6l);
						globalGrayListService.updateGlobalGrayList(grayUser);
					}
				}
			}	
			
		} catch (FundBankcardLockedException e) {
			log.error(FundBankcardLockedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundBankcardBindLimitException e) {
			log.error(FundBankcardBindLimitException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("绑卡出现异常：", e);
			throw e;
		}
		log.debug("成功绑卡。");
		return response;

	}

	/** 
	* @Title: applyBankCardBind 
	* @Description: 申请绑卡
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/checkBankCardBind")
	@ResponseBody
	public Object checkBankCardBind(@RequestBody @ValidRequestBody Request<BankCardApplyBindRequest> request)
			throws Exception {

		log.debug("检查绑卡......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			String cardNumer = request.getBody().getParam().getBankNumber();
			String cardAccount = request.getBody().getParam().getBankAccount();
			if (bankCardService.checkBind(cardNumer,request.getBody().getParam().getUserId(),cardAccount)) {
				//如果已绑定
				response.getHead().setStatus(2010);
			}

		} catch (FundBankcardLockedException e) {
			log.error(FundBankcardLockedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundBankcardBindLimitException e) {
			log.error(FundBankcardBindLimitException.MSG, e);
			response.getHead().setStatus(e.getStatus());

		} catch (Exception e) {
			log.error("绑卡出现异常：", e);
			throw e;
		}

		log.debug("成功绑卡。");
		return response;

	}

	/** 
	* @Title: queryBoundbankCard 
	* @Description: 查询绑卡
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryBoundbankCard")
	@ResponseBody
	public Response<BankCardQueryResponse> queryBoundbankCard(
			@RequestBody @ValidRequestBody Request<BankCardQueryRequest> request) throws Exception {

		log.debug("开始查询绑卡......");
		Response<BankCardQueryResponse> response = new Response<BankCardQueryResponse>(request);
		BankCardQueryResponse bcqRespnse = new BankCardQueryResponse();
		try {
			long userId = request.getBody().getParam().getUserId();
			//List<BankCard> result = bankCardService.queryBoundBankCard(userId, request.getBody().getParam()
					//.getBankCard());
			BankCardQueryRequest br = new BankCardQueryRequest();
			br.setUserId(userId);
			br.setBindCardType( request.getBody().getParam().getBindCardType());
			br.setBankAccount(request.getBody().getParam().getBankAccount());
			br.setBankNumber(request.getBody().getParam().getBankNumber());
			br.setNickName(request.getBody().getParam().getNickName());
			br.setNickNameMust(request.getBody().getParam().getNickNameMust());
			List<BankCard> result = bankCardService.queryBoundBankCardByRequest(br);
			long dbNowTime = userBankLockedService.queryDBNowTime();
			log.info(" dbNowTime : " + dbNowTime);
			long overTime = userBankLockedService.queryOverTime(userId,request.getBody().getParam().getBindCardType());
			log.info("overTime : " + overTime);
			List<UserBankStruc> ubsListResult = new ArrayList<UserBankStruc>();
			UserBankStruc ubs = null;
			for (BankCard bc : result) {
				ubs = DTOConverter.bankCard2UserBankStruc(bc);
				ubsListResult.add(ubs);
			}
			bcqRespnse.setUserBankStruc(ubsListResult);
			bcqRespnse.setOverTime(overTime);
			bcqRespnse.setDbNowTime(dbNowTime);
			log.info("bcqRespnse.getDbNowTime() : " + bcqRespnse.getDbNowTime());
			
			response.setResult(bcqRespnse);
		} catch (Exception e) {
			log.error("查询绑卡出现异常：", e);
			throw e;
		}
		
		log.debug("查询绑卡完成。");
		return response;
 
	}

	/** 
	* @Title: unbindBankCard 
	* @Description: 删除绑卡
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/unbindBankCard")
	@ResponseBody
	public Object unbindBankCard(@RequestBody @ValidRequestBody Request<BankCardUnbindRequest> request)
			throws Exception {

		log.info("开始删除绑卡......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			long userId = request.getBody().getParam().getUserId();
			long mcBankId = request.getBody().getParam().getMcBankId();
			long bankId = request.getBody().getParam().getBankId();
			long bindId = request.getBody().getParam().getBindId();
			long bindcardType = request.getBody().getParam().getBindcardType();
			String nickName = request.getBody().getParam().getNickName();
			bankCardService.unbind(bindId, userId, bankId, mcBankId,bindcardType,nickName);
			Map<String, String> map = new HashMap<String, String>();
			map.put("platform", platformName);
			msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CardUnLock, map);
			//查詢是否為灰名單
			GlobalGrayListVO grayUser = globalGrayListService.queryGlobalGrayListByUserId(userId);
			if(grayUser != null){
				if(grayUser.getRiskType() == 1){
					grayUser.setRiskType(6l);
					globalGrayListService.updateGlobalGrayList(grayUser);
				}
			}
			
		} catch (FundBankcardLockedException e) {
			log.error(FundBankcardLockedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("删除绑卡出现异常：", e);
			throw e;
		}

		log.info("成功删除绑卡。");
		return response;

	}

	/**
	 * 
	* @Title: queryBankCardRecords 
	* @Description: 用户绑卡记录
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryBankCardRecords")
	@ResponseBody
	public Response<List<BankCardQueryBindRecordResponse>> queryBankCardRecords(
			@RequestBody @ValidRequestBody Request<BankCardQueryBindRecordRequest> request) throws Exception {
		log.info("用户绑卡记录分页列表查询 start....");

		Response<List<BankCardQueryBindRecordResponse>> response = new Response<List<BankCardQueryBindRecordResponse>>(request);

		if (null != request.getBody()) {

			try {
				BankCardBindRecordQueryDTO bankCardBindRecordQueryDTO = DTOConverter
						.convertBankCardBindRecordQueryDTO(request);
				boolean isList=StringUtils.isNotBlank(request.getBody().getParam().getBankCard());
				response = searchUserCardBind(response, request, bankCardBindRecordQueryDTO,isList);
			} catch (Exception e) {
				log.error("用户绑卡记录分页列表查询 error....", e);
				throw e;
			}

		}

		log.info("用户绑卡分页列表查询 success....");
		return response;
	}

	private Response<List<BankCardQueryBindRecordResponse>> searchUserCardBind(
			Response<List<BankCardQueryBindRecordResponse>> response, Request<?> request, BankCardBindRecordQueryDTO queryDTO,boolean isList)
			throws Exception {
		Page<UserCardBind> list = null;
		if (response == null) {
			response = new Response<List<BankCardQueryBindRecordResponse>>();
		}

		PageRequest<BankCardBindRecordQueryDTO> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());

		pageRequest.setSearchDo(queryDTO);

		list = bankCardService.searchUserCardBindRecords(pageRequest);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request,isList);
		} else {
			response.setResult(new ArrayList<BankCardQueryBindRecordResponse>());
		}

		return response;
	}

	private Response<List<BankCardQueryBindRecordResponse>> fillResponse(Response<List<BankCardQueryBindRecordResponse>> response,
			Page<UserCardBind> list, Request<?> request,boolean isList) {
		if (response == null) {
			response = new Response<List<BankCardQueryBindRecordResponse>>();
		}

		log.info("查询绑卡记录，开始填充response.....");

		List<BankCardQueryBindRecordResponse> responseList = new ArrayList<BankCardQueryBindRecordResponse>();

		if (list.getResult().size() > 0) {
			UserCardBind[] bends=new UserCardBind[list.getResult().size()];
			responseList = DTOConverter.UserCardBind2BankCardQueryBindRecordResponse(isList,list.getResult().toArray(bends));
		}

		response.setResult(responseList);
		ResultPager pager = new ResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());

		response.setResultPage(pager);

		return response;
	}

	/**
	 * 
	* @Title: queryBankCardHistoryRecords 
	* @Description: 单用户历史绑卡记录
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryBankCardHistory")
	@ResponseBody
	public Object queryBankCardHistoryRecords(
			@RequestBody @ValidRequestBody Request<BankCardBindHistoryRecordRequest> request) throws Exception {
		Response<BankCardBindHistoryRecordResponse> response = new Response<BankCardBindHistoryRecordResponse>(request);
		log.info("单用户绑卡记录分页列表查询 start....");

		if (request.getBody() != null) {

			try {
				if(request.getBody().getParam().getUserAccount()!=null){
					User ups = userApprel.queryUserByName(request.getBody().getParam().getUserAccount());
					if (ups != null) {
						request.getBody().getParam().setUserId(ups.getId());
					}
				}				
				BankCardBindHistoryRecordQueryDTO bankCardBindHistoryRecordQueryDTO = DTOConverter
						.convertBankCardBindHistoryRecordQueryDTO(request);

				bankCardBindHistoryRecordQueryDTO.setSortColumns(BankCardBindHistoryRecordQueryDTO.SORT_COLUMNS
						+ " desc ");

				response = searchUserCardBindHistoryRecords(response, request, bankCardBindHistoryRecordQueryDTO);

			} catch (Exception e) {
				log.info("单用户绑卡记录分页列表查询 error....", e);
				throw e;
			}

		}

		log.info("单用户绑卡记录分页列表查询 success....");
		return response;
	}

	private Response<BankCardBindHistoryRecordResponse> searchUserCardBindHistoryRecords(
			Response<BankCardBindHistoryRecordResponse> response, Request<?> request,
			BankCardBindHistoryRecordQueryDTO queryDTO) throws Exception {
		Page<UserCardBindHistory> list = null;
		if (response == null) {
			response = new Response<BankCardBindHistoryRecordResponse>();
		}

		PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest = PageConverterUtils.getRestPageRequest(request
				.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());

		pageRequest.setSortColumns(queryDTO.getSortColumns());
		pageRequest.setSearchDo(queryDTO);

		list = bankCardService.searchUserCardBindHistoryRecords(pageRequest);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillHistoryRecordResponse(response, list, request);
		} else {
			response.setResult(new BankCardBindHistoryRecordResponse());
		}

		return response;
	}

	private Response<BankCardBindHistoryRecordResponse> fillHistoryRecordResponse(
			Response<BankCardBindHistoryRecordResponse> response, Page<UserCardBindHistory> list, Request<?> request) {
		if (response == null) {
			response = new Response<BankCardBindHistoryRecordResponse>();
		}

		BankCardBindHistoryRecordResponse recordResponse = new BankCardBindHistoryRecordResponse();

		log.info("查询绑卡记录，开始填充response.....");

		List<BankCardHistoryStruc> responseList = new ArrayList<BankCardHistoryStruc>();

		for (int i = 0; i < list.getResult().size(); i++) {
			UserCardBindHistory userCardBindHistoryRecord = list.getResult().get(i);

			BankCardHistoryStruc bankCardHistoryStruc = DTOConverter
					.UserCardBindHistoryRecord2BankCardHistoryStruc(userCardBindHistoryRecord);

			responseList.add(bankCardHistoryStruc);
		}

		recordResponse.setBankCardHistorys(responseList);
		response.setResult(recordResponse);
		ResultPager pager = new ResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());

		response.setResultPage(pager);

		return response;
	}

	/**
	 * 
	* @Title: bankCardLocking 
	* @Description: 银行卡加解锁
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/lockBankCard")
	@ResponseBody
	public Object bankCardLocking(@RequestBody @ValidRequestBody Request<BankCardLockingRequest> request)
			throws Exception {
		log.info("加解锁 start ......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			BankCardLockingRequest req = request.getBody().getParam();

			userBankLockedService.userCardLocking(req.getOperator(),
					req.getOverTime()==0?null:DataConverterUtil.convertLong2Date(req.getOverTime()),
					req.getLockId());
			if (req.getOverTime() - System.currentTimeMillis() >= TWO_HOUR) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("platform", platformName);
				msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.CardUnLock, map);
			}

		} catch (Exception e) {
			log.error("开始加解锁出现异常 error.....", e);
			throw e;
		}

		log.info("加解锁操作 success......");
		return response;
	}
	
	/**
	 * 
	* @Title: bankCardNowLock 
	* @Description: 立即綁定
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/bankCardNowLock")
	@ResponseBody
	public Object bankCardNowLock(@RequestBody @ValidRequestBody Request<BankCardQueryRequest> request)
			throws Exception {
		log.info("立即綁定 start ......");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			BankCardQueryRequest req = request.getBody().getParam();
			log.info("userId : " + req.getUserId());
			userBankLockedService.updateOverTime(req.getUserId());
			response.getHead().setStatus(1);
		} catch (Exception e) {
			log.error("开始加解锁出现异常 error.....", e);
			response.getHead().setStatus(0);
		}
		log.info("立即綁定 success......");
		return response;
	}

}
