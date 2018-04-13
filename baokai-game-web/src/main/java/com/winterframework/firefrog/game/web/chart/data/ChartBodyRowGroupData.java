package com.winterframework.firefrog.game.web.chart.data;

import java.util.List;

/**
 * @Title 走势图数据区行内分组数据
 * 
 * @author bob
 *
 */
public class ChartBodyRowGroupData {
	
	// 奖期号
	public static final String WEBISSUECODE = "webissuecode";
	
	// 中奖号码
	public static final String NUMBERRECORD = "numberrecord";
	
	// 位数分布
	public static final String WEISHUFENBU = "weishufenbu";
	
	// 号码分布
	public static final String HAOMAFENBU = "haomafenbu";
	
	// 组选
	public static final String ZUXUAN = "zuxuan";
	
	// 和值
	public static final String HEZHI = "hezhi";
	
	// 跨度
	public static final String KUADU = "kuadu";
	
	// 单双
	public static final String DANSHUANG = "danshuang";	
	
	// 中位数
	public static final String ZHONGWEI = "zhongwei";	
	
	// 奇偶
	public static final String JIOU = "jiou";	
	
	// 大小
	public static final String DAXIAO = "daxiao";		
	
	// 开奖号码格式 如“**###”
	private String numberRecordFormat;
	
	// 样式类别 
	private String styleType;
	
	// 分组内格子数据
	private List<String> balls;

	public List<String> getBalls() {
		return balls;
	}

	public void setBalls(List<String> balls) {
		this.balls = balls;
	}

	public String getStyleType() {
		return styleType;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	public String getNumberRecordFormat() {
		return numberRecordFormat;
	}

	public void setNumberRecordFormat(String numberRecordFormat) {
		this.numberRecordFormat = numberRecordFormat;
	}


}
