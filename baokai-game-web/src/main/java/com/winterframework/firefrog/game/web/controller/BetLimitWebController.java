package com.winterframework.firefrog.game.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.winterframework.firefrog.common.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.BetLimitCheckRequest;
import com.winterframework.firefrog.game.web.dto.GameGroupCode;
import com.winterframework.firefrog.game.web.dto.BetLimitModifyRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitPublishRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: BetLimitWebController 
* @Description: 投注限制请求web controller
* @author Alan
* @date 2013-9-23 下午3:01:15 
*  
*/
@Controller("betLimitWebController")
@RequestMapping(value = "/gameoa")
public class BetLimitWebController {

	private Logger logger = LoggerFactory.getLogger(BetLimitWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryBetLimit")
	private String queryBetLimitUrl;

	@PropertyConfig(value = "url.operations.modifyBetLimit")
	private String modifyBetLimitUrl;

	@PropertyConfig(value = "url.operations.auditBetLimit")
	private String auditBetLimitUrl;

	@PropertyConfig(value = "url.operations.publishBetLimit")
	private String publishBetLimitUrl;

	@Resource(name = "lotteryMaxAwardGroupMap")
	private Map<String, String> lotteryMaxAwardGroupMap;
	@Resource(name = "RedisClient")
	private RedisClient redis;

	/**
	* @Title: queryBetLimit 
	* @Description: 根据lotteryid(彩种ID)&status(状态)查询投注限制
	* @param lotteryid
	* @return Response<BetLimitQueryResponse>
	* @throws Exception
	 */
	private Response<BetLimitQueryResponse> queryBetLimit(Long lotteryid) throws Exception {
		Response<BetLimitQueryResponse> response = new Response<BetLimitQueryResponse>();
		BetLimitQueryRequest queryBetLimit = new BetLimitQueryRequest();
		queryBetLimit.setLotteryid(lotteryid);

		logger.info("query betLimit start");

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryBetLimitUrl, queryBetLimit, userid, username,
					BetLimitQueryResponse.class);
		} catch (Exception e) {
			logger.error("query betLimit error");
			throw e;
		}
		logger.info("query betLimit end");

		return response;
	}

	/**
	* @Title: toBetLimit 
	* @Description: 访问投注限制页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toBetLimit")
	public String toBetLimit(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetLimitQueryResponse> response = queryBetLimit(lotteryid);

		int status = response.getBody().getResult().getStatus();
		String pageType = "";
		switch (status) {
		case 1:
			pageType = "continue";
			break;
		case 2:
			pageType = "deleted";
			break;
		case 3:
			pageType = "audit";
			break;
		case 4:
			pageType = "released";
			break;
		case 5:
			pageType = "unapprove";
			break;
		case 6:
			pageType = "publishFailed";
			break;
		default:
			break;
		}

		List<GameGroupCode> groupCodes = DTOConvertor4Web.BetLimitQueryResponse2GameGroupCodeList(response.getBody()
				.getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":limit:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":limit:checker"));
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", status);
		model.addAttribute("pageType", pageType);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("awardGroupName", lotteryMaxAwardGroupMap.get(String.valueOf(lotteryid)));
		return "/operations/betLimit/betLimit";
	}

	/**
	* @Title: toModifyBetLimit 
	* @Description: 访问投注限制修改页面
	* @param queryBetLimit
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toModifyBetLimit")
	public String toModifyBetLimit(@ModelAttribute("queryBetLimit") BetLimitQueryRequest queryBetLimit, Model model)
			throws Exception {
		Response<BetLimitQueryResponse> response = queryBetLimit(queryBetLimit.getLotteryid());

		List<GameGroupCode> groupCodes = DTOConvertor4Web.BetLimitQueryResponse2GameGroupCodeList(response.getBody()
				.getResult(),queryBetLimit.getLotteryid());
		model.addAttribute("modifier", redis.get("check:" + queryBetLimit.getLotteryid() + ":limit:modifier"));
		model.addAttribute("checker", redis.get("check:" + queryBetLimit.getLotteryid() + ":limit:checker"));
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "modify");
		model.addAttribute("lotteryId", queryBetLimit.getLotteryid());
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(queryBetLimit.getLotteryid()));
		return "/operations/betLimit/betLimit";
	}

	/**
	* @Title: modifyBetLimit 
	* @Description: 修改投注限制
	* @param betLimitJson
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyBetLimit")
	@ResponseBody
	public Object modifyBetLimit(@RequestBody BetLimitModifyRequest betLimitJson, Model model) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("modify betLimit start");
		try {
			httpClient.invokeHttp(serverPath + modifyBetLimitUrl, betLimitJson, Object.class);
			redis.set("check:" + betLimitJson.getLotteryid() + ":limit:modifier", RequestContext.getCurrUser()
					.getUserName());
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify betLimit error");
			resp.setStatus(2l);
			resp.setMessage("Updated betting limit errors");
		}
		logger.info("modify betLimit end");

		return resp;
	}

	/**
	* @Title: toAuditBetLimit 
	* @Description: 访问投注限制审核页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toAuditBetLimit")
	public String toAuditBetLimit(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetLimitQueryResponse> response = queryBetLimit(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.BetLimitQueryResponse2GameGroupCodeList(response.getBody()
				.getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":limit:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":limit:checker"));
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "audit");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		return "/operations/betLimit/betLimit";
	}

	/**
	* @Title: toAuditBetLimitDetail 
	* @Description: 访问投注限制审核详细页面 
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toAuditBetLimitDetail")
	public String toAuditBetLimitDetail(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetLimitQueryResponse> response = queryBetLimit(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.BetLimitQueryResponse2GameGroupCodeList(response.getBody()
				.getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":limit:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":limit:checker"));
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "auditDetail");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		return "/operations/betLimit/betLimit";
	}

	/**
	* @Title: auditBetLimit 
	* @Description: 审核投注限制修改信息
	* @param betLimitJson
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/auditBetLimit")
	@ResponseBody
	public Object auditBetLimit(@RequestBody BetLimitCheckRequest betLimitJson, Model model) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("audit betLimit start");
		try {
			httpClient.invokeHttp(serverPath + auditBetLimitUrl, betLimitJson, Object.class);
			redis.set("check:" + betLimitJson.getLotteryid() + ":limit:checker", RequestContext.getCurrUser()
					.getUserName());
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("audit betLimit error");
			resp.setStatus(2l);
			resp.setMessage("Audit betting limit errors");
		}
		logger.info("audit betLimit end");

		return resp;
	}

	/**
	* @Title: toPublishBetLimit 
	* @Description: 访问发布投注限制页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "/toPublishBetLimit")
	public String toPublishBetLimit(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetLimitQueryResponse> response = queryBetLimit(lotteryid);

		List<GameGroupCode> groupCodes = DTOConvertor4Web.BetLimitQueryResponse2GameGroupCodeList(response.getBody()
				.getResult(),lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":limit:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":limit:checker"));
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("strucs", groupCodes);
		model.addAttribute("pageType", "publish");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		return "/operations/betLimit/betLimit";
	}

	/**
	* @Title: publishBetLimit 
	* @Description: 发布投注限制
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/publishBetLimit")
	@ResponseBody
	public Object publishBetLimit(@RequestBody BetLimitPublishRequest request) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("publish betLimit start");
		try {
			httpClient.invokeHttp(serverPath + publishBetLimitUrl, request, Object.class);

			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("publish betLimit error");

			resp.setStatus(2l);
			resp.setMessage("Publish betting limit errors");
		}
		logger.info("publish betLimit end");

		return resp;
	}

}
