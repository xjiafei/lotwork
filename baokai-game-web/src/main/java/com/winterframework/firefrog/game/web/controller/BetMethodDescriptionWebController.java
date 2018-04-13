package com.winterframework.firefrog.game.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionCheckRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionModifyRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionQueryResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameGroupCode;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: BetMethodDescriptionWebController 
* @Description: 玩法描述web controller
* @author Alan
* @date 2013-9-26 上午10:40:50 
*  
*/
@Controller("betMethodDescriptionWebController")
@RequestMapping(value = "/gameoa")
public class BetMethodDescriptionWebController {

	private Logger logger = LoggerFactory.getLogger(BetMethodDescriptionWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryBetMethodDescription")
	private String queryBetMethodDescriptionUrl;

	@PropertyConfig(value = "url.operations.modifyBetMethodDescription")
	private String modifyBetMethodDescriptionUrl;

	@PropertyConfig(value = "url.operations.checkBetMethodDescription")
	private String checkBetMethodDescriptionUrl;
	@PropertyConfig(value = "url.operations.publishBetMethodDescription")
	private String publishBetMethodDescriptionUrl;
	@Resource(name = "RedisClient")
	private RedisClient redis;

	@PropertyConfig(value = "url.game.queryGameAward")
	private String queryGameAwardUrl;

	private Long[] getSsqBonus() throws Exception {
		GameAwardQueryRequest requestData = new GameAwardQueryRequest();
		requestData.setAwardGroupId(187l);
		requestData.setLotteryId(99401l);
		requestData.setStatus(1);

		Long[] result = new Long[] { 60000000000l, 20000000000l, 35000000l, 3000000l, 180000l, 100000l };
		try {
			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);
			if (null != response.getBody() && null != response.getBody().getResult()) {
				AwardBonusStruc struc = response.getBody().getResult().getAwardBonusStrucList().get(0);
				result[0] = (struc.getAssistBMBonusList().get(0).getActualBonus());
				result[1] = (struc.getAssistBMBonusList().get(1).getActualBonus());
				result[2] = (struc.getAssistBMBonusList().get(2).getActualBonus());
				result[3] = (struc.getAssistBMBonusList().get(3).getActualBonus());
				result[4] = (struc.getAssistBMBonusList().get(4).getActualBonus());
				result[5] = (struc.getAssistBMBonusList().get(5).getActualBonus());
			}
		} catch (Exception e) {
			logger.error("query bouns error", e);
		}
		return result;
	}

	/**
	* @Title: queryHelp 
	* @Description: 根据lotteryid(彩种ID)&status(状态)查询玩法描述
	* @param lotteryid
	* @return Response<BetMethodDescriptionQueryResponse>
	* @throws Exception
	 */
	private Response<BetMethodDescriptionQueryResponse> queryHelp(Long lotteryid) throws Exception {
		Response<BetMethodDescriptionQueryResponse> response = new Response<BetMethodDescriptionQueryResponse>();

		BetMethodDescriptionQueryRequest queryRequest = new BetMethodDescriptionQueryRequest();
		queryRequest.setLotteryid(lotteryid);

		logger.info("query help start...");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryBetMethodDescriptionUrl, queryRequest, userid, username,
					BetMethodDescriptionQueryResponse.class);
		} catch (Exception e) {
			logger.error("query help error...");
			throw e;
		}
		logger.info("query help end...");

		return response;
	}

	/**
	* @Title: toHelp 
	* @Description: 访问玩法描述页面 
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/toHelp")
	public String toHelp(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetMethodDescriptionQueryResponse> response = queryHelp(lotteryid);
		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();
		if (null != response.getBody()) {
			groupCodes = DTOConvertor4Web.BetMethodDescriptionQueryResponse2GameGroupCodeList(response.getBody()
					.getResult(), lotteryid);
			model.addAttribute("strucs", groupCodes);

			String helpDesc = response.getBody().getResult().getLotteryHelpDes();
			String desc = helpDesc == null ? null : URLDecoder.decode(helpDesc);
			Integer checkStatus = response.getBody().getResult().getCheckStatus();
			model.addAttribute("lotteryHelpDes", desc);
			model.addAttribute("checkStatus", checkStatus);
			model.addAttribute("modifier", redis.get("check:" + lotteryid + ":help:modifier"));
			model.addAttribute("checker", redis.get("check:" + lotteryid + ":help:checker"));
		}
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		if (lotteryid >= 99301L && lotteryid <= 99307L) {
			return "/operations/help/help115";
		} else if (lotteryid == 99201L) {
			return "/operations/help/helpkl8";
		} else if ((lotteryid+"").startsWith("995")) {
			return "/operations/help/helpk3";
		}else if (lotteryid==99601||lotteryid==99602||lotteryid == 99603) {
			return "/operations/help/helpdice";
		} else if (lotteryid == 99401l) {
			model.addAttribute("bonus", this.getSsqBonus());
			return "/operations/help/helpssq";
		}
		return "/operations/help/help";
	}

	/**
	* @Title: toModifyHelp 
	* @Description: 访问玩法描述修改页面
	* @param queryRequest
	* @param model
	* @return
	* @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/toModifyHelp")
	public String toModifyHelp(@ModelAttribute("queryRequest") BetMethodDescriptionQueryRequest queryRequest,
			Model model) throws Exception {
		Response<BetMethodDescriptionQueryResponse> response = queryHelp(queryRequest.getLotteryid());

		if (null != response) {
			List<GameGroupCode> groupCodes = DTOConvertor4Web.BetMethodDescriptionQueryResponse2GameGroupCodeList(
					response.getBody().getResult(), queryRequest.getLotteryid());
			model.addAttribute("strucs", groupCodes);
			model.addAttribute("lotteryHelpDes", URLDecoder.decode(
					response.getBody().getResult().getLotteryHelpDes()==null?
							"":response.getBody().getResult().getLotteryHelpDes()));
		}
		model.addAttribute("modifier", redis.get("check:" + queryRequest.getLotteryid() + ":help:modifier"));
		model.addAttribute("checker", redis.get("check:" + queryRequest.getLotteryid() + ":help:checker"));
		model.addAttribute("pageType", "modify");
		model.addAttribute("lotteryId", queryRequest.getLotteryid());
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		if (queryRequest.getLotteryid() >= 99301L && queryRequest.getLotteryid() <= 99307L) {
			return "/operations/help/help115";
		} else if (queryRequest.getLotteryid() == 99201L) {
			return "/operations/help/helpkl8";
		} else if (queryRequest.getLotteryid() == 99401l) {
			model.addAttribute("bonus", this.getSsqBonus());
			return "/operations/help/helpssq";
		} else if ((queryRequest.getLotteryid()+"").startsWith("995")) {
			return "/operations/help/helpk3";
		} else if (queryRequest.getLotteryid()==99601||queryRequest.getLotteryid()==99602||queryRequest.getLotteryid()==99603) {
			return "/operations/help/helpdice";
		}
		return "/operations/help/help";
	}

	/**
	* @Title: modifyHelp 
	* @Description: 修改玩法描述
	* @param helpJson
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyHelp")
	@ResponseBody
	public Object modifyHelp(@RequestBody BetMethodDescriptionModifyRequest helpJson) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("modify help start...");
		try {
			httpClient.invokeHttp(serverPath + modifyBetMethodDescriptionUrl, helpJson, Object.class);
			redis.set("check:" + helpJson.getLotteryid() + ":help:modifier", RequestContext.getCurrUser().getUserName());
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify help error...");
			resp.setStatus(2l);
			resp.setMessage("failure");
		}
		logger.info("modify help end...");

		return resp;
	}

	/**
	* @Title: toAuditHelp 
	* @Description: 访问玩法描述审核页面 
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/toAuditHelp")
	public String toAuditHelp(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetMethodDescriptionQueryResponse> response = queryHelp(lotteryid);

		if (null != response) {
			List<GameGroupCode> groupCodes = DTOConvertor4Web.BetMethodDescriptionQueryResponse2GameGroupCodeList(
					response.getBody().getResult(), lotteryid);
			model.addAttribute("strucs", groupCodes);
			model.addAttribute("lotteryHelpDes", URLDecoder.decode(
					response.getBody().getResult().getLotteryHelpDes()==null?"":response.getBody().getResult().getLotteryHelpDes()));
			if (response.getBody().getResult().getLotteryHelpDes_bak() != null) {
				model.addAttribute("lotteryHelpDes_bak",
						URLDecoder.decode(response.getBody().getResult().getLotteryHelpDes_bak()));
			}
		}
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":help:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":help:checker"));
		model.addAttribute("pageType", "audit");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		if (lotteryid >= 99301L && lotteryid <= 99307L) {
			return "/operations/help/help115";
		} else if (lotteryid == 99201L) {
			return "/operations/help/helpkl8";
		} else if (lotteryid == 99401l) {
			model.addAttribute("bonus", this.getSsqBonus());
			return "/operations/help/helpssq";
		} else if ((lotteryid+"").startsWith("995")) {
			return "/operations/help/helpk3";
		} else if (lotteryid==99601||lotteryid==99602||lotteryid==99603) {
			return "/operations/help/helpdice";
		}
		return "/operations/help/help";
	}

	/**
	* @Title: toAuditHelpDetail 
	* @Description: 访问玩法描述审核详情页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/toAuditHelpDetail")
	public String toAuditHelpDetail(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<BetMethodDescriptionQueryResponse> response = queryHelp(lotteryid);

		if (null != response) {
			List<GameGroupCode> groupCodes = DTOConvertor4Web.BetMethodDescriptionQueryResponse2GameGroupCodeList(
					response.getBody().getResult(), lotteryid);
			model.addAttribute("strucs", groupCodes);

			if (response.getBody().getResult().getLotteryHelpDes_bak() != null) {
				model.addAttribute("lotteryHelpDes_bak",
						URLDecoder.decode(response.getBody().getResult().getLotteryHelpDes_bak()));
			}

			model.addAttribute("lotteryHelpDes", URLDecoder.decode(response.getBody().getResult().getLotteryHelpDes()==null?"":response.getBody().getResult().getLotteryHelpDes()));
		}
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":help:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":help:checker"));
		model.addAttribute("pageType", "auditDetail");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("checkStatus", response.getBody().getResult().getCheckStatus());
		if (lotteryid >= 99301L && lotteryid <= 99307L) {
			return "/operations/help/help115";
		} else if (lotteryid == 99201L) {
			return "/operations/help/helpkl8";
		} else if (lotteryid == 99401l) {
			model.addAttribute("bonus", this.getSsqBonus());
			return "/operations/help/helpssq";
		}else if ((lotteryid+"").startsWith("995")) {
			return "/operations/help/helpk3";
		} else if (lotteryid==99601||lotteryid==99602||lotteryid==99603) {
			return "/operations/help/helpdice";
		}
		return "/operations/help/help";
	}

	/**
	* @Title: auditHelp 
	* @Description: 审核玩法描述修改信息 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditHelp")
	@ResponseBody
	public Object auditHelp(@RequestBody BetMethodDescriptionCheckRequest request) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("audit help start...");
		try {
			httpClient.invokeHttp(serverPath + checkBetMethodDescriptionUrl, request, Object.class);
			redis.set("check:" + request.getLotteryid() + ":help:checker", RequestContext.getCurrUser().getUserName());
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("audit help error...");

			resp.setStatus(2l);
			resp.setMessage("failure");
		}
		logger.info("audit help end...");

		return resp;
	}

	/**
	 * 发布玩法描述
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publishHelp")
	@ResponseBody
	public AjaxResponse publishHelp(@RequestBody BetMethodDescriptionCheckRequest request) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		logger.info("audit help start...");
		try {
			httpClient.invokeHttp(serverPath + publishBetMethodDescriptionUrl, request, Object.class);

			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("audit help error...");

			resp.setStatus(2l);
			resp.setMessage("failure");
		}
		logger.info("audit help end...");

		return resp;
	}
}
