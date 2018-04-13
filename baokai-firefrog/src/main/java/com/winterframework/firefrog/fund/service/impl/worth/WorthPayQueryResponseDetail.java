package com.winterframework.firefrog.fund.service.impl.worth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trade")
public class WorthPayQueryResponseDetail implements Serializable{
	
	/**外部交易號*/
	@XmlElement(name = "trade_no")
	private String trade_no;
	/**訂單號*/
	@XmlElement(name = "out_trade_no")
	private String out_trade_no;
	/**交易類型 payment:支付*/
	@XmlElement(name = "trade_type")
	private String trade_type;
	/**交易金額*/
	@XmlElement(name = "amount")
	private String amount;
	/**手續費*/
	@XmlElement(name = "fee_amount")
	private String fee_amount;
	/**商品名稱*/
	@XmlElement(name = "subject")
	private String subject;
	/**交易日期*/
	@XmlElement(name = "trade_date")
	private String trade_date;
	/**訂單創建時間*/
	@XmlElement(name = "created_time")
	private String created_time;
	/**訂單狀態 wait:等待支付 completed:支付成功 failed:支付失敗*/
	@XmlElement(name = "status")
	private String status;
	/**
	 * 取得外部交易號。
	 * @return trade_no
	 */
	public String getTrade_no() {
		return trade_no;
	}
	/**
	 * 取得訂單號。
	 * @return out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}
	/**
	 * 取得交易類型。
	 * @return trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}
	/**
	 * 取得交易金額。
	 * @return amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 取得手續費。
	 * @return fee_amount
	 */
	public String getFee_amount() {
		return fee_amount;
	}
	/**
	 * 取得商品名稱。
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 取得交易日期。
	 * @return trade_date
	 */
	public String getTrade_date() {
		return trade_date;
	}
	/**
	 * 取得訂單創建時間。
	 * @return created_time
	 */
	public String getCreated_time() {
		return created_time;
	}
	/**
	 * 取得訂單狀態。
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
}
