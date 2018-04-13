package com.winterframework.firefrog.phone.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GetAllBettypeStatusResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

public class GamePlayNameInit {
	private Logger logger = LoggerFactory.getLogger(GamePlayNameInit.class);

	@Resource(name = "httpJsonClientImpl")
	protected IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.game")
	protected String gameUrl;

	@PropertyConfig(value="url.getAllBettypeStatus")
	private String getAllBettypeStatus;
	/** 
	* @Title: main 
	* @param args
	 * @throws Exception 
	*/
	@PostConstruct
	public void init() throws Exception {

		BetMethodValidListQueryRequest betMethodValidListQueryRequest = new BetMethodValidListQueryRequest();
		betMethodValidListQueryRequest.setLotteryid(99101L);

		// 从接口获得数据
		logger.info("get lottery config  betMethodValidListQuery begin...");
		
		
		Response<GetAllBettypeStatusResponse> allBettypes = httpClient.invokeHttp( gameUrl + getAllBettypeStatus, new TypeReference<Response<GetAllBettypeStatusResponse>>() {});
		List<GameBettypeStatus> gbs = allBettypes.getBody().getResult().getGameBettypeStatuss();
		logger.info("??????????????????????????????????????????????????????????????" + gbs.size());
		 Map<String,String[]> nameMap=new HashMap<String, String[]>();
		 Map<String,String[]> titleMap=new HashMap<String, String[]>();
		 Map<String,Integer[]> codeMap=new HashMap<String, Integer[]>();
		for(GameBettypeStatus betType:gbs){
			StringBuilder sb=new StringBuilder();
			StringBuilder sbCode=new StringBuilder();
			sb.append(betType.getLotteryid()).append("_").append(betType.getGameGroupCode()).append("_").append(betType.getGameSetCode())
					.append("_").append(betType.getBetMethodCode());
			sbCode.append(betType.getLotteryid()).append("_").append(betType.getGroupCodeName()).append("_").append(betType.getSetCodeName())
			.append("_").append(betType.getMethodCodeName());
			String key=sb.toString();
			String codeKey=sbCode.toString();
			String []names=new String[3];
			names[0]=betType.getGroupCodeName();
			names[1]=betType.getSetCodeName();
			names[2]=betType.getMethodCodeName();
			String []titles=new String[3];
			titles[0]=betType.getGroupCodeTitle();
			titles[1]=betType.getSetCodeTitle();
			titles[2]=betType.getMethodCodeTitle();
			nameMap.put(key, names);
			titleMap.put(key, titles);
			Integer []codes=new Integer[3];
			codes[0]=betType.getGameGroupCode();
			codes[1]=betType.getGameSetCode();
			codes[2]=betType.getBetMethodCode();
			codeMap.put(codeKey, codes);
			
			
			
		}
		//新加kl8额外的数据
		titleMap.put("99201_18_16_30", new String[]{"趣味","盘面","上下盘"});
		titleMap.put("99201_18_16_31", new String[]{"趣味","盘面","奇偶"});
		titleMap.put("99201_18_16_32", new String[]{"趣味","盘面","和值大小单双"});
		
		GameAwardNameUtil.setNameMap(nameMap);
		GameAwardNameUtil.setTitleMap(titleMap);
		GameAwardNameUtil.setCodeMap(codeMap);
	}

}
