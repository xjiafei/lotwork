package com.winterframework.firefrog.game.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.firefrog.game.service.IGameSlipDetailService;
import com.winterframework.firefrog.game.service.IGameSlipDrawService;
import com.winterframework.firefrog.game.service.IGameSlipService;

/**
 *GamePlanDetailServiceImpl
 * @ClassName
 * @Description 注单服务类
 * @author ibm
 * 2014年9月7日
 */
@Repository("gameSlipServiceImpl") 
@Transactional
public class GameSlipServiceImpl implements IGameSlipService {
	private Logger log = LoggerFactory.getLogger(GameSlipServiceImpl.class);
	
	@Resource(name="gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;
	@Resource(name="gameSlipDrawServiceImpl")
	private IGameSlipDrawService gameSlipDrawService;
	@Resource(name="gameSlipDetailServiceImpl")
	private IGameSlipDetailService gameSlipDetailService;
	
	
	@Override
	public int save(GameContext ctx, GameSlip slip) throws Exception {
		if(slip==null) return 0;
		if(slip.getId()==null){
			this.gameSlipDao.insert(slip);
		}else{
			this.gameSlipDao.update(slip);
		}
		return 1;
	} 
	@Override
	public GameSlip getById(GameContext ctx, Long slipId) throws Exception {
		return gameSlipDao.getById(slipId); 
	}
	@Override
	public List<GameSlip> getByOrderId(GameContext ctx, Long orderId)
			throws Exception { 
		return gameSlipDao.querySlipByOrder(orderId);
	}
	@Override
	public int reset(GameContext ctx, GameSlip slip) throws Exception {
		log.info("重置注单.");
		if(slip==null) return 0;
		
		int status=slip.getStatus();
		if(status==GameSlip.Status.WAIT.getValue()) return 0;
		
		this.cancel(ctx, slip);
		
		slip.setStatus(GameSlip.Status.WAIT.getValue());
		slip.setEvaluateWin(0L);
		slip.setWinNumber(0L);
		slip.setWinLevel(0L);
		this.save(ctx, slip);
		return 1;
	}
	@Override
	public int resetByOrder(GameContext ctx, GameOrder order) throws Exception {
		log.info("重置注单 by order.");
		if(order==null) return 0;
		List<GameSlip> slipList=this.getByOrderId(ctx, order.getId());
		int count=0;
		if(slipList!=null && slipList.size()>0){
			for(GameSlip slip:slipList){
				count+=this.reset(ctx, slip);
			}
		}
		return count;
	}  
	@Override
	public int cancel(GameContext ctx, GameSlip slip) throws Exception { 
		if(slip==null) return 0;
		 
		int status=slip.getStatus(); 
		if(status==GameSlip.Status.CANCEL.getValue()) return 0;
		
		slip.setStatus(GameSlip.Status.CANCEL.getValue());
		this.save(ctx, slip); 
		return 1;
	} 
	@Override
	public int cancelByOrder(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0;
		int cancelCount=0;
		List<GameSlip> slipList= this.getByOrderId(ctx, order.getId());
		if(slipList!=null && slipList.size()>0){
			for(GameSlip slip:slipList){
				cancelCount=+this.cancel(ctx, slip);
			}
		}
		return cancelCount;
	}
	@Override
	public boolean isWin(GameContext ctx, GameOrder order, GameSlip slip)
			throws Exception { 
		return this.gameSlipDrawService.isWin(ctx, order, slip);
	}
	@Override
	public boolean draw(GameContext ctx, GameOrder order,GameSlip slip) throws Exception {
		return this.gameSlipDrawService.doBusiness(ctx,order,slip);  
	} 
	
	@Override
	public Map<Long, Long> getBigAwardCount(GameContext ctx, GameSlip slip)
			throws Exception {
		if(slip==null) return null;
		List<GameSlipDetail> slipDetailList=this.gameSlipDetailService.getByParentId(ctx, slip.getId());
		if(slipDetailList!=null && slipDetailList.size()>0){ 
			Long countFirst=0L,countSecond=0L;
			for(GameSlipDetail slipDetail:slipDetailList){
				if(slipDetail.getWinLevel()!=null){
					 if(slipDetail.getWinLevel()==1L){
						 countFirst++;
					 }else if(slipDetail.getWinLevel()==1L){
						 countSecond++;
					 }
				}
			} 
			if(countFirst+countSecond>0){
				Map<Long,Long> countMap=new HashMap<Long,Long>();
				countMap.put(1L, countFirst);
				countMap.put(2L, countSecond);
				return countMap;
			} 
		} 
		return null;
	}
	  
	@Override
	public Map<String, Long> getSummary(GameContext ctx, Long orderId)
			throws Exception {
		if(orderId==null) return null;
		List<GameSlip> slipList=this.getByOrderId(ctx, orderId);
		if(slipList!=null && slipList.size()>0){
			Map<String,Long> summaryMap=new HashMap<String,Long>();
			Long maxWinAmount=0L;
			Long maxWinRatio=0L;
			Long totalWinAmount=0L;
			Long tmpWinRatio=0L;
			for(GameSlip slip:slipList){
				maxWinAmount=maxWinAmount>slip.getEvaluateWin()?maxWinAmount:slip.getEvaluateWin();
				//加上除数的二分之一再除以除数  到达四舍五入的效果
				tmpWinRatio=(slip.getEvaluateWin()*10000+slip.getTotamount()/2)/slip.getTotamount();
				maxWinRatio=maxWinRatio>tmpWinRatio?maxWinRatio:tmpWinRatio; 
				totalWinAmount+=slip.getEvaluateWin();
			}
			summaryMap.put("maxSlipWinAmount", maxWinAmount);
			summaryMap.put("maxSlipWinRatio", maxWinRatio);
			summaryMap.put("totalSlipWinAmount", totalWinAmount);
			return summaryMap;
		}
		return null;
	}
	@Override
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus)
			throws Exception { 
		return gameSlipDao.changeStatus(orderId,fromStatus,toStatus);
	}
}
