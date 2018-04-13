/**   
* @Title: MCApplayResponse.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午3:17:11 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.mow;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: WORTHConfirmRequest 
* @Description: 接收WORTH傳回來的通知
* @author Ling,Tseng
* @date 2017-5-23 
*  
*/
public class WORTHConfirmRequest {
	/** 通知類型 **/
	private String notify_type;
	/** 通知ID*/
	private String notify_id;	
	/** 通知時間*/
	private String notify_time;	
	/** 簽名*/
	private String sign;
	/** 簽名方式*/
	private String sign_type;	
	/** 外部交易號(WORTH交易號)*/
	private String trade_no;	
	/** 商戶訂單號*/
	private String order_no;
	/** 支付類型*/
	private String payment_type = "1";	
	/** 商品名稱*/
	private String title;	
	/** 商品描述*/
	private String body;	
	/** 交易金額*/
	private String total_fee;	
	/** 交易狀態*/
	private String trade_status;
	/** 賣家Email*/
	private String seller_email;
	/** 交易創建时间*/
	private String gmt_create;
	/** 交易付款时间*/
	private String gmt_payment;
	
	
	/**
	 * 取得通知類型。
	 * @return notify_type
	 */
	public String getNotify_type() {
		return notify_type;
	}
	/**
	 * 設置通知類型。
	 * @param notify_type
	 */
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	/**
	 * 取得通知ID。
	 * @return notify_id
	 */
	public String getNotify_id() {
		return notify_id;
	}
	/**
	 * 設置通知ID。
	 * @param notify_id
	 */
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	/**
	 * 取得通知時間。
	 * @return notify_time
	 */
	public String getNotify_time() {
		return notify_time;
	}
	/**
	 * 設置通知時間。
	 * @param notify_time
	 */
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
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
	 * 取得外部交易號(WORTH交易號)。
	 * @return trade_no
	 */
	public String getTrade_no() {
		return trade_no;
	}
	/**
	 * 設置外部交易號(WORTH交易號)。
	 * @param trade_no
	 */
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
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
	 * 取得交易狀態。
	 * @return trade_status
	 */
	public String getTrade_status() {
		return trade_status;
	}
	/**
	 * 設置交易狀態。
	 * @param trade_status
	 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
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
	/**
	 * 取得交易創建时间。
	 * @return gmt_create
	 */
	public String getGmt_create() {
		return gmt_create;
	}
	/**
	 * 設置交易創建时间。
	 * @param gmt_create
	 */
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	/**
	 * 取得交易付款时间。
	 * @return gmt_payment
	 */
	public String getGmt_payment() {
		return gmt_payment;
	}
	/**
	 * 設置交易付款时间。
	 * @param gmt_payment
	 */
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	
}
