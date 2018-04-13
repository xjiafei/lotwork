package com.winterframework.firefrog.game.entity;

/** 
* @ClassName: TrendType 
* @Description: 走势图遗漏数据类型 
* @author Denny 
* @date 2014-4-4 下午2:55:11 
*  
*/
public enum TrendType {
	WEISHU(1, 1), FENBU(2, 2), KUADU(3, 3), ZUXUAN(4, 11), HEZHI(5, 16), KL8(6, 15), DANSHUANG(7, 6), ZHONGWEI(8, 14), JIOU(9, 17), DAXIAO(10, 4), DAXIAORATE(
			11, 5), DANSHUANGRATE(12, 7), ZHIHERATE(13, 9), SUMVAL(14, 13), ZHIHE(15, 8), LIN12(16, 10), KUADUVAL(17, 12), SUMVALMANTISSA(18, 18),
    SSQ(1, 5),
    SSQ_RED(2, 5),
    SSQ_BLUE(3, 5),
    HAOMAZOUSHI(19,19),
	HEZHIZOUSHI(20,20),
	HEZHIZUHE(21,21),
	HAOMAXINGTAI(22,22);
	;
	private final int value;
	private final int index;

	TrendType(int value, int index) {
		this.value = value;
		this.index = index;
	}

	public int getValue() {
		return value;
	}
	
	public int getIndex() {
		return index;
	}

}
