package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**PrizeListStruc
 * 中奖列表结构
 * @author david
 *
 */
public class PrizeListStruc implements Serializable{

	private static final long serialVersionUID = -6260937461400774201L;

	private String username;
	
	private String desc;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
