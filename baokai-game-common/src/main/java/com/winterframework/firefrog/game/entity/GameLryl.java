package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: GameLryl 
* @Description: 所选号码冷热遗漏值实体Bean 
* @author Denny 
* @date 2013-7-22 下午5:05:19 
*  
*/
public class GameLryl implements Serializable {

	private static final long serialVersionUID = -1070325926943698299L;

	/** 位数：万（5）、千（4）、百（3）、十（2）、个（1）位 */
	private Integer bitNumber;
	/** 选号：0~9 */
	private Integer lottNumber;
	/** 取值 */
	private Integer retValue;

    private int valueTemp;

	public int getBitNumber() {
		return bitNumber;
	}

	public void setBitNumber(int bitNumber) {
		this.bitNumber = bitNumber;
	}

	public int getLottNumber() {
		return lottNumber;
	}

	public void setLottNumber(int lottNumber) {
		this.lottNumber = lottNumber;
	}

	public int getRetValue() {
		return retValue;
	}

	public void setRetValue(int retValue) {
		this.retValue = retValue;
	}

    public int getValueTemp() {
        return valueTemp;
    }

    public void setValueTemp(int valueTemp) {
        this.valueTemp = valueTemp;
    }
}
