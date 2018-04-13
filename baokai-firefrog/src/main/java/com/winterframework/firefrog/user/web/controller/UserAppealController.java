package com.winterframework.firefrog.user.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.exception.ServiceException;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.firefrog.user.service.IAppealService;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.QueryUserApealListRequestDTO;
import com.winterframework.firefrog.user.web.dto.UserAppealAuditRequestDTO;
import com.winterframework.firefrog.user.web.dto.UserAppealDetailRequestDTO;
import com.winterframework.firefrog.user.web.dto.UserAppealDetailResponse;
import com.winterframework.firefrog.user.web.dto.UserAppealListStruc;
import com.winterframework.firefrog.user.web.dto.UserAppealRequestDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller("userAppealController")
@RequestMapping(value = "/user")
public class UserAppealController {

	private static final Logger log = LoggerFactory.getLogger(UserAppealController.class);
	@Resource(name = "userAppealServiceImpl")
	private IAppealService userAppealService;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	/** 
	* @Title: userAppeal 
	* @Description: 保存用户申诉信息 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/userAppeal")
	@ResponseBody
	public Object userAppeal(@RequestBody @ValidRequestBody Request<UserAppealRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		Appeal appeal = ControllerDTOConverter.userAppealRequestDTO2Appeal(request);
		try {
			userAppealService.saveUserAppeal(appeal);
		} catch (ServiceException e) {
			log.error("申诉用户名不存在", e);
			response.getHead().setStatus(UserCustomer.LOGIN_FAIL_REASON_USER_NOT_EXIST);
		} catch (Exception e) {
			log.error("saveUserAppeal error.", e);
			throw e;
		}

		return response;
	}

	/** 
	* @Title: queryUserAppealByCriteria 
	* @Description: 根据条件查询用户帐号申诉列表 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserAppealByCriteria")
	@ResponseBody
	public Object queryUserAppealByCriteria(@RequestBody @ValidRequestBody Request<QueryUserApealListRequestDTO> request)
			throws Exception {
		log.info("开始查询用户申诉...");

		Response<List<UserAppealListStruc>> response = new Response<List<UserAppealListStruc>>(request);

		if (null != request.getBody()) {

			try {

				UserAppealQueryDTO queryDTO = ControllerDTOConverter.convertMUserAppealQueryDTO(request);

				queryDTO.setSortColumns(UserAppealQueryDTO.SORT_COLUMNS + " desc ");
				if (request.getBody().getParam() != null && request.getBody().getParam().getOrderBy() != null) {
					queryDTO.setSortColumns(request.getBody().getParam().getOrderBy());
				}
				response = searchCustomer(response, request, queryDTO);

			} catch (Exception e) {
				log.error("多条件查看客户申诉信息：", e);
				throw e;
			}
		}

		log.info("功成处理多条件查询请求...");
		return response;

	}

	/** 
	* @Title: searchCustomer 
	* @Description: 方法描述：与Service服务接口
	* @param response
	* @param request
	* @param queryDTO
	* @return
	* @throws Exception
	*/
	private Response<List<UserAppealListStruc>> searchCustomer(Response<List<UserAppealListStruc>> response,
			Request<?> request, UserAppealQueryDTO queryDTO) throws Exception {

		Page<Appeal> list = null;
		if (response == null) {
			response = new Response<List<UserAppealListStruc>>();
		}
		PageRequest<UserAppealQueryDTO> pageReqeust = getPageRequest(request);
		pageReqeust.setSortColumns(queryDTO.getSortColumns());

		//response.setHead(ResponseHeader.createReponseHeader(request.getHead()));
		pageReqeust.setSearchDo(queryDTO);

		list = userAppealService.searchUserAppeal(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<UserAppealListStruc>());
		}

		return response;
	}

	/**
	 * 
	 * 方法描述：设置PageRequest信息
	 * 
	 * @param request
	 * @return
	 */
	private PageRequest<UserAppealQueryDTO> getPageRequest(Request<?> request) {

		PageRequest<UserAppealQueryDTO> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		return pageReqeust;
	}

	/**
	 * 填充response
	 * 
	 * @param response
	 * @param list
	 */
	private Response<List<UserAppealListStruc>> fillResponse(Response<List<UserAppealListStruc>> response,
			Page<Appeal> list, Request<?> request) {

		if (null == response) {
			response = new Response<List<UserAppealListStruc>>(request);
		}
		log.info("开始填充response。。。");
		List<UserAppealListStruc> responseList = new ArrayList<UserAppealListStruc>();

		for (int i = 0; i < list.getResult().size(); i++) {

			Appeal appeal = list.getResult().get(i);
			UserAppealListStruc userAppealStruc = ControllerDTOConverter.appeal2UserAppealStruc(appeal);

			responseList.add(userAppealStruc);

		}
		response.setResult(responseList);
		ResultPager pager = new ResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());

		response.setResultPage(pager);

		return response;

	}

	/** 
	* @Title: queryUserAppealDetailByAppealId 
	* @Description: 根据申诉id查询申诉详情 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserAppealDetail")
	@ResponseBody
	public Object queryUserAppealDetailByAppealId(
			@RequestBody @ValidRequestBody Request<UserAppealDetailRequestDTO> request) throws Exception {
		Response<UserAppealDetailResponse> response = new Response<UserAppealDetailResponse>(request);
		try {
			Appeal appeal = userAppealService.getUserAppealDetail(request.getBody().getParam().getId());

			log.info("生成UserAppealDetailResponse");
			if (appeal != null) {
				response.setResult(ControllerDTOConverter.appeal2UserAppealDetailResponse(appeal));
			}
			log.info("生成UserAppealDetailResponse结束");

		} catch (Exception e) {
			log.error("login error.", e);
			throw e;
		}

		return response;
	}

	/** 
	* @Title: userAppealAudit 
	* @Description: 后台审核用户申诉 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/userAppealAudit")
	@ResponseBody
	public Object userAppealAudit(
			@RequestBody @ValidRequestBody @ValidRequestHeader Request<UserAppealAuditRequestDTO> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		Appeal appeal = ControllerDTOConverter.userAppealAuditRequestDTO2Appeal(request);
		try {
			userAppealService.userAppealAudit(appeal);
			/*if (appeal.getPassed() == 1) {
				Map<String, String> map = new HashMap<String, String>();
				msgToMQ.addMessageToMq(UserTools.getUserFromHead(request), NoticeTaskEnum.a, map);
			}
*/
		} catch (Exception e) {
			log.error("userAppealAudit error.", e);
			throw e;
		}

		return response;
	}

}
