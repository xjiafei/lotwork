package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameSpiteOrderQueryRequestDTO 
* @Description: 恶意记录requestDTO
* @author Alan
* @date 2013-10-23 下午3:13:02 
*  
*/
public class GameSpiteOrderQueryRequestDTO {

	private Long userid;
	
	private GameSpiteOrderQueryRequest request;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public GameSpiteOrderQueryRequest getRequest() {
		return request;
	}

	public void setRequest(GameSpiteOrderQueryRequest request) {
		this.request = request;
	}
	
}
