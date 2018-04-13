package com.winterframework.firefrog.fund.service.impl.mow;


public class MownecumWithdrawResponseData extends MowResp {


	private String mownecum_order_num;
	private String company_order_num;
	private Long transaction_charge;
	public long getErrorStatus(){
		return 100001;
	}

	public MownecumWithdrawResponseData() {

	}

	public Long getTransaction_charge() {
		return transaction_charge;
	}

	public void setTransaction_charge(Long transaction_charge) {
		this.transaction_charge = transaction_charge;
	}

	public String getMownecum_order_num() {
		return mownecum_order_num;
	}

	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}

	public String getCompany_order_num() {
		return company_order_num;
	}

	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}

	

}
