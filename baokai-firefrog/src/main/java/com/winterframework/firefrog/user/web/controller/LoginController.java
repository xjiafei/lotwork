package com.winterframework.firefrog.user.web.controller;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.event.FireFrogEventPublisher;
import com.winterframework.firefrog.config.web.controller.dto.RegisterLoginConfigDto;
import com.winterframework.firefrog.game.service.IGameBettypeStatusService;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.event.LoginEvent;
import com.winterframework.firefrog.user.exception.LoginServiceException;
import com.winterframework.firefrog.user.service.ILoginService;
import com.winterframework.firefrog.user.service.UserUrlManager;
import com.winterframework.firefrog.user.web.controller.game.GameGroupReq;
import com.winterframework.firefrog.user.web.controller.game.GameGroups;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.UserLoginRequestDTO;
import com.winterframework.firefrog.user.web.dto.UserStrucResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("loginController")
@RequestMapping(value = "/user")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "loginServiceImpl")
	private ILoginService loginService;
	
	@Resource(name = "fireFrogEventPublisher")
	FireFrogEventPublisher fireFrogEventPublisher;
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	@Resource(name = "gameBettypeStatusServiceImpl")
	private IGameBettypeStatusService gameBettypeStatusService;
							 
	
	@Autowired
	private UserUrlManager urlManager;
	


	/**
	 * 客户登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/login")
	@ResponseBody
	public Response<UserStrucResponse> login(@RequestBody @ValidRequestBody Request<UserLoginRequestDTO> request,HttpServletRequest request2)
			throws Exception {
		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);
		try {
			
			User user = loginService.login(request.getBody().getParam().getAccount(), request.getBody().getParam()
					.getPasswd(), request.getBody().getParam().getLoginIp() ,  request.getBody().getParam().getUserAgent() );
			
									
			//获取该客户对应的资金信息
			LoginEvent loginEvent = new LoginEvent(user.getId());
			fireFrogEventPublisher.publish(loginEvent);
			user.setFund(loginEvent.getUserFund());

			log.debug("生成userStruc");
			UserStrucResponse res = ControllerDTOConverter.user2UserStrucResponse(user);
			try {
				String str = configService.getConfigValueByKey("global","login");
				RegisterLoginConfigDto ddto=JsonMapper.nonEmptyMapper().fromJson(str, RegisterLoginConfigDto.class);
				res.setLoginCtrl(ddto);
				try{
				Request<GameGroupReq> requestd=new Request<GameGroupReq>();
				requestd.setHead(new RequestHeader());
				requestd.setBody(new com.winterframework.modules.web.jsonresult.RequestBody<GameGroupReq>());
				GameGroupReq req=new GameGroupReq();
				req.setUserId(user.getId());
				//如果是总代，返回0，否则返回1
				req.setType(user.getUserLevel().equals(Integer.valueOf(0))?0L:1l);
				req.setAwardType(0L);
				requestd.getBody().setParam(req);
				Response<GameGroups> responses=urlManager.getGameGroup(requestd);
				if(responses!=null){
					Long isAllzero=0l;
					for(com.winterframework.firefrog.user.web.controller.game.GameGroup g:responses.getBody().getResult().getUserAwardListStruc()){
						if(g.getDirectRet()>0){
							isAllzero=1l;
							break;
						}
						
					}
					res.setIsAllZero(isAllzero);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			} catch (Exception e) {
				log.error("getConfigValueByKey error.", e);
				throw e;
			}
			response.setResult(res);
			log.debug("生成userStruc结束");
		} catch (LoginServiceException e) {
			log.error("处理登录错误异常", e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("login error.", e);
			throw e;
		}

		return response;
	}
	
	
	
}
