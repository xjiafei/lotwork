package com.winterframework.firefrog.game.service.wincaculate.number.ssq;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNumSingleCaculator 
* @Description 双色球单式记奖 
* @author  hugh
* @date 2014年7月28日 下午4:18:21 
*  
*/
public class LotteryWinNumDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		SSQWinNumUtil winNum = new SSQWinNumUtil();
		
		//处理格式转化 begin  		
		//开奖号码		
		String[] resultCodeArrays = resultCode.split("[+]");
		String redResultCodeString = resultCodeArrays[0].trim();
		String blueResultCodeString = resultCodeArrays[1].trim();
		Set<String> redResultCodes = new HashSet<String>();
		redResultCodes.addAll(Arrays.asList(redResultCodeString.split(",")));
		String blueResultCode = blueResultCodeString;
		//处理格式转化 end

		//投注号码		
		List<String> betDetailList = Arrays.asList(betDetail.split(";"));		
		for (String string : betDetailList) {
			String[] betDetailArrays = string.trim().split("[+]");
			String redString = betDetailArrays[0].trim();
			String blueString = betDetailArrays[1].trim();		
			Set<String> redBetDetails = new HashSet<String>();
			redBetDetails.addAll(Arrays.asList(redString.split(",")));
			String blueBetDetail = blueString;	
			
			//生成SSQWinNum 计算中奖结果  多次赋值执行可累加结果
			winNum.generatorSSQWinNum(redBetDetails,blueBetDetail,redResultCodes,blueResultCode);
		}	
	
		return generateWinResultMultBean(winNum.getResultMap(), context);
	}

}
