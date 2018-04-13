/**   
* @Title: LotteryWinSlipNumCaculatorFactory.java 
* @Package com.winterframework.firefrog.game.service.wincaculate.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 上午11:46:16 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.assist.bet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ICommonKeyFactory;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: LotteryWinSlipNumCaculatorFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-1 上午10:13:43 
*  
*/
public class SpecialLotteryAssistFactory implements ICommonKeyFactory<List<ILotteryKeyFactor>>, InitializingBean {

	private Map<String, SpecialLotteryAssistMap> maps;
	@PropertyConfig(value = "key.seperator")
	private String seperator;
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;

	/**
	* Title: getObject
	* Description:
	* @param keyGenerator
	* @return 
	* @see com.winterframework.firefrog.common.validate.business.ICommonKeyFactory#getObject(com.winterframework.firefrog.common.validate.business.IKeyGenerator) 
	*/
	@Override
	public List<ILotteryKeyFactor> getObject(IKeyGenerator keyGenerator) {
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = (LotteryPlayMethodKeyGenerator) keyGenerator;
		SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> lotteryWinSlipNumMap = this
				.getLotteryWinSlipNumMap(maps, lotteryKeyGenerator.getLotteryType());
		Map<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> caculatorMap = this
				.getCaculatorMap(lotteryWinSlipNumMap);
		return caculatorMap == null ? null : caculatorMap.get(lotteryKeyGenerator);
	}

	private SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> getLotteryWinSlipNumMap(
			Map<String, SpecialLotteryAssistMap> maps, Long lotteryType) {
		Iterator<String> it = maps.keySet().iterator();
		while (it.hasNext()) {
			SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> lotteryWinSlipNumMap = maps
					.get(it.next());
			if (lotteryWinSlipNumMap.getLotteryType() != null
					&& lotteryWinSlipNumMap.getLotteryType().longValue() == lotteryType.longValue()) {
				return lotteryWinSlipNumMap;
			}
		}
		return null;
	}

	private List<SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>>> mapList = new ArrayList<SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>>>();

	private void setAllCaculatorMapForLottery(
			SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> lotteryWinSlipNumMap) {
		mapList.add(lotteryWinSlipNumMap);
		if (lotteryWinSlipNumMap.getParent() == null) {
			return;
		}
		setAllCaculatorMapForLottery(lotteryWinSlipNumMap.getParent());
	}

	private Map<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> getCaculatorMap(
			SpecialLotteryAssistMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> lotteryWinSlipNumMap) {
		if (lotteryWinSlipNumMap == null) {
			return null;
		}
		setAllCaculatorMapForLottery(lotteryWinSlipNumMap);
		Map<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> caculatorMap = new HashMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>>();
		if (mapList.isEmpty()) {
			return null;
		}
		for (int i = mapList.size() - 1; i >= 0; i--) {
			caculatorMap.putAll(mapList.get(i));
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
		ListableBeanFactory factory = new ClassPathXmlApplicationContext(
				"classpath:assistbettype/applicationContext-specialLotteryConfig.xml");
		maps = factory.getBeansOfType(SpecialLotteryAssistMap.class);
	}

	/** 
	* @Title: getSlipItemAssistList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return
	*/
	public List<ItemAssist> getSlipItemAssistList(BetMethodDescription betMethod, Long userId) {
		
		//现在4级结构直接做在了game_award 表中   直接查询此表插入值   当查出三级结构的奖金信息为0时，获取下级奖金信息
		Long actualBonus = gameAwardDao.getActualBonus(betMethod.getLotteryid(), betMethod.getGameGroupCode() + "_"
				+ betMethod.getGameSetCode() + "_" + betMethod.getBetMethodCode(), userId);

		if (actualBonus==null||actualBonus == 0) {//获取下级信息
			List<ItemAssist> resultList = new ArrayList<ItemAssist>();
			List<GameAward> gaList = gameAwardDao.getGameAwardListByBetCodeParent(
					betMethod.getLotteryid(),
					betMethod.getGameGroupCode() + "_" + betMethod.getGameSetCode() + "_"
							+ betMethod.getBetMethodCode(), userId);
			for (GameAward ga : gaList) {
				ItemAssist assist = new ItemAssist();
				assist.setEvaluatAward(ga.getActualBonus());
				assist.setEvaluatAwardDown(ga.getActualBonusDown());
				assist.setBetTypeCode(ga.getBetTypeCode());
				assist.setCreateTime(new Date());
				resultList.add(assist);
			}
			return resultList;
		}
		
//		IKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(betMethod.getLotteryid(),
//				betMethod.getGameGroupCode(), betMethod.getGameSetCode(), betMethod.getBetMethodCode(), seperator);
//		List<ILotteryKeyFactor> valueList = this.getObject(keyGenerator);
//		if (valueList != null && !valueList.isEmpty()) {
//			List<ItemAssist> resultList = new ArrayList<ItemAssist>();
//			for (ILotteryKeyFactor iLotteryKeyFactor : valueList) {
//				Long actualBonus = gameAwardDao
//						.getActualBonus(
//								betMethod.getLotteryid(),
//								iLotteryKeyFactor.getGroupCode()
//										+ "_"
//										+ iLotteryKeyFactor.getSetCode()
//										+ "_"
//										+ iLotteryKeyFactor.getMethodCode()
//										+ (iLotteryKeyFactor.getMethodType() != null ? ("_" + iLotteryKeyFactor
//												.getMethodType()) : ""), userId);
//				ItemAssist assist = new ItemAssist();
//				assist.setEvaluatAward(actualBonus);
//				if (iLotteryKeyFactor.getMethodType() != null) {
//					assist.setBetTypeCode(iLotteryKeyFactor.getGroupCode() + "_" + iLotteryKeyFactor.getSetCode() + "_"
//							+ iLotteryKeyFactor.getMethodCode() + "_" + iLotteryKeyFactor.getMethodType());
//				} else {
//					assist.setBetTypeCode(iLotteryKeyFactor.getGroupCode() + "_" + iLotteryKeyFactor.getSetCode() + "_"
//							+ iLotteryKeyFactor.getMethodCode());
//				}
//				assist.setCreateTime(new Date());
//				resultList.add(assist);
//			}
//			return resultList;
//		}
		return null;
	}
}
