package com.winterframework.firefrog.notice.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.notice.web.dto.NoticeSenderRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: NoticeMsgController 
* @Description: 消息发送接口
* @author Tod
* @date 2013-11-19 下午2:33:37 
*
 */
@Controller("noticeSenderController")
@RequestMapping("/noticeAdmin")
public class NoticeSenderController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeSenderController.class);

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	/**
	 * 
	* @Title: sendNotice 
	* @Description: 发送消息
	* 
	* NoticeSenderRequest.taskId(Long)说明：
	* 
	* 5：邮箱绑定时（验证邮箱）
	* 6：邮箱修改（验证老邮箱）
	* 7：邮箱修改（验证新邮箱）
	* 8：找回登录密码---通过邮箱找回（验证邮箱）
	* 9：找回安全密码（验证邮箱）
	* 10：账号申诉--绑定邮箱（验证邮箱）
	* 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = {"/sendNotice","/SendNotice"})
	public @ResponseBody
	Response<Object> SendNotice(@RequestBody Request<NoticeSenderRequest> request) throws Exception {
		Response<Object> resp = new Response<Object>(request);
		NoticeSenderRequest sendReq = request.getBody().getParam();
		try {
			msgToMQ.addMessageToMq(sendReq.getUserId(), sendReq.getAccount(), sendReq.getTaskId(),
					sendReq.getParamMap());
		} catch (Exception e) {
			logger.error("sendNotice error.", e);
		}
		return resp;
	}

}
