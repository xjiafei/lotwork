package com.winterframework.firefrog.global.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.global.web.dto.Config;
import com.winterframework.firefrog.global.web.dto.ConfigSaveRequestDTO;
import com.winterframework.firefrog.global.web.dto.DefaultConfig;
import com.winterframework.firefrog.global.web.dto.GlobalIpCreateRequest;
import com.winterframework.firefrog.global.web.dto.GlobalIpDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalIpStruc;
import com.winterframework.firefrog.global.web.dto.IpCreateDto;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("IPSwitchController")
@RequestMapping(value = "/globeAdmin")
public class IPSwitchController {
	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.global.createIp")
	private String createIp;

	@PropertyConfig(value = "url.global.checkCreateIp")
	private String checkCreateIp;

	@PropertyConfig(value = "url.global.queryIpList")
	private String queryIp;

	@PropertyConfig(value = "url.global.deleteIp")
	private String deleteIp;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.global.saveConfigIp")
	private String saveConfigUrl;

	@PropertyConfig(value = "url.global.queryConfigIp")
	private String queryConfigUrl;

	@RequestMapping(value = "/goIpSwitch")
	public ModelAndView goIPSwitch(IpSearchDto search, HttpServletRequest request) throws Exception {
		search.setPageNo(search.getPageNo() == null ? 1 : search.getPageNo());
		Config conf = new Config();
		conf.setModule("global");
		conf.setFunction("ipList");
		Response<ConfigSaveRequestDTO<Long>> statusResp = httpClient.invokeHttp(urlPath + queryConfigUrl, conf,
				new TypeReference<Response<ConfigSaveRequestDTO<Long>>>() {
				});
		long oldStatus = statusResp.getBody().getResult().getVal() == null ? -1 : statusResp.getBody().getResult()
				.getVal().longValue();
		long wStatus = 0;
		long bStatus = 0;
		if (oldStatus == 0L) {
			bStatus = 1L;
		} else if (oldStatus == 1L) {
			wStatus = 1L;
		}
		ModelAndView mav = null;
		List<String> aclList = (List<String>) request.getAttribute("aclList");
		if ((search.getType() != null && search.getType() == 1L) || (!CollectionUtils.isEmpty(aclList))
				&& !aclList.contains("GLOBAL_IPILLEGALITY")) {
			mav = new ModelAndView("global/ipSwitchWrite");
			search.setType(1L);
		} else {
			search.setType(0L);
			mav = new ModelAndView("global/ipSwitch");
		}

		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}

		Pager pager = new Pager();
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);

		Response<List<GlobalIpStruc>> resp = httpClient.invokeHttp(urlPath + queryIp, search, pager,
				new TypeReference<Response<List<GlobalIpStruc>>>() {
				});

		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", resp.getBody().getResult());
		mav.addObject("global_no", 3);
		mav.addObject("bStatus", bStatus);
		mav.addObject("wStatus", wStatus);
		return mav;
	}

	@RequestMapping(value = "/createIp")
	public ModelAndView createIP(@Valid IpCreateDto createDto, HttpServletRequest request) throws Exception {
		GlobalIpCreateRequest create = new GlobalIpCreateRequest();
		create.setIp(createDto.getIp1() + "." + createDto.getIp2() + "." + createDto.getIp3() + "."
				+ createDto.getIp4());
		create.setOperator(RequestContext.getCurrUser().getUserName());
		create.setPeriod(createDto.getPeriod());
		create.setType(createDto.getType());
		httpClient.invokeHttpWithoutResultType(urlPath + createIp, create);
		IpSearchDto search = new IpSearchDto();
		search.setPageNo(1L);
		search.setType(createDto.getType());
		return goIPSwitch(search, request);
	}

	@RequestMapping(value = "/deleteIp")
	public ModelAndView deleteIp(String ids, HttpServletRequest request) throws Exception {
		String[] ss = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (String str : ss) {
			idList.add(Long.parseLong(str));
		}
		GlobalIpDelRequest req = new GlobalIpDelRequest();
		req.setIds(idList);
		httpClient.invokeHttpWithoutResultType(urlPath + deleteIp, req);
		IpSearchDto search = new IpSearchDto();
		search.setPageNo(1L);
		search.setType(0L);
		return goIPSwitch(search, request);
	}

	@RequestMapping(value = "/checkCreateIp")
	@ResponseBody
	public Object checkCreateIp(String ip, Long type) throws Exception {
		GlobalIpCreateRequest create = new GlobalIpCreateRequest();
		create.setIp(ip);
		create.setType(type);
		OperateStatusResponse response = new OperateStatusResponse();
		response.setStatus(1);

		Response<GlobalIpStruc> resp = httpClient.invokeHttp(urlPath + checkCreateIp, create,
				new TypeReference<Response<GlobalIpStruc>>() {
				});
		if (resp.getHead().getStatus() == 1073) {
			response.setStatus(0);
			GlobalIpStruc struc = resp.getBody().getResult();
			response.setMessage("IP: " + struc.getIp() + " 未过期，" + "有效期为: " + struc.getEffectTime() + " - "
					+ struc.getExpireTime());
		}
		return response;

	}

	@RequestMapping(value = "/ipSwitch")
	@ResponseBody
	public Object ipSwitch(Long switchStatus, Long type) throws Exception {
		Config conf = new Config();
		conf.setModule("global");
		conf.setFunction("ipList");
		Response<ConfigSaveRequestDTO<Long>> resp = httpClient.invokeHttp(urlPath + queryConfigUrl, conf,
				new TypeReference<Response<ConfigSaveRequestDTO<Long>>>() {
				});
		long oldStatus = resp.getBody().getResult().getVal() == null ? -1 : resp.getBody().getResult().getVal()
				.longValue();

		OperateStatusResponse response = new OperateStatusResponse();
		DefaultConfig<Long> reqData = new DefaultConfig<Long>();
		if (switchStatus == 0) {
			reqData.setVal(-1L);
		} else if (switchStatus == 1L && type == 0L && oldStatus == -1) {
			reqData.setVal(0L);
		} else if (switchStatus == 1L && type == 1L && oldStatus == -1) {
			reqData.setVal(1L);
		} else if (switchStatus == 1L && oldStatus != -1) {
			response.setStatus(0l);
			return response;
		}
		reqData.setModule("global");
		reqData.setFunction("ipList");
		httpClient.invokeHttpWithoutResultType(urlPath + saveConfigUrl, reqData);
		response.setStatus(1l);
		return response;
	}
}
