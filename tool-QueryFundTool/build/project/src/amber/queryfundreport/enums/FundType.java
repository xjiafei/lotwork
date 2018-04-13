package amber.queryfundreport.enums;

import java.util.ArrayList;
import java.util.List;

public enum FundType {
	ADAL("充值"), ADML("充值"), AAXX("管理员增"), BDRX("撤销派奖"), BIRX("转入"), CEXX("客户理赔"), CFCX(
			"撤单费用"), CRVC("投注退款"), CWCR("提现退回"), CWCS("提现"), CWTF("提现"), CWTR(
			"提现退回"), CWTS("提现"), DAXX("管理员减"), DVCB("投注扣款"), DVCN("投注扣款"), IPXX(
			"平台奖励"), MDAX("充值"), PDXX("奖金派送"), PGXX("活动礼金"), RBRC("充值让利"), RHAX(
			"投注返点"), RRHA("撤销返点"), RRSX("撤销返点"), RRXX("转入"), RSXX("投注返点"), SCDX(
			"小额扣减"), SCRX("小额接收"), SOSX("转出"), WPXX("转出"), TPXX("来自旧平台");
	public final String typeName;

	FundType(String typeName) {
		this.typeName = typeName;
	}

	public static List<FundType> getTypes(String... typeNames) {
		List<FundType> list = new ArrayList<FundType>();
		FundType[] enums = FundType.values();
		for(String typeName:typeNames){
			for (FundType enumIn : enums) {
				if (enumIn.typeName.equals(typeName)) {
					list.add(enumIn);
				}
			}
		}
		return list;
	}
}
