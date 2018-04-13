package com.winterframework.firefrog.shortlived.sheepactivity.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivitySheepHongBao 
* @Description 羊年红包 
* @author  hugh
* @date 2015年1月12日 下午5:36:12 
*  
*/
public class ActivitySheepHongBao  extends BaseEntity{
 
	private static final long serialVersionUID = -6017838657396186774L;

	private Date signTime; //报名时间 			SIGN_TIME
	private Date reachTime; //达标时间 		REACH_TIME
	private Date getTime; //领取时间 			GET_TIME
	private Date verifyTime; //审核时间 		VERIFY_TIME
	private String verifyName; //审核用户名 	VERIFY_NAME
	private String verifyReason; //审核原因 	VERIFY_REASON
	private Long verifyStatus;//状态				VERIFY_STATUS
	private Long status;//状态				STATUS
	
	private Long updateStatus;//状态		UPDATE_STATUS   0显示更新  1显示发布
	private Long updateAward; //更新金额 	UPDATE_AWARD
	private String updateName;  //更新用户	UPDATE_NAME
	private String updateReason; //更新原因 	UPDATE_REASON
	
	private String userName; //用户名			USER_NAME
	private Long userId;//状态				USER_ID
	private Long award=0L;  //红包金额   			AWARD  			数据库存入10000的倍数
	private Long awardType;//红包类型			AWARD_TYPE  	
	private Long targetAward=0L;//目标投注金额	TARGET_AWARD  	数据库存入10000的倍数
	private Long allAward=0L;//累计投注金额		ALL_AWARD  		数据库存入10000的倍数
	private Long channel;//来源				CHANNEL
	private Long indexHb;//红包索引
	private Date deadTime;//截止时间
	private Long vip;
	public Long getVip() {
		return vip;
	}
	public void setVip(Long vip) {
		this.vip = vip;
	}
	
	
	
	private Date signTimeBegin; //只用于条件查询
	private Date signTimEnd; //只用于条件查询
	private int updateType; //只用于更新
	private Long[]  ids; //只用于更新
	public Date getSignTimeBegin() {
		return signTimeBegin;
	}
	public void setSignTimeBegin(Date signTimeBegin) {
		this.signTimeBegin = signTimeBegin;
	}
	public Date getSignTimEnd() {
		return signTimEnd;
	}
	public void setSignTimEnd(Date signTimEnd) {
		this.signTimEnd = signTimEnd;
	}
	public Long getUpdateAward() {
		return updateAward;
	}
	public void setUpdateAward(Long updateAward) {
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
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getAward() {
		return award;
	}
	public void setAward(Long award) {
		this.award = award;
	}
	public Long getAwardType() {
		return awardType;
	}
	public void setAwardType(Long awardType) {
		this.awardType = awardType;
	}
	public Long getTargetAward() {
		return targetAward;
	}
	public void setTargetAward(Long targetAward) {
		this.targetAward = targetAward;
	}
	public Long getAllAward() {
		return allAward==null?0L:allAward;
	}
	public void setAllAward(Long allAward) {
		this.allAward = allAward;
	}
	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getReachTime() {
		return reachTime;
	}
	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}
	public Date getGetTime() {
		return getTime;
	}
	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
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
	public Long getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(Long updateStatus) {
		this.updateStatus = updateStatus;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Long getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Long verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getIndexHb() {
		return indexHb;
	}
	public void setIndexHb(Long indexHb) {
		this.indexHb = indexHb;
	}
	public Date getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}

	
}
