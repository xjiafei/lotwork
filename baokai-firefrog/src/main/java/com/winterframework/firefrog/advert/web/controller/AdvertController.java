/**   
* @Title: AdController.java 
* @Package com.winterframework.firefrog.advert.web 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 上午10:57:56 
* @version V1.0   
*/
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

import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.service.IAdSpaceService;
import com.winterframework.firefrog.advert.service.IAdvertService;
import com.winterframework.firefrog.advert.web.dto.AdQueryRequest;
import com.winterframework.firefrog.advert.web.dto.AdReviewRequest;
import com.winterframework.firefrog.advert.web.dto.AdStruc;
import com.winterframework.firefrog.advert.web.dto.AdvertSearchDto;
import com.winterframework.firefrog.advert.web.dto.CountResult;
import com.winterframework.firefrog.advert.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.exception.SensitiveWordException;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: AdController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 上午10:57:56 
*  
*/
@Controller("adController")
@RequestMapping("/adAdmin")
public class AdvertController {

	private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);

	@Resource(name = "adServiceImpl")
	private IAdvertService adServiceImpl;
	@Resource(name = "adSpaceServiceImpl")
	private IAdSpaceService adSpaceServiceImpl;

	/**
	 * 
	* @Title: queryAdvertById 
	* @Description: 根据ID查询广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAdvertById")
	public @ResponseBody
	Response<AdStruc> queryAdvertById(@RequestBody Request<Long> request) throws Exception {
		Response<AdStruc> response = new Response<AdStruc>();
		Long id = request.getBody().getParam();
		try {
			Advert ad = adServiceImpl.getAdById(id);
			response.setResult(DTOConverter.transAdvert2AdStruc(ad));
		} catch (Exception e) {
			logger.error("queryAdvertById error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: addAdvert 
	* @Description: 新建广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/addAdvert")
	public @ResponseBody
	Response<Object> addAdvert(@RequestBody @ValidRequestBody Request<AdStruc> request) {
		Response<Object> response = new Response<Object>(request);
		AdStruc req = request.getBody().getParam();
		try {
			adServiceImpl.save(DTOConverter.transAdStruc2Advert(req));
		}catch (SensitiveWordException e) {
            response.getHead().setStatus(e.getStatus());
        }
        catch (Exception e) {
			logger.error("addAdvert error.", e);
		}
		return response;
	}

	/**
	 * 
	* @Title: updateAdvert 
	* @Description: 更新广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateAdvert")
	public @ResponseBody
	Response<Object> updateAdvert(@RequestBody @ValidRequestBody Request<AdStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AdStruc req = request.getBody().getParam();
		if(req.getStatus().longValue() == 1L){
			req.setApprover("");
		}
		try {
			adServiceImpl.update(DTOConverter.transAdStruc2Advert(req));
		} catch (Exception e) {
			logger.error("updateAdvert error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: deleteAdvert 
	* @Description: 删除广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteAdvert")
	public @ResponseBody
	Response<Object> deleteAdvert(@RequestBody Request<List<Long>> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		List<Long> ids = request.getBody().getParam();
		try {
			adServiceImpl.delete(ids);
		} catch (Exception e) {
			logger.error("deleteAdvert error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: reviewAdvert 
	* @Description:审核广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/reviewAdvert")
	public @ResponseBody
	Response<Object> reviewAdvert(@RequestBody @ValidRequestBody Request<AdReviewRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AdReviewRequest req = request.getBody().getParam();
		try {
			adServiceImpl.review(req.getIds(), req.getStatus(), req.getMemo(), request.getHead().getUserAccount());
		} catch (Exception e) {
			logger.error("reviewAdvert error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryAdPage 
	* @Description:查询广告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAdPage")
	public @ResponseBody
	Response<CountResult> queryAdPage(@RequestBody Request<AdQueryRequest> request) throws Exception {
		Response<CountResult> response = new Response<CountResult>(request);
		AdQueryRequest req = request.getBody().getParam();
		PageRequest<AdvertSearchDto> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(DTOConverter.transAdQueryRequest2AdvertSearch(req));
		try {
			CountPage<Advert> page = adServiceImpl.queryAdByPage(pageRequest);
			List<AdStruc> adStrucs = new ArrayList<AdStruc>();
			for (Advert advert : page.getResult()) {
				AdStruc adStruc = DTOConverter.transAdvert2AdStruc(advert);
				List<AdSpace> adSpaceList = adSpaceServiceImpl.getAdSpacesByAdId(adStruc.getId());
				if(!adSpaceList.isEmpty()){
					adStruc.setWidth(adSpaceList.get(0).getWidth());
					adStruc.setHeight(adSpaceList.get(0).getHeight());
				}
				adStrucs.add(adStruc);
			}
			CountResult countResult = new CountResult();
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			countResult.setSumNotPass(page.getSumNotPass());
			countResult.setSumReviewing(page.getSumReviewing());
			countResult.setSumWait(page.getSumWait());
			countResult.setAdStrucs(adStrucs);
			response.setResult(countResult);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryAdPage error.", e);
			throw e;
		}
		return response;
	}

}
