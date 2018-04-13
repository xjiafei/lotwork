package com.winterframework.firefrog.fund.enums;

import java.util.LinkedHashMap;
import java.util.Map;

import com.winterframework.firefrog.fund.enums.FundModel.FD;
//import com.winterframework.firefrog.fund.util.SNUtil;

public class FundModelTool {
	private final static Map<String, EnumItem> ss = new LinkedHashMap<String, EnumItem>();
	private final static Map<String, EnumItem> ss2 = new LinkedHashMap<String, EnumItem>();
	static {
		addItem(ss, FundModel.FD.AUTO.ITEMS.values());
		addItem(ss, FundModel.FD.ABDX.ITEMS.values());
		addItem(ss, FundModel.FD.CWXX.ITEMS.values());
		addItem(ss, FundModel.FD.MWXX.ITEMS.values());
		addItem(ss, FundModel.FD.MDAX.ITEMS.values());


		addItem(ss, FundModel.GM.BDRX.ITEMS.values());
		addItem(ss, FundModel.GM.CFCX.ITEMS.values());
		addItem(ss, FundModel.GM.CRVC.ITEMS.values());
		addItem(ss, FundModel.GM.DVCN.ITEMS.values());
		addItem(ss, FundModel.GM.DVCB.ITEMS.values());
		addItem(ss, FundModel.GM.RRSX.ITEMS.values());
		addItem(ss, FundModel.GM.RSXX.ITEMS.values());
		addItem(ss, FundModel.GM.RVCP.ITEMS.values());
		addItem(ss, FundModel.GM.PDXX.ITEMS.values());

		addItem(ss, FundModel.PM.RBRC.ITEMS.values());
		addItem(ss, FundModel.PM.IPXX.ITEMS.values());
		addItem(ss, FundModel.PM.PGXX.ITEMS.values());
		addItem(ss, FundModel.TF.DIXX.ITEMS.values());
		addItem(ss, FundModel.TF.TAIX.ITEMS.values());
		addItem(ss, FundModel.TF.TFXX.ITEMS.values());
		addItem(ss, FundModel.OT.BANB.ITEMS.values());
		addItem(ss, FundModel.OT.AAXX.ITEMS.values());
		addItem(ss, FundModel.OT.DAXX.ITEMS.values());
		addItem(ss, FundModel.OT.CEXX.ITEMS.values());
		addItem(ss, FundModel.OT.RGDK.ITEMS.values());

	}

	public static EnumItem getEnumItemBySummary(String model, String summary) {
		return ss.get(summary);
	}

	public static String createKey(EnumItem model) {
		return model.getModel().getCode() + "-" + model.getSummaryCode() + "-" + model.getTradeStatus();

	}

	public static void addItem(Map<String, EnumItem> map, EnumItem[] items) {
		for (EnumItem model : items) {
			map.put(createKey(model), model);
		}
	}

//	public static void main(String[] args) {
//		EnumItem obj=getEnumItemBySummary("","GM-CRVC-5");
//		new SNUtil().createBusinessSn(obj, 31);
//		System.out.println(obj);
//	}
}
