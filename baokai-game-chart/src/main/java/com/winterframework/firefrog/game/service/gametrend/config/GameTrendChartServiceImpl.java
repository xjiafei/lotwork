/**   
* @Title: GameTrendChartServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.gametrend.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-1 下午5:00:13 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.config;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IConverterService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.util.QueryType;
import com.winterframework.modules.spring.exetend.PropertyConfig;

import oracle.sql.TIMESTAMP;

/** 
* @ClassName: GameTrendChartServiceImpl 
* @Description: 彩种走势图遗漏数据统计入口
* @author floy
* @date 2014-4-1 下午5:00:13 
*  
*/
@Service("gameTrendChartServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameTrendChartServiceImpl implements IGameTrendChartService {

	private static final Logger log = LoggerFactory.getLogger(GameTrendChartServiceImpl.class);

	@Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameJbylTrendDao;

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	//彩种对应的统计规则配置
	@Resource(name = "gameTrendChartRuleListMap")
	private Map<String, GameTrendChartRuleList> gameTrendChartRuleListMap;
	
	@PropertyConfig(value = "key.seperator")
    private String keySeperator;
	
	@Resource(name = "jbylTrendTypeMap")
    private Map<LotteryPlayMethodKeyGenerator, List<TrendType>> jbylTrendTypeMap;
	
	@Resource(name = "danShuangSpecialManageLotteryids")
    private List<Long> danShuangSpecialManageLotteryids;
	
	@Resource(name = "ballRengeMap")
    private Map<String, String> ballRengeMap;
	
	@Resource(name = "converterServiceImpl")
	private IConverterService converterServiceImpl;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private static String parseObjectToJSON(Object o) throws JsonProcessingException{
		String jsonStr = mapper.writeValueAsString(o);
		return jsonStr;
	}

	/**
	* Title: generateTrendChartData
	* Description:彩种走势图遗漏数据统计入口方法
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameTrendChartService#generateTrendChartData() 
	*/
	@Override
	@Deprecated
	public void generateTrendChartData() throws Exception {
		log.info("生成统计数据开始....");
		if (gameTrendChartRuleListMap != null) {
			try {
				//获取所有的彩种的最新的统计奖期
				List<LotteryIdAndIssueCode> lastChartLotteryIdAndIssueCode = gameJbylTrendDao
						.getLastLotteryIdAndIssueCode();
				for (Entry<String, GameTrendChartRuleList> entry : gameTrendChartRuleListMap.entrySet()) {
					Long lotteryId = Long.valueOf(entry.getKey());
					//获取当前彩种的最新统计奖期
					LotteryIdAndIssueCode lastChartIssueCodeForLottery = this.getLastChartByLotteryId(lotteryId,
							lastChartLotteryIdAndIssueCode);
					//获取当前彩种的最新统计数据
					List<GameTrendJbyl> trendList = lastChartIssueCodeForLottery != null ? gameJbylTrendDao
							.getGameTrendChart(lastChartIssueCodeForLottery) : null;
					//获取最新统计奖期的下一期，即为需要生成数据的奖期
					GameDrawResult needGenerategdr = gameDrawResultDao.getNextDrawResuleByLotteryIdAndIssueCode(
							lotteryId,
							lastChartIssueCodeForLottery != null ? lastChartIssueCodeForLottery.getIssueCode() : null);
					//数据统计
					entry.getValue().excuteGenerate(needGenerategdr, trendList);
				}
			} catch (Exception e) {
				log.error("生成统计数据错误", e);
				throw e;
			}
		}
		log.info("生成统计数据结束....");

	}

	private LotteryIdAndIssueCode getLastChartByLotteryId(Long lotteryId,
			List<LotteryIdAndIssueCode> lastChartLotteryIdAndIssueCode) {
		for (LotteryIdAndIssueCode lotteryIdAndIssueCode : lastChartLotteryIdAndIssueCode) {
			if (lotteryIdAndIssueCode.getLotteryid().longValue() == lotteryId.longValue()) {
				return lotteryIdAndIssueCode;
			}
		}
		return null;
	}
	
	public GameTrendChartStruc queryOmissionValue(List<GameTrendJbyl> gameTrendJbyls, Long lotteryId, Integer isGeneral, Integer gameGroupCode) {
		BaseTrendChartStruc baseTrendChartStruc = new BaseTrendChartStruc();

        List<TrendType> trendTypes = matchedTrendTypes(lotteryId, isGeneral, gameGroupCode, null, null, keySeperator);
        //轉換為期數 list
        //走势图基本结构列表
        List<BaseChartStruc> baseChartStrucList = new ArrayList<BaseChartStruc>();
        for(GameTrendJbyl g : gameTrendJbyls){
        	boolean isExists = false;
        	for(BaseChartStruc baseChartStruc : baseChartStrucList){
        		if( baseChartStruc.getWebIssueCode().equals(g.getIssueCode().toString()) ) {
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		BaseChartStruc baseChartStruc = new BaseChartStruc();
        		baseChartStruc.setWebIssueCode(g.getIssueCode().toString());//期數
                baseChartStruc.setNumberRecord(g.getNumberRecord());//開獎號碼
                baseChartStrucList.add(baseChartStruc);
        	}
        }
        
        //  所有資料依照 trendType 做分類, 轉換成 Map
        Map<TrendType, List<GameTrendJbyl>> trendTypeListMap = new HashMap<TrendType, List<GameTrendJbyl>>();
        for(TrendType trendType : trendTypes){
        	//類型暫存檔
        	List<GameTrendJbyl> typeJbyls = new ArrayList<GameTrendJbyl>();
        	Integer value = TrendType.SUMVALMANTISSA.equals(trendType) ? TrendType.SUMVAL.getValue() : trendType.getValue();
        	for (GameTrendJbyl g : gameTrendJbyls) {
        		if( value.equals(Integer.valueOf(g.getTrendType())) && gameGroupCode.equals(g.getGameGroupCode()) ){
        			typeJbyls.add(g);
        		}
        	}
        	trendTypeListMap.put(trendType, typeJbyls);
        }
        
        //開始進行每一期的資料整理
        for(BaseChartStruc baseChartStruc : baseChartStrucList){
        	Map<TrendType, List<String>> chartStrucMap = new HashMap<TrendType, List<String>>();
        	for(TrendType trendType : trendTypes){
        		List<GameTrendJbyl> typeJbyls = trendTypeListMap.get(trendType);
        		//取得對應的issueCode
        		for(GameTrendJbyl gameTrendJbyl : typeJbyls){
        			if(baseChartStruc.getWebIssueCode().equals(gameTrendJbyl.getIssueCode().toString())	){
        				String value = null;
        				// (彩种为11选5类的 && 遗漏类型为单双) || 遗漏类型为中位
                        if ((danShuangSpecialManageLotteryids.contains(lotteryId) && trendType == TrendType.DANSHUANG) || trendType == TrendType.ZHONGWEI) {
                            value = gameTrendJbyl.getWebValue();
                        } else {
                            value = gameTrendJbyl.getValue();
                        }
                        List<String> singleStrucList = makeSingleStrucList(value, lotteryId, trendType);
        				chartStrucMap.put(trendType, singleStrucList);
        			}
        		}
        	}
        	baseChartStruc.setChartStruc(chartStrucMap);
        }
        
        baseTrendChartStruc.setBaseChartStrucs(baseChartStrucList);
        //轉換回頁面的資料
        GameTrendChartStruc result = converterServiceImpl.converter(baseTrendChartStruc,
				lotteryId, gameGroupCode, isGeneral);
        return result;
	}
	
	/**
     * @Title: makeSingleStrucList
     * @Description: 将VIEW_STRUC的值拆分为List：位数遗漏按照位数个数来分，其它类型不拆分
     * @return List<String>    返回类型
     * @throws
     */
    private List<String> makeSingleStrucList(String totalStruc, Long lotteryId, TrendType trendType) {

        List<String> list = new ArrayList<String>();

        String[] strucArr = totalStruc.split(",");

        String[] ballRenge = ballRengeMap.get(String.valueOf(lotteryId)).split(",");

        int ballStartIndex = Integer.parseInt(ballRenge[0]);
        int ballEndIndex = Integer.parseInt(ballRenge[1]);
        int ballCount = ballEndIndex - ballStartIndex + 1;

        StringBuffer sb = null;
        if (trendType == TrendType.WEISHU) {
            for (int i = 0; i < strucArr.length; i++) {
                if (i % ballCount == 0) {
                    if (sb != null) {
                        list.add(sb.subSequence(0, sb.length() - 1).toString());
                    }
                    sb = new StringBuffer();
                }
                sb.append(strucArr[i]);
                sb.append(",");
            }
            list.add(sb.subSequence(0, sb.length() - 1).toString());
        } else {
            list.add(totalStruc);
        }
        return list;
    }
	
	/**
     * @Title: matchedKeyGen
     * @Description: 匹配的遗漏类型
     */
    private List<TrendType> matchedTrendTypes(Long lotteryId, Integer isGeneral, Integer gameGroupCode,
                                              Integer gameSetCode, Integer betMethodCode, String keySeperator2) {

        LotteryPlayMethodKeyGenerator lotteryKeyGenerator = new LotteryPlayMethodKeyGenerator(isGeneral, gameGroupCode,
                keySeperator);// "_$_"
        LotteryPlayMethodKeyGenerator cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();

        List<TrendType> matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);

        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setLotteryType(null);
        matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setMethodCode(null);
        matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setSetCode(null);
        matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setGroupCode(null);
        matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setIsGeneral(null);
        matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        return null;
    }

	@Override
	public List<GameTrendJbyl> generateMMCTrendChartData(Long userid, Long lotteryId, Integer isGeneral, Long groupCode,
			Integer num, Long startDate, Long endDate) throws Exception {
		//先取得這個Group對應的trendTypes
		List<TrendType> trendTypes = matchedTrendTypes(lotteryId, isGeneral, Integer.valueOf(groupCode+""), null, null, keySeperator);
		
		List<GameTrendJbyl> allTrendJbyls = new ArrayList<GameTrendJbyl>();
		//先取得100期資料
		List<Map<String, Object>> results = gameJbylTrendDao.getDrawResultsByUserIdAndLotteryId(
				userid, lotteryId, num, 
				null == startDate ? null : new Date(startDate), 
				null == endDate ? null : new Date(endDate));
		//轉成 GameDrawResult
		List<GameDrawResult>  gameDrawResults = transToResult(results);
		// loop 做 excuteGenerate 但要依照順序 做一個temp暫存檔
		//  (1) 依照頁面的 類型做區分不必每個都做
		//做一個暫存上次紀錄的暫存檔, 為了統計下次的遺漏次數使用
		List<GameTrendJbyl> tempGameTrendJbyls = null;
		for(int i = gameDrawResults.size() - 1 ; i >= 0 ; i--){
			GameDrawResult gameDrawResult = gameDrawResults.get(i);
			for (Entry<String, GameTrendChartRuleList> entry : gameTrendChartRuleListMap.entrySet()) {
				Long keyLotteryId = Long.valueOf(entry.getKey());
				if(keyLotteryId.equals(lotteryId)){
					List<GameTrendJbyl> gameTrendJbyls = entry.getValue().excuteMMCGenerate(gameDrawResult, tempGameTrendJbyls, trendTypes, groupCode);
					//TODO 此段只是為了確認資料使用, 之後要注解掉
					/*
					for(GameTrendJbyl jbyl : gameTrendJbyls){
						log.info("issuecode : {}, json : {}", gameDrawResult.getIssueCode(), parseObjectToJSON(jbyl));
					}
					*/
					tempGameTrendJbyls = gameTrendJbyls;
					allTrendJbyls.addAll(gameTrendJbyls);
				}
			}
		}
		//回傳結果
		return allTrendJbyls;
	}
	
	public List<GameDrawResult> transToResult(List<Map<String, Object>> results) throws SQLException{
		List<GameDrawResult> drawResults = new ArrayList<GameDrawResult>();
		for(Map<String, Object> map : results){
			GameDrawResult drawResult = new GameDrawResult();
			drawResult.setLotteryid(    ((BigDecimal)map.get("LOTTERYID")).longValue()    );
			drawResult.setIssueCode(     ((BigDecimal)map.get("ISSUE_CODE")).longValue()   );
			drawResult.setWebIssueCode( (String)map.get("WEB_ISSUE_CODE")  );
			drawResult.setNumberRecord(   (String)map.get("NUMBER_RECORD")  );
			drawResult.setCreateTime(  new Date(((TIMESTAMP)map.get("CREATE_TIME")).timestampValue().getTime())  );
			drawResult.setUpdateTime(  new Date(((TIMESTAMP)map.get("UPDATE_TIME")).timestampValue().getTime()) );
			drawResult.setOpenDrawTime( new Date(((TIMESTAMP)map.get("OPEN_DRAW_TIME")).timestampValue().getTime())  );
			drawResult.setType(0L); 
			drawResults.add(drawResult);
		}
		return drawResults;
	}
	
	public QueryType checkQueryTypeByLotteryid(Long lotteryid){
		String lotteryidString = gameJbylTrendDao.checkQueryTypeByLotteryid();
		if(StringUtils.isNotBlank(lotteryidString) && lotteryidString.indexOf(",") > -1){
			List<String> lotteryids = Arrays.asList(StringUtils.split(lotteryidString, ","));
			if(lotteryids.contains(lotteryid.toString())){
				return QueryType.IMMEDIATE_QUERY;
			} else {
				return QueryType.TASK_QUERY;
			}
		} else {
			return QueryType.TASK_QUERY;
		}
	}

}
