package com.winterframework.firefrog.game.service.assist.bet;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;

/** 
* @ClassName: LotteryPlayMethodKeyGenerator 
* @Description: 根据采种，玩法组， 
* @author page
* @date 2013-8-2 下午1:15:25 
*  
*/
public class LotteryPlayMethodKeyGenerator implements IKeyGenerator, ILotteryKeyFactor {
	/**
	 * 彩种
	 */
	private Long lotteryType;

	/**
	 * 玩法群
	 */
	private Integer groupCode;

	/**
	 * 玩法组
	 */
	private Integer setCode;

	/**
	 * 玩法
	 */
	private Integer methodCode;

	/** 
	* 玩法辅助类型 
	*/
	private Integer methodType;
	private String seperator;

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public LotteryPlayMethodKeyGenerator() {
	}

	public LotteryPlayMethodKeyGenerator(Long lotteryType, Integer groupCode, Integer setCode, Integer methodCode,
			String seperator) {
		this.lotteryType = lotteryType;
		this.groupCode = groupCode;
		this.setCode = setCode;
		this.methodCode = methodCode;
		this.seperator = seperator;
	}

	public LotteryPlayMethodKeyGenerator(Long lotteryType, Integer groupCode, Integer setCode, Integer methodCode,
			Integer methodType, String seperator) {
		this.lotteryType = lotteryType;
		this.groupCode = groupCode;
		this.setCode = setCode;
		this.methodType = methodType;
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

		}
		if (this.methodType != null) {
			keyBuffer.append(this.methodType.toString());

		}
		return keyBuffer.toString();

	}

	@Override
	public int hashCode() {
		return this.generateKey().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LotteryPlayMethodKeyGenerator) {
			return this.generateKey().equals(((LotteryPlayMethodKeyGenerator) obj).generateKey());
		} else {
			return false;
		}
	}

	@Override
	public Object clone() {
		LotteryPlayMethodKeyGenerator key = new LotteryPlayMethodKeyGenerator();
		key.setLotteryType(this.lotteryType);
		key.setGroupCode(this.groupCode);
		key.setSetCode(this.setCode);
		key.setMethodCode(this.methodCode);
		key.setMethodType(this.methodType);
		key.setSeperator(this.seperator);
		return key;
	}

	public Integer getMethodType() {
		return methodType;
	}

	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}
}
