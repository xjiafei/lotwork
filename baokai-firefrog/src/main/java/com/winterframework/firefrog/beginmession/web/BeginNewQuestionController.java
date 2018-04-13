package com.winterframework.firefrog.beginmession.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.service.BeginNewQuestionService;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller
@RequestMapping("/begin")
public class BeginNewQuestionController{
	
	private static final Logger log = LoggerFactory.getLogger(BeginNewQuestionController.class);
	
	@Autowired
	private BeginNewQuestionService beginNewQuestionService;
	
	@RequestMapping(value = "/searchQuestion")
	@ResponseBody
	public Response<BeginNewQuestionResponse> searchQuestion(@RequestBody @ValidRequestBody Request<BeginNewQuestionRequest> request)
			throws Exception {

		Response<BeginNewQuestionResponse> response = new Response<BeginNewQuestionResponse>(request);
		try {
			
			if (null != request.getBody()) {
				PageRequest<BeginNewQuestionRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());				
				BeginNewQuestionRequest req = request.getBody().getParam();
				pageRequest.setSearchDo(req);
				Page<BeginNewQuestion> questions = beginNewQuestionService.getAllByPage(pageRequest);
				
				BeginNewQuestionResponse qures = new BeginNewQuestionResponse();
				qures.setQuestions(questions.getResult());
				ResultPager pager = new ResultPager();
				pager.setStartNo(questions.getThisPageFirstElementNumber());
				pager.setEndNo(questions.getThisPageLastElementNumber());
				pager.setTotal(questions.getTotalCount());
				response.setResultPage(pager);
				response.setResult(qures);
			}
		} catch (Exception e) {
			log.error(" searchQuestion error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/saveQuestion")
	@ResponseBody
	public void saveQuestion(@RequestBody @ValidRequestBody Request<BeginNewQuestionRequest> request)
			throws Exception {
		try {
			if (null != request.getBody()) {
				final String userAccount = request.getBody().getParam().getUserName();
				List<BeginNewQuestion> questions= request.getBody().getParam().getQuestions();
				if(questions!=null && questions.size()>0){
					questions = Lists.transform(questions, new Function<BeginNewQuestion,BeginNewQuestion>(){
						@Override
						public BeginNewQuestion apply(BeginNewQuestion question) {
							if(question.getId()==null){
								question.setCreateTime(DateUtils.currentDate());
								question.setCreateUser(userAccount);								
							}
							question.setModifyTime(DateUtils.currentDate());
							question.setModifyUser(userAccount);
							return question;
						}
					});
					beginNewQuestionService.saveQuestion(questions);
				}
			}
		} catch (Exception e) {
			log.error("saveQuestion error:", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/updateDeleteFlag")
	public void updateDeleteFalg(@RequestBody @ValidRequestBody Request<BeginNewQuestionRequest> request) throws Exception {
		if (null != request.getBody()) {
			beginNewQuestionService.updateDeleteFlag(request.getBody().getParam().getDeleteId());
		}
	}
}
