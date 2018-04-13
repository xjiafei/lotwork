package com.winterframework.firefrog.help.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.CateStruc;
import com.winterframework.firefrog.help.web.dto.CateStrucQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpDeleteRequest;
import com.winterframework.firefrog.help.web.dto.HelpDetailRequest;
import com.winterframework.firefrog.help.web.dto.HelpFeedBackQueryResponse;
import com.winterframework.firefrog.help.web.dto.HelpFeedBackRequest;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.help.web.dto.HelpStrucWithCateName;
import com.winterframework.firefrog.help.web.dto.LotteryCateStruc;
import com.winterframework.firefrog.help.web.dto.LotteryContentStruc;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: AdminHelpManagerController 
* @Description: 后台管理帮助控制类
* @author 你的名字 
* @date 2013-9-24 下午1:48:28 
*
 */
@Controller("adminHelpManager")
@RequestMapping(value = "/helpAdmin")
public class AdminHelpManagerController {

	private static final Logger logger = LoggerFactory.getLogger(AdminHelpManagerController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.createHelp")
	private String createHelp;

	@PropertyConfig(value = "url.help.modifyHelp")
	private String modifyHelp;

	@PropertyConfig(value = "url.help.updateBrowsenum")
	private String updateBrowsenum;

	@PropertyConfig(value = "url.help.queryCategory")
	private String queryCategoryUrl;

	@PropertyConfig(value = "url.help.queryLotteryKnowledgeCategory")
	private String queryLotteryKnowledgeCategory;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelp;

	@PropertyConfig(value = "url.help.queryHelpDetail")
	private String queryHelpDetail;

	@PropertyConfig(value = "url.help.deleteHelp")
	private String deleteHelp;

	@PropertyConfig(value = "url.help.queryHelpFeedback")
	private String queryHelpFeedbackUrl;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "url.imageserver.visit")
	private String urlViewPic;

	/**
	 * 
	* @Title: goCreateHelp 
	* @Description: 初始化创建帮助页面
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/goCreateHelp")
	public ModelAndView goCreateHelp(Long defaultCateId) throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminCreateHelp");
		try {
			Response<List<CateStruc>> resp = httpClient.invokeHttp(urlPath + queryCategoryUrl, createEmptyCateReq(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = resp.getBody().getResult();
			mav.addObject("cateList", cateList);
			mav.addObject("cate2Name", "新建帮助");
			for (CateStruc cateStruc : cateList) {
				mav.addObject("cateId" + cateStruc.getId(), cateStruc.getSubCate());
			}
			mav.addObject("defaultCateId", defaultCateId);
		} catch (Exception e) {
			logger.error("goCreateHelp error.", e);
			throw e;
		}
		return mav;
	}

	@RequestMapping(value = "/queryFeedback")
	@ResponseBody
	public Object queryFeedback(@RequestParam("helpId") Long helpId) throws Exception {
		Response<List<HelpFeedBackQueryResponse>> response = null;
		try {
			HelpFeedBackRequest request = new HelpFeedBackRequest();
			request.setHelpId(helpId);
			response = httpClient.invokeHttp(urlPath + queryHelpFeedbackUrl, request,
					new TypeReference<Response<List<HelpFeedBackQueryResponse>>>() {
					});
		} catch (Exception e) {
			logger.error("query feedback error", e);
		}
		return response;
	}

	/**
	 * 
	* @Title: goViewHelp 
	* @Description: 查看帮助
	* @param helpId
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/goViewHelp")
	public ModelAndView goViewHelp(Long helpId) throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminViewHelp");
		try {
			Response<List<CateStruc>> resp = httpClient.invokeHttp(urlPath + queryCategoryUrl, createEmptyCateReq(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = resp.getBody().getResult();
			HelpDetailRequest req = new HelpDetailRequest();
			req.setHelpId(helpId);
			HelpStruc help = (HelpStruc) httpClient.invokeHttp(urlPath + queryHelpDetail, req, HelpStruc.class)
					.getBody().getResult();
			if (help.getCateId().equals(4l)) {
				Response<List<LotteryCateStruc>> lotResp = httpClient.invokeHttp(urlPath
						+ queryLotteryKnowledgeCategory, new TypeReference<Response<List<LotteryCateStruc>>>() {
				});
				for (int i = 0; i < lotResp.getBody().getResult().size(); i++) {
					//把游戏列表和分类进行配对
					boolean incld = false;
					LotteryCateStruc ss = lotResp.getBody().getResult().get(i);
					for (LotteryContentStruc s : help.getLotteryContentStruc()) {
						if (s.getId().equals(ss.getId())) {
							incld = true;
							break;
						}

					}
					if (!incld) {
						help.getLotteryContentStruc().add(new LotteryContentStruc());
					}
				}

				mav.setViewName("help/helpAdminViewLottery");
				mav.addObject("lotCateList", lotResp.getBody().getResult());
			}
			HelpStrucWithCateName help2 = new HelpStrucWithCateName();
			BeanUtilsBean.getInstance().copyProperties(help2, help);
			if (cateList != null)
				for (int i = 0; i < cateList.size(); i++) {
					CateStruc cate = cateList.get(i);
					if (cate.getId().equals(help.getCateId())) {
						help2.setCateName(cate.getName());
						for (CateStruc cate2 : cate.getSubCate()) {
							if (cate2.getId().equals(help.getCateId2())) {
								help2.setCateName2(cate2.getName());
							}
						}
					} else if (cate.getId().equals(help.getCateId2())) {
						help2.setCateName2(cate.getName());
						continue;
					}
				}

			mav.addObject("cateList", cateList);
			mav.addObject("help", help2);
			mav.addObject("cate2Name", "帮助管理");
			mav.addObject("urlViewPic", urlViewPic);
			for (CateStruc cate : cateList) {
				mav.addObject("cateId" + cate.getId(), cate.getSubCate());
			}
		} catch (Exception e) {
			logger.error("goViewHelp error.", e);
			throw e;
		}
		return mav;
	}

	/**
	 * 
	* @Title: goModifyHelp 
	* @Description: 初始化修改帮助页面
	* @param helpId
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/goModifyHelp")
	public ModelAndView goModifyHelp(Long helpId, String msg) throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminModifyHelp");
		try {
			Response<List<CateStruc>> resp = httpClient.invokeHttp(urlPath + queryCategoryUrl, createEmptyCateReq(),
					new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = new ArrayList<CateStruc>();
			HelpDetailRequest req = new HelpDetailRequest();
			req.setHelpId(helpId);
			HelpStruc help = (HelpStruc) httpClient.invokeHttp(urlPath + queryHelpDetail, req, HelpStruc.class)
					.getBody().getResult();
			if (help.getCateId().equals(4l)) {
				Response<List<LotteryCateStruc>> lotResp = httpClient.invokeHttp(urlPath
						+ queryLotteryKnowledgeCategory, new TypeReference<Response<List<LotteryCateStruc>>>() {
				});
				for (CateStruc cate : resp.getBody().getResult()) {
					if (cate.getId() == 4L) {
						cateList.add(cate);
					}
				}
				for (int i = 0; i < lotResp.getBody().getResult().size(); i++) {
					//把游戏列表和分类进行配对
					boolean incld = false;
					LotteryCateStruc ss = lotResp.getBody().getResult().get(i);
					for (LotteryContentStruc s : help.getLotteryContentStruc()) {
						if (s.getId().equals(ss.getId())) {
							incld = true;
							break;
						}

					}
					if (!incld) {
						help.getLotteryContentStruc().add(new LotteryContentStruc());
					}
				}
				mav.setViewName("help/helpAdminModifyLottery");
				mav.addObject("cate2Name", "帮助管理");
				mav.addObject("lotCateList", lotResp.getBody().getResult());
			} else {
				for (CateStruc cate : resp.getBody().getResult()) {
					if (cate.getId() != 4L) {
						cateList.add(cate);
					}
				}
			}
			HelpStrucWithCateName help2 = new HelpStrucWithCateName();
			BeanUtilsBean.getInstance().copyProperties(help2, help);
			for (int i = 0; i < cateList.size(); i++) {
				CateStruc cate = cateList.get(i);
				if (cate.getId().equals(help.getCateId())) {
					help2.setCateName(cate.getName());
					continue;
				} else if (cate.getId().equals(help.getCateId2())) {
					help2.setCateName2(cate.getName());
					continue;
				}
			}
			mav.addObject("urlViewPic", urlViewPic);
			mav.addObject("cateList", cateList);
			mav.addObject("help", help2);
			for (CateStruc cate : cateList) {
				mav.addObject("cateId" + cate.getId(), cate.getSubCate());
			}
			mav.addObject("msg", msg);
		} catch (Exception e) {
			logger.error("goModifyHelp error.", e);
			throw e;
		}
		return mav;
	}

	/**
	 * 
	* @Title: goCreateLottery 
	* @Description: 初始化创建彩种页面
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/goCreateLottery")
	public ModelAndView goCreateLottery() throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminCreateLottery");
		try {
			Response<List<CateStruc>> cateResp = httpClient.invokeHttp(urlPath + queryCategoryUrl,
					createEmptyCateReq(), new TypeReference<Response<List<CateStruc>>>() {
					});
			Response<List<LotteryCateStruc>> lotResp = httpClient.invokeHttp(urlPath + queryLotteryKnowledgeCategory,
					new TypeReference<Response<List<LotteryCateStruc>>>() {
					});

			List<CateStruc> cateList = cateResp.getBody().getResult();
			mav.addObject("cateList", cateList);
			for (CateStruc cateStruc : cateList) {
				mav.addObject("cateId" + cateStruc.getId(), cateStruc.getSubCate());
			}
			mav.addObject("lotCateList", lotResp.getBody().getResult());
			mav.addObject("cate2Name", "新建彩种");
			mav.addObject("urlViewPic", urlViewPic);
		} catch (Exception e) {
			logger.error("goCreateLottery error.", e);
			throw e;
		}
		return mav;
	}

	/**
	 * 
	* @Title: goHelpManager 
	* @Description: 初始化帮助管理首页
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = { "/goHelpManager", "/" })
	public ModelAndView goHelpManager() throws Exception {
		HelpQueryRequest req = new HelpQueryRequest();
		req.setCateId(-1L);
		req.setCateId2(-1L);
		req.setIsRec(-1L);
		return searchHelp(req, "");
	}

	/**
	 * 
	* @Title: createHelp 
	* @Description: 创建帮助
	* @param helpStruc
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createHelp")
	public ModelAndView createHelp(@Valid HelpStruc helpStruc, Errors errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			logger.warn("parameter is error." + errors);
			throw new Exception();
		}
		Response<Object> resp = null;
		String tipInfo = "";
		try {
			if (helpStruc.getId() == null) {
				helpStruc.setType(0);
				resp = httpClient.invokeHttp(urlPath + createHelp, helpStruc, Object.class);
				tipInfo = "新建帮助成功";
			} else {
				helpStruc.setBrowsenum(Long.parseLong(request.getParameter("browsenum")));
				helpStruc.setSolvednum(Long.parseLong(request.getParameter("solvednum")));
				helpStruc.setUnsolvednum(Long.parseLong(request.getParameter("unsolvednum")));
				resp = httpClient.invokeHttp(urlPath + modifyHelp, helpStruc, Object.class);
				tipInfo = "修改帮助成功";
				//				return goModifyHelp(helpStruc.getId(), "修改成功");
			}
		} catch (Exception e) {
			logger.error("createHelp error.", e);
			throw e;
		}
		if (resp.getHead().getStatus() == 901l) {
			logger.warn("communication exception:parameter error.");
			throw new Exception();
		}
		HelpQueryRequest req = new HelpQueryRequest();
		req.setTitle(helpStruc.getTitle());
		req.setCateId(-1L);
		req.setCateId2(-1L);
		req.setIsRec(-1L);
		if (helpStruc.getCurrentContextPath() != null) {
			String url = helpStruc.getCurrentContextPath()
					+ "/helpAdmin/searchHelp?cateId=-1&cateId2=-1&isRec=-1";
			return new ModelAndView("redirect:" + url).addObject("tipInfo", tipInfo).addObject("title", helpStruc.getTitle());
		} else {
			return this.searchHelp(req, tipInfo);
		}
	}

	@RequestMapping(value = "/updateBrowsenum")
	@ResponseBody
	public Object updateBrowsenum(HelpStruc helpStruc) throws Exception {
		Response<Object> resp = null;
		String tipInfo = "";
		try {
			if (helpStruc.getId() != null && helpStruc.getBrowsenum() != null) {
				resp = httpClient.invokeHttp(urlPath + updateBrowsenum, helpStruc, Object.class);
				tipInfo = "修改帮助成功";
			}
		} catch (Exception e) {
			logger.error("createHelp error.", e);
			throw e;
		}
		if (resp.getHead().getStatus() == 901l) {
			logger.warn("communication exception:parameter error.");
			throw new Exception();
		}
		return tipInfo;
	}

	@RequestMapping(value = "/checkHelpTitle")
	@ResponseBody
	public Object checkHelpTitle(HelpQueryRequest request, @RequestParam("title") String title) throws Exception {
		OperateStatusResponse resp = new OperateStatusResponse();
		request.setTitle(title);
		request.setCateId(-1L);
		request.setCateId2(-1L);
		request.setIsRec(-1L);
		request.setLike_match("false");
		try {
			Response<List<HelpStruc>> helpResp = httpClient.invokeHttp(urlPath + queryHelp, request,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			List<HelpStruc> helpList = helpResp.getBody().getResult();
			if (CollectionUtils.isEmpty(helpList)) {
				resp.setStatus(1);

			} else {
				resp.setStatus(0);
			}
		} catch (Exception e) {
			logger.error("searchHelp error.", e);
			throw e;
		}
		return resp;
	}

	/**
	 * 
	* @Title: createLottery 
	* @Description: 创建彩种
	* @param request
	* @param helpStruc
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createLottery")
	public ModelAndView createLottery(HttpServletRequest request, HelpStruc helpStruc) throws Exception {
		String name = null;
		List<LotteryContentStruc> contentList = new ArrayList<LotteryContentStruc>();
		LotteryContentStruc contentStruc;

		Response<List<LotteryCateStruc>> lotResp = httpClient.invokeHttp(urlPath + queryLotteryKnowledgeCategory,
				new TypeReference<Response<List<LotteryCateStruc>>>() {
				});
		for (LotteryCateStruc lotCate : lotResp.getBody().getResult()) {
			String content = request.getParameter("lotCateContent" + lotCate.getId());
			contentStruc = new LotteryContentStruc();
			contentStruc.setId(lotCate.getId());
			contentStruc.setName(name);
			contentStruc.setContent(content);
			contentList.add(contentStruc);
		}
		helpStruc.setLotteryContentStruc(contentList);
		String tipInfo = "";
		try {
			if (helpStruc.getId() == null) {
				helpStruc.setType(1);
				httpClient.invokeHttp(urlPath + createHelp, helpStruc, Object.class);
				tipInfo = "新建帮助成功";
			} else {
				httpClient.invokeHttp(urlPath + modifyHelp, helpStruc, Object.class);
				tipInfo = "修改帮助成功";
				//				return goModifyHelp(helpStruc.getId(), "修改成功");
			}
		} catch (Exception e) {
			logger.error("createHelp error.", e);
			throw e;
		}
		HelpQueryRequest req = new HelpQueryRequest();
		req.setTitle(helpStruc.getTitle());
		req.setCateId(-1L);
		req.setCateId2(-1L);
		req.setIsRec(-1L);
		return searchHelp(req, tipInfo);

	}

	/**
	 * 
	* @Title: searchHelp 
	* @Description: 查询帮助（列表）
	* @param helpQueryRequest
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/searchHelp")
	public ModelAndView searchHelp(HelpQueryRequest request, @RequestParam(required = false) String tipInfo)
			throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminManage");
		if (request.getPageNo() == null) {
			request.setPageNo(1L);
		}
		try {
			Pager pager = new Pager();
			int startNo = (int) ((request.getPageNo() - 1) * pageSize + 1l);
			pager.setStartNo(startNo);
			pager.setEndNo(pageSize + startNo - 1);
			Response<List<CateStruc>> cateResp = httpClient.invokeHttp(urlPath + queryCategoryUrl,
					createEmptyCateReq(), new TypeReference<Response<List<CateStruc>>>() {
					});
			List<CateStruc> cateList = cateResp.getBody().getResult();
			if (request.getOrderBy() == null) {
				request.setOrderBy(" no DESC");
			}
			Response<List<HelpStruc>> helpResp = httpClient.invokeHttp(urlPath + queryHelp, request, pager,
					new TypeReference<Response<List<HelpStruc>>>() {
					});
			List<HelpStruc> helpList = helpResp.getBody().getResult();
			List<HelpStrucWithCateName> helpList2 = new ArrayList<HelpStrucWithCateName>();
			HelpStrucWithCateName help2 = null;
			for (HelpStruc help : helpList) {
				help2 = new HelpStrucWithCateName();
				BeanUtilsBean.getInstance().copyProperties(help2, help);
				for (CateStruc cate : cateList) {
					if (cate.getId().equals(help.getCateId())) {
						help2.setCateName(cate.getName());
						for (CateStruc cate2 : cate.getSubCate()) {
							if (cate2.getId().equals(help.getCateId2())) {
								help2.setCateName2(cate2.getName());
							}
						}
					} else if (cate.getId().equals(help.getCateId2())) {
						help2.setCateName2(cate.getName());
						continue;
					}
				}
				helpList2.add(help2);
			}

			ResultPager resultPage = helpResp.getBody().getPager();
			int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
			Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
			mav.addObject("page", page);

			mav.addObject("queryReq", request);
			mav.addObject("helpList", helpList2);
			mav.addObject("cateList", cateList);
			mav.addObject("cate2Name", "帮助管理");
			mav.addObject("tipInfo", tipInfo);
			order(request.getOrderBy(), mav);
			for (CateStruc cate : cateList) {
				mav.addObject("cateId" + cate.getId(), cate.getSubCate());
			}
		} catch (Exception e) {
			logger.error("searchHelp error.", e);
			throw e;
		}
		return mav;
	}

	/**
	 * 
	* @Title: delHelp 
	* @Description: 删除帮助
	* @param id
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/delHelp")
	public ModelAndView delHelp(String id) throws Exception {
		ModelAndView mav = new ModelAndView("help/helpAdminManage");
		HelpDeleteRequest delReq = new HelpDeleteRequest();
		List<Long> delList = new ArrayList<Long>();
		for (String str : id.split(",")) {
			if (str != null && !str.trim().equals("")) {
				delList.add(Long.parseLong(str));
			}
		}
		delReq.setIds(delList);
		try {
			httpClient.invokeHttp(urlPath + deleteHelp, delReq, Object.class);
		} catch (Exception e) {
			logger.error("delHelp error.", e);
			throw e;
		}
		//return mav; //del by hugh
		return null;
	}

	private Request<CateStrucQueryRequest> createEmptyCateReq() {
		Request<CateStrucQueryRequest> req = new Request<CateStrucQueryRequest>();
		RequestBody<CateStrucQueryRequest> body = new RequestBody<CateStrucQueryRequest>();
		body.setParam(new CateStrucQueryRequest());
		req.setBody(body);
		return req;
	}

	private void order(String orderBy, ModelAndView mav) {
		if (orderBy.equals("BROWSENUM")) {
			mav.addObject("browsenum_icon", "ico-up-current");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-up");
		} else if (orderBy.equals("BROWSENUM DESC")) {
			mav.addObject("browsenum_icon", "ico-down-current");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-up");
		} else if (orderBy.equals("SOLVEDNUM")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-up-current");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-up");
		} else if (orderBy.equals("SOLVEDNUM DESC")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down-current");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-down");
		} else if (orderBy.equals("UNSOLVEDNUM")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-up-current");
			mav.addObject("no_icon", "ico-up");
		} else if (orderBy.equals("UNSOLVEDNUM DESC")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down-current");
			mav.addObject("no_icon", "ico-up");
		} else if (orderBy.equals("NO")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-up-current");
		} else if (orderBy.equals("NO DESC")) {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-down-current");
		} else {
			mav.addObject("browsenum_icon", "ico-down");
			mav.addObject("solvednum_icon", "ico-down");
			mav.addObject("unsolvednum_icon", "ico-down");
			mav.addObject("no_icon", "ico-down");
		}

	}
}