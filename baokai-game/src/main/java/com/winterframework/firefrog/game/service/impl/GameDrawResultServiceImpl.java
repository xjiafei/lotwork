package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;

/**
 * 
* @ClassName: GameDrawServiceImpl 
* @Description: 彩票开奖结果处理服务。
* @author Richard
* @date 2013-11-18 下午2:33:58 
*
 */
@Service("gameDrawResultServiceImpl")
public class GameDrawResultServiceImpl implements IGameDrawResultService {

	private static final Logger log = LoggerFactory.getLogger(GameDrawResultServiceImpl.class);
	@Resource(name="gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;
	@Resource(name="gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Override
	public void gameDrawResult(GameDrawResult result) throws Exception {
		// TODO Auto-generated method stub
		
	}
	private boolean isLowFreq(Long lotteryId){
		return "99108,99109,99110,99401,99701".contains(lotteryId+"");
	}
	@Override
	public List<GameDrawResult> getGameDrawResultTopByLotteryId(Long lotteryId,
			Integer top) throws Exception {
		Date endDate=DateUtils.currentDate();
		Date startDate=isLowFreq(lotteryId)?DateUtils.addDays(endDate, -10):DateUtils.addDays(endDate, -1);
		List<GameDrawResult> drawResultList=this.gameDrawResultDao.getDrawResultByDate(lotteryId, startDate, endDate);
		//倒序排序（按奖期）
		Collections.sort(drawResultList,new Comparator<GameDrawResult>(){
			@Override
			public int compare(GameDrawResult o1, GameDrawResult o2) { 
				return o2.getIssueCode().compareTo(o1.getIssueCode());
			}
		}); 
		List<GameDrawResult> topDrawResultList=new ArrayList<GameDrawResult>();
		if(drawResultList!=null && drawResultList.size()>0){
			int lenth=top<drawResultList.size()?top:drawResultList.size();
			for(int i=0;i<lenth;i++){ 
				topDrawResultList.add(drawResultList.get(i));
			}
		} 
		//获取开奖结果实际开奖时间
		if(topDrawResultList!=null && topDrawResultList.size()>0){
			for(GameDrawResult drawResult:topDrawResultList){ 
				GameIssueEntity issueEntity= this.gameIssueService.getGameIssue(drawResult.getLotteryid(),drawResult.getIssueCode());
				drawResult.setOpenDrawTime(issueEntity.getFactionDrawTime());
			}
		}
		return topDrawResultList;
	}
	
	@Override
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		String result = gameDrawResultDao
				.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (null == result) {
			log.error("获取开奖号码失败，彩种【" + lotteryId + "】，期号【" + issueCode + "】");
			return null;
		}
		log.debug("获取时时彩开奖号码成功。开奖号码" + result);
		return result;
	}
}
