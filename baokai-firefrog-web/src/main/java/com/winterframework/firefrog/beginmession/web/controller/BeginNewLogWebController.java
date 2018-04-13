package com.winterframework.firefrog.beginmession.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewLogWebRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewLogWebResponse;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Controller
@RequestMapping("/begin")
public class BeginNewLogWebController {

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	private String urlPath;
	
	@PropertyConfig(value = "url.begin.queryLog")
	private String queryLog;
	
	@PropertyConfig(value="page.pagesize")
	private Integer pageSize;
	
	@RequestMapping(method=RequestMethod.GET,value="/toBeginLog")
	public ModelAndView toBeginLog(BeginNewLogWebRequest search) throws Exception{
		ModelAndView model = new ModelAndView("advert/begin/missionLog");
		Pager pager = new Pager();
		if(search.getPageNo()==null){
			search.setPageNo(1l);
		}
		int startNo = (int) ((search.getPageNo() - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		Response<BeginNewLogWebResponse> response = httpClient.invokeHttp(urlPath + queryLog, search, pager, BeginNewLogWebResponse.class);
		ResultPager resultPage = response.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		
		model.addObject("logs", transformDatetoString(response.getBody().getResult().getLogs()));
		model.addObject("page", page);
		model.addObject("type", search.getType());
		return model;
	};
	
	private List<BeginNewLog> transformDatetoString(List<BeginNewLog> logs){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		List<BeginNewLog> lisst =new ArrayList<BeginNewLog>();
		for(BeginNewLog log :logs){
			String timeStr = format.format(log.getLogTime());
			log.setUpdateTimeStr(timeStr);
			lisst.add(log);
			
		}
		return lisst;
	}
	
}
