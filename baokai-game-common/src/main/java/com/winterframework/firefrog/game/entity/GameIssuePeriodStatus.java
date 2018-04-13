package com.winterframework.firefrog.game.entity;

/**
 * 獎期過程狀態 Enum<br>
 * 0:待銷售(P1)、1:銷售中(P2)、2:待開獎(P3)、3:計獎中(P4)、4:驗獎中(P5)、5:派獎中(P6)、6:待結束(P7)、7:待對賬(P8)
 * @author Pogi.Lin
 */
public enum GameIssuePeriodStatus {
	/**0:待銷售(P1)*/
	WAIT_SALE(0),
	/**1:銷售中(P2)*/
	SALES(1),
	/**2:待開獎(P3)*/
	WAIT_DRAW_RESULT(2),
	/**3:計獎中(P4)*/
	CALCULATION_PROCESS(3),
	/**4:驗獎中(P5)*/
	VERIFICATIONP_ROCESS(4),
	/**5:派獎中(P6)*/
	PRIZE_PROCESS(5),
	/**6:待結束(P7)*/
	WAIT_ISSUE_OVER(6),
	/**7:待對賬(P8)*/
	WAIT_RECONCILIATION(7);
	
	private int value;
	GameIssuePeriodStatus(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
