package com.winterframework.firefrog.game.web.dto;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/** 
* @ClassName: HelpCode 
* @Description: 玩法描述
* @author Alan
* @date 2013-9-26 上午11:25:38 
*  
*/
public class HelpCode {

	/** 投注方式 */
	private Integer betMethodCode;
	/** 投注方式名称 */
	private String betMethodName;
	/** 玩法提示描述 */
	private String gamePromptDes;
	/** 玩法提示描述(修改前) */
	private String gamePromptDes_bak;
	/** 投注示例 */
	private String gameExamplesDes;
	/** 投注示例(修改前) */
	private String gameExamplesDes_bak;
	
	public Integer getBetMethodCode() {
		return betMethodCode;
	}
	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}
	public String getBetMethodName() {
//		betMethodName = GameAwardNameUtil.methodCodeName(betMethodCode);
		return betMethodName;
	}
	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
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

}
