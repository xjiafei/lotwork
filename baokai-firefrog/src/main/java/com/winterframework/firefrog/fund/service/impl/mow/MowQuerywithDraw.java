package com.winterframework.firefrog.fund.service.impl.mow;

public class MowQuerywithDraw extends MowReq {

	String mownecum_order_num;
	public String getMownecum_order_num() {
		return mownecum_order_num;
	}
	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}
	@Override
	public String createParam() {
		// TODO Auto-generated method stub
		return   company_id + mownecum_order_num + company_order_num;
		
	}
  }
