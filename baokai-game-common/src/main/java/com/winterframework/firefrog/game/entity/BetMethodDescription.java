package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: BetMethodDescription 
* @Description: 玩法描述实体Bean 
* @author Denny 
* @date 2013-8-21 下午3:16:08 
*  
*/
public class BetMethodDescription implements Serializable {

	private static final long serialVersionUID = 3778772526782608286L;

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
	/** 玩法提示描述_bak */
	private String gamePromptDes_bak;
	/** 投注示例 */
	private String gameExamplesDes;

	public BetMethodDescription() {
	}

	public BetMethodDescription(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode) {
		this.lotteryid = lotteryid;
		this.gameGroupCode = gameGroupCode;
		this.gameSetCode = gameSetCode;
		this.betMethodCode = betMethodCode;
	}

	/** 投注示例_bak */
	private String gameExamplesDes_bak;

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

	public String getGamePromptDes_bak() {
		return gamePromptDes_bak;
	}

	public void setGamePromptDes_bak(String gamePromptDes_bak) {
		this.gamePromptDes_bak = gamePromptDes_bak;
	}

	public String getGameExamplesDes_bak() {
		return gameExamplesDes_bak;
	}

	public void setGameExamplesDes_bak(String gameExamplesDes_bak) {
		this.gameExamplesDes_bak = gameExamplesDes_bak;
	}

	public String getBetTypeCode() {
		return gameGroupCode + "_" + gameSetCode + "_" + betMethodCode;
	}

}
