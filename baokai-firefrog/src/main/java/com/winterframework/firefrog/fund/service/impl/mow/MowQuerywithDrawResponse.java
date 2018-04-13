package com.winterframework.firefrog.fund.service.impl.mow;

public class MowQuerywithDrawResponse extends MowResp {
	
	public static enum MOW_ORDER_STATUS {
		FAIL(0), SUCCESS(1), PART_SUCCESS(2), UNDO(3), DOING(4), INVALID(5), NET_ERROR(9);
		private int value;

		MOW_ORDER_STATUS(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
		public static MOW_ORDER_STATUS mapping(int status){
			MOW_ORDER_STATUS mowOrderStatus = null;
			for (MOW_ORDER_STATUS type : MOW_ORDER_STATUS.values()) {
                if (type.value() == status) {
                    mowOrderStatus = type;
                    break;
                }
            }
			return mowOrderStatus;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String company_order_num;
	String mownecum_order_num;
	String amount;
	String detail;
	String exact_transaction_charge;
	
	public String getCompany_order_num() {
		return company_order_num;
	}
	public void setCompany_order_num(String company_order_num) {
		this.company_order_num = company_order_num;
	}
	public String getMownecum_order_num() {
		return mownecum_order_num;
	}
	public void setMownecum_order_num(String mownecum_order_num) {
		this.mownecum_order_num = mownecum_order_num;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getExact_transaction_charge() {
		return exact_transaction_charge;
	}
	public void setExact_transaction_charge(String exact_transaction_charge) {
		this.exact_transaction_charge = exact_transaction_charge;
	}
}
