package com.winterframework.adbox.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dto.AdResourceRes;
import com.winterframework.adbox.dto.DeviceInfoReq;
import com.winterframework.adbox.dto.ResResult;
import com.winterframework.adbox.dto.UploadStatusReq;
import com.winterframework.adbox.entity.AdDevice;
import com.winterframework.adbox.entity.AdPublishDevice;
import com.winterframework.adbox.entity.AdResource;
import com.winterframework.adbox.enums.StatusCode;
import com.winterframework.adbox.service.IAdDeviceResourceService;
import com.winterframework.adbox.service.IAdDeviceService;
import com.winterframework.adbox.service.IAdPublishResourceService;
import com.winterframework.adbox.service.IAdResourceService;
import com.winterframework.adbox.service.IAdUserService;
import com.winterframework.adbox.service.IAdVersionResourceService;
import com.winterframework.adbox.utils.FileUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

 
/**
 * @author 登陆ip分发
 *
 */
@Controller("deviceInterfaceController")
@RequestMapping("/server")
public class DeviceInterfaceController {
	
	
	private static final Logger log = LoggerFactory.getLogger(DeviceInterfaceController.class);
	@Resource(name = "adDeviceServiceImpl")
	private IAdDeviceService adDeviceServiceImpl;

	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	
	@Resource(name = "adResourceServiceImpl")
	private IAdResourceService adResourceServiceImpl;
	@Resource(name = "adUserServiceImpl")
	private IAdUserService adUserServiceImpl;



	
	@Resource(name = "adDeviceResourceServiceImpl")
	private IAdDeviceResourceService adDeviceResourceServiceImpl;
	
	@Resource(name = "adPublishResourceServiceImpl")
	private IAdPublishResourceService adPublishResourceServiceImpl;
	
	@Resource(name = "adVersionResourceServiceImpl")
	private IAdVersionResourceService adVersionResourceServiceImpl;
	
	@PropertyConfig(value = "filePath")
	private String filePath;
	
	@PropertyConfig(value = "fileServer.url")
	private String fileServer;
	
	
	@RequestMapping("/getDeviceId")
	@ResponseBody
	public Object getDeviceId(@RequestParam String code) throws Exception {
		Context ctx = new Context();
		ctx.set("userId", -1);
		ResResult<Long> res = new ResResult<Long>();
		try{
		List<AdDevice> list = adDeviceServiceImpl.getAdDeviceByCode(ctx, null, code);
		if(list!=null && list.size()>0){
			res.setData(list.get(0).getId()) ;
			res.setCode(StatusCode.OK.getValue());
		}else{
			res.setCode(StatusCode.FAILED.getValue());
		}
		}catch(Exception e){
			log.error("getDeviceId error code="+code, e);
			res.setCode(StatusCode.FAILED.getValue());
		}
		return res;
	}
	
	
	
	
	
	
	@RequestMapping("/uploadDeviceInfo")
	@ResponseBody
	public Object uploadDeviceInfo(@RequestBody DeviceInfoReq req) throws Exception {
		Context ctx = new Context();
		ctx.set("userId", -1);
		ResResult<Long> res = new ResResult<Long>();
		try{
			AdDevice adDevice = adDeviceServiceImpl.get(req.getDeviceId());
			if(adDevice == null){
				res.setCode(StatusCode.DEVICE_UNEXISTS.getValue());
				return res;
			}
			adDevice.setBattery(req.getBattery());
			adDevice.setLockScreen(req.getLockScreen());
			adDevice.setHeartbeatTime(new Date());
			adDeviceServiceImpl.save(ctx, adDevice,0);
			res.setCode(StatusCode.OK.getValue());
		}catch(Exception e){
			log.error("uploadDeviceInfo error deviceId="+req.getDeviceId(), e);
			res.setCode(StatusCode.FAILED.getValue());
		}
		return res;
	}
	
	@RequestMapping("/uploadStatus")
	@ResponseBody
	public Object uploadStatus(@RequestBody UploadStatusReq req) throws Exception {
		Context ctx = new Context();
		
		ResResult<Long> res = new ResResult<Long>();
		try{
			AdDevice adDevice = adDeviceServiceImpl.get(req.getDeviceId());
			if(adDevice == null){
				res.setCode(StatusCode.DEVICE_UNEXISTS.getValue());
				return res;
			}
			ctx.set("userId", adDevice.getModifier());
			adDevice.setStatus(req.getStatus());
			adDeviceServiceImpl.save(ctx, adDevice);
			AdPublishDevice adPublishDevice = adPublishResourceServiceImpl.getLastAdPublishDevice(req.getDeviceId(), req.getVersion());
			adPublishDevice.setStatus(req.getStatus());
			adPublishDevice.setRemark(JsonMapper.nonAlwaysMapper().toJson(req.getResoreceIds()));
			adPublishResourceServiceImpl.save(ctx, adPublishDevice);
			res.setCode(StatusCode.OK.getValue());
		}catch(Exception e){
			log.error("uploadDeviceInfo error deviceId="+req.getDeviceId(), e);
			res.setCode(StatusCode.FAILED.getValue());
		}
		return res;
	}
	
	
	@RequestMapping("/getDeviceResource")
	@ResponseBody
	public Object getDeviceResource(@RequestParam Long deviceId,@RequestParam Long version) throws Exception {
		Context ctx = new Context();
		ctx.set("userId", -1);
		Map map = new HashMap();
		
		ResResult<Map> res = new ResResult<Map>();
		try{
			res.setCode(StatusCode.OK.getValue());
			res.setData(map);
			AdDevice adde = adDeviceServiceImpl.get(deviceId);
			if(adde == null){
				res.setCode(StatusCode.DEVICE_UNEXISTS.getValue());
				return res;
			}
			adde.setHeartbeatTime(new Date());	//判断设备是否在线
			adDeviceServiceImpl.save(ctx, adde,0);
			
			Long userId = adDeviceServiceImpl.get(deviceId).getUserId();
			AdPublishDevice adPublishDevice = adPublishResourceServiceImpl.getLastAdPublishDevice(deviceId, null);
			if(adPublishDevice==null){
				map.put("version", version);
				return res;
			}
			Integer newVersion = adPublishDevice.getVersion();
			
			String resoureceIds = adVersionResourceServiceImpl.getByUserIdAndVersion(userId, Long.valueOf(newVersion)).getResourceId();

				List<AdResourceRes> resultList = new ArrayList<AdResourceRes>();
				if(!resoureceIds.equals("")){
				for(String id:resoureceIds.split(",")){
					AdResource ad = adResourceServiceImpl.get(Long.valueOf(id));
					if(ad !=null){
						AdResourceRes  adResourceRes = new AdResourceRes();
						adResourceRes.setId(ad.getId());
						adResourceRes.setType(ad.getExtType());
						adResourceRes.setUrl(fileServer+ad.getFilePath().replace(filePath, ""));
						adResourceRes.setSize(ad.getRemark()==null?0:Long.valueOf(ad.getRemark()));
						resultList.add(adResourceRes);
						
					}
				}
				}
		List<AdResourceRes> videoList = new ArrayList<AdResourceRes>();
		List<AdResourceRes> imageList = new ArrayList<AdResourceRes>();
		for(AdResourceRes adResourceRes:resultList){
			if(adResourceRes.getType().equals("video")){
				videoList.add(adResourceRes);
			}else{
				imageList.add(adResourceRes);
			}
		}
		videoList.addAll(imageList);
		map.put("list", videoList);
		map.put("version", newVersion);
		}catch(Exception e){
			log.error("getDeviceResource error deviceId="+deviceId, e);
			res.setCode(StatusCode.FAILED.getValue());
		}
		
		return res;
	}
}
