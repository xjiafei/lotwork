package com.winterframework.firefrog.game.entity;

/**
 * 上期是否暫停 Enum(當期開獎依賴上期，與追號相關)<br>
 * 0:未暫停、1:暫停（存在上期未完成的計劃）
 * @author Pogi.Lin
 */
public enum LastIssueStop {
	/**1:暫停（存在上期未完成的計劃）*/
	PAUSE(1),
	/**0:未暫停*/
	UN_PAUSE(0);
	
	private int value;
	LastIssueStop(int action){
		this.value = action;
	}
	
	public int getValue(){
		return this.value;
	}
}
