package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

public class FundRechargeAppealRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String account;

	private String appealSn;

	private String userlvl;

	private Date appealTimeFrom;
	
	private Date appealTimeTo;

	private String status;

	private String title;

	private Long refundAmtFrom;
	
	private Long refundAmtTo;

	private Long isvip;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public String getUserlvl() {
		return userlvl;
	}

	public void setUserlvl(String userlvl) {
		this.userlvl = userlvl;
	}

	public Date getAppealTimeFrom() {
		return appealTimeFrom;
	}

	public void setAppealTimeFrom(Date appealTimeFrom) {
		this.appealTimeFrom = appealTimeFrom;
	}

	public Date getAppealTimeTo() {
		return appealTimeTo;
	}

	public void setAppealTimeTo(Date appealTimeTo) {
		this.appealTimeTo = appealTimeTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getRefundAmtFrom() {
		return refundAmtFrom;
	}

	public void setRefundAmtFrom(Long refundAmtFrom) {
		this.refundAmtFrom = refundAmtFrom;
	}

	public Long getRefundAmtTo() {
		return refundAmtTo;
	}

	public void setRefundAmtTo(Long refundAmtTo) {
		this.refundAmtTo = refundAmtTo;
	}

	public Long getIsvip() {
		return isvip;
	}

	public void setIsvip(Long isvip) {
		this.isvip = isvip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
