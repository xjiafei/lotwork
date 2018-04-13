package com.winterframework.firefrog.game.service.wincaculate.number.ssq;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumFSCaculator 
* @Description 双色球复式记奖 
* @author  hugh
* @date 2014年7月28日 下午4:18:44 
*  
*/
public class LotteryWinNumFSCaculator extends AbstractLotteryWinSlipNumCaculator {

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
		
		//投注号码
		String[] betDetailArrays = betDetail.split("[+]");
		String redString = betDetailArrays[0].trim();
		String blueString = betDetailArrays[1].trim();		
		List<String> redBetDetailList = Arrays.asList(redString.split(","));
		//排列组合后6个值的list  ep: [1,2,3,4,5,6] [6,7,8,9,10,11]
		List<String> redBetDetail5List = CaculateUtil.getCombinations(redBetDetailList,6);
		
		Set<String> blueBetDetails = new HashSet<String>();;
		blueBetDetails.addAll(Arrays.asList(blueString.split(",")));
		//处理格式转化 end
		
		for (String blueBetDetail : blueBetDetails) {
			for (String redString5 : redBetDetail5List) {
				Set<String> redBetDetails = new HashSet<String>();
				redBetDetails.addAll(Arrays.asList(redString5.split(",")));
				//生成SSQWinNum 计算中奖结果  多次赋值执行可累加结果
				winNum.generatorSSQWinNum(redBetDetails,blueBetDetail,redResultCodes,blueResultCode);
			}		
		}
	
		return generateWinResultMultBean(winNum.getResultMap(), context);
	}

}
