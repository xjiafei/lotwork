package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GameSpiteOrderQueryResponse 
* @Description: 恶意记录response 
* @author Alan
* @date 2013-10-23 下午3:13:58 
*  
*/
public class GameSpiteOrderQueryResponse {

	private List<SpiteOrderStruc> spiteOrderStrucs;

	public List<SpiteOrderStruc> getSpiteOrderStrucs() {
		return spiteOrderStrucs;
	}

	public void setSpiteOrderStrucs(List<SpiteOrderStruc> spiteOrderStrucs) {
		this.spiteOrderStrucs = spiteOrderStrucs;
	}

}
