package com.winterframework.firefrog.fund.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.Param;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.dao.vo.ChargeCountType;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.RtnStruc;
import com.winterframework.firefrog.fund.web.controller.BypassConfigController.BindDate;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.fund.web.dto.ConfigSaveRequestDTO;
import com.winterframework.firefrog.fund.web.dto.ConfigValueResponse;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;
@Controller("fundBankConfigController")
@RequestMapping("/fundAdmin")
public class FundBankConfigController {
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value="url.fund.queryFundBank")
	private String queryFundBank;
	
	@PropertyConfig(value="url.fund.saveFundBank")
	private String saveFundBank;
	
	@PropertyConfig(value="url.common.configSave")
	private String saveConfig;
	
	@RequestMapping(value = "/fundbankManage/savebankcfg")
	@ResponseBody
	public String saveBankData(HttpServletRequest request) {
		String index=request.getParameter("index");
		String type=request.getParameter("type");
		String [] lowLimit=request.getParameterValues("lowLimit");
		String [] upLimit=request.getParameterValues("upLimit");
		String [] vipLowLimit=request.getParameterValues("vipLowLimit");
		String [] vipUpLimit=request.getParameterValues("vipUpLimit");
		String minute=request.getParameter("charge_ratio");
		String [] version=request.getParameterValues("version");
		String [] code=request.getParameterValues("code");
		if(index!=null && type !=null && code.length!=0) {
			FundBank[] fundBankArray=new FundBank[code.length];
			for(int i=0;i<code.length;i++) {
				if(type.equals(ChargeCountType.CHARGECOUTE_THIRED.getName()) || type.equals(ChargeCountType.CHARGECOUTE_ALIPAYBI)) {
					FundBank fundBank=new FundBank();
					fundBank.setCode(code[i].trim());
					fundBank.setVersion(version[i].equals("")||version[i]==null ?null:Long.parseLong(version[i].trim()));
					fundBank.setOtherdownlimit(Long.parseLong(lowLimit[i].trim())*10000);
					fundBank.setOtheruplimit(Long.parseLong(upLimit[i].trim())*10000);
					fundBank.setOthervipdownlimit(Long.parseLong(vipLowLimit[i].trim())*10000);
					fundBank.setOthervipuplimit(Long.parseLong(vipUpLimit[i].trim())*10000);
					fundBankArray[i]=fundBank;
				}else {
					FundBank fundBank=new FundBank();
					fundBank.setCode(code[i].trim());
					fundBank.setVersion(version[i].equals("")||version[i]==null ?null:Long.parseLong(version[i].trim()));
					fundBank.setLowlimit(Long.parseLong(lowLimit[i].trim())*10000);
					fundBank.setUplimit(Long.parseLong(upLimit[i].trim())*10000);
					fundBank.setViplowlimit(Long.parseLong(vipLowLimit[i].trim())*10000);
					fundBank.setVipuplimit(Long.parseLong(vipUpLimit[i].trim())*10000);
					fundBankArray[i]=fundBank;
				}
			}
			toStorageConfigValue(minute,type,"fund");
			toStorageFundank(fundBankArray);
			
		}
		return null;
	}

	private void toStorageFundank(FundBank[] fundBankArray) {
		if(fundBankArray.length>0) {
			try {
				httpClient.invokeHttpWithStringResult(serverPath+saveFundBank,fundBankArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	private void toStorageConfigValue(String minute,String type,String model) {
		
		if(minute!=null && !minute.equals("")) {
			ConfigSaveRequestDTO<Long> configSavereq=new ConfigSaveRequestDTO<Long>();
			configSavereq.setFunction(ChargeCountType.getValue(type));
			configSavereq.setModule(model==null?"fund":model);
			configSavereq.setVal(Long.parseLong(minute));
			try {
				httpClient.invokeHttp(serverPath+saveConfig,configSavereq,
						new TypeReference<Response<ConfigSaveRequestDTO<String>>>() {} );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/fundbankManage/queryData")
	@ResponseBody
	public String showBankData(HttpServletRequest request) {
		String code=request.getParameter("code");
		String fundBankStr=null;
		if(code!=null && !code.equals("")) {
			FundBank fundBank=queryBankData(code);
			ObjectMapper mapper = new ObjectMapper();
			try {
				fundBankStr=mapper.writeValueAsString(fundBank);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fundBankStr;	
	}

	private FundBank queryBankData(String code) {
		// TODO Auto-generated method stub
		Long userId=RequestContext.getCurrUser().getId();
		Response<BankCardQueryRecordResponse> response=queryFundBank(userId);
		List<FundBank> payWayList;
		if(response.getBody()!=null && response.getBody().getResult()!=null) {
			if((payWayList=response.getBody().getResult().getBankStruc())!=null) {
				for (FundBank fundBank : payWayList) {
					if(fundBank.getCode().equals(code)) {
						return fundBank;
					}
				}
			}
		}
		return null;	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return response;
	}
	
	@RequestMapping(value="/fundbankManage/savePoundage")
	@ResponseBody
	public String savePoundage(HttpServletRequest request) {
		String code=request.getParameter("itemCode");
		if(code!=null && !code.equals("")) {
			FundBank fundBank=new FundBank();
			String retnMin=request.getParameter("rtnMin");
			fundBank.setRtnMin(retnMin);
			fundBank.setCode(code);
			String rtnSet=request.getParameter("rtnSet");
			if(rtnSet!=null) {
				Long rtnSetL=getLongVal(rtnSet);
				fundBank.setRtnSet(rtnSetL);
				if(rtnSetL==2) {
					String rtnStrucLength=request.getParameter("rtnStrucLength");
					String []smArray=request.getParameterValues("rtnStrucSM");
					String []bigArray=request.getParameterValues("rtnStrucBig");
					String []rtnValue=request.getParameterValues("money");
					if(rtnStrucLength!=null) {
						int length=Integer.parseInt(rtnStrucLength);
						String [] rtnStrucType=new String [length];
						for(int i=0;i<length;i++) {
							rtnStrucType[i]=request.getParameter("radioVal_"+i);
						}
						List<RtnStruc> rtnStrucs=new ArrayList<RtnStruc>();
						for(int i=0;i<length;i++) {
							RtnStruc rtnStruc=new RtnStruc();
							rtnStruc.setSm(getLongVal(smArray[i])*10000);
							rtnStruc.setBig(getLongVal(bigArray[i])*10000);
							rtnStruc.setType(getLongVal(rtnStrucType[i]));
							rtnStruc.setValue(BigDecimal.valueOf(getLongVal(rtnValue[i])*10000));
							rtnStrucs.add(rtnStruc);
						}
						 fundBank.setRtnStruc(rtnStrucs);
					}
				}
				tosaveFundBank(fundBank);
			}
		}
		return null;
	}
	
	private void tosaveFundBank(FundBank fundBank) {
		FundBank [] fundBankArray=new FundBank[1];
		fundBankArray[0]=fundBank;
		if(fundBankArray.length>0) {
			try {
				httpClient.invokeHttpWithStringResult(serverPath+saveFundBank,fundBankArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Long getLongVal(String stringVal) {
		if(stringVal!=null && !stringVal.equals("")) {
			return Long.parseLong(stringVal);
		}
		return 0L;
	}
}
