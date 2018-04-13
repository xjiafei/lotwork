/**
 * 
 */
package com.winterframework.firefrog.game.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGameECLogDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameSimulateService;
import com.winterframework.firefrog.game.service.utils.ECUtils;
import com.winterframework.firefrog.game.web.dto.OperationsAddNumberRecordRequest;
import com.winterframework.modules.security.DigestUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * @author charleswang
 *
 */
@Service("gameSimulateServiceImpl")
public class GameSimulateServiceImpl implements IGameSimulateService {

	private static final Logger log = LoggerFactory.getLogger(GameSimulateServiceImpl.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.business.connect")
	private String serverPath;
	
	@Resource(name = "EClotteryNameMap")
	private Map<String, String> ECNameMap;
	
	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameECLogDaoImpl")
	private IGameECLogDao gameECLogDao;
	
	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService gameDrawResultService;
	
	@Override
	public void simulaterOpenArward(List<GameIssueEntity> issueList) throws Exception {
		try {
			for (GameIssueEntity gameIssueEntity : issueList) {
				Long type = gameIssueEntity.getLottery().getLotteryId();
				OperationsAddNumberRecordRequest json = openAwardGameIssue(gameIssueEntity);
				boolean isJlsb = Long.valueOf(99602L).equals(type)|| Long.valueOf(99603L).equals(type);
				//吉利骰宝 模拟开奖走另一条路
				if(json != null && !isJlsb){
					sendResultByECReceNFEC(createRequest(gameIssueEntity, json));
				}
			} 
		} catch (Exception e) {
			log.error("simulaterOpenArward addNumberRecord error:", e);
		}
	}
	
	@Override
	public void simulaterImmediateOpenAwardByLotteryId(Long lotteryId,Date curTime){
		try {
			List<GameIssue> issues = gameIssueDao.getNoNumberLatestByLotteryIdAndTime(lotteryId, curTime);
			if (issues != null) {
				for (GameIssue gameIssue : issues) {
					GameIssueEntity gameIssueEntity = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
					OperationsAddNumberRecordRequest json = openAwardGameIssue(gameIssueEntity);
					immediateOpenResultByTask(lotteryId, createRequest(gameIssueEntity, json));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private OperationsAddNumberRecordRequest openAwardGameIssue(GameIssueEntity gameIssueEntity) throws Exception{
		String numberRecord="12345";
		OperationsAddNumberRecordRequest json = new OperationsAddNumberRecordRequest();
		json.setDisposeMemo("模拟开奖");
		json.setIssueCode(gameIssueEntity.getIssueCode());
		json.setLotteryid(gameIssueEntity.getLottery().getLotteryId());
		Long type=gameIssueEntity.getLottery().getLotteryId();
		log.info("彩种id为"+type+",issueCode:"+gameIssueEntity.getIssueCode());
		if (Long.valueOf(99201L).equals(type)) {
			numberRecord = bubbleSort(random11X5(new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10",
																		  "11","12","13","14","15","16","17","18","19","20",
																			"21","22","23","24","25","26","27","28","29","30",
																			"31","32","33","34","35","36","37","38","39","40",
																			"41","42","43","44","45","46","47","48","49","50",
																			"51","52","53","54","55","56","57","58","59","60",
																			"61","62","63","64","65","66","67","68","69","70",
																			"71","72","73","74","75","76","77","78","79","80"
																		   )),20,","));
		}else if (Long.valueOf(99108L).equals(type) || Long.valueOf(99107L).equals(type)) {
			numberRecord = randomSSC(new String[]{"0","1","2","3","4","5","6","7","8","9"},3,"");
		} else if (gameIssueEntity.getLottery().getLotteryId().longValue() == 99301L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99302L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99303L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99304L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99305L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99306L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99307L
				) {
			numberRecord = random11X5(new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10","11")),5,",");
		}else if (gameIssueEntity.getLottery().getLotteryId().longValue() == 99401L){
			numberRecord = "01,02,03,10,13,33+13";
		}else if (gameIssueEntity.getLottery().getLotteryId().longValue() == 99701){
			//六合彩模擬測試
			numberRecord = "01,02,03,10,13,33,45";
		}else if (gameIssueEntity.getLottery().getLotteryId().longValue() == 99501L || gameIssueEntity.getLottery().getLotteryId().longValue() == 99601L
				|| gameIssueEntity.getLottery().getLotteryId().longValue() == 99502L){
			if(gameIssueEntity.getLottery().getLotteryId().longValue() == 99601L){
				return null;
			}
			numberRecord = randomSSC(new String[]{"1","2","3","4","5","6"},3,"");
		}else if (gameIssueEntity.getLottery().getLotteryId().longValue() == 99602L || gameIssueEntity.getLottery().getLotteryId().longValue() == 99603L){
			numberRecord = randomSSC(new String[]{"1","2","3","4","5","6"},3,"");
		}else{
			numberRecord = randomSSC(new String[]{"0","1","2","3","4","5","6","7","8","9"},5,"");
		} 
		json.setNumberRecord(numberRecord);
		log.info("开奖号码为："+numberRecord);
		return json;
	}
	
	/**
	 * 模擬建立 http request 參數。
	 * @param gameIssue
	 * @param json
	 * @return
	 */
	private String createRequest(GameIssueEntity gameIssue,OperationsAddNumberRecordRequest json){
		StringBuffer request = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String nowStr = sdf.format(now);
		String issueCodeStr = ECUtils.tansferGameIssueForECIssue(gameIssue.getLottery().getLotteryId(),String.valueOf(gameIssue.getWebIssueCode()));
		request.append("customer=").append("e84a38264657a15e0a392b99765dc420").append("&");
		request.append("issue=").append(issueCodeStr).append("&");
		request.append("number=").append(json.getNumberRecord()).append("&");
		request.append("lottery=").append(ECNameMap.get(gameIssue.getLottery().getLotteryId().toString())).append("&");
		request.append("message=").append("SimulaterOpenArward").append("&");
		request.append("recordId=").append(80000000000L+gameIssue.getId()).append("&");
		request.append("type=").append(1).append("&");
		request.append("time=").append(nowStr).append("&");
		request.append("stopSaleTime=").append(sdf.format(gameIssue.getSaleEndTime())).append("&");
		request.append("drawingTime=").append(sdf.format(new Date(gameIssue.getSaleEndTime().getTime()+60000))).append("&");
		request.append("earliestTime=").append(nowStr).append("&");
		request.append("verifiedTime=").append(nowStr).append("&");
		request.append("safestr=").append(DigestUtils.getMD5ofStr(request.toString()));
		return request.toString();
		
	}
	
	/**
	 * 模擬EC開獎。
	 * @param request
	 * @throws Exception
	 */
	private void sendResultByECReceNFEC(String request) throws Exception{
		log.info("simulaterOpenArward EC request:"+request);
		httpClient.invokeHttpWithoutResultTypeForEC(serverPath + "/game/receNFEC",request);
	}
	
	private void immediateOpenResultByTask(Long lotteryId,String input){
		Map<String, String> request = ECUtils.getMapFormRequestParams(input);
		String disposeMemo = "主动获取EC开奖号码";
		request.put("type", String.valueOf(ECUtils.EC_NOTICES_NORMAL_TYPE));
		String status = "auto get";
		long id = 0L;
		try {
			// 记录EC日志入库
			id = gameECLogDao.addGameECLog(ECUtils.getMapToEntity(request));
			log.info(disposeMemo + "入库,id为" + id);
			String issueCodeStr = request.get("issue");
			// 转换奖期格式 开奖中心格式to4.0格式
			String gameWebIsuue = ECUtils.getIssueFromEC(lotteryId, issueCodeStr);
			// 回传请参数
			String params = ECUtils.echoECData(request, String.valueOf(id));
			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);
			StringBuffer sb = new StringBuffer(params);

			// EC验证字符串
			sb.append("&safestr=").append(qs);
			request.put("safestr", qs);
			
			GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId, gameWebIsuue);
			GameControlEvent event = new GameControlEvent();
			event.setEnentType((long)GameControlEvent.EventType.EC_DRAW.getValue());
			event.setLotteryid(lotteryId);
			event.setStartIssueCode(issue.getIssueCode());
			event.setEndIssueCode(issue.getIssueCode());
			event.setSaleStartTime(issue.getSaleStartTime());
			event.setSaleEndTime(issue.getSaleEndTime());
			event.setStatus(-1L);
			event.setCreateTime(new Date());
			event.setParams(ECUtils.echoSaveData(request));
			gameDrawResultService.addOrUpdateDrawResult(event);
		} catch (GameIssueNotExistErrorException e) {
			status = "N1024";
		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
			status = "N0";
		}
		log.info("immediateOpenResultByTask status:"+status);
	}
	
	
	/**
	 *获取随机开奖号码
	 * @param anumberRecord
	 * @return
	 */
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
	
	/**
	 *获取随机开奖号码
	 * @param anumberRecord
	 * @return
	 */
	public static String random11X5(List<String> numbers,int count,String split){
		
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		StringBuffer sb  = new StringBuffer();
		
		for(int i=0;i<count;i++){
			
			sb.append(getAvaiNum(map,numbers));
			
			if(i != (count-1)){
				sb.append(split);
			}
		}
		
		return sb.toString();
	}

	/**
	 * 获取数组中不重的下标
	 * @param map
	 * @param numbers
	 * @return
	 */
	public static String getAvaiNum(Map<Integer,Integer> map,List<String> numbers){
		
		
		int numSize = numbers.size();
		Random random = new Random();
		int numbersPos = random.nextInt(numSize-1);
		
		String retStr = numbers.get(numbersPos);
		
		numbers.remove(numbersPos);
		
		
		return retStr;
	}
	
	/**
	 * 排序
	 * @param numbers
	 * @return
	 */
	public static String bubbleSort(String numbers){
		
		String[] numStrList = numbers.split(",");
		
		int numStrListSize = numStrList.length;
		
		StringBuffer sb = new StringBuffer();
		
		int[] abc = new int[numStrListSize];
		
		int i = 0;
		
		for(String nums:numStrList){
			abc[i] = Integer.parseInt(nums);
			i++;
		}
		
		i = 0;
		Arrays.sort(abc);  //进行排序
		for(int ii: abc){
			   if(ii < 10){
				   sb.append("0" + ii);
			   }else{
				   sb.append(ii);
			   }
			   
			   if(i != (numStrListSize -1)){
				   sb.append(",");
			   }
			   i++;
		}
		return sb.toString();
	}
}
