package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameWarnOrderQueryDTO 
* @Description: 风险记录查询DTO
* @author Alan
* @date 2013-10-15 下午1:54:10 
*  
*/
public class GameWarnOrderQueryDTO {

	private Long userid;
	
	private GameWarnOrderQueryRequest queryParam;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public GameWarnOrderQueryRequest getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(GameWarnOrderQueryRequest queryParam) {
		this.queryParam = queryParam;
	}

}
