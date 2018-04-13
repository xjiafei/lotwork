package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameIssueEntity;

/**
 * 
* @ClassName: IGameOrderSecurityService 
* @Description: 订单安全处理服务
* @author Richard
* @date 2013-11-18 下午1:47:44 
*
 */
public interface IGameOrderSecurityService {

	public boolean checkOrderSecurity(GameIssueEntity entity) throws Exception;
}
