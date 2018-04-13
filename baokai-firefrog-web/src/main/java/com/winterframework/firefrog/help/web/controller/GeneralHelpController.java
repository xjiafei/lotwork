/**   
* @Title: GeneralHelpController.java 
* @Package com.winterframework.firefrog.help.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-11 上午11:08:06 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpDetailRequest;
import com.winterframework.firefrog.help.web.dto.HelpFeedBackRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller("generalHelpController")
@RequestMapping(value = "/help")
public class GeneralHelpController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralHelpController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelpUrl;

	@PropertyConfig(value = "url.help.queryHelpDetail")
	private String queryHelpDetailUrl;

	@PropertyConfig(value = "url.help.createHelpFeedback")
	private String createHelpFeedbackUrl;

	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	private String getUrl(String path) {
		return serverPath + path;
	}

	/**
	 * 
	* @Title: queryGeneralHelpList 
	* @Description: 查询普通帮助列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGeneralHelp")
	public String queryGeneralHelpList(@ModelAttribute("page") PageRequest<HelpQueryRequest> page,
			@RequestParam(value="cateId2",required=false) Long cateId2, @RequestParam(value="cate2Name",required=false) String name,
			@RequestParam(value="orderBy",required=false) String orderBy, Model model) throws Exception {
		logger.info("queryGeneralHelp start");
		HelpQueryRequest queryDTO = new HelpQueryRequest();
		page.setPageSize(pageSize);
		Response<List<HelpStruc>> response = null;
		int startNo = page.getPageNo() == 0 ? 1 : (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endNo = page.getPageSize() - 1 + startNo;
		try {
			queryDTO.setCateId2(cateId2);
			queryDTO.setType(0L);
			queryDTO.setCateId(-1l);
			queryDTO.setIsRec(-1l);
			if (StringUtils.isNotEmpty(orderBy)) {
				queryDTO.setOrderBy(orderBy);
			}
			Pager pager = new Pager();
			pager.setStartNo(startNo);
			pager.setEndNo(endNo);
			response = httpClient.invokeHttp(getUrl(queryHelpUrl), queryDTO, pager,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			Response<List<CateStruc>> resp = httpClient.invokeHttp(getUrl(queryCategoryUrl),
					new CateStrucQueryRequest(), new TypeReference<Response<List<CateStruc>>>() {
					});
			model.addAttribute("cateList", resp.getBody().getResult());
			logger.info("queryGeneralHelp end");
		} catch (Exception e) {
			logger.error("query general help is error.", e);
			throw e;
		}
		ResultPager resultPage = response.getBody().getPager();
		Page<Object> pageObject = new Page<Object>(page.getPageNo(), page.getPageSize(), resultPage.getTotal());
		model.addAttribute("helps", response.getBody().getResult());
		model.addAttribute("totalCount", response.getBody().getPager().getTotal());
		model.addAttribute("page", pageObject);
		model.addAttribute("query", queryDTO);
		model.addAttribute("cate2Name", new java.net.URLDecoder().decode(name, "utf-8"));
		logger.info("queryGeneralHelp end");
		return "/help/generalHelpList";
	}

	/**
	 * 
	* @Title: queryGeneralHelpDetail 
	* @Description: 查询帮助详细信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGeneralDetail")
	public String queryGeneralHelpDetail(@RequestParam("helpId") Long id, Model model) throws Exception {
		logger.info("queryGeneralHelpDetail start");
		Response<HelpStruc> response = null;
		try {
			HelpDetailRequest queryDTO = new HelpDetailRequest();
			queryDTO.setHelpId(id);
			response = httpClient.invokeHttp(getUrl(queryHelpDetailUrl), queryDTO, HelpStruc.class);
			Response<List<CateStruc>> resp = httpClient.invokeHttp(getUrl(queryCategoryUrl),
					new CateStrucQueryRequest(), new TypeReference<Response<List<CateStruc>>>() {
					});
			model.addAttribute("cateList", resp.getBody().getResult());
			logger.info("queryGeneralDetail end");
		} catch (Exception e) {
			logger.error("query general detail is error.", e);
			throw e;
		}
		IUser currUser = RequestContext.getCurrUser();
		model.addAttribute("currUser", currUser);
		model.addAttribute("help", response.getBody().getResult());
		logger.info("queryGeneralHelpDetail end");
		return "/help/generalHelpDetails";
	}

	/**
	 * 
	* @Title: addHelpFeedBack 
	* @Description: 新增帮助反馈
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/addFeedBack")
	public void addHelpFeedBack(@ModelAttribute("feedBack") HelpFeedBackRequest feedBack) throws Exception {
		logger.info("addHelpFeedBack start");
		try {
			httpClient.invokeHttpWithoutResultType(getUrl(createHelpFeedbackUrl), feedBack, RequestContext
					.getCurrUser().getId(), RequestContext.getCurrUser().getUserName());
		} catch (Exception e) {
			logger.error("save feedBack is error.", e);
			throw e;
		}
		logger.info("addHelpFeedBack end");
	}
}
