package com.winterframework.firefrog.game.service.utils;

public enum BetMethodCode {
	//组3， 组6，混合组选,豹子,对子
//	ZUXUAN120, ZUXUAN60, ZUXUAN30, ZUXUAN20, ZUXUAN10, ZUXUAN5, ZUXUAN24, ZUXUAN12, ZUXUAN6, ZUXUAN4, ZU3, ZU6, HHZX, BAOZI, DUIZI;
	ZUXUAN120(43),ZUXUAN60(44),ZUXUAN30(45),ZUXUAN20(46),ZUXUAN10(47),ZUXUAN5(48),ZUXUAN24(49),ZUXUAN12(50),ZUXUAN6(51),ZUXUAN4(52),ZU3(35),ZU6(36),HHZX(37),BAOZI(38),DUIZI(64);
	
	private int code ;
	
	BetMethodCode(int code){
		this.code = code;
	}
	
	public int getBetMethodeCode(){
		return code;
	}
	/*public int getBetMethodeCode() {

		switch (this.ordinal()) {
		case 0:
			return 43;
		case 1:
			return 44;
		case 2:
			return 45;
		case 3:
			return 46;
		case 4:
			return 47;
		case 5:
			return 48;
		case 6:
			return 49;
		case 7:
			return 50;
		case 8:
			return 51;
		case 9:
			return 52;
		case 10:
			return 35;
		case 11:
			return 36;
		case 12:
			return 37;
		case 13:
			return 38;
		case 14:
			return 64;
		default:
			return 0;
		}
	}*/
}
