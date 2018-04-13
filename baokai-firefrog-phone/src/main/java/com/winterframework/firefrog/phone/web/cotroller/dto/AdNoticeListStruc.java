package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class AdNoticeListStruc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7354428105795504280L;

	private List<AdNoticeStruc> noticeList;

	private Long sumNew;

	private Long sumAudit;

	private Long sumRefuse;

	public List<AdNoticeStruc> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<AdNoticeStruc> noticeList) {
		this.noticeList = noticeList;
	}

	public Long getSumNew() {
		return sumNew;
	}

	public void setSumNew(Long sumNew) {
		this.sumNew = sumNew;
	}

	public Long getSumAudit() {
		return sumAudit;
	}

	public void setSumAudit(Long sumAudit) {
		this.sumAudit = sumAudit;
	}

	public Long getSumRefuse() {
		return sumRefuse;
	}

	public void setSumRefuse(Long sumRefuse) {
		this.sumRefuse = sumRefuse;
	}
}
