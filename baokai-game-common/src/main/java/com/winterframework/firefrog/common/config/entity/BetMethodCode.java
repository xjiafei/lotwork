package com.winterframework.firefrog.common.config.entity;
/**
 * 
* @ClassName: BetMethodCode 
* @Description: 投注方式枚举类
* @author Richard
* @date 2013-12-27 下午4:42:31 
*
 */
public enum BetMethodCode {
	//组选120，   组选60                  组选30        														  //组3， 组6，混合组选,豹子,对子
	ZUXUAN120(43),ZUXUAN60(44),ZUXUAN30(45),ZUXUAN20(46),ZUXUAN10(47),ZUXUAN5(48),ZUXUAN24(49),ZUXUAN12(50),ZUXUAN6(51),ZUXUAN4(52),ZU3(35),ZU6(36),HHZX(37),BAOZI(38),DUIZI(64);
	private Integer code =0;
	BetMethodCode(int code){
		this.code = code ;
	}
	
	public int getBetMethodeCode(){
		return code;
	}
/*	public int getBetMethodeCode(){
		
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
