/**   
* @Title: FundUserRemarkController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 上午9:51:44 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkRecyleExtVO;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.firefrog.fund.service.IFundUserRemarkService;
import com.winterframework.firefrog.fund.web.controller.vo.FundUserRemarkQuery;
import com.winterframework.firefrog.fund.web.controller.vo.QueryRemark;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundUserMaualRemarkRequest;
import com.winterframework.firefrog.fund.web.dto.FundUserRemarkImportRequest;
import com.winterframework.firefrog.fund.web.dto.FundUserRemarkRecyleStruc;
import com.winterframework.firefrog.fund.web.dto.FundUserRemarkStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: FundUserRemarkController 
* @Description: 唯一附言接口类
* @author floy
* @date 2014-3-11 上午9:51:44 
*  
*/
@Controller("fundUserRemarkController")
@RequestMapping(value = "/fund")
public class FundUserRemarkController {

	private static Logger logger = LoggerFactory.getLogger(FundUserRemarkController.class);

	@Resource(name = "fundUserRemarkService")
	private IFundUserRemarkService fundUserRemarkService;

	/**
	 * 
	* @Title: checkRemarkExist 
	* @Description: 当前附言是否存在
	* @param request
	* @return
	 */
	@RequestMapping(value = "/checkRemarkExist")
	@ResponseBody
	public Response<Boolean> checkRemarkExist(@RequestBody @ValidRequestBody Request<FundUserRemark> request)
			throws Exception {
		Response<Boolean> response = new Response<Boolean>(request);
		String remark = request.getBody().getParam().getRemark();
		try {
			Boolean isExist = fundUserRemarkService.checkRemarkExist(remark);
			response.setResult(isExist);
		} catch (Exception e) {
			logger.error("check remark exist error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: importUser 
	* @Description: 导入用户
	* @param request
	* @return
	 */
	@RequestMapping(value = "/importUser")
	@ResponseBody
	public Response<Object> importUser(@RequestBody @ValidRequestBody Request<FundUserRemarkImportRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		FundUserRemarkImportRequest req = request.getBody().getParam();
		try {
			fundUserRemarkService.importUser(req.getUserIds());
		} catch (Exception e) {
			logger.error("import user error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: getAllRecyleRemarks 
	* @Description: 获取所有的回收用户remark
	* @param request
	* @return
	 */
	@RequestMapping(value = "/getAllRecyleRemarks")
	@ResponseBody
	public Response<List<FundUserRemarkRecyleStruc>> getAllRecyleRemarks(
			@RequestBody Request<FundUserRemarkRecyle> request) throws Exception {
		Response<List<FundUserRemarkRecyleStruc>> response = new Response<List<FundUserRemarkRecyleStruc>>(request);
		PageRequest<FundUserRemarkRecyle> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("GMT_CREATED DESC");
		try {
			Page<FundUserRemarkRecyleExtVO> page = fundUserRemarkService.getAllRecyleRemarks(pageRequest);
			List<FundUserRemarkRecyleStruc> resultList = new ArrayList<FundUserRemarkRecyleStruc>();
			for (FundUserRemarkRecyleExtVO remark : page.getResult()) {
				resultList.add(DTOConverter.transforRemarkRecyle2Struc(remark));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(resultList);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("getAllRecyleRemarks error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: checkRemarkExist 
	* @Description: 当前附言是否存在
	* @param request
	* @return
	 */
	@RequestMapping(value = "/recyleRemark")
	@ResponseBody
	public Response<Boolean> recyleRemark(@RequestBody @ValidRequestBody Request<FundUserRemarkRecyle> request)
			throws Exception {
		Response<Boolean> response = new Response<Boolean>(request);
		try {
			 fundUserRemarkService.recyleRemark(request.getBody().getParam().getId(),request.getBody().getParam().getRemark());
			response.setResult(true);
		} catch (Exception e) {
			logger.error("check remark exist error", e);
			throw e;
		}
		return response;
	}
	/**
	 * 
	* @Title: getAllRemarks 
	* @Description: 获取所有的用户remark
	* @param request
	* @return
	 */
	@RequestMapping(value = "/getAllRemarks")
	@ResponseBody
	public Response<List<FundUserRemarkStruc>> getAllRemarks(@RequestBody Request<FundUserRemarkQuery> request)
			throws Exception {
		Response<List<FundUserRemarkStruc>> response = new Response<List<FundUserRemarkStruc>>(request);
		PageRequest<FundUserRemark> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("GMT_CREATED DESC");
		try {
			Page<FundUserRemark> page = fundUserRemarkService.getAllRemarks(pageRequest);
			List<FundUserRemarkStruc> resultList = new ArrayList<FundUserRemarkStruc>();
			for (FundUserRemark remark : page.getResult()) {
				FundUserRemarkStruc struc = DTOConverter.transforRemark2Struc(remark);
				struc.setVipLvl((remark.getVipLvl() != null && remark.getVipLvl() > 0) ? 1 : 0);
				if (remark.getGmtCansetremark() == null) {
					remark.setGmtCansetremark(new Date());
				}
				if(remark.getGmtCansetremark()==null){					
					struc.setIsFreeze(0);
				}else{
					struc.setIsFreeze(new Date().after(remark.getGmtCansetremark()) == true ? 0 : 1);
				}
				if (remark.getUserChain() != null) {
					struc.setTopAgent(remark.getUserChain().split("/")[1]);
				}
				resultList.add(struc);//dtoconverter
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(resultList);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("getAllRemarks error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: getNextSystemRemark 
	* @Description: 获取下一个系统唯一附言
	* @param request
	* @return
	 */
	@RequestMapping(value = "/getNextSystemRemark")
	@ResponseBody
	public Response<String> getNextSystemRemark(@RequestBody Request<Object> request) throws Exception {
		Response<String> response = new Response<String>(request);
		try {
			String remark = fundUserRemarkService.getNextSystemRemark();
			response.setResult(remark);
		} catch (Exception e) {
			logger.error("getNextSystemRemark error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: getNextSystemRemark 
	* @Description: 获取下一个系统唯一附言
	* @param request
	* @return
	 */
	@RequestMapping(value = "/getRemark")
	@ResponseBody
	public Response<FundUserRemark> getUserRemark(@RequestBody Request<QueryRemark> request) throws Exception {
		Response<FundUserRemark> response = new Response<FundUserRemark>(request);
		try {
			FundUserRemark remark = fundUserRemarkService.getRemarkByUserId(request.getBody().getParam().getUserId());
			response.setResult(remark);
		} catch (Exception e) {
			logger.error("getNextSystemRemark error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: saveRemark 
	* @Description: 保存附言
	* @param request
	* @return
	 */
	@RequestMapping(value = "/saveRemark")
	@ResponseBody
	public Response<Boolean> saveRemark(@RequestBody Request<FundUserMaualRemarkRequest> request) throws Exception {
		Response<Boolean> response = new Response<Boolean>(request);
		FundUserMaualRemarkRequest req = request.getBody().getParam();
		try {
			Long userId = request.getHead().getUserId();
			Boolean result = fundUserRemarkService.saveRemark(req.getRemark(), userId);
			response.setResult(result);
		} catch (Exception e) {
			logger.error("saveRemark error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: updateRemark 
	* @Description: 修改附言
	* @param request
	* @return
	 */
	@RequestMapping(value = "/updateRemark")
	@ResponseBody
	public Response<Boolean> updateRemark(@RequestBody @ValidRequestBody Request<FundUserMaualRemarkRequest> request)
			throws Exception {
		Response<Boolean> response = new Response<Boolean>(request);
		FundUserMaualRemarkRequest req = request.getBody().getParam();
		try {
			Boolean result = fundUserRemarkService.updateRemark(req.getRemark(), req.getUserId());
			response.setResult(result);
		} catch (Exception e) {
			logger.error("updateRemark error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: setCanModifiedDays 
	* @Description: 设置最大修改时间
	* @param request
	* @return
	 */
	@RequestMapping(value = "/setCanModifiedDays")
	@ResponseBody
	public Response<Object> setCanModifiedDays(@RequestBody Request<Long> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		Long days = request.getBody().getParam();
		try {
			fundUserRemarkService.setCanModifiedDays(days);
		} catch (Exception e) {
			logger.error("setCanModifiedDays error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: getModifiedDays 
	* @Description: 获取最大修改时间
	* @param request
	* @return
	 */
	@RequestMapping(value = "/getModifiedDays")
	@ResponseBody
	public Response<String> getModifiedDays(@RequestBody Request<Object> request) throws Exception {
		Response<String> response = new Response<String>(request);
		try {
			String days = fundUserRemarkService.getModifiedDays();
			response.setResult(days);
		} catch (Exception e) {
			logger.error("setCanModifiedDays error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: updateRemark 
	* @Description: 取消锁定
	* @param request
	* @return
	 */
	@RequestMapping(value = "/cancelLock")
	@ResponseBody
	public Response<Object> cancelLock(@RequestBody Request<Long> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		Long userId = request.getBody().getParam();
		try {
			fundUserRemarkService.cancelLock(userId);
		} catch (Exception e) {
			logger.error("cancelLock error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: updateRemark 
	* @Description: 解除附言
	* @param request
	* @return
	 */
	@RequestMapping(value = "/cancelRemark")
	@ResponseBody
	public Response<Object> cancelRemark(@RequestBody Request<Long> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		Long userId = request.getBody().getParam();
		try {
			fundUserRemarkService.cancelRemark(userId);
		} catch (Exception e) {
			logger.error("cancelRemark error", e);
			throw e;
		}
		return response;
	}

}
