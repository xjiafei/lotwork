/**   
* @Title: FundManualDepositServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-29 下午4:22:09 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpClient;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.IFundManualDepositDao;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundManualOrder.Type;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.RiskType;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.enums.MethodEnum;
import com.winterframework.firefrog.fund.enums.SysFundStatusEnum;
import com.winterframework.firefrog.fund.exception.FundBalanceShortageException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.exception.FundManualDeposRepeat;
import com.winterframework.firefrog.fund.exception.FundPTNoUserException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundManualDepositService;
import com.winterframework.firefrog.fund.service.IFundMowService;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.impl.pt.FundSysResult;
import com.winterframework.firefrog.fund.service.impl.pt.PtSysTransferRequest;
import com.winterframework.firefrog.fund.service.impl.pt.PtcheckFundRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.DepositAuditRequest;
import com.winterframework.firefrog.fund.web.dto.DepositQueryRequest;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: FundManualDepositServiceImpl 
* @Description: 
* @author Page
* @date 2013-7-29 下午4:22:09 
*  
*/
@Service("fundManualDepositService")
@Transactional(rollbackFor = Exception.class)
public class FundManualDepositServiceImpl implements IFundManualDepositService {

	@Resource(name = "fundManualDepositDao")
	protected IFundManualDepositDao fundManualDepositDao;

	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpClient;
	
	@Resource(name = "HttpClientImpl")
	protected IHttpClient httpPtClient;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;

	@PropertyConfig(value = "company_id")
	protected String companyId;

	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfile;

	@Resource(name = "fundDaoImpl")
	protected IFundDao fundDao;

	@Resource(name = "configServiceImpl")
	protected IConfigService configService;

	@Resource(name = "SNUtil")
	protected ISNGenerator snUtil;

	@PropertyConfig(value = "pt.url")
	private String ptURL;
	

	@PropertyConfig(value = "pt.url.systransfer")
	private String ptsystransfer;
	
	@PropertyConfig(value = "pt.url.checktransfer")
	private String ptchecktranfer;
	
	@Resource(name = "RedisClient")
	protected RedisClient redisSerive;

	@Resource(name = "FundManualWithdrawService")
	private IFundMowService fundMowService;
	@Resource(name = "FundManualRGDKService")
	private IFundMowService FundManualRGDKServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(FundManualDepositServiceImpl.class);
	
	@Override
	public void remark(Long id, String remark) throws Exception {
		fundManualDepositDao.updateRemark(id, remark);
	}

	@Override
	public Page<FundManualOrder> query(PageRequest<DepositQueryRequest> pageReq) throws Exception {
		return fundManualDepositDao.queryFundManualDeposit(pageReq);
	}

	protected FundManualOrder preCreate(FundManualOrder deposit) throws Exception {
		deposit.setSn(snUtil.createBusinessSn(deposit.getType().getItem(),
				userProfile.queryUserByName(deposit.getRcvAccount()).getId()));
		deposit.setStatus(FundManualOrder.Status.APPLY.getStatis());
		fundManualDepositDao.saveManualDeposit(deposit);
		return deposit;

	}

	/**
	 * 
	* @Title: auditAward 
	* @Description: 奖金发放审核通过业务处理。
	* @param deposit
	* @throws Exception
	 */
	protected void depositAudit(FundManualOrder deposit) throws Exception {

		// 通知MOW打款。
		fundMowService.apply(deposit);

		// 修改人工资金打款状态为【提现审核通过】
		deposit.setStatus(FundManualOrder.Status.NOTICEMOW.getStatis());

		fundManualDepositDao.updateManualDeposit(deposit);
	}

	public FundManualOrder preAudit(User user, DepositAuditRequest request) throws Exception {
		FundManualOrder deposit = fundManualDepositDao.queryManualDepositById(request.getId());
		deposit.setApprover(user.getUserProfile().getAccount());
		deposit.setApproveTime(new Date());
		deposit.setApprDemo(request.getMemo());
		deposit.setStatus(request.getStatus());
		int count = fundManualDepositDao.updateManualDepositCheckStatus(deposit);
		if(count == 0){
			throw new FundManualDeposRepeat();
		}
		return deposit;
	}

	/**
	* Title: apply
	* Description:
	* @param deposit
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundManualDepositService#apply(com.winterframework.firefrog.fund.entity.FundManualOrder) 
	*/
	@Override
	public void apply(FundManualOrder deposit) throws Exception {
	
		preCreate(deposit);

		if (deposit.getType() == FundManualOrder.Type.RGTX) {
			long amount = deposit.getDepositAmt();
			UserFund userFund = fundDao.getUserFund(deposit.getRcvId());

			if (amount > userFund.getBal()) {
				throw new FundBalanceShortageException();
			}
		}
	}

	/**
	* Title: audit
	* Description:
	* @param deposit
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundManualDepositService#audit(com.winterframework.firefrog.fund.entity.FundManualOrder) 
	*/
	@Override
	public void audit(User user, DepositAuditRequest request) throws Exception  {
		FundManualOrder deposit = preAudit(user, request);
		if (!FundManualOrder.Status.REFUCED.getStatis().equals(deposit.getStatus())) {
			//如果不是拒绝
			
			audit1(deposit);
			
			/*if (deposit.getType() == FundManualOrder.Type.RGTX) {
				{
					fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PROCESSING, deposit.getRcvId(),
							deposit.getDepositAmt(), deposit.getSn(), true,null));
				}

			}*/
		}
	}

	@Resource(name = "userProfileServiceImpl")
	protected IUserProfileService userProfileServiceImpl;

	@Resource(name = "FundManualWithdrawService")
	private IFundMowService fundManualWithdrawService;
	@Resource(name = "FundManualChargeService")
	private IFundMowService fundManualChargeService;
	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundWithdrawService;

	public void audit1(FundManualOrder deposit) throws Exception {

		if (deposit.getType().equals(Type.RGTX)) {
			// 如果是认同体现
			//fundMowWithdrawServiceS.apply(deposit);	
			FundWithdrawOrder withdraw = new FundWithdrawOrder(Type.RGTX.getItem());
			withdraw.setBank(deposit.getUserBank().getBank());
			BaseUser bu = new User();
			bu.setId(deposit.getRcvId());
			bu.setAccount(deposit.getRcvAccount());
			withdraw.setApplyUser(bu);
			withdraw.setApplyTime(new Date());
			withdraw.setSn(deposit.getSn());
			UserBankStruc bs = new UserBankStruc();
			bs.setBankAccount(deposit.getUserBank().getAccountHolder());
			bs.setBankId(deposit.getUserBank().getBank().getId());
			bs.setBankNumber(deposit.getUserBank().getBankCardNo());
			bs.setBranchName(deposit.getUserBank().getSubBranch());
			bs.setCity(deposit.getUserBank().getCity());
			bs.setId(deposit.getUserBank().getId());
			bs.setMcBankId(deposit.getUserBank().getId());
			bs.setProvince(deposit.getUserBank().getProvince());
			withdraw.setUserBankStruc(bs);
			withdraw.setWithdrawAmt(deposit.getDepositAmt());
			withdraw.setRiskType(RiskType.NONE);
			withdraw.setManualId(deposit.getId());
			withdraw.setStauts(WithdrawStauts.APPLY);
			fundWithdrawService.apply(withdraw, 1L, true,false);
		
			deposit.setStatus(FundManualOrder.Status.NOTICEMOW.getStatis());
		} else if (deposit.getType().equals(Type.RGCZ)) {
			//如果是人工充值
			// 通知MOW打款。
			fundManualChargeService.apply(deposit);
			deposit.setStatus(FundManualOrder.Status.NOTICEMOW.getStatis());

		} else if (deposit.getType().equals(Type.RGDK)) {
			//如果是人工打款
			// 通知MOW打款。
			FundManualRGDKServiceImpl.apply(deposit);
			deposit.setStatus(FundManualOrder.Status.NOTICEMOW.getStatis());

		} else if (deposit.getType().equals(Type.RBAP)){
			
			Long rcvId = deposit.getRcvId();
			Long depositAmt = deposit.getDepositAmt();
			String note = deposit.getNote();
			
			//如果是人工派送獎金
			String sn = snUtil.createBusinessSn(FundModel.OT.RBAP.ITEMS.SUCCESS, rcvId);
			FundGameVo fundvo = new FundGameVo(FundModel.OT.RBAP.ITEMS.SUCCESS, rcvId, depositAmt, sn, true, note);
			fundvo.setSn(sn);
			
			log.info("人工派送獎金=sn : "+sn);
			log.info("人工派送獎金=FundModel : "+FundModel.OT.RBAP.ITEMS.SUCCESS, deposit.getRcvId());
			log.info("人工派送獎金=DepositAmt : "+depositAmt);
			log.info("人工派送獎金=Note : "+note);
			
			fundChangeService.action(fundvo);
			
			deposit.setStatus(FundManualOrder.Status.ADDCOIN.getStatis());
			
			log.info("人工派送獎金=Status : "+deposit.getStatus());
			
		} else {
			if (MethodEnum.reduceAmt.equals(deposit.getType().getItem().getMethod())) {
				
				deposit.setStatus(FundManualOrder.Status.REDUCECOIN.getStatis());
			} else {
	
				deposit.setStatus(FundManualOrder.Status.ADDCOIN.getStatis());
			}
			
			dealFund(deposit);
			String sn = snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTP, deposit.getRcvId());
			if (deposit.getType().equals(Type.PGAP)) {
				//當發生系統轉帳時 補上一筆 轉出 到Pt 的帳變 再轉出
				PtSysTransferRequest ptRequest = new  PtSysTransferRequest();
				ptRequest.setAmount(deposit.getDepositAmt());
				ptRequest.setDirect(0);
				ptRequest.setUserID(deposit.getRcvId());
				UserFund fund = fundChangeService.getUserFund(deposit.getRcvId());
				Long balance = fund.getBal();
				ptRequest.setBal(balance);
				ptRequest.setSn(sn);
				ptRequest.setManualSn(deposit.getSn() );
				Response<FundSysResult> response = null;
				try {
					log.info("pt checkfund " + deposit.getSn());
					response = httpPtClient.invokeHttp(ptURL + ptsystransfer, ptRequest, new TypeReference<Response<FundSysResult>>() {
					});
				}catch (java.net.SocketTimeoutException  e)	{
					log.error("pt systransfer error");
					Thread.sleep(1000);
					PtcheckFundRequest ptCheck = new PtcheckFundRequest();
					ptCheck.setSn(sn);
				   response = httpPtClient.invokeHttp(ptURL + ptchecktranfer,
																		ptCheck ,
																		new TypeReference<Response<FundSysResult>>() {
					});
					if (response.getBody().getResult().getSucees() != 1){
						log.error("pt checkfund error");
						throw e;
					}
				}
				int status = response.getBody().getResult().getSucees();
				if(status==SysFundStatusEnum.FAIL_NO_USER.getValue()){
					log.error("pt systransfer error:not pt user");
					throw new FundPTNoUserException(deposit.getRcvAccount());
				}
				FundGameVo fundvo = new FundGameVo(FundModel.TF.TAIX.ITEMS.TFTP, deposit.getRcvId(), deposit.getDepositAmt(), sn, true,
						"转至 子系統 " + response.getBody().getResult().getPtAccount());
			
				fundvo.setSn(sn);
				fundChangeService.action(fundvo);
				deposit.setStatus(FundManualOrder.Status.NOTICEMOW.getStatis());
			}		
		}

		// 修改人工资金打款状态为【提现审核通过】
		fundManualDepositDao.updateManualDeposit(deposit);
	}

	public void dealFund(FundManualOrder deposit) throws FundChangedException {
		FundGameVo gamevo = new FundGameVo(deposit.getType().getItem(), deposit.getRcvId(), deposit
				.getDepositAmt(), deposit.getSn(), true);
		gamevo.setNote(deposit.getNote());
		fundChangeService.action(gamevo);

	}

}
