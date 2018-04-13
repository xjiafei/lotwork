package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameRiskFundDao;
import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameRiskFund;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.service.IGameRiskChangeFundGameVoService;
import com.winterframework.firefrog.game.service.IGameRiskFundOperateService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
/**
 * 
* @ClassName: GameCancelFundServiceImpl 
* @Description: 游戏项目调用风控系统处理撤销业务流程
* @author Richard hug
* @date 2014-1-14 下午2:30:17 
*
 */
@Service("activityFundServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class ActivityFundServiceImpl implements IGameRiskFundOperateService, IGameRiskChangeFundGameVoService {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityFundServiceImpl.class);
	private static final String SEPARATOR = ",";


	@Resource(name = "gameRiskFundDaoImpl")
	private IGameRiskFundDao gameRiskFundDao;	
		
	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionService;
	
	@Override
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception {
		
		log.info("begin saveRiskFund...");
		gameRiskFundDao.saveRiskFund(dto, users, amouts, Status);
	}


	@Override
	public GameFundServiceBean riskProcess(List<TORiskDTO> list) throws Exception {
		log.info("开始风控系统流程。");
		GameFundServiceBean result = new GameFundServiceBean();
		List<FundGameVos> fundList = new ArrayList<FundGameVos>();
		List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		
		for(TORiskDTO dto : list){
			
			GameFundServiceBean innerResult = switchTypeToGameFundVos(dto);
			//可能存在 返回多条或一条，所以要进行循环获取。
			for (int i = 0; i < innerResult.getFundList().size(); i++) {
				fundList.add(innerResult.getFundList().get(i));
				trans.add(innerResult.getTrans().get(i));
			}					
		}
		
		result.setFundList(fundList);
		result.setTrans(trans);		
		return result;
	}
	
	
	/**
	 * 
	* @Title: switchTypeToGameFundVos 
	* @Description: 根据type 转换 FundGameVos
	* @param dto
	* @return
	 */
	private GameFundServiceBean switchTypeToGameFundVos(TORiskDTO dto) throws Exception{
		String[] users = StringUtils.split(dto.getUserid(), SEPARATOR);
		String[] amounts = StringUtils.split(dto.getAmount(), SEPARATOR);
		log.info("switchTypeToGameFundVos users "+ users.length + ", Userid =" + dto.getUserid());
		log.info("switchTypeToGameFundVos amounts "+ amounts.length +", Amount =" + dto.getAmount());
		if(dto.getUserid()==null){
			log.error("Userid must not null");
			throw new Exception("Userid must not null");
		}
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		
		switch (dto.getType().intValue()) {
		
		case 6001:
			FundGameVos fundGameVos = new FundGameVos();
			fundGameVos.setIsAclUser(1L);
			fundGameVos.setOperator(-1L);
			fundGameVos.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.ACTIVITY_DETAIL_TYPE));
			fundGameVos.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos.setExCode(dto.getOrderCodeList());
			fundGameVos.setPlanCode(dto.getPlanCodeList());
			fundGameVos.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =2002, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos);
			break;	
		default:
			break;
		}
		
		//save game_risk_fund
		saveRiskFund(dto, users, amounts, GameFundTypesUtils.GAME_CANCEL_BET_TYPE);
		
		GameFundServiceBean result = new GameFundServiceBean();
		result.setFundList(list);
		result.setTrans(gameTransactionService.parseGameTransactionFundList(dto, list));	
		return result;
		
	}

}
