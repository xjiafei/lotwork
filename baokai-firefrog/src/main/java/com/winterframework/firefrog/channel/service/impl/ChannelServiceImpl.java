package com.winterframework.firefrog.channel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.channel.dao.IChannelDao;
import com.winterframework.firefrog.channel.service.IChannelService;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("channelServiceImpl")
public class ChannelServiceImpl implements IChannelService{
	
	
	@Resource(name = "channelDaoImpl")
	private IChannelDao channelDaoImpl;
	
	
	@Override
	public ChannelDto queryByID(Long id) {
		return channelDaoImpl.queryByID(id);
	}


	@Override
	public ChannelDto updateChannel(ChannelDto channelDto,String account) {
		return channelDaoImpl.updateChannel(channelDto,account);
	}


	@Override
	public Page<ChannelDto> searchChannel(PageRequest<ChannelSearch> pageRequest) {
		return channelDaoImpl.searchChannel(pageRequest);
	}


	@Override
	public void getReset() {
		channelDaoImpl.getReset();
	}


	@Override
	public void setDayReset() {
		channelDaoImpl.setDayReset();
	}


	@Override
	public Long getParam(String param) {
		return channelDaoImpl.getParam(param);
	}
}
