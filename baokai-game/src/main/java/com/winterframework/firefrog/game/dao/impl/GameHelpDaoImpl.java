package com.winterframework.firefrog.game.dao.impl;

import java.util.*;
import java.util.Map.Entry;

import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.firefrog.game.service.assist.bet.SpecialLotteryAssistFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameHelpDao;
import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameHelpAndBonus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

import javax.annotation.Resource;

/**
 * 
* @ClassName: GameHelpDaoImpl 
* @Description: 游戏 帮助Dao
* @author Richard
* @date 2013-8-23 上午9:59:37 
*
 */
@Repository("gameHelpDaoImpl")
public class GameHelpDaoImpl extends BaseIbatis3Dao<GameHelp> implements IGameHelpDao {
	@Resource(name = "specialLotteryAssistFactory")
	private SpecialLotteryAssistFactory specialLotteryAssistFactory;

	@Override
	public List<GameHelp> getGameHelpByLotteryId(Long lotteryid) throws Exception {

		return this.sqlSessionTemplate.selectList("getGameHelpByLotteryId", lotteryid);
	}

	@Override
	public List<GameHelp> getGameHelp(long lotteryid) {

		return null;
	}

	@Override
	public void addGameHelp(long lotteryid) {

	}

	@Override
	public void removeGameHelp(long lotteryid) {

	}

	@Override
	public void updateGameHelp(GameHelp gameHelp) {

	}

	@Override
	public void updateGameHelpList(List<GameHelp> gameHelpList) {
		for (GameHelp gh : gameHelpList) {
			this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameHelp.updateForCheck", gh);
		}
	}

	@Override
	public GameHelp getByBetMethod(long lotteryid, int gameGroupCode, int gameSetCode, int betMethodCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		//		map.put("gameGroupCode", gameGroupCode);
		//		map.put("gameSetCode", gameSetCode);
		//		map.put("betMethodCode", betMethodCode);
		map.put("betTypeCode", gameGroupCode + "_" + gameSetCode + "_" + betMethodCode);
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameHelp.getByBetMethod",
				map);
	}

	//四级结构的玩法需要特殊处理，找到玩法描述
	private static final Map<Long, String> lotteryMap = new HashMap<Long, String>();
	static {
		lotteryMap.put(99201L, "北京快乐8");
		lotteryMap.put(99301L, "山东11选五");
		lotteryMap.put(99302L, "江西11选五");
		lotteryMap.put(99303L, "广东11选五");
		lotteryMap.put(99304L, "重庆11选五");
		lotteryMap.put(99305L, "乐力11选5");
		lotteryMap.put(99306L, "顺利11选5");
		lotteryMap.put(99307L, "江苏11选5");
	}

	@Override
	public GameHelpAndBonus getByBetMethod(long lotteryid, long userid, int gameGroupCode, int gameSetCode,
			int betMethodCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		String tmp = gameGroupCode + "_" + gameSetCode + "_" + betMethodCode;
		map.put("lotteryid", lotteryid);
		//		map.put("gameGroupCode", gameGroupCode);
		//		map.put("gameSetCode", gameSetCode);
		//		map.put("betMethodCode", betMethodCode);
		map.put("betTypeCode", tmp);
		map.put("userid", userid);
		//20140507 add 经商讨暂时为此解决方法，等后续优化
		if (tmp.equals("30_13_65") || tmp.equals("30_13_66")) {

			return this.sqlSessionTemplate.selectOne(
					"com.winterframework.firefrog.game.dao.vo.GameHelp.getSpecialBetMethod", map);

		}
		BetMethodDescription betMethodDescription = new BetMethodDescription();
		betMethodDescription.setLotteryid(lotteryid);
		betMethodDescription.setGameGroupCode(gameGroupCode);
		betMethodDescription.setBetMethodCode(betMethodCode);
		betMethodDescription.setGameSetCode(gameSetCode);
		List<ItemAssist> itemAssists = specialLotteryAssistFactory.getSlipItemAssistList(betMethodDescription, userid);
		//TODO:暂时使用挨个替换的方式，以后采用bouns_code方式来替换描述中的“bouns_code”
		if (itemAssists != null && itemAssists.size() > 0) {
			List<GameHelpAndBonus> gameHelpAndBonuses = this.sqlSessionTemplate.selectList(
					"com.winterframework.firefrog.game.dao.vo.GameHelp.getByBetMethodByUseridList", map);
			/*if (tmp.equals("18_16_70")) {
				return replaceKl8QwBonus(gameHelpAndBonuses);
			} else {
				return replaceBonus(gameHelpAndBonuses);
			}*/
			return replaceAllBonus(gameHelpAndBonuses);
		}

		GameHelpAndBonus ghab = this.sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.game.dao.vo.GameHelp.getByBetMethodByUserid", map);
		if(ghab!=null){
			ghab.setMoreBouns(false);
		}
		return ghab;
	}

	private GameHelpAndBonus replaceBonus(List<GameHelpAndBonus> gameHelpAndBonuses) {
		//初始化一个对象
		GameHelpAndBonus gameHelpAndBonus = null;
		if (gameHelpAndBonuses != null && gameHelpAndBonuses.size() != 0) {
			gameHelpAndBonus = gameHelpAndBonuses.get(0);

		}
		String str = gameHelpAndBonus.getGamePromptDes();
		//装入数组进行排序
		Double[] doubles = new Double[gameHelpAndBonuses.size()];
		int i = 0;
		for (GameHelpAndBonus hb : gameHelpAndBonuses) {
			doubles[i] = Double.valueOf(hb.getActualBonus()) / 10000;
			i++;
		}
		Arrays.sort(doubles, new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return (o2 - o1) > 0 ? 1 : -1;
			}
		});
		for (double d : doubles) {
			str = str.replaceFirst("bonus", String.valueOf(d));
		}
		gameHelpAndBonus.setGamePromptDes(str);
		gameHelpAndBonus.setMoreBouns(true);
		gameHelpAndBonus.setGameExamplesDes(gameHelpAndBonus.getGameExamplesDes().replaceAll("bonus",
				String.valueOf(doubles[0])));
		return gameHelpAndBonus;
	}

	private GameHelpAndBonus replaceAllBonus(List<GameHelpAndBonus> gameHelpAndBonuses) {
		String promptDes = "";
		String examplesDes = "";
		Map<String, Long> effectBonus = new TreeMap<String, Long>();
		GameHelpAndBonus gameHelpAndBonus = null;
		if (!gameHelpAndBonuses.isEmpty()) {
			Long bonusUp=0L;
			Long bonusDown=0L;
			for (GameHelpAndBonus gameHelpAndBonu : gameHelpAndBonuses) {
				if(bonusUp<gameHelpAndBonu.getActualBonus()){					
					bonusUp=gameHelpAndBonu.getActualBonus();
					bonusDown=gameHelpAndBonu.getActualBonusDown();
				}
				effectBonus.put("bonus_" + gameHelpAndBonu.getTypeCode(), gameHelpAndBonu.getActualBonus());
				effectBonus.put("bonus2_" + gameHelpAndBonu.getTypeCode(), gameHelpAndBonu.getActualBonusDown());
				gameHelpAndBonus = gameHelpAndBonu;
			}
			gameHelpAndBonus.setActualBonus(bonusUp);	//设置最高奖金
			gameHelpAndBonus.setActualBonusDown(bonusDown);
			promptDes = gameHelpAndBonus.getGamePromptDes();
			examplesDes = gameHelpAndBonus.getGameExamplesDes();
			String initDes = examplesDes;
			for (Entry<String, Long> entry : effectBonus.entrySet()) {
				Double bonus = Double.valueOf(entry.getValue()) / 10000;

				if (Math.round(bonus) == bonus) {//bug 2923 需求小数点后是0的去掉0
					promptDes = promptDes.replaceAll(entry.getKey(),
							String.valueOf(Math.round(bonus)));
					if (initDes != null) {
						examplesDes = examplesDes.replaceAll(entry.getKey(),
								String.valueOf(Math.round(bonus)));
					} else {
						if (examplesDes == null) {
							examplesDes = "";
						}
						examplesDes = examplesDes + String.valueOf(Math.round(bonus)) + ",";
					}
				} else {

					promptDes = promptDes.replaceAll(entry.getKey(),
							String.valueOf(bonus));
					if (initDes != null) {
						examplesDes = examplesDes.replaceAll(entry.getKey(),
								String.valueOf(bonus));
					} else {
						if (examplesDes == null) {
							examplesDes = "";
						}
						examplesDes = examplesDes + String.valueOf(bonus) + ",";
					}
				}
			}
			gameHelpAndBonus.setMoreBouns(true);
			gameHelpAndBonus.setGamePromptDes(promptDes);
			gameHelpAndBonus.setGameExamplesDes(examplesDes);
		}
		return gameHelpAndBonus;
	}
	
	
	@Override
	public List<GameHelpAndBonus> getTipsAndGameName(long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("lotteryId", lotteryid);

		List<GameHelpAndBonus> ghab = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.game.dao.vo.GameHelp.getTipsAndGameName", map);
		return ghab;
	}

}
