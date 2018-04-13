package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class WithdrawStruc implements Serializable {

	private static final long serialVersionUID = -8792559268734761244L;
	private Long id;
	private String sn;
	private Long withdrawMode;
	private Long applyTime;
	private Long withdrawAmt;
	private UserBankStruc userBankStruc;
	private Long ipAddr;
	private String memo;
	private String mcMemo;
	private Long status;
	private String applyAccount;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date mcNoticeTime;
	private String apprAccount;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date apprTime;
	private String appr2Acct;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date appr2Time;
	private String apprMemo;
	private Long realWithDrawAmt;
	private String currApprer;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date currDate;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprBeginTime;
	private Long apprBeginStatus;
	private Long isVip;
	private Long riskType;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date appr2BeginTime;
	private String attach;
	private String mcSn;
	private String topAcc;
	private String apChannel;
	private String apProject;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date passDate;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date mowRcvDate;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date cancelTime;
	private String cancelAcct;
	private Date operatingTime;
	private String rootSn;					 //提現分拆-主訂單號
	private Long seperateCount;				 //分拆筆數
	private Long totalDrawAmount;			 //總提現金額(分拆)
	private Boolean isShowReview;	 		 //是否秀待複審按鈕
	
	
	//mow API  Respones專用
	private String companyOrderNum;          //平台訂單號  64
	private String mownecumOrderNum; 	     //DP訂單號   64
	private String mowApiStatus;	     	 //狀態               1
	private String amount;	     	 		 //訂單金額       10	
	private String mowDetail;	     	 	 //交易詳情       200	
	private String exactTransactionCharge;	 //實際服務費      200	
	private String md5Key;					 //md5  key   32
	private String mowApiErrorMsg;			 //錯誤訊息  128
	
	private boolean redisResault;            //Redis Lock Resault
	
	private String nowCheckUser;//目前審核人員
	private Boolean isCheck;//判斷是否該提現單目前審核中
	
	
	//畫面顯示用
	private String withdrawTimeStr1;
	private String withdrawTimeStr2;
	private String withdrawTimeStr3;
	private String withdrawTimeStr4;
	private String withdrawTimeStr5;
	

	public Boolean getIsShowReview() {
		return isShowReview;
	}



	public void setIsShowReview(Boolean isShowReview) {
		this.isShowReview = isShowReview;
	}



	public String getRootSn() {
		return rootSn;
	}



	public void setRootSn(String rootSn) {
		this.rootSn = rootSn;
	}



	public Long getSeperateCount() {
		return seperateCount;
	}



	public void setSeperateCount(Long seperateCount) {
		this.seperateCount = seperateCount;
	}



	public Long getTotalDrawAmount() {
		return totalDrawAmount;
	}



	public void setTotalDrawAmount(Long totalDrawAmount) {
		this.totalDrawAmount = totalDrawAmount;
	}



	public Long getWithdrawMode() {
		return withdrawMode;
	}



	public void setWithdrawMode(Long withdrawMode) {
		this.withdrawMode = withdrawMode;
	}



	public String getMcSn() {
		return mcSn;
	}

	

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public String getApChannel() {
		return apChannel;
	}

	public void setApChannel(String apChannel) {
		this.apChannel = apChannel;
	}

	public String getApProject() {
		return apProject;
	}

	public void setApProject(String apProject) {
		this.apProject = apProject;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public Date getMowRcvDate() {
		return mowRcvDate;
	}

	public void setMowRcvDate(Date mowRcvDate) {
		this.mowRcvDate = mowRcvDate;
	}

	public String getMcMemo() {
		return mcMemo;
	}

	public void setMcMemo(String mcMemo) {
		this.mcMemo = mcMemo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Date getApprBeginTime() {
		return apprBeginTime;
	}

	public void setApprBeginTime(Date apprBeginTime) {
		this.apprBeginTime = apprBeginTime;
	}

	public Date getAppr2BeginTime() {
		return appr2BeginTime;
	}

	public void setAppr2BeginTime(Date appr2BeginTime) {
		this.appr2BeginTime = appr2BeginTime;
	}

	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}

	public Long getRiskType() {
		return riskType;
	}

	public void setRiskType(Long riskType) {
		this.riskType = riskType;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public Long getApprBeginStatus() {
		return apprBeginStatus;
	}

	public void setApprBeginStatus(Long apprBeginStatus) {
		this.apprBeginStatus = apprBeginStatus;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public Long getRealWithDrawAmt() {
		return realWithDrawAmt;
	}

	public void setRealWithDrawAmt(Long realWithDrawAmt) {
		this.realWithDrawAmt = realWithDrawAmt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WithdrawStruc() {

	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getAppr2Acct() {
		return appr2Acct;
	}

	public void setAppr2Acct(String appr2Acct) {
		this.appr2Acct = appr2Acct;
	}

	public Date getAppr2Time() {
		return appr2Time;
	}

	public void setAppr2Time(Date appr2Time) {
		this.appr2Time = appr2Time;
	}

	public String getApprMemo() {
		return apprMemo;
	}

	public void setApprMemo(String apprMemo) {
		this.apprMemo = apprMemo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
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

	public Long getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(Long ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String getCancelAcct() {
		return cancelAcct;
	}

	public void setCancelAcct(String cancelAcct) {
		this.cancelAcct = cancelAcct;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	
	//mow API 專用
	public String getCompanyOrderNum() {
		return companyOrderNum;
	}

	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
	}
	
	
	public String getMownecumOrderNum() {
		return mownecumOrderNum;
	}

	public void setMownecumOrderNum(String mownecumOrderNum) {
		this.mownecumOrderNum = mownecumOrderNum;
	}
	public String getMowApiStatus() {
		return mowApiStatus;
	}

	public void setMowApiStatus(String mowApiStatus) {
		this.mowApiStatus = mowApiStatus;
	}
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMowDetail() {
		return mowDetail;
	}

	public void setMowDetail(String mowDetail) {
		this.mowDetail = mowDetail;
	}
	public String getExactTransactionCharge() {
		return exactTransactionCharge;
	}

	public void setExactTransactionCharge(String exactTransactionCharge) {
		this.exactTransactionCharge = exactTransactionCharge;
	}
	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getMowApiErrorMsg() {
		return mowApiErrorMsg;
	}

	public void setMowApiErrorMsg(String mowApiErrorMsg) {
		this.mowApiErrorMsg = mowApiErrorMsg;
	}
	
	public boolean getRedisResault() {
		return redisResault;
	}

	public void setRedisResault(boolean redisResault) {
		this.redisResault = redisResault;
	}



	public String getNowCheckUser() {
		return nowCheckUser;
	}



	public void setNowCheckUser(String nowCheckUser) {
		this.nowCheckUser = nowCheckUser;
	}



	public Boolean getIsCheck() {
		return isCheck;
	}



	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}



	public Date getOperatingTime() {
		return operatingTime;
	}



	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getWithdrawTimeStr1() {
		return withdrawTimeStr1;
	}



	public void setWithdrawTimeStr1(String withdrawTimeStr1) {
		this.withdrawTimeStr1 = withdrawTimeStr1;
	}



	public String getWithdrawTimeStr2() {
		return withdrawTimeStr2;
	}



	public void setWithdrawTimeStr2(String withdrawTimeStr2) {
		this.withdrawTimeStr2 = withdrawTimeStr2;
	}



	public String getWithdrawTimeStr3() {
		return withdrawTimeStr3;
	}



	public void setWithdrawTimeStr3(String withdrawTimeStr3) {
		this.withdrawTimeStr3 = withdrawTimeStr3;
	}



	public String getWithdrawTimeStr4() {
		return withdrawTimeStr4;
	}



	public void setWithdrawTimeStr4(String withdrawTimeStr4) {
		this.withdrawTimeStr4 = withdrawTimeStr4;
	}



	public String getWithdrawTimeStr5() {
		return withdrawTimeStr5;
	}



	public void setWithdrawTimeStr5(String withdrawTimeStr5) {
		this.withdrawTimeStr5 = withdrawTimeStr5;
	}

}
