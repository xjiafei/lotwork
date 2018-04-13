/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.winterframework.firefrog.fund.web.dto.RtnStruc;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundBankWithSub extends BaseEntity {

	private static final long serialVersionUID = 8262342336842061543L;

	// alias
	public static final String TABLE_ALIAS = "FundBank";
	public static final String ALIAS_LOGO = "银行的lgo";
	public static final String ALIAS_NAME = "银行名字";
	public static final String ALIAS_CODE = "编码，例如icbc";
	public static final String ALIAS_URL = "url";
	public static final String ALIAS_IN_USE = "是否使用中 1：是 0:否";
	public static final String ALIAS_DEPOSIT = "能否充值";
	public static final String ALIAS_WITHDRAW = "能否提现";
	public static final String ALIAS_BIND = "是否需要绑定";
	public static final String ALIAS_MOWNECUM_ID = "monecum id";
	public static final String ALIAS_UPLIMIT = "普通充值上限";
	public static final String ALIAS_LOWLIMIT = "普通充值下限";
	public static final String ALIAS_VIPUPLIMIT = "vip充值上限";
	public static final String ALIAS_VIPLOWLIMIT = "vip充值下限";
	public static final String ALIAS_RTN_MIN = "返回手续费最小值";
	public static final String ALIAS_VERSION = "充值版本 1:企業版 0:個人版";

	// 返回手续费结构配置
	// [{"sm":1,"big":12,type:1,value:32},{"sm":1,"big":12,type:1,value:32}]]
	// sm:最小值
	// big：最大值
	// type：类型 2)百分比 1）固定
	// value：value
	public static final String ALIAS_RTN_STRUC = "返回手续费结构配置";
	public static final String ALIAS_RTN_SET = "1）mownecum返回的手续费  2）自己设置的手续费";

	// date formats

	// columns START
	private String logo;
	private String name;
	private String code;
	private String url;
	private Long inUse;
	private Long deposit;
	private Long withdraw;
	private Long bind;
	private Long mownecumId;
	@JsonProperty("upLimit")
	private Long uplimit;
	@JsonProperty("lowLimit")
	private Long lowlimit;
	@JsonProperty("vipUpLimit")
	private Long vipuplimit;
	@JsonProperty("vipLowLimit")
	private Long viplowlimit;
	private String rtnMin;
	@JsonIgnore
	private String strRtnStruc;
	private List<RtnStruc> rtnStruc;
	private Long rtnSet;
	private Long countdown;
	private Long other;
	private Long otheruplimit;
	private Long otherdownlimit;
	private Long othervipdownlimit;
	private Long othervipuplimit;
	private String helpIds;
	private Long canRechargeAppeal;
	private Long otherCanRechargeAppeal;
	private Long moveQuickType;
	private Long moveQuickDeposit;
	private Long moveDeposit;
	private Long vipOpen;
	private Long normalOpen;
	private Long moveVipOpen;
	private Long moveNormalOpen;
	private Long userId;
	private Long daylimit;
	private Long moveCanRechargeAppeal;
	private Long version;
	private Long mappingCode;
	// columns END

	public Long getDaylimit() {
		return daylimit;
	}

	public void setDaylimit(Long daylimit) {
		this.daylimit = daylimit;
	}

	public Long getMoveVipOpen() {
		return moveVipOpen;
	}

	public void setMoveVipOpen(Long moveVipOpen) {
		this.moveVipOpen = moveVipOpen;
	}

	public Long getMoveNormalOpen() {
		return moveNormalOpen;
	}

	public void setMoveNormalOpen(Long moveNormalOpen) {
		this.moveNormalOpen = moveNormalOpen;
	}

	public Long getMoveDeposit() {
		return moveDeposit;
	}

	public void setMoveDeposit(Long moveDeposit) {
		this.moveDeposit = moveDeposit;
	}

	public Long getOtherCanRechargeAppeal() {
		return otherCanRechargeAppeal;
	}

	public void setOtherCanRechargeAppeal(Long otherCanRechargeAppeal) {
		this.otherCanRechargeAppeal = otherCanRechargeAppeal;
	}

	public FundBankWithSub() {
	}

	public Long getCanRechargeAppeal() {
		return canRechargeAppeal;
	}

	public void setCanRechargeAppeal(Long canRechargeAppeal) {
		this.canRechargeAppeal = canRechargeAppeal;
	}

	public Long getCountdown() {
		return countdown;
	}

	public void setCountdown(Long countdown) {
		this.countdown = countdown;
	}

	public FundBankWithSub(Long id) {
		this.id = id;
	}

	public void setLogo(String value) {
		this.logo = value;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public String getCode() {
		return this.code;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getUrl() {
		return this.url;
	}

	public void setInUse(Long value) {
		this.inUse = value;
	}

	public Long getInUse() {
		return this.inUse;
	}

	public void setDeposit(Long value) {
		this.deposit = value;
	}

	public Long getDeposit() {
		return this.deposit;
	}

	public void setWithdraw(Long value) {
		this.withdraw = value;
	}

	public Long getWithdraw() {
		return this.withdraw;
	}

	public void setBind(Long value) {
		this.bind = value;
	}

	public Long getBind() {
		return this.bind;
	}

	public void setMownecumId(Long value) {
		this.mownecumId = value;
	}

	public Long getMownecumId() {
		return this.mownecumId;
	}

	public void setUplimit(Long value) {
		this.uplimit = value;
	}

	public Long getUplimit() {
		return this.uplimit;
	}

	public void setLowlimit(Long value) {
		this.lowlimit = value;
	}

	public Long getLowlimit() {
		return this.lowlimit;
	}

	public void setVipuplimit(Long value) {
		this.vipuplimit = value;
	}

	public Long getVipuplimit() {
		return this.vipuplimit;
	}

	public void setViplowlimit(Long value) {
		this.viplowlimit = value;
	}

	public Long getViplowlimit() {
		return this.viplowlimit;
	}

	public String getRtnMin() {
		return rtnMin;
	}

	public void setRtnMin(String rtnMin) {
		this.rtnMin = rtnMin;
	}

	public List<RtnStruc> getRtnStruc() {
		return rtnStruc;
	}

	public void setStrRtnStruc(String strRtnStruc) {
		if (strRtnStruc != null) {
			this.rtnStruc = JsonMapper.nonAlwaysMapper().fromJson(strRtnStruc,
					JsonMapper.nonAlwaysMapper().createCollectionType(List.class, RtnStruc.class));
		}
	}

	public String getStrRtnStruc() {
		if (rtnStruc == null)
			return null;
		return JsonMapper.nonAlwaysMapper().toJson(rtnStruc);
	}

	public void setRtnStruc(List<RtnStruc> rtnStruc) {
		this.rtnStruc = rtnStruc;
	}

	public Long getRtnSet() {
		return rtnSet;
	}

	public void setRtnSet(Long rtnSet) {
		this.rtnSet = rtnSet;
	}

	public Long getOther() {
		return other;
	}

	public void setOther(Long other) {
		this.other = other;
	}

	public Long getOtheruplimit() {
		return otheruplimit;
	}

	public void setOtheruplimit(Long otheruplimit) {
		this.otheruplimit = otheruplimit;
	}

	public Long getOtherdownlimit() {
		return otherdownlimit;
	}

	public void setOtherdownlimit(Long otherdownlimit) {
		this.otherdownlimit = otherdownlimit;
	}

	public Long getOthervipdownlimit() {
		return othervipdownlimit;
	}

	public void setOthervipdownlimit(Long othervipdownlimit) {
		this.othervipdownlimit = othervipdownlimit;
	}

	public Long getOthervipuplimit() {
		return othervipuplimit;
	}

	public void setOthervipuplimit(Long othervipuplimit) {
		this.othervipuplimit = othervipuplimit;
	}

	public Long getMoveQuickType() {
		return moveQuickType;
	}

	public void setMoveQuickType(Long moveQuickType) {
		this.moveQuickType = moveQuickType;
	}

	public Long getMoveQuickDeposit() {
		return moveQuickDeposit;
	}

	public void setMoveQuickDeposit(Long moveQuickDeposit) {
		this.moveQuickDeposit = moveQuickDeposit;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getMappingCode() {
		return mappingCode;
	}

	public void setMappingCode(Long mappingCode) {
		this.mappingCode = mappingCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getTableAlias() {
		return TABLE_ALIAS;
	}

	public static String getAliasLogo() {
		return ALIAS_LOGO;
	}

	public static String getAliasName() {
		return ALIAS_NAME;
	}

	public static String getAliasCode() {
		return ALIAS_CODE;
	}

	public static String getAliasUrl() {
		return ALIAS_URL;
	}

	public static String getAliasInUse() {
		return ALIAS_IN_USE;
	}

	public static String getAliasDeposit() {
		return ALIAS_DEPOSIT;
	}

	public static String getAliasWithdraw() {
		return ALIAS_WITHDRAW;
	}

	public static String getAliasBind() {
		return ALIAS_BIND;
	}

	public static String getAliasMownecumId() {
		return ALIAS_MOWNECUM_ID;
	}

	public static String getAliasUplimit() {
		return ALIAS_UPLIMIT;
	}

	public static String getAliasLowlimit() {
		return ALIAS_LOWLIMIT;
	}

	public static String getAliasVipuplimit() {
		return ALIAS_VIPUPLIMIT;
	}

	public static String getAliasViplowlimit() {
		return ALIAS_VIPLOWLIMIT;
	}

	public static String getAliasRtnMin() {
		return ALIAS_RTN_MIN;
	}

	public static String getAliasRtnStruc() {
		return ALIAS_RTN_STRUC;
	}

	public static String getAliasRtnSet() {
		return ALIAS_RTN_SET;
	}

	public static String getAliasVersion() {
		return ALIAS_VERSION;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Logo", getLogo()).append("Name", getName())
				.append("Code", getCode()).append("Url", getUrl()).append("InUse", getInUse())
				.append("Deposit", getDeposit()).append("Withdraw", getWithdraw()).append("Bind", getBind())
				.append("MownecumId", getMownecumId()).append("Uplimit", getUplimit()).append("Lowlimit", getLowlimit())
				.append("Vipuplimit", getVipuplimit()).append("Viplowlimit", getViplowlimit())
				.append("Returnfee", getRtnMin()).append("Returnscope", getRtnStruc()).append("Returnamt", getRtnSet())
				.append("MoveQuickType", getMoveQuickType()).append("MoveQuickDeposit", getMoveQuickDeposit())
				.append("MoveDeposit", getMoveDeposit()).append("version", getVersion())
				.append("mappingCode", getMappingCode()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLogo()).append(getName()).append(getCode())
				.append(getUrl()).append(getInUse()).append(getDeposit()).append(getWithdraw()).append(getBind())
				.append(getMownecumId()).append(getUplimit()).append(getLowlimit()).append(getVipuplimit())
				.append(getViplowlimit()).append(getRtnMin()).append(getRtnStruc()).append(getRtnSet())
				.append(getMoveQuickType()).append(getMoveQuickDeposit()).append(getMoveDeposit()).append(getVersion())
				.append(getMappingCode()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundBankWithSub == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FundBankWithSub other = (FundBankWithSub) obj;
		return new EqualsBuilder().append(getId(), other.getId())

				.append(getLogo(), other.getLogo())

				.append(getName(), other.getName())

				.append(getCode(), other.getCode())

				.append(getUrl(), other.getUrl())

				.append(getInUse(), other.getInUse())

				.append(getDeposit(), other.getDeposit())

				.append(getWithdraw(), other.getWithdraw())

				.append(getBind(), other.getBind())

				.append(getMownecumId(), other.getMownecumId())

				.append(getUplimit(), other.getUplimit())

				.append(getLowlimit(), other.getLowlimit())

				.append(getVipuplimit(), other.getVipuplimit())

				.append(getViplowlimit(), other.getViplowlimit())

				.append(getRtnMin(), other.getRtnMin())

				.append(getRtnStruc(), other.getRtnStruc())

				.append(getRtnSet(), other.getRtnSet())

				.append(getVersion(), other.getVersion())

				.append(getMappingCode(), other.getMappingCode())

				.isEquals();
	}

	public String getHelpIds() {
		return helpIds;
	}

	public void setHelpIds(String helpIds) {
		this.helpIds = helpIds;
	}

	public Long getVipOpen() {
		return vipOpen;
	}

	public void setVipOpen(Long vipOpen) {
		this.vipOpen = vipOpen;
	}

	public Long getNormalOpen() {
		return normalOpen;
	}

	public void setNormalOpen(Long normalOpen) {
		this.normalOpen = normalOpen;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMoveCanRechargeAppeal() {
		return moveCanRechargeAppeal;
	}

	public void setMoveCanRechargeAppeal(Long moveCanRechargeAppeal) {
		this.moveCanRechargeAppeal = moveCanRechargeAppeal;
	}
}
