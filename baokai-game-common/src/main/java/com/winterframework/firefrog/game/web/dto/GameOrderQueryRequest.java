package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class GameOrderQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private Long lotteryId;
	/** 状态 0：全部 1:等待开奖 2:中奖 3:未中奖 4:撤销*/
	private Integer status;
	/** 期号 注意不是web显示奖期 */
	private String webIssueCode;
	/** 开始时间 */
	private Long startTime;
	/** 结束时间 */
	private Long endTime;
	/** 查询订单编号 */
	private String orderCode;
	/** 追号状态 0:全部 1:方案  2:追号 */
	private Integer parentType;
	/**是否包含下级：0：不包含 1包含*/
	private Integer containSub;
	/**用户名*/
	private String account;
	
	private Long sysUserId;
	
	/**一代回收记录时间**/
	private Date recycleDate;

	public Long getLotteryId() {
		return lotteryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}


	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getParentType() {
		return parentType;
	}

	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	public Integer getContainSub() {
		return containSub;
	}

	public void setContainSub(Integer containSub) {
		this.containSub = containSub;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Date getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}
}
