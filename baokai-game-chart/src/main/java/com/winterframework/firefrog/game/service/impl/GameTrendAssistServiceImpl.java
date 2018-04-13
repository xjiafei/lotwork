package com.winterframework.firefrog.game.service.impl;


import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryMethodTrendTypeKeyGenerator;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.utils.TrendJbylQueryParam;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: GameTrendAssistServiceImpl
 * @Description: 走势图数据Service实现类--走势辅助
 * @author Denny
 * @date 2014-3-27 下午1:47:16
 *
 */
@Service("gameTrendAssistServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameTrendAssistServiceImpl implements IGameTrendService {

    @Resource(name = "gameTrendJbylDaoImpl")
    private IGameTrendJbylDao gameTrendJbylDaoImpl;

    @PropertyConfig(value = "key.seperator")
    private String keySeperator;
    @Resource(name = "ballRengeMap")
    private Map<String, String> ballRengeMap;
    public void setKeySeperator(String keySeperator) {
        this.keySeperator = keySeperator;
    }

    private Map<LotteryPlayMethodKeyGenerator, List<TrendType>> trendTypeMap;

    public void setTrendTypeMap(Map<LotteryPlayMethodKeyGenerator, List<TrendType>> trendTypeMap) {
        this.trendTypeMap = trendTypeMap;
    }

    private Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> queryParamMap;

    public void setQueryParamMap(Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }


    /**
     * 查询彩种统计数据
     * @param lotteryId
     * @param gameGroupCode
     * @param gameSetCode
     * @param betMethodCode
     * @param num
     * @return
     */
    @Override
    public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
                                                   Integer betMethodCode, int num) {

        return gameTrendJbylDaoImpl.getTrendByBetMethod(lotteryId, gameGroupCode, gameSetCode, betMethodCode, num);
    }

    @Override
    public BaseTrendChartStruc queryOmissionValue(Long lotteryId, Integer isGeneral, Integer gameGroupCode,
                                                  Integer gameSetCode, Integer betMethodCode, Integer num,
                                                  Date startDate, Date endDate) {

        BaseTrendChartStruc baseTrendChartStruc = new BaseTrendChartStruc();

        List<TrendType> trendTypes = matchedTrendTypes(lotteryId, isGeneral, gameGroupCode, gameSetCode, betMethodCode,
                keySeperator);

        //走势图基本结构列表
        List<GameTrendJbyl> gameTrendJbylList = null;
        if (num != null && startDate == null) {
            gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId, null, null,
                    null, 100, num);
        } else {
            gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbylTimePeriod(lotteryId, null, null,
                    null, 100, startDate, endDate);
        }
        //走势图基本结构列表
        List<BaseChartStruc> baseChartStrucList = new ArrayList<BaseChartStruc>();
        for (GameTrendJbyl g : gameTrendJbylList) {
            //组合单个基本结构
            BaseChartStruc baseChartStruc = new BaseChartStruc();
            baseChartStruc.setWebIssueCode(g.getWebIssueCode());
            baseChartStruc.setNumberRecord(g.getLotteryid() == 99401l ? g.getValue().replace("+", ",")
                    : g.getValue());
            baseChartStrucList.add(baseChartStruc);
        }
        int i = 0;
        //把每个列表取出来根据类型放到map里
        Map<TrendType, List<GameTrendJbyl>> trendTypeListMap = new HashMap<TrendType, List<GameTrendJbyl>>();
        for (BaseChartStruc baseChartStruc : baseChartStrucList) {
            Map<TrendType, List<String>> chartStrucMap = new HashMap<TrendType, List<String>>();
            Integer setCode = null;
            for (TrendType trendType : trendTypes) {
                TrendJbylQueryParam queryParam = matchedQueryParam(lotteryId, gameGroupCode, gameSetCode, betMethodCode,trendType.getValue(),
                        keySeperator);

                if (queryParam != null) {
                    setCode = queryParam.getGameSetCode();
                } else {
                    setCode = null;
                }

                if (trendTypeListMap.get(trendType)==null) {
                	int trendTypeValue=trendType.getValue();
                	int tmpGroupCode=gameGroupCode;
                	if((lotteryId.longValue()+"").startsWith("995")){ 
                		if(trendType.equals(TrendType.HAOMAZOUSHI)){
                			trendTypeValue=TrendType.FENBU.getValue();
                			tmpGroupCode=37;
                		}else if(trendType.equals(TrendType.HEZHIZOUSHI)){
                			trendTypeValue=TrendType.HEZHI.getValue();
                		}else if(trendType.equals(TrendType.HEZHIZUHE)){
                			trendTypeValue=TrendType.HAOMAZOUSHI.getValue();
                		}else if(trendType.equals(TrendType.HAOMAXINGTAI)){
                			trendTypeValue=TrendType.HEZHIZOUSHI.getValue();
                		}
                	}
                    if (num != null && startDate == null) {
                        gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId, tmpGroupCode, setCode,
                                betMethodCode, trendTypeValue, num);
                    } else {
                        gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbylTimePeriod(lotteryId, tmpGroupCode, setCode,
                                betMethodCode, trendTypeValue, startDate, endDate);
                    }
                    trendTypeListMap.put(trendType, gameTrendJbylList);
                } else {
                    gameTrendJbylList = trendTypeListMap.get(trendType);
                }

                //组合结构走势图基本结构体里的列表

                List<String> singleStrucList =null;
                if(gameTrendJbylList != null && gameTrendJbylList.size()>0){
                	singleStrucList=makeSingleStrucList((lotteryId.longValue()+"").startsWith("995")?gameTrendJbylList.get(i).getValue():gameTrendJbylList.get(i).getWebValue(), lotteryId, trendType);
                } 
                chartStrucMap.put(trendType, singleStrucList);
            }
            baseChartStruc.setChartStruc(chartStrucMap);
            i++;
        }

        baseTrendChartStruc.setBaseChartStrucs(baseChartStrucList);

        return baseTrendChartStruc;
    }

    /**
     * @Title: matchedKeyGen
     * @Description: 匹配的遗漏类型
     */
    private List<TrendType> matchedTrendTypes(Long lotteryId, Integer isGeneral, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode,
                                              String keySeperator2) {

        LotteryPlayMethodKeyGenerator lotteryKeyGenerator = new LotteryPlayMethodKeyGenerator(lotteryId, isGeneral, gameGroupCode, gameSetCode, betMethodCode,
                keySeperator);
        LotteryPlayMethodKeyGenerator cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();

        List<TrendType> matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setLotteryType(null);
        matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setMethodCode(null);
        matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setSetCode(null);
        matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        cloneKeyGenerator.setGroupCode(null);
        matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
        if (matchedTrendTypes != null) {
            return matchedTrendTypes;
        }

        return null;
    }

    /**
     * @Title: matchedQueryParam
     * @Description: 匹配的查询参数
     */
    private TrendJbylQueryParam matchedQueryParam(long lotteryId, Integer gameGroupCode, Integer gameSetCode,
                                                  Integer betMethodCode, Integer trendType, String keySeperator2) {
        LotteryMethodTrendTypeKeyGenerator lotteryKeyGenerator = new LotteryMethodTrendTypeKeyGenerator(lotteryId, gameGroupCode, gameSetCode, betMethodCode,trendType,
                keySeperator);
        LotteryMethodTrendTypeKeyGenerator cloneKeyGenerator = (LotteryMethodTrendTypeKeyGenerator) lotteryKeyGenerator.clone();

        TrendJbylQueryParam matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        cloneKeyGenerator.setLotteryType(null);
        matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        cloneKeyGenerator.setTrendType(null);
        matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        cloneKeyGenerator.setMethodCode(null);
        matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        cloneKeyGenerator.setSetCode(null);
        matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        cloneKeyGenerator.setGroupCode(null);
        matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
        if (matchedQueryParam != null) {
            return matchedQueryParam;
        }

        return null;
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
}
