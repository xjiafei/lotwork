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
@Service("gameCancelFundServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class GameCancelFeeServiceImpl implements IGameRiskFundOperateService, IGameRiskChangeFundGameVoService {
	
	private static final Logger log = LoggerFactory.getLogger(GameCancelFeeServiceImpl.class);
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
		log.info("开始处理游戏撤销操作风控系统流程。");
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
	* @Title: isExistRiskFund 
	* @Description: 根据订单Code判断已发送返点信息到资金系统
	* @param orderCode
	* @return true 已存在， false 不存在
	 */
	private boolean isExistRiskFund(String orderCode){
		
		List<GameRiskFund> list = gameRiskFundDao.getGameRiskFundByOrderCode(orderCode);
		if(null != list && list.size()>0){
			return true;
		}
		return false;
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
		case 2001:
			//返点撤销
			//这里要判断是否需要发送返点信息到资金系统， GM-CRRX-1 GM-CRRX-2
			// -->
			//GM-RRSX-1			撤销返点操作时，扣除的投注本人所得的返点
			//GM-RRHA-2			撤销返点操作时，扣除的投注人所属的不同上级，分别获得的返点记录
			boolean _is = false;
			//如果 orderCode为空，则表明之前没有发过返点信息到资金系统
			if(StringUtils.isNotBlank(dto.getOrderCodeList())){
				
				log.info("begin isExistRiskFund orderCode = " + dto.getOrderCodeList());
				//已存在就要发FundGameVos信息。
				_is = isExistRiskFund(dto.getOrderCodeList());
			}
			
			if(_is){
			
				for (int i = 0; i < users.length; i++) {
					
					FundGameVos fundGameVos5 = new FundGameVos();
					fundGameVos5.setIsAclUser(1L);
					fundGameVos5.setOperator(-1L);
					//GM-RTRX-1 自己， 2 为上级。
					//users 最后一个用户为订单的拥有者
					fundGameVos5.setReason(Long.parseLong(users[users.length-1]) == Long.parseLong(users[i]) ? "GM-RRSX-1" : "GM-RRHA-2");
					fundGameVos5.setVals(users[i] + "|"+amounts[i]);
					fundGameVos5.setExCode(dto.getOrderCodeList());
					fundGameVos5.setPlanCode(dto.getPlanCodeList());
					//fundGameVos5.setNote("撤销返点");
					log.info("switchTypeToGameFundVos type =2001, users["+i+"] ="+ users[i] + ", amounts["+i+"]="+amounts[i]);
					list.add(fundGameVos5);
				}
			
			}
			break;
		case 2002://撤销奖金 GM-CPDX-1 -->  GM-BDRX-1
			FundGameVos fundGameVos = new FundGameVos();
			fundGameVos.setIsAclUser(1L);
			fundGameVos.setOperator(-1L);
			fundGameVos.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_CANCEL_AWARD_DETEAIL_TYPE));
			fundGameVos.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos.setExCode(dto.getOrderCodeList());
			fundGameVos.setPlanCode(dto.getPlanCodeList());
			fundGameVos.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =2002, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos);
			break;
		case 2003://撤销费用 GM-CFXX-1  -->  GM-CFCX-1
			FundGameVos fundGameVos2 = new FundGameVos();
			fundGameVos2.setIsAclUser(1L);
			fundGameVos2.setOperator(-1L);
			fundGameVos2.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE));
			fundGameVos2.setVals(users[0] + "|"+amounts[0]);
			fundGameVos2.setExCode(dto.getOrderCodeList());
			fundGameVos2.setPlanCode(dto.getPlanCodeList());
			fundGameVos2.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =2003, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos2);
			break;
		case 3001://本金返还加款 GM-RVCP-1 -> GM-CRVC-4
			FundGameVos fundGameVos3 = new FundGameVos();
			fundGameVos3.setIsAclUser(1L);
			fundGameVos3.setOperator(-1L);
			fundGameVos3.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_BET_RETURN_DETEAIL_TYPE));
			fundGameVos3.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos3.setExCode(dto.getOrderCodeList());
			fundGameVos3.setPlanCode(dto.getPlanCodeList());
			fundGameVos3.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =3001, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos3);
			
			break;
		case 5001://用户终止追号预约投注解冻 GM-CCBX-1  --> GM-CRVC-1
			FundGameVos fundGameVos7 = new FundGameVos();
			fundGameVos7.setIsAclUser(1L);
			fundGameVos7.setOperator(-1L);
			fundGameVos7.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE));
			fundGameVos7.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos7.setExCode(dto.getOrderCodeList());
			fundGameVos7.setPlanCode(dto.getPlanCodeList());
			fundGameVos7.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5001, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos7);
			break;
		case 5002:
			FundGameVos fundGameVos8 = new FundGameVos();
			fundGameVos8.setIsAclUser(1L);
			fundGameVos8.setOperator(-1L);
			fundGameVos8.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE));
			fundGameVos8.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos8.setExCode(dto.getOrderCodeList());
			fundGameVos8.setPlanCode(dto.getPlanCodeList());
			fundGameVos8.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5002, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos8);
			break;
		case 5003://	追中即停预约投注解冻 , 追中（x元）即停，解冻剩余追号金额   GM-CRVC-2
			FundGameVos fundGameVos9 = new FundGameVos();
			fundGameVos9.setIsAclUser(1L);
			fundGameVos9.setOperator(-1L);
			fundGameVos9.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_CONDITION_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE));
			fundGameVos9.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos9.setExCode(dto.getOrderCodeList());
			fundGameVos9.setPlanCode(dto.getPlanCodeList());
			fundGameVos9.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5003, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos9);
			break;
		case 5005://用户撤单投注资金解冻 GM-CCBX-1 --> GM-CRVC-1
			FundGameVos fundGameVos4 = new FundGameVos();
			fundGameVos4.setIsAclUser(1L);
			fundGameVos4.setOperator(-1L);
			fundGameVos4.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE));
			fundGameVos4.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos4.setExCode(dto.getOrderCodeList());
			fundGameVos4.setPlanCode(dto.getPlanCodeList());
			fundGameVos4.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5005, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos4);
			
			break;
		case 5006://系统撤单投注资金解冻 GM-CCBX-1 --> GM-CRVC-1
			FundGameVos fundGameVos6 = new FundGameVos();
			fundGameVos6.setIsAclUser(1L);
			fundGameVos6.setOperator(-1L);
			fundGameVos6.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_SYS_CANCEL_BET_UNFREEZER_DETEAIL_TYPE));
			fundGameVos6.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos6.setExCode(dto.getOrderCodeList());
			fundGameVos6.setPlanCode(dto.getPlanCodeList());
			fundGameVos6.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5006, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos6);
			break;
			
		case 5010:   //管理员撤销解冻资金
			FundGameVos fundGameVos10 = new FundGameVos();
			fundGameVos10.setIsAclUser(1L);
			fundGameVos10.setOperator(-1L);
			fundGameVos10.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_ADMIN_UNFREEZER_TYPE));
			fundGameVos10.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos10.setExCode(dto.getOrderCodeList());
			fundGameVos10.setPlanCode(dto.getPlanCodeList());
			fundGameVos10.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5010, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos10);
			break;
		case 5011:   //管理员撤销解冻资金
			FundGameVos fundGameVos11 = new FundGameVos();
			fundGameVos11.setIsAclUser(1L);
			fundGameVos11.setOperator(-1L);
			fundGameVos11.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_ADMIN_RETURN_TYPE));
			fundGameVos11.setVals(users[0] + "|"+amounts[0]); 
			fundGameVos11.setExCode(dto.getOrderCodeList());
			fundGameVos11.setPlanCode(dto.getPlanCodeList());
			fundGameVos11.setNote(GameFundTypesUtils.getFundMessage(dto.getType().intValue()));
			log.info("switchTypeToGameFundVos type =5011, users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos11);
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
