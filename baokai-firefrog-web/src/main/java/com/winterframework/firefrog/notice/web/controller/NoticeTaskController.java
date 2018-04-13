/**   
* @Title: NoticeTaskController.java 
* @Package com.winterframework.firefrog.notice.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 下午2:53:41 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.firefrog.notice.web.dto.NoticeTaskStruc;
import com.winterframework.firefrog.notice.web.dto.NoticeTaskTreeStruc;
import com.winterframework.firefrog.notice.web.dto.NoticeTempDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: NoticeTaskController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 下午2:53:41 
*  
*/
@Controller("noticeTaskController")
@RequestMapping(value = "/noticeAdmin")
public class NoticeTaskController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeTaskController.class);
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.notice.updateNoticeTask")
	private String updateNoticeTaskUrl;

	@PropertyConfig(value = "url.notice.updateInnerMsgTemp")
	private String updateInnerMsgTempUrl;

	@PropertyConfig(value = "url.notice.updateEmailTemp")
	private String updateEmailTempUrl;

	@PropertyConfig(value = "url.notice.updateNoteTemp")
	private String updateNoteTempUrl;

	@PropertyConfig(value = "url.notice.queryNoticeTask")
	private String queryNoticeTaskUrl;

	@PropertyConfig(value = "url.notice.bindUserNoticeTask")
	private String bindUserNoticeTaskUrl;
	
	@PropertyConfig(value = "url.notice.queryNoticeTaskById")
	private String queryNoticeTaskByIdUrl;

	
	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@RequestMapping(value = "/queryNoticeTask")
	public String queryNoticeTask(Model model) throws Exception {
		List<NoticeTaskTreeStruc> treeStrucs = new ArrayList<NoticeTaskTreeStruc>();
		try {
			List<NoticeTaskStruc> noticeTasks = (List<NoticeTaskStruc>) httpClient
					.invokeHttp(urlPath + queryNoticeTaskUrl, false,
							new TypeReference<Response<List<NoticeTaskStruc>>>() {
							}).getBody().getResult();
			for (NoticeTaskStruc noticeTaskStruc : noticeTasks) {
				boolean isContinue = false;
				for (NoticeTaskTreeStruc noticeTaskTreeStruc : treeStrucs) {
					if (noticeTaskStruc.getModule().equals(noticeTaskTreeStruc.getModule())) {
						noticeTaskTreeStruc.getNoticeTasks().add(noticeTaskStruc);
						noticeTaskTreeStruc.setSize(noticeTaskTreeStruc.getSize() + 1);
						isContinue = true;
						continue;
					}
				}
				if (isContinue) {
					continue;
				}
				NoticeTaskTreeStruc treeStruc = new NoticeTaskTreeStruc();
				treeStruc.setModule(noticeTaskStruc.getModule());
				treeStruc.getNoticeTasks().add(noticeTaskStruc);
				treeStruc.setSize(treeStruc.getSize() + 1);
				treeStrucs.add(treeStruc);
			}
		} catch (Exception e) {
			logger.error("queryNoticeTask error", e);
			throw e;
		}
		model.addAttribute("treeStrucs", treeStrucs);
		model.addAttribute("cate2Name", "通知任务管理");
		return "/notice/noticeTaskManage";
	}

	@RequestMapping(value = "/toInnerMsgTemp")
	public String toInnerMsgTemp(@RequestParam("id") Long id, Model model) throws Exception {
		NoticeTaskStruc queryTask = new NoticeTaskStruc();
		queryTask.setId(id);
		try{
			NoticeTaskStruc noticeTaskStruc = (NoticeTaskStruc)httpClient.invokeHttp(urlPath + queryNoticeTaskByIdUrl, queryTask, new TypeReference<Response<NoticeTaskStruc>>() {
			}).getBody().getResult();
			model.addAttribute("task", noticeTaskStruc);
		}catch(Exception e){
			logger.error("queryNoticeTask error", e);
			throw e;
		}
		model.addAttribute("id", id);
		model.addAttribute("cate2Name", "通知任务管理");
		return "/notice/innerMsgTemplate";
	}

	@RequestMapping(value = "/toNoticeTemp")
	public String toNoticeTemp(@RequestParam("id") Long id, Model model) throws Exception {
		NoticeTaskStruc queryTask = new NoticeTaskStruc();
		queryTask.setId(id);
		try{
			NoticeTaskStruc noticeTaskStruc = (NoticeTaskStruc)httpClient.invokeHttp(urlPath + queryNoticeTaskByIdUrl, queryTask, new TypeReference<Response<NoticeTaskStruc>>() {
			}).getBody().getResult();
			model.addAttribute("task", noticeTaskStruc);
		}catch(Exception e){
			logger.error("queryNoticeTask error", e);
			throw e;
		}
		model.addAttribute("id", id);
		model.addAttribute("cate2Name", "通知任务管理");
		return "/notice/noticeTemplate";
	}

	@RequestMapping(value = "/toEmailTemp")
	public String toEmailTemp(@RequestParam("id") Long id, Model model) throws Exception {
		NoticeTaskStruc queryTask = new NoticeTaskStruc();
		queryTask.setId(id);
		try{
			NoticeTaskStruc noticeTaskStruc = (NoticeTaskStruc)httpClient.invokeHttp(urlPath + queryNoticeTaskByIdUrl, queryTask, new TypeReference<Response<NoticeTaskStruc>>() {
			}).getBody().getResult();
			model.addAttribute("task", noticeTaskStruc);
		}catch(Exception e){
			logger.error("queryNoticeTask error", e);
			throw e;
		}
		model.addAttribute("id", id);
		model.addAttribute("cate2Name", "通知任务管理");
		return "/notice/emailTemplate";
	}

	@RequestMapping(value = "/saveInnerMsgTemp")
	public String saveInnerMsgTemp(@ModelAttribute("noticeTemp") NoticeTempDto noticeTemp, Model model)
			throws Exception {
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + updateInnerMsgTempUrl, noticeTemp);
		} catch (Exception e) {
			logger.error("saveInnerMsgTemp error", e);
			throw e;
		}
		return queryNoticeTask(model);
	}

	@RequestMapping(value = "/saveEmailTemp")
	public String saveEmailTemp(@ModelAttribute("noticeTemp") NoticeTempDto noticeTemp, Model model) throws Exception {
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + updateEmailTempUrl, noticeTemp);
		} catch (Exception e) {
			logger.error("saveEmailTemp error", e);
			throw e;
		}
		return queryNoticeTask(model);
	}

	@RequestMapping(value = "/saveNoteTemp")
	public String saveNoteTemp(@ModelAttribute("noticeTemp") NoticeTempDto noticeTemp, Model model) throws Exception {
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + updateNoteTempUrl, noticeTemp);
		} catch (Exception e) {
			logger.error("saveNoteTemp error", e);
			throw e;
		}
		return queryNoticeTask(model);
	}

	@RequestMapping(value = "/openNoticeTask")
	@ResponseBody
	public Object openNoticeTask(@RequestParam("id") Long id, Model model) throws Exception {
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			NoticeTaskStruc noticeTaskStruc = new NoticeTaskStruc();
			noticeTaskStruc.setId(id);
			noticeTaskStruc.setActived(1l);
			httpClient.invokeHttpWithoutResultType(urlPath + updateNoticeTaskUrl, noticeTaskStruc);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("openNoticeTask error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("openNoticeTask end");
		return resp;
	}

	@RequestMapping(value = "/closeNoticeTask")
	@ResponseBody
	public Object closeNoticeTask(@RequestParam("id") Long id, Model model) throws Exception {
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			NoticeTaskStruc noticeTaskStruc = new NoticeTaskStruc();
			noticeTaskStruc.setId(id);
			noticeTaskStruc.setActived(0l);
			httpClient.invokeHttpWithoutResultType(urlPath + updateNoticeTaskUrl, noticeTaskStruc);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("closeNoticeTask error");
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		logger.info("closeNoticeTask end");
		return resp;
	}

	@RequestMapping(value = "/updateNoticeTask")
	public String updateNoticeTask(HttpServletRequest request, Model model) throws Exception {
		try {
			List<NoticeTaskStruc> noticeTasks = (List<NoticeTaskStruc>) httpClient
					.invokeHttp(urlPath + queryNoticeTaskUrl, false,
							new TypeReference<Response<List<NoticeTaskStruc>>>() {
							}).getBody().getResult();
			for (NoticeTaskStruc noticeTaskStruc : noticeTasks) {
				if (noticeTaskStruc.getEmailActived().longValue() == 1 || noticeTaskStruc.getInnerMsgActived() == 1
						|| noticeTaskStruc.getNoteActived() == 1) {
					NoticeTaskStruc updateStruc = new NoticeTaskStruc();
					updateStruc.setId(noticeTaskStruc.getId());
					if (noticeTaskStruc.getEmailActived().longValue() == 1) {
						Long emailUsed = request.getParameter("email_" + noticeTaskStruc.getId()) != null ? Long
								.valueOf(request.getParameter("email_" + noticeTaskStruc.getId())) : 0l;
						updateStruc.setEmailUsed(emailUsed);
					}
					if (noticeTaskStruc.getInnerMsgActived().longValue() == 1) {
						Long innerMsgUsed = request.getParameter("innerMsg_" + noticeTaskStruc.getId()) != null ? Long
								.valueOf(request.getParameter("innerMsg_" + noticeTaskStruc.getId())) : 0l;
						updateStruc.setInnerMsgUsed(innerMsgUsed);
					}
					if (noticeTaskStruc.getNoteActived().longValue() == 1) {
						Long noteUsed = request.getParameter("note_" + noticeTaskStruc.getId()) != null ? Long
								.valueOf(request.getParameter("note_" + noticeTaskStruc.getId())) : 0l;
						updateStruc.setNoteUsed(noteUsed);
					}
					httpClient.invokeHttpWithoutResultType(urlPath + updateNoticeTaskUrl, updateStruc);
				}
			}
		} catch (Exception e) {
			logger.error("updateNoticeTask error", e);
			throw e;
		}
		return queryNoticeTask(model);
	}
}
