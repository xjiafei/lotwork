package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.schedule.dto.UserBankStruc;
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
	private Date appr2Time;
	private Long payBankId;
	private Long manualId;
	private String apProject;
	private String apChannel;
	

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

	//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败
	public enum WithdrawStauts {
		APPLY(0L), APPROVING(1L), APPROVED(2L), REJECT(3L), SUCCESS(4L), PART(6L), FAIL(5L);

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
				"TIMESLIMIT", 4L),ALLCHECK("ALLCHECK",5L);
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

}
