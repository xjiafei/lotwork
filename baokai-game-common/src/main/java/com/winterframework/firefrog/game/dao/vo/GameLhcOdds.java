package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 六合彩賠率。
 * @author Pogi.Lin
 */
public class GameLhcOdds extends BaseEntity{

	private static final long serialVersionUID = -7340510554553164267L;
	
	/**GAME_AWARD.ID*/
	private Long id;
	/**賠率*/
	private Long actualBonus;
	/**
	 * <pre>
	 * 六合彩奖金识别；
	 * FLARCODE:直選六碼、DIRECT:直選一碼、
	 * ONYEAR:主肖、UNYEAR:副肖、
	 * TWOFACE:兩面、
	 * RED:色波(紅)、BLUE:色波(藍)、GREEN:色波(綠)、
	 * REDBIG:半波(紅大)、REDSMALL:半波(紅小)、REDODD:半波(紅單)、REDEVEN:半波(紅雙)、
	 * BLUEBIG:半波(藍大)、BLUESMALL:半波(藍小)、BLUEODD:半波(藍單)、BLUEEVEN:半波(藍雙)、
	 * GREENBIG:半波(綠大)、GREENSMALL:半波(綠小)、GREENODD:半波(綠單)、GREENEVEN:半波(綠雙)、
	 * NOTIN4:四不中、NOTIN5:五不中、NOTIN6:六不中、NOTIN7:七不中、NOTIN8:八不中、
	 * ONCONTINUEIN2:二連肖主肖(中)、ONCONTINUEIN3:三連肖主肖(中)、ONCONTINUEIN4:四連肖主肖(中)、ONCONTINUEIN5:五連肖主肖(中)、
	 * UNCONTINUEIN2:二連肖副肖(中)、UNCONTINUEIN3:三連肖副肖(中)、UNCONTINUEIN4:四連肖副肖(中)、UNCONTINUEIN5:五連肖副肖(中)、
	 * ONCONTINUENOTIN2:二連肖主肖(不中)、ONCONTINUENOTIN3:三連肖主肖(不中)、ONCONTINUENOTIN4:四連肖主肖(不中)、ONCONTINUENOTIN5:五連肖主肖(不中)、
	 * UNCONTINUENOTIN2:二連肖副肖(不中)、UNCONTINUENOTIN3:三連肖副肖(不中)、UNCONTINUENOTIN4:四連肖副肖(不中)、UNCONTINUENOTIN5:五連肖副肖(不中)、
	 * CONTINUECODE333:三全中、CONTINUECODE322:三中二(中二)、CONTINUECODE323:三中二(中三)、CONTINUECODE2Z:二全中、CONTINUECODE22Z:二中特(中二)、CONTINUECODE22T:二中特(中特)、CONTINUECODE2T:特串
	 * </pre>
	 */
	private String lhcCode;
	/**玩法群名稱*/
	private String groupCodeName;
	/**玩法組名稱*/
	private String setCodeName;
	/**投注方法名稱*/
	private String methodCodeName;
	/**輔助玩法名稱*/
	private String methodTypeName;
	private String specialFlag;
	private Double odds;
	
	/**
	 * 取得GAME_AWARD.ID。 
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 設定GAME_AWARD.ID。
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 取得賠率。
	 * @return
	 */
	public Long getActualBonus() {
		return actualBonus;
	}
	/**
	 * 設定賠率。
	 * @param actualBonus
	 */
	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}
	/**
	 * 取得六合彩奖金识别。
	 * @return FLARCODE:直選六碼、DIRECT:直選一碼、<br>
	 * ONYEAR:主肖、UNYEAR:副肖、<br>
	 * TWOFACE:兩面、<br>
	 * RED:色波(紅)、BLUE:色波(藍)、GREEN:色波(綠)、<br>
	 * REDBIG:半波(紅大)、REDSMALL:半波(紅小)、REDODD:半波(紅單)、REDEVEN:半波(紅雙)、<br>
	 * BLUEBIG:半波(藍大)、BLUESMALL:半波(藍小)、BLUEODD:半波(藍單)、BLUEEVEN:半波(藍雙)、<br>
	 * GREENBIG:半波(綠大)、GREENSMALL:半波(綠小)、GREENODD:半波(綠單)、GREENEVEN:半波(綠雙)、<br>
	 * NOTIN4:四不中、NOTIN5:五不中、NOTIN6:六不中、NOTIN7:七不中、NOTIN8:八不中、<br>
	 * ONCONTINUEIN2:二連肖主肖(中)、ONCONTINUEIN3:三連肖主肖(中)、ONCONTINUEIN4:四連肖主肖(中)、ONCONTINUEIN5:五連肖主肖(中)、<br>
	 * UNCONTINUEIN2:二連肖副肖(中)、UNCONTINUEIN3:三連肖副肖(中)、UNCONTINUEIN4:四連肖副肖(中)、UNCONTINUEIN5:五連肖副肖(中)、<br>
	 * ONCONTINUENOTIN2:二連肖主肖(不中)、ONCONTINUENOTIN3:三連肖主肖(不中)、ONCONTINUENOTIN4:四連肖主肖(不中)、ONCONTINUENOTIN5:五連肖主肖(不中)、<br>
	 * UNCONTINUENOTIN2:二連肖副肖(不中)、UNCONTINUENOTIN3:三連肖副肖(不中)、UNCONTINUENOTIN4:四連肖副肖(不中)、UNCONTINUENOTIN5:五連肖副肖(不中)、<br>
	 * CONTINUECODE333:三全中、CONTINUECODE322:三中二(中二)、CONTINUECODE323:三中二(中三)、CONTINUECODE2Z:二全中、CONTINUECODE22Z:二中特(中二)、CONTINUECODE22T:二中特(中特)、CONTINUECODE2T:特串
	 */
	public String getLhcCode() {
		return lhcCode;
	}
	/**
	 * 設定六合彩奖金识别。
	 * @param lhcCode FLARCODE:直選六碼、DIRECT:直選一碼、<br>
	 * ONYEAR:主肖、UNYEAR:副肖、<br>
	 * TWOFACE:兩面、<br>
	 * RED:色波(紅)、BLUE:色波(藍)、GREEN:色波(綠)、<br>
	 * REDBIG:半波(紅大)、REDSMALL:半波(紅小)、REDODD:半波(紅單)、REDEVEN:半波(紅雙)、<br>
	 * BLUEBIG:半波(藍大)、BLUESMALL:半波(藍小)、BLUEODD:半波(藍單)、BLUEEVEN:半波(藍雙)、<br>
	 * GREENBIG:半波(綠大)、GREENSMALL:半波(綠小)、GREENODD:半波(綠單)、GREENEVEN:半波(綠雙)、<br>
	 * NOTIN4:四不中、NOTIN5:五不中、NOTIN6:六不中、NOTIN7:七不中、NOTIN8:八不中、<br>
	 * ONCONTINUEIN2:二連肖主肖(中)、ONCONTINUEIN3:三連肖主肖(中)、ONCONTINUEIN4:四連肖主肖(中)、ONCONTINUEIN5:五連肖主肖(中)、<br>
	 * UNCONTINUEIN2:二連肖副肖(中)、UNCONTINUEIN3:三連肖副肖(中)、UNCONTINUEIN4:四連肖副肖(中)、UNCONTINUEIN5:五連肖副肖(中)、<br>
	 * ONCONTINUENOTIN2:二連肖主肖(不中)、ONCONTINUENOTIN3:三連肖主肖(不中)、ONCONTINUENOTIN4:四連肖主肖(不中)、ONCONTINUENOTIN5:五連肖主肖(不中)、<br>
	 * UNCONTINUENOTIN2:二連肖副肖(不中)、UNCONTINUENOTIN3:三連肖副肖(不中)、UNCONTINUENOTIN4:四連肖副肖(不中)、UNCONTINUENOTIN5:五連肖副肖(不中)、<br>
	 * CONTINUECODE333:三全中、CONTINUECODE322:三中二(中二)、CONTINUECODE323:三中二(中三)、CONTINUECODE2Z:二全中、CONTINUECODE22Z:二中特(中二)、CONTINUECODE22T:二中特(中特)、CONTINUECODE2T:特串
	 */
	public void setLhcCode(String lhcCode) {
		this.lhcCode = lhcCode;
	}
	/**
	 * 取得玩法群名稱。
	 * @return
	 */
	public String getGroupCodeName() {
		return groupCodeName;
	}
	/**
	 * 設定玩法群名稱。
	 * @param groupCodeName
	 */
	public void setGroupCodeName(String groupCodeName) {
		this.groupCodeName = groupCodeName;
	}
	/**
	 * 取得玩法組名稱。
	 * @return
	 */
	public String getSetCodeName() {
		return setCodeName;
	}
	/**
	 * 設定玩法組名稱。
	 * @param setCodeName
	 */
	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}
	/**
	 * 取得投注方法名稱。
	 * @return
	 */
	public String getMethodCodeName() {
		return methodCodeName;
	}
	/**
	 * 設定投注方法名稱。
	 * @param methodCodeName
	 */
	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}
	/**
	 * 取得輔助玩法名稱。
	 * @return 
	 */
	public String getMethodTypeName() {
		return methodTypeName;
	}
	/**
	 * 設定輔助玩法名稱。
	 * @param methodTypeName 
	 */
	public void setMethodTypeName(String methodTypeName) {
		this.methodTypeName = methodTypeName;
	}
	public String getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	public Double getOdds() {
		return odds;
	}
	public void setOdds(Double odds) {
		this.odds = odds;
	}

}
