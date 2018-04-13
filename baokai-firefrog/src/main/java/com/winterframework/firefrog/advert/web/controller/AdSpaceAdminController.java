package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.service.IAdParamService;
import com.winterframework.firefrog.advert.service.IAdSpaceService;
import com.winterframework.firefrog.advert.web.dto.AdDTOConverter;
import com.winterframework.firefrog.advert.web.dto.AdParamStru;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.advert.web.dto.AdStruc;
import com.winterframework.firefrog.advert.web.dto.AdUnbingStruc;
import com.winterframework.firefrog.advert.web.dto.AdspaceRelationStruc;
import com.winterframework.firefrog.advert.web.dto.AdvertData;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("adSpaceAdminController")
@RequestMapping("/adAdmin")
public class AdSpaceAdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdSpaceAdminController.class);

	@Resource(name = "adSpaceServiceImpl")
	private IAdSpaceService adSpaceServiceImpl;
	@Resource(name = "adParamServiceImpl")
	private IAdParamService adParamServiceImpl;

	/**
	 * 查询广告位列表
	*/
	@RequestMapping(value = "/getAllAdSpace")
	@ResponseBody
	public Response<List<AdSpaceStruc>> getAllAdSpace(@RequestBody Request<AdSpaceStruc> request) throws Exception {
		Response<List<AdSpaceStruc>> response = new Response<List<AdSpaceStruc>>(request);
		List<AdSpaceStruc> adSpaceStrucList = new ArrayList<AdSpaceStruc>();
		try {
			List<AdSpace> adSpaceList = adSpaceServiceImpl.getAllAdSpace();
			if (!CollectionUtils.isEmpty(adSpaceList)) {
				for (AdSpace adSpace : adSpaceList) {
					adSpaceStrucList.add(AdDTOConverter.transAdSpaceToStruc(adSpace));
				}
			}
			response.setResult(adSpaceStrucList);
		} catch (Exception e) {
			logger.error("getAllAdSpace error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 获取广告位参数列表
	*/
	@RequestMapping(value = "/queryAllParam")
	@ResponseBody
	public Response<List<AdParamStru>> queryAllParam(@RequestBody Request<AdSpaceStruc> request) throws Exception {
		Response<List<AdParamStru>> response = new Response<List<AdParamStru>>(request);
		List<AdParamStru> adParamStruList = new ArrayList<AdParamStru>();
		try {
			List<AdParam> adParamList = adParamServiceImpl.getAllAdParam();
			if (!CollectionUtils.isEmpty(adParamList)) {
				for (AdParam adParam : adParamList) {
					adParamStruList.add(AdDTOConverter.transAdParamToStruc(adParam));
				}
			}
			response.setResult(adParamStruList);
		} catch (Exception e) {
			logger.error("queryAllParam error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 根据广告位id获取需要修改的广告位
	*/
	@RequestMapping(value = "/getAdSpaceName")
	@ResponseBody
	public Response<AdSpaceStruc> getAdSpaceByName(@RequestBody Request<AdSpaceStruc> request) throws Exception {
		Response<AdSpaceStruc> response = new Response<AdSpaceStruc>(request);
		try {
			AdSpace adSpace = adSpaceServiceImpl.getAdSpaceById(request.getBody().getParam().getId());
			response.setResult(AdDTOConverter.transAdSpaceToStruc(adSpace));
		} catch (Exception e) {
			logger.error("getAdSpaceById error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 根据广告位id获取需要修改的广告位
	*/
	@RequestMapping(value = "/getAdSpaceById")
	@ResponseBody
	public Response<AdSpaceStruc> getAdSpaceById(@RequestBody Request<AdSpaceStruc> request) throws Exception {
		Response<AdSpaceStruc> response = new Response<AdSpaceStruc>(request);
		try {
			AdSpace adSpace = adSpaceServiceImpl.getAdSpaceById(request.getBody().getParam().getId());
			response.setResult(AdDTOConverter.transAdSpaceToStruc(adSpace));
		} catch (Exception e) {
			logger.error("getAdSpaceById error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 根据广告位获取对应的广告列表
	*/
	@RequestMapping(value = "/getAdvertBySpaceId")
	@ResponseBody
	public Response<List<AdspaceRelationStruc>> getAdvertBySpaceId(@RequestBody Request<AdSpaceStruc> request)
			throws Exception {
		Response<List<AdspaceRelationStruc>> response = new Response<List<AdspaceRelationStruc>>(request);
		List<AdspaceRelationStruc> adSpaceStrucList = new ArrayList<AdspaceRelationStruc>();
		try {
			AdSpace adSpace = adSpaceServiceImpl.getAdsByAdSpaceId(request.getBody().getParam().getId());
			List<Advert> advertList = adSpace.getAdvertList();
			if (advertList != null && advertList.size() > 0) {
				for (Advert advert : advertList) {
					adSpaceStrucList.add(AdDTOConverter.transRalationToStruc(advert, adSpace.getId()));
				}
			}
			response.setResult(adSpaceStrucList);
		} catch (Exception e) {
			logger.error("getAdvertBySpaceId error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 根据广告位获取对应的广告列表
	*/
	@RequestMapping(value = "/getAdvertByNames")
	@ResponseBody
	public Response<List<AdvertData>> getAdvertByNames(@RequestBody Request<List<String>> request) throws Exception {
		Response<List<AdvertData>> response = new Response<List<AdvertData>>(request);
		List<AdvertData> advertDataList = new ArrayList<AdvertData>();
		Long userId = request.getHead().getUserId();
		try {
			List<AdSpace> adSpaces = adSpaceServiceImpl.getAdSpacesByNames(request.getBody().getParam());
			for (AdSpace adSpace : adSpaces) {
				List<Advert> advertList = adSpaceServiceImpl.getEffectAdsByAdSpaceId(adSpace.getId(), userId)
						.getAdvertList();
				AdvertData advertData = new AdvertData();
				advertData.setName(adSpace.getName());
				advertData.setList(AdDTOConverter.transAdvertToAdvertStruc(advertList));
				advertDataList.add(advertData);
			}
			response.setResult(advertDataList);
		} catch (Exception e) {
			logger.error("getAdvertByNames error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 保存广告位
	*/
	@RequestMapping(value = "/saveAdSpace")
	@ResponseBody
	public Response<AdSpaceStruc> createAdSpace(@RequestBody @ValidRequestBody Request<AdSpaceStruc> request)
			throws Exception {
		Response<AdSpaceStruc> response = new Response<AdSpaceStruc>(request);
		try {

			AdSpace adspace = AdDTOConverter.transAdStructToAdSpace(request.getBody().getParam());
			adSpaceServiceImpl.insertAdSpace(adspace);
        } catch (Exception e) {
			logger.error("saveAdSpace error", e);
            response.getHead().setStatus(901000L);
			throw e;
		}
        return response;
	}

	/** 
	 * 更新广告位
	*/
	@RequestMapping(value = "/updateAdSpace")
	@ResponseBody
	public Response<AdSpaceStruc> updateAdSpace(@RequestBody @ValidRequestBody Request<AdSpaceStruc> request)
			throws Exception {
		Response<AdSpaceStruc> response = new Response<AdSpaceStruc>(request);
		try {
			AdSpace adspace = AdDTOConverter.transAdStructToAdSpace(request.getBody().getParam());
			adSpaceServiceImpl.updateAdSpace(adspace);
		} catch (Exception e) {
			logger.error("updateAdSpace error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 根据广告id获取对应的广告位 
	*/
	@RequestMapping(value = "/getAdSpaceByAdId")
	@ResponseBody
	public Response<List<AdSpaceStruc>> getAdSpaceByAdId(@RequestBody Request<AdStruc> request) throws Exception {
		Response<List<AdSpaceStruc>> response = new Response<List<AdSpaceStruc>>(request);
		List<AdSpaceStruc> adSpaceStrucList = new ArrayList<AdSpaceStruc>();
		try {
			List<AdSpace> adSpaceList = adSpaceServiceImpl.getAdSpacesByAdId(request.getBody().getParam().getId());
			if (adSpaceList != null && adSpaceList.size() > 0) {
				for (AdSpace adSpace : adSpaceList) {
					adSpaceStrucList.add(AdDTOConverter.transAdSpaceToStruc(adSpace));
				}
			}
			response.setResult(adSpaceStrucList);
		} catch (Exception e) {
			logger.error("getAdSpaceByAdId error", e);
			throw e;
		}
		return response;
	}

	/** 
	 * 更新广告与广告位的绑定关系
	*/
	@RequestMapping(value = "/updateUnbingAdvert")
	@ResponseBody
	public Response<AdUnbingStruc> updateUnbingAdvert(
			@RequestBody @ValidRequestBody Request<List<AdUnbingStruc>> request) throws Exception {
		Response<AdUnbingStruc> response = new Response<AdUnbingStruc>(request);
		try {
			List<AdUnbingStruc> strucList = request.getBody().getParam();
			AdSpace adSpace = new AdSpace();
			List<Advert> advertList = new ArrayList<Advert>();
			if (!CollectionUtils.isEmpty(strucList)) {
				for (AdUnbingStruc adspaceRelationStruc : strucList) {
					Advert advert = AdDTOConverter.transAdRelationStructToAdRelation(adspaceRelationStruc);
					advertList.add(advert);
				}
				adSpace.setId(strucList.get(0).getAdSpaceId());
				adSpace.setAdvertList(advertList);
				adSpaceServiceImpl.updateUnbingAdvert(adSpace);
			}
		} catch (Exception e) {
			logger.error("queryHelpDetail error", e);
			throw e;
		}
		return response;

	}
}
