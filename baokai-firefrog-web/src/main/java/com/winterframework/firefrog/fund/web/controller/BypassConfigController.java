package com.winterframework.firefrog.fund.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.ConfigValueRequestDTO;
import com.winterframework.firefrog.advert.web.dto.ConfigValueResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.dao.vo.BypassTable;
import com.winterframework.firefrog.fund.dao.vo.ChargeCountType;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.fund.web.dto.BypassConfigRequest;
import com.winterframework.firefrog.fund.web.dto.BypassConfigResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;


@Controller("bypassConfigController")
@RequestMapping("/fundAdmin")
public class BypassConfigController {
	private static final Logger logger = LoggerFactory.getLogger(BypassConfigController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.fund.bypassConfig")
	private String fundByPassUrl;
	
	@PropertyConfig(value="url.fund.saveBypassCfg")
	private String saveBypassCfg;
	
	@PropertyConfig(value="url.fund.queryFundBank")
	private String queryFundBank;
	
	@PropertyConfig(value="url.common.configGet")
	private String queryCongfig;
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Rechargemange/index")
	public String queryBypassConfigByType(HttpServletRequest request) {
		String parma=request.getParameter("parma");
		String index=request.getParameter("index");
		if(parma!=null) {
			if(parma.equals("saveBypassCfg")) {
				/*保存分流设置*/
				saveBypass(request);
			}else if(parma.equals("bypass")) {
				/*分流设置数据展示*/
				showBypassConfigData(request);
			}else if(parma.equals("sv2")) {
				/*充值上下限配置*/
				showFundBank(request);	
			}
		}
		return getMolde(parma,index);	
	}
	
	/**
	 * 查询银行信息
	 * @param request
	 */
	private void showFundBank(HttpServletRequest request) {
		Long userId=RequestContext.getCurrUser().getId();
		logger.info("testttttttttttt userId="+userId);
		Response<BankCardQueryRecordResponse> response=queryFundBank(userId);
		dealFundBankData(response,request);
	}
	
	/**
	 * 对查找到的数据进行处理
	 * @param response
	 * @param request
	 */
	private void dealFundBankData(Response<BankCardQueryRecordResponse> response, HttpServletRequest request) {
		String type=request.getParameter("type");
		HttpSession session = request.getSession();
		List<FundBank> payWayList;
		List<FundBank> bankList=new ArrayList<FundBank>();
		Map<String,List<FundBank>> bankMap=new HashMap<String, List<FundBank>>();
		List<String> chargeNameList=new ArrayList<String>();
		Map<String,String> countDownMinute=new HashMap<String,String>();
		if(response.getBody()!=null && response.getBody().getResult()!=null) {
			if((payWayList=response.getBody().getResult().getBankStruc())!=null) {
				for (FundBank fundBank : payWayList) {
					if(fundBank.getUrl()!=null && !fundBank.getUrl().equals("")) {
						if(fundBank.getName().contains("银行")) {
							bankList.add(fundBank);
						}else {
							List<FundBank> bankList2=new ArrayList<FundBank>();
							bankList2.add(fundBank);
							
							if(fundBank.getName().equals("支付宝")) {	
							bankMap.put(ChargeCountType.CHARGECOUTE_ALIPAY.getName(), bankList2);
							bankMap.put(ChargeCountType.CHARGECOUTE_ALIPAYBI.getName(), bankList2);
							chargeNameList.add(ChargeCountType.CHARGECOUTE_ALIPAY.getName());
							chargeNameList.add(ChargeCountType.CHARGECOUTE_ALIPAYBI.getName());
//							获取充值倒计时
							countDownMinute.put(ChargeCountType.CHARGECOUTE_ALIPAY.getName(),
									getChargeCountData(ChargeCountType.CHARGECOUTE_ALIPAY.getName()));
							countDownMinute.put(ChargeCountType.CHARGECOUTE_ALIPAYBI.getName(),
									getChargeCountData(ChargeCountType.CHARGECOUTE_ALIPAYBI.getName()));
							}else {
								bankMap.put(fundBank.getName(),bankList2);
								chargeNameList.add(fundBank.getName());
//								获取充值倒计时
								countDownMinute.put(fundBank.getName(), getChargeCountData(fundBank.getName()));
							}
						}
					}
				}
//				额外添加网银和快捷充值
//				ChargeCountType.CHARGECOUTE_DOWN.getName() :网银充值
//				ChargeCountType.CHARGECOUTE_THIRED.getName() :快捷充值
				chargeNameList.add(ChargeCountType.CHARGECOUTE_DOWN.getName());
				chargeNameList.add(ChargeCountType.CHARGECOUTE_THIRED.getName());
				bankMap.put(ChargeCountType.CHARGECOUTE_DOWN.getName(), bankList);
				bankMap.put(ChargeCountType.CHARGECOUTE_THIRED.getName(), bankList);
				countDownMinute.put(ChargeCountType.CHARGECOUTE_DOWN.getName(), getChargeCountData(ChargeCountType.CHARGECOUTE_DOWN.getName()));
				countDownMinute.put(ChargeCountType.CHARGECOUTE_THIRED.getName(), getChargeCountData(ChargeCountType.CHARGECOUTE_THIRED.getName()));
				Collections.reverse(chargeNameList);
//				放入session中
				session.setAttribute("countDownMinute", countDownMinute);
				session.setAttribute("bankList",type==null?bankMap.get(chargeNameList.get(0)):bankMap.get(chargeNameList.get(Integer.parseInt(type))));	
				session.setAttribute("chargeName",type==null? chargeNameList.get(0):chargeNameList.get(Integer.parseInt(type)));
				session.setAttribute("chargeNameList", chargeNameList);	
				session.setAttribute("index",type==null?0:type);
			}
		}		
	}

	/**
	 * 查出所有银行信息
	 * @param userId
	 * @return
	 */
	private Response<BankCardQueryRecordResponse> queryFundBank(Long userId) {
		Response<BankCardQueryRecordResponse> response = null;
		Request<BindDate> bindDate=new Request<BypassConfigController.BindDate>();
		RequestHeader requestHeader=new RequestHeader();
		requestHeader.setUserId(userId);
		bindDate.setHead(requestHeader);
		try {
			response=httpClient.invokeHttp(serverPath+queryFundBank,bindDate,
					new TypeReference<Response<BankCardQueryRecordResponse>>() {} );
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return response;
	}
	
	/**
	 * 查出每个银行设定的充值倒计时
	 * @param name
	 * @return
	 */
	private String getChargeCountData(String name) {
		Response<ConfigValueResponse> conFigResponse = null;
		ConfigValueRequestDTO configValueRequestDTO=new ConfigValueRequestDTO();
		String minute=null;
		String chargeCouteVal=null;
		if((chargeCouteVal=ChargeCountType.getValue(name))!=null) {
			configValueRequestDTO.setFunction(chargeCouteVal);
			configValueRequestDTO.setModule("fund");
			try {
				conFigResponse=httpClient.invokeHttp(serverPath+queryCongfig,configValueRequestDTO,
						new TypeReference<Response<ConfigValueResponse>>() {} );
			} catch (Exception e) {
				e.printStackTrace();
			}
			if((conFigResponse!=null && conFigResponse.getBody()!=null) && conFigResponse.getBody().getResult()!=null ){
				minute=conFigResponse.getBody().getResult().getVal();
			}
		}
		return minute;	
	}

	public static class BindDate {
		private Integer isBind;
		public Integer getIsBind() {
			return isBind;
		}
		public void setIsBind(Integer isBind) {
			this.isBind = isBind;
		}

	}
	/**
	 * 查询并展示数据
	 * @param request
	 */
	private void showBypassConfigData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String type=request.getParameter("type");
		Map<Long,List<BypassConfigVO>>  bypassMap=new HashMap<Long, List<BypassConfigVO>>();
		@SuppressWarnings("unused")
		Response<BypassConfigResponse> bypassconfigResponse = null;
		BypassConfigRequest byreq=new BypassConfigRequest();
		byreq.setType((long) 0);
		try {
			bypassconfigResponse = httpClient.invokeHttp(serverPath+fundByPassUrl,byreq,
					new TypeReference<Response<BypassConfigResponse>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		bypassMap=getBypassTableList(bypassconfigResponse,false);
		session.setAttribute("bypassMap", bypassMap);
		session.setAttribute("bypassList",bypassMap.get(type==null?2L:Long.parseLong(type)));
		session.setAttribute("index",type==null?2:Long.parseLong(type));
	}

	/**
	 * 保存数据
	 * @param request
	 */
	private void saveBypass(HttpServletRequest request) {
		String chargeWaySet=request.getParameter("type");
		String [] lowLimit=request.getParameterValues("lowLimit");
		String [] upLimit=request.getParameterValues("upLimit");
		String [] dailyLimit=request.getParameterValues("dailyLimit");
		String [] dpSwitch2=request.getParameterValues("dpSwitch2");
		String [] bypassId=request.getParameterValues("bypassId");
		save(lowLimit,upLimit,dailyLimit,dpSwitch2,bypassId);
		//------------------
		if(chargeWaySet!=null) {
			Response<BypassConfigResponse> bypassconfigResponse = null;
			BypassConfigRequest byreq2=new BypassConfigRequest();
			byreq2.setType((long) 0);
			try {
				bypassconfigResponse = httpClient.invokeHttp(serverPath+fundByPassUrl,byreq2,
						new TypeReference<Response<BypassConfigResponse>>() {});
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<Long,List<BypassConfigVO>>  bypassMap=getBypassTableList(bypassconfigResponse,true);
			List<BypassConfigVO> bypassConfigVOList2=bypassMap.get(Long.parseLong(chargeWaySet));
			String []bypassId2=new String[bypassId.length];
			for (int i=0;i<bypassConfigVOList2.size();i++) {
				bypassId2[i]=String.valueOf(bypassConfigVOList2.get(i).getId());
			}
			save(lowLimit,upLimit,dailyLimit,dpSwitch2,bypassId2);
		}
	}
	
	private void save( String[] lowLimit, String[] upLimit, String[] dailyLimit, String[] dpSwitch2, String[] bypassId) {
		List<BypassConfigVO> bypassConfigVOList=new ArrayList<BypassConfigVO>();
			for(int i=0;i<bypassId.length;i++) {
				BypassConfigVO bypassVO=new BypassConfigVO();
				bypassVO.setSingleUplimit(Long.parseLong(upLimit[i]));
				bypassVO.setSingleLowlimit(Long.parseLong(lowLimit[i]));
				bypassVO.setDailyUplimit(Long.parseLong(dailyLimit[i]));     
				bypassVO.setIsOpen(dpSwitch2[i]);
				bypassVO.setId(Long.parseLong(bypassId[i]));
				bypassConfigVOList.add(bypassVO);
			}
			BypassConfigRequest byreq=new BypassConfigRequest();
			byreq.setBypassList(bypassConfigVOList);
			try {
				httpClient.invokeHttpWithStringResult(serverPath+saveBypassCfg,byreq);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * 对查询到的数据进行处理
	 * @param bypassconfigResponse
	 * @param bypasstableList
	 * @return
	 */
	private Map<Long,List<BypassConfigVO>> getBypassTableList(Response<BypassConfigResponse> bypassconfigResponse,boolean flag) {
		BypassTable bypassTable=new BypassTable();
		if(bypassconfigResponse.getBody()!=null) {
			BypassConfigResponse bypass=bypassconfigResponse.getBody().getResult();
			if(bypass!=null) {
				for (BypassConfigVO vo : bypass.getBypassCfgs()) {
					BypassTable bypassTable2 =chargeData(vo.getChargeWaySet(),vo,bypassTable);
					bypassTable.setBypassMap(bypassTable2.getBypassMap());
					bypassTable.setNextBypassMap(bypassTable2.getNextBypassMap());
				}
			}
		}
		if(flag) {
			return bypassTable.getNextBypassMap();
		}else {
			return bypassTable.getBypassMap();
		}	
	}
	/**
	 * 对查询到的数据进行处理
	 * @param chargeWay
	 * @param vo
	 * @param bypassMap
	 * @return
	 */
	private BypassTable chargeData(Long chargeWay, BypassConfigVO vo,BypassTable bypassTable) {
		List<BypassConfigVO> list=new ArrayList<BypassConfigVO>();
		List<BypassConfigVO> list2=new ArrayList<BypassConfigVO>();
		Map<Long,List<BypassConfigVO>>  bypassMap=bypassTable.getBypassMap();
		Map<Long,List<BypassConfigVO>>  bypassMap2=bypassTable.getNextBypassMap();
		if(bypassMap==null && vo.getChargeWaySet()!=null ||
				bypassMap.get(chargeWay)==null &&  vo.getChargeWaySet()!=null) {
			list.add(vo);
		}else {
			boolean flag=true;
			list=bypassMap.get(chargeWay);
			if(bypassTable.getNextBypassMap()!=null) {
				list2=bypassMap2.get(chargeWay);
			}
			for (BypassConfigVO bypassConfigVO : list) {
				if(vo.getChargeWaySet()==null) {
					flag=false;	
				}	
				if(bypassConfigVO.getAgency()==vo.getAgency()) {
					flag=false;
					list2.add(vo);
				}
			}
			if(flag) {
				list.add(vo);
			}	
		}
		bypassMap.put(chargeWay, list);
		bypassMap2.put(chargeWay, list2);
		bypassTable.setBypassMap(bypassMap);
		bypassTable.setNextBypassMap(bypassMap2);
		return bypassTable;
	}

	/**
	 * 定向跳转
	 * @param parma
	 * @param index
	 * @return
	 */
	private String getMolde(String parma, String index) {
		if(parma.equals("bypass")) {
			return "/fund/bypassShunt";
		}else if(parma.equals("sv2") && Integer.parseInt(index)==0) {
			return "/fund/bypassBound";
		}else if(parma.equals("sv2") && Integer.parseInt(index)==1) {
			return "/fund/bypassRevert";
		}
		return null;
	}
}
