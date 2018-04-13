package com.winterframework.firefrog.channel.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.channel.dao.IChannelDao;
import com.winterframework.firefrog.channel.dao.vo.ChannelVO;
import com.winterframework.firefrog.channel.dao.vo.VOConverter;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelSearch;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("channelDaoImpl")
public class ChannelDaoImpl extends BaseIbatis3Dao<ChannelVO> implements IChannelDao {
	
	
	@Resource(name = "RedisClient")
    private RedisClient redis;
	
	@Override
	public ChannelDto queryByID(Long id) {
		ChannelVO channelVO = sqlSessionTemplate.selectOne("queryByID", id);
		return VOConverter.ChannelVOToChannelDto(channelVO);
	}

	@Override
	public ChannelDto updateChannel(ChannelDto channelDto,String account) {
		ChannelVO channelVO = new ChannelVO();
		channelVO = VOConverter.ChannelDtoToChannelVO(channelDto);
		ChannelVO copyChannel = sqlSessionTemplate.selectOne("queryByID", channelDto.getId());
			int num = sqlSessionTemplate.update("updateChannel", channelVO);
			channelVO = sqlSessionTemplate.selectOne("queryByID", channelDto.getId());
				if(num == 1){
					Map<String, Object> filters = VOConverter.ChannelToLogFile(copyChannel,channelVO,account);
					sqlSessionTemplate.insert("insertChannelLog", filters);
				}
		return VOConverter.ChannelVOToChannelDto(channelVO);
	}

	@Override
	public Page<ChannelDto> searchChannel(PageRequest<ChannelSearch> pageRequest) {
		Page<ChannelVO> list = this.pageQuery(pageRequest, null, "channelList");
		Page<ChannelDto> page = new Page<ChannelDto>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				list.getTotalCount());

		List<ChannelDto> userList = new ArrayList<ChannelDto>();
		for (ChannelVO vo : list.getResult()) {
			if(redis.get("channel:"+vo.getId()) == null){
				redis.set("channel:"+vo.getId(), "day:0,count:0,day_appl:0,count_appl:0");	
			}
			String value = redis.get("channel:"+vo.getId());
			userList.add(VOConverter.ChannelVOToChannelDtoForSelect(vo,value));
		}
		page.setResult(userList);
		return page;
	}

	@Override
	public void getReset() {
		List<ChannelVO> channelVO = sqlSessionTemplate.selectList("getReset");
		List<ChannelDto> userList = new ArrayList<ChannelDto>();
		for (ChannelVO vo : channelVO) {
			userList.add(VOConverter.ChannelVOToChannelDto(vo));
		}
		if(userList != null){
			for(ChannelDto vo : userList){
				redis.del("Freeze:"+vo.getId());
				Set<String> IParr = redis.keys("IPFreeze:"+vo.getId()+"*");
					if(IParr.size() > 0){
						for(String ip : IParr){
							redis.del(ip);
					}
				}
			}
		}
	}

	@Override
	public void setDayReset() {
		for(int j=1 ;j<1000 ; j++){
			String value = redis.get("channel:"+j);
			String[] str = value.split(",");
			String aa = str[1];
			String[] bb = aa.split(":");
			Long count = Long.parseLong(bb[1]);
			String cc = str[3];
			String[] dd = cc.split(":");
			Long count_appl = Long.parseLong(dd[1]);
			redis.set("channel:"+j,"day:0,count:"+count+",day_appl:0,count_appl:"+count_appl);			
		}
	}
	
	public Long getParam(String param){
		Long id = null;
		if(param != null){
			id = sqlSessionTemplate.selectOne("getParam", param);
			if(id != null){
				return id ;
			}
		}
		return id ;
	}
	
}
