/**   
* @Title: LotteryWinSlipNumCaculator.java 
* @Package com.winterframework.firefrog.game.service.wincaculate.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 下午5:18:29 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.wincaculate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.wincaculate.IContextParamsConfig;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultSingleBean;
import com.winterframework.firefrog.game.service.wincaculate.nowinchecker.ILotterySlipNoWinChecker;

/** 
* @ClassName: LotteryWinSlipNumCaculator 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy
* @date 2014-3-18 下午5:18:29 
*  
*/
public class LotteryWinSlipNumCaculator extends AbstractLotteryWinSlipNumCaculator {
	private static final Logger log = LoggerFactory.getLogger(LotteryWinSlipNumCaculator.class);
	private ILotteryWinSlipNumCaculator caculator;
	private ILotterySlipNoWinChecker checker;
	private IContextParamsConfig<ILotterySlipNumCaculatorContext> config;

	public IContextParamsConfig<ILotterySlipNumCaculatorContext> getConfig() {
		return config;
	}

	public void setConfig(IContextParamsConfig<ILotterySlipNumCaculatorContext> config) {
		this.config = config;
	}

	public ILotteryWinSlipNumCaculator getCaculator() {
		return caculator;
	}

	public void setCaculator(ILotteryWinSlipNumCaculator caculator) {
		this.caculator = caculator;
	}

	public ILotterySlipNoWinChecker getChecker() {
		return checker;
	}

	public void setChecker(ILotterySlipNoWinChecker checker) {
		this.checker = checker;
	}

	/**
	* Title: getWinSlipNum
	* Description:
	* @param betDetail
	* @param resultCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator#getWinSlipNum(java.lang.String, java.lang.String) 
	*/
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		if (config != null) {
			context = config.setConfig(context);
		}
		
		resultCode = dealP3ResultCodeeByLotteryKey( resultCode, context);
		
		if (checker == null && config == null) {
			log.error("该模板类没有配置正确的checker 或 config！");
			throw new Exception("该模板类没有配置正确的checker 或 config！");
		}
		if (checker != null) {
			if (this.validator(betDetail, resultCode, context)) {				
				return this.getWinSlipCaculate(betDetail, resultCode, context);
			}
		} else {
			return this.getWinSlipCaculate(betDetail, resultCode, context);
		}
		return new WinResultSingleBean(0);
	}

	private boolean validator(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		return checker.check(betDetail, resultCode, context);
	}

	private IWinResultBean getWinSlipCaculate(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception {
		// 跟踪代码  add by tanx begin
		if(caculator==null){
			log.error("无记奖处理类");
			throw new Exception("无记奖处理类");
		}
		log.debug(caculator.getClass().getName());
		
		
		
		// 跟踪代码  add by tanx end
		return caculator.getWinSlipNum(betDetail, resultCode, context);
	}
	
	
	/** 
	* @Title: dealP3ResultCodeeByLotteryKey 
	* @Description: 排三开奖号码取前3位
	* @param resultCode
	* @param context
	* @return
	*/
	private String dealP3ResultCodeeByLotteryKey(String resultCode,ILotterySlipNumCaculatorContext context){
		LotteryPlayMethodKeyGenerator keyGenerator = (LotteryPlayMethodKeyGenerator) context.getKeyGenerator();
		if(keyGenerator.getLotteryType().longValue() == 99109L && !(keyGenerator.getBetTypeCode().startsWith("30") || keyGenerator.getBetTypeCode().startsWith("31"))){
			return resultCode.substring(0, 3);
		}
		return resultCode;
	}
	
		
}
