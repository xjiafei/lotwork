package com.winterframework.firefrog.game.enums;

/**
 * 獎金組明細投注類型。
 * @author Pogi.Lin
 */
public enum GameAwardBetType {
	/**1:直選*/
	DIRECT(1,"直选"),
	/**2:三星一碼不定位*/
	THREE_ONE(2,"三星一码不定位"),
	/**3:超級2000*/
	SUPER(3,"超级2000"),
	/**4:特肖*/
	LHC_YEAR(4,"特肖"),
	/**5:兩面色波*/
	LHC_COLOR(5,"兩面色波"),
	/**6:平码*/
	LHC_FLATCODE(6,"平码"),
	/**7:半波*/
	LHC_HALFWAVE(7,"半波"),
	/**8:一肖*/
	LHC_ONEYEAR(8,"一肖"),
	/**9:不中*/
	LHC_NOTIN(9,"不中"),
	/**10:连肖(中)二、三连肖*/
	LHC_CONTINUEIN23(10,"连肖(中)二、三连肖"),
	/**11:连肖(中)四连肖*/
	LHC_CONTINUEIN4(11,"连肖(中)四连肖"),
	/**12:连肖(中)五连肖*/
	LHC_CONTINUEIN5(12,"连肖(中)五连肖"),
	/**13:连肖(不中)二、三连肖*/
	LHC_CONTINUENOTIN23(13,"连肖(不中)二、三连肖"),
	/**14:连肖(不中)四连肖*/
	LHC_CONTINUENOTIN4(14,"连肖(不中)四连肖"),
	/**15:连肖(不中)五连肖*/
	LHC_CONTINUENOTIN5(15,"连肖(不中)五连肖"),
	/**16:连码*/
	LHC_CONTINUECODE(16,"连码"),
	/**17:猜一個號*/
	SB_THREE_ONE(17,"猜一個號");
	
	private int _value;
	private String _alias;
	private GameAwardBetType(int value,String alias){
		this._value=value;
		this._alias=alias;
	}
	
	public int getValue(){
		return _value;
	}
	public String getAlias(){
		return _alias;
	}
}
