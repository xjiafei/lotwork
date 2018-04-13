package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNum115LXFSCaculator 
* @Description 11选5 任选复式 
* 任选一中一
* 任选二中二
* 任选三中三
* 任选四中四
* 任选五中五
* 任选六中五
* 任选七中五
* 任选八中五
* 前三不定位_复式
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115LXFSCaculator extends AbstractLotteryWinSlipNumCaculator {

	private LotteryWinNum115CommonCaculator common = new LotteryWinNum115CommonCaculator();	
	
	/** 
	* @Fields lenXunType : 任选类型  比如：任选一 那么 值就是 1
	*/
	private int lenXunType;
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int winNum = 0;

		List<String> simpleResultCode = common.getResultCode(resultCode, context);
		List<String> simpleBetDetail = Arrays.asList(betDetail.split(","));
		//获取组合
		List<String> simpleBetDetails = CaculateUtil.getCombinations(simpleBetDetail, lenXunType);
		for (String string : simpleBetDetails) {
			List<String> simpleTureBetDetail = Arrays.asList(string.split(","));
			//包含对方全部，或被对方全部包含
			if (CaculateUtil.reflexiveContainAll(simpleResultCode, simpleTureBetDetail)) {
				winNum++;
			}
		}
		return generateWinResultSingleBean(winNum);
	}

	public int getLenXunType() {
		return lenXunType;
	}

	public void setLenXunType(int lenXunType) {
		this.lenXunType = lenXunType;
	}

}