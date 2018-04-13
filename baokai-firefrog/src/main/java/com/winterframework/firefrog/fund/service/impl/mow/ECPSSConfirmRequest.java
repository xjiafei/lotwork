/**   
* @Title: MCApplayResponse.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午3:17:11 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.mow;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: MCApplayResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午3:17:11 
*  
*/
public class ECPSSConfirmRequest {
	private String MerNo;
	private String BillNo;
	private String OrderNo;
	private String Amount;
	private String Succeed;
	private String Result;
	private String SignInfo;
	public String getMerNo() {
		return MerNo;
	}
	public void setMerNo(String merNo) {
		MerNo = merNo;
	}
	public String getBillNo() {
		return BillNo;
	}
	public void setBillNo(String billNo) {
		BillNo = billNo;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getSucceed() {
		return Succeed;
	}
	public void setSucceed(String succeed) {
		Succeed = succeed;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getSignInfo() {
		return SignInfo;
	}
	public void setSignInfo(String signInfo) {
		SignInfo = signInfo;
	}
	
}
