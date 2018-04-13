package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrdersStruc implements Serializable {

	private static final long serialVersionUID = 1L;

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
	/** 奖期状态 */
	private Long issueStatus;
	/** 订单总金额 */
	private Long totamount;
	/** 订单奖金（计算中奖注单得到的奖金数） */
	private Long totwin=0L;
	/** 订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销 5:存在异常 6:数据归档 */
	private Integer status;
	/** 订单父类型 */
	private Integer parentType;
	/** 父类型ID */
	private Long parentid;
	/** 追号倍数 */
	private Integer multiple;
	/** 投注时间 */
	private Long saleTime;
	/** 开奖号码 */
	private String numberRecord;
	/** 订单ID */
	private Long orderId;
	private String headImg;
	private String userNickName;
	/** 用户名 */
	private String account;
	/**用户ID*/
	private Long userid;
	/**订单中投比*/
	private Long winsRatio;
	/**渠道ID*/
	private Long channelId = 1L;
	/**渠道版本号*/
	private String channelVersion;
	
	private Long planid;
	/**追号编号*/
	private String planCode;
	
	private Boolean canCancel;
	
	private Boolean adminCanCancel;
	
	private int cancelModels;
	
	private Date endSaleTime;
	
	private Long totDiamondWin = 0l;
	
	List<SlipsStruc>slipsStruc;
	
	private Long diamondMultiple = 0l;
	/** 順利秒秒彩 判斷12生肖，預設0， 12生肖：1 ，鑽石加注：2 */
	private Long activityType =0L;
	
	public Date getEndSaleTime() {
		return endSaleTime;
	}
	
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	
	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

	public int getCancelModels() {
		return cancelModels;
	}

	public void setCancelModels(int cancelModels) {
		this.cancelModels = cancelModels;
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

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(Long issueStatus) {
		this.issueStatus = issueStatus;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getTotwin() {
		return totwin;
	}

	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParentType() {
		return parentType;
	}

	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getWinsRatio() {
		return winsRatio;
	}

	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Boolean getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(Boolean canCancel) {
		this.canCancel = canCancel;
	}

	public Boolean getAdminCanCancel() {
		return adminCanCancel;
	}

	public void setAdminCanCancel(Boolean adminCanCancel) {
		this.adminCanCancel = adminCanCancel;
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
	
	public List<SlipsStruc> getSlipsStruc() {
		return slipsStruc;
	}

	public void setSlipsStruc(List<SlipsStruc> slipsStruc) {
		this.slipsStruc = slipsStruc;
	}

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}
}
