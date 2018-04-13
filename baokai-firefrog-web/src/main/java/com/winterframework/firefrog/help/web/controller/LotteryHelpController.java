package com.winterframework.firefrog.help.web.controller;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpDetailRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.help.web.dto.LotteryCateStruc;
import com.winterframework.firefrog.help.web.dto.LotteryContentStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller("lotteryHelpController")
@RequestMapping(value = "/help")
public class LotteryHelpController {

	private static final Logger logger = LoggerFactory.getLogger(LotteryHelpController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelpUrl;

	@PropertyConfig(value = "url.help.queryHelpDetail")
	private String queryHelpDetailUrl;
	
	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.help.queryLotteryKnowledgeCategory")
	private String queryLotteryKnowledgeCategoryUrl;

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
	@RequestMapping(value = "/queryLotterylHelp")
	public String queryLotteryHelpList(@RequestParam("cateId2") Long cateId2, @RequestParam("cate2Name") String name,
			Model model) throws Exception {
		logger.info("queryLotteryHelp start");
		HelpQueryRequest queryDTO = new HelpQueryRequest();
		Response<List<HelpStruc>> response = null;
		try {
			queryDTO.setCateId2(cateId2);
			queryDTO.setType(1l);
			queryDTO.setIsRec(-1l);
			queryDTO.setCateId(-1l);
			queryDTO.setOrderBy("NO DESC,GMT_MODIFIED DESC");
			response = httpClient.invokeHttp(getUrl(queryHelpUrl), queryDTO, new TypeReference<Response<List<HelpStruc>>>() {});
			Response<List<CateStruc>> resp = httpClient.invokeHttp(getUrl(queryCategoryUrl), new CateStrucQueryRequest(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			model.addAttribute("cateList",resp.getBody().getResult());
			logger.info("queryGeneralHelp end");
		} catch (Exception e) {
			logger.error("query general help is error.", e);
			throw e;
		}
		model.addAttribute("helps", response.getBody().getResult());
		model.addAttribute("query", queryDTO);
		model.addAttribute("cate2Name", new java.net.URLDecoder().decode(name, "utf-8"));
		return "/help/lotteryHelpList";
	}

	/**
	 * 
	* @Title: queryGeneralHelpDetail 
	* @Description: 查询帮助详细信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryLotteryDetail")
	public String queryLotteryHelpDetail(@RequestParam("helpId") Long id, @RequestParam(required = false) String cate2Name,
			Model model) throws Exception {
		Response<HelpStruc> response = null;
		try {
			HelpDetailRequest queryDTO = new HelpDetailRequest();
			queryDTO.setHelpId(id);
			response = httpClient.invokeHttp(getUrl(queryHelpDetailUrl), queryDTO, HelpStruc.class);
			List<LotteryCateStruc> lotteryCateStrucs = (List<LotteryCateStruc>) httpClient
					.invokeHttp(getUrl(queryLotteryKnowledgeCategoryUrl),
							new TypeReference<Response<List<LotteryCateStruc>>>() {
							}).getBody().getResult();
			List<LotteryContentStruc> HelpStrucL = response.getBody().getResult().getLotteryContentStruc();
			for(LotteryContentStruc lotteryContentStruc:HelpStrucL){
				for(LotteryCateStruc lotteryCateStruc :lotteryCateStrucs){
					if(lotteryContentStruc.getId().longValue() == lotteryCateStruc.getId().longValue()){
						StringBuffer names = new StringBuffer(lotteryCateStruc.getName());
						if(names.length() >2){
							names.insert(2, "<br>");
						}
						lotteryContentStruc.setName(names.toString());
					}
				}
			}
			Response<List<CateStruc>> resp = httpClient.invokeHttp(getUrl(queryCategoryUrl), new CateStrucQueryRequest(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			model.addAttribute("cateList",resp.getBody().getResult());
			
			//获取热门推荐彩票的列表，并封装到属性hotLotteryDetail中去
			HelpQueryRequest lotteryReq = new HelpQueryRequest();
			lotteryReq.setCateId(-1L);
			lotteryReq.setCateId2(-1L);
			lotteryReq.setIsRec(1L);
			lotteryReq.setType(1L);
			lotteryReq.setOrderBy("NO DESC,GMT_MODIFIED DESC");
			Response<List<HelpStruc>> hotLotteryResp = httpClient.invokeHttp(getUrl(queryHelpUrl) , lotteryReq,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			if (hotLotteryResp.getBody() != null) {
				model.addAttribute("hotLotteryDetail", hotLotteryResp.getBody().getResult());
			}
			
			
			logger.info("queryGeneralHelp end");
		} catch (Exception e) {
			logger.error("query general detail is error.", e);
			throw e;
		}
		IUser currUser = RequestContext.getCurrUser();
		model.addAttribute("currUser", currUser);
		model.addAttribute("help", response.getBody().getResult());
		model.addAttribute("cate2Name", response.getBody().getResult().getTitle());
		return "/help/lotteryHelpDetails";
	}
	
	/**
	 * 
	* @Title: queryGeneralHelpDetail 
	* @Description: 查询帮助详细信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/wap/queryLotteryDetail")
	public String wapQueryLotteryHelpDetail(@RequestParam("helpId") Long id, @RequestParam(required = false) String cate2Name,
			Model model) throws Exception {
		Response<HelpStruc> response = null;
		try {
			HelpDetailRequest queryDTO = new HelpDetailRequest();
			queryDTO.setHelpId(id);
			response = httpClient.invokeHttp(getUrl(queryHelpDetailUrl), queryDTO, HelpStruc.class);
			List<LotteryCateStruc> lotteryCateStrucs = (List<LotteryCateStruc>) httpClient
					.invokeHttp(getUrl(queryLotteryKnowledgeCategoryUrl),
							new TypeReference<Response<List<LotteryCateStruc>>>() {
							}).getBody().getResult();
			List<LotteryContentStruc> HelpStrucL = response.getBody().getResult().getLotteryContentStruc();
			for(LotteryContentStruc lotteryContentStruc:HelpStrucL){
				for(LotteryCateStruc lotteryCateStruc :lotteryCateStrucs){
					if(lotteryContentStruc.getId().longValue() == lotteryCateStruc.getId().longValue()){
						StringBuffer names = new StringBuffer(lotteryCateStruc.getName());
						if(names.length() >2){
							names.insert(2, "<br>");
						}
						lotteryContentStruc.setName(names.toString());
					}
				}
			}
			Response<List<CateStruc>> resp = httpClient.invokeHttp(getUrl(queryCategoryUrl), new CateStrucQueryRequest(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			model.addAttribute("cateList",resp.getBody().getResult());
			
			//获取热门推荐彩票的列表，并封装到属性hotLotteryDetail中去
			HelpQueryRequest lotteryReq = new HelpQueryRequest();
			lotteryReq.setCateId(-1L);
			lotteryReq.setCateId2(-1L);
			lotteryReq.setIsRec(1L);
			lotteryReq.setType(1L);
			lotteryReq.setOrderBy("NO DESC,GMT_MODIFIED DESC");
			Response<List<HelpStruc>> hotLotteryResp = httpClient.invokeHttp(getUrl(queryHelpUrl) , lotteryReq,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			if (hotLotteryResp.getBody() != null) {
				model.addAttribute("hotLotteryDetail", hotLotteryResp.getBody().getResult());
			}
			
			
			logger.info("queryGeneralHelp end");
		} catch (Exception e) {
			logger.error("query general detail is error.", e);
			throw e;
		}
		IUser currUser = RequestContext.getCurrUser();
		model.addAttribute("currUser", currUser);
		model.addAttribute("help", response.getBody().getResult());
		model.addAttribute("cate2Name", response.getBody().getResult().getTitle());
		return "/wap/lotteryHelpDetails";
	}
}
