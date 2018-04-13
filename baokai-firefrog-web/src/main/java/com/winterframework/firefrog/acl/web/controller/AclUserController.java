package com.winterframework.firefrog.acl.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.acl.web.dto.AclGroupStruc;
import com.winterframework.firefrog.acl.web.dto.AclOperateLogStruc;
import com.winterframework.firefrog.acl.web.dto.AclUserDelRequest;
import com.winterframework.firefrog.acl.web.dto.AclUserPwdRequest;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.firefrog.session.interceptor.vo.AclUserStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("aclUserController")
@RequestMapping(value = "/aclAdmin")
public class AclUserController {

	@PropertyConfig(value = "url.front.domain")
	private String selfUrl;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.acl.modifyUserPwd")
	private String modifyUserPwd;

	@PropertyConfig(value = "url.acl.createUser")
	private String createUser;

	@PropertyConfig(value = "url.acl.modifyUser")
	private String modifyUser;

	@PropertyConfig(value = "url.acl.deleteUser")
	private String deleteUser;

	@PropertyConfig(value = "url.acl.queryAclGroup")
	private String queryAclGroupUrl;

	@PropertyConfig(value = "url.acl.queryUser")
	private String queryUser;

	@PropertyConfig(value = "url.acl.queryUserById")
	private String queryUserById;

	@PropertyConfig(value = "url.acl.queryUserByBindPwd")
	private String queryUserByBindPwd;
	
	@PropertyConfig(value = "url.acl.queryUserByBindCard")
	private String queryUserByBindCard;
	

	@PropertyConfig(value = "url.acl.checkAccountUnique")
	private String checkAccountUnique;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.acl.createLog")
	private String createLog;

	@RequestMapping(value = "/goModifyPwd")
	public ModelAndView goModifyPwd() throws Exception {
		ModelAndView mav = new ModelAndView("acl/userModifyPwd");
		addSucceedAclLog("进入修改密码界面", "/goModifyPwd");
		mav.addObject("acl_no", "goModifyPwd");
		return mav;
	}

	@RequestMapping(value = "/goModify")
	public ModelAndView goModify(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("acl/userModify");
		Pager pager = new Pager();
		pager.setStartNo(1);
		pager.setEndNo(5000000);
		Response<List<AclGroupStruc>> response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, 1L,
				null, new TypeReference<Response<List<AclGroupStruc>>>() {
				});
		Long groupId = ((AclUserStruc) RequestContext.getCurrUser()).getGroupId();
		mav.addObject("groups", AclGroupTools.toOneGroup(response.getBody().getResult()).get(groupId));

		AclUserDelRequest req = new AclUserDelRequest();
		req.setId(id);
		Response<AclUserStruc> userResp = httpClient.invokeHttp(serverPath + queryUserById, req,
				new TypeReference<Response<AclUserStruc>>() {
				});

		mav.addObject("user", userResp.getBody().getResult());
		addSucceedAclLog("进入修改界面", "/goModify");
		return mav;
	}

	@RequestMapping(value = { "/goUserManager", "/" })
	public ModelAndView goUserManager() throws Exception {
		AclUserStruc search = new AclUserStruc();
		search.setGroupId(-1L);
		search.setStatus(-1L);
		addSucceedAclLog("进入用户管理界面", "/goUserManager");
		return searchUser(search, 1L);
	}

	@RequestMapping(value = "/modifyPwd")
	@ResponseBody
	public Object modifyPwd(AclUserPwdRequest pwdReq) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		AclUserDelRequest req = new AclUserDelRequest();
		req.setId(RequestContext.getCurrUser().getId());
		pwdReq.setId(RequestContext.getCurrUser().getId());
		Response<AclUserStruc> userResp = httpClient.invokeHttp(serverPath + queryUserById, req,
				new TypeReference<Response<AclUserStruc>>() {
				});
		String passwd = org.apache.commons.codec.digest.DigestUtils.md5Hex(pwdReq.getOldPwd());
		if (userResp.getBody().getResult().getPasswd().equals(passwd)) {
			response.setStatus(1L);
			pwdReq.setOldPwd(passwd);
			pwdReq.setNewPwd(org.apache.commons.codec.digest.DigestUtils.md5Hex(pwdReq.getNewPwd()));
			httpClient.invokeHttpWithoutResultType(urlPath + modifyUserPwd, pwdReq);

			//addSucceedAclLog("修改密码", "/modifyPwd");
		} else {
			response.setStatus(0L);
			response.setMessage("原密码错误");
		}
		return response;
	}

	@RequestMapping(value = "/checkOldPwd")
	@ResponseBody
	public Object checkOldPwd(AclUserPwdRequest pwdReq) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		AclUserDelRequest req = new AclUserDelRequest();
		req.setId(RequestContext.getCurrUser().getId());
		pwdReq.setId(RequestContext.getCurrUser().getId());
		Response<AclUserStruc> userResp = httpClient.invokeHttp(serverPath + queryUserById, req,
				new TypeReference<Response<AclUserStruc>>() {
				});
		String passwd = org.apache.commons.codec.digest.DigestUtils.md5Hex(pwdReq.getOldPwd());
		if (userResp.getBody().getResult().getPasswd().equals(passwd)) {
			response.setStatus(1L);
		} else {
			response.setStatus(0L);
			response.setMessage("原密码错误");
		}
		return response;
	}

	@RequestMapping(value = "/modifyUser")
	@ResponseBody
	public Object modifyUser(AclUserStruc userStruc) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		response.setStatus(1L);
		response.setMessage("修改成功");
		try {
			userStruc.setModifieder(RequestContext.getCurrUser().getUserName());
			httpClient.invokeHttpWithoutResultType(urlPath + modifyUser, userStruc);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		//addSucceedAclLog("修改用户", "/modifyUser");
		return response;
	}

	@RequestMapping(value = "/deleteUser")
	public ModelAndView deleteUser(Long id) throws Exception {
		AclUserDelRequest delReq = new AclUserDelRequest();
		delReq.setId(id);
		httpClient.invokeHttpWithoutResultType(urlPath + deleteUser, delReq);
		//addSucceedAclLog("删除用户", "/deleteUser");
		return this.goUserManager();
	}

	@RequestMapping(value = "/lockUser")
	public ModelAndView lockUser(Long id) throws Exception {
		AclUserStruc userStruc = new AclUserStruc();
		userStruc.setId(id);
		userStruc.setStatus(1L);
		userStruc.setModifieder(RequestContext.getCurrUser().getUserName());
		httpClient.invokeHttpWithoutResultType(urlPath + modifyUser, userStruc);
		//addSucceedAclLog("锁定用户", "/lockUser");
		return this.goUserManager();
	}

	@RequestMapping(value = "/unlockUser")
	public ModelAndView unlockUser(Long id) throws Exception {
		AclUserStruc userStruc = new AclUserStruc();
		userStruc.setId(id);
		userStruc.setStatus(0L);
		userStruc.setModifieder(RequestContext.getCurrUser().getUserName());
		httpClient.invokeHttpWithoutResultType(urlPath + modifyUser, userStruc);
		//addSucceedAclLog("解锁用户", "/unlockUser");
		return this.goUserManager();
	}

	@RequestMapping(value = "/createUser")
	@ResponseBody
	public OperateStatusResponse createUser(AclUserStruc userStruc) throws Exception {
		OperateStatusResponse mav = new OperateStatusResponse();
		userStruc.setCreateder(RequestContext.getCurrUser().getUserName());
		httpClient.invokeHttpWithoutResultType(urlPath + createUser, userStruc);
		//addSucceedAclLog("创建用户", "/createUser");
		mav.setStatus(1);
		return mav;
	}

	@RequestMapping(value = "/searchUser")
	public ModelAndView searchUser(AclUserStruc search, Long pageNo) throws Exception {
		ModelAndView mav = new ModelAndView("acl/userManager");
		Pager pager = new Pager();
		this.pageSize=200;
		pageNo = pageNo == null ? 1L : pageNo;
		int startNo = (int) ((pageNo - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<List<AclGroupStruc>> response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, 1L,
				null, new TypeReference<Response<List<AclGroupStruc>>>() {
				});
		Long groupId = ((AclUserStruc) RequestContext.getCurrUser()).getGroupId();
		AclGroupStruc toOneGroup = AclGroupTools.toOneGroup(response.getBody().getResult()).get(groupId);
		mav.addObject("groups", toOneGroup);
		if (search.getGroupId() == -1L || search.getGroupId() == null) {
			search.setGroupId(toOneGroup != null ? toOneGroup.getId()
					: null);
		}
		if (search.getStatus() == null || search.getStatus() == -1L) {
			search.setStatus(null);
		}
		Response<List<AclUserStruc>> resp = httpClient.invokeHttp(urlPath + queryUser, search, pager, RequestContext
				.getCurrUser().getId(), RequestContext.getCurrUser().getUserName(),
				new TypeReference<Response<List<AclUserStruc>>>() {
				});
		ResultPager resultPage = resp.getBody().getPager();
		int pageNoResult = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNoResult, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", resp.getBody().getResult());
		mav.addObject("search", search);

		mav.addObject("acl_no", "goUserManager");
		return mav;
	}

	@RequestMapping(value = "/goCreateUser")
	public ModelAndView goCreateUser() throws Exception {
		ModelAndView mav = new ModelAndView("acl/userCreate");
		addSucceedAclLog("进入创建用户界面", "/goCreateUser");
		Pager pager = new Pager();
		pager.setStartNo(1);
		pager.setEndNo(10000);
		Response<List<AclGroupStruc>> response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, 1L,
				null, new TypeReference<Response<List<AclGroupStruc>>>() {
				});

		Long groupId = ((AclUserStruc) RequestContext.getCurrUser()).getGroupId();
		mav.addObject("groups", AclGroupTools.toOneGroup(response.getBody().getResult()).get(groupId));
		mav.addObject("acl_no", "goCreateUser");
		return mav;
	}

	@RequestMapping(value = "/checkBindPwd")
	@ResponseBody
	public Object checkBindPwd(String bindPwd, Long id) throws Exception {
		Response<List<AclUserStruc>> userResp = httpClient.invokeHttp(serverPath + queryUserByBindCard, bindPwd,
				new TypeReference<Response<List<AclUserStruc>>>() {
				});
		OperateStatusResponse response = new OperateStatusResponse();
		List<AclUserStruc> strucs = userResp.getBody().getResult();
		if(strucs.isEmpty()){
			response.setStatus(1L);
		}else if(strucs.size()>1){
			response.setStatus(0L);
		}else if(strucs.get(0).getId().equals(id)){
			response.setStatus(1L);
		}else{
			response.setStatus(0L);
		}
		/*if (userResp.getBody() == null || userResp.getBody().getResult() == null) {
			response.setStatus(1L);
		} else if (userResp.getBody().getResult() != null && userResp.getBody().getResult().getId() == id) {
			response.setStatus(1L);
		} else {
			response.setStatus(0L);
		}*/
		return response;
	}

	@RequestMapping(value = "/checkAccountUnique")
	@ResponseBody
	public Object checkAccountUnique(String account) throws Exception {
		Response<Boolean> userResp = httpClient.invokeHttp(serverPath + checkAccountUnique, account,
				new TypeReference<Response<Boolean>>() {
				});
		OperateStatusResponse response = new OperateStatusResponse();
		if (userResp.getBody() == null) {
			response.setStatus(1L);
		} else if (userResp.getBody().getResult()) {
			response.setStatus(1L);
		} else if (!userResp.getBody().getResult()) {
			response.setStatus(0L);
			response.setMessage("该用户名已经存在，请更换其它用户名");
		}
		return response;
	}

	private void addSucceedAclLog(String action, String url) throws Exception {
		AclOperateLogStruc o = new AclOperateLogStruc();
		o.setAccount(RequestContext.getCurrUser().getUserName());
		o.setAction(action);
		o.setIp(RequestContext.get().ip());
		o.setUrl(selfUrl + "/aclAdmin" + url);
		o.setCreateTime(new Date());
		o.setDetail(1L);
		httpClient.invokeHttp(serverPath + createLog, o, new TypeReference<Response<Object>>() {
		});
	}
}
