/**   
 * @Title: UserAgentIncomeController.java 
 * @Package com.winterframework.firefrog.fund.web.controller 
 * @Description: 总代盈亏报表 
 * @author hugh  
 * @date 2014年9月22日 下午4:41:19 
 * @version V1.0   
 */
package com.winterframework.firefrog.fund.web.controller;

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
import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.service.IUserAgentIncomeService;
import com.winterframework.firefrog.fund.web.dto.GameBetTypeDto;
import com.winterframework.firefrog.fund.web.dto.GameBetTypeDtoResponse;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeRequest;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeResponse;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeGroupDto;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @ClassName UserAgentIncomeController
 * @Description 总代盈亏报表 (前期不需要分页，后期加入分页，采用的逻辑分页)
 * @author hugh
 * @date 2014年9月22日 下午4:41:19
 * 
 */
@Controller("userAgentIncomeController")
@RequestMapping("/fund")
public class UserAgentIncomeController {

	private static final Logger log = LoggerFactory
			.getLogger(UserAgentIncomeController.class);

	@Resource(name = "userAgentIncomeServiceImpl")
	private IUserAgentIncomeService userAgentIncomeServiceImpl;

	/**
	 * @Title: getUserAgentIncomes
	 * @Description: 总代盈亏报表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserAgentIncomes")
	@ResponseBody
	public Response<UserAgentIncomeResponse> getUserAgentIncomes(
			@RequestBody @ValidRequestBody Request<UserAgentIncomeRequest> request) throws Exception {
		
		log.info("後台總代盈虧表=/fund/getUserAgentIncomes Start");
		UserAgentIncomeRequest userAgentIncomeRequest = request.getBody().getParam();
		String account = userAgentIncomeRequest.getAccount();
		Long userLvl = userAgentIncomeRequest.getUserLvl();
		Long isFreeze = userAgentIncomeRequest.getIsFreeze();
		String startDate = userAgentIncomeRequest.getStartDate();
		String endDate = userAgentIncomeRequest.getEndDate();	
		Long moneyMode = userAgentIncomeRequest.getMoneyMode();
		Long lotteryId = userAgentIncomeRequest.getLotteryId();
		String betTypeCode = userAgentIncomeRequest.getBetTypeCode();
		Long parentId = userAgentIncomeRequest.getParentId();
		Long id = userAgentIncomeRequest.getId();
		Integer page = userAgentIncomeRequest.getPage();
		
		log.info("=account:"+account);
		log.info("=userLvl:"+userLvl);
		log.info("=isFreeze:"+isFreeze);
		log.info("=startDate:"+startDate);
		log.info("=endDate:"+endDate);
		log.info("=moneyMode:"+moneyMode);
		log.info("=lotteryId:"+lotteryId);
		log.info("=betTypeCode:"+betTypeCode);
		log.info("=parentId:"+parentId);
		log.info("=id:"+id);
		log.info("=page:"+page);
		
		// 最後要回傳的型態
		Response<UserAgentIncomeResponse> response = new Response<UserAgentIncomeResponse>(request);
		// 資料查詢List
		List<UserAgentIncomeVO> list = null;
		Integer startNo = null;
		Integer endNo = null;
		int size = 0;
		try {// account != null 為查詢特定玩家
			
			if (request.getBody().getPager() != null) {
				startNo = request.getBody().getPager().getStartNo();
				endNo = request.getBody().getPager().getEndNo();
			}
			if (startNo == null) {
				startNo = 1;
			}
			if (endNo == null || endNo.intValue() == 0) {
				endNo = 30;
			}
			
			log.info("=startNo:" + userAgentIncomeRequest.getStartNo());
			log.info("=endNo:" + userAgentIncomeRequest.getEndNo());
			
			Long _size = userAgentIncomeServiceImpl.getCurrentUserReportCount(userAgentIncomeRequest);
			
			log.info("=count:" + _size);
			
			size = _size != null ? _size.intValue() : 0;
			
			userAgentIncomeRequest.setStartNo(startNo);
			userAgentIncomeRequest.setEndNo(endNo);
			
			list = userAgentIncomeServiceImpl.getCurrentUserReport(userAgentIncomeRequest);
		} catch (Exception e) {
			log.error("查询当前用户报表信息错误 ", e);
			throw e;
		}
		
		UserAgentIncomeResponse res = new UserAgentIncomeResponse();
		res.setVos(list);
		ResultPager pager = new ResultPager(++startNo, endNo,size);
		response.setResultPage(pager);
		response.setResult(res);
		
		log.info("後台總代盈虧表=/fund/getUserAgentIncomes End");
		return response;

	}

	/**
	 * @Title: getUserAndAgentIncomes
	 * @Description: 盈亏查询（自己 和 下级）首次查詢只會顯示前十筆並回總比數給client，
	 *               之後client會送頁數過來到getSubUserAndAgentIncomes Action查詢(瀑布流)，改善效能問題
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserAndAgentIncomes")
	@ResponseBody
	public Response<UserAgentIncomeResponse> getUserAndAgentIncomes(
			@RequestBody @ValidRequestBody Request<UserAgentIncomeRequest> request)
			throws Exception {
		
		log.info("获取总代以及下级报表=/fund/getUserAndAgentIncomes Start");
		
		UserAgentIncomeRequest userAgentIncomeRequest = request.getBody().getParam();
		String account = userAgentIncomeRequest.getAccount();
		Long userLvl = userAgentIncomeRequest.getUserLvl();
		Long isFreeze = userAgentIncomeRequest.getIsFreeze();
		String startDate = userAgentIncomeRequest.getStartDate();
		String endDate = userAgentIncomeRequest.getEndDate();	
		Long moneyMode = userAgentIncomeRequest.getMoneyMode();
		Long lotteryId = userAgentIncomeRequest.getLotteryId();
		String betTypeCode = userAgentIncomeRequest.getBetTypeCode();
		Long parentId = userAgentIncomeRequest.getParentId();
		Long id = userAgentIncomeRequest.getId();
		Integer page = userAgentIncomeRequest.getPage();
		
		log.info("=account:"+account);
		log.info("=userLvl:"+userLvl);
		log.info("=isFreeze:"+isFreeze);
		log.info("=startDate:"+startDate);
		log.info("=endDate:"+endDate);
		log.info("=moneyMode:"+moneyMode);
		log.info("=lotteryId:"+lotteryId);
		log.info("=betTypeCode:"+betTypeCode);
		log.info("=parentId:"+parentId);
		log.info("=id:"+id);
		log.info("=page:"+page);
		
		Integer startNo = null;
		Integer endNo = null;
		
		if (request.getBody().getPager() != null) {
			startNo = request.getBody().getPager().getStartNo();
			endNo = request.getBody().getPager().getEndNo();
		}
		if (startNo == null) {
			startNo = 1;
		}
		if (endNo == null || endNo.intValue() == 0) {
			endNo = 30;
		}
		
		userAgentIncomeRequest.setParentId(id);
		userAgentIncomeRequest.setStartNo(startNo);
		userAgentIncomeRequest.setEndNo(endNo);
		userAgentIncomeRequest.setPage(1);
		
		log.info("=startNo:" + userAgentIncomeRequest.getStartNo());
		log.info("=endNo:" + userAgentIncomeRequest.getEndNo());
		
		Response<UserAgentIncomeResponse> response = new Response<UserAgentIncomeResponse>(request);
		
		int size = userAgentIncomeServiceImpl.getUserAndAgentIncomesCount(userAgentIncomeRequest);
		
		List<UserAgentIncomeVO> list = userAgentIncomeServiceImpl.getUserAndAgentIncomes(userAgentIncomeRequest);
		UserAgentIncomeResponse res = new UserAgentIncomeResponse();
		res.setVos(list);
		response.setResult(res);
		
		ResultPager pager = new ResultPager(++startNo, endNo, size);
		response.setResultPage(pager);

		log.info("获取总代以及下级报表=/fund/getUserAndAgentIncomes End");
		
		return response;

	}

	/**
	 * @Title: getUserBetIncomes
	 * @Description: 用户玩法盈亏
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserBetIncomes")
	@ResponseBody
	public Response<UserGameBettypeIncomeResponse> getUserBetIncomes(
			@RequestBody @ValidRequestBody Request<UserAgentIncomeRequest> request)
			throws Exception {
		log.error("-----------------------------------getUserAndAgentIncomes-----------------------------------------");
		log.debug("获取用户玩法盈亏报表 ......");
		Response<UserGameBettypeIncomeResponse> response = new Response<UserGameBettypeIncomeResponse>(
				request);
		List<UserGameBettypeIncomeGroupDto> list = userAgentIncomeServiceImpl
				.getUserBetIncomes(request.getBody().getParam());
		UserGameBettypeIncomeResponse res = new UserGameBettypeIncomeResponse();
		res.setGroups(list);
		response.setResult(res);
		log.debug("获取用户玩法盈亏报表 。");
		return response;

	}

	/**
	 * @Title: getBetTypes
	 * @Description: 获取玩法列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getBetTypes")
	@ResponseBody
	public Response<GameBetTypeDtoResponse> getBetTypes(
			@RequestBody @ValidRequestBody Request<UserAgentIncomeRequest> request)
			throws Exception {

		log.debug("获取玩法列表 ......");
		Response<GameBetTypeDtoResponse> response = new Response<GameBetTypeDtoResponse>(
				request);
		if (request.getBody().getParam().getLotteryId() != null) {
			List<GameBettypeStatus> list = userAgentIncomeServiceImpl
					.getGameBetTypes(request.getBody().getParam()
							.getLotteryId());
			List<GameBetTypeDto> dtos = new ArrayList<GameBetTypeDto>();
			if (list != null) {
				for (GameBettypeStatus gameBettypeStatus : list) {
					GameBetTypeDto dto = new GameBetTypeDto();
					dto.setGameBetType(gameBettypeStatus.getBetTypeCode());
					dto.setGameBetTypeShow(gameBettypeStatus.getGroupCodeName()
							+ gameBettypeStatus.getSetCodeName()
							+ gameBettypeStatus.getMethodCodeName());
					dtos.add(dto);
				}
			}

			GameBetTypeDtoResponse res = new GameBetTypeDtoResponse();
			res.setDtos(dtos);
			response.setResult(res);

			log.debug("获取玩法列表 。");
		}
		return response;

	}
}
