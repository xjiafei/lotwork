package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: QuerySeriesConfigRequest 
* @Description: 查询运营参数响应
* @author Richard
* @date 2013-9-17 下午5:16:31 
*
 */
public class QuerySeriesConfigResponse implements Serializable{

	private static final long serialVersionUID = 2365289585154650552L;

	private Long lotteryid;
	/**撤单起始金额*/
	private Long backoutStartFee;
	private Long backoutStartFee_bak;
	/**撤单手续费比率*/
	private Long backoutRatio;
	private Long backoutRatio_bak;
	/**超过理论开奖时间,官方未开奖*/
	private Long issuewarnNotOpenaward;
	private Long issuewarnNotOpenaward_bak;
	/***早于理论开奖时间,官方提前开奖*/
	private Long issuewarnAheadOpenaward;
	private Long issuewarnAheadOpenaward_bak;
	/**超过理论开奖时间,官方延迟开奖*/
	private Long issuewarnDelayOpenaward;
	private Long issuewarnDelayOpenaward_bak;
	/**开奖后单笔撤销时间*/
	private Long issuewarnBackoutTime;
	private Long issuewarnBackoutTime_bak;
	/**开奖后异常处理时间*/
	private Long issuewarnExceptionTime;
	private Long issuewarnExceptionTime_bak;
	/** 审核发布状态：2，进行中；3，待审核；4，待发布 */
	private Integer status;
	
	/**邮箱*/
	private String email;
	private String email_bak;
	
	/**用户销售截止前可撤销时间*/
	private Long issuewarnUserBackoutTime;
	private Long issuewarnUserBackoutTime_bak;
	
	private Long hasVedio;
	
	private List<VedioStruc> vedioStrucs;
	private Integer isSupport2000;
	
	private String headImg;
	private String userNickName;

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	
	public Integer getIsSupport2000() {
		return isSupport2000;
	}

	public void setIsSupport2000(Integer isSupport2000) {
		this.isSupport2000 = isSupport2000;
	}

	public Long getHasVedio() {
		return hasVedio;
	}

	public void setHasVedio(Long hasVedio) {
		this.hasVedio = hasVedio;
	}

	public List<VedioStruc> getVedioStrucs() {
		return vedioStrucs;
	}

	public void setVedioStrucs(List<VedioStruc> vedioStrucs) {
		this.vedioStrucs = vedioStrucs;
	}

	public QuerySeriesConfigResponse(){
		
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
		return backoutRatio;
	}

	public void setBackoutRatio(Long backoutRatio) {
		this.backoutRatio = backoutRatio;
	}

	public Long getIssuewarnNotOpenaward() {
		return issuewarnNotOpenaward;
	}

	public void setIssuewarnNotOpenaward(Long issuewarnNotOpenaward) {
		this.issuewarnNotOpenaward = issuewarnNotOpenaward;
	}

	public Long getIssuewarnAheadOpenaward() {
		return issuewarnAheadOpenaward;
	}

	public void setIssuewarnAheadOpenaward(Long issuewarnAheadOpenaward) {
		this.issuewarnAheadOpenaward = issuewarnAheadOpenaward;
	}

	public Long getIssuewarnDelayOpenaward() {
		return issuewarnDelayOpenaward;
	}

	public void setIssuewarnDelayOpenaward(Long issuewarnDelayOpenaward) {
		this.issuewarnDelayOpenaward = issuewarnDelayOpenaward;
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

	public Long getBackoutStartFee_bak() {
		return backoutStartFee_bak;
	}

	public void setBackoutStartFee_bak(Long backoutStartFee_bak) {
		this.backoutStartFee_bak = backoutStartFee_bak;
	}

	public Long getBackoutRatio_bak() {
		return backoutRatio_bak;
	}

	public void setBackoutRatio_bak(Long backoutRatio_bak) {
		this.backoutRatio_bak = backoutRatio_bak;
	}

	public Long getIssuewarnNotOpenaward_bak() {
		return issuewarnNotOpenaward_bak;
	}

	public void setIssuewarnNotOpenaward_bak(Long issuewarnNotOpenaward_bak) {
		this.issuewarnNotOpenaward_bak = issuewarnNotOpenaward_bak;
	}

	public Long getIssuewarnAheadOpenaward_bak() {
		return issuewarnAheadOpenaward_bak;
	}

	public void setIssuewarnAheadOpenaward_bak(Long issuewarnAheadOpenaward_bak) {
		this.issuewarnAheadOpenaward_bak = issuewarnAheadOpenaward_bak;
	}

	public Long getIssuewarnDelayOpenaward_bak() {
		return issuewarnDelayOpenaward_bak;
	}

	public void setIssuewarnDelayOpenaward_bak(Long issuewarnDelayOpenaward_bak) {
		this.issuewarnDelayOpenaward_bak = issuewarnDelayOpenaward_bak;
	}

	public Long getIssuewarnBackoutTime_bak() {
		return issuewarnBackoutTime_bak;
	}

	public void setIssuewarnBackoutTime_bak(Long issuewarnBackoutTime_bak) {
		this.issuewarnBackoutTime_bak = issuewarnBackoutTime_bak;
	}

	public Long getIssuewarnExceptionTime_bak() {
		return issuewarnExceptionTime_bak;
	}

	public void setIssuewarnExceptionTime_bak(Long issuewarnExceptionTime_bak) {
		this.issuewarnExceptionTime_bak = issuewarnExceptionTime_bak;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_bak() {
		return email_bak;
	}

	public void setEmail_bak(String email_bak) {
		this.email_bak = email_bak;
	}

	public Long getIssuewarnUserBackoutTime() {
		return issuewarnUserBackoutTime;
	}

	public void setIssuewarnUserBackoutTime(Long issuewarnUserBackoutTime) {
		this.issuewarnUserBackoutTime = issuewarnUserBackoutTime;
	}

	public Long getIssuewarnUserBackoutTime_bak() {
		return issuewarnUserBackoutTime_bak;
	}

	public void setIssuewarnUserBackoutTime_bak(Long issuewarnUserBackoutTime_bak) {
		this.issuewarnUserBackoutTime_bak = issuewarnUserBackoutTime_bak;
	}
}
