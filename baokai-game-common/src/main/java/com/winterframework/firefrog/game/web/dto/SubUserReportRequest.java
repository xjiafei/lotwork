package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: UserCenterReportRequest 
* @Description: 用户中心盈亏报表请求
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
public class SubUserReportRequest implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	@NotNull
	private Long userId;
	
	private Long lotteryId;
	private Long queryTime;
	
	private String account;
	
	//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 Start
		/**
		 * 玩法代碼
		 */
		private String betTypeCode; 

		public String getBetTypeCode() {
			return betTypeCode;
		}

		public void setBetTypeCode(String betTypeCode) {
			this.betTypeCode = betTypeCode;
		}
	//2016.05.10 DavidWu Changed #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 End
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/** 
	* 当前登录用户 
	*/ 
	private Long curUserId;
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCurUserId() {
		return curUserId;
	}

	public void setCurUserId(Long curUserId) {
		this.curUserId = curUserId;
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
