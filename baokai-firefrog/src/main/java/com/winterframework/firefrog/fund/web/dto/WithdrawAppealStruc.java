/**   
* @Title: NoticeTaskStruc.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 下午1:23:55 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;

/** 
* @ClassName: NoticeTaskStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 下午1:23:55 
*  
*/
public class WithdrawAppealStruc {
	
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
	
	/*Request起訖日期等資訊*/
	private Long withdrawAmtFrom;    //畫面提現起金額
	private Long withdrawAmtTo;      //畫面提現訖金額	
	private Date fromWithdrawDate;   //畫面提現起日期
	private Date toWithdrawDate;	 //畫面提現訖日期
	private Date fromAppealDate;     //畫面審核起日期
	private Date toAppealDate;		 //畫面審核訖日期
	private Date fromArgueDate;      //畫面審核起日期
	private Date toArgueDate;		 //畫面審核訖日期
	private Long[] statuses;    	 //狀態範圍
	private String account;   	   	 //account	
	
	/*Responce更新狀態成功與否*/
	private String isUpdateState;   //0失敗  1成功
	private String isHaveAppeal;   //Y存在  N不存在
	private Date fromDate;

	private Date toDate;
	private String uploadImages;	//上傳圖片[{name:"sdfsdf.jpg",url:"sdfsdfwerwr.jpg"}]
	private List<FundWithdrawOrder> subDraws; //提現子單資訊
	private String isSeperate; //是否提現拆單
	private String appealTipsResult;
	
	public List<FundWithdrawOrder> getSubDraws() {
		return subDraws;
	}

	public void setSubDraws(List<FundWithdrawOrder> subDraws) {
		this.subDraws = subDraws;
	}

	public String getIsSeperate() {
		return isSeperate;
	}

	public void setIsSeperate(String isSeperate) {
		this.isSeperate = isSeperate;
	}

	public String getAppealTipsResult() {
		return appealTipsResult;
	}

	public void setAppealTipsResult(String appealTipsResult) {
		this.appealTipsResult = appealTipsResult;
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
		
	public Long getWithdrawAmt() {
		return withdrawAmt;
	}
	
	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	
		
	public Long getAppealStatus() {
		return appealStatus;
	}
	
	public void setAppealStatus(Long appealStatus) {
		this.appealStatus = appealStatus;
	}		
	
	public String getAppealMemo() {
		return appealMemo;
	}

	public void setAppealMemo(String appealMemo) {
		this.appealMemo = appealMemo;
	}	

	public Long[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Long[] statuses) {
		this.statuses = statuses;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIsUpdateState() {
		return isUpdateState;
	}

	public void setIsUpdateState(String isUpdateState) {
		this.isUpdateState = isUpdateState;
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

	public Long getWithdrawAmtFrom() {
		return withdrawAmtFrom;
	}

	public void setWithdrawAmtFrom(Long withdrawAmtFrom) {
		this.withdrawAmtFrom = withdrawAmtFrom;
	}

	public Long getWithdrawAmtTo() {
		return withdrawAmtTo;
	}

	public void setWithdrawAmtTo(Long withdrawAmtTo) {
		this.withdrawAmtTo = withdrawAmtTo;
	}

	public Date getFromWithdrawDate() {
		return fromWithdrawDate;
	}

	public void setFromWithdrawDate(Date fromWithdrawDate) {
		this.fromWithdrawDate = fromWithdrawDate;
	}

	public Date getToWithdrawDate() {
		return toWithdrawDate;
	}

	public void setToWithdrawDate(Date toWithdrawDate) {
		this.toWithdrawDate = toWithdrawDate;
	}

	public Date getFromAppealDate() {
		return fromAppealDate;
	}

	public void setFromAppealDate(Date fromAppealDate) {
		this.fromAppealDate = fromAppealDate;
	}

	public Date getToAppealDate() {
		return toAppealDate;
	}

	public void setToAppealDate(Date toAppealDate) {
		this.toAppealDate = toAppealDate;
	}

	public String getAppealTips() {
		return appealTips;
	}

	public void setAppealTips(String appealTips) {
		this.appealTips = appealTips;
	}

	public String getIsHaveAppeal() {
		return isHaveAppeal;
	}

	public void setIsHaveAppeal(String isHaveAppeal) {
		this.isHaveAppeal = isHaveAppeal;
	}

			public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public Date getFromArgueDate() {
		return fromArgueDate;
	}

	public void setFromArgueDate(Date fromArgueDate) {
		this.fromArgueDate = fromArgueDate;
	}

	public Date getToArgueDate() {
		return toArgueDate;
	}

	public void setToArgueDate(Date toArgueDate) {
		this.toArgueDate = toArgueDate;
	}
		
}
