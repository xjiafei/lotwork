package com.winterframework.firefrog.acl.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.winterframework.firefrog.session.interceptor.vo.AclUserStruc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.acl.web.dto.AclLogDetailRequest;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryView;
import com.winterframework.firefrog.acl.web.dto.AclOperateLogStruc;
import com.winterframework.firefrog.acl.web.dto.AclOperateLogView;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("AclOperateLogController")
@RequestMapping(value = "/aclAdmin")
public class AclOperateLogController {

	@PropertyConfig(value = "url.front.domain")
	private String selfUrl;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.acl.queryLog")
	private String queryLog;

	@PropertyConfig(value = "url.acl.detailLog")
	private String detailLog;

	@PropertyConfig(value = "url.acl.createLog")
	private String createLog;
	
	@RequestMapping(value = "/goLog")
	public ModelAndView goLog() throws Exception {
		AclLogQueryView search = new AclLogQueryView();
		search.setAccount("");
		search.setEndTime("");
		search.setStartTime("");
		search.setPageNo(1L);
		search.setIp("");
		search.setSearchType(0);
		search.setSearchValue("");
		search.setSeqNo("");
		
		addSucceedAclLog("进入查看日志界面","/goLog");
		return searchLog(search);
	}

	@RequestMapping(value = "/detailLog")
	public ModelAndView detailLog(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("acl/operateLogDetail");
		AclLogDetailRequest detailReq = new AclLogDetailRequest();
		detailReq.setId(id);
		Response<AclOperateLogStruc> resp = httpClient.invokeHttp(urlPath + detailLog, detailReq,
				new TypeReference<Response<AclOperateLogStruc>>() {
				});
		if (resp != null && resp.getBody() != null) {
			AclOperateLogStruc log = resp.getBody().getResult();
			AclOperateLogView view = new AclOperateLogView();
			view.setAccount(log.getAccount());
			view.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(log.getCreateTime()));
			String detail = "";
			switch (log.getDetail().intValue()) {
			case 0:
				detail = "详情";
				break;
			case 1:
				detail = "成功";
				break;
			case 2:
				detail = "失败";
				break;
			case 3:
				detail = "无权限";
				break;
			}
			view.setDetail("[ " + log.getAction() + " ] " + detail);
			view.setId(log.getId());
			view.setIp(log.getIp());
			view.setUrl(log.getUrl());
			mav.addObject("acl_no","goLog");
			mav.addObject("detail", view);
		}
		
		return mav;
	}

	@RequestMapping(value = "/searchLog")
	public ModelAndView searchLog(@Valid AclLogQueryView search) throws Exception {
		ModelAndView mav = new ModelAndView("acl/operateLog");
		Pager pager = new Pager();
		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);

		AclLogQueryRequest searchDo = new AclLogQueryRequest();
		//根据查询类型初始化为账号或者序号属性
		processAccountOrSeqNo(searchDo,search);
		if (search.getEndTime()!=null && !search.getEndTime().equals("")) {
			searchDo.setEndTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(search.getEndTime()));
		}
		if (search.getStartTime()!=null && !search.getStartTime().equals("")) {
			searchDo.setStartTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(search.getStartTime()));
		}
		if (search.getIp()!=null && !search.getIp().equals("")) {
			searchDo.setIp(IPConverter.ipToLong(search.getIp()));
		}
        searchDo.setGroupId(((AclUserStruc) RequestContext.getCurrUser()).getGroupId());
		searchDo.setPageNo(search.getPageNo());
        searchDo.setUserId(RequestContext.getCurrUser().getId());
        if (search.getSearchType() != null) {
            if (search.getSearchType() == 0) {
                searchDo.setAccount(search.getSearchValue());
            }
            if (search.getSearchType() == 1) {
                searchDo.setSeqNo(search.getSeqNo());
            }
        } else {
            searchDo.setAccount(search.getAccount());
        }
		Response<List<AclOperateLogStruc>> resp = httpClient.invokeHttp(urlPath + queryLog, searchDo, pager,
				new TypeReference<Response<List<AclOperateLogStruc>>>() {
				});

		List<AclOperateLogView> viewList = new ArrayList<AclOperateLogView>();
		AclOperateLogView view = null;
		for (AclOperateLogStruc logStruc : resp.getBody().getResult()) {
			view = new AclOperateLogView();
			view.setAccount(logStruc.getAccount());
			view.setDetail("");
			view.setId(logStruc.getId());
			view.setIp(logStruc.getIp());
			view.setUrl(logStruc.getUrl());
			String detail = "";
			switch (logStruc.getDetail().intValue()) {
			case 0:
				detail = "详情";
				break;
			case 1:
				detail = "成功";
				break;
			case 2:
				detail = "失败";
				break;
			case 3:
				detail = "无权限";
				break;
			}

			view.setDetail("[ " + logStruc.getAction() + " ] " + detail);
			view.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(logStruc.getCreateTime()));
			viewList.add(view);
		}

		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", viewList);
		mav.addObject("search", search);
		mav.addObject("acl_no","goLog");
		return mav;
	}

	/** 
	 * 处理查询账号或者序列号
	*/
	private void processAccountOrSeqNo(AclLogQueryRequest searchDo, AclLogQueryView search) {
		if (search.getSearchValue() != null && !search.getSearchValue().equals("")) {
			switch (search.getSearchType()) {
			case 1:
				searchDo.setSeqNo(search.getSearchValue());
				break;
			case 0:
			default:
				searchDo.setAccount(search.getSearchValue());
				break;
			}
		}
	}
	
	private void addSucceedAclLog(String action, String url) throws Exception{		
		AclOperateLogStruc o = new AclOperateLogStruc();
		o.setAccount(RequestContext.getCurrUser().getUserName());
		o.setAction(action);
		o.setIp(RequestContext.get().ip());
		o.setUrl(selfUrl + "/aclAdmin" + url);
		o.setCreateTime(new Date());
		o.setDetail(1L);
		httpClient.invokeHttp(urlPath + createLog, o,new TypeReference<Response<Object>>(){});
	}
}
