package com.winterframework.firefrog.phone.web.validate.composite.money;

import org.springframework.util.Assert;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;

public class LHCMutipleBetTotalMoneyValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		// 验证投注金额
		Assert.isTrue(validatedBean.getTotalAmount() > 0, "总金额必须大于0");
		Assert.isTrue(validatedBean.getTotalBet() > 0 , "注数必须大于0");
		Assert.isTrue(validatedBean.getMultiple() == 1, "倍数必须等于1");
		
		String betTypeCode = validatedBean.getGameBetType().getBetTypeCode();
		//不中
		if(BetTypeCodeMapping.lhc_55_39_87.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(18000l).equals(validatedBean.getSingleWin()), "四不中倍数必须等于1.8");
		}else if(BetTypeCodeMapping.lhc_55_39_88.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(20000l).equals(validatedBean.getSingleWin()), "五不中倍数必须等于2");	
		}else if(BetTypeCodeMapping.lhc_55_39_89.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(25000l).equals(validatedBean.getSingleWin()), "六不中倍数必须等于2.5");	
		}else if(BetTypeCodeMapping.lhc_55_39_90.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(30000l).equals(validatedBean.getSingleWin()), "七不中倍数必须等于3");	
		}else if(BetTypeCodeMapping.lhc_55_39_91.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(36000l).equals(validatedBean.getSingleWin()), "八不中倍数必须等于3.6");	
		}
		//連碼
		else if(BetTypeCodeMapping.lhc_56_42_13.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(6800000l).equals(validatedBean.getSingleWin()), "三全中倍数必须等于680");	
		}else if(BetTypeCodeMapping.lhc_56_42_14.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(0l).equals(validatedBean.getSingleWin()), "三中二倍数必须等于0");	
		}else if(BetTypeCodeMapping.lhc_56_42_15.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(680000l).equals(validatedBean.getSingleWin()), "二全中倍数必须等于68");	
		}else if(BetTypeCodeMapping.lhc_56_42_16.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(0l).equals(validatedBean.getSingleWin()), "二中特倍数必须等于0");	
		}else if(BetTypeCodeMapping.lhc_56_42_17.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(1600000l).equals(validatedBean.getSingleWin()), "特串倍数必须等于160");	
		}
		//連肖 中 and 不中
		else if(BetTypeCodeMapping.lhc_55_40_92.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_40_93.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_40_94.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_40_95.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_41_92.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_41_93.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_41_94.name().contains(betTypeCode)||
				BetTypeCodeMapping.lhc_55_41_95.name().contains(betTypeCode)){
			Assert.isTrue(Long.valueOf(0l).equals(validatedBean.getSingleWin()), "連肖 中 and 不中倍数必须等于0");	
		}
		else{
			//不會出現的狀況
			throw new GameBetAmountErrorException();
		}
		
		Assert.isTrue(validatedBean.getMoneyMode().getValue() == 1, "必须为元模式");

		if (validatedBean.getTotalAmount() > 9999990000l) {
			//log.error("投注金额不对");
			throw new GameBetAmountErrorException();
		}
	}
}
