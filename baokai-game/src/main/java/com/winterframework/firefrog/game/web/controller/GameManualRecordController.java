package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GameManualRecord;
import com.winterframework.firefrog.game.exception.GameManualRecordException;
import com.winterframework.firefrog.game.service.IGameManualRecordService;
import com.winterframework.firefrog.game.web.dto.GameManualRecordEncodingRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordPageResponse;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName GameManualRecordController 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午4:33:53 
*  
*/
@Controller("gameManualRecordController")
@RequestMapping("/game")
public class GameManualRecordController {

	private Logger log = LoggerFactory.getLogger(GameManualRecordController.class);

	@Resource(name = "gameManualRecordServiceImpl")
	private IGameManualRecordService gameManualRecordService;
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	@RequestMapping(value = "/queryGameManualRecord")
	@ResponseBody
	public Response<GameManualRecordResponse> getGameManualRecordsByLottery(
			@RequestBody @ValidRequestBody Request<GameManualRecordRequest> request) {
		Response<GameManualRecordResponse> response = new Response<GameManualRecordResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		PageRequest<GameManualRecordRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setPageSize(10);
		pr.setSearchDo(request.getBody().getParam());
		GameManualRecordPageResponse pageRes = null;
		try {
			pageRes = gameManualRecordService.getGameManualRecordsByLottery(pr);
		} catch (Exception e) {
			log.error("查询手工录号时失败", e);
		}
		Page<GameManualRecord> page = pageRes.getGameManualRecords();
		GameManualRecordResponse res = new GameManualRecordResponse();
		res.setGameIssue(pageRes.getGameIssue());
		res.setLastGameIssue(pageRes.getLastGameIssue());
		res.setGameManualRecords(page.getResult());
		ResultPager pager = new ResultPager();
		pager.setStartNo(page.getThisPageFirstElementNumber());
		pager.setEndNo(page.getThisPageLastElementNumber());
		pager.setTotal(page.getTotalCount());
		response.setResultPage(pager);
		response.setResult(res);
		return response;
	}

	@RequestMapping(value = "/encodingGameManualRecord")
	@ResponseBody
	public Response<Integer> encodingGameManualRecordsByIssue(
			@RequestBody @ValidRequestBody Request<GameManualRecordEncodingRequest> request) {
		Response<Integer> response = new Response<Integer>(request);
		response.setResult(0);
		try {
			if (request.getBody().getParam() != null) {
				GameManualRecordEncodingRequest bizReq=request.getBody().getParam();
				String lock=bizReq.getLotteryId()+""+bizReq.getIssueCode();
				if(redis.acquireLock(lock,60000)){ //最多锁60s
					try{
						gameManualRecordService.encodingGameManualRecordsByIssue(request.getBody().getParam());
					}catch(Exception e){
						log.error("手工录号失败",e);
					}finally{
						redis.releaseLock(lock);
					}
				}else{
					response.setResult(3);
				}
			}else{
				response.setResult(1);
			}
		} catch( GameManualRecordException e1){
			if(e1.getCode().longValue() == 2L){
				log.error("手工录号时失败 ", e1);
				response.setResult(2);
			}						
		}catch (Exception e) {
			log.error("手工录号时失败", e);
			response.setResult(1);
		}
		return response;
	}

}
