package com.winterframework.firefrog.game.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
/**
 * 
* @ClassName: GameOrderDaoImpl 
* @Description: GameOrder Dao 实现类
* @author Richard
* @date 2014-1-5 上午11:48:29 
*
 */
@Repository("gameOrderDaoImpl")
public class GameOrderDaoImpl extends BaseIbatis3Dao<GameOrder> implements IGameOrderDao {

	@Override
	public Long getOrderWinAmount(String orderCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("getOrderWinAmount", orderCode);
	}
	
	@Override
	public GameOrder getOrderByCode(String orderCode){
		return this.sqlSessionTemplate.selectOne("getOrderByCode", orderCode);
	}

}
