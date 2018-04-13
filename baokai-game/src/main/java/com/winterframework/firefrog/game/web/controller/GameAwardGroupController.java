package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.exception.GameAwardGroupExistErrorException;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.web.dto.AssistBmBonusStruc;
import com.winterframework.firefrog.game.web.dto.AuditGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.AuditGameAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;
import com.winterframework.firefrog.game.web.dto.AwardListStruc;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupDTO;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.DelGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupDTO;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.GameAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameAwardQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardQueryRequest;
import com.winterframework.firefrog.game.web.dto.PublishGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.PublishGameAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.QueryTheoryRequest;
import com.winterframework.firefrog.game.web.dto.QueryTheoryResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 奖金组操作类 
 * @author Richard
 * @date 2013-8-16 下午1:57:33 
 */
@Controller("gameAwardGroupController")
@RequestMapping("/game")
public class GameAwardGroupController {

	private Logger log = LoggerFactory.getLogger(GameAwardGroupController.class);

	@Resource(name = "gameAwardGroupServiceImpl")
	private IGameAwardGroupService gameAwardGroupService;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	

	/**
	 * 
	* @Title: queryGameAwardGroup 
	* @Description: 4.17 奖金组查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping("/queryGameAwardGroup")
	@ResponseBody
	public Object queryGameAwardGroup(@RequestBody @ValidRequestBody Request<GameAwardGroupQueryRequest> request)
			throws Exception {

		Response<GameAwardGroupQueryResponse> response = new Response<GameAwardGroupQueryResponse>(request);

		try {

			if (null != request.getBody()) {

				GameAwardGroupQueryRequest gameAward = request.getBody().getParam();

				GameAwardGroupQueryResponse result = new GameAwardGroupQueryResponse();
				List<GameAwardGroupEntity> list = gameAwardGroupService.queryGameAwardGroup(gameAward.getLotteryId(),
						gameAward.getStatus(), gameAward.getGameAwardGroupId());

				List<AwardListStruc> awardListStrucs = new ArrayList<AwardListStruc>();
				for (GameAwardGroupEntity entity : list) {

					AwardListStruc awardListStruc = DTOConvert.convertEntity2Struc(entity);
					awardListStrucs.add(awardListStruc);
				}

				result.setAwardListStruc(awardListStrucs);

				response.setResult(result);
			}

		} catch (Exception e) {

			log.error("Query GameAwardGroup Error:", e);

			throw e;
		}
		return response;
	}

	@RequestMapping("/queryUserGameAward")
	@ResponseBody
	public Response<GameAwardQueryResponse> queryUserGameAward(
			@RequestBody @ValidRequestBody Request<GameUserAwardQueryRequest> request) throws Exception {
		Response<GameAwardQueryResponse> response = new Response<GameAwardQueryResponse>(request);
		GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();
		try {
			GameUserAwardQueryRequest gameUserAwardQueryRequest = request.getBody().getParam();
			List<GameAwardEntity> list = gameAwardGroupService.queryUserGameAward(
					gameUserAwardQueryRequest.getLotteryId(), gameUserAwardQueryRequest.getAwardGroupId(),
					gameUserAwardQueryRequest.getType(), gameUserAwardQueryRequest.getAwardType(),
					gameUserAwardQueryRequest.getSysAwardGroupId(), gameUserAwardQueryRequest.getUserId());
			List<AwardBonusStruc> strucList = new ArrayList<AwardBonusStruc>();
			for (GameAwardEntity entity : list) {
				AwardBonusStruc struc = convertEntity2AwardBonusStruc(entity);
				strucList.add(struc);
			}
			List<AwardBonusStruc> resultList = mergeAssistBmBonus(strucList,gameUserAwardQueryRequest.getLotteryId());
			Collections.sort(resultList, new Comparator<AwardBonusStruc>() {
				@Override
				public int compare(AwardBonusStruc o1, AwardBonusStruc o2) {
					Double o1GroupCode = o1.getGameGroupCode().doubleValue();
					Double o2GroupCode = o2.getGameGroupCode().doubleValue();
					if (o1.getGameGroupCode() == 33) {
						o1GroupCode = 12.5d;
					}
					if (o2.getGameGroupCode() == 33) {
						o2GroupCode = 12.5d;
					}
					int result = o1GroupCode.compareTo(o2GroupCode);
					if (result == 0) {
						result = o1.getGameSetCode().compareTo(o2.getGameSetCode());
					}
					if (result == 0) {
						result = o1.getBetMethodCode().compareTo(o2.getBetMethodCode());
					}
					return result;
				}

			});
			awardQueryResponse.setAwardBonusStrucList(resultList);
			response.setResult(awardQueryResponse);
		} catch (Exception e) {
			log.error("Query userGameAwardMoney Error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 4.18 奖金组奖金查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGameAward")
	@ResponseBody
	public Response<GameAwardQueryResponse> queryGameAward(
			@RequestBody @ValidRequestBody Request<GameAwardQueryRequest> request) throws Exception {

		Response<GameAwardQueryResponse> response = new Response<GameAwardQueryResponse>(request);

		try {

			if (null != request.getBody()) {

				GameAwardQueryRequest gameAwardQuery = request.getBody().getParam();

				GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();

				List<GameAwardEntity> list = gameAwardGroupService.queryGameAward(gameAwardQuery.getLotteryId(),
						gameAwardQuery.getAwardGroupId(), gameAwardQuery.getStatus());
				awardQueryResponse.setMiniLotteryProfit(gameAwardGroupService.getMiniLotteryProfit(gameAwardQuery
						.getLotteryId()));
				awardQueryResponse.setLotteryId(gameAwardQuery.getLotteryId());
				List<AwardBonusStruc> strucList = new ArrayList<AwardBonusStruc>();
				List<GameAwardEntity> _gameAwardBak = new ArrayList<GameAwardEntity>();
				List<GameAwardGroupEntity> gameAwardGrouplist = null;
				gameAwardGrouplist = gameAwardGroupService.queryGameAwardGroup(gameAwardQuery.getLotteryId(),
						gameAwardQuery.getStatus(), gameAwardQuery.getAwardGroupId());

				for (GameAwardEntity entity : list) {
					AwardBonusStruc struc = null;
					if (null != _gameAwardBak && !_gameAwardBak.isEmpty()) {
						for (GameAwardEntity _gameAward : _gameAwardBak) {
							if (null != _gameAward) {

								struc = convertEntity2AwardBonusStruc(entity);

								if ((_gameAward.getDirectRet().compareTo(entity.getDirectRet()) != 0)) {
									awardQueryResponse.setDirectRetBak(_gameAward.getDirectRet().longValue());
								}

								if ((_gameAward.getThreeoneRet().compareTo(entity.getThreeoneRet()) != 0)) {
									awardQueryResponse.setThreeoneRetBak(_gameAward.getThreeoneRet().longValue());
								}

								if (_gameAward.getActualBonus().longValue() != entity.getActualBonus().longValue()) {
									struc.setActualBonusBak(_gameAward.getActualBonus().longValue());
								}

								awardQueryResponse.setDirectRet(entity.getDirectRet().longValue());
								awardQueryResponse.setLotteryId(entity.getLottery().getLotteryId());
								awardQueryResponse.setThreeoneRet(entity.getThreeoneRet().longValue());
								awardQueryResponse.setAwardGroupId(entity.getAwardGroupId());

								strucList.add(struc);

							}
						}
					} else {
						struc = convertEntity2AwardBonusStruc(entity);
						awardQueryResponse.setDirectRet(entity.getDirectRet().longValue());
						awardQueryResponse.setLotteryId(entity.getLottery().getLotteryId());
						awardQueryResponse.setThreeoneRet(entity.getThreeoneRet().longValue());
						awardQueryResponse.setAwardGroupId(entity.getAwardGroupId());

						strucList.add(struc);
					}
				}

				List<AwardBonusStruc> resultList = mergeAssistBmBonus(strucList,awardQueryResponse.getLotteryId());
				awardQueryResponse.setAwardBonusStrucList(resultList);
				awardQueryResponse.setAwardGroupName(gameAwardGrouplist.get(0).getAwardName());
				awardQueryResponse.setDirectRet(gameAwardGrouplist.get(0).getDirectRet().longValue());
				awardQueryResponse.setThreeoneRet(gameAwardGrouplist.get(0).getThreeoneRet().longValue());
				awardQueryResponse.setSuperRet(gameAwardGrouplist.get(0).getSuperRet().longValue());

				GameSeries gameSeries = gameSeriesDaoImpl.getByLotteyId(awardQueryResponse.getLotteryId());
				awardQueryResponse.setLotteryName(gameSeries.getLotteryName());
				response.setResult(awardQueryResponse);
			}

		} catch (Exception e) {

			log.error("Query GameAwardMoney Error:", e);
			throw e;
		}

		return response;
	}

	private AwardBonusStruc getAwardBonusStruc(List<AwardBonusStruc> resultList, String byteTypeCode) {
		if (resultList == null || resultList.isEmpty()) {
			return null;
		} else {
			for (AwardBonusStruc result : resultList) {
				if (StringUtils.equals(byteTypeCode, result.getMainBetTypeCode())) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * 将玩法群，玩法组，玩法一样的合并成标准结构体
	 * @param strucList
	 * @param lotteryid
	 * @return
	 */
	private List<AwardBonusStruc> mergeAssistBmBonus(List<AwardBonusStruc> strucList,Long lotteryid ) {
		Long retValue = 0l;
		Long diffValue = 200l;
		List<AwardBonusStruc> resultList = new ArrayList<AwardBonusStruc>();
		Set<String> mainBetTypeCode = new HashSet<String>();
		Map<String, AwardBonusStruc> existMainBetTypeCode = new HashMap<String, AwardBonusStruc>();
		Map<String, String> existLhcCode = new HashMap<String, String>();
		List<Long> betTypeStatusIds = new ArrayList<Long>();

		for (AwardBonusStruc awardBonusStruc : strucList) {
			mainBetTypeCode.add(awardBonusStruc.getMainBetTypeCode());
			betTypeStatusIds.add(awardBonusStruc.getGameBettypeStatusId());
		}

		Map<Long, List<GameBettypeAssist>> map = new HashMap<Long, List<GameBettypeAssist>>();
		if (betTypeStatusIds.size() > 0) {
			map = gameAwardGroupService.getBetyTypeAssistByTypeIds(betTypeStatusIds);
		}

		for (String string : mainBetTypeCode) {
			for (AwardBonusStruc awardBonusStruc : strucList) {
				if (StringUtils.equals(string, awardBonusStruc.getMainBetTypeCode())) {
					if (awardBonusStruc.getMethodType() == null && awardBonusStruc.getLhcCodeTitle() == null) {
						if (getAwardBonusStruc(resultList, awardBonusStruc.getMainBetTypeCode()) == null) {
							resultList.add(awardBonusStruc);
						}
					} else {
						//如果存在，添加一个辅助条目即可
						if (existMainBetTypeCode.containsKey(string)) {
							AssistBmBonusStruc assistBmBonusStruc = new AssistBmBonusStruc();
							assistBmBonusStruc.setMethodType(awardBonusStruc.getMethodType());
							assistBmBonusStruc.setActualBonus(awardBonusStruc.getActualBonus());
							assistBmBonusStruc.setActualBonusDown(awardBonusStruc.getActualBonusDown());
							assistBmBonusStruc.setMethodTypeTitle(awardBonusStruc.getMethodTypeTitle());
							assistBmBonusStruc.setTheoryBonus(awardBonusStruc.getTheoryBonus());
							if(lotteryid == 99701 && awardBonusStruc.getBetMethodCode() == 81){
								if(retValue - diffValue > 0){
									retValue = retValue - diffValue;
									assistBmBonusStruc.setRetVal(retValue);
								}else{
									continue;
								}
							}else{
								assistBmBonusStruc.setRetVal(awardBonusStruc.getRetVal());
							}
							assistBmBonusStruc.setLhcTheoryBonus(awardBonusStruc.getLhcTheoryBonus());
							if(!existLhcCode.containsKey(awardBonusStruc.getLhcCodeTitle())){
							//六合彩投注內容出現過就不用再出現
								assistBmBonusStruc.setLhcCodeTitle(awardBonusStruc.getLhcCodeTitle());
							}
							
							List<GameBettypeAssist> assists = map.get(awardBonusStruc.getGameBettypeStatusId());
							if (assists != null) {
								for (GameBettypeAssist gameBettypeAssist : assists) {
									if (gameBettypeAssist.getMethodType().longValue() == awardBonusStruc
											.getMethodType().longValue()) {
										assistBmBonusStruc.setTheoryBonus(gameBettypeAssist.getTheoryBonus());
									}
								}
							}

							existMainBetTypeCode.get(string).getAssistBMBonusList().add(assistBmBonusStruc);
						} else {
							AwardBonusStruc struc = new AwardBonusStruc();
							if (getAwardBonusStruc(resultList, awardBonusStruc.getMainBetTypeCode()) == null) {
								struc.setActualBonus(awardBonusStruc.getActualBonus());
								struc.setActualBonusDown(awardBonusStruc.getActualBonusDown());
								struc.setBetMethodCode(awardBonusStruc.getBetMethodCode());
								struc.setGameGroupCode(awardBonusStruc.getGameGroupCode());
								struc.setGameSetCode(awardBonusStruc.getGameSetCode());
								struc.setTheoryBonus(awardBonusStruc.getTheoryBonus());
								struc.setRetVal(awardBonusStruc.getRetVal());
								struc.setGroupCodeTitle(awardBonusStruc.getGroupCodeTitle());
								struc.setSetCodeTitle(awardBonusStruc.getSetCodeTitle());
								struc.setMethodCodeTitle(awardBonusStruc.getMethodCodeTitle());
								struc.setMethodTypeTitle(awardBonusStruc.getMethodTypeTitle());
								resultList.add(struc);
							} else {
								struc = getAwardBonusStruc(resultList, awardBonusStruc.getMainBetTypeCode());
							}
							AssistBmBonusStruc assistBmBonusStruc = new AssistBmBonusStruc();
							assistBmBonusStruc.setMethodType(awardBonusStruc.getMethodType());
							assistBmBonusStruc.setActualBonusDown(awardBonusStruc.getActualBonusDown());
							assistBmBonusStruc.setActualBonus(awardBonusStruc.getActualBonus());
							assistBmBonusStruc.setMethodTypeTitle(awardBonusStruc.getMethodTypeTitle());
							List<GameBettypeAssist> assists = map.get(awardBonusStruc.getGameBettypeStatusId());
							assistBmBonusStruc.setTheoryBonus(awardBonusStruc.getTheoryBonus());
							assistBmBonusStruc.setLhcCodeTitle(awardBonusStruc.getLhcCodeTitle());
							existLhcCode.put(awardBonusStruc.getLhcCodeTitle(), awardBonusStruc.getLhcCodeTitle());
							if(lotteryid == 99701 && awardBonusStruc.getBetMethodCode() == 81){
								retValue = awardBonusStruc.getRetVal();
							}
							assistBmBonusStruc.setRetVal(awardBonusStruc.getRetVal());
							assistBmBonusStruc.setLhcTheoryBonus(awardBonusStruc.getLhcTheoryBonus());
							if (assists != null) {
								for (GameBettypeAssist gameBettypeAssist : assists) {
									if (gameBettypeAssist.getMethodType().longValue() == awardBonusStruc
											.getMethodType().longValue()) {
										assistBmBonusStruc.setTheoryBonus(gameBettypeAssist.getTheoryBonus());
									}
								}
							}
							List<AssistBmBonusStruc> list = new ArrayList<AssistBmBonusStruc>();
							list.add(assistBmBonusStruc);
							struc.setAssistBMBonusList(list);

							existMainBetTypeCode.put(string, struc);
						}
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 转换GameAwardEntity为AwardBonusStruc
	 * @param awardEntity
	 * @return
	 */
	private AwardBonusStruc convertEntity2AwardBonusStruc(GameAwardEntity awardEntity) {

		AwardBonusStruc struc = new AwardBonusStruc();
		struc.setGameBettypeStatusId(awardEntity.getGameBetType().getId());
		struc.setActualBonus(awardEntity.getActualBonus().longValue());
		struc.setCompanyProfit(awardEntity.getCompanyProfit().longValue());
		struc.setGameGroupCode(awardEntity.getGameBetType().getGameGroupCode());
		struc.setGameSetCode(awardEntity.getGameBetType().getGameSetCode());
		struc.setBetMethodCode(awardEntity.getGameBetType().getBetMethodCode());
		struc.setMethodType(awardEntity.getGameBetType().getMethodType());
		struc.setTheoryBonus(awardEntity.getTheoryBonus().longValue());
		struc.setTopAgentPoint(awardEntity.getTopAgentPoint().longValue());
		struc.setTotalProfit(awardEntity.getTotatProfit().longValue());
		struc.setActualBonusDown(awardEntity.getActualBonusDown() != null ? awardEntity.getActualBonusDown()
				.longValue() : null);
		struc.setRetVal(awardEntity.getRetValue());
		struc.setMaxRetVal(awardEntity.getMaxRetValue());
		struc.setGroupCodeTitle(awardEntity.getGroupCodeTitle());
		struc.setSetCodeTitle(awardEntity.getSetCodeTitle());
		struc.setMethodCodeTitle(awardEntity.getMethodCodeTitle());
		struc.setMethodTypeTitle(awardEntity.getMethodTypeTitle());
		struc.setLhcCodeTitle(awardEntity.getLhcCodeTitle());
		struc.setLhcTheoryBonus(awardEntity.getLhcTheoryBonus().longValue());
		return struc;
	}

	/**
	 * 4.19 新增用户奖金组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createGameAwardGroup")
	@ResponseBody
	public Object createGameAwardGroup(@RequestBody @ValidRequestBody Request<CreateGameAwardGroupRequest> request)
			throws Exception {

		Response<CreateGameAwardGroupResponse> response = new Response<CreateGameAwardGroupResponse>(request);

		try {

			if (null != request.getBody()) {

				CreateGameAwardGroupRequest createGameAwardGroup = request.getBody().getParam();

				gameAwardGroupService.createAwardGroup(convertCreateGameAwardGroup2DTO(createGameAwardGroup));

			}
		} catch (GameAwardGroupExistErrorException eg) {

			response.getHead().setStatus(eg.getStatus());
		} catch (Exception e) {
			log.error("Create GameAwardGroup Error:", e);
			throw e;
		}

		return response;
	}

	/**
	 * 4.20 奖金组审核
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/auditAwardGroup")
	@ResponseBody
	public Object auditAwardGroup(@RequestBody @ValidRequestBody Request<AuditGameAwardGroupRequest> request)
			throws Exception {

		Response<AuditGameAwardGroupResponse> response = new Response<AuditGameAwardGroupResponse>(request);

		try {

			if (null != request.getBody()) {

				AuditGameAwardGroupRequest audit = request.getBody().getParam();

				gameAwardGroupService.auditAwardGroup(audit.getLotteryId(), audit.getAwardId(), audit.getCheckType(),
						audit.getCheckResult());

			}

		} catch (Exception e) {

			log.error("Audit AwardGroup Error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 4.21 奖金组修改 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editAwardGroup")
	@ResponseBody
	public Object editAwardGroup(@RequestBody @ValidRequestBody Request<EditGameAwardGroupRequest> request)
			throws Exception {

		Response<EditGameAwardGroupResponse> response = new Response<EditGameAwardGroupResponse>(request);

		try {

			if (null != request.getBody()) {

				EditGameAwardGroupRequest edit = request.getBody().getParam();

				gameAwardGroupService.editAwardGroup(convertEditGameAwardGroup2DTO(edit));
			}

		} catch (Exception e) {

			log.error("Edit GameAwardGroup Error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 4.22 奖金组发布
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/publishGameAwardGroup")
	@ResponseBody
	public Object publishGameAwardGroup(@RequestBody @ValidRequestBody Request<PublishGameAwardGroupRequest> request)
			throws Exception {

		Response<PublishGameAwardGroupResponse> response = new Response<PublishGameAwardGroupResponse>(request);

		try {

			if (null != request.getBody()) {

				PublishGameAwardGroupRequest publish = request.getBody().getParam();

				gameAwardGroupService.publishAwardGroup(publish.getLotteryId(), publish.getAwardId(),
						publish.getPublishResult());

			}

		} catch (Exception e) {

			log.error("Publish GameAwardGroup Error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 转换GameAwardGroup为CreateGameAwardGroupDTO
	 * @param awardGroupRequest
	 * @return
	 * @throws Exception
	 */
	private CreateGameAwardGroupDTO convertCreateGameAwardGroup2DTO(CreateGameAwardGroupRequest awardGroupRequest)
			throws Exception {

		CreateGameAwardGroupDTO awardGroupDTO = new CreateGameAwardGroupDTO();

		awardGroupDTO.setAwardBonusStrucList(awardGroupRequest.getAwardBonusStrucList());
		awardGroupDTO.setAwardName(awardGroupRequest.getAwardName());
		awardGroupDTO.setDirectRet(awardGroupRequest.getDirectRet());
		awardGroupDTO.setLotteryId(awardGroupRequest.getLotteryId());
		awardGroupDTO.setThreeoneRet(awardGroupRequest.getThreeoneRet());

		return awardGroupDTO;
	}

	/**
	 * 转换 GameAwardGroup为EditGameAwardGroupDTO
	 * @param awardGroupRequest
	 * @return
	 * @throws Exception
	 */
	private EditGameAwardGroupDTO convertEditGameAwardGroup2DTO(EditGameAwardGroupRequest awardGroupRequest)
			throws Exception {

		EditGameAwardGroupDTO awardGroupDTO = new EditGameAwardGroupDTO();

		awardGroupDTO.setAwardBonusStrucList(awardGroupRequest.getAwardBonusStrucList());
		awardGroupDTO.setDirectRet(awardGroupRequest.getDirectRet());
		awardGroupDTO.setLotteryId(awardGroupRequest.getLotteryId());
		awardGroupDTO.setThreeoneRet(awardGroupRequest.getThreeoneRet());
		awardGroupDTO.setUpdateType(awardGroupRequest.getUpdateType());
		awardGroupDTO.setAwardId(awardGroupRequest.getAwardId());

		return awardGroupDTO;
	}

	/**
	 * 理论奖金组查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTheory")
	@ResponseBody
	public Response<QueryTheoryResponse> queryTheory(@RequestBody @ValidRequestBody Request<QueryTheoryRequest> request)
			throws Exception {

		Response<QueryTheoryResponse> response = new Response<QueryTheoryResponse>(request);
		try {

			QueryTheoryResponse theory = new QueryTheoryResponse();
			QueryTheoryRequest query = request.getBody().getParam();
			List<GameBettypeStatus> list = gameAwardGroupService.queryTheoryByLotteryId(query.getLotteyId());
			List<Long> betTypeStatusIds = new ArrayList<Long>();
			for (GameBettypeStatus status : list) {
				betTypeStatusIds.add(status.getId());
			}

			Map<Long, List<GameBettypeAssist>> gameBetyTypeAssistMap = gameAwardGroupService
					.getBetyTypeAssistByTypeIds(betTypeStatusIds);

			List<AwardBonusStruc> awardList = new ArrayList<AwardBonusStruc>();

			for (GameBettypeStatus check : list) {
				AwardBonusStruc struc = new AwardBonusStruc();
				struc.setActualBonus(check.getTheoryBonus());
				struc.setBetMethodCode(check.getBetMethodCode());
				struc.setGameGroupCode(check.getGameGroupCode());
				struc.setGameSetCode(check.getGameSetCode());
				struc.setTheoryBonus(check.getTheoryBonus());

				//初始话辅助信息列表
				List<GameBettypeAssist> assistList = gameBetyTypeAssistMap.get(check.getId());
				if (assistList != null && !assistList.isEmpty()) {
					List<AssistBmBonusStruc> assistStrucList = new ArrayList<AssistBmBonusStruc>();
					for (GameBettypeAssist gameBettypeAssist : assistList) {
						AssistBmBonusStruc assistBmBonusStruc = new AssistBmBonusStruc();
						assistBmBonusStruc.setMethodType(gameBettypeAssist.getMethodType().intValue());
						assistBmBonusStruc.setActualBonus(gameBettypeAssist.getTheoryBonus());
						assistBmBonusStruc.setTheoryBonus(gameBettypeAssist.getTheoryBonus());
						assistStrucList.add(assistBmBonusStruc);
					}
					struc.setAssistBMBonusList(assistStrucList);
				}
				awardList.add(struc);
			}

			theory.setMiniLotteryProfit(gameAwardGroupService.getMiniLotteryProfit(query.getLotteyId()));
			theory.setAwardList(awardList);
			theory.setLotteryId(query.getLotteyId());
			response.setResult(theory);

		} catch (Exception e) {

			log.error("queryTheory error:", e);
			throw e;
		}

		return response;
	}

	@RequestMapping("/delGameAwardGroup")
	@ResponseBody
	public Object delGameAwardGroup(@RequestBody @ValidRequestBody Request<DelGameAwardGroupRequest> request)
			throws Exception {

		Response<Object> response = new Response<Object>(request);

		try {

			DelGameAwardGroupRequest awardGroupRequest = request.getBody().getParam();
			gameAwardGroupService.delAwardGroup(awardGroupRequest.getLotteryId(),
					Long.parseLong(awardGroupRequest.getAwardId()));

		} catch (Exception e) {
			log.error("delGameAwardGroup error:", e);
			throw e;
		}

		return response;
	}
	
	/**
	 * 查詢用戶獎金組、總代返點
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGameAwardRet")
	@ResponseBody
	public Response<GameAwardQueryResponse> queryGameAwardRet(
			@RequestBody @ValidRequestBody Request<GameUserAwardQueryRequest> request) throws Exception {
		Response<GameAwardQueryResponse> response = new Response<GameAwardQueryResponse>(request);
		GameAwardQueryResponse awardQueryResponse = new GameAwardQueryResponse();
		try {
			GameUserAwardQueryRequest gameUserAwardQueryRequest = request.getBody().getParam();
			
			List<GameAwardGroup> list = gameUserAwardGroupService.queryUserAwardRet(gameUserAwardQueryRequest.getUserId(),gameUserAwardQueryRequest.getLotteryId());
			
			for (GameAwardGroup entity : list) {
				awardQueryResponse.setLotteryId(entity.getLotteryid());
				awardQueryResponse.setAwardGroupName(entity.getAwardName());
				awardQueryResponse.setDirectRet(Long.valueOf(entity.getDirectRet().toString()));
				awardQueryResponse.setThreeoneRet(Long.valueOf(entity.getThreeoneRet().toString()));
				awardQueryResponse.setLhcHalfwave(Long.valueOf(entity.getLhcHalfwave().toString()));
				awardQueryResponse.setLhcHalfwave(Long.valueOf(entity.getLhcHalfwave().toString()));
				awardQueryResponse.setLhcOneyear(Long.valueOf(entity.getLhcOneyear().toString()));
				awardQueryResponse.setLhcNotin(Long.valueOf(entity.getLhcNotin().toString()));
				awardQueryResponse.setLhcContinuein23(Long.valueOf(entity.getLhcContinuein23().toString()));
				awardQueryResponse.setLhcContinuein4(Long.valueOf(entity.getLhcContinuein4().toString()));
				awardQueryResponse.setLhcContinuein5(Long.valueOf(entity.getLhcContinuein5().toString()));
				awardQueryResponse.setLhcContinuenotin23(Long.valueOf(entity.getLhcContinuenotin23().toString()));
				awardQueryResponse.setLhcContinuenotin4(Long.valueOf(entity.getLhcContinuenotin4().toString()));
				awardQueryResponse.setLhcContinuenotin5(Long.valueOf(entity.getLhcContinuenotin5().toString()));
				awardQueryResponse.setLhcContinuecode(Long.valueOf(entity.getLhcContinuecode().toString()));
				log.info("entity.getAwardName="+entity.getAwardName());
				log.info("entity.getDirectRet="+entity.getDirectRet());
				log.info("entity.getThreeoneRet="+entity.getThreeoneRet());
				
				//六合彩使用
				if(entity.getLotteryid()==99701){
					awardQueryResponse.setLhcYear(Long.valueOf(entity.getLhcYear().toString()));
					awardQueryResponse.setLhcColor(Long.valueOf(entity.getLhcColor().toString()));			
					awardQueryResponse.setLhcFlatcode(Long.valueOf(entity.getLhcFlatcode().toString()));	
					log.info("entity.getLhcYear="+entity.getLhcYear());
					log.info("entity.getLhcColor="+entity.getLhcColor());									
				}
				if(entity.getLotteryid()==99601 || entity.getLotteryid()==99602 || entity.getLotteryid()==99603 ){
					awardQueryResponse.setSbThreeoneRet(Long.valueOf(entity.getSbThreeoneRet().toString()));
					log.info("entity.getSbThreeoneRet="+entity.getSbThreeoneRet());
				}
			}
			
			response.setResult(awardQueryResponse);
		} catch (Exception e) {
			log.error("Query userGameAwardMoney Error:", e);
			throw e;
		}
		return response;
	}
}
