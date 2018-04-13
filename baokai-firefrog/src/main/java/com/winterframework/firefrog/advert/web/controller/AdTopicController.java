package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.firefrog.advert.service.IAdTopicService;
import com.winterframework.firefrog.advert.web.dto.AdTopicCategoryStruc;
import com.winterframework.firefrog.advert.web.dto.AdTopicDetailRequest;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.firefrog.advert.web.dto.AdTopicStruc;
import com.winterframework.firefrog.advert.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: AdTopicController 
* @Description: 广告模块专题后台接口
* @author Tod
* @date 2013-11-19 下午2:30:07 
*
 */
@Controller("adTopicController")
@RequestMapping("/adAdmin")
public class AdTopicController {

	private static final Logger logger = LoggerFactory.getLogger(AdTopicController.class);

	@Resource(name = "adTopicServiceImpl")
	private IAdTopicService adTopicService;

	/**
	 * 
	* @Title: createAdTopic 
	* @Description: 创建专题
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createAdTopic")
	public @ResponseBody
	Response<Object> createAdTopic(@RequestBody @ValidRequestBody Request<AdTopicStruc> request) throws Exception {

		Response<Object> response = new Response<Object>(request);
		AdTopicStruc struc = request.getBody().getParam();
		try {
			AdTopic adTopic = DTOConverter.transDto2AdTopic(struc);
			adTopicService.createTopic(adTopic);
		} catch (Exception e) {
			logger.error("createAdTopic error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: modifyAdTopic 
	* @Description: 修改专题
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyAdTopic")
	public @ResponseBody
	Response<Object> modifyAdTopic(@RequestBody @ValidRequestBody Request<AdTopicStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AdTopicStruc struc = request.getBody().getParam();
		try {
			AdTopic adTopic = DTOConverter.transDto2AdTopic(struc);
			adTopicService.modifyTopic(adTopic);
		} catch (Exception e) {
			logger.error("modifyAdTopic error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: detailAdTopic 
	* @Description: 查看专题详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/detailAdTopic")
	public @ResponseBody
	Response<AdTopicStruc> detailAdTopic(@RequestBody @ValidRequestBody Request<AdTopicDetailRequest> request)
			throws Exception {
		Response<AdTopicStruc> response = new Response<AdTopicStruc>(request);
		AdTopicDetailRequest detailReq = request.getBody().getParam();
		try {
			AdTopic topic = adTopicService.queryTopicDetail(detailReq.getId());
			response.setResult(DTOConverter.transAdTopic2Dto(topic));
		} catch (Exception e) {
			logger.error("detailAdTopic error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryAllCategory 
	* @Description: 查询所有的专题类型
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAllTopicCategory")
	public @ResponseBody
	Response<List<AdTopicCategoryStruc>> queryAllCategory(@RequestBody Request<Object> request) throws Exception {

		Response<List<AdTopicCategoryStruc>> response = new Response<List<AdTopicCategoryStruc>>(request);
		try {
			List<AdTopicCate> cateList = adTopicService.queryAllCate();
			List<AdTopicCategoryStruc> strucList = new ArrayList<AdTopicCategoryStruc>();
			AdTopicCategoryStruc struc = null;
			for (AdTopicCate cate : cateList) {
				struc = new AdTopicCategoryStruc();
				struc.setId(cate.getId());
				struc.setName(cate.getName());
				strucList.add(struc);
			}
			response.setResult(strucList);
		} catch (Exception e) {
			logger.error("queryAllTopicCategory error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryTopicList 
	* @Description: 查询专题列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryTopicList")
	public @ResponseBody
	Response<List<AdTopicStruc>> queryTopicList(@RequestBody Request<AdTopicSearch> request) throws Exception {

		Response<List<AdTopicStruc>> response = new Response<List<AdTopicStruc>>(request);
		PageRequest<AdTopicSearch> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("GMT_CREATED DESC");
		try {
			Page<AdTopic> page = adTopicService.queryTopic(pageRequest);
			List<AdTopicStruc> strucList = new ArrayList<AdTopicStruc>();
			for (AdTopic topic : page.getResult()) {
				strucList.add(DTOConverter.transAdTopic2Dto(topic));
			}
			response.setResult(strucList);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryAdTopic error.", e);
			throw e;
		}
		return response;
	}

}
