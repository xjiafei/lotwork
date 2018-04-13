package com.winterframework.firefrog.game.web.controller;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.order.utlis.MmcDrawService;
import com.winterframework.firefrog.game.service.order.utlis.jlsbrngdraw.JlsbDrawService;
import com.winterframework.firefrog.game.web.dto.GameDrawNumberRequest;
import com.winterframework.firefrog.game.web.dto.GameDrawNumberResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 吉利骰寶主動開獎
 * @author Ami.Tsai
 *
 */
@Controller("gameDrawSourceController")
@RequestMapping("/game")
public class GameDrawSourceController {

	private Logger log = LoggerFactory.getLogger(GameDrawSourceController.class);

	@Resource(name = "jlsbDrawService")
	private JlsbDrawService jlsbDrawService;
	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawService;
	
	@Resource(name = "mmcDrawService")
	private MmcDrawService mmcDrawService;
	
	public static String randomSSC(String[] numbers,int count,String split){
		
		int numSize = numbers.length;
		Random random = new Random();
		StringBuffer sb  = new StringBuffer();
		for(int i=0;i<count;i++){
			sb.append(numbers[random.nextInt(numSize)]);
			if(i != (count-1)){
				sb.append(split);
			}
		}
		return sb.toString();
	}

	@RequestMapping(value = "/getDrawNumber")
	@ResponseBody
	public Response<GameDrawNumberResponse> getDrawNumber(
			@RequestBody @ValidRequestBody Request<GameDrawNumberRequest> request) throws Exception {
		GameDrawNumberRequest req = request.getBody().getParam();
		log.info("getDrawNumber:lotteryId="+request.getBody().getParam().getLotteryId()+" issueCode="+request.getBody().getParam().getIssueCode()+" time="+req.getOpenDrawTime());
		
		Response<GameDrawNumberResponse> response = new Response<GameDrawNumberResponse>(request);
		Long lotteryId=request.getBody().getParam().getLotteryId();
		Long issueCode=request.getBody().getParam().getIssueCode();
		String issue=request.getBody().getParam().getIssue();
		String briefCode=request.getBody().getParam().getBriefCode();
		String drawTime = req.getOpenDrawTime();
		 
		GameDrawNumberResponse result = new GameDrawNumberResponse();
		try{
			String drawNumber= "";
			String isSimulateDraw = System.getProperty("simulateDraw");  
			if("true".equals(isSimulateDraw)){
				//模拟开奖  本地运行测试可放开注释
				drawNumber = randomSSC(new String[]{"1","2","3","4","5","6"},3,"");
				result.setNumber(drawNumber);
			}else{
				drawNumber=jlsbDrawService.getDrawResult(lotteryId, issueCode);
				result.setNumber(drawNumber);
			}
			log.info("getDrawNumber:lotteryId = " + request.getBody().getParam().getIssueCode() + " result " + drawNumber );
			response.setResult(result);
			response.getHead().setStatus(YesNo.NO.getValue());
			//task 为同步处理 故不用考虑并发
			//gameDrawService.addDrawResult(lotteryId, issueCode, drawNumber, new GameWarnIssueLog(), null);
		}catch(Exception e){
			log.error("exception occurs when get draw number.",e);
			response.getHead().setStatus(101111);
		}
		return response;
	}
}
