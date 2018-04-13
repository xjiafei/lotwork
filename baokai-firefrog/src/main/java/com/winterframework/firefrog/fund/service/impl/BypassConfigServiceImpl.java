package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.util.RandomVal;
import com.winterframework.firefrog.fund.dao.IBypassConfigDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.fund.dao.IFundChargeBypassDao;
import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.service.IBypassConfigService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.firefrog.user.entity.User;

@Service("bypassConfigServiceImpl")
public class BypassConfigServiceImpl implements IBypassConfigService {
	private static Logger log = LoggerFactory.getLogger(BypassConfigServiceImpl.class);

	private static Long UNIPAY_DEPOSIT_MODE = 5L;
	@Autowired
	private IBypassConfigDao bypassConfigDao;
	
	@Autowired
	private IUserCustomerDao userCustomerDao;
	
	@Autowired
	private IFundChargeBypassDao fundChargeBypassDao;

	@Autowired
	private IFundChargeDao fundChargeDao;

	@Autowired
	private IFundWithdrawDao fundWithdrawDao;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@PropertyConfig(value = "unitConvert")
	private Long unitConvert;

	public Optional<List<BypassConfigVO>> findByCondition(BypassConfigVO entity) throws Exception {
		return bypassConfigDao.findByCondition(entity);
	}

	public void updateBypass(List<BypassConfigVO> configs) throws Exception {
		for (BypassConfigVO vo : configs) {
			if(vo.getId() !=0){
				log.info("bypass modify: id={},isOpen={}",new Object[]{vo.getId(),vo.getIsOpen()});
				bypassConfigDao.udpateBypass(vo);
			}
		}
	}

	// 分流判斷 type參數 0:充值 1:提現
	public int checkWithdrawBypass(Long amount, String account, Long type,String chargeWay) throws Exception {
		BypassConfigVO entity = new BypassConfigVO();		
		entity.setType(type);
		entity.setIsOpen("Y");
		// 分流 0:DP 1:通匯
		int bypass = 0;
		// 查詢充值或提現相關開放的CONFIG檔
		Optional<List<BypassConfigVO>> bypassOptional = bypassConfigDao.findByCondition(entity);
		List<BypassConfigVO> bypassList = bypassOptional.get();

		if (bypassOptional.isPresent() && !bypassOptional.get().isEmpty()) {
			bypass=this.getbypass(bypassList, amount);
		}
		return bypass;
	}
	public int checkChargeBypass(Long amount, Long userId, Long type, Long chargeWay, Long platform) throws Exception {
		User user=userCustomerDao.queryUserById(userId);
		Calendar calendar = Calendar.getInstance();
		Long nowDate = calendar.getTime().getTime();
		Long registerDate = user.getUserProfile().getRegisterDate().getTime();
		Long betweenDate =(nowDate-registerDate)/(1000*60*60*24);
		
		BypassConfigVO entity = new BypassConfigVO();

		entity.setType(type);
		entity.setIsOpen("Y");		
		entity.setChargeWaySet(chargeWay);
		// 分流 0:DP
		int bypass = 0;
		if(betweenDate>90 || user.getVipLvl()!=0){//註冊時間大於90天或是vip用戶
			if(platform==3){//PC端 舊用戶
				entity.setPlatform(0l);
			} else{//APP端 舊用戶
				entity.setPlatform(1l);
			}
		}else{
			if(platform==3){//PC端 新用戶
				entity.setPlatform(0l);
				entity.setOpenUser(0l);//快捷 匯博  網銀dp 財富通dp 企業版支付寶sdpay 微信 sdpay
				
			} else{//APP端 新用戶
				entity.setPlatform(1l);
				entity.setOpenUser(0l);//快捷dp
			}
		}
		// 查詢充值或提現相關開放的CONFIG檔
		Optional<List<BypassConfigVO>> bypassOptional = bypassConfigDao.findByCondition(entity);
		List<BypassConfigVO> bypassList = bypassOptional.get();
		//檢查充值渠道是否超過每日限額
		if(bypassList.size()!=0 && String.valueOf(bypassList.get(0).getIsBypassView()).equals("Y")){
			bypassList = checkChargeOver(bypassOptional);
		}
		if (bypassOptional.isPresent() && !bypassOptional.get().isEmpty()) {
			FundChargeBypassVO fundChargeBypassVO = null;
			//檢查是否存在於白名單
			fundChargeBypassVO = fundChargeBypassDao.searchByAccount(user.getAccount(),chargeWay);				

			boolean isHaveWhiteChargeWay=false;
			if (fundChargeBypassVO != null) {  // 表示有在白名單中
				//用戶的白名單渠道
				int whiteByPass = fundChargeBypassVO.getChargeChannel().intValue();
				//檢查該渠道是否開啟
				for(BypassConfigVO vo:bypassList){
					if(vo.getAgency()==whiteByPass && vo.getIsOpen().equals("Y")){
						bypass=whiteByPass;
						isHaveWhiteChargeWay=true;
					}
				}
			}
			if(!isHaveWhiteChargeWay){ //充值不在白名單中
				bypass=this.getbypass(bypassList, amount);
			}
		}		
		return bypass;
	}
	private int getbypass(List<BypassConfigVO>bypassList,Long amount){
		int bypass=0;
		if(bypassList.size() > 1){  //充值or提現，渠道有1個以上開啟中
			List<BypassConfigVO> conditionList = new ArrayList<BypassConfigVO>();
			for(BypassConfigVO vo:bypassList){
				if(vo.getIsBypassView().equals("Y")){
					if((amount >= vo.getSingleLowlimit()*unitConvert && amount <= vo.getSingleUplimit()*unitConvert)){
						conditionList.add(vo);
					}
				} else{
					conditionList.add(vo);
				}		
			}
			int random = 0;
			if(conditionList.size() > 0){
				random = RandomVal.randInt(conditionList.size()-1, 0);
				bypass = conditionList.get(random).getAgency().intValue();
			}else{ //如果不在全部的條件內，走DP
				bypass = 0; 
			}
		}else if(bypassList.size()==0){ //全部渠都都關閉，走DP
			bypass=0;
		}else{//只有一個 則必走該選項
			bypass = bypassList.get(0).getAgency().intValue();		
		}
		return bypass;
	}
	
	public long getDailySumByType(Long type, Long agency, Long chargeWaySet) throws Exception {
		log.info("agency : " + agency);
		Long transferAgency = transferAgency(agency);
		log.info("transferAgency : " + transferAgency);
		if (chargeWaySet == 5) { // APP銀聯
			return fundChargeDao.queryDailyAppUnipayChargeSum(transferAgency(agency), UNIPAY_DEPOSIT_MODE);
		} else {
			return fundChargeDao.queryDailyChargeSum(transferAgency(agency), chargeWaySet);
		}
	}
	//將web跟app的充值渠道匯整
	private Long transferAgency(Long agency) { //AGENCY 0:dp 1:th 3:ECPSS 4:HBPay 7:WorthPay
		switch (agency.intValue()) {
		case 0:
			return 1L;
		case 1:
			return 2L;
		case 3:
			return 3L;
		case 4:
			return 4L;
		case 7:
			return 7L;
		case 8:
			return 8L;
		case 9:
			return 9L;
		default:
			return 1L;
		}
	}

	private List<BypassConfigVO> checkChargeOver(Optional<List<BypassConfigVO>> bypassOptional) throws Exception {
		List<BypassConfigVO> bypassList = new ArrayList<BypassConfigVO>();
		for (BypassConfigVO vo : bypassOptional.get()) {
			long dailySum = getDailySumByType(0l, vo.getAgency(),vo.getChargeWaySet());
			if (dailySum < vo.getDailyUplimit() * unitConvert) {
				// 將充值總額還未超標的充值渠道放入List內
				bypassList.add(vo);
			}
		}
		return bypassList;
	}

}
