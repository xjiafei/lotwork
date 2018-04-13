package com.winterframework.firefrog.game.entity;

public enum FileMode {

	FILE(1),
	NUFILE(0);
	
	private int value;
	FileMode(int action){
		this.value = action;
	}
	
	public int getValue(){
		return this.value;
	}
}
