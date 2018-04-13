package com.winterframework.firefrog.game.web.validate.composite.format;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * 生肖格式驗證
 * @author user
 *
 */
public class LHCFormatXiaoValidateExecutor extends CompositeValidateExecutor<GameSlip> {

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
        
		String str = "鼠、马、牛、羊、虎、猴、兔、鸡、龙、狗、蛇、猪";
        ValidateUtils.checkRepeat(bets);
        for(String bet : bets) {
            if (str.indexOf(bet)<=-1) {
                throw new GameBetContentPatternErrorException();
            }
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
