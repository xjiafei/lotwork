package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3ThreeNotSameDTCaculator 
* @Description 快3三不同号（胆拖选号）
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3ThreeNotSameDTCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		//D:3_T:1,2,4,5,6
		int win = 0;
		String dan=betDetail.split("_T:")[0].substring(2);
		String tuo=betDetail.split("_T:")[1];
		String[] dans =dan.split(",");	
		String[] tuos = tuo.split(","); 
		List<String> tuoCombList=CaculateUtil.getCombinations(Arrays.asList(tuos), 3-dans.length); 		
		//3号计算最多只可能中一注
		for(String tuoComb:tuoCombList){ 
			List<String> numberList =CaculateUtil.pailie(dan.replace(",", "")+tuoComb.replace(",", ""));
			for(String number:numberList){
				if(number.equals(resultCode)){
					win++;
					break;
				}
			}
		}   
		return generateWinResultSingleBean(win);
	}

}
