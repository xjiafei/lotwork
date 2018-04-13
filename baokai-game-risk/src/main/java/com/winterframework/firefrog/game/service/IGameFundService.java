package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.exception.GameFundException;
import com.winterframework.firefrog.game.service.bean.GameFundServiceBean;

/** 
* @ClassName IGameFundService 
* @Description 资金交易类 
* @author  hugh
* @date 2014年4月23日 下午2:43:34 
*  
*/
public interface IGameFundService {

	/** 
	* @Title: fundActions 
	* @Description: 审核中心 请求fundActions 资金交易
	* @param gameFundServiceBean
	* @throws GameFundException
	*/
	void fundActions(GameFundServiceBean gameFundServiceBean) throws GameFundException;
	
	
}
