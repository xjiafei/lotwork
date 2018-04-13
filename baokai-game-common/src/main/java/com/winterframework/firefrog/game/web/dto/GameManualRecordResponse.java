package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameManualRecord;


/** 
* @ClassName GameManualRecordResponse 
* @Description 手工录号
* @author  hugh
* @date 2014年9月15日 上午11:51:44 
*  
*/
public class GameManualRecordResponse  implements Serializable{
	
	private static final long serialVersionUID = -930783924244310726L;

	private GameIssue gameIssue; //最远一期没有获取到开奖号码
	
	private GameIssue lastGameIssue; //最近一期没有获取到开奖号码
	
	private List<GameManualRecord> gameManualRecords;

	public GameIssue getGameIssue() {
		return gameIssue;
	}

	public void setGameIssue(GameIssue gameIssue) {
		this.gameIssue = gameIssue;
	}

	public List<GameManualRecord> getGameManualRecords() {
		return gameManualRecords;
	}

	public void setGameManualRecords(List<GameManualRecord> gameManualRecords) {
		this.gameManualRecords = gameManualRecords;
	}

	public GameIssue getLastGameIssue() {
		return lastGameIssue;
	}

	public void setLastGameIssue(GameIssue lastGameIssue) {
		this.lastGameIssue = lastGameIssue;
	}
	
	
}
