/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.fund.enums.FundModel;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundManualOrder extends FundOrder {

	public FundManualOrder(EnumItem item) {
		super(item);
   }

	public FundManualOrder(Long  type) {
		super(Type.RGDK.creatStatus(type).getItem());
   }
	public static void main(String[] args){
		FundManualOrder o=new FundManualOrder(3L);
		 o=new FundManualOrder(5L);
		 o=new FundManualOrder(6L);
		 o=new FundManualOrder(7L);
	}
	private static final long serialVersionUID = 2338650543178636170L;

	//1 人工打款 2 人工提现 3 活动加币 5 投诉理赔 6 平台奖励 8 人工扣币 9:人工充值
	public enum Type implements FundStatus {
		//人工打款
		RGDK(1L, FundModel.OT.RGDK.ITEMS.PROCESS), //---------mow
		//人工体现
		RGTX(2L, FundModel.FD.MWXX.ITEMS.PROCESSING), //---------mow
		HDLJ(3L,FundModel.PM.PGXX.ITEMS.PGXX_3), ////++++
		TSLP(5L, FundModel.OT.CEXX.ITEMS.SUCCESS),  ////++++
		PTJL(6L, FundModel.PM.IPXX.ITEMS.SUCCESS), ////++++
		RGJB(7L, FundModel.OT.AAXX.ITEMS.SUCCESS), ////++++
		RGCZ(9L, FundModel.FD.MDAX.ITEMS.MDAX5), //----
		RGKB(8L, FundModel.OT.DAXX.ITEMS.SUCCESS), //---------mow
		PGPT(10L, FundModel.PM.PGPT.ITEMS.SUCCESS), //---------PT 活動
		DDAX(11L, FundModel.GM.DDAX.ITEMS.SUCCESS), //---------彩票分紅
		PGAP(12L, FundModel.PM.PGAP.ITEMS.SUCCESS), //---------PT 活動 auto
		DDPT(13L, FundModel.GM.DDPT.ITEMS.SUCCESS); //---------PT 傭金
    	private Type(Long status,EnumItem item) {
			
			this.statis = status;
			this.item = item;
		}
		private EnumItem item;
		private Long statis;

		public void setStatis(Long status) {
			this.statis = status;
		}

		public EnumItem getItem() {
			return this.item;
		}

		@Override
		public Long getStatis() {
			return this.statis;
		}

		@Override
		public Type creatStatus(Long value) {
			for (Type aLight : Type.values()) {
				if (aLight.getStatis().equals(value)) {
					return aLight;
				}
			}
			return null;
		}
	}

	public enum Status implements FundStatus {
		//0:未处理,1:处理中,2:已拒绝,3:扣币成功,4:加币成功,5:打款完全成功,6:打款部分成功,7:打款失败, 8:充值成功，9：订单关闭
		APPLY(0L), NOTICEMOW(1L), REFUCED(2L), REDUCECOIN(3L), ADDCOIN(4L), PAYSUCCESS(5L), PARTPAY(6L), PAYFAILED(7L),DEPOSITED(8l),CLOSED(9L);
		private Long statis;

		private Status(Long value) {
			this.statis = value;
		}

		public Status creatStatus(Long value) {
			for (Status aLight : Status.values()) {
				if (aLight.getStatis().equals(value)) {
					return aLight;
				}
			}
			return null;
		}

		public Long getStatis() {
			return statis;
		}

		public void setStatis(Long statis) {
			this.statis = statis;
		}

		private Long typeId;

		public Long getTypeId() {
			return typeId;
		}

		public void setTypeId(Long typeId) {
			this.typeId = typeId;
		}

	}

	private Type type;

	private String rcvAccount;

	private Long depositAmt;

	private Long realDepositAmt;
	private Long statusId;
	private Long status;

	private String approver;

	private Date approveTime;

	private BankCard userBank;

	private Date mcNoticeTime;

	private Long mcStatus;

	private String memo;

	private String apprDemo;
	private String attach;
	private Long vipLvl;
	private Long rcvId;
	private Long mcAmount;
	private String mcSn;
	private String chargeSn;
	private Long isBatch;
	

	public Long getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Long isBatch) {
		this.isBatch = isBatch;
	}

	public String getChargeSn() {
		return chargeSn;
	}

	public void setChargeSn(String chargeSn) {
		this.chargeSn = chargeSn;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public Long getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public BankCard getUserBank() {
		return userBank;
	}

	public void setUserBank(BankCard userBank) {
		this.userBank = userBank;
	}

	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
	}

	public Long getMcStatus() {
		return mcStatus;
	}

	public void setMcStatus(Long mcStatus) {
		this.mcStatus = mcStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApprDemo() {
		return apprDemo;
	}

	public void setApprDemo(String apprDemo) {
		this.apprDemo = apprDemo;
	}

	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getRcvId() {
		return rcvId;
	}

	public void setRcvId(Long rcvId) {
		this.rcvId = rcvId;
	}

	public Long getRealDepositAmt() {
		return realDepositAmt;
	}

	public Long getMcAmount() {
		return mcAmount;
	}

	public void setMcAmount(Long mcAmount) {
		this.mcAmount = mcAmount;
	}

	public String getMcSn() {
		return mcSn;
	}

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}



	public void setRealDepositAmt(Long realDepositAmt) {
		this.realDepositAmt = realDepositAmt;
	}
}
