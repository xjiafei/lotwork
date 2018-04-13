/**   
* @Title: NoticeTaskServiceImpl.java 
* @Package com.winterframework.firefrog.notice.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午11:44:38 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.IMailSender;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.ImConstants;
import com.winterframework.firefrog.notice.dao.INoticeTaskDao;
import com.winterframework.firefrog.notice.entity.MQMsg;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.web.dto.DTOConverter;
import com.winterframework.firefrog.notice.web.dto.NoticeStruc;
import com.winterframework.firefrog.notice.web.dto.NoticeTaskStruc;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.service.IMessage2Service;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: NoticeTaskServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午11:44:38 
*  
*/
@Service("noticeTaskServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class NoticeTaskServiceImpl implements INoticeTaskService {

	private static Logger logger = LoggerFactory.getLogger(NoticeTaskServiceImpl.class);

	@Resource(name = "noticeTaskDaoImpl")
	private INoticeTaskDao noticeTaskDao;

	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userProfileService;

	@Resource(name = "templateMailSender")
	private IMailSender mailSender;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@Resource(name= "message2ServiceImpl")
	private IMessage2Service messageService;

	@PropertyConfig(value = "notice.Redis.Key")
	private String noticeRedisKey;

	@PropertyConfig(value = "notice.note.Redis.disableTime")
	private String disableTime;

	/**
	* Title: update
	* Description:
	* @param task
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#update(com.winterframework.firefrog.notice.entity.NoticeTask) 
	*/
	@Override
	public void update(NoticeTask task) throws Exception {
		noticeTaskDao.update(task);
	}

	/**
	* Title: queryAllTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	*/
	@Override
	public List<NoticeTask> queryAllTask() throws Exception {
		return noticeTaskDao.queryAllTask();
	}

	/**
	* Title: getNoticeTaskById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#getNoticeTaskById(java.lang.Long) 
	*/
	@Override
	public NoticeTask getNoticeTaskById(Long id) throws Exception {
		return noticeTaskDao.getNoticeTaskById(id);
	}

	/**
	* Title: getSendNoticeTask
	* Description:
	* @param id
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#getSendNoticeTask(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public NoticeTask getSendNoticeTask(Long id, Long userId) throws Exception {
		return noticeTaskDao.getSendNoticeTask(id, userId);
	}
	
	/**
	* Title: getSendUserNoticeTask
	* Description:
	* @param id
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#getSendUserNoticeTask(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public UserNoticeTask getSendUserNoticeTask(Long taskid, Long userId) throws Exception {
		return noticeTaskDao.getSendUserNoticeTask(taskid, userId);
	}
	

	public void sendMail(NoticeTask notice, MQMsg taskPram, String email) throws Exception {
		logger.debug("email:"+email);
		if (notice.getEmailActivated() && notice.getEmailUsed() && StringUtils.isNotBlank(email)) {
			if (notice.getEmailTemp() != null) {
				EmailInfo emailInfo = new EmailInfo();
				emailInfo.setTitle(notice.getEmailTitle());
				emailInfo.setContent(replaceParam(notice.getEmailTemp(), taskPram.getParamMap()));
				emailInfo.setAddress(email);
				mailSender.sendMail(emailInfo);
			}
		}

	}

	public void sendInnerMessage(NoticeTask notice, MQMsg taskPram) throws Exception {
		if (notice.getInnerMsgActivated() && notice.getInnerMsgUsed()) {
			if (notice.getInnerMsgTemp() != null) {
				messageService.sendMessage(ImConstants.ADMIN_ID, ImConstants.ADMIN_ACCOUNT, taskPram.getUserId(), replaceParam(notice.getInnerMsgTemp(), taskPram.getParamMap()));
			}
		}
	}

	public void sendNotice(NoticeTask notice, MQMsg taskPram) throws Exception {
		if (notice.getNoteActivated() && notice.getNoteUsed()) {
			if (notice.getNoteTemp() != null) {
				List<String> list = new ArrayList<String>();
				String noticeStrucStr = redisSerive.get(noticeRedisKey);
				if (noticeStrucStr != null) {
					list = (List<String>) DataConverterUtil.convertJson2Object(noticeStrucStr, ArrayList.class);
				}
				NoticeStruc noticeStruc = new NoticeStruc();
				noticeStruc.setUserId(taskPram.getUserId());
				noticeStruc.setUrl(taskPram.getParamMap().get("url"));
				noticeStruc.setText(replaceParam(notice.getNoteTemp(), taskPram.getParamMap()));
				noticeStruc.setNoticeTime(DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN));
				list.add(DataConverterUtil.convertObject2Json(noticeStruc));
				redisSerive.set(noticeRedisKey, DataConverterUtil.convertObject2Json(list));
			}
		}
	}

	private String replaceParam(String template, Map<String, String> paramMap) {
		try {
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String key = "{$" + entry.getKey() + "}";
				template = template.replace(key, entry.getValue());
			}
		} catch (Exception e) {
			logger.error("replace template param error", e);
		}
		return template;
	}

	/**
	* Title: sendNoticeTask
	* Description:
	* @param notice
	* @param taskPram
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#sendNoticeTask(com.winterframework.firefrog.notice.entity.NoticeTask, com.winterframework.firefrog.notice.entity.MQMsg) 
	*/
	@Override
	public void sendNoticeTask(NoticeTask notice, MQMsg taskPram) throws Exception {
		
		UserNoticeTask usernotice = null;
		usernotice = noticeTaskServiceImpl.getSendUserNoticeTask (taskPram.getTaskId(), taskPram.getUserId());
		notice = noticeTaskServiceImpl.getNoticeTaskById (taskPram.getTaskId());
		
		boolean bActive = false ;
		if (usernotice != null && notice != null){
			NoticeTaskStruc noticeTaskStruc = DTOConverter.transNoticeTask2Struc(notice);
			noticeTaskStruc.setEmailUsed(usernotice.getEmailUsed());
			noticeTaskStruc.setInnerMsgUsed(usernotice.getInnerMsgUsed());
			noticeTaskStruc.setNoteUsed(usernotice.getNoteUsed());
			noticeTaskStruc.setSmsUsed(usernotice.getEmsUsed());
			noticeTaskStruc.setActived(usernotice.getActivated());
			noticeTaskStruc.setEmailActived(usernotice.getEmailActivated());
			noticeTaskStruc.setInnerMsgActived(usernotice.getInnerMsgActivated());
			noticeTaskStruc.setEmailActived(usernotice.getEmailActivated());
			noticeTaskStruc.setSmsActived(usernotice.getSmsActivated());
			notice = DTOConverter.transNoticeTaskStruc2Entity(noticeTaskStruc);
		}
		UserProfile userProfile = null;
		Map<String, String> paramMap = taskPram.getParamMap();
		if (notice!=null && notice.getId()!=null && notice.getId() == 101){//101是内置的给ssq奖期截止发邮件的任务
			userProfile = new UserProfile();
			userProfile.setEmail(taskPram.getUserName());
			paramMap.put("userName", userProfile.getAccount());

		} else {
			userProfile = userProfileService.queryUserProfile(taskPram.getUserId());
			if(userProfile==null){
				//如果没有这个人，肯定不发 了
				return;
			}
			paramMap.put("userName", userProfile.getAccount());

		}
		
		
		if (taskPram.getUserId() != null)
			paramMap.put("userId", String.valueOf(taskPram.getUserId()));
		
		if (notice != null) {
			String email=userProfile.getEmail();
			if (paramMap != null && paramMap.get("prior_email_address") != null && paramMap.get("prior_email_address").contains("@")) {
				email=paramMap.get("prior_email_address");
			}
			try {
				if (notice.getActivated()) {
					long starttime = System.currentTimeMillis() ;
					
					sendMail(notice, taskPram, email);
					logger.info ("sendMail time  " + Long.toString(System.currentTimeMillis() - starttime));
					sendInnerMessage(notice, taskPram);
					logger.info ("sendInnerMessage time  " + Long.toString(System.currentTimeMillis() - starttime));
					//這段代碼會造成 redis 資料過大，get & set 執行時間過久, 且沒有必要, 因此註解掉
					//sendNotice(notice, taskPram);
				}
			} catch (Exception e) {
				logger.error("send notice task email error", e);
				throw e;
			}
		}
	}

	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;

	/**
	* Title: getNoticeForUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#getNoticeForUser(java.lang.Long) 
	*/
	@Override
	public List<NoticeStruc> getNoticeForUser(Long userId) throws Exception {
		List<String> list = new ArrayList<String>();
		String noticeStrucStr = redisSerive.get(noticeRedisKey);
		if (noticeStrucStr != null) {
			list = (ArrayList) DataConverterUtil.convertJson2Object(noticeStrucStr, ArrayList.class);
		}
		List<NoticeStruc> result = new ArrayList<NoticeStruc>();
		String current = DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN);
		for (String noticeStr : list) {
			NoticeStruc notice = (NoticeStruc) DataConverterUtil.convertJson2Object(noticeStr, NoticeStruc.class);
			Date noticeTime = DateUtils.parse(notice.getNoticeTime(), DateUtils.DATETIME_FORMAT_PATTERN);
			Date endDateTime = DateUtils.addSeconds(noticeTime, Integer.parseInt(disableTime));
			if (notice.getUserId().longValue() == userId.longValue()
					&& DateUtils.format(endDateTime, DateUtils.DATETIME_FORMAT_PATTERN).compareTo(current) >= 0) {
				result.add(notice);
			}
		}
		return result;
	}

}
