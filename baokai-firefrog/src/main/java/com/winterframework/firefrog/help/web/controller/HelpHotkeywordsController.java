package com.winterframework.firefrog.help.web.controller;

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
import com.winterframework.firefrog.help.entity.HelpHotkeywords;
import com.winterframework.firefrog.help.service.IHelpHotkeywordsService;
import com.winterframework.firefrog.help.web.dto.KeywordRequest;
import com.winterframework.firefrog.help.web.dto.KeywordStruc;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("helpHotkeywordsController")
@RequestMapping("/help")
public class HelpHotkeywordsController {

	private static final Logger log = LoggerFactory.getLogger(HelpHotkeywordsController.class);

	@Resource(name = "helpHotkeywordsServiceImpl")
	private IHelpHotkeywordsService helpHotkeywordsService;

	/**
	 * 查询热门关键字
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryHotWord")
	@ResponseBody
	public Response<List<KeywordStruc>> queryHotWord(@RequestBody Request request) throws Exception {
		log.info("qury keyword start");
		Response<List<KeywordStruc>> response = new Response<List<KeywordStruc>>(request);
		try {
			List<HelpHotkeywords> helpHotkeywords = helpHotkeywordsService.getAllHelpHotkeywords();
			List<KeywordStruc> keywordStrucs = new ArrayList<KeywordStruc>();
			for (HelpHotkeywords helpHotkeyword : helpHotkeywords) {
				KeywordStruc keywordStruc = new KeywordStruc();
				keywordStruc.setId(helpHotkeyword.getId());
				keywordStruc.setKeyword(helpHotkeyword.getKeyword());
				keywordStruc.setNo(helpHotkeyword.getNo());
				keywordStrucs.add(keywordStruc);
			}
			response.setResult(keywordStrucs);
		} catch (Exception e) {
			log.error("query keyword error", e);
			throw e;
		}
		log.info("qury keyword end");
		return response;
	}

	/**
	 * 修改热门关键字
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveHotWord")
	@ResponseBody
	public Response saveHotWord(@RequestBody @ValidRequestBody Request<KeywordRequest> request) throws Exception {
		log.info("save keyword start");
		Response response = new Response(request);
		try {
			KeywordRequest keywordRequest = request.getBody().getParam();
			HelpHotkeywords helpHotkeyword = new HelpHotkeywords();
			helpHotkeyword.setId(keywordRequest.getNo());
			helpHotkeyword.setKeyword(keywordRequest.getKeyword());
			helpHotkeywordsService.updateHotkeyword(helpHotkeyword);
		} catch (Exception e) {
			log.error("save keyword error");
			throw e;
		}
		return response;
	}
}
