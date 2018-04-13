package com.winterframework.firefrog.game.web.util;

public enum GameGroupEnum {

	/**
	 *  1)		五星	10
		2)		四星	11
		3)		前三	12
		4)		后三	13
		5)		后二	14
		6)		前二	15
		7)		一星	16

	 */

	WX(10), SX(11), QS(12), HS(13), HE(14), QE(15), YX(16);

	private int _value = 0;

	GameGroupEnum(int action) {

		this._value = action;
	}

	public int getValue() {
		return this._value;
	}
}
