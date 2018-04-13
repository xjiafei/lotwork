package com.winterframework.firefrog.fund.web.controller;


import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.exception.FundBalanceShortageException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.fund.service.IFundTransferService;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.CountResultPager;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundTransferInitResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequestDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferStrc;
import com.winterframework.firefrog.fund.web.dto.FundTransferSubRequestDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferSubResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferStrc;
import com.winterframework.firefrog.fund.web.dto.FundTransferSubResponse;
import com.winterframework.firefrog.fund.web.dto.QueryFundTransferOrderDTO;
import com.winterframework.firefrog.fund.web.dto.UserFundRequestDTO;
import com.winterframework.firefrog.fund.web.dto.UserFundResponse;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: FundTransferController 
* @Description: 用户转账控制器 
* @author david
* @date 2013-7-1 上午10:03:39 
*  
*/
@Controller("fundTransferController")
@RequestMapping(value = "/fund")
public class FundTransferController {

	private static final Logger log = LoggerFactory.getLogger(FundTransferController.class);
	@Resource(name = "fundTransferServiceImpl")
	private IFundTransferService fundTransferService;

	@Resource(name = "fundChargeServiceImpl")
	private IFundService transferFundServiceImpl;
	
	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;
	
	@Resource(name = "globalGrayListServiceImpl")
	private IGlobalGrayListService globalGrayListService;
	
	@Resource(name="RedisClient")
	private RedisClient redisClient;
	
	public static class UserId{
		private Long userId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: 转账初始化请求
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/fundTransferInit")
	@ResponseBody
	public Response<FundTransferInitResponse> init(@RequestBody @ValidRequestHeader Request<UserId> request)
			throws Exception {
		Response<FundTransferInitResponse> response = new Response<FundTransferInitResponse>(request);
		long userId = request.getHead().getUserId();
		log.info("fundTransferInit  userId.");
		if(request.getBody()!=null){
			userId=(Long)request.getBody().getParam().getUserId();
		}
		try {
			FundTransferInitResponse result = fundTransferService.init(userId);
			response.setResult(result);
		} catch (Exception e) {
			log.error("fundTransferInit error.", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: fundTransfer 
	* @Description: 用户转账
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/fundTransfer")
	@ResponseBody
	public Object fundTransfer(@RequestBody @ValidRequestBody Request<FundTransferRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		FundTransferOrder fundTransferOrder = DTOConverter.fundTransferRequestDTO2UserFund(request);
		
		long userid=request.getBody().getParam().getUserId();
		String key="FUND_TRANSFER"+String.valueOf(userid);
		
			try {
				//控制一秒內用戶只可點擊一次,避免工具刷入
				if(redisClient.get(key)==null){
					redisClient.set(key,"1",1);
					fundTransferService.transferFund(fundTransferOrder);
					fundTransferService.SendMsg(fundTransferOrder.getTransferAmt()==null?0L:fundTransferOrder.getTransferAmt(), 
												fundTransferOrder.getApplyUser());
					//判斷是否為灰名單
					globalGrayListService.saveGlobalGrayList(request.getBody().getParam().getRcvUserId(), request.getBody().getParam().getUserId());
				}
			} catch (FundChangedException e) {
				response.getHead().setStatus(FundChangedException.CODE);
				log.error("转账时保存资金，资金记录发生变动", e);
			} catch (Exception e) {
				log.error("saveFundTransferOrder error.", e);
				throw e;
			}
			return response;
	}

	/** 
	* @Title: fundTransfer 
	* @Description: 用户转账
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/fundTransferSubSystem")
	@ResponseBody
	public Response<FundTransferSubResponse>  fundTransferSubSystem(@RequestBody @ValidRequestBody Request<FundTransferSubRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		
		Response<FundTransferSubResponse> response = new Response<FundTransferSubResponse>(request);
		log.info("fundTransferSubSystem start.");
		try {
			FundTransferOrder fundTransferOrder = DTOConverter.fundTransferRequestDTO2SubSystem(request, request.getBody().getParam().getDirection());
			fundTransferOrder.setSn(request.getBody().getParam().getSn());
			Long balance = fundTransferService.transferSubsystemFund (fundTransferOrder, request.getBody().getParam().getDirection(), null);
		
			FundTransferSubResponse result = new FundTransferSubResponse ();
			result.setAmount(fundTransferOrder.getTransferAmt());
			result.setSn(fundTransferOrder.getSn());
			result.setUserId(fundTransferOrder.getApplyUser().getId());
			result.setBalance(balance);
			response.setResult(result);
			
		} catch (FundChangedException e) {
			response.getHead().setStatus(FundChangedException.CODE);
			log.error("子系統转账时保存资金，资金记录发生变动  user id " + request.getBody().getParam().toString() , e);
		} catch (FundBalanceShortageException e){
			response.getHead().setStatus(FundChangedException.CODENOMONEY);
			log.error("fundTransferSubSystem 資金異動 失敗  user id " + request.getBody().getParam().toString() , e);
		}catch (org.springframework.orm.ibatis3.IbatisSystemException e){
			response.getHead().setStatus(FundChangedException.CODETIMEOUT);
			log.error("fundTransferSubSystem time out user id " + request.getBody().getParam().toString() , e);
		}catch (TransactionTimedOutException e){ 
			response.getHead().setStatus(FundChangedException.CODETIMEOUT);
			log.error("fundTransferSubSystem time out user id " + request.getBody().getParam().toString() , e);
		}catch (Exception e) {
			log.error("saveFundTransferOrder error.", e);
			throw e;
		}

		return response;
	}
	
	/** 
	* @Title: getUserFundInfo 
	* @Description: 获取用户资金信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getUserFundInfo")
	@ResponseBody
	public Response<UserFundResponse> getUserFundInfo(@RequestBody @ValidRequestBody Request<UserFundRequestDTO> request)
			throws Exception {
		Response<UserFundResponse> response = new Response<UserFundResponse>(request);
		try {
			UserFund userFund = transferFundServiceImpl.getUserFund(request.getBody().getParam().getUserId());
			UserFundResponse userFundResponse = new UserFundResponse();
			userFundResponse.setBal(userFund.getBal());
			userFundResponse.setAvailBal(userFund.getDisableAmt() > userFund.getBal()?userFund.getBal():userFund.getDisableAmt());

			response.setResult(userFundResponse);
		} catch (Exception e) {
			log.error("getUserFundInfo error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/queryFundTranferRecordByCriteria")
	@ResponseBody
	public Object queryFundTranferRecordByCriteria(
			@RequestBody @ValidRequestBody Request<QueryFundTransferOrderDTO> request) throws Exception {
		log.info("开始查询用户申诉...");

		Response<List<FundTransferStrc>> response = new Response<List<FundTransferStrc>>(request);

		if (null != request.getBody()) {

			try {

				FundTransferRecordQueryDTO queryDTO = DTOConverter.convertFundTransferRecordQueryDTO(request);
				queryDTO.setExactUser(AccountTool.getRealAccount(request.getHead().getUserAccount()));				
				queryDTO.setSortColumns(FundTransferRecordQueryDTO.SORT_COLUMNS + " desc ");

				QueryFundTransferOrderDTO reqParam = request.getBody()
						.getParam();
				if (reqParam != null) {
					String account = request.getBody().getParam().getExactUser();
					if (account != null) {
						QueryLevelRecycleHistoryResponse recycleHist = levelRecycleService
								.queryRecycleLastHistory(account);
						queryDTO.setRecycleDate(recycleHist.getActivityDate());
					}
				}
				
				response = searchFundTranferRecord(response, request, queryDTO);

			} catch (Exception e) {
				log.error("多条件查看客户转账记录：", e);
				throw e;
			}
		}

		log.info("功成处理多条件查询请求...");
		return response;

	}

	/** 
	* @Title: searchCustomer 
	* @Description: 方法描述：与Service服务接口
	* @param response
	* @param request
	* @param queryDTO
	* @return
	* @throws Exception
	*/
	private Response<List<FundTransferStrc>> searchFundTranferRecord(Response<List<FundTransferStrc>> response,
			Request<?> request, FundTransferRecordQueryDTO queryDTO) throws Exception {

		CountPage<FundTransferOrder> list = null;
		if (response == null) {
			response = new Response<List<FundTransferStrc>>();
		}
		PageRequest<FundTransferRecordQueryDTO> pageReqeust = getPageRequest(request);
		pageReqeust.setSortColumns(queryDTO.getSortColumns());

		//response.setHead(ResponseHeader.createReponseHeader(request.getHead()));
		pageReqeust.setSearchDo(queryDTO);

		list = fundTransferService.searchFundTransferOrder(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<FundTransferStrc>());
		}

		return response;
	}

	/**
	 * 
	 * 方法描述：设置PageRequest信息
	 * 
	 * @param request
	 * @return
	 */
	private PageRequest<FundTransferRecordQueryDTO> getPageRequest(Request<?> request) {

		PageRequest<FundTransferRecordQueryDTO> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		return pageReqeust;
	}

	/**
	 * 填充response
	 * 
	 * @param response
	 * @param list
	 */
	private Response<List<FundTransferStrc>> fillResponse(Response<List<FundTransferStrc>> response,
			CountPage<FundTransferOrder> list, Request<?> request) {

		if (null == response) {
			response = new Response<List<FundTransferStrc>>(request);
		}
		log.info("开始填充response。。。");
		List<FundTransferStrc> responseList = new ArrayList<FundTransferStrc>();

		for (int i = 0; i < list.getResult().size(); i++) {

			FundTransferOrder fundTransferOrder = list.getResult().get(i);
			FundTransferStrc fundTransferStrc = DTOConverter.fundTransferOrder2FundTransferStrc(fundTransferOrder);

			responseList.add(fundTransferStrc);

		}
		response.setResult(responseList);
		CountResultPager pager = new CountResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());
		pager.setSum(list.getSum());
		pager.setSum2(list.getSum2());

		response.setResultPage(pager);

		return response;

	}

}
