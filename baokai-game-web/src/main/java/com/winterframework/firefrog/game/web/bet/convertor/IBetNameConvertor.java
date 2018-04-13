package com.winterframework.firefrog.game.web.bet.convertor;

import java.util.List;

import com.winterframework.firefrog.game.web.bet.entity.LotteryGameGroup;

public interface IBetNameConvertor {

//	public Integer getGameGroupCode(String gameGroupName);
//
//	public Integer getGameSetCode(String gameSetName);
//
//	public Integer getBetMethodCode(String betMethodName);

//	public String getGameGroupName(Integer gameGroupCode);
//
//	public String getGameSetName(Integer gameSetCode);
//
//	public String getBetMethodName(Integer betMethodCode);
//
//	public String getGameGroupTitle(Integer gameGroupCode);
//
//	public String getGameSetTitle(Integer gameSetCode);
//
//	public String getBetMethodTitle(Integer betMethodCode);

//	public LotteryGameGroup getGameGroup(Integer gameGroupCode);
//
//	public LotteryGameSet getGameSet(Integer gameSetCode);
//
//	public LotteryBetMethod getBetMethod(Integer betMethodCode);

//	public String getBetMethodFullNameByValue(Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode);
//
//	public String getMMCBetMethodFullTitleByValue(Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode);

	public List<LotteryGameGroup> getGameTypes(String[] gameTypeCodes);


}
