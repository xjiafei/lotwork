package com.winterframework.firefrog.game.web.util;

public enum GameSetCodeEnum {

	/**
	 *  1)		直选	10
		2)		组选	11
		3)		不定位	12
		4)		趣味	13
		5)		定位胆	14

	 */

	ZX(10), ZHUXUAN(11), BDW(12), QW(13), DWD(14);

	private int _value = 0;

	GameSetCodeEnum(int action) {
		this._value = action;
	}

	public int getValue() {
		return this._value;
	}
}
