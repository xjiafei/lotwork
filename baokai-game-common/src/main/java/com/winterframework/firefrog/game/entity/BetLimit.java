package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: BetLimit 
* @Description: 投注限制实体Bean 
* @author Denny 
* @date 2013-8-21 下午3:15:34 
*  
*/
public class BetLimit implements Serializable {

	private static final long serialVersionUID = 9132588001790733692L;

	/** 彩种 */
	private Long lotteryid;
	/** 玩法群 */
	private Integer gameGroupCode;
	/** 玩法组 */
	private Integer gameSetCode;
	/** 投注方式 */
	private Integer betMethodCode;
	/** 限制倍数 */
	private Integer maxMultiple;
	/** 限制倍数_ 对比 */
	private Integer maxMultiple_bak;
	/** 限制最大中奖金额 */
	private Long maxBonus;
	/** 审核发布状态：2，进行中；3，待审核；4，待发布；5，审核未通过；6，发布未通过 */
	private Integer status;
	
	private List<BetLimitAssist> betLimitAssist;
	
	private Long id;
	
	private String specialLimit;   //用於11選5趣味定單雙針對號碼限制
	private String specialLimit_bak;   //用於11選5趣味定單雙針對號碼限制  
	private String multipleIndex;
	private String specialMaxBonus;	//用於11選5趣味定單雙針對號碼限制

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public Integer getMaxMultiple() {
		return maxMultiple;
	}

	public void setMaxMultiple(Integer maxMutiple) {
		this.maxMultiple = maxMutiple;
	}

	public Integer getMaxMultiple_bak() {
		return maxMultiple_bak;
	}

	public void setMaxMultiple_bak(Integer maxMultiple_bak) {
		this.maxMultiple_bak = maxMultiple_bak;
	}

	public Long getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(Long maxBonus) {
		this.maxBonus = maxBonus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<BetLimitAssist> getBetLimitAssist() {
		return betLimitAssist;
	}

	public void setBetLimitAssist(List<BetLimitAssist> betLimitAssist) {
		this.betLimitAssist = betLimitAssist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialLimit() {
		return specialLimit;
	}

	public void setSpecialLimit(String specialLimit) {
		this.specialLimit = specialLimit;
	}

	public String getSpecialLimit_bak() {
		return specialLimit_bak;
	}

	public void setSpecialLimit_bak(String specialLimit_bak) {
		this.specialLimit_bak = specialLimit_bak;
	}

	public String getMultipleIndex() {
		return multipleIndex;
	}

	public void setMultipleIndex(String multipleIndex) {
		this.multipleIndex = multipleIndex;
	}

	public String getSpecialMaxBonus() {
		return specialMaxBonus;
	}

	public void setSpecialMaxBonus(String specialMaxBonus) {
		this.specialMaxBonus = specialMaxBonus;
	}
	
	
}
