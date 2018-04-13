package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
/**
 * 
* @ClassName: EditSeriesConfigRequest 
* @Description: 修改运营参数
* @author Richard
* @date 2013-9-17 下午5:24:49 
*
 */
public class EditSeriesConfigRequest implements Serializable{
	
	private static final long serialVersionUID = -7292313251865350863L;
	@NotNull
	private Long lotteryid;
	/**撤单起始金额*/
	@NotNull
	private Long backoutStartFee;
	/**撤单手续费比率*/
	@NotNull
	private Long BackoutRatio;
	/**超过理论开奖时间,官方未开奖*//*
	@NotNull
	private Long issuewarnNotOpenaward;
	*//***早于理论开奖时间,官方提前开奖*//*
	@NotNull
	private Long issuewarnAheadOpenaward;
	*//**超过理论开奖时间,官方延迟开奖*//*
	@NotNull
	private Long issuewarnDelayOpenaward;*/
	/**开奖后单笔撤销时间*/
	@NotNull
	private Long issuewarnBackoutTime;
	/**开奖后异常处理时间*/
	@NotNull
	private Long issuewarnExceptionTime;
	
	/**截止销售前用户撤单时间*/
	@NotNull
	private Long issuewarnUserBackoutTime;
	
	private String email;
	
	public EditSeriesConfigRequest(){
		
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getBackoutStartFee() {
		return backoutStartFee;
	}

	public void setBackoutStartFee(Long backoutStartFee) {
		this.backoutStartFee = backoutStartFee;
	}

	public Long getBackoutRatio() {
		return BackoutRatio;
	}

	public void setBackoutRatio(Long backoutRatio) {
		BackoutRatio = backoutRatio;
	}

	public Long getIssuewarnBackoutTime() {
		return issuewarnBackoutTime;
	}

	public void setIssuewarnBackoutTime(Long issuewarnBackoutTime) {
		this.issuewarnBackoutTime = issuewarnBackoutTime;
	}

	public Long getIssuewarnExceptionTime() {
		return issuewarnExceptionTime;
	}

	public void setIssuewarnExceptionTime(Long issuewarnExceptionTime) {
		this.issuewarnExceptionTime = issuewarnExceptionTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIssuewarnUserBackoutTime() {
		return issuewarnUserBackoutTime;
	}

	public void setIssuewarnUserBackoutTime(Long issuewarnUserBackoutTime) {
		this.issuewarnUserBackoutTime = issuewarnUserBackoutTime;
	}
}
