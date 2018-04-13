/**   
* @Title: GameTrendQueryRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.GameTrendQueryRequest.java 
* @author Denny  
* @date 2014-6-17 下午3:09:22 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: GameTrendQueryRequest 
* @Description: 基本走势图数据查询对象
* @author Denny 
* @date 2014-6-17 下午3:09:22 
*  
*/
public class GameTrendQueryRequest implements Serializable {

	private static final long serialVersionUID = 4987435493779435710L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private Integer gameGroupCode;
	@NotNull
	/** 查询类型：1，按期数；2，按起始日期 */
	private Integer type;
	/** 在五星查询时为必填，1为基本走势，0为五星综合走势 */
	private Integer isGeneral;
	/** 期数 */
	private Integer issue;
	private Long startDate;
	private Long endDate;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsGeneral() {
		return isGeneral;
	}

	public void setIsGeneral(Integer isGeneral) {
		this.isGeneral = isGeneral;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
}
