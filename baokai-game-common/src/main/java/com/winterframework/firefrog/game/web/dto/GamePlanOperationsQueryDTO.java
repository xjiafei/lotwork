package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GamePlanOperationsQueryDTO 
* @Description: 追号记录运营查询 
* @author Alan
* @date 2013-10-14 下午2:48:52 
*  
*/
public class GamePlanOperationsQueryDTO {

	private Long userid;
	
	private GamePlanOperationsRequest queryRequest;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public GamePlanOperationsRequest getQueryRequest() {
		return queryRequest;
	}

	public void setQueryRequest(GamePlanOperationsRequest queryRequest) {
		this.queryRequest = queryRequest;
	}

}
