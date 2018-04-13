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
	/**彩种ID*/
	private Long lotteryType;
	/**玩法群編碼*/
	private Integer groupCode;
	/**玩法组編碼*/
	private Integer setCode;
	/**投注方法編碼*/
	private Integer methodCode;
	/**辅助玩法类型*/
	private Integer methodType;
	/**分隔符號*/
	private String seperator;

	/**
	 * 取得分隔符號。
	 * @return
	 */
	public String getSeperator() {
		return seperator;
	}

	/**
	 * 設定分隔符號。
	 * @param seperator
	 */
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public LotteryPlayMethodKeyGenerator() {
	}

	/**
	 * @param lotteryType 彩种ID
	 * @param groupCode 玩法群編碼
	 * @param setCode 玩法組編碼
	 * @param methodCode 投注方法編碼
	 * @param seperator 分隔符號
	 */
	public LotteryPlayMethodKeyGenerator(Long lotteryType, Integer groupCode, Integer setCode, Integer methodCode,
			String seperator) {
		this.lotteryType = lotteryType;
		this.groupCode = groupCode;
		this.setCode = setCode;
		this.methodCode = methodCode;
		this.seperator = seperator;
	}

	@Override
	public Long getLotteryType() {
		return lotteryType;
	}

	/**
	 * 設定彩种ID。
	 * @param lotteryType
	 */
	public void setLotteryType(Long lotteryType) {
		this.lotteryType = lotteryType;
	}

	@Override
	public Integer getGroupCode() {
		return groupCode;
	}

	/**
	 * 設定玩法群編碼。
	 * @param groupCode
	 */
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	@Override
	public Integer getSetCode() {
		return setCode;
	}

	/**
	 * 設定玩法组編碼。
	 * @param setCode
	 */
	public void setSetCode(Integer setCode) {
		this.setCode = setCode;
	}

	@Override
	public Integer getMethodCode() {
		return methodCode;
	}

	/**
	 * 設定投注方法編碼。
	 * @param methodCode
	 */
	public void setMethodCode(Integer methodCode) {
		this.methodCode = methodCode;
	}

	/**
	 * 主鍵組合 = groupCode + seperator + setCode + seperator + methodCode + seperator + methodType
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
		key.setLotteryType(new Long(this.lotteryType.longValue()));
		key.setGroupCode(groupCode);
		key.setSetCode(setCode);
		key.setMethodCode(methodCode);
		key.setMethodType(methodType);
		key.setSeperator(this.seperator);
		return key;
	}

	@Override
	public Integer getMethodType() {
		return methodType;
	}

	/**
	 * 設定輔助玩法類型。
	 * @param methodType
	 */
	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}
}
