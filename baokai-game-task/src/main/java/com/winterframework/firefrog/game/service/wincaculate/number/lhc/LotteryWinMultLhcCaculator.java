package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 六合彩 mult result主流程
 * @author user
 *
 */
public abstract class LotteryWinMultLhcCaculator extends LotteryWinLhcCaculator {

	private static final Logger log = LoggerFactory.getLogger(LotteryWinMultLhcCaculator.class);
	
	/**methodType 暫存*/
	private static final Map<String, List<GameAward>> bettypeAssists = new HashMap<String, List<GameAward>>(); 
	
	/**最小投注個數*/
	private int sizeCombinations;
	
	protected Map<String, ILotteryWinLhcCodeCaculator> lhcCodeCaculators;
	
	public Map<String, ILotteryWinLhcCodeCaculator> getLhcCodeCaculators() {
		return lhcCodeCaculators;
	}

	public void setLhcCodeCaculators(Map<String, ILotteryWinLhcCodeCaculator> lhcCodeCaculators) {
		this.lhcCodeCaculators = lhcCodeCaculators;
	}

	public int getSizeCombinations() {
		return sizeCombinations;
	}

	public void setSizeCombinations(int sizeCombinations) {
		this.sizeCombinations = sizeCombinations;
	}
	
	@Resource(name = "gameAwardDaoImpl")
	protected IGameAwardDao gameAwardDaoImpl;
	
	protected  List<GameAward> findGameAwardByBetTypeCode(Long lotteryId, String betTypeCode, Long awardGroupId) throws Exception{
		if(bettypeAssists.containsKey(betTypeCode)){
			return bettypeAssists.get(betTypeCode);
		} else {
			//對獎
			List<GameAward> gameAwards = gameAwardDaoImpl.getGameAwardByGroupIdAndBetTypeCodeParent(awardGroupId, betTypeCode);
			bettypeAssists.put(betTypeCode, gameAwards);
			return gameAwards;
		}
	}
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		if(StringUtils.isBlank(betDetail)){
			throw new Exception(" LotteryWinSpecialNumLhcCaculator 投注內容錯誤:="+betDetail);
		}
		if(StringUtils.isBlank(resultCode)){
			throw new Exception(" LotteryWinSpecialNumLhcCaculator 開獎內容錯誤:="+resultCode);
		}
		log.debug("LotteryWinSpecialNumLhcCaculator, 投注内容：BetDetail="+ betDetail + ", 开奖号码 resultCode="+ resultCode);
		
		//展開投注細節
		List<String> tempBetDetails = LHCUtil.combinationsAllBet(betDetail, getSizeCombinations());
		
		LotteryPlayMethodKeyGenerator keyGenerator = (LotteryPlayMethodKeyGenerator) context.getKeyGenerator();
		
		List<GameAward> gameAwards = findGameAwardByBetTypeCode(keyGenerator.getLotteryType(), 
				keyGenerator.getBetTypeCode(), 
				(Long) context.getParamMap().get("awardGroupId"));
		Date orderTime = (Date)context.getParamMap().get("orderTime");
		Map<String, Integer> assistCodeWinNumberMap = getAssistCodeWinNumberMap(orderTime, tempBetDetails, resultCode, keyGenerator.getBetTypeCode(), gameAwards);
		
		return generateWinResultMultBean(assistCodeWinNumberMap, context);
	}
	
	protected abstract Map<String, Integer> getAssistCodeWinNumberMap(Date orderTime, List<String> betDetails, String resultCode, 
			String betTypeCode, List<GameAward> gameAwards );
}
