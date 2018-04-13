package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.firefrog.fund.service.impl.ddb.DDBDeposit;
import com.winterframework.firefrog.fund.service.impl.din.DINDeposit;
import com.winterframework.firefrog.fund.service.impl.ecpss.ECPSSDeposit;
import com.winterframework.firefrog.fund.service.impl.hb.HBDeposit;
import com.winterframework.firefrog.fund.service.impl.huayin.HUAYINDeposit;
import com.winterframework.firefrog.fund.service.impl.jinyang.JINYANGDeposit;
import com.winterframework.firefrog.fund.service.impl.sp.SPDeposit;
import com.winterframework.firefrog.fund.service.impl.th.THDeposit;
import com.winterframework.firefrog.fund.service.impl.wft.WFTDeposit;
import com.winterframework.firefrog.fund.service.impl.worth.WORTHDeposit;
import com.winterframework.firefrog.fund.service.impl.yinbang.YINBANGDeposit;

public class ChargeApplyResponse {

	private Long orderId;

	private String revAccName;
	private String rcvBankName;
	private String rcvAccNum;

	private String rcvEmail;

	private Long expireTime;
	private String breakUrl;

	private UserBankStruc[] cards;

	private String chargeMemo;
	private Long bankId;
	private Long mode;
	private String customerIp;
	private String payOrderNo;
	private THDeposit thPay;
	private ECPSSDeposit pay;
	private HBDeposit hbPay;
	private WORTHDeposit worthPay;
	private SPDeposit spPay;
	private DDBDeposit ddbPay;
	private WFTDeposit wftPay;
	private DINDeposit dinPay;
	private HUAYINDeposit huayinPay;
	private YINBANGDeposit yinbangPay;
	private JINYANGDeposit jinyangPay;
	
	public THDeposit getThPay() {
		return thPay;
	}

	public void setThPay(THDeposit thPay) {
		this.thPay = thPay;
	}

	
	public String getCustomerIp() {
		return customerIp;
	}

	public ECPSSDeposit getPay() {
		return pay;
	}

	public void setPay(ECPSSDeposit pay) {
		this.pay = pay;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public Long getOrderId() {
		return orderId;
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

	public String getRcvBankName() {
		return rcvBankName;
	}

	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}

	public String getRcvAccNum() {
		return rcvAccNum;
	}

	public void setRcvAccNum(String rcvAccNum) {
		this.rcvAccNum = rcvAccNum;
	}

	public void setMode(Long mode) {
		this.mode = mode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRevAccName() {
		return revAccName;
	}

	public void setRevAccName(String revAccName) {
		this.revAccName = revAccName;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	
	

	public UserBankStruc[] getCards() {
		return cards;
	}

	public void setCards(UserBankStruc[] cards) {
		this.cards = cards;
	}

	public String getChargeMemo() {
		return chargeMemo;
	}

	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}

	public HBDeposit getHbPay() {
		return hbPay;
	}

	public void setHbPay(HBDeposit hbPay) {
		this.hbPay = hbPay;
	}

	/**
	 * 取得華勢參數(微信)。
	 * @return worthPay
	 */
	public WORTHDeposit getWorthPay() {
		return worthPay;
	}

	/**
	 * 設置華勢參數(微信)。
	 * @param worthPay
	 */
	public void setWorthPay(WORTHDeposit worthPay) {
		this.worthPay = worthPay;
	}

	public SPDeposit getSpPay() {
		return spPay;
	}

	public void setSpPay(SPDeposit spPay) {
		this.spPay = spPay;
	}

	public DDBDeposit getDdbPay() {
		return ddbPay;
	}

	public void setDdbPay(DDBDeposit ddbPay) {
		this.ddbPay = ddbPay;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public WFTDeposit getWftPay() {
		return wftPay;
	}

	public void setWftPay(WFTDeposit wftPay) {
		this.wftPay = wftPay;
	}

	public DINDeposit getDinPay() {
		return dinPay;
	}

	public void setDinPay(DINDeposit dinPay) {
		this.dinPay = dinPay;
	}

	public HUAYINDeposit getHuayinPay() {
		return huayinPay;
	}

	public void setHuayinPay(HUAYINDeposit huayinPay) {
		this.huayinPay = huayinPay;
	}

	public YINBANGDeposit getYinbangPay() {
		return yinbangPay;
	}

	public void setYinbangPay(YINBANGDeposit yinbangPay) {
		this.yinbangPay = yinbangPay;
	}

	public JINYANGDeposit getJinyangPay() {
		return jinyangPay;
	}

	public void setJinyangPay(JINYANGDeposit jinyangPay) {
		this.jinyangPay = jinyangPay;
	}
	
	
}
