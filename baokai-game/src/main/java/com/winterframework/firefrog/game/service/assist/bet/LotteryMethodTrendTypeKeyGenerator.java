/**   
* @Title: LotteryMethodTrendTypeKeyGenerator.java 
* @Package com.winterframework.firefrog.game.special.assist.config 
* @Description: winter-game.LotteryMethodTrendTypeKeyGenerator.java 
* @author Denny  
* @date 2014-4-14 下午4:27:09 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.assist.bet;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;

/** 
* @ClassName: LotteryMethodTrendTypeKeyGenerator 
* @Description: 彩种投注方式遗漏类型KeyGenerator 
* @author Denny 
* @date 2014-4-14 下午4:27:09 
*  
*/
public class LotteryMethodTrendTypeKeyGenerator implements IKeyGenerator, ILotteryKeyFactor {

	/** 彩种 */
	private Long lotteryType;
	/** 玩法群 */
	private Integer groupCode;
	/** 玩法组 */
	private Integer setCode;
	/** 投注方式 */
	private Integer methodCode;
	/** 遗漏类型 */
	private Integer trendType;
	private String seperator;

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public LotteryMethodTrendTypeKeyGenerator() {
	}

	public LotteryMethodTrendTypeKeyGenerator(Long lotteryType, Integer groupCode, Integer setCode, Integer methodCode,
			Integer trendType, String seperator) {
		this.lotteryType = lotteryType;
		this.groupCode = groupCode;
		this.setCode = setCode;
		this.methodCode = methodCode;
		this.trendType = trendType;
		this.seperator = seperator;
	}

	/**
	* Title: getLotteryType
	* Description:
	* @return 
	* @see com.winterframework.firefrog.game.validate.business.ILotteryKeyFactor#getLotteryType() 
	*/
	@Override
	public Long getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	* Title: getGroupCode
	* Description:
	* @return 
	* @see com.winterframework.firefrog.game.validate.business.ILotteryKeyFactor#getGroupCode() 
	*/
	@Override
	public Integer getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	/**
	* Title: getSetCode
	* Description:
	* @return 
	* @see com.winterframework.firefrog.game.validate.business.ILotteryKeyFactor#getSetCode() 
	*/
	@Override
	public Integer getSetCode() {
		return setCode;
	}

	public void setSetCode(Integer setCode) {
		this.setCode = setCode;
	}

	/**
	* Title: getMethodCode
	* Description:
	* @return 
	* @see com.winterframework.firefrog.game.validate.business.ILotteryKeyFactor#getMethodCode() 
	*/
	@Override
	public Integer getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(Integer methodCode) {
		this.methodCode = methodCode;
	}

	public Integer getTrendType() {
		return trendType;
	}

	public void setTrendType(Integer trendType) {
		this.trendType = trendType;
	}

	/**
	* Title: generateKey
	* Description:
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.IKeyGenerator#generateKey() 
	*/
	@Override
	public String generateKey() {
		StringBuffer keyBuffer = new StringBuffer();
		if (this.groupCode != null) {
			keyBuffer.append(this.groupCode.toString());
			keyBuffer.append(seperator);
		}
		if (this.setCode != null) {
			keyBuffer.append(this.setCode.toString());
			keyBuffer.append(seperator);
		}
		if (this.methodCode != null) {
			keyBuffer.append(this.methodCode.toString());
			keyBuffer.append(seperator);
		}
		if (this.trendType != null) {
			keyBuffer.append(this.trendType.toString());
		}
		return keyBuffer.toString();

	}

	@Override
	public int hashCode() {
		return this.generateKey().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LotteryMethodTrendTypeKeyGenerator) {
			return this.generateKey().equals(((LotteryMethodTrendTypeKeyGenerator) obj).generateKey());
		} else {
			return false;
		}
	}

	@Override
	public Object clone() {
		LotteryMethodTrendTypeKeyGenerator key = new LotteryMethodTrendTypeKeyGenerator();
		key.setLotteryType(lotteryType);
		key.setGroupCode(groupCode);
		key.setSetCode(setCode);
		key.setMethodCode(methodCode);
		key.setTrendType(trendType);
		key.setSeperator(this.seperator);
		return key;
	}

	@Override
	public Integer getMethodType() {
		return null;
	}
}
