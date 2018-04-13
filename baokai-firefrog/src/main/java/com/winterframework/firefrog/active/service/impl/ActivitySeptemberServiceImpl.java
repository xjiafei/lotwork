package com.winterframework.firefrog.active.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IActivityResultDao;
import com.winterframework.firefrog.active.dao.IActivitySignUpDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.dao.vo.ActivityResult;
import com.winterframework.firefrog.active.entity.ActivitySeptEntity;
import com.winterframework.firefrog.active.enums.ActivityEnum;
import com.winterframework.firefrog.active.service.IActivitySeptemberService;
import com.winterframework.firefrog.active.service.IAuguestActivityService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IVipActivityDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Service("activitySeptemberServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySeptemberServiceImpl implements IActivitySeptemberService {

		private static final Logger log = LoggerFactory.getLogger(ActivitySeptemberServiceImpl.class);
		
		private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
		private final String activityCode_step2="160902";
		private final Long  max_betAmount= 17050000000l;
		//活動主檔                                                                                    
		@Resource(name = "activityDaoImpl")
		IActivityDao    activityDao;
		//活動LOG紀錄
		@Resource(name = "activityLogDaoImpl")
		private IActivityLogDao activityLogDaoImpl;
		//活動(中獎)結果
		@Resource(name = "activityResultDaoImpl")
		IActivityResultDao    activityResultDao;
		//活動報名表
		@Resource(name = "activitySignUpDaoImpl")
		IActivitySignUpDao    activitySignUpDao;
		//活動規則
		@Resource(name = "activityConfigDaoImpl")
		IActivityConfigDao    activityConfigDao;
		//活動規則
		@Resource(name = "auguestActivityImpl")
		IAuguestActivityService    auguestActivityImpl;
		
		//計算投注金額
		@PropertyConfig("game_get_queryUserPeriodBets")
		private String queryUserPeriodBets;
		
		@Resource(name = "HttpJsonClientImpl")
		protected IHttpJsonClient httpJsonClient;
		
		//資金系統
		@Resource(name = "fundChangeServiceImpl")
		private IFundAtomicOperationService fundChangeService;
		
		@Resource(name="userCustomerDaoImpl")
		private IUserCustomerDao userCustomerDaoImpl;

		@Resource(name="vipactivtyDao")
		private IVipActivityDao vipactivtyDao;

		@PropertyConfig(value="encrypter.Key")
		protected String encrypterKey;
		@PropertyConfig(value="encrypter.value")
		protected String encrypterValue;

		private Long month=9l;
		
		/** 
		* @Title: queryUserBets 
		* @Description: 查詢使用者投注金額 
		* @param  ActivitySeptEntity
		*/
		@Override
		public Long queryUserBets(ActivitySeptEntity entity) throws Exception {
			
			Map<String, Object> amountMap = new HashMap<String, Object>();		
			amountMap.put("userId", entity.getUserId());	
			amountMap.put("startTime", auguestActivityImpl.getWantedTime(0, 0, 0, 0, 0).getTime());
				
			amountMap.put("endTime", auguestActivityImpl.getWantedTime(0, 23, 59, 59, 0).getTime());
			log.debug("getAgentAmount : userId=" + entity.getUserId() + "  startTime=" + amountMap.get("startTime") 
					+ " endTime" + amountMap.get("endTime") );
			Long betAmount = (Long) httpJsonClient.invokeHttp(queryUserPeriodBets,
					amountMap, new TypeReference<Long>(){});
			log.debug("getAgentAmount : userId=" + entity.getUserId() + "  startTime=" + amountMap.get("startTime") 
					+ " endTime" + amountMap.get("endTime")  + " betAmount =" + betAmount);
			return betAmount;
			
		}

		/** 
		* @Title: queryLevel 
		* @Description: 查詢使用者可點燈數
		* @param  ActivitySeptEntity
		*/
		
		@Override
		public ActivitySeptEntity queryLevel(Long userId) throws Exception {		
			ActivityConfig activityConfigVO = new ActivityConfig();
			ActivitySeptEntity activitySeptEntity=new ActivitySeptEntity();
			//取得活動資訊			
			try{							
				Long id = queryActivityId(activityCode_step2);
				activityConfigVO.setActivityId(id);
				List<ActivityConfig> activityConfig=activityConfigDao.getActCfgByCondition(activityConfigVO);
				activitySeptEntity.setUserId(userId);
				final Long betAmount=queryUserBets(activitySeptEntity);
				//final Long betAmount=3050000000l;
				
				Collection<ActivityConfig> filterList = Collections2.filter(activityConfig  					  
					     , new Predicate<ActivityConfig>(){  
					  
					                  @Override  
					  
					                  public boolean apply(ActivityConfig input) {  
					                	    if(betAmount >= max_betAmount && 
					                	    		ActivityEnum.StepActivity2CodeTransfer.OneLv.getCode().equals(input.getType())){
					                	    	 return true;
					                	    }else{
					                	    	 if(betAmount >=input.getMinPrize() && betAmount<input.getMaxPrize() && 
							                        		ActivityEnum.StepActivity2CodeTransfer.TopLv.getCode()!=input.getType()){
					                	    		 return true; 
					                	    	 }else{
					                	    		 return false; 
					                	    	 }
					                	    		 
					                	    }
					                    	  
					                  } 			  
					});  			
				if(!filterList.isEmpty()){
					//取得可點燈LEVEL 以及派獎金額
					for(ActivityConfig o:filterList){
						activitySeptEntity.setLevel(ActivityEnum.getLvByCode(o.getType()));
					}
					//取得下一盞燈還需投注多少錢
					if(betAmount < max_betAmount){
						Long leftMoney=getLeftMoney(betAmount,activitySeptEntity.getLevel(),id);
						//resultMap.put("leftMoney", leftMoney/10000);	
						activitySeptEntity.setLeftMoney(leftMoney/10000);
					}else{
						activitySeptEntity.setLeftMoney(0l);
					}
					//是否已經領獎
					ActivityResult activityResultVO= new ActivityResult();
					activityResultVO.setUserId(userId);
					activityResultVO.setActivityId(id);
					ActivitySeptEntity isFinished= isGetAward(activityResultVO,activitySeptEntity.getLevel());
					activitySeptEntity.setIsFinished(isFinished.getIsFinished());
					activitySeptEntity.setNowLevel(isFinished.getNowLevel());

					//領取琉璃燈 而且尚未領取
					if(activitySeptEntity.getLevel().equals(1) && 
							!ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv().equals(activitySeptEntity.getNowLevel())){
						//琉璃燈TYPE
						ActivityResult resultVO= new ActivityResult();
						resultVO.setType(ActivityEnum.StepActivity2CodeTransfer.TopLv.getCode());
						resultVO.setActivityId(id);
						List<ActivityResult> activityResult=getListAwardRecord(resultVO);
						if(!activityResult.isEmpty()){
							if(activityResult.size()>=3){
								activitySeptEntity.setIsTop(0l);
							}else{
								activitySeptEntity.setIsTop(1l);
								activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv());
							}
						}else{
							activitySeptEntity.setIsTop(1l);
							activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv());
						}			
					}else if(ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv().equals(activitySeptEntity.getNowLevel())){
						activitySeptEntity.setIsTop(0l);
						activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv());
					}
					else{
						activitySeptEntity.setIsTop(0l);
					}
					if(isFinished.getNowLevel().equals(ActivityEnum.StepActivity2CodeTransfer.OneLv.getLv())){
						activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.TopLv.getLv());
					}	
				}else{
					//查無活動參數
					activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv());
					Long leftMoney=getLeftMoney(betAmount,ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv(),id);
					activitySeptEntity.setLeftMoney(leftMoney/10000);
					activitySeptEntity.setIsFinished(1l);	
					activitySeptEntity.setNowLevel(ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv());
					activitySeptEntity.setIsTop(0l);
					return activitySeptEntity;
				}
			}catch(Exception e){
				log.error("queryLevel error!",e);
				activitySeptEntity.setLevel(ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv());
				activitySeptEntity.setLeftMoney(0l);
				activitySeptEntity.setIsFinished(1l);	
				activitySeptEntity.setNowLevel(ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv());
				activitySeptEntity.setIsTop(0l);
				return activitySeptEntity;
			}		
			return activitySeptEntity;
		}

		@Override
		public Long queryActivityId(String activityCode) throws Exception {
			Activity actVO = new Activity();
			Activity actResultVO = new Activity();
			actVO.setActivityCode(activityCode);
			log.debug("getAcvitityByCode : ActivityCode=" + actVO.getActivityCode());
			actResultVO = activityDao.getActivityByCode(actVO);
			return actResultVO.getId();
		}
		
		@Override
		public Activity getActivityByCode(String activityCode) throws Exception {
			Activity actVO = new Activity();
			actVO.setActivityCode(activityCode);
			return activityDao.getActivityByCode(actVO);
		}
		
		@Override
		public Boolean activityDraw(Long userid, Integer drawLv) {
			
			UserCustomer user = userCustomerDaoImpl.getById(userid);			
			Activity actVO = new Activity();
			actVO.setActivityCode(activityCode_step2);
			Activity activity = activityDao.getActivityByCode(actVO);
			
			
			if(!DateUtils.between(activity.getStartTime(), activity.getEndTime())){
				//限制於活動期間內進入
				return false;
			}
			
			VipActivityVo activityVo = new VipActivityVo();
			activityVo.setAccount(user.getAccount());
			activityVo.setMonth(month);
			activityVo.setStartTime(activity.getStartTime());
			activityVo.setEndTime(activity.getEndTime());
			List<VipActivityVo> vos = vipactivtyDao.queryByActivityVo(activityVo);
			//判斷用戶是否有報名
			if(vos==null || vos.isEmpty()){
				log.error(" no register september step2 activity2 userid="+userid);
				return false;
			}
			
			ActivitySeptEntity activitySeptEntity=new ActivitySeptEntity();
			activitySeptEntity.setUserId(userid);
			try {
				final Long betAmount=queryUserBets(activitySeptEntity);
				
				log.info(" betAmount = "+betAmount);
				
				String type= activityConfigDao.getMaxDrawType(activity.getId(),betAmount);
				Integer maxLv = ActivityEnum.getLvByCode(type);
				//判斷用戶點擊領獎是否在限制內
				
				log.info(" drawLv={},maxLv={}",new Object[]{drawLv,maxLv});
				
				if(maxLv==-1 || betAmount==0){
					//用戶投注皆未達標
					throw new Exception("no draw can get");
				}else if((drawLv>7 || drawLv<0 || drawLv<maxLv) && drawLv>0){
					//錯誤的領獎訊息
					throw new Exception("error septmeber activity2 draw lv");
				}else if(drawLv==0 && maxLv!=1){
					//錯誤的領獎訊息
					throw new Exception("error septmeber activity2 draw lv");
				}else if(drawLv==0){
					//判斷是否有琉璃燈資格
					ActivityResult actRsVO = new ActivityResult();
					actRsVO.setActivityId(activity.getId());
					actRsVO.setType(ActivityEnum.getCodeByLv(drawLv));
					List<ActivityResult> resutls = activityResultDao.getListAwardRecord(actRsVO);
					if(resutls!=null && resutls.size()>2){
						//以經點滿三盞燈琉璃燈
						return false;
					}
					
					actRsVO = new ActivityResult();
					actRsVO.setActivityId(activity.getId());
					actRsVO.setUserId(userid);;
					resutls = activityResultDao.getListAwardRecord(actRsVO);
					if(resutls==null || resutls.size()<7){
						//尚未點滿七盞燈
						return false;
					}
				}
			} catch (Exception e1) {
				log.error(e1.getMessage());
				return false;
			}
			
			String drawType= ActivityEnum.getCodeByLv(drawLv);
			ActivityResult result = activityResultDao.queryActivityResultByUserIdAndType(userid,drawType);
			
			if(result!=null){
				return false;
			}else{
				try{
					ActivityConfig config = new ActivityConfig();
					config.setActivityId(activity.getId());
					config.setType(drawType); 
					config = activityConfigDao.getActCfg(config);
					Map<String,Object> ruleMap = DataConverterUtil.convertJson2Map(config.getRule());
					UserCustomer userCustomer = userCustomerDaoImpl.getById(userid);
					//今日投注金額
					Long awardAmount = 0l;
					if(userCustomer.getVipLvl()>0){
						awardAmount = new Long(ruleMap.get("vip").toString());
					}else{
						awardAmount = new Long(ruleMap.get("normal").toString());
					}
					ActivityResult actRsVO = new ActivityResult();
					actRsVO.setActivityId(activity.getId());
					actRsVO.setUserId(userid);
					actRsVO.setStatus(1L);
					actRsVO.setType(drawType);
					actRsVO.setCreateUser(userid);
					actRsVO.setCreateTime(new Date());
					actRsVO.setModifyUser(userid);
					actRsVO.setModifyTime(actRsVO.getCreateTime());
					actRsVO.setResult(" ");					
					activityResultDao.insert(actRsVO);
					
					ActivityLog activityLog = new ActivityLog();
					
					activityLog.setActivityId(activity.getId());
					activityLog.setUserId(userid);
					activityLog.setPrize(awardAmount);
					activityLog.setCreateTime(actRsVO.getCreateTime());
					activityLog.setAwardTime(actRsVO.getCreateTime());
					activityLog.setStatus(1l);
					activityLog.setResultId(actRsVO.getId());
					activityLogDaoImpl.insert(activityLog);
					saveFundChaneLog(userid,awardAmount);
				}catch(Exception e){
					log.error("activityDraw error!",e);
					return false;
				}
				return true;
			}

		}
		
		private void saveFundChaneLog(Long userId, Long amount){
			FundGameVo vo = new FundGameVo();
			vo.setUserId(userId);
			vo.setAmount(amount);
			vo.setIsAclUser(0L);
			vo.setOperator(0L);
			vo.setReason(ACTIVITY_REASON_KEY);
			vo.setNote("九月活动第二波");
			List<FundGameVo> vos = Lists.newArrayList();
			vos.add(vo);
			List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
			fundChangeService.action(vos, maps);
		}
		
		private Long getLeftMoney(Long betAmount,Integer level,Long activityId){
			ActivityConfig activityConfigVO = new ActivityConfig();
			activityConfigVO.setType(ActivityEnum.getCodeByLv(level-1));
			activityConfigVO.setActivityId(activityId);
			ActivityConfig leftMoneyConfig=activityConfigDao.getActCfg(activityConfigVO);
			Long leftMoney=new BigDecimal(leftMoneyConfig.getMinPrize()).subtract(new BigDecimal(betAmount)).longValue();
			return  leftMoney;
		}
		
		@Override
		public List<ActivityResult> getListAwardRecord(ActivityResult activityResultVO){

			return activityResultDao.getListAwardRecord(activityResultVO);
		}
		
		private ActivitySeptEntity isGetAward(ActivityResult activityResultVO, Integer level){
			List<ActivityResult> activityResult = getListAwardRecord(activityResultVO);
			ActivitySeptEntity activitySeptEntity=new ActivitySeptEntity();
			
			if(!activityResult.isEmpty()){
				// 依type排序
		        Collections.sort(activityResult,
		        new Comparator<ActivityResult>() {
		            public int compare(ActivityResult o1, ActivityResult o2) {
		                return o1.getType().compareTo(o2.getType());
		            }
		        });
				//取得最高派獎資訊，若使用者點燈在派獎資訊裡面已經有資料
				if(ActivityEnum.getCodeByLv(level).equals(activityResult.get(0).getType())){
						activitySeptEntity.setIsFinished(1l);
				}else{
					activitySeptEntity.setIsFinished(0l);					
				}
				activitySeptEntity.setNowLevel(ActivityEnum.getLvByCode(activityResult.get(0).getType()));
			}else{
				activitySeptEntity.setIsFinished(0l);
				activitySeptEntity.setNowLevel(ActivityEnum.StepActivity2CodeTransfer.OrignLv.getLv());
			}
			 
			return activitySeptEntity;
		}

		@Override
		public List<ActivityConfig> queryActivityConfig(Long  activityId) {
			ActivityConfig activityConfigVO = new ActivityConfig();
			activityConfigVO.setActivityId(activityId);
			List<ActivityConfig> activityConfig=activityConfigDao.getActCfgByCondition(activityConfigVO);
			return activityConfig;
		}
		
		@Override
		public String queryMinTypeByUserIdAndActivityIdAndLessThanResult(Long userId, Long activityid, String day){
			String minType = activityResultDao.queryMinTypeByUserIdAndActivityIdAndLessThanResult(userId, activityid, day);
			return minType;
		}
		
		@Override
		public ActivityResult queryActivityResultByUserIdAndActivityIdAndResult(Long userId, Long activityid, String day){
			return activityResultDao.queryActivityResultByUserIdAndActivityIdAndResult(userId, activityid, day);
		}
						
		@Override
		public boolean isGameTime() throws Exception {
			Activity actVO = new Activity();
			actVO.setActivityCode(activityCode_step2);
			Activity activity = activityDao.getActivityByCode(actVO);
				
			if(!DateUtils.between(activity.getStartTime(), activity.getEndTime())){
				//限制於活動期間內進入
				return false;
			}else{
				return true;
			}
			
		}
}
