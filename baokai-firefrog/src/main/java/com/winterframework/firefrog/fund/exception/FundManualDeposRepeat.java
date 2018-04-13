package com.winterframework.firefrog.fund.exception;

public class FundManualDeposRepeat extends RuntimeException {
	private static final long serialVersionUID = -439873258672943470L;

	public final static Long CODE = 2017L;

	public final static String MSG = "订单重复处理";

	public FundManualDeposRepeat() {
		super(MSG);
	}

	public FundManualDeposRepeat(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
