package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.exception.LinkFundSystemErrorException;
import com.winterframework.firefrog.game.fund.service.IGameRiskService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.web.dto.AddCoinRequest;
import com.winterframework.firefrog.game.web.dto.FreezeFundRequest;
import com.winterframework.firefrog.game.web.dto.ReduceCoinRequest;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;
import com.winterframework.firefrog.game.web.dto.TORiskResponse;
import com.winterframework.firefrog.game.web.dto.UnFreezeFundRequest;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Service("gameFundRiskServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameFundRiskServiceImpl implements IGameFundRiskService {

	private static final Logger log = LoggerFactory.getLogger(GameFundRiskServiceImpl.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.gameRisk.reduceCoin")
	private String reduceCoinUrl;

	@PropertyConfig("url.gameRisk.addCoin")
	private String addCoinUrl;

	@PropertyConfig("url.gameRisk.unFreezeFund")
	private String unFreezeUrl;

	@PropertyConfig("url.gameRisk.freezeFund")
	private String freezeUrl;

	@PropertyConfig("url.gameRisk.integration")
	private String integrationUrl;

	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@Resource(name = "gameRiskServiceImpl")
	private IGameRiskService gameRiskService; 

	@Override
	@Deprecated
	public void reduceCoin(Long amount, Long lotteryId, Long issueCode, String orderCode, Integer type, Long userId)
			throws Exception {
		ReduceCoinRequest reduceCoinRequest = new ReduceCoinRequest();
		reduceCoinRequest.setAmount(amount);
		reduceCoinRequest.setLotteryid(lotteryId);
		reduceCoinRequest.setIssueCode(issueCode);
		reduceCoinRequest.setOrderCodeList(orderCode);
		reduceCoinRequest.setType(type);
		reduceCoinRequest.setUserid(userId);

		try {

			httpClient.invokeHttpWithoutResultType(serverPath + reduceCoinUrl, reduceCoinRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
			throw new LinkFundSystemErrorException();
		}

	}

	@Override
	@Deprecated
	public void freezeFundGameOrder(Long amount, Long issueCode, Long lotteryId, String orderCode, Integer type,
			Long userId) throws Exception {

		FreezeFundRequest freezeFundRequest = new FreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setOrderCodeList(orderCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);

		try {
			httpClient.invokeHttpWithoutResultType(serverPath + freezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("freezeFundGameOrder error:", e);
			throw new LinkFundSystemErrorException();
		}
	}

	@Override
	@Deprecated
	public void freezeFundGameplan(Long amount, Long issueCode, Long lotteryId, String planCode, Integer type,
			Long userId) throws Exception {

		FreezeFundRequest freezeFundRequest = new FreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setPlanCodeList(planCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);
		freezeFundRequest.setOrderCodeList("");

		try {
			httpClient.invokeHttpWithoutResultType(serverPath + freezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("freezeFundGameOrder error:", e);
			throw new LinkFundSystemErrorException();
		}
	}

	@Override
	@Deprecated
	public void addCoin(Long amount, Long lotteryId, String orderCode, Integer type, Long userId) throws Exception {

		//3001 本金返还加款（审核系统内部接口5001-5006接口生成摘要）3002 派发奖金

		AddCoinRequest addCoinRequest = new AddCoinRequest();
		addCoinRequest.setAmount(amount);
		addCoinRequest.setLotteryid(lotteryId);
		addCoinRequest.setOrderCodeList(orderCode);
		addCoinRequest.setType(type);
		addCoinRequest.setUserid(userId);

		try {

			httpClient.invokeHttpWithoutResultType(serverPath + addCoinUrl, addCoinRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
			throw new LinkFundSystemErrorException();
		}
	}

	@Override
	@Deprecated
	public void unFreezeFundGamePlan(Long amount, Long issueCode, Long lotteryId, String planCode, Integer type,
			Long userId) throws Exception {

		UnFreezeFundRequest freezeFundRequest = new UnFreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setPlanCodeList(planCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);
		freezeFundRequest.setOrderCodeList("");
		try {

			httpClient.invokeHttpWithoutResultType(serverPath + unFreezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
			throw new LinkFundSystemErrorException();
		}
	}

	@Override
	@Deprecated
	public void unFreezeFundGameOrder(Long amount, Long issueCode, Long lotteryId, String orderCode, Integer type,
			Long userId) throws Exception {
		UnFreezeFundRequest freezeFundRequest = new UnFreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setOrderCodeList(orderCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);
		try {

			httpClient.invokeHttpWithoutResultType(serverPath + unFreezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
			throw new LinkFundSystemErrorException();
		}

	}

	/**
	 * 投注扣款
	 * 
	 *
	 */
	@Override
	public void betAmountReduce(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				request.setGameFundTypes(GameFundTypesUtils.GAME_BET_FREEZEN_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
			} else {
				throw new RuntimeException("betAmountReduce is null");
			}

		} catch (Exception e) {
			log.error("betAmountReduce error:", e);
			throw new LinkFundSystemErrorException();
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask betAmountReduce exception"+responce.getBody().getResult().getExceptionMessage());
		}

	}

	/**
	 * 派奖
	 * 
	 * 1 解冻计划中该订单的金额(当是计划时)
	 * 2 当前订单进行投注扣款(普通方案产生的订单扣款5004与计划的订单扣款类型5009)
	 * 3 解冻该订单的返点
	 * 4 如果中奖派发奖金
	 *
	 */
	@Override
	public void distributeAward(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				request.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
				if(responce.getBody().getResult().getResultStatus()!=0){
					throw new RuntimeException("betAmountFreezer is fail");
				}
			} else {
				throw new RuntimeException("distributeAward is null");
			}

		} catch (Exception e) {
			log.error("distributeAward error:", e);
			throw new LinkFundSystemErrorException();
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask distributeAward exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}
	
	/**
	 * 派奖
	 * 
	 * 1 解冻计划中该订单的金额(当是计划时)
	 * 2 当前订单进行投注扣款(普通方案产生的订单扣款5004与计划的订单扣款类型5009)
	 * 3 解冻该订单的返点
	 * 4 如果中奖派发奖金
	 *
	 */
	@Override
	public void distributeAwardMMC(List<TORiskDTO> toRiskDTOList) throws Exception {

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				//				Request<TORiskRequest> request = new Request<TORiskRequest>();
				//				TORiskRequest riskRequest = new TORiskRequest();
				//				riskRequest.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
				//				riskRequest.setToRiskDTOList(toRiskDTOList);
				//				RequestBody<TORiskRequest> body = new RequestBody<TORiskRequest>();
				//				body.setParam(riskRequest);
				//				
				//				request.setBody(body);
				/*request.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});*/
				gameRiskService.integration(toRiskDTOList);  
			} else {
				
				throw new RuntimeException("distributeAward is null");
			}

		} catch (Exception e) {
			
			log.error("distributeAward error:", e);
			throw new RuntimeException("ask distributeAward exception"+e.getMessage());
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask distributeAward exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}

	/**
	 * 投注扣款冻结
	 * 
	 * 1 冻结计划整个总金额(当是计划时,而且是计划的第一期时)产生一个TORiskDTO
	 * 2 计划执行产生的订单无需在进行冻结(当是计划时)
	 * 3 冻结订单金额(当是普通方案产生的订单)产生一个TORiskDTO
	 * 4 无论计划还是方案产生的订单,都需要冻结返点产生两个TORiskDTO
	 *
	 */
	@Override
	public void betAmountFreezer(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				request.setGameFundTypes(GameFundTypesUtils.GAME_BET_FREEZEN_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
				if(responce.getBody().getResult().getResultStatus()!=0){
					throw new RuntimeException("betAmountFreezer is fail");
				}
			} else {
				throw new RuntimeException("betAmountFreezer is null");
			}

		} catch (Exception e) {
			log.error("betAmountFreezer error:", e);
			throw new LinkFundSystemErrorException();
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask betAmountFreezer exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}

	@Override
	public void cancelFee(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				request.setGameFundTypes(GameFundTypesUtils.GAME_CANCEL_BET_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
				if(responce.getBody().getResult().getResultStatus()!=0){
					throw new RuntimeException("betAmountFreezer is fail");
				}
			} else {
				throw new RuntimeException("cancelFee is null");
			}

		} catch (Exception e) {
			log.error("cancelFee error:", e);
			throw new LinkFundSystemErrorException();
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask cancelFee exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}

	
	public void activityFund(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				request.setGameFundTypes(GameFundTypesUtils.ACTIVITY_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
			} else {
				throw new RuntimeException("activityFund is null");
			}

		} catch (Exception e) {
			log.error("activityFund error:", e);
			throw new LinkFundSystemErrorException();
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask activityFund exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}
}
