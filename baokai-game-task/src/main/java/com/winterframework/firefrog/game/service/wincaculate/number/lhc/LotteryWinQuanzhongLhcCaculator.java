package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 連碼 三全中, 二全中
 * @author user
 *
 */
public class LotteryWinQuanzhongLhcCaculator extends LotteryWinSingleLhcCaculator{
	
	/**最小投注個數*/
	private int sizeCombinations;
	
	public int getSizeCombinations() {
		return sizeCombinations;
	}
	
	public void setSizeCombinations(int sizeCombinations) {
		this.sizeCombinations = sizeCombinations;
	}

	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialNum, Set<String> zhengmas, Long singleWin) {
		Integer winCount = 0;
		List<String> allBets = LHCUtil.combinationsAllBet(betDetail, getSizeCombinations());
		for(String bet : allBets){//展開後的每一注
			boolean isWin = Boolean.TRUE;
			String[] nums = bet.split(",");
			for(String num : nums){
				if(zhengmas.contains(num)){
					
				} else {
					isWin = Boolean.FALSE;
					break;
				}
			}
			
			if(isWin){
				winCount++;
			}
		}
		
		return singleWin.intValue() * winCount;
	}

}
