package com.winterframework.firefrog.fund.service.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDto;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDtoUser;
import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.RiskType;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.enums.FundLogEnum;
import com.winterframework.firefrog.fund.enums.FundLogEnum.LogModel;
import com.winterframework.firefrog.fund.enums.FundLogEnum.WITHDRAW_STATUS;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.enums.SPFundBank.SPBankWithDraw;
import com.winterframework.firefrog.fund.enums.THFundBank.THBank;
import com.winterframework.firefrog.fund.exception.FundBalanceShortageException;
import com.winterframework.firefrog.fund.exception.FundChangeProcessException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.exception.FundWithdrawHighException;
import com.winterframework.firefrog.fund.exception.FundWithdrawLowException;
import com.winterframework.firefrog.fund.exception.FundWithdrawTooMuchException;
import com.winterframework.firefrog.fund.service.IBypassConfigService;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundMowService;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.fund.service.IFundWithdrawLogService;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.IWithdrawRiskIdentifierService;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse.MOW_ORDER_STATUS;
import com.winterframework.firefrog.fund.service.impl.mow.MowWithDraw;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQuery;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.th.ThPayWithDraw;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.fund.util.WithdrawServiceUtil;
import com.winterframework.firefrog.fund.util.sppay.SignUtils;
import com.winterframework.firefrog.fund.web.controller.ConfigUtils;
import com.winterframework.firefrog.fund.web.controller.RedisKey;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawApplyRequest;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Service("fundWithdrawService")
@Transactional(rollbackFor = Exception.class)
public class FundWithdrawServiceImpl implements IFundWithdrawService {
	private static Logger logger = LoggerFactory.getLogger(FundWithdrawServiceImpl.class);

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	private ConfigUtils configUtils;

	@PropertyConfig(value = "thpay_url")
	private String thpayUrl;
	
	@PropertyConfig(value = "thpay_withdraw_query_url")
	private String thpayWithdrawQueryUrl;
	
	@PropertyConfig(value = "thpay_charset")
	private String inputCharset;
	
	@PropertyConfig(value = "thpay_company_id")
	private String merchantCode;
	
	@PropertyConfig(value = "thpay_key")
	private String thpayKey;
	
	@PropertyConfig(value = "thpay_withdraw_url")
	private String thpayWithdrawUrl;
	
	@Resource(name = "withdrawRiskIdentifierService")
	private IWithdrawRiskIdentifierService withdrawRiskIdentifierService;

	@Resource(name = "fundChargeServiceImpl")
	private IFundService<FundOrder> fundService;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@Resource(name="fundWithdrawLogServiceImpl")
	private IFundWithdrawLogService fundWithdrawLogServiceImpl;
	
	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;

	@Resource(name = "fundBankDaoImpl")
	private IFundBankDao fundBankDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@PropertyConfig(value = "withdraw_url")
	private String mcUrl;
	
	@PropertyConfig(value = "mownum_url")
	protected String mowUrl;
	
	@PropertyConfig(value = "company_id")
	private Long company_id;
	
	@PropertyConfig(value = "querywithdral")
	private String querywithdral;

	@Resource(name = "fundDaoImpl")
	private IFundDao fundDao;

	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;

	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;

	@Resource(name = "FundMowWithdrawService")
	private IFundMowService fundMowService;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "rediskey_withdraw_times")
	private String keyWithdrawTimes;

	@Resource(name = "fundMowPayDao")
	private IFundMowPayDao fundMowPayDao;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@PropertyConfig(value = "platform.name")
	private String platformName;
	
	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;
	
	@Resource(name = "bypassConfigServiceImpl")
	private IBypassConfigService bypassConfigServiceImpl;
	@PropertyConfig(value = "sppay_withdraw")
	private String sppayWithdrawUrl;
	@PropertyConfig(value = "sppay_private_key")
	private String sppayPrivateKey;
	@PropertyConfig(value = "sppay_account")
	private String sppayAccount;
	@PropertyConfig(value = "sppay_withdraw_query")
	private String sppayWithdrawQueryUrl;
	
	@Override
	public CountPage<FundWithdrawOrder> queryFundWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {
		return fundWithdrawDao.queryFundWithdraw(pageRequest);
	}  
	
	public CountPage<FundWithdrawOrder> queryFundWithdrawDetail(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		return fundWithdrawDao.queryFundWithdrawDetail(pageRequest);

	} 
	
	public CountPage<FundWithdrawOrder> queryReFundWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		return fundWithdrawDao.queryReFundWithdraw(pageRequest);

	}  
	
	public CountPage<FundWithdrawOrder> queryDetailWithdrawList(PageRequest<QueryFundWithdrawOrderRequest> pageRequest)
			throws Exception {

		return fundWithdrawDao.queryDetailFundWithdraw(pageRequest);

	}  

	public void callMowService(Long id) throws Exception {
		FundWithdrawOrder withDraw = fundWithdrawDao.queryById(id);
		int channel = bypassConfigServiceImpl.checkWithdrawBypass(withDraw.getWithdrawAmt(),withDraw.getApplyAccount(),1l,"");
		logger.info("ThPay channel = "+channel);
		//分流 0:DP 1:通匯
		if(channel == 0 ){
			callMowWithDraw(withDraw,false);
		}else if(channel==8){
			callSpPayWithDraw(withDraw);
		}else{
			callThPayWithDraw(withDraw);
		}
	};
	private Long getCentAmt(Long amt){
		return amt/100;
	}
	private void callSpPayWithDraw(FundWithdrawOrder order) throws Exception {
		//修改提現   渠道參數改成SPPay
		fundWithdrawDao.updateWithdrawMode(8l, order.getSn());
		String resp = "";
		//若在呼叫秒付API時發生錯誤 則新增一筆資料到FUND_MOW_PAY中 狀態()MOW_STATUS=7 TASK暪格3分鐘會做新詢問處理
		try{
			
			String signDataSrc = "CP_NO="+order.getSn()+"&TXNAMT="+getCentAmt(order.getWithdrawAmt())
			+"&OPNBNK="+SPBankWithDraw.mapping(order.getUserBankStruc().getBankId())+"&OPNBNKNAM="+SPBankWithDraw.mappingName(order.getUserBankStruc().getBankId())
			+"&ACTNO="+order.getUserBankStruc().getBankNumber()+"&ACTNAM="+order.getUserBankStruc().getBankAccount()
			+"&ACTIDCARD="+"442345201701011234"+"&ACTMOBILE="+"18807551234";
			
			String SIGN = SignUtils.Signaturer(signDataSrc, sppayPrivateKey);
			String param="MERCNUM="+java.net.URLEncoder.encode(sppayAccount,"UTF-8")+"&TRANDATA="+java.net.URLEncoder.encode(signDataSrc,"UTF-8")+"&SIGN="+java.net.URLEncoder.encode(SIGN,"UTF-8");
			
			logger.info(" params : " + param);
			
			resp = mcwClient.postHttpJson(sppayWithdrawUrl, param);
			logger.info("SpPay withdraw sn ={},result={} :" , new Object[]{order.getSn(),resp});
		}catch(Exception e){
			FundMowPay mowPay = createFundMowPay(order,7l);
			fundMowPayDao.saveMowPay(mowPay);
			fundWithdrawDao.updateNowStatus(order.getId(),WithdrawStauts.APPROVED);
			logger.info("Thpay 無响應 read timeout：" + resp + "寫入一筆FUND_MOW_PAY :" +order.getSn());
			return;
		}
		
		//解密
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap = JsonUtil.fromJson2Map(resp, String.class, String.class);
		if("000000".equals(responseMap.get("RECODE"))){
			FundMowPay mowPay = createFundMowPay(order,8l);
			fundMowPayDao.saveMowPay(mowPay);
		}else{
			throw new RuntimeException("sppay callback exception : " + resp);
		}
        
	}
	public void callMowWithDraw(FundWithdrawOrder order,boolean isTransfer) throws Exception {
		//修改提現   渠道參數改成ThPay
		fundWithdrawDao.updateWithdrawMode(1l, order.getSn());
		
		MowWithDraw map = new MowWithDraw();
		map.setAmount(NumUtil.toMow(order.getWithdrawAmt().longValue()));
		map.setBank_id(order.getUserBankStruc().getBankId());
		map.setCard_num(order.getUserBankStruc().getBankNumber());
		map.setCard_name(order.getUserBankStruc().getBankAccount());
		map.setCompany_order_num(order.getSn());
		map.setCompany_user(MD5Encrypt.encrypt("" + order.getApplyUser().getId()));
		map.setIssue_bank_address(order.getUserBankStruc().getBranchName());
		map.setMemo(order.getMemo());// TODO

		MownecumWithdrawResponseData resp = mcwClient.invokeHttp(mowUrl + mcUrl, map,
				MownecumWithdrawResponseData.class);
//		String msg = "company_order_num already exists!";
		logger.info("withdraw result:" + JsonMapper.nonAlwaysMapper().toJson(resp));
		logger.info("resp.getStatus():" + resp.getStatus());
		if (resp.getStatus() != null && resp.getStatus() == 1) {
			FundMowPay mowPay = new FundMowPay();
			mowPay.setExId(order.getId());
			mowPay.setApplyTime(new Date());
			mowPay.setId(order.getId());
			mowPay.setFfAmount(order.getWithdrawAmt());
			mowPay.setFfBankId(order.getUserBankStruc().getBankId());
			mowPay.setFfCardName(order.getUserBankStruc().getBankAccount());
			mowPay.setFfCardNum(order.getUserBankStruc().getBankNumber());
			mowPay.setFfCompanyUser(map.getCompany_user());
			mowPay.setExSn(resp.getCompany_order_num());
			mowPay.setMownecumOrderNum(resp.getMownecum_order_num());
			if(isTransfer){
				mowPay.setMowStatus(8l);
				fundMowPayDao.updateMowPay(mowPay);
			}else{
				//订单号
				fundMowPayDao.saveMowPay(mowPay);
			}
		} else {
			throw resp;
		}

	}
	
	private void callThPayWithDraw(FundWithdrawOrder order) throws Exception {
		//修改提現   渠道參數改成ThPay
		fundWithdrawDao.updateWithdrawMode(2l, order.getSn());
		//轉換ThPay 要的格式
		Map<String, Object> map = transferTHMap(order);
		String resp = "";
		

		logger.info("-------------------map :" , map.toString());
		resp = mcwClient.invokeHttpXml(thpayUrl + thpayWithdrawUrl, map);
		logger.info("ThPay withdraw sn ={},result={} :" , new Object[]{order.getSn(),resp});
		
		//若"银行卡号所属银行不匹配银行代码" 則直接轉發DP
		if (resp.equals("success")) {
			FundMowPay mowPay = createFundMowPay(order,8l);
			fundMowPayDao.saveMowPay(mowPay);
		}else if(resp.contains("商户余额不足") || resp.contains("银行卡号所属银行不匹配银行代码")
				|| resp.contains("银行卡号所属银行不在系统处理范围内")){
			//余额不足 轉送DP
			fundWithdrawDao.updateWithdrawMode(1l, order.getSn());
			logger.info("结算失败，原因：商户余额不足 或 银行卡号所属银行不匹配银行代码   轉DP  SN = " +order.getSn());
			callMowWithDraw(order,false);
		}else if(resp.equals("")){
			//若在呼叫通匯API時發生錯誤 則新增一筆資料到FUND_MOW_PAY中 狀態()MOW_STATUS=7 TASK暪格3分鐘會做新詢問處理
			FundMowPay mowPay = createFundMowPay(order,7l);
			fundMowPayDao.saveMowPay(mowPay);
			fundWithdrawDao.updateNowStatus(order.getId(),WithdrawStauts.APPROVED);
			logger.info("Thpay 無响應 read timeout：" + resp + "寫入一筆FUND_MOW_PAY :" +order.getSn());
		}else{			
			throw new RuntimeException("thpay callback exception : " + resp);
		}
	}
	
	private FundMowPay createFundMowPay(FundWithdrawOrder order,Long status) throws Exception{
		FundMowPay mowPay = new FundMowPay();
		mowPay.setExId(order.getId());
		mowPay.setApplyTime(new Date());
		mowPay.setId(order.getId());
		mowPay.setFfAmount(order.getWithdrawAmt());
		mowPay.setFfBankId(order.getUserBankStruc().getBankId());
		mowPay.setFfCardName(order.getUserBankStruc().getBankAccount());
		mowPay.setFfCardNum(order.getUserBankStruc().getBankNumber());
		mowPay.setFfCompanyUser(MD5Encrypt.encrypt("" + order.getApplyUser().getId()));
		mowPay.setMowStatus(status);
		//订单号
		mowPay.setExSn(order.getSn());
		//mowPay.setMownecumOrderNum(resp.getMownecum_order_num());
		return mowPay;
	}
	
	private Map<String, Object> transferTHMap(FundWithdrawOrder order) throws Exception{
		//轉換ThPay 要的格式
		ThPayWithDraw thPay = new ThPayWithDraw();
		logger.info("sn : " + order.getSn());
		thPay.setMerchant_order(order.getSn());
		logger.info("bankNumber : " + order.getUserBankStruc().getBankNumber());
		thPay.setBank_card_no(order.getUserBankStruc().getBankNumber());
		logger.info("bankAccount : " + order.getUserBankStruc().getBankAccount());
		thPay.setBank_account(order.getUserBankStruc().getBankAccount());
		logger.info("bankId: " + order.getUserBankStruc().getBankId());
		thPay.setBank_code(THBank.mapping(order.getUserBankStruc().getBankId()));
		logger.info("amount: " + NumUtil.toMow(order.getWithdrawAmt().longValue()));
		thPay.setAmount(NumUtil.toMow(order.getWithdrawAmt().longValue()));
		logger.info("inputCharset: " + inputCharset);
		thPay.setInput_charset(inputCharset);
		logger.info("merchantCode: " + merchantCode);
		thPay.setMerchant_code(merchantCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("input_charset", inputCharset);
		map.put("merchant_code", merchantCode);
		map.put("merchant_order", thPay.getMerchant_order());
		map.put("bank_card_no", thPay.getBank_card_no());
		map.put("bank_account", thPay.getBank_account());
		map.put("bank_code", thPay.getBank_code());
		map.put("amount", thPay.getAmount());
		map.put("sign", MD5Encrypt.encrypt(thPay.createParam(thpayKey)));
		logger.info("sign: " + map.get("sign"));
		return map;
	}
	
	@Override
	public MowQuerywithDrawResponse queryMowWithdrawOrderStatus(String mcSn,String sn) throws Exception{
		//api
		MowQuerywithDraw request = new MowQuerywithDraw();
		request.setCompany_id(company_id);
		request.setMownecum_order_num(mcSn);
		request.setCompany_order_num(sn);
		logger.info("Mownecum_order_num:  " + mcSn);
		logger.info("Company_order_num:  " + sn);
		MowQuerywithDrawResponse resp = mcwClient.invokeHttp(mowUrl + querywithdral, request,MowQuerywithDrawResponse.class);
		return resp;
	}
	
	@Override
	public int queryThWithdrawOrderStatus(String sn) throws Exception{
		ThPayQueryResponse thPayQueryResponse = getThPayQueryResponse(sn);
		int remitStatus = 0;
		if(thPayQueryResponse.getThPayQueryResponseDetail().getIsSuccess().equals("TRUE")){
			//1:系统处理中  2:银行处理中 3:成功  4:失败
			remitStatus = Integer.valueOf(thPayQueryResponse.getThPayQueryResponseDetail().getRemitStatus());
		}else{
			logger.error("通汇获取状态失败");
			throw new Exception("通汇获取状态失败~~");
			
		}
		return remitStatus;
	}
	@Override
	public int queryWithdrawOrderStatus(Integer withdrawMode,String sn) throws Exception{
		int status=0;
		if(withdrawMode==8){
			status=getSpWithdrawOrderStatus(sn);
		}
		return status;
	}
	private int getSpWithdrawOrderStatus(String sn) throws Exception{
		String signDataSrc = "CP_NO="+sn;
		String SIGN = SignUtils.Signaturer(signDataSrc, sppayPrivateKey);
		String param="MERCNUM="+java.net.URLEncoder.encode(sppayAccount,"UTF-8")+"&TRANDATA="+java.net.URLEncoder.encode(signDataSrc,"UTF-8")+"&SIGN="+java.net.URLEncoder.encode(SIGN,"UTF-8");
		
		logger.info(" params : " + param);
		String resp = mcwClient.postHttpJson(sppayWithdrawQueryUrl, param);
		logger.info("SpPay withdraw sn ={},result={} :" , new Object[]{sn,resp});
		int status = 0;
		//解密
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap = JsonUtil.fromJson2Map(resp, String.class, String.class);
		if("000000".equals(responseMap.get("RECODE"))){
			//0 待处理	1 代付成功	2 代付失败
			if(responseMap.get("STLSTS")!=null){
				status=Integer.valueOf(responseMap.get("STLSTS"));
			}		
		}else{
			logger.error("秒付获取状态失败");
			throw new Exception("秒付获取状态失败~~");
			
		}
		return status;
	}
	@Override
	public List<FundWithdrawOrder> seperateDrawApply(User user,WithdrawApplyRequest applyRequest) throws Exception {
		String sn = snUtil.createBusinessSn(FundModel.FD.CWXX.ITEMS.CWXX, user.getId());
		String seperaeCfg = configService.getConfigValueByKey("fund", "drawSeperate");
		//convert提現seperateDrawApplylist
		WithdralDtoUser withDraw = configUtils.getWITHDRAW();
		WithdralDto wd = withDraw.getUser();
		if (applyRequest.getIsVip() != null && applyRequest.getIsVip() > 0) {
			wd = withDraw.getVip();
		}
		List<FundWithdrawOrder> orders = WithdrawServiceUtil.seperateDrawList(user,applyRequest,sn, seperaeCfg,wd);
		boolean isSeperate = orders.size() > 1?true:false;
		//檢查提現相關限制
		checkWithdrawLimit(applyRequest,wd,isSeperate);
		for(FundWithdrawOrder order:orders){
			
			Long id = apply(order, applyRequest.getIsVip(), false,isSeperate);
			FundWithdraw fundWithdraw = fundWithdrawLogServiceImpl.getFundWithdrawById(id);
			//提現單申請成功時,增加審請歷程記錄
			Date now = fundWithdraw.getApplyTime()==null?new Date():fundWithdraw.getApplyTime();
			fundWithdrawLogServiceImpl.saveFundWithLog(fundWithdraw.getSn(), LogModel.WITHDRAW, now, FundLogEnum.WITHDRAW_STATUS.START);
			fundWithdrawLogServiceImpl.saveFundWithLog(fundWithdraw.getSn(), LogModel.WITHDRAW, DateUtils.addSeconds(now, 5), FundLogEnum.WITHDRAW_STATUS.CHECK);
		}
		//4. update redis
		String lefttTimes=changeWithdrawTime(applyRequest.getUserId(), wd, true);
		logger.info("userId="+applyRequest.getUserId()+",withdraw success left times="+lefttTimes);
		return orders;
	}
	
	@Override
	public Long apply(FundWithdrawOrder withdraw, Long isVip, boolean isManual,boolean isSeperate) throws Exception {
		Long id =null;
		if (isManual) {
			//如果是手工，则做两件事，1入库，2 call mowcum
			id = fundWithdrawDao.saveFundWithdraw(withdraw, withdraw.getSn());
			AclUser baseUser = UserTools.getAdmin();
			this.audit(id, baseUser.getAccount(), 2l, null, null);
			callChange(withdraw, null);

		} else {
			
			logger.info("=Paige:apply(): SN:"+withdraw.getSn());
			//1. 申请，产生张变
			callChange(withdraw, null);
			//2. add fund_freeze
			//		FundFreezeLog freezeLog = freezeService.saveFundFreeze(new BigDecimal(withdraw.getWithdrawAmt().longValue()),
			//				withdraw.getApplyTime(), withdraw.getApplyTime(), fund.getId());

			//get risk type
			RiskType riskType = withdrawRiskIdentifierService.queryRiskType(withdraw);
			withdraw.setRiskType(riskType);

			//3. add fund_withdraw
			id = fundWithdrawDao.saveFundWithdraw(withdraw, withdraw.getSn());

			//无风险类型且风险类型配置为部分审核的时候系统自动审核通过且通知到mowne
//			String withDrawCheckStr = configService.getConfigValueByKey("fund", "withdrawCheck");
//			WithDrawCheck withDrawCheck = (WithDrawCheck) DataConverterUtil.convertJson2Object(withDrawCheckStr,
//					WithDrawCheck.class);
			if (riskType.getIndex().equals(RiskType.NONE.getIndex())) {
				AclUser baseUser = UserTools.getAdmin();
				this.audit(id, baseUser.getAccount(), 2l, null, null);
				//callMowService(id);
			}
			
		}
		return id;
	}
	
	private WithdralDto checkWithdrawLimit(WithdrawApplyRequest applyRequest, WithdralDto wd,boolean isSeperate){
		Long lowLimit = wd.getLowLimit();
		long amount = applyRequest.getPreWithdrawAmt().longValue();
		if (!isSeperate && amount < lowLimit) {
			throw new FundWithdrawLowException();
		}

		Long highValue = wd.getUpLimit();
		if (amount > highValue) {
			throw new FundWithdrawHighException();
		}

		UserFund fund = fundService.getUserFund(applyRequest.getUserId());
		if (amount > fund.getBal()) {
			throw new FundBalanceShortageException();
		}
		return wd;
	}

	//修改redid的体现次数
	private String changeWithdrawTime(Long userId, WithdralDto wd, boolean ifAdd) {
		String withdrawCountRedisKey = RedisKey
				.createDateKey(module, keyWithdrawTimes, userId);
		String availWithdrawCount = redisSerive.get(withdrawCountRedisKey);
		logger.info("before withdraw, userId="+userId+",check redis availWithdrawCount="+(availWithdrawCount== null ? "null": availWithdrawCount));
		String leftTimes;
		if (StringUtils.isNotBlank(availWithdrawCount) && availWithdrawCount.length() > 8) {

			String[] _value = availWithdrawCount.split("\\|");

			StringBuilder str = new StringBuilder();
			Long count = Long.parseLong(_value[0]) + (ifAdd ? 1 : -1);

			//带实现
			if (wd.getTime() != -1) {
				if (count > wd.getTime()) {
					throw new FundWithdrawTooMuchException();
				}
			}
			leftTimes=str.append(count).append("|").append(_value[1]).toString();
			redisSerive.set(withdrawCountRedisKey, leftTimes);
			return leftTimes;
		} else {
			leftTimes=1 + "|" + (ifAdd ? 1 : 0);
			redisSerive.set(withdrawCountRedisKey, leftTimes);
			return leftTimes;
		}
	}

	@Override
	public boolean audit2(Long id, String approver, Long status, String memo) throws Exception {
		int num = fundWithdrawDao.updateFundWithdrawAudingInfo2(id, approver, status, memo);
		if(num != 0){
		FundWithdrawOrder entity = fundWithdrawDao.queryById(id);

		if (WithdrawStauts.REJECT.getValue() == status) {
			callChange(entity, null);
			WithdralDtoUser withDraw = configUtils.getWITHDRAW();
			WithdralDto wd = withDraw.getUser();
			changeWithdrawTime(entity.getApprover().getId(), wd, false);
			return true;
		} else if (WithdrawStauts.APPROVED.getValue() == status) {
			//FundWithdrawOrder withdraw = new FundWithdrawOrder(FundModel.FD.CWXX.ITEMS.CWXX_3);
			callMowService(id);
			}
		}else{
			throw new FundChangeProcessException();
		}
		return false;
	}
	
	public boolean refund(Long id, String approver, Long status, String memo,String attach) throws Exception {
		FundWithdrawOrder checkEntity = fundWithdrawDao.queryById(id);
		if (!checkEntity.getStauts().equals(WithdrawStauts.APPROVED)) {
			//如果一開始已經不是處理中狀態的話要中斷----防止重复提交
			throw new FundChangeProcessException();
		}
		
		int num = fundWithdrawDao.refundgo(id, approver, status, memo,attach);
		FundWithdrawOrder entity = fundWithdrawDao.queryById(id);				
		if(num != 0){		
			if (WithdrawStauts.APPROVEDREFUND.getValue() == status) {
				//退款需要还钱
				callChange(entity, memo);			
				WithdralDtoUser withDraw = configUtils.getWITHDRAW();
				WithdralDto wd = withDraw.getUser();
				changeWithdrawTime(entity.getApprover().getId(), wd, false);				
				return true;
			}else{
				throw new FundChangeProcessException();
			}
			
		}
		return false;	
			
	}

	@Override
	public Long getAvailWithdrawTime(Long userId) throws Exception {

		String redisKey = RedisKey.createDateKey(module, keyWithdrawTimes, userId);

		String value = redisSerive.get(redisKey);
		logger.info("init withdraw, userId="+userId+",check redis availWithdrawCount="+(value== null ? "null": value));		
		if (StringUtils.isBlank(value)) {
			initChargeCount(redisKey);
		} else {

			String date = dateFormat.format(new Date());
			if (value.indexOf("|") > 0) {
				String[] _values = value.split("\\|");

				if (date.equals(_values[1])) {
					return Long.valueOf(_values[0]);
				}
			}

			initChargeCount(redisKey);
		}
		return 0L;
	}
	public void sendMsgaudit2 (Long id ) throws Exception {
			FundWithdrawOrder entity = fundWithdrawDao.queryById(id);
			Map<String, String> map = new HashMap<String, String>();
			map.put("operateDate",	DateUtils.format(entity.getApplyTime(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
			msgToMQ.addMessageToMq(entity.getApplyUser(), NoticeTaskEnum.WithdrawRefuse, map);
	}

	public void addChargeCount(Long userId) throws Exception {

		//String value = configService.getConfigValueByKey(MODULE, keyWithdrawTimesLimit);
		//String value = configService.getConfigValueByKey(MODULE, "withdraw");
		//WithDrawClass ws=JsonMapper.nonAlwaysMapper().fromJson(value, WithDrawClass.class);
		Long curr = getAvailWithdrawTime(userId);
		curr = (curr == null) ? 0L : curr;
		String redisKey = module + "_" + keyWithdrawTimes + "_" + userId;
		String value = (curr + 1) + "|" + dateFormat.format(new Date());
		redisSerive.set(redisKey, value);

	}

	private String initChargeCount(String redisKey) throws Exception {

		//String value = configService.getConfigValueByKey(MODULE, keyWithdrawTimesLimit);
		//String value = configService.getConfigValueByKey(MODULE, "withdraw");
		//WithDrawClass ws=JsonMapper.nonAlwaysMapper().fromJson(value, WithDrawClass.class);
		String str = "0|" + dateFormat.format(new Date());

		redisSerive.set(redisKey, str.toString(),172800);
		return str.toString();
	}

	/**'?'.'.＇，；　＇；；＇
	* Title: remark
	* Description:
	* @param id
	* @param memo 
	 * @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundWithdrawService#remark(java.lang.Long, java.lang.String) 
	*/
	@Override
	public void remark(Long id, String memo) throws Exception {
		FundWithdraw entity = new FundWithdraw();
		entity.setId(id);
		entity.setMemo(memo);
		fundWithdrawDao.updateRemark(id, memo);
	}

	@Override
	public boolean audit(Long id, String approver, Long status, String attach, String memo) throws Exception {
		logger.info("audit id : " + id);
		FundWithdrawOrder entity = fundWithdrawDao.queryById(id);
		int num = 0;
		if("Y".equals(entity.getIsSeperate()) && status == 1){
			List<FundWithdrawOrder> subDraws = fundWithdrawDao.queryByRootSn(entity.getRootSn());
			logger.info("subDraws size : " + subDraws.size());
			for(FundWithdrawOrder draw:subDraws){
				int updateCount = fundWithdrawDao.updateFundWithdrawAudingInfo(draw.getId(), approver, status, attach, memo);
				if(updateCount > 0)
					num++;
			}
		}else{
			if (!entity.getStauts().equals(WithdrawStauts.APPLY) && !entity.getStauts().equals(WithdrawStauts.APPROVING)) {
				//如果不是申请状态或者待复审状态的话，那么直接返回true，防止重复提交
				throw new FundChangeProcessException();
			}
			num = fundWithdrawDao.updateFundWithdrawAudingInfo(id, approver, status, attach, memo);
		}
		
		if(num != 0){
			entity = fundWithdrawDao.queryById(id);
			if (WithdrawStauts.REJECT.getValue() == status) {
				//拒绝需要还钱
				callChange(entity, memo);
			
				WithdralDtoUser withDraw = configUtils.getWITHDRAW();
				WithdralDto wd = withDraw.getUser();
				changeWithdrawTime(entity.getApprover().getId(), wd, false);
				
				return true;
			} else {
				//如果是通过的话，复审的时候才体现
				if (status == 2) {
					callMowService(id);
				}
			}
		}else{
			throw new FundChangeProcessException();
		}
		
		return false;
	}

	public void sendMsgaudit (Long id ) throws Exception {
		FundWithdrawOrder entity = fundWithdrawDao.queryById(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", platformName);
		map.put("operateDate",
				DateUtils.format(entity.getApplyTime(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
		msgToMQ.addMessageToMq(entity.getApplyUser(), NoticeTaskEnum.WithdrawRefuse, map);
	}
	
	public void sendMsgauditRefund (Long id ) throws Exception {
		FundWithdrawOrder entity = fundWithdrawDao.queryById(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", platformName);
		map.put("operateDate",
				DateUtils.format(entity.getApplyTime(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
		msgToMQ.addMessageToMq(entity.getApplyUser(), NoticeTaskEnum.WithdrawRefuse, map);
	}
	
	@Override
	public void reduceChargeCount(Long userId) throws Exception {
		Long curr = getAvailWithdrawTime(userId);
		curr = (curr == null) ? 1L : curr;
		String redisKey = module + "_" + keyWithdrawTimes + "_" + userId;
		String value = (curr - 1) + "|" + dateFormat.format(new Date());
		redisSerive.set(redisKey, value);

	}

	public void yuchuli(Long id, String app, Long currStatus) throws Exception {
		fundWithdrawDao.yuchuli(id, app, currStatus);
	}

	/**
	 * 一审结束订单
	 * @param id
	 * @param app
	 * @throws Exception
	 */
	public void yuchuliEnd(Long id, Long currStatus) throws Exception {
		fundWithdrawDao.yuchuliEnd(id, currStatus);
	}
	
	/**
	 * 風險處理訂單處理中退款
	 * @param id
	 * @param app
	 * @throws Exception
	 */
	public void yuchuliEndRefund(Long id, Long currStatus) throws Exception {
		fundWithdrawDao.yuchuliEndRefund(id, currStatus);
	}
	


	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	@Transactional(rollbackFor = Exception.class)
	public void callChange(FundWithdrawOrder fundOrder, String note) throws FundChangedException {
		note = null;
		if (StringUtils.contains(fundOrder.getSn(), "FDCWCF")) {
			
			logger.info("=callChange: if: status: "+fundOrder.getStauts());
			
			//R如果是人工提现
			switch (fundOrder.getStauts()) {

			case APPLY: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PROCESSING, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			case REJECT: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.REJECTED, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			case FAIL: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.FAILED, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			case PART: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PART_RETURN, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt() - fundOrder.getRealWithdrawAmt(), fundOrder.getSn(), true,
						note), new FundGameVo(FundModel.FD.CWXX.ITEMS.PART_REDUCE, fundOrder.getApplyUser().getId(),
						((FundWithdrawOrder) fundOrder).getRealWithdrawAmt(), null, true, note));
				break;
			}
		
			case APPROVEDREFUND: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.REJECTED, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}	
			case SUCCESS: {
				fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.SUCCESS, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			default:
				break;
			}
		} else {
			
			logger.info("=callChange: else: status: "+fundOrder.getStauts());
			
			switch (fundOrder.getStauts()) {

			case APPLY: {
				FundGameVo fundGameVo = new FundGameVo(FundModel.FD.CWXX.ITEMS.CWXX, fundOrder.getApplyUser().getId(),fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note);
				fundGameVo.setSn(fundOrder.getSn());
				fundChangeService.action(fundGameVo);
				break;
			}
			case REJECT: {
				FundGameVo fundVo = new FundGameVo(FundModel.FD.CWXX.ITEMS.REJECTED, fundOrder.getApplyUser().getId(), 
						fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note);
				fundVo.setExCode(fundOrder.getSn());
				
				logger.info("@@@@@@@@@@@@@@@@@@paige:ExCode:"+fundVo.getExCode());
				
				fundChangeService.action(fundVo);
				break;
			}
			case FAIL: {
				fundChangeService.action(new FundGameVo(FundModel.FD.CWXX.ITEMS.FAILED, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			case PART: {
				fundChangeService.action(new FundGameVo(FundModel.FD.CWXX.ITEMS.PART_REFUND, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt() - fundOrder.getRealWithdrawAmt(), fundOrder.getSn(), true,
						note), new FundGameVo(FundModel.FD.CWXX.ITEMS.PART_REDUCE, fundOrder.getApplyUser().getId(),
						((FundWithdrawOrder) fundOrder).getRealWithdrawAmt(), null, true, note));
				break;
			}
			case APPROVEDREFUND: {
				fundChangeService.action(new FundGameVo(FundModel.FD.CWXX.ITEMS.REJECTED, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			case SUCCESS: {
				fundChangeService.action(new FundGameVo(FundModel.FD.CWXX.ITEMS.SUCCESS, fundOrder.getApplyUser()
						.getId(), fundOrder.getWithdrawAmt(), fundOrder.getSn(), true, note));
				break;
			}
			default:
				break;
			}
		}
	}

	@Override
	public FundWithdrawOrder queryFundWithdrawById(Long id) throws Exception {
		return fundWithdrawDao.queryById(id);
	}
	
	@Override
	public List<FundWithdrawOrder> queryByRootSn(String rootSn){
		return fundWithdrawDao.queryByRootSn(rootSn);
	}
	@Override
	public void updateWithdrawalResult(MowQuerywithDrawResponse mowOrder) throws Exception {
		MOW_ORDER_STATUS status = MOW_ORDER_STATUS.mapping(mowOrder.getStatus()
				.intValue());
		logger.info("updateWithdrawalResult status:"+status.name());
		switch (status) {
			case DOING:
			case UNDO:
			case INVALID:
			case NET_ERROR:
				logger.info("no need to do");
				break;
			case SUCCESS:
			case FAIL:
			case PART_SUCCESS:
				doUpdateWithDrawResult(mowOrder);
				break;
			default:
				break;
		}
		
	}
	
	private void doUpdateWithDrawResult(MowQuerywithDrawResponse mowOrder) throws Exception{
		logger.info("doUpdateWithDrawResult start");
		WithdrawConfirmRequest confirmRequest = new WithdrawConfirmRequest();
		confirmRequest.setAmount(new BigDecimal(mowOrder.getAmount()));
		confirmRequest
				.setCompany_order_num(mowOrder.getCompany_order_num());
		confirmRequest.setDetail(mowOrder.getDetail());
		confirmRequest.setError_msg(mowOrder.getError_msg());
		confirmRequest.setExact_transaction_charge(new BigDecimal(mowOrder
				.getExact_transaction_charge()));
		confirmRequest.setKey(mowOrder.getKey());
		confirmRequest.setMownecum_order_num(mowOrder
				.getMownecum_order_num());
		confirmRequest.setStatus(mowOrder.getStatus());
		String str = snUtil.parseBusinessSnDesc(confirmRequest
				.getCompany_order_num());
		paymentCallbackDispatcher.doDispatch(str, confirmRequest);
	}
	
	private ThPayQueryResponse getThPayQueryResponse(String sn) throws JAXBException{
		ThPayQuery thPayQuery = new ThPayQuery();
		thPayQuery.setInputCharset(inputCharset);
		thPayQuery.setMerchantCode(merchantCode);
		thPayQuery.setMerchantOrder(sn);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("input_charset", inputCharset);
		params.put("merchant_code", merchantCode);
		params.put("merchant_order", sn);
		params.put("sign", MD5Encrypt.encrypt(thPayQuery.createParam(thpayKey)));
		logger.info("------------------ThPay post start -------------------");
		String xml = mcwClient.invokeHttpXml(thpayUrl + thpayWithdrawQueryUrl, params);
		logger.info("ThPay Response  = " + xml);
		JAXBContext context = JAXBContext.newInstance(ThPayQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		return (ThPayQueryResponse) unmarshal.unmarshal(new StringReader(xml));
	}
	
	@Override
	public void refundRestProcess(FundWithdrawOrder order) throws Exception{
			Date now = order.getCancelTime()==null?new Date():order.getCancelTime();
			String sn = order.getSn();
			fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, now, WITHDRAW_STATUS.DRAW_RETURN_START1);
			fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, DateUtils.addSeconds(now, 5), WITHDRAW_STATUS.DRAW_RETURN);				
			FundMowPay mowPay = new FundMowPay();
			mowPay.setExSn(sn);
			mowPay.setMowStatus(0L); //退款需修改fundMowPay的狀態為0
			fundMowPayDao.updateMowPay(mowPay);		
	}
	@Override
	public Integer queryUnhandleWithdraw() {
		return fundWithdrawDao.getCountUnhandle();
	}
	

}
