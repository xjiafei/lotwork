package com.winterframework.firefrog.help.web.controller;

import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.web.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.help.web.dto.KeywordStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: HelpIndexController 
* @Description: 帮助首页控制类
* @author 你的名字 
* @date 2013-9-24 下午1:47:34 
*
 */
@Controller("help")
public class HelpIndexController {

	private Logger logger = LoggerFactory.getLogger(HelpIndexController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;

	@PropertyConfig(value = "url.help.queryHotWord")
	private String queryHotWordUrl;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelpUrl;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;
	@PropertyConfig(value = "url.imageserver.visit")
	private String urlViewPic;

    @PropertyConfig(value = "url.profile.queryUserSecurityInfo",required=false)
    private String queryUserSecurityInfo;
	/**
	 * 
	* @Title: goIndex 
	* @Description: 初始化首页
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/help/goIndex")
	public ModelAndView goIndex() throws Exception {
		ModelAndView mav = new ModelAndView("help/index");
		try {
			Response<List<CateStruc>> resp = httpClient.invokeHttp(urlPath + queryCategoryUrl, createEmptyCateReq(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = resp.getBody().getResult();
			HelpQueryRequest questionReq = new HelpQueryRequest();
			questionReq.setCateId(-1L);
			questionReq.setCateId2(-1L);
			questionReq.setIsRec(1L);
			questionReq.setType(0L);
			questionReq.setOrderBy("RECOMMAND_TIME DESC NULLS LAST");
			Response<List<HelpStruc>> hotQuestionResp = httpClient.invokeHttp(urlPath + queryHelpUrl, questionReq,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			HelpQueryRequest lotteryReq = new HelpQueryRequest();
			lotteryReq.setCateId(-1L);
			lotteryReq.setCateId2(-1L);
			lotteryReq.setIsRec(1L);
			lotteryReq.setType(1L);
			lotteryReq.setIsSearchHotlottery(1);
			lotteryReq.setOrderBy("NO DESC,GMT_MODIFIED DESC");
			Response<List<HelpStruc>> hotLotteryResp = httpClient.invokeHttp(urlPath + queryHelpUrl, lotteryReq,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			Response<List<KeywordStruc>> keywordList = httpClient.invokeHttp(urlPath + queryHotWordUrl,
					new TypeReference<Response<List<KeywordStruc>>>() {
					});
			mav.addObject("cateList", cateList);
            mav.addObject("user", (UserStrucResponse)(RequestContext.getCurrUser()));
			if (hotQuestionResp.getBody() != null) {
				mav.addObject("hotQuestion", hotQuestionResp.getBody().getResult());
			}
			if (hotLotteryResp.getBody() != null) {
				mav.addObject("hotLottery", hotLotteryResp.getBody().getResult());
			}
			mav.addObject("keywordList", keywordList.getBody().getResult());
		} catch (Exception e) {
			logger.error("goIndex error.", e);
			throw e;
		}
		
		mav.addObject("urlViewPic", urlViewPic);
		return mav;
	}

	/**
	 * 
	* @Title: queryListByKey 
	* @Description: 根据关键字查询帮助
	* @param key
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = {"/help/queryListByKey", "/helpAdmin/queryListByKey"})
	public ModelAndView queryListByKey(String key, Long pageNo) throws Exception {
		ModelAndView mav = new ModelAndView("help/helpQueryList");
		HelpQueryRequest req = new HelpQueryRequest();
		req.setKeywords(key);
		req.setCateId(-1L);
		req.setCateId2(-1L);
		req.setIsRec(-1L);
		if (pageNo == null) {
			pageNo = 1l;
		}
		Pager pager = new Pager();
		int startNo = (int) ((pageNo - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);

		try {
			Response<List<CateStruc>> resp = httpClient.invokeHttp(urlPath + queryCategoryUrl, createEmptyCateReq(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = resp.getBody().getResult();
			Response<List<KeywordStruc>> keywordResp = httpClient.invokeHttp(urlPath + queryHotWordUrl,
					new TypeReference<Response<List<KeywordStruc>>>() {
					});

			Response<List<HelpStruc>> helpResp = httpClient.invokeHttp(urlPath + queryHelpUrl, req, pager,
					new TypeReference<Response<List<HelpStruc>>>() {
					});

			List<HelpStruc> helpList = helpResp.getBody().getResult();
			for (HelpStruc help : helpList) {
				help.setTitle(highlightText(help.getTitle(), key));
				help.setPreface(highlightText(help.getPreface(), key));
			}

			ResultPager resultPage = helpResp.getBody().getPager();
			int pageNum = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
			Page<Object> page = new Page<Object>(pageNum, pageSize.intValue(), resultPage.getTotal());
			mav.addObject("page", page);

			mav.addObject("helpList", helpList);
			mav.addObject("cateList", cateList);
			mav.addObject("keywordList", keywordResp.getBody().getResult());
			mav.addObject("key", key);
		} catch (Exception e) {
			logger.error("queryListByKey error.", e);
			throw e;
		}
		return mav;
	}

	private Request<CateStrucQueryRequest> createEmptyCateReq() {
		Request<CateStrucQueryRequest> req = new Request<CateStrucQueryRequest>();
		RequestBody<CateStrucQueryRequest> body = new RequestBody<CateStrucQueryRequest>();
		body.setParam(new CateStrucQueryRequest());
		req.setBody(body);
		return req;
	}

	private String highlightText(String text, String key) {
		if(text==null){
			return null;
		}
		return text.replaceAll(key, "<span class=\"help-search-highlight\">" + key + "</span>");
	}
}
