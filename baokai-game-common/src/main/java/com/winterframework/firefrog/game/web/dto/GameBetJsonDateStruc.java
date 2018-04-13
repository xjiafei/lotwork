package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: JsonDateStruc 
* @Description: 返回的数据结构
* @author david 
* @date 2013-9-27 下午3:02:42 
*  
*/
public class GameBetJsonDateStruc {
	/**撤单手续费*/
	private Double handingcharge;

	private GameBetTplData tplData;

	//封锁变价信息
	private GameBlockadeInfo blockadeInfo;

	//封锁变价订单详细信息
	private GameOrderRequestDTO orderData;
	
	private List<GameOrderResponeOverMutipleDTO> overMutipleData;
	
	private String projectId;
	
	private String writeTime;
	
	private String result;
	
	private Long totalprice;
	
	private Long winMoney;
	
	private Long winNum;
	
	private Long orderId;
	
	private String number;
	
	private Long diamondLv;
	
	private Double diamondTimes;
	
	private List<MMCOrderDetail> list;
	
	public List<GameOrderResponeOverMutipleDTO> getOverMutipleData() {
		return overMutipleData;
	}

	public void setOverMutipleData(List<GameOrderResponeOverMutipleDTO> overMutipleData) {
		this.overMutipleData = overMutipleData;
	}

	public Double getHandingcharge() {
		return handingcharge;
	}

	public void setHandingcharge(Double handingcharge) {
		this.handingcharge = handingcharge;
	}

	public GameBetTplData getTplData() {
		return tplData;
	}

	public void setTplData(GameBetTplData tplData) {
		this.tplData = tplData;
	}

	public GameBlockadeInfo getBlockadeInfo() {
		return blockadeInfo;
	}

	public void setBlockadeInfo(GameBlockadeInfo blockadeInfo) {
		this.blockadeInfo = blockadeInfo;
	}

	public GameOrderRequestDTO getOrderData() {
		return orderData;
	}

	public void setOrderData(GameOrderRequestDTO orderData) {
		this.orderData = orderData;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Long totalprice) {
		this.totalprice = totalprice;
	}

	public Long getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(Long winMoney) {
		this.winMoney = winMoney;
	}

	public Long getWinNum() {
		return winNum;
	}

	public void setWinNum(Long winNum) {
		this.winNum = winNum;
	}

	public List<MMCOrderDetail> getList() {
		return list;
	}

	public void setList(List<MMCOrderDetail> list) {
		this.list = list;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getDiamondLv() {
		return diamondLv;
	}

	public void setDiamondLv(Long diamondLv) {
		this.diamondLv = diamondLv;
	}

	public Double getDiamondTimes() {
		return diamondTimes;
	}

	public void setDiamondTimes(Double diamondTimes) {
		this.diamondTimes = diamondTimes;
	}
}
