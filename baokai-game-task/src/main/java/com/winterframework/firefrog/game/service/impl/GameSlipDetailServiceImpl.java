package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameSlipDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.firefrog.game.service.IGameSlipDetailService;


/**
 * 注单明细服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */	
@Service("gameSlipDetailServiceImpl") 
@Transactional
public class GameSlipDetailServiceImpl implements IGameSlipDetailService {
	@Resource(name="gameSlipDetailDaoImpl")
	private IGameSlipDetailDao gameSlipDetailDao; 
	 
	@Override
	public List<GameSlipDetail> getByParentId(GameContext ctx, Long parentId)
			throws Exception {
		return this.gameSlipDetailDao.getByParentId(parentId);
	} 
	@Override
	public int save(GameContext ctx, GameSlip slip, GameSlipDetail slipDetail)
			throws Exception {
		if(slipDetail==null) return 0;
		if(slipDetail.getId()!=null){
			this.gameSlipDetailDao.update(slipDetail);
		}else{
			this.gameSlipDetailDao.insert(slipDetail);
		}
		return 1;
	}
}
