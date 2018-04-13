package com.winterframework.firefrog.game.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
 
/**
 * 注單明細。
 * @author Pogi.Lin
 */
public class GameSlip {

	/**投注內容*/
	private String betDetail;
	/**創建時間*/
	private Date crateTime;
	/**注單總獎金*/
	private Long evaluateWin;
	/**玩法类型实例*/
	private GameBetType gameBetType;
	private Long id;
	/**輔助玩法明細資料*/
	private List<SlipItemAssist> slipItemAssist;
	/**元角模式；1:元、2:角*/
	private MoneyMode moneyMode;
	/**倍數*/
	private Integer multiple;
	/**是否存在多獎金；0:不存在、1:存在(需要GAME_SLIP_ASSIST輔助)*/
	private Integer mutlAward;
	/**總金額*/
	private Long totalAmount;
	/**總注數*/
	private Long totalBet;
	public GameOrder gameOrder;
	/**獎期開獎物件*/
	private GameIssueEntity issueCode;
	/**注單明細狀態；1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常*/
	private GameSlipStatus gameSlipStatus;
	/**封鎖號碼物件*/
	private LockPoint lockPoints;
	private Long planDetailId;
	/**單注獎金*/
	private Long singleWin;
	/**单注最小中奖金额*/
	private Long singleWinDown;
	/**訂單ID*/
	private Long orderId;
	/**文件模式；0:非文件、1:文件*/
	private Integer fileMode;
	/**中獎注數*/
    private Long winNumber;
    /**奖金模式；1:普通、2:高獎金*/
    private Integer awardMode;
    /**返点*/
    private Long retPoint;
    /**返点奖金；高獎金模式時記錄*/
	private Long retAward;
	/**方案明细ID*/
	private Long packageItemId;
	/**鑽石加獎金額*/
	private Long diamondAmount = 0l;
	/**鑽石中獎金額*/
	private Long diamondWin = 0l;
	
	/**獎金明細*/
	private List<GameAward> gameAwards;
	
	/**六合彩生肖設定*/
	private List<GameNumberConfig> gameNumberConfigs;
	
	/**
	 * 取得單注獎金。
	 * @return
	 */
	public Long getSingleWin() {
		return singleWin;
	}

	/**
	 * 設定單注獎金。
	 * @param singleWin
	 */
	public void setSingleWin(Long singleWin) {
		this.singleWin = singleWin;
	}

	/**
	 * 取得中獎注數。
	 * @return
	 */
	public Long getWinNumber() {
		return winNumber;
	}

	/**
	 * 設定中獎注數。
	 * @param winNumber
	 */
	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}
	/**
	 * 取得封鎖號碼物件。
	 * @return
	 */
	public LockPoint getLockPoints() {
		return lockPoints;
	}
	/**
	 * 設定封鎖號碼物件。
	 * @param lockPoints
	 */
	public void setLockPoints(LockPoint lockPoints) {
		this.lockPoints = lockPoints;
	}

	public GameSlip(){
		
	}

	/**
	 * 取得投注內容。
	 * @return
	 */
	public String getBetDetail() {
		return betDetail;
	}

	/**
	 * 設定投注內容。
	 * @param betDetail
	 */
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}

	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCrateTime() {
		return crateTime;
	}

	/**
	 * 設定創建時間。
	 * @param crateTime
	 */
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	/**
	 * 取得注單總獎金。
	 * @return
	 */
	public Long getEvaluateWin() {
		if(null==evaluateWin){
			evaluateWin=0L;
		}
		return evaluateWin;
	}

	/**
	 * 設定注單總獎金。
	 * @param evaluateWin
	 */
	public void setEvaluateWin(Long evaluateWin) {
		this.evaluateWin = evaluateWin;
	}

	/**
	 * 取得玩法类型实例。
	 * @return
	 */
	public GameBetType getGameBetType() {
		return gameBetType;
	}

	/**
	 * 設定玩法类型实例。
	 * @param gameBetType
	 */
	public void setGameBetType(GameBetType gameBetType) {
		this.gameBetType = gameBetType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 取得輔助玩法明細資料。
	 * @return
	 */
	public List<SlipItemAssist> getSlipItemAssist() {
		return slipItemAssist;
	}
	/**
	 * 設定輔助玩法明細資料。
	 * @param slipItemAssist
	 */
	public void setSlipItemAssist(List<SlipItemAssist> slipItemAssist) {
		this.slipItemAssist = slipItemAssist;
	}

	/**
	 * 取得元角模式。
	 * @return 1:元、2:角
	 */
	public MoneyMode getMoneyMode() {
		return moneyMode;
	}

	/**
	 * 設定元角模式。
	 * @param moneyMode 1:元、2:角
	 */
	public void setMoneyMode(MoneyMode moneyMode) {
		this.moneyMode = moneyMode;
	}

	/**
	 * 取得倍數。
	 * @return
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * 設定倍數。
	 * @param multiple
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * 取得是否存在多獎金。
	 * @return 0:不存在、1:存在(需要GAME_SLIP_ASSIST輔助)
	 */
	public Integer getMutlAward() {
		return mutlAward;
	}

	/**
	 * 設定是否存在多獎金。
	 * @param mutlAward 0:不存在、1:存在(需要GAME_SLIP_ASSIST輔助)
	 */
	public void setMutlAward(Integer mutlAward) {
		this.mutlAward = mutlAward;
	}

	/**
	 * 取得總金額。
	 * @return
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 設定總金額。
	 * @param totalAmount
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 取得總注數。
	 * @return
	 */
	public Long getTotalBet() {
		return totalBet;
	}

	/**
	 * 設定總注數。
	 * @param totalBet
	 */
	public void setTotalBet(Long totalBet) {
		this.totalBet = totalBet;
	}

	public GameOrder getGameOrder() {
		return gameOrder;
	}

	public void setGameOrder(GameOrder gameOrder) {
		this.gameOrder = gameOrder;
	}

	/**
	 * 取得注單明細狀態；1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常。
	 * @return
	 */
	public GameSlipStatus getGameSlipStatus() {
		return gameSlipStatus;
	}
	/**
	 * 設定注單明細狀態；1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常。
	 * @param gameSlipStatus
	 */
	public void setGameSlipStatus(GameSlipStatus gameSlipStatus) {
		this.gameSlipStatus = gameSlipStatus;
	}
	/**
	 * 設定獎期開獎物件。
	 * @param issueCode
	 */
	public void setIssueCode(GameIssueEntity issueCode) {
		this.issueCode = issueCode;
	}
	/**
	 * 取得獎期開獎物件。
	 * @return
	 */
	public GameIssueEntity getIssueCode() {
		return issueCode;
	}

	public Long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(Long planDetailId) {
		this.planDetailId = planDetailId;
	}

	/**
	 * 取得文件模式。
	 * @return 0:非文件、1:文件
	 */
	public Integer getFileMode() {
		return fileMode;
	}

	/**
	 * 設定文件模式。
	 * @param fileMode 0:非文件、1:文件
	 */
	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}

	/**
	 * 取得单注最小中奖金额。
	 * @return
	 */
	public Long getSingleWinDown() {
		return singleWinDown;
	}

	/**
	 * 設定单注最小中奖金额。
	 * @param singleWinDown
	 */
	public void setSingleWinDown(Long singleWinDown) {
		this.singleWinDown = singleWinDown;
	}
	
	/**
	 * 取得奖金模式。
	 * @return 1:普通(default)、2:高獎金
	 */
	public Integer getAwardMode() {
		if(null == awardMode){
			awardMode = 1;
		}
		return awardMode;
	}

	/**
	 * 設定奖金模式。
	 * @param awardMode 1:普通、2:高獎金
	 */
	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}
	
	/**
	 * 取得返点。
	 * @return
	 */
	public Long getRetPoint() {
		if(null==retPoint){
			retPoint=0L;
		}
		return retPoint;
	}

	/**
	 * 設定返点。
	 * @param retPoint
	 */
	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	/**
	 * 取得返点奖金；高獎金模式時記錄。
	 * @return
	 */
	public Long getRetAward() {
		if(null==retAward){
			retAward=0L;
		}
		return retAward;
	}

	/**
	 * 設定返点奖金；高獎金模式時記錄。
	 * @param retAward
	 */
	public void setRetAward(Long retAward) {
		this.retAward = retAward;
	}

	/**
	 * 取得方案明细ID。
	 * @return
	 */
	public Long getPackageItemId() {
		return packageItemId;
	}

	/**
	 * 設定方案明细ID。
	 * @param packageItemId
	 */
	public void setPackageItemId(Long packageItemId) {
		this.packageItemId = packageItemId;
	}

	/**
	 * 取得鑽石加獎金額。
	 * @return
	 */
	public Long getDiamondAmount() {
		return diamondAmount;
	}

	/**
	 * 設定鑽石加獎金額。
	 * @param diamondAmount
	 */
	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}

	/**
	 * 取得鑽石中獎金額。
	 * @return
	 */
	public Long getDiamondWin() {
		return diamondWin;
	}

	/**
	 * 設定鑽石中獎金額。
	 * @param diamondWin
	 */
	public void setDiamondWin(Long diamondWin) {
		this.diamondWin = diamondWin;
	}

	/**
	 * 取得訂單ID。
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 設定訂單ID。
	 * @param orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<GameAward> getGameAwards() {
		return gameAwards;
	}

	public void setGameAwards(List<GameAward> gameAwards) {
		this.gameAwards = gameAwards;
	}

	public List<GameNumberConfig> getGameNumberConfigs() {
		return gameNumberConfigs;
	}

	public void setGameNumberConfigs(List<GameNumberConfig> gameNumberConfigs) {
		this.gameNumberConfigs = gameNumberConfigs;
	}
	
}
