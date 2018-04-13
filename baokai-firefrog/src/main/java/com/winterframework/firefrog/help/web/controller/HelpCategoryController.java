package com.winterframework.firefrog.help.web.controller;

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
import com.winterframework.firefrog.help.entity.HelpCategory;
import com.winterframework.firefrog.help.service.IHelpCategoryService;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucAddRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucDeleteRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucEditRequest;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("helpCategoryController")
@RequestMapping("/help")
public class HelpCategoryController {

	private static final Logger logger = LoggerFactory.getLogger(HelpCategoryController.class);

	@Resource(name = "helpCategoryServiceImpl")
	private IHelpCategoryService helpCategoryServiceImpl;

	/**
	 * 查询类目列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCategory")
	@ResponseBody
	public Response<List<CateStruc>> queryCategory(@RequestBody Request<CateStrucQueryRequest> request)
			throws Exception {
		logger.info("queryCategory start");
		Response<List<CateStruc>> resp = new Response<List<CateStruc>>(request);
		try {
			CateStrucQueryRequest queryRequest = request.getBody().getParam();
			HelpCategory helpCategory = new HelpCategory();
			helpCategory.setId(queryRequest.getId());
			helpCategory.setCname(queryRequest.getcName());
			List<HelpCategory> helpCategorys = helpCategoryServiceImpl.queryHelpCategorys(helpCategory);
			List<CateStruc> resultList = new ArrayList<CateStruc>();
			for (HelpCategory cate : helpCategorys) {
				CateStruc cateStruc = transformHelpCategory2CateStruc(cate);
				List<CateStruc> subList = new ArrayList<CateStruc>();
				for (HelpCategory subCate : cate.getSubCategorys()) {
					subList.add(transformHelpCategory2CateStruc(subCate));
				}
				cateStruc.setSubCate(subList);
				resultList.add(cateStruc);
			}
			resp.setResult(resultList);

		} catch (Exception e) {
			logger.error("queryCategory error", e);
			throw e;
		}
		logger.info("queryCategory end");
		return resp;
	}

	/**
	 * 新建类目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createCategory")
	@ResponseBody
	public Response createCategory(@RequestBody @ValidRequestBody Request<CateStrucAddRequest> request)
			throws Exception {
		logger.info("createCategory start");
		Response resp = new Response(request);
		try {
			CateStrucAddRequest addRequest = request.getBody().getParam();
			HelpCategory helpCategory = new HelpCategory();
			helpCategory.setCname(addRequest.getName());
			helpCategory.setClevel(addRequest.getLevel());
			helpCategory.setCno(addRequest.getNo());
			if (addRequest.getParentId() != null) {
				HelpCategory parent = new HelpCategory();
				parent.setId(addRequest.getParentId());
				helpCategory.setParent(parent);
			}
			helpCategoryServiceImpl.createHelpCategory(helpCategory);
		} catch (Exception e) {
			logger.error("createCategory error", e);
			throw e;
		}
		logger.info("createCategory end");
		return resp;
	}

	/**
	 * 修改类目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyCategory")
	@ResponseBody
	public Response modifyCategory(@RequestBody @ValidRequestBody Request<CateStrucEditRequest> request)
			throws Exception {
		logger.info("modifyCategory start");
		Response resp = new Response(request);
		try {
			CateStrucEditRequest editRequest = request.getBody().getParam();
			HelpCategory helpCategory = new HelpCategory();
			helpCategory.setId(editRequest.getId());
			helpCategory.setCname(editRequest.getName());
			helpCategory.setCno(editRequest.getNumber());
			helpCategoryServiceImpl.update(helpCategory);
		} catch (Exception e) {
			logger.error("modifyCategory error", e);
			throw e;
		}
		logger.info("modifyCategory end");
		return resp;
	}

	/**
	 * 删除类目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory")
	@ResponseBody
	public Response deleteCategory(@RequestBody @ValidRequestBody Request<CateStrucDeleteRequest> request)
			throws Exception {
		logger.info("deleteCategory start");
		Response resp = new Response(request);
		try {
			CateStrucDeleteRequest delRequest = request.getBody().getParam();
			helpCategoryServiceImpl.deleteHelpCategory(delRequest.getId());
		} catch (Exception e) {
			logger.error("deleteCategory error", e);
			throw e;
		}
		return resp;
	}

	private CateStruc transformHelpCategory2CateStruc(HelpCategory cate) {
		CateStruc cateStruc = new CateStruc();
		cateStruc.setId(cate.getId());
		cateStruc.setLevel(cate.getClevel());
		cateStruc.setName(cate.getCname());
		cateStruc.setNumber(cate.getCno());
		return cateStruc;
	}
}
