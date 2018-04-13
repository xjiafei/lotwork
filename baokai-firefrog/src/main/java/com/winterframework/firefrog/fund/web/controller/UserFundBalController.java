/**   
* @Title: UserFundBalController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-30 下午5:56:32 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.fund.web.dto.UserBal;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: UserFundBalController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-30 下午5:56:32 
*  
*/
@Controller("userFundBalController")
@RequestMapping("/fund")
public class UserFundBalController {

	@Resource(name = "fundChargeServiceImpl")
	private IFundService fundService;

	private static final Logger logger = LoggerFactory.getLogger(UserFundBalController.class);

	/**
	 * 
	* @Title: 获取用户余额 
	* @Description: 获取用户余额 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getUserBal")
	@ResponseBody
	public Response<Long> getUserBal(@RequestBody Request<Long> request) throws Exception {
		Response<Long> response = new Response<Long>();
		try {
			//get bal
			UserFund userFund = fundService.getUserFund(request.getBody().getParam());
			response.setResult(userFund.getBal());
		} catch (Exception e) {
			logger.error("getUserBal error", e);
			throw e;
		}
		return response;
	}
	/**
	 * 
	* @Title: 获取用户余额 
	* @Description: 获取用户余额  和可用 餘額
	* @param request
	* @return
	* @throws Exception
	 */
	
	@RequestMapping(value = "/getUserTransferBal")
	@ResponseBody
	public Response<UserBal> getUserBaldisAmount(@RequestBody Request<Long> request) throws Exception {
		Response<UserBal> response = new Response<UserBal>();
		try {
			//get bal
			UserFund userFund = fundService.getUserFund(request.getBody().getParam());
			
			UserBal userbal = new UserBal();
			userbal.setBal(userFund.getBal());
			userbal.setDisableAmt(userFund.getDisableAmt());
			response.setResult(userbal);
		} catch (Exception e) {
			logger.error("getUserBal error", e);
			throw e;
		}
		return response;
	}
}
