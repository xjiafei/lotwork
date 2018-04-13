package com.winterframework.firefrog.game.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.IGameOrderSecurityService;

/**
 * 
* @ClassName: GameOrderSecurityServiceImpl 
* @Description: 奖期安全处理类
* @author Richard
* @date 2013-11-18 下午1:51:32 
*
 */
@Service("gameOrderSecurityServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameOrderSecurityServiceImpl implements IGameOrderSecurityService {

	private static final Logger log = LoggerFactory.getLogger(GameOrderSecurityServiceImpl.class);

	/**
	 * 订单安全检查。比对
	* Title: checkOrderSecurity
	* Description:
	* @param entity
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameOrderSecurityService#checkOrderSecurity(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public boolean checkOrderSecurity(GameIssueEntity entity) throws Exception {

		log.debug("开始进行订单对比");

		log.debug("订单安全对比结束");
		//本期暂时不处理
		return false;
	}

}
