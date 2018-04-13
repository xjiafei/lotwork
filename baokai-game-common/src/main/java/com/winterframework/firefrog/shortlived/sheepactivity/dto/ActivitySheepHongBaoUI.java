package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.io.Serializable;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;


public class ActivitySheepHongBaoUI implements Serializable {
	private static final long serialVersionUID = -1810852323093377270L;

	private String signTime; //报名时间 			SIGN_TIME
	private String reachTime; //达标时间 		REACH_TIME
	private String getTime; //领取时间 			GET_TIME
	private String verifyTime; //审核时间 		VERIFY_TIME
	private String verifyName; //审核用户名 	VERIFY_NAME
	private String verifyReason; //审核原因 	VERIFY_REASON
	private String verifyStatus;//状态				VERIFY_STATUS
	private String status;//状态				STATUS
	
	private String updateStatus;//状态		UPDATE_STATUS   0显示更新  1显示发布
	private String updateAward; //更新金额 	UPDATE_AWARD
	private String updateName;  //更新用户	UPDATE_NAME
	private String updateReason; //更新原因 	UPDATE_REASON
	
	private String userName; //用户名			USER_NAME
	private String userId;//状态				USER_ID
	private String award;  //红包金额   			AWARD  			数据库存入10000的倍数
	private String awardType;//红包类型			AWARD_TYPE  	
	private String targetAward;//目标投注金额	TARGET_AWARD  	数据库存入10000的倍数
	private String allAward;//累计投注金额		ALL_AWARD  		数据库存入10000的倍数
	private String channel;//来源				CHANNEL
	
	public ActivitySheepHongBaoUI(ActivitySheepHongBao struc) {
		super();
		if(struc.getSignTime() != null){
			signTime = DateFormatUtils.format(struc.getSignTime(), "yyyy-MM-dd HH:mm:ss");
		}
		if(struc.getReachTime() != null){
			reachTime = DateFormatUtils.format(struc.getReachTime(), "yyyy-MM-dd HH:mm:ss");
		}
		if(struc.getGetTime() != null){
			getTime = DateFormatUtils.format(struc.getGetTime(), "yyyy-MM-dd HH:mm:ss");
		}
		if(struc.getVerifyTime() != null){
			verifyTime = DateFormatUtils.format(struc.getVerifyTime(), "yyyy-MM-dd HH:mm:ss");
		}
		verifyName = struc.getVerifyName();
		verifyReason = struc.getVerifyReason();
		if(struc.getVerifyStatus()!=null){
			if(struc.getVerifyStatus()==0L){
				verifyStatus = "未申请";
			}else if(struc.getVerifyStatus()==1L){
				verifyStatus = "已申请";
			}else if(struc.getVerifyStatus()==2L){
				verifyStatus = "审核通过";
			}else if(struc.getVerifyStatus()==3L){
				verifyStatus = "审核不通过";
			}
		}
		

		if(struc.getStatus()==1L){
			status = "未开始";
		}else if(struc.getStatus()==2L){
			status = "可申领";
		}else if(struc.getStatus()==3L){
			status = "已放弃";
		}else if(struc.getStatus()==5L){
			status = "进行中";
		}else if(struc.getStatus()==6L){
			status = "已达标";
		}else if(struc.getStatus()==7L){
			status = "待审核";
		}else if(struc.getStatus()==8L){
			status = "审核通过";
		}else if(struc.getStatus()==9L){
			status = "审核不通过";
		}else if(struc.getStatus()==10L){
			status = "已过期";
		}
		
		userName = struc.getUserName();
		if(struc.getAward()!=null){
		award = struc.getAward()/10000+"";
		}
		if(struc.getAwardType()==1L){
			awardType = "第一个红包";
		}else if(struc.getAwardType()==2L){
			awardType = "第二个红包";
		}else if(struc.getAwardType()==3L){
			awardType = "第三个红包";
		}
		if(struc.getTargetAward()!=null){
		targetAward = struc.getTargetAward()/10000+"";
		}
		if(struc.getUpdateStatus() == 0){
			allAward = struc.getAllAward()/10000+"";
		}else{
			allAward = struc.getAllAward()/10000+struc.getUpdateAward()/10000 +"";
		}
		
		
		if(struc.getChannel()!= null && struc.getChannel().longValue() == 1){
			this.channel = "IOS2.1"; 
		}else if(struc.getChannel()!= null && struc.getChannel().longValue() == 2){
			this.channel = "Andriod";
		}else if(struc.getChannel()!= null && struc.getChannel().longValue() == 3){
			this.channel = "wen3.0";
		}else if(struc.getChannel()!= null && struc.getChannel().longValue() == 4){
			this.channel = "wen3.0";
		}
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getReachTime() {
		return reachTime;
	}

	public void setReachTime(String reachTime) {
		this.reachTime = reachTime;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyReason() {
		return verifyReason;
	}

	public void setVerifyReason(String verifyReason) {
		this.verifyReason = verifyReason;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getUpdateAward() {
		return updateAward;
	}

	public void setUpdateAward(String updateAward) {
		this.updateAward = updateAward;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getTargetAward() {
		return targetAward;
	}

	public void setTargetAward(String targetAward) {
		this.targetAward = targetAward;
	}

	public String getAllAward() {
		return allAward;
	}

	public void setAllAward(String allAward) {
		this.allAward = allAward;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
}
