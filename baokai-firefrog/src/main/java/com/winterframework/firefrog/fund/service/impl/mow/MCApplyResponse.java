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
public class MCApplyResponse extends MowResp{
	private Long mode;
	private String bank_card_num;
	private String bank_acc_name;
	private Long bank_id;
	//20140911205215
	private String datetime;
	private String issuing_bank_address;
	private String email;
	private String company_order_num;
	private String remarks;
	private String mownecum_order_num;
	private String break_url;
	private Long collection_bank_id;
	
	public String getDatetime() {
		return datetime;
	}
	public Date getMowExpTime(){
		try{
		return new SimpleDateFormat("yyyyMMddHHmmss").parse(datetime);
		}catch(Exception e){
			return new Date();
		}
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Long getCollection_bank_id() {
		return collection_bank_id;
	}
	public void setCollection_bank_id(Long collection_bank_id) {
		this.collection_bank_id = collection_bank_id;
	}
	public String getBreak_url() {
		return break_url;
	}
	public void setBreak_url(String break_url) {
		this.break_url = break_url;
	}
	public Long getMode() {
		return mode;
	}
	public void setMode(Long mode) {
		this.mode = mode;
	}
	public String getBank_card_num() {
		return bank_card_num;
	}
	public void setBank_card_num(String bank_card_num) {
		this.bank_card_num = bank_card_num;
	}
	
	
	public String getBank_acc_name() {
		return bank_acc_name;
	}
	public void setBank_acc_name(String bank_acc_name) {
		this.bank_acc_name = bank_acc_name;
	}
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public String getIssuing_bank_address() {
		return issuing_bank_address;
	}
	public void setIssuing_bank_address(String issuing_bank_address) {
		this.issuing_bank_address = issuing_bank_address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany_order_num() {
		return company_order_num;
	}
	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMownecum_order_num() {
		return mownecum_order_num;
	}
	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}
	

}
