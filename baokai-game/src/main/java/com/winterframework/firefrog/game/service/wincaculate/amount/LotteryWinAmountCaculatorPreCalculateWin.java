package com.winterframework.firefrog.game.service.wincaculate.amount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultMultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultSingleBean;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.ISlipItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusAwardJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.firefrog.game.service.IGameSlipDetailService;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: LotteryWinAmountCaculatorPreCalculateWin 
* @Description: 预计奖处理
* @author 你的名字 
* @date 2014-4-7 上午11:46:09 
*  
*/
@Service("lotteryWinAmountCaculatorPreCalculateWin")
public class LotteryWinAmountCaculatorPreCalculateWin extends LotteryWinAmountCaculator {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;

	@Resource(name = "slipItemAssistDaoImpl")
	private ISlipItemAssistDao slipItemAssistDaoImpl;

	@Resource(name="gamePointDaoImpl")
	private IGamePointDao gamePointDao;
	
	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	@Resource(name = "gameSlipDetailServiceImpl")
	private IGameSlipDetailService gameSlipDetailService;
	
	private final Long amtUnit=10000l;
	
//	private Long formatLong(int moneyModel,Long aaa){
//		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)============"+aaa);
//		if(aaa==null) return null;
//		if(1==moneyModel){
//		    aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
//		}else{
//			aaa= NumberUtils.toLong(String.valueOf(aaa/100)+"00");
//		}
//		//System.out.println(" Long formatLong(Long moneyModel,Long aaa)2============"+aaa);
//
//		return aaa;
//	}
	/** 
	* @Title: getEvalWinAmount 
	* @Description:根据是否多奖金模式获取预期奖金
	* @param slip
	* @param isWin
	* @param record 投注号码
	* @param resultCode 开奖号码
	* @return
	 * @throws Exception 
	*/
	public Long getEvalWinAmount(GameSlip slip, IWinResultBean winResultBean, String record, String resultCode) throws Exception { 
		long winNum = 0L;
		int moneyModeVal=slip.getMoneyMode().longValue()==MoneyMode.JIAO.getValue()?10:(slip.getMoneyMode().longValue()==MoneyMode.FEN.getValue()?100:1);
		List<String> listCode=new ArrayList<String>();
		if(Long.valueOf(99109).equals(slip.getLotteryid())){
			listCode.add(resultCode.substring(0,3));
			listCode.add(resultCode.substring(3));
		}else{
			listCode.add(resultCode);
		}		
		//判断是否有变价
		List<GamePoint> pointList = gamePointDao.getGamePointsBySlipId(slip.getId(),listCode);
		if (pointList != null && !pointList.isEmpty()) {
			Long winAmountResult = 0L;
			//通过变价表获取中奖金额
			for (GamePoint gamePoint : pointList) {
				winAmountResult += gamePoint.getMult() * gamePoint.getRetValue() * gamePoint.getSingleBet();
				if("31_14_10".equals(slip.getBetTypeCode())){//排5一星定位  含前面3位  和 后面2位和值					
					winNum += gamePoint.getSingleBet() ;
				}else{
					winNum = gamePoint.getSingleBet() ;	
				}				
			}
			log.info("返回变价结果");
			slip.setWinNumber(Long.valueOf(winNum));	
			//角模式转换
			//gamepoint 中存的改成除以10的了  winAmountResult=winAmountResult/moneyModeVal;
			return winAmountResult;
		}
		//非变价中奖
		String[] betTypes = slip.getBetTypeCode().split("_");
		log.info("获取中奖金额： lotteryId=" + slip.getLotteryid() + ",groupCode = " + betTypes[0] + ", setCode = "
				+ betTypes[1] + ", methodCode=" + betTypes[2] + ",record=" + record);
		Long winAmountResult = 0L;
		/**
		 * 添加超级对子 逻辑 双色球暂不支持
		 */
		boolean isResultSuperPair=false;
		isResultSuperPair = getSuperPair(betTypes[0],resultCode);
		
		if (winResultBean instanceof WinResultSingleBean) {
			WinResultSingleBean winResultSingleBean = (WinResultSingleBean) winResultBean;
			if(SuperPairUtil.isSuperPair(slip.getBetTypeCode())){
				winAmountResult = (isResultSuperPair?slip.getSingleWinDown():slip.getSingleWin())* winResultSingleBean.getSingleWin();
			}else{
				if(Long.valueOf(99701).equals(slip.getLotteryid())){
					//六合彩特碼算法
					winAmountResult = slip.getTotamount()*winResultSingleBean.getSingleWin()/amtUnit;
				}else{
					winAmountResult = slip.getSingleWin()* winResultSingleBean.getSingleWin();					
				}
			}
			log.info("获取中奖金额, 返回金额为 " + winAmountResult);
			slip.setWinNumber(winResultSingleBean.getSingleWin().longValue());
		} else {


			WinResultMultBean winResultMultBean = (WinResultMultBean) winResultBean;
			List<SlipItemAssist> resultList = new ArrayList<SlipItemAssist>();
			
			if(slip.getLotteryid().longValue() == 99401L){
		
				GameIssue issue = gameIssueDao.getGameIssueByLotteryIssue(slip.getLotteryid(), slip.getIssueCode());
				String prefix =  slip.getGameGroupCode() + "_" + slip.getGameSetCode() + "_" + slip.getBetMethodCode() + "_";
				GameBonusJsonBean  bonus = JsonMapper.nonEmptyMapper().fromJson(issue.getAwardStruct(), GameBonusJsonBean.class);;
				List<GameBonusAwardJsonBean> awards = bonus.getAwards();
				List<Long> level = new ArrayList<Long>();  
				long oneMoney = 0;
				long twoMoney = 0;
				long winAmountResultD = 0;
				for (Entry<String, Integer> temp : winResultMultBean.getMultWin().entrySet()) { 
					//普通中奖计算
					for (GameBonusAwardJsonBean award : awards) {
						if ((award.getMinAward()== 0 ||award.getMinAward().equals(""))
								&&  StringUtils.equals(temp.getKey(), award.getGameBetType())) {
							winAmountResultD= award.getMaxAward() * temp.getValue()* slip.getMultiple();
							//双色球 需要区分元角模式
							winAmountResultD=winAmountResultD/moneyModeVal;
							winAmountResult += winAmountResultD;
							if(temp.getKey().equals(prefix +"1")){
								oneMoney = award.getMaxAward()* slip.getMultiple();
							}
							if(temp.getKey().equals(prefix +"2")){
								twoMoney = award.getMaxAward()* slip.getMultiple();
							}
							winNum += temp.getValue();  
						}			
					}
					Long winLevel=0L; 
					//记录中大奖的数目
					if (temp.getKey().equals(prefix +"1") && temp.getValue().intValue() != 0 ) {
						winLevel=1L; 
						winAmountResult -= oneMoney*temp.getValue()/moneyModeVal;
						slip.setAwardOne(slip.getAwardOne() + temp.getValue()* slip.getMultiple().intValue());  
						level.add(1L);
					}else if(temp.getKey().equals(prefix +"2") && temp.getValue().intValue() != 0 ){
						winLevel=2L;
						winAmountResult -= twoMoney*temp.getValue()/moneyModeVal;
						slip.setAwardTwo(slip.getAwardTwo() + temp.getValue()* slip.getMultiple().intValue());
						level.add(2L);
					}else if(temp.getKey().equals(prefix +"3") && temp.getValue().intValue() != 0 ){			
						winLevel=3L;
						level.add(3L);
					}else if(temp.getKey().equals(prefix +"4") && temp.getValue().intValue() != 0 ){		
						winLevel=4L;
						level.add(4L);
					}else if(temp.getKey().equals(prefix +"5") && temp.getValue().intValue() != 0 ){		
						winLevel=5L;
						level.add(5L);
					}else if(temp.getKey().equals(prefix +"6") && temp.getValue().intValue() != 0 ){		
						winLevel=6L;
						level.add(6L);
					}		
					
					GameSlipDetail slipDetail=new GameSlipDetail();
					slipDetail.setParentId(slip.getId());
					slipDetail.setBetTypeCode(temp.getKey());
					slipDetail.setRealNumber("waiting");
					slipDetail.setStatus(GameSlipDetail.Status.WIN.getValue());
					slipDetail.setWinLevel(Integer.valueOf((winLevel.intValue())));
					slipDetail.setWinAmount(winAmountResultD);
					slipDetail.setCreateTime(DateUtils.currentDate());
					//this.gameSlipDetailService.save(null, slip, slipDetail);
				}
				if(!level.isEmpty()){
					Collections.sort(level);
					slip.setWinLevel(level.get(0));
				}
				slip.setWinNumber(Long.valueOf(winNum));
			}else{
				resultList = slipItemAssistDaoImpl.getSlipAssistItemList(slip.getId());	
				for (Entry<String, Integer> temp : winResultMultBean.getMultWin().entrySet()) {
					for (SlipItemAssist slipItemAssist : resultList) {
						boolean isSuperPair=SuperPairUtil.isSuperPair(slipItemAssist.getBetTypeCode());
						String betTypeCode=isSuperPair?SuperPairUtil.getCommBetTypeCode(slipItemAssist.getBetTypeCode()):slipItemAssist.getBetTypeCode();
						if (StringUtils.equals(temp.getKey(), betTypeCode)) {
							if(isSuperPair){
								winAmountResult += (isResultSuperPair?slipItemAssist.getEvaluatAwardDown():slipItemAssist.getEvaluatAward()) * temp.getValue();
							}else{
								winAmountResult += slipItemAssist.getEvaluatAward() * temp.getValue();
							}
							slipItemAssist.setWinNumber(temp.getValue().longValue());
							winNum+=temp.getValue();
							
							GameSlipAssist vo = VOConverter.convertSlipAssistEntity2Vo(slipItemAssist);
							//slipItemAssistDaoImpl.update(vo); 	
						}
					}
				}
				slip.setWinNumber(Long.valueOf(winNum));				
			} 
			log.info("获取中奖金额lotteryId=" + slip.getLotteryid() + ", groupCode=" + slip.getGameGroupCode() + ",setCode="
					+ slip.getGameSetCode() + ",  winAmount=" + winAmountResult);
		}
		return winAmountResult;
	}
	
	/** 
	* @Title: getEvalWinAmount 
	* @Description:根据是否多奖金模式获取预期奖金
	* @param slip
	* @param isWin
	* @param record 投注号码
	* @param resultCode 开奖号码
	* @return
	 * @throws Exception 
	*/
	public Long getEvalWinAmount_ByHand(GameSlip slip, IWinResultBean winResultBean, String record, String resultCode) throws Exception { 
		long winNum = 0L;
		int moneyModeVal=slip.getMoneyMode().longValue()==MoneyMode.JIAO.getValue()?10:(slip.getMoneyMode().longValue()==MoneyMode.FEN.getValue()?100:1);
		List<String> listCode=new ArrayList<String>();
		if(Long.valueOf(99109).equals(slip.getLotteryid())){
			listCode.add(resultCode.substring(0,3));
			listCode.add(resultCode.substring(3));
		}else{
			listCode.add(resultCode);
		}		
		//判断是否有变价
		List<GamePoint> pointList = gamePointDao.getGamePointsBySlipId(slip.getId(),listCode);
		if (pointList != null && !pointList.isEmpty()) {
			Long winAmountResult = 0L;
			//通过变价表获取中奖金额
			for (GamePoint gamePoint : pointList) {
				winAmountResult += gamePoint.getMult() * gamePoint.getRetValue() * gamePoint.getSingleBet();
				if("31_14_10".equals(slip.getBetTypeCode())){//排5一星定位  含前面3位  和 后面2位和值					
					winNum += gamePoint.getSingleBet() ;
				}else{
					winNum = gamePoint.getSingleBet() ;	
				}				
			}
			log.info("返回变价结果");
			slip.setWinNumber(Long.valueOf(winNum));	
			//角模式转换
			//gamepoint 中存的改成除以10的了  winAmountResult=winAmountResult/moneyModeVal;
			return winAmountResult;
		}
		//非变价中奖
		String[] betTypes = slip.getBetTypeCode().split("_");
		log.info("获取中奖金额： lotteryId=" + slip.getLotteryid() + ",groupCode = " + betTypes[0] + ", setCode = "
				+ betTypes[1] + ", methodCode=" + betTypes[2] + ",record=" + record);
		Long winAmountResult = 0L;
		/**
		 * 添加超级对子 逻辑 双色球暂不支持
		 */
		boolean isResultSuperPair=false;
		isResultSuperPair = getSuperPair(betTypes[0],resultCode);
		
		if (winResultBean instanceof WinResultSingleBean) {
			WinResultSingleBean winResultSingleBean = (WinResultSingleBean) winResultBean;
			if(SuperPairUtil.isSuperPair(slip.getBetTypeCode())){
				winAmountResult = (isResultSuperPair?slip.getSingleWinDown():slip.getSingleWin())* winResultSingleBean.getSingleWin();
			}else{
				if(Long.valueOf(99701).equals(slip.getLotteryid())){
					//六合彩特碼算法
					winAmountResult = slip.getTotamount()*winResultSingleBean.getSingleWin()/amtUnit;
				}else{
					winAmountResult = slip.getSingleWin()* winResultSingleBean.getSingleWin();					
				}
			}
			log.info("获取中奖金额, 返回金额为 " + winAmountResult);
			slip.setWinNumber(winResultSingleBean.getSingleWin().longValue());
		} else {


			WinResultMultBean winResultMultBean = (WinResultMultBean) winResultBean;
			List<SlipItemAssist> resultList = new ArrayList<SlipItemAssist>();
			
			if(slip.getLotteryid().longValue() == 99401L){
		
				GameIssue issue = gameIssueDao.getGameIssueByLotteryIssue(slip.getLotteryid(), slip.getIssueCode());
				String prefix =  slip.getGameGroupCode() + "_" + slip.getGameSetCode() + "_" + slip.getBetMethodCode() + "_";
				GameBonusJsonBean  bonus = JsonMapper.nonEmptyMapper().fromJson(issue.getAwardStruct(), GameBonusJsonBean.class);;
				List<GameBonusAwardJsonBean> awards = bonus.getAwards();
				List<Long> level = new ArrayList<Long>();  
				long oneMoney = 0;
				long twoMoney = 0;
				long winAmountResultD = 0;
				for (Entry<String, Integer> temp : winResultMultBean.getMultWin().entrySet()) { 
					//普通中奖计算
					for (GameBonusAwardJsonBean award : awards) {
						if ((award.getMinAward()== 0 ||award.getMinAward().equals(""))
								&&  StringUtils.equals(temp.getKey(), award.getGameBetType())) {
							winAmountResultD= award.getMaxAward() * temp.getValue()* slip.getMultiple();
							//双色球 需要区分元角模式
							winAmountResultD=winAmountResultD/moneyModeVal;
							winAmountResult += winAmountResultD;
							if(temp.getKey().equals(prefix +"1")){
								oneMoney = award.getMaxAward()* slip.getMultiple();
							}
							if(temp.getKey().equals(prefix +"2")){
								twoMoney = award.getMaxAward()* slip.getMultiple();
							}
							winNum += temp.getValue();  
						}			
					}
					Long winLevel=0L; 
					//记录中大奖的数目
					if (temp.getKey().equals(prefix +"1") && temp.getValue().intValue() != 0 ) {
						winLevel=1L; 
						winAmountResult -= oneMoney*temp.getValue()/moneyModeVal;
						slip.setAwardOne(slip.getAwardOne() + temp.getValue()* slip.getMultiple().intValue());  
						level.add(1L);
					}else if(temp.getKey().equals(prefix +"2") && temp.getValue().intValue() != 0 ){
						winLevel=2L;
						winAmountResult -= twoMoney*temp.getValue()/moneyModeVal;
						slip.setAwardTwo(slip.getAwardTwo() + temp.getValue()* slip.getMultiple().intValue());
						level.add(2L);
					}else if(temp.getKey().equals(prefix +"3") && temp.getValue().intValue() != 0 ){			
						winLevel=3L;
						level.add(3L);
					}else if(temp.getKey().equals(prefix +"4") && temp.getValue().intValue() != 0 ){		
						winLevel=4L;
						level.add(4L);
					}else if(temp.getKey().equals(prefix +"5") && temp.getValue().intValue() != 0 ){		
						winLevel=5L;
						level.add(5L);
					}else if(temp.getKey().equals(prefix +"6") && temp.getValue().intValue() != 0 ){		
						winLevel=6L;
						level.add(6L);
					}		
					
					GameSlipDetail slipDetail=new GameSlipDetail();
					slipDetail.setParentId(slip.getId());
					slipDetail.setBetTypeCode(temp.getKey());
					slipDetail.setRealNumber("waiting");
					slipDetail.setStatus(GameSlipDetail.Status.WIN.getValue());
					slipDetail.setWinLevel(Integer.valueOf((winLevel.intValue())));
					slipDetail.setWinAmount(winAmountResultD);
					slipDetail.setCreateTime(DateUtils.currentDate());
					//this.gameSlipDetailService.save(null, slip, slipDetail);
				}
				if(!level.isEmpty()){
					Collections.sort(level);
					slip.setWinLevel(level.get(0));
				}
				slip.setWinNumber(Long.valueOf(winNum));
			}else{
				resultList = slipItemAssistDaoImpl.getSlipAssistItemList(slip.getId());	
				for (Entry<String, Integer> temp : winResultMultBean.getMultWin().entrySet()) {
					for (SlipItemAssist slipItemAssist : resultList) {
						boolean isSuperPair=SuperPairUtil.isSuperPair(slipItemAssist.getBetTypeCode());
						String betTypeCode=isSuperPair?SuperPairUtil.getCommBetTypeCode(slipItemAssist.getBetTypeCode()):slipItemAssist.getBetTypeCode();
						if (StringUtils.equals(temp.getKey(), betTypeCode)) {
							if(isSuperPair){
								winAmountResult += (isResultSuperPair?slipItemAssist.getEvaluatAwardDown():slipItemAssist.getEvaluatAward()) * temp.getValue();
							}else{
								winAmountResult += slipItemAssist.getEvaluatAward() * temp.getValue();
							}
							slipItemAssist.setWinNumber(temp.getValue().longValue());
							winNum+=temp.getValue();
							
							GameSlipAssist vo = VOConverter.convertSlipAssistEntity2Vo(slipItemAssist);
							//slipItemAssistDaoImpl.update(vo); 	
						}
					}
				}
				slip.setWinNumber(Long.valueOf(winNum));				
			} 
			log.info("获取中奖金额lotteryId=" + slip.getLotteryid() + ", groupCode=" + slip.getGameGroupCode() + ",setCode="
					+ slip.getGameSetCode() + ",  winAmount=" + winAmountResult);
		}
		return winAmountResult;
	}
	
	private boolean getSuperPair(String groupCode, String resultCode) {
		//新增超級2000秒秒彩四星、中三
		if(groupCode.equals("45") || groupCode.equals("47") || groupCode.equals("48") || 
				groupCode.equals("50") || groupCode.equals("51") || groupCode.equals("52") ){
			return isSuperPairQianer(resultCode);
		}
		if(groupCode.equals("46") || groupCode.equals("49")){
			return isSuperPairHouer(resultCode);
		}
		//目前未使用
		//if(groupCode.equals("51")){
		//	return isSuperPairZhongsan(resultCode);
		//}
		return false;
	}
	
	//前面兩個號碼比對
	private boolean isSuperPairQianer(String numberRecord){
		if(null!=numberRecord){
			if(numberRecord.length()==5){
				return numberRecord.substring(0,1).equals(numberRecord.substring(1,2));
			}
		}
		return false;
	}
	//後面兩個號碼比對
	private boolean isSuperPairHouer(String numberRecord){
		if(null!=numberRecord){
			if(numberRecord.length()==5){
				return numberRecord.substring(3,4).equals(numberRecord.substring(4,5));
			}
		}
		return false;
	}
	//前後各一個號碼比對
	private boolean isSuperPairZhongsan(String numberRecord){
		if(null!=numberRecord){
			if(numberRecord.length()==5){
				return numberRecord.substring(0,1).equals(numberRecord.substring(4,5));
			}
		}
		return false;
	}

}
