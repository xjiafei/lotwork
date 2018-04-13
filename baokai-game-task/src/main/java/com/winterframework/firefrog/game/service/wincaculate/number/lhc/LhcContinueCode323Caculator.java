package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

/**
 * 連碼三中二 中三
 * @author user
 *
 */
public class LhcContinueCode323Caculator implements ILotteryWinLhcCodeCaculator{

	@Override
	public Integer caculator(List<String> betDetails, String resultCode, List<GameNumberConfig> gameNumberConfigs) {
		Integer result = 0; //中二的注數
		String[] winNums = resultCode.split(",");
		Set<String> newWinNums = new HashSet<String>();
		for(int i = 0 ; i < winNums.length ; i++){
			if(i < winNums.length - 1){
				//正碼
				newWinNums.add(winNums[i]);
			} else {
				//特碼
			}
		}
		
		for(String betDetail : betDetails){
			String[] bets = betDetail.split(",");
			Integer count = 0;
			for(String bet : bets){
				if(newWinNums.contains(bet)){
					//開獎數字符合
					count++;
				} else {
					//不符合
				}
			}
			//中三才加入
			if(count.equals(3)){
				result++;
			}
		}
		return result;
	}
}
