package com.winterframework.firefrog.game.fund.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.game.exception.GameFundException;
import com.winterframework.firefrog.game.fund.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.fund.service.IGameFundService;
import com.winterframework.modules.web.jsonresult.Response;
//import com.winterframework.firefrog.game.dao.vo.FundChangeDetail;

@Service("gameFundServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class GameFundServiceImpl implements IGameFundService {

	private final Logger log = LoggerFactory.getLogger(GameFundServiceImpl.class);

	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
 
	/**
	* @Title: fundActions
	* @Description: 审核中心请求资金系统交易
	* @param gameFundServiceBean
	* @throws GameFundException 
	* @see com.winterframework.firefrog.game.service.IGameFundService#fundActions(com.winterframework.firefrog.game.fund.bean.GameFundServiceBean) 
	*/ 
	public void fundActions(GameFundServiceBean gameFundServiceBean) throws Exception {
		if(gameFundServiceBean==null) return;
		log.info("审核中心开始请求资金系统"); 
		fundChangeService.actions(gameFundServiceBean.getFundList()); 

	}
 
}
