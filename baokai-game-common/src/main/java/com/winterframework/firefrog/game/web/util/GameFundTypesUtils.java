package com.winterframework.firefrog.game.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName GameFundTypesUtils 
* @Description version 2  摘要帮助类 
* @author  hugh
* @date 2014年7月31日 上午10:32:29 
*  
*/
public class GameFundTypesUtils {
	
//	投注冻结
	public final static int GAME_BET_FREEZEN_TYPE = 1;
	
//	派发奖金
	public final static int GAME_DISTRIBUTE_TYPE = 2;
	
//	投注扣款
	public final static int GAME_BET_REDUCE_TYPE = 3;
	
//	撤销扣款
	public final static int GAME_CANCEL_BET_TYPE = 4;
	
	public final static int ACTIVITY_TYPE = 5;
	/**
	 * 详细的交易类型
	 */
	public static int ACTIVITY_DETAIL_TYPE = 6001;
	
//	撤销返点 "GM-CRRX-1" : "GM-CRRX-2"
	public static int GAME_CANCEL_RET_DETEAIL_TYPE = 2001;
	
//	撤销奖金
	public static int GAME_CANCEL_AWARD_DETEAIL_TYPE = 2002;
	
//	撤单费用
	public static int GAME_CANCEL_FEE_DETEAIL_TYPE = 2003;
	
//	派奖后投注撤销
//	public static int GAME_CANCEL_BET_AFTER_DETEAIL_TYPE = 2004;
	
//	本金返还加款
	public static int GAME_BET_RETURN_DETEAIL_TYPE = 3001;
	
//	派发奖金
	public static int GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE = 3002;
	
//	奖金冻结
	public static int GAME_AWARD_FREEZER_DETEAIL_TYPE = 4001;
	
//	追号扣款冻结
	public static int GAME_PLAN_FREEZER_DETEAIL_TYPE = 4002;
	
//	投注扣款冻结
	public static int GAME_BET_FREEZER_DETEAIL_TYPE = 4003;
	
//	返点冻结
	public static int GAME_RET_FREEZER_DETEAIL_TYPE = 4004;
	
//	用户终止追号预约投注解冻
	public static int GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE = 5001;
	
//	系统撤追号投注解冻
	public static int GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE = 5002;
	
//	追中即停预约投注解冻
	public static int GAME_CONDITION_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE = 5003;
	
//	派奖后投注资金解冻  
	public static int GAME_BET_UNFREEZER_DETEAIL_TYPE = 5004;
	
//	用户撤单投注资金解冻
	public static int GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE = 5005;
	
//	系统撤单投注资金解冻
	public static int GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE = 5006;
	
//	派奖后奖金解冻
	public static int GAME_AWARD_UNFREEZER_DETEAIL_TYPE = 5007;
	
//	派奖后返点解冻  "GM-RTRX-1" : "GM-RTRX-2"
	public static int GAME_RET_UNFREEZER_DETEAIL_TYPE = 5008;
	
//	派奖后计划投注资金解冻
	public static int GAME_PLAN_UNFREEZER_DETEAIL_TYPE = 5009;
	
	//管理员撤销解冻资金
	public static int GAME_ADMIN_UNFREEZER_TYPE = 5010;
	
	//管理员撤销返还扣款
	public static int GAME_ADMIN_RETURN_TYPE = 5011;
	
	
	private static final Map<Integer, String> RISK_GAME_FUND_MAP = new HashMap<Integer, String>();
	private static final Map<Integer, Integer> GAME_RISK_FUND_TYPE_MAP = new HashMap<Integer, Integer>();
	static {
		//1
		GAME_RISK_FUND_TYPE_MAP.put(GAME_BET_FREEZER_DETEAIL_TYPE, GAME_BET_FREEZEN_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_PLAN_FREEZER_DETEAIL_TYPE, GAME_BET_FREEZEN_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_RET_FREEZER_DETEAIL_TYPE, GAME_BET_FREEZEN_TYPE);
		//2
		GAME_RISK_FUND_TYPE_MAP.put(GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE, GAME_DISTRIBUTE_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_BET_UNFREEZER_DETEAIL_TYPE, GAME_DISTRIBUTE_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_RET_UNFREEZER_DETEAIL_TYPE, GAME_DISTRIBUTE_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_PLAN_UNFREEZER_DETEAIL_TYPE, GAME_DISTRIBUTE_TYPE);
		//4
		GAME_RISK_FUND_TYPE_MAP.put(GAME_CANCEL_RET_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_CANCEL_AWARD_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_CANCEL_FEE_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_BET_RETURN_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_CONDITION_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_ADMIN_UNFREEZER_TYPE, GAME_CANCEL_BET_TYPE);
		GAME_RISK_FUND_TYPE_MAP.put(GAME_ADMIN_RETURN_TYPE, GAME_CANCEL_BET_TYPE);
		//5
		GAME_RISK_FUND_TYPE_MAP.put(ACTIVITY_DETAIL_TYPE, ACTIVITY_TYPE); 		
	}
//	TODO 未完成资金摘要对应关系
	static {
		RISK_GAME_FUND_MAP.put(GAME_BET_FREEZER_DETEAIL_TYPE, "GM-DVCB-1");   //投注扣款（冻结）, 4003用户投注金额被冻结
		RISK_GAME_FUND_MAP.put(GAME_BET_UNFREEZER_DETEAIL_TYPE, "GM-DVCB-2"); //（解冻并扣款 ）,  5004确认派奖后，进行实扣		
		RISK_GAME_FUND_MAP.put(GAME_PLAN_FREEZER_DETEAIL_TYPE, "GM-DVCN-1");  //追号扣款（冻结）, 4002追号时，追号资金冻结  
		RISK_GAME_FUND_MAP.put(GAME_PLAN_UNFREEZER_DETEAIL_TYPE, "GM-DVCN-2");//（解冻并扣款）,   5009派奖后计划投注资金解冻  //以前GM-DVNP-1		
		RISK_GAME_FUND_MAP.put(GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE, "GM-PDXX-3");//派发奖金	  3002	
		//GM-RSXX-1			确认派奖时（方案号），投注本人所得的返点                                                       //5008
		//GM-RHAX-2			确认派奖时（方案号），投注人所属的不同上级，分别获得的返点记录              //5008
		//GM-RRSX-1			撤销返点操作时，扣除的投注本人所得的返点                                                       //2001
		//GM-RRHA-2			撤销返点操作时，扣除的投注人所属的不同上级，分别获得的返点记录		//2001

		RISK_GAME_FUND_MAP.put(GAME_CANCEL_AWARD_DETEAIL_TYPE, "GM-BDRX-1");//	撤销奖金  2002
		RISK_GAME_FUND_MAP.put(GAME_CANCEL_FEE_DETEAIL_TYPE, "GM-CFCX-1");//	撤单费用  2003		
		
																		         //GM-CRVC-1                     单期投注、追号投注进行撤单时，按方案号解冻金额  
		RISK_GAME_FUND_MAP.put(GAME_CONDITION_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"GM-CRVC-2");//5003追中即停预约投注解冻 , 追中（x元）即停，解冻剩余追号金额
																				 //GM-CRVC-3                       官方未开奖，平台整期撤单情况
		RISK_GAME_FUND_MAP.put(GAME_BET_RETURN_DETEAIL_TYPE, "GM-CRVC-4");	     //GM-CRVC-4投注退款？  3001在开奖后，发现开错奖，且本期无需开奖，需要撤销派奖、返点及本金
																				 //本金返还加款 以前GM-RVCP-1
		
		RISK_GAME_FUND_MAP.put(GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, "GM-CRVC-1");//	5005用户撤单投注资金解冻         以前GM-CCBX-1
		RISK_GAME_FUND_MAP.put(GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, "GM-CRVC-3"); //	5006系统撤单投注资金解冻         以前GM-CCBX-1		
		RISK_GAME_FUND_MAP.put(GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"GM-CRVC-3");//	5002系统撤追号投注解冻             以前GM-CCBX-1
		RISK_GAME_FUND_MAP.put(GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"GM-CRVC-1");    //	5001用户终止追号预约投注解冻  以前GM-CCBX-1
		RISK_GAME_FUND_MAP.put(GAME_ADMIN_UNFREEZER_TYPE,"GM-CRVC-6");//管理员撤销解冻资金
		RISK_GAME_FUND_MAP.put(GAME_ADMIN_RETURN_TYPE,"GM-CRVC-5");    //管理员撤销返还扣款
		RISK_GAME_FUND_MAP.put(ACTIVITY_DETAIL_TYPE,"PM-PGXX-3");
	}
	
	private static final Map<Integer, String> RISK_GAME_FUND_MESSAGE_MAP = new HashMap<Integer, String>();
	static {
		//RISK_GAME_FUND_MESSAGE_MAP.put(GAME_BET_FREEZER_DETEAIL_TYPE, "投注冻结扣款");   //投注扣款（冻结）, 4003用户投注金额被冻结
		//RISK_GAME_FUND_MESSAGE_MAP.put(GAME_BET_UNFREEZER_DETEAIL_TYPE, "投注解冻并扣款"); //（解冻并扣款 ）,  5004确认派奖后，进行实扣		
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_PLAN_FREEZER_DETEAIL_TYPE, "追号扣款");  //追号扣款（冻结）, 4002追号时，追号资金冻结  
		//RISK_GAME_FUND_MESSAGE_MAP.put(GAME_PLAN_UNFREEZER_DETEAIL_TYPE, "追号解冻并扣款");//（解冻并扣款）,   5009派奖后计划投注资金解冻  //以前GM-DVNP-1		
		//RISK_GAME_FUND_MESSAGE_MAP.put(GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE, "派发奖金");//派发奖金	  3002	
		
		//GM-RSXX-1			确认派奖时（方案号），投注本人所得的返点                                                       //5008
		//GM-RHAX-2			确认派奖时（方案号），投注人所属的不同上级，分别获得的返点记录              //5008
		//GM-RRSX-1			撤销返点操作时，扣除的投注本人所得的返点                                                       //2001
		//GM-RRHA-2			撤销返点操作时，扣除的投注人所属的不同上级，分别获得的返点记录		//2001

		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_CANCEL_AWARD_DETEAIL_TYPE, "撤销奖金");//	撤销奖金  2002
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_CANCEL_FEE_DETEAIL_TYPE, "撤单费用");//	撤单费用  2003		
		
																		         //GM-CRVC-1                     单期投注、追号投注进行撤单时，按方案号解冻金额  
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_CONDITION_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"追中返款");//5003追中即停预约投注解冻 , 追中（x元）即停，解冻剩余追号金额
																				 //GM-CRVC-3                       官方未开奖，平台整期撤单情况
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_BET_RETURN_DETEAIL_TYPE, "系统撤单");	     //GM-CRVC-4投注退款？  3001在开奖后，发现开错奖，且本期无需开奖，需要撤销派奖、返点及本金
																				 //本金返还加款 以前GM-RVCP-1
		
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, "本人撤销");//	5005用户撤单投注资金解冻         以前GM-CCBX-1
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE, "系统撤单"); //	5006系统撤单投注资金解冻         以前GM-CCBX-1		
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"系统撤单");//	5002系统撤追号投注解冻             以前GM-CCBX-1
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE,"本人撤销");    //	5001用户终止追号预约投注解冻  以前GM-CCBX-1
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_ADMIN_UNFREEZER_TYPE,"管理员撤单");//管理员撤销解冻资金
		RISK_GAME_FUND_MESSAGE_MAP.put(GAME_ADMIN_RETURN_TYPE,"管理员撤单");    //管理员撤销返还扣款
		RISK_GAME_FUND_MESSAGE_MAP.put(ACTIVITY_DETAIL_TYPE,"活动派奖");  
	}
	
	public static final String getFundDigest(int gameFundType) {
		return RISK_GAME_FUND_MAP.get(gameFundType);
	}
	public static final String getFundMessage(int gameFundType) {
		return RISK_GAME_FUND_MESSAGE_MAP.get(gameFundType);
	}
	public static final int getRiskFundType(int fundCode) {
		return GAME_RISK_FUND_TYPE_MAP.get(fundCode);
	}
	//用户交易为正值的摘要
	public static List<String> addReason = Arrays.asList("GM-PDXX-3", "GM-RSXX-1", "GM-RHAX-2", "GM-CRVC-1","GM-CRVC-2",
			"GM-CRVC-3","GM-CRVC-4","GM-RTRX-1", "GM-RTRX-2", "GM-RVCP-1","GM-CCBX-1","PM-PGXX-3");//老的也先加上兼容  "GM-RTRX-1", "GM-RTRX-2", "GM-RVCP-1","GM-CCBX-1"
}
