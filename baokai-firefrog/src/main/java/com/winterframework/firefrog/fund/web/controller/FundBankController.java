/**   
* @Title: FundBankController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午11:26:42 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.convert.BeanConverter;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IFundBankService;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRecordWithSubResponse;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundBankUpdateRequest;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: FundBankController 
* @Description: 银行操作 controller
* @author Tod
* @date 2013-7-1 上午11:26:42 
*  
*/
@Controller("fundBankController")
@RequestMapping(value = "/fund")
public class FundBankController {

	private static Logger logger = LoggerFactory.getLogger(FundBankController.class);

	@Resource(name = "fundBankServiceImpl")
	private IFundBankService fundBankService;

	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;

	/**
	 * 
	* @Title: queryAllBank 
	* @Description: 查询所有的银行
	* @param request
	* @return
	 */
	@RequestMapping(value = "/queryAllBank")
	@ResponseBody
	public Response<BankCardQueryRecordResponse>  queryAllBank(@RequestBody @ValidRequestHeader Request<BindDate> request) throws Exception {
		Response<BankCardQueryRecordResponse> response = new Response<BankCardQueryRecordResponse>(request);

		BankCardQueryRecordResponse result = new BankCardQueryRecordResponse();

		List<FundBank> bankList;
		List<BankCard> bankCards;
		try {
			// 1、查询银行列表信息
			bankList = fundBankService.queryAllBank();

			result.setBankStruc(bankList);
			if (request.getBody() != null && request.getBody().getParam() != null
					&& Integer.valueOf(1).equals(request.getBody().getParam().getIsBind())) {
				// 如果需要绑卡的话,则返回绑定信息
				// 2、查询用户绑定的银行卡列表信息
				bankCards = bankCardService.queryBoundBankCard(request.getHead().getUserId(), null);
				int cardSize = bankCards.size();
				UserBankStruc[] userBankStruc = new UserBankStruc[cardSize];
				for (int j = 0; j < cardSize; j++) {
					userBankStruc[j] = DTOConverter.bankCard2UserBankStruc(bankCards.get(j));
				}
				result.setUserBankStruc(userBankStruc);
			}
			response.setResult(result);
		} catch (Exception e) {
			logger.error("queryAllBank error.", e);
			throw e;
		}
		return response;
	}

	
	/**
	 * 判斷VIP和普通用戶是否有權限看到充值頁籤
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkChargeOpen")
	@ResponseBody
	public String checkAliOpen(@RequestBody @ValidRequestHeader Request<FundBank> request) throws Exception{
		FundBank  bank = request.getBody().getParam();
		Boolean isChargeOpen = fundBankService.checkChargeOpen(bank.getId(), bank.getUserId(),0l);
		String isOpen = "";
		if(isChargeOpen){
			isOpen="Y";
		}else{
			isOpen="N";
		}
		return isOpen;
	}
	
	/**
	 * 判斷是否有權限看到支付寶(手機用)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkAliOpenPhone")
	@ResponseBody
	public Response<String> checkAliOpenPhone(@RequestBody @ValidRequestHeader Request<FundBank> request) throws Exception{
		Response<String> res = new Response<String>();
		FundBank  bank = request.getBody().getParam();
		Boolean isAliOpen = fundBankService.checkChargeOpen(bank.getId(), bank.getUserId(),1l);
		String isOpen = "";
		if(isAliOpen){
			isOpen="Y";
		}else{
			isOpen="N";
		}
		
		res.setResult(isOpen);
		return res;
	}
	
	public static class BindDate {
		private Integer isBind;

		public Integer getIsBind() {
			return isBind;
		}

		public void setIsBind(Integer isBind) {
			this.isBind = isBind;
		}

	}

	/**
	 * @Title: bankParamsSet
	 * @Description: 银行管理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bankParamsSet")
	@ResponseBody
	public Object bankParamsSet(@RequestBody @ValidRequestHeader Request<FundBank[]> request) throws Exception {
		logger.info("银行参数设置 start...");
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			FundBank[] bankStrucs = request.getBody().getParam();
			for (FundBank bankStruc : bankStrucs) {
				fundBankService.updateBank(bankStruc);
			}
		} catch (Exception e) {
			logger.error("银行参数设置 error...", e);
			throw e;
		}
		logger.info("银行参数设置 success...");

		return response;
	}

	/**
	 * @Title: updateBank
	 * @Description: 银行修改
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBank")
	@ResponseBody
	public Object updateBank(@RequestBody @ValidRequestHeader Request<FundBankUpdateRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			FundBankUpdateRequest bankReq = request.getBody().getParam();
			FundBank bank = new FundBank();
			BeanConverter.convert(bank, bankReq);
			fundBankService.updateBank(bank);
		} catch (Exception e) {
			logger.error("updateBank error.", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: addBank 
	* @Description: 银行新增
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/addBank")
	@ResponseBody
	public Object addBank(@RequestBody @ValidRequestHeader Request<FundBankUpdateRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			FundBankUpdateRequest bankReq = request.getBody().getParam();
			FundBank bank = new FundBank();
			BeanConverter.convert(bank, bankReq);
			bank.setInUse(1L);
			fundBankService.saveBank(bank);
		} catch (Exception e) {
			logger.error("saveBank error.", e);
			throw e;
		}
		return response;
	}
}