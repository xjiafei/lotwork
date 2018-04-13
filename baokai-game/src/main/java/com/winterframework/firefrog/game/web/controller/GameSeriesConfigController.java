package com.winterframework.firefrog.game.web.controller;

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
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.EditSeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigCheckRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigDTO;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigPublishRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigVedioSourceRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.LotteryListStruc;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameSeriesConfigController 
* @Description:运营参数操作类
* @author Richard & Alan
* @date 2013-9-17 下午5:28:04 
*
 */
@Controller("gameSeriesConfigController")
@RequestMapping("/game")
public class GameSeriesConfigController {

	private Logger log = LoggerFactory.getLogger(GameSeriesConfigController.class);

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	/**
	 * 
	* @Title: queryGameSeriesConfig 
	* @Description: 查询运营参数信息
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGameSeriesConfig")
	@ResponseBody
	public Response<QuerySeriesConfigResponse> queryGameSeriesConfig(
			@RequestBody @ValidRequestBody Request<QuerySeriesConfigRequest> request) throws Exception {

		Response<QuerySeriesConfigResponse> response = new Response<QuerySeriesConfigResponse>(request);
		try {

			if (null != request.getBody()) {

				QuerySeriesConfigRequest configRequest = request.getBody().getParam();

				GameSeriesConfigEntity entity = configService.queryGameSeriesConfigByLotteryId(configRequest
						.getLotteryid());

				QuerySeriesConfigResponse config = DTOConvert.GameSeriesConfigEntity2QuerySeriesConfigResponse(entity);
				if(configRequest.getUserId()!=null){
					User user = userCustomerDao.queryUserById(configRequest.getUserId());
					if(user!=null){
						config.setIsSupport2000(user.getSuperPairStatus()==null?0:user.getSuperPairStatus());
						config.setUserNickName(user.getNickName());
						config.setHeadImg(user.getHeadImg());
					}
				}
				response.setResult(config);

			}

		} catch (Exception e) {

			log.error("查询运营参数信息错误：", e);
			throw e;
		}

		return response;
	}

	/**
	 * 
	* @Title: editGameSeriesConfig 
	* @Description: 修改运营参数信息
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/editGameSeriesConfig")
	@ResponseBody
	public Response<EditSeriesConfigResponse> editGameSeriesConfig(
			@RequestBody @ValidRequestBody Request<EditSeriesConfigRequest> request) throws Exception {

		Response<EditSeriesConfigResponse> response = new Response<EditSeriesConfigResponse>(request);

		try {

			if (null != request.getBody()) {

				EditSeriesConfigRequest edit = request.getBody().getParam();

				GameSeriesConfigDTO dto = new GameSeriesConfigDTO();

				dto.setLotteryid(edit.getLotteryid());
				dto.setBackoutRatio(edit.getBackoutRatio() * 100);//页面已经控制不能是小数,实际相当于 先除以100 再乘以10000  因为前台数据是百分比
				dto.setBackoutStartFee(edit.getBackoutStartFee() * 10000);
				//dto.setIssuewarnAheadOpenaward(edit.getIssuewarnAheadOpenaward());
				dto.setIssuewarnBackoutTime(edit.getIssuewarnBackoutTime());
				//dto.setIssuewarnDelayOpenaward(edit.getIssuewarnDelayOpenaward());
				dto.setIssuewarnExceptionTime(edit.getIssuewarnExceptionTime());
				//dto.setIssuewarnNotOpenaward(edit.getIssuewarnNotOpenaward());
				dto.setLotteryid(edit.getLotteryid());
				dto.setEmail(edit.getEmail());
				dto.setIssuewarnUserBackoutTime(edit.getIssuewarnUserBackoutTime());
				configService.editGameSeriesConfig(dto);
			}

		} catch (Exception e) {
			log.error("修改运营参数信息错误：", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: auditGameSeriesConfig 
	* @Description: 审核运营参数信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditGameSeriesConfig")
	@ResponseBody
	public Object auditGameSeriesConfig(@RequestBody @ValidRequestBody Request<GameSeriesConfigCheckRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			Long lotteryid = request.getBody().getParam().getLotteryid();
			Long auditType = request.getBody().getParam().getAuditType();

			configService.auditGameSeriesConfig(lotteryid, auditType);
		} catch (Exception e) {
			log.error("审核运营参数信息错误：", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: auditGameSeriesConfig 
	* @Description: 发布运营参数信息
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/releaseGameSeriesConfig")
	@ResponseBody
	public Object releaseGameSeriesConfig(@RequestBody @ValidRequestBody Request<GameSeriesConfigPublishRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			Long lotteryid = request.getBody().getParam().getLotteryid();
			Long publishType = request.getBody().getParam().getPublishType();

			configService.releaseGameSeriesConfig(lotteryid, publishType);
		} catch (Exception e) {
			log.error("发布运营参数信息错误：", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 
	* @Title: vedioSourceConfig 
	* @Description: 视频源增删改
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/vedioSourceConfig")
	@ResponseBody
	public Object vedioSourceConfig(@RequestBody @ValidRequestBody Request<GameSeriesConfigVedioSourceRequest> request)
			throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		try {
			configService.vedioSourceConfig(request.getBody().getParam());
		} catch (Exception e) {
			log.error("发布运营参数信息错误：", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryGameSeries 
	* @Description: 彩种列表查询
	* @param reqeust
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryGameSeries")
	@ResponseBody
	public Response<GameSeriesResponse> queryGameSeries(@RequestBody Request<GameSeriesRequest> request)
			throws Exception {

		Response<GameSeriesResponse> response = new Response<GameSeriesResponse>(request);

		try {

			if (null != request.getBody()) {

				GameSeriesResponse gameResponse = new GameSeriesResponse();
				GameSeriesRequest gameSeries = request.getBody().getParam();
				List<GameSeries> list = configService.getAllGameSeries(gameSeries.getLotteryId(),
						gameSeries.getStatus());

				List<LotteryListStruc> listStrucs = new ArrayList<LotteryListStruc>();
				for (GameSeries game : list) {
					LotteryListStruc struc = new LotteryListStruc();

					struc.setLotteryHelpDes(game.getLotteryHelpDes());
					struc.setLotteryid(game.getLotteryid());
					struc.setLotteryName(game.getLotteryName());
					struc.setLotterySeriesCode(game.getLotterySeriesCode());
					struc.setLotteryTypeCode(game.getLotteryTypeCode());
					struc.setMiniLotteryProfit(game.getMiniLotteryProfit());
					struc.setStatus(game.getStatus());
					struc.setCreateTime(DataConverterUtil.convertDate2Long(game.getCreateTime()));
					if (null != game.getUpdateTime()) {
						struc.setUpdateTime(DataConverterUtil.convertDate2Long(game.getUpdateTime()));
					}
					struc.setOperationChangeStatus(getOperationChangStatus(game.getChangeStatus()));
					struc.setOperationLock(getOperationLock(game.getLotteryIsLock()));
					struc.setOperator(game.getOperator());
					listStrucs.add(struc);
				}

				gameResponse.setList(listStrucs);
				response.setResult(gameResponse);
			}

		} catch (Exception e) {

			log.error("queryGameSeries error:", e);
			throw e;
		}

		return response;
	}

	private int[] getOperationLock(Long operationLock) {
		int[] operationLocks = new int[8];
		if (operationLock != null) {
			String operationLockStr = Long.toBinaryString(operationLock);
			operationLocks = new int[operationLockStr.length()];
			for (int i = 0; i < operationLockStr.length(); i++) {
				operationLocks[i] = Integer.parseInt(String.valueOf(operationLockStr.charAt(i)));
			}
		}
		return operationLocks;
	}

	private int[] getOperationChangStatus(Long changeStatus) {
		int[] changeStatuses = new int[] { 1, 1, 1, 1, 1, 1, 1, 1 };
		if (changeStatus != null) {
			String changeStatusStr = String.valueOf(changeStatus);
			changeStatuses = new int[changeStatusStr.length()];
			for (int i = 0; i < changeStatusStr.length(); i++) {
				changeStatuses[i] = Integer.parseInt(String.valueOf(changeStatusStr.charAt(i)));
			}
		}
		return changeStatuses;
	}
}
