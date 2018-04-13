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
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
 
/**
 * 风控资金服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年12月12日
 */
@Transactional(rollbackFor=Exception.class)
@Service("gameRiskFundServiceImpl")
public class GameRiskFundServiceImpl implements IGameRiskChangeFundGameVoService {

	private static final Logger log = LoggerFactory.getLogger(GameRiskFundServiceImpl.class);
	private static final String SEPARATOR = ",";
	
	@Resource(name = "gameRiskFundDaoImpl")
	private IGameRiskFundDao gameRiskFundDao;	
	@Resource(name = "gameTransactionRecordServiceImpl")
	private ITransactionRecordService gameTransactionService;	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Override
	public GameFundServiceBean riskProcess(List<TORiskDTO> list) throws Exception { 
		log.info("开始资金请求处理");
		GameFundServiceBean result = new GameFundServiceBean();
		List<FundGameVos> fundList = new ArrayList<FundGameVos>();
		List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		if(list!=null && list.size()>0){
			for(TORiskDTO dto : list){
				GameFundServiceBean innerResult = switchTypeToGameFundVos(dto);
				if(innerResult!=null && innerResult.getFundList()!=null && innerResult.getFundList().size()>0){					
					//可能存在 返回多条或一条，所以要进行循环获取。
					for (int i = 0; i < innerResult.getFundList().size(); i++) {
						fundList.add(innerResult.getFundList().get(i));
						trans.add(innerResult.getTrans().get(i));
					}	
				}
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
		if(dto==null) return null;
		String[] users = StringUtils.split(dto.getUserid(), SEPARATOR);
		String[] amounts = StringUtils.split(dto.getAmount(), SEPARATOR);
		log.info("switchTypeToGameFundVos users "+ users.length + ", Userid =" + dto.getUserid());
		log.info("switchTypeToGameFundVos amounts "+ amounts.length +", Amount =" + dto.getAmount());
		 
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		int fundType=dto.getType().intValue();
		String reason=GameFundTypesUtils.getFundDigest(fundType);
		String note=GameFundTypesUtils.getFundMessage(fundType);
		Long isAclUser=1L;
		Long operator=-1L;
		if(fundType!=GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE && fundType!=GameFundTypesUtils.GAME_CANCEL_RET_DETEAIL_TYPE
		   && fundType!=GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE && fundType!=GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE
		   && fundType!=GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE){
			FundGameVos fundGameVos = packFundVos(dto, users[0], amounts[0], fundType,reason,note,isAclUser,operator);
			log.info("switchTypeToGameFundVos type ="+fundType+", users[0] ="+ users[0] + ", amounts[0]="+amounts[0]);
			list.add(fundGameVos); 
		}else{
			if(fundType==GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE) return null;
			//GM-RSXX-1			确认派奖时（方案号），投注本人所得的返点
			//GM-RHAX-2			确认派奖时（方案号），投注人所属的不同上级，分别获得的返点记录
			String selfUser=users[users.length-1];
			for (int i = 0; i < users.length; i++) {
				Long amount = Long.parseLong(amounts[i]);
				if(amount.longValue() == 0L){
					continue;
				}
				if(fundType==GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE){
					reason=Long.parseLong(selfUser) == Long.parseLong(users[i]) ? "GM-RSXX-1" : "GM-RHAX-2";
					note=Long.parseLong(selfUser) == Long.parseLong(users[i]) ? 
						null:customerDao.getUserNameById(Long.parseLong(selfUser)) + "投注产生";
				}else if(fundType==GameFundTypesUtils.GAME_CANCEL_RET_DETEAIL_TYPE){
					reason=Long.parseLong(selfUser) == Long.parseLong(users[i]) ? "GM-RRSX-1" : "GM-RRHA-2"; 
				}else if(fundType==GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE || fundType==GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE){ 
					isAclUser=0L;
					operator=Long.parseLong(users[0]);
				}
				FundGameVos fundGameVos = packFundVos(dto, users[i], amounts[i], fundType,reason,note,isAclUser,operator); 
				log.info("switchTypeToGameFundVos type ="+fundType+", users[i] ="+ users[i] + ", amounts[i]="+amounts[i]);
				list.add(fundGameVos);
			} 
		}
				 
		saveRiskFund(dto, users, amounts, GameFundTypesUtils.getRiskFundType(fundType));
		GameFundServiceBean result = new GameFundServiceBean();
		result.setFundList(list);
		result.setTrans(gameTransactionService.parseGameTransactionFundList(dto, list));	
		return result;
	}

	private FundGameVos packFundVos(TORiskDTO dto, String user,
			String amount, int fundType,String reason,String note,Long isAclUser,Long operator) {
		FundGameVos fundGameVos = new FundGameVos();
		fundGameVos.setIsAclUser(isAclUser);
		fundGameVos.setOperator(operator);
		fundGameVos.setReason(reason);
		fundGameVos.setVals(user+ "|"+amount); 
		fundGameVos.setExCode(dto.getOrderCodeList());
		fundGameVos.setPlanCode(dto.getPlanCodeList());
		fundGameVos.setNote(note);
		return fundGameVos; 
	}  
	private void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception {		
		gameRiskFundDao.saveRiskFund(dto, users, amouts, Status);
	}	

}
