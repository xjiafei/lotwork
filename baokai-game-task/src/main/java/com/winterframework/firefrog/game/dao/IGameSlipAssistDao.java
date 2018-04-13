package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 注单辅助DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameSlipAssistDao extends BaseDao<GameSlipAssist> {
	List<GameSlipAssist> getBySlipId(Long slipId) throws Exception;	
}
