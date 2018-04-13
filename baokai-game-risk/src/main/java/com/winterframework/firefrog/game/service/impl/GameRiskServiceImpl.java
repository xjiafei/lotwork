package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.service.IGameFundService;
import com.winterframework.firefrog.game.service.IGameRiskChangeFundGameVoService;
import com.winterframework.firefrog.game.service.IGameRiskService;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 * 
* @ClassName: GameRiskServiceImpl 
* @Description: 风控中心服务类
* @author Richard, David
* @date 2013-12-18 上午11:37:12 
*
 */
@Service("gameRiskServiceImpl")
@Transactional
public class GameRiskServiceImpl implements IGameRiskService {

	private final Logger log = LoggerFactory.getLogger(GameRiskServiceImpl.class);

	@Resource(name = "gameFundServiceImpl")
	private IGameFundService gameFundServiceImpl;

	@Resource(name = "gameBetAndFreezeServiceImpl")
	private IGameRiskChangeFundGameVoService betFreezeService;

	@Resource(name = "gameDistributeFundServiceImpl")
	private IGameRiskChangeFundGameVoService distributeFundService;

	@Resource(name = "gameCancelFundServiceImpl")
	private IGameRiskChangeFundGameVoService gameCancelFundService;

	@Resource(name = "activityFundServiceImpl")
	private IGameRiskChangeFundGameVoService activityFundServiceImpl;
	@Resource(name = "gameRiskFundServiceImpl")
	private IGameRiskChangeFundGameVoService gameRiskFundService;

	/**
	 * 
	* Title: integration 风控审核中心游戏资金操作
	* Description: 建议如果某个集成接口代码过长,建议委托代理类来实现，例如封装摘要，循环等功能
	* @param request 
	 * @throws Exception 
	 */
	@Override
	public void integration(TORiskRequest request) throws Exception {

		log.info("进入风控审核中心游戏资金操作模块。");
		List<TORiskDTO> riskList = request.getToRiskDTOList();
		if (null == riskList || riskList.isEmpty()) {
			throw new RuntimeException("integration toRikDTOList is NULL.");
		}

		int gameFundType = request.getGameFundTypes();
		GameFundServiceBean result = new GameFundServiceBean();

		switch (gameFundType) {
		//投注冻结
		case GameFundTypesUtils.GAME_BET_FREEZEN_TYPE:
			//审核通知资金从余额到冻结 
			result = betFreezeService.riskProcess(riskList);
			break;
		//派奖
		case GameFundTypesUtils.GAME_DISTRIBUTE_TYPE:
			//派发奖金。
			result = distributeFundService.riskProcess(riskList);
			break;
		//投注扣款
		case GameFundTypesUtils.GAME_BET_REDUCE_TYPE:

			break;
		//撤销
		case GameFundTypesUtils.GAME_CANCEL_BET_TYPE:
			//撤销
			result = gameCancelFundService.riskProcess(riskList);
			break;

		case GameFundTypesUtils.ACTIVITY_TYPE:
			//撤销
			result = activityFundServiceImpl.riskProcess(riskList);
			break;
		default:
			break;
		}

		//调用资金系统
		gameFundServiceImpl.fundActions(result);

	}

	@Override
	public void integrationNew(TORiskRequest request) throws Exception {
		
		log.info("进入风控审核中心游戏资金操作模块。");
		List<TORiskDTO> riskList= request.getToRiskDTOList();
		if(null == riskList || riskList.isEmpty()){
			throw new RuntimeException("integration toRikDTOList is NULL.");
		} 
		GameFundServiceBean result = new GameFundServiceBean() ; 
		  
		result = gameRiskFundService.riskProcess(riskList);  
		
		//调用资金系统 
		this.fundActions(result);
		
	}

	private void fundActions(GameFundServiceBean result) throws Exception {
		//调用资金系统
		gameFundServiceImpl.fundActions(result);
	}

	@Deprecated
	private void fundActionsBatches(GameFundServiceBean result) throws Exception {
		//调用资金系统
		//gameFundServiceImpl.fundActions(result);
		if (result != null && result.getFundList() != null && result.getTrans() != null
				&& result.getFundList().size() > 0 && result.getTrans().size() > 0) {
			if (result.getFundList().size() != result.getTrans().size()) {
				throw new Exception("gameFundServiceBean object invalid.(fundList size and transList size mismatch.)");
			}
			GameFundServiceBean resultTemp = null;
			List<FundGameVos> fundList = result.getFundList();
			List<GameTransactionFund> trans = result.getTrans();
			int interval = 100;
			int interSize = (fundList.size() % interval) == 0 ? (fundList.size() / interval) : (fundList.size()
					/ interval + 1);
			int beginIndex = 0, endIndex = 0;
			for (int i = 0; i < interSize; i++) {
				beginIndex = i * interval;
				endIndex = ((i + 1) * interval) > fundList.size() ? fundList.size() : ((i + 1) * interval);
				resultTemp = new GameFundServiceBean();
				resultTemp.setFundList(fundList.subList(beginIndex, endIndex));
				resultTemp.setTrans(trans.subList(beginIndex, endIndex));
				try {
					gameFundServiceImpl.fundActions(resultTemp);
				} catch (Exception e) {
					log.error("调用firefrog资金请求失败", e);
					throw e;
				}
			}
		}
	}

}
