/**   
* @Title: GameLockParamWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-12 下午1:56:29 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

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
import com.winterframework.firefrog.game.web.dto.GameLockParamRequest;
import com.winterframework.firefrog.game.web.dto.GameLockParamResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * 
* @ClassName: GameLockParamWebController 
* @Description:变价参数设置controller
* @author floy
* @date 2013-9-23 下午3:52:52 
*
 */
@Controller("gameLockParamWebController")
@RequestMapping(value = "/gameoa")
@SuppressWarnings("unchecked")
public class GameLockParamWebController {
	private Logger log = LoggerFactory.getLogger(GameLockParamWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryGameLockParam")
	private String queryGameLockParamUrl;

	@PropertyConfig(value = "url.game.updateGameLockParam")
	private String updateGameLockParamUrl;

	/**
	 * 
	* @Title: toGameLockParam
	* @Description:封锁标签主页，查询封锁数据。
	* @return
	 */
	@RequestMapping("/toGameLockParam")
	@ResponseBody
	public ModelAndView toGameLockParam(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelockappraise/viewGameLockParam");
		Response<GameLockParamResponse> response = new Response<GameLockParamResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockParamUrl, lotteryId,
					new TypeReference<Response<GameLockParamResponse>>() {
					});
		} catch (Exception e) {
			log.error("query gameLockParam error", e);
		}
		if (lotteryId == 99109) {
			view.addObject("p3", 99109);
		} else if (lotteryId == 99110) {
			view.addObject("p3", 99110);
		}else{
			view.addObject("p3", lotteryId);
		}
		view.addObject("lotteryId", lotteryId == 99110 ? 99109 : lotteryId);
		view.addObject("gameLockParam", response.getBody().getResult());
		return view;
	}

	/**
	 * 
	* @Title: publishGameLock
	* @Description:封锁标签主页，查询封锁数据。
	* @return
	 */
	@RequestMapping("/publishGameLock")
	public ModelAndView publishGameLock(@ModelAttribute("request") GameLockParamRequest request) throws Exception {
		request.setStatus(4l);
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameLockParamUrl, request);
		} catch (Exception e) {
			log.error("publishGameLock error", e);
			throw e;
		}
		return this.toGameLockParam(request.getGameId());//"forward:/gameoa/toGameLockParam?lotteryid=" + request.getGameId();
	}

	/**
	 * 
	* @Title: toAuditGameLockParam
	* @Description:封锁标签主页，查询封锁数据。
	* @return
	 */
	@RequestMapping("/toAuditGameLockParam")
	@ResponseBody
	public ModelAndView toAuditGameLockParam(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelockappraise/auditGameLockParam");
		Response<GameLockParamResponse> response = new Response<GameLockParamResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockParamUrl, lotteryId,
					new TypeReference<Response<GameLockParamResponse>>() {
					});
		} catch (Exception e) {
			log.error("query gameLockParam error", e);
			throw e;
		}
		view.addObject("lotteryId", lotteryId);
		view.addObject("gameLockParam", response.getBody().getResult());
		return view;
	}

	/**
	 * 
	* @Title: auditGameLockParam
	* @Description:审核变价。
	* @return
	 */
	@RequestMapping("/auditGameLockParam")
	public ModelAndView auditGameLockParam(@ModelAttribute("request") GameLockParamRequest request) throws Exception {
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameLockParamUrl, request, userid, username);
		} catch (Exception e) {
			log.error("auditGameLockParam error", e);
			throw e;
		}
		return this.toGameLockParam(request.getGameId());
	}

	/**
	 * 
	* @Title: toAuditGameLockParam
	* @Description:封锁标签主页，查询封锁数据。
	* @return
	 */
	@RequestMapping("/toEditGameLockParam")
	@ResponseBody
	public ModelAndView toEditGameLockParam(@RequestParam("lotteryid") Long lotteryId) throws Exception {
		ModelAndView view = new ModelAndView("operations/gamelockappraise/editGameLockParam");
		Response<GameLockParamResponse> response = new Response<GameLockParamResponse>();
		try {
			response = httpClient.invokeHttp(serverPath + queryGameLockParamUrl, lotteryId,
					new TypeReference<Response<GameLockParamResponse>>() {
					});
		} catch (Exception e) {
			log.error("query gameLockParam error", e);
			throw e;
		}
		view.addObject("lotteryId", lotteryId);
		view.addObject("gameLockParam", response.getBody().getResult());
		return view;
	}

	/**
	 * 
	* @Title: editGameLockParam
	* @Description:修改变价。
	* @return
	 */
	@RequestMapping("/editGameLockParam")
	public ModelAndView editGameLockParam(@ModelAttribute("request") GameLockParamRequest request) throws Exception {
		request.setStatus(1l);
		try {
			Long userid = RequestContext.getCurrUser().getId();
			String username = RequestContext.getCurrUser().getUserName();
			httpClient.invokeHttpWithoutResultType(serverPath + updateGameLockParamUrl, request, userid, username);
		} catch (Exception e) {
			log.error("editGameLockParam error", e);
			throw e;
		}
		return this.toGameLockParam(request.getGameId());
	}

}
