package com.winterframework.firefrog.notice.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgMQRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 向MQ队列中添加信息的controller
 */
@Controller("noticeMsgMQController")
@RequestMapping("/noticeAdmin")
public class NoticeMsgMQController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeMsgMQController.class);

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher noticeMsgPublisher;

	/** 
	 * 向MQ对列中添加信息
	*/
	@RequestMapping(value = "/addMessageToMq")
	@ResponseBody
	public Response<Object> addMessageToMq(@RequestBody Request<NoticeMsgMQRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		NoticeMsgMQRequest req = request.getBody().getParam();
		try {
			noticeMsgPublisher.addMessageToMq(req.getUserId(), req.getAccount(), req.getTaskId(), req.getParamMap());
		} catch (Exception e) {
			logger.error("addMessageToMq error.", e);
			throw e;
		}
		return response;
	}
}
