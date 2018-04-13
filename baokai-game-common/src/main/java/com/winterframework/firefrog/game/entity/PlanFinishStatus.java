package com.winterframework.firefrog.game.entity;

/**
 * 追號單是否完成狀態 Enum<br>
 * 0:未完成、1:已完成
 * @author Pogi.Lin
 */
public enum PlanFinishStatus {
	/**1:已完成*/
	FINISH(1),
	/**0:未完成*/
	UN_FINISH(0);
	
	private int value;
	
	PlanFinishStatus(int action){
		this.value = action;
	}
	
	public int getValue(){
		return this.value;
	}
}
