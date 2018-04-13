package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.IBaseOmissionService;
import com.winterframework.firefrog.game.service.IConverterService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.web.dto.*;
import com.winterframework.firefrog.game.web.util.WapChart;
import com.winterframework.modules.web.util.JsonMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: BaseOmissionServiceImpl 
* @Description:  走势图数据查询入口
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("baseOmissionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BaseOmissionServiceImpl implements IBaseOmissionService {

	private Logger log = LoggerFactory.getLogger(BaseOmissionServiceImpl.class);

	@Resource(name = "gameTrendAssistServiceImpl")
	private IGameTrendService gameTrendAssistServiceImpl;

	@Resource(name = "gameTrendJbylServiceImpl")
	private IGameTrendService gameTrendJbylServiceImpl;

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;

	@Resource(name = "converterServiceImpl")
	private IConverterService converterServiceImpl;

	@Resource(name = "ballRengeMap")
	private Map<String, String> ballRengeMap;

	@Resource(name = "RedisClient")
	private RedisClient redisClient;

/*    @Resource(name = "chart.chache.switch")
    private String chartChacheSwitch;*/
	private final String CHART_KEY = "CHART_KEY";
	private final String CHART_SEPARATE = "_";

	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "liveTime")
	private Map<String, String> liveTime;
    @Resource(name = "gameTrendJbylDaoImpl")
    private IGameTrendJbylDao gameTrendJbylDaoImpl;
    @Override
    public List<BaseChartStruc> queryBaseChartStruc(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
                                                    Integer betMethodCode, int num) throws Exception {
        try {
            // 取走势图数据
            return gameTrendAssistServiceImpl.queryOmissionValue(lotteryId,
                    null, gameGroupCode, gameSetCode, betMethodCode, num, null, null).getBaseChartStrucs();
        } catch (Exception e) {
            log.error("查询走势辅助数据出错", e);
            throw e;
        }

    }

	@Override
	public GameTrendChartStruc queryTrendCharts(Long lotteryId, Integer gameGroupCode, Integer type, Integer isGeneral,
			Integer num, Long startDate, Long endDate) throws Exception {

		log.info("开始查询基本遗漏走势图数据......");
//        String key = redisKey(lotteryId, gameGroupCode, type, isGeneral, num, startDate, endDate);

        BaseTrendChartStruc baseTrendChartStruc = null;

		try {
			if (type == 1) {
                baseTrendChartStruc = gameTrendJbylServiceImpl.queryOmissionValue(lotteryId, isGeneral, gameGroupCode,
						null, null, num, null, null);
			} else {
                baseTrendChartStruc = gameTrendJbylServiceImpl.queryOmissionValue(lotteryId, isGeneral,
						gameGroupCode, null, null, null, DataConverterUtil.convertLong2Date(startDate),
						DataConverterUtil.convertLong2Date(endDate));
			}
			//2.转换成基本走势图的JSON数据结构
			GameTrendChartStruc result = converterServiceImpl.converter(baseTrendChartStruc,
					lotteryId, gameGroupCode, isGeneral);
			return result;
		} catch (Exception e) {
			log.error("查询基本遗漏走势图数据出错", e);
			throw e;
		}

	}
	@Override
	public List<GameTrendReport> queryTrendReport(GameTrendQueryRequest request) throws Exception {

            //走势图基本结构列表
        List<GameTrendJbyl> gameTrendJbylList = null;
        if (request.getType() == 1) {
            gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(request.getLotteryId(), null, null,
                    null, 100, request.getIssue());
        } else {
            gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbylTimePeriod(request.getLotteryId(), null, null,
                    null, 100, DataConverterUtil.convertLong2Date(request.getStartDate()), DataConverterUtil.convertLong2Date(request.getEndDate()));
        }
		if (gameTrendJbylList != null) {
			List<GameTrendReport> result = new ArrayList<GameTrendReport>();
			for (GameTrendJbyl gdr : gameTrendJbylList) {
				GameTrendReport report = new GameTrendReport();
				report.setNumberRecord(gdr.getLotteryid() == 99401l ? gdr.getValue().replace("+", ",") : gdr
						.getValue());
				report.setWebIssueCode(gdr.getWebIssueCode());
				result.add(report);
			}
			return result;
		}

		return null;
	}


    private String redisKey(Long lotteryId, Integer gameGroupCode, Integer type, Integer isGeneral,
                            Integer num, Long startDate, Long endDate){
        return lotteryId.toString() + "_" + gameGroupCode.toString() + "_" + type.toString() + "" + isGeneral.toString()
                + num == null ? "" : num.toString() + startDate == null ? "" :startDate.toString() +
                num.toString() + startDate == null ? "" :startDate.toString();
    }

	@Override
	public List<WapChart> getWapChart(Long lotteryId) {
		List<GameTrendJbyl> gameTrendList = gameTrendJbylDaoImpl.getWapChart(lotteryId);
		return convertGameTrendTogetWapChart(gameTrendList);
	}

	
	private List<WapChart> convertGameTrendTogetWapChart(List<GameTrendJbyl> gameTrendList) {
		List<WapChart> list = new ArrayList<WapChart>();
		for(GameTrendJbyl gameTrend : gameTrendList){
			WapChart wapChart = new WapChart();
			if(gameTrend.getLotteryid() == 99105){
				wapChart.setWebIssueCode(gameTrend.getWebIssueCode().substring(gameTrend.getWebIssueCode().length()-3, gameTrend.getWebIssueCode().length()));
			}else{
				String[] str = gameTrend.getWebIssueCode().split("-");
				wapChart.setWebIssueCode(str[1].toString());
			}
			wapChart.setNumberRecord(gameTrend.getNumberRecord());
			String number = gameTrend.getNumberRecord();
			Long totValue = 0l;
			Long big = 0l;
			Long smill = Long.valueOf(String.valueOf(number.charAt(2)));
			for(int i = 2 ; i < number.length() ; i++ ){
				Long record = Long.valueOf(String.valueOf(number.charAt(i)));
				totValue += record;
				if(big < record){
					big = record ;
				}
				if(smill > record){
					smill = record ;
				}
			}
			wapChart.setKuadu(big-smill);
			wapChart.setTotValue(totValue);
			wapChart.setSuper2000(number.charAt(0) != number.charAt(1));
			list.add(wapChart);
		}
		return list;
	}

}
