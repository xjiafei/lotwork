package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.service.IGameMultipleService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameMultipleQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameMultipleQueryResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameMutipleController 
* @Description: 游戏投注限制Controller
* @author Alan
* @date 2013-8-26 下午2:05:18 
*  
*/
@Controller("gameMultipleController")
@RequestMapping("/game")
public class GameMultipleController {
	
	private Logger log = LoggerFactory.getLogger(GameMultipleController.class);
	
	@Resource(name = "gameMultipleServiceImpl")
	private IGameMultipleService gameMultipleService;

	/** 
	* @Title: queryMaxMutiple 
	* @Description: 4.14 线上投注限制查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryMaxMutiple")
	@ResponseBody
	public Response<GameMultipleQueryResponse> queryMaxMutiple(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<GameMultipleQueryRequest> request) throws Exception {
		Response<GameMultipleQueryResponse> response = new Response<GameMultipleQueryResponse>();
		
		try{
			
			if(null != request.getBody()){
				GameMultipleQueryRequest queryRequest = request.getBody().getParam();
				GameMultipleQueryResponse queryResponse = new GameMultipleQueryResponse();
				
				GameMultipleEntity entity = DTOConvert.gameMutipleQueryRequest2GameMutipleEntity(queryRequest);
				
				GameMultipleEntity gameMutiple = gameMultipleService.queryGameMultiple(entity);
				
				queryResponse.setMaxMutiple(gameMutiple.getMaxMultiple());
				queryResponse.setMaxCountIssue(gameMutiple.getMaxCountIssue());
				
				response.setResult(queryResponse);
			}
			
		}catch(Exception e){
			log.error("查询投注限制失败:", e);
			throw e;
		}
		
		return response;
	}

}
