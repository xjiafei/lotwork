/**   
* @Title: GeneralHelpController.java 
* @Package com.winterframework.firefrog.help.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-11 上午11:08:06 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdParamStru;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.advert.web.dto.AdUnbingStruc;
import com.winterframework.firefrog.advert.web.dto.AdspaceRelationStruc;
import com.winterframework.firefrog.advert.web.dto.StrucConvert;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("adSpaceController")
@RequestMapping(value = "/adAdmin")
public class AdSpaceController {

	private static final Logger logger = LoggerFactory.getLogger(AdSpaceController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value="url.advert.getAllAdSpace")
	private String getAllAdSpace;
	
	@PropertyConfig(value="url.advert.getAdSpaceById")
	private String getAdSpaceById;
	
	@PropertyConfig(value="url.advert.saveAdSpace")
	private String saveAdSpace;
	
	@PropertyConfig(value="url.advert.updateAdSpace")
	private String updateAdSpace;
	
	@PropertyConfig(value="url.advert.getAdvertBySpaceId")
	private String getAdvertBySpaceId;
	
	@PropertyConfig(value="url.advert.updateUnbingAdvert")
	private String updateUnbingAdvert;
	
	@PropertyConfig(value="url.advert.queryAllParam")
	private String queryAllParam;
	
	private String getUrl(String path) {
		return serverPath +path;
	}
	@PropertyConfig(value = "url.imageserver.visit")
	private String urlViewPic;
	
	/** 
	 * 查询广告位列表
	*/
	@RequestMapping(value = "/queryAllAdSpace")
	public ModelAndView queryAllAdSpace(AdSpaceStruc req ) throws Exception {
		ModelAndView mav = new ModelAndView("advert/queryAllAdSpace");
		try {
			Response<List<AdSpaceStruc>> cateResp = httpClient.invokeHttp(getUrl(getAllAdSpace),req,
					new TypeReference<Response<List<AdSpaceStruc>>>() {
					});
			List<AdSpaceStruc> adSpaceList = cateResp.getBody().getResult();
			mav.addObject("queryReq", req);
			mav.addObject("adSpaceList", adSpaceList);
			mav.addObject("cate2Name", "帮助管理");
			mav.addObject("urlViewPic", urlViewPic);
		} catch (Exception e) {
			logger.error("searchHelp error.", e);
			throw e;
		}
		mav.addObject("urlViewPic", urlViewPic);
		return mav;
	}
	/** 
	 * 新建或者修改广告位页面
	*/
	@RequestMapping(value = "/editAdSpace")
	public ModelAndView editAdSpace(AdSpaceStruc request) throws Exception {
		ModelAndView mav = new ModelAndView("advert/editAdSpace");
		if (request.getId() != null) {
			mav.addObject("isEdit", true);
			try {
				Response<AdSpaceStruc> adSpaceResp = httpClient.invokeHttp(getUrl(getAdSpaceById),request, new TypeReference<Response<AdSpaceStruc>>() {});
				AdSpaceStruc adSpaceStruc = adSpaceResp.getBody().getResult();
				mav.addObject("adSpaceStruc", adSpaceStruc);
				
				//处理广告位的站位图。
				List<String> dftImgs = new ArrayList<String>();
				if (StringUtils.isNotEmpty(adSpaceStruc.getDftImgs())) {
					String[] array = adSpaceStruc.getDftImgs().split(";");
					for (String string : array) {
						dftImgs.add(string);
					}
				} 
				request.setParamId(adSpaceStruc.getParamId());
				mav.addObject("dftImgs", dftImgs);
			} catch (Exception e) {
				logger.error("searchHelp error.", e);
				throw e;
			}
		}
		else
		{
			mav.addObject("isEdit", false);
		}
		mav.addObject("urlViewPic", urlViewPic);
		return mav;
	}
	
	/** 
	 * 保存或者修改广告位的操作
	*/
	@RequestMapping(value = "/saveEditAdSpace")
	@ResponseBody
	public Object saveOrUpdateAdSpace(AdSpaceStruc request) throws Exception {
		OperateStatusResponse operateSatus = new OperateStatusResponse();
		String tipInfo="";
		try {
			if (request.getId() == null) {
				httpClient.invokeHttp(getUrl(saveAdSpace), request, Object.class);
				tipInfo="新建广告位成功";
			} else {
				httpClient.invokeHttp(getUrl(updateAdSpace), request, Object.class);
				tipInfo="修改广告位成功";
			}
			operateSatus.setStatus(1);
			operateSatus.setMessage(tipInfo);
		} catch (Exception e) {
			operateSatus.setStatus(0);
			logger.error("createHelp error.", e);
			throw e;
		}
		return operateSatus;
	}
	
	/** 
	 * 获取广告位参数列表
	*/
	@RequestMapping(value = "/getParamList")
	@ResponseBody
	public Object getParamList() throws Exception {
		Response<List<AdParamStru>> resp = new Response<List<AdParamStru>>();
			try {
				Response<List<AdParamStru>> paramResp = httpClient.invokeHttp((getUrl(queryAllParam)),null,
						new TypeReference<Response<List<AdParamStru>>>() {
						});
				List<AdParamStru> paramList = paramResp.getBody().getResult();
				resp.setResult(paramList);
			} catch (Exception e) {
				logger.error("searchHelp error.", e);
				throw e;
			}
		return resp;
	}
	
	/** 
	 * 解绑或者绑定广告位的操作
	*/
	@RequestMapping(value = "/getAdvertBySpaceId")
	public ModelAndView getAdvertBySpaceId(AdSpaceStruc request) throws Exception {
		ModelAndView mav = new ModelAndView("advert/unbingAdvert");
		Response<List<AdspaceRelationStruc>> relationResp= null;
		if (request.getId() != null) {
			try {
				relationResp = httpClient.invokeHttp(getUrl(getAdvertBySpaceId), request,new TypeReference<Response<List<AdspaceRelationStruc>>>() {});
				List<AdspaceRelationStruc> adspaceRelationStruc = relationResp.getBody().getResult();
				mav.addObject("queryReq", request);
				mav.addObject("adspaceRelationStruc", adspaceRelationStruc);
				mav.addObject("adSpaceId", request.getId());
			} catch (Exception e) {
				logger.error("searchHelp error.", e);
				throw e;
			}
		}
		mav.addObject("urlViewPic", urlViewPic);
		return mav;
	}
	
	/** 
	 * 保存广告位的解绑或绑定操作
	*/
	@RequestMapping(value = "/updateUnbingAdvert")
	@ResponseBody
	public Object updateUnbingAdvert(@RequestParam String unbingList) throws Exception {
		OperateStatusResponse operateSatus = new OperateStatusResponse();
		List<AdUnbingStruc> list = StrucConvert.convertToStruc(unbingList);
		try {
			httpClient.invokeHttp(getUrl(updateUnbingAdvert), list,new TypeReference<Response<List<AdUnbingStruc>>>() {});
			operateSatus.setStatus(1);
		} catch (Exception e) {
			logger.error("searchHelp error.", e);
			operateSatus.setStatus(0);
			throw e;
		}
		return operateSatus;
	}
}
