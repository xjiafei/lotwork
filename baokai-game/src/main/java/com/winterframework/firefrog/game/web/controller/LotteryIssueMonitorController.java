package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
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
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.vo.GameExceptionAuditOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueList;
import com.winterframework.firefrog.game.dao.vo.RiskOrderDetail;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.dao.vo.SpiteOrders;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.entity.LotteryIssueMonitorLogs;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.ILotteryIssueMonitorService;
import com.winterframework.firefrog.game.web.dto.AuditLotteryIssueMonitorRequest;
import com.winterframework.firefrog.game.web.dto.AuditLotteryIssueMonitorResponse;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrderRequest;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrderResponse;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrdersRequest;
import com.winterframework.firefrog.game.web.dto.AuditLotteryMonitorOrdersResponse;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditGameIssueInfoRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditGameIssueInfoRespone;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequest;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditResponse;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditStruc;
import com.winterframework.firefrog.game.web.dto.GameWarnDetailQueryDto;
import com.winterframework.firefrog.game.web.dto.IssueWarnLogStruc;
import com.winterframework.firefrog.game.web.dto.LotteryIssueMonitorLogRequest;
import com.winterframework.firefrog.game.web.dto.LotteryIssueMonitorLogResponse;
import com.winterframework.firefrog.game.web.dto.LotteryIssueStruc;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorDetailResponse;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorIssueDetail;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequestDTO;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListResponse;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorRequest;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorResponse;
import com.winterframework.firefrog.game.web.dto.LotteryNoticesResponse;
import com.winterframework.firefrog.game.web.dto.LotteryNoticesStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryIssueWarnDTO;
import com.winterframework.firefrog.game.web.dto.RiskOrderDetailStruc;
import com.winterframework.firefrog.game.web.dto.RiskOrderStruc;
import com.winterframework.firefrog.game.web.dto.SpiteOrderStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: LotteryMonitorController 
* @Description: 彩种奖期监控
* @author Richard
* @date 2013-10-12 上午10:50:30 
*
 */
@Controller("lotteryIssueMonitorController")
@RequestMapping("/game")
public class LotteryIssueMonitorController {

	private Logger log = LoggerFactory.getLogger(LotteryIssueMonitorController.class);

	@Resource(name = "lotteryIssueMonitorServiceImpl")
	private ILotteryIssueMonitorService lotteryIssueMonitorService;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	private static SimpleDateFormat dateFormat = null;
	private static SimpleDateFormat timeFormat = null;

	@RequestMapping("/lotteryNotices")
	@ResponseBody
	public Response<LotteryNoticesResponse> lotteryNotices(@RequestBody Request<Object> request) throws Exception {

		Response<LotteryNoticesResponse> response = new Response<LotteryNoticesResponse>(request);

		try {

			LotteryNoticesResponse noticesResponse = new LotteryNoticesResponse();
			List<GameWarnIssueEntity> list = lotteryIssueMonitorService.queryGameWarnIssue();

			List<LotteryNoticesStruc> noticesStrucs = new ArrayList<LotteryNoticesStruc>();
			if (null != list) {
				for (GameWarnIssueEntity entity : list) {
					LotteryNoticesStruc struc = new LotteryNoticesStruc();

					struc.setIssueCode(entity.getIssueCode());
					struc.setLotteryid(entity.getLottery().getLotteryId().intValue());
					struc.setLotteryName(entity.getLottery().getLotteryName());
					struc.setStatus(entity.getStatus().getValue());
					struc.setWebIssueCode(entity.getWebIssueCode());

					noticesStrucs.add(struc);
				}
			}

			noticesResponse.setList(noticesStrucs);

			response.setResult(noticesResponse);

		} catch (Exception e) {
			log.error("lotteryNotices error:", e);
			throw e;
		}
		return response;

	}

	/**
	 * 
	* @Title: queryLotteryWarnList 
	* @Description: 5.5.31	彩种奖期监控列表(OMI031) 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryWarnList")
	@ResponseBody
	public Response<LotteryMonitorListResponse> queryLotteryWarnList(
			@RequestBody @ValidRequestBody Request<LotteryMonitorListRequest> request) throws Exception {

		Response<LotteryMonitorListResponse> response = new Response<LotteryMonitorListResponse>(request);
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		timeFormat = new SimpleDateFormat("HH:mm:ss");

		try {

			LotteryMonitorListRequest listRequest = request.getBody().getParam();

			LotteryMonitorListResponse result = new LotteryMonitorListResponse();

			LotteryMonitorListRequestDTO dto = new LotteryMonitorListRequestDTO();
			dto.setEndIssueTime(DataConverterUtil.convertLong2Date(listRequest.getEndIssueTime()));
			dto.setIssueType(listRequest.getIssueType());
			dto.setStartIssueTime(DataConverterUtil.convertLong2Date(listRequest.getStartIssueTime()));
			dto.setLotteryId(listRequest.getLotteryId());

			int sNo = request.getBody().getPager().getStartNo();
			int eNo = request.getBody().getPager().getEndNo();

			PageRequest<LotteryMonitorListRequestDTO> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
			pageRequest.setSearchDo(dto);

			Page<GameWarnIssueList> page = lotteryIssueMonitorService.queryGameWarnList(pageRequest);

			List<LotteryIssueStruc> listStrucs = new ArrayList<LotteryIssueStruc>();

			if (null != page.getResult()) {
				//convert
				for (GameWarnIssueList warn : page.getResult()) {
					LotteryIssueStruc struc = new LotteryIssueStruc();
					struc.setConfirmDrawTime(DataConverterUtil.convertDate2Long(warn.getFactionDrawTime()));
					struc.setIssueCode(warn.getIssueCode());
					struc.setLotteryid(warn.getLotteryid());
					struc.setLotteryName(warn.getLotteryName());
					struc.setNumberRecord(warn.getNumberRecord());
					struc.setOpenDrawTime(DataConverterUtil.convertDate2Long(warn.getOpenDrawTime()));
					struc.setRecivceDrawTime(DataConverterUtil.convertDate2Long(warn.getRecivceDrawTime()));
					struc.setPeriodStatus(warn.getPeriodStatus());
					struc.setSaleDate(dateFormat.format(warn.getSaleEndTime()));
					struc.setSalePeriod(timeFormat.format(warn.getSaleStartTime()) + "-"
							+ timeFormat.format(warn.getSaleEndTime()));
					String desc = GameWarnEvent.getRoutNameByRoutCode(warn.getDes());
					if (desc != null && desc.indexOf("x") > 0 && warn.getRecivceDrawTime() != null) {
						String r = (struc.getOpenDrawTime() - warn.getRecivceDrawTime().getTime()) / 1000 + "";
						desc = desc.replace("x", r);
					}
					struc.setWarnDescStr(desc);
					struc.setPauseStatus(warn.getPauseStatus());

					struc.setWebIssueCode(warn.getWebIssueCode());
					listStrucs.add(struc);
				}
			}

			result.setList(listStrucs);
			response.setResult(result);

			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("queryLotteryWarnList error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryGameExceptionAuditList 
	* @Description:	游戏异常审核列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameExceptionAuditList")
	@ResponseBody
	public Response<GameExceptionAuditResponse> queryGameExceptionAuditList(
			@RequestBody @ValidRequestBody Request<GameExceptionAuditRequest> request) throws Exception {

		Response<GameExceptionAuditResponse> response = new Response<GameExceptionAuditResponse>(request);
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		timeFormat = new SimpleDateFormat("HH:mm:ss");

		try {

			GameExceptionAuditRequest listRequest = request.getBody().getParam();

			GameExceptionAuditResponse result = new GameExceptionAuditResponse();

			GameExceptionAuditRequestDTO dto = new GameExceptionAuditRequestDTO();
			dto.setEndIssueTime(DataConverterUtil.convertLong2Date(listRequest.getEndIssueTime()));
			dto.setStartIssueTime(DataConverterUtil.convertLong2Date(listRequest.getStartIssueTime()));
			dto.setLotteryId(listRequest.getLotteryId());
			dto.setStatus(listRequest.getStatus());
			int sNo = request.getBody().getPager().getStartNo();
			int eNo = request.getBody().getPager().getEndNo();

			PageRequest<GameExceptionAuditRequestDTO> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
			pageRequest.setSearchDo(dto);

			Page<GameExceptionAuditOrder> page = lotteryIssueMonitorService.queryGameWarnAuditList(pageRequest);

			List<GameExceptionAuditStruc> listStrucs = new ArrayList<GameExceptionAuditStruc>();

			if (null != page.getResult()) {
				//convert
				for (GameExceptionAuditOrder warn : page.getResult()) {
					GameExceptionAuditStruc struc = new GameExceptionAuditStruc();
					struc.setConfirmDrawTime(DataConverterUtil.convertDate2Long(warn.getFactionDrawTime()));
					struc.setIssueCode(warn.getIssueCode());
					struc.setLotteryid(warn.getLotteryId());
					struc.setOpenDrawTime(DataConverterUtil.convertDate2Long(warn.getOpenDrawTime()));
					struc.setSaleDate(dateFormat.format(warn.getSaleTime()));
					struc.setWebIssueCode(warn.getWebIssueCode());
					struc.setStatus(warn.getStatus());
					listStrucs.add(struc);
				}
			}

			result.setList(listStrucs);
			response.setResult(result);

			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("queryLotteryWarnList error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryGameExceptionAuditList 
	* @Description:	游戏异常审核列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/getGameExceptionAuditGameIssueInfo")
	@ResponseBody
	public Response<GameExceptionAuditGameIssueInfoRespone> getGameExceptionAuditGameIssueInfo(
			@RequestBody @ValidRequestBody Request<GameExceptionAuditGameIssueInfoRequest> request) throws Exception {

		Response<GameExceptionAuditGameIssueInfoRespone> response = new Response<GameExceptionAuditGameIssueInfoRespone>(
				request);
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		timeFormat = new SimpleDateFormat("HH:mm:ss");

		try {

			GameIssueEntity gie = gameIssueService.queryGameIssue(request.getBody().getParam().getLotteryId(), request
					.getBody().getParam().getIssueCode());

			GameExceptionAuditGameIssueInfoRespone info = new GameExceptionAuditGameIssueInfoRespone();
			info.setConfirmDrawTime(timeFormat.format(gie.getFactionDrawTime()));
			info.setIssueCode(gie.getIssueCode());
			info.setWebIssueCode(gie.getWebIssueCode());
			info.setLotteryid(gie.getLottery().getLotteryId());
			info.setLotteryName(gie.getLottery().getLotteryName());
			if (gie.getSaleStartTime() != null) {
				info.setSaleDate(dateFormat.format(gie.getSaleStartTime()));
			}
			if (gie.getOpenDrawTime() != null) {
				info.setOpenDrawTime(timeFormat.format(gie.getOpenDrawTime()));
			}
			response.setResult(info);
		} catch (Exception e) {
			log.error("getGameExceptionAuditGameIssueInfo error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryLotteryMonitorDetail 
	* @Description: 5.5.32	彩种奖期监控详情(OMI032)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryMonitorGameWarnDetail")
	@ResponseBody
	public Response<LotteryMonitorDetailResponse> queryLotteryMonitorGameWarnOrder(
			@RequestBody @ValidRequestBody Request<LotteryMonitorDetailRequest> request) throws Exception {

		Response<LotteryMonitorDetailResponse> response = new Response<LotteryMonitorDetailResponse>(request);

		try {

			LotteryMonitorDetailRequest detailRequest = request.getBody().getParam();

			GameWarnDetailQueryDto detailQueryDto = new GameWarnDetailQueryDto();
			detailQueryDto.setIssueCode(detailRequest.getIssueCode());
			detailQueryDto.setLotteryId(detailRequest.getLotteryId());

			//			PageRequest<GameWarnDetailQueryDto> pageRequest = new PageRequest<GameWarnDetailQueryDto>();
			int sNo = request.getBody().getPager().getStartNo();
			int eNo = request.getBody().getPager().getEndNo();

			PageRequest<GameWarnDetailQueryDto> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
			pageRequest.setSearchDo(detailQueryDto);

			LotteryMonitorIssueDetail detail = lotteryIssueMonitorService.queryGameWarnDetail(pageRequest);

			LotteryMonitorDetailResponse detailResponse = new LotteryMonitorDetailResponse();

			//convert
			detailResponse.setIssueCode(detail.getIssueCode());
			detailResponse.setLotteryId(detail.getLotteryId());
			detailResponse.setLotteryName(detail.getLotteryName());
			detailResponse.setNumberRecord(detail.getNumberRecord());
			detailResponse.setOtherTypeStr(detail.getOtherTypeStr());
			detailResponse.setWebIssueCode(detail.getWebIssueCode());
			detailResponse.setWarnTypeStr(detail.getWarnTypeStr());
			detailResponse.setSuggestTypeStr(detail.getSuggestTypeStr());
			List<RiskOrderStruc> riskOrderList = new ArrayList<RiskOrderStruc>();

			List<RiskOrderStruc> riskOrderAuditList = new ArrayList<RiskOrderStruc>();

			Page<RiskOrders> page = detail.getPage();

			if (null != page && null != page.getResult()) {
				for (RiskOrders risk : page.getResult()) {

					RiskOrderStruc struc = new RiskOrderStruc();
					struc.setAccount(risk.getAccount());
					struc.setCountWins(risk.getCountWins());
					struc.setIssueCode(risk.getIssueCode());
					struc.setIssueWinsRatio(risk.getIssueWinsRatio());
					struc.setLotteryid(risk.getLotteryid());
					struc.setMaxslipWins(risk.getMaxslipWins());
					struc.setOrderwarnContinuousWins(risk.getOrderwarnContinuousWins());
					struc.setContinuousWinsTimes(risk.getContinuousWinsTimes());
					struc.setUserId(risk.getUserid());

					List<RiskOrderDetailStruc> detailStrucs = new ArrayList<RiskOrderDetailStruc>();

					List<RiskOrderDetailStruc> auditedDetailStrucs = new ArrayList<RiskOrderDetailStruc>();
					if (null != risk.getOrderDetails() && risk.getOrderDetails().size() > 0) {

						for (RiskOrderDetail orderDetail : risk.getOrderDetails()) {

							RiskOrderDetailStruc _struc = new RiskOrderDetailStruc();

							_struc.setChannelId(orderDetail.getChannelId());
							_struc.setChannelVersion(orderDetail.getChannelVersion());
							_struc.setIssueCode(orderDetail.getIssueCode());
							_struc.setLotteryid(orderDetail.getLotteryid());
							_struc.setMaxslipWins(orderDetail.getMaxslipWins());
							_struc.setOrderCode(orderDetail.getOrderCode());
							_struc.setParentType(orderDetail.getParentType());
							_struc.setSaleTime(DataConverterUtil.convertDate2Long(orderDetail.getSaleTime()));
							_struc.setSlipWinsratio(orderDetail.getSlipWinsratio());
							_struc.setStatus(orderDetail.getStatus());
							_struc.setTotamount(orderDetail.getTotamount());
							_struc.setTotwin(orderDetail.getTotwin());
							_struc.setUserId(orderDetail.getUserid());
							_struc.setWinsRatio(orderDetail.getWinsRatio());
							_struc.setCountWin(orderDetail.getCountWin());
							_struc.setOrderId(orderDetail.getOrderId());

							if (orderDetail.getStatus() == 0) {//待审核
								detailStrucs.add(_struc);
							} else {
								auditedDetailStrucs.add(_struc);
							}

						}
					}

					if (!detailStrucs.isEmpty()) {
						struc.setRiskOrderDetailStrucs(detailStrucs);
						riskOrderList.add(struc);
					}
					if (!auditedDetailStrucs.isEmpty()) {
						struc.setRiskOrderDetailStrucs(auditedDetailStrucs);
						riskOrderAuditList.add(struc);
					}
				}

			}
			detailResponse.setRiskOrderList(riskOrderList);
			detailResponse.setRiskOrderAuditedList(riskOrderAuditList);

			List<SpiteOrderStruc> spiteOrderStrucs = new ArrayList<SpiteOrderStruc>();

			if (null != detail.getSpiteOrderList()) {
				for (SpiteOrders spite : detail.getSpiteOrderList()) {

					SpiteOrderStruc struc = new SpiteOrderStruc();

					struc.setAccount(spite.getAccount());
					struc.setIssueCode(spite.getIssueCode());
					struc.setLotteryid(spite.getLotteryid());
					struc.setOrderCode(spite.getOrderCode());
					struc.setStatus(spite.getStatus().toString());
					struc.setTotamount(spite.getTotamount());
					struc.setUserId(spite.getUserid());
					struc.setSaleTime(DataConverterUtil.convertDate2Long(spite.getSaleTime()));
					struc.setUserStatus(spite.getIsFreeze());
					struc.setOrderId(spite.getOrderId());

					spiteOrderStrucs.add(struc);
				}
			}
			detailResponse.setSpiteOrderList(spiteOrderStrucs);

			detailResponse.setSuggestTypeStr(detail.getSuggestTypeStr());
			detailResponse.setWarnParas(detail.getWarnParas());
			detailResponse.setWarnTypeStr(detail.getWarnTypeStr());
			detailResponse.setStatus(detail.getStatus());
			detailResponse.setIsCanCanel(detail.getIsCanCanel());
			detailResponse.setIsAfterSaleEndTime(detail.getIsAfterSaleEndTime());
			response.setResult(detailResponse);

			if (null != page) {
				ResultPager pager = new ResultPager();
				pager.setEndNo(page.getThisPageLastElementNumber());
				pager.setStartNo(page.getThisPageFirstElementNumber());
				pager.setTotal(page.getTotalCount());
				response.setResultPage(pager);
			}

		} catch (Exception e) {
			log.error("queryLotteryMonitorGameWarnOrder error:", e);

			throw e;
		}

		return response;
	}

	/**
	 * 
	* @Title: queryLotteryMonitorOrderDetail 
	* @Description: 5.5.33	彩种奖期监控-风险详情(OMI033)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryLotteryMonitorOrderDetail")
	@ResponseBody
	public Response<LotteryMonitorResponse> queryLotteryMonitorOrderDetail(
			@RequestBody @ValidRequestBody Request<LotteryMonitorRequest> request) throws Exception {

		Response<LotteryMonitorResponse> response = new Response<LotteryMonitorResponse>(request);

		try {
			LotteryMonitorRequest lotteryMonitorRequest = request.getBody().getParam();
			LotteryMonitorResponse result = new LotteryMonitorResponse();

			List<GameWarnOrderEntity> list = lotteryIssueMonitorService.queryGameWarnOrderDetail(
					lotteryMonitorRequest.getLotteryId(), lotteryMonitorRequest.getIssueCode());

			List<RiskOrderDetailStruc> detailStrucs = new ArrayList<RiskOrderDetailStruc>();

			if (null != list && list.size() > 0) {

				for (GameWarnOrderEntity entity : list) {
					RiskOrderDetailStruc struc = new RiskOrderDetailStruc();

					struc.setChannelId(entity.getChannelId().intValue());
					struc.setIssueCode(entity.getIssueCode());
					struc.setLotteryid(entity.getLottery().getLotteryId());
					struc.setMaxslipWins(entity.getMaxslipWins());
					struc.setOrderCode(entity.getOrderCode());
					struc.setParentType(entity.getParantType().getValue());
					struc.setSaleTime(DataConverterUtil.convertDate2Long(entity.getSaleTime()));
					struc.setSlipWinsratio(entity.getSlipWinsratio());
					struc.setStatus(entity.getStatus().getValue());
					struc.setTotamount(entity.getCountWin());
					struc.setTotwin(entity.getMaxslipWins());
					struc.setUserId(entity.getUser().getId());
					struc.setWinsRatio(entity.getWinsRatio());

					detailStrucs.add(struc);
				}
			}
			result.setList(detailStrucs);

			response.setResult(result);

		} catch (Exception e) {
			log.error("queryLotteryMonitorOrderDetail error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: lotteryIssueMonitorLog 
	* @Description: 5.5.34	彩种奖期监控日志(OMI034)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/lotteryIssueMonitorLog")
	@ResponseBody
	public Response<LotteryIssueMonitorLogResponse> lotteryIssueMonitorLog(
			@RequestBody @ValidRequestBody Request<LotteryIssueMonitorLogRequest> request) throws Exception {

		Response<LotteryIssueMonitorLogResponse> response = new Response<LotteryIssueMonitorLogResponse>(request);
		try {

			LotteryIssueMonitorLogResponse issueMonitorLogResponse = new LotteryIssueMonitorLogResponse();

			LotteryIssueMonitorLogRequest logRequest = request.getBody().getParam();

			QueryLotteryIssueWarnDTO searchDao = new QueryLotteryIssueWarnDTO();
			searchDao.setEndCreateTime(DataConverterUtil.convertLong2Date(logRequest.getEndCreateTime()));
			searchDao.setLotteryid(logRequest.getLotteryid());
			searchDao.setStartCreateTime(DataConverterUtil.convertLong2Date(logRequest.getStartCreateTime()));
			searchDao.setWarnType(logRequest.getWarnType());

			int sNo = request.getBody().getPager().getStartNo();
			int eNo = request.getBody().getPager().getEndNo();

			PageRequest<QueryLotteryIssueWarnDTO> pageRequest = PageConverterUtils.getRestPageRequest(sNo, eNo);
			pageRequest.setSearchDo(searchDao);

			Page<LotteryIssueMonitorLogs> page = lotteryIssueMonitorService.queryLotteryIssueWarnLog(pageRequest);

			List<IssueWarnLogStruc> issueWarnLogStrucs = new ArrayList<IssueWarnLogStruc>();
			if (null != page.getResult()) {
				for (LotteryIssueMonitorLogs logs : page.getResult()) {
					IssueWarnLogStruc struc = new IssueWarnLogStruc();

					struc.setCreateTime(DataConverterUtil.convertDate2Long(logs.getCreateTime()));
					struc.setDisposeInfo(logs.getDisposeInfo());
					struc.setDisposeMemo(logs.getDisposeMemo());
					struc.setDisposeUser(logs.getDisposeUser());
					struc.setDoingMemo(logs.getDoingMemo());
					struc.setLotteryName(logs.getLotteryName());
					struc.setStatus(GameWarnEvent.getTypeByCode(logs.getWarnDes()));

					struc.setWarnTypeName(GameWarnEvent.getNameByCode(logs.getWarnDes()));
					struc.setWebIssueCode(logs.getWebIssueCode());
					struc.setWarnDes(GameWarnEvent.getMessageByCode(logs.getWarnDes()));

					GameIssueEntity issue=null;
					try{
					 issue = gameIssueService.getGameIssue(logs.getLotteryId(), logs.getWebIssueCode());
					}catch(Exception e){
						log.error("query issue warn logs issuecode error:", e);
					}
					if (issue != null) {
						String desc = GameWarnEvent.getMessageByCode(logs.getWarnDes());
						if (desc != null && desc.indexOf("x") > 0 && issue.getRecivceDrawTime() != null) {
							String r = (issue.getOpenDrawTime().getTime() - issue.getRecivceDrawTime().getTime())
									/ 1000 + "";
							desc = desc.replace("x", r);
						}
						struc.setWarnDes(desc);

						String info = logs.getDisposeInfo();
						if (info != null && info.indexOf("x") > 0 && issue.getRecivceDrawTime() != null) {
							String r = (issue.getOpenDrawTime().getTime() - issue.getRecivceDrawTime().getTime())
									/ 1000 + "";
							info = desc.replace("x", r);
						}
						struc.setDisposeInfo(info);

						String memo = logs.getDisposeMemo();
						if (memo != null && memo.indexOf("x") > 0 && issue.getRecivceDrawTime() != null) {
							String r = (issue.getOpenDrawTime().getTime() - issue.getRecivceDrawTime().getTime())
									/ 1000 + "";
							memo = desc.replace("x", r);
						}
						struc.setDisposeMemo(memo);

						String dmemo = logs.getDoingMemo();
						if (dmemo != null && dmemo.indexOf("x") > 0 && issue.getRecivceDrawTime() != null) {
							String r = (issue.getOpenDrawTime().getTime() - issue.getRecivceDrawTime().getTime())
									/ 1000 + "";
							dmemo = desc.replace("x", r);
						}
						struc.setDoingMemo(dmemo);

						String type = GameWarnEvent.getNameByCode(logs.getWarnDes());
						if (type != null && type.indexOf("x") > 0 && issue.getRecivceDrawTime() != null) {
							String r = (issue.getOpenDrawTime().getTime() - issue.getRecivceDrawTime().getTime())
									/ 1000 + "";
							type = desc.replace("x", r);
						}
						struc.setWarnTypeName(type);
					}
					issueWarnLogStrucs.add(struc);
				}
			}
			issueMonitorLogResponse.setList(issueWarnLogStrucs);
			response.setResult(issueMonitorLogResponse);

			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);
		} catch (Exception e) {
			log.error("lotteryIssueMonitorLog error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: auditIssue 
	* @Description:5.5.43	彩种奖期监控-风险方案审核(OMI043)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/auditIssue")
	@ResponseBody
	public Response<AuditLotteryIssueMonitorResponse> auditIssue(
			@RequestBody @ValidRequestBody Request<AuditLotteryIssueMonitorRequest> request) throws Exception {

		Response<AuditLotteryIssueMonitorResponse> response = new Response<AuditLotteryIssueMonitorResponse>(request);

		try {

			AuditLotteryIssueMonitorRequest auditLotteryIssueMonitorRequest = request.getBody().getParam();

			lotteryIssueMonitorService.auditLotteryIssueMonitor(auditLotteryIssueMonitorRequest.getLotteryId(),
					auditLotteryIssueMonitorRequest.getIssueCode());

		} catch (Exception e) {
			log.error("auditIssue error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: auditOrder 
	* @Description: 5.5.44	彩种奖期监控-订单审核(OMI044)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/auditOrder")
	@ResponseBody
	public Response<AuditLotteryMonitorOrderResponse> auditOrder(
			@RequestBody @ValidRequestBody Request<AuditLotteryMonitorOrderRequest> request) throws Exception {

		Response<AuditLotteryMonitorOrderResponse> response = new Response<AuditLotteryMonitorOrderResponse>(request);

		try {

			AuditLotteryMonitorOrderRequest audit = request.getBody().getParam();
			lotteryIssueMonitorService.auditLotteryOrderMonitor(audit.getOrderCode(), audit.getStatus());

		} catch (Exception e) {
			log.error("auditOrder error:", e);
			throw e;
		}

		return response;

	}

	/**
	 * 
	* @Title: auditOrder 
	* @Description: 5.5.44	彩种奖期监控-订单批量审核(OMI044)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/auditOrders")
	@ResponseBody
	public Response<AuditLotteryMonitorOrdersResponse> auditOrders(
			@RequestBody @ValidRequestBody Request<AuditLotteryMonitorOrdersRequest> request) throws Exception {

		Response<AuditLotteryMonitorOrdersResponse> response = new Response<AuditLotteryMonitorOrdersResponse>(request);

		try {

			AuditLotteryMonitorOrdersRequest audit = request.getBody().getParam();
			lotteryIssueMonitorService.auditLotteryOrdersMonitor(audit.getOrderCodes());

		} catch (Exception e) {
			log.error("auditOrder error:", e);
			throw e;
		}

		return response;

	}

}
