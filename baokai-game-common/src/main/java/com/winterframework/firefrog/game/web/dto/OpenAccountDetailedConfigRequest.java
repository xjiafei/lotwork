package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class OpenAccountDetailedConfigRequest implements Serializable {

	private static final long serialVersionUID = 5972340622701510002L;
	/** 用户中心奖金组列表基本结构 */
	private List<UserAwardListStruc> userAwardListStruc;
	/** 授权用户ID */
	private Long userid;
	
	private Long userLvl;

	public List<UserAwardListStruc> getUserAwardListStruc() {
		return userAwardListStruc;
	}

	public void setUserAwardListStruc(List<UserAwardListStruc> userAwardListStruc) {
		this.userAwardListStruc = userAwardListStruc;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}

}
