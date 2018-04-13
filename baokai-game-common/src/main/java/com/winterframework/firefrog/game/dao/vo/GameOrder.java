package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameOrder extends BaseEntity {

	private static final long serialVersionUID = 1251592659764090685L;
	//alias
	public static final String TABLE_ALIAS = "订单表";
	public static final String ALIAS_PARENTID = "订单父ID";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_LOTTORYID = "彩种";
	public static final String ALIAS_TOTAMOUNT = "订单总金额";
	public static final String ALIAS_STATUS = "订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销";
	public static final String ALIAS_ORDER_TIME = "生成时间";
	public static final String ALIAS_CALCULATE_WIN_TIME = "计奖时间";
	public static final String ALIAS_SALE_TIME = "销售时间";
	public static final String ALIAS_CANCEL_TIME = "撤销时间";
	public static final String ALIAS_CANCEL_MODES = "撤销方式 0:默认 1:用户 2:系统";
	public static final String ALIAS_ORDER_CODE = "订单编号";
	public static final String ALIAS_File_MODE = "文件模式";
	public static final String ALIAS_FUND_STATUS = "资金状态";
	public static final String ALIAS_DIAMOND_MULTIPLE = "鑽石加獎倍數";
	//date formats

	//columns START
	private Long parentid;
	
	private Long userid;
	private Long issueCode;
	private Long lotteryid;
	private Long totamount;
	private Integer status;
	private Date orderTime;
	private Date calculateWinTime;
	private Date saleTime;
	private Date cancelTime;
	private Integer cancelModes;
	private String orderCode;

	//新增字段
	private Integer fileMode;
	private Long diamondMultiple;
	//额外添加字段，与数据库不一致
	private String webIssueCode;
	private Long totalWin;
	private String numberRecord;
	private Integer multiple;
	private Integer parentType;
	private Long totDiamondWin = 0L;
	//columns END
	
	
	//开奖中间字段  中奖情况一等奖几注  不存于数据库
	private Integer awardOne = 0;
	//开奖中间字段  中奖情况二等奖几注  不存于数据库
	private Integer awardTwo = 0;	
	
	//开奖中间字段  中奖情况一等奖总共几注  不存于数据库
	private Integer awardOneCount = 0;
	//开奖中间字段  中奖情况二等奖总共几注  不存于数据库
	private Integer awardTwoCount = 0;
	
	//最迟可撤销时间
	private Date endCanCancelTime;
	private Long adminCanCancelTime;
	
	//撤单手续费
	private Long cancelFee;
	private Long planId;
	private Long planDetailId;
	private Long lastOrderId;  
	private Long lastIssueCode;	
	
	private String userName;
	
	//活动接口使用，请调用的地方自行添加此字段的查询
	private String userChain;
	private Integer fundStatus;
	private Long awardGroupId;	//用户奖金组ID
	
	private String headImg;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public enum Status{
		//订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销 5:存在异常 6:数据归档
		WAIT(1), WIN(2), UN_WIN(3), CANCEL(4), EXCEP(5), FILE(6); 
		private int _value=1;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	/**
	 * 撤销方式
	 */
	public enum CancelMode { 
		/**0:默认*/
		DEFAULT(0), 
		/**1:用户*/
		USER(1), 
		/**2:系统*/
		SYSTEM(2); 
		private int vlaue; 
		CancelMode(int value) {
			this.vlaue = value;
		} 
		public int getValue(){
			return this.vlaue;
		} 
	}
	
	/**
	 * 资金状态
	 */
	public enum FundStatus{
		/**-1：无需调用*/
		IGNORE(-1),
		/**0:初始值*/
		INIT(0),
		/**1：待调用*/
		NEED(1),
		/**2:已派奖*/
		WIN(2), 
		/**3：调用完成*/
		UNWIN(3); 
		private int _value=1;
		FundStatus(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(Long planDetailId) {
		this.planDetailId = planDetailId;
	}

	public Long getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(Long lastOrderId) {
		this.lastOrderId = lastOrderId;
	} 
	
	public GameOrder() {
	}

	public GameOrder(Long id) {
		this.id = id;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getCancelModes() {
		return cancelModes;
	}

	public void setCancelModes(Integer cancelModes) {
		this.cancelModes = cancelModes;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getFileMode() {
		return fileMode;
	}

	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(Long totalWin) {
		this.totalWin = totalWin;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getParentType() {
		return parentType;
	}

	public void setParentType(Integer parentType) {
		this.parentType = parentType;
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

	public Date getEndCanCancelTime() {
		return endCanCancelTime;
	}

	public void setEndCanCancelTime(Date endCanCancelTime) {
		this.endCanCancelTime = endCanCancelTime;
	}

	public Long getCancelFee() {
		return cancelFee;
	}

	public void setCancelFee(Long cancelFee) {
		this.cancelFee = cancelFee;
	}

	public Long getLastIssueCode() {
		return lastIssueCode;
	}

	public void setLastIssueCode(Long lastIssueCode) {
		this.lastIssueCode = lastIssueCode;
	}

	public Long getAdminCanCancelTime() {
		return adminCanCancelTime;
	}

	public void setAdminCanCancelTime(Long adminCanCancelTime) {
		this.adminCanCancelTime = adminCanCancelTime;
	}

	public Integer getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(Integer fundStatus) {
		this.fundStatus = fundStatus;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Long getTotDiamondWin() {
		return totDiamondWin;
	}

	public void setTotDiamondWin(Long totDiamondWin) {
		this.totDiamondWin = totDiamondWin;
	}

	public Long getDiamondMultiple() {
		return diamondMultiple;
	}

	public void setDiamondMultiple(Long diamondMultiple) {
		this.diamondMultiple = diamondMultiple;
	}
}
