package com.winterframework.firefrog.schedule;

import com.winterframework.firefrog.game.dao.vo.GameIssue;

public interface InitGetNumberTask{

	/**
	 * 執行取號
	 * @throws Exception
	 */
	public void execute() throws Exception;
	
	/**
	 * 根據獎期取號
	 * @param issue
	 * @throws Exception
	 */
	public void getDrawNumberByIssue(GameIssue issue) throws Exception;
	
}
