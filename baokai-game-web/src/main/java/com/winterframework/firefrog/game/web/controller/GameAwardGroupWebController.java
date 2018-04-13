package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.AssistBmBonusStruc;
import com.winterframework.firefrog.game.web.dto.AssistBonusDTO;
import com.winterframework.firefrog.game.web.dto.AuditGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.AwardDTO;
import com.winterframework.firefrog.game.web.dto.AwardListStruc;
import com.winterframework.firefrog.game.web.dto.AwardListStrucDTO;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.DelGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameAwardTheoryBonus;
import com.winterframework.firefrog.game.web.dto.GameBonusPoolRequest;
import com.winterframework.firefrog.game.web.dto.GameBonusPoolStruc;
import com.winterframework.firefrog.game.web.dto.GameUserAwardOperater;
import com.winterframework.firefrog.game.web.dto.GameUserAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.MethodCodeDTO;
import com.winterframework.firefrog.game.web.dto.PublishGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.QueryTheoryRequest;
import com.winterframework.firefrog.game.web.dto.QueryTheoryResponse;
import com.winterframework.firefrog.game.web.dto.SetCodeDTO;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameAwardGroupWebController 
* @Description: 奖金组 
* @author Richard
* @date 2013-9-24 上午9:45:02 
*
 */
@RequestMapping("/gameoa")
@Controller("gameAwardGroupWebController")
@SuppressWarnings("unchecked")
public class GameAwardGroupWebController {

	private Logger log = LoggerFactory.getLogger(GameAwardGroupWebController.class);

	private SimpleDateFormat dateFormat;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameAwardGroupList")
	private String queryGameAwardGroupListUrl;

	@PropertyConfig(value = "url.game.queryGameAward")
	private String queryGameAwardUrl;

	@PropertyConfig(value = "url.game.queryUserGameAward")
	private String queryUserGameAwardUrl;

	@PropertyConfig(value = "url.game.createGameAwardGroup")
	private String createGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.auditGameAwardGroup")
	private String auditGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.editGameAwardGroup")
	private String editGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.publishGameAwardGroup")
	private String publishGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.querySellingStatusUrl")
	private String querySellingStatusUrl;

	@PropertyConfig(value = "url.game.delGameAwardGroup")
	private String delGameAwardGroupUrl;

	@PropertyConfig(value = "url.game.queryGameBonusPool")
	private String queryGameBonusPoolUrl;

	@PropertyConfig(value = "url.game.updateGameBonusPool")
	private String updateGameBonusPoolUrl;

	private static ObjectMapper mapper = new ObjectMapper();

	private static final Map<Long, Object> map = new HashMap<Long, Object>();

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		map.put(202006L, "已存在相同奖金组名称");
	}

	/**
	 * 
	* @Title: queryGameAwardGroupList 
	* @Description: 奖金组查询 
	* @param lotteryid
	* @param status
	* @param awardId
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameAwardGroupList")
	@ResponseBody
	public ModelAndView queryGameAwardGroupList(@RequestParam("lotteryid") Long lotteryid, Integer status, Long awardId)
			throws Exception {

		ModelAndView view = new ModelAndView("operations/gameAwardGroup/index");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			GameAwardGroupQueryRequest awardGroupQueryRequest = new GameAwardGroupQueryRequest();
			awardGroupQueryRequest.setGameAwardGroupId(awardId);
			awardGroupQueryRequest.setLotteryId(lotteryid);
			awardGroupQueryRequest.setStatus(status);

			Response<GameAwardGroupQueryResponse> response = httpClient.invokeHttp(serverPath
					+ queryGameAwardGroupListUrl, awardGroupQueryRequest, GameAwardGroupQueryResponse.class);

			List<AwardListStrucDTO> dtos = new ArrayList<AwardListStrucDTO>();

			for (AwardListStruc struc : response.getBody().getResult().getAwardListStruc()) {

				AwardListStrucDTO dto = new AwardListStrucDTO();

				dto.setAwardGroupId(struc.getAwardGroupId());
				dto.setAwardName(struc.getAwardName());
				dto.setCreateTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getCreateTime())));
				dto.setDirectRet(struc.getDirectRet());
				dto.setLotteryId(struc.getLotteryId());
				dto.setStatus(struc.getStatus());
				dto.setSysAwardGroup(struc.getSysAwardGroup());
				dto.setThreeoneRet(struc.getThreeoneRet());
				if (null != struc.getUpdateTime() && struc.getUpdateTime() > 0) {
					dto.setUpdateTime(dateFormat.format(DataConverterUtil.convertLong2Date(struc.getUpdateTime())));
				}
				dtos.add(dto);
			}

			view.addObject("gameAwardGroupList", dtos);
			view.addObject("lotteryId", lotteryid);
			view.addObject("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryid));

		} catch (Exception e) {

			log.error("queryGameAwardGroupList error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 奖金组奖金查询 
	 * @param lotteryId
	 * @param awardGroupId
	 * @param type
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選返點、7:骰寶直選返點、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 * @param retValue
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/queryUserGameAward","/frontQueryUserGameAward"})
	@ResponseBody
	public Object queryUserGameAward(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("awardGroupId") Long awardGroupId, @RequestParam("type") Long type,
			@RequestParam("awardType") Long awardType,@RequestParam("retValue") Long retValue) throws Exception {

		GameUserAwardOperater op = new GameUserAwardOperater();
		try {
			GameUserAwardQueryRequest requestData = new GameUserAwardQueryRequest();
			requestData.setAwardGroupId(awardGroupId);
			requestData.setLotteryId(lotteryId);
			requestData.setAwardType(awardType);
			requestData.setType(type);
			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryUserGameAwardUrl,
					requestData, GameAwardQueryResponse.class);
			List<AwardDTO> dtos = new ArrayList<AwardDTO>();
			if (null != response.getBody() && null != response.getBody().getResult()) {
				GameAwardQueryResponse awardQueryResponse = response.getBody().getResult();
				dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());
			}
			op.setStatus("ok");
			StringBuffer htmlBuffer = new StringBuffer();

			htmlBuffer.append("<table class='table table-border'>").append(
					"<thead><tr><th>玩法群</th><th>玩法组</th><th>玩法/投注方式</th><th>奖金（元）</th><th>返点</th></tr></thead><tbody>");
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
						Double retVal = methodCodes.getRetVal()<0?retValue.doubleValue()/100.00:methodCodes.getRetVal().doubleValue() / 100.00;
						if (methodCodes.getMethodCount() == 0 && methodCodes.getMethodCodeCount() == 0) {
							if(awards.getGroupCode().intValue()>=44 && awards.getGroupCode().intValue()<=51){
								htmlBuffer.append("<td>非对子:" + methodCodes.getActualBonus() / 100 +"</br>对子:"+methodCodes.getActualBonusDown() / 100+ "</td>");
							}else
							htmlBuffer.append("<td>" + methodCodes.getActualBonus() / 100 + "</td>");
							htmlBuffer.append("<td>" + retVal + "%</td>");
						}
						if (methodCodes.getMethodCount() != 0) {
							htmlBuffer.append("<td>");
							List<AssistBonusDTO> assistBonusList = methodCodes.getAssistBonusList();
							for (int h = 0; h < assistBonusList.size(); h++) {
								AssistBonusDTO assistBonus = assistBonusList.get(h);
								htmlBuffer.append("<div>");
								htmlBuffer.append(assistBonus.getMethodTypeName() + "&nbsp;&nbsp;"
										+ assistBonus.getActualBonus() / 100);
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
									htmlBuffer.append(methodCodeChild.getMethodCodeName() + "&nbsp;&nbsp;非对子:"
											+ methodCodeChild.getActualBonus() / 100+ "&nbsp;&nbsp;对子:"
											+ methodCodeChild.getActualBonusDown() / 100);
								}else
								htmlBuffer.append(methodCodeChild.getMethodCodeName() + "&nbsp;&nbsp;"
										+ methodCodeChild.getActualBonus() / 100);
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
	* @Title: queryGameAward 
	* @Description: 4.18	奖金组奖金查询 
	* @param lotteryId
	* @param awardGroupId
	* @param status
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameAward")
	@ResponseBody
	public ModelAndView queryGameAward(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("awardId") Long awardId, @RequestParam("status") Integer status) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameAwardGroup/queryGameAward");
		if (lotteryId.longValue() == 99401l) {
			view = new ModelAndView("operations/gameAwardGroup/ssq/queryGameAward");
			try {
				Response<GameBonusPoolStruc> res = httpClient.invokeHttp(serverPath + queryGameBonusPoolUrl, lotteryId,
						GameBonusPoolStruc.class);
				view.addObject("gameBonusPool", res.getBody().getResult());
				view.addObject("awardId", awardId);
			} catch (Exception e) {
				log.error("queryGameBonusPool error:", e);
				throw e;
			}
		}

		try {

			GameAwardQueryRequest requestData = new GameAwardQueryRequest();
			requestData.setAwardGroupId(awardId);
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(status);

			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);

			GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();

			List<AwardDTO> dtos = new ArrayList<AwardDTO>();
			awardQueryResponse.setLotteryId(lotteryId);
			if (null != response.getBody() && null != response.getBody().getResult()) {
				awardQueryResponse = response.getBody().getResult();
				dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());
			}

			view.addObject("awardList", dtos);
			view.addObject("lotteryId", lotteryId);
			view.addObject("gameAward", awardQueryResponse);
			view.addObject("miniLotteryProfit", awardQueryResponse.getMiniLotteryProfit());

		} catch (Exception e) {

			log.error("queryGameAward error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: toGameLockDate 
	* @Description:发布奖金池
	* @return
	 */
	@RequestMapping("/publishGameBonusPool")
	public ModelAndView publishConfig(@ModelAttribute("request") GameBonusPoolRequest request,
			@RequestParam("awardId") Long awardId) throws Exception {
		request.setStatus(4l);
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameBonusPoolUrl, request);
		} catch (Exception e) {
			log.error("发布奖金池失败", e);
			throw e;
		}
		return this.queryGameAward(request.getLotteryid(), awardId, 1);
	}

	/**
	 * 
	* @Title: toEditGameBonusPool 
	* @Description:发布奖金池
	* @return
	 */
	@RequestMapping("/toEditGameBonusPool")
	public ModelAndView toEditGameBonusPool(@RequestParam("lotteryid") Long lotteryId,
			@RequestParam("awardId") Long awardId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gameAwardGroup/ssq/editGameAward");
		try {
			Response<GameBonusPoolStruc> res = httpClient.invokeHttp(serverPath + queryGameBonusPoolUrl, lotteryId,
					GameBonusPoolStruc.class);
			view.addObject("gameBonusPool", res.getBody().getResult());
			view.addObject("awardId", awardId);
			view.addObject("lotteryId", lotteryId);
			view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		} catch (Exception e) {
			log.error("queryGameBonusPool error:", e);
			throw e;
		}
		try {

			GameAwardQueryRequest requestData = new GameAwardQueryRequest();
			requestData.setAwardGroupId(awardId);
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(1);

			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);

			GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();
			awardQueryResponse.setLotteryId(lotteryId);
			List<AwardDTO> dtos = new ArrayList<AwardDTO>();
			if (null != response.getBody() && null != response.getBody().getResult()) {
				awardQueryResponse = response.getBody().getResult();
				dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());
			}

			view.addObject("awardList", dtos);
			view.addObject("gameAward", awardQueryResponse);
			view.addObject("awardId", awardId);
			view.addObject("lotteryId", lotteryId);
			view.addObject("miniLotteryProfit", awardQueryResponse.getMiniLotteryProfit());

		} catch (Exception e) {

			log.error("queryGameAward error:", e);
			throw e;
		}
		return view;

	}

	/**
	 * 
	* @Title: toEditGameBonusPool 
	* @Description:发布奖金池
	* @return
	 */
	@RequestMapping("/toAuditGameBonusPool")
	public ModelAndView toAuditGameBonusPool(@RequestParam("lotteryid") Long lotteryId,
			@RequestParam("awardId") Long awardId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gameAwardGroup/ssq/auditGameAward");
		try {
			Response<GameBonusPoolStruc> res = httpClient.invokeHttp(serverPath + queryGameBonusPoolUrl, lotteryId,
					GameBonusPoolStruc.class);
			view.addObject("gameBonusPool", res.getBody().getResult());
			view.addObject("awardId", awardId);
			view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		} catch (Exception e) {
			log.error("queryGameBonusPool error:", e);
			throw e;
		}
		try {

			GameAwardQueryRequest requestData = new GameAwardQueryRequest();
			requestData.setAwardGroupId(awardId);
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(1);

			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);

			GameAwardQueryResponse awardQueryResponse = response.getBody().getResult();
			awardQueryResponse.setLotteryId(lotteryId);
			List<AwardDTO> dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());

			view.addObject("awardList", dtos);
			view.addObject("gameAward", awardQueryResponse);
			view.addObject("lotteryId", lotteryId);
			view.addObject("awardId", awardId);
			view.addObject("miniLotteryProfit", awardQueryResponse.getMiniLotteryProfit());

		} catch (Exception e) {

			log.error("queryGameAward error:", e);
			throw e;
		}
		return view;

	}

	/**
	 * 
	* @Title: toGameLockDate 
	* @Description:修改奖金池
	* @return
	 */
	@RequestMapping("/editGameBonusPool")
	public ModelAndView updateGameBonusPool(@ModelAttribute("request") GameBonusPoolRequest request,
			@RequestParam("awardId") Long awardId) throws Exception {
		request.setStatus(1l);

		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameBonusPoolUrl, request);
		} catch (Exception e) {
			log.error("修改奖金信息失败", e);
			throw e;
		}

		return this.queryGameAward(request.getLotteryid(), awardId, 1);
	}

	/**
	 * 
	* @Title: toGameLockDate 
	* @Description:修改奖金池
	* @return
	 */
	@RequestMapping("/auditGameBonusPool")
	public ModelAndView auditGameBonusPool(@ModelAttribute("request") GameBonusPoolRequest request,
			@RequestParam("awardId") Long awardId) throws Exception {
		request.setStatus(2l);

		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameBonusPoolUrl, request);
		} catch (Exception e) {
			log.error("修改奖金信息失败", e);
			throw e;
		}

		return this.queryGameAward(request.getLotteryid(), awardId, 1);
	}

	public Long getTolalRetBat(Long actualBonus,Long theoryBonus,Long actualBonusDown,Integer gameGroupCode ) {
		if (gameGroupCode >= 44 && gameGroupCode <= 51) {
			if (actualBonus == null || actualBonusDown == null) {
				return 0l;
			}
			Double actualBonusd = actualBonus.doubleValue();
			Double actualBonusDownd = actualBonusDown.doubleValue();
			Double val = 10000 - (actualBonusd - actualBonusDownd) * 0.1
					/ actualBonusd * 10000;
			return val.longValue();
		} else {
			if (actualBonus == null || theoryBonus == null) {
				return 0l;
			}
			Double actualBonusd = actualBonus.doubleValue();
			Double theoryBonusd = theoryBonus.doubleValue();
			Double val = ((actualBonusd / theoryBonusd) * 10000);
			return val.longValue();
		}

	}
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
				/*if(struc.getGameGroupCode().intValue()>=44 && struc.getGameGroupCode().intValue()<=51){
					groupCode.setGroupName(SuperPairUtil.alias+"_"+groupCode.getGroupName());
				}*/
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
				/*if(struc.getGameGroupCode().intValue()>=44 && struc.getGameGroupCode().intValue()<=51){
					setCode.setSetCodeName(SuperPairUtil.alias+"_"+setCode.getSetCodeName());
				}*/
				groupCode.getSetCodeList().add(setCode);
			}

			MethodCodeDTO methodCode = new MethodCodeDTO();
			methodCode.setCompanyProfit(struc.getCompanyProfit());
			methodCode.setMethodCode(struc.getBetMethodCode());
			methodCode.setTheoryBonus(struc.getTheoryBonus() == null ? null : struc.getTheoryBonus() / 100);
			methodCode.setTopAgentPoint(struc.getTopAgentPoint());
			methodCode.setTotalProfit(struc.getTotalProfit());
			if(struc.getGameGroupCode()>=44&&struc.getGameGroupCode()<=51)
			{methodCode.setRetVal(100l);}
			else
			methodCode.setRetVal(struc.getRetVal());
			methodCode.setActualBonus(struc.getActualBonus() == null ? null : struc.getActualBonus() / 100);
			methodCode.setActualBonusDown(struc.getActualBonusDown() == null ? null : struc.getActualBonusDown() / 100);
			methodCode.setActualBonusBak(struc.getActualBonusBak() == null ? null : struc.getActualBonusBak());
			methodCode.setMethodCodeName(GameAwardNameUtil.getTitle(lotteryId, struc.getGameGroupCode(),
					struc.getGameSetCode(), struc.getBetMethodCode(), 2));
			methodCode.setTotalRetbate(this.getTolalRetBat(struc.getActualBonus(),struc.getTheoryBonus(),struc.getActualBonusDown(),struc.getGameGroupCode()));

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
					assistBonusDto.setLhcCodeName(assistBmBonusStruc.getLhcCodeTitle());
					assistBonusDto.setRetVal(assistBmBonusStruc.getRetVal());
					assistBonusDto.setLhcTheoryBonus(assistBmBonusStruc.getLhcTheoryBonus() == null ? null
							: Double.valueOf(assistBmBonusStruc.getLhcTheoryBonus())/100);
					methodCode.getAssistBonusList().add(assistBonusDto);
					totalRetbate += this.getTolalRetBat(
							assistBmBonusStruc.getActualBonus(),
							assistBmBonusStruc.getTheoryBonus(),null,struc.getGameGroupCode());
				}
				if (setCode.getSetCode() == 15) {
					methodCode.setTotalRetbate(totalRetbate);
				} else {
					methodCode.setTotalRetbate(this.getTolalRetBat(methodCode
							.getAssistBonusList().get(0).getActualBonus(),
							methodCode.getAssistBonusList().get(0)
									.getTheoryBonus(),methodCode.getAssistBonusList().get(0).getActualBonusDown(),struc.getGameGroupCode()));
				}
			}

			log.info(" ====" + groupCode.getGroupCode() + "    " + setCode.getSetCode() + "    "
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

	/**
	 * 
	* @Title: preCreateGameAwardGroup 
	* @Description:新增用户奖金组 
	* @return
	* @throws Exception
	 */
	@RequestMapping("/preCreateGameAwardGroup")
	@ResponseBody
	public ModelAndView preCreateGameAwardGroup(@RequestParam("lotteryId") Long lotteryId) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameAwardGroup/createGameAwardGroup");

		try {

			QueryTheoryRequest request = new QueryTheoryRequest();
			request.setLotteyId(lotteryId);

			//此处回去理论奖金
			Response<QueryTheoryResponse> response = httpClient.invokeHttp(serverPath + querySellingStatusUrl, request,
					QueryTheoryResponse.class);

			if (null == response.getBody().getResult()) {
				throw new RuntimeException("获取奖金组理论奖金异常，请联系管理员。");
			}

			GameAwardTheoryBonus gameAwardTheoryBonus = new GameAwardTheoryBonus();
			QueryTheoryResponse theory = response.getBody().getResult();

			gameAwardTheoryBonus.setLotteryId(lotteryId);
			List<AwardDTO> dtos = convertStruc2DTO(lotteryId, theory.getAwardList());

			gameAwardTheoryBonus.setAwardList(dtos);

			view.addObject("miniLotteryProfit", theory.getMiniLotteryProfit());
			view.addObject("gameAwardTheoryBonus", gameAwardTheoryBonus);
			view.addObject("gameAwardName", GameAwardNameUtil4Web.lotteryName(lotteryId));

		} catch (Exception e) {
			log.error("preCreateGameAwardGroup error:", e);
			throw e;
		}
		return view;
	}

	/**
	 * 
	* @Title: createGameAwardGroup 
	* @Description: 4.19	新增用户奖金组
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/createGameAwardGroup")
	@ResponseBody
	public Object createGameAwardGroup(@ModelAttribute("createGameAwardGroupStr") String createGameAwardGroupStr)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {
			CreateGameAwardGroupRequest request = mapper.readValue(createGameAwardGroupStr,
					new TypeReference<CreateGameAwardGroupRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + createGameAwardGroupUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();

				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(2l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");
		} catch (Exception e) {

			log.error("createGameAwardGroup error:", e);
			resp.setStatus(2l);
			resp.setMessage("cancelGameIssueRule errors");
		}

		return resp;
	}

	@RequestMapping("/preAuditGameAwardGroup")
	@ResponseBody
	public ModelAndView preAuditGameAwardGroup(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("awardId") Long awardId, @RequestParam("status") Integer status) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameAwardGroup/auditGameAwardGroup");

		try {

			GameAwardQueryRequest requestData = new GameAwardQueryRequest();
			requestData.setAwardGroupId(awardId);
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(status);

			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);

			GameAwardQueryResponse awardQueryResponse = response.getBody().getResult();
			awardQueryResponse.setLotteryId(lotteryId);
			List<AwardDTO> dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());

			view.addObject("awardList", dtos);
			view.addObject("gameAward", awardQueryResponse);
			view.addObject("lotteryId", lotteryId);
			view.addObject("awardId", awardId);
			view.addObject("miniLotteryProfit", awardQueryResponse.getMiniLotteryProfit());

		} catch (Exception e) {

			log.error("queryGameAward error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: auditGameAwardGroup 
	* @Description:4.20	奖金组审核
	* @param lotteryId
	* @param awardId
	* @param checkType
	* @return
	* @throws Exception
	 */
	@RequestMapping("/auditGameAwardGroup")
	@ResponseBody
	public Object auditGameAwardGroup(@ModelAttribute("auditGameAwardGroupStr") String auditGameAwardGroupStr)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			AuditGameAwardGroupRequest request = mapper.readValue(auditGameAwardGroupStr,
					new TypeReference<AuditGameAwardGroupRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + auditGameAwardGroupUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();

				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(1l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (Exception e) {

			log.error("auditGameAwardGroup error:", e);
			resp.setStatus(2l);
			resp.setMessage("auditGameAwardGroup error");
		}
		return resp;
	}

	@RequestMapping("/preEditGameAwardGroup")
	public ModelAndView preEditGameAwardGroup(@RequestParam("lotteryId") Long lotteryId,
			@RequestParam("awardId") Long awardId, @RequestParam("status") Integer status) throws Exception {

		ModelAndView view = new ModelAndView("operations/gameAwardGroup/editGameAwardGroup");

		try {

			GameAwardQueryRequest requestData = new GameAwardQueryRequest();
			requestData.setAwardGroupId(awardId);
			requestData.setLotteryId(lotteryId);
			requestData.setStatus(status);

			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);

			GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();
			awardQueryResponse.setLotteryId(lotteryId);
			List<AwardDTO> dtos = new ArrayList<AwardDTO>();
			if (null != response.getBody() && null != response.getBody().getResult()) {
				awardQueryResponse = response.getBody().getResult();
				dtos = convertStruc2DTO(lotteryId, awardQueryResponse.getAwardBonusStrucList());
			}

			view.addObject("awardList", dtos);
			view.addObject("gameAward", awardQueryResponse);
			view.addObject("awardId", awardId);
			view.addObject("lotteryId", lotteryId);
			view.addObject("status", status);
			view.addObject("miniLotteryProfit", awardQueryResponse.getMiniLotteryProfit());

		} catch (Exception e) {

			log.error("queryGameAward error:", e);
			throw e;
		}

		return view;
	}

	/**
	 * 
	* @Title: editGameAwardGroup 
	* @Description: 4.21	奖金组修改
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/editGameAwardGroup")
	@ResponseBody
	public Object editGameAwardGroup(@ModelAttribute("editGameAwardGroupStr") String editGameAwardGroupStr)
			throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {
			EditGameAwardGroupRequest request = mapper.readValue(editGameAwardGroupStr,
					new TypeReference<EditGameAwardGroupRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + editGameAwardGroupUrl,
					request);

			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();

				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(1l);
				resp.setMessage(message);
				return resp;
			}

			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (Exception e) {

			log.error("editGameAwardGroup error:", e);
			resp.setStatus(2l);
			resp.setMessage("editGameAwardGroup errors");
		}

		return resp;
	}

	/**
	 * 
	* @Title: publishGameAwardGroup 
	* @Description:4.22	奖金组发布
	* @param lotteryId
	* @param awardId
	* @return
	* @throws Exception
	 */
	@RequestMapping("/publishGameAwardGroup")
	@ResponseBody
	public Object publishGameAwardGroup(@ModelAttribute("publishGameStr") String publishGameStr) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			PublishGameAwardGroupRequest request = mapper.readValue(publishGameStr,
					new TypeReference<PublishGameAwardGroupRequest>() {
					});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + publishGameAwardGroupUrl,
					request);
			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();

				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(1l);
				resp.setMessage(message);
				return resp;
			}
			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (Exception e) {

			log.error("publishGameAwardGroup error:", e);
			resp.setStatus(2l);
			resp.setMessage("publishGameAwardGroup error");
		}
		return resp;
	}

	@RequestMapping("/delGameAwardGroup")
	@ResponseBody
	public Object delGameAwardGroup(@ModelAttribute("delStr") String delStr) throws Exception {

		AjaxResponse resp = new AjaxResponse();

		try {

			DelGameAwardGroupRequest request = mapper.readValue(delStr, new TypeReference<DelGameAwardGroupRequest>() {
			});

			Response<Object> response = httpClient.invokeHttpWithoutResultType(serverPath + delGameAwardGroupUrl,
					request);
			String message = "";
			if (response.getHead().getStatus() > 0) {
				try {
					message = map.get(response.getHead().getStatus()).toString();

				} catch (Exception e) {
					message = "系统异常，请联系管理员";
				}

				resp.setStatus(1l);
				resp.setMessage(message);
				return resp;
			}
			resp.setStatus(1l);
			resp.setMessage("success");

		} catch (Exception e) {

			log.error("publishGameAwardGroup error:", e);
			resp.setStatus(2l);
			resp.setMessage("publishGameAwardGroup error");
		}
		return resp;
	}

}
