package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.entity.LotteryWinLhcMap;

/**
 * 兩面
 * @author Ami.Tsai
 *
 */
public class LotteryWinSpecialDoubleFaceLhcCaculator extends LotteryWinSingleLhcCaculator {
	
	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialCode, Set<String> zhengmas, Long singleWin) {
		String[] wimNums = LotteryWinLhcMap.NUM_MAP.get(betDetail);
		Boolean isWin = false;

		if(EQUZL_NUMBER.equals(specialCode)){
			//若開出49則為和
			return 10000;
		}else{
			//大小肖需要額外判斷方式
			if(betDetail.equals("小肖")|| betDetail.equals("大肖")){
				//查詢該年度生肖設定檔
				List<GameNumberConfig> numberConfigs = findThisYearNumberConfig(orderTime);
				
				for(String win:wimNums){
					for(GameNumberConfig numConfig:numberConfigs){
						if(numConfig.getNumType().equals(win)){
							//該生肖是否有特碼
							if(numConfig.getGameNumber().indexOf(specialCode)!=-1){
								isWin = true;
							}
						}
					}				
				}
			} else{
				isWin = Arrays.asList(wimNums).contains(specialCode);	
			}	
		}
		
		if(isWin){
			return singleWin.intValue();
		}else{
			return 0;
		}
		
	}
}
