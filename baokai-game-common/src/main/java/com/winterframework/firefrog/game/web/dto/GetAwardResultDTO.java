package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: HistoryBallsResultDTO 
* @Description: 选号历史遗漏结果DTO 
* @author Denny 
* @date 2013-9-27 下午5:15:07 
*  
*/
public class GetAwardResultDTO implements Serializable {

	private static final long serialVersionUID = 3095522861012029864L;
	private Integer isSuccess;
	private String type;
	private String msg;
	private Boolean moreBouns;
	private GetAwardDTO data;

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

	public GetAwardDTO getData() {
		return data;
	}

	public void setData(GetAwardDTO data) {
		this.data = data;
	}

	public Boolean getMoreBouns() {
		return moreBouns;
	}

	public void setMoreBouns(Boolean moreBouns) {
		this.moreBouns = moreBouns;
	}
}
