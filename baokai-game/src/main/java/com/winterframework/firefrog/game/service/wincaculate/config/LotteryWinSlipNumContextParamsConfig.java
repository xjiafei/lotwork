package com.winterframework.firefrog.game.service.wincaculate.config;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.IContextParamsConfig;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;

/** 
* @ClassName LotteryWinSlipNumContextParamsConfig 
* @Description 上下文参数实现类 
* @author  hugh
* @date 2014年3月21日 下午5:10:02 
*  
*/
public class LotteryWinSlipNumContextParamsConfig implements IContextParamsConfig<ILotterySlipNumCaculatorContext> {

	@Override
	public ILotterySlipNumCaculatorContext setConfig(ILotterySlipNumCaculatorContext context) {
		if (context == null) {
			context = new LotterySlipNumCaculatorContext();
		}
		context.setResultCodeBeginIndex(resultCodeBeginIndex);
		context.setResultCodeEndIndex(resultCodeEndIndex);
		context.setParamMap(paramMap);
		return context;
	}

	public Map<String, Object> paramMap = new HashMap<String, Object>();

	public Integer resultCodeBeginIndex;
	public Integer resultCodeEndIndex;

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Integer getResultCodeBeginIndex() {
		return resultCodeBeginIndex;
	}

	public void setResultCodeBeginIndex(Integer resultCodeBeginIndex) {
		this.resultCodeBeginIndex = resultCodeBeginIndex;
	}

	public Integer getResultCodeEndIndex() {
		return resultCodeEndIndex;
	}

	public void setResultCodeEndIndex(Integer resultCodeEndIndex) {
		this.resultCodeEndIndex = resultCodeEndIndex;
	}

}
