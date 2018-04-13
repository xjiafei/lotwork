package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: HistoryBallsResultDTO 
* @Description: 选号历史遗漏结果DTO 
* @author Denny 
* @date 2013-9-27 下午5:15:07 
*  
*/
public class HistoryBallsResultDTO implements Serializable {

	private static final long serialVersionUID = -4308755370071706145L;

	private Integer isSuccess;
	private String type;
	private String msg;
	private HistoryBallsDTO data;

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HistoryBallsDTO getData() {
		return data;
	}

	public void setData(HistoryBallsDTO data) {
		this.data = data;
	}

}
