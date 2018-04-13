/**   
* @Title: NoticeTaskServiceImpl.java 
* @Package com.winterframework.firefrog.notice.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午11:44:38 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.email.IMailSender;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.ImConstants;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.IWithdrawAppealDao;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.service.IWithdrawAppealService;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.web.dto.WithdrawAppealStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditRequest;
import com.winterframework.firefrog.notice.entity.MQMsg;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.web.dto.NoticeStruc;
import com.winterframework.firefrog.user.service.IMessage2Service;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;

/** 
* @ClassName: NoticeTaskServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午11:44:38 
*  
*/
@Service("withdrawAppealServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class WithdrawAppealServiceImpl implements IWithdrawAppealService {

	private static Logger logger = LoggerFactory.getLogger(WithdrawAppealServiceImpl.class);

	
	@Resource(name = "withdrawAppealDaoImpl")
	private IWithdrawAppealDao withdrawAppealDao;

	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userProfileService;

	@Resource(name = "templateMailSender")
	private IMailSender mailSender;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;
	
	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;

	@Resource(name= "message2ServiceImpl")
	private IMessage2Service messageService;

	@PropertyConfig(value = "notice.Redis.Key")
	private String noticeRedisKey;

	@PropertyConfig(value = "notice.note.Redis.disableTime")
	private String disableTime;

	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;


	
	/**
	* Title: queryWithdrawAppeal
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	*/
	@Override
	public List<WithdrawAppeal> queryWithdrawAppeal(Request<WithdrawAppealStruc> request) throws Exception {
		return withdrawAppealDao.queryWithdrawAppeal(request);
	}
	
	
	/**
	 * Title: queryWithdrawAppeal
	 * Description:
	 * @return
	 * @throws Exception 
	 * @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	 */
	@Override
	public List<WithdrawAppeal> queryAppeal(Request<WithdrawAppealStruc> request) throws Exception {
		return withdrawAppealDao.queryAppeal(request);
	}
	
	/**
	 * Title: queryWithdrawAppeal
	 * Description:
	 * @return
	 * @throws Exception 
	 * @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	 */
	@Override
	public List<WithdrawAppeal> updateAppealByWithdrawSn(Request<WithdrawAppealStruc> request) throws Exception {
		return withdrawAppealDao.updateAppealByWithdrawSn(request);
	}
	
	/**
	 * Title: updateAppealStatus
	 * Description:
	 * @return
	 * @throws Exception 
	 * @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	 */
	@Override
	public List<WithdrawAppeal> updateAppealStatus(Request<WithdrawAppealStruc> request) throws Exception {
		return withdrawAppealDao.updateAppealStatus(request);
	}
	
	/**
	 * Title: queryAppealbySn
	 * Description:
	 * @return
	 * @throws Exception 
	 * @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	 */
	@Override
	public List<WithdrawAppeal> queryAppealbySn(Request<WithdrawAppealStruc> request) throws Exception {
		return withdrawAppealDao.queryAppealbySn(request);
	}
	
	
	/**
	 * Title: updateAppealStatus
	 * Description:
	 * @return
	 * @throws Exception 
	 * @see com.winterframework.firefrog.notice.service.INoticeTaskService#queryAllTask() 
	 */
	@Override
	public List<WithdrawAppeal> createAppealSn(Request<WithdrawAuditRequest> request) throws Exception {
		FundWithdrawOrder entity = fundWithdrawDao.queryById(request.getBody().getParam().getId());
		return withdrawAppealDao.createAppealSn(entity,request,snUtil);
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

	
	
	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;



	@Override
	public Long queryUncheckAppeal() throws Exception {
		return withdrawAppealDao.queryUncheckAppealCount();
	}



	

	

}
