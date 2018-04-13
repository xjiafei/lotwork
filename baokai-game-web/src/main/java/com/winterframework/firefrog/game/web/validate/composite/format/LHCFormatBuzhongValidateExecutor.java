package com.winterframework.firefrog.game.web.validate.composite.format;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

public class LHCFormatBuzhongValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//判斷是否胆拖
		boolean isDanTou = StringUtils.trimToEmpty(validatedBean.getBetDetail()).indexOf("胆") > -1;
		String[] bets = new String[]{};
		if(isDanTou){
			//胆拖
			String[] danTouArray = validatedBean.getBetDetail().split(";");
			String danMa = danTouArray[0];//胆
			String tuoMa = danTouArray[1];//拖
			danMa = danMa.replaceAll("胆:", "");
			tuoMa = tuoMa.replaceAll("拖:", "");
			//取得胆碼個數
			String[] danMas = danMa.split(",");
			String[] tuoMas = tuoMa.split(",");
			bets = ArrayUtils.addAll(danMas, tuoMas);
		} else {
			bets = validatedBean.getBetDetail().split(",");
		}
        
        String regularexpression = "[0-4][0-9]";
        ValidateUtils.checkRepeat(bets);
        for(String bet : bets) {
        	if(bet.matches(regularexpression) && !"00".equals(bet)){
        		//格式正確
        	} else {
        		throw new GameBetContentPatternErrorException();
        	}
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
