package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.Map;

import com.winterframework.firefrog.game.web.dto.ECIssueCheckReponse;
import com.winterframework.firefrog.game.web.dto.GetIssueDrawResult;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;


/** 
* @ClassName: IECProcessesService 
* @Description: 开奖中心Service接口 
*  
*/
public interface IECProcessesService {

	public String receivedLotteryAwardNumber(Map<String, String> request,String reqPath) throws Exception;
	
	public String receivedChangeLotteryAwardNumber(Map<String, String> request,String reqPath) throws Exception;
	
	public String receivedAwardNumberBeforeSaleTime(Map<String, String> request,String reqPath) throws Exception;

	public String issueCheckEC(Map<String, String> request, String reqPath);

	public String queryCheckEC(Map<String, String> params, String string);

	public String getIssueDrawResult(Request<GetIssueDrawResult> request, String reqPath);
	void receivedBallResult(Long lotteryId, String webIssueCode, String ball, Date receiceTime) throws Exception;
}
