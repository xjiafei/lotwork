package com.winterframework.firefrog.game.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 封鎖模型內的玩法選項(六合彩專用)
 * @author Pogi.Lin
 */
public enum GameLockPlayType {
	/**0:特碼*/
	TEMA(0, "特码"),
	/**1:正特碼_一肖*/
	YIXIAO(1, "正特码_一肖"),
	/**2:其他玩法*/
	OTHER(2, "其他玩法");

	private int value;
	private String alias;
	
	GameLockPlayType(int value, String alias) {
		this.value = value;
		this.alias = alias;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getAlias() {
		return alias;
	}

	/**
	 * name to String.
	 */
	public String toString() {
		return this.name();
	}
	
	/**
	 * 是否為特碼玩法。
	 * @param betTypeCode 投注方式
	 * @return
	 */
	public static boolean isTEMA(String betTypeCode) {
		if(StringUtils.isBlank(betTypeCode)) return false;
		String[] betTypeCodeArray = betTypeCode.split("_");
		if("54".equals(betTypeCodeArray[0])) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否為正特碼_一肖玩法。
	 * @param betTypeCode 投注方式
	 * @return
	 */
	public static boolean isYIXIAO(String betTypeCode) {
		if(StringUtils.isBlank(betTypeCode)) return false;
		String[] betTypeCodeArray = betTypeCode.split("_");
		if("55".equals(betTypeCodeArray[0]) && "38".equals(betTypeCodeArray[1])) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否為六合彩其他玩法。
	 * @param betTypeCode 投注方式
	 * @return
	 */
	public static boolean isOTHER(String betTypeCode) {
		if(StringUtils.isBlank(betTypeCode)) return false;
		if(isTEMA(betTypeCode) || isYIXIAO(betTypeCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 依據 playType 取得名稱。
	 * @param playType 玩法類型；0:特碼、1:正特碼_一肖、2:其他玩法
	 * @return
	 */
	public static String getName(int playType) {
		if(TEMA.getValue() == playType) {
			return TEMA.name();
		} else if(YIXIAO.getValue() == playType) {
			return YIXIAO.name();
		} else if(OTHER.getValue() == playType) {
			return OTHER.name();
		} else {
			return null;
		}
	}
}
