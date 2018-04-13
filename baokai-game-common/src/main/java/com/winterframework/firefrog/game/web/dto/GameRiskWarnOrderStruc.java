 package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.entity.ChannelType;


/** 
* @ClassName GameRiskWarnOrderStruc 
* @Description 风控订单结构 
* @author  hugh
* @date 2014年4月10日 下午2:59:29 
*  
*/
public class GameRiskWarnOrderStruc implements Serializable  {
	
	private static final long serialVersionUID = 6307494668453432048L;
	
	//columns START
	private Long id;
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private String orderCode;
	private Long countWin;
	private Long winsRatio;
	private Long maxslipWins;
	private Long slipWinsratio;
	private Long parentType;
	private Long status;
	private Long createTime;
	private Long updateTime;
	private Long orderId;
	private String userid;
	private Long type;
	private String saleTime;
	private Long channelId;
	private Long totamount;
	
	public GameRiskWarnOrderStruc(){
	
	}
	
	public GameRiskWarnOrderStruc(GameWarnOrder order) {
		this.id = order.getId();
		this.lotteryid = order.getLotteryid();
		this.issueCode = order.getIssueCode();
		this.webIssueCode = order.getWebIssueCode();
		this.orderCode = order.getOrderCode();
		this.countWin = order.getCountWin();
		this.winsRatio = order.getWinsRatio();
		this.maxslipWins = order.getMaxslipWins();
		this.slipWinsratio = order.getSlipWinsratio();
		this.parentType = order.getParentType();
		this.status = order.getStatus();
		this.createTime = DataConverterUtil.convertDate2Long(order.getCreateTime());
		this.updateTime = DataConverterUtil.convertDate2Long(order.getUpdateTime());
		this.orderId = order.getOrderId();
		this.userid = order.getUserid()+"";
		this.type = order.getType();
		this.saleTime = DateUtils.format(order.getSaleTime(), DateUtils.DATETIME_FORMAT_PATTERN);
		this.channelId = order.getChannelId();
		this.totamount = order.getTotamount();
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

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getCountWin() {
		return countWin;
	}

	public void setCountWin(Long countWin) {
		this.countWin = countWin;
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

	public Long getParentType() {
		return parentType;
	}

	public void setParentType(Long parentType) {
		this.parentType = parentType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 依據 channelId 取得來源名稱。
	 * @return
	 */
	public String getChannelName() {
		return ChannelType.getName(channelId);
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}