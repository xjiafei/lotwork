package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameMutipleQueryResponse 
* @Description: 投注限制查询Response
* @author Alan
* @date 2013-8-26 下午2:02:16 
*  
*/
public class GameMultipleQueryResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer maxMutiple;
	private Integer maxCountIssue;
	
	public GameMultipleQueryResponse(){
		
	}

	public Integer getMaxMutiple() {
		return maxMutiple;
	}

	public void setMaxMutiple(Integer maxMutiple) {
		this.maxMutiple = maxMutiple;
	}

	public Integer getMaxCountIssue() {
		return maxCountIssue;
	}

	public void setMaxCountIssue(Integer maxCountIssue) {
		this.maxCountIssue = maxCountIssue;
	}
	
}
