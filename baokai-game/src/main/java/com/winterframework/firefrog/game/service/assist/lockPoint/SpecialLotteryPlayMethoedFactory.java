package com.winterframework.firefrog.game.service.assist.lockPoint;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ICommonKeyFactory;
import com.winterframework.firefrog.game.lock.base.Method;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;

@Service("specialLotteryPlayMethoedFactory")
@Lazy(false)
@SuppressWarnings({"resource","rawtypes","unchecked"})
public class SpecialLotteryPlayMethoedFactory implements ICommonKeyFactory<List<Method>>,InitializingBean{
	
	private Map<String, SpecialPlayMethoedKeyAssistMap> maps;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	
		ListableBeanFactory factory = new ClassPathXmlApplicationContext(
				"classpath:lockPoint/applicationContext-specialLockPoitConfig.xml");
		        
		maps = factory.getBeansOfType(SpecialPlayMethoedKeyAssistMap.class);
		
	}
	
	@Override
	public List<Method> getObject(IKeyGenerator keyGenerator) {
		
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = (LotteryPlayMethodKeyGenerator) keyGenerator;
		SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List<Method>> lotteryWinSlipNumMap = this
				.getLotteryWinSlipNumMap(maps);
		return lotteryWinSlipNumMap.get(lotteryKeyGenerator);
	}
	
	private SpecialPlayMethoedKeyAssistMap<LotteryPlayMethodKeyGenerator, List<Method>> getLotteryWinSlipNumMap(
			Map<String, SpecialPlayMethoedKeyAssistMap> maps) {
		return maps.get("lockPointConfigtemplate");
	}
	
}
