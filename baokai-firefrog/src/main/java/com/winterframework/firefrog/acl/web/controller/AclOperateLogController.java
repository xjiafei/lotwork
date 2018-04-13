package com.winterframework.firefrog.acl.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.service.IAclOperateLogService;
import com.winterframework.firefrog.acl.web.dto.AclLogDetailRequest;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.firefrog.acl.web.dto.AclOperateLogStruc;
import com.winterframework.firefrog.acl.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: AclOperateLogController 
* @Description: 日志管理控制类
* @author Tod
* @date 2013-10-15 下午5:04:50 
*
 */
@Controller("aclOperateLogController")
@RequestMapping("/aclAdmin")
public class AclOperateLogController {

	private static final Logger logger = LoggerFactory.getLogger(AclOperateLogController.class);

	@Resource(name = "aclOperateLogServiceImpl")
	private IAclOperateLogService logService;

	/**
	 * 
	* @Title: createLog 
	* @Description: 新建日志 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createLog")
	public @ResponseBody
	Response<Object> createLog(@RequestBody @ValidRequestBody Request<AclOperateLogStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AclOperateLogStruc dto = request.getBody().getParam();
		AclOperateLog log = DTOConverter.transDto2Log(dto);
		try {
			logService.saveLog(log);
		} catch (Exception e) {
			logger.error("createLog error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/detailLog")
	public @ResponseBody
	Response<AclOperateLogStruc> detailLog(@RequestBody @ValidRequestBody Request<AclLogDetailRequest> request)
			throws Exception {
		Response<AclOperateLogStruc> response = new Response<AclOperateLogStruc>(request);
		AclLogDetailRequest dto = request.getBody().getParam();
		try {
			AclOperateLog log = logService.queryLogDetail(dto.getId());
			response.setResult(DTOConverter.transLog2Dto(log));
		} catch (Exception e) {
			logger.error("detailLog error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询日志列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryLogList")
	public @ResponseBody
	Response<List<AclOperateLogStruc>> queryList(@RequestBody Request<AclLogQueryRequest> request) throws Exception {
		Response<List<AclOperateLogStruc>> response = new Response<List<AclOperateLogStruc>>(request);
		PageRequest<AclLogQueryRequest> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());

		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("GMT_CREATED desc");
		try {
			Page<AclOperateLog> page = logService.queryList(pageRequest);
			List<AclOperateLogStruc> list = new ArrayList<AclOperateLogStruc>();
			for (AclOperateLog log : page.getResult()) {
				list.add(DTOConverter.transLog2Dto(log));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryLogList error.", e);
			throw e;
		}
		return response;
	}

}
