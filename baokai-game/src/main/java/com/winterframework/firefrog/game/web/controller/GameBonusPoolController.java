/**   
* @Title: GameBonusPoolController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午3:50:05 
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

import com.winterframework.firefrog.game.dao.vo.GameBonusPool;
import com.winterframework.firefrog.game.service.IGameBonusPoolService;
import com.winterframework.firefrog.game.web.dto.GameBonusPoolRequest;
import com.winterframework.firefrog.game.web.dto.GameBonusPoolStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameBonusPoolController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午3:50:05 
*  
*/
@Controller("gameBonusPoolController")
@RequestMapping("/game")
public class GameBonusPoolController {

	private Logger log = LoggerFactory.getLogger(GameBonusPoolController.class);

	@Resource(name = "gameBonusPoolServiceImpl")
	private IGameBonusPoolService gameBonusPoolServiceImpl;

	@RequestMapping(value = "/queryGameBonusPool")
	@ResponseBody
	public Response<GameBonusPoolStruc> queryGameBonusPool(@RequestBody Request<Long> request) throws Exception {
		Response<GameBonusPoolStruc> response = new Response<GameBonusPoolStruc>(request);
		try {
			GameBonusPool gameBonusPool = gameBonusPoolServiceImpl.queryGameBonus(request.getBody().getParam());
			GameBonusPoolStruc gameBonusStruc = new GameBonusPoolStruc();
			gameBonusStruc.setId(gameBonusPool.getId());
			gameBonusStruc.setActualBonus(gameBonusPool.getActualBonus());
			gameBonusStruc.setActualBonusProcess(gameBonusPool.getActualBonusProcess());
			gameBonusStruc.setChangeReason(gameBonusPool.getChangeReason());
			gameBonusStruc.setDistribute1(gameBonusPool.getDistribute1());
			gameBonusStruc.setDistribute1process(gameBonusPool.getDistribute1process());
			gameBonusStruc.setDistribute2(gameBonusPool.getDistribute2());
			gameBonusStruc.setDistribute2process(gameBonusPool.getDistribute2process());
			gameBonusStruc.setLotteryid(gameBonusPool.getLotteryid());
			gameBonusStruc.setMinimumBonus(gameBonusPool.getMinimumBonus());
			gameBonusStruc.setMinimumBonusProcess(gameBonusPool.getMinimumBonusProcess());
			gameBonusStruc.setStatus(gameBonusPool.getStatus());
			response.setResult(gameBonusStruc);
		} catch (Exception e) {
			log.error("query gameBonusPool error", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/updateGameBonusPool")
	@ResponseBody
	public Response<Object> updateGameBonusPool(@RequestBody Request<GameBonusPoolRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			GameBonusPoolRequest gameBonusStruc = request.getBody().getParam();
			GameBonusPool gameBonusPool = new GameBonusPool();
			gameBonusPool.setId(gameBonusStruc.getId());
			gameBonusPool.setActualBonus(gameBonusStruc.getActualBonus());
			gameBonusPool.setActualBonusProcess(gameBonusStruc.getActualBonusProcess());
			gameBonusPool.setChangeReason(gameBonusStruc.getChangeReason());
			gameBonusPool.setDistribute1(gameBonusStruc.getDistribute1());
			gameBonusPool.setDistribute1process(gameBonusStruc.getDistribute1process() == null ? null : (gameBonusStruc
					.getDistribute1process()).longValue());
			gameBonusPool.setDistribute2(gameBonusStruc.getDistribute2());
			gameBonusPool.setDistribute2process(gameBonusStruc.getDistribute2process() == null ? null : (gameBonusStruc
					.getDistribute2process()).longValue());
			gameBonusPool.setLotteryid(gameBonusStruc.getLotteryid());
			gameBonusPool.setMinimumBonus(gameBonusStruc.getMinimumBonus());
			gameBonusPool.setMinimumBonusProcess(gameBonusStruc.getActualBonusProcess());
			gameBonusPool.setStatus(gameBonusStruc.getStatus());
			gameBonusPoolServiceImpl.updateGameBonusPool(gameBonusPool);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return response;
	}
}
