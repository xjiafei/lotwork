package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: BetMethodDescription 
* @Description: 玩法描述和奖金实体Bean 
* @author Denny 
* @date 2013-8-21 下午3:16:08 
*  
*/
public class BetMethodDescriptionJoinBonus implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4179927334520373088L;
	/** 彩种 */
	private Long lotteryid;
	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 玩法提示描述 */
	private String gamePromptDes;
	/** 投注示例 */
	private String gameExamplesDes;
	
	private Long actualBonus;
	private Long theoryBonus;	//玩法理论奖金
	private Long retPoint;	//玩法返点
	private Long actualBonusDown;
	private Boolean moreBouns;//是否多奖金，前台盈利追号标识

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public String getGamePromptDes() {
		return gamePromptDes;
	}

	public void setGamePromptDes(String gamePromptDes) {
		this.gamePromptDes = gamePromptDes;
	}

	public String getGameExamplesDes() {
		return gameExamplesDes;
	}

	public void setGameExamplesDes(String gameExamplesDes) {
		this.gameExamplesDes = gameExamplesDes;
	}

	public Long getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}
	
	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}
	
	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Boolean getMoreBouns() {
		return moreBouns;
	}

	public void setMoreBouns(Boolean moreBouns) {
		this.moreBouns = moreBouns;
	}

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}
	

}
