package com.winterframework.firefrog.game.dao.vo;

public enum GameWarnEvent {
	
	
	SYSTEM_B_AWARD_1("1_1","官方提前开奖x秒（暂缓奖期）","官方提前开奖x秒（开奖已暂缓）",1,"继续派奖或撤销本期方案"),//（开奖中心推送）
	
	SYSTEM_B_AWARD_2("1_2","官方提前开奖x秒（已处理）","官方提前开奖x秒（已处理）",2,"无需操作"),//（开奖中心推送）
	
	SYSTEM_R_AWARD("1_3","开奖号码错误（官方重新开奖）","官方重新开奖",1,"无需操作"),//（开奖中心推送）
	
	SYSTEM_N_AWARD("1_4","官方未开奖（暂缓奖期）","官方未开奖",1,"继续派奖或撤销本期方案"),//（开奖中心推送）
	
	SYSTEM_C_AWARD_TODO("1_5_1","系统取消开奖(已暂停销)","系统取消开奖",1,"继续派奖或撤销本期方案"),//（开奖中心推送）
	
	SYSTEM_C_AWARD("1_5","系统取消开奖","系统取消开奖",2,"无需操作"),//（开奖中心推送）
	
	MANUAL_PAUSE("2_5","派奖已被人工暂缓","暂缓派奖",1,"继续派奖或撤销本期方案"),	
	
	MANUAL_CONTINUE("2_6","无异常","继续派奖",2,"无需操作"),//（平台人工继续操作）
	
	MANUAL_R_AWARD("2_7","开奖号码错误（已处理）","输入官方实际开奖号码（已处理）",2,"无需操作"),//（平台人工继续操作）	
	
	MANUAL_R_AWARD_TODO("2_7_1","输入官方实际开奖号码（开始执行）","输入官方实际开奖号码（开始执行）",1,"无需操作"),//（平台人工继续操作）	
	
	MANUAL_I_AWARD("2_10","手工录号（已处理）","手工录号（已处理）",2,"无需操作"),//（手工录号）	
	
	MANUAL_I_AWARD_TODO("2_10_1","手工录号（开始执行）","手工录号（开始执行）",1,"无需操作"),//（手工录号）	
	
	MANUAL_CANCEL_PACKAGE("2_8","方案已被撤销（已处理）","批量撤销本期方案操作（已处理）",2,"无需操作"),//（（平台撤销本期方案操作）
	
	MANUAL_CANCEL_PACKAGE_TODO("2_8_1","批量撤销本期方案操作（开始执行）","批量撤销本期方案操作（开始执行）",1,"无需操作"),//（（平台撤销本期方案操作）	
	
	MANUAL_CANCEL_PLAN("2_9","追号已被撤销（已处理）","批量撤销追号方案操作（已处理）",2,"无需操作"),//（（平台撤销本期方案操作）
	
	MANUAL_CANCEL_PLAN_TODO("2_9_1","批量撤销追号方案操作（开始执行）","批量撤销追号方案操作（开始执行）",1,"无需操作"),//（（平台撤销本期方案操作）
	
//	2 开奖
//	3 撤销开奖时间提前的违规订单
//	4 撤销本期方案
//	5 撤销后续追号
//	6 重新开奖
//	9 继续派奖
//	10 补开奖
//	12 手工输入开奖号码
//	13 获取开奖号码
//	14 第一次追号
//	18 生成走势图（依赖获取开奖号码）
//	20 生成报表（依赖开奖）
	R_2("10_2","重做开奖任务","重做开奖任务",2,"无需操作"),
	R_3("10_3","重做撤销开奖时间提前的违规订单任务","重做撤销开奖时间提前的违规订单任务",2,"无需操作"),
	R_4("10_4","重做撤销本期方案任务","重做撤销本期方案任务",2,"无需操作"),
	R_5("10_5","重做撤销后续追号任务","重做撤销后续追号任务",2,"无需操作"),
	R_6("10_6","重做重新开奖任务","重做重新开奖任务",2,"无需操作"),
	R_9("10_9","重做补开奖任务","重做补开奖任务",2,"无需操作"),
	R_10("10_10","重做开奖任务任务","重做开奖任务任务",2,"无需操作"),
	R_12("10_12","重做手工输入开奖号码任务","重做手工输入开奖号码任务",2,"无需操作"),
	R_13("10_13","重做获取开奖号码任务","重做 获取开奖号码任务",2,"无需操作"),
	R_14("10_14","重做第一次追号任务","重做第一次追号任务",2,"无需操作"),
	R_18("10_18","重做生成走势图任务","重做生成走势图任务",2,"无需操作"),
	R_20("10_20","重做生成报表任务","重做生成报表任务",2,"无需操作");
	
	private String code;
	private String message;
	private String name;
	private int type;
	private String suggest;
	
	private GameWarnEvent(String code, String message, String name, int type,String suggest) {
		this.code = code;
		this.message = message;
		this.setName(name);
		this.type = type;
		this.suggest = suggest;
	}
	
	public static String getMessageByCode(String code){
		for (GameWarnEvent event : GameWarnEvent.values()) {
			if(event.code.equals(code)){
				return event.message;
			}
		}
		return null;
	}
	
	
	public static String getCodeByPageCode(Integer pageCode){
		for (GameWarnEvent event : GameWarnEvent.values()) {
			if(event.code.split("_")[1].equals(pageCode+"")){
				return event.code;
			}
		}
		return null;
	}
	public static int getTypeByCode(String code){
		for (GameWarnEvent event : GameWarnEvent.values()) {
			if(event.code.equals(code)){
				return event.type;
			}
		}
		return 0;
	}
	
	public static String getNameByCode(String code){
		for (GameWarnEvent event : GameWarnEvent.values()) {
			if(event.code.equals(code)){
				return event.name;
			}
		}
		return null;
	}
	public static String getSuggestByCode(String code){
		for (GameWarnEvent event : GameWarnEvent.values()) {
			if(event.code.equals(code)){
				return event.suggest;
			}
		}
		return null;
	}
	public static String getSuggestByRoutCode(String routCode){
		if(routCode == null){
			return null;
		}
		String suggest = "";
		String[] routs = routCode.split(",");
		for (String string : routs) {
			suggest +=  "," + getSuggestByCode(string) ;
		}
		
		return suggest.substring(1, suggest.length());
	}
	
	public static String getLastSuggestByRoutCode(String routCode){
		if(routCode == null){
			return null;
		}
		String[] routs = routCode.split(",");		
		return getSuggestByCode(routs[routs.length-1]);
	}
	public static String getRoutNameByRoutCode(String routCode){
		if(routCode == null){
			return null;
		}
		String routName = "";
		String[] routs = routCode.split(",");
		for (String string : routs) {
			routName +=  "," + getNameByCode(string) ;
		}
		
		return routName.substring(1, routName.length());
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
