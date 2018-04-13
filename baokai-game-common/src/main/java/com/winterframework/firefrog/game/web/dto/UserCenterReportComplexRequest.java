package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserCenterReportComplexRequest 
* @Description: 用户中心盈亏报表复杂条件请求
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
/**
 * @author David.Wu
 *
 */
public class UserCenterReportComplexRequest implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	private String account;
	private Long lotteryId;
	private Long queryTime;
	//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
	/**
	 * 玩法代碼
	 */
	private String betTypeCode; 
	private String crowdId;
	private String groupId;
	private String setId;
	private String methodId;

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Long queryTime) {
		this.queryTime = queryTime;
	}
}
