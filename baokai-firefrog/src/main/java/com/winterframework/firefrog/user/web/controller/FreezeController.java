package com.winterframework.firefrog.user.web.controller;

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
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IFreezeService;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.FreezeRequestDTO;
import com.winterframework.firefrog.user.web.dto.QueryFreezeUserRequestDTO;
import com.winterframework.firefrog.user.web.dto.QueryUnFreezeUserLogRequestDTO;
import com.winterframework.firefrog.user.web.dto.QueryUnFreezeUserLogStruc;
import com.winterframework.firefrog.user.web.dto.UnFreezeRequestDTO;
import com.winterframework.firefrog.user.web.dto.UserStrucResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
 *    
 * 类功能说明:  冻结和解冻，查询冻结信息操作类 
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David 
 *
 */

@Controller("freezeController")
@RequestMapping("/user")
public class FreezeController {

	private static final Logger log = LoggerFactory.getLogger(FreezeController.class);

	@Resource(name = "freezeServiceImpl")
	private IFreezeService freezeService;

	/** 
	* @Title: queryUserFreezeByCriteria 
	* @Description: 冻结账户查询 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserFreeze")
	@ResponseBody
	public Object queryUserFreezeByCriteria(@RequestBody Request<QueryFreezeUserRequestDTO> request) throws Exception {
		log.info("开始查询冻结用户...");

		Response<List<UserStrucResponse>> response = null;

		if (null != request.getBody()) {

			try {
				QueryFreezeUserDTO queryDTO = ControllerDTOConverter.convertMQueryFreezeUserDTO(request);
				queryDTO.setSortColumns(" FREEZE_DATE DESC");
				response = searchUserFreeze(response, request, queryDTO);

			} catch (Exception e) {
				log.error("查询冻结客户信息错误", e);
				throw e;
			}
		}

		log.info("成功处理多条件查询冻结用户信息请求...");
		return response;

	}

	private Response<List<UserStrucResponse>> searchUserFreeze(Response<List<UserStrucResponse>> response,
			Request<QueryFreezeUserRequestDTO> request, QueryFreezeUserDTO queryDTO) throws Exception {
		Page<User> list = null;
		if (response == null) {
			response = new Response<List<UserStrucResponse>>();
		}
		@SuppressWarnings("unchecked")
		PageRequest<QueryFreezeUserDTO> pageReqeust = (PageRequest<QueryFreezeUserDTO>) getPageRequest(request);
		//response.setHead(ResponseHeader.createReponseHeader(request.getHead()));
		pageReqeust.setSearchDo(queryDTO);

		list = freezeService.searchFreezeUser(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<UserStrucResponse>());
		}
		return response;
	}

	private Response<List<UserStrucResponse>> fillResponse(Response<List<UserStrucResponse>> response, Page<User> list,
			Request<QueryFreezeUserRequestDTO> request) {
		if (null == response) {
			response = new Response<List<UserStrucResponse>>(request);
		}
		log.info("开始填充response。。。");
		List<UserStrucResponse> responseList = new ArrayList<UserStrucResponse>();

		for (int i = 0; i < list.getResult().size(); i++) {

			User user = list.getResult().get(i);
			UserStrucResponse userStrucResponse = ControllerDTOConverter.user2UserStrucResponse(user);
			responseList.add(userStrucResponse);
		}
		response.setResult(responseList);
		ResultPager pager = new ResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());

		response.setResultPage(pager);
		return response;

	}

	private PageRequest<?> getPageRequest(Request<?> request) {
		PageRequest<?> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody().getPager().getStartNo(),
				request.getBody().getPager().getEndNo());
		return pageReqeust;
	}

	@RequestMapping(value = "/queryUserFreezeLog")
	@ResponseBody
	public Object queryUserFreezeLogByCriteria(@RequestBody Request<QueryUnFreezeUserLogRequestDTO> request)
			throws Exception {
		log.info("开始查询冻结解冻记录...");

		Response<List<QueryUnFreezeUserLogStruc>> response = null;

		if (null != request.getBody()) {

			try {

				QueryUnFreezeUserLogDTO queryDTO = ControllerDTOConverter.convertMQueryUnFreezeUserLogDTO(request);
				queryDTO.setSortColumns(" unfreezeDate desc");
				response = searchUserFreezeLog(response, request, queryDTO);

			} catch (Exception e) {
				log.error("查询冻结客户信息错误", e);
				throw e;
			}
		}

		log.info("成功处理多条件查询冻结用户信息请求...");
		return response;
	}

	private Response<List<QueryUnFreezeUserLogStruc>> searchUserFreezeLog(
			Response<List<QueryUnFreezeUserLogStruc>> response, Request<QueryUnFreezeUserLogRequestDTO> request,
			QueryUnFreezeUserLogDTO queryDTO) throws Exception {
		Page<FreezeLog> list = null;
		if (response == null) {
			response = new Response<List<QueryUnFreezeUserLogStruc>>();
		}
		@SuppressWarnings("unchecked")
		PageRequest<QueryUnFreezeUserLogDTO> pageReqeust = (PageRequest<QueryUnFreezeUserLogDTO>) getPageRequest(request);
		response.setHead(ResponseHeader.createReponseHeader(request.getHead()));
		pageReqeust.setSearchDo(queryDTO);

		list = freezeService.searchUnFreezeUserLog(pageReqeust);

		if (null != list && list.getResult() != null && list.getResult().size() > 0) {
			response = fillFreezeResponse(response, list, request);
		} else {
			response.setResult(new ArrayList<QueryUnFreezeUserLogStruc>());
		}
		return response;
	}

	private Response<List<QueryUnFreezeUserLogStruc>> fillFreezeResponse(
			Response<List<QueryUnFreezeUserLogStruc>> response, Page<FreezeLog> list,
			Request<QueryUnFreezeUserLogRequestDTO> request) {
		if (null == response) {
			response = new Response<List<QueryUnFreezeUserLogStruc>>(request);
		}
		log.info("开始填充response。。。");
		List<QueryUnFreezeUserLogStruc> responseList = new ArrayList<QueryUnFreezeUserLogStruc>();

		for (int i = 0; i < list.getResult().size(); i++) {
			FreezeLog freezeLog = list.getResult().get(i);
			
			log.info("=freezeLog: Memo:"+freezeLog.getMemo());
			log.info("=freezeLog: FreezeAccount:"+freezeLog.getFreezeAccount());
			
			QueryUnFreezeUserLogStruc queryUnFreezeUserLogStruc = ControllerDTOConverter
					.freezeLog2queryUnFreezeUserLogStruc(freezeLog);
			responseList.add(queryUnFreezeUserLogStruc);
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
	 * 
	 * 方法描述：冻结用户操作
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/freezeUser")
	@ResponseBody
	public Object freezeUser(@RequestBody @ValidRequestBody Request<FreezeRequestDTO> request)
			throws Exception {

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);

		try {
			//range 1单个用户， 0 用户树。
			if (null != request.getBody() && null != request.getBody().getParam()) {
				FreezeDTO freezeDTO = new FreezeDTO();
				FreezeRequestDTO requestDTO = request.getBody().getParam();
				freezeDTO.setFreezeDate(new Date());
				freezeDTO.setFreezeId(request.getHead().getUserId());
				freezeDTO.setFreezeMethod(requestDTO.getMethod());
				freezeDTO.setMemo(requestDTO.getMemo());
				freezeDTO.setUserId(requestDTO.getUserId());
				freezeDTO.setFreezeAccount(requestDTO.getFreezeAccount());

				if (null != requestDTO.getRange() && requestDTO.getRange().intValue() == 0) {
					freezeService.freezeUserAndSubUser(freezeDTO);
				} else {
					freezeService.freezeUser(freezeDTO);
				}

			}

		} catch (Exception e) {

			log.error("冻结用户操作异常：", e);
			throw e;
		}

		return response;
	}

	/**
	 * 
	 * 方法描述：解冻用户操作
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unFreezeUser")
	@ResponseBody
	public Object nuFreezeUser(@RequestBody @ValidRequestBody @ValidRequestHeader Request<UnFreezeRequestDTO> request)
			throws Exception {

		Response<UserStrucResponse> response = new Response<UserStrucResponse>(request);

		try {
			//range 1解冻 单个用户 0解冻用户树
			if (null != request.getBody() && null != request.getBody().getParam()) {

				UnFreezeDTO unFreezeDTO = new UnFreezeDTO();

				UnFreezeRequestDTO requestDTO = request.getBody().getParam();
				unFreezeDTO.setMemo(requestDTO.getMemo());
				unFreezeDTO.setUserId(requestDTO.getUserId());
				unFreezeDTO.setFreezeId(request.getHead().getUserId());
				unFreezeDTO.setFreezeAccount(requestDTO.getFreezeAccount());

				if (null != requestDTO.getRange() && requestDTO.getRange().intValue() == 0) {
					freezeService.unFreezeUserAndSubUser(unFreezeDTO);
				} else {
					freezeService.unFreezeUser(unFreezeDTO);
				}
			}

		} catch (Exception e) {

			log.error("解冻用户操作异常：", e);
			throw e;
		}
		return response;
	}

}
