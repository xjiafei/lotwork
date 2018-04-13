package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FundWithdrawTips extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4675799535000125387L;
	
	private String tipsModel;
	
	private String tipsGroupa;

	private String tipsGroupb;

	private String tipsContext;

	private Date createTime;

	private String createUser;

	private Date modifyTime;

	private String modifyUser;
	
	private String tipsMemo;

	public String getTipsGroupa() {
		return tipsGroupa;
	}

	public void setTipsGroupa(String tipsGroupa) {
		this.tipsGroupa = tipsGroupa;
	}

	public String getTipsGroupb() {
		return tipsGroupb;
	}

	public void setTipsGroupb(String tipsGroupb) {
		this.tipsGroupb = tipsGroupb;
	}

	public String getTipsContext() {
		return tipsContext;
	}

	public void setTipsContext(String tipsContext) {
		this.tipsContext = tipsContext;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getTipsMemo() {
		return tipsMemo;
	}

	public void setTipsMemo(String tipsMemo) {
		this.tipsMemo = tipsMemo;
	}

	@Override
	public String toString() {
		return "FundWithdrawTips [tipsGroupa=" + tipsGroupa + ", tipsGroupb="
				+ tipsGroupb + ", tipsContext=" + tipsContext + ", createTime="
				+ createTime + ", createUser=" + createUser + ", modifyTime="
				+ modifyTime + ", modifyUser=" + modifyUser + ", tipsMemo="
				+ tipsMemo + "]";
	}

	public String getTipsModel() {
		return tipsModel;
	}

	public void setTipsModel(String tipsModel) {
		this.tipsModel = tipsModel;
	}

}
