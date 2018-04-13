package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.phone.web.validate.utils.BetCalUtil;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberGroup4ValidateExecutor 
* @Description: 组选4注数验证
* @author Richard
* @date 2013-8-6 下午2:08:08 
*
 */
public class BetNumberGroup4ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ((BetValidateContext) context).getBets();

		String repeatStr = bets[0];
		String oddStr = bets[1];
		//验证重号与单号部分的的内容是否有重复
		ValidateUtils.checkRepeat(repeatStr);
		ValidateUtils.checkRepeat(oddStr);
		//获取选项号码重复个数D，选项号码不一样的个数S，重号选项个数m，单号选项个数n，用于计算注数  ss[0]重号部分，ss[1] 单号部分
		int d = BetCalUtil.getD(repeatStr, oddStr);
		//获取选项号码不一样的个数S
		int s = BetCalUtil.getS(repeatStr, oddStr);
		//获取单号选项个数n
		int n = BetCalUtil.getN(oddStr);

		//D*C(n-1,1)+S*C(n,1)
		int totbets = d * CombinUtil.combin(n - 1, 1) + s * CombinUtil.combin(n, 1);

		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}
}
