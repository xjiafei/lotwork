package com.winterframework.firefrog.acl.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.service.AclUserService;
import com.winterframework.firefrog.acl.web.dto.AclUserLoginRequest;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.firefrog.acl.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.user.exception.LoginServiceException;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("aclLoginController")
@RequestMapping("/user")
public class AclLoginController {
	private static final Logger logger = LoggerFactory.getLogger(AclUserController.class);

	@Resource(name = "aclUserServiceImpl")
	private AclUserService aclUserService;
	@Resource(name = "RedisClient")
	private RedisClient redis;
	private static IPSeeker ipseek = IPSeeker.getInstance();
	/**
	 * 管理人员登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/adminLogin")
	@ResponseBody
	public Response<AclUserStruc> adminLogin(@RequestBody @ValidRequestBody Request<AclUserLoginRequest> request)
			throws Exception {
		Response<AclUserStruc> response = new Response<AclUserStruc>(request);
		AclUserLoginRequest loginReq = request.getBody().getParam();
		try {
			AclUser user = aclUserService.adminLogin(loginReq.getAccount(), loginReq.getPasswd());
			AclUserStruc struc=DTOConverter.transAclUser2Dto(user);
			response.setResult(struc);
				try{
					if ((user.getLastIp()!= null)) {
						struc.setLastArea(ipseek.getAddress(IPConverter.longToIp(user.getLastIp())));
						struc.setLastLoginDate(user.getGmtModified().getTime());
					}
					user.setLastIp(loginReq.getLoginIp());
					aclUserService.modifyUser(user);
					
				}catch(Exception e){
					e.printStackTrace();
				}

		} catch (LoginServiceException e) {
			logger.info("处理登录错误异常", e.getMessage());
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			logger.error("adminLogin error.", e);
			throw e;
		}

		return response;
	}

}
