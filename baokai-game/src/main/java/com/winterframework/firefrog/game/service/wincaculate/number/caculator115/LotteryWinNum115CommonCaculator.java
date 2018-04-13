package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultSingleBean;

/** 
* @ClassName LotteryWinNum115ZhuXFSCaculator 
* @Description 11选5 组选公共处理类
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115CommonCaculator{

	
	public IWinResultBean getZhuXWinSlipNum(List<String> simpleBetDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {
				
		List<String> simpleResultCode = getResultCode(resultCode, context);

		int winNum = simpleBetDetail.containsAll(simpleResultCode) ? 1 : 0;	
		return new WinResultSingleBean(winNum);
	}
	
	
	public List<String> getResultCode(String resultCode, ILotterySlipNumCaculatorContext context){
		if (context != null && context.getResultCodeBeginIndex() != null && context.getResultCodeEndIndex() != null) {
			resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		}		
		return  Arrays.asList(resultCode.split(","));
		
	}
}