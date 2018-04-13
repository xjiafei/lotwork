package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNum115LXDSCaculator 
* @Description 11选5 任选单式 
* 任选一中一
* 任选二中二
* 任选三中三
* 任选四中四
* 任选五中五
* 任选六中五
* 任选七中五
* 任选八中五
* 
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115LXDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		List<String> simpleResultCode = Arrays.asList(resultCode.split(","));
		String[] details=betDetail.split(",");
		int winNum=0;
		for(String detail:details){
			List<String> simpleBetDetail = Arrays.asList(detail.split(" "));
		//包含对方全部，或被对方全部包含
			boolean isAllHave = CaculateUtil.reflexiveContainAll(simpleResultCode, simpleBetDetail);
			winNum+=( isAllHave ? 1 : 0);
		}
		return generateWinResultSingleBean(winNum);
	}
		

}