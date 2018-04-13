/**   
* @Title: GameLockAppraiseWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-7 下午1:55:17 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseAddRequest;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseChangeStruc;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseDeleteRequest;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLockAppriaseOperateRequest;
import com.winterframework.firefrog.game.web.dto.GameLockParamResponse;
import com.winterframework.firefrog.game.web.dto.GameLockRequest;
import com.winterframework.firefrog.game.web.dto.GameLockStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil4Web;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 
* @ClassName: GameLockWebController 
* @Description:变价controller
* @author floy
* @date 2013-9-23 下午3:52:52 
*
 */
@Controller("gameLockAppraiseWebController")
@RequestMapping(value = "/gameoa")
@SuppressWarnings("unchecked")
public class GameLockAppraiseWebController {

	private Logger log = LoggerFactory.getLogger(GameLockAppraiseWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryAllGameLockAppraise")
	private String queryAllGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.operateGameLockAppraise")
	private String operateGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.deleteGameLockAppraise")
	private String deleteGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.queryGameConfig")
	private String queryGameConfigUrl;

	@PropertyConfig(value = "url.game.addGameLockAppraise")
	private String addGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.queryGameLockAppraise")
	private String queryGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.updateGameLockAppraise")
	private String updateGameLockAppraiseUrl;

	@PropertyConfig(value = "url.game.queryGameLockParam")
	private String queryGameLockParamUrl;

	/**
	 * 
	* @Title: queryAllGameLockAppraise 
	* @Description:查询所有的变价数据
	* @return
	 */
	@RequestMapping("/queryAllGameLockAppraise")
	@ResponseBody
	public ModelAndView queryAllGameLockAppraise(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		log.info("queryAllGameLockAppraise start");
		ModelAndView view = new ModelAndView("operations/gamelockappraise/index");
		Response<List<GameLockAppraiseQueryResponse>> response = new Response<List<GameLockAppraiseQueryResponse>>();
		try {
			response = httpClient.invokeHttp(serverPath + queryAllGameLockAppraiseUrl, lotteryId,
					new TypeReference<Response<List<GameLockAppraiseQueryResponse>>>() {
					});
		} catch (Exception e) {
			log.error("queryAllGameLockAppraise error", e);
			throw e;
		}
		if (lotteryId == 99109) {
			view.addObject("p3", 99109);
		} else if (lotteryId == 99110) {
			view.addObject("p3", 99110);
		}
		view.addObject("lotteryId", lotteryId == 99110 ? 99109 : lotteryId);
		view.addObject("notUser", true);
		for (GameLockAppraiseQueryResponse r : response.getBody().getResult()) {
			if (r.getCurUser() == 1l) {
				view.addObject("notUser", false);
			}
		}
		view.addObject("resultList", response.getBody().getResult());
		return view;
	}

	/**
	 * 
	* @Title: operateGameLockAppraise 
	* @Description:查询所有的变价数据
	* @return
	 */
	@RequestMapping("/operateGameLockAppraise")
	@ResponseBody
	public Object operateGameLockAppraise(@RequestParam("id") Long id, @RequestParam("status") Long status) {

		AjaxResponse resp = new AjaxResponse();
		GameLockAppriaseOperateRequest request = new GameLockAppriaseOperateRequest();
		request.setId(id);
		request.setStatus(status);
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + operateGameLockAppraiseUrl, request);
			resp.setStatus(1l);
		} catch (Exception e) {
			resp.setStatus(2l);
			resp.setMessage("操作失败！");
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryAllGameLockAppraise 
	* @Description:查询所有的变价数据
	* @return
	 */
	@RequestMapping("/deleteGameLockAppraise")
	public ModelAndView deleteGameLockAppraise(@ModelAttribute("request") GameLockAppraiseDeleteRequest request)
			throws Exception {
		log.info("deleteGameLockAppraise start");
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + deleteGameLockAppraiseUrl, request.getIds());
		} catch (Exception e) {
			log.error("deleteGameLockAppraise error", e);
			throw e;
		}
		return this.queryAllGameLockAppraise(request.getLotteryid());//"forward:/gameoa/queryAllGameLockAppraise?lotteryid=" + request.getLotteryid();
	}

	/**
	 * 
	* @Title: toAddGameLockAppraise 
	* @Description:新增变价方案
	* @return
	 */
	@RequestMapping("/toAddGameLockAppraise")
	@ResponseBody
	public ModelAndView toAddGameLockAppraise(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		log.info("toAddGameLockAppraise start");
		ModelAndView view = new ModelAndView("operations/gamelockappraise/addGameLockAppraise");
		Response<GameLockStruc> response = new Response<GameLockStruc>();
		Response<GameLockParamResponse> responseParam = new Response<GameLockParamResponse>();
		try {
			GameLockRequest request = new GameLockRequest();
			request.setGameId(lotteryId);
			if (lotteryId == 99110l) {
				request.setGameId(99109l);
			}
			response = httpClient.invokeHttp(serverPath + queryGameConfigUrl, request,
					new TypeReference<Response<GameLockStruc>>() {
					});
			responseParam = httpClient.invokeHttp(serverPath + queryGameLockParamUrl, lotteryId,
					new TypeReference<Response<GameLockParamResponse>>() {
					});
		} catch (Exception e) {
			log.error("查询封锁配置信息失败", e);
			throw e;
		}
		view.addObject("lotteryId", lotteryId);
		view.addObject("lotteryName", GameAwardNameUtil4Web.lotteryName(lotteryId));
		view.addObject("gameLockValue", lotteryId != 99110l ? response.getBody().getResult().getUpVal2() : response
				.getBody().getResult().getUpVal());
		view.addObject("downMinMoney", responseParam.getBody().getResult().getMinVal());
		return view;
	}

	/**
	 * 
	* @Title: addGameLockAppraise 
	* @Description:查询所有的变价数据
	* @return
	 */
	@RequestMapping("/addGameLockAppraise")
	public ModelAndView addGameLockAppraise(@ModelAttribute("request") GameLockAppraiseAddRequest request)
			throws Exception {
		log.info("addGameLockAppraise start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			httpClient.invokeHttpWithoutResultType(serverPath + addGameLockAppraiseUrl, request, userid, username);
		} catch (Exception e) {
			log.error("addGameLockAppraise error", e);
			throw e;
		}
		return this.queryAllGameLockAppraise(request.getGameId());//"forward:/gameoa/queryAllGameLockAppraise?lotteryid=" + request.getGameId();
	}

	@RequestMapping(value = "/viewGameLockAppraise")
	@ResponseBody
	public ModelAndView viewGameLockAppraise(@RequestParam("lotteryid") Long lotteryId, @RequestParam("id") Long id)
			throws Exception {

		log.info("query GameLockAppraise start");
		ModelAndView view = new ModelAndView("operations/gamelockappraise/viewLockAppraise");
		Response<GameLockAppraiseQueryResponse> response = new Response<GameLockAppraiseQueryResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockAppraiseUrl, id,
					new TypeReference<Response<GameLockAppraiseQueryResponse>>() {
					});
		} catch (Exception e) {
			log.error("queryGameLockAppraise error", e);
			throw e;
		}
		if(lotteryId==99109){
			view.addObject("p3", 99109);
		}else if(lotteryId==99110){
			view.addObject("p3", 99110);
		}
		view.addObject("lotteryId", lotteryId==99110?99109:lotteryId);
		view.addObject("title", response.getBody().getResult().getTitle());
		view.addObject("id", id);
		view.addObject("minVal", response.getBody().getResult().getMinVal());
		view.addObject("size", response.getBody().getResult().getChangeStrucList().size());
		view.addObject("changeStrucs", response.getBody().getResult().getChangeStrucList());
		return view;
	}

	@RequestMapping(value = "/toEditGameLockAppraise")
	@ResponseBody
	public ModelAndView toEditGameLockAppraise(@RequestParam("lotteryid") Long lotteryId, @RequestParam("id") Long id)
			throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelockappraise/updateLockAppraise");
		Response<GameLockAppraiseQueryResponse> response = new Response<GameLockAppraiseQueryResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockAppraiseUrl, id,
					new TypeReference<Response<GameLockAppraiseQueryResponse>>() {
					});
		} catch (Exception e) {
			log.error("toEditGameLockAppraise error", e);
			throw e;
		}
		view.addObject("title", response.getBody().getResult().getTitle());
		view.addObject("lotteryId", lotteryId);
		view.addObject("id", id);
		return view;
	}

	/**
	 * 
	* @Title: addGameLockAppraise 
	* @Description:查询所有的变价数据
	* @return
	 */
	@RequestMapping("/editGameLockAppraise")
	public ModelAndView editGameLockAppraise(@ModelAttribute("request") GameLockAppraiseAddRequest request)
			throws Exception {
		log.info("editGameLockAppraise start");
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameLockAppraiseUrl, request, userid, username);
		} catch (Exception e) {
			log.error("editGameLockAppraise error", e);
			throw e;
		}
		return this.queryAllGameLockAppraise(request.getGameId());
	}

	@RequestMapping(value = "/getGameLockAppraiseChangeStruc")
	@ResponseBody
	public Object getGameLockAppraiseChangeStruc(@RequestParam("id") Long id) {
		Response<GameLockAppraiseQueryResponse> response = new Response<GameLockAppraiseQueryResponse>();
		List<GameLockAppraiseChangeStruc> list = new ArrayList<GameLockAppraiseChangeStruc>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockAppraiseUrl, id,
					new TypeReference<Response<GameLockAppraiseQueryResponse>>() {
					});
			list = response.getBody().getResult().getChangeStrucList();
		} catch (Exception e) {
			log.error("getGameLockAppraiseChangeStruc error", e);
		}

		Object[] chartResult = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object[] sub = new Object[2];
			sub[0] = list.get(i).getFrom() / 10000;
			sub[1] = list.get(i).getTo() / 10000;
			Object rate = list.get(i).getRate();

			Object[] parent = new Object[2];
			parent[0] = sub;
			parent[1] = rate;
			chartResult[i] = parent;
		}
		return chartResult;
	}
}
