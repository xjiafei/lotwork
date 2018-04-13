package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115ZhuXDTCaculator 
* @Description 11选5 组选胆拖
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115ZhuXDTCaculator extends AbstractLotteryWinSlipNumCaculator {

	private LotteryWinNum115CommonCaculator common = new LotteryWinNum115CommonCaculator();
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {
				
		List<String> simpleBetDetail = Arrays.asList(betDetail.split("]"));
		
		//获取胆号
		String danPageString = simpleBetDetail.get(0).trim();
		
		String danNumString = (danPageString.split("胆"))[1].trim();	
		List<String> danNums = Arrays.asList(danNumString.split(","));		
		//获取拖号
		List<String> tuoNums = Arrays.asList(simpleBetDetail.get(1).trim().split(","));
		//获取胆号拖号全号码
		List<String> compare = new ArrayList<String>();
		compare.addAll(danNums);
		compare.addAll(tuoNums);
		
		List<String> simpleResultCode = common.getResultCode(resultCode, context);
		
		int winNum = 0;
		if(simpleResultCode.containsAll(danNums) && compare.containsAll(simpleResultCode)){
			winNum = 1;
		}else{
			winNum = 0;
		}
		
		return generateWinResultSingleBean(winNum);
	}
}