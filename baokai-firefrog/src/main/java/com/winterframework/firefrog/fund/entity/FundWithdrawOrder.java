package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.user.entity.User;

/**
 * 
* @ClassName: FundWithdraw 
* @Description: 提现申请实体
* @author Richard
* @date 2013-6-28 下午3:33:25 
*
 */
public class FundWithdrawOrder extends FundOrder {

	public FundWithdrawOrder(EnumItem item) {
		super(item);
	}

	private static final long serialVersionUID = -6289482247285410623L;
	private Long applyIpAddr;
	/**审核人*/
	private User approver;
	/**审核时间*/
	private Date approverTime;
	/**审核备注**/
	private String auditMemo;
	//卡信息
	private BankCard card;
	//银行信息
	private FundBank bank;
	private String cardStr;
	private UserBankStruc userBankStruc;
	//Mownecum 
	private FundWithdrawMCOrder mc;
	private WithdrawStauts stauts;
	private Long sum;
	private String topAcc;
	private String currApprer;
	private Date apprBeginTime;
	private Long apprBeginStatus;
	private Long isVip;
	private RiskType riskType;
	private String attach;
	private Date currDate;
	private Date appr2BeginTime;
	private String appr2Acct;
	private Date apprTime;
	private Date appr2Time;
	private Long payBankId;
	private Long manualId;
	private String apProject;
	private String apChannel;
	private String cancelAcct;
	private Date cancelTime;
	private Date mcNoticeTime;
	private String applyAccount;
	private Long withdrawMode;
	private String rootSn;
	private String isSeperate;
	private Date operatingTime;
	private Long seperateCount;				 //分拆筆數
	private Long totalDrawAmount;			 //總提現金額(分拆)
	
	//mow API  Respones專用
	private String companyOrderNum;          //平台訂單號  64
	private String mownecumOrderNum; 	     //DP訂單號   64
	private String mowApiStatus;	     	 //狀態               1
	private String amount;	     	 		 //訂單金額       10	
	private String mowDetail;	     	 	 //交易詳情       200	
	private String exactTransactionCharge;	 //實際服務費      200	
	private String md5Key;					 //md5  key   32
	private String mowApiErrorMsg;			 //錯誤訊息  128

	private String nowCheckUser;//目前審核人員
	private Boolean isCheck;//判斷是否該提現單目前審核中
	private Boolean isShowReview;

	//畫面顯示用
	private String withdrawTimeStr1;
	private String withdrawTimeStr2;
	private String withdrawTimeStr3;
	private String withdrawTimeStr4;
	private String withdrawTimeStr5;
	
	public String getIsSeperate() {
		return isSeperate;
	}

	public void setIsSeperate(String isSeperate) {
		this.isSeperate = isSeperate;
	}

	public Boolean getIsShowReview() {
		return isShowReview;
	}

	public void setIsShowReview(Boolean isShowReview) {
		this.isShowReview = isShowReview;
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

	public String getRootSn() {
		return rootSn;
	}

	public void setRootSn(String rootSn) {
		this.rootSn = rootSn;
	}

	public Long getWithdrawMode() {
		return withdrawMode;
	}

	public void setWithdrawMode(Long withdrawMode) {
		this.withdrawMode = withdrawMode;
	}

	public String getApProject() {
		return apProject;
	}

	public void setApProject(String apProject) {
		this.apProject = apProject;
	}

	public String getApChannel() {
		return apChannel;
	}

	public void setApChannel(String apChannel) {
		this.apChannel = apChannel;
	}

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	private Long realWithdrawAmt;
	public Long getRealWithdrawAmt() {
		return realWithdrawAmt;
	}

	public Long getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(Long payBankId) {
		this.payBankId = payBankId;
	}

	public void setRealWithdrawAmt(Long realWithdrawAmt) {
		this.realWithdrawAmt = realWithdrawAmt;
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

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public Long getManualId() {
		return manualId;
	}

	public void setManualId(Long manualId) {
		this.manualId = manualId;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public Date getAppr2BeginTime() {
		return appr2BeginTime;
	}

	public void setAppr2BeginTime(Date appr2BeginTime) {
		this.appr2BeginTime = appr2BeginTime;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}

	public RiskType getRiskType() {
		return riskType;
	}

	public void setRiskType(RiskType riskType) {
		this.riskType = riskType;
	}

	public Date getApprBeginTime() {
		return apprBeginTime;
	}

	public void setApprBeginTime(Date apprBeginTime) {
		this.apprBeginTime = apprBeginTime;
	}

	public Long getApprBeginStatus() {
		return apprBeginStatus;
	}

	public void setApprBeginStatus(Long apprBeginStatus) {
		this.apprBeginStatus = apprBeginStatus;
	}

	//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败、 7已退款、8鎖定中
	public enum WithdrawStauts {
		APPLY(0L), APPROVING(1L), APPROVED(2L), REJECT(3L), SUCCESS(4L), FAIL(5L) ,PART(6L), LOCKING(8L),APPROVEDREFUND(7L);

		public Long _action = 0L;

		WithdrawStauts(Long action) {
			this._action = action;
		}

		public Long getValue() {
			return _action;
		}

		public static WithdrawStauts creatStatus(Long value) {
			for (WithdrawStauts aLight : WithdrawStauts.values()) {
				if (aLight._action == (value)) {
					return aLight;
				}
			}
			return null;
		}

	}

	public enum RiskType {
		NONE("NONE", 0L), IPLIMIT("IPLIMIT", 1L), BLACKLIST("BLACKLIST", 2L), FUNDLIMIT("FUNDLIMIT", 3L), TIMESLIMIT(
				"TIMESLIMIT", 4L),ALLCHECK("ALLCHECK",5L),GRAYLIST("GRAYLIST",6L),GRAYTRANSFER("GRAYTRANSFER",7L),
				GRAYTRANSFERMANY("GRAYTRANSFERMANY",8L);
		private String name;
		private Long index;

		private RiskType(String name, Long index) {
			this.name = name;
			this.index = index;
		}

		public static RiskType getEnum(Long index) {
			for (RiskType c : RiskType.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return NONE;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getIndex() {
			return index;
		}

	}

	private Date applyExpireTime;
	private Long withdrawAmt;
	private String memo;



	public BankCard getCard() {
		return card;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public void setCard(BankCard card) {
		this.card = card;
	}

	public FundBank getBank() {
		return bank;
	}

	public void setBank(FundBank bank) {
		this.bank = bank;
	}

	public Date getApplyExpireTime() {
		return applyExpireTime;
	}

	public void setApplyExpireTime(Date applyExpireTime) {
		this.applyExpireTime = applyExpireTime;
	}

	public Long getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public FundWithdrawMCOrder getMc() {
		return mc;
	}

	public void setMc(FundWithdrawMCOrder mc) {
		this.mc = mc;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public Date getApproverTime() {
		return approverTime;
	}

	public void setApproverTime(Date approverTime) {
		this.approverTime = approverTime;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public Long getApplyIpAddr() {
		return applyIpAddr;
	}

	public void setApplyIpAddr(Long applyIpAddr) {
		this.applyIpAddr = applyIpAddr;
	}

	public WithdrawStauts getStauts() {
		return stauts;
	}

	public void setStauts(WithdrawStauts stauts) {
		this.stauts = stauts;
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

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
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
	
	// MOW API Response 狀態專用
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


	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}


	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
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

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}
	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

}
