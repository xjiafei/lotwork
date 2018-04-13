package com.winterframework.firefrog.game.web.chart.selector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.web.chart.IChartDataSelector;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowData;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupData;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;

/**
 * @Title 走势图数据筛选器
 * 
 * 将传入的数据翻译为每行所需要的数据，组装每行数据为List对象
 * 期号和中奖号码单独设置在行数据体内，其他格子数据内设置为List<ChartBodyRowGroupData>交由分组筛选器处理
 * 
 * @author bob
 *
 */
public class ChartDataSelector implements IChartDataSelector {

	private String numberRecordFormat;

	@Override
	public Object select(Object data) {
		// 传入的数据
		@SuppressWarnings("unchecked")
		List<BaseChartStruc> strucs = (List<BaseChartStruc>) data;

		List<ChartBodyRowData> bodyItemDatas = new ArrayList<ChartBodyRowData>();

		if (strucs != null && strucs.size() > 0) {

			for (BaseChartStruc struc : strucs) {

				ChartBodyRowData bodyRow = new ChartBodyRowData();

				List<ChartBodyRowGroupData> groups = new ArrayList<ChartBodyRowGroupData>();

				// 奖期号
				ChartBodyRowGroupData issueCodeGroup = new ChartBodyRowGroupData();
				List<String> issueCode = new ArrayList<String>();
				issueCode.add(struc.getWebIssueCode());
				issueCodeGroup.setBalls(issueCode);
				issueCodeGroup.setStyleType(ChartBodyRowGroupData.WEBISSUECODE);
				groups.add(issueCodeGroup);

				// 开奖号码
				ChartBodyRowGroupData numberRecordGroup = new ChartBodyRowGroupData();
				List<String> numberRecord = new ArrayList<String>();
				String nr = null;
				StringBuilder sb = new StringBuilder();
				if (struc.getNumberRecord().contains(",")) {
					String[] nrs = struc.getNumberRecord().split(",");
					for (String record : nrs) {
						sb.append(record);
						sb.append(" #");
					}
					nr = sb.toString();
					nr = nr.substring(0, nr.length() - 2);
				} else {
					for (int i = 0; i < struc.getNumberRecord().length(); i++) {
						sb.append(struc.getNumberRecord().substring(i, i+1));
						sb.append("#");
					}
					nr = sb.toString();
					nr = nr.substring(0, nr.length() - 1);
				}
				
				numberRecord.add(nr);
				
				numberRecordGroup.setBalls(numberRecord);
				numberRecordGroup.setStyleType(ChartBodyRowGroupData.NUMBERRECORD);
				numberRecordGroup.setNumberRecordFormat(numberRecordFormat);
				groups.add(numberRecordGroup);

				// 位数分布数据处理
				if (struc.getChartStruc().containsKey(TrendType.WEISHU)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.WEISHU);
					if(chartStrucList!=null && chartStrucList.size()>0){
						for (String chartStruc : chartStrucList) {
							String[] ballV = StringUtils.split(chartStruc, ",");
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							List<String> balls = new ArrayList<String>();

							for (String ball : ballV) {
								balls.add(ball);
							}

							group.setStyleType(ChartBodyRowGroupData.WEISHUFENBU);
							group.setBalls(balls);

							groups.add(group);
						}
					} 
				}

				// 跨度
				if (struc.getChartStruc().containsKey(TrendType.KUADU)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.KUADU);
					if(chartStrucList!=null && chartStrucList.size()>0){  
						for (String chartStruc : chartStrucList) {
							
							String[] ballV = StringUtils.split(chartStruc, ",");
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							for (String ball : ballV) {
								balls.add(ball);
							}
							
							group.setStyleType(ChartBodyRowGroupData.KUADU);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				// 和值
				if (struc.getChartStruc().containsKey(TrendType.HEZHI)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.HEZHI);
					if(chartStrucList!=null && chartStrucList.size()>0){  
						for (String chartStruc : chartStrucList) {
							
							String[] ballV = StringUtils.split(chartStruc, ",");
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							for (String ball : ballV) {
								balls.add(ball);
							}
							
							group.setStyleType(ChartBodyRowGroupData.HEZHI);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				// 号码分布
				if (struc.getChartStruc().containsKey(TrendType.FENBU)) {
					List<String> chartStrucList = struc.getChartStruc().get(TrendType.FENBU);
					if(chartStrucList!=null && chartStrucList.size()>0){  
						for (String chartStruc : chartStrucList) {
							
							String[] ballV = StringUtils.split(chartStruc, ",");
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							for (String ball : ballV) {
								balls.add(ball);
							}
							
							group.setStyleType(ChartBodyRowGroupData.HAOMAFENBU);
							group.setBalls(balls);
							groups.add(group);
							
						}
					}
				}

				// 组选类型
				if (struc.getChartStruc().containsKey(TrendType.ZUXUAN)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.ZUXUAN);
					if(chartStrucList!=null && chartStrucList.size()>0){  
						for (String chartStruc : chartStrucList) {
							
							String[] ballV = StringUtils.split(chartStruc, ",");
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							for (String ball : ballV) {
								balls.add(ball);
							}
							
							group.setStyleType(ChartBodyRowGroupData.ZUXUAN);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				// 单双
				if (struc.getChartStruc().containsKey(TrendType.DANSHUANG)) {
					List<String> chartStrucList = struc.getChartStruc().get(TrendType.DANSHUANG);
					if(chartStrucList!=null && chartStrucList.size()>0){ 						
						for (String chartStruc : chartStrucList) {
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							balls.add(chartStruc);
							
							group.setStyleType(ChartBodyRowGroupData.DANSHUANG);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				// 中位
				if (struc.getChartStruc().containsKey(TrendType.ZHONGWEI)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.ZHONGWEI);
					if(chartStrucList!=null && chartStrucList.size()>0){  
						for (String chartStruc : chartStrucList) {
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							balls.add(chartStruc);
							
							group.setStyleType(ChartBodyRowGroupData.ZHONGWEI);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}

				}

				// 大小
				if (struc.getChartStruc().containsKey(TrendType.DAXIAO)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.DAXIAO);
					if(chartStrucList!=null && chartStrucList.size()>0){ 
						for (String chartStruc : chartStrucList) {
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							balls.add(chartStruc);
							
							group.setStyleType(ChartBodyRowGroupData.DAXIAO);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				// 奇偶
				if (struc.getChartStruc().containsKey(TrendType.JIOU)) {

					List<String> chartStrucList = struc.getChartStruc().get(TrendType.JIOU);
					if(chartStrucList!=null && chartStrucList.size()>0){ 
						for (String chartStruc : chartStrucList) {
							
							ChartBodyRowGroupData group = new ChartBodyRowGroupData();
							
							List<String> balls = new ArrayList<String>();
							
							balls.add(chartStruc);
							
							group.setStyleType(ChartBodyRowGroupData.JIOU);
							group.setBalls(balls);
							
							groups.add(group);
							
						}
					}
				}

				bodyRow.setGroups(groups);

				bodyItemDatas.add(bodyRow);
			}
		}
		return bodyItemDatas;
	}

	public String getNumberRecordFormat() {
		return numberRecordFormat;
	}

	public void setNumberRecordFormat(String numberRecordFormat) {
		this.numberRecordFormat = numberRecordFormat;
	}

}
