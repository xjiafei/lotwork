package com.winterframework.firefrog.game.entity;

import java.util.Date;

/** 
 * 後台→遊戲中心→方案紀錄→查詢結果明細
 * @ClassName: GameOrderOperationsEntity 
 * @Description: 订单记录后台运营映射类 
 * @author Alan
 * @date 2013-10-15 下午5:20:15 
 */
public class GameOrderOperationsEntity {

	/** 订单编号 */
	private String orderCode;
	/** 彩种 */
	private Long lotteryid;
	/**彩种名称*/
	private String lotteryName;
	/**WEB期号*/
	private String webIssueCode;
	/** 期号 */
	private Long issueCode;
	/** 奖期状态*/
	private Long issueStatus;
	/** 订单总金额 */
	private Long totamount;
	/** 订单奖金（计算中奖注单得到的奖金数） */
	private Long totwin;
	/** 订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销 5:存在异常 6:数据归档 */
	private Integer status;
	/** 订单父类型 */
	private Integer parentType;
	/** 父类型ID */
	private Long parentid;
	/** 投注时间 */
	private Date saleTime;
	/** 开奖号码 */
	private String numberRecord;
	/** 订单ID */
	private Long orderId;
	/** 用户帳號 */
	private String account;
	/**用户ID*/
	private Long userid;
	/**订单中投比*/
	private Long winsRatio;
	/**渠道ID*/
	private Long channelId;
	/**渠道版本号*/
	private String channelVersion;
	/**追号倍数*/
	private Integer multiple;
	
	private Long planid;
	/**追号编号*/
	private String planCode;
	/**撤銷方式；0:默認, 1:用戶, 2:系統*/
	private int cancelModes;
	/**截止投注时间*/
	private Date endSaleTime;
	
	private Long adminCanCancelTime;
	
	private Date caculateWinTime;
	
	/**最迟可撤销时间*/
	private Date endCanCancelTime;
	
	private Long totDiamondWin = 0l;
	
	private Long diamondMultiple;
	
	/**
	 * 取得最迟可撤销时间。
	 * @return
	 */
	public Date getEndCanCancelTime() {
		return endCanCancelTime;
	}
	/**
	 * 設定最迟可撤销时间。
	 * @param endCanCancelTime
	 */
	public void setEndCanCancelTime(Date endCanCancelTime) {
		this.endCanCancelTime = endCanCancelTime;
	}
	/**
	 * 取得截止投注时间。
	 * @return
	 */
	public Date getEndSaleTime() {
		return endSaleTime;
	}
	/**
	 * 設定截止投注时间。
	 * @param endSaleTime
	 */
	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}
	/**
	 * 取得订单编号。
	 * @return
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 取得撤銷方式。
	 * @return 0:默認, 1:用戶, 2:系統
	 */
	public int getCancelModes() {
		return cancelModes;
	}
	/**
	 * 設定撤銷方式。
	 * @param cancelModes 0:默認, 1:用戶, 2:系統
	 */
	public void setCancelModes(int cancelModes) {
		this.cancelModes = cancelModes;
	}
	/**
	 * 設定订单编号。
	 * @param orderCode
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 取得彩种。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}
	/**
	 * 設定彩种。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	/**
	 * 取得彩种名称。
	 * @return
	 */
	public String getLotteryName() {
		return lotteryName;
	}
	/**
	 * 設定彩种名称。
	 * @param lotteryName
	 */
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	/**
	 * 取得WEB期号。
	 * @return
	 */
	public String getWebIssueCode() {
		return webIssueCode;
	}
	/**
	 * 設定WEB期号。
	 * @param webIssueCode
	 */
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	/**
	 * 取得期号。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}
	/**
	 * 設定期号。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	/**
	 * 取得奖期状态。
	 * @return
	 */
	public Long getIssueStatus() {
		return issueStatus;
	}
	/**
	 * 設定奖期状态。
	 * @param issueStatus
	 */
	public void setIssueStatus(Long issueStatus) {
		this.issueStatus = issueStatus;
	}
	/**
	 * 取得订单总金额。
	 * @return
	 */
	public Long getTotamount() {
		return totamount;
	}
	/**
	 * 設定订单总金额。
	 * @param totamount
	 */
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	/**
	 * 取得订单奖金（计算中奖注单得到的奖金数）。
	 * @return
	 */
	public Long getTotwin() {
		return totwin;
	}
	/**
	 * 設定订单奖金（计算中奖注单得到的奖金数）。
	 * @param totwin
	 */
	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}
	/**
	 * 取得订单状态。
	 * @return 1:等待开奖, 2:中奖, 3:未中奖, 4:撤销, 5:存在异常, 6:数据归档
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 設定订单状态。
	 * @param status 1:等待开奖, 2:中奖, 3:未中奖, 4:撤销, 5:存在异常, 6:数据归档
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 取得订单父类型。
	 * @return
	 */
	public Integer getParentType() {
		return parentType;
	}
	/**
	 * 設定订单父类型。
	 * @param parentType
	 */
	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}
	/**
	 * 取得父类型ID。
	 * @return
	 */
	public Long getParentid() {
		return parentid;
	}
	/**
	 * 設定父类型ID。
	 * @param parentid
	 */
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	/**
	 * 取得投注时间。
	 * @return
	 */
	public Date getSaleTime() {
		return saleTime;
	}
	/**
	 * 設定投注时间。
	 * @param saleTime
	 */
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	/**
	 * 取得开奖号码。
	 * @return
	 */
	public String getNumberRecord() {
		return numberRecord;
	}
	/**
	 * 設定开奖号码。
	 * @param numberRecord
	 */
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	/**
	 * 取得订单ID。
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 設定订单ID。
	 * @param orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 取得用户帳號。
	 * @return
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 設定用户帳號。
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 取得用户ID。
	 * @return
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 設定用户ID。
	 * @param userid
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 取得订单中投比。
	 * @return
	 */
	public Long getWinsRatio() {
		return winsRatio;
	}
	/**
	 * 設定订单中投比。
	 * @param winsRatio
	 */
	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}
	/**
	 * 取得渠道ID。
	 * @return
	 */
	public Long getChannelId() {
		return channelId;
	}
	/**
	 * 設定渠道ID。
	 * @param channelId
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	/**
	 * 取得渠道版本号。
	 * @return
	 */
	public String getChannelVersion() {
		return channelVersion;
	}
	/**
	 * 設定渠道版本号。
	 * @param channelVersion
	 */
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	/**
	 * 取得追号倍数。
	 * @return
	 */
	public Integer getMultiple() {
		return multiple;
	}
	/**
	 * 設定追号倍数。
	 * @param multiple
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Long getPlanid() {
		return planid;
	}
	public void setPlanid(Long planid) {
		this.planid = planid;
	}
	/**
	 * 取得追号编号。
	 * @return
	 */
	public String getPlanCode() {
		return planCode;
	}
	/**
	 * 設定追号编号。
	 * @param planCode
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public Long getAdminCanCancelTime() {
		return adminCanCancelTime;
	}
	public void setAdminCanCancelTime(Long adminCanCancelTime) {
		this.adminCanCancelTime = adminCanCancelTime;
	}
	public Date getCaculateWinTime() {
		return caculateWinTime;
	}
	public void setCaculateWinTime(Date caculateWinTime) {
		this.caculateWinTime = caculateWinTime;
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
