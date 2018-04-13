package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigCheckRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigPublishRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigVedioSourceRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.SBLimitRequest;
import com.winterframework.firefrog.game.web.dto.SBLimitResponse;
import com.winterframework.firefrog.game.web.dto.SubUserReportResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameSeriesConfigWebController 
* @Description: 运营参数信息设置web controller
* @author Alan
* @date 2013-9-29 上午11:25:07 
*  
*/
@Controller("gameSeriesConfigWebController")
@RequestMapping(value = "/gameoa")
public class GameSeriesConfigWebController {

	private Logger logger = LoggerFactory.getLogger(GameSeriesConfigWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.operations.queryGameSeriesConfig")
	private String queryGameSeriesConfigUrl;

	@PropertyConfig(value = "url.operations.modifyGameSeriesConfig")
	private String modifyGameSeriesConfigUrl;

	@PropertyConfig(value = "url.operations.auditGameSeriesConfig")
	private String auditGameSeriesConfigUrl;

	@PropertyConfig(value = "url.operations.releaseGameSeriesConfig")
	private String releaseGameSeriesConfigUrl;
	
	@PropertyConfig(value = "url.operations.vedioSourceConfig")
	private String vedioSourceConfigUrl;

	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	@PropertyConfig(value = "url.operations.querysblimit")
	private String querysblimit;
	@PropertyConfig(value = "url.operations.setsblimit")
	private String ssetsblimit;
	
	/**
	* @Title: querySeriesConfig 
	* @Description: 根据lotteryid(彩种ID)&status(状态)查询参数设置
	* @param lotteryid
	* @return Response<QuerySeriesConfigResponse>
	* @throws Exception
	 */
	private Response<QuerySeriesConfigResponse> querySeriesConfig(Long lotteryid) throws Exception {
		Response<QuerySeriesConfigResponse> response = new Response<QuerySeriesConfigResponse>();

		QuerySeriesConfigRequest request = new QuerySeriesConfigRequest();
		request.setLotteryid(lotteryid);

		logger.info("query series config start...");
		try {
			response = httpClient.invokeHttp(serverPath + queryGameSeriesConfigUrl, request,
					QuerySeriesConfigResponse.class);
		} catch (Exception e) {
			logger.error("query series config error...");
			throw e;
		}
		logger.info("query series config end...");

		return response;
	}

	/**
	* @Title: toSeriesConfig 
	* @Description: 访问参数设置页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toSeriesConfig")
	public String toSeriesConfig(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<QuerySeriesConfigResponse> response = querySeriesConfig(lotteryid);
		int status = response.getBody().getResult().getStatus();
		model.addAttribute("seriesConfig", response.getBody().getResult());
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("status", status);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("videoStrucs",response.getBody().getResult().getVedioStrucs());
		String pageType = "";
		switch (status) {
		case 3:
			pageType = "auditDetail";
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
		model.addAttribute("pageType", pageType);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":series:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":series:checker"));
		String winray = null;
		Long count = 10L;
		//try {
		SBLimitRequest limit = new SBLimitRequest();
		try {
			limit.setThreshold("0");
			Response<SBLimitResponse> result = httpClient.invokeHttp(serverPath + querysblimit, limit , SBLimitResponse.class);
			winray = result.getBody().getResult().getThreshold();
			count = result.getBody().getResult().getSlipCount();
		} catch (Exception e) {
			logger.error("modify series config error...");
			throw e;
		}
		if (lotteryid == 99602L || lotteryid == 99603L ){
			model.addAttribute("sblimit", winray );
			model.addAttribute("slipcount", count );
		}
		return "/operations/seriesConfig/seriesConfig";
	}

	/**
	* @Title: toModifySeriesConfig 
	* @Description: 访问参数设置修改页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toModifySeriesConfig")
	public String toModifySeriesConfig(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<QuerySeriesConfigResponse> response = querySeriesConfig(lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":series:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":series:checker"));
		model.addAttribute("seriesConfig", response.getBody().getResult());
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("pageType", "modify");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("videoStrucs",response.getBody().getResult().getVedioStrucs());
		return "/operations/seriesConfig/seriesConfig";
	}

	/**
	* @Title: modifySeriesConfig 
	* @Description: 修改参数信息
	* @param modifyForm
	* @param model
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "modifySeriesConfig")
	public String modifySeriesConfig(@ModelAttribute("modifyForm") EditSeriesConfigRequest modifyForm, Model model)
			throws Exception {
		logger.info("modify series config start...");
		modifyForm.setBackoutRatio(modifyForm.getBackoutRatio() == null ? 0 : modifyForm.getBackoutRatio());
		modifyForm.setBackoutStartFee(modifyForm.getBackoutStartFee() == null ? 0 : modifyForm.getBackoutStartFee());
		modifyForm.setIssuewarnBackoutTime(modifyForm.getIssuewarnBackoutTime() == null ? 0 : modifyForm
				.getIssuewarnBackoutTime());
		modifyForm.setIssuewarnExceptionTime(modifyForm.getIssuewarnExceptionTime() == null ? 0 : modifyForm
				.getIssuewarnExceptionTime());
		modifyForm.setIssuewarnUserBackoutTime(modifyForm.getIssuewarnUserBackoutTime() == null ? 0 : modifyForm
				.getIssuewarnUserBackoutTime());

		try {
			httpClient.invokeHttp(serverPath + modifyGameSeriesConfigUrl, modifyForm, Object.class);
			redis.set("check:" + modifyForm.getLotteryid() + ":series:modifier", RequestContext.getCurrUser()
					.getUserName());
		} catch (Exception e) {
			logger.error("modify series config error...");
			throw e;
		}
		logger.info("modify series config end...");

		Response<QuerySeriesConfigResponse> response = querySeriesConfig(modifyForm.getLotteryid());

		model.addAttribute("seriesConfig", response.getBody().getResult());
		model.addAttribute("lotteryId", modifyForm.getLotteryid());
		model.addAttribute("videoStrucs",response.getBody().getResult().getVedioStrucs());
		//return "redirect:/gameoa/toAuditSeriesConfig?lotteryid="+modifyForm.getLotteryid();
		return toAuditSeriesConfig(modifyForm.getLotteryid(), 1l, model);
	}

	
	/**
	* @Title: modifySBlimitConfig 
	* @Description: 修改参数信息
	* @param modifyForm
	* @param model
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "modifySBlimitConfig")
	@ResponseBody
	public SBLimitResponse modifySBlimitConfig(@RequestParam("sbvalue") String sbvalue,@RequestParam("sbcount") Long sbcount)
			throws Exception {
		logger.info("modify series config start..." + sbvalue + " " + sbcount);
		
		Response<SBLimitResponse> response = new Response<SBLimitResponse>();
		SBLimitRequest limit = new SBLimitRequest();
		try {
			limit.setThreshold(sbvalue);
			limit.setSlipCount(sbcount);
			response = httpClient.invokeHttp(serverPath + ssetsblimit, limit , SBLimitResponse.class);
		
		} catch (Exception e) {
			logger.error("modify series config error...");
			throw e;
		}
		return response.getBody().getResult();
	}
	
	/**
	* @Title: toAuditSeriesConfig 
	* @Description: 访问参数配置审核页面 
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toAuditSeriesConfig")
	public String toAuditSeriesConfig(@RequestParam("lotteryid") Long lotteryid,
			@RequestParam(required = false) Long modify, Model model) throws Exception {
		Response<QuerySeriesConfigResponse> response = querySeriesConfig(lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":series:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":series:checker"));
		model.addAttribute("seriesConfig", response.getBody().getResult());
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("pageType", "auditDetail");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("modify", modify);
		model.addAttribute("videoStrucs",response.getBody().getResult().getVedioStrucs());
		return "/operations/seriesConfig/seriesConfig";
	}

	/**
	* @Title: auditSeriesConfig 
	* @Description: 审核参数配置信息
	* @param lotteryid
	* @throws Exception
	 */
	@RequestMapping(value = "auditSeriesConfig")
	@ResponseBody
	public Object auditSeriesConfig(@RequestBody GameSeriesConfigCheckRequest request) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		logger.info("audit series config start...");
		try {
			httpClient.invokeHttp(serverPath + auditGameSeriesConfigUrl, request, Object.class);
			redis.set("check:" + request.getLotteryid() + ":series:checker", RequestContext.getCurrUser().getUserName());

			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("audit series config error...");
			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("audit series config end...");

		return resp;
	}

	/**
	* @Title: toReleaseSeriesConfig 
	* @Description: 访问参数配置发布页面
	* @param lotteryid
	* @param model
	* @throws Exception
	 */
	@RequestMapping(value = "toReleaseSeriesConfig")
	public String toReleaseSeriesConfig(@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		Response<QuerySeriesConfigResponse> response = querySeriesConfig(lotteryid);
		model.addAttribute("modifier", redis.get("check:" + lotteryid + ":series:modifier"));
		model.addAttribute("checker", redis.get("check:" + lotteryid + ":series:checker"));
		model.addAttribute("seriesConfig", response.getBody().getResult());
		model.addAttribute("status", response.getBody().getResult().getStatus());
		model.addAttribute("pageType", "released");
		model.addAttribute("lotteryId", lotteryid);
		model.addAttribute("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));
		model.addAttribute("videoStrucs",response.getBody().getResult().getVedioStrucs());
		return "/operations/seriesConfig/seriesConfig";
	}

	/**
	* @Title: releaseGameSeriesConfig 
	* @Description: 发布参数配置信息
	* @param lotteryid
	* @throws Exception
	 */
	@RequestMapping(value = "releaseGameSeriesConfig")
	@ResponseBody
	public Object releaseGameSeriesConfig(@RequestBody GameSeriesConfigPublishRequest request) throws Exception {
		AjaxResponse resp = new AjaxResponse();

		logger.info("release series config start...");
		try {
			httpClient.invokeHttp(serverPath + releaseGameSeriesConfigUrl, request, Object.class);

			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("release series config error...");
			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("release series config end...");

		return resp;
	}
	
	/**
	* @Title: vedioSourceConfigConfig 
	* @Description: 视频源增删改
	* @throws Exception
	 */
	@RequestMapping(value = "vedioSourceConfig")
	@ResponseBody
	public Object vedioSourceConfig(@RequestParam("lotteryid") Long lotteryid,@RequestParam(required = false) Long id,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) String name,
			@RequestParam(required = false) String url,@RequestParam String type) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		GameSeriesConfigVedioSourceRequest request=new GameSeriesConfigVedioSourceRequest();
		request.setId(id);
		request.setLotteryId(lotteryid);
		request.setName(name);
		request.setStatus(status);
		request.setType(type);
		request.setUrl(url);

		logger.info("vedioSource config start...");
		try {
			httpClient.invokeHttp(serverPath + vedioSourceConfigUrl, request, Object.class);

			resp.setStatus(1L);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("vedioSource config error...");
			resp.setStatus(2L);
			resp.setMessage("failure");
		}
		logger.info("vedioSource config end...");

		return resp;
	}
	
	

}
