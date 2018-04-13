package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.entity.SellingStatus;
import com.winterframework.firefrog.game.service.IBetMethodSellingStatusService;
import com.winterframework.firefrog.game.service.ILotterySellingStatusService;
import com.winterframework.firefrog.game.web.dto.BetMethodStatusStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.SellingStatusCheckRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusCheckResponse;
import com.winterframework.firefrog.game.web.dto.SellingStatusModifyRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusModifyResponse;
import com.winterframework.firefrog.game.web.dto.SellingStatusPublishRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusPublishResponse;
import com.winterframework.firefrog.game.web.dto.SellingStatusQueryRequest;
import com.winterframework.firefrog.game.web.dto.SellingStatusQueryResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: SellingStatusController 
* @Description: 销售状态Controller 
* @author Denny 
* @date 2013-8-20 下午2:07:10 
*  
*/
@Controller("sellingStatusController")
@RequestMapping("/game")
public class SellingStatusController {
	private Logger log = LoggerFactory.getLogger(SellingStatusController.class);
	
	@Resource(name = "lotterySellingStatusServiceImpl")
	private ILotterySellingStatusService lotterySellingStatusServiceImpl;

	@Resource(name = "betMethodSellingStatusServiceImpl")
	private IBetMethodSellingStatusService betMethodSellingStatusServiceImpl;
	
	/** 
	* @Title: querySellingStatus 
	* @Description: 4.37 查询销售状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/querySellingStatus")
	@ResponseBody
	public Response<SellingStatusQueryResponse> querySellingStatus(
			@RequestBody @ValidRequestBody Request<SellingStatusQueryRequest> request) throws Exception {

		log.debug("开始查询销售状态......");
		Response<SellingStatusQueryResponse> response = new Response<SellingStatusQueryResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();

		List<SellingStatus> sellingStatusList = new ArrayList<SellingStatus>();
		SellingStatusQueryResponse result = new SellingStatusQueryResponse();
		List<BetMethodStatusStruc> betMethodStatusStrucList = new ArrayList<BetMethodStatusStruc>();
		int status;
		int checkStatus;
		Date takeOffTime;
		BetMethodStatusStruc bmss = new BetMethodStatusStruc();
		try {
			status = lotterySellingStatusServiceImpl.queryLotterySellingStatus(lotteryid);
			checkStatus = lotterySellingStatusServiceImpl.queryLotteryCheckStatus(lotteryid);
			takeOffTime = lotterySellingStatusServiceImpl.queryLotterySellingTakeOffTime(lotteryid);
			log.info("takeOffTime===="+takeOffTime);
			sellingStatusList = betMethodSellingStatusServiceImpl.queryBetMethodSellingStatus(lotteryid);
			for (SellingStatus ss : sellingStatusList) {
				bmss = DTOConvert.sellingStatus2BetMethodStatusStruc(ss);
				betMethodStatusStrucList.add(bmss);
			}
			result.setBetMethodStatusStruc(betMethodStatusStrucList);
			result.setStatus(status);
			result.setCheckStatus(checkStatus);
			result.setTakeOffTime(takeOffTime);
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询销售状态异常 ", e);
			throw e;
		}

		log.debug("查询销售状态完成。");
		return response;
	}
	
	/** 
	* @Title: modifySellingStatus 
	* @Description: 4.38 修改销售状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/modifySellingStatus")
	@ResponseBody
	public Response<SellingStatusModifyResponse> modifySellingStatus(
			@RequestBody @ValidRequestBody Request<SellingStatusModifyRequest> request) throws Exception {

		log.debug("开始修改销售状态......");
		Response<SellingStatusModifyResponse> response = new Response<SellingStatusModifyResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();
		int lotterySellingStatus = request.getBody().getParam().getStatus();
		int lotteryCheckStatus = request.getBody().getParam().getCheckStatus();
		
		List<BetMethodStatusStruc> betMethodStatusModifyList = request.getBody().getParam().getBetMethodStatusStruc();
		Date takeOffTime = null;
		List<SellingStatus> sellingStatusList = new ArrayList<SellingStatus>();
		SellingStatus ss = new SellingStatus();
		try {
			for (BetMethodStatusStruc bmms : betMethodStatusModifyList) {
				ss = DTOConvert.betMethodStatusStruc2SellingStatus(bmms, lotteryid);
				sellingStatusList.add(ss);
			}
			if(request.getBody().getParam().getTakeOffTime() != null){
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				takeOffTime = sdf.parse(request.getBody().getParam().getTakeOffTime());
				if(now.before(takeOffTime)){
					lotterySellingStatus = lotterySellingStatus == 0 ? 1:0;
				}else{
					takeOffTime = null;
				}
			}
			
			lotterySellingStatusServiceImpl.modifyLotterySellingStatus(lotteryCheckStatus, lotterySellingStatus, lotteryid,takeOffTime);
			betMethodSellingStatusServiceImpl.modifyBetMethodSellingStatus(sellingStatusList, lotteryid);
		} catch (Exception e) {
			log.error("修改销售状态异常 ", e);
			throw e;
		}

		log.debug("修改销售状态完成。");
		return response;
	}
	
	/** 
	* @Title: checkSellingStatus 
	* @Description: 4.39 审核销售状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/checkSellingStatus")
	@ResponseBody
	public Response<SellingStatusCheckResponse> checkSellingStatus(
			@RequestBody @ValidRequestBody Request<SellingStatusCheckRequest> request) throws Exception {

		log.debug("开始审核销售状态......");
		Response<SellingStatusCheckResponse> response = new Response<SellingStatusCheckResponse>(request);
		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long auditType = request.getBody().getParam().getAuditType();

		try {
			lotterySellingStatusServiceImpl.checkLotterySellingStatus(lotteryid, auditType);
			betMethodSellingStatusServiceImpl.checkBetMethodSellingStatus(lotteryid, auditType);
		} catch (Exception e) {
			log.error("审核销售状态异常 ", e);
			throw e;
		}

		log.debug("审核销售状态完成。");
		return response;
	}
	
	/** 
	* @Title: publishSellingStatus 
	* @Description: 4.40 发布销售状态
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/publishSellingStatus")
	@ResponseBody
	public Response<SellingStatusPublishResponse> publishSellingStatus(
			@RequestBody @ValidRequestBody Request<SellingStatusPublishRequest> request) throws Exception {

		log.debug("开始查询销售状态......");
		Response<SellingStatusPublishResponse> response = new Response<SellingStatusPublishResponse>(request);
		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long publishType = request.getBody().getParam().getPublishType();

		try {
			lotterySellingStatusServiceImpl.publishLotterySellingStatus(lotteryid, publishType);
			betMethodSellingStatusServiceImpl.publishBetMethodSellingStatus(lotteryid, publishType);
		} catch (Exception e) {
			log.error("查询销售状态异常 ", e);
			throw e;
		}

		log.debug("查询销售状态完成。");
		return response;
	}
	
	/** 
	* @Title: queryValidBetMethods 
	* @Description: 查询彩种有效投注列表 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryValidBetMethods")
	@ResponseBody
	public Response<BetMethodValidListQueryResponse> queryValidBetMethods(
			@RequestBody @ValidRequestBody Request<BetMethodValidListQueryRequest> request) throws Exception {

		log.debug("开始查询彩种有效玩法列表......");
		Response<BetMethodValidListQueryResponse> response = new Response<BetMethodValidListQueryResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();

		BetMethodValidListQueryResponse result = new BetMethodValidListQueryResponse();
		List<String> temp=new ArrayList<String>();
		
		try {
			List<GameBettypeStatus> list=betMethodSellingStatusServiceImpl.queryValidBetMethods(lotteryid);
			for(GameBettypeStatus status: list){
				StringBuilder sb=new StringBuilder();
				sb.append(status.getGameGroupCode()).append(",").append(status.getGameSetCode()).append(",").append(status.getBetMethodCode());
				temp.add(sb.toString());
			}
			result.setValidMethods((String[]) temp.toArray(new String[]{}));
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询彩种有效玩法列表异常 ", e);
			throw e;
		}

		log.debug("查询彩种有效玩法列表完成。");
		return response;
	}
	
}
