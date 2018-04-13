/**   
* @Title: GameRiskFlowController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: （审核模块）审核流程 
* @author hugh 
* @date 2014-2-21 上午11:23:22 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.comtroller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.game.service.IGameRiskConfigService;
import com.winterframework.firefrog.game.web.dto.GameRiskRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameRiskFlowController 
* @Description:（审核模块）审核流程  验奖（ 派发奖金 派发返点）  撤销  重做 入口
* @author Hugh
* @date 2014-4-22 上午11:23:22 
*  
*/
@Controller("gameRiskFlowController")
@RequestMapping("/gameRisk")
public class GameRiskFlowController {

	private Logger log = LoggerFactory.getLogger(GameRiskFlowController.class);

	@Resource(name = "gameRiskConfigServiceImpl")
	private IGameRiskConfigService gameRiskConfigService;

	/**
	 * 
	* @Title: riskFlow 
	* @Description:  验奖派奖入口
	* @param request
	* @return Response<GameRiskResponse>
	* @throws Exception
	 */
	@RequestMapping(value = "/riskFlow")
	@ResponseBody
	public Response<GameRiskResponse> riskFlow(Request<GameRiskRequest> request) throws Exception {
		Response<GameRiskResponse> response = new Response<GameRiskResponse>();
		GameRiskResponse res = new GameRiskResponse();
		try {
			//res = null;

		} catch (Exception e) {
			log.error("验奖异常", e);
			res.setResultStatus(1L);
			res.setExceptionMessage("验奖异常：" + e.getMessage());
		}
		response.setResult(res);
		return response;
	}

	/**
	 * 
	* @Title: cancelRiskFlow 
	* @Description:  撤销验奖派奖入口
	* @param request
	* @return Response<GameRiskResponse>
	* @throws Exception
	 */
	@RequestMapping(value = "/cancelRiskFlow")
	@ResponseBody
	public Response<GameRiskResponse> cancelRiskFlow(Request<GameRiskRequest> request) throws Exception {
		Response<GameRiskResponse> response = new Response<GameRiskResponse>();
		GameRiskResponse res = new GameRiskResponse();
		try {
			//res = null;

		} catch (Exception e) {
			log.error("撤销验奖派奖异常", e);
			res.setResultStatus(1L);
			res.setExceptionMessage("撤销验奖派奖异常：" + e.getMessage());
		}
		response.setResult(res);
		return response;
	}

	/**
	 * 
	* @Title: redoRiskFlow 
	* @Description:  重做验奖派奖入口
	* @param request
	* @return Response<GameRiskResponse>
	* @throws Exception
	 */
	@RequestMapping(value = "/redoRiskFlow")
	@ResponseBody
	public Response<GameRiskResponse> redoRiskFlow(Request<GameRiskRequest> request) throws Exception {
		Response<GameRiskResponse> response = new Response<GameRiskResponse>();
		GameRiskResponse res = new GameRiskResponse();
		try {
			//res = null;

		} catch (Exception e) {
			log.error("重做验奖派奖异常", e);
			res.setResultStatus(1L);
			res.setExceptionMessage("重做验奖派奖异常：" + e.getMessage());
		}
		response.setResult(res);
		return response;
	}

}
