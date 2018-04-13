package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;

/** 
* @ClassName IGameWarnService 
* @Description 风控审核 
* @author  hugh
* @date 2014年4月22日 下午2:34:05 
*  
*/
public interface IGameWarnService {

	/** 
	* @Title: riskFlow 
	* @Description: 风控审核 验奖过程  调用派奖、返点派奖
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	boolean riskFlow(Long lotteryId, Long issueCode) throws GameRiskException;
	
	
	/** 
	* @Title: queryGameRiskConfig 
	* @Description: 获取审核配置信息
	* @return
	*/
	GameRiskConfig queryGameRiskConfig();
	
	void setGameReturnPointFundServcie(IGameReturnPointFundService gameReturnPointFundServcie);

	void setGameOrderWinFundServcie(IGameOrderWinFundService gameOrderWinFundServcie) ;
	
}
