package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.net.URLDecoder;

/** 
* @ClassName: BetMethodHelpStruc 
* @Description: 投注方式帮助基本结构 
* @author Denny 
* @date 2013-8-21 下午4:47:11 
*  
*/
public class BetMethodHelpStruc implements Serializable {

	private static final long serialVersionUID = -2984208444341891733L;

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
	/** 投注示例_bak */
	private String gameExamplesDes_bak;

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

	@SuppressWarnings("deprecation")
	public String getGamePromptDes() {
		return gamePromptDes == null ? null : URLDecoder.decode(gamePromptDes);
	}

	@SuppressWarnings("deprecation")
	public void setGamePromptDes(String gamePromptDes) {
		this.gamePromptDes = gamePromptDes==null ? null : URLDecoder.decode(gamePromptDes);
	}

	@SuppressWarnings("deprecation")
	public String getGameExamplesDes() {
		return gameExamplesDes == null ? null : URLDecoder.decode(gameExamplesDes);
	}

	@SuppressWarnings("deprecation")
	public void setGameExamplesDes(String gameExamplesDes) {
		this.gameExamplesDes = gameExamplesDes==null ? null : URLDecoder.decode(gameExamplesDes);
	}

	@SuppressWarnings("deprecation")
	public String getGamePromptDes_bak() {
		return gamePromptDes_bak == null ? null : URLDecoder.decode(gamePromptDes_bak);
	}

	@SuppressWarnings("deprecation")
	public void setGamePromptDes_bak(String gamePromptDes_bak) {
		this.gamePromptDes_bak = gamePromptDes_bak==null ? null : URLDecoder.decode(gamePromptDes_bak);
	}

	@SuppressWarnings("deprecation")
	public String getGameExamplesDes_bak() {
		return gameExamplesDes_bak == null ? null : URLDecoder.decode(gameExamplesDes_bak);
	}

	@SuppressWarnings("deprecation")
	public void setGameExamplesDes_bak(String gameExamplesDes_bak) {
		this.gameExamplesDes_bak = gameExamplesDes_bak==null ? null : URLDecoder.decode(gameExamplesDes_bak);
	}
}
