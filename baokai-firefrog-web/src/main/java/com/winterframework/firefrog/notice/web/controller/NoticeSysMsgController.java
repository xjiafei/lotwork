package com.winterframework.firefrog.notice.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.firefrog.notice.web.dto.MessageQueryDetailRequestDTO;
import com.winterframework.firefrog.notice.web.dto.MessageQueryRequest;
import com.winterframework.firefrog.notice.web.dto.MsgInfo;
import com.winterframework.firefrog.notice.web.dto.MsgInfoResponse;
import com.winterframework.firefrog.notice.web.dto.MsgStrucResponse;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgDetailRequest;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgInfo;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgStatusRequest;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("NoticeSysMsgController")
@RequestMapping(value = "/noticeAdmin")
public class NoticeSysMsgController {
	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.notice.querySysMsgList")
	private String queryList;

	@PropertyConfig(value = "url.notice.queryUserMsgList")
	private String queryUserMsgList;

	@PropertyConfig(value = "url.notice.queryUserMsgDetail")
	private String queryUserMsgDetail;

	@PropertyConfig(value = "url.notice.querySysMsgDetail")
	private String queryDetail;

	@PropertyConfig(value = "url.notice.modifySysMsgStatus")
	private String modifyMsg;

	@PropertyConfig(value = "url.notice.createMsg")
	private String createMsg;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	private final static Map<String, String> groupMap = new HashMap<String, String>();

	static {
		groupMap.put("ALL", "全部客户");
		groupMap.put("TOP_AGENT", "总代");
		groupMap.put("AGENT_1", "一级代理");
		groupMap.put("AGENT_2", "二级代理");
		groupMap.put("AGENT_3", "三级代理");
		groupMap.put("AGENT_4", "四级代理");
		groupMap.put("AGENT_5", "五级代理");
		groupMap.put("AGENT_xx", "xx级代理");
		groupMap.put("PASSANGE", "玩家");
		groupMap.put("VIP", "VIP");
		groupMap.put("NONVIP", "非VIP");
	}

	@RequestMapping(value = "/goCreateMsg")
	public ModelAndView goCreateMsg(Long msgType) throws Exception {
		ModelAndView mav = new ModelAndView("notice/msgCreate");
		mav.addObject("cate2Name", "goCreateMsg");
		return mav;
	}

	@RequestMapping(value = { "/goSysMsgManager", "/" })
	public Object goMsgManager(Long msgType) throws Exception {
		NoticeMsgSearchDto search = new NoticeMsgSearchDto();
		search.setPageNo(1L);
		if (msgType == null) {
			msgType = 0L;
		}
		search.setSendSatus(msgType == 0L ? -1L : 3L);
		search.setMsgType(msgType);
		return "forward:/noticeAdmin/searchMsg?pageNo=" + search.getPageNo() + "&msgType=" + search.getMsgType()
				+ "&sendSatus=" + search.getSendSatus();
	}

	@RequestMapping(value = "/goUserMsg")
	public ModelAndView goUserMsg() throws Exception {
		return searchUserMsg(new MessageQueryRequest(), 1L);
	}

	@RequestMapping(value = "/searchUserMsgDetail")
	public ModelAndView searchUserMsgDetail(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/notice/msgUserView");
		MessageQueryDetailRequestDTO req = new MessageQueryDetailRequestDTO();
		req.setRootId(id);
		Response<List<MsgStrucResponse>> resp = httpClient.invokeHttp(urlPath + queryUserMsgDetail, req,
				new TypeReference<Response<List<MsgStrucResponse>>>() {
				});
		MsgStrucResponse msg = resp.getBody().getResult().get(0);
		mav.addObject("msg", msg);
		mav.addObject("time",
				DateUtils.format(new Date(msg.getSendTime()), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
		return mav;
	}

	@RequestMapping(value = "/searchUserMsgInfo")
	@ResponseBody
	public Object searchUserMsgInfo(Long id, Integer pageNo) throws Exception {
		MessageQueryDetailRequestDTO req = new MessageQueryDetailRequestDTO();
		req.setRootId(id);
		Response<List<MsgStrucResponse>> resp = httpClient.invokeHttp(urlPath + queryUserMsgDetail, req,
				new TypeReference<Response<List<MsgStrucResponse>>>() {
				});
		List<MsgInfo> infoList = new ArrayList<MsgInfo>();
		MsgInfo info = null;
		List<MsgStrucResponse> msgList = resp.getBody().getResult();
		String firstPerson = msgList.get(0).getSendAccount();

		if (pageNo != null) {

			int startNum = (pageNo - 1) * 10 + 1;
			int num = pageNo * 10;
			if (startNum <= msgList.size()) {
				if (num > msgList.size() - 1) {
					num = msgList.size() - 1;
				}
				if (pageNo == 1) {
					startNum = 0;
				}
				for (int i = startNum; i <= num; i++) {
					MsgStrucResponse msg = msgList.get(i);
					info = new MsgInfo();
					info.setMsgid(msg.getId());
					info.setContent(msg.getContent());
					info.setTime(DateUtils.format(new Date(msg.getSendTime()),
							DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
					info.setUsername(msg.getSendAccount());
					info.setType(msg.getSendAccount().equals(firstPerson) ? 1L : 2L);
					infoList.add(info);
				}
			}

		} else {
			for (MsgStrucResponse msg : msgList) {
				info = new MsgInfo();
				info.setMsgid(msg.getId());
				info.setContent(msg.getContent());
				info.setTime(DateUtils.format(new Date(msg.getSendTime()),
						DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
				info.setUsername(msg.getSendAccount());
				info.setType(msg.getSendAccount().equals(firstPerson) ? 1L : 2L);
				infoList.add(info);
			}
		}

		MsgInfoResponse result = new MsgInfoResponse();
		result.setData(infoList);
		result.setIsSuccess(1L);
		result.setType("success");
		result.setMsg("msg");
		return result;
	}

	@RequestMapping(value = "/searchUserMsg")
	public ModelAndView searchUserMsg(MessageQueryRequest search, Long userType) throws Exception {
		ModelAndView mav = new ModelAndView("/notice/msgUser");
		if (userType != null && userType == 2L) {
			search.setReceiver(search.getSender());
			search.setSender(null);
		}
		search.setType(2L);
		search.setPageNo(search.getPageNo() == null ? 1L : search.getPageNo());
		Pager pager = new Pager();
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		search.setSendTimeStart(DateUtils.parse(search.getSendTimeStartStr(), DateUtils.DATETIME_FORMAT_PATTERN));
		search.setSendTimeEnd(DateUtils.parse(search.getSendTimeEndStr(), DateUtils.DATETIME_FORMAT_PATTERN));
		Response<List<MsgStrucResponse>> resp = httpClient.invokeHttp(urlPath + queryUserMsgList, search, pager,
				new TypeReference<Response<List<MsgStrucResponse>>>() {
				});
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());

		mav.addObject("page", page);
		List<NoticeMsgInfo> msgList = new ArrayList<NoticeMsgInfo>();
		List<MsgStrucResponse> list = resp.getBody().getResult();
		NoticeMsgInfo info = null;
		for (MsgStrucResponse msgStruc : list) {
			info = new NoticeMsgInfo();
			info.setSentTime(DateUtils.format(new Date(msgStruc.getSendTime()),
					DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
			info.setTitle(msgStruc.getTitle());
			info.setOperator(msgStruc.getSendAccount());
			String content = msgStruc.getContent();
			content = content.length()>40?content.substring(0,40):content;
			info.setContent(content);
			info.setAcceptUser(msgStruc.getReceiveAccount());
			info.setId(msgStruc.getId());
			info.setRepayCount(msgStruc.getSendMsgRout() == null ? 0l : msgStruc.getSendMsgRout().split(",").length);
			msgList.add(info);
		}
		mav.addObject("list", msgList);
		mav.addObject("search", search);
		mav.addObject("start", DateUtils.format(search.getSendTimeStart(), DateUtils.DATETIME_FORMAT_PATTERN));
		mav.addObject("end", DateUtils.format(search.getSendTimeEnd(), DateUtils.DATETIME_FORMAT_PATTERN));
		mav.addObject("userType", userType);
		mav.addObject("cate2Name", "goUserMsg");
		return mav;
	}

	@RequestMapping(value = "/viewMsgManager")
	public ModelAndView viewMsgManager(Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/notice/msgView");
		NoticeMsgDetailRequest req = new NoticeMsgDetailRequest();
		req.setId(id);
		Response<NoticeMsgStruc> resp = httpClient.invokeHttp(urlPath + queryDetail, req,
				new TypeReference<Response<NoticeMsgStruc>>() {
				});
		NoticeMsgStruc msg = resp.getBody().getResult();
		NoticeMsgInfo info = new NoticeMsgInfo();
		info.setAccept(msg.getSendType() == 1L ? "按用户组" : "按用户名");
		if (msg.getSendType() == 2L) {
			info.setAcceptUser(msg.getReceives());
		} else if (msg.getSendType() == 1L) {
			StringBuilder sb = new StringBuilder(1024);
			for (String group : msg.getRecGroups().split(",")) {
				sb.append(groupMap.get(group));
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			info.setAcceptGroup(sb.toString());
		}
		info.setContent(msg.getContent());
		info.setTitle(msg.getTitle());
		info.setOperator(msg.getOperater());
		info.setSentTime(DateUtils.format(msg.getGmtSended(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
		info.setExpiredTime(msg.getEffectHours() + "小时    （"
				+ DateUtils.format(msg.getGmtSended(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN) + "至"
				+ DateUtils.format(msg.getGmtExpired(), DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN) + "）");
		//如有資料則顯示至頁面，否則顯示無
		info.setMessagePush(msg.getMessagePush()!=null?msg.getMessagePush():"无");
		mav.addObject("msg", info);
		return mav;
	}

	@RequestMapping(value = "/modifySysMsgStatus")
	@ResponseBody
	public Object modifyMsgStatus(NoticeMsgStatusRequest req) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + modifyMsg, req);
			response.setStatus(1L);
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/createMsg")
	@ResponseBody
	public Object createMsg(@Valid NoticeMsgStruc msgStruc, Errors ve) throws Exception {

		OperateStatusResponse response = new OperateStatusResponse();
		if (ve.hasErrors()) {
			response.setStatus(0L);
			response.setMessage(ve.getFieldError().getDefaultMessage());
			return response;
		}
		if (msgStruc != null && msgStruc.getGmtSendedStr() != null && msgStruc.getGmtSendedStr().length() > 1) {
			Date come = DateUtils.parse(msgStruc.getGmtSendedStr(), DateUtils.DATETIME_FORMAT_PATTERN);
			if (come.before(new Date())) {
				response.setStatus(0L);
				response.setMessage("必须设置为当期时间以后的时间");
				return response;
			}
		}

		if (msgStruc.getGmtSendedStr() != null) {
			msgStruc.setGmtSended(DateUtils.parse(msgStruc.getGmtSendedStr(), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if (msgStruc.getReceives() != null) {
			//针对文件上传用户分割符问题的处理
			msgStruc.setReceives(msgStruc.getReceives().replace("；", ";"));
			String[] receivesArray = msgStruc.getReceives().split(";");
			String result = "";
			for (String receive : receivesArray) {
				if (receive != null && receive.trim().length() != 0) {
					result += receive + ";";
				}
			}
			if (result.length() != 0) {
				result = result.substring(0, result.length() - 1);
			}
			msgStruc.setReceives(result);

		}
		Long userId = RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();
		try {
			 Object res = httpClient.invokeHttpWithoutResultType(urlPath + createMsg, msgStruc, userId, account);
			 if(res != null){
			response.setStatus(1L);
			 }
		} catch (Exception e) {
			response.setStatus(0L);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/searchMsg")
	public ModelAndView searchMsg(@ModelAttribute("search") NoticeMsgSearchDto search) throws Exception {
		search.setPageNo(search.getPageNo() == null ? 1L : search.getPageNo());
		Pager pager = new Pager();
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);

		search.setTimeStart(DateUtils.parse(search.getTimeStartStr(), DateUtils.DATETIME_FORMAT_PATTERN));
		search.setTimeEnd(DateUtils.parse(search.getTimeEndStr(), DateUtils.DATETIME_FORMAT_PATTERN));
		Response<List<NoticeMsgStruc>> resp = httpClient.invokeHttp(urlPath + queryList, search, pager,
				new TypeReference<Response<List<NoticeMsgStruc>>>() {
				});
		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());

		if (search.getMsgType() == null) {
			search.setMsgType(0L);
		}
		ModelAndView mav = null;
		if (search.getMsgType() == 0L) {
			mav = new ModelAndView("/notice/msgSent");
		} else {
			mav = new ModelAndView("/notice/msgSending");
		}
		mav.addObject("page", page);

		List<NoticeMsgStruc> list = resp.getBody().getResult();
		Date current = new Date();
		for (NoticeMsgStruc msgStruc : list) {
			msgStruc.setGmtSendedStr(DateUtils.format(msgStruc.getGmtSended(),
					DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN));
			if (msgStruc.getGmtExpired() != null && current.getTime() > msgStruc.getGmtExpired().getTime()
					&& msgStruc.getStatus() != 3L) {
				msgStruc.setSendSatus(1L);
			} else {
				if (msgStruc.getStatus() == 2L) {
					msgStruc.setSendSatus(0L);
				} else if (msgStruc.getStatus() == 3L) {
					msgStruc.setSendSatus(2L);
				}
			}
		}
		mav.addObject("list", list);
		mav.addObject("search", search);
		mav.addObject("cate2Name", "goSysMsgManager");
		return mav;
	}
}
