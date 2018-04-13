package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameFundQueue extends BaseEntity {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -4349019767483001829L;
	//alias
	public static final String TABLE_ALIAS = "资金请求队列表";  
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_FUND_TYPE = "资金类型";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_USERID_LIST = "用户列表";
	public static final String ALIAS_AMOUNT_LIST = "金额列表";
	public static final String ALIAS_ORDER_CODE_LIST = "订单编码列表";
	public static final String ALIAS_PLAN_CODE_LIST = "追号编码列表";
	public static final String ALIAS_NOTE = "备注";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_REMARK = "异常备注";

	//date formats

	//columns START  
	private Long lotteryId;
	private Long issueCode;
	private Integer fundType;
	private Integer status;
	private String userIdList;
	private String amountList;
	private String orderCodeList;
	private String planCodeList;
	private String note;
	private Date createTime;
	private Date updateTime;
	private String remark;
	//columns END  
	public enum Status { 
		//状态: 0--未处理  1--已处理
		UN_EXEC(0), EXEC(1);
		private int _value = 0; 
		Status(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Integer getFundType() {
		return fundType;
	}
	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	public String getAmountList() {
		return amountList;
	}
	public void setAmountList(String amountList) {
		this.amountList = amountList;
	}
	public String getOrderCodeList() {
		return orderCodeList;
	}
	public void setOrderCodeList(String orderCodeList) {
		this.orderCodeList = orderCodeList;
	}
	public String getPlanCodeList() {
		return planCodeList;
	}
	public void setPlanCodeList(String planCodeList) {
		this.planCodeList = planCodeList;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
}
