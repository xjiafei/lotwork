package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameMissingValueQueryRequest 
* @Description: 遗漏值查询Request
* @author Denny 
* @date 2013-7-22 下午4:38:13 
*  
*/
public class GameMissingValueQueryRequest implements Serializable {

	private static final long serialVersionUID = 9113814704215723439L;

	/** 彩种 */
	private long lotteryId;
	/** 玩法群 */
	private int gameGroupCode;
	/** 玩法组 */
	private int gameSetCode;
	/** 投注方式 */
	private int betMethodCode;
	/** 查询类型：1 当前遗漏， 2 最大遗漏 */
	private int type;

	public long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(int gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public int getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(int gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public int getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(int betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
