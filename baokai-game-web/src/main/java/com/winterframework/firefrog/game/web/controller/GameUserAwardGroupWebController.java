package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.AssistBmBonusStruc;
import com.winterframework.firefrog.game.web.dto.AssistBonusDTO;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.AwardDTO;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupLoginResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupModifyRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardOperater;
import com.winterframework.firefrog.game.web.dto.GameUserAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.MethodCodeDTO;
import com.winterframework.firefrog.game.web.dto.OpenAccountDetailedConfigRequest;
import com.winterframework.firefrog.game.web.dto.SetCodeDTO;
import com.winterframework.firefrog.game.web.dto.UserAwardListStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: GameUserAwardGroupWebController 
* @Description: 用户奖金组WebController 
* @author Denny 
* @date 2013-9-24 下午7:27:45 
*  
*/
@RequestMapping(value = "/gameUserCenter")
@Controller("gameUserAwardGroupWebController")
public class GameUserAwardGroupWebController {

	private Logger logger = LoggerFactory.getLogger(GameUserAwardGroupWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameUserAwardGroup")
	private String queryGameUserAwardGroupUrl;

	@PropertyConfig(value = "url.game.queryGameUserAwardGroupForModify")
	private String queryGameUserAwardGroupForModifyUrl;

	@PropertyConfig(value = "url.game.modifyGameUserAwardGroup")
	private String modifyGameUserAwardGroupUrl;

	@PropertyConfig(value = "url.game.queryGameUserAwardGroupByLotteryId")
	private String queryGameUserAwardGroupByLotteryIdUrl;

	@PropertyConfig(value = "url.game.saveProxyBetGameAwardGroup")
	private String saveProxyBetGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.openAccountDetailedConfigAwardGroup")
	private String openAccountDetailedConfigAwardGroupUrl;

	@PropertyConfig(value = "url.game.openAccountQuickConfigAwardGroup")
	private String openAccountQuickConfigAwardGroupUrl;

	@PropertyConfig(value = "url.game.queryAwardGroupByLogin")
	private String queryAwardGroupByLoginUrl;

	@PropertyConfig(value = "url.game.queryUserGameAward")
	private String queryUserGameAwardUrl;

	private List<AwardDTO> convertStruc2DTO(Long lotteryId, List<AwardBonusStruc> list) throws Exception {

		List<AwardDTO> groupCodes = new ArrayList<AwardDTO>();

		int lastGroupCode = -1;
		AwardDTO groupCode = null;

		int lastSetCode = -1;
		SetCodeDTO setCode = null;
		Collections.sort(list, new Comparator<AwardBonusStruc>() {

			@Override
			public int compare(AwardBonusStruc o1, AwardBonusStruc o2) {
				Double o1GroupCode = o1.getGameGroupCode().doubleValue();
				Double o2GroupCode = o2.getGameGroupCode().doubleValue();
				if (o1.getGameGroupCode() == 33) {
					o1GroupCode = 12.5d;
				}
				if (o2.getGameGroupCode() == 33) {
					o2GroupCode = 12.5d;
				}
				int result = o1GroupCode.compareTo(o2GroupCode);
				if (result == 0) {
					result = o1.getGameSetCode().compareTo(o2.getGameSetCode());
				}
				if (result == 0) {
					result = o1.getBetMethodCode().compareTo(o2.getBetMethodCode());
				}
				return result;
			}

		});

		boolean otherGroup = false;
		for (AwardBonusStruc struc : list) {

			if (lastGroupCode != struc.getGameGroupCode().intValue()) {
				otherGroup = true;
				lastGroupCode = struc.getGameGroupCode();
				groupCode = new AwardDTO();
				groupCode.setGroupCode(lastGroupCode);
				groupCode.setSetCodeList(new ArrayList<SetCodeDTO>());
				groupCode.setGroupName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
						struc.getGameSetCode(), struc.getBetMethodCode(), 0));
				groupCodes.add(groupCode);
			} else {
				otherGroup = false;
			}

			if (lastSetCode != struc.getGameSetCode() || (lastSetCode == struc.getGameSetCode() && otherGroup)) {
				lastSetCode = struc.getGameSetCode();
				setCode = new SetCodeDTO();
				setCode.setSetCode(lastSetCode);
				setCode.setMethodCodeList(new ArrayList<MethodCodeDTO>());
				setCode.setSetCodeName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
						struc.getGameSetCode(), struc.getBetMethodCode(), 1));
				groupCode.getSetCodeList().add(setCode);
			}

			MethodCodeDTO methodCode = new MethodCodeDTO();
			methodCode.setCompanyProfit(struc.getCompanyProfit());
			methodCode.setMethodCode(struc.getBetMethodCode());
			methodCode.setTheoryBonus(struc.getTheoryBonus() == null ? null : struc.getTheoryBonus() / 100);
			methodCode.setTopAgentPoint(struc.getTopAgentPoint());
			methodCode.setTotalProfit(struc.getTotalProfit());
			methodCode.setRetVal(struc.getRetVal());
			methodCode.setActualBonus(struc.getActualBonus() == null ? null : struc.getActualBonus() / 100);
			methodCode.setActualBonusDown(struc.getActualBonusDown() == null ? null : struc.getActualBonusDown() / 100);
			methodCode.setActualBonusBak(struc.getActualBonusBak() == null ? null : struc.getActualBonusBak());
			methodCode.setMethodCodeName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
					struc.getGameSetCode(), struc.getBetMethodCode(), 2));

			methodCode.setTotalRetbate(this.getTolalRetBat(struc.getActualBonus(), struc.getTheoryBonus()));
			/*if (methodCode.getMethodCodeName().equals("混合组选") || methodCode.getMethodCodeName().equals("和值")
					|| methodCode.getMethodCodeName().equals("包胆")) {
				for (AwardBonusStruc struc1 : list) {
					MethodCodeDTO methodCode1 = new MethodCodeDTO();
					methodCode1.setCompanyProfit(struc1.getCompanyProfit());
					methodCode1.setMethodCode(struc1.getBetMethodCode());
					methodCode1.setTheoryBonus(struc1.getTheoryBonus());
					methodCode1.setTopAgentPoint(struc1.getTopAgentPoint());
					methodCode1.setTotalProfit(struc1.getTotalProfit());
					methodCode1.setActualBonus(struc1.getActualBonus() == null ? null : struc1.getActualBonus() / 100);
					methodCode1.setActualBonusBak(struc1.getActualBonusBak() == null ? null : struc1
							.getActualBonusBak());
					if (groupCode.getGroupCode().intValue() == struc1.getGameGroupCode().intValue()
							&& setCode.getSetCodeName().equals("组选") && methodCode1.getMethodCodeName().equals("组三")) {
						methodCode.getMethodCodeList().add(methodCode1);
					}
					if (groupCode.getGroupCode().intValue() == struc1.getGameGroupCode().intValue()
							&& setCode.getSetCodeName().equals("组选") && methodCode1.getMethodCodeName().equals("组六")) {
						methodCode.getMethodCodeList().add(methodCode1);
					}
				}
			}*/

			List<Long> lotteryIds = Arrays.asList(99101L, 99102L, 99103L, 99104L, 99105L, 99106L);

			if (lotteryIds.contains(lotteryId)
					&& GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(), struc.getGameSetCode(),
							struc.getBetMethodCode(), 1).equals("定位胆")
					&& GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(), struc.getGameSetCode(),
							struc.getBetMethodCode(), 2).equals("复式")) {
				for (int j = 57; j < 62; j++) {
					MethodCodeDTO methodCode1 = new MethodCodeDTO();
					methodCode1.setCompanyProfit(methodCode.getCompanyProfit());
					methodCode1.setMethodCode(j);
					methodCode1.setTheoryBonus(methodCode.getTheoryBonus());
					methodCode1.setTopAgentPoint(methodCode.getTopAgentPoint());
					methodCode1.setTotalProfit(methodCode.getTotalProfit());
					methodCode1.setActualBonus(methodCode.getActualBonus());
					methodCode1.setActualBonusDown(methodCode.getActualBonusDown());
					methodCode1.setActualBonusBak(methodCode.getActualBonusBak());
					methodCode1.setMethodCodeName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
							struc.getGameSetCode(), struc.getBetMethodCode(), 2));
					methodCode.getMethodCodeList().add(methodCode1);

				}

			}

			List<Long> lotteryIds3d = Arrays.asList(99108L);
			if (lotteryIds3d.contains(lotteryId)
					&& GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(), struc.getGameSetCode(),
							struc.getBetMethodCode(), 1).equals("定位胆")
					&& GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(), struc.getGameSetCode(),
							struc.getBetMethodCode(), 2).equals("复式")) {

				for (int j = 59; j < 62; j++) {
					MethodCodeDTO methodCode1 = new MethodCodeDTO();
					methodCode1.setCompanyProfit(methodCode.getCompanyProfit());
					methodCode1.setMethodCode(j);
					methodCode1.setTheoryBonus(methodCode.getTheoryBonus());
					methodCode1.setTopAgentPoint(methodCode.getTopAgentPoint());
					methodCode1.setTotalProfit(methodCode.getTotalProfit());
					methodCode1.setActualBonus(methodCode.getActualBonus());
					methodCode1.setActualBonusBak(methodCode.getActualBonusBak());
					methodCode1.setMethodCodeName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
							struc.getGameSetCode(), struc.getBetMethodCode(), 2));
					methodCode.getMethodCodeList().add(methodCode1);
				}

			}

			List<AssistBmBonusStruc> assistBmBonusStrucList = struc.getAssistBMBonusList();
			long totalRetbate = 0l;
			if (assistBmBonusStrucList != null && !assistBmBonusStrucList.isEmpty()) {
				methodCode.setAssistBonusList(new ArrayList<AssistBonusDTO>());
				for (AssistBmBonusStruc assistBmBonusStruc : assistBmBonusStrucList) {
					AssistBonusDTO assistBonusDto = new AssistBonusDTO();
					assistBonusDto.setActualBonus(assistBmBonusStruc.getActualBonus() == null ? null
							: assistBmBonusStruc.getActualBonus() / 100);
					assistBonusDto.setMethodType(assistBmBonusStruc.getMethodType());
					assistBonusDto.setTheoryBonus(assistBmBonusStruc.getTheoryBonus() == null ? null
							: assistBmBonusStruc.getTheoryBonus() / 100);
					assistBonusDto.setActualBonusDown(assistBmBonusStruc.getActualBonusDown() == null ? null
							: assistBmBonusStruc.getActualBonusDown() / 100);
					methodCode.getAssistBonusList().add(assistBonusDto);
					totalRetbate += this.getTolalRetBat(assistBmBonusStruc.getActualBonus(),
							assistBmBonusStruc.getTheoryBonus());
				}
				if (setCode.getSetCode() == 15) {
					methodCode.setTotalRetbate(totalRetbate);
				} else {
					methodCode.setTotalRetbate(this.getTolalRetBat(methodCode.getAssistBonusList().get(0)
							.getActualBonus(), methodCode.getAssistBonusList().get(0).getTheoryBonus()));
				}
			}

			logger.info(" ====" + groupCode.getGroupCode() + "    " + setCode.getSetCode() + "    "
					+ methodCode.getMethodCode() + "");
			setCode.getMethodCodeList().add(methodCode);

		}

		for (AwardDTO dto : groupCodes) {
			List<SetCodeDTO> list1 = dto.getSetCodeList();
			for (SetCodeDTO cdto : list1) {
				List<MethodCodeDTO> methodList = cdto.getMethodCodeList();
				for (int i = 0; i < methodList.size() - 1; i++) {
					for (int j = methodList.size() - 1; j > i; j--) {
						if (GameAwardNameUtil.getTitle(lotteryId, dto.getGroupCode(), cdto.getSetCode(),
								methodList.get(j).getMethodCode(), 2).equals(
								GameAwardNameUtil.getTitle(lotteryId, dto.getGroupCode(), cdto.getSetCode(), methodList
										.get(i).getMethodCode(), 2))) {
							methodList.remove(j);
						}
					}
				}
			}

		}

		return groupCodes;
	}

	public Long getTolalRetBat(Long actualBonus, Long theoryBonus) {
		if (actualBonus == null || theoryBonus == null) {
			return 0l;
		}
		Double actualBonusd = actualBonus.doubleValue();
		Double theoryBonusd = theoryBonus.doubleValue();
		Double val = ((actualBonusd / theoryBonusd) * 10000);
		return val.longValue();
	}

	/**
	 * 奖金组奖金查询 
	 * @param lotteryId
	 * @param awardGroupId
	 * @param type
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選返點、7:骰寶直選返點、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 * @param retValue
	 * @param sysAwardGroupId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserGameAward")
	@ResponseBody
	public Object queryUserGameAward(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam(required = false) Long awardGroupId, @RequestParam("type") Long type,
			@RequestParam("awardType") Long awardType, @RequestParam("retValue") Long retValue,
			@RequestParam(required = false) Long sysAwardGroupId, @RequestParam(required = false) Long userId)
			throws Exception {

		GameUserAwardOperater op = new GameUserAwardOperater();
		try {
			GameUserAwardQueryRequest requestData = new GameUserAwardQueryRequest();
			requestData.setAwardGroupId(awardGroupId);
			requestData.setLotteryId(lotteryId);
			requestData.setAwardType(awardType);
			requestData.setType(type);
			requestData.setSysAwardGroupId(sysAwardGroupId);
			requestData.setUserId(userId);
			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryUserGameAwardUrl,
					requestData, GameAwardQueryResponse.class);
			List<AwardDTO> dtos = new ArrayList<AwardDTO>();
			if (null != response.getBody() && null != response.getBody().getResult()) {
				GameAwardQueryResponse awardQueryResponse = response.getBody().getResult();
				dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());
			}
			op.setStatus("ok");
			StringBuffer htmlBuffer = new StringBuffer();

			if (lotteryId != 99601) {
				htmlBuffer
						.append("<table class='table table-border'>")
						.append("<thead><tr><th>玩法群</th><th>玩法组</th><th>玩法/投注方式</th><th>奖金（元）</th><th>返点</th></tr></thead><tbody>");
			} else {
				htmlBuffer
						.append("<table class='table table-border'>")
						.append("<thead><tr><th>玩法群</th><th>玩法组</th><th>玩法/投注方式</th><th>赔率</th><th>返点</th></tr></thead><tbody>");
			}
			for (int i = 0; i < dtos.size(); i++) {
				AwardDTO awards = dtos.get(i);
				List<SetCodeDTO> setCodeList = dtos.get(i).getSetCodeList();
				for (int j = 0; j < setCodeList.size(); j++) {
					SetCodeDTO setCodes = setCodeList.get(j);
					List<MethodCodeDTO> methodCodeList = setCodes.getMethodCodeList();
					for (int k = 0; k < methodCodeList.size(); k++) {
						htmlBuffer.append("<tr>");
						MethodCodeDTO methodCodes = methodCodeList.get(k);
						if (j == 0 && k == 0) {
							htmlBuffer.append("<td rowspan='" + awards.getRowsCount() + "'>" + awards.getGroupName()
									+ "</td>");
						}
						if (k == 0) {
							htmlBuffer.append("<td rowspan='" + setCodes.getSetCount() + "'>"
									+ setCodes.getSetCodeName() + "</td>");
						}
						htmlBuffer.append("<td>" + methodCodes.getMethodCodeName() + "</td>");
						Double retVal = methodCodes.getRetVal() < 0 ? retValue.doubleValue() / 100.00 : methodCodes
								.getRetVal().doubleValue() / 100.00;
						retVal = type == 0 ? 0d : retVal;
						if("两面".equals(setCodes.getSetCodeName().trim())){
							retVal=0d;
						}
						if (methodCodes.getMethodCount() == 0 && methodCodes.getMethodCodeCount() == 0) {
							if (lotteryId != 99601) {
								htmlBuffer.append("<td>" + String.format("%.2f", methodCodes.getActualBonus() / 100.00)
										+ "</td>");
							} else {
								if(awards.getGroupCode().intValue()>=44 && awards.getGroupCode().intValue()<=51){
									htmlBuffer.append("<td>" + "非对子1:"
											+ String.format("%.2f", (methodCodes.getActualBonus() / 100.00 - 2) / 2) +
													"</br>对子1:" + String.format("%.2f", (methodCodes.getActualBonusDown() / 100.00 - 2) / 2)
											+ "</td>");
								}else{
									htmlBuffer.append("<td>" + "1:"
											+ String.format("%.2f", (methodCodes.getActualBonus() / 100.00 - 2) / 2)
											+ "</td>");
								}
							}
							htmlBuffer.append("<td>" + retVal + "%</td>");
						}
						if (methodCodes.getMethodCount() != 0) {
							htmlBuffer.append("<td>");
							List<AssistBonusDTO> assistBonusList = methodCodes.getAssistBonusList();
							for (int h = 0; h < assistBonusList.size(); h++) {
								AssistBonusDTO assistBonus = assistBonusList.get(h);
								htmlBuffer.append("<div>");
								if (lotteryId != 99601) {
									htmlBuffer.append(assistBonus.getMethodTypeName() + "&nbsp;&nbsp;"
											+ String.format("%.2f", assistBonus.getActualBonus() / 100.00));
								} else {
									if(awards.getGroupCode().intValue()>=44 && awards.getGroupCode().intValue()<=51){
										htmlBuffer.append(assistBonus.getMethodTypeName() + "&nbsp;&nbsp;" + "非对子1:"
												+ String.format("%.2f", (assistBonus.getActualBonus() / 100.00 - 2) / 2)
												+"</br>对子1:"+String.format("%.2f", (assistBonus.getActualBonusDown() / 100.00 - 2) / 2));
									}else
									htmlBuffer.append(assistBonus.getMethodTypeName() + "&nbsp;&nbsp;" + "1:"
											+ String.format("%.2f", (assistBonus.getActualBonus() / 100.00 - 2) / 2));
								}
								htmlBuffer.append("</div>");
							}
							htmlBuffer.append("</td>");

							htmlBuffer.append("<td>" + retVal + "%</td>");
						}

						if (methodCodes.getMethodCodeCount() != 0) {
							htmlBuffer.append("<td>");
							List<MethodCodeDTO> list = methodCodes.getMethodCodeList();
							for (int g = 0; g < list.size(); g++) {
								MethodCodeDTO methodCodeChild = list.get(g);
								htmlBuffer.append("<div>");
								if(awards.getGroupCode().intValue()>=44 && awards.getGroupCode().intValue()<=51){
									htmlBuffer.append(methodCodeChild.getMethodCodeName() + "&nbsp;&nbsp;非对子1:"
											+ String.format("%.2f", methodCodeChild.getActualBonus() / 100.00)+
											"</br>对子1:"+String.format("%.2f", methodCodeChild.getActualBonusDown() / 100.00));
								}else
								htmlBuffer.append(methodCodeChild.getMethodCodeName() + "&nbsp;&nbsp;"
										+ String.format("%.2f", methodCodeChild.getActualBonus() / 100.00));
								htmlBuffer.append("</div>");
							}
							htmlBuffer.append("</td>");
							htmlBuffer.append("<td>" + retVal + "%</td>");
						}
						htmlBuffer.append("</tr>");
					}
				}
			}

			htmlBuffer.append("</tbody>");
			op.setHtml(htmlBuffer.toString());

		} catch (Exception e) {
			op.setStatus("failure");
			op.setHtml("查询用户奖金错误");
		}
		return op;
	}

	/**
	 * 
	* @Title: queryBonusDetails 
	* @Description: 奖金详情查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryBonusDetails")
	public String queryBonusDetails(@RequestParam("type") Integer type, @RequestParam("awardType") Integer awardType,
			@ModelAttribute("queryBonusDetails") GameUserAwardGroupQueryRequest queryBonusDetails, Model model)
			throws Exception {
		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>();

		Long currentUserId = null;
		String currentUserName = null;
		logger.info("query bonusDetails start");
		try {
			currentUserId = RequestContext.getCurrUser().getId();
			currentUserName = RequestContext.getCurrUser().getUserName();
			queryBonusDetails.setUserid(currentUserId);
			queryBonusDetails.setType(type);
			queryBonusDetails.setAwardType(awardType);
			response = httpClient.invokeHttp(serverPath + queryGameUserAwardGroupUrl, queryBonusDetails, currentUserId,
					currentUserName, GameUserAwardGroupQueryResponse.class);
			List<UserAwardListStruc> result = response.getBody().getResult().getUserAwardListStruc();
			/*Map<String,List<UserAwardListStruc>> awardGoupMap = new HashMap<String,List<UserAwardListStruc>>();
			for(UserAwardListStruc u : result){
				String lotteryName = u.getLotteryName();
				if(awardGoupMap.containsKey(lotteryName)){
					awardGoupMap.get(lotteryName).add(u);
				}else{
					ArrayList<UserAwardListStruc> s = new ArrayList<UserAwardListStruc>();
					s.add(u);
					awardGoupMap.put(lotteryName, s);
				}
			}*/
			Map<Long, Map<String, List<UserAwardListStruc>>> awardGoupMap = new HashMap<Long, Map<String, List<UserAwardListStruc>>>();

			Map<String, List<UserAwardListStruc>> awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
			Long seriesCode;
			String seriesName;
			for (UserAwardListStruc u : result) {
				seriesCode = u.getLotterySeriesCode();
				seriesName = u.getLotteryName();

				if (awardGoupMap.containsKey(seriesCode)) {
					if (awardGoupMap.get(seriesCode).containsKey(seriesName)) {
						awardGoupMap.get(seriesCode).get(seriesName).add(u);
					} else {
						List<UserAwardListStruc> s = new ArrayList<UserAwardListStruc>();
						s.add(u);
						awardGoupMap.get(seriesCode).put(seriesName, s);
					}
				} else {
					ArrayList<UserAwardListStruc> s = new ArrayList<UserAwardListStruc>();
					s.add(u);
					awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
					awardGoupMapPerSeries.put(seriesName, s);

					awardGoupMap.put(seriesCode, awardGoupMapPerSeries);
				}
			}

			model.addAttribute("awardGoupMap", awardGoupMap);
			model.addAttribute("type", type);
		} catch (Exception e) {
			logger.error("query bonusDetails error");
			throw e;
		}

		logger.info("query bonusDetails end");

		return "/userCenter/queryBonusDetails";
	}

	/**
	 * 
	* @Title: queryBonusDetailsForModifyRet 
	* @Description: 返点修改初始化
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryBonusDetailsForModifyRet")
	public String queryBonusDetailsForModifyRet(@RequestParam("userid") Long userid,
			@RequestParam("type") Integer type, @RequestParam("awardType") Integer awardType,
			@ModelAttribute("queryBonusDetails") GameUserAwardGroupQueryRequest queryBonusDetails, Model model)
			throws Exception {
		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>();

		queryBonusDetails.setUserid(userid);
		queryBonusDetails.setType(type);
		queryBonusDetails.setAwardType(awardType);

		Long currentUserId = null;
		String currentUserAccount = null;
		logger.info("query bonusDetails for modify ret start");
		try {
			currentUserId = RequestContext.getCurrUser().getId();
			currentUserAccount = RequestContext.getCurrUser().getUserName();
			//			response = httpClient.invokeHttp(serverPath + queryGameUserAwardGroupUrl, queryBonusDetails,222L, "greg1", GameUserAwardGroupQueryResponse.class);
			response = httpClient.invokeHttp(serverPath + queryGameUserAwardGroupForModifyUrl, queryBonusDetails,
					currentUserId, currentUserAccount, GameUserAwardGroupQueryResponse.class);
			List<UserAwardListStruc> result = (List<UserAwardListStruc>) response.getBody().getResult()
					.getUserAwardListStruc();
			Map<Long, Map<String, List<UserAwardListStruc>>> awardGoupMap = new HashMap<Long, Map<String, List<UserAwardListStruc>>>();

			Map<String, List<UserAwardListStruc>> awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
			Long seriesCode;
			String seriesName;
			for (UserAwardListStruc u : result) {
				seriesCode = u.getLotterySeriesCode();
				seriesName = u.getLotterySeriesName();

				if (awardGoupMap.containsKey(seriesCode)) {
					awardGoupMap.get(seriesCode).get(seriesName).add(u);
				} else {
					ArrayList<UserAwardListStruc> s = new ArrayList<UserAwardListStruc>();
					s.add(u);
					awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
					awardGoupMapPerSeries.put(seriesName, s);
					awardGoupMap.put(seriesCode, awardGoupMapPerSeries);
				}
			}

			model.addAttribute("awardGoupMap", awardGoupMap);
			model.addAttribute("req", queryBonusDetails);
		} catch (Exception e) {
			logger.error("query bonusDetails  for modify ret error");
			throw e;
		}

		logger.info("query bonusDetails  for modify ret end");
		if (type == 0) {
			return "/userCenter/modifyRet";
		} else if (type == 1) {
			return "/userCenter/commonUserModifyRet";
		}

		return "";
	}

	//	/**
	//	 * 
	//	* @Title: queryBonusDetailsForModifyRetProxy
	//	* @Description: 返点修改初始化
	//	* @param request
	//	* @return
	//	* @throws Exception
	//	 */
	//	@RequestMapping(value = "/queryBonusDetailsForModifyRetProxy")
	//	public String queryBonusDetailsForModifyRetProxy(@ModelAttribute("queryBonusDetails") GameUserAwardGroupQueryRequest queryBonusDetails, Model model) throws Exception {
	//		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>();
	//		
	//		queryBonusDetails.setUserid(32l);
	//		queryBonusDetails.setType(1);
	//		
	//		logger.info("query bonusDetails for modify ret start");
	//		try{
	//			response = httpClient.invokeHttp(serverPath + queryGameUserAwardGroupUrl, queryBonusDetails,31L, "admin", GameUserAwardGroupQueryResponse.class);
	//			List<UserAwardListStruc> result = (List<UserAwardListStruc>) response.getBody().getResult().getUserAwardListStruc();
	//			Map<Long, Map<String, List<UserAwardListStruc>>> awardGoupMap = new HashMap<Long, Map<String, List<UserAwardListStruc>>>();
	//			
	//			Map<String, List<UserAwardListStruc>> awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
	//			Long seriesCode;
	//			String seriesName;
	//			for (UserAwardListStruc u : result) {
	//				seriesCode = u.getLotterySeriesCode();
	//				seriesName = u.getLotterySeriesName();
	//				
	//				if (awardGoupMap.containsKey(seriesCode)) {
	//					awardGoupMap.get(seriesCode).get(seriesName).add(u);
	//				} else {
	//					ArrayList<UserAwardListStruc> s = new ArrayList<UserAwardListStruc>();
	//					s.add(u);
	//					awardGoupMapPerSeries = new HashMap<String, List<UserAwardListStruc>>();
	//					awardGoupMapPerSeries.put(seriesName, s);
	//					
	//					awardGoupMap.put(seriesCode, awardGoupMapPerSeries);
	//				}
	//			}
	//			
	//			model.addAttribute("awardGoupMap", awardGoupMap);
	//		}catch(Exception e){
	//			logger.error("query bonusDetails  for modify ret error");
	//			throw e;
	//		}
	//		
	//		logger.info("query bonusDetails  for modify ret end");
	//		
	//		return "/userCenter/modifyRet";
	//	}

	/**
	 * 
	* @Title: modifyRet 
	* @Description: 修改返点
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyRet")
	@ResponseBody
	public Object modifyRet(@RequestBody GameUserAwardGroupModifyRequest retJson) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		logger.info("modify ret start");
		try {

			httpClient.invokeHttp(serverPath + modifyGameUserAwardGroupUrl, retJson, Object.class);
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify ret error");
			resp.setStatus(2l);
			resp.setMessage("modify ret error");
		}

		logger.info("modify ret end");

		return resp;
	}

	/**
	 * 
	* @Title: commonUserModifyRet 
	* @Description: 修改返点
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/commonUserModifyRet")
	@ResponseBody
	public Object commonUserModifyRet(@RequestBody GameUserAwardGroupModifyRequest retJson) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		logger.info("modify ret start");
		try {

			httpClient.invokeHttp(serverPath + modifyGameUserAwardGroupUrl, retJson, Object.class);
			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {
			logger.error("modify ret error");
			resp.setStatus(2l);
			resp.setMessage("modify ret error");
		}

		logger.info("modify ret end");

		return resp;
	}

	//	/** 
	//	* @Title: queryGameUserAwardGroupByLotteryId 
	//	* @Description: 查询用户可配置的投注奖金组列表         方法已移到betcontroller中
	//	* @param amount
	//	* @return
	//	* @throws Exception
	//	*/
	//	@RequestMapping(value = "/queryGameUserAwardGroupByLotteryId")
	//	@ResponseBody
	//	public Object queryGameUserAwardGroupByLotteryId(@RequestParam("userid") Long userid, @RequestParam("lotteryName") String lotteryName)
	//			throws Exception {
	//		logger.info("queryGameUserAwardGroupByLotteryId start");
	//		GameUserBetAwardGroupRequest request = new GameUserBetAwardGroupRequest();
	//		Response<GameUserBetAwardGroupResponse> response = new Response<GameUserBetAwardGroupResponse>();
	//		
	//		long lotteryid = SSCBetNameUtil.getLotteryId(lotteryName);
	//		
	//		request.setLotteryid(lotteryid);
	//		request.setUserid(userid);
	//		
	//		List<UserAwardListByBetStruc> userAwardList = new ArrayList<UserAwardListByBetStruc>();
	//		
	//		
	//		try {
	//			response = httpClient
	//					.invokeHttp(serverPath + queryGameUserAwardGroupByLotteryIdUrl, request, GameUserBetAwardGroupResponse.class);
	//			
	//			if(null != response.getBody().getResult()){
	//				userAwardList = response.getBody().getResult().getUserAwardListByBetStruc();
	//			}
	//			
	//			logger.info("queryGameUserAwardGroupByLotteryId end");
	//		} catch (Exception e) {
	//			logger.error("queryGameUserAwardGroupByLotteryId is error.", e);
	//			throw e;
	//		}
	//
	//		return userAwardList;
	//	}

	//	/**
	//	 * 
	//	* @Title: saveProxyBetGameAwardGroup 
	//	* @Description: 保存代理投注奖金组
	//	* @param request
	//	* @return
	//	* @throws Exception
	//	 */
	//	@RequestMapping(value = "/saveBetAward")
	//	@ResponseBody
	//	public Object saveProxyBetGameAwardGroup(@RequestParam("lotteryId") Long lotteryId, @RequestParam("awardGroupId") Long awardGroupId,@RequestParam("userid") Long userid) throws Exception {
	//
	//		AjaxResponse resp = new AjaxResponse();
	//		
	//		SaveProxyBetGameAwardGroupRequest retJson = new SaveProxyBetGameAwardGroupRequest();
	//		retJson.setAwardGroupId(awardGroupId);
	//		retJson.setLotteryid(lotteryId);
	//		retJson.setUserid(userid);
	//		
	//		logger.info("modify ret start");
	//		try{
	//			httpClient.invokeHttpWithoutResultType(serverPath + saveProxyBetGameAwardGroupUrl, retJson);
	//			resp.setStatus(1l);
	//			resp.setMessage("success");			
	//		}catch(Exception e){
	//			logger.error("modify ret error");
	//			resp.setStatus(2l);
	//			resp.setMessage("modify ret error");
	//		}
	//		
	//		logger.info("modify ret end");
	//		
	//		return resp;
	//	}

	@RequestMapping("/queryGameUserAwardGroup")
	@ResponseBody
	public Response<GameUserAwardGroupQueryResponse> queryGameUserAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupQueryRequest> request) throws Exception {
		logger.info("开始查询用户奖金组......");
		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>(request);

		response = httpClient.invokeHttp(serverPath + queryGameUserAwardGroupUrl, request,
				GameUserAwardGroupQueryResponse.class);

		logger.info("查询用户奖金组完成。");
		return response;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/openAccountDetailedConfigAwardGroup")
	@ResponseBody
	public Object openAccountDetailedConfigAwardGroup(
			@RequestBody @ValidRequestBody Request<OpenAccountDetailedConfigRequest> request) throws Exception {

		logger.info("开户详细分配奖金组......");
		Response response = new Response(request);

		response = httpClient.invokeHttp(serverPath + openAccountDetailedConfigAwardGroupUrl, request);

		logger.info("开户详细分配奖金组完成。");
		return response;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/openAccountQuickConfigAwardGroup")
	@ResponseBody
	public Object openAccountQuickConfigAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupModifyRequest> request) throws Exception {

		logger.info("开户快捷分配用户奖金组......");
		Response response = new Response(request);

		response = httpClient.invokeHttp(serverPath + openAccountQuickConfigAwardGroupUrl, request);

		logger.info("开户快捷分配用户奖金组完成。");
		return response;

	}

	/**
	 * 
	* @Title: queryGameUserAwardGroupByLogin
	* @Description: 当用户登录时候查询投注奖金组的情况,返回存在投注奖金组的彩种 参见UMI009
	* @param request
	* @return 
	* @throws Exception
	 */
	@RequestMapping("/queryAwardGroupByLogin")
	@ResponseBody
	public Response<GameUserAwardGroupLoginResponse> queryGameUserAwardGroupByLogin(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupQueryRequest> request) throws Exception {

		logger.info("登录查询用户投注奖金组信息......");

		Response<GameUserAwardGroupLoginResponse> response = new Response<GameUserAwardGroupLoginResponse>(request);

		response = httpClient.invokeHttp(serverPath + queryAwardGroupByLoginUrl, request);

		logger.info("登录查询用户投注奖金组完成。");
		return response;
	}

}
