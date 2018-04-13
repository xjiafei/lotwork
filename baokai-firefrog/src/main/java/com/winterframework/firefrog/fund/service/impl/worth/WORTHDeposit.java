package com.winterframework.firefrog.fund.service.impl.worth;

public class WORTHDeposit {
	/**網域 **/
	private String transDomain;
	/**worth地址**/
	//private String worthUrl;
	/** 接口名稱*/
	private String service = "online_pay";	
	/** 商戶ID*/
	private String merchant_ID;	
	/** 通知URL*/
	private String notify_url;	
	/** 返回URL*/
	private String return_url;
	/** 簽名*/
	private String sign;	
	/** 簽名方式*/
	private String sign_type;	
	/** 編碼字符*/
	private String charset = "UTF-8";	
	/** 商品名稱*/
	private String title;	
	/** 商品描述*/
	private String body;	
	/** 商戶訂單號*/
	private String order_no;	
	/** 交易金額*/
	private String total_fee;	
	/** 支付類型*/
	private String payment_type = "1";	
	/** 支付方式*/
	private String paymethod;
	/** 网银代码*/
	private String defaultbank;
	/** 接入方式*/
	private String isApp;
	/** 賣家Email*/
	private String seller_email;
	/**
	 * 取得網域。
	 * @return transDomain
	 */
	public String getTransDomain() {
		return transDomain;
	}
	/**
	 * 設置網域。
	 * @param transDomain
	 */
	public void setTransDomain(String transDomain) {
		this.transDomain = transDomain;
	}
	/**
	 * 取得接口名稱。
	 * @return service
	 */
	public String getService() {
		return service;
	}
	/**
	 * 設置接口名稱。
	 * @param service
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * 取得商戶ID。
	 * @return merchant_ID
	 */
	public String getMerchant_ID() {
		return merchant_ID;
	}
	/**
	 * 設置商戶ID。
	 * @param merchant_ID
	 */
	public void setMerchant_ID(String merchant_ID) {
		this.merchant_ID = merchant_ID;
	}
	/**
	 * 取得通知URL。
	 * @return notify_url
	 */
	public String getNotify_url() {
		return notify_url;
	}
	/**
	 * 設置通知URL。
	 * @param notify_url
	 */
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	/**
	 * 取得返回URL。
	 * @return return_url
	 */
	public String getReturn_url() {
		return return_url;
	}
	/**
	 * 設置返回URL。
	 * @param return_url
	 */
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	/**
	 * 取得簽名。
	 * @return sign
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * 設置簽名。
	 * @param sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * 取得簽名方式。
	 * @return sign_type
	 */
	public String getSign_type() {
		return sign_type;
	}
	/**
	 * 設置簽名方式。
	 * @param sign_type
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	/**
	 * 取得編碼字符。
	 * @return charset
	 */
	public String getCharset() {
		return charset;
	}
	/**
	 * 設置編碼字符。
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	/**
	 * 取得商品名稱。
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 設置商品名稱。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 取得商品描述。
	 * @return body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * 設置商品描述。
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * 取得商戶訂單號。
	 * @return order_no
	 */
	public String getOrder_no() {
		return order_no;
	}
	/**
	 * 設置商戶訂單號。
	 * @param order_no
	 */
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	/**
	 * 取得交易金額。
	 * @return total_fee
	 */
	public String getTotal_fee() {
		return total_fee;
	}
	/**
	 * 設置交易金額。
	 * @param total_fee
	 */
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	/**
	 * 取得支付類型。
	 * @return payment_type
	 */
	public String getPayment_type() {
		return payment_type;
	}
	/**
	 * 設置支付類型。
	 * @param payment_type
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	/**
	 * 取得支付方式。
	 * @return paymethod
	 */
	public String getPaymethod() {
		return paymethod;
	}
	/**
	 * 設置支付方式。
	 * @param paymethod
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
		
	/**
	 * 取得网银代码。
	 * @return defaultbank
	 */
	public String getDefaultbank() {
		return defaultbank;
	}
	/**
	 * 設置网银代码。
	 * @param defaultbank
	 */
	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}
	/**
	 * 取得接入方式。
	 * @return isApp
	 */
	public String getIsApp() {
		return isApp;
	}
	/**
	 * 設置接入方式。
	 * @param isApp
	 */
	public void setIsApp(String isApp) {
		this.isApp = isApp;
	}
	/**
	 * 取得賣家Email。
	 * @return seller_email
	 */
	public String getSeller_email() {
		return seller_email;
	}
	/**
	 * 設置賣家Email。
	 * @param seller_email
	 */
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	

}
