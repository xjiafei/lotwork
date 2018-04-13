package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameDetailProject;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameDetailRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameWinDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameWinListResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.LastNumberRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.LastNumberResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.MethodInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.MethodInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.firefrog.phone.web.cotroller.dto.WinAmtRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.WinAmtResponse;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("gameController")
@RequestMapping("/information")
public class GameController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	
	@PropertyConfig(value="url.front.gameList")
	private String gameListUrl;
	
	@PropertyConfig(value = "url.front.gamedetail")
	private String gameDetailUrl;
	@PropertyConfig(value = "url.game.getWinAmt")
	private String winAmtUrl;
	@PropertyConfig(value = "url.game.getCurrentGameIssue")
	private String curIssueUrl;
	@PropertyConfig(value = "url.game.queryDescByBetMethodByUserId")
	private String queryBetMethodDescUrl;
	
	
	
	@RequestMapping(value = "/gameList")
	@ResponseBody
	public Response<GameListResponse> gameList(@RequestBody Request<GameListRequest> request) throws Exception{
		
		Response<GameListResponse> response = new Response<GameListResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			
			GameListResponse result = new GameListResponse();
			GameOrderQueryRequest gameRequest = new GameOrderQueryRequest();
//			gameRequest.setStatus(0);
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			gameRequest.setAccount(account);
			UserToken ut = getUserToken(account);
			gameRequest.setParentType(0);
			gameRequest.setContainSub(0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(java.util.Calendar.DATE, -2);
			gameRequest.setStartTime(cal.getTimeInMillis());
			gameRequest.setEndTime(new Date().getTime());
			gameRequest.setLotteryId(request.getBody().getParam().getLotteryId());
			Response<GameOrderQueryResponse> gameOrderResponse =  httpClient.invokeHttp(gameUrl+gameListUrl, gameRequest, new Pager(1,10000),ut.getUserId(),ut.getUserName(), new TypeReference<Response<GameOrderQueryResponse>>(){} );
			
			List<OrdersStruc> orderList = gameOrderResponse.getBody().getResult().getOrdersStruc();
			
			List<GameListDto> list = new ArrayList<GameListDto>();
			if(null != orderList && !orderList.isEmpty()){
				
				for(OrdersStruc struc : orderList){
					GameListDto dto = new GameListDto();
					dto.setLotteryid(struc.getLotteryid());
					dto.setBonus(new BigDecimal(struc.getTotwin()==null ? 0:struc.getTotwin()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
					dto.setEnid(String.valueOf(struc.getOrderId()));
					dto.setIfwin(struc.getStatus());
					dto.setIscancel(struc.getCancelModels());
					dto.setIssue(struc.getWebIssueCode());
					dto.setTime(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getSaleTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					dto.setTotalprice(new BigDecimal(struc.getTotamount()==null ? 0:struc.getTotamount()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).floatValue());
					list.add(dto);
				}
			}
			result.setList(list);
			
			response.getBody().setResult(result);
			
		} catch (Exception e) {
			log.error("gameList error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	
	@RequestMapping(value = "/gameDetail")
	@ResponseBody
	public Response<GameDetailResponse> gameDetail(@RequestBody Request<GameDetailRequest> request) throws Exception{
		
		Response<GameDetailResponse> response = new Response<GameDetailResponse>(request);
		String token = request.getHead().getSessionId();
		try{
			
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			GameOrderDetailQueryRequest orderDetailRequest = new GameOrderDetailQueryRequest();
			orderDetailRequest.setOrderId(Long.parseLong(request.getBody().getParam().getId()));
			
			GameDetailResponse gameDetail = new GameDetailResponse();
			Response<GameOrderDetailQueryResponse> gameResponse =  httpClient.invokeHttp(gameUrl + gameDetailUrl, orderDetailRequest, new Pager(1,10000),ut.getUserId(),ut.getUserName(), new TypeReference<Response<GameOrderDetailQueryResponse>>() {});
			if(null != gameResponse.getBody().getResult() && null != gameResponse.getBody().getResult().getSlipsStruc()){
				
				GameOrderDetailQueryResponse orderDetail =  gameResponse.getBody().getResult();
				List<GameDetailProject> list = new ArrayList<GameDetailProject>(); 
				OrdersStruc struc = orderDetail.getOrdersStruc();
				if(null != struc){
					
					gameDetail.setEnid(String.valueOf(struc.getOrderId()));
					gameDetail.setIssue(struc.getWebIssueCode());
					gameDetail.setTime(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getSaleTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					gameDetail.setTotal(new BigDecimal(struc.getTotamount()==null ? 0 : struc.getTotamount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).floatValue());
					gameDetail.setBonus(new BigDecimal(struc.getTotwin() == null ? 0 : struc.getTotwin()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).floatValue());
					gameDetail.setLotteryId(struc.getLotteryid());
					gameDetail.setGameNo(struc.getOrderCode());
					
					//0：否1：是
					Integer cancancel = 0;
					if(struc.getCanCancel() && struc.getCancelModels()==0){
						cancancel = 1; 
					}
					gameDetail.setCancancel(cancancel);
					//0:默认(未撤单) 1:用户 2:系统	
					gameDetail.setIscancel(struc.getCancelModels());
					gameDetail.setOpencode(struc.getNumberRecord());
					
					if(null != orderDetail.getSlipsStruc()){
						for(SlipsStruc slip : orderDetail.getSlipsStruc()){
							
							GameDetailProject project = new GameDetailProject();
							project.setCodedetails(slip.getBetDetail());
							project.setMethodid(slip.getGameGroupCode()+"_"+slip.getGameSetCode()+"_"+slip.getBetMethodCode());
							project.setNums(slip.getTotbets());
							project.setMultiple(slip.getMultiple());
							project.setPrice(new BigDecimal(slip.getTotamount()==null ? 0 : slip.getTotamount()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).floatValue());
							project.setModes(slip.getMoneyMode()==2 ? "角":(slip.getMoneyMode()==3?"分":"元"));
							Integer ifwin = slip.getStatus();
							project.setBonus(new BigDecimal(slip.getAward()==null ? 0 : slip.getAward()).divide(new BigDecimal(10000),2,RoundingMode.HALF_UP).doubleValue());
							//0：未开奖1：未中奖2：派奖中3：已派奖4：撤銷5：存在異常	
							if(slip.getStatus()==2){
								ifwin = 3;
							}else if(slip.getStatus()==3){
								ifwin = 1;
							}else if(slip.getStatus()==1){
								ifwin = 0;
							}
							project.setIfwin(ifwin);
							list.add(project);
						}
					}
					gameDetail.setList(list);
				}
			}else{
				response.getHead().setStatus(902001L);
			}
			
			response.getBody().setResult(gameDetail);
			
		}catch(Exception e){
			log.error("gameDetail error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	@RequestMapping(value = "/winList")
	@ResponseBody
	public Response<GameWinListResponse> winList(@RequestBody Request<String[]> request) throws Exception{
		final String INDEX_LOTTERY_KEY = "firefrog_index_lastdata_";
		Response<GameWinListResponse> response = new Response<GameWinListResponse>(request);
		try {
			String[] lotteryIds=request.getBody().getParam();
			GameWinListResponse result = new GameWinListResponse();
			if(null!=lotteryIds){
				List<GameWinDto> list=new ArrayList<GameWinDto>();
				for(String lotteryId:lotteryIds){
					String indexLotteryStr=redisClient2.get(INDEX_LOTTERY_KEY+lotteryId);
					IndexLottery indexLottery=JsonUtil.fromJson(indexLotteryStr, IndexLottery.class);
					if(indexLottery!=null){
						if(indexLottery.getWins()!=null && indexLottery.getWins().size()>0){
							for(Map.Entry<String,String>e:indexLottery.getWins().entrySet()){
								GameWinDto dto=new GameWinDto();
								dto.setLotteryName(indexLottery.getLottery());
								dto.setUserName(e.getKey());
								dto.setWinMoney(Double.valueOf(e.getValue())/10000);
								list.add(dto);
							}
						}
					}
				}
				result.setList(list);
			}
			response.getBody().setResult(result);
		} catch (Exception e) {
			log.error("gameList error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	@RequestMapping(value = "/winAmt")
	@ResponseBody
	public Response<WinAmtResponse> winAmt(@RequestBody Request<WinAmtRequest> request) throws Exception{
		Response<WinAmtResponse> response = new Response<WinAmtResponse>(request);
		String token = request.getHead().getSessionId();
		
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			WinAmtResponse winAmtRes=new WinAmtResponse();
			UserToken ut = getUserToken(account);
			WinAmtRequest bizRequest=request.getBody().getParam();
			bizRequest.setUserId(ut.getUserId()); 
			
			Response<Double> bizRes= httpClient.invokeHttp(gameUrl+winAmtUrl, bizRequest,Double.class);
			if(bizRes!=null && bizRes.getBody().getResult()!=null){
					winAmtRes.setWinAmt(bizRes.getBody().getResult()/10000); 
			}else{
				winAmtRes.setWinAmt(0.0);
			}
			response.setResult(winAmtRes);
		} catch (Exception e) {
			log.error("winAmt error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	@RequestMapping(value = "/lastNumber")
	@ResponseBody
	public Response<LastNumberResponse> lastNumber(@RequestBody Request<LastNumberRequest> request) throws Exception{
		Response<LastNumberResponse> response = new Response<LastNumberResponse>(request);
		String token = request.getHead().getSessionId();
		
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			LastNumberResponse lastNumberRes=new LastNumberResponse();
			UserToken ut = getUserToken(account);
			LastNumberRequest bizRequest=request.getBody().getParam();
			Long lotteryId=bizRequest.getLotteryId();
			Response<GameIssueQueryResponse> bizRes= httpClient.invokeHttp(gameUrl+curIssueUrl, bizRequest,GameIssueQueryResponse.class);
			if(bizRes!=null && bizRes.getBody().getResult()!=null){
				lastNumberRes.setIssueCode(bizRes.getBody().getResult().getLastWebIssueCode());
				lastNumberRes.setCodes(bizRes.getBody().getResult().getNumberRecord());
				if (lotteryId == 99701) {
					StringBuilder sb = new StringBuilder(bizRes.getBody().getResult().getLastWebIssueCode());
					sb.insert(2, "/");
					lastNumberRes.setIssue(sb.toString());
				} else {
					lastNumberRes.setIssue(bizRes.getBody().getResult().getLastWebIssueCode());
				}
			}
			response.setResult(lastNumberRes);
		} catch (Exception e) {
			log.error("lastNumber error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	@RequestMapping(value = "/methodInit")
	@ResponseBody
	public Response<MethodInitResponse> methodInit(@RequestBody Request<MethodInitRequest> request) throws Exception{
		Response<MethodInitResponse> response = new Response<MethodInitResponse>(request);
		String token = request.getHead().getSessionId();
		
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			MethodInitRequest bizRequest=request.getBody().getParam();
			Long  lotteryId=bizRequest.getLotteryId();
			String betTypeCode=bizRequest.getBetTypeCode();
			
			QueryDescByBetMethodByUserIdRequest descRequest=new QueryDescByBetMethodByUserIdRequest();
			descRequest.setLotteryid(lotteryId);
			descRequest.setUserid(ut.getUserId());
			
			descRequest.setGameGroupCode(Integer.valueOf(betTypeCode.split("_")[0]));
			descRequest.setGameSetCode(Integer.valueOf(betTypeCode.split("_")[1]));
			descRequest.setBetMethodCode(Integer.valueOf(betTypeCode.split("_")[2]));
			
			Response<QueryDescByBetMethodByUserIdResponse> descRes= httpClient.invokeHttp(gameUrl+queryBetMethodDescUrl, descRequest,QueryDescByBetMethodByUserIdResponse.class);
			MethodInitResponse methodInitRes=new MethodInitResponse();
			if(descRes!=null && descRes.getBody().getResult()!=null){
				Double bonus = 0.0;
				Double bonusDown = 0.0;
				Double theoryBonus = 0.0;
				Double retPoint = 0.0;
				if (null != descRes.getBody().getResult().getActualBonus()) {
					bonus = Double.valueOf(descRes.getBody().getResult().getActualBonus());
					bonus = bonus / 10000;
				}
				if (null != descRes.getBody().getResult().getActualBonusDown()) {
					bonusDown = Double.valueOf(descRes.getBody().getResult().getActualBonusDown());
					bonusDown = bonusDown / 10000;
				}
				if (null != descRes.getBody().getResult().getTheoryBonus()) {
					theoryBonus = Double.valueOf(descRes.getBody().getResult().getTheoryBonus());
					theoryBonus = theoryBonus / 10000;
				}
				if (null != descRes.getBody().getResult().getRetPoint()) {
					retPoint = Double.valueOf(descRes.getBody().getResult().getRetPoint());
					retPoint = retPoint / 100;
				}
				methodInitRes.setExample(descRes.getBody().getResult().getGameExamplesDes());
				methodInitRes.setTips(descRes.getBody().getResult().getGamePromptDes());
				if (Math.round(bonusDown) == bonusDown) {
					methodInitRes.setExample(methodInitRes.getExample().replaceAll("bonus2", String.valueOf(Math.round(bonusDown))));
					methodInitRes.setTips(methodInitRes.getTips().replaceAll("bonus2", String.valueOf(Math.round(bonusDown))));
				} else {
					methodInitRes.setExample(methodInitRes.getExample().replaceAll("bonus2", String.valueOf(bonusDown)));
					methodInitRes.setTips(methodInitRes.getTips().replaceAll("bonus2", String.valueOf(bonusDown)));
				}
				if (Math.round(bonus) == bonus) {
					methodInitRes.setExample(methodInitRes.getExample().replaceAll("bonus", String.valueOf(Math.round(bonus))));
					methodInitRes.setTips(methodInitRes.getTips().replaceAll("bonus", String.valueOf(Math.round(bonus))));
				
				} else {
					methodInitRes.setExample(methodInitRes.getExample().replaceAll("bonus", String.valueOf(bonus)));
					methodInitRes.setTips(methodInitRes.getTips().replaceAll("bonus", String.valueOf(bonus)));
				}
				methodInitRes.setTheoryBonus(theoryBonus);
				methodInitRes.setActualBonus(bonus);
				methodInitRes.setRetPoint(retPoint);
				
			}
			response.setResult(methodInitRes);
		} catch (Exception e) {
			log.error("method init error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
}
