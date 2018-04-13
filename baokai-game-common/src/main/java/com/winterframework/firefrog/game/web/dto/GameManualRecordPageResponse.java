package com.winterframework.firefrog.game.web.dto;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameManualRecord;
import com.winterframework.modules.page.Page;

public class GameManualRecordPageResponse {

	private GameIssue gameIssue;
	private GameIssue lastGameIssue;
	private Page<GameManualRecord> gameManualRecords;
	public GameIssue getGameIssue() {
		return gameIssue;
	}
	public void setGameIssue(GameIssue gameIssue) {
		this.gameIssue = gameIssue;
	}
	public Page<GameManualRecord> getGameManualRecords() {
		return gameManualRecords;
	}
	public void setGameManualRecords(Page<GameManualRecord> gameManualRecords) {
		this.gameManualRecords = gameManualRecords;
	}
	public GameIssue getLastGameIssue() {
		return lastGameIssue;
	}
	public void setLastGameIssue(GameIssue lastGameIssue) {
		this.lastGameIssue = lastGameIssue;
	}

	
}
