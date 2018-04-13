package com.winterframework.firefrog.json.api.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.web.dto.NoticeStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping("/api/jsonp/")
public class JsonpController {

	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;
	private static final Logger logger = LoggerFactory.getLogger(JsonpController.class);

	/**
	 * @param callBack
	 * @return
	 */
	@RequestMapping(value = "/getTips")
	@ResponseBody
	public JsonStruc<JsonDataStruc<Tip>> getTips(String callBack,Long userId) {
		JsonStruc<JsonDataStruc<Tip>> js = new JsonStruc<JsonDataStruc<Tip>>("msgPush");
		js.setCallBack(callBack);
		JsonDataStruc<Tip> jds = new JsonDataStruc<Tip>();
		jds.setName("messageTips");
		if ("-100".equals(callBack)) {
			jds.setTplData(new Tip[] { new Tip(
					"<li><div class=\"game_logo\"><img src=\"../images/game/n115/tipslogo.png\" /></div><div class=\"content\">606d559c572853cc827274037b2c 2013090 671f4e2d5956 <span class=\"color-red\">1,000</span> 5143, <a href=\"#\"> 67e5770b8be660c5</a></div><i class=\"close_btn\"></i></li>") });
		} else {
			try {
				List<NoticeStruc> list = noticeTaskServiceImpl.getNoticeForUser(userId);
				if (!list.isEmpty()) {
					Tip[] notes = new Tip[list.size()];
					int i = 0;
					for (NoticeStruc noticeStruc : list) {
						Tip tip = new Tip(noticeStruc.getText());
						notes[i] = tip;
						i++;
					}
					jds.setTplData(notes);
					js.getData().add(jds);
				}

			} catch (Exception e) {
				logger.error("get Tips error", e);
				js.setIsSuccess(0);
			}
		}

		return js;
	}

}
