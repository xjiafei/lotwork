package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
 * 处理如下彩种
 * 前三组选组选包胆  0, 3
 * 后三组选组选包胆 2,5
*  后二组选包胆 3,5
*  前二组选包胆 0, 2
*/
public class LotteryWinNumZXBDCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());

		
		Integer win = resultCode.contains(betDetail) == true ? 1 : 0;
		if(resultCode.length() ==3){
			String prefix = "";		
			//组三
			if (CaculateUtil.count(resultCode) == 2) {
				prefix = "62";
			} else {
				//组六
				prefix= "63";
			}
			Map<String, Integer> resultMap = new HashMap<String, Integer>();
			resultMap.put(prefix, win);
			return generateWinResultMultBean(resultMap, context);
		}else{
			return generateWinResultSingleBean(win);
		}
	
	}
	
	
	public static void main(String[] args) {
		String resultCode = "557";
		String betDetail = "575";
		String[] rNumbers = CaculateUtil.getResultNumbers(resultCode);
		String[] bNumbers = CaculateUtil.getResultNumbers(betDetail);
		Arrays.sort(rNumbers);
		Arrays.sort(bNumbers);
		Integer win =Arrays.equals(rNumbers, bNumbers)== true ? 1 : 0;
		System.out.println(win);
	}
}