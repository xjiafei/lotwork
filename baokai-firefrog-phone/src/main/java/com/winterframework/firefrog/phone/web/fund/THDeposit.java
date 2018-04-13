package com.winterframework.firefrog.phone.web.fund;


public class THDeposit {
	// 参数字符集编码
	private String inputCharset = "UTF-8";
	// 服务器异步通知地址
	private String notifyUrl;
	// 支付方式
	private String payType;
	// 银行编码
	private String bankCode;
	// 商户号
	private String merchantCode;
	// 商户订单号
	private String orderNo;
	// 商户订单总金额
	private String orderAmount;
	// 商户订单时间
	private String orderTime;
	// 来路域名
	private String reqReferer;
	// 消费者IP
	private String customerIp;
	// 签名
	private String sign;

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getReqReferer() {
		return reqReferer;
	}

	public void setReqReferer(String reqReferer) {
		this.reqReferer = reqReferer;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String createSign(){
		return "bank_code=" + bankCode + "&customer_ip=" + customerIp + "&input_charset=" + inputCharset + "&merchant_code=" 
				+ merchantCode + "&notify_url=" + notifyUrl + "&order_amount=" + orderAmount + "&order_no=" + orderNo + 
				"&order_time=" + orderTime + "&pay_type=" + payType + "&req_referer=" + reqReferer + "&key=11f28a4a128a11e6bf7b00259079b2c3";
	}

}
