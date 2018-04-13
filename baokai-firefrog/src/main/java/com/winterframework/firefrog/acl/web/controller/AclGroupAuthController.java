/**   
* @Title: AclGroupAuthController.java 
* @Package com.winterframework.firefrog.acl.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午6:00:44 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;
import com.winterframework.firefrog.acl.service.AclUserService;
import com.winterframework.firefrog.acl.service.IAclGroupAuthorizationService;
import com.winterframework.firefrog.acl.service.IAclGroupService;
import com.winterframework.firefrog.acl.service.IAclService;
import com.winterframework.firefrog.acl.web.dto.AclGroupBindAuthRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupDeleteRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupIdRequest;
import com.winterframework.firefrog.acl.web.dto.AclGroupStruc;
import com.winterframework.firefrog.acl.web.dto.AclStruc;
import com.winterframework.firefrog.acl.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: AclGroupAuthController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 下午6:00:44 
*  
*/
@Controller("aclGroupAuthController")
@RequestMapping("/aclAdmin")
public class AclGroupAuthController {
	private static final Logger logger = LoggerFactory.getLogger(AclGroupAuthController.class);

	@Resource(name = "aclGroupAuthorizationServiceImpl")
	private IAclGroupAuthorizationService aclGroupAuthorizationService;

	@Resource(name = "aclServiceImpl")
	private IAclService aclService;

	@Resource(name = "aclGroupServiceImpl")
	private IAclGroupService aclGroupService;

	@Resource(name = "aclUserServiceImpl")
	private AclUserService aclUserServiceImpl;

	/**
	 * 
	* @Title: bindAuthForGroup 
	* @Description: 创建帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/bindAuthForGroup")
	public @ResponseBody
	Response<Object> bindAuthForGroup(@RequestBody @ValidRequestBody Request<AclGroupBindAuthRequest> request)
			throws Exception {
		logger.debug("bindAuthForGroup start");
		Response<Object> resp = new Response<Object>(request);
		try {
			AclGroupBindAuthRequest req = request.getBody().getParam();
			List<AclGroupAuthorization> authList = new ArrayList<AclGroupAuthorization>();
			List<Long> aclList = req.getAclIds();
			if (aclList != null) {
				AclGroup aclGroup = new AclGroup();
				aclGroup.setId(req.getGid());
				for (Long aclId : aclList) {
					AclGroupAuthorization auth = new AclGroupAuthorization();
					auth.setAclGroup(aclGroup);
					Acl acl = new Acl();
					acl.setId(aclId);
					auth.setAcl(acl);
					authList.add(auth);
				}
				aclGroupAuthorizationService.bindAuthForGroup(authList);
			}
		} catch (Exception e) {
			logger.error("bindAuthForGroup end", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryAclByGroup 
	* @Description: 查询group的acl
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAclByGroup") 
	public @ResponseBody
	Response<List<AclStruc>> queryAclByGroup(@RequestBody @ValidRequestBody Request<AclGroupIdRequest> request)
			throws Exception {
		
		logger.debug("queryAclByGroup start");
		Response<List<AclStruc>> resp = new Response<List<AclStruc>>(request);
		try {
			List<Acl> acls =new ArrayList();
			Long groupId = request.getBody().getParam().getAclGId();
			acls= aclService.queryAclByGroup(groupId);		
			
			List<AclStruc> aclStrucs = new ArrayList<AclStruc>();
			for (Acl acl : acls) {
				AclStruc aclStruc = new AclStruc();
				aclStruc.setId(acl.getId());
				aclStruc.setLabel(acl.getLabel());
				aclStruc.setName(acl.getName());
				aclStruc.setOrders(acl.getOrders());
				if (acl.getParentAcl() != null) {
					aclStruc.setPid(acl.getParentAcl().getId());
				}
				aclStruc.setTypes(acl.getType());
				aclStrucs.add(aclStruc);
			}
			resp.setResult(DTOConverter.handerAclTreeList(aclStrucs));
		} catch (Exception e) {
			logger.error("queryAclByGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryAclGroupByUser 
	* @Description: 查询当前用户的下级group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAclGroupByUser")
	public @ResponseBody
	Response<AclGroupStruc> queryAclGroupByUser(@RequestBody Request<Object> request) throws Exception {
		logger.debug("queryAclGroupByUser start");
		Response<AclGroupStruc> resp = new Response<AclGroupStruc>();
		try {
			Long userId = request.getHead().getUserId();
			AclGroup aclGroup = aclGroupService.queryAclGroupByUser(userId);
			resp.setResult(DTOConverter.aclGroupEntity2Struc(aclGroup));
		} catch (Exception e) {
			logger.error("queryAclGroupByUser end", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: deleteAclGroup 
	* @Description: 删除group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteAclGroup")
	public @ResponseBody
	Response<Object> deleteAclGroup(@RequestBody Request<AclGroupDeleteRequest> request) throws Exception {
		logger.debug("deleteAclGroup start");
		Response<Object> resp = new Response<Object>(request);
		try {
			List<Long> ids = request.getBody().getParam().getIds();
			aclGroupService.deleteGroup(ids);
		} catch (Exception e) {
			logger.error("deleteAclGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: updateAclGroup 
	* @Description: 更新group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateAclGroup")
	public @ResponseBody
	Response<Object> updateAclGroup(@RequestBody @ValidRequestBody Request<AclGroupStruc> request) throws Exception {
		logger.debug("updateAclGroup start");
		Response<Object> resp = new Response<Object>(request);
		try {
			AclGroupStruc aclGroupStruc = request.getBody().getParam();
			AclGroup aclGroup = new AclGroup();
			aclGroup.setId(aclGroupStruc.getId());
			aclGroup.setName(aclGroupStruc.getName());
			aclGroup.setInUse(aclGroupStruc.getInUser());
			if (aclGroupStruc.getPid() != null) {
				AclGroup parentGroup = new AclGroup();
				parentGroup.setId(aclGroupStruc.getPid());
				aclGroup.setParentGroup(parentGroup);
			}
			Long userId = request.getHead().getUserId();
			String modifier = aclUserServiceImpl.getByUserId(userId).getAccount();//todo for modify a userName;
			aclGroup.setModifierer(modifier);
			aclGroupService.update(aclGroup);
		} catch (Exception e) {
			logger.error("updateAclGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: copyAclGroup 
	* @Description: 复制group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/copyAclGroup")
	public @ResponseBody
	Response<Object> copyAclGroup(@RequestBody @ValidRequestBody Request<AclGroupStruc> request) throws Exception {
		logger.debug("copyAclGroup start");
		Response<Object> resp = new Response<Object>(request);
		try {
			AclGroupStruc struc = request.getBody().getParam();

			Long userId = request.getHead().getUserId();
			String creatorer = aclUserServiceImpl.getByUserId(userId).getAccount();
			AclGroup aclGroup = new AclGroup();
			aclGroup.setId(struc.getId());
			aclGroup.setInUse(struc.getInUser());
			aclGroup.setCreatorer(creatorer);
			aclGroup.setName(struc.getName());
			aclGroupService.copyAclGroup(aclGroup);
		} catch (Exception e) {
			logger.error("copyAclGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 *  
	* @Title: saveAclGroup 
	* @Description: 创建group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/saveAclGroup")
	public @ResponseBody
	Response<AclGroupStruc> saveAclGroup(@RequestBody @ValidRequestBody Request<AclGroupStruc> request) throws Exception {
		logger.debug("saveAclGroup start");
		Response<AclGroupStruc> resp = new Response<AclGroupStruc>(request);
		try {
			AclGroupStruc aclGroupStruc = request.getBody().getParam();
			AclGroup aclGroup = new AclGroup();
			aclGroup.setId(aclGroupStruc.getId());
			aclGroup.setName(aclGroupStruc.getName());
			aclGroup.setInUse(aclGroupStruc.getInUser());
			if (aclGroupStruc.getPid() != null) {
				AclGroup parentGroup = new AclGroup();
				parentGroup.setId(aclGroupStruc.getPid());
				aclGroup.setParentGroup(parentGroup);
			}

			Long userId = request.getHead().getUserId();
			String creatorer = aclUserServiceImpl.getByUserId(userId).getAccount();
			aclGroup.setCreatorer(creatorer);
			AclGroup dbAclGroup = aclGroupService.insert(aclGroup);
			resp.setResult(DTOConverter.aclGroupEntity2Struc(dbAclGroup));
		} catch (Exception e) {
			logger.error("saveAclGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryAclGroup 
	* @Description: 查询group列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAclGroup")
	public @ResponseBody
	Response<List<AclGroupStruc>> queryAclGroup(@RequestBody Request<Object> request) throws Exception {
		logger.debug("queryAclGroup start");
		Response<List<AclGroupStruc>> resp = new Response<List<AclGroupStruc>>(request);
		try {
			PageRequest<Long> pageRequest = null;
			pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager().getStartNo(), request
					.getBody().getPager().getEndNo());
			pageRequest.setSearchDo(request.getHead().getUserId());
			Page<AclGroup> page = aclGroupService.queryAclGroup(pageRequest);
			List<AclGroup> groupList = page.getResult();
			List<AclGroupStruc> result = new ArrayList<AclGroupStruc>();
			for (AclGroup group : groupList) {
				AclGroupStruc groupStruc = DTOConverter.aclGroupEntity2Struc(group);
				result.add(groupStruc);
			}
			resp.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			resp.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryAclGroup error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: querySubAclGroup 
	* @Description: 根据上级group查询出下级group
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySubAclGroup")
	public @ResponseBody
	Response<List<AclGroupStruc>> querySubAclGroup(@RequestBody Request<AclGroupIdRequest> request) throws Exception {
		logger.debug("querySubAclGroup start");
		Response<List<AclGroupStruc>> resp = new Response<List<AclGroupStruc>>(request);
		try {
			Long groupId = request.getBody().getParam().getAclGId();
			List<AclGroup> aclGroupList = aclGroupService.queryFirstSubGroups(groupId);
			List<AclGroupStruc> result = new ArrayList<AclGroupStruc>();
			for (AclGroup aclGroup : aclGroupList) {
				AclGroupStruc aclGroupStruc = new AclGroupStruc();
				aclGroupStruc.setCreatorer(aclGroup.getCreatorer());
				aclGroupStruc.setGmtCreated(aclGroup.getGmtCreated());
				aclGroupStruc.setGmtModified(aclGroup.getGmtModified());
				aclGroupStruc.setId(aclGroup.getId());
				aclGroupStruc.setInUser(aclGroup.getInUse());
				aclGroupStruc.setModifierer(aclGroup.getModifierer());
				aclGroupStruc.setName(aclGroup.getName());
				if (aclGroup.getParentGroup() != null) {
					aclGroupStruc.setPid(aclGroup.getParentGroup().getId());
				}
				result.add(aclGroupStruc);
			}
			resp.setResult(result);
		} catch (Exception e) {
			logger.error("querySubAclGroup error", e);
		}
		return resp;
	}

	/**
	 * 
	* @Title: checkDelete 
	* @Description: 根据group判断是否可以删除
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkDelete")
	public @ResponseBody
	Response<Boolean> checkDelete(@RequestBody Request<AclGroupIdRequest> request) throws Exception {
		logger.debug("checkDelete start");
		Response<Boolean> resp = new Response<Boolean>(request);
		try {
			Long groupId = request.getBody().getParam().getAclGId();
			Boolean isDelete = aclGroupService.checkDelete(groupId);
			resp.setResult(isDelete);
		} catch (Exception e) {
			logger.error("checkDelete error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: checkByName 
	* @Description: 根据group判断是否可以创建
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkByName")
	public @ResponseBody
	Response<Boolean> checkByName(@RequestBody Request<AclGroupStruc> request) throws Exception {
		logger.debug("checkByName start");
		Response<Boolean> resp = new Response<Boolean>(request);
		try {
			AclGroupStruc aclGroupStruc = request.getBody().getParam();
			AclGroup aclGroup = new AclGroup();
			aclGroup.setName(aclGroupStruc.getName());
			aclGroup.setId(aclGroupStruc.getId());
			Boolean isDelete = aclGroupService.checkByName(aclGroup);
			resp.setResult(isDelete);
		} catch (Exception e) {
			logger.error("checkDelete error", e);
			throw e;
		}
		return resp;
	}
}
