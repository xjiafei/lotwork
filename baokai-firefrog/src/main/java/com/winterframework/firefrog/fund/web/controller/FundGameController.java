/**   
* @Title: FundAdjustController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-18 上午11:08:06 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityLog;
import com.winterframework.firefrog.active.web.dto.ActivityLogRequest;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVos;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * 
* @ClassName: FundGameController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2013-12-12 上午9:45:50 
*
 */
@Controller("fundGameController")
@RequestMapping(value = "/fund/api/game/")
public class FundGameController {

	private static final Logger logger = LoggerFactory.getLogger(FundGameController.class);

	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;

	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	/**
	 * 
	* @Title: freeze 
	* @Description:  
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/action")
	@ResponseBody
	public Object gameAction(@RequestBody @ValidRequestBody Request<List<FundGameVo>> request) throws Exception {
		Response<List<FundChangeDetail>> response = new Response<List<FundChangeDetail>>(request);
		List<FundGameVo> vos = request.getBody().getParam();
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		try {
			logger.info("before action  " + vos.size());
			fundChangeService.action(vos, maps);
		} catch (Exception e) {
			logger.error("冻结用户游戏币失败", e);
			throw e;
		}
		response.setResult(maps);
		return response;
	}
	
	/** 
	* @Title: saveActivityLog
	* @Description: 用戶活動記錄寫入
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/saveActivityLog")
	@ResponseBody
	public void saveActivityLog(@RequestParam("activityId") String activityId ,@RequestParam("userId") String userId,
			@RequestParam("prize") String prize, @RequestParam("memo") String memo) throws Exception {
		logger.info("activityId : " + activityId);
		logger.info("userId : " + userId);
		logger.info("prize : " + prize);
		logger.info("memo : " + memo);
		
		Date now = Calendar.getInstance().getTime();
		ActivityLog al = new ActivityLog();
		al.setActivityId(Long.parseLong(activityId));
		al.setMemo(memo);
		al.setStatus(1L);
		al.setUserId(Long.parseLong(userId));
		al.setPrize(Long.parseLong(prize));
		al.setAwardTime(now);
		al.setCreateTime(now);
		try{
			activityLogDaoImpl.saveActivityLog(al);
		} catch (Exception e) {
			logger.error("寫入活動紀錄異常", e);
			throw e;
		}
		
		//return response;
	}

	@RequestMapping(value = "/migrate")
	@ResponseBody
	public Object migrate(@RequestBody @ValidRequestBody Request<FundGameVo> request) throws Exception {
		Response<List<FundChangeDetail>> response = new Response<List<FundChangeDetail>>(request);
		FundGameVo vos = request.getBody().getParam();
		if (StringUtils.isNotEmpty(vos.getSn())) {
			vos.setReason("TF-TPXX-2");
			vos.setAmount(Math.abs(vos.getAmount()));
			vos.setNote("来自旧平台迁移失败");
			vos.setSn(null);
		} else {
			vos.setReason("TF-TPXX-1");
			vos.setNote("来自旧平台");
		}
		vos.setIsAclUser(0L);
		//vos.setNote("来自旧平台");
		List<FundGameVo> lists = new ArrayList<FundGameVo>();
		lists.add(vos);
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		try {
			fundChangeService.action(lists, maps);
			response.setResult(maps);
		} catch (Exception e) {
			logger.error("冻结用户游戏币失败", e);
			response.getHead().setStatus(-1);
		}
		return response;
	}

	/**
	 * 
	* @Title: freeze 
	* @Description:  
	* @param request 连续处理4次，减小碰撞
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/actions")
	@ResponseBody
	public Object actions(@RequestBody @ValidRequestBody Request<List<FundGameVos>> request) throws Exception {
		Response<List<FundChangeDetail>> response = new Response<List<FundChangeDetail>>(request);
		List<FundGameVos> vos = request.getBody().getParam();
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		String only = "UUID:" + UUID.randomUUID();
		try {
			logger.debug(only + " begin to action ");
			fundChangeService.actions(vos, maps);
		} catch (Exception e) {
			logger.error("用户操作失败", e);
			throw e;
		}
		response.setResult(maps);
		return response;
	}

	public static void main(String[] ars) {
		String s = "[{\"reason\":\"GM-CRVC-4 \",\"vals\":\"1191|200000\",\"operator\":-1,\"isAclUser\":1},{\"reason\":\"GM-RRHA-2\",\"vals\":\"31|200\",\"operator\":-1,\"isAclUser\":1},{\"reason\":\"GM-RRSX-1\",\"vals\":\"1191|39800\",\"operator\":-1,\"isAclUser\":1},{\"reason\":\"GM-BDRX-4\",\"vals\":\"1191|180000\",\"operator\":-1,\"isAclUser\":1}]";
		Object obj = JsonMapper.nonAlwaysMapper().fromJson(s, List.class);
		System.out.println(1);
	}

	/** 
	 * 创建具体的操作人员
	*/
	private BaseUser getOperatorUser(FundGameVo vo) {
		BaseUser operator = null;
		if (vo.getIsAclUser() == 0) {
			operator = new User();
			operator.setId(vo.getOperator());
		} else {
			operator = new AclUser();
			operator.setId(vo.getOperator());
		}
		return operator;
	}

}
