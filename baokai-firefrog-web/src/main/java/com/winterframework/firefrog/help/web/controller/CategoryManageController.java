/**   
* @Title: CategoryManageController.java 
* @Package com.winterframework.firefrog.help.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-11 上午11:08:06 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucAddRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucDeleteRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucEditRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucModifyNoRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("categoryManageController")
@RequestMapping(value = "/helpAdmin")
public class CategoryManageController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryManageController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;

	@PropertyConfig(value = "url.help.createCategory")
	private String createCategoryUrl;

	@PropertyConfig(value = "url.help.modifyCategory")
	private String modifyCategoryUrl;

	@PropertyConfig(value = "url.help.deleteCategory")
	private String deleteCategoryUrl;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	private String getUrl(String path) {
		return serverPath + path;
	}

	@RequestMapping(value = "/queryCategory")
	public String queryCategory(@RequestParam("cate2Name") String name,@RequestParam(required=false) Long parentId, Model model) throws Exception {
		Response<List<CateStruc>> response = null;
		logger.info("query category start");
		try {
			CateStrucQueryRequest request = new CateStrucQueryRequest();
			response = httpClient.invokeHttp(getUrl(queryCategoryUrl), request,
					new TypeReference<Response<List<CateStruc>>>() {
					});
		} catch (Exception e) {
			logger.error("query category error!");
			throw e;
		}
		model.addAttribute("cate2Name", "类目列表");
		model.addAttribute("cateStrucs", response.getBody().getResult());
		model.addAttribute("parentId", parentId);
		logger.info("query category end");
		return "/help/categoryListManage";
	}

	@RequestMapping(value = "/addCategory")
	@ResponseBody
	public Object addCategory(@ModelAttribute("categoryAdd") @Valid CateStrucAddRequest categoryAdd,
			BindingResult result) throws Exception {
		logger.info("add category start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("添加数据错误！");
				return resp;
			}
			if (categoryAdd.getParentId() == null || categoryAdd.getParentId().longValue() == 0) {
				categoryAdd.setParentId(null);
			}
			httpClient.invokeHttpWithoutResultType(getUrl(createCategoryUrl), categoryAdd);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("add category error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("add category end");
		return resp;
	}

	@RequestMapping(value = "/editCategory")
	@ResponseBody
	public Object editCategory(@ModelAttribute("categoryEdit") @Valid CateStrucEditRequest categoryEdit,
			BindingResult result) throws Exception {
		logger.info("edit category start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("修改数据错误！");
				return resp;
			}
			httpClient.invokeHttpWithoutResultType(getUrl(modifyCategoryUrl), categoryEdit);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("edit category error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("edit category end");
		return resp;
	}

	@RequestMapping(value = "/editCategoryNo")
	@ResponseBody
	public Object editCategoryNo(@ModelAttribute("categoryEditNo") @Valid CateStrucModifyNoRequest categoryEditNo,
			BindingResult result) throws Exception {
		logger.info("edit category start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("移动数据错误！");
				return resp;
			}
			CateStrucEditRequest categoryEdit = new CateStrucEditRequest();
			categoryEdit.setId(categoryEditNo.getId());
			categoryEdit.setNumber(categoryEditNo.getNumber());
			httpClient.invokeHttpWithoutResultType(getUrl(modifyCategoryUrl), categoryEdit);
			categoryEdit.setId(categoryEditNo.getId1());
			categoryEdit.setNumber(categoryEditNo.getNumber1());
			httpClient.invokeHttpWithoutResultType(getUrl(modifyCategoryUrl), categoryEdit);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("edit category error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("edit category end");
		return resp;
	}

	@RequestMapping(value = "/deleteCategory")
	@ResponseBody
	public Object deleteCategory(@ModelAttribute("categoryDelete") @Valid CateStrucDeleteRequest categoryDelete,
			BindingResult result) throws Exception {
		logger.info("delete category start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("删除数据错误！");
				return resp;
			}
			httpClient.invokeHttpWithoutResultType(getUrl(deleteCategoryUrl), categoryDelete);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("delete category error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("delete category end");
		return resp;
	}
}
