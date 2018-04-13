package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.web.dto.TORiskDTO;
/**
 * 
* @ClassName: IGameRiskFundOperateService 
* @Description: 风控审核操作Service 
* @author Richard
* @date 2014-1-5 下午1:36:02 
*
 */
public interface IGameRiskFundOperateService {

	
	/**
	 * 
	* @Title: saveRiskFund 
	* @Description: 保存Game_Risk_Fund 信息
	* @throws Exception
	 */
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception;
	

}
