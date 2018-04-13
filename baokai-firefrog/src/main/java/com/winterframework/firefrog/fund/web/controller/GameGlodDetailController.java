/**   
* @Title: GameGlodDetailController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: 游戏币报表 
* @author hugh  
* @date 2014年9月22日 下午4:41:19 
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

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.fund.dao.IGameGoldDetailDao;
import com.winterframework.firefrog.fund.dao.vo.GameGoldDetailVO;
import com.winterframework.firefrog.fund.web.dto.GameGoldDetailResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName GameGlodDetailController 
* @Description 游戏币报表 
* @author  hugh
* @date 2014年9月22日 下午4:41:19 
*  
*/
@Controller("gameGlodDetailController")
@RequestMapping("/fund")
public class GameGlodDetailController {

	private static final Logger log = LoggerFactory.getLogger(GameGlodDetailController.class);

	@Resource(name = "gameGoldDetailDaoImpl")
	private IGameGoldDetailDao gameGoldDetailDaoImpl;

	/** 
	* @Title: getGameGoldDetails 
	* @Description: 获取游戏币明细
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getGameGoldDetails")
	@ResponseBody
	public Response<GameGoldDetailResponse> getGameGoldDetails(@RequestBody @ValidRequestBody Request<GameGoldDetailVO> request)
			throws Exception {

		log.debug("获取游戏币明细......");
		Response<GameGoldDetailResponse> response = new Response<GameGoldDetailResponse>(request);
		List<GameGoldDetailVO> list = gameGoldDetailDaoImpl.getGameGoldDetails(request.getBody().getParam());
		GameGoldDetailResponse res = new GameGoldDetailResponse();
		res.setDetails(list);
		response.setResult(res);
		log.debug("获取游戏币明细。");
		return response;

	}



}
