package com.winterframework.firefrog.help.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.exception.SensitiveWordException;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.firefrog.help.service.IHelpService;
import com.winterframework.firefrog.help.web.dto.DTOConverter;
import com.winterframework.firefrog.help.web.dto.HelpDeleteRequest;
import com.winterframework.firefrog.help.web.dto.HelpDetailRequest;
import com.winterframework.firefrog.help.web.dto.HelpFeedBackQueryResponse;
import com.winterframework.firefrog.help.web.dto.HelpFeedBackRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: HelpController 
* @Description: 帮助后端接口类
* @author Tod
* @date 2013-9-24 下午1:37:56 
*
 */
@Controller("helpController")
@RequestMapping("/help")
public class HelpController {

	private static final Logger logger = LoggerFactory.getLogger(HelpController.class);

	@Resource(name = "helpServiceImpl")
	private IHelpService helpService;
    @Resource(name = "globalSensitiveWordServiceImpl")
    private GlobalSensitiveWordService globalSensitiveWordService;
	/**
	 * 
	* @Title: createHelp 
	* @Description: 创建帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createHelp")
	public @ResponseBody
	Response<Object> createHelp(@RequestBody @ValidRequestBody Request<HelpStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		HelpStruc helpStruc = request.getBody().getParam();
		try {
            helpStruc.setTitle(globalSensitiveWordService.replace(helpStruc.getTitle(), SensitiveWord.Type.help));
            helpStruc.setContent(globalSensitiveWordService.replace(helpStruc.getContent(), SensitiveWord.Type.help));
            helpStruc.setPreface(globalSensitiveWordService.replace(helpStruc.getPreface(), SensitiveWord.Type.help));
			Help help = DTOConverter.transHelpStruc2Help(helpStruc, null);
			helpService.createHelp(help);
		} catch (SensitiveWordException e) {
            response.getHead().setStatus(e.getStatus());
        } catch (Exception e) {
			logger.error("createHelp error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: modifyHelp 
	* @Description: 修改帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/modifyHelp")
	public @ResponseBody
	Response<Object> modifyHelp(@RequestBody Request<HelpStruc> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		HelpStruc helpStruc = request.getBody().getParam();
		Help oriHelp = helpService.queryHelpDetail(helpStruc.getId());
		try {
            helpStruc.setTitle(globalSensitiveWordService.replace(helpStruc.getTitle(), SensitiveWord.Type.help));
            helpStruc.setContent(globalSensitiveWordService.replace(helpStruc.getContent(), SensitiveWord.Type.help));
            helpStruc.setPreface(globalSensitiveWordService.replace(helpStruc.getPreface(), SensitiveWord.Type.help));
            Help help = DTOConverter.transHelpStruc2Help(helpStruc, oriHelp);
			helpService.modifyHelp(help);
		} catch (SensitiveWordException e) {
            response.getHead().setStatus(e.getStatus());
        } catch (Exception e) {
			logger.error("modifyHelp error", e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/updateBrowsenum")
	public void updateBrowsenum(@RequestParam("helpId") Long helpId) throws Exception {
		Help oriHelp = helpService.queryHelpDetail(helpId);
		try {
			//Help help = DTOConverter.transHelpStruc2Help(helpStruc, oriHelp);
			oriHelp.setBrowsenum(oriHelp.getBrowsenum()+1);
			helpService.updateBrowsenum(oriHelp);
		} catch (Exception e) {
			logger.error("modifyHelp error", e);
			throw e;
		}
	}
	
	/**
	 * 
	* @Title: deleteHelp 
	* @Description: 删除帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteHelp")
	public @ResponseBody
	Response<Object> deleteHelp(@RequestBody Request<HelpDeleteRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		HelpDeleteRequest delReq = request.getBody().getParam();
		List<Long> ids = delReq.getIds();
		try {
			for (Long id : ids) {
				helpService.deleteHelp(id);
			}
		} catch (Exception e) {
			logger.error("deleteHelp error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryHelpDetail 
	* @Description: 查询帮助详情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryHelpDetail")
	public @ResponseBody
	Response<HelpStruc> queryHelpDetail(@RequestBody Request<HelpDetailRequest> request) throws Exception {
		Response<HelpStruc> response = new Response<HelpStruc>(request);
		HelpDetailRequest detailReq = request.getBody().getParam();
		try {
			Help help = helpService.queryHelpDetail(detailReq.getHelpId());
			//help.setBrowsenum(help.getBrowsenum()==null?1:help.getBrowsenum()+1);
			helpService.updateBrowsenum(help);
			HelpStruc helpStruc = DTOConverter.transHelp2HelpStruc(help);
			response.setResult(helpStruc);
		} catch (Exception e) {
			logger.error("queryHelpDetail error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryHelp 
	* @Description: 根据条件查询帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryHelp")
	public @ResponseBody
	Response<List<HelpStruc>> queryHelp(@RequestBody Request<HelpQueryRequest> request) throws Exception {
		Response<List<HelpStruc>> response = new Response<List<HelpStruc>>(request);
		PageRequest<HelpQueryRequest> pageRequest = null;
		if (request.getBody().getPager() == null) {
			//临时解决方案，前台首页最毒显示100个热门彩种
			pageRequest = PageConverterUtils.getRestPageRequest(1, 100);
		} else {
			pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager().getStartNo(), request
					.getBody().getPager().getEndNo());
		}
		HelpQueryRequest searchDo = request.getBody().getParam();
		//如果是首页的热门彩种查询,重新设置查询数量
		if (searchDo != null && searchDo.getIsSearchHotlottery() != null && searchDo.getIsSearchHotlottery() == 1) {
			pageRequest = PageConverterUtils.getRestPageRequest(1, 6);
		}
		
		
		if(searchDo.getCateId()!=null)
		searchDo.setCateId(searchDo.getCateId() < 0 ? null : searchDo.getCateId());
		if(searchDo.getCateId2()!=null)
		searchDo.setCateId2(searchDo.getCateId2() < 0 ? null : searchDo.getCateId2());
		if(searchDo.getIsRec()!=null)
		searchDo.setIsRec(searchDo.getIsRec() < 0 ? null : searchDo.getIsRec());
		if(searchDo.getOrderBy() == null){
			searchDo.setOrderBy("no desc");
		}
		pageRequest.setSearchDo(searchDo);
		pageRequest.setSortColumns(searchDo.getOrderBy());
		try {
			Page<Help> page = helpService.queryHelp(pageRequest);
			List<Help> helpList = page.getResult();
			List<HelpStruc> helpStrucs = new ArrayList<HelpStruc>();
			for (Help help : helpList) {
				helpStrucs.add(DTOConverter.transHelp2HelpStruc(help));
			}
			response.setResult(helpStrucs);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryHelp error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: createFeedback 
	* @Description: 创建帮助反馈
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createFeedback")
	public @ResponseBody
	Response<Object> createFeedback(@RequestBody Request<HelpFeedBackRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		HelpFeedBackRequest feedbackReq = request.getBody().getParam();
		Long uId = request.getHead().getUserId();
		try {
			HelpFeedback feedback = DTOConverter.transFeedReq2HelpFeedback(feedbackReq, uId);
			helpService.createFeedback(feedback);
		} catch (Exception e) {
			logger.error("createFeedback error", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryHelp 
	* @Description: 根据条件查询帮助
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFeedback")
	@ResponseBody
	public Response<List<HelpFeedBackQueryResponse>> queryFeedback(@RequestBody Request<HelpFeedBackRequest> request)
			throws Exception {
		Response<List<HelpFeedBackQueryResponse>> response = new Response<List<HelpFeedBackQueryResponse>>(request);
		HelpFeedBackRequest feedbackReq = request.getBody().getParam();
		Long helpId = feedbackReq.getHelpId();
		try {
			List<HelpFeedback> helpFeedbacks = helpService.queryUnsolvedFeedback(helpId);
			List<HelpFeedBackQueryResponse> list = new ArrayList<HelpFeedBackQueryResponse>();
			HelpFeedBackQueryResponse few = new HelpFeedBackQueryResponse();
			HelpFeedBackQueryResponse many = new HelpFeedBackQueryResponse();
			HelpFeedBackQueryResponse complex = new HelpFeedBackQueryResponse();
			HelpFeedBackQueryResponse other = new HelpFeedBackQueryResponse();
			few.setCount(0l);
			many.setCount(0l);
			complex.setCount(0l);
			other.setCount(0l);
			few.setReasonId(0l);
			many.setReasonId(1l);
			complex.setReasonId(2l);
			other.setReasonId(3l);
			list.add(few);
			list.add(many);
			list.add(complex);
			list.add(other);
			List<String> reason = new ArrayList<String>();
			other.setReason(reason);
			for (HelpFeedback helpFeedback : helpFeedbacks) {
				if (helpFeedback.getReasonId().ordinal() == 0) {
					few.setCount(Long.valueOf(few.getCount())+1);
				} else if (helpFeedback.getReasonId().ordinal() == 1) {
					many.setCount(Long.valueOf(many.getCount())+1);
				} else if (helpFeedback.getReasonId().ordinal() == 2) {
					complex.setCount(Long.valueOf(complex.getCount())+1);
				} else if (helpFeedback.getReasonId().ordinal() == 3) {
					other.setCount(Long.valueOf(other.getCount())+1);
					reason.add(helpFeedback.getReasonContent());
				}
			}
			response.setResult(list);
		} catch (Exception e) {
			logger.error("queryFeedback error", e);
			throw e;
		}
		return response;
	}

}
