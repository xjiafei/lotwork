package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;

/** 
 * 处理如下彩种
 * 六合彩特碼
*/
public abstract class LotteryWinSingleLhcCaculator extends LotteryWinLhcCaculator {
	
	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	protected Map<String,String[]> winToBetTypeMap;
	
	private static final Logger log = LoggerFactory.getLogger(LotteryWinSingleLhcCaculator.class);
	
	protected final String EQUZL_NUMBER="49";
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		if(StringUtils.isBlank(betDetail)){
			throw new Exception(" LotteryWinSpecialNumLhcCaculator 投注內容錯誤:="+betDetail);
		}
		if(StringUtils.isBlank(betDetail)){
			throw new Exception(" LotteryWinSpecialNumLhcCaculator 開獎內容錯誤:="+resultCode);
		}
		log.debug("LotteryWinSpecialNumLhcCaculator, 投注内容：BetDetail="+ betDetail + ", 开奖号码 resultCode="+ resultCode);
		
		String[] winNums = resultCode.split(",");
		String specialNum = "";
		
		Set<String> zhengmas = new HashSet<String>();//正碼
		
		for(int i = 0 ; i < winNums.length ; i++){
			if(i < winNums.length - 1){
				//正碼
				zhengmas.add(winNums[i]);
			} else {
				//特碼
				specialNum = winNums[i];
			}
		}
		
		Long singleWin = (Long) context.getParamMap().get("singleWin");
		Date orderTime = (Date)context.getParamMap().get("orderTime");
		Integer winOdds = checkWinMethod(orderTime, betDetail,specialNum, zhengmas, singleWin);
		return generateWinResultSingleBean(winOdds);
	}
	
	protected abstract Integer checkWinMethod(Date orderTime, String betDetail, String specialNum, Set<String> zhengmas, Long singleWin);
	
	public Map<String, String[]> getWinToBetTypeMap() {
		return winToBetTypeMap;
	}

	public void setWinToBetTypeMap(Map<String, String[]> winToBetTypeMap) {
		this.winToBetTypeMap = winToBetTypeMap;
	}

}