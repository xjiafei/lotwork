package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: BetMethodStatusStruc 
* @Description: 投注方式销售状态基本结构 
* @author Denny 
* @date 2013-8-21 下午4:52:49 
*  
*/
public class BetMethodStatusStruc implements Serializable {

	private static final long serialVersionUID = 2690858181018199596L;

	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 投注方式销售状态：0，停售；1，可销售 */
	private Integer status;
	/** 投注方式销售状态：标识是否更改 */
	private boolean status_changed = false;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isStatus_changed() {
		return status_changed;
	}

	public void setStatus_changed(boolean status_changed) {
		this.status_changed = status_changed;
	}


}
