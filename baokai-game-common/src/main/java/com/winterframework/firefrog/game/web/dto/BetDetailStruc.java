package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 投注方案DTO
 * @author Richard
 * @date 2013-7-22 上午9:47:18 
 */
public class BetDetailStruc implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;
	
	@NotNull
	private Long issueCode;
	@NotNull
	private Integer gameGroupCode;
	@NotNull
	private Integer gameSetCode;
	@NotNull
	private Integer betMethodCode;
	@NotNull
	private Integer moneyMode;
	@NotNull
	private Integer totbets;
	@NotNull
	private Integer itemAmount;
	@NotNull
	private Long totamount;
	@NotNull
	private Integer multiple;
	@NotNull
	private String betdetail;
	/**文件模式；0:非文件、1:文件*/
	@NotNull
	private Integer fileMode = new Integer(0);
	/**奖金模式；1:普通、2:高獎金*/
	@NotNull
	private Integer awardMode;
	/**鑽石加獎金額*/
	private Long diamondAmount = 0l;
    /**六合彩賠率*/
	private Double odds;
	//有变价的号码列表
	List<PointsRequestDTO> pointsList;
	public List<PointsRequestDTO> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<PointsRequestDTO> pointsList) {
		this.pointsList = pointsList;
	}

	public BetDetailStruc() {

	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Integer getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(Integer moneyMode) {
		this.moneyMode = moneyMode;
	}

	public Integer getTotbets() {
		return totbets;
	}

	public void setTotbets(Integer totbets) {
		this.totbets = totbets;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Integer getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getBetdetail() {
		return betdetail;
	}

	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
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
	 * 取得奖金模式。
	 * @return 1:普通、2:高獎金
	 */
	public Integer getAwardMode() {
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
     * 取得六合彩賠率。
     * @return
     */
    public Double getOdds() {
		return odds;
	}
    /**
     * 設定六合彩賠率。
     * @param odds
     */
	public void setOdds(Double odds) {
		this.odds = odds;
	}
	
}
