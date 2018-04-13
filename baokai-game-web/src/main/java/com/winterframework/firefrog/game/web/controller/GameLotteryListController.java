package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.LotteryListQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryListStruc;
import com.winterframework.firefrog.game.web.dto.LotteryListStrucDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameLotteryListWebController 
* @Description: 彩种列表信息
* @author Richard
* @date 2013-9-23 下午3:52:31 
*
 */
@RequestMapping(value = "/lottery")
@Controller("gameLotteryListController")
public class GameLotteryListController {
	
	private Logger log = LoggerFactory.getLogger(GameLotteryListController.class);
	private SimpleDateFormat dateFormat ;
	
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.game.queryLotteryList")
	private String queryLotteryListUrl;
	
	@RequestMapping("/queryStopLotteryList")
	@ResponseBody
	public Response<LotteryListQueryResponse> queryStopLotteryList() throws Exception{
		
		log.info("queryStopLotteryList Start");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Response<LotteryListQueryResponse> res = new Response<LotteryListQueryResponse>();
		
		try {
			
			GameSeriesRequest requestData = new GameSeriesRequest();
			requestData.setStatus(0);
			
			Response<GameSeriesResponse> response = httpClient.invokeHttp(serverPath + queryLotteryListUrl, requestData, GameSeriesResponse.class);
			LotteryListQueryResponse lotteryListQueryResponse = new LotteryListQueryResponse();
			List<LotteryListStrucDto> list = new ArrayList<LotteryListStrucDto>();
			if(response!=null && response.getBody()!=null && response.getBody().getResult()!=null){
				lotteryListQueryResponse.setLotteryListStruc(response.getBody().getResult().getList());
			}
			
			res.setResult(lotteryListQueryResponse);
			
		} catch (Exception e) {
			log.error("queryStopLotteryList error:", e);
			throw e;
		}
		return res;
	}
}
