package com.winterframework.firefrog.game.entity;

/**
 * 注單明細狀態；1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常
 * @author Pogi.Lin
 */
public enum GameSlipStatus {
	/**1:等待開獎*/
	WAITING(1), 
	/**2:中獎*/
	WIN(2), 
	/**3:未中獎*/
	NUWIN(3), 
	/**4:撤銷*/
	CANCEL(4), 
	/**5:異常*/
	EXCEP(5);

	private int value;

	GameSlipStatus(int action) {
		this.value = action;
	}

	public int getValue() {
		return this.value;
	}
}
