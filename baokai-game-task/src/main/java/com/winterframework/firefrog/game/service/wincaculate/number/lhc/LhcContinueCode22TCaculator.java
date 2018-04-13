package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

/**
 * 連碼二中特 
 * @author user
 *
 */
public class LhcContinueCode22TCaculator implements ILotteryWinLhcCodeCaculator{

	@Override
	public Integer caculator(List<String> betDetails, String resultCode, List<GameNumberConfig> gameNumberConfigs) {
		Integer result = 0; //中二的注數
		String[] winNums = resultCode.split(",");
		Set<String> newWinNums = new HashSet<String>();
		String specialNum = ""; //特碼
		for(int i = 0 ; i < winNums.length ; i++){
			if(i < winNums.length - 1){
				//正碼
				newWinNums.add(winNums[i]);
			} else {
				//特碼
				specialNum = winNums[i];
			}
		}
		
		for(String betDetail : betDetails){
			String[] bets = betDetail.split(",");
			
			List<String> tempBets = new ArrayList<String>();
			//判斷是否存在特碼
			boolean isSpecial = Boolean.FALSE;
			for(String bet : bets){
				if(specialNum.equals(bet)){
					//特碼數字符合
					isSpecial = Boolean.TRUE;
				} else {
					//不符合
				}
				tempBets.add(bet);
			}
			if(isSpecial){
				//特碼數字符合
				tempBets.remove(specialNum); //移除特碼
				if(newWinNums.contains(tempBets.get(0))){
					//正碼符合
					result++;
				} else {
					//沒有正碼符合
					continue;
				}
			} else {
				//沒有特碼數字符合
				continue;
			}
		}
		return result;
	}

}
