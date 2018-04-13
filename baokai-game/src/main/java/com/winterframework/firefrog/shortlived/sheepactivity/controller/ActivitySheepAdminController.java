package com.winterframework.firefrog.shortlived.sheepactivity.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepBigLittleResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepConfigOperateRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCountResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCountResquest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCountResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCountResquest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetailResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepOperateResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepWheelSurfResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityAwardConfigService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivityService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepBigLittleService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepDetailService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepHongBaoService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepOperateLogService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepWheelSurfService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IUserSystemUpdateLogService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 羊年活动后台管理控制器
 * @author hugh
 *
 */
@Controller("activitySheepAdminController")
@RequestMapping("/gameoa")
public class ActivitySheepAdminController {

	/**
	 * 
	* @Title: querySheepHongBao 
	* @Description: 红包记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepHongBao")
	@ResponseBody
	public Response<ActivitySheepHongBaoResponse> querySheepHongBao(
			@RequestBody  Request<ActivitySheepHongBao> request) throws Exception {
		log.info("queryRedEnvelope  start...");

		Response<ActivitySheepHongBaoResponse> response = new Response<ActivitySheepHongBaoResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<ActivitySheepHongBao> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		Page<ActivitySheepHongBao> page = null;
		try {
			page = activitySheepHongBaoServiceImpl.queryPage(pr);
			Long unCheckNum = activitySheepHongBaoServiceImpl.getUncheckNum();
			if(page != null){
				response.setResult(new ActivitySheepHongBaoResponse(page.getResult(),unCheckNum));
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageFirstElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}								
		} catch (Exception e) {
			log.error("queryRedEnvelope error", e);
			throw e;
		}
		log.info("queryRedEnvelope end...");
		return response;
	}
	
	
	/**
	 * 
	* @Title: querySheepHongBaoDetail 
	* @Description: 红包用户记录详情查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepHongBaoDetail")
	@ResponseBody
	public Response<ActivitySheepHongBaoDetailResponse> querySheepHongBaoDetail(
			@RequestBody  Request<ActivitySheepHongBaoDetail> request) throws Exception {
		log.info("querySheepHongBaoDetail  start...");

		Response<ActivitySheepHongBaoDetailResponse> response = new Response<ActivitySheepHongBaoDetailResponse>();

		ActivitySheepHongBaoDetailResponse result = new ActivitySheepHongBaoDetailResponse();

		try {
			List<ActivitySheepHongBaoDetail> list= activitySheepDetailServiceImpl.getCount(request.getBody().getParam().getId());	
			if(list != null){
				result.setLists(list);
				response.setResult(result);
			}								
		} catch (Exception e) {
			log.error("querySheepHongBaoDetail error", e);
			throw e;
		}
		log.info("querySheepHongBaoDetail end...");
		return response;
	}
	
	/**
	 * 
	* @Title: updateSheepHongBao 
	* @Description: 红包记录更新
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateSheepHongBao")
	@ResponseBody
	public Response<Object> updateSheepHongBao(
			@RequestBody  Request<ActivitySheepHongBao> request) throws Exception {
		log.info("updateSheepHongBao  start...");

		Response<Object> response = new Response<Object>();
		try {
			activitySheepHongBaoServiceImpl.updateEntityByType(request.getBody().getParam());
			response.setResult(1);							
		} catch (Exception e) {
			log.error("updateSheepHongBao error", e);
			throw e;
		}
		log.info("queryRedEnvelope end...");
		return response;
	}
	
	/**
	 * 
	* @Title: querySheepBigLittle 
	* @Description: 羊年活动猜大小查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepBigLittle")
	@ResponseBody
	public Response<ActivitySheepBigLittleResponse> querySheepBigLittle(
			@RequestBody @ValidRequestBody Request<ActivitySheepBigLittle> request) throws Exception {
		log.info("querySheepBigLittle  start...");

		Response<ActivitySheepBigLittleResponse> response = new Response<ActivitySheepBigLittleResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<ActivitySheepBigLittle> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<ActivitySheepBigLittle> page = null;
	
		try {
			page = activitySheepBigLittleServiceImpl.queryPage(pr);
			if(page != null){
				ActivitySheepBigLittleResponse sheepBig = new ActivitySheepBigLittleResponse();
				sheepBig.setList(page.getResult());
				sheepBig.setUnCheckNum(activitySheepDetailServiceImpl.getUncheckNum(4L));
				response.setResult(sheepBig);
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageFirstElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}			
		} catch (Exception e) {
			log.error("querySheepBigLittle error", e);
			throw e;
		}

		log.info("querySheepBigLittle end...");
		return response;
	}
	
	
	/**
	 * 
	* @Title: updateSheepHongBao 
	* @Description: 猜大小记录更新
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateSheepBigLittle")
	@ResponseBody
	public Response<Object> updateSheepBigLittle(
			@RequestBody  Request<ActivitySheepBigLittle> request) throws Exception {
		log.info("updateSheepBigLittle  start...");

		Response<Object> response = new Response<Object>();
		try {
			activitySheepBigLittleServiceImpl.updateEntityByType(request.getBody().getParam());
			response.setResult(1);							
		} catch (Exception e) {
			log.error("updateSheepBigLittle error", e);
			throw e;
		}
		log.info("updateSheepBigLittle end...");
		return response;
	}
	
	/**
	 * 
	* @Title: updateSheepHongBao 
	* @Description: 猜大小记录更新
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateSheepDetail")
	@ResponseBody
	public Response<Object> updateSheepDetail(
			@RequestBody  Request<ActivitySheepDetail> request) throws Exception {
		log.info("updateSheepDetail  start...");

		Response<Object> response = new Response<Object>();
		try {
			activitySheepDetailServiceImpl.updateEntityByType(request.getBody().getParam());
			response.setResult(1);							
		} catch (Exception e) {
			log.error("updateSheepDetail error", e);
			throw e;
		}
		log.info("updateSheepDetail end...");
		return response;
	}
	
	
	/**
	 * 
	* @Title: querySheepWheelSurf 
	* @Description: 羊年活动转盘查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepWheelSurf")
	@ResponseBody
	public Response<ActivitySheepWheelSurfResponse> queryLotteryResult(
			@RequestBody @ValidRequestBody Request<ActivitySheepWheelSurf> request) throws Exception {
		log.info("querySheepBigLittle  start...");

		Response<ActivitySheepWheelSurfResponse> response = new Response<ActivitySheepWheelSurfResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<ActivitySheepWheelSurf> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<ActivitySheepWheelSurf> page = null;
	
		try {
			
			page = activitySheepWheelSurfServiceImpl.queryPage(pr);
			if(page != null){
				//response.setResult(new ActivitySheepWheelSurfResponse(page.getResult(),0L));
				ActivitySheepWheelSurfResponse resp = new ActivitySheepWheelSurfResponse();
				resp.setList(page.getResult());
				resp.setUnCheckNum(activitySheepDetailServiceImpl.getUncheckNum(5L));
				response.setResult(resp);
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageFirstElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}			
		} catch (Exception e) {
			log.error("querySheepBigLittle error", e);
			throw e;
		}

		log.info("querySheepBigLittle end...");
		return response;
	}

	/**
	 * 
	* @Title: updateSheepWheelSurf 
	* @Description: 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateSheepWheelSurf")
	@ResponseBody
	public Response<Object> updateSheepWheelSurf(
			@RequestBody  Request<ActivitySheepWheelSurf> request) throws Exception {
		log.info("updateSheepDetail  start...");

		Response<Object> response = new Response<Object>();
		try {
			activitySheepWheelSurfServiceImpl.updateEntityByType(request.getBody().getParam());
			response.setResult(1);							
		} catch (Exception e) {
			log.error("updateSheepDetail error", e);
			throw e;
		}
		log.info("updateSheepDetail end...");
		return response;
	}
	
	
	/**
	 * 
	* @Title: querySheepDetail 
	* @Description: 羊年活动抽奖详情查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepDetail")
	@ResponseBody
	public Response<ActivitySheepDetailResponse> querySheepDetail(
			@RequestBody @ValidRequestBody Request<ActivitySheepDetail> request) throws Exception {
		log.info("querySheepDetail  start...");

		Response<ActivitySheepDetailResponse> response = new Response<ActivitySheepDetailResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<ActivitySheepDetail> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<ActivitySheepDetail> page = null;
	
		try {
			page = activitySheepDetailServiceImpl.queryPage(pr);
			if(page!=null){
				ActivitySheepDetailResponse dresponse= new ActivitySheepDetailResponse();
				dresponse.setList(page.getResult());
				dresponse.setUnCheckNum(activitySheepDetailServiceImpl.getUncheckNum(request.getBody().getParam().getActivityId()));
				
				response.setResult(dresponse);
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageFirstElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}
		

		} catch (Exception e) {
			log.error("querySheepDetail error", e);
			throw e;
		}

		log.info("querySheepDetail end...");
		return response;
	}
	  
	
	/**
	 * 
	* @Title: querySheepOperateLog 
	* @Description: 羊年活动操作查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepOperateLog")
	@ResponseBody
	public Response<ActivitySheepOperateResponse> querySheepOperateLog(
			@RequestBody @ValidRequestBody Request<ActivitySheepOperateLog> request) throws Exception {
		log.info("queryRedEnvelope  start...");

		Response<ActivitySheepOperateResponse> response = new Response<ActivitySheepOperateResponse>();

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<ActivitySheepOperateLog> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<ActivitySheepOperateLog> page = null;

		try {
			page = activitySheepOperateLogServiceImpl.queryPage(pr);
			if(page!=null){
				response.setResult(new ActivitySheepOperateResponse(page.getResult()));
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageFirstElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}

		} catch (Exception e) {
			log.error("queryRedEnvelope error", e);
			throw e;
		}

		log.info("queryRedEnvelope end...");
		return response;
	}
	
	/**
	 * @Title: updateActivitySheepConfigOperate
	 * @Description: 更新配置 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateActivitySheepConfigOperate")
	@ResponseBody
	public Response<Object> updateActivitySheepConfigOperate(
			@RequestBody @ValidRequestBody Request<ActivitySheepConfigOperateRequest> request)
			throws Exception {

		log.info("开始更新配置 ......");
		Response<Object> response = new Response<Object>(request);
	
		try {
		
			activitySheepOperateLogServiceImpl.updateActivitySheepConfigOperate(request.getBody().getParam());
		} catch (Exception e) {
			log.error("更新配置 异常 ", e);
			throw e;
		}

		log.info("更新配置 完成。");
		return response;
	}

	
	/**
	 * 
	* @Title: querySheepHongBaoCount 
	* @Description: 红包记录总览查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepHongBaoCount")
	@ResponseBody
	public Response<ActivitySheepHongBaoCountResponse> querySheepHongBaoCount(
			@RequestBody  Request<ActivitySheepHongBaoCountResquest> request) throws Exception {
		log.info("querySheepHongBaoCount  start...");

		Response<ActivitySheepHongBaoCountResponse> response = new Response<ActivitySheepHongBaoCountResponse>();

		try {
			List<ActivitySheepHongBaoCount> counts = activitySheepHongBaoServiceImpl.getCounts(request.getBody().getParam().getDate());
			ActivitySheepHongBaoCountResponse result = new ActivitySheepHongBaoCountResponse();
			result.setLists(counts);
			response.setResult(result );							
		} catch (Exception e) {
			log.error("querySheepHongBaoCount error", e);
			throw e;
		}
		log.info("querySheepHongBaoCount end...");
		return response;
	}
	
	/**
	 * 
	* @Title: querySheepHongBaoCount 
	* @Description: 红包记录总览查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySheepDetailCount")
	@ResponseBody
	public Response<ActivitySheepDetailCountResponse> querySheepDetailCount(
			@RequestBody  Request<ActivitySheepDetailCountResquest> request) throws Exception {
		log.info("querySheepDetailCount  start...");

		Response<ActivitySheepDetailCountResponse> response = new Response<ActivitySheepDetailCountResponse>();

		try {
			List<ActivitySheepDetailCount> counts = activitySheepDetailServiceImpl.getCounts(request.getBody().getParam().getDate(),request.getBody().getParam().getActivityId());
			ActivitySheepDetailCountResponse result = new ActivitySheepDetailCountResponse();
			result.setLists(counts);
			response.setResult(result );							
		} catch (Exception e) {
			log.error("querySheepDetailCount error", e);
			throw e;
		}
		log.info("querySheepDetailCount end...");
		return response;
	}
	
	private Logger log = LoggerFactory.getLogger(ActivitySheepAdminController.class);

	@Resource(name = "activityAwardConfigServiceImpl")
	private IActivityAwardConfigService activityAwardConfigServiceImpl;

	@Resource(name = "activityServiceImpl")
	private IActivityService activityServiceImpl;

	@Resource(name = "userSystemUpdateLogServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;
	
	@Resource(name = "activitySheepDetailServiceImpl")
	private IActivitySheepDetailService activitySheepDetailServiceImpl;
	
	@Resource(name = "activitySheepBigLittleServiceImpl")
	private IActivitySheepBigLittleService activitySheepBigLittleServiceImpl;
	
	@Resource(name = "activitySheepHongBaoServiceImpl")
	private IActivitySheepHongBaoService activitySheepHongBaoServiceImpl;
	
	@Resource(name = "activitySheepWheelSurfServiceImpl")
	private IActivitySheepWheelSurfService activitySheepWheelSurfServiceImpl;
	
	@Resource(name = "activitySheepOperateLogServiceImpl")
	private IActivitySheepOperateLogService activitySheepOperateLogServiceImpl;
}
