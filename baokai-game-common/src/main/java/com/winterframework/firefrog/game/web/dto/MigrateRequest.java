package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName MigrateRequest 
* @Description 活动投注记录到活动记录接口请求
* @author  david
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class MigrateRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long fundTime;
	
	private Long betTime;
	
	private Long updateTime;
	
	private Long id;//userid

	public Long getFundTime() {
		return fundTime;
	}

	public void setFundTime(Long fundTime) {
		this.fundTime = fundTime;
	}

	public Long getBetTime() {
		return betTime;
	}

	public void setBetTime(Long betTime) {
		this.betTime = betTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
