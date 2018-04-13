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
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.service.IGameRiskChangeFundGameVoService;
import com.winterframework.firefrog.game.service.IGameRiskFundOperateService;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;

/**
 * 
* @ClassName: GameDistributeFundService 
* @Description: 风控派奖流程Serives
* @author Richard hugh
* @date 2014-1-5 下午1:44:15 
*
 */
@Transactional(rollbackFor=Exception.class)
@Service("gameDistributeFundServiceImpl")
public class GameDistributeFundService implements IGameRiskChangeFundGameVoService, IGameRiskFundOperateService {

	private static final Logger log = LoggerFactory.getLogger(GameDistributeFundService.class);
	private static final String SEPARATOR = ",";
	
	@Resource(name = "gameRiskFundDaoImpl")
	private IGameRiskFundDao gameRiskFundDao;
	
	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionService;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Override
	public GameFundServiceBean riskProcess(List<TORiskDTO> list) throws Exception {
		
		log.info("开始处理投注冻结操作流程。");
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
		
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		
		switch (dto.getType().intValue()) {
		
			case 3002://派发奖金
				FundGameVos fundGameVos = new FundGameVos();
				fundGameVos.setIsAclUser(1L);
				fundGameVos.setOperator(-1L);
				fundGameVos.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE));
				fundGameVos.setVals(users[0] + "|"+amounts[0]); //派发奖金时， 用户和资金只有一条。
				fundGameVos.setExCode(dto.getOrderCodeList());
				fundGameVos.setPlanCode(dto.getPlanCodeList());
				fundGameVos.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
				log.info("switchTypeToGameFundVos type =3002, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
				list.add(fundGameVos);
				break;
			
			case 5004://派奖后投注资金解冻   
				//方案。
				FundGameVos fundGameVos2= new FundGameVos();
				fundGameVos2.setIsAclUser(1L);
				fundGameVos2.setOperator(-1L);
				fundGameVos2.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_BET_UNFREEZER_DETEAIL_TYPE));
				fundGameVos2.setVals(users[0] + "|"+amounts[0]);
				fundGameVos2.setExCode(dto.getOrderCodeList());
				fundGameVos2.setPlanCode(dto.getPlanCodeList());
				fundGameVos2.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
				log.info("switchTypeToGameFundVos type =5004, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
				list.add(fundGameVos2);
				break;
			case 5008: //派奖后返点解冻  -->
				//GM-RSXX-1			确认派奖时（方案号），投注本人所得的返点
				//GM-RHAX-2			确认派奖时（方案号），投注人所属的不同上级，分别获得的返点记录
				for (int i = 0; i < users.length; i++) {
					Long amount = Long.parseLong(amounts[i]);
					if(amount.longValue() == 0L){
						continue;
					}
					FundGameVos fundGameVos5 = new FundGameVos();
					fundGameVos5.setIsAclUser(1L);
					fundGameVos5.setOperator(-1L);
					//GM-RTRX-1 自己， 2 为上级。
					//users 最后一个用户为订单的拥有者
					fundGameVos5.setReason(Long.parseLong(users[users.length-1]) == Long.parseLong(users[i]) ? "GM-RSXX-1" : "GM-RHAX-2");
					fundGameVos5.setVals(users[i] + "|"+amounts[i]);
					fundGameVos5.setExCode(dto.getOrderCodeList());
					fundGameVos5.setPlanCode(dto.getPlanCodeList());
					fundGameVos5.setNote(Long.parseLong(users[users.length-1]) == Long.parseLong(users[i]) ? 
							null:customerDao.getUserNameById(Long.parseLong(users[users.length-1])) + "投注产生");
					log.info("switchTypeToGameFundVos type =5008, users[0] ="+ users[i] + ", amounts[0]="+amounts[i]);
					list.add(fundGameVos5);
				}
				
				break;
				
			case 5009: //派奖后计划订单解冻 
				//追号
				//这里针对于追号。 从冻结到余额，从余额到冻结。
//				FundGameVos fundGameVos3 = new FundGameVos();
//				fundGameVos3.setIsAclUser(1L);
//				fundGameVos3.setOperator(-1L);
//				fundGameVos3.setReason("GM-RVCP-1"); //解冻到余额
//				fundGameVos3.setVals(users[0] + "|"+amounts[0]);
//				log.info("switchTypeToGameFundVos type =5009, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
//				list.add(fundGameVos3);
//				
//				FundGameVos fundGameVos4 = new FundGameVos();
//				fundGameVos4.setIsAclUser(1L);
//				fundGameVos4.setOperator(-1L);
//				fundGameVos4.setReason("GM-DVCN-1");
//				fundGameVos4.setVals(users[0] + "|"+amounts[0]);
//				log.info("switchTypeToGameFundVos type =5009, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
//				
//				list.add(fundGameVos4);
//				FundGameVos fundGameVos7= new FundGameVos();
//				fundGameVos7.setIsAclUser(1L);
//				fundGameVos7.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_PLAN_UNFREEZER_DETEAIL_TYPE));
//				fundGameVos7.setVals(users[0] + "|"+amounts[0]);
//				log.info("switchTypeToGameFundVos type =5009, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
//				fundGameVos7.setOperator(-1L);
//				list.add(fundGameVos7);
				
				FundGameVos fundGameVos3 = new FundGameVos();
				fundGameVos3.setIsAclUser(1L);
				fundGameVos3.setOperator(-1L);
				fundGameVos3.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_PLAN_UNFREEZER_DETEAIL_TYPE)); //解冻到余额
				fundGameVos3.setVals(users[0] + "|"+amounts[0]);
				fundGameVos3.setExCode(dto.getOrderCodeList());
				fundGameVos3.setPlanCode(dto.getPlanCodeList());
				fundGameVos3.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
				log.info("switchTypeToGameFundVos type =5009, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
				list.add(fundGameVos3);				
				break;
	
			default:
				break;
		}
		
		saveRiskFund(dto, users, amounts, GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
		GameFundServiceBean result = new GameFundServiceBean();
		result.setFundList(list);
		result.setTrans(gameTransactionService.parseGameTransactionFundList(dto, list));	
		return result;
	}
	

	@Override
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception {		
		gameRiskFundDao.saveRiskFund(dto, users, amouts, Status);
	}	

}
