package com.winterframework.firefrog.global.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.service.GlobalIpService;
import com.winterframework.firefrog.global.web.dto.DTOConverter;
import com.winterframework.firefrog.global.web.dto.GlobalIpCreateRequest;
import com.winterframework.firefrog.global.web.dto.GlobalIpDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalIpStruc;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: GlobalIpController 
* @Description: IP黑白名单管理控制类
* @author Tod
* @date 2013-10-15 下午4:46:49 
*
 */
@Controller("globalIpController")
@RequestMapping("/globeAdmin")
public class GlobalIpController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalIpController.class);

	@Resource(name = "globalIpServiceImpl")
	private GlobalIpService globalIpService;

	/**
	 * 
	* @Title: deleteIp 
	* @Description: 删除IP
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteIp")
	public @ResponseBody
	Response<Object> deleteIp(@RequestBody @ValidRequestBody Request<GlobalIpDelRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			globalIpService.deleteIp(request.getBody().getParam().getIds());
		} catch (Exception e) {
			logger.error("deleteIp error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: createIp 
	* @Description: 创建IP
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createIp")
	public @ResponseBody
	Response<Object> createIp(@RequestBody @ValidRequestBody Request<GlobalIpCreateRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		GlobalIpCreateRequest req = request.getBody().getParam();
		IPAddress ip = new IPAddress();
		ip.setIp(req.getIp());
		AclUser operator = new AclUser();
		operator.setAccount(req.getOperator());
		ip.setOperator(operator);
		ip.setType(req.getType() == 0L ? IPAddress.Type.blackList : IPAddress.Type.whiteList);
		try {
			if (globalIpService.getSameIpLimit(ip.getIp(), String.valueOf(req.getType())) == null) {
				globalIpService.saveIp(ip, req.getPeriod().intValue());
			}
		} catch (Exception e) {
			logger.error("createIp error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: createIp 
	* @Description: 创建IP
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkCreateIp")
	public @ResponseBody
	Response<Object> checkCreateIp(@RequestBody Request<GlobalIpCreateRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		GlobalIpCreateRequest req = request.getBody().getParam();
		IPAddress ip = new IPAddress();
		ip.setIp(req.getIp());
		ip.setType(req.getType() == 0L ? IPAddress.Type.blackList : IPAddress.Type.whiteList);
		try {
			if (globalIpService.getSameIpLimit(ip.getIp(), String.valueOf(req.getType())) != null) {
				response.getHead().setStatus(1073);
				IPAddress ipAddress = globalIpService.getSameIpLimit(ip.getIp(), String.valueOf(req.getType()));
				response.setResult(DTOConverter.transIp2Dto(ipAddress));
			} else {
				response.getHead().setStatus(0);
			}

		} catch (Exception e) {
			logger.error("checkCreateIp error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询IP列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryIpList")
	public @ResponseBody
	Response<List<GlobalIpStruc>> queryList(@RequestBody Request<IpSearchDto> request) throws Exception {
		Response<List<GlobalIpStruc>> response = new Response<List<GlobalIpStruc>>(request);
		PageRequest<IpSearchDto> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("EFFECT_TIME DESC");
		try {
			Page<IPAddress> page = globalIpService.queryList(pageRequest);
			List<GlobalIpStruc> list = new ArrayList<GlobalIpStruc>();
			for (IPAddress ip : page.getResult()) {
				list.add(DTOConverter.transIp2Dto(ip));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryList error.", e);
			throw e;
		}
		return response;
	}

}
