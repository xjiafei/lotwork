package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 連碼 特串
 * @author user
 *
 */
public class LotteryWinTechuanLhcCaculator extends LotteryWinSingleLhcCaculator{
	
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
		
		//展開所有可中獎組合
		Set<Set<String>> allWinNumbers = new HashSet<Set<String>>();
		for(String zhengma : zhengmas){
			Set<String> zhengAndTema = new HashSet<String>();
			zhengAndTema.add(specialNum);
			zhengAndTema.add(zhengma);
			allWinNumbers.add(zhengAndTema);
		}
		
		//投注的所有組合
		List<String> allBets = LHCUtil.combinationsAllBet(betDetail, getSizeCombinations());
		
		for(String bet : allBets){//展開後的每一注
			String[] nums = bet.split(",");
			List<String> bets = Arrays.asList(nums);
			
			for(Set<String> winNumbers : allWinNumbers){
				if(winNumbers.containsAll(bets)){
					winCount++;
				} else {
					//沒中獎
				}
			}
		}
		
		return singleWin.intValue() * winCount;
	}

}
