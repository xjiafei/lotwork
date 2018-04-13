/**   
* @Title: GameOrderDownloadStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-21 下午5:29:12 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: GameOrderDownloadStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-21 下午5:29:12 
*  
*/
public class GameOrderDownloadStruc {

	private String lotteryName;
	private Long lotteryId;
	private String orderCode;
	private String webIssueCode;
	private String formatSaleTime;
	private Long totamount;
	private List<BetDownloadStruc> betList;

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getFormatSaleTime() {
		return formatSaleTime;
	}

	public void setFormatSaleTime(String formatSaleTime) {
		this.formatSaleTime = formatSaleTime;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public List<BetDownloadStruc> getBetList() {
		return betList;
	}

	public void setBetList(List<BetDownloadStruc> betList) {
		this.betList = betList;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
}
