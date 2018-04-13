package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: HistoryBallsResultResponse 
* @Description: 选号历史遗漏结果Response
* @author Denny 
* @date 2013-9-29 下午8:16:36 
*  
*/
public class HistoryBallsResultResponse implements Serializable{

	private static final long serialVersionUID = 8141572342899402779L;

	private HistoryBallsResultDTO result;

	public HistoryBallsResultDTO getResult() {
		return result;
	}

	public void setResult(HistoryBallsResultDTO result) {
		this.result = result;
	}
}
