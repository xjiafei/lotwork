package com.winterframework.firefrog.beginmession.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.advert.web.dto.ExportViewDataModel;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.view.ExcelView;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.IUser;
import com.winterframework.modules.web.util.RequestContext;

@Controller
@RequestMapping(value ="/begin")
public class BeginNewQuestionWebController {
	
	private static final Logger log = LoggerFactory.getLogger(BeginNewQuestionWebController.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryQuestion")
	private String queryQuestion;
	
	@PropertyConfig(value = "url.begin.saveQuestion")
	private String saveQuestion;
	
	@PropertyConfig(value = "url.begin.deleteFlag")
	private String deleteQuestion;
	
	@PropertyConfig(value="page.pagesize")
	private Integer pageSize;
	
	@RequestMapping(value="/fileUpload",method =RequestMethod.POST )
	public  void uploadFile(@RequestParam(value = "tempFile") MultipartFile multipartFile) throws IOException{
		List<String> actionList = new ArrayList<String>();
		Workbook workbook = null;
		if(multipartFile.getOriginalFilename().endsWith("xls")){
			workbook = new HSSFWorkbook(multipartFile.getInputStream());
			
		}else{
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
		}
		
		if(workbook.getSheetAt(0)!=null){
			Sheet sheet= workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext()){
				Row row = rows.next();
				if(row.getCell(0)!=null){
					String account= row.getCell(0).getStringCellValue();
					actionList.add(account);					
				}
			}
		}
	}
	
	@RequestMapping(value="/downloadFile",method =RequestMethod.GET )
	public  ModelAndView downloadFile(@RequestParam(value = "test") String test, HttpServletResponse response) throws IOException{
		
		List<BeginNewQuestion> questions = new ArrayList<BeginNewQuestion>();
		BeginNewQuestion question = new BeginNewQuestion();
		question.setCrtAnswer("aa");
		question.setErrAnswer1("bb");
		question.setErrAnswer2("cc");
		
		questions.add(question);
		
		BeginNewQuestion question1 = new BeginNewQuestion();
		question1.setCrtAnswer("aa");
		question1.setErrAnswer1("bb");
		question1.setErrAnswer2("cc");
		questions.add(question1);
		
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet= workbook.createSheet();
		int index = 0;
		for(BeginNewQuestion ques:questions){
			Row  row=sheet.createRow(index);
			Cell cell = row.createCell(0);
			cell.setCellValue(ques.getCrtAnswer());
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(ques.getErrAnswer1());
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(ques.getErrAnswer2());
			index++;
		}
        
         
 		ExportViewDataModel viewTemplates = new ExportViewDataModel();
		String[] titles = new String[] { "奖期", "錯誤答案1","錯誤答案2" };
		String[] columns = new String[] { "crtAnswer", "errAnswer1","errAnswer2"};
		viewTemplates.setFileName(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_PATTERN));
		viewTemplates.setHeader(titles);
		viewTemplates.setColumns(columns);
		viewTemplates.setDataList(questions);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dataModel", viewTemplates);
		return new ModelAndView(new ExcelView(), model);
	}
	
	
	@RequestMapping(value="toNewQuestion",method =RequestMethod.GET)
	public ModelAndView toNewQuestion(BeginNewQuestionWebRequest search) throws Exception {
		ModelAndView model = new ModelAndView("advert/begin/newQuestion");
		
		Pager pager = new Pager();
		if(search.getPageNo()==null){
			search.setPageNo(1l);
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		
		Response<BeginNewQuestionWebResponse> response = httpClient.invokeHttp(urlPath + queryQuestion, search, pager, BeginNewQuestionWebResponse.class);
		ResultPager resultPage = response.getBody().getPager();
		
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		
		model.addObject("questions", response.getBody().getResult().getQuestions());
		model.addObject("page", page);
		return model;
	} 
	
	@RequestMapping(value="searchQuestion")
	public ModelAndView searchQuestion(BeginNewQuestionWebRequest search) throws Exception {
		return toNewQuestion(search);
	} 
	
	@RequestMapping(value="saveQuestion",method=RequestMethod.POST,produces="application/json")
	public ModelAndView saveQuestion(@RequestBody BeginNewQuestionWebRequest dtoRequest) throws Exception {
		
		IUser user = RequestContext.getCurrUser();
		dtoRequest.setUserName(user.getUserName());
		dtoRequest.getPageNo();
		httpClient.invokeHttpWithoutResultType(urlPath + saveQuestion, dtoRequest);
		return toNewQuestion(dtoRequest);
	}
	
	@RequestMapping(value="updateDeleteFalg",method=RequestMethod.POST)
	public void updateDeleteFalg(@RequestBody BeginNewQuestionWebRequest dtoRequest) throws Exception {
		httpClient.invokeHttpWithoutResultType(urlPath + deleteQuestion, dtoRequest);
	}
	
	
}
