package com.winterframework.firefrog.game.entity;

/**
 * 元角模式。<br>
 * 1:元模式、2:角模式、3:分模式
 * @author Pogi.Lin
 */
public enum MoneyMode {
	
	/**1:元模式*/
	YUAN(1,"元"),
	/**2:角模式*/
	JIAO(2,"角"),
	/**2:角模式*/
	FEN(3,"分");
	
	private int value;
	private String alias;
	MoneyMode(int action,String alias){
		this.value = action;
		this.alias=alias;
	}
	
	public int getValue(){
		return this.value;
	}
	public String getAlias(){
		return this.alias;
	}
	/**
	 * 依據 value 取得 Enum 物件。
	 * @param value 元角模式
	 * @return 對應不到時回傳 IllegalArgumentException
	 */
	public MoneyMode getObject(int value) {
		if(YUAN.getValue() == value) {
			return YUAN;
		} else if(JIAO.getValue() == value) {
			return JIAO;
		} else if(FEN.getValue() == value) {
			return FEN;
		} else {
			throw new IllegalArgumentException("元角模式无法判断此来源:" + value);
		}
	}
}
