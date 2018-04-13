package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.winterframework.firefrog.common.util.DataConverterUtil;

/**
 * 
* @ClassName: RiskOrderDetailStruc 
* @Description:5.2.31	RISK_ORDER_DETAIL_STRUC（风险方案详情基本结构）CSI031
* @author Richard
* @date 2013-10-12 上午10:08:02 
*
 */
public class RiskOrderDetailStruc implements Serializable {

	private static final long serialVersionUID = -7286361440113496362L;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Long id;
	private Long lotteryid;
	private Long issueCode;
	private Long userId;
	private String orderCode;
	private Long orderId;
	private Long saleTime;
	private String isoSaleTime;
	private Long totamount;
	private Long totwin;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private Integer parentType;
	private Integer channelId;
	private String channelVersion;
	private Integer status;
	private Long countWin;

	private String numberRecord;
	private String apprUser;
	private Long apprTime;
	private String apprTimeStr;
	private String apprMemo;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
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

	public Long getWinsRatio() {
		return winsRatio;
	}

	public void setWinsRatio(Long winsRatio) {
		this.winsRatio = winsRatio;
	}

	public Long getMaxslipWins() {
		return maxslipWins;
	}

	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}

	public Long getSlipWinsratio() {
		return slipWinsratio;
	}

	public void setSlipWinsratio(Long slipWinsratio) {
		this.slipWinsratio = slipWinsratio;
	}

	/**
	 * 
	* @Title: getParentType 
	* @Description: 1:方案  2:追号
	* @return
	 */
	public Integer getParentType() {
		return parentType;
	}

	/**
	 * 
	* @Title: setParentType 
	* @Description: 1:方案  2:追号
	* @param parentType
	 */
	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	/**
	 * 
	* @Title: getChannelId 
	* @Description: 1 WEB 2 IOS 3 IPAD 4 android 5 WAC
	* @return
	 */
	public Integer getChannelId() {
		return channelId;
	}

	/**
	 * 
	* @Title: setChannelId 
	* @Description: 1 WEB 2 IOS 3 IPAD 4 android 5 WAC
	* @param channelId
	 */
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	 * 
	* @Title: getStatus 
	* @Description: 1 待审核 2 审核通过 3 审核不通过
	* @return
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	public Long getCountWin() {
		return countWin;
	}

	public void setCountWin(Long countWin) {
		this.countWin = countWin;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public String getApprUser() {
		return apprUser;
	}

	public void setApprUser(String apprUser) {
		this.apprUser = apprUser;
	}

	public Long getApprTime() {
		return apprTime;
	}

	public void setApprTime(Long apprTime) {
		this.apprTime = apprTime;
	}

	public String getApprMemo() {
		return apprMemo;
	}

	public void setApprMemo(String apprMemo) {
		this.apprMemo = apprMemo;
	}

	public String getIsoSaleTime() {
		return isoSaleTime;
	}

	public void setIsoSaleTime(String isoSaleTime) {
		this.isoSaleTime = isoSaleTime;
	}

	public String getApprTimeStr() {
		if (apprTime != null)
			apprTimeStr = dateFormat.format(DataConverterUtil.convertLong2Date(apprTime));
		return apprTimeStr;
	}

	public void setApprTimeStr(String apprTimeStr) {
		this.apprTimeStr = apprTimeStr;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
