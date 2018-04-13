package com.winterframework.firefrog.channel.dao.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.winterframework.firefrog.channel.web.dto.ChannelDto;

public class VOConverter {
	
	public static ChannelVO ChannelDtoToChannelVO(ChannelDto channelDto){
		ChannelVO channelVO = new ChannelVO();
			channelVO.setId(channelDto.getId());
			channelVO.setName(channelDto.getName());
			channelVO.setUrl(channelDto.getUrl());
			channelVO.setVaild(channelDto.getVaild());
			channelVO.setEnd_time(channelDto.getEnd_time());
			channelVO.setFrequency(channelDto.getFrequency());
			channelVO.setFrequency_time(channelDto.getFrequency_time());
			channelVO.setFreeze(channelDto.getFreeze());
			channelVO.setFreeze_time(channelDto.getFreeze_time());
			channelVO.setIp(channelDto.getIp());
			channelVO.setIp_time(channelDto.getIp_time());
			channelVO.setReset(channelDto.getReset());
		return channelVO;
	}
	
	public static ChannelDto ChannelVOToChannelDto(ChannelVO channelVO){
		ChannelDto channelDto = new ChannelDto();
			channelDto.setId(channelVO.getId());
			channelDto.setName(channelVO.getName());
			channelDto.setUrl(channelVO.getUrl());
			channelDto.setVaild(channelVO.getVaild());
			channelDto.setEnd_time(channelVO.getEnd_time());
			channelDto.setFrequency(channelVO.getFrequency());
			channelDto.setFrequency_time(channelVO.getFrequency_time());
			channelDto.setFreeze(channelVO.getFreeze());
			channelDto.setFreeze_time(channelVO.getFreeze_time());
			channelDto.setIp(channelVO.getIp());
			channelDto.setIp_time(channelVO.getIp_time());
			channelDto.setReset(channelVO.getReset());
		return channelDto;
	}
	
	public static ChannelDto ChannelVOToChannelDtoForSelect(ChannelVO channelVO,String value){
		ChannelDto channelDto = new ChannelDto();
			channelDto.setId(channelVO.getId());
			channelDto.setName(channelVO.getName());
			channelDto.setUrl(channelVO.getUrl());
			channelDto.setVaild(channelVO.getVaild());
			channelDto.setEnd_time(channelVO.getEnd_time());
			channelDto.setFrequency(channelVO.getFrequency());
			channelDto.setFrequency_time(channelVO.getFrequency_time());
			channelDto.setFreeze(channelVO.getFreeze());
			channelDto.setFreeze_time(channelVO.getFreeze_time());
			channelDto.setReset(channelVO.getReset());
			String[] str = value.split(",");
			for(int i = 0; i < str.length ; i++ ){
				if(i == 0){
					String aa = str[0];
					String[] bb = aa.split(":");
					channelDto.setDay(Long.parseLong(bb[1]));
				}if(i == 1){
					String aa = str[1];
					String[] bb = aa.split(":");
					channelDto.setCount(Long.parseLong(bb[1]));
				}if(i == 2){
					String aa = str[2];
					String[] bb = aa.split(":");
					channelDto.setDay_appl(Long.parseLong(bb[1]));
				}if(i == 3){
					String aa = str[3];
					String[] bb = aa.split(":");
					channelDto.setCount_appl(Long.parseLong(bb[1]));
				}
			}
		return channelDto;
	}

	public static Map<String, Object> ChannelToLogFile(ChannelVO copyChannel,ChannelVO channelVO,String account) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("type", 1l);
		filters.put("last_status", copyChannel.toString());
		filters.put("current_status", channelVO.toString());
		filters.put("update_time", new Date());
		filters.put("actor", account);
		return filters;
	}
	
	
}
