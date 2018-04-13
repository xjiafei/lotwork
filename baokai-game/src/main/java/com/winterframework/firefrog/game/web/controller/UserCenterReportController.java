package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.game.service.IGameUserCenterReportService;
import com.winterframework.firefrog.game.web.dto.ChainUserStruc;
import com.winterframework.firefrog.game.web.dto.CurrentUserCenterReportReponse;
import com.winterframework.firefrog.game.web.dto.CurrentUserCenterReportRequest;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.SubUserReportResponse;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexReponse;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexRequest;
import com.winterframework.firefrog.game.web.dto.UserCenterReportStruc;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: UserCenterReportController 
* @Description: 用户中心盈亏报表查询controller
* @author david 
* @date 2013-9-17 下午3:27:57 
*  
*/
@Controller("userCenterReportController")
@RequestMapping("/game")
//TODO gxh
public class UserCenterReportController {

	private Logger log = LoggerFactory.getLogger(UserCenterReportController.class);

	@Resource(name = "gameUserCenterReportServiceImpl")
	private IGameUserCenterReportService gameUserCenterReportService;

	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userService;

	/** 
	* @Title: queryUserCenterReportByUserId 
	* @Description: 通过用户id查询报表信息，所有直接下級信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserCenterReportByUserId")
	@ResponseBody
	public Response<SubUserReportResponse> queryUserCenterReportByUserId(
			@RequestBody @ValidRequestBody Request<SubUserReportRequest> request) throws Exception {
		
		log.info("前台报表查看下级用户=/game/queryUserCenterReportByUserId Start");
		
		SubUserReportRequest param = request.getBody().getParam();
		
		String account = param.getAccount();
		String betTypeCode = param.getBetTypeCode();
		Long curUserId = param.getCurUserId();
		Long lotteryId = param.getLotteryId();
		Long queryTime = param.getQueryTime();
		Long userId = param.getUserId();
		
		log.info("=account:"+account);
		log.info("=betTypeCode:"+betTypeCode);
		log.info("=curUserId:"+curUserId);
		log.info("=lotteryId:"+lotteryId);
		log.info("=queryTime:"+queryTime);
		log.info("=userId:"+userId);
		
		Response<SubUserReportResponse> response = new Response<SubUserReportResponse>(request);
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<SubUserReportRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		pr.setSortColumns(" UC.USER_CHAIN asc");
		Page<UserCenterReportInfo> page = null;
		List<UserCenterReportInfo> ucrs = null;
		List<UserCenterReportStruc> ucrStrucs = new ArrayList<UserCenterReportStruc>();
		SubUserReportResponse result = new SubUserReportResponse();
		UserCenterReportStruc ucrStruc = null;
		try {
			page = gameUserCenterReportService.queryUserCenterReportByUserId(pr);
			User user = userService.queryUserById(request.getBody().getParam().getUserId());
			ucrs = page.getResult();
			if (ucrs != null && ucrs.size() > 0) {
				for (UserCenterReportInfo ucri : ucrs) {
					ucrStruc = DTOConvert.UserCenterReportInfo2UserCenterReportStruc(ucri);
					ucrStrucs.add(ucrStruc);
				}
			}
			result.setUserCenterReportStrucs(ucrStrucs);

			List<ChainUserStruc> chainUsers = new ArrayList<ChainUserStruc>();

			if (user != null && user.getUserProfile() != null) {
				String chain = StringUtils.trimToEmpty(user.getUserProfile().getUserChain());
				result.setUserChain(chain);

				log.debug("userchain:" + chain);
				if (StringUtils.isNotEmpty(chain)) {
					int currSepIndex = 0;
					int lastSepIndex = 0;
					String chainUserName;
					ChainUserStruc struc;
					// 按顺序遍历chain中的用户名，并根据用户名查询User对象
					while (lastSepIndex < chain.length() && (currSepIndex = chain.indexOf("/", lastSepIndex + 1)) != -1) {
						chainUserName = chain.substring(lastSepIndex + 1, currSepIndex);
						log.debug("currChainUserName:" + chainUserName);
						user = userService.queryUserByName(chainUserName);

						// 产生新的ChainUserStruc对象
						struc = new ChainUserStruc();
						struc.setAccount(user.getUserProfile().getAccount());
						struc.setUserId(user.getId());

						// 对象加入list中
						chainUsers.add(struc);

						lastSepIndex = currSepIndex;
					}
				}
			}
			result.setChainUsers(chainUsers);

			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());

			response.setResultPage(pager);
			//如果查询用户不是当前登录用户的下级，则将查询结果清空
			Long currentId = request.getBody().getParam().getCurUserId();
			User currentUser = userService.queryUserById(currentId);
			if(!user.getUserProfile().getUserChain().contains(currentUser.getUserProfile().getUserChain())){
				response.getBody().getResult().setUserCenterReportStrucs(null);
				ResultPager newPager = new ResultPager();
				response.setResultPage(newPager);
			}

		} catch (Exception e) {
			log.error("用户中心报表查询出错", e);
			throw e;
		}

		log.info("前台报表查看下级用户=/game/queryUserCenterReportByUserId End");
		return response;
	}

	/** 
	* @Title: queryUserCenterReportByComplexCondition 
	* @Description:  报表复杂条件查询/一般查詢
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryUserCenterReportByComplexCondition")
	@ResponseBody
	public Response<UserCenterReportComplexReponse> queryUserCenterReportByComplexCondition(
			@RequestBody @ValidRequestHeader @ValidRequestBody Request<UserCenterReportComplexRequest> request)
			throws Exception {
		log.info("前台报表查询=/game/queryUserCenterReportByComplexCondition Start");
		
		long userId = request.getHead().getUserId();
		String account = request.getBody().getParam().getAccount();
		Long lotteryId = request.getBody().getParam().getLotteryId();
		Long queryTime = request.getBody().getParam().getQueryTime();
		String betTypeCode = request.getBody().getParam().getBetTypeCode();
		String crowdId = request.getBody().getParam().getCrowdId();
		String groupId = request.getBody().getParam().getGroupId();
		String setId = request.getBody().getParam().getSetId();
		String methodId = request.getBody().getParam().getMethodId();
		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();
		
		log.info("=userId:"+userId);
		log.info("=account:"+account);
		log.info("=lotteryId:"+lotteryId);
		log.info("=queryTime:"+queryTime);
		log.info("=betTypeCode:"+betTypeCode);
		log.info("=crowdId:"+crowdId);
		log.info("=groupId:"+groupId);
		log.info("=setId:"+setId);
		log.info("=methodId:"+methodId);
		log.info("sNo:"+sNo);
		log.info("eNo:"+eNo);
		
		Response<UserCenterReportComplexReponse> response = new Response<UserCenterReportComplexReponse>(request);
		
		PageRequest<UserCenterReportComplexRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());
		Page<UserCenterReportInfo> page = null;
		List<UserCenterReportInfo> ucrs = null;
		List<UserCenterReportStruc> ucrStrucs = new ArrayList<UserCenterReportStruc>();
		UserCenterReportComplexReponse result = new UserCenterReportComplexReponse();
		UserCenterReportStruc ucrStruc = null;
		
		try {
			page = gameUserCenterReportService.getUserReportByBetTypeCodeList(pr, userId);
			
			ucrs = page.getResult();
			if (ucrs != null && ucrs.size() > 0) {
				for (UserCenterReportInfo ucri : ucrs) {
					ucrStruc = DTOConvert.UserCenterReportInfo2UserCenterReportStruc(ucri);
					ucrStrucs.add(ucrStruc);
				}
			}
			result.setUserCenterReportStrucs(ucrStrucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());

			response.setResultPage(pager);

			if(account != null && !account.trim().equals("")){
				log.info("userService.queryUserById Start");
				User currentUser = userService.queryUserById(userId);
				log.info("userService.queryUserById End");
				log.info("userService.queryUserByName Start");
				User user = userService.queryUserByName(account);
				log.info("userService.queryUserByName End");
				
				if(user != null && currentUser != null && user.getUserProfile().getUserChain().contains(currentUser.getUserProfile().getUserChain())){
					log.info("查詢用戶Account符合登入者資格");
				}else{
					response.getBody().getResult().setUserCenterReportStrucs(null);
					ResultPager newPager = new ResultPager();
					response.setResultPage(newPager);
					log.info("查詢用戶Account不符合登入者資格");
				}
			}
			
		} catch (Exception e) {
			log.error("用户中心报表查询出错", e);
			throw e;
		}

		log.info("前台报表查询=/game/queryUserCenterReportByComplexCondition End");
		return response;
	}

	/** 
	* @Title: queryCurrentUserReport 
	* @Description: 当前用户的报表信息  默认显示当天資訊
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryCurrentUserReport")
	@ResponseBody
	public Response<CurrentUserCenterReportReponse> queryCurrentUserReport(
			@RequestBody @ValidRequestBody Request<CurrentUserCenterReportRequest> request) throws Exception {
		log.info("前台报表第一次初始化默认查询=/game/queryCurrentUserReport Start");
		Response<CurrentUserCenterReportReponse> response = new Response<CurrentUserCenterReportReponse>(request);
		try {
			UserCenterReportInfo ui = gameUserCenterReportService.getCurrentUserReportByUserId(request.getBody().getParam().getUserId());
			CurrentUserCenterReportReponse ur = new CurrentUserCenterReportReponse();
			ur.setUserCenterReportStrucs(DTOConvert.UserCenterReportInfo2UserCenterReportStruc(ui));
			response.setResult(ur);
		} catch (Exception e) {
			log.error("查询当前用户报表信息错误 ", e);
			throw e;
		}
		
		log.info("前台报表第一次初始化默认查询=/game/queryCurrentUserReport End");
		
		return response;
	}
	
	@RequestMapping(value = "/saveUserHeadImg")
	@ResponseBody
	public Response<Long> saveUserHeadImg(@RequestBody @ValidRequestBody Request<Map<String,String>> request) throws Exception {
		Long userId = request.getHead().getUserId();
		Response<Long> response = new Response<Long>();
		try{
			String headImg = request.getBody().getParam().get("headImg");
			String nickName = request.getBody().getParam().get("nickName");
			if(userService.checkUserNickNameOnly(nickName)){
				userService.saveUserHeadImg(userId, nickName, headImg);
				response.setResult(1l);
			}
			else{
				response.setResult(0l);
			}
		}catch(Exception e){
			response.setResult(2l);
		}
		return response;
	}

}
