package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationPlanService;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNum115LXDTCaculator 
* @Description 11选5 任选胆拖
* 任选一中一
* 任选二中二
* 任选三中三
* 任选四中四
* 任选五中五
* 任选六中五
* 任选七中五
* 任选八中五
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115LXDTCaculator extends AbstractLotteryWinSlipNumCaculator {
	private Logger log = LoggerFactory.getLogger(LotteryWinNum115LXDTCaculator.class);
	/** 
	* @Fields lenXunType : 任选类型  比如：任选一 那么 值就是 1
	*/ 
	private int lenXunType;
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {
		
		log.info("115任选胆拖计算奖金");
		log.info("lenXunType="+lenXunType);
		log.info("betDetail="+betDetail);
		log.info("resultCode="+resultCode);
		int winNum = 0;
		List<String> simpleResultCode = Arrays.asList(resultCode.split(","));
		List<String> simpleBetDetail = Arrays.asList(betDetail.split("]"));
		
		//获取胆号
		String danPageString = simpleBetDetail.get(0).trim();
		
		String danNumString = (danPageString.split("胆"))[1].trim();//[胆 01 02]		
		
		List<String> danNums = Arrays.asList(danNumString.split(","));		
		
		//获取拖号
		List<String> tuoNums = Arrays.asList(simpleBetDetail.get(1).trim().split(","));
		
		//获取组合	
		List<String> simpleBetDetails = CaculateUtil.getCombinations(tuoNums, lenXunType - danNums.size());
		
		//获取中奖数
		for (String string : simpleBetDetails) {
			List<String> compare = new ArrayList<String>();
			List<String> simpleTureBetDetail = Arrays.asList(string.split(","));
			compare.addAll(danNums);
			compare.addAll(simpleTureBetDetail);
			//包含对方全部，或被对方全部包含
			if(CaculateUtil.reflexiveContainAll(simpleResultCode, compare)){
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