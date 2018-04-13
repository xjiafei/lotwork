package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
 * @ClassName: GameOrderOperationsQueryRequest 
 * @Description: 订单记录运营查询request
 * @author Alan
 * @date 2013-10-14 下午5:12:48 
 */
public class GameOrderOperationsQueryRequest {

	/**彩种ID*/
	private Long lotteryid;
	/**投注时间选择方式*/
	private Long selectTimeMode;
	/**开始提交时间*/
	private Long startCreateTime;
	/**结束提交时间*/
	private Long endCreateTime;
	/**用户名或方案编号*/
	private String paramCode;
	/**状态            添加新的状态值10，如果状态为10，则只查询审核未通过的的记录，此状态值需要在后台特殊处理*/
	private Integer status;
	/**奖金开始范围*/
	private Long startWinsCount;
	/**奖金结束范围*/
	private Long endWinsCount;
	/**是否包含下级*/
	private Integer containSub;
	/**排序规则*/
	private Integer sortType;
	/**奖期*/
	private Long issueCode;
	/**來源*/
	private String device;
	/**本期方案所有的orderId*/
	private List<Long> orderIdList;
	
	/**
	 * 取得來源。
	 * @return
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * 設定來源。
	 * @param device
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * 取得彩种ID。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}
	/**
	 * 設定彩种ID。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	/**
	 * 取得投注时间选择方式。
	 * @return
	 */
	public Long getSelectTimeMode() {
		return selectTimeMode;
	}
	/**
	 * 設定投注时间选择方式。
	 * @param selectTimeMode
	 */
	public void setSelectTimeMode(Long selectTimeMode) {
		this.selectTimeMode = selectTimeMode;
	}
	/**
	 * 取得开始提交时间。
	 * @return
	 */
	public Long getStartCreateTime() {
		return startCreateTime;
	}
	/**
	 * 設定开始提交时间。
	 * @param startCreateTime
	 */
	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	/**
	 * 取得结束提交时间。
	 * @return
	 */
	public Long getEndCreateTime() {
		return endCreateTime;
	}
	/**
	 * 設定结束提交时间。
	 * @param endCreateTime
	 */
	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	/**
	 * 取得用户名或方案编号。
	 * @return
	 */
	public String getParamCode() {
		return paramCode;
	}
	/**
	 * 設定用户名或方案编号。
	 * @param paramCode
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	/**
	 * 取得状态 。<br>
	 * 添加新的状态值10，如果状态为10，则只查询审核未通过的的记录，此状态值需要在后台特殊处理
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 設定状态。<br>
	 * 添加新的状态值10，如果状态为10，则只查询审核未通过的的记录，此状态值需要在后台特殊处理
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 取得奖金开始范围。
	 * @return
	 */
	public Long getStartWinsCount() {
		return startWinsCount;
	}
	/**
	 * 設定奖金开始范围。
	 * @param startWinsCount
	 */
	public void setStartWinsCount(Long startWinsCount) {
		this.startWinsCount = startWinsCount;
	}
	/**
	 * 取得奖金结束范围。
	 * @return
	 */
	public Long getEndWinsCount() {
		return endWinsCount;
	}
	/**
	 * 設定奖金结束范围。
	 * @param endWinsCount
	 */
	public void setEndWinsCount(Long endWinsCount) {
		this.endWinsCount = endWinsCount;
	}
	/**
	 * 取得是否包含下级。
	 * @return
	 */
	public Integer getContainSub() {
		return containSub;
	}
	/**
	 * 設定是否包含下级。
	 * @param containSub
	 */
	public void setContainSub(Integer containSub) {
		this.containSub = containSub;
	}
	/**
	 * 取得排序规则。
	 * @return
	 */
	public Integer getSortType() {
		return sortType;
	}
	/**
	 * 設定排序规则。
	 * @param sortType
	 */
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	/**
	 * 取得奖期。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}
	/**
	 * 設定奖期。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	/**
	 * 取得獎期內所有orderId。
	 * @param orderIdList
	 */
	public List<Long> getOrderIdList() {
		return orderIdList;
	}
	/**
	 * 設定獎期內所有orderId。
	 * @param orderIdList
	 */
	public void setOrderIdList(List<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}

}
