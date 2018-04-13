package com.winterframework.firefrog.advert.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdNoticeDetailRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeListStruc;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.advert.web.dto.AdNoticeStruc;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("AdNoticeFrontController")
@RequestMapping(value = "/ad")
public class AdNoticeFrontController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.advert.queryNoticeDetail")
	private String queryNoticeDetail;

	@PropertyConfig(value = "url.advert.queryNoticeList")
	private String queryNoticeList;

	@RequestMapping(value = "/adNoticeQuery")
	public ModelAndView queryNotice(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("advert/adNoticeQuery");
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

	@RequestMapping(value = "/noticeList")
	public ModelAndView noticeList(AdNoticeSearchDto search, Model model) throws Exception {
		ModelAndView mav = new ModelAndView("advert/noticeList");
		//Integer userLvl = 1;
		try {
			UserStrucResponse usr = (UserStrucResponse) RequestContext.getCurrUser();
			//userLvl = usr.getUserLvl();
			search.setFrontCall(1l);
			search.setUserId(usr.getId());
			if(search.getNoticeLevel()==4 && usr.getVipLvl()<1){
				search.setNoticeLevel(0L);
			}
			/*if(userLvl==-1){
				search.setRcCustomer(1L);
			}else if(userLvl==0){
				search.setRcTopAgent(1L);
			}else{
				search.setRcOtAgent(1L);
			}
			if(usr.getVipLvl()>0){
				search.setRcVip(1l);
			}else{
				search.setRcNonVip(1l);
			}*/
		} catch (Exception e) {

		}
		Pager pager = new Pager();
		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}
		if (search.getOrderBy() == null) {
			search.setOrderBy("GMT_CREATED DESC");

		}
		if (search.getNoticeLevel() == null)
		{
			search.setNoticeLevel(0L);
		}
		/*search.setPeriodStatus(1L);
		search.setStatus(3L);*/
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<AdNoticeListStruc> resp = httpClient.invokeHttp(urlPath + queryNoticeList, search, pager,
				new TypeReference<Response<AdNoticeListStruc>>() {
				});
		AdNoticeListStruc result = resp.getBody().getResult();
		List<AdNoticeStruc> list = result.getNoticeList();
		Iterator<AdNoticeStruc> its = list.iterator();
		while (its.hasNext()) {
			AdNoticeStruc adNoticeStruc = its.next();
			adNoticeStruc.setGmtEffectBeginStr(DateUtils.format(adNoticeStruc.getGmtEffectBegin(),
					DateUtils.DATETIME_FORMAT_PATTERN));
		}
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", list);
		mav.addObject ("noticelevel" , search.getNoticeLevel());
		return mav;
	}
}
