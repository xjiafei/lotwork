package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
 * 处理如下彩种
 * 前三直选和值 0, 3 ，false
 * 后三直选和值2,5,false
*  后二直选和值3,5,false
*  前二直选和值0, 2,false
*  前三组选组选和值0, 3,true
*  后三组选组选和值2,5,true
*  后二组选和值3,5,true
*  前二组选和值0, 2,true
*/
public class LotteryWinNumHZCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 是否校验豹子信息
	*/
	//private boolean isCheckBaoZi;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
	
		String[] betNumbers;//存储投注内容分割后的值
		int hezhi = 0;//和值变量
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//		if (isCheckBaoZi) {
		//			if (CaculateUtil.count(resultCode) == 1) {// 本期开奖为豹子号, 则本期全部组选和值未中奖.
		//				return 0;
		//			}
		//		}
		hezhi = CaculateUtil.getHezhi(resultCode);
		betNumbers = betDetail.split(",");
		Integer win = CaculateUtil.isExist(betNumbers, hezhi) == true ? 1 : 0;
		LotteryPlayMethodKeyGenerator keyGenerator = (LotteryPlayMethodKeyGenerator) context.getKeyGenerator();
		
		if(resultCode.length() ==3 && keyGenerator.getSetCode().intValue() == 11){
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
}