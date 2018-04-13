package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: LotteryListQueryResponse 
* @Description: 
* @author Denny 
* @date 2013-9-30 上午11:31:02 
*  
*/
public class LotteryListQueryResponse  implements Serializable {

	private static final long serialVersionUID = 7117018321445937328L;
	
	private List<LotteryListStruc> lotteryListStruc;

	public List<LotteryListStruc> getLotteryListStruc() {
		return lotteryListStruc;
	}

	public void setLotteryListStruc(List<LotteryListStruc> lotteryListStruc) {
		this.lotteryListStruc = lotteryListStruc;
	}

}
