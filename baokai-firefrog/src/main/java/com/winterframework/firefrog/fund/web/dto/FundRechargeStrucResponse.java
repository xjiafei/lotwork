package com.winterframework.firefrog.fund.web.dto;

import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;

public class FundRechargeStrucResponse {

	private FundChargeAppealVO fundChargeAppealVO;
	
	private FundChargeOrder fundChargeOrder;

	public FundChargeAppealVO getFundChargeAppealVO() {
		return fundChargeAppealVO;
	}

	public void setFundChargeAppealVO(FundChargeAppealVO fundChargeAppealVO) {
		this.fundChargeAppealVO = fundChargeAppealVO;
	}

	public FundChargeOrder getFundChargeOrder() {
		return fundChargeOrder;
	}

	public void setFundChargeOrder(FundChargeOrder fundChargeOrder) {
		this.fundChargeOrder = fundChargeOrder;
	}

	
}
