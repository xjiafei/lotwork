package com.winterframework.firefrog.acl.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.service.AclUserService;
import com.winterframework.firefrog.acl.web.dto.AclUserDelRequest;
import com.winterframework.firefrog.acl.web.dto.AclUserPwdRequest;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.firefrog.acl.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: AclUserController 
* @Description: 管理用户控制类
* @author Tod
* @date 2013-10-15 下午5:03:21 
*
 */
@Controller("aclUserController")
@RequestMapping("/aclAdmin")
public class AclUserController {
	private static final Logger logger = LoggerFactory.getLogger(AclUserController.class);

	@Resource(name = "aclUserServiceImpl")
	private AclUserService aclUserService;

	/**
	 * 
	* @Title: deleteUser 
	* @Description: 删除用户
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteUser")
	public @ResponseBody
	Response<Object> deleteUser(@RequestBody @ValidRequestBody Request<AclUserDelRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			aclUserService.deleteUser(request.getBody().getParam().getId());
		} catch (Exception e) {
			logger.error("deleteUser error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: createUser 
	* @Description: 创建用户
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createUser")
	public @ResponseBody
	Response<Object> createUser(@RequestBody @ValidRequestBody Request<AclUserStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AclUserStruc dto = request.getBody().getParam();
		AclUser user = DTOConverter.transDto2AclUser(dto);
		user.setPasswd(DigestUtils.md5Hex(user.getPasswd()));
		try {
			aclUserService.saveUser(user);
		} catch (Exception e) {
			logger.error("createUser error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: modifyUser 
	* @Description: 编辑用户
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyUser")
	public @ResponseBody
	Response<Object> modifyUser(@RequestBody @ValidRequestBody Request<AclUserStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AclUserStruc dto = request.getBody().getParam();
		AclUser user = DTOConverter.transDto2AclUser(dto);
		if (user.getPasswd() != null && !user.getPasswd().equals("")) {
			user.setPasswd(DigestUtils.md5Hex(user.getPasswd()));
		}
		try {
			aclUserService.modifyUser(user);
		} catch (Exception e) {
			logger.error("modifyUser error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/modifyUserPwd")
	public @ResponseBody
	Response<Object> modifyUserPwd(@RequestBody @ValidRequestBody Request<AclUserPwdRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		AclUserPwdRequest dto = request.getBody().getParam();
		try {
			aclUserService.modifyUserPwd(dto.getId(), dto.getOldPwd(), dto.getNewPwd());
		} catch (Exception e) {
			logger.error("modifyUserPwd error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询用户列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryUserList")
	public @ResponseBody
	Response<List<AclUserStruc>> queryList(@RequestBody Request<AclUserStruc> request) throws Exception {
		Response<List<AclUserStruc>> response = new Response<List<AclUserStruc>>(request);
		PageRequest<AclUserStruc> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
        pageRequest.getSearchDo().setId(aclUserService.getByUserId(response.getHead().getUserId()).getGroup().getId());
		pageRequest.setSortColumns("GMT_CREATED DESC");
		try {
			Page<AclUser> page = aclUserService.queryList(pageRequest);
			List<AclUserStruc> list = new ArrayList<AclUserStruc>();
			for (AclUser user : page.getResult()) {
				list.add(DTOConverter.transAclUser2Dto(user));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryList error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/queryUserById")
	public @ResponseBody
	Response<AclUserStruc> queryById(@RequestBody Request<AclUserDelRequest> request) throws Exception {
		Response<AclUserStruc> response = new Response<AclUserStruc>(request);
		try {
			AclUser user = aclUserService.getByUserId(request.getBody().getParam().getId());
			response.setResult(DTOConverter.transAclUser2Dto(user));
		} catch (Exception e) {
			logger.error("queryById error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/queryByBindPwd")
	public @ResponseBody
	Response<AclUserStruc> queryByBindPwd(@RequestBody Request<String> bindPwd) throws Exception {
		Response<AclUserStruc> response = new Response<AclUserStruc>(bindPwd);
		try {
			AclUser user = aclUserService.getUserByBindPwd(bindPwd.getBody().getParam());
			response.setResult(DTOConverter.transAclUser2Dto(user));
		} catch (Exception e) {
			logger.error("queryById error.", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/queryUserByBindCard")
	public @ResponseBody
	Response<List<AclUserStruc>> queryUserByBindCard(@RequestBody Request<String> bindPwd) throws Exception {
		Response<List<AclUserStruc>> response = new Response<List<AclUserStruc>>(bindPwd);
		List<AclUserStruc> strucs = new ArrayList<AclUserStruc>();
		try {
			List<AclUser> users = aclUserService.queryUserByBindCard(bindPwd.getBody().getParam());
			for(AclUser user:users){
				strucs.add(DTOConverter.transAclUser2Dto(user));
			}
			response.setResult(strucs);
		} catch (Exception e) {
			logger.error("queryById error.", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/checkAccountUnique")
	public @ResponseBody
	Response<Boolean> checkAccountUnique(@RequestBody Request<String> account) throws Exception {
		Response<Boolean> response = new Response<Boolean>(account);
		try {
			List<AclUser> user = aclUserService.getUserByAccount(account.getBody().getParam());
			if (user != null && user.size() > 0) {
				response.setResult(false);
			} else {
				response.setResult(true);
			}

		} catch (Exception e) {
			logger.error("queryById error.", e);
			response.setResult(false);
			throw e;
		}
		return response;
	}
}
