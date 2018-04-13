package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.web.controller.vo.WithDrawClass;
import com.winterframework.modules.web.util.JsonMapper;

public class WithdrawInitResponse implements Serializable {

	private static final long serialVersionUID = 58542442327945384L;

	private List<UserBankStruc> userBankStruc;
	//当前已经取现的次数
	private Long availWithdrawTime;
	private Long bal;
	private Long availBal;
	private WithDrawClass withdrawStruc;

	public WithdrawInitResponse() {

	}

	public Long getAvailWithdrawTime() {
		return availWithdrawTime;
	}

	public void setAvailWithdrawTime(Long availWithdrawTime) {
		this.availWithdrawTime = availWithdrawTime;
	}

	public Long getBal() {
		return bal;
	}

	public void setBal(Long bal) {
		this.bal = bal;
	}

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

	public WithDrawClass getWithdrawStruc() {
		return withdrawStruc;
	}

	public void setWithdrawStruc(WithDrawClass withdrawStruc) {
		this.withdrawStruc = withdrawStruc;
	}
	public void setWithdrawStruc(String withdrawStruc) {
		this.withdrawStruc = JsonMapper.nonAlwaysMapper().fromJson(withdrawStruc, WithDrawClass.class);
	}
	public List<UserBankStruc> getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(List<UserBankStruc> userBankStruc) {
		this.userBankStruc = userBankStruc;
	}
	
	}
