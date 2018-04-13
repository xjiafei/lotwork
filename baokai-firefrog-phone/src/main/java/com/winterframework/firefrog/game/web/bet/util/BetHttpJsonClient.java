package com.winterframework.firefrog.game.web.bet.util;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.httpjsonclient.impl.HttpJsonClientImpl;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardRequest;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardResponse;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeRequest;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeResponse;
import com.winterframework.firefrog.game.web.dto.DailyActivityRequest;
import com.winterframework.firefrog.game.web.dto.DailyActivityResponse;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeRequest;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeResponse;
import com.winterframework.firefrog.game.web.dto.GameColdAndHotNumbersQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLrylQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameMissingValueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanResponse;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.GetAllBettypeStatusResponse;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberResponse;
import com.winterframework.firefrog.game.web.dto.GetLuckyRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyResponse;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionRequest;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.SaveProxyBetGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.phone.web.controller.SLMMCController;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoAbortRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoAbortResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

public class BetHttpJsonClient {
	
	@Resource(name = "httpJsonClientImpl")
	protected IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.connect.chart")
	private String chartServerPath;

	@PropertyConfig(value = "url.game.saveGameOrder")
	private String saveGameOrder;

	@PropertyConfig(value = "url.game.saveGamePlan")
	private String saveGamePlan;

	@PropertyConfig(value = "url.game.getCurrentGameIssue")
	private String currentGameIssue;

	@PropertyConfig(value = "url.game.queryBetLimitByBet")
	private String queryBetLimit;

	@PropertyConfig(value = "url.game.queryMaxGameIssue")
	private String queryMaxGameIssue;

	@PropertyConfig(value = "url.game.queryOrders")
	private String queryOrders;

	@PropertyConfig(value = "url.game.queryPlans")
	private String queryPlansUrl;

	@PropertyConfig(value = "url.game.queryTraceGameIssues")
	private String queryTraceGameIssues;

	@PropertyConfig(value = "url.game.handlingCharge")
	private String handlingCharge;

	@PropertyConfig(value = "url.game.queryUserBal")
	private String queryUserBal;

	@PropertyConfig(value = "url.query.gameValidBetMethods")
	private String gameValidBetMethods;

	@PropertyConfig(value = "url.game.queryAssistAction")
	private String queryAssistActionUrl;

	@PropertyConfig(value = "url.game.queryDescByBetMethodByUserId")
	private String queryBetMethodDescUrl;

	@PropertyConfig(value = "url.game.queryMissingValue")
	private String queryMissingValueUrl;

	@PropertyConfig(value = "url.game.queryColdAndHotNumbers")
	private String queryColdAndHotNumbersUrl;

	@PropertyConfig(value = "url.game.queryGameUserAwardGroupByLotteryId")
	private String queryGameUserAwardGroupByLotteryIdUrl;

	@PropertyConfig(value = "url.game.queryLotteryGameUserAwardGroupByLotteryIdAndUserId")
	private String queryLotteryGameUserAwardGroupByLotteryIdAndUserIdUrl;

	@PropertyConfig(value = "url.game.saveProxyBetGameAwardGroup")
	private String saveProxyBetGameAwardGroupUrl;

	@PropertyConfig(value = "url.operations.queryGameSeriesConfig")
	private String queryGameSeriesConfigUrl;

	@PropertyConfig(value = "url.game.queryOrderDetail")
	private String queryOrderDetail;

	@PropertyConfig(value = "url.game.getAllBettypeStatus")
	private String getAllBettypeStatus;
	
	@PropertyConfig(value = "url.game.queryLotteryList")
	private String queryLotteryListUrl;
	
	@PropertyConfig("url.game.getUserDailyActivityStruc")
	private String getUserDailyActivityStrucUrl;
	
	@PropertyConfig("url.game.getDailyBetPrize")
	private String getDailyBetPrizeUrl;
	
	@PropertyConfig("url.game.getLucky")
	private String getLuckyUrl;
	
	@PropertyConfig("url.game.getLuckyNumber")
	private String getLuckyNumberUrl;
	
	@PropertyConfig("url.game.queryUserAwardLog")
	private String queryUserAwardLogUrl;
	
	@PropertyConfig("url.game.queryTodayUserAwardLog")
	private String queryTodayUserAwardLogUrl;

	@PropertyConfig("url.sheepyear.getUserHongBaoList")
	private String getUserHongBaoListUrl;
	
	@PropertyConfig("url.sheepyear.applyUserHongBao")
	private String applyUserHongBaoUrl;
	
	@PropertyConfig("url.sheepyear.abortUserHongBao")
	private String abortUserHongBaoUrl;
	
	@PropertyConfig("url.sheepyear.drawUserHongBao")
	private String drawUserHongBaoUrl;
	
	@PropertyConfig("url.sheepyear.getUserDice")
	private String getUserDiceUrl;
	
	@PropertyConfig("url.sheepyear.getUserDiceAward")
	private String getUserDiceAwardUrl;
	
	@PropertyConfig("url.sheepyear.getUserRotaryAward")
	private String getUserRotaryAwardUrl;
	
	@PropertyConfig("url.sheepyear.getUserRotary")
	private String getUserRotaryUrl;
	
	@PropertyConfig("url.sheepyear.getUserInfo")
	private String getUserInfoUrl;
	
	@PropertyConfig("url.game.getGameIssueNumberRecord")
	private String getGameIssueNumberRecordUrl;
	

	public Response<CancelOrderChargeResponse> getCancelOrderCharge(CancelOrderChargeRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + handlingCharge, request, CancelOrderChargeResponse.class);
	}

	public Response<GameIssueQueryResponse> getCurrentGameIssue(GameIssueQueryRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + currentGameIssue, request, GameIssueQueryResponse.class);
	}

	public Response<TraceGameIssueListQueryResponse> getTraceGameIssues(TraceGameIssueListQueryRequest request)
			throws Exception {
		return httpClient.invokeHttp(serverPath + queryTraceGameIssues, request, TraceGameIssueListQueryResponse.class);
	}

	public Response<BetLimitQueryResponse> getBetLimit(BetLimitQueryByBetRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryBetLimit, request, BetLimitQueryResponse.class);
	}

	public Response<Long> getMaxGameIssue(Long request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryMaxGameIssue, request, Long.class);
	}

	public Response<GameOrderQueryResponse> getGameOrders(GameOrderQueryRequest request, Long userId, Pager pager)
			throws Exception {
		return httpClient.invokeHttp(serverPath + queryOrders, request, pager, userId, null,
				GameOrderQueryResponse.class);
	}

	public Response<GamePlanQueryResponse> getGamePlans(GamePlanQueryRequest request, Long userId, Pager pager)
			throws Exception {
		return httpClient.invokeHttp(serverPath + queryPlansUrl, request, pager, userId, null,
				GamePlanQueryResponse.class);
	}

	public Response<GameOrderResponse> saveGameOrder(GameOrderRequest request, Long userId, String account)
			throws Exception {
		IHttpJsonClient httpClient1 = new HttpJsonClientImpl();
		return httpClient1.invokeHttp(serverPath + saveGameOrder, request, userId, account, GameOrderResponse.class);
	}

	public Response<GamePlanResponse> saveGamePlan(GamePlanRequest request, Long userId, String account)
			throws Exception {
		return httpClient.invokeHttp(serverPath + saveGamePlan, request, userId, account, GamePlanResponse.class);
	}

	public Response<Long> getUserBal(Long userId) throws Exception {
		return httpClient.invokeHttp(serverPath + queryUserBal, userId, Long.class);
	}

	public Response<BetMethodValidListQueryResponse> getValidBetMethodList(BetMethodValidListQueryRequest request)
			throws Exception {
		return httpClient.invokeHttp(serverPath + gameValidBetMethods, request, BetMethodValidListQueryResponse.class);
	}

	public Response<QueryAssistActionResponse> getQueryAssistAction(QueryAssistActionRequest request) throws Exception {
		return httpClient.invokeHttp(chartServerPath + queryAssistActionUrl, request, QueryAssistActionResponse.class);
	}

	public Response<QueryDescByBetMethodByUserIdResponse> QueryDescByBetMethodByUserId(
			QueryDescByBetMethodByUserIdRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryBetMethodDescUrl, request,
				QueryDescByBetMethodByUserIdResponse.class);
	}

	public Response<GameLrylQueryResponse> queryMissingValue(GameMissingValueQueryRequest request) throws Exception {
		return httpClient.invokeHttp(chartServerPath + queryMissingValueUrl, request, GameLrylQueryResponse.class);
	}

	public Response<GameLrylQueryResponse> queryColdAndHotNumbers(GameColdAndHotNumbersQueryRequest request)
			throws Exception {
		return httpClient.invokeHttp(chartServerPath + queryColdAndHotNumbersUrl, request, GameLrylQueryResponse.class);
	}

	public Response<GameUserBetAwardGroupResponse> queryGameUserAwardGroupByLotteryId(
			GameUserBetAwardGroupRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryGameUserAwardGroupByLotteryIdUrl, request,
				GameUserBetAwardGroupResponse.class);
	}

	public Response<LotteryGameUserAwardGroupQueryResponse> getUserArwardGroupsByLotteryIdAndUserId(
			LotteryGameUserAwardGroupQueryRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryLotteryGameUserAwardGroupByLotteryIdAndUserIdUrl, request,
				LotteryGameUserAwardGroupQueryResponse.class);
	}

	public Response saveProxyBetGameAwardGroup(SaveProxyBetGameAwardGroupRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + saveProxyBetGameAwardGroupUrl, request, String.class);

	}

	public Response<QuerySeriesConfigResponse> getQuerySeriesConfig(QuerySeriesConfigRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryGameSeriesConfigUrl, request, QuerySeriesConfigResponse.class);
	}

	public Response<GameOrderDetailQueryResponse> getOrderDetai(GameOrderDetailQueryRequest queryRequest)
			throws Exception {
		return httpClient.invokeHttp(serverPath + queryOrderDetail, queryRequest, RequestContext.getCurrUser().getId(),
				RequestContext.getCurrUser().getUserName(), GameOrderDetailQueryResponse.class);
	}

	public Response<GetAllBettypeStatusResponse> getAllBettypeStatus() throws Exception {
		return httpClient.invokeHttp(serverPath + getAllBettypeStatus,
				new TypeReference<Response<GetAllBettypeStatusResponse>>() {
				});
	}
	
	public Response<GameSeriesResponse> querySeries(GameSeriesRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + queryLotteryListUrl, request, GameSeriesResponse.class);
	}

	public Response<DailyActivityResponse> getUserDailyActivityStruc(
			DailyActivityRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserDailyActivityStrucUrl, request, DailyActivityResponse.class);
	}

	public Response<DailyBetPrizeResponse> getDailyBetPrize(
			DailyBetPrizeRequest request) throws Exception{
		return httpClient.invokeHttp(serverPath + getDailyBetPrizeUrl, request, DailyBetPrizeResponse.class);
	}
	
	public Response<GetLuckyResponse> getLucky(
			GetLuckyRequest request) throws Exception{
		return httpClient.invokeHttp(serverPath + getLuckyUrl, request, GetLuckyResponse.class);
	}
	
	public Response<GetLuckyNumberResponse> getLuckyNumber(
			GetLuckyNumberRequest request) throws Exception{
		return httpClient.invokeHttp(serverPath + getLuckyNumberUrl, request, GetLuckyNumberResponse.class);
	}

	public Response<ActivityUserAwardResponse> queryTodayUserAwardLog(
			ActivityUserAwardRequest request) throws Exception{
		return httpClient.invokeHttp(serverPath + queryTodayUserAwardLogUrl, request, ActivityUserAwardResponse.class);
	}

	public Response<ActivityUserAwardResponse> queryMyUserAwardLog(
			ActivityUserAwardRequest request)throws Exception {
		return httpClient.invokeHttp(serverPath + queryUserAwardLogUrl, request, ActivityUserAwardResponse.class);
	}

	public Response<ActivitySheepUserHongBaoResponse> getUserHongBaoList(ActivitySheepUserHongBaoRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserHongBaoListUrl, request, ActivitySheepUserHongBaoResponse.class);
		
	}

	public Response<ActivitySheepUserApplyHongBaoResponse> applyHongbaoAmount(
			ActivitySheepUserApplyHongBaoRequest applyRequest) throws Exception {
		return httpClient.invokeHttp(serverPath + applyUserHongBaoUrl, applyRequest, ActivitySheepUserApplyHongBaoResponse.class);

	}

	public Response<ActivitySheepUserHongBaoAbortResponse> aborthongbao(ActivitySheepUserHongBaoAbortRequest abortRequest) throws Exception {
		return httpClient.invokeHttp(serverPath + abortUserHongBaoUrl, abortRequest,ActivitySheepUserHongBaoDrawResponse.class);
		
	}

	public Response<ActivitySheepUserHongBaoDrawResponse> drawHongbao(
			ActivitySheepUserHongBaoDrawRequest drawRequest) throws Exception {
		return httpClient.invokeHttp(serverPath + drawUserHongBaoUrl, drawRequest, ActivitySheepUserHongBaoDrawResponse.class);

	}

	public Response<ActivitySheepUserDiceResponse> getUserDiceData(
			ActivitySheepUserDiceRequest diceRequest) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserDiceUrl, diceRequest, ActivitySheepUserDiceResponse.class);

	}

	public Response<ActivitySheepUserDiceAwardResponse> diceAward(
			ActivitySheepUserDiceAwardRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserDiceAwardUrl, request, ActivitySheepUserDiceAwardResponse.class);
	}
	
	public Response<ActivitySheepUserRotaryRewardResponse> rotaryAward(
			ActivitySheepUserRotaryRewardRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserRotaryAwardUrl, request, ActivitySheepUserRotaryRewardResponse.class);
	}

	public Response<ActivitySheepUserRotaryResponse> getUserRotaryData(
			ActivitySheepUserRotaryRequest rotaryRequest) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserRotaryUrl, rotaryRequest, ActivitySheepUserRotaryResponse.class);
	}
	
	public Response<UserInfoResponse> getUserInfo(
			UserInfoRequest request) throws Exception {
		return httpClient.invokeHttp(serverPath + getUserInfoUrl, request, UserInfoResponse.class);
	}

	public Response<List<IssueStruc>>getGameIssueNumberRecord(
			Long lotteryId) throws Exception {
		return httpClient.invokeHttp(serverPath + getGameIssueNumberRecordUrl, lotteryId, new TypeReference<Response<List<IssueStruc>>>() {
		});
	}

}
