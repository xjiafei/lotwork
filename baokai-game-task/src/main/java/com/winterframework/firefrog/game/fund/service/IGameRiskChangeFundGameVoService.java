package com.winterframework.firefrog.game.fund.service; 

import java.util.List;

import com.winterframework.firefrog.game.fund.bean.GameFundServiceBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;


/**
 * 
* @ClassName: IGameRiskChangeFundService 
* @Description: 风控审核转换资金系统请求Service
* @author Richard hugh
* @date 2014-1-5 上午11:22:54 
*
 */
public interface IGameRiskChangeFundGameVoService {
	
	/**
	 * 处理风控审核系统信息，并返回GameFundResultServiceBean
	* @Title: riskProcess
	* @Description: 
	* @param list
	* @return
	* @throws Exception
	 */
	public GameFundServiceBean riskProcess(List<TORiskDTO> list) throws Exception;
	
	
}
