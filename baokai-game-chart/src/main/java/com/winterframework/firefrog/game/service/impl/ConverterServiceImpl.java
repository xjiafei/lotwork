package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IConverterService;
import com.winterframework.firefrog.game.service.IDataMakerService;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/** 
* @ClassName: ConverterServiceImpl 
* @Description:  走势图数据转换为前端页面的JSON数据结构
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("converterServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ConverterServiceImpl implements IConverterService {

	private Logger log = LoggerFactory.getLogger(ConverterServiceImpl.class);

	@Resource(name = "dataMakerMap")
	private Map<TrendType, IDataMakerService> dataMakerMap;

	@Resource(name = "noStatisticsDataTrendType")
	private List<TrendType> noStatisticsDataTrendType;

	@PropertyConfig(value = "lottery.BJKL8")
	private Long BJKL8;

	/** 页面显示的类型排列顺序 */
	private List<TrendType> displayedOrderedTrendTypeList;

	private static Map<Long, Integer[]> WEISHUBITSMAP = new HashMap<Long, Integer[]>();
	static {
		WEISHUBITSMAP.put(10L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(11L, new Integer[] { 1, 4 });
		WEISHUBITSMAP.put(12L, new Integer[] { 0, 2 });
		WEISHUBITSMAP.put(13L, new Integer[] { 2, 4 });
		WEISHUBITSMAP.put(333L, new Integer[] { 1, 3 });
		WEISHUBITSMAP.put(14L, new Integer[] { 3, 4 });
		WEISHUBITSMAP.put(15L, new Integer[] { 0, 1 });
		WEISHUBITSMAP.put(16L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(18L, new Integer[] { 0, 19 });
		WEISHUBITSMAP.put(22L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(23L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(24L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(25L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(26L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(27L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(28L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(29L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(30L, new Integer[] { 3, 4 });
		WEISHUBITSMAP.put(31L, new Integer[] { 0, 4 });
		WEISHUBITSMAP.put(32L, new Integer[] { 0, 6 });
		WEISHUBITSMAP.put(33L, new Integer[] { 0, 6 });
		WEISHUBITSMAP.put(34L, new Integer[] { 0, 6 });
		WEISHUBITSMAP.put(344L, new Integer[] { 0, 2 });

	}

	private static Map<Long, List<TrendType>> TRENDTYPEMAP = new HashMap<Long, List<TrendType>>();
	static {
		TRENDTYPEMAP.put(14L, new ArrayList<TrendType>() {
			{
				add(TrendType.WEISHU);
				add(TrendType.ZUXUAN);
				add(TrendType.FENBU);
				add(TrendType.KUADU);
				add(TrendType.SUMVAL);
			}
		});

		TRENDTYPEMAP.put(15L, new ArrayList<TrendType>() {
			{
				add(TrendType.WEISHU);
				add(TrendType.ZUXUAN);
				add(TrendType.FENBU);
				add(TrendType.KUADU);
				add(TrendType.SUMVAL);
			}
		});

		TRENDTYPEMAP.put(30L, new ArrayList<TrendType>() {
			{
				add(TrendType.WEISHU);
				add(TrendType.ZUXUAN);
				add(TrendType.FENBU);
				add(TrendType.KUADU);
				add(TrendType.SUMVAL);
			}
		});

	}

	@Override
	public GameTrendChartStruc converter(BaseTrendChartStruc baseTrendChartStruc, long lotteryId, long gameGroupCode,
			Integer isGeneral) {

		GameTrendChartStruc gameTrendChartStruc = new GameTrendChartStruc();
		if (baseTrendChartStruc == null || baseTrendChartStruc.getBaseChartStrucs().size() == 0) {
			gameTrendChartStruc.setIsSuccess(0);
		} else {
			gameTrendChartStruc.setIsSuccess(1);
			List<List<Integer>> numberRecords = getEffectiveNumberRecords(baseTrendChartStruc.getBaseChartStrucs(),
					gameGroupCode);
			List<Object> data = makeData(baseTrendChartStruc, numberRecords, lotteryId, gameGroupCode, isGeneral);
			gameTrendChartStruc.setData(data);

			List<Object> statistics = makeStatistics(baseTrendChartStruc);
			gameTrendChartStruc.setStatistics(statistics);
		}
		return gameTrendChartStruc;
	}

	@Override
	public QueryAssistActionResponse converter(List<BaseChartStruc> baseChartStrucs, long lotteryId,
			long gameGroupCode, Integer isGeneral) {

		BaseTrendChartStruc baseTrendChartStruc = new BaseTrendChartStruc();
		baseTrendChartStruc.setBaseChartStrucs(baseChartStrucs);
		QueryAssistActionResponse gameTrendChartStruc = new QueryAssistActionResponse();

		List<List<Integer>> numberRecords = getEffectiveNumberRecords(baseTrendChartStruc.getBaseChartStrucs(),
				gameGroupCode);
		List<Object> data = makeData(baseTrendChartStruc, numberRecords, lotteryId, gameGroupCode, isGeneral);
		gameTrendChartStruc.setData(data);

		return gameTrendChartStruc;
	}

	private List<Object> makeData(BaseTrendChartStruc baseTrendChartStruc, List<List<Integer>> numberRecords,
			Long lotteryId, Long gameGroupCode, Integer isGeneral) {

		List<Object> result = new ArrayList<Object>();
		List<Object> single = null;

		List<BaseChartStruc> baseChartStrucList = baseTrendChartStruc.getBaseChartStrucs();
		for (int i = 0; i < baseChartStrucList.size(); i++) {
			BaseChartStruc struc = baseChartStrucList.get(i);
			single = new ArrayList<Object>();
			single.add(struc.getWebIssueCode());
			// 北京快乐8不需要显示每一期的开奖号码
			if (lotteryId.longValue() != BJKL8) {
				single.add(struc.getNumberRecord());
			} else {
				String s = struc.getNumberRecord().replaceAll(",", "");
				single.add(s);
			}

			Map<TrendType, List<String>> map = struc.getChartStruc();

			displayedOrderedTrendTypeList = TRENDTYPEMAP.get(gameGroupCode);
			if (displayedOrderedTrendTypeList == null) {
				displayedOrderedTrendTypeList = orderAsDisplayed(map.keySet());
			}

			for (TrendType trendType : displayedOrderedTrendTypeList) {
				single = dataMakerMap.get(trendType).makeData(single, map.get(trendType), numberRecords, i, lotteryId,
						isGeneral);
			}
			if(lotteryId.longValue() == 99112l){
				single.add(getDiamondLv(struc.getNumberRecord()));
			}
			result.add(single);
		}

		return result;
	}

	private Long getDiamondLv(String numberRecord){
		char diamond = numberRecord.charAt(0);
		Long count = 0l;
		for(int i = 1; i<numberRecord.length(); i++){
			if(diamond == numberRecord.charAt(i)){
				count++;
			}
		}
		return count;
	}
	
	private List<Object> makeStatistics(BaseTrendChartStruc baseTrendChartStruc) {

		// 用于统计的类型
		List<TrendType> statisticsTrendType = new ArrayList<TrendType>();
		for (TrendType trendType : displayedOrderedTrendTypeList) {
			if (!noStatisticsDataTrendType.contains(trendType)) {
				statisticsTrendType.add(trendType);
			}
		}

		// 所有遗漏数据
		List<List<Integer>> omissionData = new ArrayList<List<Integer>>();
		List<BaseChartStruc> list = baseTrendChartStruc.getBaseChartStrucs();
		for (BaseChartStruc baseChartStruc : list) {
			List<Integer> eachIssueData = new ArrayList<Integer>();
			Map<TrendType, List<String>> chartStruc = baseChartStruc.getChartStruc();
			for (TrendType trendType : statisticsTrendType) {
				List<String> eachTypeForEachIssue = chartStruc.get(trendType);
				for (String cell : eachTypeForEachIssue) {
					for (String s : cell.trim().split(",")) {
						eachIssueData.add(Integer.parseInt(s));
					}
				}
			}
			omissionData.add(eachIssueData);
		}

		List<Object> result = new ArrayList<Object>();
		// 将所有遗漏数据转换为二维数组，便于统计计算
		int[][] statisticsOmissionData = convertOmissionData(omissionData);
		result.add(makeStatisticsTimes(statisticsOmissionData));
		result.add(makeStatisticsAverageOmission(statisticsOmissionData));
		result.add(makeStatisticsMaxOmission(statisticsOmissionData));
		result.add(makeStatisticsMaxContinuous(statisticsOmissionData));
		return result;
	}

	private int[][] convertOmissionData(List<List<Integer>> omissionData) {
		int[][] result = new int[omissionData.size()][omissionData.get(0).size()];
		for (int i = 0; i < omissionData.size(); i++) {
			for (int j = 0; j < omissionData.get(i).size(); j++) {
				result[i][j] = omissionData.get(i).get(j);
			}
		}
		return result;
	}

	private List<Integer> makeStatisticsTimes(int[][] statisticsOmissionData) {

		List<Integer> result = new ArrayList<Integer>();
		for (int j = 0; j < statisticsOmissionData[0].length; j++) {
			int times = 0;
			for (int i = 0; i < statisticsOmissionData.length; i++) {
				if (statisticsOmissionData[i][j] == 0) {
					times++;
				}
			}
			result.add(times);
		}
		return result;
	}

	private List<Integer> makeStatisticsAverageOmission(int[][] statisticsOmissionData) {
		// 平均遗漏值=总期数（统计期内）÷该类号码的总中出次数（统计期内）
		List<Integer> result = new ArrayList<Integer>();
		for (int j = 0; j < statisticsOmissionData[0].length; j++) {
			int times = 0;
			int omission = 0;
			for (int i = 0; i < statisticsOmissionData.length; i++) {
				if (statisticsOmissionData[i][j] == 0) {
					times++;
				}

				omission += statisticsOmissionData[i][j];
			}
			result.add(times == 0 ? omission / statisticsOmissionData.length : statisticsOmissionData.length / times);
		}

		return result;
	}

	private List<Integer> makeStatisticsMaxOmission(int[][] statisticsOmissionData) {
		List<Integer> result = new ArrayList<Integer>();
		for (int j = 0; j < statisticsOmissionData[0].length; j++) {
			int maxOmission = 0;
			for (int i = 0; i < statisticsOmissionData.length; i++) {
				if (statisticsOmissionData[i][j] > maxOmission) {
					maxOmission = statisticsOmissionData[i][j];
				}
			}
			result.add(maxOmission);
		}

		return result;
	}

	private List<Integer> makeStatisticsMaxContinuous(int[][] statisticsOmissionData) {
		List<Integer> result = new ArrayList<Integer>();

		for (int j = 0; j < statisticsOmissionData[0].length; j++) {
			int maxContinuous = 0;
			int temp = 0;
			boolean flag = false;
			for (int i = 0; i < statisticsOmissionData.length; i++) {

				if (statisticsOmissionData[i][j] == 0) {
					if (flag) {
						temp++;
					} else {
						if (temp > maxContinuous) {
							maxContinuous = temp;
						}

						temp = 1;
					}
					flag = true;

				} else {
					flag = false;
				}
			}
			result.add(maxContinuous);
		}

		return result;
	}

	private List<List<Integer>> getEffectiveNumberRecords(List<BaseChartStruc> baseChartStrucList, long gameGroupCode) {

		Integer[] bits = WEISHUBITSMAP.get(gameGroupCode);

		List<List<Integer>> numberRecords = new ArrayList<List<Integer>>();
		List<Integer> nr = null;
		for (BaseChartStruc gdr : baseChartStrucList) {
			nr = new ArrayList<Integer>();

			String numberRecord = gdr.getNumberRecord();
			String[] arr = null;
			if (numberRecord.contains(",")) {
				arr = numberRecord.split(",");
				for (int i = bits[0]; i <= bits[1]; i++) {
					nr.add(Integer.parseInt(arr[i]));
				}
			} else {
				if (numberRecord.length() == (bits[1] - bits[0] + 1)) {
					for (int i = 0; i < numberRecord.length(); i++) {
						nr.add(Integer.parseInt(numberRecord.substring(i, i + 1)));
					}
				} else {
					for (int i = bits[0]; i <= bits[1]; i++) {
						nr.add(Integer.parseInt(numberRecord.substring(i, i + 1)));
					}

				}
			}

			numberRecords.add(nr);
		}

		return numberRecords;
	}

	private static List<TrendType> orderAsDisplayed(Set<TrendType> set) {
		List<TrendType> list = new ArrayList<TrendType>();

		List<Integer> orderedNumbers = new ArrayList<Integer>();
		for (TrendType trendType : set) {
			orderedNumbers.add(trendType.getIndex());
		}
		Integer[] arr = orderedNumbers.toArray(new Integer[] {});
		Arrays.sort(arr);

		for (Integer index : arr) {
			for (TrendType trendType : set) {
				if (trendType.getIndex() == index) {
					list.add(trendType);
					continue;
				}
			}
		}

		return list;
	}

}
