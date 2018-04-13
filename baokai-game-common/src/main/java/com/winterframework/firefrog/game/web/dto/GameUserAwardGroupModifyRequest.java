package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GameUserAwardGroupModifyRequest 
* @Description: 修改奖金组请求参数DTO
* @author Denny 
* @date 2013-9-23 下午3:35:45 
*  
*/
public class GameUserAwardGroupModifyRequest implements Serializable {

	private static final long serialVersionUID = 8310186281515609893L;

	private Long userid;

	private List<UserAwardListStruc> userAwardListStruc;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public List<UserAwardListStruc> getUserAwardListStruc() {
		return userAwardListStruc;
	}

	public void setUserAwardListStruc(List<UserAwardListStruc> userAwardListStruc) {
		this.userAwardListStruc = userAwardListStruc;
	}

}
