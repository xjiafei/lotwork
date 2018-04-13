package com.winterframework.firefrog.game.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.constance.ServiceConstance;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/** 
* @ClassName: DTOConverter 
* @Description: DTO转换类 
* @author Alan
* @date 2013-9-24 下午8:55:27 
*/
public class DTOConvertor4Web {

	private static Logger log = LoggerFactory.getLogger(DTOConvertor4Web.class);
	
	public static final String shierShengXiao="(十二生肖)";
	
	public static List<GameGroupCode> BetLimitQueryResponse2GameGroupCodeList(BetLimitQueryResponse response,
			Long lotteryId) {

		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();

		int lastGroupCode = -1;
		GameGroupCode groupCode = null;

		int lastSetCode = -1;
		GameSetCode setCode = null;
		List<BetMethodMultipleStruc> betMethodMultipleStrucs = response.getBetLimitList();
		for (int i = 0; i < betMethodMultipleStrucs.size(); i++) {

			BetMethodMultipleStruc betMethodMultipleStruc = response.getBetLimitList().get(i);

			if (lastGroupCode != betMethodMultipleStruc.getGameGroupCode()) {
				lastGroupCode = betMethodMultipleStruc.getGameGroupCode();
				groupCode = new GameGroupCode();
				groupCode.setGameGroupCode(lastGroupCode);
				groupCode.setSetCodes(new ArrayList<GameSetCode>());
				groupCode.setGameGroupCodeName(GameAwardNameUtil.getTitle(lotteryId, betMethodMultipleStrucs.get(i)
						.getGameGroupCode(), betMethodMultipleStrucs.get(i).getGameSetCode(), betMethodMultipleStrucs
						.get(i).getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			}

			if (lastSetCode != betMethodMultipleStruc.getGameSetCode()) {
				lastSetCode = betMethodMultipleStruc.getGameSetCode();
				setCode = new GameSetCode();
				setCode.setGameSetCode(lastSetCode);
				setCode.setMethodCodes(new ArrayList<BetLimitMethodCode>());
				setCode.setGameSetCodeName(GameAwardNameUtil.getTitle(lotteryId, betMethodMultipleStrucs.get(i)
						.getGameGroupCode(), betMethodMultipleStrucs.get(i).getGameSetCode(), betMethodMultipleStrucs
						.get(i).getBetMethodCode(), 1));
				groupCode.getSetCodes().add(setCode);
			}

			BetLimitMethodCode methodCode = new BetLimitMethodCode();
			methodCode.setBetMethodCode(betMethodMultipleStruc.getBetMethodCode());
			methodCode.setMultiple(betMethodMultipleStruc.getMultiple());
			methodCode.setMultiple_bak(betMethodMultipleStruc.getMultiple_bak());
			if(methodCode.getMultiple()==-1){
				Long maxMul=-1L;
				for(BetLimitAssistStruc asst:betMethodMultipleStrucs.get(i).getAssistList()){
					if(maxMul<asst.getMaxMultiple()){
						maxMul=asst.getMaxMultiple();
					}
				}
				methodCode.setMaxBonus(maxMul*betMethodMultipleStruc.getMaxBonus());
			}else{
				methodCode.setMaxBonus(betMethodMultipleStruc.getMaxBonus());
			}
			methodCode.setBetMethodName(GameAwardNameUtil.getTitle(lotteryId, betMethodMultipleStrucs.get(i)
					.getGameGroupCode(), betMethodMultipleStrucs.get(i).getGameSetCode(), betMethodMultipleStrucs
					.get(i).getBetMethodCode(), 2));
			if (betMethodMultipleStrucs.get(i).getAssistList() != null) {
				methodCode.setAssistList(betMethodMultipleStrucs.get(i).getAssistList());
			}
			methodCode.setSpecialMultiple(betMethodMultipleStruc.getSpecialMultiple() == null?null:betMethodMultipleStruc.getSpecialMultiple().split(","));
			methodCode.setSpecialMultiple_bak(betMethodMultipleStruc.getSpecialMultiple_bak() == null?null:betMethodMultipleStruc.getSpecialMultiple_bak().split(","));			
			methodCode.setSpecialMaxBonus(betMethodMultipleStruc.getSpecialMaxBonus() == null?null:betMethodMultipleStruc.getSpecialMaxBonus().split(","));
			setCode.getMethodCodes().add(methodCode);
		}

		return groupCodes;
	}

	public static List<GameGroupCode> BetLimitQueryResponse2GameGroupCodeList(BetLimitQueryResponse response1,
			BetLimitQueryResponse response2, Long lotteryId) {
		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();

		int lastGroupCode = -1;
		GameGroupCode groupCode = null;

		int lastSetCode = -1;
		GameSetCode setCode = null;

		for (int i = 0; i < response2.getBetLimitList().size(); i++) {

			BetMethodMultipleStruc betMethodMultipleStruc = response2.getBetLimitList().get(i);

			if (lastGroupCode != betMethodMultipleStruc.getGameGroupCode()) {
				lastGroupCode = betMethodMultipleStruc.getGameGroupCode();
				groupCode = new GameGroupCode();
				groupCode.setGameGroupCode(lastGroupCode);
				groupCode.setSetCodes(new ArrayList<GameSetCode>());
				groupCode.setGameGroupCodeName(GameAwardNameUtil.getTitle(lotteryId, response2.getBetLimitList().get(i)
						.getGameGroupCode(), response2.getBetLimitList().get(i).getGameSetCode(), response2
						.getBetLimitList().get(i).getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			}

			if (lastSetCode != betMethodMultipleStruc.getGameSetCode()) {
				lastSetCode = betMethodMultipleStruc.getGameSetCode();
				setCode = new GameSetCode();
				setCode.setGameSetCode(lastSetCode);
				setCode.setMethodCodes(new ArrayList<BetLimitMethodCode>());
				setCode.setGameSetCodeName(GameAwardNameUtil.getTitle(lotteryId, response2.getBetLimitList().get(i)
						.getGameGroupCode(), response2.getBetLimitList().get(i).getGameSetCode(), response2
						.getBetLimitList().get(i).getBetMethodCode(), 1));
				groupCode.getSetCodes().add(setCode);
			}

			BetLimitMethodCode methodCode = new BetLimitMethodCode();
			methodCode.setBetMethodCode(betMethodMultipleStruc.getBetMethodCode());
			methodCode.setMultiple(betMethodMultipleStruc.getMultiple());
			methodCode.setMaxBonus(betMethodMultipleStruc.getMaxBonus());
			methodCode.setBetMethodName(GameAwardNameUtil.getTitle(lotteryId, response2.getBetLimitList().get(i)
					.getGameGroupCode(), response2.getBetLimitList().get(i).getGameSetCode(), response2
					.getBetLimitList().get(i).getBetMethodCode(), 2));
			//对比数据
			for (int j = 0; j < response1.getBetLimitList().size(); j++) {

				BetMethodMultipleStruc betMethodMultipleStruc2 = response1.getBetLimitList().get(j);

				if (betMethodMultipleStruc2.getGameGroupCode() == betMethodMultipleStruc.getGameGroupCode()
						&& betMethodMultipleStruc2.getGameSetCode() == betMethodMultipleStruc.getGameSetCode()
						&& betMethodMultipleStruc2.getBetMethodCode() == betMethodMultipleStruc.getBetMethodCode()
						&& betMethodMultipleStruc2.getMultiple() != betMethodMultipleStruc.getMultiple()) {
					methodCode.setMultiple_bak(betMethodMultipleStruc2.getMultiple());
				}
			}

			setCode.getMethodCodes().add(methodCode);
		}

		return groupCodes;
	}

	public static List<GameGroupCode> BetMethodDescriptionQueryResponse2GameGroupCodeList(
			BetMethodDescriptionQueryResponse response, Long lotteryId) {
		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();

		int lastGroupCode = -1;
		GameGroupCode groupCode = null;

		int lastSetCode = -1;
		GameSetCode setCode = null;

		for (int i = 0; i < response.getBetMethodHelpStruc().size(); i++) {

			BetMethodHelpStruc betMethodHelpStruc = response.getBetMethodHelpStruc().get(i);

			if (lastGroupCode != betMethodHelpStruc.getGameGroupCode()) {
				lastGroupCode = betMethodHelpStruc.getGameGroupCode();
				groupCode = new GameGroupCode();
				groupCode.setGameGroupCode(lastGroupCode);
				groupCode.setSetCodes(new ArrayList<GameSetCode>());
				groupCode.setGameGroupCodeName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodHelpStruc()
						.get(i).getGameGroupCode(), response.getBetMethodHelpStruc().get(i).getGameSetCode(), response
						.getBetMethodHelpStruc().get(i).getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			}

			if (lastSetCode != betMethodHelpStruc.getGameSetCode()) {
				lastSetCode = betMethodHelpStruc.getGameSetCode();
				setCode = new GameSetCode();
				setCode.setGameSetCode(lastSetCode);
				setCode.setHelpCodes(new ArrayList<HelpCode>());
				setCode.setGameSetCodeName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodHelpStruc()
						.get(i).getGameGroupCode(), response.getBetMethodHelpStruc().get(i).getGameSetCode(), response
						.getBetMethodHelpStruc().get(i).getBetMethodCode(), 1));
				groupCode.getSetCodes().add(setCode);
			}

			HelpCode helpCode = new HelpCode();
			helpCode.setBetMethodCode(betMethodHelpStruc.getBetMethodCode());
			helpCode.setBetMethodName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodHelpStruc().get(i)
					.getGameGroupCode(), response.getBetMethodHelpStruc().get(i).getGameSetCode(), response
					.getBetMethodHelpStruc().get(i).getBetMethodCode(), 2));
			helpCode.setGamePromptDes(betMethodHelpStruc.getGamePromptDes());
			helpCode.setGamePromptDes_bak(betMethodHelpStruc.getGamePromptDes_bak());
			helpCode.setGameExamplesDes(betMethodHelpStruc.getGameExamplesDes());
			helpCode.setGameExamplesDes_bak(betMethodHelpStruc.getGameExamplesDes_bak());
			setCode.getHelpCodes().add(helpCode);
		}

		return groupCodes;
	}

	public static List<GameGroupCode> BetMethodDescriptionQueryResponse2GameGroupCodeList(
			BetMethodDescriptionQueryResponse response1, BetMethodDescriptionQueryResponse response2, Long lotteryId) {
		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();

		int lastGroupCode = -1;
		GameGroupCode groupCode = null;

		int lastSetCode = -1;
		GameSetCode setCode = null;

		for (int i = 0; i < response2.getBetMethodHelpStruc().size(); i++) {

			BetMethodHelpStruc betMethodHelpStruc = response2.getBetMethodHelpStruc().get(i);

			if (lastGroupCode != betMethodHelpStruc.getGameGroupCode()) {
				lastGroupCode = betMethodHelpStruc.getGameGroupCode();
				groupCode = new GameGroupCode();
				groupCode.setGameGroupCode(lastGroupCode);
				groupCode.setSetCodes(new ArrayList<GameSetCode>());
				groupCode.setGameGroupCodeName(GameAwardNameUtil.getTitle(lotteryId, response2.getBetMethodHelpStruc()
						.get(i).getGameGroupCode(), response2.getBetMethodHelpStruc().get(i).getGameSetCode(),
						response2.getBetMethodHelpStruc().get(i).getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			}

			if (lastSetCode != betMethodHelpStruc.getGameSetCode()) {
				lastSetCode = betMethodHelpStruc.getGameSetCode();
				setCode = new GameSetCode();
				setCode.setGameSetCode(lastSetCode);
				setCode.setHelpCodes(new ArrayList<HelpCode>());
				setCode.setGameSetCodeName(GameAwardNameUtil.getTitle(lotteryId,
						response2.getBetMethodHelpStruc().get(i).getGameGroupCode(), response2.getBetMethodHelpStruc()
								.get(i).getGameSetCode(), response2.getBetMethodHelpStruc().get(i).getBetMethodCode(),
						1));
				groupCode.getSetCodes().add(setCode);
			}

			HelpCode helpCode = new HelpCode();
			helpCode.setBetMethodCode(betMethodHelpStruc.getBetMethodCode());
			helpCode.setBetMethodName(GameAwardNameUtil.getTitle(lotteryId, response2.getBetMethodHelpStruc().get(i)
					.getGameGroupCode(), response2.getBetMethodHelpStruc().get(i).getGameSetCode(), response2
					.getBetMethodHelpStruc().get(i).getBetMethodCode(), 2));
			helpCode.setGamePromptDes(betMethodHelpStruc.getGamePromptDes());
			helpCode.setGameExamplesDes(betMethodHelpStruc.getGameExamplesDes());

			for (int j = 0; j < response1.getBetMethodHelpStruc().size(); j++) {
				BetMethodHelpStruc betMethodHelpStruc2 = response1.getBetMethodHelpStruc().get(i);

				if (betMethodHelpStruc2.getGameGroupCode() == betMethodHelpStruc.getGameGroupCode()
						&& betMethodHelpStruc2.getGameSetCode() == betMethodHelpStruc.getGameSetCode()
						&& betMethodHelpStruc2.getBetMethodCode() == betMethodHelpStruc.getBetMethodCode()
						&& !betMethodHelpStruc2.getGamePromptDes().equals(betMethodHelpStruc.getGamePromptDes())) {

					if (!betMethodHelpStruc2.getGamePromptDes().equals(betMethodHelpStruc.getGamePromptDes())) {
						helpCode.setGamePromptDes_bak(betMethodHelpStruc2.getGamePromptDes());
					}

					if (!betMethodHelpStruc2.getGameExamplesDes().equals(betMethodHelpStruc.getGamePromptDes())) {
						helpCode.setGameExamplesDes_bak(betMethodHelpStruc2.getGameExamplesDes());
					}
				}
			}

			setCode.getHelpCodes().add(helpCode);
		}

		return groupCodes;
	}

	public static List<GameGroupCode> SellingStatusQueryResponse2GameGroupCodeList(SellingStatusQueryResponse response,
			Long lotteryId) {
		List<GameGroupCode> groupCodes = new ArrayList<GameGroupCode>();

		int lastGroupCode = -1;
		GameGroupCode groupCode = null;

		int lastSetCode = -1;
		GameSetCode setCode = null;

		for (int i = 0; i < response.getBetMethodStatusStruc().size(); i++) {

			BetMethodStatusStruc betMethodStatusStruc = response.getBetMethodStatusStruc().get(i);

			if (lastGroupCode != betMethodStatusStruc.getGameGroupCode()) {
				lastGroupCode = betMethodStatusStruc.getGameGroupCode();
				groupCode = new GameGroupCode();
				groupCode.setGameGroupCode(lastGroupCode);
				groupCode.setSetCodes(new ArrayList<GameSetCode>());
				groupCode.setGameGroupCodeName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodStatusStruc()
						.get(i).getGameGroupCode(), response.getBetMethodStatusStruc().get(i).getGameSetCode(),
						response.getBetMethodStatusStruc().get(i).getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			}

			if (lastSetCode != betMethodStatusStruc.getGameSetCode()) {
				lastSetCode = betMethodStatusStruc.getGameSetCode();
				setCode = new GameSetCode();
				setCode.setGameSetCode(lastSetCode);
				setCode.setSellingCodes(new ArrayList<SellingStatusCode>());
				setCode.setGameSetCodeName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodStatusStruc()
						.get(i).getGameGroupCode(), response.getBetMethodStatusStruc().get(i).getGameSetCode(),
						response.getBetMethodStatusStruc().get(i).getBetMethodCode(), 1));
				groupCode.getSetCodes().add(setCode);
			}

			SellingStatusCode sellingStatusCode = new SellingStatusCode();
			sellingStatusCode.setBetMethodCode(betMethodStatusStruc.getBetMethodCode());
			sellingStatusCode.setBetMethodName(GameAwardNameUtil.getTitle(lotteryId, response.getBetMethodStatusStruc()
					.get(i).getGameGroupCode(), response.getBetMethodStatusStruc().get(i).getGameSetCode(), response
					.getBetMethodStatusStruc().get(i).getBetMethodCode(), 2));
			sellingStatusCode.setStatus(betMethodStatusStruc.getStatus());
			sellingStatusCode.setStatus_changed(betMethodStatusStruc.isStatus_changed());

			if (betMethodStatusStruc.getStatus() == 1) {
				groupCode.setGameGroupColorStatus(1);
				setCode.setGameSetColorStatus(1);
			}

			setCode.getSellingCodes().add(sellingStatusCode);
		}

		return groupCodes;
	}

	public static List<OrdersStrucDTO> orderStrucs2OrderStrucsDTO(List<OrdersStruc> ordersStruc) {
		List<OrdersStrucDTO> orderStrucs = new ArrayList<OrdersStrucDTO>();
		if (ordersStruc != null && !ordersStruc.isEmpty()) {
			for (OrdersStruc os : ordersStruc) {
				OrdersStrucDTO od = orderStruc2OrderStrucDTO(os);
				orderStrucs.add(od);
			}
		}
		return orderStrucs;
	}

	public static List<SlipsStrucDTO> slipsStruc2SlipsStrucDTO(List<SlipsStruc> slipsStruc, Long lotteryId) {
		List<SlipsStrucDTO> slipStrucs = new ArrayList<SlipsStrucDTO>();
		if (slipsStruc != null && !slipsStruc.isEmpty()) {
			for (SlipsStruc os : slipsStruc) {
				SlipsStrucDTO od = slipStruc2SlipStrucDTO(os, lotteryId);
				slipStrucs.add(od);
			}
		}
		return slipStrucs;
	}

	/** 
	* @Title: explode 
	* @Description: 将中奖号码String转换为int型的List 
	*/
	public static List<String> explode(String s) {
		List<String> list = new ArrayList<String>();
		if (s != null) {
			if (s.contains(",")) {
				String[] recordArray = s.split(",");
				for (String record : recordArray) {
					list.add(record);
				}

			} else {
				for (int i = 0; i < s.length(); i++) {
					list.add(String.valueOf(s.charAt(i)));
				}
			}
		}
		return list;
	}

	public static OrdersStrucDTO orderStruc2OrderStrucDTO(OrdersStruc os) {
		OrdersStrucDTO od = new OrdersStrucDTO();
		od.setAccount(os.getAccount());
		od.setIssueCode(os.getIssueCode());
		if (os.getLotteryid() == 99112||os.getLotteryid()==99306) {
			od.setWebIssueCode("/");
		} else {
			od.setWebIssueCode(os.getWebIssueCode());
		}
		od.setLotteryid(os.getLotteryid());
		if(os.getLotteryid()==99112L && os.getActivityType()==1L){
			od.setLotteryName(GameAwardNameUtil.lotteryName(os.getLotteryid())+shierShengXiao);
		}else{
			od.setLotteryName(GameAwardNameUtil.lotteryName(os.getLotteryid()));
		}
		
		od.setNumberRecord(os.getNumberRecord());
		od.setOrderCode(os.getOrderCode());
		od.setOrderId(os.getOrderId());
		od.setParentid(os.getParentid());
		od.setParentType(os.getParentType());
		od.setSaleTime(os.getSaleTime());
		od.setFormatSaleTime(DateUtils.format(DataConverterUtil.convertLong2Date(os.getSaleTime()),
				"yyyy-MM-dd HH:mm:ss"));
		od.setStatus(os.getStatus());
		od.setStatusName(getOrderStrucStatusNameForFrontUser(os.getStatus()));
		od.setTotamount(os.getTotamount());
		if(os.getDiamondMultiple() != null){
			od.setDiamondMultiple(Double.valueOf(os.getDiamondMultiple()));
		}
		if(os.getTotwin() != null ){
			od.setTotwin(formatLong(os.getTotwin()+os.getTotDiamondWin()));
		}else{
			od.setTotwin(formatLong(os.getTotwin()));
		}
		od.setCanCancel(os.getCanCancel());//前台投注人员能否撤销投注标识，比较奖期的截止投注时间获得
		if (os.getLotteryid() != 99401l) {
			od.setNumberRecordList(explode(os.getNumberRecord()));
			if(os.getLotteryid() == 99701l){
				List<String> numberRecordColorList = new ArrayList<String>();
				for(String num : od.getNumberRecordList()){
					numberRecordColorList.add(GameAwardNameUtil.getLhcNumBallColor(num));
				}
				od.setNumberRecordColorList(numberRecordColorList);
			}
		} else {
			List<String> result = explode(os.getNumberRecord());
			if (!result.isEmpty()) {
				String last = result.get(result.size() - 1);
				List<String> code = Arrays.asList(last.split("\\+"));
				result.remove(result.size() - 1);
				result.addAll(code);
			}
			od.setNumberRecordList(result);
		}

		return od;
	}

	private static Long formatLong(Long aaa){ 
		if(aaa==null) return null;
		return NumberUtils.toLong(String.valueOf(aaa/100)+"00"); 
	}
	
	public static SlipsStrucDTO slipStruc2SlipStrucDTO(SlipsStruc ss, Long lotteryId) {
		SlipsStrucDTO sd = new SlipsStrucDTO();
		sd.setBetMethodCode(ss.getBetMethodCode());
		sd.setGameGroupCode(ss.getGameGroupCode());

		if ((lotteryId + "").startsWith("993")) {//11选五显示2,3 结构,其他显示1,3 级结构
			sd.setGamePlayName(GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 1)
					+ "_"
					+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 2));
		} else if ((lotteryId + "").startsWith("995") || (lotteryId + "").startsWith("996")) {
			String groupName = GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 0);
			String methodName = GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 2);
			if (groupName.trim().equals(methodName.trim())) {
				sd.setGamePlayName(groupName);
			} else {
				sd.setGamePlayName(groupName + "_" + methodName);
			}
			if (groupName.trim().equals("猜1个号")) {
				sd.setGamePlayName("猜1个号就中奖");
			}
		}else if(Long.valueOf(99701).equals(lotteryId)){
			sd.setGamePlayName(GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 0)
					+ "_"
					+GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 1)
							+ "_"
					+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 2));
		}else {
			sd.setGamePlayName(GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 0)
					+ "_"
					+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 2));
		}
		sd.setGameSetCode(ss.getGameSetCode());
		sd.setMoneyMode(ss.getMoneyMode());
		sd.setMoneyModeName(sd.getMoneyMode() == 1 ? "元" : (sd.getMoneyMode() == 3?"分":"角"));
		sd.setMultiple(ss.getMultiple());
		sd.setSlipid(ss.getSlipid());
		sd.setStatus(ss.getStatus());
		if (ss.getStatus() != null) {//packageitem转换时 状态值可能为null
			sd.setStatusName(getSlipStrucStatusName(ss.getStatus()));
		}
		sd.setTotamount(ss.getTotamount());
		sd.setDiamondAmount(ss.getDiamondAmount());
		sd.setTotbets(ss.getTotbets());
		sd.setBetDetail(ss.getBetDetail());
		sd.setAward(ss.getAward());
		sd.setGamePoints(ss.getGamePoints());
		sd.setAwardMode(ss.getAwardMode());
		sd.setGroupAward(ss.getGroupAward());
		sd.setGroupAwardDown(ss.getGroupAwardDown());
		sd.setRetPoint(ss.getRetPoint());
		sd.setRetAward(ss.getRetAward());
		sd.setDiamondWin(ss.getDiamondWin());
        sd.setSingleWin(ss.getSingleWin());
        sd.setLhcMultBonus(ss.getLhcMultBonus());
		return sd;
	}

	public static GameOrderDetailQueryResponseDTO gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(
			GameOrderDetailQueryResponse gr) {
		GameOrderDetailQueryResponseDTO gd = new GameOrderDetailQueryResponseDTO();
		gd.setOrdersStruc(orderStruc2OrderStrucDTO(gr.getOrdersStruc()));
		gd.setSlipsStruc(slipsStruc2SlipsStrucDTO(gr.getSlipsStruc(), gr.getOrdersStruc().getLotteryid()));
		gd.setPlanId(gr.getPlanId());
		return gd;
	}

	/**
	 * 前台用户不能看到 5:存在异常 6:数据归档 转变为处理中
	 * @param status 订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销 5:处理中 6:数据归档 7:存在异常 8:存在异常 other:存在异常
	 * @return
	 */
	private static String getOrderStrucStatusNameForFrontUser(Integer status) {
		if (null == status) {
			return "";
		}
		switch (status) {
		case 1:
			return "等待开奖";
		case 2:
			return "中奖";
		case 3:
			return "未中奖";
		case 4:
			return "撤销";
		case 5:
			return "处理中";
		case 6:
			return "数据归档";
		case 7:
			return "存在异常";
		case 8:
			return "存在异常";
		default:
			return "存在异常";
		}
	}

	private static String getSlipStrucStatusName(Integer status) {
		// 订单状态 1:等待开奖 2:中奖 3:未中奖 4:撤销 5:存在异常 6:数据归档
		switch (status) {
		case 1:
			return "等待开奖";
		case 2:
			return "中奖";
		case 3:
			return "未中奖";
		case 4:
			return "撤销";
		default:
			return "存在异常";
		}
	}

	public static List<PlansStrucForUI> plansStrucs2PlansStrucForUIs(List<PlansStruc> plansStrucs) {
		List<PlansStrucForUI> plansStrucForUIs = new ArrayList<PlansStrucForUI>();
		if (plansStrucs != null && !plansStrucs.isEmpty()) {
			for (PlansStruc os : plansStrucs) {
				PlansStrucForUI od = plansStruc2PlansStrucForUI(os);
				plansStrucForUIs.add(od);
			}
		}
		return plansStrucForUIs;
	}

	public static PlansStrucForUI plansStruc2PlansStrucForUI(PlansStruc ps) {
		PlansStrucForUI psUI = new PlansStrucForUI();
		psUI.setPlanCode(ps.getPlanCode());
		psUI.setLotteryid(ps.getLotteryid());
		psUI.setLotteryName(ps.getLotteryName());
		psUI.setStartIssueCode(ps.getStartIssueCode());
		psUI.setStartWebIssueCode(ps.getStartWebIssueCode());
		psUI.setFinishIssue(ps.getFinishIssue());
		psUI.setTotalIssue(ps.getTotalIssue());
		psUI.setUsedAmount(ps.getUsedAmount() != null ? ps.getUsedAmount() : 0);
		psUI.setTotamount(ps.getTotamount() != null ? ps.getTotamount() : 0);
		psUI.setTotalWin(ps.getTotalWin() != null ? ps.getTotalWin() : 0);
		psUI.setStopMode(ps.getStopMode());
		psUI.setStopParms(ps.getStopParms());
		psUI.setStatus(ps.getStatus());
		psUI.setSaleTime(DateUtils.format(DataConverterUtil.convertLong2Date(ps.getSaleTime()), "yyyy-MM-dd HH:mm:ss"));
		psUI.setPlanid(ps.getPlanid());
		psUI.setCanStop(ps.getCanStop());

		return psUI;
	}

	public static PlansStrucForUI2 plansStruc2PlansStrucForUI2(PlansStruc ps) {
		PlansStrucForUI2 ui = new PlansStrucForUI2();
		if (null != ps.getPlanid()) {
			ui.setPlanid(ps.getPlanid());
		}
		if (null != ps.getPlanCode()) {
			ui.setPlanCode(ps.getPlanCode());
		} else {
			ui.setPlanCode("");
		}
		if (null != ps.getLotteryName()) {
			ui.setLotteryName(ps.getLotteryName());
		} else {
			ui.setLotteryName("");
		}
		if (null != ps.getStartIssueCode()) {
			ui.setStartIssueCode(ps.getStartIssueCode());
		} else {
			ui.setStartIssueCode(new Long(0));
		}
		if (null != ps.getStartWebIssueCode()) {
			ui.setStartWebIssueCode(ps.getStartWebIssueCode());
		} else {
			ui.setStartWebIssueCode("");
		}
		if (null != ps.getAccount()) {
			ui.setAccount(ps.getAccount());
		} else {
			ui.setAccount("");
		}
		if (null != ps.getSaleTime()) {
			ui.setSaleTime(DateUtils.format(DataConverterUtil.convertLong2Date(ps.getSaleTime()), "yyyy-MM-dd HH:mm:ss"));
		} else {
			ui.setSaleTime("");
		}
		if (null != ps.getTotalIssue()) {
			ui.setTotalIssue(ps.getTotalIssue());
		} else {
			ui.setTotalIssue(new Integer(0));
		}
		if (null != ps.getFinishIssue()) {
			ui.setFinishIssue(ps.getFinishIssue());
		} else {
			ui.setFinishIssue(new Integer(0));
		}
		if (null != ps.getCancelIssue()) {
			ui.setCancelIssue(ps.getCancelIssue());
		} else {
			ui.setCancelIssue(new Integer(0));
		}
		if (null != ps.getTotamount()) {
			ui.setTotamount(ps.getTotamount());
		} else {
			ui.setTotamount(new Long(0));
		}
		if (null != ps.getUsedAmount()) {
			ui.setUsedAmount(ps.getUsedAmount());
		} else {
			ui.setUsedAmount(new Long(0));
		}
		if (null != ps.getCanceledAmount()) {
			ui.setCanceledAmount(ps.getCanceledAmount());
		} else {
			ui.setCanceledAmount(new Long(0));
		}
		if (null != ps.getTotalWin()) {
			ui.setTotalWin(ps.getTotalWin());
		} else {
			ui.setTotalWin(new Long(0));
		}
		if (null != ps.getStopMode()) {
			ui.setStopMode(convertStopMode(ps.getStopMode(), ps.getStopParms()));
		} else {
			ui.setStopMode("");
		}
		if (null != ps.getStopParms()) {
			ui.setStopParams(ps.getStopParms());
		} else {
			ui.setStopParams(new Long(0));
		}
		if (null != ps.getStatus()) {
			ui.setStatus(convertPlanStatus(ps.getStatus()));
		} else {
			ui.setStatus("");
		}
		if (null != ps.getChannelid()) {
			ui.setChannelid(ChannelType.getName(new Long(ps.getChannelid())));
			ui.setChannelVersion(ps.getChannelVersion());
		} else {
			ui.setChannelid("");
			ui.setChannelVersion("");
		}
		ui.setLotteryid(ps.getLotteryid());
		return ui;
	}

	private static String convertStopMode(Integer stopMode, Long stopParams) {
		if (null == stopMode) {
			return "";
		}

		switch (stopMode) {
		case 0:
			return "不停止";
		case 1:
			return "累计盈利" + stopParams / 10000 + "后停";
		case 2:
			return "中奖即停";
		default:
			return "";
		}
	}

	private static String convertPlanStatus(Integer status) {
		if (null == status) {
			return "";
		}

		switch (status) {
		case 0:
			return "未开始";
		case 1:
			return "进行中";
		case 2:
			return "已结束";
		case 3:
			return "已终止";
		case 4:
			return "处理中";
		case 5:
			return "处理中";
		case 6:
			return "执行中";
		default:
			return "";
		}
	}

	public static SpiteStrucForUI SpiteOrderStruc2SpiteStrucForUI(SpiteOrderStruc sos) {
		SpiteStrucForUI ui = new SpiteStrucForUI();
		ui.setOrderId(sos.getOrderId());
		ui.setOrderCode(sos.getOrderCode());
		ui.setLotteryName(sos.getLotteryName());
		ui.setWebIssueCode(sos.getWebIssueCode());
		ui.setUserId(sos.getUserId());
		ui.setAccount(sos.getAccount());
		ui.setSaleTime(DateUtils.format(DataConverterUtil.convertLong2Date(sos.getSaleTime()), "yyyy-MM-dd HH:mm:ss"));
		ui.setTotamount(sos.getTotamount());
		ui.setStatus(sos.getStatus());
		ui.setUserStatus(sos.getUserStatus());
		return ui;
	}

	public static OperationReportStrucForUI operationReportStruc2OperationReportStrucForUI(OperationReportStruc ors) {
		OperationReportStrucForUI orsUI = new OperationReportStrucForUI();
		orsUI.setIssueCode(ors.getIssueCode());
		orsUI.setWebIssueCode(ors.getWebIssueCode());
		orsUI.setLotteryid(ors.getLotteryid());
		orsUI.setLotteryName(ors.getLotteryName());
		orsUI.setReportDate(DateUtils.format(DataConverterUtil.convertLong2Date(ors.getReportDate()), "yyyy-MM-dd"));
		orsUI.setTotalCancel(ors.getTotalCancel());
		orsUI.setTotalPoints(ors.getTotalPoints());
		orsUI.setTotalProfit(ors.getTotalProfit());
		orsUI.setTotalSales(ors.getTotalSales());
		orsUI.setTotalWins(ors.getTotalWins());
		return orsUI;
	}

	public static OrdersStrucForUI ordersStruc2OrdersStrucForUI(OrdersStruc os) {
		OrdersStrucForUI ui = new OrdersStrucForUI();
		ui.setOrderId(os.getOrderId());
		ui.setOrderCode(os.getOrderCode());
		ui.setLotteryid(os.getLotteryid());
		ui.setLotteryName(os.getLotteryName());
		ui.setIssueCode(os.getIssueCode());
		ui.setIssueStatus(os.getIssueStatus());
		if (os.getLotteryid() == 99112||os.getLotteryid()==99306) {
			ui.setWebIssueCode("/");
		} else {
			ui.setWebIssueCode(os.getWebIssueCode());
		}
		ui.setAccount(os.getAccount());
		ui.setSaleTime(DateUtils.format(DataConverterUtil.convertLong2Date(os.getSaleTime()), "yyyy-MM-dd HH:mm:ss"));
		ui.setTotalAmount(os.getTotamount() == null ? 0 : os.getTotamount());
		ui.setPlanId(os.getPlanid());
		ui.setPlanCode(os.getPlanCode());
		ui.setCancelModels(os.getCancelModels());
		ui.setEndSaleTime(DateUtils.format(os.getEndSaleTime(), "yyyy-MM-dd HH:mm:ss"));

		log.debug("ordersStruc2OrdersStrucForUI......." + os.toString());

		long totwin = os.getTotwin() == null ? 0l : os.getTotwin();

		ui.setTotwin(totwin);
		if (totwin == 0L) {
			ui.setWinsRatio(0d);
		} else {
			BigDecimal a = new BigDecimal(os.getTotwin()+os.getTotDiamondWin());
			BigDecimal b = new BigDecimal(os.getTotamount());
			BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_HALF_EVEN);
			ui.setWinsRatio(c.doubleValue());
		}
		
		ui.setStatusSign(os.getStatus() == 2 ? 1 : 0);
		ui.setStatus(convertStatus(os.getStatus()));
		if (os.getTotwin() != null && os.getTotwin().longValue() > 0 && os.getStatus() != null
				&& os.getStatus().intValue() == 3) {
			ui.setStatus("存在异常");
		}
		ui.setNumberRecord(os.getNumberRecord());
		
		
		ui.setNumberRecordList(explode(os.getNumberRecord()));
		if(os.getLotteryid() == 99701l){
			List<String> numberRecordColorList = new ArrayList<String>();
			for(String num : ui.getNumberRecordList()){
				numberRecordColorList.add(GameAwardNameUtil.getLhcNumBallColor(num));
			}
			ui.setNumberRecordColorList(numberRecordColorList);
		}
		
		
		ui.setParentType(os.getParentType() == null ? "否" : os.getParentType() == 2 ? "是" : "否");
		ui.setChannelVersion(os.getChannelVersion());
		ui.setChannelId(ChannelType.getName(os.getChannelId()));
		ui.setParentid(os.getParentid());
		ui.setMultiple(os.getMultiple());
		ui.setCanCancel(os.getCanCancel());
		ui.setAdminCanCancel(os.getAdminCanCancel());
		ui.setTotDiamondWin(os.getTotDiamondWin());
		if(os.getDiamondMultiple() != null){
			ui.setDiamondMultiple(Double.valueOf(os.getDiamondMultiple())/10);
		}
		return ui;
	}

	public static GameReportStrucForUI gameReportStruc2GameReportStrucForUI(GameReportStruc struc) {

		GameReportStrucForUI ui = new GameReportStrucForUI();
		ui.setAmonut(struc.getAmonut() / 10000);
		ui.setGameType(struc.getGameType().equals("D") ? "投注单" : "追号单");
		ui.setLotteryName(struc.getLotteryName());
		ui.setOrderCode(struc.getOrderCode());
		ui.setPlanCode(struc.getPlanCode());
		ui.setReson(struc.getReson());
		ui.setStatus(struc.getStatus() == 0 ? "处理中" : "已完成");
		ui.setTid(struc.getTid());
		ui.setTradeDate(DateUtils.format(struc.getTradeDate(), "yyyy-MM-dd HH:mm:ss"));
		ui.setUserName(struc.getUserName());

		return ui;

	}

	private static String convertStatus(Integer status) {
		if (null == status) {
			return "";
		}

		switch (status) {
		case 1:
			return "等待开奖";
		case 2:
			return "中奖";
		case 3:
			return "未中奖";
		case 4:
			return "撤销";
		case 5:
			return "处理中";
		case 6:
			return "数据归档";
		case 7:
			return "存在异常";
		default:
			return "";
		}
	}

	public static SlipsStrucForUI slipsStruc2SlipsStrucForUI(SlipsStruc ss, Long lotteryId) {
		SlipsStrucForUI sd = new SlipsStrucForUI();
		sd.setSlipId(ss.getSlipid());
		if ((lotteryId + "").startsWith("995") || (lotteryId + "").startsWith("996")) {
			String groupName = GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 0);
			String methodName = GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 2);
			if (groupName.trim().equals(methodName.trim())) {
				sd.setGamePlayName(groupName);
			} else {
				sd.setGamePlayName(groupName + "_" + methodName);
			}
			if (groupName.trim().equals("猜1个号")) {
				sd.setGamePlayName("猜1个号就中奖");
			}

		} else {
			sd.setGamePlayName(GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
					ss.getBetMethodCode(), 0)
					+ "_"
					+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 1)
					+ "_"
					+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
							ss.getBetMethodCode(), 2));
		}
		sd.setTotbets(ss.getTotbets());
		sd.setMultiple(ss.getMultiple());
		sd.setTotamount(ss.getTotamount());
		sd.setMoneyMode(ss.getMoneyMode() == 1 ? "元" :(ss.getMoneyMode() == 3?"分":"角"));
		sd.setStatus(String.valueOf(ss.getStatus()));
		sd.setAward(ss.getAward());
		sd.setWinsRadio(ss.getWinsRadio());
		sd.setBetDetail(ss.getBetDetail());
		sd.setAwardMode(ss.getAwardMode());
		sd.setRetPoint(ss.getRetPoint());
		sd.setRetAward(ss.getRetAward());
		sd.setGroupAward(ss.getGroupAward());
		sd.setGroupAwardDown(ss.getGroupAwardDown());
		sd.setDiamondAmount(ss.getDiamondAmount());
		sd.setDiamondWin(ss.getDiamondWin());
		sd.setSingleWin(ss.getSingleWin());
		sd.setLhcMultBonus(ss.getLhcMultBonus());
		return sd;
	}

	public static BetChartStrucKl8 transforGameJbylTrend2BetChartStrucKl8(GameTrendJbyl gameTrendJbyl) {
		BetChartStrucKl8 betChartStrucKl8 = new BetChartStrucKl8();
		String[] valueArray = gameTrendJbyl.getValue().split(",");

		betChartStrucKl8.setDan(valueArray[3].equals(String.valueOf(ServiceConstance.DAN)));
		betChartStrucKl8.setShuang(valueArray[3].equals(String.valueOf(ServiceConstance.SHUANG)));
		betChartStrucKl8.setHe(valueArray[1].equals(String.valueOf(ServiceConstance.HE)));
		betChartStrucKl8.setJi(valueArray[1].equals(String.valueOf(ServiceConstance.JI)));
		betChartStrucKl8.setOu(valueArray[1].equals(String.valueOf(ServiceConstance.OU)));
		betChartStrucKl8.setShang(valueArray[0].equals(String.valueOf(ServiceConstance.SHANG)));
		betChartStrucKl8.setZhong(valueArray[0].equals(String.valueOf(ServiceConstance.ZHONG)));
		betChartStrucKl8.setXia(valueArray[0].equals(String.valueOf(ServiceConstance.XIA)));

		betChartStrucKl8.setDa(valueArray[2].equals(String.valueOf(ServiceConstance.DA)));
		betChartStrucKl8.setXiao(valueArray[2].equals(String.valueOf(ServiceConstance.XIAO)));
		betChartStrucKl8.setHeZhi(Long.valueOf(valueArray[4]));
		betChartStrucKl8.setWebIssueCode(gameTrendJbyl.getWebIssueCode());
		return betChartStrucKl8;
	}

	public static FundTransactionStrucForUI fundTransactionStruc2FundTransactionStrucForUI(FundTransactionStruc s) {
		FundTransactionStrucForUI ui = new FundTransactionStrucForUI();

		ui.setTransactionId(s.getTransactionId() != null ? s.getTransactionId() : "");
		ui.setTransactionInfp(s.getTransactionInfp() != null ? s.getTransactionInfp() : "");
		ui.setTransactionTime(s.getTransactionTime() != null ? DateUtils.format(s.getTransactionTime(),
				"yyyy-MM-dd HH:mm:ss") : "");
		ui.setAccountBalance(s.getAccountBalance() != null && s.getAccountBalance() != 0 ? Double.toString(s
				.getAccountBalance() / 10000.0000) : "");
		ui.setAvailBalance(s.getAvailBalance() != null && s.getAvailBalance() != 0 ? Double.toString(s
				.getAvailBalance() / 10000.0000) : "0");
		ui.setFreezeAccount(s.getFreezeAccount() != null && s.getFreezeAccount() != 0 ? Double.toString(s
				.getFreezeAccount() / 10000.0000) : "");
		ui.setTotalAccountAdd(s.getTotalAccountAdd() != null && s.getTotalAccountAdd() != 0 ? Double.toString(s
				.getTotalAccountAdd() / 10000.0000) : "");
		ui.setTotalAccountReduce(s.getTotalAccountReduce() != null && s.getTotalAccountReduce() != 0 ? Double
				.toString(s.getTotalAccountReduce() / 10000.0000) : "");
		ui.setAvailAccountAdd(s.getAvailAccountAdd() != null && s.getAvailAccountAdd() != 0 ? Double.toString(s
				.getAvailAccountAdd() / 10000.0000) : "");
		ui.setAvailAccountReduce(s.getAvailAccountReduce() != null && s.getAvailAccountReduce() != 0 ? Double
				.toString(s.getAvailAccountReduce() / 10000.0000) : "");
		ui.setFreezeAccountChange(s.getFreezeAccountChange() != null && s.getFreezeAccountChange() != 0 ? Double
				.toString(s.getFreezeAccountChange() / 10000.0000) : "");
		ui.setNote(s.getNote());
		ui.setAccount(s.getAccount());
		ui.setPlanCode(s.getPlanCode());
		ui.setOrderCode(s.getOrderCode());

		return ui;
	}

	private static String shangXiaCaculate(List<Integer> numberRecordList) {
		int upCount = 0, downCount = 0;
		for (Integer record : numberRecordList) {
			if (record >= 1 && record <= 40) {
				upCount++;
			} else {
				downCount++;
			}
		}
		return upCount > downCount ? "上" : upCount == downCount ? "中" : "下";
	}

	private static String jiOuCaculate(List<Integer> numberRecordList) {
		int jiCount = 0, ouCount = 0;
		for (Integer record : numberRecordList) {
			if (record % 2 == 0) {
				ouCount++;
			} else {
				jiCount++;
			}
		}
		return jiCount > ouCount ? "奇" : jiCount == ouCount ? "和" : "偶";
	}

	private static Long heZhiCaculate(List<Integer> numberRecordList) {
		Long sum = 0l;
		for (Integer record : numberRecordList) {
			sum += record;
		}
		return sum;
	}

	private static String daXiaoCaculate(Long sum) {
		return sum > 810l ? "大" : "小";
	}

	private static String danSuangCaculate(Long sum) {
		return sum % 2 == 0 ? "双" : "单";
	}

	public static String getKl8Tip(List<Integer> numberRecordList) {
		StringBuffer sb = new StringBuffer();
		sb.append("和值=").append(heZhiCaculate(numberRecordList)).append(" (")
				.append(daXiaoCaculate(heZhiCaculate(numberRecordList))).append(",")
				.append(danSuangCaculate(heZhiCaculate(numberRecordList))).append(")").append(" 盘面= (")
				.append(shangXiaCaculate(numberRecordList)).append(",").append(jiOuCaculate(numberRecordList))
				.append(")");
		return sb.toString();
	}

	public static String getBetKl8Tip(List<Integer> numberRecordList) {
		StringBuffer sb = new StringBuffer();
		sb.append("和值=").append(heZhiCaculate(numberRecordList)).append(" (")
				.append(daXiaoCaculate(heZhiCaculate(numberRecordList))).append(",")
				.append(danSuangCaculate(heZhiCaculate(numberRecordList))).append(")").append("<br>").append(" 盘面= (")
				.append(shangXiaCaculate(numberRecordList)).append(",").append(jiOuCaculate(numberRecordList))
				.append(")");
		return sb.toString();
	}

	public static OperationDetailReportStruc winsReport2winsReportForUI(OperationDetailReportStruc reportStruc) {
		OperationDetailReportStruc strucUI = new OperationDetailReportStruc();
		strucUI.setLotteryId(reportStruc.getLotteryId());
		strucUI.setGameGroupCode(reportStruc.getGameGroupCode());
		strucUI.setGameSetCode(reportStruc.getGameSetCode());
		strucUI.setBetMethodCode(reportStruc.getBetMethodCode());
		strucUI.setGameGroupName(GameAwardNameUtil.getTitle(reportStruc.getLotteryId(), reportStruc.getGameGroupCode()
				.intValue(), reportStruc.getGameSetCode().intValue(), reportStruc.getBetMethodCode().intValue(), 0));
		strucUI.setGameSetName(GameAwardNameUtil.getTitle(reportStruc.getLotteryId(), reportStruc.getGameGroupCode()
				.intValue(), reportStruc.getGameSetCode().intValue(), reportStruc.getBetMethodCode().intValue(), 1));
		strucUI.setBetMethodName(GameAwardNameUtil.getTitle(reportStruc.getLotteryId(), reportStruc.getGameGroupCode()
				.intValue(), reportStruc.getGameSetCode().intValue(), reportStruc.getBetMethodCode().intValue(), 2));
		strucUI.setTotalSales(reportStruc.getTotalSales());
		strucUI.setTotalPoints(reportStruc.getTotalPoints());
		strucUI.setTotalCancel(reportStruc.getTotalCancel());
		strucUI.setTotalWins(reportStruc.getTotalWins());
		strucUI.setTotalProfit(reportStruc.getTotalProfit());
		return strucUI;
	}
	
	public static void main(String args []){
		Date a =new Date ();
		System.out.println(a.getTime());
	}
}