package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: SellingStatus 
* @Description: 销售状态实体Bean 
* @author Denny 
* @date 2013-8-21 下午3:16:24 
*  
*/
public class SellingStatus implements Serializable {

	private static final long serialVersionUID = 10902325138873633L;

	/** 彩种 */
	private Long lotteryid;
	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 销售状态：0，停售；1，在售 */
	private Integer status;
	/** 销售状态：是否更改标识 */
	private boolean status_changed = false;
	/** 理论奖金 */
	private Long theoryBonus;
	
	private Date createTime;
	private Date updateTime;

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

	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
