/**   
* @Title: GameUserAwardGroupController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: 用户奖金组相关操作Controller 
* @author Denny  
* @date 2013-9-17 下午4:57:24 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.service.IGameUserAwardService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameUserAwardRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
 
/**
 * 用户奖金相关操作Controller 
 * @ClassName
 * @Description
 * @author ibm
 * 2014年11月18日
 */
@Controller("gameUserAwardController")
@RequestMapping("/game") 
public class GameUserAwardController {
	private Logger log = LoggerFactory.getLogger(GameUserAwardController.class);

	@Resource(name = "gameUserAwardServiceImpl")
	private IGameUserAwardService gameUserAwardService; 
  
	/**
	 * 修改用户奖金
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyGameUserAward")
	@ResponseBody
	public Object modifyGameUserAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardRequest> request) throws Exception {

		log.info("开始修改用户奖金......");
		Response response = new Response(request); 
		Long userId=request.getBody().getParam().getUserId();
		if(userId==null){
			throw new Exception("parameter userId cannot be null.");
		}
		List<GameUserAwardStruc> userAwardStrucList = request.getBody().getParam().getUserAwardStrucList();

		List<GameUserAward> userAwardList = new ArrayList<GameUserAward>();
		GameUserAward userAward =null; 
		if(userAwardStrucList!=null && userAwardStrucList.size()>0){
			for (GameUserAwardStruc userAwardStruc : userAwardStrucList) {
				userAward = DTOConvert.userAwardStruc2UserAward(userAwardStruc);
				userAward.setUserId(userId);
				userAwardList.add(userAward);
			}
			try {
				this.gameUserAwardService.batchModifyGameUserAward(userAwardList);
				response.getHead().setStatus(1L);	//1：success
			} catch (Exception e) {
				log.error("修改用户奖金异常 ", e); 
				throw e;
			} 
		}
		log.info("修改用户奖金完成。");
		return response;
	} 

}
