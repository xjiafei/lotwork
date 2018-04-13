package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GameUserAwardGroupQueryResponse 
* @Description: 用户奖金组查询返回参数DTO 
* @author Denny 
* @date 2013-9-17 下午5:13:56 
*  
*/
public class GameUserAwardGroupQueryResponse implements Serializable{

	private static final long serialVersionUID = 2935974190078328680L;

	/** 用户奖金组 */
	private List<UserAwardListStruc> userAwardListStruc;

	public List<UserAwardListStruc> getUserAwardListStruc() {
		return userAwardListStruc;
	}

	public void setUserAwardListStruc(List<UserAwardListStruc> userAwardListStruc) {
		this.userAwardListStruc = userAwardListStruc;
	}
}
