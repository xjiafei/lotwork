package com.winterframework.firefrog.advert.web.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.advert.web.dto.ActivityAwardConfig;
import com.winterframework.firefrog.advert.web.dto.ActivityConfigRequest;
import com.winterframework.firefrog.advert.web.dto.ActivityConfigResponse;

import com.winterframework.firefrog.advert.web.dto.PageForView;
import com.winterframework.firefrog.advert.web.dto.PageUtils;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepBigLittle;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepBigLittleResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepConfigOperateRequest;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepDetail;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepDetailCount;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepDetailCountResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepDetailCountResquest;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepDetailResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBao;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoCountResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoCountResquest;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoDetail;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoDetailResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepHongBaoResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepOperateLog;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepOperateResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepWheelSurf;
import com.winterframework.firefrog.advert.web.dto.sheep.ActivitySheepWheelSurfResponse;
import com.winterframework.firefrog.advert.web.dto.sheep.OperatorLog;
import com.winterframework.firefrog.advert.web.dto.sheep.OperatorLogJsonData;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: ActivitySheepWebController 
* @Description: 羊年活动
* @author Hugh
* @date 2015-1-12 下午3:01:15 
*  
*/
@Controller("activitySheepWebController")
@RequestMapping(value = "/adAdmin")
public class ActivitySheepWebController {

	private Logger logger = LoggerFactory.getLogger(ActivitySheepWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.gamecenter.connect")
	private String serverPath;

	private String queryActivityConfigUrl = "/game/queryActivityConfig";

	private String updateActivityConfigUrl = "/game/updateActivityConfig";

	private String queryActivitySheepHongBao = "/gameoa/querySheepHongBao";

	private String updateActivitySheepHongBao = "/gameoa/updateSheepHongBao";

	private String queryActivitySheepYaDaXiaoAll = "/gameoa/querySheepBigLittle";

	private String queryActivitySheepYaDaXiaoDetail = "/gameoa/querySheepDetail";

	private String updateActivityDaXiao = "/gameoa/updateSheepBigLittle";

	private String updateActivityDaXiaoDetail = "/gameoa/updateSheepDetail";

	private String queryActivitySheepZpAll = "/gameoa/querySheepWheelSurf";

	private String updateActivityZp = "/gameoa/updateSheepWheelSurf";

	private String querySheepOperateLog = "/gameoa/querySheepOperateLog";

	private String querySheepHongBaoCount = "/gameoa/querySheepHongBaoCount";

	private String querySheepDetailCount = "/gameoa/querySheepDetailCount";

	private String updateActivitySheepConfigOperate = "/gameoa/updateActivitySheepConfigOperate";

	private String querySheepHongBaoDetail = "/gameoa/querySheepHongBaoDetail";

	@RequestMapping(value = "/queryActivitySheepConfig")
	public String queryActivityConfig(Model model, Long activityId, Long ratioAll) throws Exception {
		Response<ActivityConfigResponse> response = new Response<ActivityConfigResponse>();
		ActivityConfigRequest query = new ActivityConfigRequest();

		query.setId(activityId);//升级活动 红包活动id 1
		logger.info("query queryActivityConfig start");
		PageRequest<ActivitySheepOperateLog> page = new PageRequest<ActivitySheepOperateLog>();
		page.setPageSize(Integer.MAX_VALUE);

		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			response = httpClient.invokeHttp(serverPath + queryActivityConfigUrl, query, userid, username,
					ActivityConfigResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityConfig end");

		if (response != null && response.getBody() != null && response.getBody().getResult() != null)
			model.addAttribute("configs", response.getBody().getResult().getConfigs());
		model.addAttribute("ratioAll", ratioAll != null ? ratioAll : 10000);
		if (activityId == 4) {//压大小
			return "/advert/sheepActivityDaXiaoConfig";
		} else {
			return "/advert/sheepActivityZpConfig";
		}
	}

	@RequestMapping(value = "/updateActivitySheepConfig")
	public String updateActivityConfig(Model model, Long activityId, Long[] id, Double[] award, Long[] allNumber,
			Double[] ratio, String[] awardName, Long[] winNumber) throws Exception {
		Double ratioAll = 0d;
		if (id != null) {
			for (int i = 0; i < id.length; i++) {
				if (!awardName[i].equals("连中奖")) {
					ratioAll += ratio[i];
				}
			}

			if (ratioAll * 100 == 10000) {
				for (int i = 0; i < id.length; i++) {
					System.out.println(id[i]);
					ActivityAwardConfig config = new ActivityAwardConfig();
					config.setId(id[i]);
					Double d = award[i] * 10000;
					config.setAward(d.longValue());
					config.setRatio((long) (ratio[i] * 10000) / 100);
					if (allNumber != null) {
						config.setAllNumber(allNumber[i]);
						config.setLastNumber(allNumber[i] - winNumber[i]);
					}
					try {
						httpClient.invokeHttp(serverPath + updateActivityConfigUrl, config, Object.class);
					} catch (Exception e) {
						logger.error("query betLimit error");
						throw e;
					}
				}
			}
		}
		BigDecimal b = new BigDecimal(ratioAll * 100);
		return queryActivityConfig(model, activityId, b.longValue());
	}

	@RequestMapping(value = "/queryActivityYaDaXiaoLog")
	public String queryActivityYaDaXiaoLog(Model model, Long pageStatus, ActivitySheepBigLittle request,
			@ModelAttribute("page") PageRequest<ActivitySheepBigLittle> page) throws Exception {
		Response<ActivitySheepBigLittleResponse> response = new Response<ActivitySheepBigLittleResponse>();

		Response<ActivitySheepDetailResponse> responseDetail = new Response<ActivitySheepDetailResponse>();
		if (request.getUserName() != null && request.getUserName() == "") {
			request.setUserName(null);
		}
		if (request.getChannel() != null && request.getChannel() == -1) {
			request.setChannel(null);
		}
		if (request.getUpdateStatus() != null && request.getUpdateStatus() == -1) {
			request.setUpdateStatus(null);
		}
		if (page == null) {
			page = new PageRequest<ActivitySheepBigLittle>();
		}
		if (pageStatus != null && pageStatus == 3) {
			page.setPageSize(50);
		}
		ActivitySheepDetail requestDetail = new ActivitySheepDetail();
		try {
			if (pageStatus == null) {
				response = httpClient.invokeHttp(serverPath + queryActivitySheepYaDaXiaoAll, request,
						PageUtils.getPager(page), ActivitySheepBigLittleResponse.class);
			} else {

				requestDetail.setBeginTime(DataConverterUtil.convertLong2Date(request.getStartTimel()));
				requestDetail.setEndTime(DataConverterUtil.convertLong2Date(request.getEndTimel()));
				requestDetail.setChannel(request.getChannel());
				requestDetail.setStatus(pageStatus);
				requestDetail.setUserName(request.getUserName());
				requestDetail.setActivityId(4l);
				responseDetail = httpClient.invokeHttp(serverPath + queryActivitySheepYaDaXiaoDetail, requestDetail,
						PageUtils.getPager(page), ActivitySheepDetailResponse.class);
			}
		} catch (Exception e) {
			logger.error("query queryActivityYaDaXiaoLog error");
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("unCheckNum", response.getBody().getResult().getUnCheckNum());
			model.addAttribute("pageStatus", pageStatus);
			model.addAttribute("query", request);
		} else if (responseDetail != null && responseDetail.getBody() != null
				&& responseDetail.getBody().getResult() != null) {
			model.addAttribute("struc", responseDetail.getBody().getResult().getList());
			ResultPager rp = responseDetail.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("unCheckNum", responseDetail.getBody().getResult().getUnCheckNum());
			model.addAttribute("pageStatus", pageStatus);
			model.addAttribute("query", request);
			model.addAttribute("timeStartStr",
					DateUtils.format(requestDetail.getBeginTime(), DateUtils.DATETIME_FORMAT_PATTERN));
			model.addAttribute("timeEndStr",
					DateUtils.format(requestDetail.getEndTime(), DateUtils.DATETIME_FORMAT_PATTERN));

		}
		if (pageStatus == null) {
			return "/advert/activitySheepYaDaXiao";
		} else if (pageStatus == 3) {
			return "/advert/activitySheepYaDaXiaoAudit";
		} else if (pageStatus == 4) {
			return "/advert/activitySheepYaDaXiaoPass";
		} else if (pageStatus == 5) {
			return "/advert/activitySheepYaDaXiaoUnPass";
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/queryActivitySheepHongBao")
	public String queryActivityHongBaoLog(Model model, String userName, Long status, String timestart, String timeend,
			Long channel, Long updateStatus, Long pageStatus,
			@ModelAttribute("page") PageRequest<ActivitySheepHongBao> page) throws Exception {
		Response<ActivitySheepHongBaoResponse> response = new Response<ActivitySheepHongBaoResponse>();
		//ActivityConfigRequest query = new ActivityConfigRequest();
		System.out.println(userName);
		System.out.println(status);
		System.out.println(timestart);
		System.out.println(timeend);
		if (pageStatus == null || pageStatus == 0) {
			pageStatus = null;
		}
		model.addAttribute("channel", channel);
		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("timestart", timestart);
		model.addAttribute("timeend", timeend);
		if (status != null && status == -1) {
			status = null;
		}
		if (channel != null && channel == -1) {
			channel = null;
		}
		if (updateStatus != null && updateStatus == -1) {
			updateStatus = null;
		}
		if (page != null) {
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		} else {
			page = new PageRequest<ActivitySheepHongBao>();
		}

		if (pageStatus != null && pageStatus == 1l) {
			page.setPageSize(50);
		}

		//query.setId(1L);
		logger.info("query queryActivityHongBaoLog start");
		ActivitySheepHongBao query = new ActivitySheepHongBao();
		query.setSignTimeBegin(DateUtils.parse(timestart, DateUtils.DATETIME_FORMAT_PATTERN));
		query.setSignTimEnd(DateUtils.parse(timeend, DateUtils.DATETIME_FORMAT_PATTERN));
		query.setUpdateStatus(updateStatus);
		query.setStatus(status);
		query.setChannel(channel);
		query.setUserName(userName);
		query.setVerifyStatus(pageStatus);
		try {
			response = httpClient.invokeHttp(serverPath + queryActivitySheepHongBao, query, PageUtils.getPager(page),
					ActivitySheepHongBaoResponse.class);
		} catch (Exception e) {
			logger.error("query queryActivityConfig error");
			throw e;
		}
		logger.info("query queryActivityHongBaoLog end");

		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("unCheckNum", response.getBody().getResult().getUnCheckNum());
		} else {
			model.addAttribute("page", null);
			model.addAttribute("struc", null);
			model.addAttribute("bet", 0);
			model.addAttribute("red", 0);
			model.addAttribute("unCheckNum", 0);
		}
		if (pageStatus == null || pageStatus == 0L) {
			return "/advert/activitySheepHongBao";
		} else if (pageStatus == 1L) {
			return "/advert/activitySheepHongBaoAudit";
		} else if (pageStatus == 2L) {
			return "/advert/activitySheepHongBaoPass";
		} else if (pageStatus == 3L) {
			return "/advert/activitySheepHongBaoRefuse";
		} else {
			return "/advert/activitySheepHongBao";
		}

	}

	@RequestMapping(value = "/updateActivitySheepHongBao")
	public Object updateActivitySheepHongBao(String ids2, Model model, ActivitySheepHongBao hongBao) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		//query.setId(1L);
		logger.info("update queryActivityHongBaoLog start");
		if (ids2 != null) {
			if (ids2.contains(",")) {
				ids2 = ids2.substring(1);
			}
			String[] ids = ids2.split(",");
			Long[] idls = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idls[i] = Long.valueOf(ids[i]);
			}
			hongBao.setIds(idls);
		}

		IUser user = RequestContext.getCurrUser();
		if (user != null) {
			hongBao.setUpdateName(user.getUserName());
			hongBao.setVerifyName(user.getUserName());
		} else {
			hongBao.setVerifyName("");
		}

		try {
			httpClient.invokeHttp(serverPath + updateActivitySheepHongBao, hongBao, Object.class);
		} catch (Exception e) {
			logger.error("update queryActivityConfig error");
			throw e;
		}
		logger.info("update queryActivityHongBaoLog end");

		response.setStatus(1);
		return response;
	}

	@RequestMapping(value = "/updateActivityDaXiao")
	@ResponseBody
	public Object updateActivityDaXiao(String type, Integer num, String reason, Long id, Integer updateType)
			throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		//query.setId(1L);
		logger.info("update queryActivityHongBaoLog start");
		ActivitySheepBigLittle big = new ActivitySheepBigLittle();
		big.setId(id);
		big.setUpdateReason(reason);
		big.setUpdateType(updateType);
		//big.setUserName(RequestContext.getCurrUser().getUserName());
		big.setUpdateName(RequestContext.getCurrUser().getUserName());
		if (num != null) {
			if ("decrease".equals(type)) {
				big.setUpdateLastNum(Long.valueOf(-num));
			} else {
				big.setUpdateLastNum(Long.valueOf(num));
			}
			IUser user = RequestContext.getCurrUser();
			if (user != null) {
				big.setUserName(user.getUserName());
			} else {
				big.setUserName("");
			}
		}

		OperateStatusResponse op = new OperateStatusResponse();
		try {
			httpClient.invokeHttp(serverPath + updateActivityDaXiao, big, Object.class);
			op.setStatus(1);
		} catch (Exception e) {
			op.setStatus(0);
			op.setMessage("更新失败");
			logger.error("update queryActivityConfig error");
			throw e;
		}
		logger.info("update queryActivityHongBaoLog end");
		return op;
	}

	@RequestMapping(value = "/queryActivitySheepYaDaXiaoDetail")
	@ResponseBody
	public Object queryActivitySheepYaDaXiaoDetail(String id, Long activityId) throws Exception {
		logger.info("update queryActivityHongBaoLog start");
		Response<ActivitySheepDetailResponse> responseDetail = new Response<ActivitySheepDetailResponse>();
		OperateStatusResponse op = new OperateStatusResponse();
		PageRequest<ActivitySheepBigLittle> page = new PageRequest<ActivitySheepBigLittle>();
		page.setPageNo(0);
		page.setPageSize(Integer.MAX_VALUE);
		try {
			ActivitySheepDetail requestDetail = new ActivitySheepDetail();
			requestDetail.setActivityId(activityId);
			requestDetail.setUserName(id);
			responseDetail = httpClient.invokeHttp(serverPath + queryActivitySheepYaDaXiaoDetail, requestDetail,
					PageUtils.getPager(page), ActivitySheepDetailResponse.class);

			if (responseDetail != null && responseDetail.getBody() != null
					&& responseDetail.getBody().getResult() != null) {
				Object[][] data = new Object[responseDetail.getBody().getResult().getList().size()][];
				int i = 0;
				for (ActivitySheepDetail detail : responseDetail.getBody().getResult().getList()) {
					data[i] = new Object[5];

					data[i][0] = DateUtils.format(detail.getActivityTime(), DateUtils.DATETIME_FORMAT_PATTERN);

					if (detail != null && detail.getActivityType() == 1 && detail.getGetNum() != null
							&& detail.getGetNum() > 0) {
						data[i][1] = "管理员添加";
					} else {
						data[i][1] = detail.getRecharge() == null ? 0 : detail.getRecharge() / 10000.00;
					}
					data[i][2] = detail.getGetNum() == null ? 0 : detail.getGetNum();
					data[i][3] = detail.getUseNum() == null ? 0 : detail.getUseNum();
					if (detail != null && detail.getActivityType() == 1 && detail.getUseNum() != null
							&& detail.getUseNum() > 0) {
						data[i][4] = "管理员扣除";
					} else {
						data[i][4] = detail.getResult() == null ? "" : detail.getResult();
					}
					i++;
				}
				op.setStatus(1);
				op.setData(data);

			}
		} catch (Exception e) {
			op.setStatus(0);
			op.setMessage("更新失败");
			logger.error("update queryActivityConfig error");
			throw e;
		}
		logger.info("update queryActivityHongBaoLog end");
		return op;
	}

	@RequestMapping(value = "/updateActivityDaXiaoDetail")
	@ResponseBody
	public Object updateActivitySheepHongBao(String ids2, Model model, ActivitySheepDetail as) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		//query.setId(1L);
		logger.info("update queryActivityHongBaoLog start");
		if (ids2 != null) {
			String[] ids = ids2.split(",");
			Long[] idls = null;
			if (ids[0].equals("")) {
				idls = new Long[ids.length - 1];
			} else {
				idls = new Long[ids.length];
			}
			int j = 0;
			for (int i = 0; i < ids.length; i++) {
				if (ids[i].equals("")) {
					continue;
				}
				idls[j] = Long.valueOf(ids[i]);
				j++;
			}
			as.setIds(idls);
		}

		IUser user = RequestContext.getCurrUser();
		if (user != null) {
			as.setVerifyName(user.getUserName());
		} else {
			as.setVerifyName("");
		}

		try {
			httpClient.invokeHttp(serverPath + updateActivityDaXiaoDetail, as, Object.class);
			response.setStatus(1);
		} catch (Exception e) {
			response.setStatus(0);
			response.setMessage("操作失败");
			logger.error("update queryActivityConfig error");
			throw e;
		}
		logger.info("update queryActivityHongBaoLog end");
		return response;
	}

	@RequestMapping(value = "/queryActivityZpLog")
	public String queryActivityZpLog(Model model, Long pageStatus, ActivitySheepWheelSurf request,
			@ModelAttribute("page") PageRequest<ActivitySheepBigLittle> page) throws Exception {
		Response<ActivitySheepWheelSurfResponse> response = new Response<ActivitySheepWheelSurfResponse>();

		Response<ActivitySheepDetailResponse> responseDetail = new Response<ActivitySheepDetailResponse>();
		if (request.getUserName() != null && request.getUserName() == "") {
			request.setUserName(null);
		}
		if (request.getChannel() != null && request.getChannel() == -1) {
			request.setChannel(null);
		}
		if (request.getUpdateStatus() != null && request.getUpdateStatus() == -1) {
			request.setUpdateStatus(null);
		}
		if (page == null) {
			page = new PageRequest<ActivitySheepBigLittle>();
		}

		if (pageStatus != null && pageStatus == 3) {
			page.setPageSize(50);
		}
		ActivitySheepDetail requestDetail = new ActivitySheepDetail();
		try {
			if (pageStatus == null) {
				response = httpClient.invokeHttp(serverPath + queryActivitySheepZpAll, request,
						PageUtils.getPager(page), ActivitySheepWheelSurfResponse.class);
			} else {

				requestDetail.setBeginTime(DataConverterUtil.convertLong2Date(request.getStartTimel()));
				requestDetail.setEndTime(DataConverterUtil.convertLong2Date(request.getEndTimel()));
				requestDetail.setChannel(request.getChannel());
				requestDetail.setStatus(pageStatus);
				requestDetail.setUserName(request.getUserName());
				requestDetail.setActivityId(5l);
				responseDetail = httpClient.invokeHttp(serverPath + queryActivitySheepYaDaXiaoDetail, requestDetail,
						PageUtils.getPager(page), ActivitySheepDetailResponse.class);
			}
		} catch (Exception e) {
			logger.error("query queryActivityYaDaXiaoLog error");
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("unCheckNum", response.getBody().getResult().getUnCheckNum());
			model.addAttribute("pageStatus", pageStatus);
			model.addAttribute("query", request);
		} else if (responseDetail != null && responseDetail.getBody() != null
				&& responseDetail.getBody().getResult() != null) {
			model.addAttribute("struc", responseDetail.getBody().getResult().getList());
			ResultPager rp = responseDetail.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("unCheckNum", responseDetail.getBody().getResult().getUnCheckNum());
			model.addAttribute("pageStatus", pageStatus);
			model.addAttribute("query", request);
			model.addAttribute("timeStartStr",
					DateUtils.format(requestDetail.getBeginTime(), DateUtils.DATETIME_FORMAT_PATTERN));
			model.addAttribute("timeEndStr",
					DateUtils.format(requestDetail.getEndTime(), DateUtils.DATETIME_FORMAT_PATTERN));

		}
		if (pageStatus == null) {
			return "/advert/activitySheepZpXiao";
		} else if (pageStatus == 3) {
			return "/advert/activitySheepZpAudit";
		} else if (pageStatus == 4) {
			return "/advert/activitySheepZpPass";
		} else if (pageStatus == 5) {
			return "/advert/activitySheepZpUnPass";
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/updateActivityZp")
	@ResponseBody
	public Object updateActivityZp(String type, Integer num, String reason, Long id, Integer updateType)
			throws Exception {
		//query.setId(1L);
		logger.info("update queryActivityHongBaoLog start");
		ActivitySheepWheelSurf big = new ActivitySheepWheelSurf();
		big.setId(id);
		big.setUpdateReason(reason);
		big.setUpdateType(updateType);
		big.setUpdateName(RequestContext.getCurrUser().getUserName());
		if (num != null) {
			if ("decrease".equals(type)) {
				big.setUpdateLastNum(Long.valueOf(-num));
			} else {
				big.setUpdateLastNum(Long.valueOf(num));
			}
		}

		OperateStatusResponse op = new OperateStatusResponse();
		try {
			httpClient.invokeHttp(serverPath + updateActivityZp, big, Object.class);
			op.setStatus(1);
		} catch (Exception e) {
			op.setStatus(0);
			op.setMessage("更新失败");
			logger.error("update queryActivityConfig error");
			throw e;
		}
		logger.info("update queryActivityHongBaoLog end");
		return op;
	}

	@RequestMapping(value = "/queryActivityOperateLog")
	public String querySheepOperateLog(Model model, ActivitySheepOperateLog request,
			@ModelAttribute("page") PageRequest<ActivitySheepOperateLog> page) throws Exception {

		Response<ActivitySheepOperateResponse> response = new Response<ActivitySheepOperateResponse>();
		if (request.getUserName() != null && request.getUserName() == "") {
			request.setUserName(null);
		}
		if (request.getActivityId() != null && request.getActivityId() == -1) {
			request.setActivityId(null);
		}
		request.setBeginTime(DataConverterUtil.convertLong2Date(request.getStartTimel()));
		request.setEndTime(DataConverterUtil.convertLong2Date(request.getEndTimel()));
		if (page != null) {
			System.out.println(page.getPageNo());
			System.out.println(page.getPageSize());
		} else {
			page = new PageRequest<ActivitySheepOperateLog>();
		}
		try {
			response = httpClient.invokeHttp(serverPath + querySheepOperateLog, request, PageUtils.getPager(page),
					ActivitySheepOperateResponse.class);
		} catch (Exception e) {
			logger.error("query querySheepOperateLog error");
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getList());
			ResultPager rp = response.getBody().getPager();
			PageForView pages = PageUtils.getPageForView(page, rp);
			pages.setPageNo(page.getPageNo());
			model.addAttribute("page", pages);
			model.addAttribute("query", request);
			model.addAttribute("timeStartStr",
					DateUtils.format(request.getBeginTime(), DateUtils.DATETIME_FORMAT_PATTERN));
			model.addAttribute("timeEndStr", DateUtils.format(request.getEndTime(), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		return "/advert/activitySheepOperateLog";
	}

	@RequestMapping(value = "/queryActivityHongBaoLogzl")
	public String querySheepHongBaoCount(Model model, String date) throws Exception {
		Response<ActivitySheepHongBaoCountResponse> response = new Response<ActivitySheepHongBaoCountResponse>();
		ActivitySheepHongBaoCountResquest request = new ActivitySheepHongBaoCountResquest();
		try {
			response = httpClient.invokeHttp(serverPath + querySheepHongBaoCount, request,
					ActivitySheepHongBaoCountResponse.class);
		} catch (Exception e) {
			logger.error("querySheepHongBaoCount error", e);
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getLists());
		}
		return "/advert/activitySheepHongbaozl";
	}

	@RequestMapping(value = "/queryActivityYaDaoXiaoLogzl")
	public String querySheepCount(Model model, String date, Long activityId) throws Exception {
		Response<ActivitySheepDetailCountResponse> response = new Response<ActivitySheepDetailCountResponse>();
		ActivitySheepDetailCountResquest request = new ActivitySheepDetailCountResquest();
		request.setActivityId(activityId);
		try {
			response = httpClient.invokeHttp(serverPath + querySheepDetailCount, request,
					ActivitySheepDetailCountResponse.class);
		} catch (Exception e) {
			logger.error("querySheepHongBaoCount error", e);
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			model.addAttribute("struc", response.getBody().getResult().getLists());
			model.addAttribute("activityId", activityId);
		}
		if (activityId == 4) {
			return "/advert/activitySheepYaDaXiaozl";
		} else {
			return "/advert/activitySheepZpzl";
		}
	}

	@RequestMapping(value = "/querySheepHongBaoDetailCount")
	@ResponseBody
	public Object querySheepHongBaoDetailCount(Model model, String date) throws Exception {
		Response<ActivitySheepHongBaoCountResponse> response = new Response<ActivitySheepHongBaoCountResponse>();
		ActivitySheepHongBaoCountResquest request = new ActivitySheepHongBaoCountResquest();
		request.setDate(date);
		OperateStatusResponse op = new OperateStatusResponse();
		try {
			response = httpClient.invokeHttp(serverPath + querySheepHongBaoCount, request,
					ActivitySheepHongBaoCountResponse.class);
			op.setStatus(1);
		} catch (Exception e) {
			op.setStatus(0);
			op.setMessage("查询详情错误");
			logger.error("querySheepHongBaoCount error", e);
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			Object[][] object = new Object[response.getBody().getResult().getLists().size()][4];
			int i = 0;
			for (ActivitySheepHongBaoCount count : response.getBody().getResult().getLists()) {
				if (i == 0) {
					object[i][0] = date;
				} else {
					object[i][0] = count.getChannelStr();
				}
				object[i][1] = count.getSighNum();
				object[i][2] = count.getReachNum();
				object[i][3] = count.getGetNum();
				i++;
			}
			op.setData(object);
		}
		return op;
	}

	@RequestMapping(value = "/querySheepDetailCount")
	@ResponseBody
	public Object querySheepDetailCount(Model model, String date, Long activityId) throws Exception {
		Response<ActivitySheepDetailCountResponse> response = new Response<ActivitySheepDetailCountResponse>();
		ActivitySheepDetailCountResquest request = new ActivitySheepDetailCountResquest();
		request.setActivityId(activityId);
		request.setDate(date);
		OperateStatusResponse op = new OperateStatusResponse();
		try {
			response = httpClient.invokeHttp(serverPath + querySheepDetailCount, request,
					ActivitySheepDetailCountResponse.class);
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			op.setMessage("查询详情错误");
			logger.error("querySheepHongBaoCount error", e);
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			Object[][] object = new Object[response.getBody().getResult().getLists().size()][6];
			int i = 0;
			if (!response.getBody().getResult().getLists().isEmpty()) {
				for (ActivitySheepDetailCount count : response.getBody().getResult().getLists()) {
					if (count != null) {
						if (activityId == 4) {
							if (i == 0) {
								object[i][0] = date;
							} else {
								object[i][0] = count.getChannelStr();
							}
							object[i][1] = count.getGetNum();
							object[i][2] = count.getUseNum();
							object[i][3] = count.getWinNum();
							object[i][4] = count.getFailNum();
							object[i][5] = count.getWin8Num();
						}
						if (activityId == 5) {
							if (i == 0) {
								object[i][0] = date;
							} else {
								object[i][0] = count.getLevels() == null ? "" : count.getLevels();
							}
							object[i][1] = count.getGetNum();
							object[i][2] = count.getUseNum();
							object[i][3] = count.getWinNum();
							long getNum = count.getGetNum() != null ? count.getGetNum() : 0;
							long useNum = count.getUseNum() != null ? count.getUseNum() : 0;
							object[i][4] = getNum - useNum;
							object[i][5] = count.getAward() != null ? count.getAward() / 10000.00 : 0;
						}
						i++;
					}

				}
				if (i == 0) {
					object = new Object[0][6];
				}
			}
			op.setData(object);
		}
		return op;
	}

	@RequestMapping(value = "/updateActivitySheepConfigOperate")
	@ResponseBody
	public Object updateActivitySheepConfigOperate(Model model, Long[] rsId, String[] rsTime, Long[] rsNum,
			Long action, Long activityConfigId) throws Exception {
		ActivitySheepConfigOperateRequest request = new ActivitySheepConfigOperateRequest();
		request.setIsOpen(action);
		request.setActivityConfigId(activityConfigId);
		List<ActivitySheepOperateLog> list = new ArrayList<ActivitySheepOperateLog>();
		for (int i = 0; i < rsNum.length; i++) {
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setNum(rsNum[i]);
			log.setGmtCreated(DateUtils.parse(rsTime[i], DateUtils.DATETIME_FORMAT_PATTERN));
			log.setActivityConfigId(activityConfigId);
			if (rsId[i] != -1) {
				log.setId(rsId[i]);
			}
			log.setActivityId(5l);
			list.add(log);
		}
		request.setLogs(list);
		OperateStatusResponse op = new OperateStatusResponse();
		try {
			httpClient.invokeHttp(serverPath + updateActivitySheepConfigOperate, request,
					ActivitySheepDetailCountResponse.class);
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			op.setMessage("查询详情错误");
			logger.error("querySheepHongBaoCount error", e);
			throw e;
		}
		return op;
	}

	@RequestMapping(value = "/queryActivitySheepConfigOperate")
	@ResponseBody
	public Object queryActivitySheepConfigOperate(Model model, ActivitySheepOperateLog request,
			@ModelAttribute("page") PageRequest<ActivitySheepOperateLog> page, Long reChargeModel) throws Exception {

		Response<ActivitySheepOperateResponse> response = new Response<ActivitySheepOperateResponse>();
		OperatorLog op = new OperatorLog();
		page = new PageRequest<ActivitySheepOperateLog>();
		page.setPageSize(Integer.MAX_VALUE);

		try {
			response = httpClient.invokeHttp(serverPath + querySheepOperateLog, request, PageUtils.getPager(page),
					ActivitySheepOperateResponse.class);
			op.setStatus(1l);
		} catch (Exception e) {
			logger.error("query querySheepOperateLog error");
			throw e;
		}
		if (response != null && response.getBody() != null && response.getBody().getResult() != null) {
			OperatorLogJsonData jsonDate = new OperatorLogJsonData();

			Object[][] object = new Object[response.getBody().getResult().getList().size()][4];
			jsonDate.setStatus(reChargeModel);
			Long length = 0l;

			jsonDate.setModel(reChargeModel);
			jsonDate.setActivityConfigId(request.getActivityConfigId());
			int i = 0;
			for (ActivitySheepOperateLog opl : response.getBody().getResult().getList()) {
				object[i][0] = DateUtils.format(opl.getGmtCreated(), DateUtils.DATETIME_FORMAT_PATTERN);
				object[i][1] = opl.getNum();
				object[i][2] = i + 1;
				object[i][3] = opl.getId();
				length = length + opl.getNum();
				i++;
			}
			jsonDate.setLength(length);
			jsonDate.setRewards(object);
			op.setData(jsonDate);
		}
		return op;
	}

	@RequestMapping(value = "/querySheepHongBaoDetail")
	@ResponseBody
	public Object querySheepHongBaoDetail(Model model, Long id, String userName, Long amount) throws Exception {

		Response<ActivitySheepHongBaoDetailResponse> response = new Response<ActivitySheepHongBaoDetailResponse>();
		OperatorLog op = new OperatorLog();
		ActivitySheepHongBaoDetail request = new ActivitySheepHongBaoDetail();
		request.setId(id);
		try {
			response = httpClient.invokeHttp(serverPath + querySheepHongBaoDetail, request,
					ActivitySheepHongBaoDetailResponse.class);
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			logger.error("query querySheepOperateLog error");
			throw e;
		}

		if (response != null && response.getBody() != null && response.getBody().getResult() != null
				&& response.getBody().getResult().getLists() != null) {
			Object[][] object = new Object[response.getBody().getResult().getLists().size() + 1][4];
			object[0][0] = userName;
			object[0][1] = amount / 10000.00;
			int i = 1;
			for (ActivitySheepHongBaoDetail opl : response.getBody().getResult().getLists()) {
				object[i][0] = opl.getDate();
				object[i][1] = opl.getAward3() / 10000.00;
				object[i][2] = opl.getAward4() / 10000.00;
				object[i][3] = opl.getAward3() / 10000.00 + opl.getAward4() / 10000.00;
				i++;
			}

			op.setData(object);
		} else {
			Object[][] object = new Object[1][2];
			object[0][0] = userName;
			object[0][1] = amount / 10000.00;
			op.setData(object);
		}
		return op;
	}
}
