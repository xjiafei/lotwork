package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdNoticeAuditRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeDelRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeDetailRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeListStruc;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.advert.web.dto.AdNoticeStruc;
import com.winterframework.firefrog.advert.web.dto.AdNoticeUpdateResult;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller("AdNoticeController")
@RequestMapping(value = "/adAdmin")
public class AdNoticeController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.advert.createNotice")
	private String createNotice;

	@PropertyConfig(value = "url.advert.modifyNotice")
	private String modifyNotice;

	@PropertyConfig(value = "url.advert.queryNoticeList")
	private String queryNoticeList;

	@PropertyConfig(value = "url.advert.queryNoticeDetail")
	private String queryNoticeDetail;

	@PropertyConfig(value = "url.advert.deleteNotice")
	private String deleteNotice;

	@PropertyConfig(value = "url.advert.auditNotice")
	private String auditNotice;

	@RequestMapping(value = "/goCreateNotice")
	public ModelAndView goCreateNotice() throws Exception {
		ModelAndView mav = new ModelAndView("advert/adNoticeCreate");
		mav.addObject("cate2Name", "goCreateNotice");
		return mav;
	}

	@RequestMapping(value = "/goModifyNotice")
	public ModelAndView goModifyNotice(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("advert/adNoticeModify");
		AdNoticeDetailRequest detailReq = new AdNoticeDetailRequest();
		detailReq.setId(id);
		Response<AdNoticeStruc> resp = httpClient.invokeHttp(urlPath + queryNoticeDetail, detailReq,
				new TypeReference<Response<AdNoticeStruc>>() {
				});
		AdNoticeStruc notice = resp.getBody().getResult();
		notice.setGmtEffectBeginStr(DateUtils.format(notice.getGmtEffectBegin(), DateUtils.DATETIME_FORMAT_PATTERN));
		notice.setGmtEffectEndStr(DateUtils.format(notice.getGmtEffectEnd(), DateUtils.DATETIME_FORMAT_PATTERN));
		mav.addObject("notice", notice);
		return mav;
	}

	@RequestMapping(value = "/goNoticeListPublish")
	public ModelAndView goNoticeListPublish() throws Exception {
		AdNoticeSearchDto search = new AdNoticeSearchDto();
		search.setStatus(-1L);
		search.setPeriodStatus(-1L);
		search.setType(-1L);
		return searchList(search);
	}

	@RequestMapping(value = "/goNoticeListAudit")
	public ModelAndView goNoticeListAudit() throws Exception {
		AdNoticeSearchDto search = new AdNoticeSearchDto();
		search.setStatus(-1L);
		search.setPeriodStatus(-1L);
		search.setType(-1L);
		search.setIsAudit(1L);
		return searchList(search);
	}

	@RequestMapping(value = "/searchNoticeList")
	public ModelAndView searchList(AdNoticeSearchDto search) throws Exception {
		ModelAndView mav = null;
		if (search.getIsAudit() != null && search.getIsAudit() == 1L) {
			mav = new ModelAndView("advert/adNoticeListAudit");
			mav.addObject("cate2Name", "goNoticeListAudit");
		} else {
			mav = new ModelAndView("advert/adNoticeListPublish");
			mav.addObject("cate2Name", "goNoticeListPublish");
		}
		search.setType(search.getType() == -1L ? null : search.getType());
		search.setPeriodStatus(search.getPeriodStatus() == -1L ? null : search.getPeriodStatus());
		search.setStatus(search.getStatus() == null || search.getStatus() == -1L ? null : search.getStatus());
		if (search.getRcCustomer() == null && search.getRcNonVip() == null && search.getRcOtAgent() == null
				&& search.getRcTopAgent() == null && search.getRcVip() == null) {
			search.setRcAll(1L);
		}
		Pager pager = new Pager();
		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}
		if (search.getOrderBy() == null) {
			search.setOrderBy("GMT_CREATED DESC");
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<AdNoticeListStruc> resp = httpClient.invokeHttp(urlPath + queryNoticeList, search, pager,
				new TypeReference<Response<AdNoticeListStruc>>() {
				});
		AdNoticeListStruc result = resp.getBody().getResult();
		List<AdNoticeStruc> list = result.getNoticeList();
		for (AdNoticeStruc struc : list) {
			struc.setGmtEffectBeginStr(DateUtils.format(struc.getGmtEffectBegin(), DateUtils.DATETIME_FORMAT_PATTERN));
			struc.setGmtEffectEndStr(DateUtils.format(struc.getGmtEffectEnd(), DateUtils.DATETIME_FORMAT_PATTERN));
			struc.setTypeStr(struc.getType() == 1L ? "普通" : "紧急");
			StringBuilder groups = new StringBuilder(1024);
			if (struc.getRcAll() == 1L) {
				groups.append("全部用户，");
			} else {
				groups.append(struc.getRcTopAgent() == 1L ? "总代理，" : "");
				groups.append(struc.getRcOtAgent() == 1L ? "其他代理，" : "");
				groups.append(struc.getRcCustomer() == 1L ? "玩家，" : "");
				groups.append(struc.getRcVip() == 1L ? "VIP，" : "");
				groups.append(struc.getRcNonVip() == 1L ? "非VIP，" : "");
			}
			struc.setGroups(groups.deleteCharAt(groups.length() - 1).toString());
		}
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", list);
		mav.addObject("search", search);
		mav.addObject("sumNew", result.getSumNew());
		mav.addObject("sumAudit", result.getSumAudit());
		mav.addObject("sumRefuse", result.getSumRefuse());
		return mav;
	}

	@RequestMapping(value = "/createNotice")
	@ResponseBody
	public Object createNotice(@Valid AdNoticeStruc notice, Long isPush) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		if (isPush != null && isPush == 1L) {
			notice.setShowPages("agent_first_page");
		}
		//设置用户
		IUser user = RequestContext.getCurrUser();
		if (user != null) {
			notice.setOperator(user.getUserName());
		}
		try {
			notice.setGmtEffectBegin(DateUtils.parse(notice.getGmtEffectBeginStr(), DateUtils.DATETIME_FORMAT_PATTERN));
			if (!"".equals(notice.getGmtEffectEndStr())) {
				notice.setGmtEffectEnd(DateUtils.parse(notice.getGmtEffectEndStr(), DateUtils.DATETIME_FORMAT_PATTERN));
			}
			Response<AdNoticeUpdateResult> result = httpClient.invokeHttp(urlPath + createNotice,notice, new TypeReference<Response<AdNoticeUpdateResult>>(){});
			switch(result.getBody().getResult().getStatus()){
				case AdNoticeUpdateResult.SUCCESS:
					response.setStatus(1L);
					break;
				case AdNoticeUpdateResult.FAIL:
					response.setStatus(0L);
					response.setMessage("系统异常,请稍候重试或连络技术处理");
					break;
				case AdNoticeUpdateResult.CONTENT_TOO_LONG:
					response.setStatus(0L);
					response.setMessage("输入内容过多，存档失败。");
					break;
			}
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/modifyNotice")
	@ResponseBody
	public Object modifyNotice(@Valid AdNoticeStruc notice, Long isPush) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		if (isPush != null && isPush == 1L) {
			notice.setShowPages("agent_first_page");
		} else if (notice.getType() == 1L) {
			notice.setShowPages("empty");
		}
		try {
			notice.setGmtEffectBegin(DateUtils.parse(notice.getGmtEffectBeginStr(), DateUtils.DATETIME_FORMAT_PATTERN));
			if (!"".equals(notice.getGmtEffectEndStr())) {
				notice.setGmtEffectEnd(DateUtils.parse(notice.getGmtEffectEndStr(), DateUtils.DATETIME_FORMAT_PATTERN));
			}
			httpClient.invokeHttpWithoutResultType(urlPath + modifyNotice, notice);
			response.setStatus(1L);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/deleteNotice")
	@ResponseBody
	public Object deleteNotice(String ids) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		List<Long> idList = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			idList.add(Long.parseLong(id));
		}
		AdNoticeDelRequest delReq = new AdNoticeDelRequest();
		delReq.setIds(idList);
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + deleteNotice, delReq);
			response.setStatus(1L);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/applyNotice")
	@ResponseBody
	public Object applyNotice(String ids) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		AdNoticeStruc notice = null;
		try {
			for (String id : ids.split(",")) {
				notice = new AdNoticeStruc();
				notice.setId(Long.parseLong(id));
				notice.setCreateType(1L);
				httpClient.invokeHttpWithoutResultType(urlPath + modifyNotice, notice);
			}
			response.setStatus(1L);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/auditNotice")
	@ResponseBody
	public Object auditNotice(String ids, Long status, String memo) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		List<Long> idList = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			idList.add(Long.parseLong(id));
		}
		AdNoticeAuditRequest audit = new AdNoticeAuditRequest();
		audit.setIds(idList);
		audit.setStatus(status);
		audit.setMemo(memo);
		IUser user = RequestContext.getCurrUser();
		if (user != null) {
			audit.setApprover(user.getUserName());
		}
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + auditNotice, audit);
			response.setStatus(1L);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

}
