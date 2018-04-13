package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName GameManualRecord 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 上午11:29:05 
*  
*/
public class GameManualRecord  extends BaseEntity{
 
	private static final long serialVersionUID = 3271174446435549203L;
	
	private Long lotteryId;
	private Long issueCode;
	private String webIssueCode;
	private String confirmResuld;
	private Integer status;  //0未锁定  1锁定
	private Date saleEndTime;
	
	private Long firstUserId;
	private String firstResuld;
	private Date firstEncodingTime;
	
	private Long sencondUserId;	
	private String sencondResuld;
	private Date sencondEncodingTime;
	
	private Long dealUserId;
	
	private String firstUserName;//级联查询查出
	private String sencondUserName;//级联查询查出
	
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
	public String getConfirmResuld() {
		return confirmResuld;
	}
	public void setConfirmResuld(String confirmResuld) {
		this.confirmResuld = confirmResuld;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getSaleEndTime() {
		return saleEndTime;
	}
	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}
	public Long getFirstUserId() {
		return firstUserId;
	}
	public void setFirstUserId(Long firstUserId) {
		this.firstUserId = firstUserId;
	}
	public String getFirstResuld() {
		return firstResuld;
	}
	public void setFirstResuld(String firstResuld) {
		this.firstResuld = firstResuld;
	}
	public Date getFirstEncodingTime() {
		return firstEncodingTime;
	}
	public void setFirstEncodingTime(Date firstEncodingTime) {
		this.firstEncodingTime = firstEncodingTime;
	}
	public Long getSencondUserId() {
		return sencondUserId;
	}
	public void setSencondUserId(Long sencondUserId) {
		this.sencondUserId = sencondUserId;
	}
	public String getSencondResuld() {
		return sencondResuld;
	}
	public void setSencondResuld(String sencondResuld) {
		this.sencondResuld = sencondResuld;
	}
	public Date getSencondEncodingTime() {
		return sencondEncodingTime;
	}
	public void setSencondEncodingTime(Date sencondEncodingTime) {
		this.sencondEncodingTime = sencondEncodingTime;
	}
	public String getFirstUserName() {
		return firstUserName;
	}
	public void setFirstUserName(String firstUserName) {
		this.firstUserName = firstUserName;
	}
	public String getSencondUserName() {
		return sencondUserName;
	}
	public void setSencondUserName(String sencondUserName) {
		this.sencondUserName = sencondUserName;
	}
	public Long getDealUserId() {
		return dealUserId;
	}
	public void setDealUserId(Long dealUserId) {
		this.dealUserId = dealUserId;
	}
		
}
