package com.winterframework.firefrog.game.web.bet.convertor.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.bet.convertor.IBetNameConvertor;
import com.winterframework.firefrog.game.web.bet.entity.LotteryBetMethod;
import com.winterframework.firefrog.game.web.bet.entity.LotteryGameGroup;
import com.winterframework.firefrog.game.web.bet.entity.LotteryGameSet;

public class BetNameConvertor implements IBetNameConvertor {

	protected List<LotteryGameGroup> gameGroups;

	protected List<LotteryGameSet> gameSets;

	protected List<LotteryBetMethod> betMethods;
	
	public BetNameConvertor() {
	}

	public BetNameConvertor(List<LotteryGameGroup> gameGroups, List<LotteryGameSet> gameSets,
			List<LotteryBetMethod> betMethods) {
		super();
		this.gameGroups = gameGroups;
		this.gameSets = gameSets;
		this.betMethods = betMethods;
	}

//	@Override
//	public Integer getGameGroupCode(String gameGroupName) {
//
//		if (StringUtils.isEmpty(gameGroupName))
//			return null;
//
//		for (LotteryGameGroup group : gameGroups) {
//			if (group.getName().equals(gameGroupName)) {
//				return group.getCode();
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Integer getGameSetCode(String gameSetName) {
//
//		if (StringUtils.isEmpty(gameSetName))
//			return null;
//
//		for (LotteryGameSet set : gameSets) {
//			if (set.getName().equals(gameSetName)) {
//				return set.getCode();
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Integer getBetMethodCode(String betMethodName) {
//
//		if (StringUtils.isEmpty(betMethodName))
//			return null;
//
//		for (LotteryBetMethod method : betMethods) {
//			if (method.getName().equals(betMethodName)) {
//				return method.getCode();
//			}
//		}
//		return null;
//	}

//	@Override
//	public String getGameGroupName(Integer gameGroupCode) {
//		return getGameGroup(gameGroupCode).getName();
//	}
//
//	@Override
//	public String getGameSetName(Integer gameSetCode) {
//		return getGameSet(gameSetCode).getName();
//	}
//
//	@Override
//	public String getBetMethodName(Integer betMethodCode) {
//		return getBetMethod(betMethodCode).getName();
//	}
//
//	@Override
//	public String getGameGroupTitle(Integer gameGroupCode) {
//		return getGameGroup(gameGroupCode).getTitle();
//	}
//
//	@Override
//	public String getGameSetTitle(Integer gameSetCode) {
//		return getGameSet(gameSetCode).getTitle();
//	}
//
//	@Override
//	public String getBetMethodTitle(Integer betMethodCode) {
//		return getBetMethod(betMethodCode).getTitle();
//	}

	public LotteryGameGroup getGameGroup(Integer gameGroupCode) {
		for (LotteryGameGroup group : gameGroups) {
			if (group.getCode().intValue() == gameGroupCode) {
				// 为防止原始对象被修改，创建新对象，并复制所有属性到新对象
				LotteryGameGroup resultGroup = new LotteryGameGroup();
				resultGroup.setCode(group.getCode());
				resultGroup.setName(group.getName());
				resultGroup.setTitle(group.getTitle());
				resultGroup.setIsNew(group.getIsNew());
				resultGroup.setIsdiamond(group.getIsdiamond());
				return resultGroup;
			}
		}
		return null;
	}

	public LotteryGameSet getGameSet(Integer gameSetCode) {
		return getGameSet(gameSetCode, null);
	}

	/**
	 * 根据gameSetCode和parent来查找玩法组
	 * 
	 * 1. 如果只有一个配置的玩法组同参数<code>gameSetCode</code>相同，则不再判断parent
	 * 2. 如果有多个配置的玩法组同参数<code>gameSetCode</code>相同
	 * 		2.1 如果参数<code>parent</code>不为空，则查找配置中具有相同parent的玩法组；找不到，就返回配置中parent为空的玩法组；
	 * 		2.2 如果参数<code>parent</code>为空，则查找配置中parent为空的玩法组
	 * 3. 如果以上步骤均未找到匹配的玩法组，返回null
	 * 
	 * @param gameSetCode
	 * @param parent
	 * @return
	 */
	protected LotteryGameSet getGameSet(Integer gameSetCode, String parent) {

		List<LotteryGameSet> sets = new ArrayList<LotteryGameSet>();

		for (LotteryGameSet set : gameSets) {
			if (set.getCode().intValue() == gameSetCode) {
				sets.add(set);
			}
		}

		LotteryGameSet foundSet = null;
		if (sets.size() == 1) {
			foundSet = sets.get(0);
		} else {
			for (LotteryGameSet set : sets) {
				if (StringUtils.isNotEmpty(parent) && parent.equals(set.getParent())) {
					foundSet = set;
					break;
				}
			}
			if (foundSet == null) {
				for (LotteryGameSet set : sets) {
					if (StringUtils.isEmpty(set.getParent())) {
						foundSet = set;
						break;
					}
				}
			}
		}

		if (foundSet != null) {
			// 为防止原始对象被修改，创建新对象，并复制所有属性到新对象
			LotteryGameSet resultSet = new LotteryGameSet();
			resultSet.setCode(foundSet.getCode());
			resultSet.setName(foundSet.getName());
			resultSet.setTitle(foundSet.getTitle());
			return resultSet;
		}

		return null;

	}

	public LotteryBetMethod getBetMethod(Integer betMethodCode) {
		return getBetMethod(betMethodCode, null, null);
	}

	/**
	 * 根据betMethodCode, parent和mode来查找投注方式
	 * 
	 * 1. 如果只有一个配置的投注方案同参数<code>betMethodCode</code>相同，则不再判断parent和mode
	 * 2. 如果有多个配置的投注方式同参数<code>betMethodCode</code>相同
	 * 		2.1 如果参数<code>parent</code>不为空且<code>mode</code>不为空，则查找配置中具有相同parent和mode的投注方式；找不到，就返回配置中parent和mode均为空的投注方式；
	 * 		2.2 如果参数<code>parent</code>为空或<code>mode</code>为空，则查找配置中parent和mode均为空的投注方式
	 * 3. 如果以上步骤均未找到匹配的投注方式，返回null
	 * 
	 * @param betMethodCode
	 * @param parent
	 * @param mode
	 * @return
	 */
	protected LotteryBetMethod getBetMethod(Integer betMethodCode, String parent, String mode) {

		List<LotteryBetMethod> methods = new ArrayList<LotteryBetMethod>();

		for (LotteryBetMethod method : betMethods) {
			if (method.getCode().intValue() == betMethodCode) {
				methods.add(method);
			}
		}

		LotteryBetMethod foundMethod = null;
		if (methods.size() == 1) {
			foundMethod = methods.get(0);
		} else {
			for (LotteryBetMethod method : methods) {
				if ((StringUtils.isNotEmpty(parent) && parent.equals(method.getParent()))
						&& (StringUtils.isNotEmpty(mode) && mode.equals(method.getMode()))) {
					foundMethod = method;
					break;
				}
			}
			if (foundMethod == null) {
				for (LotteryBetMethod method : methods) {
					if (StringUtils.isEmpty(method.getParent()) && StringUtils.isEmpty(method.getMode())) {
						foundMethod = method;
						break;
					}
				}
			}
		}

		if (foundMethod != null) {
			// 为防止原始对象被修改，创建新对象，并复制所有属性到新对象
			LotteryBetMethod resultMethod = new LotteryBetMethod();
			resultMethod.setCode(foundMethod.getCode());
			resultMethod.setName(foundMethod.getName());
			resultMethod.setTitle(foundMethod.getTitle());
			resultMethod.setParent(foundMethod.getParent());
			resultMethod.setMode(foundMethod.getMode());
			return resultMethod;
		}

		return null;
	}

//	@Override
//	public String getBetMethodFullNameByValue(Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode) {
//
//		List<LotteryGameGroup> gameGroup = getGameTypes(new String[] { gameGroupCode + "," + gameSetCode + ","
//				+ betMethodCode });
//
//		return gameGroup.get(0).getName() + "." + gameGroup.get(0).getChilds().get(0).getName() + "."
//				+ gameGroup.get(0).getChilds().get(0).getChilds().get(0).getName();
//	}
//
//	@Override
//	public String getMMCBetMethodFullTitleByValue(Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode) {
//
//		List<LotteryGameGroup> gameGroup = getGameTypes(new String[] { gameGroupCode + "," + gameSetCode + ","
//				+ betMethodCode });
//
//		return gameGroup.get(0).getTitle() + "_"/*+gameGroup.get(0).getChilds().get(0).getTitle()+"_"*/
//				+ gameGroup.get(0).getChilds().get(0).getChilds().get(0).getTitle();
//	}

	@Override
	public List<LotteryGameGroup> getGameTypes(String[] gameTypeCodes) {

		List<LotteryGameGroup> gameGroups = new ArrayList<LotteryGameGroup>();

		for (String gameTypeCode : gameTypeCodes) {

			String[] codes = StringUtils.split(gameTypeCode, ",");

			//解析得玩法群、玩法组、玩法code
			Integer gameGroupCode = Integer.valueOf(codes[0]);
			Integer gameSetCode = Integer.valueOf(codes[1]);
			Integer betMethodCode = Integer.valueOf(codes[2]);

			//取得当前玩法群，如果没有，则从convertor中读取，并加入玩法群List中
			LotteryGameGroup currGameGroup = null;
			for (LotteryGameGroup gameGroup : gameGroups) {
				if (gameGroup.getCode().intValue() == gameGroupCode) {
					currGameGroup = gameGroup;
					break;
				}
			}
			if (currGameGroup == null) {
				currGameGroup = getGameGroup(gameGroupCode);
				currGameGroup.setChilds(new ArrayList<LotteryGameSet>());
				gameGroups.add(currGameGroup);
			}

			//从当前玩法群的玩法组列表中中取得当前玩法组，如果没有，则从convertor中读取，并加入到当前玩法组List中
			LotteryGameSet currGameSet = null;
			for (LotteryGameSet gameSet : currGameGroup.getChilds()) {
				if (gameSet.getCode().intValue() == gameSetCode) {
					currGameSet = gameSet;
					break;
				}
			}
			if (currGameSet == null) {
				currGameSet = getGameSet(gameSetCode, currGameGroup.getName());
				currGameSet.setParent(currGameGroup.getName());
				currGameSet.setChilds(new ArrayList<LotteryBetMethod>());
				currGameGroup.getChilds().add(currGameSet);
			}

			//从当前玩法组的投注方式列表中中取得当前投注方式，如果没有，则从convertor中读取，并加入到当前投注方式List中
			LotteryBetMethod currBetMethod = null;
			for (LotteryBetMethod betMethod : currGameSet.getChilds()) {
				if (betMethod.getCode().intValue() == betMethodCode) {
					currBetMethod = betMethod;
					break;
				}
			}
			//fixH.S
			if (currBetMethod == null && 
					//六合彩group 為 53, 54, 55, 56,  目錄要將他排除掉
					(gameGroupCode.compareTo(53) < 0 && gameGroupCode.compareTo(56) < 0)) {
				//投注页面显示原因，添加显示处理硬编码
				currBetMethod = getBetMethod(betMethodCode, currGameSet.getName(), currGameGroup.getName());
//				if ((currGameSet.getTitle().equals("组选") || currGameSet.getTitle().equals("直选"))
//						&& (currBetMethod.getTitle().equals("和值") || currBetMethod.getTitle().equals("包胆"))) {
//					currBetMethod.setTitle(currGameSet.getTitle() + currBetMethod.getTitle());
//				}
//				if ((currGameGroup.getTitle().equals("后二") || currGameGroup.getTitle().equals("前二"))
//						&& (currBetMethod.getTitle().equals("单式") || currBetMethod.getTitle().equals("复式"))) {
//					currBetMethod.setTitle(currGameSet.getTitle() + currBetMethod.getTitle());
//				}
				currBetMethod.setParent(currGameSet.getName());
				currBetMethod.setMode(currGameGroup.getName());
				currGameSet.getChilds().add(currBetMethod);
			}
		}

		return gameGroups;
	}

	public void setGameGroups(List<LotteryGameGroup> gameGroups) {
		this.gameGroups = gameGroups;
	}

	public void setGameSets(List<LotteryGameSet> gameSets) {
		this.gameSets = gameSets;
	}

	public void setBetMethods(List<LotteryBetMethod> betMethods) {
		this.betMethods = betMethods;
	}

}
