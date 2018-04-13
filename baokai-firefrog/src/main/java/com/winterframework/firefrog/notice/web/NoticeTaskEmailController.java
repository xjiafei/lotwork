/**   
* @Title: NoticeTaskEmailController.java 
* @Package com.winterframework.firefrog.notice.web 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-24 下午3:23:17 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.IMailSender;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.service.IUserNoticeTaskService;
import com.winterframework.firefrog.notice.web.dto.DTOConverter;
import com.winterframework.firefrog.notice.web.dto.EmailDto;
import com.winterframework.firefrog.notice.web.dto.NoticeStruc;
import com.winterframework.firefrog.notice.web.dto.NoticeTaskStruc;
import com.winterframework.firefrog.notice.web.dto.NoticeTempDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: NoticeTaskEmailController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-24 下午3:23:17 
*  
*/
@Controller("noticeTaskEmailController")
@RequestMapping("/noticeAdmin")
public class NoticeTaskEmailController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeTaskEmailController.class);

	@Resource(name = "templateMailSender")
	private IMailSender mailSender;

	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;

	@Resource(name = "userNoticeTaskServiceImpl")
	private IUserNoticeTaskService userNoticeTaskServiceImpl;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@PropertyConfig(value = "notice.Redis.Key")
	private String noticeRedisKey;

	/**
	 * 
	* @Title: testEmail 
	* @Description: 测试邮件发送
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/testEmail")
	@ResponseBody
	public Response testEmail(@RequestBody Request<EmailDto> request) throws Exception {
		logger.debug("test email begin");
		Response resp = new Response(request);
		try {
			EmailDto emailDto = request.getBody().getParam();
			EmailInfo emailInfo = new EmailInfo();
			emailInfo.setAddress(emailDto.getRcvEmail());
			emailInfo.setContent(emailDto.getContent());
			emailInfo.setTitle(emailDto.getTitle());
			emailInfo.setFromAdress(emailDto.getSendEmail());
			mailSender.sendMail(emailInfo);
		} catch (Exception e) {
			logger.error("test email error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: updateNoticeTask 
	* @Description: 更新通知任务
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateNoticeTask")
	@ResponseBody
	public Response updateNoticeTask(@RequestBody Request<NoticeTaskStruc> request) throws Exception {
		logger.debug("updateNoticeTask begin");
		Response resp = new Response(request);
		try {
			NoticeTaskStruc noticeTaskStruc = request.getBody().getParam();
			noticeTaskServiceImpl.update(DTOConverter.transNoticeTaskStruc2Entity(noticeTaskStruc));
		} catch (Exception e) {
			logger.error("updateNoticeTask error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: updateInnerMsgTemp 
	* @Description: 更新站内信模板
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateInnerMsgTemp")
	@ResponseBody
	public Response updateInnerMsgTemp(@RequestBody Request<NoticeTempDto> request) throws Exception {
		logger.debug("updateInnerMsgTemp begin");
		Response resp = new Response(request);
		try {
			NoticeTempDto innerMsgTemp = request.getBody().getParam();
			NoticeTask noticeTask = new NoticeTask();
			noticeTask.setId(innerMsgTemp.getId());
			noticeTask.setInnerMsgTitle(innerMsgTemp.getTitle());
			noticeTask.setInnerMsgTemp(innerMsgTemp.getTemp());
			noticeTaskServiceImpl.update(noticeTask);
		} catch (Exception e) {
			logger.error("updateInnerMsgTemp error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: updateEmailTemp 
	* @Description: 更新邮件模板
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateEmailTemp")
	@ResponseBody
	public Response updateEmailTemp(@RequestBody Request<NoticeTempDto> request) throws Exception {
		logger.debug("updateEmailTemp begin");
		Response resp = new Response(request);
		try {
			NoticeTempDto innerMsgTemp = request.getBody().getParam();
			NoticeTask noticeTask = new NoticeTask();
			noticeTask.setId(innerMsgTemp.getId());
			noticeTask.setEmailTitle(innerMsgTemp.getTitle());
			noticeTask.setEmailTemp(innerMsgTemp.getTemp());
			noticeTaskServiceImpl.update(noticeTask);
		} catch (Exception e) {
			logger.error("updateEmailTemp error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: updateNoteTemp 
	* @Description: 更新通知模板
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateNoteTemp")
	@ResponseBody
	public Response updateNoteTemp(@RequestBody Request<NoticeTempDto> request) throws Exception {
		logger.debug("updateNoteTemp begin");
		Response resp = new Response(request);
		try {
			NoticeTempDto innerMsgTemp = request.getBody().getParam();
			NoticeTask noticeTask = new NoticeTask();
			noticeTask.setId(innerMsgTemp.getId());
			noticeTask.setNoteTemp(innerMsgTemp.getTemp());
			noticeTaskServiceImpl.update(noticeTask);
		} catch (Exception e) {
			logger.error("updateNoteTemp error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryNoticeTaskById 
	* @Description: 根据ID查询通知任务
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryNoticeTaskById")
	@ResponseBody
	public Response<NoticeTaskStruc> queryNoticeTaskById(@RequestBody Request<NoticeTaskStruc> request)
			throws Exception {
		logger.debug("queryNoticeTaskById begin");
		Response<NoticeTaskStruc> resp = new Response<NoticeTaskStruc>(request);
		try {
			NoticeTaskStruc noticeTaskStruc = request.getBody().getParam();
			NoticeTask noticeTask = noticeTaskServiceImpl.getNoticeTaskById(noticeTaskStruc.getId());
			resp.setResult(DTOConverter.transNoticeTask2Struc(noticeTask));
		} catch (Exception e) {
			logger.error("queryNoticeTaskById error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: queryNoticeTask 
	* @Description: 查询通知任务
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryNoticeTask")
	@ResponseBody
	public Response<List<NoticeTaskStruc>> queryNoticeTask(@RequestBody Request<Boolean> request) throws Exception {
		logger.debug("queryNoticeTask begin");
		Response<List<NoticeTaskStruc>> resp = new Response<List<NoticeTaskStruc>>(request);
		try {
			Boolean setByUser = request.getBody().getParam();
			List<NoticeTaskStruc> taskStrucList = new ArrayList<NoticeTaskStruc>();
			List<NoticeTask> taskList = noticeTaskServiceImpl.queryAllTask();
			if (setByUser) {
				Long userId = request.getHead().getUserId();
				List<UserNoticeTask> userTaskList = userNoticeTaskServiceImpl.queryUserNoticeTaskByUser(userId);
				taskStrucList = handerNoticeTaskSruc(taskList, userTaskList);
			} else {
				for (NoticeTask noticeTask : taskList) {
					taskStrucList.add(DTOConverter.transNoticeTask2Struc(noticeTask));
				}

			}
			resp.setResult(taskStrucList);
		} catch (Exception e) {
			logger.error("queryNoticeTask error", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: getNoticeForUser 
	* @Description: 查询用户桌面通知
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/getNoticeForUser")
	@ResponseBody
	public Response<List<NoticeStruc>> getNoticeForUser(@RequestBody Request request) throws Exception {
		logger.debug("getNoticeForUser begin");
		Response<List<NoticeStruc>> resp = new Response<List<NoticeStruc>>(request);
		Long userId = request.getHead().getUserId();
		List<NoticeStruc> list = noticeTaskServiceImpl.getNoticeForUser(userId);
		resp.setResult(list);
		return resp;
	}

	/**
	 * 
	* @Title: bindUserNoticeTask 
	* @Description: 绑定用户通知任务
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/bindUserNoticeTask")
	@ResponseBody
	public Response bindUserNoticeTask(@RequestBody Request<NoticeTaskStruc[]> request) throws Exception {
		logger.debug("bindUserNoticeTask begin");
		Response resp = new Response(request);
		try {
			NoticeTaskStruc[] noticeStrucs = request.getBody().getParam();
			userNoticeTaskServiceImpl.deleteByUser(request.getHead().getUserId());
			if (noticeStrucs != null) {
				for (NoticeTaskStruc noticeStruc : noticeStrucs) {
					userNoticeTaskServiceImpl.saveOrUpdateUserNoticeTask(DTOConverter.transNoticeTaskStruc2UserEntity(
							noticeStruc, request.getHead().getUserId()));
				}
			}
		} catch (Exception e) {
			logger.error("bindUserNoticeTask error", e);
			throw e;
		}
		return resp;
	}

	private List<NoticeTaskStruc> handerNoticeTaskSruc(List<NoticeTask> taskList, List<UserNoticeTask> userTaskList) {
		List<NoticeTaskStruc> noticeTaskStrucs = new ArrayList<NoticeTaskStruc>();
		for (NoticeTask noticeTask : taskList) {
			NoticeTaskStruc noticeTaskStruc = DTOConverter.transNoticeTask2Struc(noticeTask);
			//noticeTaskStruc.setId(null);
			if (noticeTask.getSetByUser() && noticeTask.getActivated()) {
				//可悲用户设置，并且已激活
				noticeTaskStrucs.add(noticeTaskStruc);
				for (UserNoticeTask userNoticeTask : userTaskList) {
					//如果可以被用户设置的话
					if (noticeTaskStruc.getId().equals(Long.valueOf(userNoticeTask.getTask()))) {
						noticeTaskStruc.setEmailUsed(userNoticeTask.getEmailUsed());
						noticeTaskStruc.setInnerMsgUsed(userNoticeTask.getInnerMsgUsed());
						noticeTaskStruc.setNoteUsed(userNoticeTask.getNoteUsed());
						noticeTaskStruc.setSmsUsed(userNoticeTask.getEmsUsed());
						noticeTaskStruc.setActived(userNoticeTask.getActivated());
						noticeTaskStruc.setEmailActived(userNoticeTask.getEmailActivated());
						noticeTaskStruc.setInnerMsgActived(userNoticeTask.getInnerMsgActivated());
						noticeTaskStruc.setEmailActived(userNoticeTask.getEmailActivated());
						noticeTaskStruc.setSmsActived(userNoticeTask.getSmsActivated());
					}
				}
			}
		}
		return noticeTaskStrucs;
	}
}
