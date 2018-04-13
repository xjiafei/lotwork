package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;
 
/**
 * 用户奖金请求参数DTO
 * @ClassName
 * @Description
 * @author ibm
 * 2014年11月18日
 */
public class GameUserAwardRequest implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = -6717586384483903558L;
 
	private Long userId;
	private List<GameUserAwardStruc> userAwardStrucList;

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<GameUserAwardStruc> getUserAwardStrucList() {
		return userAwardStrucList;
	}

	public void setUserAwardStrucList(List<GameUserAwardStruc> userAwardStrucList) {
		this.userAwardStrucList = userAwardStrucList;
	} 
	
  
}
