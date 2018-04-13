package com.winterframework.firefrog.shortlived.mmcRanking.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.game.dao.vo.MMCRankingLog;
import com.winterframework.firefrog.game.fund.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVo;
import com.winterframework.firefrog.game.fund.service.IGameFundService;
import com.winterframework.firefrog.game.web.dto.MmcRankingDataDto;
import com.winterframework.firefrog.game.web.dto.MmcRankingDto;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistory;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistoryResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingMyPrizeDto;
import com.winterframework.firefrog.game.web.dto.MmcRankingPrizeDto;
import com.winterframework.firefrog.game.web.dto.MmcRankingResponse;
import com.winterframework.firefrog.shortlived.mmcRanking.dao.IMMCRankingDao;
import com.winterframework.firefrog.shortlived.mmcRanking.dao.IMMCRankingLogDao;
import com.winterframework.firefrog.shortlived.mmcRanking.service.IMMCRankingService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName ActivityServiceImpl 
* @Description 活动 
* @author  hugh
* @date 2014年12月2日 下午3:32:55 
*  
*/
@Service("mmcRankingService")
@Transactional(rollbackFor = Exception.class)
public class MMCRankingServiceImpl implements IMMCRankingService{
	
	@Resource(name = "mmcRankingDaoImpl")
	private IMMCRankingDao mmcRankingDaoImpl;
	@Resource(name = "RedisClient")
	private RedisClient redis;
	@Resource(name="gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDao; 
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	@Resource(name = "gameFundServiceImpl")
	private IGameFundService gameFundServiceImpl;
	@PropertyConfig(value = "mmc.rank.firstOneMoney")
	private Long firstOneMoney;
	@PropertyConfig(value = "mmc.rank.getKing")
	private Long getKing;
	@PropertyConfig(value = "mmc.rank.limit")
	private Long limit;
	@Resource(name = "mmcRankingLogDaoImpl")
	private IMMCRankingLogDao mmcRankingLogDaoImpl;
	
	
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	
	private final Logger log = LoggerFactory.getLogger(MMCRankingServiceImpl.class);
	
	/**
	 * 取得排行榜
	 */
	public MmcRankingResponse queryTop(String account){
		List<MmcRankingDto> mmcRankList = new ArrayList<MmcRankingDto>();
		MmcRankingResponse mmcRankingResponse = new MmcRankingResponse();
		MmcRankingDataDto mmcRankingDataDto = new MmcRankingDataDto();
		MmcRankingPrizeDto prize = new MmcRankingPrizeDto();
		MmcRankingMyPrizeDto myPrize = new MmcRankingMyPrizeDto();
		//取得今天時間
		String beginDate = dateParser("yyyy-MM-dd 00:00:00");
		String endDate = dateParser("yyyy-MM-dd 23:59:59");
		
		//取得前10名資料
		log.info("queryTop beginDate="+beginDate+" , endDate="+endDate);
		List<MMCRanking> topTen = mmcRankingDaoImpl.queryTop(beginDate, endDate, null);
		log.info("topten done");
		//取得當前用戶資料
		MMCRanking userRanking = mmcRankingDaoImpl.queryByAccount(account,beginDate,endDate);
		log.info("userRanking done");
		if(userRanking == null){
			userRanking = new MMCRanking();
			userRanking.setRank(0l);
		}
		//取得當前用戶前後兩名資料
		log.info("queryTop userRank="+userRanking.getRank());
		List<MMCRanking> userRanks = mmcRankingDaoImpl.queryTop(beginDate, endDate, userRanking.getRank());
		log.info("userRanks done");
		for(MMCRanking m : topTen){
			//當前用戶排名大於10，則不需要8、9、10名
			if(userRanking.getRank() >= 10 && (m.getRank() == 8 || m.getRank() == 9 ||m.getRank() == 10)){
				continue;
			}
			MmcRankingDto rankingDto = new MmcRankingDto();
			rankingDto.setNumber(m.getRank());
			rankingDto.setName(m.getAccount());
			rankingDto.setAmount(Double.valueOf(m.getAmount())/10000);
			rankingDto.setIsDiamond(m.getAmount() > limit ?true:false);
			mmcRankList.add(rankingDto);
			
		}
		//當前用戶為10名以後，則移除第8、9、10名，再塞入當前用戶前一名與後一名
		for(MMCRanking m :userRanks){
			if(userRanking.getRank() <=9){
				break;
			}
			MmcRankingDto rankingDto = new MmcRankingDto();
			rankingDto.setNumber(m.getRank());
			rankingDto.setName(m.getAccount());
			rankingDto.setAmount(Double.valueOf(m.getAmount())/10000);
			rankingDto.setIsDiamond(m.getAmount() > limit ?true:false);
			mmcRankList.add(rankingDto);
		}
		
		mmcRankingDataDto.setList(mmcRankList);
		mmcRankingDataDto.setUser(userRanking!=null?userRanking.getRank():null);
		
		//
		String isKing = redis.get("mmcKing"+dateParser("yyyyMMdd"));
		String isIKing = redis.get("mmcKing"+dateParser("yyyyMMdd")+account);
		
		log.info("queryTop isKing="+isKing);
		if(isKing != null){
			String userShowTip = redis.get("mmcKingShowTip"+dateParser("yyyyMMdd")+account);
			//該用戶是不是已顯示過訊息
			if(userShowTip == null){
				//該用戶已經顯示過超車訊息
				redis.set("mmcKingShowTip"+dateParser("yyyyMMdd")+account, isKing, 300);
				String[] kingData = isKing.split(":");
				prize.setIsWinning(true);
				isKing = kingData[0].substring(0,1)+"***"+kingData[0].substring(kingData[0].length()-1,kingData[0].length());
				prize.setMessage("恭喜<span style='font-size: 18px; color: #fff'>"+isKing+"</span>超越奖金保持人,并成为目前奖金榜冠军,获得超车奖金"+getKing/10000+"元!");
			}else{
				String[] kingData = isKing.split(":");
				String[] userTipData = userShowTip.split(":");
				//用戶訊息是不是已經顯示過
				if(!kingData[0].equals(userTipData[0]) || !kingData[1].equals(userTipData[1])){
					//該用戶已經顯示過超車訊息
					redis.set("mmcKingShowTip"+dateParser("yyyyMMdd")+account, isKing, 300);
					prize.setIsWinning(true);
					isKing = kingData[0].substring(0,1)+"***"+kingData[0].substring(kingData[0].length()-1,kingData[0].length());
					prize.setMessage("恭喜<span style='font-size: 18px; color: #fff'>"+isKing+"</span>超越奖金保持人,并成为目前奖金榜冠军,获得超车奖金"+getKing/10000+"元!");
				}else{
					prize.setIsWinning(false);
					prize.setMessage("");
				}
				
			}
			
		}else{
			prize.setIsWinning(false);
			prize.setMessage("");
		}
		
		
		if(isIKing !=null){
			if(isIKing.equals("1")){
				myPrize.setIsShowing(true);
				myPrize.setMessage("恭喜您超越奖金保持人,并成为目前奖金榜冠军, 获得超车奖金"+getKing/10000+"元");
				redis.set("mmcKing"+dateParser("yyyyMMdd")+account, "0", 120);
			}else{
				myPrize.setIsShowing(false);
				myPrize.setMessage(null);
			}
		}
		mmcRankingDataDto.setPrize(prize);
		mmcRankingDataDto.setMyprize(myPrize);
		mmcRankingResponse.setData(mmcRankingDataDto);
		return mmcRankingResponse;
	}
	
	/**
	 * 投注後資料更新、超車派錢
	 */
	public void isKingDay(Long orderId,String orderCode){
		try {
			//取本次中獎金額
			GameOrderWin orderWin = gameOrderWinDao.selectGameOrderWinByOrderId(orderId);
			if(orderWin == null){
				log.info("isKingDay orderId="+orderId+" 未中獎");
				return ;
			}
			Long userWinMoney = (orderWin.getCountWin() + orderWin.getDiamondCountWin());
			Long kingMoney = 0l;
			log.info("isKingDay orderId="+orderId+",userWinMoney="+userWinMoney);
			String beginDate = dateParser("yyyy-MM-dd 00:00:00");
			String endDate = dateParser("yyyy-MM-dd 23:59:59");
			log.info("isKingDay orderId="+orderId+",beginDate="+beginDate+" , endDate="+endDate);
			List<MMCRanking> topTen = mmcRankingDaoImpl.queryTop(beginDate, endDate, null);
			//取得目前排行榜第一名
			for(MMCRanking m : topTen){
				if(m.getRank() == 1){
					kingMoney = m.getAmount();
					break;
				}
			}
			User user=userCustomerDao.queryUserById(orderWin.getUserid());
			log.info("isKingDay orderId="+orderId+",kingMoney="+kingMoney);
			//本次中獎金額大於排行榜第一名
			if(userWinMoney > kingMoney){
				//如果大於10000，才算冠軍
				if(userWinMoney > limit){
					redis.set("mmcKing"+dateParser("yyyyMMdd"), user.getUserProfile().getAccount() + ":"+userWinMoney, 300);
					redis.set("mmcKing"+dateParser("yyyyMMdd")+user.getUserProfile().getAccount(), "1", 120);
					//不管怎樣只給500元，所以註解
//					if(kingMoney == 0 || kingMoney < limit){
//						//給第一次上榜獎金
//						GameFundServiceBean gameFundServiceBean = new GameFundServiceBean();
//						List<FundGameVo> fundList = new ArrayList<FundGameVo>();
//						FundGameVo fundGameVo = new FundGameVo();
//						fundGameVo.setUserId(orderWin.getUserid());
//						fundGameVo.setAmount(firstOneMoney);
//						fundGameVo.setReason(ACTIVITY_REASON_KEY);
//						fundGameVo.setIsAclUser(0l);
//						fundGameVo.setOperator(0l);
//						fundGameVo.setNote("秒秒风云榜上榜奖金");
//						fundList.add(fundGameVo);
//						gameFundServiceBean.setFundList(fundList);
//						gameFundServiceImpl.fundActions(gameFundServiceBean);
//						log.info("isKingDay orderId="+orderId+",上榜獎金="+user.getUserProfile().getAccount()+","+firstOneMoney);
//					}
					//給超車獎金
					GameFundServiceBean gameFundServiceBean = new GameFundServiceBean();
					List<FundGameVo> fundList = new ArrayList<FundGameVo>();
					FundGameVo fundGameVo = new FundGameVo();
					fundGameVo.setUserId(orderWin.getUserid());
					fundGameVo.setAmount(getKing);
					fundGameVo.setReason(ACTIVITY_REASON_KEY);
					fundGameVo.setIsAclUser(0l);
					fundGameVo.setOperator(0l);
					fundGameVo.setNote("秒秒风云榜夺冠奖金");
					fundList.add(fundGameVo);
					gameFundServiceBean.setFundList(fundList);
					gameFundServiceImpl.fundActions(gameFundServiceBean);
					log.info("isKingDay orderId="+orderId+",超車獎金="+user.getUserProfile().getAccount()+","+firstOneMoney);
					
					
					//記錄超車資訊
					MMCRanking userRanking = mmcRankingDaoImpl.queryByAccount(user.getUserProfile().getAccount(),beginDate,endDate);
					MMCRankingLog mmcRankingLog  = new MMCRankingLog();
					mmcRankingLog.setAccount(user.getUserProfile().getAccount());
					mmcRankingLog.setBeforeAmount(kingMoney);
					mmcRankingLog.setAfterAmount(userWinMoney);
					if(userRanking == null){
						mmcRankingLog.setRank(0l);
					}else{
						mmcRankingLog.setRank(userRanking.getRank());
					}
					mmcRankingLog.setGiveAmount(getKing);
					mmcRankingLog.setCreateDate(new Date());
					mmcRankingLogDaoImpl.insert(mmcRankingLog);
				}
			}
			//更新秒秒風雲榜Table
			//取得當前用戶資料
			MMCRanking userRanking = mmcRankingDaoImpl.queryByAccount(user.getUserProfile().getAccount(),beginDate,endDate);
			if(userRanking != null){
				//判斷這次中獎金額是不是大於排行榜金額
				if(userWinMoney > userRanking.getAmount()){
					userRanking.setAmount(userWinMoney);
					userRanking.setUpdateDate(new Date());
					log.info("isKingDay orderId="+orderId+",更新用戶="+user.getUserProfile().getAccount()+","+userWinMoney);
					mmcRankingDaoImpl.update(userRanking);
				}
			}else{
				MMCRanking newUserRanking = new MMCRanking();
				newUserRanking.setAccount(user.getUserProfile().getAccount());
				newUserRanking.setAmount(userWinMoney);
				newUserRanking.setCreatDate(new Date());
				newUserRanking.setUpdateDate(new Date());
				newUserRanking.setOrderCode(orderCode);
				log.info("isKingDay orderId="+orderId+",新增用戶="+user.getUserProfile().getAccount()+","+userWinMoney);
				mmcRankingDaoImpl.insert(newUserRanking);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("秒秒風雲榜超車錯誤 orderId="+orderId+","+e);
		}
		
		
	}
	
	
	
	
	/**
	 * 歷史日冠軍
	 */
	public MmcRankingHistoryResponse queryHistory(){
		
		try {
			MmcRankingHistoryResponse respone = new MmcRankingHistoryResponse();
			List<MmcRankingHistory> mmcRankList = new ArrayList<MmcRankingHistory>();
			List<MMCRanking> historyList= mmcRankingDaoImpl.queryHistory();
			SimpleDateFormat daySdf = new SimpleDateFormat("MM/dd");
			SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			
			
			for(MMCRanking m :historyList){
				log.info("account="+m.getAccount());
				log.info("amount="+m.getAmount());
				log.info("createDate="+m.getCreatDate());
				MmcRankingHistory mrh = new MmcRankingHistory();
				mrh.setDay(daySdf.format(m.getCreatDate()));
				mrh.setMoney(Double.valueOf(m.getAmount())/10000);
				mrh.setName(m.getAccount());
				mmcRankList.add(mrh);
			}
			respone.setDay(mmcRankList);
			return respone; 
		} catch (Exception e) {
			// TODO: handle exception
			log.error("秒秒風雲榜查詢歷史冠軍錯誤 :"+e);
			return null;
		}
	}
	private String dateParser(String forttmer){
		SimpleDateFormat sdf = new SimpleDateFormat(forttmer);
		String formatDate = sdf.format(new Date());
		return formatDate;
	}
}
