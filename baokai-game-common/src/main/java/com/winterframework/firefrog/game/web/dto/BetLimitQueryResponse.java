package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetLimitQueryResponse 
* @Description: 投注限制查询响应参数封装对象
* @author Denny 
* @date 2013-8-21 下午4:42:30 
*  
*/
public class BetLimitQueryResponse implements Serializable {

	private static final long serialVersionUID = 8054841765235869264L;

	@NotNull
	private List<BetMethodMultipleStruc> betMethodMultipleStruc;
	
	private Integer status;

	public List<BetMethodMultipleStruc> getBetLimitList() {
		return betMethodMultipleStruc;
	}

	public void setBetLimitList(List<BetMethodMultipleStruc> betMethodMultipleStruc) {
		this.betMethodMultipleStruc = betMethodMultipleStruc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
