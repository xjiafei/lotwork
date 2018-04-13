package com.winterframework.firefrog.phone.web.validate.business;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;

/** 
* @ClassName: BetValidateContext 
* @Description: 投注验证上下文 
* @author page
* @date 2013-8-2 下午4:02:44 
*  
*/
public class BetValidateContext implements IValidateExecutorContext {
	private IKeyGenerator keyGenerator;

	private GameOrder gameOrder;

	/**
	 * 彩种
	 */
	private Long lotteryType;

	/**
	 * 玩法群
	 */
	private Integer groupCode;

	/**
	 * 玩法组
	 */
	private Integer setCode;

	/**
	 * 玩法
	 */
	private Integer methodCode;

	/**
	 * 投注数组
	 */
	private String[] bets;
	
	/** 
	* @Fields assitBets :投注辅助数组，比如在胆码投注中，胆码内容和拖码内容可以只切割一次分别传递
	*/ 
	private String[] assitBets;

	/**
	 * 计算出来的总注数
	 */
	private Integer totalBets;

	public Long getLotteryType() {
		return lotteryType;
	}

	public Integer getGroupCode() {
		return groupCode;
	}

	public Integer getSetCode() {
		return setCode;
	}

	public Integer getMethodCode() {
		return methodCode;
	}

	public Map<String, Object> paramMap = new HashMap<String, Object>();

	public BetValidateContext(GameOrder gameOrder, GameSlip slip) {
		this.gameOrder = gameOrder;
		this.lotteryType = gameOrder.getLottery().getLotteryId();
		this.groupCode = slip.getGameBetType().getGameGroupCode();
		this.setCode = slip.getGameBetType().getGameSetCode();
		this.methodCode = slip.getGameBetType().getBetMethodCode();
        this.totalBets = slip.getTotalBet().intValue();
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public GameOrder getGameOrder() {
		return gameOrder;
	}

	/**
	* Title: getKeyGenerator
	* Description:
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.IValidateExecutorContext#getKeyGenerator() 
	*/
	@Override
	public IKeyGenerator getKeyGenerator() {
		return this.keyGenerator;
	}

	/**
	* Title: setKeyGenerator
	* Description:
	* @param keyGenerator 
	* @see com.winterframework.firefrog.common.validate.business.IValidateExecutorContext#setKeyGenerator(com.winterframework.firefrog.common.validate.business.IKeyGenerator) 
	*/
	@Override
	public void setKeyGenerator(IKeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public String[] getBets() {
		return bets;
	}

	public void setBets(String[] bets) {
		this.bets = bets;
	}

	public Integer getTotalBets() {
		return totalBets;
	}

	public void setTotalBets(Integer totalBets) {
		this.totalBets = totalBets;
	}

	public String[] getAssitBets() {
		return assitBets;
	}

	public void setAssitBets(String[] assitBets) {
		this.assitBets = assitBets;
	}
}
