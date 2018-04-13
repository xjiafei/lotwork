package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
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
@RequestMapping(value = "/gameoa")
@Controller("gameLotteryListWebController")
public class GameLotteryListWebController {
	
	private Logger log = LoggerFactory.getLogger(GameLotteryListWebController.class);
	private SimpleDateFormat dateFormat ;
	
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.game.queryLotteryList")
	private String queryLotteryListUrl;
	
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	@Resource(name = "RedisClient2")
	private RedisClient2 redis2;
	
	
	@RequestMapping("/lotteryList")
	public ModelAndView lotteryList(Long lotteryId, Integer status) throws Exception{
		
		ModelAndView view = new ModelAndView("operations/lotteryList/index");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String mmcKillModeValue=redis.get("mmcKillMode");
		if(StringUtils.isEmpty(mmcKillModeValue)) {
			mmcKillModeValue="0";
		}
		String mc115KillModeValue=redis.get("mc115KillMode");
		if(StringUtils.isEmpty(mc115KillModeValue)) {
			mc115KillModeValue="0";
		}
		
		String killModeValue=redis2.get("killMode");
		if(StringUtils.isEmpty(killModeValue)) {
			killModeValue="0";
		}
		
		Integer mmcKillMode=Integer.valueOf(mmcKillModeValue);
		Integer mc115KillMode=Integer.valueOf(mc115KillModeValue);
		Integer killMode=Integer.valueOf(killModeValue);
		try {
			
			GameSeriesRequest requestData = new GameSeriesRequest();
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(status);
			
			Response<GameSeriesResponse> response = httpClient.invokeHttp(serverPath + queryLotteryListUrl, requestData, GameSeriesResponse.class);
			
			List<LotteryListStrucDto> list = new ArrayList<LotteryListStrucDto>();
			if(response!=null && response.getBody()!=null && response.getBody().getResult()!=null){
				for(LotteryListStruc struc : response.getBody().getResult().getList()){
					
					LotteryListStrucDto dto = new LotteryListStrucDto();
					if(struc.getLotteryid() == 88101 || struc.getLotteryid() == 88102){
						continue;
					}
					dto.setCreateTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getCreateTime())));
					dto.setLotteryHelpDes(struc.getLotteryHelpDes());
					dto.setLotteryid(struc.getLotteryid());
					dto.setLotteryName(struc.getLotteryName());
					dto.setLotterySeriesCode(struc.getLotterySeriesCode());
					dto.setLotteryTypeCode(struc.getLotteryTypeCode());
					dto.setMiniLotteryProfit(struc.getMiniLotteryProfit());
					dto.setStatus(struc.getStatus());
					if(null != struc.getUpdateTime() && struc.getUpdateTime() > 0){
						dto.setUpdateTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getUpdateTime())));
					}
					dto.setOperationChangeStatus(struc.getOperationChangeStatus());
					dto.setOperationLock(struc.getOperationLock());
					list.add(dto);
				}
				
			}
			
			view.addObject("lotteryList", list);
			view.addObject("mmcKillMode",mmcKillMode);
			view.addObject("mc115KillMode",mc115KillMode);
			view.addObject("killMode",killMode);
			
		} catch (Exception e) {
			log.error("queryLotteryList error:", e);
			throw e;
		}
		return view;
	}
	
	@RequestMapping(value = "/startAllKillMode")
	@ResponseBody
	public Object startAllKillMode(String parm) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		log.error("startAllKillMode:");
		redis2.set("killMode", "1");
		resp.setStatus(1L);
		resp.setMessage("success");

		return resp;
	}
	
	@RequestMapping(value = "/stopAllKillMode")
	@ResponseBody
	public Object stopAllKillMode(String parm) throws Exception {
		log.error("stopAllKillMode:");
		AjaxResponse resp = new AjaxResponse();
		redis2.set("killMode", "0");
		resp.setStatus(1L);
		resp.setMessage("success");
		return resp;
	}
	
	
	@RequestMapping(value = "/startKillMode")
	@ResponseBody
	public Object startKillMode(String parm) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		log.error("startKillMode:");
		redis.set("mmcKillMode", "1");
		resp.setStatus(1L);
		resp.setMessage("success");

		return resp;
	}
	
	@RequestMapping(value = "/startMc115KillMode")
	@ResponseBody
	public Object startMc115KillMode(String parm) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		log.error("startKillMode:");
		redis.set("mc115KillMode", "1");
		resp.setStatus(1L);
		resp.setMessage("success");

		return resp;
	}
	
	@RequestMapping(value = "/stopKillMode")
	@ResponseBody
	public Object stopKillMode(String parm) throws Exception {
		log.error("stopKillMode:");
		AjaxResponse resp = new AjaxResponse();
		redis.set("mmcKillMode", "0");
		resp.setStatus(1L);
		resp.setMessage("success");
		return resp;
	}
	
	@RequestMapping(value = "/stopMc115KillMode")
	@ResponseBody
	public Object stopMc115KillMode(String parm) throws Exception {
		log.error("stopMc115KillMode:");
		AjaxResponse resp = new AjaxResponse();
		redis.set("mc115KillMode", "0");
		resp.setStatus(1L);
		resp.setMessage("success");
		return resp;
	}
	

}
