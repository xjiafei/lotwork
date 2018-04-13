package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 开奖结果Struc
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class GameDrawResultStruc implements Serializable {
	/** 彩种 */
	private Long lotteryId;
	/** 奖期 */
	private Long issueCode;
	/** WEB奖期 */
	private String webIssueCode;
	/** 开奖类型 */
	private Long type;
	/** 开奖号码 */
	private String number; //设置方式
	/** 开奖时间*/
	private Date drawTime; //投注属性

	private String name;
	private String order;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		if(null!=order){			
			if (order.equals("da")) {
				return "大";
			} else if (order.equals("xiao")) {
				return "小";
			} else if (order.equals("dan")) {
				return "单";
			} else if (order.equals("shuang")) {
				return "双";
			}
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
