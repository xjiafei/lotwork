package com.winterframework.firefrog.game.web.validate.business;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidatorFactory;

/** 
* @ClassName: BetValidatorFactory 
* @Description: 按一定规则来匹配选择哪一个验证器。 按递减方式来逐个匹配，先是4个key factor都匹配，其次是能匹配3个，类推，最后匹配1个
* @author page
* @date 2013-8-6 下午3:27:40 
*  
*/
public class BetValidatorFactory<GameSlip> implements IValidatorFactory<GameSlip> {
	
	private Map<String, Map<LotteryPlayMethodKeyGenerator, IValidateExecutor<GameSlip>>> executorMaps;


	/**
	* Title: getValidateExecutor
	* Description:
	* @param keyGenerator
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.IValidatorFactory#getValidateExecutor(com.winterframework.firefrog.common.validate.business.IKeyGenerator) 
	*/
	@Override
	public IValidateExecutor<GameSlip> getValidateExecutor(String lotteryCode, IKeyGenerator keyGenerator) {
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = (LotteryPlayMethodKeyGenerator) keyGenerator;
		LotteryPlayMethodKeyGenerator cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();

		IValidateExecutor<GameSlip> matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
		if (matchedValidator != null) {
			return matchedValidator;
		}

		cloneKeyGenerator.setLotteryType(null);
		matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
		if (matchedValidator != null) {
			return matchedValidator;
		}

		cloneKeyGenerator.setGroupCode(null);
		matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
		if (matchedValidator != null) {
			return matchedValidator;
		}

		cloneKeyGenerator.setSetCode(null);
		matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
		if (matchedValidator != null) {
			return matchedValidator;
		}
        //判断玩法为空的时候
        cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();
        cloneKeyGenerator.setLotteryType(null);
        cloneKeyGenerator.setMethodCode(null);
        matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
        if (matchedValidator != null) {
            return matchedValidator;
        }

        cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();
        cloneKeyGenerator.setLotteryType(null);
        cloneKeyGenerator.setSetCode(null);
        matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
        if (matchedValidator != null) {
            return matchedValidator;
        }
        cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();
        cloneKeyGenerator.setGroupCode(null);
        cloneKeyGenerator.setSetCode(null);
        cloneKeyGenerator.setMethodCode(null);
        matchedValidator = getValidateExecutorMap(lotteryCode).get(cloneKeyGenerator);
        if (matchedValidator != null) {
            return matchedValidator;
        }
		return null;
	}


	public Map<String, Map<LotteryPlayMethodKeyGenerator, IValidateExecutor<GameSlip>>> getExecutorMaps() {
		return executorMaps;
	}


	public void setExecutorMaps(Map<String, Map<LotteryPlayMethodKeyGenerator, IValidateExecutor<GameSlip>>> executorMaps) {
		this.executorMaps = executorMaps;
	}
	
	public Map<LotteryPlayMethodKeyGenerator, IValidateExecutor<GameSlip>> getValidateExecutorMap(String lotteryCode) {
		return executorMaps.get(lotteryCode);
	}


}
