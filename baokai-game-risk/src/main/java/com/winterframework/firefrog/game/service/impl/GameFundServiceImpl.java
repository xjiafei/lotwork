package com.winterframework.firefrog.game.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGameRiskFundDao;
import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
//import com.winterframework.firefrog.game.dao.vo.FundChangeDetail;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.exception.GameFundException;
import com.winterframework.firefrog.game.service.IGameFundService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Service("gameFundServiceImpl")
@Transactional
public class GameFundServiceImpl implements IGameFundService {

	private final Logger log = LoggerFactory.getLogger(GameFundServiceImpl.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "fund.action")
	private String fundActionUrl;

	@PropertyConfig(value = "fund.actions")
	private String fundActionsUrl;

	@PropertyConfig(value = "url.connect.fund")
	private String baseFundUrl;

	@Resource(name = "gameRiskFundDaoImpl")
	private IGameRiskFundDao gameRiskFundDao;

	@Resource(name = "gameTransactionFundDaoImpl")
	private IGameTransactionFundDao gameTransactionFundDao;

	/**
	* @Title: fundActions
	* @Description: 审核中心请求资金系统交易
	* @param gameFundServiceBean
	* @throws GameFundException 
	* @see com.winterframework.firefrog.game.service.IGameFundService#fundActions(com.winterframework.firefrog.game.service.bean.GameFundServiceBean) 
	*/
	@Transactional(rollbackFor=Exception.class)
	public void fundActions(GameFundServiceBean gameFundServiceBean) throws GameFundException {
		if(gameFundServiceBean==null) return;
		log.info("审核中心开始请求资金系统");
		Response<List<Map<String, ?>>> response = new Response<List<Map<String, ?>>>();
		try {
			// old
			// httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionsUrl, gameFundServiceBean.getFundList());
			//注释部分等待资金接口更新
			response = httpClient.invokeHttp(baseFundUrl + fundActionsUrl, gameFundServiceBean.getFundList(),
					List.class);
			if (response == null){
				throw new GameFundException(baseFundUrl + fundActionsUrl);
			}
		} catch (Exception e) {
			log.error("审核中心请求资金系统异常：" + baseFundUrl + fundActionsUrl, e);
	//	暫時不使用功能	saveFailGameTransactionFunds(gameFundServiceBean.getTrans());
			throw new GameFundException(baseFundUrl + fundActionsUrl);
		}
	//	暫時不使用功能      saveSussuesGameTransactionFunds(gameFundServiceBean.getTrans(), response);

	}

	/** 
	* @Title: saveSussuesGameTransactionFunds 
	* @Description: 保存资金交易信息
	* @param trans
	* @param response
	*/
	private void saveSussuesGameTransactionFunds(List<GameTransactionFund> trans,
			Response<List<Map<String, ?>>> response) throws GameFundException{

		List<Map<String, ?>> result = response.getBody().getResult();
		if (result == null || result.isEmpty()) {
			log.error("资金系统未返回信息");
			saveFailGameTransactionFunds(trans);
			throw new GameFundException(baseFundUrl + fundActionsUrl +"资金系统未返回信息");		
		} else if (result != null && result.size() != trans.size()) {
			log.error("资金系统未返回信息有误");
			throw new GameFundException(baseFundUrl + fundActionsUrl +"资金系统未返回信息有误");
		} else {
			for (int j = 0; j < trans.size(); j++) {
				GameTransactionFund transactionFund = trans.get(j);
				transactionFund.setStatus(1L);
				if(result.get(j).get("sn") != null){
					transactionFund.setTid(result.get(j).get("sn").toString());
				}else{
					gameTransactionFundDao.insert(transactionFund);
					log.error("资金系统未返回信息缺少序列号");
					throw new GameFundException(baseFundUrl + fundActionsUrl +"资金系统未返回信息有误，资金系统未返回信息缺少序列号");
				}
				if(result.get(j).get("summary") != null){
					transactionFund.setReason(result.get(j).get("summary").toString());					
				}else{
					log.error("资金系统未返回信息缺少中文摘要");
				}
				if(result.get(j).get("balance") != null){
					transactionFund.setBalance(Long.parseLong(result.get(j).get("balance").toString()));			
				}else{
					log.error("资金系统未返回信息缺少余额信息");
				}
				gameTransactionFundDao.insert(transactionFund);
			}
		}
	}

	/** 
	* @Title: saveFailGameTransactionFunds 
	* @Description: 保存资金交易失败信息
	* @param trans
	*/
	private void saveFailGameTransactionFunds(List<GameTransactionFund> trans) {
		for (GameTransactionFund transactionFund : trans) {			transactionFund.setStatus(2L);
			gameTransactionFundDao.insert(transactionFund);
		}
	}
}
