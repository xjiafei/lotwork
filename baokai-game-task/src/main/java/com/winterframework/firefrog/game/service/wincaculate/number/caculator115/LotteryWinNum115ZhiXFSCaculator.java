package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115ZhiXFSCaculator 
* @Description 11选5 直选复式
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115ZhiXFSCaculator extends AbstractLotteryWinSlipNumCaculator {

	private LotteryWinNum115CommonCaculator common = new LotteryWinNum115CommonCaculator();	
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		
		List<String> simpleResultCode = common.getResultCode(resultCode, context);
		String[] simpleBetDetail = betDetail.split(",");

		int countEqualeNum = 0;
		outer: for (int i = 0; i < simpleResultCode.size(); i++) {

			String[] simpleWeiBetDetail = simpleBetDetail[i].split(" ");
			for (int j = 0; j < simpleWeiBetDetail.length; j++) {
				if (simpleWeiBetDetail[j].equals(simpleResultCode.get(i))) {
					countEqualeNum++;
					continue outer;
				}
			}

			//判断每一位是否都有相同的数
			if (countEqualeNum < i + 1) {
				return generateWinResultSingleBean(0);
			}
		}

		return generateWinResultSingleBean(1);
	}
}