/**   
* @Title: MCApplayResponse.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午3:17:11 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.hb;

/** 
* @ClassName: MCApplayResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午3:17:11 
*  
*/
public class HBPayConfirmRequest {
	private String orderId;
	private String respCode;
	private String respInfo;
	private String amount;
	private String signature;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	
	
	
}
