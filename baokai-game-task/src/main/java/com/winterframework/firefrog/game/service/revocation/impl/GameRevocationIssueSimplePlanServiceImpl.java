package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService;
import com.winterframework.firefrog.game.web.dto.LockServiceReqeust;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName GameRevocationIssueSimplePlanServiceImpl 
* @Description 撤销一期没有生成订单的追号（奖期还未开奖）
* @author  hugh
* @date 2014年5月7日 下午4:12:08 
*  
*/
@Service("gameRevocationIssueSimplePlanServiceImpl")
@Transactional
public class GameRevocationIssueSimplePlanServiceImpl extends AbstractGameRevocationService implements
		IGameRevocationIssueService {

	@Resource(name = "gameRevocationPlanStatusMachineImpl")
	private IGameRevocationPlanService gameRevocationPlanStatusMachineImpl;

	@PropertyConfig(value = "url.game.undoGamePlanLock")
	private String undoGamePlanLock;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.business.connect")
	private String serverPath;
	/**
	* @Title: doRevocation
	* @Description:撤销一期没有生成订单的追号（奖期还未开奖）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService#cancel(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void doRevocation(Long lotteryId, Long issueCode) throws Exception{

		List<GamePlanDetail> details = selectOneIssueUndoGamePlanDetailsByLotteryIssue(lotteryId, issueCode);
		if (details != null) {
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();

			for (GamePlanDetail detail : details) {
				//一单单追号撤销
				List<TORiskDTO> toRiskDTOList1 = gameRevocationPlanStatusMachineImpl.doRevocation(
						gamePlanDao.getPlanById(detail.getPlanid()), detail, null, true);
				if (toRiskDTOList1 != null) {
					toRiskDTOList.addAll(toRiskDTOList1);
				}
				
				//add 撤销封锁变价
				LockServiceReqeust lockServiceReqeust = new LockServiceReqeust();
				lockServiceReqeust.setIssueCode(issueCode);
				lockServiceReqeust.setLotteryId(detail.getPlanid());//这里将lotteryId设置为planId，
				lockServiceReqeust.setUserId(gamePackDaoImpl.getById(gamePlanDao.getById(detail.getPlanid()).getPackageId()).getUserid());			
                httpClient.invokeHttpWithoutResultType(serverPath + undoGamePlanLock, lockServiceReqeust);

			}

			fundRequest(toRiskDTOList);
		}


	}
	@Resource(name = "gamePackDaoImpl")
	protected IGamePackageDao gamePackDaoImpl;
}
