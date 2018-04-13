package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.Date;

import com.winterframework.firefrog.advert.web.dto.BaseEntity;



/** 
* @ClassName ActivitySheepWheelSurf 
* @Description 羊年转盘抽奖
* @author  hugh
* @date 2015年1月12日 下午5:36:12 
*  
*/
public class ActivitySheepWheelSurf  extends BaseEntity{
 
	private static final long serialVersionUID = -1L;
		
	private String userName; //用户名			USER_NAME
	private Long userId;  //剩余次数   		LASTNUM  		
	private Long lastNum;  //剩余次数   		LASTNUM  		
	private Long allAward;//累计中奖			ALL_AWARD			数据库存入10000的倍数
	private Long allRecharge;//累计充值		ALL_RECHARGE  		数据库存入10000的倍数
	private Long channel;//来源				CHANNEL
	private Long status;//状态				STATUS   
	
	private Long updateStatus;//状态				STATUS   0显示更新  1显示发布
	private Long updateLastNum; //更新次数 	UPDATE_LAST_NUM
	private String updateName;  //更新用户	UPDATE_NAME
	private String updateReason; //更新原因 	UPDATE_REASON
	
	private Date beginTime; //只用于条件查询
	private Date endTime; //只用于条件查询
	private int updateType; //只用于更新
	private Long[]  ids; //只用于更新
	
	private Long startTimel;
	
	private Long endTimel;
	
	private Long vip;
	public Long getVip() {
		return vip;
	}
	public void setVip(Long vip) {
		this.vip = vip;
	}
	
	public Long getStartTimel() {
		return startTimel;
	}
	public void setStartTimel(Long startTimel) {
		this.startTimel = startTimel;
	}
	public Long getEndTimel() {
		return endTimel;
	}
	public void setEndTimel(Long endTimel) {
		this.endTimel = endTimel;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public Long getUpdateLastNum() {
		return updateLastNum;
	}
	public void setUpdateLastNum(Long updateLastNum) {
		this.updateLastNum = updateLastNum;
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
	public Long getLastNum() {
		return lastNum;
	}
	public void setLastNum(Long lastNum) {
		this.lastNum = lastNum;
	}

	public Long getAllAward() {
		return allAward;
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
	public Long getAllRecharge() {
		return allRecharge;
	}
	public void setAllRecharge(Long allRecharge) {
		this.allRecharge = allRecharge;
	}
	public Long getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(Long updateStatus) {
		this.updateStatus = updateStatus;
	}
	
	

	
}
