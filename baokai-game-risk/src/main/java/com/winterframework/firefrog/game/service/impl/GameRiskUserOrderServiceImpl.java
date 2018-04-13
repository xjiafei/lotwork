package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderLogDao;
import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrderLog;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameRiskService;
import com.winterframework.firefrog.game.service.IGameRsikUserOrderService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.IUserSystemUpdateLogService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GamePlanDTO;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnOrderStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserOrderListResponse;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserOrderStruc;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanRequest;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName IGameWarnUserOrderService 
* @Description 风控用户订单信息 
* @author  hugh
* @date 2014年4月10日 下午3:25:40 
*  
*/
@Service("gameRiskUserOrderServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameRiskUserOrderServiceImpl implements IGameRsikUserOrderService {
	private Logger log = LoggerFactory.getLogger(GameRiskUserOrderServiceImpl.class);

	@Resource(name = "gameRiskConfigDaoImpl")
	private IGameRiskConfigDao gameRiskConfigDaoImpl;

	@Resource(name = "gameIssueDaoImpl")
	protected IGameIssueDao gameIssueDaoImpl;

	@Resource(name = "gameWarnUserDaoImpl")
	protected IGameWarnUserDao gameWarnUserDaoImpl;

	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao gameWarnOrderDaoImpl;

	@Resource(name = "gameWarnOrderLogDaoImpl")
	private IGameWarnOrderLogDao gameWarnOrderLogDaoImpl;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDaoImpl;

	@Resource(name = "gameRiskServiceImpl")
	private IGameRiskService gameRiskServiceImpl;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	
	
	@Resource(name = "userSystemUpdateLogServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;
	/** 
	* @Title: queryWarnUserOrderList 
	* @Description: 查询风控用户订单信息
	* @param req
	* @return
	* @throws Exception 
	*/
	public Response<GameRiskWarnUserOrderListResponse> queryWarnUserOrderList(
			Request<GameRiskWarnUserListQueryRequest> request) throws Exception {
		Response<GameRiskWarnUserOrderListResponse> response = new Response<GameRiskWarnUserOrderListResponse>(request);

		//参数处理
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		PageRequest<GameRiskWarnUserListQueryRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		GameRiskWarnUserListQueryRequest req = request.getBody().getParam();
		pr.setSearchDo(req);
		pr.setSortColumns("ID desc");

		//获取审核配置信息
		GameRiskConfig config = gameRiskConfigDaoImpl.queryGameRiskConfig(req.getLotteryId());

		//获取审核奖期信息 
		GameRiskIssue gameRiskIssue = gameIssueDaoImpl.getGameRiskIssue(req.getLotteryId(), req.getIssueCode());
		if (gameRiskIssue == null) {
			response.setResult(null);
			ResultPager pager = new ResultPager();
			pager.setEndNo(0);
			pager.setStartNo(0);
			pager.setTotal(0);
			response.setResultPage(pager);
			return response;
		}

		//获取审核用户信息
		Page<GameWarnUser> page = gameWarnUserDaoImpl.queryGameRiskWarnUser(pr);
		List<GameWarnUser> users = page.getResult();
		List<GameRiskWarnUserOrderStruc> userStrucs = new ArrayList<GameRiskWarnUserOrderStruc>();
		if (users != null) {
			for (GameWarnUser gameWarnUser : users) {

				//获取审核订单信息
				List<GameRiskWarnOrderStruc> orderStrucs = parseToStrucs(getRiskWarnOrders(req.getLotteryId(),
						req.getIssueCode(), req.getStatus().longValue(), gameWarnUser.getUserid()));
				for (GameRiskWarnOrderStruc gameRiskWarnOrderStruc : orderStrucs) {
				
					try {
						User user = customerDao.queryUserById(Long.parseLong(gameRiskWarnOrderStruc.getUserid()));
						gameRiskWarnOrderStruc.setUserid(user.getUserProfile().getAccount());
					} catch (Exception e) {
						log.error("SSCGameDrawService queryUserById error: userId=" + gameRiskWarnOrderStruc.getUserid(), e);
					}
				}
							
				GameRiskWarnUserOrderStruc user = new GameRiskWarnUserOrderStruc(gameWarnUser);
				user.setWarnOrderStrucs(orderStrucs);
				try {
					User userDB = customerDao.queryUserById(user.getUserid());
					user.setUserName(userDB.getUserProfile().getAccount());
				} catch (Exception e) {
					log.error("SSCGameDrawService queryUserById error: userId=" + user.getUserid(), e);
				}
				userStrucs.add(user);
			}
		}

		//封装返回		
		GameRiskWarnUserOrderListResponse result = new GameRiskWarnUserOrderListResponse();
		result.setRiskIssueStruc(parseIssueStruc(gameRiskIssue));
		result.setRiskConfigStruc(DTOConvert.GameRiskConfig2QuerySeriesConfigRiskResponse(config));
		result.setWarnUserOrderStrucs(userStrucs);
		response.setResult(result);

		ResultPager pager = new ResultPager();
		pager.setEndNo(page.getThisPageLastElementNumber());
		pager.setStartNo(page.getThisPageFirstElementNumber());
		pager.setTotal(page.getTotalCount());
		response.setResultPage(pager);

		return response;
	}

	/** 
	* @Title: getRiskWarnOrders 
	* @Description: 获取审核订单信息
	* @param lotteryId
	* @param issueCode
	* @param status
	* @return
	*/
	public List<GameWarnOrder> getRiskWarnOrders(Long lotteryId, Long issueCode, Long status, Long userId) {
		List<GameWarnOrder> orders = new ArrayList<GameWarnOrder>();
		GameWarnOrder entity = new GameWarnOrder();
		entity.setLotteryid(lotteryId);
		entity.setIssueCode(issueCode);
		entity.setUserid(userId);
		if (status == 2) {
			entity.setStatus(0L);
			orders = gameWarnOrderDaoImpl.getAllByEntity(entity);
		} else {
			entity.setStatus(1L);
			orders.addAll(gameWarnOrderDaoImpl.getAllByEntity(entity));
			entity.setStatus(2L);
			orders.addAll(gameWarnOrderDaoImpl.getAllByEntity(entity));
		}
		return orders;
	}

	/** 
	* @Title: parseToStrucs 
	* @Description: 数据类型转化
	* @param orders
	* @return
	*/
	private List<GameRiskWarnOrderStruc> parseToStrucs(List<GameWarnOrder> orders) {
		List<GameRiskWarnOrderStruc> orderStrucs = new ArrayList<GameRiskWarnOrderStruc>();
		for (GameWarnOrder gameWarnOrder : orders) {
			GameRiskWarnOrderStruc order = new GameRiskWarnOrderStruc(gameWarnOrder);
			orderStrucs.add(order);
		}
		return orderStrucs;
	}

	/** 
	* @Title: updateWarnOrder 
	* @Description: 审核、更新订单
	* @param orderIds
	* @param status
	 * @throws Exception 
	*/
	@Override 
	public GenerateGamePlanRequest updateWarnOrder(List<Long> ids, Long status, GameWarnOrderLog entity) throws Exception {
		
		TORiskRequest request = new TORiskRequest();
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		request.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);

		GenerateGamePlanRequest planRequest = new GenerateGamePlanRequest();
		List<GamePlanDTO> planList = new ArrayList<GamePlanDTO>();
		for (Long id : ids) {
			GameWarnOrder gameWarnOrder = gameWarnOrderDaoImpl.getById(id);
			Long orderId = gameWarnOrder.getOrderId();
			
			Long warnStatus = gameWarnOrder.getStatus();
			if(warnStatus.longValue() != 0L){
				continue;
			}
			
			entity.setWarnOrderId(gameWarnOrder.getId());
			
			GameOrder order = gameOrderDaoImpl.getById(orderId);

			if (order == null) {
				log.error("手动审核查询关联订单为空!");
				throw new GameRiskException("查询订单为空");
			}

			GameOrderWin win = gameOrderWinDaoImpl.selectGameOrderWinByOrderId(orderId);
			GameRetPoint ret = gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId);
			
//			if(win.getStatus() != 2L){
//				continue;
//			}
//			if(ret.getStatus().longValue() != GameRetPoint.STATUS_WARN.longValue()){
//				continue;
//			}
			//调用投注、返点解冻
			toRiskDTOList.add(packageOrderTORiskDTO(order));
			toRiskDTOList.add(packageRetTORiskDTO(order, ret)); 
			gameWarnOrderLogDaoImpl.insert(entity);
			
			if (1 == status.longValue()) {
				//调用派奖
				toRiskDTOList.add(packageWinTORiskDTO(order, win));
				order.setStatus(2);
				win.setStatus(new Long(GameOrderWin.Status.DISTRIBUTE.getValue())); 
				ret.setStatus(2);	//更新返点状态：已派发
				gameOrderWinDaoImpl.update(win);
				gameReturnPointDaoImpl.update(ret);	
				gameSlipService.changeStatus(order.getId(),GameSlip.Status.EXCEP.getValue(),GameSlip.Status.WIN.getValue());
				
				try {
					userSystemUpdateLogServiceImpl.addUserBet(order.getUserid());
				} catch (Exception e) {
					log.error("activity error");
				}
			} else {
				order.setStatus(3);
				gameSlipService.changeStatus(order.getId(),GameSlip.Status.EXCEP.getValue(),GameSlip.Status.UN_WIN.getValue());
			}
			gameOrderDaoImpl.update(order); 
			
			//更新追号信息
			GamePlan plan = gamePlanDao.getGamePlanByOrderId(order.getId());

			if (plan != null) {
				if (1 == status.longValue()) {
					if(plan.getWinAmount() == null){
						plan.setWinAmount(win.getCountWin());
					}else{
						plan.setWinAmount(plan.getWinAmount() + win.getCountWin()); 
					}					
					plan.setUpdateTime(DateUtils.currentDate());
					gamePlanDao.update(plan);
				} 
				//打开计划

				planRequest.setIssueCode(order.getIssueCode());
				planRequest.setLotteryId(order.getLotteryid());

				GamePlanDTO e = new GamePlanDTO();
				e.setGamePlanId(plan.getId());
				//e.setPlanAmount(win.getCountWin());
				planList.add(e);
			}

		}
		gameWarnOrderDaoImpl.updateWarnOrder(ids, status);
		planRequest.setPlanList(planList);
		
		if(toRiskDTOList!=null && toRiskDTOList.size()>0){
			request.setToRiskDTOList(toRiskDTOList); 
			gameRiskServiceImpl.integration(request);
		}
		
		return planRequest;
	}

	/** 
	* @Title: updateNotPassByLotteryAndIssue 
	* @Description: 奖期期号所有风控订单不通过
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public void updateNotPassByLotteryAndIssue(Long lotteryId, Long issueCode) {
		gameWarnOrderDaoImpl.updateNotPassByLotteryAndIssue(lotteryId, issueCode);
	}

	/** 
	* @Title: parseIssueStruc 
	* @Description: 转换issue
	* @param gameRiskIssue
	* @return
	*/
	public GameRiskIssueStruc parseIssueStruc(GameRiskIssue gameRiskIssue) {
		GameSeries series = gameSeriesDaoImpl.getByLotteyId(gameRiskIssue.getLotteryid());
		GameRiskIssueStruc issue = new GameRiskIssueStruc(gameRiskIssue);
		issue.setLotteryName(series.getLotteryName());
		return issue;
	}

	private TORiskDTO packageOrderTORiskDTO(GameOrder order) {
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setAmount(order.getTotamount() + "");
		dto.setUserid(order.getUserid() + "");
		dto.setType(GameFundTypesUtils.GAME_BET_UNFREEZER_DETEAIL_TYPE);
		return dto;
	}

	private TORiskDTO packageWinTORiskDTO(GameOrder order, GameOrderWin orderWin) {
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setAmount(orderWin.getCountWin() + "");
		dto.setUserid(orderWin.getUserid() + "");
		dto.setType(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE);
		return dto;
	}

	private TORiskDTO packageRetTORiskDTO(GameOrder order, GameRetPoint ret) {
		TORiskDTO dto = packageTORiskDTO(order);
		dto.setAmount(ret.getRetPointChain());
		dto.setUserid(ret.getRetUserChain());
		dto.setType(GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE);
		return dto;
	}

	private TORiskDTO packageTORiskDTO(GameOrder order) {
		TORiskDTO dto = new TORiskDTO();
		dto.setIssueCode(order.getIssueCode());
		dto.setLotteryid(order.getLotteryid().longValue());
		dto.setOrderCodeList(order.getOrderCode());
		GamePlan gamePlan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if (gamePlan != null) {
			dto.setPlanCodeList(gamePlan.getPlanCode());
		}
		return dto;
	}

}
