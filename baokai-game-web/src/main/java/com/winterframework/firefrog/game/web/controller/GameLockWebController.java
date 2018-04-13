package com.winterframework.firefrog.game.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameLockDataStruc;
import com.winterframework.firefrog.game.web.dto.GameLockRequest;
import com.winterframework.firefrog.game.web.dto.GameLockStruc;
import com.winterframework.firefrog.game.web.dto.GetGameIssueStrucsRequest;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameLockWebController 
* @Description:封锁controller
* @author floy
* @date 2013-9-23 下午3:52:52 
*
 */
@Controller("gameLockWebController")
@RequestMapping(value = "/gameoa")
@SuppressWarnings("unchecked")
public class GameLockWebController {

	private Logger log = LoggerFactory.getLogger(GameLockWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameConfig")
	private String queryGameConfigUrl;

	@PropertyConfig(value = "url.game.updateGameConfig")
	private String updateGameConfigUrl;

	@PropertyConfig(value = "url.game.getGameLockData")
	private String getGameLockDataUrl;

	/**根據 lotteryId 查詢當前獎期及前後各10筆的獎期資料；第一筆為當期獎期，2~11為前10筆獎期，12~21為後10筆獎期*/
	@PropertyConfig("url.game.getGameIssuesForLockData")
	private String getGameIssuesForLockDataUrl;

	@PropertyConfig(value = "url.game.queryGameAward")
	private String queryGameAwardUrl;

	private Long[] getSsqBonus() throws Exception {
		GameAwardQueryRequest requestData = new GameAwardQueryRequest();
		requestData.setAwardGroupId(187l);
		requestData.setLotteryId(99401l);
		requestData.setStatus(1);

		Long[] result = new Long[] { 35000000l, 3000000l, 180000l, 100000l };
		try {
			Response<GameAwardQueryResponse> response = httpClient.invokeHttp(serverPath + queryGameAwardUrl,
					requestData, GameAwardQueryResponse.class);
			if (null != response.getBody() && null != response.getBody().getResult()) {
				AwardBonusStruc struc = response.getBody().getResult().getAwardBonusStrucList().get(0);
				result[0] = (struc.getAssistBMBonusList().get(2).getActualBonus());
				result[1] = (struc.getAssistBMBonusList().get(3).getActualBonus());
				result[2] = (struc.getAssistBMBonusList().get(4).getActualBonus());
				result[3] = (struc.getAssistBMBonusList().get(5).getActualBonus());
			}
		} catch (Exception e) {
			log.error("query bouns error", e);
		}
		return result;
	}

	/**
	 * 封锁标签主页，查询封锁数据。
	 * @param lotteryId 彩種ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toGameLockDate")
	@ResponseBody
	public ModelAndView toGameLockDate(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		GameLockDataQueryRequest request = new GameLockDataQueryRequest();
		request.setLotteryId(lotteryId);
		request.setSortType(0l);
		ModelAndView view = new ModelAndView("operations/gamelock/gameLockData");
		// 雙色球路徑不一樣
		if (lotteryId.longValue() == 99401l) {
			view = new ModelAndView("operations/gamelock/ssq/gameLockData");
		}
		Response<GameLockDataStruc> response = new Response<GameLockDataStruc>();
		try {
			GetGameIssueStrucsRequest req = new GetGameIssueStrucsRequest();
			req.setLotteryId(lotteryId);
			List<IssueStruc> issueList = (List<IssueStruc>) httpClient
					.invokeHttp(serverPath + getGameIssuesForLockDataUrl, req,
							new TypeReference<Response<List<IssueStruc>>>() {
							}).getBody().getResult();
			// 取得當期獎期編碼
			request.setIssueCode(issueList.get(0).getIssueCode());
			response = httpClient.invokeHttp(serverPath + getGameLockDataUrl, request,
					new TypeReference<Response<GameLockDataStruc>>() {
					});
			view.addObject("issueList", issueList);
		} catch (Exception e) {
			log.error("getGameLockData", e);
			throw e;
		}
		GameLockDataStruc struc = response.getBody().getResult();
		view.addObject("request", request);
		struc.caculateValue();
		view.addObject("struc", struc);
		view.addObject("lotteryId", lotteryId);
		return view;
	}

	/**
	 * 
	* @Title: toGameLockDate 
	* @Description:封锁参数设置页。
	* @return
	 */
	@RequestMapping("/toGameLockConfig")
	@ResponseBody
	public ModelAndView toGameLockConfig(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelock/gameLockConfig");
		if (lotteryId.longValue() == 99401l) {
			view = new ModelAndView("operations/gamelock/ssq/gameLockConfig");
		}
		Response<GameLockStruc> response = new Response<GameLockStruc>();
		try {
			GameLockRequest request = new GameLockRequest();
			request.setGameId(lotteryId);
			response = httpClient.invokeHttp(serverPath + queryGameConfigUrl, request,
					new TypeReference<Response<GameLockStruc>>() {
					});
		} catch (Exception e) {
			log.error("查询封锁配置信息失败", e);
			throw e;
		}
		view.addObject("gameLock", response.getBody().getResult());
		view.addObject("lotteryId", lotteryId);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("bonus", this.getSsqBonus());
		return view;
	}

	/**
	 * 
	* @Title: toGameLockDate 
	* @Description:发布封锁数据
	* @return
	 */
	@RequestMapping("/publishConfig")
	public ModelAndView publishConfig(@ModelAttribute("request") GameLockRequest request) throws Exception {
		request.setStatus(request.getStatus());
		request.setGameId(request.getGameId());
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameConfigUrl, request);
		} catch (Exception e) {
			log.error("发布封锁配置信息失败", e);
			throw e;
		}
		return this.toGameLockConfig(request.getGameId());//"forward:/gameoa/toGameLockConfig?lotteryid=" + request.getGameId();
	}

	/**
	 * 
	* @Title: toEditGameLockConfig 
	* @Description:封锁配置修改页。
	* @return
	 */
	@RequestMapping("/toEditGameLockConfig")
	@ResponseBody
	public ModelAndView toEditGameLockConfig(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelock/editGameLockConfig");
		if (lotteryId.longValue() == 99401l) {
			view = new ModelAndView("operations/gamelock/ssq/editGameLockConfig");
		}
		Response<GameLockStruc> response = new Response<GameLockStruc>();
		try {
			GameLockRequest request = new GameLockRequest();
			request.setGameId(lotteryId);
			response = httpClient.invokeHttp(serverPath + queryGameConfigUrl, request,
					new TypeReference<Response<GameLockStruc>>() {
					});
		} catch (Exception e) {
			log.error("查询封锁配置信息失败", e);
			throw e;
		}
		GameLockStruc struc = response.getBody().getResult();
		view.addObject("lotteryId", lotteryId);
		view.addObject("id", struc.getId());
		view.addObject("upVal", struc.getUpVal());
		view.addObject("upValProcess", struc.getUpValProcess());
		view.addObject("upVal2", struc.getUpVal2());
		view.addObject("upValProcess2", struc.getUpValProcess2());
		view.addObject("upVal3", struc.getUpVal3());
		view.addObject("upValProcess3", struc.getUpValProcess3());
		view.addObject("gameLock", struc);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("bonus", this.getSsqBonus());
		return view;
	}

	/**
	 * 
	* @Title: editConfig 
	* @Description:修改封锁数据
	* @return
	 */
	@RequestMapping("/editConfig")
	public ModelAndView editConfig(@ModelAttribute("request") GameLockRequest request) throws Exception {
		request.setStatus(1l);
		request.setGameId(request.getGameId());
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameConfigUrl, request);
		} catch (Exception e) {
			log.error("修改封锁配置信息失败", e);
			throw e;
		}
		return this.toGameLockConfig(request.getGameId());
	}

	/**
	 * 
	* @Title: toAuditGameLockConfig 
	* @Description:封锁配置审核页。
	* @return
	 */
	@RequestMapping("/toAuditGameLockConfig")
	@ResponseBody
	public ModelAndView toAuditGameLockConfig(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelock/auditGameLockConfig");
		if (lotteryId.longValue() == 99401l) {
			view = new ModelAndView("operations/gamelock/ssq/auditGameLockConfig");
		}
		Response<GameLockStruc> response = new Response<GameLockStruc>();
		try {
			GameLockRequest request = new GameLockRequest();
			request.setGameId(lotteryId);
			response = httpClient.invokeHttp(serverPath + queryGameConfigUrl, request,
					new TypeReference<Response<GameLockStruc>>() {
					});
		} catch (Exception e) {
			log.error("查询封锁配置信息失败", e);
			throw e;
		}
		GameLockStruc struc = response.getBody().getResult();
		//此处暂不实行封锁数据查询，只用于跳转到封锁主页
		view.addObject("lotteryId", lotteryId);
		view.addObject("id", struc.getId());
		view.addObject("upVal", struc.getUpVal());
		view.addObject("upValProcess", struc.getUpValProcess());
		view.addObject("upVal2", struc.getUpVal2());
		view.addObject("upValProcess2", struc.getUpValProcess2());
		view.addObject("upVal3", struc.getUpVal3());
		view.addObject("upValProcess3", struc.getUpValProcess3());
		view.addObject("gameLock", struc);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("bonus", this.getSsqBonus());
		return view;
	}

	/**
	 * 
	* @Title: editConfig 
	* @Description:审核封锁数据
	* @return
	 */
	@RequestMapping("/auditConfig")
	public ModelAndView auditConfig(@ModelAttribute("request") GameLockRequest request) throws Exception {
		GameLockRequest requestData = new GameLockRequest();
		requestData.setId(request.getId());
		requestData.setStatus(request.getStatus());
		requestData.setGameId(request.getGameId());
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameConfigUrl, requestData);
		} catch (Exception e) {
			log.error("审核封锁配置信息失败", e);
			throw e;
		}
		return this.toGameLockConfig(request.getGameId());
	}

	/**
	 * 依據條件查詢封锁数据。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGameLockData", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getGameLockData(@ModelAttribute("request") GameLockDataQueryRequest request) throws Exception {

		ModelAndView view = new ModelAndView("operations/gamelock/gameLockData");
		// 雙色球路徑不一樣
		if (request.getLotteryId() == 99401l) {
			view = new ModelAndView("operations/gamelock/ssq/gameLockData");
		}
		Response<GameLockDataStruc> response = new Response<GameLockDataStruc>();
		try {
			GetGameIssueStrucsRequest req = new GetGameIssueStrucsRequest();
			req.setLotteryId(request.getLotteryId());
			List<IssueStruc> issueList = (List<IssueStruc>) httpClient
					.invokeHttp(serverPath + getGameIssuesForLockDataUrl, req,
							new TypeReference<Response<List<IssueStruc>>>() {
							}).getBody().getResult();
			response = httpClient.invokeHttp(serverPath + getGameLockDataUrl, request,
					new TypeReference<Response<GameLockDataStruc>>() {
					});
			view.addObject("issueList", issueList);
		} catch (Exception e) {
			log.error("getGameLockData", e);
			throw e;
		}
		view.addObject("request", request);
		response.getBody().getResult().caculateValue();
		view.addObject("struc", response.getBody().getResult());
		view.addObject("lotteryId", request.getLotteryId());
		return view;
	}

}
