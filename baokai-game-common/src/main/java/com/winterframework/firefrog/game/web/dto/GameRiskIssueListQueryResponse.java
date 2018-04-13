package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统 审核奖期查询返回
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskIssueListQueryResponse implements Serializable {

	private static final long serialVersionUID = 109920214872655867L;

	private List<GameRiskIssueStruc> gameRiskIssueResponse;

	public List<GameRiskIssueStruc> getGameRiskIssueResponse() {
		return gameRiskIssueResponse;
	}

	public void setGameRiskIssueResponse(List<GameRiskIssueStruc> gameRiskIssueResponse) {
		this.gameRiskIssueResponse = gameRiskIssueResponse;
	}



}
