package com.winterframework.firefrog.fund.service.impl.worth;

import java.io.Serializable;

public class WorthPayOrderCheckQuery implements Serializable{

	/**簽名*/
	private String sign;
	/**簽名類型*/
	private String sign_type;
	/**商戶ID*/
	private String merchant_ID;
	/**輸入編號*/
	private String charset;
	/**返回類型*/
	private String return_type;
	/**訂單號*/
	private String order_no;
		
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
	 * 取得簽名類型。
	 * @return sign_type
	 */
	public String getSign_type() {
		return sign_type;
	}

	/**
	 * 設置簽名類型。
	 * @param sign_type
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
	 * 取得輸入編號。
	 * @return charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * 設置輸入編號。
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 取得返回類型。
	 * @return return_type
	 */
	public String getReturn_type() {
		return return_type;
	}

	/**
	 * 設置返回類型。
	 * @param return_type
	 */
	public void setReturn_type(String return_type) {
		this.return_type = return_type;
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

	public String createParam(String worthpayKey){
		return "charset="+charset+"&merchant_ID="+merchant_ID+"&order_no="+order_no+worthpayKey;
	}
}
