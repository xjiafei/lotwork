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
public class GameUserAwardGroupLoginResponse implements Serializable{


	private static final long serialVersionUID = -8502588311276260240L;
	/** 投注奖金组 */
	private List<UserAwardListByLoginStruc> userAwardListByBetStruc;
	
	public List<UserAwardListByLoginStruc> getUserAwardListByBetStruc() {
		return userAwardListByBetStruc;
	}
	public void setUserAwardListByBetStruc(
			List<UserAwardListByLoginStruc> userAwardListByBetStruc) {
		this.userAwardListByBetStruc = userAwardListByBetStruc;
	}

}
