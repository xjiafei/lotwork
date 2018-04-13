package com.winterframework.firefrog.game.web.controller;

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
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.jbyl.IBaseOmissionService;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionRequest;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;
import com.winterframework.firefrog.game.web.dto.QueryTrendChartRequest;
import com.winterframework.firefrog.game.web.dto.QueryTrendChartResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameTrendChartController 
* @Description: 走势数据图表Controller 
* @author David 
* @date 2013-7-22 上午9:37:52 
*  
*/
@Controller("gameTrendChartController")
@RequestMapping("/game")
public class GameTrendChartController {

	private Logger log = LoggerFactory.getLogger(GameTrendChartController.class);
	
	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService drawService;
	
	@Resource(name = "baseOmissionServiceImpl")
	private IBaseOmissionService baseOmissionService;

	/**
	 * 
	* @Title: queryAssistAction 
	* @Description: 4.41 投注辅助图表查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAssistAction")
	@ResponseBody
	public Response<QueryAssistActionResponse> queryAssistAction(
			@RequestBody @ValidRequestBody Request<QueryAssistActionRequest> request) throws Exception {

		Response<QueryAssistActionResponse> response = new Response<QueryAssistActionResponse>(request);
		try {
			if (null != request.getBody()) {
				QueryAssistActionRequest actionRequest = request.getBody().getParam();
				QueryAssistActionResponse result = new QueryAssistActionResponse();
				// 查询期数
				int num = actionRequest.getCount() == null ? 20 : actionRequest.getCount();
				List<BaseChartStruc> list = baseOmissionService.queryBaseChartStruc(actionRequest.getLotteryid(),
						actionRequest.getGameGroupCode(), actionRequest.getGameSetCode(),
						actionRequest.getBetMethodCode(), num);
				result.setList(list);
				response.setResult(result);
			}

		} catch (Exception e) {

			log.error("投注辅助图表查询错误", e);
			throw e;
		}

		return response;
	}
	
	/** 
	* @Title: queryTrendCharts 
	* @Description: 查询走势表
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryTrendCharts")
	@ResponseBody
	public Response<QueryTrendChartResponse> queryTrendCharts(
			@RequestBody @ValidRequestBody Request<QueryTrendChartRequest> request) throws Exception {
		log.info("开始查询走势表......");
		Response<QueryTrendChartResponse> response = new Response<QueryTrendChartResponse>(request);

		try {
			
			if(null != request.getBody()){
				
				
				QueryTrendChartResponse result = new QueryTrendChartResponse();
				QueryTrendChartRequest queryTrendChartRequest = request.getBody().getParam();
				// 查询期数
				int num = 0; 
				
				List<GameDrawResult> list = null; 
				if(queryTrendChartRequest.getType() == 1){
					num = queryTrendChartRequest.getIssue().intValue();
					
					list = drawService.getAllByLotteryIdAndCountIssue(queryTrendChartRequest.getLotteryId(), num);
				}else{
					// 根据日期信息查询
					Date startDate = DataConverterUtil.convertLong2Date(queryTrendChartRequest.getStartDate());
					Date endDate = DataConverterUtil.convertLong2Date(queryTrendChartRequest.getEndDate());
					list = drawService.getDrawResultByDate(queryTrendChartRequest.getLotteryId(), startDate, endDate);
					num = list.size();
					
				}
				
				BaseTrendChartStruc chartStruc = baseOmissionService.queryTrendCharts(list,queryTrendChartRequest.getLotteryId(), queryTrendChartRequest.getGameGroupCode(), queryTrendChartRequest.getIsGeneral(), num);
				result.setBaseTrendChartStrucs(chartStruc);
				
				response.setResult(result);
			}
			
		} catch (Exception e) {
			
			log.error("queryTrendCharts Error!", e);
			
			throw e;
		}
		
		log.info("查询走势表完成。");
		return response;
	}

	
}
