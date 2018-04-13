/**   
* @Title: LotteryWinSlipNumCaculatorFactory.java 
* @Package com.winterframework.firefrog.game.service.wincaculate.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 上午11:46:16 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.wincaculate.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinCaculatorFactory;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.modules.utils.SpringContextHolder;

/** 
* @ClassName: LotteryWinSlipNumCaculatorFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-18 上午11:46:16 
*  
*/
public class LotteryWinSlipNumCaculatorFactory implements ILotteryWinCaculatorFactory<ILotteryWinSlipNumCaculator>,
		InitializingBean , ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(LotteryWinSlipNumCaculatorFactory.class);

	private Map<String, LotteryWinSlipNumMap> maps;

	/**
	* Title: getObject
	* Description:
	* @param keyGenerator
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.ICommonKeyFactory#getObject(com.winterframework.firefrog.common.validate.business.IKeyGenerator) 
	*/
	@Override
	public ILotteryWinSlipNumCaculator getObject(IKeyGenerator keyGenerator) {
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = (LotteryPlayMethodKeyGenerator) keyGenerator;
		String key=lotteryKeyGenerator.getLotteryType() + " "
				+ lotteryKeyGenerator.getGroupCode() + " " + lotteryKeyGenerator.getSetCode()
				+ lotteryKeyGenerator.getMethodCode();
		logger.info("记奖号码lotteryKeyGenerator:" +key);
		

		LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator> slipMap =(LotteryWinSlipNumMap<LotteryPlayMethodKeyGenerator, ILotteryWinSlipNumCaculator>) this.applicationContext.getBean("lottery"+lotteryKeyGenerator.getLotteryType());
		
		ILotteryWinSlipNumCaculator caculatorMap = slipMap.get(lotteryKeyGenerator);
		if (caculatorMap == null) {
			caculatorMap = slipMap.getParent().get(lotteryKeyGenerator);
		}
		if(caculatorMap != null){
			logger.info(key+"成功后去处理类："+caculatorMap.getClass().getName());
		}else{
			logger.info(key+"成功后去处理类："+caculatorMap);
		}
		
		return caculatorMap;
	}

	
	/**
	* Title: afterPropertiesSet
	* Description:
	* @throws Exception 
	* @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
	*/
	@Override
	public void afterPropertiesSet() throws Exception {
	
	}
	private  ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext; //NOSONAR
	}
}
