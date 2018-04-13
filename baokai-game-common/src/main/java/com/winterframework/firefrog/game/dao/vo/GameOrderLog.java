package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

 
/**
 * 订单操作日志
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
public class GameOrderLog extends BaseEntity { 
	//alias
	public static final String TABLE_ALIAS = "订单操作日志表";
	public static final String ALIAS_ORDERID = "订单ID";
	public static final String ALIAS_OPERATION = "操作CODE";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREARETIME = "创建时间"; 
 
	//columns START
	private Long orderId;
	private String operation;
	private String remark;
	private Long userId;
	private Date createTime; 
	//columns END
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	  
	
}
