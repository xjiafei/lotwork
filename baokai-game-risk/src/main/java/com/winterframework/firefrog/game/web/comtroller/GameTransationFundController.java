package com.winterframework.firefrog.game.web.comtroller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.exception.GameFundException;
import com.winterframework.firefrog.game.service.IGameRiskService;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.QueryTransactionRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryTransactionRecordResponse;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;
import com.winterframework.firefrog.game.web.dto.TORiskResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameTransationFundController 
* @Description: 游戏和资金风控模块 
* @author Richard & David
* @date 2013-12-12 下午1:59:45 
*
 */
@Controller("GameTransationFundController")
@RequestMapping("/gameRisk")
public class GameTransationFundController {

	private static final Logger log = LoggerFactory.getLogger(GameTransationFundController.class);

	@Resource(name = "gameRiskServiceImpl")
	private IGameRiskService service; 

	/** 
	* @Title: integration 
	* @Description:操作投注冻结、投注扣款、派奖、撤销统一入口
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping("/integration")
	@ResponseBody
	public Response<TORiskResponse> integration(@RequestBody @ValidRequestBody Request<TORiskRequest> request)
			throws Exception {

		log.info("游戏资金操作统一入口方法开始");
		TORiskResponse res = new TORiskResponse();
		Response<TORiskResponse> response = new Response<TORiskResponse>(request);
		try {
			TORiskRequest coin = request.getBody().getParam();
			service.integrationNew(coin);
			res.setResultStatus(0L);
			log.info("游戏资金操作成功");
		}catch (GameFundException e) { 
			log.error("审核调用资金异常", e);
			res.setResultStatus(1L);
			res.setExceptionMessage("验奖异常：" + e.getMessage());
		} catch (Exception e) { 
			log.error("审核调用资金异常", e);
			res.setResultStatus(1L);
			res.setExceptionMessage("验奖异常：" + e.getMessage());
		}
		response.setResult(res);
		return response;
	}

	/**
	 * 
	* @Title: queryTransactionRecord 
	* @Description:5.6.1	交易记录查询(REI001) 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryTransactionRecord")
	@ResponseBody
	public Response<QueryTransactionRecordResponse> queryTransactionRecord(
			@RequestBody @ValidRequestBody Request<QueryTransactionRecordRequest> request) throws Exception {

		log.info("开始进入交易记录查询接口");

		Response<QueryTransactionRecordResponse> response = new Response<QueryTransactionRecordResponse>();

		try {
			@SuppressWarnings("unused")
			QueryTransactionRecordRequest qr = request.getBody().getParam();

			List<FundTransactionStruc> list = null;

			QueryTransactionRecordResponse result = new QueryTransactionRecordResponse();
			result.setFundTransactionStrucs(list);
			response.setResult(result);

		} catch (Exception e) {
			log.error("queryTransactionRecord error:", e);
			throw e;
		}

		return response;

	}

}
