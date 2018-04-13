package com.winterframework.firefrog.fund.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.config.web.controller.dto.ChargeDto;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.fund.enums.FundModel.GM;
import com.winterframework.firefrog.fund.service.IDisableAmtChangeService;
import com.winterframework.firefrog.fund.web.controller.ConfigUtils;

@Service("disableAmtChangeServiceImpl")
public class DisableAmtChangeServiceImpl implements IDisableAmtChangeService {

	@Resource(name = "configUtils")
	private ConfigUtils configUtils;
	
	/**
	 * 不可体现余额配置，从 xml中读取
	 */
	@Resource(name = "paramTypeMap")
	private Map<String, Long> paramTypeMap = new HashMap<String, Long>();

	public Map<String, Long> getParamTypeMap() {
		return paramTypeMap;
	}

	public void setParamTypeMap(Map<String, Long> paramTypeMap) {
		this.paramTypeMap = paramTypeMap;
	}

	@Override
	public Long getAddAmt(Long amount, EnumItem item) {
		ChargeDto dto = queryAmtChangeConfig(item);
		if (dto != null) {
			Long ratio = dto.getCharge_ratio();
			return amount * ratio / 100;
		} else {
			return 0L;
		}
	}

	@Override
	public Long getReduceAmt(Long amount, EnumItem item) {
		if (item == null) {
			return 0L;
		}
		Long ratio = 0L;
		if (GM.DVCB.ITEMS.DVCB_2.equals(item)) {
			ratio = 1L;
		}
		return amount * ratio;
	}

	private ChargeDto queryAmtChangeConfig(EnumItem item) {
		if (item == null) {
			return null;
		}
		String aimStr = item.getSummaryCode() + "-" + item.getTradeCode();
		Long type = paramTypeMap.get(aimStr);
		if(type==null){
			return null;
		}
		ChargeDto[] dtos = configUtils.getCHARGE();
		for (ChargeDto temp : dtos) {
			if (temp.getType().equals(type)) {
				return temp;
			}
		}
		return null;
	}
}
