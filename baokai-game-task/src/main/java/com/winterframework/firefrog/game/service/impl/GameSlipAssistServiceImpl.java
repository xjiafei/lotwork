package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameSlipAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.service.IGameSlipAssistService;


/**
 * 注单辅助服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
@Repository("gameSlipAssistServiceImpl") 
@Transactional
public class GameSlipAssistServiceImpl implements IGameSlipAssistService {
	@Resource(name="gameSlipAssistDaoImpl")
	private IGameSlipAssistDao gameSlipAssistDao; 
	 
	@Override
	public List<GameSlipAssist> getBySlipId(GameContext ctx, Long slipId)
			throws Exception {
		return this.gameSlipAssistDao.getBySlipId(slipId);
	}
	@Override
	public int win(GameContext ctx, GameSlip slip, GameSlipAssist slipAssist)
			throws Exception {
		if(slip==null || slipAssist==null) return 0;
		/*???????????????
		 * Long winCount=(Long)ctx.get("winCount");
		if(winCount!=null && winCount>0){
			slipAssist.setWinNumber(winCount); 
			this.gameSlipAssistDao.update(slipAssist);
		}else{
			throw new GameException("Game slip assist wins but no win count.");
		}*/
		return 1;
	}
	@Override
	public int unwin(GameContext ctx, GameSlip slip, GameSlipAssist slipAssist)
			throws Exception {
		if(slip==null || slipAssist==null) return 0;
		slipAssist.setWinNumber(0L);
		this.gameSlipAssistDao.update(slipAssist);
		return 1;
	}
}
