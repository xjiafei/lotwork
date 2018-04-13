package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.service.IAdNoticeService;
import com.winterframework.firefrog.advert.web.dto.AdNoticeAuditRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeDelRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeDetailRequest;
import com.winterframework.firefrog.advert.web.dto.AdNoticeListStruc;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.advert.web.dto.AdNoticeStruc;
import com.winterframework.firefrog.advert.web.dto.AdNoticeUpdateResult;
import com.winterframework.firefrog.advert.web.dto.CountResultPager;
import com.winterframework.firefrog.advert.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: AdNoticeController 
* @Description: 广告模块公告后台接口
* @author Tod
* @date 2013-11-19 下午2:29:16 
*
 */
@Controller("adNoticeController")
@RequestMapping("/adAdmin")
public class AdNoticeController {

	private static final Logger logger = LoggerFactory.getLogger(AdNoticeController.class);

	@Resource(name = "adNoticeServiceImpl")
	private IAdNoticeService adNoticeService;

	/**
	 * 
	* @Title: createAdNotice 
	* @Description: 创建公告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createAdNotice")
	public @ResponseBody
	Response<AdNoticeUpdateResult> createAdNotice(@RequestBody @ValidRequestBody Request<AdNoticeStruc> request) throws Exception {

		Response<AdNoticeUpdateResult> response = new Response<AdNoticeUpdateResult>(request);
		AdNoticeUpdateResult result = new AdNoticeUpdateResult();
		AdNoticeStruc struc = request.getBody().getParam();
		try {
			AdNotice adNotice = DTOConverter.transDto2AdNotice(struc);
			adNoticeService.createNotice(adNotice, struc.getCreateType());
			result.setStatus(AdNoticeUpdateResult.SUCCESS);
		} catch (UncategorizedSQLException e) {
			logger.error("createAdNotice error.", e);
			result.setStatus(AdNoticeUpdateResult.FAIL);
			if(e.getSQLException()!=null&&e.getSQLException().getErrorCode() == 1461){
				//oracle errorcode-1461:值太大放不下
				result.setStatus(AdNoticeUpdateResult.CONTENT_TOO_LONG);
			}
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("createAdNotice error.", e);
			result.setStatus(AdNoticeUpdateResult.FAIL);
			result.setMessage(e.getMessage());
		}
		response.setResult(result);
		return response;
	}

	/**
	 * 
	* @Title: modifyAdNotice 
	* @Description: 修改公告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyAdNotice")
	public @ResponseBody
	Response<Object> modifyAdNotice(@RequestBody @ValidRequestBody Request<AdNoticeStruc> request) throws Exception {

		Response<Object> response = new Response<Object>(request);
		AdNoticeStruc struc = request.getBody().getParam();
		try {
			AdNotice adNotice = DTOConverter.transDto2AdNotice(struc);
			adNoticeService.modifyNotice(adNotice, struc.getCreateType());
		} catch (Exception e) {
			logger.error("modifyAdNotice error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: auditAdNotice 
	* @Description: 审核公告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditAdNotice")
	public @ResponseBody
	Response<Object> auditAdNotice(@RequestBody @ValidRequestBody Request<AdNoticeAuditRequest> request)
			throws Exception {

		Response<Object> response = new Response<Object>(request);
		AdNoticeAuditRequest auditReq = request.getBody().getParam();
		boolean isPass = auditReq.getStatus() == 0L ? false : true;
		try {
			adNoticeService.auditNotice(auditReq.getIds(), isPass, auditReq.getMemo(), auditReq.getApprover());
		} catch (Exception e) {
			logger.error("auditAdNotice AdNotice error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: deleteAdNotice 
	* @Description: 删除公告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteAdNotice")
	public @ResponseBody
	Response<Object> deleteAdNotice(@RequestBody @ValidRequestBody Request<AdNoticeDelRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			adNoticeService.deleteNotice(request.getBody().getParam().getIds());
		} catch (Exception e) {
			logger.error("deleteAdNotice error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryDetail 
	* @Description: 查询公告详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAdNoticeDetail")
	public @ResponseBody
	Response<AdNoticeStruc> queryDetail(@RequestBody @ValidRequestBody Request<AdNoticeDetailRequest> request)
			throws Exception {
		Response<AdNoticeStruc> response = new Response<AdNoticeStruc>(request);
		try {
			AdNotice adNotice = adNoticeService.queryNoticeDetail(request.getBody().getParam().getId());
			response.setResult(DTOConverter.transAdNotice2Dto(adNotice));
		} catch (Exception e) {
			logger.error("queryDetail error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryDetail 
	* @Description: 查询公告详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAdNoticeForUser")
	public @ResponseBody
	Response<List<AdNoticeStruc>> queryAdNoticeForUser(@RequestBody Request<List<String>> request)
			throws Exception {
		Response<List<AdNoticeStruc>> response = new Response<List<AdNoticeStruc>>(request);
		try {
			logger.info("queryAdNoticeForUser start.");
			Long userId = request.getHead().getUserId();
			List<AdNotice> adNotices = adNoticeService.getAdNoticeForUser(userId, request.getBody().getParam());
			List<AdNoticeStruc> result = new ArrayList<AdNoticeStruc>();
			for (AdNotice adNotice : adNotices) {
				result.add(DTOConverter.transAdNotice2Dto(adNotice));
			}
			response.setResult(result);
		} catch (Exception e) {
			logger.error("queryAdNoticeForUser error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询公告列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAdNoticeList")
	public @ResponseBody
	Response<AdNoticeListStruc> queryList(@RequestBody Request<AdNoticeSearchDto> request) throws Exception {
		Response<AdNoticeListStruc> response = new Response<AdNoticeListStruc>(request);
		PageRequest<AdNoticeSearchDto> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns(request.getBody().getParam().getOrderBy());
		try {
			CountPage<AdNotice> page = adNoticeService.queryNoticeList(pageRequest);
			List<AdNoticeStruc> list = new ArrayList<AdNoticeStruc>();
			for (AdNotice notice : page.getResult()) {
				list.add(DTOConverter.transAdNotice2Dto(notice));
			}
			CountResultPager pager = new CountResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			pager.setSumNotPass(page.getSumNotPass());
			pager.setSumReviewing(page.getSumReviewing());
			pager.setSumWait(page.getSumWait());

			AdNoticeListStruc result = new AdNoticeListStruc();
			result.setNoticeList(list);
			result.setSumNew(page.getSumWait());
			result.setSumAudit(page.getSumReviewing());
			result.setSumRefuse(page.getSumNotPass());

			response.setResult(result);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryAdNoticeList error.", e);
			throw e;
		}
		return response;
	}

}
