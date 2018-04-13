package com.winterframework.firefrog.game.fund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.fund.bean.FundGameVos;
import com.winterframework.firefrog.game.fund.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.fund.service.IGameFundService;
import com.winterframework.firefrog.game.fund.service.IGameRiskChangeFundGameVoService;
import com.winterframework.firefrog.game.fund.service.IGameRiskService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;

/**
 * 
* @ClassName: GameRiskServiceImpl 
* @Description: 风控中心服务类
* @author Richard, David
* @date 2013-12-18 上午11:37:12 
*
 */
@Service("gameRiskServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class GameRiskServiceImpl implements IGameRiskService {

	private final Logger log = LoggerFactory.getLogger(GameRiskServiceImpl.class);

	@Resource(name = "gameFundServiceImpl")
	private IGameFundService gameFundServiceImpl;
 
	@Resource(name = "gameRiskFundServiceImpl")
	private IGameRiskChangeFundGameVoService gameRiskFundService;
 

	@Override
	public void integration(List<TORiskDTO> riskDtoList) throws Exception {
		 
		if(null == riskDtoList || riskDtoList.isEmpty()){
			throw new RuntimeException("integration toRikDTOList is NULL.");
		} 
		GameFundServiceBean result = new GameFundServiceBean() ; 
		  
		result = gameRiskFundService.riskProcess(riskDtoList);  
		
		//调用资金系统 
		this.fundActions(result);		 
	}

	public void fundActions(GameFundServiceBean result) throws Exception {
		//调用资金系统
		gameFundServiceImpl.fundActions(result);
	}


}
