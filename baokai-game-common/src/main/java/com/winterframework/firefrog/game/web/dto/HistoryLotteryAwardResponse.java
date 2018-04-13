package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameIssue;

/**
 * 歷史開獎資料回傳物件。
 * @author Pogi.Lin
 */
public class HistoryLotteryAwardResponse implements Serializable {

	private static final long serialVersionUID = -121115077869587006L;
	/**狀態；1:成功、2:失敗*/
	private Long status;
	/**回傳訊息*/
	private String message;
	/**獎期資料*/
	private List<GameIssue> gameIssues;
	
	/**
	 * 取得狀態。
	 * @return 1:成功、2:失敗
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * 設定狀態。
	 * @param status 1:成功、2:失敗
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * 取得回傳訊息。
	 * @return 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 設定回傳訊息。
	 * @param message 
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 取得獎期資料。
	 * @return 
	 */
	public List<GameIssue> getGameIssues() {
		return gameIssues;
	}

	/**
	 * 設定獎期資料。
	 * @param gameIssues 
	 */
	public void setGameIssues(List<GameIssue> gameIssues) {
		this.gameIssues = gameIssues;
	}
}