package com.winterframework.firefrog.game.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.dao.vo.RiskOrderDetail;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.RiskOrderDetailStruc;
import com.winterframework.firefrog.game.web.dto.RiskOrderStruc;
import com.winterframework.firefrog.game.web.dto.SpiteOrderStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GameWarnOrderController 
* @Description: 风险/恶意记录 Controller
* @author Alan
* @date 2013-10-15 下午3:17:32 
*  
*/
@Controller("gameWarnOrderController")
@RequestMapping("/game")
public class GameWarnOrderController {

	private Logger logger = LoggerFactory.getLogger(GameWarnOrderController.class);

	@Resource(name = "gameWarnOrderServiceImpl")
	private IGameWarnOrderService gameWarnOrderService;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Resource(name = "gameRiskConfigDaoImpl")
	private IGameRiskConfigDao gameRiskConfigDaoImpl;

	/** 
	* @Title: queryGameWarnOrders 
	* @Description: 风险记录分页查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryGameWarnOrders")
	@ResponseBody
	public Response<GameWarnOrderQueryResponse> queryGameWarnOrders(
			@RequestBody @ValidRequestBody Request<GameWarnOrderQueryRequest> request) throws Exception {

		logger.info("query warn orders start...");
		Response<GameWarnOrderQueryResponse> response = new Response<GameWarnOrderQueryResponse>(request);
		long userId = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		GameWarnOrderQueryDTO queryDTO = new GameWarnOrderQueryDTO();
		queryDTO.setUserid(userId);
		queryDTO.setQueryParam(request.getBody().getParam());

		PageRequest<GameWarnOrderQueryDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);

		Page<RiskOrders> page = null;
		List<RiskOrders> orders = null;
		List<RiskOrderStruc> riskOrderList = new ArrayList<RiskOrderStruc>();
		GameWarnOrderQueryResponse result = new GameWarnOrderQueryResponse();
		try {
			page = gameWarnOrderService.queryGameWarnOrders(pr);
			orders = page.getResult();
			//获取审核配置信息
			GameRiskConfig config = request.getBody().getParam().getLotteryid() == null ? new GameRiskConfig()
					: gameRiskConfigDaoImpl.queryGameRiskConfig(request.getBody().getParam().getLotteryid());
			result.setRiskConfigStruc(DTOConvert.GameRiskConfig2QuerySeriesConfigRiskResponse(config));
			if (orders != null && orders.size() > 0) {
				for (RiskOrders risk : orders) {
					RiskOrderStruc struc = new RiskOrderStruc();
					struc.setAccount(risk.getAccount());
					struc.setCountWins(risk.getCountWins());
					struc.setIssueCode(risk.getIssueCode());
					struc.setIssueWinsRatio(risk.getIssueWinsRatio());
					struc.setLotteryid(risk.getLotteryid());
					struc.setLotteryName(risk.getLotteryName());
					struc.setMaxslipWins(risk.getMaxslipWins());
					struc.setOrderwarnContinuousWins(risk.getOrderwarnContinuousWins());
					struc.setContinuousWinsTimes(risk.getContinuousWinsTimes());
					struc.setUserId(risk.getUserid());
					try {
						GameIssueEntity issue = gameIssueServiceImpl.getGameIssue(risk.getLotteryid(),
								risk.getIssueCode());
						struc.setWebIssueCode(issue.getWebIssueCode());
					} catch (Exception e) {
						logger.error(risk.getLotteryid() + "第" + risk.getIssueCode() + "期奖期不存在", e);
					}
					List<RiskOrderDetailStruc> detailStrucs = new ArrayList<RiskOrderDetailStruc>();
					if (null != risk.getOrderDetails() && risk.getOrderDetails().size() > 0) {

						for (RiskOrderDetail orderDetail : risk.getOrderDetails()) {

							RiskOrderDetailStruc _struc = new RiskOrderDetailStruc();
							_struc.setId(orderDetail.getId());
							_struc.setChannelId(orderDetail.getChannelId());
							_struc.setChannelVersion(orderDetail.getChannelVersion());
							_struc.setIssueCode(orderDetail.getIssueCode());
							_struc.setLotteryid(orderDetail.getLotteryid());
							_struc.setMaxslipWins(orderDetail.getMaxslipWins());
							_struc.setOrderCode(orderDetail.getOrderCode());
							_struc.setParentType(orderDetail.getParentType());
							_struc.setSaleTime(DataConverterUtil.convertDate2Long(orderDetail.getSaleTime()));
							_struc.setIsoSaleTime(DateUtils.format(orderDetail.getSaleTime(),
									DateUtils.DATETIME_FORMAT_PATTERN));
							_struc.setSlipWinsratio(orderDetail.getSlipWinsratio());
							_struc.setStatus(orderDetail.getStatus());
							_struc.setTotamount(orderDetail.getTotamount());
							_struc.setTotwin(orderDetail.getTotwin());
							_struc.setUserId(orderDetail.getUserid());
							_struc.setWinsRatio(orderDetail.getWinsRatio());
							_struc.setCountWin(orderDetail.getCountWin());
							_struc.setNumberRecord(orderDetail.getNumberRecord());
							_struc.setApprUser(orderDetail.getApprUser());
							_struc.setApprTime(DataConverterUtil.convertDate2Long(orderDetail.getApprTime()));
							if (orderDetail.getApprTime() != null) {
								_struc.setApprTimeStr(dateFormat.format(orderDetail.getApprTime()));
							}
							_struc.setApprMemo(orderDetail.getApprMemo());
							_struc.setOrderId(orderDetail.getOrderId());

							detailStrucs.add(_struc);
						}
					}

					struc.setRiskOrderDetailStrucs(detailStrucs);

					riskOrderList.add(struc);
				}

			}
			result.setRiskOrderStruc(riskOrderList);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			logger.error("query warn orders error", e);
			throw e;
		}

		logger.info("query warn orders end...");
		return response;
	}

	/**
	 * 
	* @Title: queryGameSpiteOrders 
	* @Description: 恶意记录分页查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGameSpiteOrders")
	@ResponseBody
	public Response<GameSpiteOrderQueryResponse> queryGameSpiteOrders(
			@RequestBody Request<GameSpiteOrderQueryRequest> request) throws Exception {
		logger.info("query game spite orders start...");

		Response<GameSpiteOrderQueryResponse> response = new Response<GameSpiteOrderQueryResponse>();

		Long userid = request.getHead().getUserId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		GameSpiteOrderQueryRequestDTO queryDTO = new GameSpiteOrderQueryRequestDTO();
		queryDTO.setUserid(userid);

		queryDTO.setRequest(request.getBody().getParam());

		PageRequest<GameSpiteOrderQueryRequestDTO> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(queryDTO);

		Page<GameSpiteOperationsEntity> page = null;
		List<GameSpiteOperationsEntity> spites = null;
		List<SpiteOrderStruc> spiteStrucs = new ArrayList<SpiteOrderStruc>();
		GameSpiteOrderQueryResponse result = new GameSpiteOrderQueryResponse();
		SpiteOrderStruc spiteOrderStruc = null;
		try {
			page = gameWarnOrderService.queryGameSpiteOrders(pr);
			spites = page.getResult();
			if (spites != null && spites.size() > 0) {
				for (GameSpiteOperationsEntity gso : spites) {
					spiteOrderStruc = DTOConvert.gameSpiteOperationsEntity2SpiteOrderStruc(gso);
					spiteStrucs.add(spiteOrderStruc);
				}
			}
			result.setSpiteOrderStrucs(spiteStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			logger.error("query game spite orders error", e);
			throw e;
		}

		logger.info("query game spite orders end...");
		return response;
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
