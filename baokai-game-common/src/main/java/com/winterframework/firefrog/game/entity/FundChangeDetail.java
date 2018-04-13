package com.winterframework.firefrog.game.entity;
import java.util.HashMap;
import java.util.Map;

public class FundChangeDetail {
	private static final Map<String, String> map = new HashMap<String, String>();
	static {

		map.put("ADAL", "充值");
		map.put("ADML", "充值");
		map.put("CWCR", "提现退回");
		map.put("CWCS", "提现");
		map.put("CWTF", "提现");
		map.put("CWTF", "提现");
		map.put("CWTR", "提现退回");
		map.put("CWTR", "提现退回");
		map.put("CWTS", "提现");
		map.put("MDAX", "充值");
		map.put("BDRX", "撤销派奖");
		map.put("CFCX", "撤单费用");
		map.put("CRVC", "投注退款");
		map.put("DVCB", "投注扣款");
		map.put("DVCN", "投注扣款");
		map.put("PDXX", "奖金派送");
		map.put("RHAX", "投注返点");
		map.put("RRHA", "撤销返点");
		map.put("RRSX", "撤销返点");
		map.put("RSXX", "投注返点");
		map.put("AAXX", "管理员增");
		map.put("CEXX", "客户理赔");
		map.put("DAXX", "管理员减");
		map.put("IPXX", "平台奖励");
		map.put("PGXX", "活动礼金");
		map.put("RBRC", "充值让利");
		map.put("BIRX", "转入");
		map.put("RRXX", "转入");
		map.put("SCDX", "小额扣减");
		map.put("SCRX", "小额接收");
		map.put("SOSX", "转出");
		map.put("WPXX", "转出");

	}

	public static String getSummary(String summaryCode) {
		return map.get(summaryCode);
	}

}
