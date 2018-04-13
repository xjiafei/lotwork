package com.winterframework.firefrog.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.service.IMessageService;
import com.winterframework.firefrog.user.web.dto.ControllerDTOConverter;
import com.winterframework.firefrog.user.web.dto.MessageDelRequest;
import com.winterframework.firefrog.user.web.dto.MessageIdsDTO;
import com.winterframework.firefrog.user.web.dto.MessageListResponseDTO;
import com.winterframework.firefrog.user.web.dto.MessageMarkAndDeleteRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageQueryDetailRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageQueryRequest;
import com.winterframework.firefrog.user.web.dto.MessageRecipient;
import com.winterframework.firefrog.user.web.dto.MessageReplyRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageSendRequest;
import com.winterframework.firefrog.user.web.dto.MsgStrucResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * @Description: 站内信管理的Controller
 * @author Denny
 * @date 2013-4-22 下午2:39:15
 * 
 */
@Controller
@RequestMapping("/user/message")
public class MessageController {

	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Resource(name= "messageServiceImpl")
	private IMessageService messageService;
	@Resource(name = "globalSensitiveWordServiceImpl")
	private GlobalSensitiveWordService word;

	@RequestMapping(value = "/deleteMessage")
	public @ResponseBody
	Response<Object> deleteMessage(@RequestBody Request<MessageDelRequest> request) throws Exception {
		Response<Object> resp = new Response<Object>(request);
		try {
			messageService.deleteMessage(request.getBody().getParam().getId());
		} catch (Exception e) {
			log.error("deleteMessage error.", e);
			throw e;
		}
		return resp;
	}

	@RequestMapping(value = "/queryMessageAll")
	public @ResponseBody
	Response<List<MsgStrucResponse>> queryMessageAll(@RequestBody Request<MessageQueryRequest> request)
			throws Exception {
		Response<List<MsgStrucResponse>> response = new Response<List<MsgStrucResponse>>(request);
		PageRequest<MessageQueryRequest> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("SEND_TIME DESC");
		try {
			Page<MessageTopic> page = messageService.queryMessageList(pageRequest);
			List<MsgStrucResponse> list = new ArrayList<MsgStrucResponse>();
			for (MessageTopic msg : page.getResult()) {
				list.add(ControllerDTOConverter.message2MsgStrucResponse(msg, -1L));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			log.error("queryMessageAll error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 加载站内信列表接口
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryMessageList")
	@ResponseBody
	public Response<MessageListResponseDTO> queryMessageList(@RequestBody Request request) throws Exception {

		log.debug("开始加载站内信列表......");

		Response<MessageListResponseDTO> response = new Response<MessageListResponseDTO>(request);
		long userId = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<Long> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(userId);
		pr.setSortColumns("type,gmtModified desc");
		Page<MessageTopic> page = null;
		List<MsgStrucResponse> listResponse1 = null;
		MsgStrucResponse msr = null;
		try {
			page = messageService.getMessageList(pr);
			List<MessageTopic> list = page.getResult();
			int total = page.getTotalCount();

			ArrayList<MsgStrucResponse> listResponse = new ArrayList<MsgStrucResponse>();
			if (list != null && list.size() > 0) {
				for (MessageTopic mt : list) {
					msr = ControllerDTOConverter.message2MsgStrucResponse(mt, userId);
					listResponse.add(msr);
				}

				int unreadCnt = messageService.getUnreadMessagesNumber(userId);

				ResultPager resultPage = new ResultPager();
				resultPage.setStartNo(sNo);
				resultPage.setEndNo(eNo);
				resultPage.setTotal(total);

				MessageListResponseDTO messageListResponse = new MessageListResponseDTO();
				messageListResponse.setReceives(listResponse);
				messageListResponse.setUnreadCnt(unreadCnt);

				response.setResult(messageListResponse);
				response.setResultPage(resultPage);

			}
		} catch (Exception e) {
			log.error("加载站内信列表异常 ", e);
			throw e;
		}

		log.debug("加载站内信列表完成。");
		return response;
	}

	/**
	 * 加载未读站内信列表接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryUnReadMessageList")
	@ResponseBody
	public Response<MessageListResponseDTO> queryUnReadMessageList(@RequestBody Request<Object> request)
			throws Exception {
		Response<MessageListResponseDTO> response = new Response<MessageListResponseDTO>(request);
		long userId = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<Long> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(userId);
		pr.setSortColumns("type,gmtModified desc");
		Page<MessageTopic> page = null;
		List<MsgStrucResponse> listResponse = null;
		MsgStrucResponse msr = null;
		try {
			page = messageService.getUnReadMessageList(pr);
			List<MessageTopic> list = page.getResult();
			int total = page.getTotalCount();

			listResponse = new ArrayList<MsgStrucResponse>();
			if (list != null && list.size() > 0) {
				for (MessageTopic mt : list) {
					msr = ControllerDTOConverter.message2MsgStrucResponse(mt, userId);
					listResponse.add(msr);
				}

				ResultPager resultPage = new ResultPager();
				resultPage.setStartNo(sNo);
				resultPage.setEndNo(eNo);
				resultPage.setTotal(total);

				MessageListResponseDTO messageListResponse = new MessageListResponseDTO();
				messageListResponse.setReceives(listResponse);

				response.setResult(messageListResponse);
				response.setResultPage(resultPage);

			}
		} catch (Exception e) {
			log.error("加载站内信列表异常 ", e);
			throw e;
		}

		log.debug("加载站内信列表完成。");
		return response;

	}

	/**
	 * 标记消息为已读
	 * 
	 * @param msgIds
	 * @return
	 */
	@RequestMapping(value = "/markRead")
	@ResponseBody
	public Response<MessageIdsDTO> markRead(@RequestBody Request<MessageIdsDTO> request) throws Exception {

		log.info("开始标记消息为已读......");
		Response<MessageIdsDTO> response = new Response<MessageIdsDTO>(request);
		try {
			MessageMarkAndDeleteRequestDTO[] idsAndFlagParam = request.getBody().getParam().getMsgIds();
			messageService.markRead(idsAndFlagParam);

		} catch (Exception e) {
			log.error("标记消息为已读出现异常：", e);
			throw e;
		}

		log.info("成功标记...");
		return response;
	}

	/**
	 * 删除站内信
	 * 
	 * @param msgIds
	 * @return
	 */
	@RequestMapping(value = "/deleteMessages")
	@ResponseBody
	public Response<MessageIdsDTO> deleteMessages(@RequestBody Request<MessageIdsDTO> request) throws Exception {

		log.info("开始删除站内信......");
		Response<MessageIdsDTO> response = new Response<MessageIdsDTO>(request);
		try {
			MessageMarkAndDeleteRequestDTO[] idsAndFlagParam = request.getBody().getParam().getMsgIds();
			messageService.deleteMessages(idsAndFlagParam);

		} catch (Exception e) {
			log.error("删除站内信出现异常：", e);
			throw e;
		}

		log.info("成功删除...");

		return response;

	}

	/**
	 * 查询站内信详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryMessageDetail")
	@ResponseBody
	public Response<MessageListResponseDTO> queryMessageDetail(
			@RequestBody @ValidRequestBody Request<MessageQueryDetailRequestDTO> request) throws Exception {

		log.info("开始查詢站内信詳細信息......");
		Response<MessageListResponseDTO> response = new Response<MessageListResponseDTO>(request);

		long userId = request.getHead().getUserId();
		long rootId = request.getBody().getParam().getRootId();

		try {
			List<Message> messageList = messageService.getMessageDetail(rootId, userId);
			if (messageList != null && messageList.size() > 0) {
				List<MsgStrucResponse> msgStrucResponseList = new ArrayList<MsgStrucResponse>();

				for (Message m : messageList) {
					msgStrucResponseList.add(ControllerDTOConverter.message2MsgStrucResponse(m, userId));
				}

				MessageListResponseDTO result = new MessageListResponseDTO();
				result.setReceives(msgStrucResponseList);
				response.setResult(result);

				//同时将主题更新为已读状态
				MessageMarkAndDeleteRequestDTO param = new MessageMarkAndDeleteRequestDTO();
				param.setId(rootId);
				if (userId == messageList.get(0).getSender().getId()) {
					param.setFlag(0);
				} else {
					param.setFlag(1);
				}

				MessageMarkAndDeleteRequestDTO[] idsAndFlagParam = { param };
				messageService.markRead(idsAndFlagParam);
			}
		} catch (Exception e) {
			log.error("查詢站内信詳細信息出现异常：", e);
			throw e;
		}

		log.info("查詢站内信詳細信息成功...");

		return response;

	}

	/**
	 * 
	* @Title: Message 
	* @Description: 发送站内信（包括单独发送和群发）
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public Object sendMessage(@RequestBody @ValidRequestBody Request<MessageSendRequest> request) throws Exception {
 
		Response<Object> response = new Response<Object>(request);
		if(!messageService.checkUserTo(request.getBody().getParam().getSendAccount(), request.getHead().getUserId(), request.getBody().getParam().getReceivesList())){
			return null;
		}
		MessageSendRequest messageRequest = request.getBody().getParam();
		MessageRecipient[] receivers = messageRequest.getReceivesList();
		String title = messageRequest.getTitle();
		String content = messageRequest.getContent();
		title = word.replace(title, SensitiveWord.Type.message);
		content = word.replace(content, SensitiveWord.Type.message);
		String senderName = messageRequest.getSendAccount();
		long userId = request.getHead().getUserId();

		log.info("发送站内信：title = " + title + " content = " + content + " senderName = " + senderName + " userId = "
				+ userId);

		try {
			if (receivers.length == 1) {
				log.info("发送单用户站内信");
				messageService.sendMessages(receivers[0].getReceiveId(), userId, senderName, title, content);
			} else {
				log.info("发送多用户站内信");
				long[] receiverIds = new long[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					receiverIds[i] = receivers[i].getReceiveId();
				}
				messageService.sendMessages(receiverIds, userId, senderName, title, content);
			}
		} catch (Exception e) {
			log.error("send message error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 回复消息
	 * 
	 * @param requestb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/replyMessage")
	@ResponseBody
	public Response<MessageReplyRequestDTO> replyMessage(
			@RequestBody @ValidRequestBody Request<MessageReplyRequestDTO> request) throws Exception {

		log.info("开始回复消息......");

		Response<MessageReplyRequestDTO> response = new Response<MessageReplyRequestDTO>(request);
		MessageReplyRequestDTO messageReplyRequest = request.getBody().getParam();
		long userId = request.getHead().getUserId();
		long topicId = messageReplyRequest.getRootId();
		long parentId = messageReplyRequest.getParentId();
		String content = messageReplyRequest.getContent();
		content = word.replace(content, SensitiveWord.Type.message);

		try {
			messageService.replyMessage(userId, topicId, parentId, content);
		} catch (Exception e) {
			log.error("reply message error: ", e);
			throw e;
		}

		log.info("回复消息完成。");
		return response;
	}

	@RequestMapping(value = "/queryMessageDetailForSys")
	@ResponseBody
	public Response<List<MsgStrucResponse>> queryMessageDetailForSys(
			@RequestBody @ValidRequestBody Request<MessageQueryDetailRequestDTO> request) throws Exception {
		Response<List<MsgStrucResponse>> response = new Response<List<MsgStrucResponse>>(request);
		long rootId = request.getBody().getParam().getRootId();
		try {
			List<Message> messageList = messageService.getMessageDetail(rootId);
			List<MsgStrucResponse> msgList = new ArrayList<MsgStrucResponse>();
			MsgStrucResponse msg = null;
			for (Message message : messageList) {
				msg = ControllerDTOConverter.message2MsgStrucResponse(message, 0L);
				msgList.add(msg);
			}
			response.setResult(msgList);
		} catch (Exception e) {
			log.error("queryMessageDetailForSys is error.", e);
			throw e;
		}
		return response;
	}

}
