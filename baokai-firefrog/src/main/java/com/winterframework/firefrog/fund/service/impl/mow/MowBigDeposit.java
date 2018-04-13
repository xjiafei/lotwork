package com.winterframework.firefrog.fund.service.impl.mow;

public class MowBigDeposit extends MowReq {
  	//金额
	private Number amount;
	//附言
	private String note;
	
	private String memo;
	private String estimated_payment_bank;
	
	private Integer deposit_mode=1;
	private Integer group_id=0;
	private Integer note_model;
	private String pay_acc_num;
	private String pay_acc_name;

	

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



	public Integer getNote_model() {
		return note_model;
	}

	public void setNote_model(Integer note_model) {
		this.note_model = note_model;
	}



	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPay_acc_num() {
		return pay_acc_num;
	}

	public void setPay_acc_num(String pay_acc_num) {
		this.pay_acc_num = pay_acc_num;
	}

	public String getPay_acc_name() {
		return pay_acc_name;
	}

	public void setPay_acc_name(String pay_acc_name) {
		this.pay_acc_name = pay_acc_name;
	}

	public  String createParam(){
		return  ""+company_id + amount+ company_order_num+ company_user+ estimated_payment_bank+ group_id+ memo +note+ note_model;
	};
	

}
