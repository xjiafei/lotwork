package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: LotteryGameUserAwardGroupQueryResponse 
* @Description: 彩种用户奖金组结构
* @author david 
* @date 2014-5-31 下午6:28:32 
*  
*/
public class LotteryGameUserAwardGroupQueryResponse implements Serializable{

	private static final long serialVersionUID = 2935974190078328680L;

	/** 用户奖金组列表信息 */
	private List<LotteryGameUserAwardListStruc> userAwardListStruc;

	public List<LotteryGameUserAwardListStruc> getUserAwardListStruc() {
		return userAwardListStruc;
	}

	public void setUserAwardListStruc(List<LotteryGameUserAwardListStruc> userAwardListStruc) {
		this.userAwardListStruc = userAwardListStruc;
	}

}
