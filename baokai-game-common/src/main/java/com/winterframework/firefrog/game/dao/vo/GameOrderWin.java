 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameOrderWin extends BaseEntity {
	
	private static final long serialVersionUID = 3129143334596538440L;
	//alias
	public static final String TABLE_ALIAS = "中奖订单表";
	public static final String ALIAS_ORDERID = "订单ID";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_COUNT_WIN = "总计奖金";
	public static final String ALIAS_ORDER_TIME = "订单创建时间";
	public static final String ALIAS_CALCULATE_WIN_TIME = "计奖时间";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_STATUS = "派奖状态 0 待派奖 1 已派奖 2 已冻结 3 已撤销";
	public static final String ALIAS_WINS_RATIO = "订单中投比";
	public static final String ALIAS_MAXSLIP_WINS = "单注最大奖金";
	public static final String ALIAS_SLIP_WINSRATIO = "单注最大中投比";
	public static final String ALIAS_DIAMOND_COUNT_WIN = "鑽石总计奖金";
	
	//date formats
	
	//columns START
	private Long orderid;
	private Long userid;
	private Long issueCode;
	private Integer lotteryid;
	private Long countWin;
	private Date orderTime;
	private Date calculateWinTime;
	private Date saleTime;
	private Long status;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private Long isNotice;
	private Long diamondCountWin = 0l;
	//columns END
	private String orderCode;

	
	//开奖中间字段  中奖情况一等奖几注  不存于数据库
	private Integer awardOne = 0;
	//开奖中间字段  中奖情况二等奖几注  不存于数据库
	private Integer awardTwo = 0;	
	
	//开奖中间字段  中奖情况一等奖总共几注  不存于数据库
	private Integer awardOneCount = 0;
	//开奖中间字段  中奖情况二等奖总共几注  不存于数据库
	private Integer awardTwoCount = 0;
	
	public enum Status{
		//派奖状态 0 待派奖 1 已派奖 2 已冻结 3 已撤销
		WAITING(0),DISTRIBUTE(1),FREEZE(2),CANCEL(3);
		private int _value;
		private Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	public GameOrderWin(){
	}

	public GameOrderWin(
		Long id
	){
		this.id = id;
	}

	public void setOrderid(Long value) {
		this.orderid = value;
	}
	
	public Long getOrderid() {
		return this.orderid;
	}
	public void setUserid(Long value) {
		this.userid = value;
	}
	
	public Long getUserid() {
		return this.userid;
	}
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	
	public Long getIssueCode() {
		return this.issueCode;
	}
	public void setLotteryid(Integer value) {
		this.lotteryid = value;
	}
	
	public Integer getLotteryid() {
		return this.lotteryid;
	}
	public void setCountWin(Long value) {
		this.countWin = value;
	}
	
	public Long getCountWin() {
		return this.countWin;
	}
	
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCalculateWinTime() {
		return calculateWinTime;
	}

	public void setCalculateWinTime(Date calculateWinTime) {
		this.calculateWinTime = calculateWinTime;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setWinsRatio(Long value) {
		this.winsRatio = value;
	}
	
	public Long getWinsRatio() {
		return this.winsRatio;
	}
	public void setMaxslipWins(Long value) {
		this.maxslipWins = value;
	}
	
	public Long getMaxslipWins() {
		return this.maxslipWins;
	}
	public void setSlipWinsratio(Long value) {
		this.slipWinsratio = value;
	}
	
	public Long getSlipWinsratio() {
		return this.slipWinsratio;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getAwardOne() {
		return awardOne;
	}

	public void setAwardOne(Integer awardOne) {
		this.awardOne = awardOne;
	}

	public Integer getAwardTwo() {
		return awardTwo;
	}

	public void setAwardTwo(Integer awardTwo) {
		this.awardTwo = awardTwo;
	}

	public Integer getAwardOneCount() {
		return awardOneCount;
	}

	public void setAwardOneCount(Integer awardOneCount) {
		this.awardOneCount = awardOneCount;
	}

	public Integer getAwardTwoCount() {
		return awardTwoCount;
	}

	public void setAwardTwoCount(Integer awardTwoCount) {
		this.awardTwoCount = awardTwoCount;
	}

	public Long getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Long isNotice) {
		this.isNotice = isNotice;
	}

	public Long getDiamondCountWin() {
		return diamondCountWin;
	}

	public void setDiamondCountWin(Long diamondCountWin) {
		this.diamondCountWin = diamondCountWin;
	}
   
}

