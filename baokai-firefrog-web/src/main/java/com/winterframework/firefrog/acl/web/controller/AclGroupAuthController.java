/**   
* @Title: AclGroupAuthController.java 
* @Package com.winterframework.firefrog.acl.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-15 下午2:55:46 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.acl.web.dto.AclGroupBindAuthRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupDeleteRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupIdRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupStruc;
import com.winterframework.firefrog.acl.web.dto.AclOperateLogStruc;
import com.winterframework.firefrog.acl.web.dto.AclStruc;
import com.winterframework.firefrog.acl.web.dto.AclTreeResponse;
import com.winterframework.firefrog.acl.web.dto.AclTreeStruc;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.firefrog.session.interceptor.vo.AclUserStruc;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

/** 
* @ClassName: AclGroupAuthController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-15 下午2:55:46 
*  
*/
@Controller("aclGroupAuthController")
@RequestMapping(value = "/aclAdmin")
public class AclGroupAuthController {

	@PropertyConfig(value = "url.front.domain")
	private String selfUrl;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.acl.createLog")
	private String createLog;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.acl.queryAclGroup")
	private String queryAclGroupUrl;

	@PropertyConfig(value = "url.acl.querySubAclGroup")
	private String querySubAclGroupUrl;

	@PropertyConfig(value = "url.acl.queryAclByGroup")
	private String queryAclByGroupUrl;

	@PropertyConfig(value = "url.acl.copyAclGroup")
	private String copyAclGroupUrl;

	@PropertyConfig(value = "url.acl.updateAclGroup")
	private String updateAclGroupUrl;

	@PropertyConfig(value = "url.acl.bindAuthForGroup")
	private String bindAuthForGroupUrl;

	@PropertyConfig(value = "url.acl.querySubGroups")
	private String querySubGroupsUrl;

	@PropertyConfig(value = "url.acl.saveAclGroup")
	private String saveAclGroupUrl;

	@PropertyConfig(value = "url.acl.deleteAclGroup")
	private String deleteAclGroupUrl;

	@PropertyConfig(value = "url.acl.checkDelete")
	private String checkDeleteUrl;

	@PropertyConfig(value = "url.acl.checkByName")
	private String checkByNameUrl;

	@PropertyConfig(value = "url.acl.queryAclGroupByUser")
	private String queryAclGroupByUser;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	private static final Logger logger = LoggerFactory.getLogger(AclGroupAuthController.class);

	@RequestMapping(value = "/queryAclGroup")
	public String queryAclGroup(@ModelAttribute("page") PageRequest<Long> page, @RequestParam("userId") Long userId,
			Model model) throws Exception {
		logger.debug("queryAclGroup start");
		
		Response<List<AclGroupStruc>> response = null;
		int startNo = page.getPageNo() == 0 ? 1 : (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endNo = page.getPageSize() - 1 + startNo;
		try {
			Pager pager = new Pager();
			pager.setStartNo(startNo);
			pager.setEndNo(endNo+100);
			response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, userId, null,
					new TypeReference<Response<List<AclGroupStruc>>>() {
					});
		} catch (Exception e) {
			logger.error("query AclGroup error", e);
			throw e;
		}
		ResultPager resultPage = response.getBody().getPager();
		
		Long groupId = ((AclUserStruc) RequestContext.getCurrUser()).getGroupId();
		AclGroupStruc aclGroup=AclGroupTools.toOneGroup(response.getBody().getResult()).get(groupId);
		List<AclGroupStruc> sons=new ArrayList<AclGroupStruc>();
		AclGroupTools.getSun(aclGroup, sons);
		model.addAttribute("groups", sons);
		Page<Object> pageObject = new Page<Object>(page.getPageNo(), page.getPageSize(), sons.size());
		page.setPageSize(sons.size());
		page.setPageNo(1);
        pageObject.setPageSize(100);
		model.addAttribute("page", pageObject);
		model.addAttribute("cate2Name", "权限组管理");
		addSucceedAclLog("进入查看权限组界面","/queryAclGroup");
		return "/acl/aclGroupAuthManage";
	}

	@RequestMapping(value = "/queryAclByGroup")
	public String queryAclByGroup(@RequestParam("id") Long id, @RequestParam("pid") Long pid,
			@RequestParam("name") String name, Model model) throws Exception {
		logger.debug("queryAcl By Group start");
		model.addAttribute("id", id);
		model.addAttribute("pid", pid);
		model.addAttribute("name", new java.net.URLDecoder().decode(name, "utf-8"));
		//addSucceedAclLog("查看组权限","/queryAclByGroup");
		return "/acl/showGroupAuth";
	}

	@RequestMapping(value = "buildAclTree")
	@ResponseBody
	public Object buildAclTree(@RequestParam("id") Long id, @RequestParam("pid") Long pid) throws Exception {
		logger.debug("query acl tree start");
		List<AclStruc> aclStrucs = new ArrayList<AclStruc>();
		List<AclStruc> pAclStrucs = new ArrayList<AclStruc>();
		AclTreeResponse resp = new AclTreeResponse();
		List<AclTreeStruc> aclTreeStrucs = new ArrayList<AclTreeStruc>();
		List<AclTreeStruc> pAclTreeStrucs = new ArrayList<AclTreeStruc>();
		try {
			AclGroupIdRequest groupIdRequest = new AclGroupIdRequest();
			groupIdRequest.setAclGId(id);
			aclStrucs = (List<AclStruc>) httpClient
					.invokeHttp(serverPath + queryAclByGroupUrl, groupIdRequest,
							new TypeReference<Response<List<AclStruc>>>() {
							}).getBody().getResult();
			groupIdRequest.setAclGId(pid);
			pAclStrucs = (List<AclStruc>) httpClient
					.invokeHttp(serverPath + queryAclByGroupUrl, groupIdRequest,
							new TypeReference<Response<List<AclStruc>>>() {
							}).getBody().getResult();
			for (AclStruc aclStruc : aclStrucs) {
				handerAclTreeStruc(aclStruc, aclTreeStrucs);
			}
			for (AclStruc aclStruc : pAclStrucs) {
				handerAclTreeStruc(aclStruc, pAclTreeStrucs);
			}
			if(aclTreeStrucs!=null)
			for (AclTreeStruc aclTreeStruc : aclTreeStrucs) {
				for (AclTreeStruc pAclTreeStruc : pAclTreeStrucs) {
					if (aclTreeStruc.getId().longValue() == pAclTreeStruc.getId().longValue()) {
						pAclTreeStruc.setIsChecked(1l);
						continue;
					}
				}

			}
			resp.setData(aclTreeStrucs);
			resp.setPdata(pAclTreeStrucs);
			//addSucceedAclLog("创建权限树","/buildAclTree");
		} catch (Exception e) {
			logger.error("query acl tree error", e);
			resp.setIsSuccess(0l);
			resp.setMsg(e.getMessage());
		}
		return resp;
	}

	private void handerAclTreeStruc(AclStruc aclStruc, List<AclTreeStruc> aclTreeStrucs) {
		aclTreeStrucs.add(aclStruc2AclTreeStruc(aclStruc));
		List<AclStruc> subList = aclStruc.getSubAcls();
		if (subList == null || subList.isEmpty()) {
			return;
		} else {
			for (AclStruc sub : subList) {
				handerAclTreeStruc(sub, aclTreeStrucs);
			}
		}
	}

	private AclTreeStruc aclStruc2AclTreeStruc(AclStruc aclStruc) {
		AclTreeStruc aclTreeStruc = new AclTreeStruc();
		aclTreeStruc.setId(aclStruc.getId());
		aclTreeStruc.setPid(aclStruc.getPid() == null ? 0 : aclStruc.getPid());
		aclTreeStruc.setText(aclStruc.getLabel());
		aclTreeStruc.setType(aclStruc.getTypes());
		aclTreeStruc.setIsChecked(0l);
		aclTreeStruc.setValue(aclStruc.getId() + "");
		return aclTreeStruc;
	}

	@RequestMapping(value = "/toCopyGroup")
	public String toCopyGroup(@RequestParam("id") Long id, @RequestParam("name") String name, Model model)
			throws Exception {
		model.addAttribute("id", id);
		model.addAttribute("name", new java.net.URLDecoder().decode(name, "utf-8"));
		return "/acl/copyGroup";
	}

	@RequestMapping(value = "/copyGroup")
	public String copyGroup(@Valid @ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc, Errors errors,
			@RequestParam("userId") Long userId, Model model) throws Exception {
		if (errors.hasErrors()) {
			logger.warn("parameter is error.");
			throw new Exception();
		}
		logger.debug("copy group start");
		userId = RequestContext.getCurrUser().getId();
		try {
			
			httpClient.invokeHttpWithoutResultType(serverPath + copyAclGroupUrl, aclGroupStruc, userId, null);
			//addSucceedAclLog("复制权限组","/copyGroup");
		} catch (Exception e) {
			logger.error("copy group error", e);
		}
		return this.queryAclGroup(new PageRequest<Long>(), userId, model);
	}

	@RequestMapping(value = "/closeGroup")
	@ResponseBody
	public Object closeGroup(@ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc,
			@RequestParam("userId") Long userId, Model model) throws Exception {
		logger.debug("close group start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			userId = RequestContext.getCurrUser().getId();
			httpClient.invokeHttpWithoutResultType(serverPath + updateAclGroupUrl, aclGroupStruc, userId, null);
			resp.setStatus(1l);
			//addSucceedAclLog("禁用权限组","/closeGroup");
		} catch (Exception e) {
			logger.error("close group error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/openGroup")
	@ResponseBody
	public Object openGroup(@ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc,
			@RequestParam("userId") Long userId, Model model) throws Exception {
		logger.debug("open group start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			userId = RequestContext.getCurrUser().getId();
			httpClient.invokeHttpWithoutResultType(serverPath + updateAclGroupUrl, aclGroupStruc, userId, null);
			resp.setStatus(1l);
			//addSucceedAclLog("打开权限组","/openGroup");
		} catch (Exception e) {
			logger.error("open group error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/checkByName")
	@ResponseBody
	public Object checkByName(@ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc, Model model)
			throws Exception {
		logger.debug("checkByName start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			Response<Boolean> checkResp = httpClient.invokeHttp(serverPath + checkByNameUrl, aclGroupStruc,
					new TypeReference<Response<Boolean>>() {
					});
			if (checkResp.getBody().getResult()) {
				resp.setStatus(1);
			} else {
				resp.setStatus(0l);
				resp.setMessage("组名不可重复");
			}
		} catch (Exception e) {
			logger.error("checkByName", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/deleteGroup")
	@ResponseBody
	public Object deleteGroup(@RequestParam("id") Long id, Model model) throws Exception {
		logger.debug("delete group start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			AclGroupDeleteRequest request = new AclGroupDeleteRequest();
			List<Long> ids = new ArrayList<Long>();
			ids.add(id);
			request.setIds(ids);
			AclGroupIdRequest checkRequest = new AclGroupIdRequest();
			checkRequest.setAclGId(id);
			Response<Boolean> checkResp = httpClient.invokeHttp(serverPath + checkDeleteUrl, checkRequest,
					new TypeReference<Response<Boolean>>() {
					});
			if (checkResp.getBody().getResult() == true) {
				httpClient.invokeHttpWithoutResultType(serverPath + deleteAclGroupUrl, request);
				resp.setStatus(1l);
				//addSucceedAclLog("删除权限组","/deleteGroup");
			} else {
				resp.setStatus(2l);
				resp.setMessage("");
			}
		} catch (Exception e) {
			logger.error("delete group error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/toUpdateGroup")
	public String toUpdateGroup(@RequestParam("id") Long id, @RequestParam("pid") Long pid,
			@RequestParam("name") String name, @RequestParam("inUser") Long inUser,
			@RequestParam("userId") Long userId, Model model) throws Exception {
		Response<List<AclGroupStruc>> response = null;
		AclGroupStruc group = null;
		try {
			Pager pager = new Pager();
			pager.setStartNo(1);
			pager.setEndNo(5000000);
			response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, userId, null,
					new TypeReference<Response<List<AclGroupStruc>>>() {
					});
			group = (AclGroupStruc) httpClient
					.invokeHttp(serverPath + queryAclGroupByUser, new AclGroupStruc(), userId, null,
							new TypeReference<Response<AclGroupStruc>>() {
							}).getBody().getResult();
		} catch (Exception e) {
			logger.error("query aclGroup error", e);
			throw e;
		}
		/*model.addAttribute("id", id);
		model.addAttribute("pid", pid);
		model.addAttribute("inUser", inUser);
		model.addAttribute("name", new java.net.URLDecoder().decode(name, "utf-8"));
		model.addAttribute("groups", AclGroupTools.toOneGroup(response.getBody().getResult()).get(pid));
		model.addAttribute("group", group);*/

		model.addAttribute("id", id);
		model.addAttribute("pid", pid);
		model.addAttribute("group", group);
		model.addAttribute("inUser", inUser);
		model.addAttribute("name", new java.net.URLDecoder().decode(name, "utf-8"));
		model.addAttribute("groups", AclGroupTools.toOneGroup(response.getBody().getResult()).get(pid));
		return "/acl/updateGroup";
	}

	@RequestMapping(value = "/updateGroup")
	@ResponseBody
	public Object updateGroup(@Valid @ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc, Errors errors,
			@RequestParam("ids") String ids, @RequestParam("userId") Long userId, Model model) throws Exception {
		if (errors.hasErrors()) {
			logger.warn("parameter is error.");
			throw new Exception();
		}
		logger.debug("update group start");
		OperateStatusResponse resp = new OperateStatusResponse();
		AclGroupBindAuthRequest bindRequest = new AclGroupBindAuthRequest();
		bindRequest.setGid(aclGroupStruc.getId());
		List<Long> idList = new ArrayList<Long>();
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			idList.add(Long.valueOf(id));
		}
		bindRequest.setAclIds(idList);
		try {
			userId = RequestContext.getCurrUser().getId();
			httpClient.invokeHttpWithoutResultType(serverPath + updateAclGroupUrl, aclGroupStruc, userId, null);
			httpClient.invokeHttpWithoutResultType(serverPath + bindAuthForGroupUrl, bindRequest);
			resp.setStatus(1l);
			//addSucceedAclLog("更新权限组","/updateGroup");
		} catch (Exception e) {
			logger.error("update group error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/toSubAddGroup")
	public String toSubAddGroup(@RequestParam("pname") String pname, @RequestParam("pid") Long pid, Model model)
			throws Exception {
		model.addAttribute("pid", pid);
		model.addAttribute("pname", new java.net.URLDecoder().decode(pname, "utf-8"));
		return "/acl/addSubGroup";
	}
	@RequestMapping(value = "/toAddGroup")
	public String toAddGroup(Model model) throws Exception {
		Response<List<AclGroupStruc>> response = null;
		Long userId=RequestContext.getCurrUser().getId();
		Response<AclGroupStruc> resp = null;
		try {
			Pager pager = new Pager();
			pager.setStartNo(1);
			pager.setEndNo(5000000);
			response = httpClient.invokeHttp(serverPath + queryAclGroupUrl, null, pager, userId, null,
					new TypeReference<Response<List<AclGroupStruc>>>() {
					});
			resp = httpClient
					.invokeHttp(serverPath + queryAclGroupByUser, new AclGroupStruc(), userId, null,
							new TypeReference<Response<AclGroupStruc>>() {
							});
			addSucceedAclLog("进入创建权限組界面","/toAddGroup");
		} catch (Exception e) {
			logger.error("query aclGroup error", e);
			throw e;
		}
		Long groupId = ((AclUserStruc) RequestContext.getCurrUser()).getGroupId();
		
		model.addAttribute("groups", AclGroupTools.toOneGroup(response.getBody().getResult()).get(groupId));
		model.addAttribute("group", resp.getBody().getResult());
		model.addAttribute("cate2Name", "创建权限组");
		return "/acl/addGroup";
	}

	@RequestMapping(value = "/addGroup")
	@ResponseBody
	public Object addGroup(@ModelAttribute("aclGroupStruc") AclGroupStruc aclGroupStruc,
			@RequestParam("ids") String ids, @RequestParam("userId") Long userId, Model model) throws Exception {
		logger.debug("add group start");
		OperateStatusResponse resp = new OperateStatusResponse();
		AclGroupBindAuthRequest bindRequest = new AclGroupBindAuthRequest();
		List<Long> idList = new ArrayList<Long>();
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			idList.add(Long.valueOf(id));
		}
		bindRequest.setAclIds(idList);
		try {
			userId = RequestContext.getCurrUser().getId();
			AclGroupStruc saveAclGroupStruc = (AclGroupStruc) httpClient
					.invokeHttp(serverPath + saveAclGroupUrl, aclGroupStruc, userId, null,
							new TypeReference<Response<AclGroupStruc>>() {
							}).getBody().getResult();
			bindRequest.setGid(saveAclGroupStruc.getId());
			httpClient.invokeHttpWithoutResultType(serverPath + bindAuthForGroupUrl, bindRequest);
			resp.setStatus(1l);
			//addSucceedAclLog("添加关闭权限组","/addGroup");
		} catch (Exception e) {
			logger.error("add group error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	private void addSucceedAclLog(String action, String url) throws Exception{		
		AclOperateLogStruc o = new AclOperateLogStruc();
		o.setAccount(RequestContext.getCurrUser().getUserName());
		o.setAction(action);
		o.setIp(RequestContext.get().ip());
		o.setUrl(selfUrl+"/aclAdmin" + url);
		o.setCreateTime(new Date());
		o.setDetail(1L);
		httpClient.invokeHttp(urlPath + createLog, o,new TypeReference<Response<Object>>(){});
	}
}
