package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameOrderQueryDTO implements Serializable {

	private static final long serialVersionUID = 7682320349087313895L;

	private Long userId;
	private GameOrderQueryRequest queryParam;

	public GameOrderQueryRequest getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(GameOrderQueryRequest queryParam) {
		this.queryParam = queryParam;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
