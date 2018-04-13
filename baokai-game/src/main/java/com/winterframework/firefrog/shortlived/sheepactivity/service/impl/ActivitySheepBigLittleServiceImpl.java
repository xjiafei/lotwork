package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IActivityAwardConfigDao;
import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepBigLittleDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepBigLittleService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.BigLittleAward;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


/** 
* @ClassName ActivitySheepBigLittleServiceImpl 
* @Description 羊年活动猜大小
* @author  hugh
* @date 2015年1月12日 下午3:33:03 
*  
*/
@Service("activitySheepBigLittleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySheepBigLittleServiceImpl implements IActivitySheepBigLittleService{
	
	private Logger log = LoggerFactory.getLogger(ActivitySheepBigLittleServiceImpl.class);
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 
	
	@Resource(name = "activitySheepBigLittleDaoImpl")
	private IActivitySheepBigLittleDao activitySheepBigLittleDaoImpl; 
	
	@Resource(name = "activitySheepDetailDaoImpl")
	private IActivitySheepDetailDao activitySheepDetailDaoImpl; 
	
	@Resource(name = "activityAwardConfigDaoImpl")
	private IActivityAwardConfigDao activityAwardConfigImpl; 
	
	@Resource(name="userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;
	
	public Page<ActivitySheepBigLittle> queryPage(
			PageRequest<ActivitySheepBigLittle> pr) {
		return activitySheepBigLittleDaoImpl.getAllByPage(pr);
	}
		
	public void update(ActivitySheepBigLittle activity) throws Exception{
		activitySheepBigLittleDaoImpl.update(activity);
	}
	
	public void updateEntityByType(ActivitySheepBigLittle activity) throws Exception{
			
		ActivitySheepBigLittle big = activitySheepBigLittleDaoImpl.getById(activity.getId());
		if ( activity.getUpdateType() == 1){
			//编辑
			big.setUpdateStatus(1L);
			big.setUpdateName(activity.getUpdateName());
			big.setUpdateReason(activity.getUpdateReason());
			big.setUpdateLastNum(activity.getUpdateLastNum());
			
			ActivitySheepDetail detail = new ActivitySheepDetail();
			detail.setActivityId(4L);
			detail.setActivityTime(new Date());
			detail.setActivityType(1L);//管理员添加
			detail.setAward(0L);//管理员添加
			detail.setChannel(4L);
			if(activity.getUpdateLastNum()==null){
				activity.setUpdateLastNum(0L);
			}
	
			if(activity.getUpdateLastNum() > 0){
				detail.setGetNum(activity.getUpdateLastNum());
				
			}else{
				detail.setUseNum(0-activity.getUpdateLastNum());
			}
			detail.setResult("");//管理员添加
			detail.setRecharge(0L);
			detail.setStatus(0L);
			detail.setUserName(big.getUserName());
			detail.setUserId(big.getUserId());
			activitySheepDetailDaoImpl.insert(detail);
			
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(4L);
			log.setActivityName("羊年大小通吃");
			log.setNum(activity.getUpdateLastNum());
			if(activity.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+activity.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(0-activity.getUpdateLastNum())+"次");
			}
			log.setUserName(big.getUserName());
			log.setOperateName(big.getUpdateName());
			log.setOperateType(1L);
			activitySheepOperateLogDaoImpl.insert(log );
			
		}else if( activity.getUpdateType() == 2){
			//发布
			big.setUpdateStatus(0L);
			
			big.setUpdateReason("");
			big.setLastNum(big.getLastNum() + big.getUpdateLastNum());
			
			
			List<ActivitySheepDetail> detail = activitySheepDetailDaoImpl.getNotPublishByUserId(big.getUserId(),4L);
			for (ActivitySheepDetail activitySheepDetail : detail) {
				activitySheepDetail.setStatus(1L);
				activitySheepDetailDaoImpl.update(activitySheepDetail);
			}
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(4L);
			log.setActivityName("羊年大小通吃");
			log.setNum(activity.getUpdateLastNum());
		
			if(big.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+big.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(0-big.getUpdateLastNum())+"次");
			}
			big.setUpdateLastNum(0L);
			log.setUserName(big.getUserName());
			log.setOperateName(big.getUpdateName());
			big.setUpdateName("");
			log.setOperateType(2L);
			activitySheepOperateLogDaoImpl.insert(log );
		}else if( activity.getUpdateType() == 3){
			//拒绝发布
			big.setUpdateStatus(0L);
			
			big.setUpdateReason("");
			//big.setLastNum(big.getLastNum() + big.getUpdateLastNum());
			
			
			List<ActivitySheepDetail> detail = activitySheepDetailDaoImpl.getNotPublishByUserId(big.getUserId(),4L);
			for (ActivitySheepDetail activitySheepDetail : detail) {
//				activitySheepDetail.setStatus(2L);
//				activitySheepDetailDaoImpl.update(activitySheepDetail);
				
				activitySheepDetailDaoImpl.delete(activitySheepDetail.getActivityConfigId());
			}
			
			
			ActivitySheepOperateLog log = new ActivitySheepOperateLog();
			log.setGmtCreated(new Date());
			log.setActivityId(4L);
			log.setActivityName("羊年大小通吃");
			log.setNum(activity.getUpdateLastNum());
			if(big.getUpdateLastNum() > 0){
				log.setOperateContent("添加"+big.getUpdateLastNum()+"次");
			}else{
				log.setOperateContent("减少"+(1-big.getUpdateLastNum())+"次");
			}
			big.setUpdateLastNum(0L);
			log.setUserName(big.getUserName());
			log.setOperateName(big.getUpdateName());
			big.setUpdateName("");
			log.setOperateType(3L);
			activitySheepOperateLogDaoImpl.insert(log );
		}
		activitySheepBigLittleDaoImpl.update(big);
	}

	@Override
	public ActivitySheepBigLittle getUserDice(Long userId) {
		return activitySheepBigLittleDaoImpl.getUserDice(userId);
	}

	@Override
	public void initUserDice(Long userId, String userName,Long channel) {
		ActivitySheepBigLittle asbl=new ActivitySheepBigLittle();
		asbl.setUserName(userName);
		asbl.setUserId(userId);
		asbl.setLastNum(0L);
		asbl.setNextWinNumNow(0l);
		asbl.setMaxNextWinNum(0l);
		asbl.setAllAward(0l);
		asbl.setStatus(0l);
		asbl.setChannel(channel);
		activitySheepBigLittleDaoImpl.insert(asbl);
	}
	
   @Transactional( rollbackFor = Exception.class,isolation=Isolation.SERIALIZABLE)
	public BigLittleAward getAward(Long userId,boolean isGuessLittle,Long channel) throws Exception{
		BigLittleAward award = new BigLittleAward();
		//获取配置信息
		ActivityAwardConfig activity = new ActivityAwardConfig();
		activity.setActivityId(4L);
		List<ActivityAwardConfig> configs = activityAwardConfigImpl.getAllByEntity(activity);
		ActivityAwardConfig win = null;
		ActivityAwardConfig fail = null;
		ActivityAwardConfig  continuousWin = null;		
		for (ActivityAwardConfig activityAwardConfig : configs) {
			if(activityAwardConfig.getAwardName().equals("押中奖")){
				win = activityAwardConfig;
			}else if(activityAwardConfig.getAwardName().equals("押错奖")){
				fail = activityAwardConfig;
			}else{
				continuousWin = activityAwardConfig;
			}
		}
		//获取用户信息
		ActivitySheepBigLittle big = new ActivitySheepBigLittle();
		big.setUserId(userId);
		List<ActivitySheepBigLittle> bigs = activitySheepBigLittleDaoImpl.getAllByEntity(big);
		ActivitySheepBigLittle user = bigs.get(0);
		if(user.getLastNum()<=0){
			throw new Exception("user.getLastNum()<=0");
		}
		
		Long userContinuousWinNum = user.getNextWinNumNow();
		Long userMaxContinuousWinNum = user.getMaxNextWinNum();
		Long maxContinuousWinNum = continuousWin.getRatio()/100;		
		//开始抽奖  
//		//能赢的号码个数
//		int toWinNum = (int) (maxContinuousWinNum - userContinuousWinNum > 0 ? maxContinuousWinNum - userContinuousWinNum : 0);
//		//输的号码个数
//		int toFailNum = (int) (maxContinuousWinNum - toWinNum);				
		long toWinNum = (long) ((win.getLastNumber() * win.getRatio())*0.01);//剩余数 * 比例
		long toFailNum = (long) ((fail.getLastNumber() * fail.getRatio())*0.01);//剩余数 * 比例		
		
		
		if(userContinuousWinNum >= maxContinuousWinNum  || win.getLastNumber()<=0){
			toWinNum = 0;
			toFailNum = 8L;
		}
		
		if( fail.getLastNumber()<=0){
			toWinNum = 8;
			toFailNum = 0L;
		}
		
		if(fail.getLastNumber()<=0 && (win.getLastNumber()<=0 || userContinuousWinNum >= maxContinuousWinNum)){
			return new BigLittleAward(false);
		}
		
		int[] littleNum = {1,2,3};
		int[] bigNum = {4,5,6};
		int[] toWinNums = null;
		int[] toFailNums = null;
		if(isGuessLittle){
			toWinNums = littleNum;
			toFailNums = bigNum;
		}else{
			toWinNums =bigNum ;
			toFailNums = littleNum;
		}
		
		List<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < toWinNum; i++) {
			nums.add(toWinNums[new Random().nextInt(3)]);
		}			
		for (int i = 0; i < toFailNum; i++) {
			nums.add(toFailNums[new Random().nextInt(3)]);
		}

		//开奖结果
		Integer[] result = new Integer[3];		
		result[0] = nums.get(new Random().nextInt(nums.size()));
		result[1] = nums.get(new Random().nextInt(nums.size()));
		result[2] = nums.get(new Random().nextInt(nums.size()));		
		int resultHe = result[0] + result[1] + result[2] ;
		String drawResult =  result[0] +","+ result[1] +"," + result[2] ;
		//开奖结果  大小
		boolean isLittleRsult = false;	
		String drawType="大";
		if(resultHe <= 10 ){
			isLittleRsult = true;
			
		}
		
		if(isGuessLittle){
			drawType="小";
		}
		//奖金
		Long winMoney = 0L;
		//开奖结果  输赢
		boolean isWin = false;	
		String res = "";
		Long configId = null;
		if((isLittleRsult && isGuessLittle)  || (!isLittleRsult && !isGuessLittle)){
			//如果赢
			isWin = true;
			winMoney = win.getAward();	
			
			configId= win.getId();
			if(userContinuousWinNum > 0){		
				configId= continuousWin.getId();				
				
			}	
			userContinuousWinNum ++;
			if(userContinuousWinNum > userMaxContinuousWinNum){
				userMaxContinuousWinNum = userContinuousWinNum;
			}
			if(userContinuousWinNum ==8){
				res = "连中8次";
				
			}else{				
				res = "猜中";
			}
			
			if(userContinuousWinNum%8 == 0){
				winMoney += continuousWin.getAward();
				continuousWin.setLastNumber(continuousWin.getLastNumber() -1 );
				continuousWin.setWinNumber(continuousWin.getWinNumber() + 1);			
			}
			
//			if(userContinuousWinNum >=maxContinuousWinNum-1){
//				winMoney += continuousWin.getAward();
//			}
			
			
			win.setLastNumber(win.getLastNumber() -1 );
			win.setWinNumber(win.getWinNumber() + 1);
		}else{
			//如果输
			isWin = false;
			configId= win.getId();
			userContinuousWinNum = 0L;	
			winMoney= fail.getAward();				
			res = "猜错";
			
			fail.setLastNumber(fail.getLastNumber() -1 );
			fail.setWinNumber(fail.getWinNumber() + 1);
		}
		user.setLastNum(user.getLastNum() - 1);
		user.setMaxNextWinNum(userMaxContinuousWinNum);
		user.setNextWinNumNow(userContinuousWinNum);
				
		ActivitySheepDetail detail = new ActivitySheepDetail();		
		detail.setActivityId(4L);
		detail.setActivityTime(new Date());
		detail.setActivityType(0L);
		detail.setChannel(channel);
		detail.setUseNum(1L);
		detail.setStatus(3L);
		detail.setResult(res);
		detail.setAward(winMoney);//管理员添加
		detail.setUserName(user.getUserName());
		detail.setDrawResult(drawResult);
		detail.setDrawType(drawType);
		detail.setActivityConfigId(configId);
		detail.setUserId(userId);
		activitySheepDetailDaoImpl.insert(detail);
		activitySheepBigLittleDaoImpl.reduceTime(user);
		activityAwardConfigImpl.update(win);
		activityAwardConfigImpl.update(fail);
		activityAwardConfigImpl.update(continuousWin);
		award.setWin(isWin);
		award.setLittleRsult(isLittleRsult);
		award.setResultNum(result);
		award.setAward(winMoney);
		award.setContinuousWinNum(userContinuousWinNum);
		award.setLastGuessNum(user.getLastNum());			
		
		return award;
	}

	@Override
	public void initUserDice(Long userId, Long times,Long amount,Long channel) {
		ActivitySheepBigLittle asbl=new ActivitySheepBigLittle();
		asbl.setUserName(userCustomerDao.getUserNameById(userId));
		asbl.setUserId(userId);
		asbl.setLastNum(times);
		asbl.setNextWinNumNow(0l);
		asbl.setMaxNextWinNum(0l);
		asbl.setAllAward(amount);
		asbl.setStatus(0l);
		asbl.setChannel(channel);
		activitySheepBigLittleDaoImpl.insert(asbl);
		
	}

	@Override
	public void addUserDiceLastNum(Long userId, Long times,Long amount) {
		activitySheepBigLittleDaoImpl.addUserDiceLastNum(userId,times,amount);
	}
}
