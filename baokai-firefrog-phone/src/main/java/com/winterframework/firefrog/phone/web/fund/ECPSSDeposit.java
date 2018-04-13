package com.winterframework.firefrog.phone.web.fund;

public class ECPSSDeposit {
	// 商戶號
	private String merNo;
	// 4.0訂單號
	private String billNo;
	// 充值金額
	private String amount;
	// 支付完成支付結果顯示頁
	private String returnURL;
	// 接收結果的回callback URL
	private String adviceURL;
	// 簽名
	private String signInfo;
	// 交易時間
	private String orderTime;
	// 銀行編碼
	private String defaultBankNumber = "";
	// 支付方式
	private String payType;
	// 備註
	private String remark = "";
	// 產品信息
	private String products = "";
	
	private String ecpssDomain;

	public String getEcpssDomain() {
		return ecpssDomain;
	}

	public void setEcpssDomain(String ecpssDomain) {
		this.ecpssDomain = ecpssDomain;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getAdviceURL() {
		return adviceURL;
	}

	public void setAdviceURL(String adviceURL) {
		this.adviceURL = adviceURL;
	}

	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getDefaultBankNumber() {
		return defaultBankNumber;
	}

	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String createSign() {
		
		return "MerNo=" + merNo + "&BillNo=" + billNo
				+ "&Amount=" + amount + "&OrderTime=" + orderTime + "&ReturnURL="
				+ returnURL + "&AdviceURL=" + adviceURL + "&amberkeyforecpss";
	}

}
