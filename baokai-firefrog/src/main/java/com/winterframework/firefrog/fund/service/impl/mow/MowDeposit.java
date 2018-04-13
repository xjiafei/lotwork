package com.winterframework.firefrog.fund.service.impl.mow;

public class MowDeposit extends MowReq {
    //银行id
	private Long bank_id;
	//金额
	private Number amount;
	//附言
	private String note;
	/**
	 * 1银行卡2第三方
	 */
	private String memo;
	private String estimated_payment_bank;
	private Integer deposit_mode=1;
	private Integer group_id=0;
	private String web_url;
	private Integer note_model;
	private Integer terminal;
	public Long getBank_id() {
		return bank_id;
	}

	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}

	public Number getAmount() {
		return amount;
	}

	public void setAmount(Number amount) {
		this.amount = amount;
	}

	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEstimated_payment_bank() {
		return estimated_payment_bank;
	}

	public void setEstimated_payment_bank(String estimated_payment_bank) {
		this.estimated_payment_bank = estimated_payment_bank;
	}

	public Integer getDeposit_mode() {
		return deposit_mode;
	}

	public void setDeposit_mode(Integer deposit_mode) {
		this.deposit_mode = deposit_mode;
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	public Integer getNote_model() {
		return note_model;
	}

	public void setNote_model(Integer note_model) {
		this.note_model = note_model;
	}
	
	public Integer getTerminal() {
		return terminal;
	}

	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public  String createParam(){
		return  ""+company_id+ bank_id + amount+ company_order_num+ company_user+ estimated_payment_bank+ deposit_mode + group_id + web_url + memo +note+ note_model;
	};
	

}
