package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.modules.web.util.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameGroupCode;
import com.winterframework.firefrog.game.web.dto.SellingStatusCheckRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusModifyRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusPublishRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusQueryRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusQueryResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: SellingStatusWebController 
* @Description: 销售状态web controller
* @author Alan
* @date 2013-9-27 下午2:57:19 
*  
*/
@Controller("sellingStatusWebController")
@RequestMapping(value = "/gameoa")
public class SellingStatusWebController {

	private Logger logger = LoggerFactory.getLogger(SellingStatusWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.querySellingStatus")
	private String querySellingStatusUrl;

	@PropertyConfig(value = "url.operations.modifySellingStatus")
	private String modifySellingStatusUrl;

	@PropertyConfig(value = "url.operations.checkSellingStatus")
	private String checkSellingStatusUrl;

	@PropertyConfig(value = "url.operations.publishSellingStatus")
	private String publishSellingStatusUrl;
	@Resource(name = "RedisClient")
	private RedisClient redis;

	/**
	* @Title: querySellingStatus 
	* @Description: 根据lotteryid(彩种ID)查询销售状态
	* @param lotteryid
	* @return Response<SellingStatusQueryResponse>
	* @throws Exception
	 */
	private Response<SellingStatusQueryResponse> querySellingStatus(Long lotteryid) throws Exception {
		Response<SellingStatusQueryResponse> response = new Response<SellingStatusQueryResponse>();

		SellingStatusQueryRequest request = new SellingStatusQueryRequest();
		request.setLotteryid(lotteryid);

		logger.info("query selling status start...");
		try {
			response = httpClient.invokeHttp(serverPath + querySellingStatusUrl, request,
					SellingStatusQueryResponse.class);
		} catch (Exception e) {
			logger.info("query selling status error...");
			throw e;
		}

		return response;
	}

	/**
	* @Title: toSellingStatus 
	* @Description: 访问销售状态页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toSellingStatus")
	public String toSellingStatus(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<SellingStatusQueryResponse> response = querySellingStatus(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.SellingStatusQueryResponse2GameGroupCodeList(response
				.getBody().getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":sell:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":sell:checker"));
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("takeOffTime", response.getBody().getResult().getTakeOffTime()==null?null:formatDate(response.getBody().getResult().getTakeOffTime()));
		return "/operations/sellingStatus/sellingStatus";
	}

	/**
	* @Title: toModifySellingStatus 
	* @Description: 访问销售状态修改页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toModifySellingStatus")
	public String toModifySellingStatus(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<SellingStatusQueryResponse> response = querySellingStatus(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.SellingStatusQueryResponse2GameGroupCodeList(response
				.getBody().getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":sell:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":sell:checker"));
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "modify");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("takeOffTime", response.getBody().getResult().getTakeOffTime()==null?null:formatDate(response.getBody().getResult().getTakeOffTime()));
		return "/operations/sellingStatus/sellingStatus";
	}

	/**
	* @Title: modifySellingStatus 
	* @Description: 修改销售状态
	* @param jsonStr
	* @throws Exception
	 */
	@RequestMapping(value = "/modifySellingStatus")
	@ResponseBody
	public Object modifySellingStatus(@RequestBody SellingStatusModifyRequest jsonStr) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		logger.info("modify selling status start...");
		try {
			httpClient.invokeHttp(serverPath + modifySellingStatusUrl, jsonStr, Object.class);
			redis.set("check:" + jsonStr.getLotteryid() + ":sell:modify", RequestContext.getCurrUser().getUserName());

			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify selling status error...");

			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("modify selling status end...");

		return resp;
	}

	/**
	* @Title: toAuditSellingStatus 
	* @Description: 访问销售状态审核页面 
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toAuditSellingStatus")
	public String toAuditSellingStatus(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<SellingStatusQueryResponse> response = querySellingStatus(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.SellingStatusQueryResponse2GameGroupCodeList(response
				.getBody().getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":sell:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":sell:checker"));
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "audit");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("takeOffTime", response.getBody().getResult().getTakeOffTime()==null?null:formatDate(response.getBody().getResult().getTakeOffTime()));
		return "/operations/sellingStatus/sellingStatus";
	}

	/**
	* @Title: toAuditSellingStatusDetail 
	* @Description: 访问销售状态审核详细页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toAuditSellingStatusDetail")
	public String toAuditSellingStatusDetail(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<SellingStatusQueryResponse> response = querySellingStatus(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.SellingStatusQueryResponse2GameGroupCodeList(response
				.getBody().getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":sell:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":sell:checker"));
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "auditDetail");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("takeOffTime", response.getBody().getResult().getTakeOffTime()==null?null:formatDate(response.getBody().getResult().getTakeOffTime()));
		return "/operations/sellingStatus/sellingStatus";
	}

	/**
	* @Title: auditSellingStatus 
	* @Description: 审核销售状态 
	* @param lotteryid
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditSellingStatus")
	@ResponseBody
	public Object auditSellingStatus(@RequestBody SellingStatusCheckRequest sellingRequest) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		logger.info("audit selling status start...");
		try {
			httpClient.invokeHttp(serverPath + checkSellingStatusUrl, sellingRequest, Object.class);
			redis.set("check:" + sellingRequest.getLotteryid() + ":sell:checker", RequestContext.getCurrUser()
					.getUserName());
			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("audit selling status error...");

			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("audit selling status end...");

		return resp;
	}

	/**
	* @Title: publishSellingStatus 
	* @Description: 发布销售状态
	* @param lotteryid
	* @throws Exception
	 */
	@RequestMapping(value = "/publishSellingStatus")
	@ResponseBody
	public Object publishSellingStatus(@RequestBody SellingStatusPublishRequest sellingRequest) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		logger.info("publish selling status start...");
		try {
			httpClient.invokeHttp(serverPath + publishSellingStatusUrl, sellingRequest, Object.class);

			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("publish selling status error...");

			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("publish selling status end...");

		return resp;
	}
	
	private String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

}
