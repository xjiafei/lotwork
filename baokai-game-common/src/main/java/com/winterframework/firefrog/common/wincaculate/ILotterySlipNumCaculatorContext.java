package com.winterframework.firefrog.common.wincaculate;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;

public interface ILotterySlipNumCaculatorContext {
	IKeyGenerator getKeyGenerator();

	void setKeyGenerator(IKeyGenerator keyGenerator);
	
	Map<String, Object> getParamMap();

	void setParamMap(Map<String, Object> paramMap);
	
	public Integer getResultCodeBeginIndex();

	public void setResultCodeBeginIndex(Integer resultCodeBeginIndex);

	public Integer getResultCodeEndIndex();

	public void setResultCodeEndIndex(Integer resultCodeEndIndex);
	
	String getResultCode(String resultCode);
}
