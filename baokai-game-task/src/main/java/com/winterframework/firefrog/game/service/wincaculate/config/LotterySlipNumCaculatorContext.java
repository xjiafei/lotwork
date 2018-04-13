package com.winterframework.firefrog.game.service.wincaculate.config;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;

/** 
* @ClassName LotterySlipNumCaculatorContext 
* @Description 计算开奖数 上下文参数 
* @author  hugh
* @date 2014年3月21日 下午2:38:34 
*  
*/
public class LotterySlipNumCaculatorContext implements ILotterySlipNumCaculatorContext {

	public Map<String, Object> paramMap = new HashMap<String, Object>();

	public Integer resultCodeBeginIndex;
	public Integer resultCodeEndIndex;

	public LotterySlipNumCaculatorContext(Integer resultCodeBeginIndex, Integer resultCodeEndIndex) {
		super();
		this.resultCodeBeginIndex = resultCodeBeginIndex;
		this.resultCodeEndIndex = resultCodeEndIndex;
	}

	public LotterySlipNumCaculatorContext() {
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

	private IKeyGenerator keyGenerator;

	@Override
	public IKeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	@Override
	public void setKeyGenerator(IKeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	public String getResultCode(String resultCode){
		if(resultCodeBeginIndex !=null && resultCodeEndIndex !=null){
			
			if(resultCodeEndIndex<=resultCode.length()){
				return resultCode.substring(resultCodeBeginIndex, resultCodeEndIndex);
			}else if(resultCodeEndIndex-resultCodeBeginIndex <= resultCode.length()){
				return resultCode.substring(resultCode.length()-(resultCodeEndIndex-resultCodeBeginIndex), resultCode.length());
			}else{
				return resultCode;
			}
			
		}else{
			return resultCode;
		}
		
	}
}
