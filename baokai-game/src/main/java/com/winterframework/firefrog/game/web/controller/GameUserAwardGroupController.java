package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.dto.GameModifyAwardUserGroup;
import com.winterframework.firefrog.game.dto.SubSysModifyGameUserAwardRequestDTO;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserBetAwardGroupEntity;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameUserAwardActualBoundsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardActualBoundsQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupLoginResponse;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupModifyRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.game.web.dto.OpenAccountDetailedConfigRequest;
import com.winterframework.firefrog.game.web.dto.SaveProxyBetGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.UserAwardListByBetStruc;
import com.winterframework.firefrog.game.web.dto.UserAwardListByLoginStruc;
import com.winterframework.firefrog.game.web.dto.UserAwardListStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
 * 用户奖金组相关操作Controller 
 * @author Denny 
 * @date 2013-9-17 下午4:57:24 
 */
@Controller("gameUserAwardGroupController")
@RequestMapping("/game")
public class GameUserAwardGroupController {
	private Logger log = LoggerFactory.getLogger(GameUserAwardGroupController.class);

	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;

	@Resource(name = "gameAwardGroupServiceImpl")
	private IGameAwardGroupService gameAwardGroupService;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "game_change_WG_bonus")
	private String game_change_WG_bonus;

	/**
	 * 4.43.3 奖金组查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGameUserAwardGroup")
	@ResponseBody
	public Response<GameUserAwardGroupQueryResponse> queryGameUserAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupQueryRequest> request) throws Exception {

		log.info("开始查询用户奖金组......");
		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>(request);
		long userid = request.getBody().getParam().getUserid();
		int type = request.getBody().getParam().getType();
		int awardType = request.getBody().getParam().getAwardType();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		List<GameAwardGroupEntity> awardGroupList = new ArrayList<GameAwardGroupEntity>();

		GameUserAwardGroupQueryResponse result = new GameUserAwardGroupQueryResponse();
		List<UserAwardListStruc> ualss = new ArrayList<UserAwardListStruc>();

		try {
			if (type == 0) {
				// 总代
				awardGroupList = gameAwardGroupService.queryGameAwardGroupByUserId(userid);

				for (GameAwardGroupEntity entity : awardGroupList) {
					UserAwardListStruc uals = new UserAwardListStruc();
					uals = DTOConvert.gameAwardGroupEntity2UserAwardListStruc(entity, uals);
					//根据奖金组名称得到奖金组奖金 可能非数值 
					Long award = getAwardFromAwardName(uals.getAwardName());					
					uals.setAward(award);	//根据奖金组名称得到奖金组奖金
					uals.setTheoryAward(2000L);	//理论奖金都是2000
					ualss.add(uals);
				}
			} else if (type == 1) {
				// 用户
				userAwardGroupList = gameUserAwardGroupService.queryGameUserAwardGroupByUserIdAndBetType(userid,
						awardType);

				for (UserAwardGroupEntity entity : userAwardGroupList) {
					UserAwardListStruc uals = new UserAwardListStruc();
					uals = DTOConvert.userAwardGroupEntity2UserAwardListStruc(entity, uals); 
					Long award = getAwardFromAwardName(uals.getAwardName());
					uals.setAward(award);	
					uals.setTheoryAward(2000L);	//理论奖金都是2000
					ualss.add(uals);
				}
			}

			result.setUserAwardListStruc(ualss);

			log.info("查询用户奖金组完成中，" + ualss.toString());

			response.setResult(result);
		} catch (Exception e) {
			log.error("查询用户奖金组异常 ", e);
			throw e;
		}

		log.info("查询用户奖金组完成。");
		return response;
	}

	private Long getAwardFromAwardName(String awardName) {
		//根据奖金组名称得到奖金组奖金 可能非数值
		Long award=0L;
		try{
			String awardStr=awardName.substring(3);
			award=Long.valueOf(awardStr);
		}catch(Exception e){
			log.debug(e.getMessage(),e);
		}
		return award;
	}

	/**
	 * 奖金组修改初始
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGameUserAwardGroupForModify")
	@ResponseBody 
	public Response<GameUserAwardGroupQueryResponse> queryGameUserAwardGroupForModify(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupQueryRequest> request) throws Exception {

		log.info("开始修改用户奖金组......");
		Response<GameUserAwardGroupQueryResponse> response = new Response<GameUserAwardGroupQueryResponse>(request);
		long userid = request.getBody().getParam().getUserid();
		int awardType = request.getBody().getParam().getAwardType();

		long currentUserid = request.getHead().getUserId();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		List<UserAwardGroupEntity> currentUserAwardGroupList = new ArrayList<UserAwardGroupEntity>();

		GameUserAwardGroupQueryResponse result = new GameUserAwardGroupQueryResponse();
		List<UserAwardListStruc> ualss = new ArrayList<UserAwardListStruc>(); 

		try {
			userAwardGroupList = gameUserAwardGroupService.queryGameUserAwardGroupByUserIdAndBetType(userid, awardType);
			currentUserAwardGroupList = gameUserAwardGroupService.queryGameUserAwardGroup(currentUserid);

			for (UserAwardGroupEntity entity : currentUserAwardGroupList) {
				UserAwardListStruc uals = new UserAwardListStruc();
				uals = DTOConvert.userAwardGroupEntity2UserAwardListStruc(entity, uals);
				ualss.add(uals);
			} 
			for (UserAwardListStruc u : ualss) {
				for (UserAwardGroupEntity entity : userAwardGroupList) {
					if (entity.getLotteryId().equals(u.getLotteryId())
							&& entity.getSysGroupAward().getId().equals(u.getSysAwardGrouId())) {
						u.setDirectRet(entity.getDirectRet());
						u.setThreeoneRet(entity.getThreeoneRet()); 
						u.setMaxDirectRet(entity.getMaxDirectRet()); 
						u.setMaxThreeOneRet(entity.getMaxThreeOneRet());
						u.setSuperRet(entity.getSuperRet()); 
						u.setMaxSuperRet(entity.getMaxSuperRet());
						u.setStatus(entity.getStatus());
					}
				}
			}
			result.setUserAwardListStruc(ualss);
			response.setResult(result);
		} catch (Exception e) {
			log.error("修改用户奖金组异常 ", e);
			throw e;
		}

		log.info("修改用户奖金组完成。");
		return response;
	}

	/**
	 * 当用户登录时候查询投注奖金组的情况,返回存在投注奖金组的彩种 参见UMI009
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/queryAwardGroupByLogin")
	@ResponseBody
	public Response<GameUserAwardGroupLoginResponse> queryGameUserAwardGroupByLogin(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupQueryRequest> request) throws Exception {

		log.info("登录查询用户投注奖金组信息......");
		Response<GameUserAwardGroupLoginResponse> response = new Response<GameUserAwardGroupLoginResponse>(request);

		long subUserid = request.getBody().getParam().getUserid();

		List<UserBetAwardGroupEntity> currentUserAwardGroupList = new ArrayList<UserBetAwardGroupEntity>();

		GameUserAwardGroupLoginResponse result = new GameUserAwardGroupLoginResponse();
		List<UserAwardListByLoginStruc> ualss = new ArrayList<UserAwardListByLoginStruc>();
		UserAwardListByLoginStruc uals = new UserAwardListByLoginStruc();

		try {
			currentUserAwardGroupList = gameUserAwardGroupService.queryGameBetAwardGroup(subUserid);

			for (UserBetAwardGroupEntity entity : currentUserAwardGroupList) {
				uals = DTOConvert.userAwardGroupEntity2UserAwardListByLoginStruc(entity);
				ualss.add(uals);
			}

			result.setUserAwardListByBetStruc(ualss);
			response.setResult(result);
		} catch (Exception e) {
			log.error("登录查询用户投注奖金组异常 ", e);
			throw e;
		}

		log.info("登录查询用户投注奖金组完成。");
		return response;
	}
	
	@RequestMapping("/queryGameAwardGroupByLotid")
	@ResponseBody
	public Response<LotteryGameUserAwardGroupQueryResponse> queryGameAwardGroupByLotid(
			@RequestBody @ValidRequestBody Request<LotteryGameUserAwardGroupQueryRequest> request) throws Exception {

		log.info("查询用户奖金组 by lot id......");
		Response<LotteryGameUserAwardGroupQueryResponse> response = new Response<LotteryGameUserAwardGroupQueryResponse>(request);

		
		List<UserBetAwardGroupEntity> currentUserAwardGroupList = new ArrayList<UserBetAwardGroupEntity>();

		GameUserAwardGroupLoginResponse result = new GameUserAwardGroupLoginResponse();
		List<UserAwardListByLoginStruc> ualss = new ArrayList<UserAwardListByLoginStruc>();
		UserAwardListByLoginStruc uals = new UserAwardListByLoginStruc();

		try {
			for (UserBetAwardGroupEntity entity : currentUserAwardGroupList) {
				uals = DTOConvert.userAwardGroupEntity2UserAwardListByLoginStruc(entity);
				ualss.add(uals);
			}

			result.setUserAwardListByBetStruc(ualss);
		} catch (Exception e) {
			log.error("登录查询用户投注奖金组异常 ", e);
			throw e;
		}

		log.info("登录查询用户投注奖金组完成。");
		return response;
	}
	
	/**
	 * 当进入投注页面时,显示可以设置的投注奖金组列表,参见BMI020
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/queryGameUserAwardGroupByLotteryId")
	@ResponseBody
	public Response<GameUserBetAwardGroupResponse> queryGameUserAwardGroupByLotteryId(
			@RequestBody @ValidRequestBody Request<GameUserBetAwardGroupRequest> request) throws Exception {

		log.info("查询设置投注奖金组列表信息......");
		Response<GameUserBetAwardGroupResponse> response = new Response<GameUserBetAwardGroupResponse>(request);

		long subUserid = request.getBody().getParam().getUserid();
		long lotteryid = request.getBody().getParam().getLotteryid();

		List<UserBetAwardGroupEntity> currentUserAwardGroupList = new ArrayList<UserBetAwardGroupEntity>();

		GameUserBetAwardGroupResponse result = new GameUserBetAwardGroupResponse();
		List<UserAwardListByBetStruc> ualss = new ArrayList<UserAwardListByBetStruc>();
		UserAwardListByBetStruc uals = new UserAwardListByBetStruc();

		try {
			currentUserAwardGroupList = gameUserAwardGroupService.queryGameBetAwardGroup(subUserid, lotteryid);
			//多獎金組時,才需要做調整
			if(currentUserAwardGroupList!=null && currentUserAwardGroupList.size()>1){
				currentUserAwardGroupList = gameUserAwardGroupService.checkMaxAward(currentUserAwardGroupList);				
			}
			
			for (UserBetAwardGroupEntity entity : currentUserAwardGroupList) {
				uals = DTOConvert.userAwardGroupEntity2UserAwardListByBetStruc(entity);
				ualss.add(uals);
			}

			result.setUserAwardListByBetStruc(ualss);
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询设置投注奖金组列表信息异常 ", e);
			throw e;
		}

		log.info("查询设置投注奖金组列表信息完成。");
		return response;
	}

	/** 
	 * 投注页面获取用户当前彩种的奖金组李诶奥 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryLotteryGameUserAwardGroupByLotteryIdAndUserId")
	@ResponseBody
	public Response<LotteryGameUserAwardGroupQueryResponse> queryLotteryGameUserAwardGroupByLotteryIdAndUserId(
			@RequestBody @ValidRequestBody Request<LotteryGameUserAwardGroupQueryRequest> request) throws Exception {

		log.info("投注页面获取用户当前彩种的奖金组查询 ......");
		Response<LotteryGameUserAwardGroupQueryResponse> response = new Response<LotteryGameUserAwardGroupQueryResponse>(
				request);

		long userId = request.getBody().getParam().getUserId();
		long lotteryid = request.getBody().getParam().getLotteryId();

		List<GameUserAwardGroup> currentUserAwardGroupList = new ArrayList<GameUserAwardGroup>();

		LotteryGameUserAwardGroupQueryResponse result = new LotteryGameUserAwardGroupQueryResponse();
		List<LotteryGameUserAwardListStruc> ualss = new ArrayList<LotteryGameUserAwardListStruc>();

		try {
			currentUserAwardGroupList = gameUserAwardGroupService.queryLotteryGameUserAwardGroupByLotteryIdAndUserId(
					userId, lotteryid);
			if (!currentUserAwardGroupList.isEmpty()) {
				for (GameUserAwardGroup entity : currentUserAwardGroupList) {
					GameAwardGroup awardGroup=gameAwardGroupService.queryById(entity.getSysGroupAwardId());
					LotteryGameUserAwardListStruc struc = new LotteryGameUserAwardListStruc(); 
					struc.setAwardGroupId(entity.getId());
					struc.setAwardName(entity.getAwardName());
					struc.setBetType(entity.getBetType());
					struc.setCreateTime(entity.getCreateTime());
					struc.setUpdateTimte(entity.getUpdateTime());
					struc.setDirectRet(entity.getDirectRet());
					struc.setThreeoneRet(entity.getThreeoneRet());
					struc.setSuperRet(entity.getSuperRet());
					struc.setGid(entity.getSysGroupAwardId());
					struc.setAwardGroupRetStatus(awardGroup.getAwardRetStatus());
					struc.setLhcYear(entity.getLhcYear());
					struc.setLhcColor(entity.getLhcColor());
					struc.setLhcFlatcode(entity.getLhcFlatcode());
					struc.setLhcHalfwave(entity.getLhcHalfwave());
					struc.setLhcOneyear(entity.getLhcOneyear());
					struc.setLhcNotin(entity.getLhcNotin());
					struc.setLhcContinuein23(entity.getLhcContinuein23());
					struc.setLhcContinuein4(entity.getLhcContinuein4());
					struc.setLhcContinuein5(entity.getLhcContinuein5());
					struc.setLhcContinuenotin23(entity.getLhcContinuenotin23());
					struc.setLhcContinuenotin4(entity.getLhcContinuenotin4());
					struc.setLhcContinuenotin5(entity.getLhcContinuenotin5());
					struc.setLhcContinuecode(entity.getLhcContinuecode());
					ualss.add(struc);
				}
			}

			result.setUserAwardListStruc(ualss);
			response.setResult(result);
		} catch (Exception e) {
			log.error("投注页面获取用户当前彩种的奖金组查询 异常 ", e);
			throw e;
		}

		log.info("投注页面获取用户当前彩种的奖金组查询 完成。");
		return response;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/updateGameAwardUserGroupById")
	@ResponseBody
	public Object updateGameAwardUserGroupById(
			@RequestBody @ValidRequestBody Request<SaveProxyBetGameAwardGroupRequest> request) throws Exception {
			log.info("update game award user group start......");
			Response response = new Response(request);
			Long userAwardGroupId = request.getBody().getParam().getAwardGroupId();
			Long userid = request.getBody().getParam().getUserid();
			Long lotteryid = request.getBody().getParam().getLotteryid();
			try {
				gameUserAwardGroupService.updateGameUserAwardGroupById(userAwardGroupId, userid, lotteryid);
			} catch(UserGameAwardConfigErrorException e){
				log.error("已配置奖金组 ", e); 
				response.getHead().setStatus(2L);
			}catch (Exception e) {
				log.error("保存代理投注奖金组异常 ", e); 
				response.getHead().setStatus(3L);
			}
			log.info("update game award user group end......");
			return response;
	}
	/**
	 * 保存代理投注奖金组,参见BMI019
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/saveProxyBetGameAwardGroup")
	@ResponseBody
	public Object saveProxyBetGameAwardGroup(
			@RequestBody @ValidRequestBody Request<SaveProxyBetGameAwardGroupRequest> request) throws Exception {

		log.info("保存代理投注奖金组......");
		Response response = new Response(request);
		Long userAwardGroupId = request.getBody().getParam().getAwardGroupId();
		Long userid = request.getBody().getParam().getUserid();
		Long lotteryid = request.getBody().getParam().getLotteryid();

		log.info("保存代理投注奖金组 userid=" + userid + ",userAwardGroupId=" + userAwardGroupId);

		try {
			gameUserAwardGroupService.modifyGameUserAwardGroupById(userAwardGroupId, userid, lotteryid);
		} catch(UserGameAwardConfigErrorException e){
			log.error("已配置奖金组 ", e); 
			response.getHead().setStatus(2L);
		}catch (Exception e) {
			log.error("保存代理投注奖金组异常 ", e); 
			response.getHead().setStatus(3L);
		}

		log.info("保存代理投注奖金组信息完成。");
		return response;
	}

	/**
	 * 4.43.5 修改奖金组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/modifyGameUserAwardGroup")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Object modifyGameUserAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupModifyRequest> request) throws Exception {

		log.info("开始修改用户奖金组......");
		Response response = new Response(request);
		long userid = request.getBody().getParam().getUserid();
		List<UserAwardListStruc> ualss = request.getBody().getParam().getUserAwardListStruc();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		UserAwardGroupEntity entity = new UserAwardGroupEntity();

		for (UserAwardListStruc uals : ualss) {
			entity = DTOConvert.userAwardListStruc2UserAwardGroupEntity(uals, userid);
			userAwardGroupList.add(entity);
		}
		try {
			gameUserAwardGroupService.batchModifyGameUserAwardGroup(userAwardGroupList);

			try{
				for(UserAwardListStruc v : ualss){
					if(v.getLotteryId() == 88101 || v.getLotteryId() == 88102){
						GameModifyAwardUserGroup gm = new GameModifyAwardUserGroup();
						gm.setDirectRet(v.getDirectRet().longValue());
						gm.setLotteryId(v.getLotteryId());
						gm.setStatus(v.getStatus().longValue());
						gm.setSysAwardGroupId(Long.valueOf(v.getAwardGroupId()));
						gm.setThreeoneRet(v.getThreeoneRet().longValue());
						gm.setUserId(userid);
						List<GameModifyAwardUserGroup> param = new ArrayList<GameModifyAwardUserGroup>();
						param.add(gm);
						SubSysModifyGameUserAwardRequestDTO requestDTO = new SubSysModifyGameUserAwardRequestDTO();
						requestDTO.setModifyAwarList(param);
					}
				}
			}catch(Exception e){
				log.error("修改萬國返點錯誤 userId="+userid);;
			}
		} catch (Exception e) {
			log.error("修改用户奖金组异常 ", e);
			throw e;
		}

		log.info("修改用户奖金组完成。");
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/changeBonusByUserId")
	@ResponseBody
	//此方法当未使用
	public Object changeGameUserAwardGroup(@RequestBody @ValidRequestBody Request<GameUserAwardGroupModifyRequest> request) throws Exception{
		
		log.info("开始修改用户奖金组......");
		Response response = new Response(request);
		long userid = request.getBody().getParam().getUserid();
		List<UserAwardListStruc> ualss = request.getBody().getParam().getUserAwardListStruc();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		UserAwardGroupEntity entity = new UserAwardGroupEntity();

		for (UserAwardListStruc uals : ualss) {
			entity = DTOConvert.userAwardListStruc2UserAwardGroupEntity(uals, userid);
			userAwardGroupList.add(entity);
		}
		try {
			gameUserAwardGroupService.chageGameUserAwardGroup(userAwardGroupList, userid);

		} catch (Exception e) {
			log.error("修改用户奖金组异常 ", e);
			throw e;
		}

		log.info("修改用户奖金组完成。");
		return response;
	}

	/**
	 * 5.4.4	开户分配奖金组 (UMI004)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/openAccountDetailedConfigAwardGroup")
	@ResponseBody
	public Object openAccountDetailedConfigAwardGroup(
			@RequestBody @ValidRequestBody Request<OpenAccountDetailedConfigRequest> request) throws Exception {

		log.info("开户分配奖金组......");
		Response response = new Response(request);
		long currentUserId = request.getHead().getUserId();
		long userid = request.getBody().getParam().getUserid();
		List<UserAwardListStruc> ualss = request.getBody().getParam().getUserAwardListStruc();
		long userLvl = request.getBody().getParam().getUserLvl();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		UserAwardGroupEntity entity = new UserAwardGroupEntity();

		for (UserAwardListStruc uals : ualss) {
			entity = DTOConvert.userAwardListStruc2UserAwardGroupEntity(uals, userid);
			userAwardGroupList.add(entity);
		}
		try {
			gameUserAwardGroupService.openAccountAssignAwardGroup(userAwardGroupList, currentUserId, userLvl);

		} catch (Exception e) {
			log.error("开户分配奖金组异常 ", e);
			throw e;
		}

		log.info("开户分配奖金组完成。");
		return response;
	}

	/**
	 * 4.43.9 快捷设置分配奖金组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/openAccountQuickConfigAwardGroup")
	@ResponseBody
	public Object openAccountQuickConfigAwardGroup(
			@RequestBody @ValidRequestBody Request<GameUserAwardGroupModifyRequest> request) throws Exception {

		log.info("开始修改用户奖金组......");
		Response response = new Response(request);
		long userid = request.getBody().getParam().getUserid();
		List<UserAwardListStruc> ualss = request.getBody().getParam().getUserAwardListStruc();

		List<UserAwardGroupEntity> userAwardGroupList = new ArrayList<UserAwardGroupEntity>();
		UserAwardGroupEntity entity = new UserAwardGroupEntity();

		for (UserAwardListStruc uals : ualss) {
			entity = DTOConvert.userAwardListStruc2UserAwardGroupEntity(uals, userid);
			userAwardGroupList.add(entity);
		}
		try {
			gameUserAwardGroupService.batchModifyGameUserAwardGroup(userAwardGroupList);

		} catch (Exception e) {
			log.error("修改用户奖金组异常 ", e);
			throw e;
		}

		log.info("修改用户奖金组完成。");
		return response;
	}

	/**
	 * 查询用户奖金组金额
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectActualBounds")
	@ResponseBody
	//此方法当未使用
	public Response<GameUserAwardActualBoundsQueryResponse> selectActualBounds(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<GameUserAwardActualBoundsQueryRequest> request)
			throws Exception {
		log.info("查询用户奖金组金额 start...");
		Response<GameUserAwardActualBoundsQueryResponse> resp = new Response<GameUserAwardActualBoundsQueryResponse>();
		GameUserAwardActualBoundsQueryResponse result = new GameUserAwardActualBoundsQueryResponse();

		Integer lotteryid = request.getBody().getParam().getLotteryid();
		Long gameGroupCode = request.getBody().getParam().getGameGroupCode();
		Long gameSetCode = request.getBody().getParam().getGameSetCode();
		Long betMethodCode = request.getBody().getParam().getBetMethodCode();
		Long userId = request.getHead().getUserId();

		try {
			Long actualBounds = gameUserAwardGroupService.selectActualBoundsByCodes(lotteryid, userId, gameGroupCode,
					gameSetCode, betMethodCode);
			result.setActualBounds(actualBounds);
			resp.setResult(result);
		} catch (Exception e) {
			log.error("查询用户奖金组金额 error...");
			throw e;
		}
		log.info("查询用户奖金组金额 end...");

		return resp;
	}

}
