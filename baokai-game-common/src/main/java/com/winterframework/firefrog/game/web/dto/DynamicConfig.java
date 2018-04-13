package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: DynamicConfig 
* @Description: 输出后台相关配置和最新开奖等数据 
* @author david 
* @date 2013-10-11 下午1:49:45 
*  
*/
public class DynamicConfig {
	private Integer isSuccess;
	private String msg;
	private GameConfigAndAwardData data;

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public GameConfigAndAwardData getData() {
		return data;
	}

	public void setData(GameConfigAndAwardData data) {
		this.data = data;
	}
}
