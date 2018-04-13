package com.winterframework.firefrog.json.api.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.json.api.web.dto.AdvertData;
import com.winterframework.firefrog.json.api.web.dto.AdvertStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping("/api/jsonp/")
public class JsonpController {

	@PropertyConfig(value = "url.imageserver.visit")
	private String visitUrl;

	@PropertyConfig(value = "url.notice.getNoticeForUser")
	private String getNoticeForUserUrl;

	@PropertyConfig(value = "url.advert.queryAdNoticeForUser")
	private String queryAdNoticeForUserUrl;

	@PropertyConfig(value = "url.advert.getAdvertByNames")
	private String getAdvertByNamesUrl;

	@PropertyConfig(value = "url.fund.getUserBal")
	private String getUserBalUrl;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	private static final Logger logger = LoggerFactory.getLogger(JsonpController.class);

	/**
	 * 获取桌面通知，从session获取用户id
	 * @param callBack
	 * @return
	 */
	@RequestMapping(value = "/getAdverts")
	@ResponseBody
	public JsonStruc<AdvertData> getAdverts(String[] k,String u, String callBack) {
		JsonStruc<AdvertData> js = new JsonStruc<AdvertData>("");
		//String[] namesArray = k.split(",");
		List<String> list = Arrays.asList(k);
		js.setCallBack(callBack);
		
		Long userId = RequestContext.getCurrUser().getId();
		if(userId== null && "-1".equals(u)){
			userId=-1l;
		}
		try {
			Response<List<AdvertData>> response = httpClient.invokeHttp(urlPath + getAdvertByNamesUrl, list, userId,
					null, new TypeReference<Response<List<AdvertData>>>() {
					});
			List<AdvertData> advertDatas = response.getBody().getResult();
			for (AdvertData advertData : advertDatas) {
				List<AdvertStruc> listStruc = advertData.getList();
				for (AdvertStruc advert : listStruc) {
					advert.setSrc(visitUrl + advert.getSrc());
				}
			}
			js.getData().addAll(advertDatas);
			js.setMsg("请求成功");
		} catch (Exception e) {
			logger.error("getAdverts errors", e);
			js.setIsSuccess(0);
			js.setMsg("请求失败");
		}
		return js;
	}

	/**
	 * 获取桌面通知，从session获取用户id
	 * @param callBack
	 * @return
	 */
	@RequestMapping(value = "/getTips")
	@ResponseBody
	public JsonStruc<JsonDataStruc<Tip>> getTips(String callBack) {
		JsonStruc<JsonDataStruc<Tip>> js = new JsonStruc<JsonDataStruc<Tip>>("msgPush");
		js.setCallBack(callBack);
		JsonDataStruc<Tip> jds = new JsonDataStruc<Tip>();
		jds.setName("messageTips");
		Long userId = RequestContext.getCurrUser().getId();
		if ("-100".equals(callBack)) {
			jds.setTplData(new Tip[] { new Tip(
					"<li><div class=\"game_logo\"><img src=\"../images/game/n115/tipslogo.png\" /></div><div class=\"content\">606d559c572853cc827274037b2c 2013090 671f4e2d5956 <span class=\"color-red\">1,000</span> 5143, <a href=\"#\"> 67e5770b8be660c5</a></div><i class=\"close_btn\"></i></li>") });
		} else {
			try {
				Response<List<NoticeStruc>> response = httpClient.invokeHttp(urlPath + getNoticeForUserUrl, null,
						userId, null, new TypeReference<Response<List<NoticeStruc>>>() {
						});
				List<NoticeStruc> list = response.getBody().getResult();
				if (!list.isEmpty()) {
					Tip[] notes = new Tip[list.size()];
					int i = 0;
					for (NoticeStruc noticeStruc : list) {
						Tip tip = new Tip(noticeStruc.getText());
						notes[i] = tip;
						i++;
					}
					jds.setTplData(notes);
				}

			} catch (Exception e) {
				logger.error("get Tips error", e);
				js.setIsSuccess(0);
			}
		}

		js.getData().add(jds);
		return js;
	}

	/**
	 * 获取公告
	 * @param callBack
	 * @return
	 */
	@RequestMapping(value = "/getNotices")
	@ResponseBody
	public JsonStruc<JsonDataStruc<Note>> getNotices(String[] page, String callBack) {
		Long userId = RequestContext.getCurrUser().getId();//45l;
		JsonStruc<JsonDataStruc<Note>> js = new JsonStruc<JsonDataStruc<Note>>("msgNotice");
		js.setCallBack(callBack);
		js.setMsg("success");
		List<String> pageList = Arrays.asList(page);
		try {
			Response<List<AdNoticeStruc>> response = httpClient.invokeHttp(urlPath + queryAdNoticeForUserUrl, pageList,
					userId, null, new TypeReference<Response<List<AdNoticeStruc>>>() {
					});
			List<AdNoticeStruc> list = response.getBody().getResult();
			for (String pageName : pageList) {
				JsonDataStruc<Note> jds = new JsonDataStruc<Note>();
				jds.setName(pageName);
				List<Note> notes = new ArrayList<Note>();
				for (AdNoticeStruc adNotice : list) {
					if (isContainsPage(pageName, adNotice.getShowPages())) {
						Long endTime=System.currentTimeMillis()+100*24*1000*3600;
						if(adNotice.getGmtCreatede()!=null){
							endTime=adNotice.getGmtCreatede().getTime();
						}
						Note note = new Note(adNotice.getTitle(), String.valueOf(adNotice.getId()),endTime);
						notes.add(note);
					}

				}
				Note[] noteArray = new Note[notes.size()];
				jds.setTplData(notes.toArray(noteArray));
				js.getData().add(jds);
			}

		} catch (Exception e) {
			logger.error("get notice error", e);
			js.setIsSuccess(0);
		}

		return js;
	}

	private boolean isContainsPage(String pages, String showPages) {
		if (showPages == null) {
			return false;
		}
		String[] spages = showPages.split(",");
		for (int i = 0; i < spages.length; i++) {
			if (pages.equals(spages[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取余额
	 * @param callBack
	 * @return
	 */
	@RequestMapping(value = "/getBal")
	@ResponseBody
	public JsonStruc<Long> getBal(String callBack) {
		Long userId = RequestContext.getCurrUser().getId();
		JsonStruc<Long> js = new JsonStruc<Long>("");
		try {
			Response<Long> response = httpClient.invokeHttp(urlPath + getUserBalUrl, userId, null,
					new TypeReference<Response<Long>>() {
					});

			js.setCallBack(callBack);
			Long bal = response.getBody().getResult();
			js.getData().add(bal);
		} catch (Exception e) {
			logger.error("get user bal error", e);
		}

		return js;
	}

}
