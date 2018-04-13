package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GamePlanQueryDTO implements Serializable {

	private static final long serialVersionUID = 1407793876716280705L;

	private Long userId;
	private GamePlanQueryRequest queryParam;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public GamePlanQueryRequest getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(GamePlanQueryRequest queryParam) {
		this.queryParam = queryParam;
	}

}
