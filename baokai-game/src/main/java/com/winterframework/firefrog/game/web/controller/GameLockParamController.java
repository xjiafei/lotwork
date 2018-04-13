/**   
* @Title: GameLockParamController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-12 下午2:10:20 
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

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameLockParam;
import com.winterframework.firefrog.game.service.IGameLockParamService;
import com.winterframework.firefrog.game.web.dto.GameLockParamRequest;
import com.winterframework.firefrog.game.web.dto.GameLockParamResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameLockParamController 
* @Description: 变价Controller 
* @author floy
*  
*/
@Controller("gameLockParamController")
@RequestMapping("/game")
public class GameLockParamController {
	private Logger log = LoggerFactory.getLogger(GameLockParamController.class);
	@Resource(name = "gameLockParamServiceImpl")
	private IGameLockParamService gameLockParamService;
	private static final String DATE_FORMATE = "HH:mm:ss";

	/** 
	* @Title: queryGameLock 
	* @Description: 查询变价配置
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryGameLockParam")
	@ResponseBody
	public Response<GameLockParamResponse> queryGameLockParam(@RequestBody Request<Long> request) throws Exception {
		Response<GameLockParamResponse> response = new Response<GameLockParamResponse>(request);
		try {
			GameLockParam gameLockParam = gameLockParamService.queryGameLockParam(request.getBody().getParam());
			GameLockParamResponse result = new GameLockParamResponse();
			result.setId(gameLockParam.getId());
			result.setGameId(gameLockParam.getGameId());
			result.setEndTime(DateUtils.format(gameLockParam.getEndTime(), DATE_FORMATE));
			result.setEndTimeProcess(DateUtils.format(gameLockParam.getEndTimeProcess(), DATE_FORMATE));
			result.setMinVal(gameLockParam.getMinVal());
			result.setMinValProcess(gameLockParam.getMinValProcess());
			result.setModifier(gameLockParam.getModifierStr());
			result.setOperator(gameLockParam.getOperator());
			result.setStartTime(DateUtils.format(gameLockParam.getStartTime(), DATE_FORMATE));
			result.setStartTimeProcess(DateUtils.format(gameLockParam.getStartTimeProcess(), DATE_FORMATE));
			result.setStatus(gameLockParam.getStatus());
			response.setResult(result);
		} catch (Exception e) {
			log.error("query gameLock param error", e);
		}
		return response;
	}

	/** 
	* @Title: updateGameConfig 
	* @Description: 修改配置
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/updateGameLockParam")
	@ResponseBody
	public Response<Object> updateGameLockParam(@RequestBody Request<GameLockParamRequest> request) throws Exception {
		log.info("updateGameConfig start");
		Response<Object> response = new Response<Object>(request);
		GameLockParamRequest gameLockParamRequest = request.getBody().getParam();
		try {
			GameLockParam gameLockParam = new GameLockParam();
			gameLockParam.setEndTime(DateUtils.parse(gameLockParamRequest.getEndTime(), DATE_FORMATE));
			gameLockParam.setEndTimeProcess(DateUtils.parse(gameLockParamRequest.getEndTimeProcess(), DATE_FORMATE));
			gameLockParam.setGameId(gameLockParamRequest.getGameId());
			gameLockParam.setId(gameLockParamRequest.getId());
			gameLockParam.setMinVal(gameLockParamRequest.getMinVal());
			gameLockParam.setMinValProcess(gameLockParamRequest.getMinValProcess());
			if (gameLockParamRequest.getStatus() == 1l) {//修改
				gameLockParam.setModifierStr(request.getHead().getUserAccount());
			}
			if (gameLockParamRequest.getStatus() == 2l || gameLockParamRequest.getStatus() == 3l) {//审核通过和审核未通过
				gameLockParam.setOperator(request.getHead().getUserAccount());
			}

			gameLockParam.setStartTime(DateUtils.parse(gameLockParamRequest.getStartTime(), DATE_FORMATE));
			gameLockParam
					.setStartTimeProcess(DateUtils.parse(gameLockParamRequest.getStartTimeProcess(), DATE_FORMATE));
			gameLockParam.setStatus(gameLockParamRequest.getStatus());
			gameLockParamService.updateGameLockParam(gameLockParam);
		} catch (Exception e) {
			log.error("updateGameConfig error ", e);
			throw e;
		}
		return response;
	}

}
