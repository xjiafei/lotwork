package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.dto.GameDrawResultQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameDrawResultQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameDrawResultStruc;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.GameListDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.HistoryInfoReqeust;
import com.winterframework.firefrog.phone.web.cotroller.dto.HistoryInfoResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.HistoryInfoResponseDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("histroyInfoController")
@RequestMapping("/information")
public class HistroyInfoController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(HistroyInfoController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.front.histroyInfo")
	private String histroryUrl;
	
	@PropertyConfig(value = "url.front.gameList")
	private String queryOrders;
	
	@RequestMapping(value = "/historyInfo")
	@ResponseBody
	public Response<HistoryInfoResponse> histroyInfo(@RequestBody Request<HistoryInfoReqeust> request) throws Exception{
		
		//默认
		int num = 3;
		Response<HistoryInfoResponse> response = new Response<HistoryInfoResponse>(request);
		HistoryInfoResponse result = new HistoryInfoResponse();
		String token = request.getHead().getSessionId();
		try {
			//test code
			//UserToken ut = new UserToken();
			//ut.setUserName("captainray");
			//ut.setUserId(1292830L);
			String account = getUserNameByToken(token);
			UserToken ut = getUserToken(account);
			
			
			
			HistoryInfoReqeust info = request.getBody().getParam();
			
			GameDrawResultQueryRequest chart = new GameDrawResultQueryRequest();
			
			if(info.getNum()!=null){
				
				num = info.getNum();
			}
			chart.setNum(num);
			
			if(info.getLotteryId()!=null){
				chart.setLotteryId(info.getLotteryId());
			}
			log.info("chart.getLotteryId() : " + chart.getLotteryId());
			log.info("chart.getNum() : " + chart.getNum());
			List<HistoryInfoResponseDto> list = new ArrayList<HistoryInfoResponseDto>();
			Response<GameDrawResultQueryResponse> gameResponse = null;
			if(chart.getLotteryId() <= 0){
				log.info("chart.getLotteryId() <= 0");
				Long[] all = allSeries();
				for(Long lotteryid:all){
					
					chart.setLotteryId(lotteryid);
					//查詢GAME_DRAW_RESULT(不包含秒秒彩 秒秒彩另外處理)		
					gameResponse = httpClient.invokeHttp(gameUrl + histroryUrl, chart, new TypeReference<Response<GameDrawResultQueryResponse>>(){});
					List<GameDrawResultStruc> drawlist = gameResponse.getBody().getResult().getGameDrawResultStrucList();
					if(null != drawlist && !drawlist.isEmpty()){
						
						for(GameDrawResultStruc struc : drawlist){
							HistoryInfoResponseDto dto = new HistoryInfoResponseDto();
							dto.setCode(struc.getNumber());
							dto.setIssue(struc.getWebIssueCode());
							dto.setLotteryid(struc.getLotteryId());
							dto.setTime(DateUtils.format(struc.getDrawTime(), DateUtils.DATETIME_FORMAT_PATTERN));
							list.add(dto);
						}
					}
				}
				log.info("all list : " + list.size());
			}else{
				if(99112 != chart.getLotteryId()){
					gameResponse = httpClient.invokeHttp(gameUrl + histroryUrl, chart,new TypeReference<Response<GameDrawResultQueryResponse>>(){});
					List<GameDrawResultStruc> drawlist =  gameResponse.getBody().getResult().getGameDrawResultStrucList();
					if(null != drawlist && !drawlist.isEmpty()){
						
						for(GameDrawResultStruc struc : drawlist){
							HistoryInfoResponseDto dto = new HistoryInfoResponseDto();
							dto.setCode(struc.getNumber());
							dto.setIssue(struc.getWebIssueCode());
							dto.setLotteryid(struc.getLotteryId());
							dto.setTime(DateUtils.format(struc.getDrawTime(), DateUtils.DATETIME_FORMAT_PATTERN));
							list.add(dto);
						}
					}
					log.info("single lottery list size : " + list.size());
				}
			}
			
			//2015-01-11 //add slmmc records
			// get user ssc records
			log.info("info.getLotteryId() : " + info.getLotteryId());
			if(info.getLotteryId()==null || 99112 == chart.getLotteryId()){
				log.info("chart.getNum() : " + chart.getNum());
				List<GameListDto> records = getGameList(ut,chart.getNum());
				log.info("slmmc information records size : " + records.size());
				for(GameListDto record:records){
					HistoryInfoResponseDto dto = new HistoryInfoResponseDto();
					dto.setCode(record.getNumberRecord());
					log.info("record.getNumberRecord() : " + record.getNumberRecord());
					dto.setIssue("");
					log.info("record.getIssue() : " + record.getIssue());
					dto.setLotteryid(record.getLotteryid());
					log.info("record.setLotteryid() : " + record.getLotteryid());
					dto.setTime(DateUtils.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.getTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					log.info("record.getTime() : " + record.getTime());
					list.add(dto);
				}
			}
			
			//2016-12-12 //add slmmc2000 records
			// get user ssc records
			log.info("info.getLotteryId() : " + info.getLotteryId());
			if(info.getLotteryId()==null || 99113 == chart.getLotteryId()){
				log.info("chart.getNum() : " + chart.getNum());
				List<GameListDto> records = getGameList2000(ut,chart.getNum());
				log.info("slmmc information records size : " + records.size());
				for(GameListDto record:records){
					HistoryInfoResponseDto dto = new HistoryInfoResponseDto();
					dto.setCode(record.getNumberRecord());
					log.info("record.getNumberRecord() : " + record.getNumberRecord());
					dto.setIssue("");
					log.info("record.getIssue() : " + record.getIssue());
					dto.setLotteryid(record.getLotteryid());
					log.info("record.setLotteryid() : " + record.getLotteryid());
					dto.setTime(DateUtils.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.getTime()), DateUtils.DATETIME_FORMAT_PATTERN));
					log.info("record.getTime() : " + record.getTime());
					list.add(dto);
				}
			}
			log.info("final list size : " + list.size());
			
			result.setList(list);
			response.setResult(result);
			
		} catch (Exception e) {
			
			log.error("HistroyIinfoController error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	private List<GameListDto> getGameList(UserToken ut,int num) throws Exception {
		GameOrderQueryRequest gameRequest = new GameOrderQueryRequest();
		gameRequest.setAccount(ut.getUserName());
		gameRequest.setLotteryId(99112L);
		gameRequest.setParentType(0);
		gameRequest.setContainSub(0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(java.util.Calendar.DATE, -7);

		gameRequest.setStartTime(cal.getTimeInMillis());
		gameRequest.setEndTime(new Date().getTime());
		Response<GameOrderQueryResponse> gameOrderResponse = null;
		try {
			gameOrderResponse = httpClient.invokeHttp(gameUrl + queryOrders,
					gameRequest, new Pager(1, num), ut.getUserId(),
					ut.getUserName(),
					new TypeReference<Response<GameOrderQueryResponse>>() {
					});
		} catch (Exception e) {
			throw new Exception("getGameList error!!");
		}
		List<OrdersStruc> orderList = gameOrderResponse.getBody().getResult()
				.getOrdersStruc();

		List<GameListDto> list = new ArrayList<GameListDto>();
		if (null != orderList && !orderList.isEmpty()) {
			for (int i=0;i < orderList.size();i++) {
				if(i >= num){
					break;
				}
				GameListDto dto = new GameListDto();
				log.info("----------------------------");
				dto.setOrderCode(orderList.get(i).getOrderCode());
				log.info("OrderCode : " + dto.getOrderCode());
				dto.setNumberRecord(orderList.get(i).getNumberRecord());
				log.info("NumberRecord : " + dto.getNumberRecord());
				dto.setLotteryid(orderList.get(i).getLotteryid());
				log.info("Lotteryid : " + orderList.get(i).getLotteryid());
				dto.setBonus(new BigDecimal(orderList.get(i).getTotwin() == null ? 0
						: orderList.get(i).getTotwin()).divide(new BigDecimal(10000), 2,
						RoundingMode.HALF_UP).doubleValue());
				log.info("Bonus : " + dto.getBonus());
				dto.setEnid(String.valueOf(orderList.get(i).getOrderId()));
				log.info("Enid : " + dto.getEnid());
				dto.setIfwin(orderList.get(i).getStatus());
				log.info("Ifwin : " + dto.getIfwin());
				dto.setIscancel(orderList.get(i).getCancelModels());
				log.info("Iscancel : " + dto.getIscancel());
				dto.setIssue(orderList.get(i).getWebIssueCode());
				log.info("Issue : " + dto.getIssue());
				dto.setTime(DateUtils.format(
						DataConverterUtil.convertLong2Date(orderList.get(i).getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				log.info("Time : " + dto.getTime());
				dto.setTotalprice(new BigDecimal(
						orderList.get(i).getTotamount() == null ? 0 : orderList.get(i).getTotamount())
						.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
						.floatValue());
				log.info("Totalprice : " + dto.getTotalprice());

				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 超級2000秒秒彩注單查詢
	 * @param ut
	 * @param num
	 * @return
	 * @throws Exception
	 */
	private List<GameListDto> getGameList2000(UserToken ut,int num) throws Exception {
		GameOrderQueryRequest gameRequest = new GameOrderQueryRequest();
		gameRequest.setAccount(ut.getUserName());
		gameRequest.setLotteryId(99113L);
		gameRequest.setParentType(0);
		gameRequest.setContainSub(0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(java.util.Calendar.DATE, -7);

		gameRequest.setStartTime(cal.getTimeInMillis());
		gameRequest.setEndTime(new Date().getTime());
		Response<GameOrderQueryResponse> gameOrderResponse = null;
		try {
			gameOrderResponse = httpClient.invokeHttp(gameUrl + queryOrders,
					gameRequest, new Pager(1, num), ut.getUserId(),
					ut.getUserName(),
					new TypeReference<Response<GameOrderQueryResponse>>() {
					});
		} catch (Exception e) {
			throw new Exception("getGameList error!!");
		}
		List<OrdersStruc> orderList = gameOrderResponse.getBody().getResult()
				.getOrdersStruc();

		List<GameListDto> list = new ArrayList<GameListDto>();
		if (null != orderList && !orderList.isEmpty()) {
			for (int i=0;i < orderList.size();i++) {
				if(i >= num){
					break;
				}
				GameListDto dto = new GameListDto();
				log.info("----------------------------");
				dto.setOrderCode(orderList.get(i).getOrderCode());
				log.info("OrderCode : " + dto.getOrderCode());
				dto.setNumberRecord(orderList.get(i).getNumberRecord());
				log.info("NumberRecord : " + dto.getNumberRecord());
				dto.setLotteryid(orderList.get(i).getLotteryid());
				log.info("Lotteryid : " + orderList.get(i).getLotteryid());
				dto.setBonus(new BigDecimal(orderList.get(i).getTotwin() == null ? 0
						: orderList.get(i).getTotwin()).divide(new BigDecimal(10000), 2,
						RoundingMode.HALF_UP).doubleValue());
				log.info("Bonus : " + dto.getBonus());
				dto.setEnid(String.valueOf(orderList.get(i).getOrderId()));
				log.info("Enid : " + dto.getEnid());
				dto.setIfwin(orderList.get(i).getStatus());
				log.info("Ifwin : " + dto.getIfwin());
				dto.setIscancel(orderList.get(i).getCancelModels());
				log.info("Iscancel : " + dto.getIscancel());
				dto.setIssue(orderList.get(i).getWebIssueCode());
				log.info("Issue : " + dto.getIssue());
				dto.setTime(DateUtils.format(
						DataConverterUtil.convertLong2Date(orderList.get(i).getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				log.info("Time : " + dto.getTime());
				dto.setTotalprice(new BigDecimal(
						orderList.get(i).getTotamount() == null ? 0 : orderList.get(i).getTotamount())
						.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
						.floatValue());
				log.info("Totalprice : " + dto.getTotalprice());

				list.add(dto);
			}
		}
		return list;
	}
}
