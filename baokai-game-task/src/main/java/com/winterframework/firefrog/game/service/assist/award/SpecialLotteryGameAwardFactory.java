package com.winterframework.firefrog.game.service.assist.award;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ICommonKeyFactory;
import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.Status;
import com.winterframework.firefrog.game.service.assist.bet.ILotteryKeyFactor;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: SpecialLotteryGameAwardFactory 
* @Description: 保存用户奖金组返点值
* @author Richard
* @date 2014-4-14 上午11:52:45 
*
 */
@Service("specialLotteryGameAwardFactory")
public class SpecialLotteryGameAwardFactory implements ICommonKeyFactory<List<ILotteryKeyFactor>>, InitializingBean {

	@SuppressWarnings("rawtypes")
	private Map<String, SpecialLotteryGameAwardMap> maps;

	@PropertyConfig(value = "key.seperator")
	private String seperator;

	@Resource(name = "gameUserAwardDaoImpl")
	private IGameUserAwardDao gameAwardDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		ListableBeanFactory factory = new ClassPathXmlApplicationContext(
                "classpath:assistbettype/applicationContext-specialGameAwardLotteryConfig.xml");
		maps = factory.getBeansOfType(SpecialLotteryGameAwardMap.class);
	}

	@Override
	public List<ILotteryKeyFactor> getObject(IKeyGenerator keyGenerator) {

		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = (LotteryPlayMethodKeyGenerator) keyGenerator;
		SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> lotteryGameAwardUserMap = this
				.getLotteryWinSlipNumMap(maps);
		return lotteryGameAwardUserMap.get(keyGenerator);
	}

	private SpecialLotteryGameAwardMap<LotteryPlayMethodKeyGenerator, List<ILotteryKeyFactor>> getLotteryWinSlipNumMap(
			Map<String, SpecialLotteryGameAwardMap> maps2) {
		return maps.get("speciallotteryGameAwardtemplate");
	}

	/**
	 * 
	* @Title: saveGameAwardUser 
	* @Description: 保存用户奖金组返点值信息  
	* @param lotteryId
	* @param betTypeCode 格式如下 GameGroupCode_GameSetCode_BetMethodCode，示例：10_10_10
	* @param gameAwardUserGroupId
	* @param retValue
	* @throws Exception
	 */
	public void saveGameAwardUsers(Long lotteryId, String betTypeCode, Long gameAwardUserGroupId,
			Integer directLimitRet, Integer threeLimitRet) throws Exception {

		String[] betTypeCodes = betTypeCode.split("_");
		IKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(lotteryId, Integer.parseInt(betTypeCodes[0]),
				Integer.parseInt(betTypeCodes[1]), Integer.parseInt(betTypeCodes[2]), seperator);

		List<ILotteryKeyFactor> valueList = this.getObject(keyGenerator);
		if (valueList != null && !valueList.isEmpty()) {
			saveGameAwardUser(lotteryId, betTypeCode, gameAwardUserGroupId, threeLimitRet);
			return;
		}

		saveGameAwardUser(lotteryId, betTypeCode, gameAwardUserGroupId, directLimitRet);
	}

	private void saveGameAwardUser(Long lotteryId, String betTypeCode, Long gameAwardUserGroupId, Integer retValue)
			throws Exception {

		GameUserAward award = new GameUserAward();

		award.setAwardGroupId(gameAwardUserGroupId);
		award.setBetTypeCode(betTypeCode);
		award.setCreateTime(new Date());
		award.setLotteryId(lotteryId);
		award.setRetValue(retValue.longValue());
		award.setStatus(Status.CURRENT.getValue());

		gameAwardDao.insert(award);
	}

	/**
	 * 
	* @Title: updateGameAwardUsers 
	* @Description: 更新用户奖金组信息。
	* @param award
	* @param directLimitRet
	* @param threeLimitRet
	* @throws Exception
	 */
	public void updateGameAwardUsers(GameUserAward award, Integer directLimitRet, Integer threeLimitRet)
			throws Exception {

		String[] betTypeCodes = award.getBetTypeCode().split("_");
		IKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(award.getLotteryId(),
				Integer.parseInt(betTypeCodes[0]), Integer.parseInt(betTypeCodes[1]),
				Integer.parseInt(betTypeCodes[2]), seperator);

		List<ILotteryKeyFactor> valueList = this.getObject(keyGenerator);
		if (valueList != null && !valueList.isEmpty()) {
			award.setRetValue(directLimitRet.longValue());
			gameAwardDao.update(award);
			return;
		}

		award.setRetValue(threeLimitRet.longValue());
		gameAwardDao.update(award);

	}

}
