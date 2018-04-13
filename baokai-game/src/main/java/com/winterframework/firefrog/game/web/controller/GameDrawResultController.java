package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameDrawResultQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameDrawResultQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameDrawResultStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
 
/**
 * 开奖结果Controller
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
@Controller("gameDrawResultController")
@RequestMapping("/game")
public class GameDrawResultController {

	private Logger log = LoggerFactory.getLogger(GameDrawResultController.class);

	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService gameDrawResultService; 
	 
	/**
	 * 查询最近num期的开奖结果
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryGameDrawResultTop")
	@ResponseBody
	public Response<GameDrawResultQueryResponse> queryGameDrawResultTopByLotteryId(
			@RequestBody @ValidRequestBody Request<GameDrawResultQueryRequest> request) throws Exception {

		log.info("开始：查询最近num期的开奖结果......");
		Response<GameDrawResultQueryResponse> response = new Response<GameDrawResultQueryResponse>(request); 
		GameDrawResultQueryRequest reqParam = request.getBody().getParam();
		long lotteryId = reqParam.getLotteryId();
		int top = reqParam.getNum(); 
		try {
			List<GameDrawResult> drawResultList = gameDrawResultService.getGameDrawResultTopByLotteryId(lotteryId, top);
			GameDrawResultQueryResponse resResult=new GameDrawResultQueryResponse(); 
			List<GameDrawResultStruc> gameDrawResultStrucList=new ArrayList<GameDrawResultStruc>();
			if(drawResultList!=null && drawResultList.size()>0){
				for(GameDrawResult drawResult:drawResultList){
					gameDrawResultStrucList.add(DTOConvert.gameDrawResult2Struc(drawResult));
				}
			}
			resResult.setGameDrawResultStrucList(gameDrawResultStrucList);
			response.setResult(resResult);
		} catch (Exception e) {
			log.error("查询最近num期的开奖结果异常：", e);
			throw e;
		}

		log.info("结束：查询最近num期的开奖结果。");
		return response;
	} 
}
