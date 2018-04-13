package com.winterframework.firefrog.game.dao.vo;

/** 
* @ClassName GameBonusAwardJsonBean 
* @Description 每个玩法中奖中多少钱 
* @author  hugh
* @date 2014年8月1日 上午11:16:40 
*  
*/
public class GameBonusAwardJsonBean {
	private String gameBetType; //1_1_1_1 包含辅助位数
	private Long maxAward; //最高中多少钱
	private Long minAward; //最低中多少钱
	
	
	public String getGameBetType() {
		return gameBetType;
	}
	public void setGameBetType(String gameBetType) {
		this.gameBetType = gameBetType;
	}
	public Long getMaxAward() {
		return maxAward;
	}
	public void setMaxAward(Long maxAward) {
		this.maxAward = maxAward;
	}
	public Long getMinAward() {
		return minAward;
	}
	public void setMinAward(Long minAward) {
		this.minAward = minAward;
	}
	
	

}
