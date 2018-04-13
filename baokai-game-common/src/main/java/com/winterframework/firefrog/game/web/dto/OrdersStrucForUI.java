package com.winterframework.firefrog.game.web.dto;

import java.util.List;
import java.util.Set;

/** 
* @ClassName: OrdersStrucForUI 
* @Description: 订单结构For页面 
* @author Alan
* @date 2013-10-21 下午3:01:03 
*  
*/
public class OrdersStrucForUI {

	private Long orderId;
	private String orderCode;
	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private Long issueStatus;
	private String webIssueCode;
	private String account;
	private String saleTime;
	private Long totalAmount;
	private Long totwin;
	private Double winsRatio;
	private Integer statusSign = 0;
	private String status;
	private String numberRecord;
	private String parentType;
	private String channelVersion;
	private String channelId;
	private Long parentid;
	private Integer multiple;
	private Long userid;
    private double totalAmountD;
    private double totwinD;
    private Long planId;
    private String planCode;
    private int cancelModels;
    private Boolean canCancel;
    private Boolean adminCanCancel;
    private String endSaleTime;
    private Long totDiamondWin = 0l;
    private Double diamondMultiple;
    private List<String> numberRecordList;
    private List<String> numberRecordColorList;
    private List<String> betDetail;
    private Set<String> shenXiaoList;
    
    public int getCancelModels() {
		return cancelModels;
	}
	public void setCancelModels(int cancelModels) {
		this.cancelModels = cancelModels;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
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
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTotwin() {
		return totwin;
	}
	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}
	public Double getWinsRatio() {
		return winsRatio;
	}
	public void setWinsRatio(Double winsRatio) {
		this.winsRatio = winsRatio;
	}
	public Integer getStatusSign() {
		return statusSign;
	}
	public void setStatusSign(Integer statusSign) {
		this.statusSign = statusSign;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public String getParentType() {
		return parentType;
	}
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	public String getChannelVersion() {
		return channelVersion;
	}
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}

    public void setTotalAmountD(double totalAmountD) {
        this.totalAmountD = totalAmountD;
    }

    public double getTotalAmountD() {
        return totalAmountD;
    }

    public void setTotwinD(double totwinD) {
        this.totwinD = totwinD;
    }

    public double getTotwinD() {
        return totwinD;
    }
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
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
	public String getEndSaleTime() {
		return endSaleTime;
	}
	public void setEndSaleTime(String endSaleTime) {
		this.endSaleTime = endSaleTime;
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
	public Double getDiamondMultiple() {
		return diamondMultiple;
	}
	public void setDiamondMultiple(Double diamondMultiple) {
		this.diamondMultiple = diamondMultiple;
	}
    
    public List<String> getNumberRecordList() {
		return numberRecordList;
	}
	public void setNumberRecordList(List<String> numberRecordList) {
		this.numberRecordList = numberRecordList;
	}
	public List<String> getNumberRecordColorList() {
		return numberRecordColorList;
	}
	public void setNumberRecordColorList(List<String> numberRecordColorList) {
		this.numberRecordColorList = numberRecordColorList;
	}
	public List<String> getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(List<String> betDetail) {
		this.betDetail = betDetail;
	}
	public Set<String> getShenXiaoList() {
		return shenXiaoList;
	}
	public void setShenXiaoList(Set<String> shenXiaoList) {
		this.shenXiaoList = shenXiaoList;
	}

	
}
