package com.winterframework.firefrog.fund.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.config.entity.ConfigEntity;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.config.web.controller.dto.ChargeDto;
import com.winterframework.firefrog.config.web.controller.dto.TransfertoUser;
import com.winterframework.firefrog.config.web.controller.dto.WithDrawCheck;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDtoUser;
import com.winterframework.modules.web.util.JsonMapper;

@Service("configUtils")
public class ConfigUtils {
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	//10
	public static final String CHARGE_COUTE = "chargeCoute";
	//{"vip":{"time":10,"lowLimit":2000000,"upLimit":5000000000},"user":{"time":10,"lowLimit":10000000,"upLimit":5000000000}}
	public static final String WITHDRAW = "withdraw";
	//{"time":5,"userLimit":1000000000,"vipLimit":5000000000}
	public static final String TRANSFER = "transfer";
	//1
	public static final String CHARGE_COUNT_DOWN = "chargeCountDown";
	//[{"charge_ratio":10,"type":1,"bel":1},{"charge_ratio":20,"type":2,"bel":2},{"charge_ratio":30,"type":3,"bel":3},{"charge_ratio":40,"type":4,"bel":4},{"charge_ratio":50,"type":5,"bel":5},{"charge_ratio":60,"type":6,"bel":6}]
	public static final String CHARGE = "charge";
	//{"part":1,"amt":100000000,"time":3}
	public static final String WITHDRAW_CHECK = "withdrawCheck";
	private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);

	public void saveConfig(ConfigEntity config) throws Exception {
		try {
			configService.saveConfig(config);
		} catch (Exception e) {
			log.error("saveConfig error.", e);
			throw e;
		}
	}

	public ChargeDto[] getCHARGE() {
		return getConfig("fund", CHARGE, ChargeDto[].class);
	}

	public Long getCHARGE_COUTE() {
		return getConfig("fund", CHARGE_COUTE, Long.class);
	}

	public WithdralDtoUser getWITHDRAW() {
		return getConfig("fund", WITHDRAW, WithdralDtoUser.class);
	}


	public TransfertoUser getTransfer() {
		return getConfig("fund", TRANSFER, TransfertoUser.class);
	}

	public Long getCHARGE_COUNT_DOWN() {
		return getConfig("fund", CHARGE_COUNT_DOWN, Long.class);
	}

	public WithDrawCheck getWITHDRAW_CHECK() {
		return getConfig("fund", WITHDRAW_CHECK, WithDrawCheck.class);
	}

    public String getIPSwich() {
        return getConfig("global", "ipList", String.class);
    }

	private <T> T getConfig(String model, String key, Class<T> cls) {
		String str = configService.getConfigValueByKey(model, key);
		return JsonMapper.nonEmptyMapper().fromJson(str, cls);
	}
}
