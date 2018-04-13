package com.winterframework.firefrog.advert.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdTopicCategoryStruc;
import com.winterframework.firefrog.advert.web.dto.AdTopicDetailRequest;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.firefrog.advert.web.dto.AdTopicStruc;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller("AdTopicController")
@RequestMapping(value = "/adAdmin")
public class AdTopicController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.advert.createAdTopic")
	private String createAdTopic;

	@PropertyConfig(value = "url.advert.detailTopic")
	private String detailTopic;

	@PropertyConfig(value = "url.advert.modifyTopic")
	private String modifyAdTopic;

	@PropertyConfig(value = "url.advert.queryAllTopicCategory")
	private String queryAllTopicCategory;

	@PropertyConfig(value = "url.advert.queryTopicList")
	private String queryTopicList;

	@RequestMapping(value = "/goCreateTopic")
	public ModelAndView goCreateTopic() throws Exception {
		ModelAndView mav = new ModelAndView("advert/adTopicCreate");
		Response<List<AdTopicCategoryStruc>> resp = httpClient.invokeHttp(urlPath + queryAllTopicCategory,
				new TypeReference<Response<List<AdTopicCategoryStruc>>>() {
				});
		mav.addObject("cateList", resp.getBody().getResult());
		mav.addObject("cate2Name", "goCreateTopic");
		return mav;
	}

	@RequestMapping(value = "/goTopicManager")
	public ModelAndView goTopicManager() throws Exception {
		AdTopicSearch search = new AdTopicSearch();
		search.setCateId(-1L);
		return queryTopicList(search, 1L);
	}

	@RequestMapping(value = "/goTopicView")
	public ModelAndView goTopicView() throws Exception {
		AdTopicSearch search = new AdTopicSearch();
		search.setCateId(-1L);
		return queryTopicList(search, 2L);
	}

	@RequestMapping(value = "/goModifyTopic")
	public ModelAndView goModifyTopic(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("advert/adTopicModify");
		Response<List<AdTopicCategoryStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllTopicCategory,
				new TypeReference<Response<List<AdTopicCategoryStruc>>>() {
				});
		AdTopicDetailRequest detalReq = new AdTopicDetailRequest();
		detalReq.setId(id);
		Response<AdTopicStruc> resp = httpClient.invokeHttp(urlPath + detailTopic, detalReq,
				new TypeReference<Response<AdTopicStruc>>() {
				});
		AdTopicStruc topic = resp.getBody().getResult();
		mav.addObject("cateList", cateResp.getBody().getResult());
		mav.addObject("topic", topic);
		mav.addObject("url0", topic.getUrls().get(0));
		mav.addObject("linkNum", topic.getUrls().size());
		return mav;
	}

	
	@RequestMapping(value = "/haveTopicName")
	@ResponseBody
	public OperateStatusResponse haveTopicName(String title, int cateId) throws Exception {
		OperateStatusResponse res = new OperateStatusResponse();
		if(cateId==-2){
			Response<List<AdTopicCategoryStruc>> resp = httpClient.invokeHttp(urlPath + queryAllTopicCategory,
					new TypeReference<Response<List<AdTopicCategoryStruc>>>() {
					});
			if( resp.getBody()!=null && resp.getBody().getResult()!=null ){
				List<AdTopicCategoryStruc> list = resp.getBody().getResult();
				for (AdTopicCategoryStruc adTopicCategoryStruc : list) {
					if(adTopicCategoryStruc.getName().equals(title)){
						res.setMessage("yes");
						res.setStatus(1);
						return res;
						//return "yes";
					}
				}
			}
		}else{
			AdTopicSearch search = new AdTopicSearch();
			search.setTitle(title);
			Pager pager = new Pager();
			if (search.getPageNo() == null) {
				search.setPageNo(1L);
			}
			int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
			pager.setStartNo(startNo);
			pager.setEndNo(pageSize + startNo - 1);
			Response<List<AdTopicStruc>> resp = httpClient.invokeHttp(urlPath + queryTopicList, search, pager,
					new TypeReference<Response<List<AdTopicStruc>>>() {
					});
			if(resp.getBody()==null || resp.getBody().getResult() ==null || resp.getBody().getResult().isEmpty()){
				res.setStatus(0);
				return res;
				//return "no";
			}else{
				res.setStatus(1);
				return res;
				//return "yes";
			}
		}
		res.setStatus(0);
		return res;
		//return "no";
	}
	
	
	
	@RequestMapping(value = "/createTopic")
	public ModelAndView createTopic(@Valid AdTopicStruc topic, HttpServletRequest request) throws Exception {
		String link = null;
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			link = (String) request.getParameter("link" + i);
			if (link != null) {
				urls.add(link);
			}
		}
		topic.setUrls(urls);
		IUser user = RequestContext.getCurrUser();
		if (user != null) {
			topic.setOperator(user.getUserName());
		} else {
			topic.setOperator("");
		}
		httpClient.invokeHttpWithoutResultType(urlPath + createAdTopic, topic);
		return goTopicManager();
	}

	@RequestMapping(value = "/modifyTopic")
	public ModelAndView modifyTopic(@Valid AdTopicStruc topic, HttpServletRequest request) throws Exception {
		String link = null;
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			link = (String) request.getParameter("link" + i);
			if (link != null) {
				urls.add(link);
			}
		}
		topic.setUrls(urls);
		topic.setOperator(RequestContext.getCurrUser().getUserName());
		httpClient.invokeHttpWithoutResultType(urlPath + modifyAdTopic, topic);
		return goTopicManager();
	}

	@RequestMapping(value = "/queryTopicList")
	public ModelAndView queryTopicList(AdTopicSearch search, Long queryType) throws Exception {
		ModelAndView mav = null;
		if (queryType == 2L) {
			mav = new ModelAndView("advert/adTopicView");
			mav.addObject("cate2Name", "goTopicView");
		} else {
			mav = new ModelAndView("advert/adTopicManager");
			mav.addObject("cate2Name", "goTopicManager");
		}
		search.setCateId(search.getCateId() == -1L ? null : search.getCateId());
		Pager pager = new Pager();
		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<List<AdTopicStruc>> resp = httpClient.invokeHttp(urlPath + queryTopicList, search, pager,
				new TypeReference<Response<List<AdTopicStruc>>>() {
				});
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		List<AdTopicStruc> list = resp.getBody().getResult();
		Response<List<AdTopicCategoryStruc>> cateResp = httpClient.invokeHttp(urlPath + queryAllTopicCategory,
				new TypeReference<Response<List<AdTopicCategoryStruc>>>() {
				});
		List<AdTopicCategoryStruc> cateList = cateResp.getBody().getResult();
		for (AdTopicStruc struc : list) {
			struc.setGmtCreatedStr(DateUtils.format(struc.getGmtCreated(), DateUtils.DATETIME_FORMAT_PATTERN));
			for (AdTopicCategoryStruc cate : cateList) {
				if (cate.getId().equals(struc.getCateId())) {
					struc.setCateName(cate.getName());
				}
			}
		}
		mav.addObject("cateList", cateList);
		mav.addObject("list", list);
		mav.addObject("search", search);
		mav.addObject("page", page);

		return mav;
	}

}
