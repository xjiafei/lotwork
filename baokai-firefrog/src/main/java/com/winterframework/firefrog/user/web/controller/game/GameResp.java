package com.winterframework.firefrog.user.web.controller.game;

import java.util.List;

public class GameResp {
	List<LoginGameGroup> userAwardListByBetStruc;

	public List<LoginGameGroup> getUserAwardListByBetStruc() {
		return userAwardListByBetStruc;
	}

	public void setUserAwardListByBetStruc(List<LoginGameGroup> userAwardListByBetStruc) {
		this.userAwardListByBetStruc = userAwardListByBetStruc;
	}

}
