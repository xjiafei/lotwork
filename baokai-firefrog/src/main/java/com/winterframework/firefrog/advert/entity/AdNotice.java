package com.winterframework.firefrog.advert.entity;

import java.util.Date;

import com.winterframework.firefrog.acl.entity.AclUser;

public class AdNotice {

	private Long id;

	private Type type;

	private String title;

	private String content;

	private Date gmtEffectBegin;

	private Date gmtEffectEnd;

	private AclUser operator;

	private String showPages;

	private Status status;

	private Long rcAll;

	private Long rcTopAgent;

	private Long rcOtAgent;

	private Long rcVip;

	private Long rcNonVip;

	private Long rcCustomer;

	private String memo;

	private AclUser approver;

	private Date gmtCreatede;
	
	private Long noticelevel;
	public enum Type {
		NORMAL("normal", 1), URGENCY("urgency", 2);
		private String name;
		private int index;

		private Type(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Type getEnum(int index) {
			for (Type c : Type.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum Status {
		NEW("new", 1), AUDIT("audit", 2), PASS("pass", 3), REFUSE("refuse", 4), DELETE("delete", 5);
		private String name;
		private int index;

		private Status(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Status getEnum(int index) {
			for (Status c : Status.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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

	public AclUser getOperator() {
		return operator;
	}

	public void setOperator(AclUser operator) {
		this.operator = operator;
	}

	public String getShowPages() {
		return showPages;
	}

	public void setShowPages(String showPages) {
		this.showPages = showPages;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public AclUser getApprover() {
		return approver;
	}

	public void setApprover(AclUser approver) {
		this.approver = approver;
	}

	public Date getGmtCreatede() {
		return gmtCreatede;
	}

	public void setGmtCreatede(Date gmtCreatede) {
		this.gmtCreatede = gmtCreatede;
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


	
}
