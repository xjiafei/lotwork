package com.winterframework.firefrog.game.entity;

/**
 * 獎期暫停狀態 Enum<br>
 * 0：停賣、1：正常、2:撤銷
 * @author Pogi.Lin
 */
public enum PauseStatus {
	/**0：停賣*/
	PAUSE(0),
	/**1：正常*/
	NORMAL(1),
	/**2:撤銷*/
	CANCAL(2);
	
	private int value;
	PauseStatus(int action){
		this.value= action;
	}
	
	public int getValue(){
		return this.value;
	}
}
