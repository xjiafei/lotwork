package com.winterframework.firefrog.game.entity;

/** 
* @ClassName: GamePlansFuturesEntity 
* @Description: 追号明细基本结构映射类 
* @author Alan
* @date 2013-10-21 上午11:53:10 
*  
*/
public class GamePlansFuturesEntity {

	/**追号明细ID*/
	private Long planDetailsId;
	/**追号倍数*/
	private Integer mutiple;
	/**投注总金额*/
	private Long totalAmount;
	/**状态*/
	private Integer status;
	
	public Long getPlanDetailsId() {
		return planDetailsId;
	}
	public void setPlanDetailsId(Long planDetailsId) {
		this.planDetailsId = planDetailsId;
	}
	public Integer getMutiple() {
		return mutiple;
	}
	public void setMutiple(Integer mutiple) {
		this.mutiple = mutiple;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
