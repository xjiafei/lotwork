package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: GameTrendJbylServiceImpl
 * @Description: 走势图数据Service实现类--基本走势&综合走势
 * @author Denny
 * @date 2014-3-27 下午1:47:16
 *
 */
@Service("gameTrendJbylServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameTrendJbylServiceImpl implements IGameTrendService {

    private Logger log = LoggerFactory.getLogger(GameTrendJbylServiceImpl.class);

    @Resource(name = "gameTrendJbylDaoImpl")
    private IGameTrendJbylDao gameTrendJbylDaoImpl;

    @PropertyConfig(value = "key.seperator")
    private String keySeperator;

    @Resource(name = "danShuangSpecialManageLotteryids")
    private List<Long> danShuangSpecialManageLotteryids;

    @Resource(name = "jbylTrendTypeMap")
    private Map<LotteryPlayMethodKeyGenerator, List<TrendType>> jbylTrendTypeMap;

    @Resource(name = "ballRengeMap")
    private Map<String, String> ballRengeMap;

    /**
     * Title: getTrendByBetMethod
     * Description:查询彩种统计数据
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

    /*@Override
    public Map<TrendType, List<String>> queryOmissionValue(Long lotteryId, Integer isGeneral, Integer gameGroupCode,
            Integer gameSetCode, Integer betMethodCode, Integer num) {

        log.info("开始查询遗漏数据......");

        Map<TrendType, List<String>> valueMap = new HashMap<TrendType, List<String>>();

        List<TrendType> trendTypes = matchedTrendTypes(lotteryId, isGeneral, gameGroupCode, gameSetCode, betMethodCode,
                keySeperator);



        for (TrendType trendType : trendTypes) {
            List<String> values = new ArrayList<String>();

            List<GameTrendJbyl> gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId, gameGroupCode, null,
                    null, trendType.getValue(), num);
            if (gameTrendJbylList.size() > 0) {
                for (GameTrendJbyl g : gameTrendJbylList) {
                    // (彩种为11选5类的 && 遗漏类型为单双) || 遗漏类型为中位
                    if ((danShuangSpecialManageLotteryids.contains(lotteryId) && trendType == TrendType.DANSHUANG) || trendType == TrendType.ZHONGWEI) {
                        values.add(g.getWebValue());
                    } else {
                        values.add(g.getValue());
                    }

                }
            } else {
                log.error("彩种ID为：[ " + lotteryId + " ]，玩法群为：[ " + gameGroupCode + " ]，遗漏类型值为：[ " + trendType.getValue()
                        + " ]的数据不存在！");
            }

            valueMap.put(trendType, values);
        }

        return valueMap;
    }*/
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
            for (TrendType trendType : trendTypes) {
                if (trendTypeListMap.get(trendType)==null) {
                	int trendTypeValue=trendType.getValue();
                	int tmpGroupCode=gameGroupCode;
                	if((lotteryId.longValue()+"").startsWith("995") || (lotteryId.longValue()+"").startsWith("996")){ 
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
                        gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId, tmpGroupCode, null,
                                null, trendTypeValue, num);
                    } else {
                        gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbylTimePeriod(lotteryId, tmpGroupCode, null,
                                null, trendTypeValue, startDate, endDate);
                    }
                    trendTypeListMap.put(trendType, gameTrendJbylList);
                } else {
                    gameTrendJbylList = trendTypeListMap.get(trendType);
                }

                String value;
                // (彩种为11选5类的 && 遗漏类型为单双) || 遗漏类型为中位
                if ((danShuangSpecialManageLotteryids.contains(lotteryId) && trendType == TrendType.DANSHUANG) || trendType == TrendType.ZHONGWEI) {
                    value = gameTrendJbylList.get(i).getWebValue();
                } else {
                    value = gameTrendJbylList.get(i).getValue();
                }
                //组合结构走势图基本结构体里的列表

                List<String> singleStrucList = makeSingleStrucList(value, lotteryId, trendType);
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
    private List<TrendType> matchedTrendTypes(Long lotteryId, Integer isGeneral, Integer gameGroupCode,
                                              Integer gameSetCode, Integer betMethodCode, String keySeperator2) {

        LotteryPlayMethodKeyGenerator lotteryKeyGenerator = new LotteryPlayMethodKeyGenerator(isGeneral, gameGroupCode,
                keySeperator);
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
