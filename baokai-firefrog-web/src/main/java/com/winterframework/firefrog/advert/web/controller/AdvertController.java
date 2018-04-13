/**   
* @Title: AdvertController.java 
* @Package com.winterframework.firefrog.advert.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-7 下午2:22:07 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdQueryRequest;
import com.winterframework.firefrog.advert.web.dto.AdReviewRequest;
import com.winterframework.firefrog.advert.web.dto.AdSpaceListOperater;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.advert.web.dto.AdStruc;
import com.winterframework.firefrog.advert.web.dto.CountResult;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: AdvertController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-7 下午2:22:07 
*  
*/
@Controller
@RequestMapping(value = "/adAdmin")
public class AdvertController {

	private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "url.advert.addAdvert")
	private String addAdvertUrl;

	@PropertyConfig(value = "url.allAdSpace.query")
	private String queryAllAdSpaceUrl;

	@PropertyConfig(value = "url.advert.queryAdPage")
	private String queryAdPageUrl;

	@PropertyConfig(value = "url.advert.deleteAdvert")
	private String deleteAdvertUrl;

	@PropertyConfig(value = "url.advert.updateAdvert")
	private String updateAdvertUrl;

	@PropertyConfig(value = "url.advert.queryAdvertById")
	private String queryAdvertByIdUrl;

	@PropertyConfig(value = "url.advert.reviewAdvert")
	private String reviewAdvertUrl;

	@PropertyConfig(value = "url.advert.getAdSpaceByAdId")
	private String getAdSpaceByAdIdUrl;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.imageserver.visit")
	protected String imageServerVisit;

	@RequestMapping(value = "/toAddAdvert")
	public String toAddAdvert(Model model) throws Exception {
		Response<List<AdSpaceStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllAdSpaceUrl, new AdSpaceStruc(),
				new TypeReference<Response<List<AdSpaceStruc>>>() {
				});
		model.addAttribute("spaces", cateResp.getBody().getResult());
		model.addAttribute("imageServerVisit", imageServerVisit);
		model.addAttribute("cate2Name", "goPublish");
		return "/advert/addAdvert";
	}

	@RequestMapping(value = "/toUpdateAdvert")
	public String toUpdateAdvert(@RequestParam("id") Long id, Model model) throws Exception {
		Response<List<AdSpaceStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllAdSpaceUrl, new AdSpaceStruc(),
				new TypeReference<Response<List<AdSpaceStruc>>>() {
				});
		AdStruc adStrucQuery = new AdStruc();
		adStrucQuery.setId(id);
		Response<List<AdSpaceStruc>> cateRespSelect = null;
		Response<AdStruc> resp = null;
		try {
			cateRespSelect = httpClient.invokeHttp(urlPath + getAdSpaceByAdIdUrl, adStrucQuery,
					new TypeReference<Response<List<AdSpaceStruc>>>() {
					});
			resp = httpClient.invokeHttp(urlPath + queryAdvertByIdUrl, id, new TypeReference<Response<AdStruc>>() {
			});
		} catch (Exception e) {
			logger.error("toUpdateAdvert error", e);
			throw e;
		}

		AdStruc adStruc = resp.getBody().getResult();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		model.addAttribute("begin", dateformat.format(new Date(adStruc.getGmtEffectBegin())));
		model.addAttribute("end", dateformat.format(new Date(adStruc.getGmtEffectEnd())));
		model.addAttribute("spaces", cateResp.getBody().getResult());
		String selectSpaces = "";
		AdSpaceStruc as = null;
		for (AdSpaceStruc adSpaceS : cateRespSelect.getBody().getResult()) {
			selectSpaces = selectSpaces + adSpaceS.getId() + ",";
			as = adSpaceS;
		}
		model.addAttribute("selectSpaces", selectSpaces);
		model.addAttribute("spaceStruc", as);
		model.addAttribute("adStruc", resp.getBody().getResult());
		model.addAttribute("imageServerVisit", imageServerVisit);
		model.addAttribute("cate2Name", "goPublish");
		return "/advert/editAdvert";
	}

	@RequestMapping(value = "/getAdSpaceListByAd")
	@ResponseBody
	public AdSpaceListOperater getAdSpaceListByAd(@RequestParam("id") Long id, Model model) throws Exception {
		AdStruc adStrucQuery = new AdStruc();
		adStrucQuery.setId(id);
		AdSpaceListOperater op = new AdSpaceListOperater();
		try {
			Response<List<AdSpaceStruc>> cateRespSelect = httpClient.invokeHttp(urlPath + getAdSpaceByAdIdUrl,
					adStrucQuery, new TypeReference<Response<List<AdSpaceStruc>>>() {
					});
			List<String> data = new ArrayList<String>();
			for (AdSpaceStruc adSpaceStruc : cateRespSelect.getBody().getResult()) {
				String name = adSpaceStruc.getPageIdStr() + "(" + adSpaceStruc.getName() + "-"
						+ adSpaceStruc.getWidth() + "*" + adSpaceStruc.getHeight() + ")";
				data.add(name);
			}
			op.setData(data);
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			op.setMessage(e.getMessage());
			logger.error("getAdSpaceListByAd error", e);
		}
		return op;
	}

	@RequestMapping(value = "/reviewAdvert")
	@ResponseBody
	public OperateStatusResponse reviewAdvert(@RequestParam("ids") String ids, @RequestParam("status") Long status,
			@RequestParam(required = false) String memo, Model model) throws Exception {
		OperateStatusResponse op = new OperateStatusResponse();
		List<Long> idList = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			idList.add(Long.valueOf(id));
		}
		AdReviewRequest req = new AdReviewRequest();
		IUser user = RequestContext.getCurrUser();
		req.setIds(idList);
		req.setStatus(status);
		if(StringUtils.isEmpty(memo))
		{
			req.setMemo("");
		}else{
			req.setMemo(new java.net.URLDecoder().decode(memo, "utf-8"));
		}
		try {
			if(user!=null){
				httpClient.invokeHttpWithoutResultType(urlPath + reviewAdvertUrl, req, user.getId(), user.getUserName());
			}else{
				httpClient.invokeHttpWithoutResultType(urlPath + reviewAdvertUrl, req);
			}
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			op.setMessage(e.getMessage());
			logger.error("reviewAdvert error", e);
		}

		return op;
	}

	@RequestMapping(value = "/updateAdvert")
	@ResponseBody
	public OperateStatusResponse updateAdvert(@ModelAttribute("adStruc") @Valid AdStruc adStruc, BindingResult result,
			@RequestParam("ids") String ids, @RequestParam("positionv") String positionv) throws Exception {
		List<Long> spacesIds = new ArrayList<Long>();
		OperateStatusResponse op = new OperateStatusResponse();
		if (result.hasErrors()) {
			op.setStatus(0l);
			op.setMessage("添加数据错误！");
			return op;
		}
		for (String space : positionv.split(",")) {
			spacesIds.add(Long.valueOf(space));
		}
		adStruc.setSpacesIds(spacesIds);
		for (String id : ids.split(",")) {
			switch (Integer.valueOf(id)) {
			case 1:
				adStruc.setRcAll(1l);
				break;
			case 13:
				adStruc.setRcGuest(1l);
				break;
			case 2:
				adStruc.setRcTopAgent(1l);
				break;
			case 3:
				adStruc.setRcOtAgent(1l);
				break;
			case 10:
				adStruc.setRcCustomer(1l);
				break;
			case 11:
				adStruc.setRcVip(1l);
				break;
			case 12:
				adStruc.setRcNonVip(1l);
				break;
			default:
				break;
			}
		}
		if (adStruc.getRcAll() == null) {
			adStruc.setRcAll(0l);
		}
		if (adStruc.getRcCustomer() == null) {
			adStruc.setRcCustomer(0l);
		}
		if (adStruc.getRcGuest() == null) {
			adStruc.setRcGuest(0l);
		}
		if (adStruc.getRcNonVip() == null) {
			adStruc.setRcNonVip(0l);
		}
		if (adStruc.getRcOtAgent() == null) {
			adStruc.setRcOtAgent(0l);
		}
		if (adStruc.getRcTopAgent() == null) {
			adStruc.setRcTopAgent(0l);
		}
		if (adStruc.getRcVip() == null) {
			adStruc.setRcVip(0l);
		}
		try {
			IUser user  = RequestContext.getCurrUser();
			if(user!=null){
				adStruc.setOperator(user.getUserName());
			}
			httpClient.invokeHttpWithoutResultType(urlPath + updateAdvertUrl, adStruc);
			op.setStatus(1l);
		} catch (Exception e) {
			logger.error("add advert error", e);
			op.setStatus(0l);
			op.setMessage(e.getMessage());
		}
		return op;
	}

	@RequestMapping(value = "/addAdvert")
	@ResponseBody
	public OperateStatusResponse addAdvert(@ModelAttribute("adStruc") @Valid AdStruc adStruc, BindingResult result,
			@RequestParam("ids") String ids, @RequestParam("positionv") String positionv) throws Exception {
		List<Long> spacesIds = new ArrayList<Long>();
		OperateStatusResponse op = new OperateStatusResponse();
		if (result.hasErrors()) {
			op.setStatus(0l);
			op.setMessage("添加数据错误！");
			return op;
		}
		for (String space : positionv.split(",")) {
			spacesIds.add(Long.valueOf(space));
		}
		adStruc.setSpacesIds(spacesIds);
		for (String id : ids.split(",")) {
			switch (Integer.valueOf(id)) {
			case 1:
				adStruc.setRcAll(1l);
				break;
			case 13:
				adStruc.setRcGuest(1l);
				break;
			case 2:
				adStruc.setRcTopAgent(1l);
				break;
			case 3:
				adStruc.setRcOtAgent(1l);
				break;
			case 10:
				adStruc.setRcCustomer(1l);
				break;
			case 11:
				adStruc.setRcVip(1l);
				break;
			case 12:
				adStruc.setRcNonVip(1l);
				break;
			default:
				break;
			}
		}
		try {
			IUser user  = RequestContext.getCurrUser();
			if(user!=null){
				adStruc.setOperator(user.getUserName());
			}
			Response<AdStruc> response = httpClient.invokeHttpWithoutResultType(urlPath + addAdvertUrl, adStruc);
            if (response.getHead().getStatus()==100320L){
                op.setMessage("内容包含敏感词，请检查");
                op.setStatus(100320l);
            } else {
                op.setStatus(1l);
            }

		} catch (Exception e) {
			logger.error("add advert error", e);
			op.setStatus(0l);
			op.setMessage(e.getMessage());
		}
		return op;
	}

	@RequestMapping(value = { "/queryPublishAdPage", "/" })
	public String queryPublishAdPage(@ModelAttribute("adQueryRequest") AdQueryRequest adQueryRequest,
			@RequestParam(required = false) String isAll, Model model) throws Exception {
		Response<List<AdSpaceStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllAdSpaceUrl, new AdSpaceStruc(),
				new TypeReference<Response<List<AdSpaceStruc>>>() {
				});
		adQueryRequest.setPageSize(pageSize);
		if (adQueryRequest.getSpaceId() == null || adQueryRequest.getSpaceId().longValue() == -1) {
			adQueryRequest.setSpaceId(null);
		}
		if (adQueryRequest.getType() == null || adQueryRequest.getType().longValue() == -1) {
			adQueryRequest.setType(null);
		}
		if (adQueryRequest.getStatus() == null || adQueryRequest.getStatus().longValue() == -1) {
			adQueryRequest.setStatus(null);
		}
		if (adQueryRequest.getOrderById() == null || adQueryRequest.getOrderById().longValue() == -1) {
			adQueryRequest.setOrderById(null);
		}
		if (adQueryRequest.getOrderBySpaces() == null || adQueryRequest.getOrderBySpaces().longValue() == -1) {
			adQueryRequest.setOrderBySpaces(null);
		}
		if (isAll != null && isAll.equals("true")) {
			adQueryRequest.setRcAll(1l);
			adQueryRequest.setRcCustomer(1l);
			adQueryRequest.setRcGuest(1l);
			adQueryRequest.setRcNonVip(1l);
			adQueryRequest.setRcOtAgent(1l);
			adQueryRequest.setRcTopAgent(1l);
			adQueryRequest.setRcVip(1l);
		}
		int startNo = adQueryRequest.getPageNo() == 0 ? 1 : (adQueryRequest.getPageNo() - 1)
				* adQueryRequest.getPageSize() + 1;
		int endNo = adQueryRequest.getPageSize() - 1 + startNo;
		Pager pager = new Pager();
		pager.setStartNo(startNo);
		pager.setEndNo(endNo);
		Response<CountResult> response = null;
		try {
			response = httpClient.invokeHttp(urlPath + queryAdPageUrl, adQueryRequest, pager,
					new TypeReference<Response<CountResult>>() {
					});
		} catch (Exception e) {
			logger.error("queryPublishAdPage error", e);
			throw e;
		}

		ResultPager resultPage = (ResultPager) response.getBody().getPager();
		Page<Object> pageObject = new Page<Object>(adQueryRequest.getPageNo(), adQueryRequest.getPageSize(),
				resultPage.getTotal());

		if (isAll != null && isAll.equals("true")) {
			adQueryRequest.setRcAll(null);
			adQueryRequest.setRcCustomer(null);
			adQueryRequest.setRcGuest(null);
			adQueryRequest.setRcNonVip(null);
			adQueryRequest.setRcOtAgent(null);
			adQueryRequest.setRcTopAgent(null);
			adQueryRequest.setRcVip(null);
		}
		model.addAttribute("spaces", cateResp.getBody().getResult());
		model.addAttribute("adStrucs", response.getBody().getResult().getAdStrucs());
		model.addAttribute("adQueryRequest", adQueryRequest);
		model.addAttribute("totalCount", response.getBody().getPager().getTotal());
		model.addAttribute("page", pageObject);
		model.addAttribute("sumNotPass", response.getBody().getResult().getSumNotPass());
		model.addAttribute("sumReviewing", response.getBody().getResult().getSumReviewing());
		model.addAttribute("sumWait", response.getBody().getResult().getSumWait());
		model.addAttribute("imageServerVisit", imageServerVisit);
		model.addAttribute("cate2Name", "goPublish");
		model.addAttribute("userName", RequestContext.getCurrUser().getUserName());
		return "/advert/advertPublishManage";
	}

	@RequestMapping(value = "/queryReviewAdPage")
	public String queryReviewAdPage(@ModelAttribute("adQueryRequest") AdQueryRequest adQueryRequest,
			@RequestParam(required = false) String isAll, Model model) throws Exception {
		Response<List<AdSpaceStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllAdSpaceUrl, new AdSpaceStruc(),
				new TypeReference<Response<List<AdSpaceStruc>>>() {
				});
		adQueryRequest.setPageSize(pageSize);
		if (adQueryRequest.getSpaceId() == null || adQueryRequest.getSpaceId().longValue() == -1) {
			adQueryRequest.setSpaceId(null);
		}
		if (adQueryRequest.getType() == null || adQueryRequest.getType().longValue() == -1) {
			adQueryRequest.setType(null);
		}
		if (adQueryRequest.getStatus() == null || adQueryRequest.getStatus().longValue() == -1) {
			adQueryRequest.setStatus(null);
		}
		if (adQueryRequest.getOrderById() == null || adQueryRequest.getOrderById().longValue() == -1) {
			adQueryRequest.setOrderById(null);
		}
		if (adQueryRequest.getOrderBySpaces() == null || adQueryRequest.getOrderBySpaces().longValue() == -1) {
			adQueryRequest.setOrderBySpaces(null);
		}
		if (isAll != null && isAll.equals("true")) {
			adQueryRequest.setRcAll(1l);
			adQueryRequest.setRcCustomer(1l);
			adQueryRequest.setRcGuest(1l);
			adQueryRequest.setRcNonVip(1l);
			adQueryRequest.setRcOtAgent(1l);
			adQueryRequest.setRcTopAgent(1l);
			adQueryRequest.setRcVip(1l);
		}
		if(adQueryRequest.getStatus() == null){
			List<Long> allStatus = new ArrayList<Long>();
			allStatus.add(1l);
			allStatus.add(2l);
			allStatus.add(3l);
			adQueryRequest.setAllStatus(allStatus);
		}
		int startNo = adQueryRequest.getPageNo() == 0 ? 1 : (adQueryRequest.getPageNo() - 1)
				* adQueryRequest.getPageSize() + 1;
		int endNo = adQueryRequest.getPageSize() - 1 + startNo;
		Pager pager = new Pager();
		pager.setStartNo(startNo);
		pager.setEndNo(endNo);
		Response<CountResult> response = null;
		try {
			response = httpClient.invokeHttp(urlPath + queryAdPageUrl, adQueryRequest, pager,
					new TypeReference<Response<CountResult>>() {
					});
		} catch (Exception e) {
			logger.error("queryReviewAdPage error", e);
			throw e;
		}

		ResultPager resultPage = (ResultPager) response.getBody().getPager();
		Page<Object> pageObject = new Page<Object>(adQueryRequest.getPageNo(), adQueryRequest.getPageSize(),
				resultPage.getTotal());

		if (isAll != null && isAll.equals("true")) {
			adQueryRequest.setRcAll(null);
			adQueryRequest.setRcCustomer(null);
			adQueryRequest.setRcGuest(null);
			adQueryRequest.setRcNonVip(null);
			adQueryRequest.setRcOtAgent(null);
			adQueryRequest.setRcTopAgent(null);
			adQueryRequest.setRcVip(null);
		}
		model.addAttribute("spaces", cateResp.getBody().getResult());
		model.addAttribute("adStrucs", response.getBody().getResult().getAdStrucs());
		model.addAttribute("adQueryRequest", adQueryRequest);
		model.addAttribute("totalCount", response.getBody().getPager().getTotal());
		model.addAttribute("page", pageObject);
		model.addAttribute("sumNotPass", response.getBody().getResult().getSumNotPass());
		model.addAttribute("sumReviewing", response.getBody().getResult().getSumReviewing());
		model.addAttribute("sumWait", response.getBody().getResult().getSumWait());
		model.addAttribute("imageServerVisit", imageServerVisit);
		model.addAttribute("cate2Name", "goReview");
		return "/advert/advertReviewManage";
	}

	@RequestMapping(value = "/deleteAdvert")
	@ResponseBody
	public OperateStatusResponse deleteAdvert(@RequestParam("ids") String ids) {
		List<Long> spacesIds = new ArrayList<Long>();
		OperateStatusResponse op = new OperateStatusResponse();
		for (String space : ids.split(",")) {
			spacesIds.add(Long.valueOf(space));
		}
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + deleteAdvertUrl, spacesIds);
			op.setStatus(1l);
		} catch (Exception e) {
			op.setStatus(0l);
			op.setMessage("删除失败");
			logger.error("deleteAdvert error", e);
		}
		return op;
	}
}
