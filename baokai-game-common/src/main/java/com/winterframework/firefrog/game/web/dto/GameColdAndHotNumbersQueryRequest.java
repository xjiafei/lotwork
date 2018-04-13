package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameColdAndHotNumbersQueryRequest 
* @Description: 冷热查询QueryRequest
* @author Denny 
* @date 2013-7-22 下午4:37:17 
*  
*/
public class GameColdAndHotNumbersQueryRequest implements Serializable {

	private static final long serialVersionUID = -1691704166950275910L;

	/** 彩种 */
	private long lotteryId;
	/** 玩法群 */
	private int gameGroupCode;
	/** 玩法组 */
	private int gameSetCode;
	/** 投注方式 */
	private int betMethodCode;
	/** 期数 */
	private int countIssue;

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

	public int getCountIssue() {
		return countIssue;
	}

	public void setCountIssue(int countIssue) {
		this.countIssue = countIssue;
	}

}
