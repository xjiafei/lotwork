package com.winterframework.firefrog.game.entity;

/**
 * 獎期狀態 Enum。<br>
 * 0:已生成(M1)、1:開始銷售(M2)、2:結束銷售(M3)、3:開獎號碼確認(M4)、4:計獎完成(M5)、5:驗獎完成(M6)、6:派獎完成(M7)、7:獎期結束(M8)、8:對賬結束(M9)
 * @author Pogi.Lin
 */
public enum GameIssueStatus {

	/**0:已生成(M1)*/
	CREATE(0),
	/**1:開始銷售(M2)*/
	SALE_START(1),
	/**2:結束銷售(M3)*/
	SALE_END(2),
	/**3:開獎號碼確認(M4)*/
	ACK_DRAW_RESULT(3),
	/**4:計獎完成(M5)*/
	CALCULATION(4),
	/**5:驗獎完成(M6)*/
	VERIFICATION(5),
	/**6:派獎完成(M7)*/
	PRIZE(6),
	/**7:獎期結束(M8)*/
	ISSUE_OVER(7),
	/**8:對賬結束(M9)*/
	RECONCILIATION(8);
	
	private int value;
	GameIssueStatus(int action){
		this.value = action;
	}
	
	public int getValue(){
		return this.value; 
	}
}
