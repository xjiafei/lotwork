package com.winterframework.firefrog.shortlived.sheepactivity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepBigLittleDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepDetailCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepDetailService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName ActivitySheepDetailServiceImpl 
* @Description 羊年活动详情
* @author  hugh
* @date 2015年1月12日 下午3:33:03 
*  
*/
@Service("activitySheepDetailServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivitySheepDetailServiceImpl implements IActivitySheepDetailService {

	@Resource(name = "activitySheepBigLittleDaoImpl")
	private IActivitySheepBigLittleDao activitySheepBigLittleDaoImpl;

	@Resource(name = "activitySheepDetailDaoImpl")
	private IActivitySheepDetailDao activitySheepDetailDaoImpl;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;

	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	public Page<ActivitySheepDetail> queryPage(PageRequest<ActivitySheepDetail> pr) {
		return activitySheepDetailDaoImpl.getAllByPage(pr);
	}

	public void update(ActivitySheepDetail activity) throws Exception {
		activitySheepDetailDaoImpl.update(activity);
	}

	public void updateEntityByType(ActivitySheepDetail activity) throws Exception {
		if (activity.getUpdateType() == 3) {

			//审核通过------------------------------------
			Long[] ids = activity.getIds();
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			for (Long id : ids) {
				ActivitySheepDetail entity = activitySheepDetailDaoImpl.getById(id);
				if (entity.getStatus() != 3) {
					continue;
				}
				entity.setVerifyName(activity.getVerifyName() != null ? activity.getVerifyName() : null);
				entity.setVerifyTime(new Date());
				entity.setStatus(4l);

				TORiskDTO activityDTO = new TORiskDTO();
				activityDTO.setAmount(entity.getAward() + "");
				activityDTO.setType(GameFundTypesUtils.ACTIVITY_DETAIL_TYPE);
				activityDTO.setUserid(entity.getUserId() + "");
				toRiskDTOList.add(activityDTO);

				activitySheepDetailDaoImpl.update(entity);
			}
			fundRiskService.activityFund(toRiskDTOList);
		} else if (activity.getUpdateType() == 4) {

			//审核不通过------------------------------------
			Long[] ids = activity.getIds();
			for (Long id : ids) {
				ActivitySheepDetail entity = activitySheepDetailDaoImpl.getById(id);
				if (entity.getStatus() != 3) {
					continue;
				}
				entity.setVerifyName(activity.getVerifyName());
				entity.setVerifyTime(new Date());
				entity.setVerifyReason(activity.getVerifyReason());
				entity.setStatus(5l);
				activitySheepDetailDaoImpl.update(entity);
			}
		}
	}

	public Long getUncheckNum(Long activity) {
		return activitySheepDetailDaoImpl.getUncheckNum(activity);
	}

	@Override
	public List<ActivitySheepDetail> getUserDiceDetailList(Long userId) {
		return activitySheepDetailDaoImpl.getUserDiceDetailList(userId);
	}

	@Override
	public List<ActivitySheepDetail> getAllUserRotaryDetailList() {
		return activitySheepDetailDaoImpl.getAllUserRotaryDetailList();
	}

	@Override
	public List<ActivitySheepDetail> getUserRotaryDetailList(Long userId) {
		return activitySheepDetailDaoImpl.getUserRotaryDetailList(userId);
	}

	public List<ActivitySheepDetailCount> getCounts(String date, Long activityId) throws Exception {

		List<ActivitySheepDetailCount> list = new ArrayList<ActivitySheepDetailCount>();

		if (date != null && activityId == 4) {
			list.add(getCounts(activityId, date, false, false).get(0));
			list.addAll(getCounts(activityId, date, true, false));
			if (list.size() == 0 || list.get(0) == null) {
				return list;
			}
			for (ActivitySheepDetailCount activitySheepDetailCount : list) {

				activitySheepDetailCount.setCountDate(DateUtils.parse(date));
			}
			return list;
		} else if (date != null && activityId == 5) {
			list.add(getCounts(activityId, date, false, false).get(0));
			list.addAll(getCounts(activityId, date, false, true));
			if (list.size() == 0 || list.get(0) == null) {
				return list;
			}
			for (ActivitySheepDetailCount activitySheepDetailCount : list) {
				activitySheepDetailCount.setWinNum(activitySheepDetailCount.getUseNum());
				activitySheepDetailCount.setCountDate(DateUtils.parse(date));
			}
			return list;
		}

		Date begin = DateUtils.parse("2015-01-31");
		Date end = DateUtils.parse("2015-03-20");
		Date now = DateUtils.getStartTimeOfCurrentDate();
		Date notChangeNow = DateUtils.getStartTimeOfCurrentDate();
		if (now.after(end)) {
			now = end;
		}
		while (now.after(begin)) {
			String dateOne = DateUtils.format(now);
			String key = "game.sheep.detai.count" + activityId + dateOne;
			ActivitySheepDetailCount count = null;
			try {
				redisClient.del(key);
			} catch (Exception e) {
				// TODO: handle exception
			}

			//			if(redisClient.exists(key)){
			//				String value=  redisClient.get(key);
			//				count = jmapper.fromJson(value, ActivitySheepDetailCount.class);
			//			}else{
			List<ActivitySheepDetailCount> counts = getCounts(activityId, dateOne, false, false);
			if (counts == null) {
				count = new ActivitySheepDetailCount();
			} else {
				count = counts.get(0);
			}
			if (count == null)
				count = new ActivitySheepDetailCount();
			count.setCountDate(now);
			//				if(now.before( DateUtils.addDays(notChangeNow, -1))){					
			//					String value = jmapper.toJson(count);
			//					redisClient.set(key, value );
			//				}				
			//			}	
			count.setCountDate(now);
			list.add(count);
			now = DateUtils.addDays(now, -1);
		}

		if (activityId == 4) {
			//			for (ActivitySheepDetailCount activitySheepDetailCount : list) {				
			//				activitySheepDetailCount.setCountDate(DateUtils.parse(date));
			//			}			
		} else if (activityId == 5) {
			for (ActivitySheepDetailCount activitySheepDetailCount : list) {
				activitySheepDetailCount.setWinNum(activitySheepDetailCount.getUseNum());
				//				activitySheepDetailCount.setCountDate(DateUtils.parse(date));
			}
		}
		return list;
	}

	public List<ActivitySheepDetailCount> getCounts(Long activityId, String dateOne, boolean isGroupByChannel,
			boolean isGroupByLevel) {

		Date beginDateOne = DateUtils.parse(dateOne + " 00:00:00", DateUtils.DATETIME_FORMAT_PATTERN);
		Date endDateOne = DateUtils.parse(dateOne + " 23:59:59", DateUtils.DATETIME_FORMAT_PATTERN);
		return activitySheepDetailDaoImpl.getCounts(activityId, beginDateOne, endDateOne, isGroupByChannel,
				isGroupByLevel);
	}

	public List<ActivitySheepHongBaoDetail> getCount(Long hongBaoId) throws Exception {
		List<ActivitySheepHongBaoDetail> list = new ArrayList<ActivitySheepHongBaoDetail>();
		Date begin = DateUtils.parse("2015-01-31");
		Date end = DateUtils.parse("2015-03-20");
		Date now = DateUtils.getStartTimeOfCurrentDate();
		if (now.after(end)) {
			now = end;
		}
		while (now.after(begin)) {
			String dateOne = DateUtils.format(now);
			ActivitySheepHongBaoDetail count = getCounts(hongBaoId, dateOne);
			if (count == null) {
				count = new ActivitySheepHongBaoDetail();
				count.setAward3(0L);
				count.setAward4(0L);
			}

			count.setDate(dateOne);
			list.add(count);
			now = DateUtils.addDays(now, -1);
		}
		return list;
	}

	public ActivitySheepHongBaoDetail getCounts(Long hongBaoId, String dateOne) {
		Date beginDateOne = DateUtils.parse(dateOne + " 00:00:00", DateUtils.DATETIME_FORMAT_PATTERN);
		Date endDateOne = DateUtils.parse(dateOne + " 23:59:59", DateUtils.DATETIME_FORMAT_PATTERN);
		return activitySheepDetailDaoImpl.getHongBaoCounts(hongBaoId, beginDateOne, endDateOne);
	}
}
