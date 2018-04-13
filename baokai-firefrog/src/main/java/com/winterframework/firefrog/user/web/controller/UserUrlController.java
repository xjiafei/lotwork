package com.winterframework.firefrog.user.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.UserUrl;
import com.winterframework.firefrog.user.service.UserUrlManager;
import com.winterframework.firefrog.user.web.controller.game.GameGroup;
import com.winterframework.firefrog.user.web.controller.game.GameGroupReq;
import com.winterframework.firefrog.user.web.controller.game.GameGroups;
import com.winterframework.firefrog.user.web.controller.game.SelfResultPager;
import com.winterframework.firefrog.user.web.dto.UserAwardStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/user/url")
public class UserUrlController {

	@Autowired
	private UserUrlManager userUrlManager;
	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;
	@RequestMapping(value = "/initCreateUrl")
	public @ResponseBody Response<GameGroups> initCreateUrl(
			@RequestBody @ValidRequestHeader Request<GameGroupReq> request)
			throws Exception {

		Response<GameGroups> response = userUrlManager.getGameGroup(request);
		for (com.winterframework.firefrog.user.web.controller.game.GameGroup grpu : response
				.getBody().getResult().getUserAwardListStruc()) {
			grpu.setAwardGroupId(grpu.getSysAwardGrouId());
		}
		return response;

	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public Response<Object> urlCreate(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		//php傳空白來會變成0
		if(StringUtils.isNotBlank(request.getBody().getParam().getQq()) && "0".equals(request.getBody().getParam().getQq())){
			request.getBody().getParam().setQq("");
		}
		Response<Object> rs = new Response<Object>(request);
		UserUrl uu = request.getBody().getParam();
		Long size = uu.getSize();
		if (request.getBody().getParam().getType() == 0) {
			// if create common user
			size = 1L;
		}
		uu.setUuid(UUID.randomUUID().toString());
		while (size-- > 0) {
			userUrlManager.save(request.getBody().getParam());
		}
		UserUrl nuu = new UserUrl();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int days = request.getBody().getParam().getDays().intValue();
		if (days == -1)
			days = 365 * 10;
		c.add(Calendar.DATE, days);
		nuu.setUuid(uu.getUuid());
		List<UserUrl> uuids = userUrlManager.getByEntity(nuu);
		for (UserUrl uuid : uuids) {
			Long pid = Long.valueOf(request.getHead().getUserId() + ""
					+ uuid.getType());
			String key = createKey(uuid.getId(), pid);
			uuid.setCreator(request.getHead().getUserId());
			String url = "id=" + uuid.getId() + "&exp=" + c.getTimeInMillis()
					+ "&pid=" + pid + "&token=" + key;
			if(StringUtils.isNotEmpty(uuid.getQq())){
				url+="&qq="+uuid.getQq();
			}
			uuid.setUrl(url);

			userUrlManager.update(uuid);
		}
		return rs;
	}

	@RequestMapping(value = "/list")
	public @ResponseBody Response<List<UserUrl>> urlList(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		request.getBody().getParam().setCreator(request.getHead().getUserId());
		Response<List<UserUrl>> rs = new Response<List<UserUrl>>(request);
		PageRequest<UserUrl> pr = PageRequest.getRestPageRequest(request
				.getBody().getPager().getStartNo(), request.getBody()
				.getPager().getEndNo());
		pr.setSortColumns("gmtCreated desc");
		pr.setSearchDo(request.getBody().getParam());

		Page<UserUrl> urls = userUrlManager.findByPageRequest(pr);
		rs.setResult(urls.getResult());
		if (urls.getResult() != null)
			for (UserUrl url : urls.getResult()) {
				url.setStrUserawardListStruc(null);
				url.setUserawardListStruc(null);

			}
		SelfResultPager resultPager = new SelfResultPager(
				urls.getThisPageFirstElementNumber(),
				urls.getThisPageLastElementNumber(), urls.getTotalCount());
		rs.setResultPage(resultPager);
		resultPager.setOtherParam(urls.getOtherCount());
		return rs;
	}

	@RequestMapping(value = "/queryUrl")
	public @ResponseBody Response<UserUrl> queryUrl(
			@RequestBody Request<UserUrl> request) throws Exception {
		request.getBody().getParam().setCreator(request.getHead().getUserId());

		Response<UserUrl> rs = new Response<UserUrl>(request);
		PageRequest<UserUrl> pr = PageRequest.getRestPageRequest(request
				.getBody().getPager().getStartNo(), request.getBody()
				.getPager().getEndNo());
		pr.setSearchDo(request.getBody().getParam());
		pr.getSearchDo().setCreator(null);
		List<UserUrl> urls = userUrlManager.getByEntity(request.getBody()
				.getParam());
		if (urls.size() > 0) {
			rs.setResult(urls.get(0));
			rs.getBody().getResult().setStrUserawardListStruc(null);
			rs.getBody().getResult().setUserawardListStruc(null);
		}
		return rs;
	}

	@RequestMapping(value = "/modifyMemo")
	public @ResponseBody Response<Object> urlupdateMemo(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		Response<Object> rs = new Response<Object>(request);
		userUrlManager.update(request.getBody().getParam());
		return rs;
	}

	@RequestMapping(value = "/getById")
	public @ResponseBody Response<UserUrl> getById(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		Response<UserUrl> rs = new Response<UserUrl>(request);
		GameGroupReq value = new GameGroupReq();
		value.setType(0l);
		value.setUserId(0l);
		value.setAwardType(0l);
		Request<GameGroupReq> rq = new Request<GameGroupReq>(); 
		rq.setHead(new RequestHeader());
		rq.setBody(new com.winterframework.modules.web.jsonresult.RequestBody<GameGroupReq>());
		rq.getBody().setParam(value);
		
		UserUrl url = userUrlManager.getById(request.getBody().getParam()
				.getId());
		
		Response<GameGroups> response = userUrlManager.getGameGroup(rq);
		//UserAwardStruc[] struc = url.getUserawardListStruc();
		UserAwardStruc[] struc = new UserAwardStruc[response.getBody().getResult().getUserAwardListStruc().length];
		//解決連結內無設定獎金組時詳情顯示空白
		for(int i=0;i<response.getBody().getResult().getUserAwardListStruc().length;i++){
			Boolean isLottery = false;
			GameGroup v = response.getBody().getResult().getUserAwardListStruc()[i];
			
			for(UserAwardStruc x:url.getUserawardListStruc()){
				
				if(v.getLotteryId().equals(x.getLotteryId()) && v.getAwardName().equals(x.getAwardName())){
					isLottery = true;
					struc[i] = x;
					break;
				}
			}
			if(!isLottery && v.getStatus() == 1){
				UserAwardStruc userAwardStruc = new UserAwardStruc();
				userAwardStruc.setLotterySeriesCode(new BigDecimal(v.getLotterySeriesCode()));
				userAwardStruc.setLotterySeriesName(v.getLotterySeriesName());
				userAwardStruc.setAwardGroupId(v.getAwardGroupId().toString());
				userAwardStruc.setAwardName(v.getAwardName());
				userAwardStruc.setDirectRet(new BigDecimal(0));
				userAwardStruc.setThreeoneRet(new BigDecimal(0));
				userAwardStruc.setSuperRet(new BigDecimal(0));
				userAwardStruc.setStatus(new BigDecimal(v.getStatus()));
				userAwardStruc.setDirectLimitRet(new BigDecimal(0));
				userAwardStruc.setGroupChain(v.getGroupChain());
				userAwardStruc.setThreeLimitRet(new BigDecimal(v.getThreeLimitRet()));
				userAwardStruc.setSuperLimitRet(new BigDecimal(v.getSuperLimitRet()));
				userAwardStruc.setLotteryId(v.getLotteryId());
				//userAwardStruc.setBetTypeCode(v.getBetType().toString());
				userAwardStruc.setMaxDirectRet(0l);
				userAwardStruc.setMaxThreeOneRet(0l);
				userAwardStruc.setMaxSuperRet(0l);
				struc[i] = userAwardStruc;
			}
		}
		url.setUserawardListStruc(struc);
		
		
		rs.setResult(url);
		return rs;
	}

	@RequestMapping(value = "/checkRegisted")
	public @ResponseBody Response<Long> checkRegisted(
			@RequestBody Request<UserUrl> request) throws Exception {
		Response<Long> rs = new Response<Long>(request);
		UserUrl url = userUrlManager.getById(request.getBody().getParam()
				.getId());
		rs.setResult(url.getRegisters());
		return rs;
	}

	@RequestMapping(value = "/getRegisters")
	public @ResponseBody Response<List<String>> checkRegisterd(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		Response<List<String>> rs = new Response<List<String>>(request);
		UserCustomer uc = new UserCustomer();
		uc.setUrlId(request.getBody().getParam().getId());
		List<UserCustomer> url = userCustomerDao.getAllByEntity(uc);
		List<String> ss = new ArrayList<String>();
		for (UserCustomer rr : url) {
			ss.add(rr.getAccount());
		}
		rs.setResult(ss);
		return rs;
	}

	@RequestMapping(value = "/delete")
	public @ResponseBody Response<UserUrl> delte(
			@RequestBody @ValidRequestHeader Request<UserUrl> request)
			throws Exception {
		Response<UserUrl> rs = new Response<UserUrl>(request);
		userUrlManager.removeById(request.getBody().getParam().getId());
		return rs;
	}

	/**
	 * 生成url后缀
	 * 
	 * @param id
	 * @param pid
	 * @return
	 */
	private static String createKey(Long id, Long pid) {
		return StringUtils.substring(
				org.apache.commons.codec.digest.DigestUtils.md5Hex(id + "|"
						+ pid), 28);
	}

	public static void main(String[] s) {
		System.out.println(createKey(11L, 3L));
	}
}
