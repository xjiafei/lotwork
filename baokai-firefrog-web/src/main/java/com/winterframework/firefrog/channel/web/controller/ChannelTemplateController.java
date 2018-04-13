package com.winterframework.firefrog.channel.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelRequest;
import com.winterframework.firefrog.channel.web.dto.ChannelSearch;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("channelTemplateController")
@RequestMapping(value = "/channel")
public class ChannelTemplateController {
	
	private Logger logger = LoggerFactory.getLogger(ChannelTemplateController.class);
	
	@PropertyConfig(value = "url.front.domain")
	private String domain;
	
	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;
	
	@PropertyConfig(value = "url.channel.getParam")
	private String getParam;
	
	@PropertyConfig(value = "url.channel.deleteDay")
	private String deleteDay;
	
	@PropertyConfig(value = "url.channel.queryByID")
	private String queryByID;
	
	@PropertyConfig(value = "url.channel.updateChannel")
	private String updateChannel;
	
	@PropertyConfig(value = "url.channel.action")
	private String action;
	
	@PropertyConfig(value = "url.channel.searchChannel")
	private String searchChannel;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@RequestMapping(value = "/toSaveChannelTemplate")
	public ModelAndView channelTemplate () throws Exception{
		ModelAndView mav = new ModelAndView("/channel/channelTemplate");
		ChannelDto channelDto = new ChannelDto();
			channelDto.setId(1l);	
		Response<ChannelDto> channelRes	= httpClient.invokeHttp(serverPath+queryByID,channelDto,new TypeReference<Response<ChannelDto>>(){});
		mav.addObject("channel",channelRes.getBody().getResult());
		if(channelRes.getBody().getResult().getUrl() != null && channelRes.getBody().getResult().getName() != null){
			mav.addObject("url",channelRes.getBody().getResult().getUrl()+"?param="+channelRes.getBody().getResult().getName());
		}
		return mav;
	}
	
	@RequestMapping(value = "/queryByID")
	public ModelAndView queryByID (ChannelDto channelDto) throws Exception{
		ModelAndView mav = new ModelAndView("/channel/channelTemplate");
		
		Response<ChannelDto> channelRes	= httpClient.invokeHttp(serverPath+queryByID,channelDto,new TypeReference<Response<ChannelDto>>(){});
		
		mav.addObject("channel",channelRes.getBody().getResult());
		if(channelRes.getBody().getResult().getUrl() != null && channelRes.getBody().getResult().getName() != null){
			mav.addObject("url",channelRes.getBody().getResult().getUrl()+"?param="+channelRes.getBody().getResult().getName());
		}
		return mav;
	}
	
	@RequestMapping(value = "/updateChannel")
	public ModelAndView updateChannel (ChannelRequest request) throws Exception{
		ModelAndView mav = new ModelAndView("/channel/channelTemplate");
		if(DateUtils.parse(request.getEnd_timeStr(),DateUtils.DATE_FORMAT_PATTERN) != null){
			request.setEnd_time(DateUtils.getEndTimeOfDate(DateUtils.parse(request.getEnd_timeStr(),DateUtils.DATE_FORMAT_PATTERN)));
		}
		if(request.getReset() == null){
			request.setReset(0l);
		}
		Long userId = RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();
		try{
			Response<ChannelDto> channelRes	= httpClient.invokeHttp(serverPath+updateChannel,request,userId,account,new TypeReference<Response<ChannelDto>>(){});
			if(channelRes.getBody().getResult().getUrl() != null && channelRes.getBody().getResult().getName() != null){
				mav.addObject("url",channelRes.getBody().getResult().getUrl()+"?param="+channelRes.getBody().getResult().getName());
			}
			mav.addObject("channel",channelRes.getBody().getResult());
			mav.addObject("status",1);
		}catch(Exception e){
			mav.addObject("status",2);
		}
		return mav;
	}
	
	@RequestMapping(value = "/channelView")
	public ModelAndView channelView (ChannelSearch search,Long pageNo) throws Exception{
		ModelAndView mav = new ModelAndView("/channel/channelView");
		Pager pager = new Pager();
		this.pageSize=50;
		pageNo = pageNo == null ? 1L : pageNo;
		int startNo = (int) ((pageNo - 1) * pageSize + 1l);
		pager.setStartNo(startNo);
		pager.setEndNo(pageSize + startNo - 1);
		
		Response<List<ChannelDto>> resp = httpClient.invokeHttp(serverPath + searchChannel, search, pager, 1l, null,
				new TypeReference<Response<List<ChannelDto>>>() {
				});
		ResultPager resultPage = resp.getBody().getPager();
		int pageNoResult = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		Page<Object> page = new Page<Object>(pageNoResult, pageSize.intValue(), resultPage.getTotal());
		mav.addObject("page", page);
		mav.addObject("list", resp.getBody().getResult());
		mav.addObject("search", search);
		return mav;
	}
	
	@RequestMapping(value = "/deleteDay")
	public ModelAndView deleteDay (Long id) throws Exception{
		httpClient.invokeHttpWithoutResultType(serverPath + deleteDay, id);
		return null;
	}	
}
