package com.winterframework.firefrog.global.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.exception.SensitiveWordException;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.global.web.dto.DTOConverter;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordNameRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
* @ClassName: GlobalSensitiveWordController 
* @Description: 敏感词管理控制类
* @author Tod
* @date 2013-10-10 下午4:08:51 
*
 */
@Controller("globalSensitiveWordController")
@RequestMapping("/globeAdmin")
public class GlobalSensitiveWordController {

	private static final Logger logger = LoggerFactory.getLogger(GlobalSensitiveWordController.class);

	@Resource(name = "globalSensitiveWordServiceImpl")
	private GlobalSensitiveWordService globalSensitiveWordService;

	@RequestMapping(value = "/queryByType")
	public @ResponseBody
	Response<Map<Long, Long>> queryByType(@RequestBody Request<Object> request) throws Exception {
		Response<Map<Long, Long>> response = new Response<Map<Long, Long>>(request);
		response.setResult(globalSensitiveWordService.queryByType());
		return response;

	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询敏感词列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySensitiveWordList")
	public @ResponseBody
	Response<List<GlobalSensitiveWordStruc>> queryList(@RequestBody Request<GlobalSensitiveWordQueryRequest> request)
			throws Exception {
		Response<List<GlobalSensitiveWordStruc>> response = new Response<List<GlobalSensitiveWordStruc>>(request);
		PageRequest<GlobalSensitiveWordQueryRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
				.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		pageRequest.setSortColumns("ID DESC");
		try {
			Page<SensitiveWord> page = globalSensitiveWordService.queryList(pageRequest);
			List<GlobalSensitiveWordStruc> list = new ArrayList<GlobalSensitiveWordStruc>();
			GlobalSensitiveWordStruc wordStruc = null;
			for (SensitiveWord word : page.getResult()) {
				wordStruc = new GlobalSensitiveWordStruc();
				wordStruc.setWord(word.getWord());
				wordStruc.setType((long) word.getType().ordinal());
				wordStruc.setId(word.getId());
				list.add(wordStruc);
			}
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageLastElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResult(list);
			response.setResultPage(pager);
		} catch (Exception e) {
			logger.error("queryList error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询敏感词列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySensitiveWordListByType")
	public @ResponseBody
	Response<List<GlobalSensitiveWordStruc>> queryListByType(@RequestBody Request<Long> request) throws Exception {
		Response<List<GlobalSensitiveWordStruc>> response = new Response<List<GlobalSensitiveWordStruc>>(request);

		try {
			List<SensitiveWord> wrodList = globalSensitiveWordService.quaryListByType(request.getBody().getParam());
			List<GlobalSensitiveWordStruc> list = new ArrayList<GlobalSensitiveWordStruc>();
			GlobalSensitiveWordStruc wordStruc = null;
			for (SensitiveWord word : wrodList) {
				wordStruc = new GlobalSensitiveWordStruc();
				wordStruc.setWord(word.getWord());
				wordStruc.setType((long) word.getType().ordinal());
				wordStruc.setId(word.getId());
				list.add(wordStruc);
			}
			response.setResult(list);
		} catch (Exception e) {
			logger.error("queryList error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: saveWord 
	* @Description: 新加敏感词
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/saveWord")
	public @ResponseBody
	Response<String> saveWord(@RequestBody @ValidRequestBody Request<List<GlobalSensitiveWordStruc>> request)
			throws Exception {

		Response<String> response = new Response<String>(request);
		try {
			List<SensitiveWord> words = new ArrayList<SensitiveWord>();
			SensitiveWord word = null;
			for (GlobalSensitiveWordStruc wordStruc : request.getBody().getParam()) {
				word = DTOConverter.transWordStruc2Word(wordStruc);
				words.add(word);
			}
			String result = globalSensitiveWordService.save(words);
			if (result != null) {
				response.setResult(result);
			}
		} catch (Exception e) {
			logger.error("saveWord error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: reNameWord 
	* @Description: 重命名敏感词
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/reNameWord")
	@ResponseBody
	public Response<Object> reNameWord(@RequestBody @ValidRequestBody Request<GlobalSensitiveWordNameRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			globalSensitiveWordService.reName(request.getBody().getParam().getId(), request.getBody().getParam()
					.getWord());
		} catch (Exception e) {
			if (e.getMessage().equals("SensitiveWordRepeat")) {
				response.setResult(request.getBody().getParam().getWord());
				return response;
			}
			logger.error("modifyWord error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: deleteWord 
	* @Description: 删除敏感词
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/deleteWord")
	public @ResponseBody
	Response<Object> deleteWord(@RequestBody @ValidRequestBody Request<GlobalSensitiveWordDelRequest> request)
			throws Exception {

		Response<Object> response = new Response<Object>(request);
		try {
			globalSensitiveWordService.delete(request.getBody().getParam().getIds());
		} catch (Exception e) {
			logger.error("deleteWord error.", e);
			throw e;
		}
		return response;
	}

    @RequestMapping(value = "/match")
    @ResponseBody
    public Response match(@RequestBody @ValidRequestBody Request<GlobalSensitiveWordStruc> request) throws Exception{
        Response<GlobalSensitiveWordStruc> response = new Response<GlobalSensitiveWordStruc>();
        try {
            globalSensitiveWordService.match(request.getBody().getParam().getWord(), SensitiveWord.Type.getType(request.getBody().getParam().getType()));
        } catch (SensitiveWordException e) {
            response.getHead().setStatus(e.getStatus());
        } catch (Exception e) {
            throw e;
        }
        return response;
    }
}
