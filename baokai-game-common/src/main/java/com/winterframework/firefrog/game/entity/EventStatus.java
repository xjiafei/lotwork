package com.winterframework.firefrog.game.entity;

/**
 * 任務鎖定狀態 Enum<br>
 * 1:正常、2:鎖定,任務執行中
 * @author Pogi.Lin
 */
public enum EventStatus {
	/**1:正常*/
	NORMAL(1),
	/**2:鎖定,任務執行中*/
	LOCK(2);
	
	private int value;

	EventStatus(int action) {
		this.value = action;
	}
	
	public int getValue(){
		return this.value;
	}
}
