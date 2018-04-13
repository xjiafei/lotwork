/**   
* @Title: NoticeTask.java 
* @Package NoticeTaskVO 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午9:37:02 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;

/** 
* @ClassName: NoticeTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午9:37:02 
*  
*/
public class WithdrawAppeal {

	/*FUND_WITHDRAW的欄位*/
	private String sn;	
	private Date applyTime;	
	private UserBankStruc userBankStruc;
	
	/*FUND_WITHDRAW_APPEAL的欄位*/
	private Long id;	
	private Long userId;	
	private String appealSn;	
	private Long withdrawAmt;
	private Date withdrawTime;
	private Long vipLvl;
	private String userName;
	private Long appealStatus;
	private String appealAcct;
	private Date appealTime;
	private String bankId;
	private String bankName;
	private String cardNumber;
	private String appealMemo;
	private Date argueTime;
	private String argueAcct;
	private String withdrawSn;
	private Long isAppeal;
	private String appealTips;
	private Boolean isCheck;
	private String nowCheckUser;
	private String appealTipsResult;
	
	public enum APPEAL_STATUS{
		APPEAL_UNCHECK(0L),
		APPEAL_PROCESS(1L),
		APPEAL_SUCESS(2L),
		APPEAL_FAIL(3L),
		APPEAL_REVIEW(4L);
		private Long value;
		
		private APPEAL_STATUS(Long value){
			this.value=value;
		}
		
		public Long getValue(){
			return value;
		}
	}
	/*其他*/
	private String cardStr;
	private String uploadImages;
	private String isSeperate;

	public String getIsSeperate() {
		return isSeperate;
	}

	public void setIsSeperate(String isSeperate) {
		this.isSeperate = isSeperate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Long getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public String getAppealMemo() {
		return appealMemo;
	}

	public void setAppealMemo(String appealMemo) {
		this.appealMemo = appealMemo;
	}

	public Long getAppealStatus() {
		return appealStatus;
	}

	public void setAppealStatus(Long appealStatus) {
		this.appealStatus = appealStatus;
	}
	
	public String getCardStr() {
		if(cardStr==null){
			return DataConverterUtil.convertObject2Json(getUserBankStruc());
		}
		return cardStr;
	}

	public void setCardStr(String cardStr) {
		this.cardStr = cardStr;
	}

	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAppealAcct() {
		return appealAcct;
	}

	public void setAppealAcct(String appealAcct) {
		this.appealAcct = appealAcct;
	}

	public Date getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getArgueTime() {
		return argueTime;
	}

	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
	}

	public String getArgueAcct() {
		return argueAcct;
	}

	public void setArgueAcct(String argueAcct) {
		this.argueAcct = argueAcct;
	}

	public String getWithdrawSn() {
		return withdrawSn;
	}

	public void setWithdrawSn(String withdrawSn) {
		this.withdrawSn = withdrawSn;
	}

	public Long getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(Long isAppeal) {
		this.isAppeal = isAppeal;
	}

	public String getAppealTips() {
		return appealTips;
	}

	public void setAppealTips(String appealTips) {
		this.appealTips = appealTips;
	}

	public String getUploadImages() {
		return uploadImages;
	}

	public void setUploadImages(String uploadImages) {
		this.uploadImages = uploadImages;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getNowCheckUser() {
		return nowCheckUser;
	}

	public void setNowCheckUser(String nowCheckUser) {
		this.nowCheckUser = nowCheckUser;
	}

	public String getAppealTipsResult() {
		return appealTipsResult;
	}

	public void setAppealTipsResult(String appealTipsResult) {
		this.appealTipsResult = appealTipsResult;
	}
	
	
	
}
