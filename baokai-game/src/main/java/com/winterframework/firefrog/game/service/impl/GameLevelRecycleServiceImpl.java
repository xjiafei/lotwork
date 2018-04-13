package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameLevelRecycleDao;
import com.winterframework.firefrog.game.service.IGameLevelRecycleService;
import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle.RecycleStatus;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;

/**
 * 类功能说明: 一代回收
 */
@Service("gameLevelRecycleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLevelRecycleServiceImpl implements IGameLevelRecycleService {

	private Logger logger = LoggerFactory.getLogger(GameLevelRecycleServiceImpl.class);
	
	@Resource(name = "gameLevelRecycleDaoImpl")
	private IGameLevelRecycleDao gameLevelRecycleDao;
	
	@Override
	public List<QueryLevelRecycleHistoryResponse> queryLevelRecycleHistory(
			QueryLevelRecycleHistoryRequest queryLRHistoryRequest)
			throws Exception {

		List<QueryLevelRecycleHistoryResponse> response = null;

		LevelRecycleDTO levelRecycleDTO = new LevelRecycleDTO();
		levelRecycleDTO.setUserId(queryLRHistoryRequest.getUserId());
		levelRecycleDTO.setAccount(queryLRHistoryRequest.getAccount());
		levelRecycleDTO.setStartNo(queryLRHistoryRequest.getStartNo());
		levelRecycleDTO.setOperator(queryLRHistoryRequest.getOperator());
		levelRecycleDTO.setEndNo(queryLRHistoryRequest.getEndNo());
		levelRecycleDTO.setTaskStatus(queryLRHistoryRequest.getTaskStatus());

		List<LevelRecycle> levelRecycleList = gameLevelRecycleDao
				.queryLevelRecycleHistory(levelRecycleDTO);

		return fillLRHistoryResponse(response, levelRecycleList);
	}
	
	private List<QueryLevelRecycleHistoryResponse> fillLRHistoryResponse(
			List<QueryLevelRecycleHistoryResponse> response,
			List<LevelRecycle> levelRecycleList) {
		response = new ArrayList<QueryLevelRecycleHistoryResponse>();

		for (LevelRecycle levelRecycle : levelRecycleList) {
			QueryLevelRecycleHistoryResponse gHistoryResponse = new QueryLevelRecycleHistoryResponse();
			gHistoryResponse.setAccount(levelRecycle.getAccount());
			gHistoryResponse.setActivityDate(levelRecycle.getActivityDate());
			gHistoryResponse.setAvailBal(levelRecycle.getAvailBal());
			gHistoryResponse.setCreateDate(levelRecycle.getCreateDate());
			gHistoryResponse.setOperator(levelRecycle.getOperator());
			gHistoryResponse.setRecycleReason(levelRecycle.getRecycleReason());
			gHistoryResponse.setRecycleStatus(levelRecycle.getRecycleStatus());
			gHistoryResponse.setTaskStatus(levelRecycle.getTaskStatus());
			gHistoryResponse.setTopAgent(levelRecycle.getTopAgent());
			gHistoryResponse.setUserId(levelRecycle.getUserId());
			response.add(gHistoryResponse);
		}

		return response;
	}

	@Override
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(Long userId) {
		List<QueryLevelRecycleHistoryResponse> list = null;
		QueryLevelRecycleHistoryResponse result = new QueryLevelRecycleHistoryResponse();
		try {
			QueryLevelRecycleHistoryRequest queryLRHistoryRequest = new QueryLevelRecycleHistoryRequest();
			queryLRHistoryRequest.setUserId(userId);
			queryLRHistoryRequest
					.setTaskStatus(RecycleStatus.FINISH.getIntegerValue());
			list = this.queryLevelRecycleHistory(queryLRHistoryRequest);
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		} catch (Exception e) {
			logger.error("queryLevelRecycleHistory", e);
		}
		return result;
	}
	
	@Override
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(String account) {
		List<QueryLevelRecycleHistoryResponse> list = null;
		QueryLevelRecycleHistoryResponse result = new QueryLevelRecycleHistoryResponse();
		try {
			QueryLevelRecycleHistoryRequest queryLRHistoryRequest = new QueryLevelRecycleHistoryRequest();
			queryLRHistoryRequest.setAccount(account);
			queryLRHistoryRequest
					.setTaskStatus(LevelRecycle.RecycleStatus.FINISH
							.getIntegerValue());
			list = this.queryLevelRecycleHistory(queryLRHistoryRequest);
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		} catch (Exception e) {
			logger.error("queryLevelRecycleHistory", e);
		}
		return result;
	}
}
