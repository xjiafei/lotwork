package com.winterframework.firefrog.game.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockSelector;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockService;
import com.winterframework.firefrog.game.lock.config.mongo.service.LotteryLockService;
import com.winterframework.firefrog.game.web.dto.LockServiceReqeust;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;

@Controller("lockController")
@RequestMapping("/game")
public class LockController {

	private static final Logger log = LoggerFactory.getLogger(LockController.class);

	@Autowired
	private LockSelector selector;

	@RequestMapping(value = "/nudoLock")
	@ResponseBody
	public Response<Object> nudoLockService(@RequestBody @ValidRequestBody Request<LockServiceReqeust> request)
			throws Exception {
		log.info("封锁变价撤销。。。");

		Response<Object> response = new Response<Object>(request);
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long lotteryid = request.getBody().getParam().getLotteryId();
		Long issueCode = request.getBody().getParam().getIssueCode();
		Long userId = request.getBody().getParam().getUserId();
		Long orderId = request.getBody().getParam().getOrderId();

		try {
			LotteryLockService lockService = selector.getRealService(99401L);
			if (lockService != null) {
				lockService.undoLock(lotteryid, issueCode, userId, orderId);
			}

		} catch (Exception e) {
			log.error("封锁变价撤销失败...", e);
			head.setStatus(1L);//失败
		}
		log.info("封锁变价撤销结束...");

		response.setHead(head);
		return response;
	}

	@RequestMapping(value = "/undoGamePlanLock")
	@ResponseBody
	public Response<Object> nudoGamePlanLockService(@RequestBody @ValidRequestBody Request<LockServiceReqeust> request)
			throws Exception {
		log.info("封锁变价预约追号撤销。。。");

		Response<Object> response = new Response<Object>(request);
		ResponseHeader head = ResponseHeader.createReponseHeader(request.getHead());

		Long planId = request.getBody().getParam().getLotteryId();
		Long issueCode = request.getBody().getParam().getIssueCode();

		try {
			LotteryLockService lockService = selector.getRealService(99401L);
			if (lockService != null) {
				lockService.undoGamePlanDetail(issueCode, planId);
			}

		} catch (Exception e) {
			log.error("封锁变价预约追号撤销失败...", e);
			head.setStatus(1L);//失败
		}
		log.info("封锁变价预约追号撤销结束...");

		response.setHead(head);
		return response;
	}

}
