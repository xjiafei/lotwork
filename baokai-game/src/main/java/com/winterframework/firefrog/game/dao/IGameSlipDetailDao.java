package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 注单明细DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
public interface IGameSlipDetailDao extends BaseDao<GameSlipDetail> {
	List<GameSlipDetail> getByParentId(Long parentId) throws Exception;	
}
