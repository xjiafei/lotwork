/**   
* @Title: GameLockAppraiseController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-7 下午4:38:12 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;
import com.winterframework.firefrog.game.service.IGameLockAppraiseService;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseAddRequest;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseChangeStruc;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLockAppriaseOperateRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameLockController 
* @Description: 封锁Controller 
* @author floy
*  
*/
@Controller("GameLockAppraiseController")
@RequestMapping("/game")
public class GameLockAppraiseController {

	private Logger log = LoggerFactory.getLogger(GameLockAppraiseController.class);

	@Resource(name = "gameLockAppraiseServiceImpl")
	private IGameLockAppraiseService gameLockAppraiseService;

	private static final long PUBLISH = 4l;

	/** 
	* @Title: queryAllGameLockAppraise 
	* @Description: 查询所有变价数据
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryAllGameLockAppraise")
	@ResponseBody
	public Response<List<GameLockAppraiseQueryResponse>> queryAllGameLockAppraise(@RequestBody Request<Long> request)
			throws Exception {
		log.info("queryAllGameLockAppraise start");
		Response<List<GameLockAppraiseQueryResponse>> response = new Response<List<GameLockAppraiseQueryResponse>>(
				request);
		try {
			List<GameLockAppraise> list = gameLockAppraiseService
					.queryAllGameLockAppraise(request.getBody().getParam());
			List<GameLockAppraiseQueryResponse> resultList = new ArrayList<GameLockAppraiseQueryResponse>();
			for (GameLockAppraise gameLockAppraise : list) {
				GameLockAppraiseQueryResponse gameLockAppraiseQueryResponse = new GameLockAppraiseQueryResponse();
				gameLockAppraiseQueryResponse.setId(gameLockAppraise.getId());
				gameLockAppraiseQueryResponse.setStatus(gameLockAppraise.getStatus());
				gameLockAppraiseQueryResponse.setTitle(gameLockAppraise.getTitle());
				gameLockAppraiseQueryResponse.setGmtModify(DateUtils.format(gameLockAppraise.getGmtModified(),
						DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));
				gameLockAppraiseQueryResponse.setCurUser(gameLockAppraise.getCurrUse());
				resultList.add(gameLockAppraiseQueryResponse);
			}
			response.setResult(resultList);

		} catch (Exception e) {
			log.error("queryAllGameLockAppraise error", e);
			throw e;
		}

		return response;
	}

	/** 
	* @Title: operateGameLockAppraise
	* @Description: 修改变价数据状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/operateGameLockAppraise")
	@ResponseBody
	public Response<Object> operateGameLockAppraise(@RequestBody Request<GameLockAppriaseOperateRequest> request)
			throws Exception {
		log.info("operateGameLockAppraise start");
		Response<Object> response = new Response<Object>(request);
		try {
			GameLockAppraise gameLockAppraise = new GameLockAppraise();
			gameLockAppraise.setId(request.getBody().getParam().getId());
			gameLockAppraise.setStatus(request.getBody().getParam().getStatus());
			//gameLockAppraise.setCurrUse(gameLockAppraise.getStatus() == PUBLISH ? 1l : 0l);
			if(gameLockAppraise.getStatus() == PUBLISH){
				gameLockAppraiseService.updateCurUser(request.getBody().getParam().getId());
				gameLockAppraise.setCurrUse(1l);
				GameLockAppraise gameLockAppraiseDb = gameLockAppraiseService.queryGameLockAppraise(request.getBody().getParam().getId());
				gameLockAppraise.setChangeStruc(gameLockAppraiseDb.getChangeStrucProcess());
			}else{
				gameLockAppraise.setCurrUse(0l);
			}
			gameLockAppraiseService.updateStatus(gameLockAppraise);
		} catch (Exception e) {
			log.error("operateGameLockAppraise error", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: updateGameLockAppraise
	* @Description: 修改变价数据
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/updateGameLockAppraise")
	@ResponseBody
	public Response<Object> updateGameLockAppraise(@RequestBody Request<GameLockAppraiseAddRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		GameLockAppraiseAddRequest gameLockAppraiseAddRequest = request.getBody().getParam();
		GameLockAppraise gameLockAppraise = new GameLockAppraise();
		gameLockAppraise.setId(gameLockAppraiseAddRequest.getId());
		gameLockAppraise.setTitle(gameLockAppraiseAddRequest.getTitle());
		gameLockAppraise.setChangeStrucProcess(gameLockAppraiseAddRequest.getChangeStruc());
		gameLockAppraise.setModifier(request.getHead().getUserId());
		gameLockAppraise.setGmtModified(new Date());
		gameLockAppraise.setStatus(1l);
		//gameLockAppraise.setCurrUse(0l);
		try {
			gameLockAppraiseService.updateGameLockAppraise(gameLockAppraise);
		} catch (Exception e) {
			log.error("updateGameLockAppraise error", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: deleteGameLockAppraise
	* @Description: 删除变价数据状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/deleteGameLockAppraise")
	@ResponseBody
	public Response<Object> deleteGameLockAppraise(@RequestBody Request<String> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			gameLockAppraiseService.deleteGameLockAppraise(request.getBody().getParam());
		} catch (Exception e) {
			log.error("deleteGameLockAppraise error", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: addGameLockAppraise
	* @Description: 新增变价
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/addGameLockAppraise")
	@ResponseBody
	public Response<Object> addGameLockAppraise(@RequestBody Request<GameLockAppraiseAddRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		GameLockAppraiseAddRequest gameLockAppraiseAddRequest = request.getBody().getParam();
		GameLockAppraise gameLockAppraise = new GameLockAppraise();
		gameLockAppraise.setGameId(gameLockAppraiseAddRequest.getGameId());
		gameLockAppraise.setCurrUse(gameLockAppraiseAddRequest.getCurrUse());
		gameLockAppraise.setId(gameLockAppraiseAddRequest.getId());
		gameLockAppraise.setMinVal(gameLockAppraiseAddRequest.getMinVal());
		gameLockAppraise.setChangeStruc(gameLockAppraiseAddRequest.getChangeStruc());
		gameLockAppraise.setChangeStrucProcess(gameLockAppraiseAddRequest.getChangeStruc());
		gameLockAppraise.setTemplete(gameLockAppraiseAddRequest.getTemplete());
		gameLockAppraise.setTitle(gameLockAppraiseAddRequest.getTitle());
		gameLockAppraise.setModifier(request.getHead().getUserId());
		try {
			gameLockAppraiseService.addGameLockAppraise(gameLockAppraise);
		} catch (Exception e) {
			log.error("addGameLockAppraise error", e);
			throw e;
		}
		return response;
	}

	/** 
	* @Title: queryGameLockAppraise
	* @Description: 查询变价数据
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/queryGameLockAppraise")
	@ResponseBody
	public Response<GameLockAppraiseQueryResponse> queryGameLockAppraise(@RequestBody Request<Long> request)
			throws Exception {
		Response<GameLockAppraiseQueryResponse> response = new Response<GameLockAppraiseQueryResponse>(request);
		try {
			GameLockAppraise gameLockAppraise = gameLockAppraiseService.queryGameLockAppraise(request.getBody()
					.getParam());
			GameLockAppraiseQueryResponse gameLockAppraiseQueryResponse = new GameLockAppraiseQueryResponse();
			gameLockAppraiseQueryResponse.setId(gameLockAppraise.getId());
			gameLockAppraiseQueryResponse.setTitle(gameLockAppraise.getTitle());
			gameLockAppraiseQueryResponse.setMinVal(gameLockAppraise.getMinVal());
			gameLockAppraiseQueryResponse.setChangeStrucList((List<GameLockAppraiseChangeStruc>) DataConverterUtil
					.convertJson2Object(gameLockAppraise.getChangeStruc(), List.class,
							GameLockAppraiseChangeStruc.class));
			response.setResult(gameLockAppraiseQueryResponse);
		} catch (Exception e) {
			log.error("queryGameLockAppraise error", e);
			throw e;
		}
		return response;
	}
}
