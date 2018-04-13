package com.winterframework.firefrog.fund.entity;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.entity.User;

public class FundOrder implements Serializable {

	private static final long serialVersionUID = 5363982818836250779L;
	private Long id;
	//申请人
	private BaseUser applyUser;
	private Date applyTime;
	private String sn;
	private EnumItem item;
	private Long depositMode;
	

	public Long getDepositMode() {
		return depositMode;
	}

	public void setDepositMode(Long depositMode) {
		this.depositMode = depositMode;
	}

	public FundOrder(EnumItem item) {
		//默认系统用户
		User user = new User();
		user.setId(-1L);
		this.item=item;
	}

	public BaseUser getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(BaseUser applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumItem getItem() {
		return item;
	}

	public void setItem(EnumItem item) {
		this.item = item;
	}

}
