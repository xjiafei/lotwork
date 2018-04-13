/**   
* @Title: TrendTypeEnum.java 
* @Package com.winterframework.firefrog.game.service.gametrend.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午1:58:45 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.config;

/** 
* @ClassName: TrendTypeEnum 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-2 下午1:58:45 
*  
*/
public enum TrendTypeEnum {
	WeiShu("WeiShu", "1"), FenBu("FenBu", "2"), KuaDu("KuaDu", "3"), ZuXuan("ZuXuan", "4"), HeZhi("HeZhi", "5"), KL8(
			"KL8", "6"), DanShuang("DanShuang", "7"), ZhongWei("ZhongWei", "8"), JiOu("JiOu", "9"), DaXiao("DaXiao",
			"10"), DaXiaoRate("DaXiaoRate", "11"), DanShuangRate("DanShuangRate", "12"), ZhiHeRate("ZhiHeRate", "13"), SumVal(
			"SumVal", "14"), ZhiHe("ZhiHe", "15"), Lin12("Lin12", "16"), KuaDuVal("KuaDuVal", "17"), NumberRecord(
			"NumberRecord", "100"), HeZhiZuHe("HeZhiZuHe", "19"), HaoMaXingTai("HaoMaXingTai", "20");

	private String name;
	private String index;

	private TrendTypeEnum(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
