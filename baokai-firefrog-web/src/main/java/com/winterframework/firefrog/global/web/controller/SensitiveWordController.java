package com.winterframework.firefrog.global.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.global.web.dto.GlobalIpDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordNameRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordStruc;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordWithType;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller("SensitiveWordController")
@RequestMapping(value = "/globeAdmin")
public class SensitiveWordController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;

	@PropertyConfig(value = "url.global.createWord")
	private String createWord;

	@PropertyConfig(value = "url.global.reNameWord")
	private String reNameWord;

	@PropertyConfig(value = "url.global.queryWord")
	private String queryWord;

	@PropertyConfig(value = "url.global.deleteWord")
	private String deleteWord;
    @PropertyConfig(value = "url.global.match")
    private String match;

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordController.class);
    private static Map<Long, String> typeMap = new HashMap<Long, String>();

	static {
		typeMap.put(0L, "注册页");
		typeMap.put(1L, "广告");
		typeMap.put(2L, "帮助");
		typeMap.put(3L, "站内信");
		typeMap.put(4L, "评论");
		typeMap.put(5L, "客服");
	}

	@RequestMapping(value = "/goSensitiveWord")
	public ModelAndView goSensitiveWord() throws Exception {
		GlobalSensitiveWordQueryRequest search = new GlobalSensitiveWordQueryRequest();
		search.setPageNo(1L);
		return searchSensitiveWord(search);
	}

	@RequestMapping(value = "/createSensitiveWord")
	@ResponseBody
	public Object createSensitiveWord(String wordStr, Long pid) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		List<GlobalSensitiveWordStruc> wordList = new ArrayList<GlobalSensitiveWordStruc>();
		GlobalSensitiveWordStruc word = null;
		String[] ss = wordStr.split("\n");
		for (String str : ss) {
			word = new GlobalSensitiveWordStruc();
			word.setType(pid);
			word.setWord(str);
			wordList.add(word);
		}
		try {
			Response<String> resp = httpClient.invokeHttpWithoutResultType(urlPath + createWord, wordList);
			if (resp.getBody().getResult() != null) {
				response.setStatus(1L);
				response.setMessage(resp.getBody().getResult());
			} else {
				//协议里面有问题，如果是0的话表示新建成功
				response.setStatus(0L);
			}
		} catch (Exception e) {
			response.setStatus(2L);
			response.setMessage("新建");
		}

		return response;
	}

	@RequestMapping(value = "/reNameSensitiveWord")
	@ResponseBody
	public OperateStatusResponse reNameSensitiveWord(GlobalSensitiveWordNameRequest name) throws Exception {
		OperateStatusResponse response = new OperateStatusResponse();
		Response<String> resp = null;
		try {
			resp = httpClient.invokeHttpWithoutResultType(urlPath + reNameWord, name);
			if (resp.getBody().getResult() != null) {
				response.setStatus(1L);
				response.setMessage(name.getWord());
			} else {
			//协议里面有问题，如果是0的话表示新建成功
				response.setStatus(0L);
				response.setMessage(resp.getBody().getResult());
			}
		} catch (Exception e) {
			response.setStatus(2L);
			response.setMessage("修改");
		}
		return response;
	}

	@RequestMapping(value = "/deleteSensitiveWord")
	public ModelAndView deleteSensitiveWord(String ids) throws Exception {
		String[] ss = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (String str : ss) {
			idList.add(Long.parseLong(str));
		}
		GlobalIpDelRequest req = new GlobalIpDelRequest();
		req.setIds(idList);
		httpClient.invokeHttpWithoutResultType(urlPath + deleteWord, req);
		return goSensitiveWord();
	}

	@RequestMapping(value = "/searchSensitiveWord")
	public ModelAndView searchSensitiveWord(GlobalSensitiveWordQueryRequest search) throws Exception {
		ModelAndView mav = new ModelAndView("global/sensitiveWord");
		Pager pager = new Pager();
		if (search != null && StringUtils.isNotEmpty(search.getWord())) {
			search.setWord(search.getWord().replace(" ", "").trim());
		}
		if (search.getPageNo() == null) {
			search.setPageNo(1L);
		}
		if(search != null && "请输入敏感词".equals(search.getWord())){
			search.setWord(null);
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<List<GlobalSensitiveWordStruc>> resp = httpClient.invokeHttp(urlPath + queryWord, search, pager,
				new TypeReference<Response<List<GlobalSensitiveWordStruc>>>() {
				});

		List<GlobalSensitiveWordWithType> list = new ArrayList<GlobalSensitiveWordWithType>();
		GlobalSensitiveWordWithType typeSturc = null;
		for (GlobalSensitiveWordStruc struc : resp.getBody().getResult()) {
			typeSturc = new GlobalSensitiveWordWithType();
			typeSturc.setId(struc.getId());
			typeSturc.setType(typeMap.get(struc.getType()));
			typeSturc.setWord(struc.getWord());
			list.add(typeSturc);
		}

		ResultPager resultPage = resp.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", list);
		mav.addObject("search", search);
		mav.addObject("global_no", 2);
		Response<Map<Long, Long>> typeResp = httpClient.invokeHttp(urlPath + "/globeAdmin/queryByType",
				new TypeReference<Response<Map<Long, Long>>>() {
				});
		for (int i = 0; i < 6; i++) {
			mav.addObject("type_" + i, 0);
		}
		for (Entry<Long, Long> e : typeResp.getBody().getResult().entrySet()) {
			mav.addObject("type_" + e.getKey(), e.getValue());
		}
		return mav;
	}

    /**
     * 敏感词对比，如果包含敏感词返回true
     * @param word
     * @return
     */
    @RequestMapping(value = "/match")
    @ResponseBody
    public Boolean match(GlobalSensitiveWordStruc word) {
        Response<GlobalSensitiveWordStruc> response;
        try {
            response = httpClient.invokeHttpWithoutResultType(urlPath + match, word);
            if (response.getHead() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("调用敏感词匹配接口失败:", e);
            return true;
        }
    }
}
