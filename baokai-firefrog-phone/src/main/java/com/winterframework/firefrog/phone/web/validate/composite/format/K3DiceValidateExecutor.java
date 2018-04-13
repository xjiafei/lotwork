/**   
* @Title: K3DiceValidateExecutor.java 
* @Package com.winterframework.firefrog.game.web.validate.composite.format 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-4-7 上午11:56:00 
* @version V1.0   
*/
package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.dto.GameDrawResultStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.phone.web.bet.entity.LotteryConfig;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: K3DiceValidateExecutor 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-4-7 上午11:56:00 
*  
*/
public class K3DiceValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(K3DiceValidateExecutor.class);
	@PropertyConfig("ballLists")
	private String ballListsStr;

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder()
				.getFileMode().getValue(), " ");
		LotteryConfig config = new LotteryConfig();
		config = convertJsonToObject(ballListsStr, LotteryConfig.class);
		
		String groupCodeName = GameAwardNameUtil.getName(validatedBean.getGameOrder().getLottery().getLotteryId(),
				validatedBean.getGameBetType().getGameGroupCode(), validatedBean.getGameBetType().getGameSetCode(),
				validatedBean.getGameBetType().getBetMethodCode(), 0);
		List<String> groupResult = new ArrayList<String>();
		for (GameDrawResultStruc struc : config.getBallLists()) {
			if (struc.getName().equals(groupCodeName)) {
				groupResult.add(struc.getOrder());
			}
		}
		if(groupResult.size()==0||!groupResult.contains(validatedBean.getBetDetail())){
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}

		((BetValidateContext) context).setBets(bets);
	}

	private ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * 从JSON数据到对象的转换
	 * 
	 * @param json
	 * @param objClass
	 * @return
	 * @throws Exception
	 */
	protected <T> T convertJsonToObject(String json, Class<T> objClass) throws Exception {
		return jsonMapper.readValue(json, objClass);
	}
}
