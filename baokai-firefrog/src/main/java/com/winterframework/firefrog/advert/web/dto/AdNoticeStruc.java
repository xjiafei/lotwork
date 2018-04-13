package com.winterframework.firefrog.advert.web.dto;

import java.util.Date;

public class AdNoticeStruc {
	
	private Long id;
	private Long type;
	private String title;
	private String content;
	private Date gmtEffectBegin;
	private Date gmtEffectEnd;
	private String operator;
	private String showPages;
	private Long status;
	private Long rcAll;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	private String groups;
	private Long createType;
	private String memo;
	private String approver;
	private Date gmtCreatede;
	private Long noticelevel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @return the noticelevel
	 */
	public Long getNoticelevel() {
		return noticelevel;
	}
	/**
	 * @param noticelevel the noticelevel to set
	 */
	public void setNoticelevel(Long noticelevel) {
		this.noticelevel = noticelevel;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getGmtEffectBegin() {
		return gmtEffectBegin;
	}
	public void setGmtEffectBegin(Date gmtEffectBegin) {
		this.gmtEffectBegin = gmtEffectBegin;
	}
	public Date getGmtEffectEnd() {
		return gmtEffectEnd;
	}
	public void setGmtEffectEnd(Date gmtEffectEnd) {
		this.gmtEffectEnd = gmtEffectEnd;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getShowPages() {
		return showPages;
	}
	public void setShowPages(String showPages) {
		this.showPages = showPages;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getRcAll() {
		return rcAll;
	}
	public void setRcAll(Long rcAll) {
		this.rcAll = rcAll;
	}
	public Long getRcTopAgent() {
		return rcTopAgent;
	}
	public void setRcTopAgent(Long rcTopAgent) {
		this.rcTopAgent = rcTopAgent;
	}
	public Long getRcOtAgent() {
		return rcOtAgent;
	}
	public void setRcOtAgent(Long rcOtAgent) {
		this.rcOtAgent = rcOtAgent;
	}
	public Long getRcVip() {
		return rcVip;
	}
	public void setRcVip(Long rcVip) {
		this.rcVip = rcVip;
	}
	public Long getRcNonVip() {
		return rcNonVip;
	}
	public void setRcNonVip(Long rcNonVip) {
		this.rcNonVip = rcNonVip;
	}
	public Long getRcCustomer() {
		return rcCustomer;
	}
	public void setRcCustomer(Long rcCustomer) {
		this.rcCustomer = rcCustomer;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public Long getCreateType() {
		return createType;
	}
	public void setCreateType(Long createType) {
		this.createType = createType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public Date getGmtCreatede() {
		return gmtCreatede;
	}
	public void setGmtCreatede(Date gmtCreatede) {
		this.gmtCreatede = gmtCreatede;
	}
	
	

}
