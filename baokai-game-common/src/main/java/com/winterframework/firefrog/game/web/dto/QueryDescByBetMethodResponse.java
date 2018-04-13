package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: QueryDescByBetMethodResponse 
* @Description: 按投注方式查询玩法描述响应参数封装DTO 
* @author Denny 
* @date 2013-10-12 上午11:36:27 
*  
*/
public class QueryDescByBetMethodResponse implements Serializable {

	private static final long serialVersionUID = 5927173517834451468L;

	private String gamePromptDes;
	private String gameExamplesDes;

	public String getGamePromptDes() {
		return gamePromptDes;
	}

	public void setGamePromptDes(String gamePromptDes) {
		this.gamePromptDes = gamePromptDes;
	}

	public String getGameExamplesDes() {
		return gameExamplesDes;
	}

	public void setGameExamplesDes(String gameExamplesDes) {
		this.gameExamplesDes = gameExamplesDes;
	}
}
