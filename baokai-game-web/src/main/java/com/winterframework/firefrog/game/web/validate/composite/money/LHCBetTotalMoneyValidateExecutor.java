package com.winterframework.firefrog.game.web.validate.composite.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;
import com.winterframework.firefrog.game.util.LHCUtil.LhcCode;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * 
* @ClassName: LHCBetTotalMoneyValidateExecutor 
* @Description: 投注总金额验证 
* @author Richard
* @date 2013-8-6 下午2:27:21 
*
 */
public class LHCBetTotalMoneyValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(LHCBetTotalMoneyValidateExecutor.class);
	
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context)  {

		// 验证投注金额
		ValidateUtils.validateLHCTotalMoney(validatedBean.getTotalAmount(), ((BetValidateContext) context).getTotalBets(),
				validatedBean.getMultiple(), validatedBean.getMoneyMode().getValue(),validatedBean.getGameBetType().getBetMethodCode(),
				validatedBean.getSingleWin());
		try{
			if("54_10_81".equals(validatedBean.getGameBetType().getBetTypeCode())){
				List<Map<String, GameAward>> betTypeGameAwards=ListFilterGameAwardsByBetTypeCode(validatedBean.getGameBetType().getBetTypeCode(), validatedBean.getGameAwards());
				Assert.isTrue(validSingleWinByMapping(betTypeGameAwards,validatedBean), "倍数必须大于40或小于47");
			} else {
				BetTypeCodeMapping betTypeCodeMapping = LHCUtil.findLhcBetTypeCode(validatedBean.getGameBetType().getBetTypeCode());
				
				Assert.isTrue(betTypeCodeMapping.validSingleWin(validatedBean.getGameBetType().getBetTypeCode(), 
						validatedBean.getGameAwards(), 
						validatedBean, 
						validatedBean.getGameNumberConfigs()), "賠率發生錯誤");
			}
		}catch(Exception e){
			log.error("賠率發生錯誤", e);
			throw new GameBetAmountErrorException();
		}
	}
	
	/**
	 * 取得特碼直選一碼所對應的獎金明細
	 * @param betTypeCode
	 * @param gameAwards
	 * @return
	 */
	public List<Map<String, GameAward>> ListFilterGameAwardsByBetTypeCode(final String betTypeCode, final List<GameAward> gameAwards){
		List<GameAward> newAwards = new ArrayList<GameAward>(gameAwards);
		for(int i = 0 ; i < newAwards.size() ; i++){
			GameAward newAward = newAwards.get(i);
			if(betTypeCode.equals(newAward.getBetTypeCode())){
				
			} else {
				newAwards.remove(i);
				i--;
			}
		}		
		List<Map<String, GameAward>> tempGameAwardList = new ArrayList<Map<String, GameAward>>();
		for(GameAward newAward : newAwards){
			Map<String, GameAward> tempGameAwards = new HashMap<String, GameAward>();
			tempGameAwards.put(newAward.getLhcCode(), newAward);
			tempGameAwardList.add(tempGameAwards);
		}
		
		return tempGameAwardList;
	}
	
	
	/**特码-直选-直选一码*/
	public Boolean validSingleWinByMapping(final List<Map<String, GameAward>>betTypeGameAwards, final GameSlip gameSlip){
		for(Map<String, GameAward> betTypeGameAward: betTypeGameAwards){
			if(betTypeGameAward.get(LhcCode.DIRECT.toString()).getActualBonus().equals(gameSlip.getSingleWin())){
				return true;
			}
		}
		return false;
	}

}
