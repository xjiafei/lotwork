/**   
* @Title: FundAdjustOrder.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 上午11:01:17 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: FundAdjustOrder 
* @Description: 资金调整Entity
* @author Alan 
* @date 2013-7-18 上午11:01:17 
*  
*/
public class FundAdjustOrder extends FundOrder implements Serializable {

	public FundAdjustOrder(EnumItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -3960166138076545471L;

	public enum Increase {
		ADDITION("addition", 1), REDUCTION("reduction", 2);
		private Increase(String key, long value) {
			this.key = key;
			this.value = value;
		}

		private String key;
		private Long value;

		public long getValue() {
			return value;
		}

		public static Increase getIncreaseByValue(long value) {
			for (Increase i : Increase.values()) {
				if (i.getValue() == value) {
					return i;
				}
			}
			return null;
		}
	}

	public enum Type {
		ACTIVITIESADDMONEY("activitiesAddMoney", 1), ARTIFICIALDEDUCTIONS("artificialDeductions", 2), CLAIMSCANADIANDOLLARS(
				"claimsCanadianDollars", 3);
		private Type(String key, long value) {
			this.key = key;
			this.value = value;
		}

		private String key;
		private Long value;

		public long getValue() {
			return value;
		}

		public static Type getTypeByValue(long value) {
			for (Type t : Type.values()) {
				if (t.getValue() == value) {
					return t;
				}
			}
			return null;
		}

	}

	public enum ApprStatus {
		APPR("appr", 1), SUCCESS("success", 2), FAILED("failed", 3);
		private ApprStatus(String key, long value) {
			this.key = key;
			this.value = value;
		}

		private String key;
		private long value;

		public long getValue() {
			return value;
		}

		public static ApprStatus getApprStatusByValue(long value) {
			for (ApprStatus a : ApprStatus.values()) {
				if (a.getValue() == value) {
					return a;
				}
			}
			return null;
		}
	}

	//用户名(转账给该用户)
	private User user;
	//申请备注
	private String applyMemo;
	//转账金额
	private Long amt;
	private Long frozenAmt;
	//方式(1为增加、2为减少)
	private Increase increase;
	//申请类型(1、活动加币   2、人工扣款  3、理赔加币)
	private Type type;
	//审核人账户
	private User apprUser;
	//审核时间
	private Date apprTime;
	//审核备注
	private String apprMemo;
	//审核状态
	private ApprStatus apprStatus;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplyMemo() {
		return applyMemo;
	}

	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

	public Long getFrozenAmt() {
		return frozenAmt;
	}

	public void setFrozenAmt(Long frozenAmt) {
		this.frozenAmt = frozenAmt;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Increase getIncrease() {
		return increase;
	}

	public void setIncrease(Increase increase) {
		this.increase = increase;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getApprUser() {
		return apprUser;
	}

	public void setApprUser(User apprUser) {
		this.apprUser = apprUser;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getApprMemo() {
		return apprMemo;
	}

	public void setApprMemo(String apprMemo) {
		this.apprMemo = apprMemo;
	}

	public ApprStatus getApprStatus() {
		return apprStatus;
	}

	public void setApprStatus(ApprStatus apprStatus) {
		this.apprStatus = apprStatus;
	}

}
