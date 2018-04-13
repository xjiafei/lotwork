package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 
 * @ClassName FundWithdrawLog.java
 * @author Ami.Tsai
 * @date 2015年12月21日
 *
 */
public class FundWithdrawLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482485296948049201L;

	//alias
	public static final String TABLE_ALIAS = "FundWithdrawLog";
	
	private Long id;
	
	private String withdrawSn;

	private String logModel;
	
	private String logContent;

	private Date createTime;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWithdrawSn() {
		return withdrawSn;
	}

	public void setWithdrawSn(String withdrawSn) {
		this.withdrawSn = withdrawSn;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogModel() {
		return logModel;
	}

	public void setLogModel(String logModel) {
		this.logModel = logModel;
	}

}
