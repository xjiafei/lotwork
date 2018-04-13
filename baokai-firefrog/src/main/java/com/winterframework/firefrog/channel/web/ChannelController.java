package com.winterframework.firefrog.channel.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.firefrog.acl.web.dto.DTOConverter;
import com.winterframework.firefrog.channel.dao.vo.VOConverter;
import com.winterframework.firefrog.channel.service.IChannelService;
import com.winterframework.firefrog.channel.web.dto.ActionRequest;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.channel.web.dto.ChannelResponse;
import com.winterframework.firefrog.channel.web.dto.ChannelSearch;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.modules.web.util.RequestContext;

@Controller("channelController")
@RequestMapping("/channel")
public class ChannelController {
	
	@Resource(name = "RedisClient")
    private RedisClient redis;
	
	@Resource(name = "channelServiceImpl")
	private IChannelService channelService;
	
	
	@RequestMapping(value = "/queryByID")
	@ResponseBody
	public Response<ChannelDto> queryByID(@RequestBody Request<ChannelDto> request){
		Response<ChannelDto> response = new Response<ChannelDto>();
		ChannelDto channelDto = channelService.queryByID(request.getBody().getParam().getId());
		response.setResult(channelDto);
		return response;
	}
	
	@RequestMapping(value = "/updateChannel")
	@ResponseBody
	public Response<ChannelDto> updateChannel(@RequestBody Request<ChannelDto> request) throws Exception{
		Response<ChannelDto> response = new Response<ChannelDto>();
		String account = request.getHead().getUserAccount();
		try{
			ChannelDto channelDto = channelService.updateChannel(request.getBody().getParam(),account);
			response.setResult(channelDto);
		}catch(Exception e){
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value = "/searchChannel")
	@ResponseBody
	public Response<List<ChannelDto>> searchChannel(@RequestBody Request<ChannelSearch> request){
		Response<List<ChannelDto>> response = new Response<List<ChannelDto>>(request);
		PageRequest<ChannelSearch> pageRequest = PageConverterUtils.getRestPageRequest(request.getBody().getPager()
				.getStartNo(), request.getBody().getPager().getEndNo());
		pageRequest.setSearchDo(request.getBody().getParam());
		Page<ChannelDto> page = channelService.searchChannel(pageRequest);
		ResultPager pager = new ResultPager();
		pager.setEndNo(page.getThisPageLastElementNumber());
		pager.setStartNo(page.getThisPageFirstElementNumber());
		pager.setTotal(page.getTotalCount());
		response.setResult(page.getResult());
		response.setResultPage(pager);
		return response;
	}
	
	@RequestMapping(value = "/deleteDay")
	@ResponseBody
	public Response<Object> deleteDay(@RequestBody Request<Long> request){
		Response<Object> response = new Response<Object>();
		redis.set("channel:"+ request.getBody().getParam(), "day:0,count:0,day_appl:0,count_appl:0");
		redis.del("Freeze:"+request.getBody().getParam());
		redis.del("Freeze_time:"+request.getBody().getParam());
		Set<String> arr = redis.keys("Frequency:"+request.getBody().getParam()+"*");
		if(arr.size() > 0){
			for(String dd : arr){
				redis.del(dd);
			}
		}
		Set<String> IParr = redis.keys("IPFreeze:"+request.getBody().getParam()+"*");
		if(IParr.size() > 0){
			for(String ip : IParr){
				redis.del(ip);
			}
		}
		Set<String> IParr_Time = redis.keys("IPFreeze_time:"+request.getBody().getParam()+"*");
		if(IParr_Time.size() > 0){
			for(String ip_time : IParr_Time){
				redis.del(ip_time);
			}
		}
		return response;
	}
	
	public boolean checkFrequency(Long id,Long frequency,Long frequency_time){
		Set<String> arr = redis.keys("Frequency:"+id+"*");
			if(arr.size() < frequency){
				redis.set("Frequency:"+id+"_"+Integer.valueOf((int) (Math.random()*10000)), "true",Integer.parseInt((frequency_time*60)+""));
			}else{
				return true;
		}
		
		return false;
	}
	
	@RequestMapping(value = "/action")
	@ResponseBody
	public Response<Long> action(@RequestBody Request<ActionRequest> request){
		Response<Long> response = new Response<Long>();
		
		//取得channel id
		Long id = channelService.getParam(request.getBody().getParam().getParam());
		if(id == null){
			response.setResult(7l);
			return response;
		}
		ChannelDto channelDto = channelService.queryByID(id);
		Long day = 0l;
		Long count = 0l;
		Long day_appl = 0l;
		Long count_appl = 0l;
		
		String value = redis.get("channel:"+channelDto.getId());
		String[] str = value.split(",");
		for(int i = 0; i < str.length ; i++ ){
			if(i == 0){
				String aa = str[0];
				String[] bb = aa.split(":");
				day = Long.parseLong(bb[1]);
			}if(i == 1){
				String aa = str[1];
				String[] bb = aa.split(":");
				count = Long.parseLong(bb[1]);
			}
			if(i == 2){
				String aa = str[2];
				String[] bb = aa.split(":");
				day_appl = Long.parseLong(bb[1]);
			}
			if(i == 3){
				String aa = str[3];
				String[] bb = aa.split(":");
				count_appl = Long.parseLong(bb[1]);
			}
		}
		
		//判斷IP限制
		if(channelDto.getIp_time() != 0){
			//判斷是否永久凍結
			if(channelDto.getFreeze() != 0){
			if(redis.get("IPFreeze:"+channelDto.getId()+request.getBody().getParam().getIp()) != null ){
				Long ipfre_num = 0l ;
				String ipfre = redis.get("IPFreeze:"+channelDto.getId()+request.getBody().getParam().getIp());
				if(ipfre != null ){
					ipfre_num = Long.parseLong(ipfre);
				}
				if(ipfre_num >= channelDto.getFreeze()){
					day_appl++;
					count_appl++;
					redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
					response.setResult(6l);
					return response;
					}
				}
			}
			
			//判斷是否暫時凍結
			if(channelDto.getFreeze_time() != 0){
				if(redis.get("IPFreeze_time:"+channelDto.getId()+request.getBody().getParam().getIp()) != null){
					day_appl++;
					count_appl++;
					redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
					response.setResult(5l);
					return response;
				}
			}
			
			
			if(redis.get("ChannelIP:"+channelDto.getId()+request.getBody().getParam().getIp()) != null){
				String ip = redis.get("ChannelIP:"+channelDto.getId()+request.getBody().getParam().getIp());
				Long ip_num = 0l;
				if(ip != null){
					ip_num = Long.parseLong(ip);
				}
				if(ip_num >= channelDto.getIp()){
					if(redis.get("IPFreeze:"+channelDto.getId()+request.getBody().getParam().getIp()) != null){
						redis.incr("IPFreeze:"+channelDto.getId()+request.getBody().getParam().getIp());
					}else{
						redis.set("IPFreeze:"+channelDto.getId()+request.getBody().getParam().getIp(),"1");
					}
					redis.set("IPFreeze_time:"+channelDto.getId()+request.getBody().getParam().getIp(),"ture",Integer.parseInt(channelDto.getFreeze_time() == null ? "1" : channelDto.getFreeze_time().toString()) * 60);
					redis.del("ChannelIP:"+channelDto.getId()+request.getBody().getParam().getIp());
					day_appl++;
					count_appl++;
					redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
					response.setResult(5l);
					return response;
				}else{
					redis.incr("ChannelIP:"+channelDto.getId()+request.getBody().getParam().getIp());
				}
			}else{
				redis.set("ChannelIP:"+channelDto.getId()+request.getBody().getParam().getIp(),"1",Integer.parseInt(channelDto.getIp_time() == null ? "1" : channelDto.getIp_time().toString()) * 60);
			}
		}
		
		//判斷是否永久凍結
		if(channelDto.getFreeze() != 0l){
			if(redis.get("Freeze:"+channelDto.getId()) != null ){
				String fre = redis.get("Freeze:"+channelDto.getId());
				Long fre_num = 0l ;
				if(fre != null){
					fre_num = Long.parseLong(fre);
				}
				if(fre_num >= channelDto.getFreeze()){
					day_appl++;
					count_appl++;
					redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
					response.setResult(4l);
					return response;
				}
			}
		}
				
		//判斷是否暫時凍結
		if(channelDto.getFreeze_time() != 0){
			if(redis.get("Freeze_time:"+channelDto.getId()) != null){
				day_appl++;
				count_appl++;
				redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
				response.setResult(3l);
				return response;
			}
		}
		
		//判斷是否有效次數
		if(channelDto.getVaild() != 0){
			if(count >= channelDto.getVaild()){
				day_appl++;
				count_appl++;
				redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
				response.setResult(1l);
				return response;
			}
		}
		
		//判斷是否在時間內
		if(channelDto.getEnd_time() != null){
			Date date = new Date();
			if(date.getTime() > channelDto.getEnd_time().getTime()){
				day_appl++;
				count_appl++;
				redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
				response.setResult(2l);
				return response;
			}
		}
		
		//判斷預警發佈
		if(channelDto.getFrequency_time() != 0){
		boolean  bo = this.checkFrequency(channelDto.getId(),channelDto.getFrequency(),channelDto.getFrequency_time());
		 if(bo){
				if(channelDto.getFreeze_time() != 0){
						redis.set("Freeze_time:" +channelDto.getId(),"true", Integer.parseInt(channelDto.getFreeze_time() == null ? "1" : channelDto.getFreeze_time().toString()) * 60 );
				}
				if(channelDto.getFreeze() != 0 ){
					if(redis.get("Freeze:"+channelDto.getId()) == null){
						redis.set("Freeze:" +channelDto.getId(),"1");
					}else{
						String freeze_value = redis.get("Freeze:"+channelDto.getId());
						Long freeze_num = Long.parseLong(freeze_value);
						freeze_num++;
						redis.set("Freeze:" +channelDto.getId(),freeze_num.toString());
					}
				}
				day_appl++;
				count_appl++;
				redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
				response.setResult(3l);
				return response;
			}
			 
		}
		day++;
		count++;
		day_appl++;
		count_appl++;
		redis.set("channel:"+ channelDto.getId(), "day:"+day+","+"count:"+count+",day_appl:"+day_appl+",count_appl:"+count_appl);
		response.setResult(0l);
		return response;
	}
}
