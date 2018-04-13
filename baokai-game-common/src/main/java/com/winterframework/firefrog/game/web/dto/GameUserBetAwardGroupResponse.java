package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GameUserAwardGroupQueryResponse 
* @Description: 用户投注奖金组查询返回参数DTO 
* @author Denny 
* @date 2013-9-17 下午5:13:56 
*  
*/
public class GameUserBetAwardGroupResponse implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 810393268572580708L;
	
	/** 投注奖金组 */
	private List<UserAwardListByBetStruc> userAwardListByBetStruc;
	
	public List<UserAwardListByBetStruc> getUserAwardListByBetStruc() {
		return userAwardListByBetStruc;
	}
	public void setUserAwardListByBetStruc(
			List<UserAwardListByBetStruc> userAwardListByBetStruc) {
		this.userAwardListByBetStruc = userAwardListByBetStruc;
	}

}
