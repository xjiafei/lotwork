package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.enums.EnumItem;

public class FundChargeOrder extends FundOrder {

	public FundChargeOrder(EnumItem item) {
		super(item);
		mcOrder = new FundChargeMCOrder();
	}

	private static final long serialVersionUID = 7943137176670719792L;

	public enum Status {
		APPLY("apply", 1),SUCCESS("success", 2), FAILED("failed", 3), USER_CANCEL("failed", 4), ADMIN_CANCEL("failed", 5), PROCESS ("process", 6);
		private Status(String key, long value) {
			this.key = key;
			this.value = value;
		};

		private String key;
		private long value;

		public long getValue() {
			return value;
		};

		public static Status getStatusByValue(Long value) {
			if(value==null) return null;
			for (Status c : Status.values()) {
				if (c.getValue() == value) {
					return c;
				}
			}
			return null;
		}
	}

	private FundBank revBank;

	private Long preChargeAmt;

	private FundBank payBank;
	private BankCard payCard;
	private String issuing_bank_address;

	private BankCard revCard;

	private String revEmail;

	private Long realChargeAmt;

	private Date chargeTime;

	private Status status;
	private Long realBankId;

	private String memo;
	private Long mode;
	private String breakUrl;
	private Long depositMode;
	private FundChargeMCOrder mcOrder;
	private String topVip;
	private String mcSn;
	private String userAct;
	private Long platfom;
	private String ver;
	private Date operatingTime;
	private String bankNumber;
	private String bankAccount;
	private String nickName;
	private String chargeCardNum;
	private String customerIp;
	private Long chargeMode;
	/**0個人版，1企業版*/
	private Long chargeVersion;

	public Long getChargeVersion() {
		return chargeVersion;
	}

	public void setChargeVersion(Long chargeVersion) {
		this.chargeVersion = chargeVersion;
	}

	public Long getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Long chargeMode) {
		this.chargeMode = chargeMode;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public Long getRealBankId() {
		return realBankId;
	}

	public String getUserAct() {
		return userAct;
	}

	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}

	public void setRealBankId(Long realBankId) {
		this.realBankId = realBankId;
	}

	public String getTopVip() {
		return topVip;
	}

	public void setTopVip(String topVip) {
		this.topVip = topVip;
	}

	public String getMcSn() {
		return mcSn;
	}

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}

	public Long getMode() {
		return mode;
	}

	public String getBreakUrl() {
		return breakUrl;
	}

	public void setBreakUrl(String breakUrl) {
		this.breakUrl = breakUrl;
	}

	public Long getDepositMode() {
		return depositMode;
	}

	public void setDepositMode(Long depositMode) {
		this.depositMode = depositMode;
	}

	public BankCard getPayCard() {
		return payCard;
	}

	public void setPayCard(BankCard payCard) {
		this.payCard = payCard;
	}

	public String getIssuing_bank_address() {
		return issuing_bank_address;
	}

	public void setIssuing_bank_address(String issuing_bank_address) {
		this.issuing_bank_address = issuing_bank_address;
	}

	public void setMode(Long mode) {
		this.mode = mode;
	}

	public String getRevEmail() {
		return revEmail;
	}

	public void setRevEmail(String revEmail) {
		this.revEmail = revEmail;
	}

	public Long getPreChargeAmt() {
		return preChargeAmt;
	}

	public void setPreChargeAmt(Long preChargeAmt) {
		this.preChargeAmt = preChargeAmt;
	}

	public Long getRealChargeAmt() {
		return realChargeAmt;
	}

	public void setRealChargeAmt(Long realChargeAmt) {
		this.realChargeAmt = realChargeAmt;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}
	/**是否需要附言，deposit 2快捷 5銀聯 7微信 8支付寶企業版，不需要*/
	public boolean needMemo(){
		return !Long.valueOf(2).equals(this.getDepositMode()) && !Long.valueOf(5).equals(this.getDepositMode()) && !Long.valueOf(7).equals(this.getDepositMode()) && !Long.valueOf(8).equals(this.getDepositMode());
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}



	public FundChargeMCOrder getMcOrder() {
		return mcOrder;
	}

	public void setMcOrder(FundChargeMCOrder mcOrder) {
		this.mcOrder = mcOrder;
	}

	
	public FundBank getRevBank() {
		return revBank;
	}

	public void setRevBank(FundBank revBank) {
		this.revBank = revBank;
	}

	public FundBank getPayBank() {
		return payBank;
	}

	public void setPayBank(FundBank payBank) {
		this.payBank = payBank;
	}

	public BankCard getRevCard() {
		return revCard;
	}

	public void setRevCard(BankCard revCard) {
		this.revCard = revCard;
	}

	public Long getPlatfom() {
		return platfom;
	}

	public void setPlatfom(Long platfom) {
		this.platfom = platfom;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getChargeCardNum() {
		return chargeCardNum;
	}

	public void setChargeCardNum(String chargeCardNum) {
		this.chargeCardNum = chargeCardNum;
	}
	
}
