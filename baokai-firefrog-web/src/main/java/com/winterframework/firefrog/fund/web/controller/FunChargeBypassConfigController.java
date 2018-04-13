package com.winterframework.firefrog.fund.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("funChargeBypassConfigController")
@RequestMapping("/fundAdmin")
public class FunChargeBypassConfigController {
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value="url.fund.queryBypassWhite")
	private String queryBypassWhiteList;
	
	@PropertyConfig(value="url.fund.deleteBypassWhite")
	private String deleteBypassWhite;
	
	@PropertyConfig(value="url.fund.deleteBypassWhite.isAccountExist")
	private String isAccountExist;
	
	@PropertyConfig(value="url.fund.saveWhiteList")
	private String saveWhiteList;
	
	
	@RequestMapping(value = "/FundRechargemange/index")
	@ResponseBody
	public String queryBypassConfigByType(HttpServletRequest request) {
		String parma=request.getParameter("parma");
		if(parma!=null) {
			if (parma.equals("whitelist")) {
				return queryChargeBypassWhiteList(request);
			}else if(parma.equals("deleteBypassWhiteListData")) {
				deleteBypassWhite(request);
			}else if(parma.equals("saveItem")) {
				/*判断白名单是否存在改用户*/
				/*如果不存在就保存该数据，如果存在就返回字符串*/
				if(!isAccountExist(request)) {
					saveBypassWhite(request);
				}else {
					return "已经存在该用户";
				}
			}	
		}
		return null;
	}
	
	
	/**
	 * 保存白名单数据
	 * @param request
	 */
	private void saveBypassWhite(HttpServletRequest request) {
		if(request.getParameter("account") !=null && request.getParameter("memo") != null
				&& request.getParameter("chargeWaySetItem") !=null && request.getParameter("agencyItem") !=null) {
			FundChargeBypassRequest fundChargeBypassRequest=new FundChargeBypassRequest();
			fundChargeBypassRequest.setAccount(request.getParameter("account"));
			fundChargeBypassRequest.setMemo(request.getParameter("memo"));
			fundChargeBypassRequest.setChargeWaySet(Long.parseLong(request.getParameter("chargeWaySetItem")));
			fundChargeBypassRequest.setChargeChannel(Long.parseLong(request.getParameter("agencyItem")));
			try {
				httpClient.invokeHttpWithoutResultType(serverPath+saveWhiteList, fundChargeBypassRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 *判断白名单用户是否存在
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isAccountExist(HttpServletRequest request) {
		String isExist="N";
		if(request.getParameter("account")!=null && request.getParameter("chargeWaySetItem") !=null) {
			String account=request.getParameter("account");
			String chargeWaySet=request.getParameter("chargeWaySetItem");
			if(account!=null && chargeWaySet !=null) {
				FundChargeBypassRequest fundChargeBypassRequest=new FundChargeBypassRequest();
				fundChargeBypassRequest.setAccount(account);
				fundChargeBypassRequest.setChargeWaySet(Long.parseLong(chargeWaySet));
				Response<FundChargeBypassResponse> response = null;
				try {
					response=httpClient.invokeHttp(serverPath+isAccountExist, fundChargeBypassRequest,
							new TypeReference<Response<FundChargeBypassResponse>>() {});
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (response!=null && response.getBody()!=null) {
					isExist =response.getBody().getResult().getIsExist();
				}
			}
		}
		return isExist.equals("Y")?true:false;	
	}
	/**
	 * 删除白名单
	 * @param request  FundChargeBypassRequest
	 */
	private void deleteBypassWhite(HttpServletRequest request) {
		String id=request.getParameter("deleteId");
		if(id!=null && !id.equals("")) {
			FundChargeBypassRequest fundChargeBypassRequest=new FundChargeBypassRequest();
			fundChargeBypassRequest.setDeleteId(Long.parseLong(id));
			 try {
				httpClient.invokeHttpWithStringResult(serverPath+deleteBypassWhite, fundChargeBypassRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询白名单列表
	 * @param request 
	 */
	private String queryChargeBypassWhiteList(HttpServletRequest request) {
		String chargeChannelStr= request.getParameter("chargeChannel");
		String chargeWaySetStr=request.getParameter("chargeWaySet");
		String pageNoStr=request.getParameter("pageNo");
		String fundChargeBypassStr=null;
		if(chargeChannelStr!=null && chargeWaySetStr!=null) {
			FundChargeBypassRequest fundChargeBypassRequest=new FundChargeBypassRequest();
			fundChargeBypassRequest.setAccount(null);
			fundChargeBypassRequest.setChargeChannel(Long.parseLong(chargeChannelStr));
			fundChargeBypassRequest.setChargeWaySet(Long.parseLong(chargeWaySetStr));
			fundChargeBypassRequest.setPageNo(Long.parseLong(pageNoStr==null?"0":pageNoStr));
			@SuppressWarnings("unused")
			Response<FundChargeBypassResponse> response = null;
			try {
				response = httpClient.invokeHttp(serverPath+queryBypassWhiteList,fundChargeBypassRequest,
						new TypeReference<Response<FundChargeBypassResponse>>() {});
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(response != null && response.getBody() != null) {
				FundChargeBypassResponse fundChargeBypassResponse=response.getBody().getResult();
				if(fundChargeBypassResponse!=null) {
					ObjectMapper mapper = new ObjectMapper();
					try {
						fundChargeBypassStr=mapper.writeValueAsString(fundChargeBypassResponse);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return fundChargeBypassStr;
	}	
}
