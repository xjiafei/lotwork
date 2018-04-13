package com.winterframework.firefrog.fund.service.impl.mow;

import java.math.BigDecimal;

public class MowWithDraw  extends MowReq{

	private Long bank_id;
	private BigDecimal amount;
	private String card_num;
	private String card_name;
	private String issue_bank_address;
	private String issue_bank_name;
	private String memo;
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	
	public String getIssue_bank_address() {
		return issue_bank_address;
	}
	public void setIssue_bank_address(String issue_bank_address) {
		this.issue_bank_address = issue_bank_address;
	}
	public String getIssue_bank_name() {
		return issue_bank_name;
	}
	public void setIssue_bank_name(String issue_bank_name) {
		this.issue_bank_name = issue_bank_name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String createParam() {
		return ""+company_id+ bank_id+ company_order_num+ amount+ card_num+ card_name+ company_user+ issue_bank_name+ issue_bank_address+ memo;
	}
	
	

}
