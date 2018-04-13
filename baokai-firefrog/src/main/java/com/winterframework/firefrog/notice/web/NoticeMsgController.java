package com.winterframework.firefrog.notice.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.service.INoticeMsgService;
import com.winterframework.firefrog.notice.web.dto.DTOConverter;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgDetailRequest;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgStatusRequest;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: NoticeMsgController 
* @Description: 通知消息后台接口
* @author Tod
* @date 2013-11-19 下午2:33:37 
*
 */
@Controller("noticeMsgController")
@RequestMapping("/noticeAdmin")
public class NoticeMsgController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeMsgController.class);
	@Autowired	
	private RabbitTemplate rabbitTemplate;
	@Resource(name = "noticeMsgServiceImpl")
	private INoticeMsgService msgService;

	/**
	 * 
	* @Title: modifyMsgStatus 
	* @Description: 修改消息状态
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifySysMsgStatus")
	public @ResponseBody
	Response<Object> modifyMsgStatus(@RequestBody Request<NoticeMsgStatusRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		NoticeMsgStatusRequest req = request.getBody().getParam();
		try {
			msgService.modifyMsgStatus(req.getId(), req.getStatus());
		} catch (Exception e) {
			logger.error("modifySysMsgStatus error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: createMsg 
	* @Description: 发送系统消息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createSysMsg")
	public @ResponseBody
	Response<Object> createMsg(@RequestBody Request<NoticeMsgStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		NoticeMsgStruc struc = request.getBody().getParam();
		String account = request.getHead().getUserAccount();
		struc.setOperater(account);
		try {
			msgService.saveMsg(DTOConverter.transDto2NoticeMsg(struc),rabbitTemplate);
		} catch (Exception e) {
			logger.error("createSysMsg error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryMsgDetail 
	* @Description: 查询系统消息详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySysMsgDetail")
	public @ResponseBody
	Response<NoticeMsgStruc> queryMsgDetail(@RequestBody Request<NoticeMsgDetailRequest> request) throws Exception {
		Response<NoticeMsgStruc> response = new Response<NoticeMsgStruc>(request);
		NoticeMsgDetailRequest req = request.getBody().getParam();
		try {
			NoticeMsg msg = msgService.queryMsgDetail(req.getId());
			response.setResult(DTOConverter.transNoticeMsg2Dto(msg));
		} catch (Exception e) {
			logger.error("querySysMsgDetail error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询系统消息列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySysMsgList")
	public @ResponseBody
	Response<List<NoticeMsgStruc>> queryList(@RequestBody Request<NoticeMsgSearchDto> request) throws Exception {
		Response<List<NoticeMsgStruc>> response = new Response<List<NoticeMsgStruc>>(request);
		PageRequest<NoticeMsgSearchDto> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("GMT_CREATED DESC");
		try {
			Page<NoticeMsg> page = msgService.queryMsg(pageRequest);
			List<NoticeMsgStruc> list = new ArrayList<NoticeMsgStruc>();
			for (NoticeMsg msg : page.getResult()) {
				list.add(DTOConverter.transNoticeMsg2Dto(msg));
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("querySysMsgList error.", e);
			throw e;
		}
		return response;
	}
}
