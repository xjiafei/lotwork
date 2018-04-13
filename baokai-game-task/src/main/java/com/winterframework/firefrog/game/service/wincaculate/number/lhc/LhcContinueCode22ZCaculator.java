package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

/**
 * 連碼二中二 
 * @author user
 *
 */
public class LhcContinueCode22ZCaculator implements ILotteryWinLhcCodeCaculator{

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
			boolean isWin = Boolean.TRUE;
			for(String bet : bets){
				if(newWinNums.contains(bet)){
					//開獎數字符合
				} else {
					//不符合
					isWin = Boolean.FALSE;
				}
			}
			//加入win注數
			if(isWin){
				result++;
			}
		}
		return result;
	}
}
