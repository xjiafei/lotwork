package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: BetNumberHZAndKDAndBDValidateExecutor 
* @Description: 和值跨度包胆注数验证
* @author Richard,david
* @date 2013-8-6 上午10:13:06 
*
 */
public class BetNumberHZAndKDAndBDValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	/** 
	* @Fields GAMEMETHOD_MAP : 判断是什么玩法，走什么玩法的规则，因为jdk1.7以前switch语句不支持String，这里做个map做转换 
	*/
	private static final Map<String, Integer> GAMEMETHOD_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields SX_ZHIXUAN_HEZHI_BETS_MAP :三星直选和值对应的投注数0-27
	*/
	private static final Map<String, Integer> SX_ZHIXUAN_HEZHI_BETS_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields SX_ZHIXUAN_KUADU_BETS_MAP : 三星直选跨度对应的投注数0-9
	*/
	private static final Map<String, Integer> SX_ZHIXUAN_KUADU_BETS_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields SX_ZUXUAN_HEZHI_BETS_MAP : 三星组选和值对应投注数1-26 
	*/
	private static final Map<String, Integer> SX_ZUXUAN_HEZHI_BETS_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields SX_ZUXUAN_BAODAN_BETS : 三星组选包胆中奖注数是固定值54 
	*/
	private static final Map<String, Integer> SX_ZUXUAN_BAODAN_BETS = new HashMap<String, Integer>();
	static{
		SX_ZUXUAN_BAODAN_BETS.put("0", 54);
		SX_ZUXUAN_BAODAN_BETS.put("1", 54);
		SX_ZUXUAN_BAODAN_BETS.put("2", 54);
		SX_ZUXUAN_BAODAN_BETS.put("3", 54);
		SX_ZUXUAN_BAODAN_BETS.put("4", 54);
		SX_ZUXUAN_BAODAN_BETS.put("5", 54);
		SX_ZUXUAN_BAODAN_BETS.put("6", 54);
		SX_ZUXUAN_BAODAN_BETS.put("7", 54);
		SX_ZUXUAN_BAODAN_BETS.put("8", 54);
		SX_ZUXUAN_BAODAN_BETS.put("9", 54);
	}

	/** 
	* @Fields EX_ZUXUAN_BAODAN_BETS : 二星组选包胆中奖注数是固定值9
	*/
	private static final  Map<String, Integer> EX_ZUXUAN_BAODAN_BETS = new HashMap<String, Integer>();
	static{
		EX_ZUXUAN_BAODAN_BETS.put("0", 9);
		EX_ZUXUAN_BAODAN_BETS.put("1", 9);
		EX_ZUXUAN_BAODAN_BETS.put("2", 9);
		EX_ZUXUAN_BAODAN_BETS.put("3", 9);
		EX_ZUXUAN_BAODAN_BETS.put("4", 9);
		EX_ZUXUAN_BAODAN_BETS.put("5", 9);
		EX_ZUXUAN_BAODAN_BETS.put("6", 9);
		EX_ZUXUAN_BAODAN_BETS.put("7", 9);
		EX_ZUXUAN_BAODAN_BETS.put("8", 9);
		EX_ZUXUAN_BAODAN_BETS.put("9", 9);
	}

	/** 
	* @Fields EX_ZHIXUAN_HEZHI_BETS_MAP :  二星直选和值对应投注数 0-18
	*/
	private static final Map<String, Integer> EX_ZHIXUAN_HEZHI_BETS_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields EX_ZHIXUAN_KUADU_BETS_MAP : 二星直选跨度对应的投注数0-9
	*/
	private static final Map<String, Integer> EX_ZHIXUAN_KUADU_BETS_MAP = new HashMap<String, Integer>();

	/** 
	* @Fields EX_ZUXUAN_HEZHI_BETS_MAP : 二星组选和值对应投注数1-17
	*/
	private static final Map<String, Integer> EX_ZUXUAN_HEZHI_BETS_MAP = new HashMap<String, Integer>();
	
	private static final Map<String,Integer> K3_HEZHI_HEZHI_HEZHI_BETS_MAP = new HashMap<String, Integer>();
	
	private static final Map<String,Integer> K3_SANTONGHAODANXUAN_BETS_MAP = new HashMap<String, Integer>();
	
	private static final Map<String,Integer> K3_SANTONGHAOTONGXUAN_BETS_MAP = new HashMap<String, Integer>();
	
	private static final Map<String,Integer> K3_YIBUTONGHAO_BETS_MAP = new HashMap<String, Integer>();
	
	static {
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("3", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("4", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("5", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("6", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("7", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("8", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("9", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("10", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("11", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("12", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("13", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("14", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("15", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("16", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("17", 1);
		K3_HEZHI_HEZHI_HEZHI_BETS_MAP.put("18", 1);
	}
	static {
		K3_SANTONGHAODANXUAN_BETS_MAP.put("111", 1);
		K3_SANTONGHAODANXUAN_BETS_MAP.put("222", 1);
		K3_SANTONGHAODANXUAN_BETS_MAP.put("333", 1);
		K3_SANTONGHAODANXUAN_BETS_MAP.put("444", 1);
		K3_SANTONGHAODANXUAN_BETS_MAP.put("555", 1);
		K3_SANTONGHAODANXUAN_BETS_MAP.put("666", 1);
	}
	static {
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("111", 0);
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("222", 0);
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("333", 0);
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("444", 0);
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("555", 0);
		K3_SANTONGHAOTONGXUAN_BETS_MAP.put("666", 1);
	}
	
	static {
		K3_YIBUTONGHAO_BETS_MAP.put("1", 1);
		K3_YIBUTONGHAO_BETS_MAP.put("2", 1);
		K3_YIBUTONGHAO_BETS_MAP.put("3", 1);
		K3_YIBUTONGHAO_BETS_MAP.put("4", 1);
		K3_YIBUTONGHAO_BETS_MAP.put("5", 1);
		K3_YIBUTONGHAO_BETS_MAP.put("6", 1);
	}

	static {
		EX_ZUXUAN_HEZHI_BETS_MAP.put("1", 1);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("2", 1);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("3", 2);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("4", 2);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("5", 3);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("6", 3);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("7", 4);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("8", 4);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("9", 5);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("10", 4);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("11", 4);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("12", 3);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("13", 3);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("14", 2);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("15", 2);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("16", 1);
		EX_ZUXUAN_HEZHI_BETS_MAP.put("17", 1);
	}

	static {
		EX_ZHIXUAN_KUADU_BETS_MAP.put("0", 10);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("1", 18);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("2", 16);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("3", 14);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("4", 12);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("5", 10);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("6", 8);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("7", 6);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("8", 4);
		EX_ZHIXUAN_KUADU_BETS_MAP.put("9", 2);
	}

	static {
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("0", 1);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("1", 2);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("2", 3);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("3", 4);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("4", 5);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("5", 6);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("6", 7);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("7", 8);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("8", 9);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("9", 10);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("10", 9);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("11", 8);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("12", 7);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("13", 6);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("14", 5);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("15", 4);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("16", 3);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("17", 2);
		EX_ZHIXUAN_HEZHI_BETS_MAP.put("18", 1);
	}

	static {
		SX_ZUXUAN_HEZHI_BETS_MAP.put("1", 1);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("2", 2);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("3", 2);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("4", 4);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("5", 5);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("6", 6);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("7", 8);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("8", 10);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("9", 11);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("10", 13);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("11", 14);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("12", 14);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("13", 15);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("14", 15);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("15", 14);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("16", 14);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("17", 13);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("18", 11);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("19", 10);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("20", 8);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("21", 6);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("22", 5);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("23", 4);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("24", 2);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("25", 2);
		SX_ZUXUAN_HEZHI_BETS_MAP.put("26", 1);
	}

	static {
		SX_ZHIXUAN_KUADU_BETS_MAP.put("0", 10);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("1", 54);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("2", 96);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("3", 126);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("4", 144);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("5", 150);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("6", 144);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("7", 126);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("8", 96);
		SX_ZHIXUAN_KUADU_BETS_MAP.put("9", 54);
	}

	static {
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("0", 1);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("1", 3);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("2", 6);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("3", 10);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("4", 15);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("5", 21);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("6", 28);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("7", 36);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("8", 45);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("9", 55);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("10", 63);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("11", 69);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("12", 73);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("13", 75);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("14", 75);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("15", 73);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("16", 69);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("17", 63);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("18", 55);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("19", 45);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("20", 36);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("21", 28);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("22", 21);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("23", 15);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("24", 10);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("25", 6);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("26", 3);
		SX_ZHIXUAN_HEZHI_BETS_MAP.put("27", 1);
	}

	static {
		GAMEMETHOD_MAP.put("12-10-33", 1);//前三直选和值
		GAMEMETHOD_MAP.put("12-10-34", 2);//前三直选跨度
		GAMEMETHOD_MAP.put("12-11-33", 3);//前三组选组选和值
		GAMEMETHOD_MAP.put("12-11-39", 4);//前三组选组选包胆

		GAMEMETHOD_MAP.put("13-10-33", 5);//后三直选和值
		GAMEMETHOD_MAP.put("13-10-34", 6);//后三直选跨度
		GAMEMETHOD_MAP.put("13-11-33", 7);//后三组选组选和值
		GAMEMETHOD_MAP.put("13-11-39", 8);//后三组选组选包胆
		
		GAMEMETHOD_MAP.put("33-10-33", 1);//中三直选和值
		GAMEMETHOD_MAP.put("33-10-34", 2);//中三直选跨度
		GAMEMETHOD_MAP.put("33-11-33", 3);//中三组选组选和值
		GAMEMETHOD_MAP.put("33-11-39", 4);//中三组选组选包胆

		GAMEMETHOD_MAP.put("14-10-33", 9);//后二直选和值
		GAMEMETHOD_MAP.put("14-10-34", 10);//后二直选跨度
		GAMEMETHOD_MAP.put("14-11-33", 11);//后二组选和值
		GAMEMETHOD_MAP.put("14-11-39", 12);//后二组选包胆

		GAMEMETHOD_MAP.put("15-10-33", 13);//前二直选和值
		GAMEMETHOD_MAP.put("15-10-34", 14);//前二直选跨度
		GAMEMETHOD_MAP.put("15-11-33", 15);//前二组选和值
		GAMEMETHOD_MAP.put("15-11-39", 16);//前二组选包胆

        GAMEMETHOD_MAP.put("30-10-33", 17);//P5后二直选和值
        GAMEMETHOD_MAP.put("30-10-34", 18);//P5后二直选跨度
        GAMEMETHOD_MAP.put("30-11-33", 19);//P5后二组选和值
        GAMEMETHOD_MAP.put("30-11-39", 20);//P5后二组选包胆
        //k3 和值
        GAMEMETHOD_MAP.put("34-28-71", 21);
        GAMEMETHOD_MAP.put("35-29-72", 22);
        GAMEMETHOD_MAP.put("36-30-73", 23);
        GAMEMETHOD_MAP.put("42-36-78", 24);
        //超级对子
        GAMEMETHOD_MAP.put("46-10-33", 1);//前三直选和值
		GAMEMETHOD_MAP.put("46-10-34", 2);//前三直选跨度
		GAMEMETHOD_MAP.put("46-11-33", 3);//前三组选组选和值
		GAMEMETHOD_MAP.put("46-11-39", 4);//前三组选组选包胆

		GAMEMETHOD_MAP.put("47-10-33", 5);//后三直选和值
		GAMEMETHOD_MAP.put("47-10-34", 6);//后三直选跨度
		GAMEMETHOD_MAP.put("47-11-33", 7);//后三组选组选和值
		GAMEMETHOD_MAP.put("47-11-39", 8);//后三组选组选包胆
		
		GAMEMETHOD_MAP.put("51-10-33", 1);//中三直选和值
		GAMEMETHOD_MAP.put("51-10-34", 2);//中三直选跨度
		GAMEMETHOD_MAP.put("51-11-33", 3);//中三组选组选和值
		GAMEMETHOD_MAP.put("51-11-39", 4);//中三组选组选包胆

		GAMEMETHOD_MAP.put("48-10-33", 9);//后二直选和值
		GAMEMETHOD_MAP.put("48-10-34", 10);//后二直选跨度
		GAMEMETHOD_MAP.put("48-11-33", 11);//后二组选和值
		GAMEMETHOD_MAP.put("48-11-39", 12);//后二组选包胆

		GAMEMETHOD_MAP.put("49-10-33", 13);//前二直选和值
		GAMEMETHOD_MAP.put("49-10-34", 14);//前二直选跨度
		GAMEMETHOD_MAP.put("49-11-33", 15);//前二组选和值
		GAMEMETHOD_MAP.put("49-11-39", 16);//前二组选包胆
        
        

	}

	public static final Integer getMapValueByKey(String key) {
		return GAMEMETHOD_MAP.get(key);
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();
		// 前三  12 后三 13 前二15 后二 14  直选10 组选11  和值33 跨度34
		String key = validatedBean.getGameBetType().getGameGroupCode() + "-" + validatedBean.getGameBetType().getGameSetCode() + "-"
				+ validatedBean.getGameBetType().getBetMethodCode();
		Integer value = getMapValueByKey(key);
		Integer totbets = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = getBetCountMap(value, map);//根据投注方式或者需要的map
		for (String bet : bets) {
			totbets += map.get(bet)==null?0: map.get(bet);
		}
		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}

	private Map<String, Integer> getBetCountMap(Integer value, Map<String, Integer> map) {
		switch (value) {
		case 1:
			map=SX_ZHIXUAN_HEZHI_BETS_MAP;
			break;
		case 2:
			map=SX_ZHIXUAN_KUADU_BETS_MAP;
			break;
		case 3:
			map=SX_ZUXUAN_HEZHI_BETS_MAP;
			break;
		case 4:
			map=SX_ZUXUAN_BAODAN_BETS;
			break;
		case 5:
			map=SX_ZHIXUAN_HEZHI_BETS_MAP;
			break;
		case 6:
			map=SX_ZHIXUAN_KUADU_BETS_MAP;
			break;
		case 7:
			map=SX_ZUXUAN_HEZHI_BETS_MAP;
			break;
		case 8:
			map=SX_ZUXUAN_BAODAN_BETS;
			break;
		case 9:
			map=EX_ZHIXUAN_HEZHI_BETS_MAP;
			break;
		case 10:
			map=EX_ZHIXUAN_KUADU_BETS_MAP;
			break;
		case 11:
			map=EX_ZUXUAN_HEZHI_BETS_MAP;
			break;
		case 12:
			map=EX_ZUXUAN_BAODAN_BETS;
			break;
		case 13:
			map=EX_ZHIXUAN_HEZHI_BETS_MAP;
			break;
		case 14:
			map=EX_ZHIXUAN_KUADU_BETS_MAP;
			break;
		case 15:
			map=EX_ZUXUAN_HEZHI_BETS_MAP;
			break;
		case 16:
			map=EX_ZUXUAN_BAODAN_BETS;
			break;
        case 17:
            map=EX_ZHIXUAN_HEZHI_BETS_MAP;
            break;
        case 18:
            map=EX_ZHIXUAN_KUADU_BETS_MAP;
            break;
        case 19:
            map=EX_ZUXUAN_HEZHI_BETS_MAP;
            break;
        case 20:
            map=EX_ZUXUAN_BAODAN_BETS;
            break;
        case 21:
            map=K3_HEZHI_HEZHI_HEZHI_BETS_MAP;
            break;
        case 22:
            map=K3_SANTONGHAOTONGXUAN_BETS_MAP;
            break;
        case 23:
            map=K3_SANTONGHAODANXUAN_BETS_MAP;
            break;
        case 24:
            map=K3_YIBUTONGHAO_BETS_MAP;
            break;
		default:
			break;
		}
		return map;
	}
}
