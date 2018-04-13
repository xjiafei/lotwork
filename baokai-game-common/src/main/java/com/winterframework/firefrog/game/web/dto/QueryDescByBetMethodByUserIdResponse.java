package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryDescByBetMethodResponse 
* @Description: 按投注方式查询玩法描述响应参数封装DTO 
* @author Denny 
* @date 2013-10-12 上午11:36:27 
*  
*/
public class QueryDescByBetMethodByUserIdResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -935947732843158276L;
	private String gamePromptDes;
	private String gameExamplesDes;
	private Long actualBonus;
	private Long theoryBonus;	//玩法理论奖金
	private Long actualBonusDown;
	private Long retPoint;	//玩法返点
	private Boolean moreBouns;//是否多奖金


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

	public void setMoreBouns(Boolean moreBouns) {
		this.moreBouns = moreBouns;
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

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}


}
