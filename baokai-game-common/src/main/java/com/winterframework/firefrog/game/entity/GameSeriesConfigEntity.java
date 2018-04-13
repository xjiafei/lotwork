package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.VedioStruc;

/** 
* @ClassName: GameSeriesConfigEntity 
* @Description: 游戏奖期规则配置entity
* @author Alan
* @date 2013-9-17 下午5:38:07 
*  
*/
public class GameSeriesConfigEntity implements Serializable {

	private static final long serialVersionUID = -4797922610680651282L;

	private Long id;
	private Lottery lottery;
	private Long backoutStartFee;
	private Long backoutStartFee_bak;
	private Long backoutRatio;
	private Long backoutRatio_bak;
	private Long issuewarnNotOpenaward;
	private Long issuewarnNotOpenaward_bak;
	private Long issuewarnAheadOpenaward;
	private Long issuewarnAheadOpenaward_bak;
	private Long issuewarnDelayOpenaward;
	private Long issuewarnDelayOpenaward_bak;
	private Long issuewarnBackoutTime;
	private Long issuewarnBackoutTime_bak;
	private Long issuewarnExceptionTime;
	private Long issuewarnExceptionTime_bak;
	private Date createTime;
	private Date updateTime;
	private StatusType status;
	
	/**邮箱*/
	private String email;
	private String email_bak;
	
	/**用户销售截止前可撤销时间*/
	private Long issuewarnUserBackoutTime;
	private Long issuewarnUserBackoutTime_bak;
	
	private Long hasVideo;
	private List<VedioStruc> vedioStrucs;
	
	public enum StatusType {
		//状态：2进行中，3待审核、4待发布、5审核未通过、6发布未通过
		Action(2), Pending(3), Released(4), Unapprove(5), PublishFailed(6);
		
		private int _value = 3;
		
		StatusType(int status){
			this._value = status;
		}
		
		public int getValue(){
			return _value;
		}
	}
	
	public GameSeriesConfigEntity(){
		
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Long hasVideo) {
		this.hasVideo = hasVideo;
	}

	public List<VedioStruc> getVedioStrucs() {
		return vedioStrucs;
	}

	public void setVedioStrucs(List<VedioStruc> vedioStrucs) {
		this.vedioStrucs = vedioStrucs;
	}
}
