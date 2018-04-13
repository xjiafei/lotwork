package com.winterframework.firefrog.shortlived.sheepactivity.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivitySheepBigLittle 
* @Description 羊年猜大小
* @author  hugh
* @date 2015年1月12日 下午5:36:12 
*  
*/
public class ActivitySheepBigLittle extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String userName; //用户名			USER_NAME
	private Long userId; //用户名			USER_NAME
	private Long lastNum; //剩余次数   		LAST_NUM  			
	private Long nextWinNumNow;//连续中奖次数	NEXT_WIN_NUM_NOW  	
	private Long maxNextWinNum;//最大连续中奖次数MAX_NEXT_WIN_NUM  
	private Long allAward;//累计投注金额		ALL_AWARD  		数据库存入10000的倍数
	private Long channel;//来源				CHANNEL
	private Long status;//状态				STATUS  

	private Long updateStatus;//状态				STATUS   0显示更新  1显示发布
	private Long updateLastNum; //更新次数 	UPDATE_LAST_NUM
	private String updateName; //更新用户	UPDATE_NAME
	private String updateReason; //更新原因 	UPDATE_REASON
	private Long vip;
	public Long getVip() {
		return vip;
	}
	public void setVip(Long vip) {
		this.vip = vip;
	}
	private Date beginTime; //只用于条件查询
	private Date endTime; //只用于条件查询
	private Integer updateType; //只用于更新
	private Long[] ids; //只用于更新

	private String channelStr;

	private Long allAwardUi;

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

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
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

	public Long getNextWinNumNow() {
		return nextWinNumNow;
	}

	public void setNextWinNumNow(Long nextWinNumNow) {
		this.nextWinNumNow = nextWinNumNow;
	}

	public Long getMaxNextWinNum() {
		return maxNextWinNum;
	}

	public void setMaxNextWinNum(Long maxNextWinNum) {
		this.maxNextWinNum = maxNextWinNum;
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

	public Long getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Long updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChannelStr() {
		if (channel == null) {
			return null;
		} else if (channel == 1) {
			return "ios2.1";
		} else if (channel == 2) {
			return "android2.1";
		} else if (channel == 3) {
			return "web3.0";
		} else if (channel == 4) {
			return "web4.0";
		}
		return null;
	}

	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}

	public Long getAllAwardUi() {
		if (allAward != null) {
			return allAward / 10000;
		}
		return null;
	}

	public void setAllAwardUi(Long allAwardUi) {
		this.allAwardUi = allAwardUi;
	}

}
