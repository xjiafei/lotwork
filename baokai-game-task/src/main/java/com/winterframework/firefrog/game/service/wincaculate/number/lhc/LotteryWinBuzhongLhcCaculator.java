package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 正特碼  不中
 * @author user
 */
public class LotteryWinBuzhongLhcCaculator extends LotteryWinSingleLhcCaculator{
	
	/**最小投注個數*/
	private int sizeCombinations;
	
	public int getSizeCombinations() {
		return sizeCombinations;
	}
	
	public void setSizeCombinations(int sizeCombinations) {
		this.sizeCombinations = sizeCombinations;
	}
	
	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialNum,Set<String> zhengmas, Long singleWin) {
		Integer winCount = 0;
		zhengmas.add(specialNum);
		List<String> allBets = LHCUtil.combinationsAllBet(betDetail, getSizeCombinations());
		for(String bet : allBets){//展開後的每一注
			boolean isWin = Boolean.TRUE;
			String[] nums = bet.split(",");
			for(String num : nums){
				if(zhengmas.contains(num)){
					isWin = Boolean.FALSE;
					break;
				} else {

				}
			}
			
			if(isWin){
				winCount++;
			}
		}
		
		return singleWin.intValue() * winCount;
	}

}
