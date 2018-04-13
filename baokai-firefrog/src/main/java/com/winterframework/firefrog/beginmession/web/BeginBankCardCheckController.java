package com.winterframework.firefrog.beginmession.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.entity.BeginBankCardCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.firefrog.beginmession.service.BeginBankCardCheckService;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.beginmession.web.dto.BackendCancelMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginBankCardCheckResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListDto;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginCheckDataRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginFileUploadStatusRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginFileUploadStatusResponse;
import com.winterframework.firefrog.beginmession.web.dto.BeginUpdateStatusRequest;
import com.winterframework.firefrog.beginmession.web.dto.CancelListDto;
import com.winterframework.firefrog.beginmession.web.dto.DTOConverter;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller
@RequestMapping("/begin")
public class BeginBankCardCheckController {

	private static final Logger log = LoggerFactory
			.getLogger(BeginBankCardCheckController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Autowired
	private BeginBankCardCheckService beginBankCardCheckService;

	@Autowired
	private BeginMissionService beginMissionService;

	@Autowired
	private IUserProfileService userProfileService;

	@RequestMapping("/searchCheckData")
	@ResponseBody
	public Response<BeginBankCardCheckResponse> searchCheckData(
			@RequestBody @ValidRequestBody Request<BeginCheckDataRequest> request)
			throws Exception {
		Response<BeginBankCardCheckResponse> response = new Response<BeginBankCardCheckResponse>(
				request);
		log.info("-----------searchCheckData-------------");
		log.info("pageNo : " + request.getBody().getParam().getPageNo());
		log.info("status : " + request.getBody().getParam().getStatus());
		log.info("timeStart : " + request.getBody().getParam().getTimeStart());
		log.info("timeEnd : " + request.getBody().getParam().getTimeEnd());
		try {
			if (null != request.getBody()) {
				PageRequest<BeginCheckData> pageRequest = PageConverterUtils
						.getRestPageRequest(request.getBody().getPager()
								.getStartNo(), request.getBody().getPager()
								.getEndNo());
				BeginCheckDataRequest req = request.getBody().getParam();

				BeginCheckData convertData = DTOConverter
						.transCheckData2BeginCheckData(req);
				pageRequest.setSearchDo(convertData);
				pageRequest.setSortColumns("c.REGISTER_DATE DESC");

				Page<BeginSearchCheckData> checks = beginBankCardCheckService
						.queryBankCardChecksByCondition(pageRequest);
				List<BeginBankCardCheckData> transferData = DTOConverter
						.transBeginCheckData2BeginSearchCheckData(checks
								.getResult());
				BeginBankCardCheckResponse bcres = new BeginBankCardCheckResponse();

				if (req.getTimeStart() == null || req.getTimeEnd() == null) {
					bcres.setTimeStart(DATE_FORMAT.format(DateUtils
							.getStartTimeOfDate(new Date())));
					bcres.setTimeEnd(DATE_FORMAT.format(DateUtils
							.getEndTimeOfDate(new Date())));
				} else {
					bcres.setTimeStart(DATE_FORMAT.format(new Date(req.getTimeStart())));
					bcres.setTimeEnd(DATE_FORMAT.format(new Date(req.getTimeEnd())));
				}
				bcres.setCheckDatas(transferData);
				ResultPager pager = new ResultPager();
				pager.setStartNo(checks.getThisPageFirstElementNumber());
				pager.setEndNo(checks.getThisPageLastElementNumber());
				pager.setTotal(checks.getTotalCount());
				response.setResultPage(pager);
				response.setResult(bcres);
			}
		} catch (Exception e) {
			log.error("searchCheckData error:", e);
			throw e;
		}
		return response;
	}

	@RequestMapping("/searchCheckDataNoPage")
	@ResponseBody
	public Response<BeginBankCardCheckResponse> searchCheckDataNoPage(
			@RequestBody @ValidRequestBody Request<BeginCheckDataRequest> request)
			throws Exception {
		Response<BeginBankCardCheckResponse> response = new Response<BeginBankCardCheckResponse>(
				request);
		try {
			log.info("-------------searchCheckDataNoPage");
			if (null != request.getBody()) {
				BeginCheckDataRequest req = request.getBody().getParam();
				BeginCheckData convertData = DTOConverter
						.transCheckData2BeginCheckData(req);

				List<BeginSearchCheckData> checks = beginBankCardCheckService
						.queryCheckDataAllByCondition(convertData);
				List<BeginBankCardCheckData> transferData = DTOConverter
						.transBeginCheckData2BeginSearchCheckData(checks);
				BeginBankCardCheckResponse bcres = new BeginBankCardCheckResponse();
				bcres.setCheckDatas(transferData);
				response.setResult(bcres);
			}
		} catch (Exception e) {
			log.error("searchCheckData error:", e);
			throw e;
		}

		return response;

	}

	@RequestMapping(value = "/queryCancelList")
	@ResponseBody
	public Response<BeginCancelListResponse> queryCancelList(
			@RequestBody @ValidRequestBody Request<BeginCancelListRequest> request)
			throws Exception {
		Response<BeginCancelListResponse> response = new Response<BeginCancelListResponse>(
				request);
		log.info("into queryCancelList ");
		try {
			if (null != request.getBody()) {
				log.debug("startNo : "
						+ request.getBody().getPager().getStartNo());
				log.debug("endNo : " + request.getBody().getPager().getEndNo());
				log.debug("getTimeStart : "
						+ request.getBody().getParam().getTimeStart());
				log.debug("getTimeEnd : "
						+ request.getBody().getParam().getTimeEnd());

				PageRequest<BeginCancelListDto> pageRequest = PageConverterUtils
						.getRestPageRequest(request.getBody().getPager()
								.getStartNo(), request.getBody().getPager()
								.getEndNo());
				BeginCancelListRequest req = request.getBody().getParam();
				List<Long> statusList = new ArrayList<Long>();
				statusList.add(Status.INVALID.getValue());
				statusList.add(Status.CANCEL.getValue());
				BeginCancelListDto dto = DTOConverter
						.transBeginCancelList2CancelListDto(req);
				dto.setStatusList(statusList);
				pageRequest.setSearchDo(dto);
				pageRequest.setSortColumns("b.CANCEL_TIME DESC");

				Page<CancelListData> cancelList = beginBankCardCheckService
						.searchCancelList(pageRequest);
				List<CancelListDto> transferData = DTOConverter
						.transBeginMission2CancleListData(cancelList
								.getResult());
				BeginCancelListResponse clres = new BeginCancelListResponse();

				if (req.getTimeStart() == null || req.getTimeEnd() == null) {
					clres.setTimeStart(DATE_FORMAT.format(DateUtils
							.getStartTimeOfDate(new Date())));
					clres.setTimeEnd(DATE_FORMAT.format(DateUtils
							.getEndTimeOfDate(new Date())));
				} else {
					clres.setTimeStart(DATE_FORMAT.format(new Date(req.getTimeStart())));
					clres.setTimeEnd(DATE_FORMAT.format(new Date(req.getTimeEnd())));
				}
				clres.setCancelDatas(transferData);
				ResultPager pager = new ResultPager();
				pager.setStartNo(cancelList.getThisPageFirstElementNumber());
				pager.setEndNo(cancelList.getThisPageLastElementNumber());
				pager.setTotal(cancelList.getTotalCount());
				response.setResultPage(pager);
				response.setResult(clres);
			}
		} catch (Exception e) {
			log.error("queryCancelList error:", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/queryCancelListNoPage")
	@ResponseBody
	public Response<BeginCancelListResponse> queryCancelListNoPage(
			@RequestBody @ValidRequestBody Request<BeginCancelListRequest> request)
			throws Exception {
		Response<BeginCancelListResponse> response = new Response<BeginCancelListResponse>(
				request);
		log.info("into queryCancelList ");
		try {
			if (null != request.getBody()) {
				BeginCancelListRequest req = request.getBody().getParam();
				List<Long> statusList = new ArrayList<Long>();
				statusList.add(Status.INVALID.getValue());
				statusList.add(Status.CANCEL.getValue());
				BeginCancelListDto dto = DTOConverter
						.transBeginCancelList2CancelListDto(req);
				dto.setStatusList(statusList);

				List<CancelListData> cancelList = beginBankCardCheckService
						.searchCancelListAll(dto);
				List<CancelListDto> transferData = DTOConverter
						.transBeginMission2CancleListData(cancelList);
				BeginCancelListResponse clres = new BeginCancelListResponse();
				clres.setCancelDatas(transferData);
				response.setResult(clres);
			}
		} catch (Exception e) {
			log.error("queryCancelList error:", e);
			throw e;
		}
		return response;
	}

	@RequestMapping("/updateCheckStatus")
	@ResponseBody
	public void updateCheckStatus(
			@RequestBody @ValidRequestBody Request<BeginUpdateStatusRequest> request)
			throws Exception {
		try {
			if (null != request.getBody()) {
				final String checkUser = request.getBody().getParam()
						.getCurUser();
				Map<Long, String> userids = (Map<Long, String>) request
						.getBody().getParam().getData();
				if (userids != null && userids.size() > 0) {
					beginBankCardCheckService.updateCheckStatus(userids,
							checkUser);
				}
			}
		} catch (Exception e) {
			log.error("updateCheckStatus error:", e);
			throw e;
		}
	}

	@RequestMapping("/backendCancelMission")
	@ResponseBody
	public Response<Object> backendCancelMission(
			@RequestBody @ValidRequestBody Request<BackendCancelMissionRequest> request)
			throws Exception {
		log.debug("------------------------backendCancelMission");
		Response<Object> res = new Response<Object>();
		try {
			if (null != request.getBody()) {
				final String checkUser = request.getBody().getParam()
						.getCanceluser();
				Integer result = beginBankCardCheckService.backendCancelMission(checkUser,request
						.getBody().getParam().getAccount(), request.getBody()
						.getParam().getReason());
				res.setResult(result);
			}
		} catch (Exception e) {
			log.error("backendCancelMission error:", e);
			res.setResult(0);
		}
		return res;
	}

	@RequestMapping("/fileUploadByStatus")
	@ResponseBody
	public Response<BeginFileUploadStatusResponse> fileUploadByStatus(
			@RequestBody @ValidRequestBody Request<BeginFileUploadStatusRequest> request)
			throws Exception {
		Response<BeginFileUploadStatusResponse> response = new Response<BeginFileUploadStatusResponse>(
				request);
		log.info("------------------------fileUploadByStatus");
		List<String> nonAccounts = new ArrayList<String>();
		BeginFileUploadStatusResponse result = new BeginFileUploadStatusResponse();
		try {
			if (null != request.getBody()) {
				final String checkUser = request.getBody().getParam()
						.getCurUser();
				List<String> accounts = (List<String>) request.getBody()
						.getParam().getAccounts();
				String status = "Y";
				if ("N".equals(request.getBody().getParam().getStatus())) {
					status = "N";
				}

				if (accounts != null && accounts.size() > 0) {
					nonAccounts = beginBankCardCheckService
							.batchUpdateCheckStatus(accounts, checkUser, status);
				}
			}

			result.setStatus(1);
			if (nonAccounts.size() > 0 && !nonAccounts.isEmpty()) {
				result.setNonAccount(nonAccounts);
			}
		} catch (Exception e) {
			log.error("updateCheckStatus error:", e);
			result.setStatus(0);

		}
		response.setResult(result);
		return response;

	}
}
