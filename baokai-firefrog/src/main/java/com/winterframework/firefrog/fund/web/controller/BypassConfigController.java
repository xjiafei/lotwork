/**   
* @Title: BankCardController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: 银行卡管理Controller 
* @author Denny  
* @date 2013-7-1 下午4:44:27 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Optional;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.service.IBypassConfigService;
import com.winterframework.firefrog.fund.web.dto.BypassConfigRequest;
import com.winterframework.firefrog.fund.web.dto.BypassConfigResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: BankCardController 
* @Description: 银行卡管理Controller
* @author Denny 
* @date 2013-7-1 下午4:44:27 
*  
*/
@Controller("bypassConfigController")
@RequestMapping("/fund/bypass")
public class BypassConfigController {

	private static final Logger log = LoggerFactory.getLogger(BypassConfigController.class);
	
	@Resource(name = "bypassConfigServiceImpl")  
	private IBypassConfigService bypassConfigService;
	
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	/** 
	* @Title: queryBypassAgency
	* @Description: 查询分流設置(充值or提現)
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryBypassConfigByType")
	@ResponseBody
	public Response<BypassConfigResponse> queryBypassConfigByType(
			@RequestBody @ValidRequestBody Request<BypassConfigRequest> request) throws Exception {
		Response<BypassConfigResponse> response = new Response<BypassConfigResponse>(request);
		try {
			if (null != request.getBody()) {
				BypassConfigVO entity = new BypassConfigVO();
				Long type = request.getBody().getParam().getType();
				entity.setType(type); 
				entity.setIsBypassView("Y"); 
				Optional<List<BypassConfigVO>> bypassOptional = bypassConfigService.findByCondition(entity);
				if(bypassOptional.isPresent() && !bypassOptional.get().isEmpty()){
					BypassConfigResponse res = new BypassConfigResponse();
					//如為充值 則多抓每日充值金額
					if(type.intValue() == 0){
						log.info("tyPE : " + type.intValue());
						for(BypassConfigVO vo:bypassOptional.get()){
							log.info("AGENCY 0:dp 1:th 3:ECPSS 4:HBPay 7:WorthPay-> " + vo.getAgency());
							log.info("CHARGEWAY_SET 2:QUCIK 5:APPUNIPAY 7:WECHAT-> " + vo.getChargeWaySet());
							long dailyCharge = bypassConfigService.getDailySumByType(type, vo.getAgency(),vo.getChargeWaySet());
							vo.setDailyCharge(dailyCharge/10000);
							log.info("DailySum="+vo.getDailyCharge());
						}
					}
					res.setBypassCfgs(bypassOptional.get());
					response.getBody().setResult(res);
				}
			}
		}catch(Exception e){
			log.error(" queryBypassConfigByType error:", e);
			throw e;
		}
		return response;
 	}
	
	/** 
	* @Title: queryBypass
	* @Description: 修改充值分流設置
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/saveBypassCfg")
	@ResponseBody
	public String saveBypassCfg(@RequestBody @ValidRequestBody Request<BypassConfigRequest> request) throws Exception {
		log.info("-----------saveBypassCfg----------");
		log.info("adminUser is "+request.getHead().getUserAccount());
		List<BypassConfigVO> lists = request.getBody().getParam().getBypassList();
		try{
			if(lists != null && lists.size() > 0){
				bypassConfigService.updateBypass(lists);
			}
		}catch(Exception e){
			log.error(" saveBypassCfg error:", e);
			throw e;
		}
		return "success";
	}
}
