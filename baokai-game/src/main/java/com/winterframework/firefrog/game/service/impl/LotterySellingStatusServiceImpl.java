package com.winterframework.firefrog.game.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameSeriesCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import com.winterframework.firefrog.game.service.ILotterySellingStatusService;

/** 
* @ClassName: LotterySellingStatusServiceImpl 
* @Description: 彩种销售状态Service实现类
* @author Denny 
* @date 2013-8-29 上午12:55:32 
*  
*/
@Service("lotterySellingStatusServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class LotterySellingStatusServiceImpl implements ILotterySellingStatusService {
	
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	
	@Resource(name = "gameSeriesCheckDaoImpl")
	private IGameSeriesCheckDao gameSeriesCheckDaoImpl;

	@Override
	public Integer queryLotterySellingStatus(long lotteryid) throws Exception {
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getLotterySellingStatus(lotteryid);
		Integer lotterySellingStatus ;
		
		if (gsc == null) {
			lotterySellingStatus = gameSeriesDaoImpl.getLotterySellingStatus(lotteryid);
		}else{
			lotterySellingStatus = (Integer)gsc.getStatus();
		}
		return lotterySellingStatus;
	}

	@Override
	public void modifyLotterySellingStatus(Integer checkStatus, Integer status, Long lotteryid ,Date takeOffTime) throws Exception {
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 2);

		if (checkStatus == 2 && gsc == null) {
			// copy to check table
			GameSeries gs = gameSeriesDaoImpl.getByLotteyId(lotteryid);
			GameSeriesCheck check = com.winterframework.firefrog.game.dao.vo.VOConverter.gameSeries2GameSeriesCheck(gs);
			check.setStatus(status);
			check.setUpdateTime(new Date());
			check.setCheckStatus(3);
            //标记为销售状态
            check.setCheckType(2);
            check.setTakeOffTime(takeOffTime);
			gameSeriesCheckDaoImpl.insertOne(check);
		} else {
			// update check table
			gsc.setStatus(status);
			gsc.setUpdateTime(new Date());
			gsc.setCheckStatus(3);
            gsc.setCheckType(2);
            gsc.setTakeOffTime(takeOffTime);
			gameSeriesCheckDaoImpl.updateGameSeriesCheck(gsc);
		}
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 6, 3);
		gameSeriesDaoImpl.updateLastModifyDate(lotteryid);
	}

	@Override
	public void checkLotterySellingStatus(Long lotteryid, Long auditType) throws Exception{
		gameSeriesCheckDaoImpl.updateToPublish(lotteryid, auditType);
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 6, auditType==1 ? 4 : 5);
		if(auditType!=1){
			this.cancelModifySellingStatus(lotteryid);
		}
	}
	
	private void cancelModifySellingStatus(Long lotteryid)throws Exception{
		gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 2);
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 6, 1);
		//gameSeriesDaoImpl.updateForPublish(lotteryid, 1, new Date(),null);
	}

	@Override
	public void publishLotterySellingStatus(Long lotteryid, Long publishType) throws Exception{
		//1发布通过 2发布不通过
		if(publishType == 1){
			GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getLotterySellingStatus(lotteryid);
			int status = gsc.getStatus();
			Date currentTime = new Date();
			gameSeriesDaoImpl.updateForPublish(lotteryid, status, currentTime,gsc.getTakeOffTime());
			
			gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 2);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 6, 1);
		}else{
			gameSeriesCheckDaoImpl.updateSeriesCheckToNotPublished(lotteryid);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 6, 6);
			cancelModifySellingStatus(lotteryid);
		}
	}

	@Override
	public int queryLotteryCheckStatus(long lotteryid) {
		Integer lotteryCheckStatus = (Integer)gameSeriesCheckDaoImpl.getLotteryCheckStatus(lotteryid);
		if (lotteryCheckStatus == null) {
			lotteryCheckStatus = 2;
		}
		return lotteryCheckStatus;
	}
	
	@Override
	public Date queryLotterySellingTakeOffTime(long lotteryid) throws Exception {
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getLotterySellingStatus(lotteryid);
		Date lotterySellingStatus ;
		
		if (gsc == null) {
			lotterySellingStatus = null;
		}else{
			lotterySellingStatus = gsc.getTakeOffTime();
		}
		return lotterySellingStatus;
	}

}
