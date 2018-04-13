package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameTrendIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.firefrog.game.dao.vo.GameTrendIssue;
import com.winterframework.firefrog.game.service.IGameTrendIssueService;


 
/**
 * 走势图奖期服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月23日
 */
@Service("gameTrendIssueServiceImpl") 
@Transactional
public class GameTrendIssueServiceImpl implements IGameTrendIssueService {
	@Resource(name="gameTrendIssueDaoImpl")
	private IGameTrendIssueDao gameTrendIssueDao; 
	  
	@Override
	public int save(GameContext ctx, GameTrendIssue trendIssue)
			throws Exception {
		if(trendIssue==null) return 0;
		if(trendIssue.getId()!=null){
			this.gameTrendIssueDao.update(trendIssue);
		}else{
			this.gameTrendIssueDao.insert(trendIssue);
		}
		return 1;
	}
	@Override
	public int remove(GameContext ctx, Long id) throws Exception { 
		return this.gameTrendIssueDao.delete(id);
	}
	
	@Override
	public GameTrendIssue getByLotteryIdAndIssueCode(GameContext ctx,
			Long lotteryId, Long issueCode) throws Exception { 
		return this.gameTrendIssueDao.getByLotteryIdAndIssueCode(lotteryId, issueCode);
	}
}
