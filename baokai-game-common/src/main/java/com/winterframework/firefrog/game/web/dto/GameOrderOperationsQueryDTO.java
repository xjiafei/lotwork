package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameOrderOperationsQueryDTO 
* @Description: 订单记录运营查询DTO
* @author Alan
* @date 2013-10-14 下午5:30:33 
*  
*/
public class GameOrderOperationsQueryDTO {

	private Long userid;
	
	private GameOrderOperationsQueryRequest queryRequest;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public GameOrderOperationsQueryRequest getQueryRequest() {
		return queryRequest;
	}

	public void setQueryRequest(GameOrderOperationsQueryRequest queryRequest) {
		this.queryRequest = queryRequest;
	}

}
