/**   
* @Title: GameSeriesConfigRiskController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-21 上午11:23:22 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.service.IGameRiskConfigService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigRiskRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRiskResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameSeriesConfigRiskController 
* @Description: 运营参数（审核模块）操作类
* @author Alan
* @date 2014-2-21 上午11:23:22 
*  
*/
@Controller("gameSeriesConfigRiskController")
@RequestMapping("/game")
public class GameSeriesConfigRiskController {
	
	private Logger log = LoggerFactory.getLogger(GameSeriesConfigRiskController.class);
	
	@Resource(name = "gameRiskConfigServiceImpl")
	private IGameRiskConfigService gameRiskConfigService;
	
	/**
	 * 
	* @Title: queryGameSeriesConfigForRisk 
	* @Description:  查询运营参数信息（审核模块）
	* @param request
	* @return Response<QuerySeriesConfigRiskResponse>
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGameSeriesConfigRisk")
	@ResponseBody
	public Response<QuerySeriesConfigRiskResponse> queryGameSeriesConfigForRisk() throws Exception {
		Response<QuerySeriesConfigRiskResponse> response = new Response<QuerySeriesConfigRiskResponse>();
		
		try {
			
			GameRiskConfig config = gameRiskConfigService.selectGameRiskConfig();
			
			QuerySeriesConfigRiskResponse result = DTOConvert.GameRiskConfig2QuerySeriesConfigRiskResponse(config);
			
			response.setResult(result);
				
			
		} catch (Exception e) {
			
			log.error("查询运营参数信息（审核模块）错误：", e);
			throw e;
		}
		
		return response;
	}
	
	/**
	 * 
	* @Title: editGameSeriesConfig 
	* @Description: 修改运营参数信息（审核模块）
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/editGameSeriesConfigRisk")
	@ResponseBody
	public Response<Object> editGameSeriesConfig(@RequestBody @ValidRequestBody Request<EditSeriesConfigRiskRequest> request) throws Exception{
		
		Response<Object> response = new Response<Object>(request);
		
		try {
			
			if(null != request.getBody()){
				
				EditSeriesConfigRiskRequest edit = request.getBody().getParam();
				
				GameRiskConfig config = new GameRiskConfig();
				
				config.setOrderwarnContinuousWins(edit.getOrderwarnContinuousWins());
				config.setOrderwarnMaxwins(edit.getOrderwarnMaxwins() * 10000);
				config.setOrderwarnSlipWinsratio(edit.getOrderwarnSlipWinsRatio() * 10000);
				config.setOrderwarnWinsRatio(edit.getOrderwarnWinsRatio() * 10000);
				config.setOrderwarnMaxslipWins(edit.getOrderwarnMaxslipWins() * 10000);
				
				gameRiskConfigService.updateGameRiskConfig(config);
			}
			
		} catch (Exception e) {
			log.error("修改运营参数信息（审核模块）错误：", e);
			throw e;
		}
		
		return response;
	}

}
