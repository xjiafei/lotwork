/**   
* @Title: SecurityCardController.java 
* @Package com.winterframework.firefrog.user.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-18 下午1:41:48 
* @version V1.0   
*/
package com.winterframework.firefrog.user.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.user.service.ISecurityCardService;
import com.winterframework.firefrog.user.web.dto.QuerySecurityCardRequest;
import com.winterframework.firefrog.user.web.dto.UserStrucResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: SecurityCardController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-2-18 下午1:41:48 
*  
*/
@Controller("securityCardController")
@RequestMapping(value = "/user/profile")
public class SecurityCardController {
	private static final Logger logger = LoggerFactory.getLogger(SecurityCardController.class);

	@Resource(name = "securityCardServiceImpl")
	private ISecurityCardService securityCardService;

	/** 
	* @Title: 绑定火蛙安全中心 
	* @throws Exception
	*/
	@RequestMapping(value = "/bindSecurityCard")
	@ResponseBody
	public Object bindSecurityCard(@RequestBody Request<QuerySecurityCardRequest> request) throws Exception {
		logger.info("开始绑定火蛙安全中心 请求...");
		QuerySecurityCardRequest queryRequest = request.getBody().getParam();
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		if (null != request.getBody()) {
			try {
				securityCardService.bindSecurityCard(queryRequest.getUserId(),
						queryRequest.getSercuritySerilizeNumber(), queryRequest.getPhoneType());
			} catch (Exception e) {
				log("绑定火蛙安全中心异常：", e);
				response.getHead().setStatus(1);
				throw e;
			}
		}
		return response;
	}

	/** 
	* @Title: 修改绑定火蛙安全中心
	* @throws Exception
	*/
	@RequestMapping(value = "/updateSecurityCard")
	@ResponseBody
	public Object updateSecurityCard(@RequestBody Request<QuerySecurityCardRequest> request) throws Exception {
		logger.info("开始修改绑定火蛙安全中心请求...");
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);
		QuerySecurityCardRequest queryRequest = request.getBody().getParam();
		if (null != request.getBody()) {
			try {
				securityCardService.updateSecurityCard(queryRequest);
			} catch (Exception e) {
				log("总代用户查询系统异常：", e);
				throw e;
			}
		}
		return response;
	}

	/** 
	* @Title: 请求解除火蛙安全中心绑定
	* @throws Exception
	*/
	@RequestMapping(value = "/unbindSecurityCard")
	@ResponseBody
	public Object unbindSecurityCard(@RequestBody Request<QuerySecurityCardRequest> request) throws Exception {
		logger.info("开始解除火蛙安全中心绑定请求...");
		Response<List<UserStrucResponse>> response = new Response<List<UserStrucResponse>>(request);
		QuerySecurityCardRequest queryRequest = request.getBody().getParam();
		if (null != request.getBody()) {
			try {
				securityCardService.unbindSecurityCard(queryRequest);
			} catch (Exception e) {
				log("解除火蛙安全中心绑定异常：", e);
				throw e;
			}
		}
		return response;
	}

	/** 
	* @Title: 获取火蛙安全中心验证码 
	* @throws Exception
	*/
	@RequestMapping(value = "/checkSecurityCardNumber ")
	@ResponseBody
	public Object getSecurityCardNumber(@RequestBody Request<QuerySecurityCardRequest> request) throws Exception {
		logger.info("开始获取火蛙安全中心验证码 ...");
		Response<Boolean> response = new Response<Boolean>(request);
		QuerySecurityCardRequest queryRequest = request.getBody().getParam();
		if (null != request.getBody()) {
			try {
				Boolean sercurityCode = securityCardService.checkSecurityCard(queryRequest.getSercuritySerilizeNumber());
				response.setResult(sercurityCode);
			} catch (Exception e) {
				log("总代用户查询系统异常：", e);
				throw e;
			}
		}
		return response;
	}

	/**
	 * 
	 * 方法描述：日志信息
	 * 
	 * @param message
	 * @param e
	 */
	private void log(String message, Exception e) {
		if (logger.isErrorEnabled()) {
			logger.error(message, e);
		}
	}

}
