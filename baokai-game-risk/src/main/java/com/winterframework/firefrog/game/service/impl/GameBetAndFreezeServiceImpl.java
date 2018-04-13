package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameRiskFundDao;
import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.service.IGameRiskChangeFundGameVoService;
import com.winterframework.firefrog.game.service.IGameRiskFundOperateService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 * 
* @ClassName: GameBetAndUnFreezeService 
* @Description: 投注冻结操作
* @author Richard hugh
* @date 2014-1-5 上午11:34:24 
*
 */
@Service("gameBetAndFreezeServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class GameBetAndFreezeServiceImpl implements IGameRiskChangeFundGameVoService, IGameRiskFundOperateService {
	
	private static final Logger log = LoggerFactory.getLogger(GameBetAndFreezeServiceImpl.class);
	
	private static final String SEPARATOR = ",";

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;
	
	@Resource(name = "gameRiskFundDaoImpl")
	private IGameRiskFundDao gameRiskFundDao;
	
	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionService;
	
	@Override
	public GameFundServiceBean riskProcess(List<TORiskDTO> list) throws Exception {
		
		log.info("开始处理投注冻结操作流程。");
		
		GameFundServiceBean result = new GameFundServiceBean();
		List<FundGameVos> fundList = new ArrayList<FundGameVos>();
		List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		
		for(TORiskDTO dto : list){
			
			
			String[] users = StringUtils.split(dto.getUserid(), SEPARATOR);
			String[] amounts = StringUtils.split(dto.getAmount(), SEPARATOR);
			
			//Type 4004	返点冻结
			//在投注冻结时，不需要将返点信息发送到资金模块。
			if(dto.getType() != GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE){
				
				for(int i = 0 ; i< users.length; i++){
					FundGameVos fundGameVos = new FundGameVos();
					fundGameVos.setIsAclUser(0L);
					fundGameVos.setOperator(Long.parseLong(users[0]));
					fundGameVos.setReason(GameFundTypesUtils.getFundDigest(dto.getType()));
					// amout|user
					fundGameVos.setVals(users[i]+"|"+amounts[i]);
					fundGameVos.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
					fundGameVos.setExCode(dto.getOrderCodeList());
					fundGameVos.setPlanCode(dto.getPlanCodeList());
					
					
					fundList.add(fundGameVos);
					trans.add(gameTransactionService.parseGameTransactionFund(dto, fundGameVos));
					
				}
		
			}
			//Save Game_Risk_Fund
			saveRiskFund(dto, users, amounts, GameFundTypesUtils.GAME_BET_FREEZEN_TYPE);
		}

		result.setFundList(fundList);
		result.setTrans(trans);	
		return result;
	}


	@Override
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception {

		gameRiskFundDao.saveRiskFund(dto, users, amouts, Status);
			
	}



}
