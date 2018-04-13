package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameRetBetTypePoint extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 804083150183867604L;
	//columns START
	private Long package_id; //方案ID
	private Long item_id; //模板ID
	private String order_code; //訂單編號
	private Long issuecode; //獎期
	private Date createTime; //創建時間
	private String bettype_ret_point_chain ; //投註方式返點金額鏈
	private String bettype_ret_user_chain; //返點用戶鏈
	
	
	public Long getPackage_id() {
		return package_id;
	}
	public void setPackage_id(Long package_id) {
		this.package_id = package_id;
	}
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Long getIssuecode() {
		return issuecode;
	}
	public void setIssuecode(Long issuecode) {
		this.issuecode = issuecode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBettype_ret_point_chain() {
		return bettype_ret_point_chain;
	}
	public void setBettype_ret_point_chain(String bettype_ret_point_chain) {
		this.bettype_ret_point_chain = bettype_ret_point_chain;
	}
	public String getBettype_ret_user_chain() {
		return bettype_ret_user_chain;
	}
	public void setBettype_ret_user_chain(String bettype_ret_user_chain) {
		this.bettype_ret_user_chain = bettype_ret_user_chain;
	}
	
	
}
