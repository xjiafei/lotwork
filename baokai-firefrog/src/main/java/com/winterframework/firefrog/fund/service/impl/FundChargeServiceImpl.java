/**   
* @Title: FundChargeServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-2 上午11:01:41 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.common.util.ZxingUtil;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao;
import com.winterframework.firefrog.fund.dao.impl.FundManualDepositDaoImpl;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.enums.FundChargeEnum;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IFundManualDepositService;
import com.winterframework.firefrog.fund.service.IFundUserRemarkService;
import com.winterframework.firefrog.fund.service.impl.change.AbstractFundService;
import com.winterframework.firefrog.fund.service.impl.ddb.DdbPayResponse;
import com.winterframework.firefrog.fund.service.impl.din.DinPayResponse;
import com.winterframework.firefrog.fund.service.impl.huayin.HuaYinPayResponse;
import com.winterframework.firefrog.fund.service.impl.jinyang.JinYangPayDetailResponse;
import com.winterframework.firefrog.fund.service.impl.jinyang.JinYangPayResponse;
import com.winterframework.firefrog.fund.service.impl.mow.MCApplyResponse;
import com.winterframework.firefrog.fund.service.impl.mow.MowChargeCancel;
import com.winterframework.firefrog.fund.service.impl.mow.MowDeposit;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.fund.util.hbpay.Base64;
import com.winterframework.firefrog.fund.util.hbpay.LocalUtil;
import com.winterframework.firefrog.fund.util.sppay.SignUtils;
import com.winterframework.firefrog.fund.util.yinbangpay.Base64Local;
import com.winterframework.firefrog.fund.util.yinbangpay.SecurityRSAPay;
import com.winterframework.firefrog.fund.web.controller.FundChargeController;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.ChargeApplyRequest;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: FundChargeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-2 上午11:01:41 
*  
*/
@Service("fundChargeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FundChargeServiceImpl extends AbstractFundService<FundChargeOrder> implements IFundChargeService {

	@Resource(name = "fundChargeDaoImpl")
	private IFundChargeDao fundChargeDao;

	@Resource(name = "fundBankDaoImpl")
	private IFundBankDao fundBankDao;
	
	@Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
	
	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userServcie;
	
	@Resource(name = "fundUserRemarkService")
	private IFundUserRemarkService fundUserRemarkService;

	@Resource(name = "fundChargeExceptionDao")
	private IFundChargeExceptionDao fundChargeExceptionDao;

	@PropertyConfig(value = "deposit_url")
	private String mcUrl;
	
	@PropertyConfig(value = "thpay_deposit_url")
	private String thDepositUrl;

	@PropertyConfig(value = "deposit_cancel_url")
	private String canUrl;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;

	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "limitkey_charge_low")
	private String lowLimt;

	@PropertyConfig(value = "limitkey_charge_high")
	private String highLimit;

	@PropertyConfig(value = "fund_charge_limitTime")
	private String timeLimit;
	
	//@PropertyConfig(value = "game_activity_user_charge")
	//private String game_activity_user_charge;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@PropertyConfig(value = "platform.name")
	private String platformName;
	
	@PropertyConfig(value = "unitConvert")
	private Long unitConvert;
	@PropertyConfig(value = "image.upload.path")
	private String imageUploadPath;
	@PropertyConfig(value = "image.server.upload.url")
	private String imageServerUploadUrl;
	
	private static Logger logger = LoggerFactory.getLogger(FundChargeController.class);

	@Resource(name = "RedisClient")
	private RedisClient redisImpl;
	
	@Autowired
	private BeginMissionService beginMissionService;
	@PropertyConfig(value = "sppay_private_key")
	private String sppayPrivateKey;
	@PropertyConfig(value = "sppay_account")
	private String sppayAccount;
	@PropertyConfig(value = "sppay_qrurl")
	private String sppayQrUrl;
	
	@PropertyConfig(value = "ddbpay_account")
	private String ddbpayAccount;
	@PropertyConfig(value = "ddbpay_private_key")
	private String ddbpayPrivateKey;
	@PropertyConfig(value = "ddbpay_qrurl")
	private String ddbpayQrUrl;
	@PropertyConfig(value = "wftpay_account")
	private String wftpayAccount;
	@PropertyConfig(value = "wftpay_private_key")
	private String wftpayPrivateKey;
	@PropertyConfig(value = "wftpay_qrurl")
	private String wftpayQrUrl;
	
	@PropertyConfig(value = "dinpay_account")
	private String dinpayAccount;
	@PropertyConfig(value = "dinpay_private_key")
	private String dinpayPrivateKey;
	@PropertyConfig(value = "dinpay_qrurl")
	private String dinpayQrUrl;

	@PropertyConfig(value = "huayinpay_account")
	private String huayinpayAccount;
	@PropertyConfig(value = "huayinpay_private_key")
	private String huayinpayPrivateKey;
	@PropertyConfig(value = "huayinpay_qrurl")
	private String huayinpayQrUrl;
	
	@PropertyConfig(value = "yinbangpay_account")
	private String yinbangpayAccount;
	@PropertyConfig(value = "yinbangpay_terminal")
	private String yinbangpayTerminal;
	@PropertyConfig(value = "yinbangpay_private_key")
	private String yinbangpayPrivateKey;
	@PropertyConfig(value = "yinbangplt_public_key")
	private String yinbangpayPlatformKey;
	@PropertyConfig(value = "yinbangpay_qrurl")
	private String yinbangpayQrUrl;
	@PropertyConfig(value = "yinbangpay_bankurl")
	private String yinbangpayBankUrl;
	
	@PropertyConfig(value = "jinyangpay_account")
	private String jinyangpayAccount;
	@PropertyConfig(value = "jinyangpay_key")
	private String jinyangpayKey;
	@PropertyConfig(value = "jinyangpay_qrurl")
	private String jinyangpayQrUrl;
	@PropertyConfig(value = "jinyangpay_bankurl")
	private String jinyangpayBankUrl;

	public String GetPostscript()
	{
		
		while (true){
			String memo = UUID.randomUUID().toString().substring(32);
			for ( int n = 0 ; n < 4 ; n ++)
			 {
				String szran = random(new String[]{
						"2","3","4","5","6",
						"7","8","9","a","b",
						"c","d","e","f","g",
						"h","i","j","k","m",
						"n","p","q","r","s",
						"t","u","v","w","z",
						"x","y",}, 1,",");
				memo = memo.replaceFirst("[o0O1l]", szran);
			 }
			if (redisImpl.get("FUNDPOSTSCRIPT_" + memo) == null){
				redisImpl.set("FUNDPOSTSCRIPT_" + memo, "FUNDPOSTSCRIPT_" + memo , 86400 * 2);
				return memo;
			}
		}
	}
	public static String random(String[] numbers,int count,String split){
		
		int numSize = numbers.length;
		
		Random random = new Random();
		
		StringBuffer sb  = new StringBuffer();
		
		for(int i=0;i<count;i++){
			sb.append(numbers[random.nextInt(numSize)]);
			if(i != (count-1)){
				sb.append(split);
			}
		}
		
		return sb.toString();
	}
	
	public String GetAliMemo(FundChargeOrder order)
	{
		String memo = "";
		String bankNumber = order.getBankNumber();
		String bankAccount = order.getBankAccount();
		String nickName = order.getNickName();
		int at = bankNumber==null?0:bankNumber.indexOf("@");
		int bnl = bankNumber==null?0:bankNumber.length();
		int bal = bankAccount==null?0:bankAccount.length();
		
		String lastWord="";
		if(StringUtils.isNotBlank(nickName)){
			int nil = nickName.length();
			lastWord = nickName.substring(nil-1,nil);
		}else{
			lastWord = bankAccount.substring(bal-1,bal);
		}
		
		//mail
		if(at > -1){
			
			memo = lastWord
					+  bankNumber.substring(0, 3)
					+   bankNumber.substring(at, bnl);
			
		}else{//手機號
			
			memo = lastWord
					+  bankNumber.substring(0, 3)
					+  bankNumber.substring(bnl-4, bnl);
		}
		
		
		return memo;
		
	}
	
	/**
	* Title: apply
	* Description:
	* @param order
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#apply(com.winterframework.firefrog.fund.entity.FundChargeOrder) 
	*/
	@Override
	public FundChargeOrder apply(FundChargeOrder order) throws Exception {
		FundUserRemark remark = fundUserRemarkService.getRemarkByUserId(order.getApplyUser().getId());
		if (order.needMemo()) {
			//只有充值类型为1的时候才设置充值附言
			if (remark != null && remark.getRemark() != null && !remark.getRemark().equals("")) {
				order.setMemo(remark.getRemark());
			} else {
				// 濾掉  0 O（零和字母O），1 和 L
				String memo = GetPostscript ();
				order.setMemo(memo);
			}
		}
		//支付寶附言
		if(order.getDepositMode()==6){
			
			String memo = GetAliMemo (order);
			order.setMemo(memo);
			
		}


		String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
		order.setSn(sn);

		fundChargeDao.insert(order);

		//MOW
		MowDeposit params = createParams(order);
		logger.info(" params : " + params.createParam());
		//使用终端  1电脑端 2手机端 3平板 9其他
		if(order.getPlatfom() == 3){
			params.setTerminal(1);
		}
		if(order.getPlatfom() == 1 || order.getPlatfom() == 2){
			params.setTerminal(2);
		}
		if(order.getPlatfom() >= 4){
			params.setTerminal(9);
		}
			
		logger.info("---------------Terminal = "+params.getTerminal());
		
		MCApplyResponse applayResponse = mcwClient.invokeHttp(mowUrl + mcUrl, params, MCApplyResponse.class);
		logger.info("charge result:" + JsonMapper.nonAlwaysMapper().toJson(applayResponse));

		if (applayResponse.getStatus() != 1)
			throw applayResponse;
		BankCard revCard = new BankCard();
		revCard.setAccountHolder(applayResponse.getBank_acc_name());
		FundBank fb = new FundBank();
		fb.setId(applayResponse.getCollection_bank_id());
		revCard.setBank(fb);
		revCard.setBankCardNo(applayResponse.getBank_card_num());
		revCard.setSubBranch(applayResponse.getIssuing_bank_address());
		FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
		resultOrder.setRevCard(revCard);
		resultOrder.setApplyTime(new Date());
		if(Long.valueOf(2).equals(applayResponse.getMode())){
			resultOrder.setRevEmail(applayResponse.getEmail());
		}
		if (applayResponse.getRemarks() != null) {
			resultOrder.setMemo(applayResponse.getRemarks());
		} else {
			resultOrder.setMemo(params.getMemo());
		}

		FundChargeMCOrder fundOrder = new FundChargeMCOrder();
		fundOrder.setSn(applayResponse.getMownecum_order_num());
		fundOrder.setExpireTime(applayResponse.getMowExpTime());
		resultOrder.setMcOrder(fundOrder);
		resultOrder.setStatus(Status.APPLY);
		fb.setId(applayResponse.getCollection_bank_id());
		resultOrder.setSn(applayResponse.getCompany_order_num());
		if(applayResponse.getBreak_url()!=null)
			resultOrder.setBreakUrl(applayResponse.getBreak_url().trim());
		resultOrder.setMode(applayResponse.getMode());
		fundChargeDao.update(resultOrder);

		return resultOrder;
	}
	
	/**
	* Title: thpayApply
	* Description:
	* @param order
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#apply(com.winterframework.firefrog.fund.entity.FundChargeOrder) 
	*/
	@Override
	public FundChargeOrder thpayApply(FundChargeOrder order) throws Exception {
		String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
		order.setSn(sn);
		order.setStatus(Status.APPLY);
		fundChargeDao.insert(order);
		return order;
	}
	@Override
	public FundChargeOrder ecpssApply(FundChargeOrder order) throws Exception {
		logger.info(" ecpssApply service start ~~~");
		String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
		order.setSn(sn);
		order.setStatus(Status.APPLY);
		fundChargeDao.insert(order);
		logger.info(" ecpssApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder hbpayApply(FundChargeOrder order) throws Exception {
		logger.info(" HBPayApply service start ~~~");
		String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
		order.setSn(sn);
		order.setStatus(Status.APPLY);
		fundChargeDao.insert(order);
		logger.info(" HBPayApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder worthApply(FundChargeOrder order) throws Exception {
		logger.info(" worthApply service start ~~~");
		String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
		order.setSn(sn);
		order.setStatus(Status.APPLY);
		fundChargeDao.insert(order);
		logger.info(" worthApply service end !!!!");
		return order;
	}
	private String generageQrcode(String content){
		String fileName=UUID.randomUUID().toString().replace("-", "")+".png"; //"1493025677058.png";
		String imagePath=imageUploadPath+File.separator+fileName;
		ZxingUtil.enQRCode(content, 400, 400, imagePath);
		return imageServerUploadUrl+fileName;
	}
	private boolean isQr(Long depositMode){
		return depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue() || depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()
				 || depositMode==FundChargeEnum.DepositeMode.QQ.getValue() || depositMode==FundChargeEnum.DepositeMode.UNIPAY.getValue();
	}
	private Long getCentAmt(Long amt){
		return amt/100;
	}
	private String getSpChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "ALIPAY";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "WEIXIN";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "QQ";
		}else if(depositMode==FundChargeEnum.DepositeMode.UNIPAY.getValue()){
			return "UNIPAY";
		}
		return "";
	}
	private String getDdbChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "alipay_scan";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "weixin_scan";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "qq_scan";
		}else if(depositMode==FundChargeEnum.DepositeMode.QUICK.getValue()){
			return "direct_pay";
		}
		return "";
	}
	private String getWftChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "ALIPAYSCAN";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "WEIXIN";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "QQ";
		}
		return "";
	}
	private String getYinBangChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "1006";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "1005";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "1013";
		}else if(depositMode==FundChargeEnum.DepositeMode.QUICK.getValue()){
			return "1003";
		}
		return "";
	}
	private String getJinYangChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "ALIPAY";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "WEIXIN";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "QQPAY";
		}else if(depositMode==FundChargeEnum.DepositeMode.QUICK.getValue()){
			return "1003";
		}
		return "";
	}
	@Override
	public FundChargeOrder spApply(FundChargeOrder order) throws Exception {
		logger.info(" SPPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund/sppay/notify";
			String signDataSrc = "ORDERNO="+order.getSn()+"&TXNAMT="+getCentAmt(order.getPreChargeAmt())+"&CHANNELCODE="+getSpChannelCode(order.getDepositMode())+"&REMARK="+"在线交易"+"&RETURNURL="+notifyUrl;
			
			String SIGN = SignUtils.Signaturer(signDataSrc, sppayPrivateKey);
			String param="MERCNUM="+java.net.URLEncoder.encode(sppayAccount,"UTF-8")+"&TRANDATA="+java.net.URLEncoder.encode(signDataSrc,"UTF-8")+"&SIGN="+java.net.URLEncoder.encode(SIGN,"UTF-8");
			
			logger.info(" params : " + param);
				
			String response = mcwClient.postHttpJson(sppayQrUrl, param);
			logger.info("charge result:" + response);

			Map<String,String> resMap = JsonUtil.fromJson2Map(response, String.class,String.class);
            String recode = resMap.get("RECODE");
            String qrcodeUrl="";
            String orderNo="";
            if(recode.equals("000000")){
            	 qrcodeUrl=resMap.get("ORCODEURL");
            	 orderNo=resMap.get("ORDERNO");
            	 FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
             	resultOrder.setApplyTime(new Date());
             	FundChargeMCOrder fundOrder = new FundChargeMCOrder();
             	resultOrder.setMcOrder(fundOrder);
             	resultOrder.setStatus(Status.APPLY); 
             	resultOrder.setSn(orderNo);
             	resultOrder.setBreakUrl(generageQrcode(qrcodeUrl));
             	fundChargeDao.update(resultOrder);
             	
             	return resultOrder;
            }else{
            	String remsg = resMap.get("REMSG");
            	logger.info(" SPPayApply error: recode="+recode+"  remsg="+remsg);
            }
            
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info(" HBPayApply service end !!!!");
		return order;
	}
	private Map<String, String> createParamsDDB(FundChargeOrder order) throws Exception {
	 	String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund/ddbpay/notify";
		String merchantCode=ddbpayAccount;
		String serviceType=getDdbChannelCode(order.getDepositMode());
		String interfaceVersion="V3.3";
		String clientIp=order.getCustomerIp();
		String signType="RSA-S";
		String orderNo=order.getSn();
		String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
		String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
		String productName="money";
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("notify_url", notifyUrl);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("client_ip", clientIp);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("order_time", orderTime);
		reqMap.put("order_amount", orderAmount);
		reqMap.put("product_name", productName);
		
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("client_ip=").append(clientIp).append("&");	
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("notify_url=").append(notifyUrl).append("&");	
		signSrc.append("order_amount=").append(orderAmount).append("&");
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("order_time=").append(orderTime).append("&");
		signSrc.append("product_name=").append(productName).append("&");
		signSrc.append("service_type=").append(serviceType);		
			
		String signInfo = signSrc.toString();
		String merchantPrivateKey = ddbpayPrivateKey;	
		String sign = new String(LocalUtil.sign(Base64.decode(merchantPrivateKey.getBytes()), signInfo));	// 签名   signInfo签名参数排序，  merchant_private_key商户私钥  				
		reqMap.put("sign", sign);				
		return reqMap;
	}
	private Map<String, String> createParamsDIN(FundChargeOrder order) throws Exception {
		String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "dinpay_trans_domain")+"/fund/dinpay/notify";
		String merchantCode=dinpayAccount;
		String serviceType=getDdbChannelCode(order.getDepositMode());
		String interfaceVersion="V3.1";
		String clientIp=order.getCustomerIp();
		String signType="RSA-S";
		String orderNo=order.getSn();
		String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
		String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
		String productName="money";
		
		/*String notifyUrl = "http://ping.applinzi.com/Notify_Url.jsp";
		//String merchantCode="1111110166";
		String merchantCode=dinpayAccount;
		String serviceType="qq_scan";
		String interfaceVersion="V3.1";
		String clientIp="116.25.124.158";
		String signType="RSA-S";
		String orderNo=new Date().getTime()+"";
		String orderTime="2017-08-18 11:25:23";
		String orderAmount="0.01";
		String productName="iPhone";*/
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("notify_url", notifyUrl);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("client_ip", clientIp);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("order_time", orderTime);
		reqMap.put("order_amount", orderAmount);
		reqMap.put("product_name", productName);
		
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("client_ip=").append(clientIp).append("&");	
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("notify_url=").append(notifyUrl).append("&");	
		signSrc.append("order_amount=").append(orderAmount).append("&");
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("order_time=").append(orderTime).append("&");
		signSrc.append("product_name=").append(productName).append("&");
		signSrc.append("service_type=").append(serviceType);		
			
		String signInfo = signSrc.toString();
		String merchantPrivateKey = dinpayPrivateKey;	
		String sign = new String(LocalUtil.sign(Base64.decode(merchantPrivateKey.getBytes()), signInfo));	// 签名   signInfo签名参数排序，  merchant_private_key商户私钥  				
		reqMap.put("sign", sign);				
		return reqMap;
	}
	
	private Map<String, String> createParamsHUAYIN(FundChargeOrder order) throws Exception {
		String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/huayinpay/notify";
		String merchantCode=huayinpayAccount;
		String serviceType=getDdbChannelCode(order.getDepositMode());
		String interfaceVersion="V3.1";
		String clientIp=order.getCustomerIp();
		String signType="RSA-S";
		String orderNo=order.getSn();
		String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
		String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
		String productName="money";
		
		/*String notifyUrl = "http://ping.applinzi.com/Notify_Url.jsp";
		//String merchantCode="1111110166";
		String merchantCode=dinpayAccount;
		String serviceType="qq_scan";
		String interfaceVersion="V3.1";
		String clientIp="116.25.124.158";
		String signType="RSA-S";
		String orderNo=new Date().getTime()+"";
		String orderTime="2017-08-18 11:25:23";
		String orderAmount="0.01";
		String productName="iPhone";*/
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("notify_url", notifyUrl);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("client_ip", clientIp);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("order_time", orderTime);
		reqMap.put("order_amount", orderAmount);
		reqMap.put("product_name", productName);
		
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("client_ip=").append(clientIp).append("&");	
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("notify_url=").append(notifyUrl).append("&");	
		signSrc.append("order_amount=").append(orderAmount).append("&");
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("order_time=").append(orderTime).append("&");
		signSrc.append("product_name=").append(productName).append("&");
		signSrc.append("service_type=").append(serviceType);		
			
		String signInfo = signSrc.toString();
		String merchantPrivateKey = huayinpayPrivateKey;	
		String sign = new String(LocalUtil.sign(Base64.decode(merchantPrivateKey.getBytes()), signInfo));	// 签名   signInfo签名参数排序，  merchant_private_key商户私钥  				
		reqMap.put("sign", sign);				
		return reqMap;
	}
	private Map<String, String> createParamsYINBANG(FundChargeOrder order) throws Exception {
		String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/yinbangpay/notify";
		
		String merId=yinbangpayAccount;
		String version="1.0.9";
		String terId=yinbangpayTerminal;
		String orderName="money";
		String businessOrdid=order.getSn();
		String tradeMoney=order.getPreChargeAmt()/100+"";
		String payType=getYinBangChannelCode(order.getDepositMode());
		String asynURL=notifyUrl;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessOrdid", businessOrdid);
		map.put("orderName", orderName);
		map.put("merId", merId);
		map.put("terId", terId);
		map.put("tradeMoney", tradeMoney);
		map.put("payType", payType); // 1000默认支持所有支付方式
		map.put("asynURL", asynURL);

		String jsonParam = JsonUtil.toJson(map);
		
		//服务器公钥加密
		byte by[] = SecurityRSAPay.encryptByPublicKey(jsonParam.getBytes("UTF-8"), Base64Local.decode(yinbangpayPlatformKey));
		String encParam = Base64Local.encodeToString(by, true);
		
		//商户自己的私钥签名
		byte signBy[] = SecurityRSAPay.sign(by, Base64Local.decode(yinbangpayPrivateKey));
		String sign = Base64Local.encodeToString(signBy, true);
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("encParam", encParam);
		reqMap.put("merId", merId);
		reqMap.put("version", version);
		reqMap.put("sign", sign);
		return reqMap;
	}
	private Map<String, String> createParamsJINYANG(FundChargeOrder order) throws Exception {
		String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/jinyangpay/notify";
		
		String merId=jinyangpayAccount;
		String payType=getJinYangChannelCode(order.getDepositMode());
		String paymoney=BigDecimal.valueOf(order.getPreChargeAmt()*1.0/10000).stripTrailingZeros().toPlainString();
		String orderno=order.getSn();
		String asynURL=notifyUrl;
		String version="v2.8";
		String signType="1";	//MD5
		String isshow="0";//是否显示收银台
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_mchtid", merId);
		map.put("p2_paytype", payType);
		map.put("p3_paymoney", paymoney);
		map.put("p4_orderno", orderno);
		map.put("p5_callbackurl", asynURL);
		map.put("p7_version", version); // 1000默认支持所有支付方式
		map.put("p8_signtype", signType);
		map.put("p11_isshow", isshow);
		
		StringBuffer sb = new StringBuffer();
		sb.append("p1_mchtid").append("=").append(merId).append("&")
		.append("p2_paytype").append("=").append(payType).append("&")
		.append("p3_paymoney").append("=").append(paymoney).append("&")
		.append("p4_orderno").append("=").append(orderno).append("&")
		.append("p5_callbackurl").append("=").append(asynURL).append("&")
		.append("p6_notifyurl").append("=").append("").append("&")
		.append("p7_version").append("=").append(version).append("&")
		.append("p8_signtype").append("=").append(signType).append("&")
		.append("p9_attach").append("=").append("").append("&")
		.append("p10_appname").append("=").append("").append("&")
		.append("p11_isshow").append("=").append(isshow).append("&")
		.append("p12_orderip").append("=").append("")
		
        .append(jinyangpayKey);
		map.put("sign", MD5Encrypt.encrypt(sb.toString()));
		return map;
	}
	private Map<String, Object> createParamsWFT(FundChargeOrder order) throws Exception {
		String version="3.0";
		String method="Rx.online.pay";
		String partner=wftpayAccount;
		String banktype=getWftChannelCode(order.getDepositMode());
		String paymoney=NumUtil.toMow(order.getPreChargeAmt())+"";
		String ordernumber=order.getSn();
		String callbackurl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund/wftpay/notify";
		//String hrefbackurl="money";
		String isshow="0";
		
		StringBuffer mainStr= new StringBuffer();
		mainStr.append("partner=").append(partner).append("&");	
		mainStr.append("banktype=").append(banktype).append("&");
		mainStr.append("paymoney=").append(paymoney).append("&");				
		mainStr.append("ordernumber=").append(ordernumber).append("&");	
		mainStr.append("callbackurl=").append(callbackurl);
		
		
		StringBuffer signStr= new StringBuffer();
		signStr.append(mainStr).append(wftpayPrivateKey);
		String sign=MD5Encrypt.encrypt(signStr.toString());
		
		StringBuffer paramStr= new StringBuffer();
		paramStr.append("version=").append(version).append("&");
		paramStr.append("method=").append(method).append("&");
		paramStr.append(mainStr).append("&");
		paramStr.append("isshow=").append(isshow).append("&");		
		paramStr.append("sign=").append(sign);
			
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("version", version);
		reqMap.put("method", method);
		reqMap.put("partner", partner);
		reqMap.put("banktype", banktype);
		reqMap.put("paymoney", paymoney);
		reqMap.put("ordernumber", ordernumber);
		reqMap.put("callbackurl", callbackurl);
		reqMap.put("isshow", isshow);
		reqMap.put("sign", sign);
		
		//return paramStr.toString();
		return reqMap;
	}
	  
	@Override
	public FundChargeOrder ddbApply(FundChargeOrder order) throws Exception {
		logger.info(" DDBPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String, String> dataMap =createParamsDDB(order);
			logger.info(" params : " + JsonUtil.toJson(dataMap));
				
			String response = mcwClient.postHttpForm(ddbpayQrUrl, dataMap,"UTF-8");
			logger.info("charge result:" + response);
			JAXBContext context = JAXBContext.newInstance(DdbPayResponse.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			DdbPayResponse payRes= (DdbPayResponse)unmarshal.unmarshal(new StringReader(response));
			if(payRes!=null && payRes.getDdbPayResponseDetail()!=null && payRes.getDdbPayResponseDetail().getRespCode()!=null
					&& payRes.getDdbPayResponseDetail().getRespCode().equals("SUCCESS")){
				logger.info("ddb pay request success.order="+order.getSn());
				//String signature = payRes.getDdbPayResponseDetail().getSign();
				//验签
	           // boolean vfy = LocalUtil.verifySignature(Base64.decode(hbpayPublicKey.getBytes()), signature, msgData.getBytes("UTF-8"));
				if(payRes.getDdbPayResponseDetail().getResultCode().equals("0")){
					FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
					resultOrder.setApplyTime(new Date());
					FundChargeMCOrder fundOrder = new FundChargeMCOrder();
					resultOrder.setMcOrder(fundOrder);
					resultOrder.setStatus(Status.APPLY); 
					resultOrder.setSn(payRes.getDdbPayResponseDetail().getOrderNo());
					resultOrder.setBreakUrl(generageQrcode(payRes.getDdbPayResponseDetail().getQrcode()));
					fundChargeDao.update(resultOrder);
					return resultOrder;
				}else{
					logger.error("ddb pay request failed.order="+order.getSn()+ " result desc:"+payRes.getDdbPayResponseDetail().getResultDesc());
				}
			}else{
				logger.error("ddb pay request failed.order="+order.getSn());
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info(" DDBPayApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder dinApply(FundChargeOrder order) throws Exception {
		logger.info(" DINPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String, String> dataMap =createParamsDIN(order);
			logger.info(" params : " + JsonUtil.toJson(dataMap));
				
			String response = mcwClient.postHttpForm(dinpayQrUrl, dataMap,"UTF-8");
			logger.info("charge result:" + response);
			JAXBContext context = JAXBContext.newInstance(DinPayResponse.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			DinPayResponse payRes= (DinPayResponse)unmarshal.unmarshal(new StringReader(response));
			if(payRes!=null && payRes.getDinPayResponseDetail()!=null && payRes.getDinPayResponseDetail().getRespCode()!=null
					&& payRes.getDinPayResponseDetail().getRespCode().equals("SUCCESS")){
				logger.info("din pay request success.order="+order.getSn());
				//String signature = payRes.getDdbPayResponseDetail().getSign();
				//验签
	           // boolean vfy = LocalUtil.verifySignature(Base64.decode(hbpayPublicKey.getBytes()), signature, msgData.getBytes("UTF-8"));
				if(payRes.getDinPayResponseDetail().getResultCode().equals("0")){
					FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
					resultOrder.setApplyTime(new Date());
					FundChargeMCOrder fundOrder = new FundChargeMCOrder();
					resultOrder.setMcOrder(fundOrder);
					resultOrder.setStatus(Status.APPLY); 
					resultOrder.setSn(payRes.getDinPayResponseDetail().getOrderNo());
					resultOrder.setBreakUrl(generageQrcode(URLDecoder.decode(payRes.getDinPayResponseDetail().getQrcode(),"UTF-8")));
					fundChargeDao.update(resultOrder);
					return resultOrder;
				}else{
					logger.error("din pay request failed.order="+order.getSn()+ " result desc:"+payRes.getDinPayResponseDetail().getResultDesc());
				}
			}else{
				logger.error("din pay request failed.order="+order.getSn());
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info(" DINPayApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder huayinApply(FundChargeOrder order) throws Exception {
		logger.info(" HUAYINPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String, String> dataMap =createParamsHUAYIN(order);
			logger.info(" params : " + JsonUtil.toJson(dataMap));
				
			String response = mcwClient.postHttpForm(huayinpayQrUrl, dataMap,"UTF-8");
			logger.info("charge result:" + response);
			JAXBContext context = JAXBContext.newInstance(HuaYinPayResponse.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			HuaYinPayResponse payRes= (HuaYinPayResponse)unmarshal.unmarshal(new StringReader(response));
			if(payRes!=null && payRes.getHuaYinPayResponseDetail()!=null && payRes.getHuaYinPayResponseDetail().getRespCode()!=null
					&& payRes.getHuaYinPayResponseDetail().getRespCode().equals("SUCCESS")){
				logger.info("huayin pay request success.order="+order.getSn());
				//String signature = payRes.getDdbPayResponseDetail().getSign();
				//验签
	           // boolean vfy = LocalUtil.verifySignature(Base64.decode(hbpayPublicKey.getBytes()), signature, msgData.getBytes("UTF-8"));
				if(payRes.getHuaYinPayResponseDetail().getResultCode().equals("0")){
					FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
					resultOrder.setApplyTime(new Date());
					FundChargeMCOrder fundOrder = new FundChargeMCOrder();
					resultOrder.setMcOrder(fundOrder);
					resultOrder.setStatus(Status.APPLY); 
					resultOrder.setSn(payRes.getHuaYinPayResponseDetail().getOrderNo());
					resultOrder.setBreakUrl(generageQrcode(URLDecoder.decode(payRes.getHuaYinPayResponseDetail().getQrcode(),"UTF-8")));
					fundChargeDao.update(resultOrder);
					return resultOrder;
				}else{
					logger.error("huayin pay request failed.order="+order.getSn()+ " result desc:"+payRes.getHuaYinPayResponseDetail().getResultDesc());
				}
			}else{
				logger.error("huayin pay request failed.order="+order.getSn());
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info(" HUAYINPayApply service end !!!!");
		return order;
	}
	
	@Override
	public FundChargeOrder yinbangApply(FundChargeOrder order) throws Exception {
		logger.info(" YINBANGPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String, String> dataMap =createParamsYINBANG(order);
			logger.info(" params : " + JsonUtil.toJson(dataMap));
			String response = mcwClient.postHttpForm(yinbangpayQrUrl, dataMap,"UTF-8");
			logger.info("charge result:" + response);
			
			Map<String,String> resMap = JsonUtil.fromJson2Map(response, String.class,String.class);
			
			if (!StringUtils.isEmpty(resMap.get("encParam")) && !StringUtils.isEmpty(resMap.get("sign"))) {
				if(SecurityRSAPay.verify(Base64Local.decode(resMap.get("encParam")),Base64Local.decode(yinbangpayPlatformKey),Base64Local.decode(resMap.get("sign")))){
					// 商户自己私钥解密
					String data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(resMap.get("encParam")),Base64Local.decode(yinbangpayPrivateKey)), "utf-8");
					Map<String,String> resultMap = JsonUtil.fromJson2Map(data, String.class,String.class);
					if(resultMap.get("respCode")!=null && resultMap.get("respCode").equals("1000")){
						logger.info("yinbang pay request success.order="+order.getSn());
						
						
						FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
						resultOrder.setApplyTime(new Date());
						FundChargeMCOrder fundOrder = new FundChargeMCOrder();
						fundOrder.setSn(resultMap.get("payOrderId"));
						resultOrder.setMcOrder(fundOrder);
						resultOrder.setStatus(Status.APPLY); 
						resultOrder.setSn(resultMap.get("orderId"));
						resultOrder.setBreakUrl(generageQrcode(URLDecoder.decode(resultMap.get("code_url"),"UTF-8")));
						fundChargeDao.update(resultOrder);
						return resultOrder;
					}else{
						logger.error("yinbang pay request failed.order="+order.getSn()+" respCode="+resultMap.get("respCode")+" respDesc="+resultMap.get("respDesc"));
					}
					
				}else{
					logger.error("yinbang pay verify failed.order="+order.getSn()+" encParam="+resMap.get("encParam")+" sign="+resMap.get("sign"));
				}
				
			}else{
				logger.error("yinbang pay params invalid.order="+order.getSn()+" encParam="+resMap.get("encParam")+" sign="+resMap.get("sign"));
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info("YINBANGPayApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder jinyangApply(FundChargeOrder order) throws Exception {
		logger.info(" JINYANGPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String, String> dataMap =createParamsJINYANG(order);
			logger.info(" params : " + JsonUtil.toJson(dataMap));
			String response = mcwClient.postHttpForm(jinyangpayQrUrl, dataMap,"UTF-8");
			logger.info("charge result:" + response);
			
			JinYangPayResponse res = JsonUtil.fromJson(response, JinYangPayResponse.class);
			
			if (!StringUtils.isEmpty(res.getRspCode())) {
				String rspCode=res.getRspCode();
				String rspMsg=res.getRspMsg();
				if(rspCode.equals("1")){
					JinYangPayDetailResponse data=res.getData();
					if(data!=null){
						String r1_mchtid=data.getR1_mchtid();
						String r2_systemorderno=data.getR2_systemorderno();
						String r3_orderno=data.getR3_orderno();
						String r4_amount=BigDecimal.valueOf(Double.valueOf(data.getR4_amount())).stripTrailingZeros().toPlainString();
						String r5_version=data.getR5_version();
						String r6_qrcode=data.getR6_qrcode();
						String r7_paytype=data.getR7_paytype();
						String sign=data.getSign();
						StringBuffer sb=new StringBuffer();
						sb.append("r1_mchtid=").append(r1_mchtid)
						.append("&").append("r2_systemorderno=").append(r2_systemorderno)
						.append("&").append("r3_orderno=").append(r3_orderno)
						.append("&").append("r4_amount=").append(r4_amount)
						.append("&").append("r5_version=").append(r5_version)
						.append("&").append("r6_qrcode=").append(r6_qrcode)
						.append("&").append("r7_paytype=").append(r7_paytype)
						.append(jinyangpayKey);
						
						String sign2=MD5Encrypt.encrypt(sb.toString());
						if(!StringUtils.isEmpty(sign) && sign.equals(sign2)){
							logger.info("jinyang pay request success.order="+order.getSn());
							
							FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
							resultOrder.setApplyTime(new Date());
							FundChargeMCOrder fundOrder = new FundChargeMCOrder();
							fundOrder.setSn(r2_systemorderno);
							resultOrder.setMcOrder(fundOrder);
							resultOrder.setStatus(Status.APPLY); 
							resultOrder.setSn(r3_orderno);
							resultOrder.setBreakUrl(generageQrcode(URLDecoder.decode(r6_qrcode,"UTF-8")));
							fundChargeDao.update(resultOrder);
							return resultOrder;
						}else{
							logger.error("jinyang pay verify failed.order="+order.getSn()+" sign="+sign+" sign2="+sign2+" params="+sb.toString());
						}
					}else{
						logger.error("jinyang pay request failed.order="+order.getSn()+" data="+data);
					}
				}else{
					logger.error("jinyang pay request invalid.order="+order.getSn()+" rspCode="+rspCode+" rspMsg="+rspMsg);
				}
			}else{
				logger.error("jinyang pay request invalid.order="+order.getSn()+" res="+response);
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info("JINYANGPayApply service end !!!!");
		return order;
	}
	@Override
	public FundChargeOrder wftApply(FundChargeOrder order) throws Exception {
		logger.info(" WFTPayApply service start ~~~");
		if(isQr(order.getDepositMode())){
			String sn =snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			fundChargeDao.insert(order);

			Map<String,Object> paramMap =createParamsWFT(order);
			logger.info(" params : " + paramMap);
				
			String response = mcwClient.invokeHttpGet(wftpayQrUrl, paramMap);
			logger.info("charge result:" + response);
			if(response.indexOf("data=")>=0){
				response= URLDecoder.decode(response,"UTF-8");
				String qrcode=response.substring(response.indexOf("data=")+5);
				FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
				resultOrder.setApplyTime(new Date());
				FundChargeMCOrder fundOrder = new FundChargeMCOrder();
				resultOrder.setMcOrder(fundOrder);
				resultOrder.setStatus(Status.APPLY); 
				resultOrder.setSn(sn);
				resultOrder.setBreakUrl(generageQrcode(qrcode));
				fundChargeDao.update(resultOrder);
				return resultOrder;
			}else{
				logger.error("wft pay request failed.order="+order.getSn()+ " result desc:"+response);
			}
		}else{
			String sn = snUtil.createBusinessSn(FundModel.FD.AUTO.ITEMS.AUTO, order.getApplyUser().getId());
			order.setSn(sn);
			order.setStatus(Status.APPLY);
			fundChargeDao.insert(order);
		}
		logger.info(" WFTPayApply service end !!!!");
		return order;
	}
	public FundChargeOrder queryById(String orderNum) throws Exception {
		return fundChargeDao.queryByOrderNum(orderNum);
	};

	public void cancelMowOrder(String orderNum, Status status) throws Exception {
		FundChargeOrder order = this.queryById(orderNum);
		MowChargeCancel params = new MowChargeCancel();
		params.setMownecum_order_num(order.getMcOrder().getSn());
		params.setCompany_order_num(order.getSn());
		//MCApplyResponse applayResponse = mcwClient.invokeHttp(mowUrl + canUrl, params, MCApplyResponse.class);
		//applayResponse.setCompany_order_num(applayResponse.getCompany_order_num());
		//FundChargeOrder resultOrder = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
		//resultOrder.setSn(orderNum);
		//resultOrder.setStatus(status);
		this.cancelOrder(order.getId(), status);
		this.cancelManalOrde(order.getSn());

		//fundChargeDao.update(resultOrder);
	};
	
	private MowDeposit createParams(FundChargeOrder order) {
		MowDeposit params = new MowDeposit();
		params.setBank_id(order.getPayBank().getId());
		params.setAmount(NumUtil.toMow(order.getPreChargeAmt()));
		params.setCompany_order_num(order.getSn());
		params.setNote(order.getMemo());
		params.setMemo(order.getMemo());
		if (order.needMemo())
			if (order.getMemo() == null) {
				params.setMemo(UUID.randomUUID().toString().substring(32));
				params.setNote(UUID.randomUUID().toString().substring(32));
			} else {
				params.setMemo(order.getMemo());
				params.setNote(order.getMemo());
			}

		params.setNote_model(1);
		Integer depositMode = 1;
		if (order.getDepositMode() != null && (order.getDepositMode() == 2 || order.getDepositMode() == 5 || order.getDepositMode() == 7 || order.getDepositMode() == 8)) {
			depositMode = 2;
		}
		if (order.getDepositMode() != null && (order.getDepositMode() == 6)) {
			depositMode = 3;
		}
		params.setDeposit_mode(depositMode);
		params.setCompany_user(MD5Encrypt.encrypt("" + order.getApplyUser().getId()));
		params.setWeb_url("www.baidu.com");
		return params;
	}
	
	

	/**
	* Title: confirm
	* Description:
	* @param mcOrder
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#confirm(com.winterframework.firefrog.fund.entity.FundChargeMCOrder) 
	*/
	@Override
	
	public FundChargeOrder confirm(FundChargeMCOrder mcOrder) throws Exception {
		FundChargeOrder order = mcOrder.getOrder();
		//order.setChargeTime(mcOrder.());
		order.setStatus(FundChargeOrder.Status.SUCCESS);
		order.setMcOrder(mcOrder);
		order.setRealBankId(mcOrder.getBankId());
		//确认的时候，修改的是real_bank_ID
		order.getPayCard().getBank().setId(null);
		
		long updateNumber = fundChargeDao.update(order);
		if (mcOrder.getOrder().getSn().contains("MDAX")) {
			FundManualOrder vv = fundManualDepositDao.queryManualDepositBySn(mcOrder.getOrder().getSn());
			vv.setStatus(FundManualOrder.Status.DEPOSITED.getStatis());
			fundManualDepositDao.updateManualDeposit(vv);
		}

		if (updateNumber >= 1) {
			//注释
			//order.getApplyUser().getId();
			//order.getRealChargeAmt();
			/*
			try {
				ActivityUserBetOrChargeAmountStruc[] ass=new ActivityUserBetOrChargeAmountStruc[1];
				ActivityUserBetOrChargeAmountStruc as=new ActivityUserBetOrChargeAmountStruc();
				as.setAmount(mcOrder.getAmount());
				as.setDate(DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN));
				as.setDemo("新系统充值");
				as.setSource("4.0");
				as.setType(1);
				as.setUserId(order.getApplyUser().getId());
				ass[0]=as;
				Request<ActivityUserBetOrChargeAmountStruc[]> rq = new Request<ActivityUserBetOrChargeAmountStruc[]>();
				rq.setHead(new RequestHeader());
				rq.getHead().setUserId(order.getApplyUser().getId());
				rq.setBody(new com.winterframework.modules.web.jsonresult.RequestBody<ActivityUserBetOrChargeAmountStruc[]>());
				rq.getBody().setParam(ass);
				logger.info("addTransfer jason start");
				HttpJsonClientExt.postJsonObject(game_activity_user_charge, rq);
				
			} catch (Exception e) {
				logger.error("4.0充值保存game活动用户数据错误",e);
			}
			*/
			//如果sn是可以正常获取的话
			order = fundChargeDao.queryByOrderNum(order.getSn());
			BaseUser applyUser = order.getApplyUser();
			order.setApplyUser(applyUser);
			//资金变更
			updateFund(order);
			//通知
		//	SendMsg (order.getMcOrder().getAmount(), applyUser);
		
		}
		
		return order;
	}
	
	public void sendMsg (Long amount, BaseUser applyUser) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", platformName);
		Double famount = (Double.valueOf(amount) / 10000);
		map.put("rechargeMoney", String.valueOf(famount));
		msgToMQ.addMessageToMq(applyUser, NoticeTaskEnum.ChargeSuccessful, map);
	}
	
	/**
	* Title: query
	* Description:
	* @param pageReq
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#query(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public CountPage<FundChargeOrder> query(PageRequest<ChargeQueryRequest> pageReq) throws Exception {
		return fundChargeDao.queryFundCharge(pageReq);
	}

	/**
	* Title: reportException
	* Description:
	* @param fundChargeException
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#reportException(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public void reportException(FundChargeException fundChargeException) throws Exception {
		String sn = snUtil.createBusinessSn(FundModel.FD.ABDX.ITEMS.NEW, 0);
		fundChargeException.setSn(sn);
		fundChargeExceptionDao.save(fundChargeException);
	}

	/**
	* Title: cancelOrder
	* Description:
	* @param chargeId
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#cancelOrder(java.lang.Long) 
	*/
	@Override
	public long updateOrder(Long chargeId, Status status) throws Exception {
		return fundChargeDao.updateStatus(chargeId, status.getValue());
	}
	
	/**
	* Title: cancelOrder
	* Description:
	* @param chargeId
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundChargeService#cancelOrder(java.lang.Long) 
	*/
	@Override
	public void cancelOrder(Long chargeId, Status status) throws Exception {
		fundChargeDao.updateStatus(chargeId, status.getValue());
	}

	@Override
	public FundChargeOrder haveChargeItem(Long userId, Long depositeMode) throws Exception {
		Integer time = 30;
		Date currentc = DateUtils.minusMinutes(new Date(), 60 * 24);
		//Date currentc = DateUtils.minusMinutes(new Date(), 30);

		FundChargeOrder order = fundChargeDao.queryByUserId(userId, currentc, depositeMode);

		if (order != null) {
			if (order.getPayBank().getId() != null) {
				FundBank bank = fundBankDao.queryById(order.getPayBank().getId());
				order.getPayBank().setName(bank.getName());
				order.getPayBank().setUrl(bank.getUrl());

				/*List<BankCard> bankList = bankCardDao.getBoundBankCard(userId,order.getPayCard().getBankCardNo());
				if(bankList!=null && bankList.size()>0){
					BankCard bankcard = (BankCard)bankList.get(0);
					order.setNickName(bankcard.getNickName());
					order.setBankAccount(bankcard.getAccountHolder());
				}*/
				
			}
			if (order.getRevCard() != null && order.getRevCard().getBank() != null
					&& order.getRevCard().getBank().getId() != null) {
				FundBank bank = fundBankDao.queryById(order.getRevCard().getBank().getId());
				order.setRevBank(new FundBank());
				order.getRevBank().setName(bank.getName());
				order.getRevBank().setUrl(bank.getUrl());
			}
			
			
			
		}
		return order;
	}

	private void cancelManalOrde(String sn) throws Exception {
		if (sn.startsWith("FDMDAX")) {
			FundManualOrder vv = fundManualDepositDao.queryManualDepositBySn(sn);
			vv.setStatus(FundManualOrder.Status.CLOSED.getStatis());
			fundManualDepositDao.updateManualDeposit(vv);
		}

	}

	@Autowired
	protected FundManualDepositDaoImpl fundManualDepositDao;
	@Resource(name = "fundManualDepositService")
	private IFundManualDepositService fundManualDepositService;

	public void updateFund(FundChargeOrder order) throws FundChangedException {
			//TODO gxh
		//afterFund.setDisableAmt(beforeFund.getDisableAmt() + realCargeAmt*2/10);
		FundChargeMCOrder mcOrder = order.getMcOrder();
		Long addAmtLong;
		try {
			addAmtLong = getRealAmtWithFee(order);
		} catch (Exception e) {
			//获取附加手续费异常，直接添加实际金额
			addAmtLong = (mcOrder != null) ? mcOrder.getAmount().longValue() : order.getRealChargeAmt();
		}

		logger.info("updateFund: order sn:"+order.getSn());
		
		FundGameVo fundGameVo;
		
		if (order.getSn().contains("MDAX")) {
			if (order.getDepositMode() == 2){
				fundGameVo = new FundGameVo(FundModel.FD.MDAX.ITEMS.MDAX5, order.getApplyUser().getId(),mcOrder.getAmount(), order.getSn(), true);
				// 只有 帳變 不收手續費
				fundGameVo.setSn(order.getSn());
 				fundChangeService.action(fundGameVo);
				
			}else {
				fundGameVo = new FundGameVo(FundModel.FD.MDAX.ITEMS.MDAX5, order.getApplyUser().getId(),mcOrder.getAmount(), order.getSn(), true);
				fundGameVo.setSn(order.getSn());
				// 第一個 FundGameVo 是帳變 ，  第二個是手續費 
				fundChangeService.action(fundGameVo, new FundGameVo(FundModel.PM.RBRC.ITEMS.RBRC, order.getApplyUser().getId(), addAmtLong, null, true));
			}

		} else {
			if (order.getDepositMode() == 2){
				fundGameVo = new FundGameVo(FundModel.FD.AUTO.ITEMS.SUCCESS, order.getApplyUser().getId(),mcOrder.getAmount(), order.getSn(), true);
				fundGameVo.setSn(order.getSn());
				fundChangeService.action(fundGameVo);
				
			}else {
				fundGameVo = new FundGameVo(FundModel.FD.AUTO.ITEMS.SUCCESS, order.getApplyUser().getId(),mcOrder.getAmount(), order.getSn(), true);
				fundGameVo.setSn(order.getSn());
				fundChangeService.action(fundGameVo, new FundGameVo(FundModel.PM.RBRC.ITEMS.RBRC, order.getApplyUser().getId(), addAmtLong, null, true));
			}
		}

	}

	private Long getRealAmtWithFee(FundChargeOrder order) throws Exception {
		Long bankId = order.getPayCard().getBank().getId();
		if (bankId == null) {
			bankId = order.getPayBank().getId();
		}
		return getRealAmtWithFee(bankId, order.getRealChargeAmt(), order.getMcOrder().getBankFee());
	}

	@Override
	public void cancelMowOrde() throws Exception {

		Date now = new Date();
		Date time = new Date(now.getTime() - 30 * 60 * 1000);

		List<FundCharge> order = fundChargeDao.getMowFundChargeByTimeAndStatusOne(time);
		if (order != null) {
			for (FundCharge fundCharge : order) {
				Date dt=new Date(System.currentTimeMillis());
				if(fundCharge.getSn().contains("FDMDAX")){
					if(dt.before(DateUtils.addMinutes(fundCharge.getApplyTime(), 60))){
						//如果是1个小时之前
						 //如果是人工打款，并且是60分钟之内的话，则不cancel
						continue;
						
					}
				}
				
				FundChargeOrder fundChargeOrder = VOConverter.transFundCharge2FundChargeOrder(fundCharge);
				//如果半个小时内还有订单，则取消订单，发送mowcumu取消订单
				//fundChargeDao.updateStatus(fundChargeOrder.getId(), Status.FAILED.getValue());
				cancelMowOrder(fundChargeOrder.getSn(), Status.FAILED);
			}
		}
	}

	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;

	@Override
	public Long queryPeriodChargeSum(Long userId,Date startTime,Date endTime) throws Exception {
		return fundChargeDao.queryPeriodChargeSum(userId, startTime, endTime);
	}
	
	@Override
	public void changeOrderStatus(String orderNum, FundManualOrder.Status status) throws Exception {
		FundManualOrder vv = fundManualDepositDao.queryManualDepositBySn(orderNum);
		vv.setStatus(status.getStatis());
		fundManualDepositDao.updateManualDeposit(vv);
	}
	
	@Override
	public long updateFundCharge(FundCharge fundCharge) throws Exception {
		return fundChargeDao.updateFundCharge(fundCharge);
	}
	@Override
	public Long checkChargeLimit(ChargeApplyRequest chargeApplyRequest)
			throws Exception {
		
		FundBank bank = fundBankDao.getById(chargeApplyRequest.getBankId());
		Long dayChagre = fundChargeDao.getDayCharge(chargeApplyRequest.getBankId());
		Long nowTotalChargeAmt = chargeApplyRequest.getPreChargeAmt()*10000;
		//目前充值金額達上限
		if(dayChagre!=null && (dayChagre>=bank.getDaylimit()||(nowTotalChargeAmt+dayChagre) > bank.getDaylimit())){
			return 0l;
		}else{
			return -1l;
		}
	}
	


	/**
	* @Title: chargeThirdPartyLimit 
	* @Description: 第三方支付充值限制檢查
	* @param userId 
	* @param chargeAmt 本次充值金額 
	* @return approvelResult 審核結果 1通過 0不通過
	* @throws Exception
	 */	
	@Override
	public boolean checkChargeThirdPartyLimit(Long userId, Long chargeAmt) throws Exception {
		//預設核准結果為不通過
		boolean approvelResult=false;
		
		
		//取出DB config欄位內資料
		String thirdChargeLimit = configService.getConfigValueByKey("fund", "thirdChargeLimit");
		Map<String, Object> thirdChargeLimitMap = DataConverterUtil.convertJson2Map(thirdChargeLimit);
		
		
		
		//取出系統當下時間
		Date systemDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date endDate = sdf.parse(thirdChargeLimitMap.get("firstLimitDayEnd").toString()); //取出config內設定結束時間

		logger.debug("endDate="+endDate);
		logger.debug("timeCompare Result="+systemDate.before(endDate));
		
		
		
		
		//檢查今日充值金額加總是否大於5000
		Long todayTotalChargeAmt = chargeAmt+fundChargeDao.queryTodayChargeAmt(userId,chargeAmt); //再加上DB查出來的總金額
		
		Long limitMoney = (long)Double.parseDouble(thirdChargeLimitMap.get("limitMoney").toString())*unitConvert;

		
				
		if(todayTotalChargeAmt > limitMoney){//充值金額大於限制金額(目前設定5000元)為true
			
			if(systemDate.before(endDate)){ //如果系統時間是在2016-12-20 00:00:00前為true
				
				//查詢2016-11-29 05:00:00 前快捷/銀聯 充值紀錄
				Long firstLimitDay = Long.parseLong(thirdChargeLimitMap.get("firstLimitDay").toString());
				Long firtsLimitDayResult = fundChargeDao.queryThirdChargeRecordsTemp(userId,firstLimitDay);
				
				
				
				if(firtsLimitDayResult>0){ //2016-11-29 05:00:00 前快捷/銀聯 充值紀錄的筆數大於0為true	

					approvelResult=true;
					return approvelResult;
				}
			}
			
			
			//查詢用戶21天前有充值記錄
			Long secondLimitDay = Long.parseLong(thirdChargeLimitMap.get("secondLimitDay").toString());
			Long secondLimitDayResult = fundChargeDao.queryThirdChargeRecords(userId,secondLimitDay);
			
			
			if(secondLimitDayResult>0){//用戶21天前有充值記錄的筆數大於0為true
				approvelResult=true;
				return approvelResult;
			}
			
			
		}else{ //充值金額小於限制金額(目前設定5000元)
			approvelResult=true;  //核准通過
			return approvelResult;
			
		}
		
		
		return approvelResult;

	}
	
	
	

	/**
	 * 
	 * Title: checkBankDayLimit
	 * Description: 銀行(支付寶)當日充值限制檢查
	 * param userId
	 * param preChargeAmt
	 * return boolean 檢查結果 ture 通過 false不通過
	 */
	@Override
	public boolean checkBankDayLimit(Long depositMode) throws Exception {
		// 預設檢查結果為不通過false
		boolean checkResult = false;

		Long dayLimit = 0L;
		//查出支付寶資訊  index:0個人版  1企業版
		List<FundBank> banks = fundBankDao.queryByCode(30L);
		if (depositMode == 6L) { // 支付寶個人版的單日限額
			dayLimit = banks.get(0).getDaylimit();
		} else if (depositMode == 8L) {// 支付寶企業版的單日限額
			dayLimit = banks.get(1).getDaylimit();
		}
		// 檢查平台充值總額是否超過單日限額
		logger.debug("單日限額為:" + dayLimit);	
		Long dayTotal=fundChargeDao.queryBankDayChargeSum(depositMode);
		logger.debug("單日充值總額為:" + dayTotal);
		if (dayTotal < dayLimit) {
			checkResult = true;
		}

		return checkResult;
	}
	@Override
	public void executeChargeDispatch(FundChargeMCOrder mcOrder, FundChargeOrder order,
			String orderId) throws Exception {
		
		logger.info("order is :" + order);
		if (order.getStatus() == null || order.getStatus().equals(Status.APPLY)
				|| order.getStatus().equals(Status.FAILED) // time out call cancel
				|| order.getStatus().equals(Status.ADMIN_CANCEL) // admin call  cancel
				|| order.getStatus().equals(Status.USER_CANCEL)) // user call cancel
		{
			long num = this.updateOrder(order.getId(), Status.PROCESS);
			if (num != 1l) {
				throw new Exception("訂單資料異常");
			}
			Date now = new Date();
			order.setSn(orderId);

			BankCard payCard = new BankCard();
			FundBank payBank = new FundBank();
			payBank.setId(order.getPayCard().getBank().getId());
			payCard.setBank(payBank);
			order.setPayCard(payCard);
			order.setRealBankId(order.getPayCard().getBank().getId());
			order.setChargeTime(now);
			order.setOperatingTime(now);
			mcOrder.setOrder(order);
			
			//訂單發幣
			this.confirm(mcOrder);		 
			//判斷是否為新手進行首充獎勵
			beginMissionService.firstChargeAward(mcOrder.getOrder().getApplyUser().getId(), mcOrder.getAmount());
			//結束才寄信
			sendMsg (mcOrder.getAmount(), mcOrder.getOrder().getApplyUser());
		}

	}
	@Override
	public Integer queryUnhandleCharge() {
		return fundChargeDao.getCountUnhandle();
	}

}
