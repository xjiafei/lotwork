package com.winterframework.firefrog.game.web.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.service.IECProcessesService;
import com.winterframework.firefrog.game.service.ec.utils.ECUtils;
import com.winterframework.firefrog.game.web.dto.GetIssueDrawResult;
import com.winterframework.firefrog.game.web.dto.ZKBallRequest;
import com.winterframework.firefrog.game.web.dto.ZKBallResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: ECController 
* @Description: 开奖中心controller
* @author charles 
* @date 2014-5-1 下午3:27:57 
*  
*/
@Controller("ECController")
@RequestMapping("/game")
public class ECController {

	private Logger log = LoggerFactory.getLogger(ECController.class);

	@Resource(name = "eCProcessesServiceImpl")
	private IECProcessesService eCProcessesServiceImpl;

	/** 
	* @Title: receNFEC 
	* @Description: 获取开奖号码
	* @param request
	* @return
	* @throws Exception
	*/
	/**
	 * wget --header "Content-Type: application/firefrog" --post-data "customer=a4b609491bd9daf3b86ef278657ff7a5&lottery=PLW&issue=2014199&time=20140726203517&number=25137&verifiedTime=19700101080000&earliestTime=20140726203513&stopSaleTime=19700101080000&drawingTime=20140726200000&recordId=574698&safestr=1e5b0b9156bf24dc0328eccab684b8b7" http://em.ff/game/receNFEC
	 * @param data
	 * @param rep
	 * @throws Exception
	 */

	@RequestMapping(value = "/receNFEC")
	public void receNumberFromEC(@RequestBody String data, HttpServletResponse rep) throws Exception {

		String status = new String();

		String disposeMemo = "EC开奖通知";

		log.info(disposeMemo + "收到," + data);
		if(data.contains("---------------------------")){
			log.error("收到脏数据脏数据");
			//咱数据处理
			return;
		}

		Map<String, String> params = ECUtils.getMapFormRequestParams(data);

		try {
			status = eCProcessesServiceImpl.receivedLotteryAwardNumber(params, "/game/receNFEC");

		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
		}

		log.info(disposeMemo + "成功,返回状态: " + status);

		rep.getWriter().write(status);

	}
	@ResponseBody
	@RequestMapping(value = "/receZK")
	public Response<ZKBallResponse> receNumberFromZK(@RequestBody @ValidRequestBody Request<ZKBallRequest> request) throws Exception {

		
		Long lotteryId = request.getBody().getParam().getLotteryId();
		Long issueCode = request.getBody().getParam().getIssueCode();
		String ball= request.getBody().getParam().getBall();
		Date receiceTime=request.getBody().getParam().getSendTime();
		String webIssueCode=request.getBody().getParam().getWebIssueCode();
		log.info("getBall:" + lotteryId + "|" + issueCode);
		Response<ZKBallResponse> response = new Response<ZKBallResponse>(request);
		ZKBallResponse res = new ZKBallResponse();
		
		try {
			eCProcessesServiceImpl.receivedBallResult(lotteryId,webIssueCode,ball,receiceTime);
			res.setSuccess(0);
//			status = eCProcessesServiceImpl.receivedLotteryAwardNumber(params, "/game/receNFEC");
		} catch (Exception e) {
			res.setSuccess(1);
			log.error( "自开奖开奖号码推送失败..."+lotteryId+"|"+issueCode, e);
		}
		response.setResult(res);
		return response;

	}

	/** 
	* @Title: receUNFEC 
	* @Description: 获取变更开奖号码
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/receUNFEC")
	public void receUpdateNumberFromEC(@RequestBody String data, HttpServletResponse rep) throws Exception {

		//ECReceivesNumbersRequest request
		String disposeMemo = "EC获取变更开奖号码";
		String status = new String();

		log.info(disposeMemo + "收到," + data);

		Map<String, String> params = ECUtils.getMapFormRequestParams(data);

		try {
			status = eCProcessesServiceImpl.receivedChangeLotteryAwardNumber(params, "/game/receUNFEC");

		} catch (Exception e) {
			log.error(disposeMemo + "变更失败...", e);
		}

		if (status != null) {
			log.info(disposeMemo + "变更成功,返回状态: " + status);
		}

		rep.getWriter().write(status);
	}
	
	/** 
	* @Title: receNFECBST 
	* @Description: 提前截止销售获取变更开奖号码
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/receNFECBST")
	public void receNumberFromECBeforeSaleTime(@RequestBody String data, HttpServletResponse rep) throws Exception {
		//ECReceivesNumbersRequest request
		String disposeMemo = "EC获取变更开奖号码";
		String status = new String();

		log.info(disposeMemo + "收到," + data);

		Map<String, String> params = ECUtils.getMapFormRequestParams(data);

		try {
			status = eCProcessesServiceImpl.receivedAwardNumberBeforeSaleTime(params, "/game/receNFECBST");

		} catch (Exception e) {
			log.error(disposeMemo + "变更失败...", e);
		}

		if (status != null) {
			log.info(disposeMemo + "变更成功,返回状态: " + status);
		}

		rep.getWriter().write(status);
	}
	
	/** 
	* @Title: getIssueDrawResult 
	* @Description: 从ec获取开奖结果
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getIssueDrawResult")
	public String getIssueDrawResult(@RequestBody Request<GetIssueDrawResult> request) throws Exception {
		//ECReceivesNumbersRequest request
		String disposeMemo = "商户从EC获取开奖号码";
		String result = new String();

		log.info(disposeMemo + "开始执行" );

		try {
			result = eCProcessesServiceImpl.getIssueDrawResult(request,"http://em.ff/game/receNFEC");

		} catch (Exception e) {
			log.error(disposeMemo + "失败...", e);
		}

		return result;
	}


	/** 
	* @Title: issueCheckEC 
	* @Description: 奖期校对
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/issueCheckEC")
	@ResponseBody
	public void issueCheckEC(@RequestBody String data,HttpServletResponse rep) throws Exception {
		//ECReceivesNumbersRequest request
		String disposeMemo = "EC获取奖期校对";
		String response = new String();
		
		if(StringUtils.isEmpty(data)){
//			ECIssueCheckReponse result=new ECIssueCheckReponse();
//			result.setIsSuccess(false);
//			result.setMessage("");
//			result.setMessage("N8");//调用方式错误。必须为post
			
			rep.getWriter().write("N8");
		}
		

		log.info(disposeMemo + "收到," + data);

		Map<String, String> params = ECUtils.getMapFormRequestParams(data);

		try {
			response = eCProcessesServiceImpl.issueCheckEC(params, "/game/issueCheckEC");

		} catch (Exception e) {
			log.error(disposeMemo + "获取失败...", e);
		}

		rep.getWriter().write(response);
	}
	
	/** 
	* @Title: queryCheckEC 
	* @Description: ec校验查询进程
	* @param data
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryCheckEC")
	@ResponseBody
	public String queryCheckEC(@RequestBody String data) throws Exception {
		//ECReceivesNumbersRequest request
		String disposeMemo = "EC校验商户查询进程";
		
		String message="N16383";//查询进程真实有效
		
		
		if(StringUtils.isEmpty(data)){
			return "N8";//不是post提交
		}
		

		log.info(disposeMemo + "收到," + data);

		Map<String, String> params = ECUtils.getMapFormRequestParams(data);

		try {
			message = eCProcessesServiceImpl.queryCheckEC(params, "/game/queryCheckEC");

		} catch (Exception e) {
			message="N16384";//查询进程不存在
			log.error(disposeMemo + "获取失败...", e);
		}

		return message;
	}

}
