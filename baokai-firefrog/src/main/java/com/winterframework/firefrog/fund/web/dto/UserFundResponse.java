/**   
* @Title: UserFundRequestDTO.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-6-28 下午5:58:38 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName:UserFundResponse
* @Description: 用户资金信息请求返回response
* @author david
* @date 2013-6-28 下午5:58:38 
*  
*/
public class UserFundResponse {
	private Long bal;

	private Long availBal;

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

}
