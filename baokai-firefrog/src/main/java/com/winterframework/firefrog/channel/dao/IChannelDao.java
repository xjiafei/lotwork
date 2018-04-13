package com.winterframework.firefrog.channel.dao;

import java.util.List;

import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IChannelDao {
	
	public ChannelDto queryByID(Long id);

	public ChannelDto updateChannel(ChannelDto channelDto,String account);

	public Page<ChannelDto> searchChannel(PageRequest<ChannelSearch> pageRequest);
 
	public void getReset();

	public void setDayReset();

	public Long getParam(String param);
}
