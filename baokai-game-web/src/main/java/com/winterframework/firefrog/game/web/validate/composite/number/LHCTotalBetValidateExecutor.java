package com.winterframework.firefrog.game.web.validate.composite.number;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * 不中的注數檢核
 * @author user
 *
 */
public class LHCTotalBetValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {
		
		String betTypeCode = validatedBean.getGameBetType().getBetTypeCode();
		
		//取得該玩法的最小球數
		Long minBalls = LHCUtil.findLhcBetTypeCode(betTypeCode).getMinBalls();
		
		//判斷是否胆拖
		boolean isDanTou = StringUtils.trimToEmpty(validatedBean.getBetDetail()).indexOf("胆") > -1;

		if(isDanTou){
			//胆拖
			String[] bets = validatedBean.getBetDetail().split(";");
			String danMa = bets[0];//胆
			String tuoMa = bets[1];//拖
			danMa = danMa.replaceAll("胆:", "");
			tuoMa = tuoMa.replaceAll("拖:", "");
			//取得胆碼個數
			String[] danMas = danMa.split(",");
			if(danMas.length >= minBalls){
				throw new GameBetContentPatternErrorException();
			} else {
				//拖碼個數
				String[] tuoMas = tuoMa.split(",");
				//計算注數
				Long realBets = ValidateUtils.countCNM(Long.valueOf(tuoMas.length + ""), (minBalls - danMas.length));
				
				ValidateUtils.validateBetsCount(Integer.valueOf(realBets + ""), validatedBean.getTotalBet());
			}
		} else {
			//複式
			String[] bets = validatedBean.getBetDetail().split(",");
			
			if(bets.length > 14){
				throw new GameBetContentPatternErrorException();
			} else {
				
				Long realBets = ValidateUtils.countCNM(Long.valueOf(bets.length + ""), minBalls);
				
				ValidateUtils.validateBetsCount(Integer.valueOf(realBets + ""), validatedBean.getTotalBet());
			}
			
		}
		((BetValidateContext) context).setTotalBets(Integer.valueOf(validatedBean.getTotalBet() + ""));
	}
}
