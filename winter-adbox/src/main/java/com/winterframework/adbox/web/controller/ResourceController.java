package com.winterframework.adbox.web.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;



import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.dto.ResResult;
import com.winterframework.adbox.entity.AdDevice;
import com.winterframework.adbox.entity.AdPublishDevice;
import com.winterframework.adbox.entity.AdResource;
import com.winterframework.adbox.entity.AdUser;
import com.winterframework.adbox.entity.AdVersionResource;
import com.winterframework.adbox.enums.StatusCode;
import com.winterframework.adbox.service.IAdDeviceResourceService;
import com.winterframework.adbox.service.IAdDeviceService;
import com.winterframework.adbox.service.IAdPublishResourceService;
import com.winterframework.adbox.service.IAdResourceService;
import com.winterframework.adbox.service.IAdUserService;
import com.winterframework.adbox.service.IAdVersionResourceService;
import com.winterframework.adbox.utils.FileUtil;
import com.winterframework.adbox.utils.PageResult;
import com.winterframework.modules.spring.exetend.PropertyConfig;

 
/** 校验文件类型http://blog.163.com/songyalong1117@126/blog/static/17139189720144273308468/
 *ResourceController
 * @ClassName
 * @Description
 * @author ibm
 * 2015年8月17日
 */
@Controller("resourceController")
@RequestMapping("/resource")
public class ResourceController {
	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
	@PropertyConfig(value = "filePath")
	private String filePath;
	@Resource(name="fileUtil")
	private FileUtil fileUtil;
	
	
	@Resource(name = "adResourceServiceImpl")
	private IAdResourceService adResourceServiceImpl;
	
	@Resource(name = "adDeviceServiceImpl")
	private IAdDeviceService adDeviceServiceImpl;
	
	@Resource(name = "adDeviceResourceServiceImpl")
	private IAdDeviceResourceService adDeviceResourceServiceImpl;
	
	
	@Resource(name = "adPublishResourceServiceImpl")
	private IAdPublishResourceService adPublishResourceServiceImpl;
	
	@Resource(name = "adVersionResourceServiceImpl")
	private IAdVersionResourceService adVersionResourceServiceImpl;
	
	@Resource(name = "adUserServiceImpl")
	private IAdUserService adUserServiceImpl;
	
	
	@RequestMapping("/deleteResource")
	@ResponseBody
	public Object deleteResource(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			String ids = request.getParameter("ids");
			String[] id = ids.split(",");
			AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
			Context ctx = new Context();
	        ctx.set("userId", adUser.getId());
			for(String iiid:id){
				if(iiid!=null&&!iiid.equals("")){
					AdResource adr = adResourceServiceImpl.get(Long.valueOf(iiid));
					Long userId = adr.getUserId();
					adResourceServiceImpl.remove(ctx, Long.valueOf(iiid));
					 List<AdDevice> ads = adDeviceServiceImpl.getAdDeviceByCode(ctx, userId, null);
			         for(AdDevice ad:ads){
			            ad.setStatus(3);//等待发布
			            adDeviceServiceImpl.save(ctx, ad);
			         }
				}
			}
		}catch(Exception e){
			log.error("deleteDevice error", e);
			return 0;
		}
		return 1;
	}
	
	@RequestMapping("/upload")
	public ModelAndView upload(@RequestParam("uploadExcel") CommonsMultipartFile uploadExcel,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
	        String userId = request.getParameter("userId");
	        AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
	        Long loginId = adUser.getId();
	        if(userId == null){
		        return this.toResList(request, response);
	        }
	        Context ctx = new Context();
	        ctx.set("userId", loginId);
	        MultipartFile multiFile = uploadExcel;
            String type = fileUtil.getExtension(multiFile.getOriginalFilename());
            String fileNameNotType = org.apache.commons.codec.digest.DigestUtils.md5Hex(multiFile.getBytes());
			//压缩图片名称
			String fileName = fileNameNotType + "." + type;
			
			type = this.getType(type);
			if(type == null){
				return this.toResList(request, response);
			}
			Long size = multiFile.getSize();
			if(size >209715200){
				request.setAttribute("message", "文件大小不能超过200M！");
				return this.toResList(request, response);
			}
            String filePath=getFilePath(type,fileName,userId); 
            List<AdResource> dbAdResources = adResourceServiceImpl.getAdResourceByPath(ctx, filePath, Long.valueOf(userId));
            if(dbAdResources!=null&&dbAdResources.size()>0){
            	return this.toResList(request, response);
            	
            }
            fileUtil.save(filePath,multiFile);
            AdResource adResource = new AdResource();
            adResource.setExtType(type);
            adResource.setFileName(multiFile.getOriginalFilename());
            adResource.setFilePath(filePath);
            adResource.setUserId(Long.valueOf(userId));
            adResource.setRemark(size+"");
            adResourceServiceImpl.save(ctx, adResource);
            List<AdDevice> ads = adDeviceServiceImpl.getAdDeviceByCode(ctx, Long.valueOf(userId), null);
            for(AdDevice ad:ads){
            	ad.setStatus(3);//等待发布
            	adDeviceServiceImpl.save(ctx, ad);
            }
	    } catch (Exception e) {
	    	log.error("upload error", e);
	    	
		}
	    return this.toResList(request, response);
	}
	
	
	@RequestMapping("/toResList")
	public ModelAndView toResList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("fileList");
		String userId = request.getParameter("userId");
		String message =  (String)request.getAttribute("message");
		view.addObject("userId", userId==null?null:Long.valueOf(userId));
		view.addObject("message", message==null?null:message);
		return view;
	}
	
	@RequestMapping("/getResList")
	@ResponseBody
	public Object getResList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId = request.getParameter("userId");
		PageResult<AdResource> pg = new PageResult<AdResource>();
		List<AdResource> listRes = new ArrayList<AdResource>();
		pg.setRows(listRes);
		try{
			if(userId == null||userId.equals("")){
				List<AdUser> listU = (List<AdUser>)request.getSession().getAttribute("userList");
				if(listU!=null&&listU.size()>0){
					userId = String.valueOf(listU.get(0).getId());
				}else{
					return null;
				}
			}
			Context ctx = new Context();
			ctx.set("userId", userId);
			listRes = adResourceServiceImpl.getAdResourceByPath(ctx, null, Long.valueOf(userId));
			if(listRes !=null && listRes.size()>0){
				for(AdResource adResource:listRes){
					adResource.setCreateName(adUserServiceImpl.get(adResource.getCreator()).getName());
				}
				
				pg.setRows(listRes);
				return pg;
			}
		}catch(Exception e){
			log.error("getResList error", e);
		}
		return pg;
	}
	
	
	
	@RequestMapping("/publish")
	@ResponseBody
	public Object publish(HttpServletRequest request,HttpServletResponse response){
		ResResult<Long> res = new ResResult<Long>();
		try{
			res.setCode(1);
			String userId = request.getParameter("userId");
		    AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
		    Long loginId = adUser.getId();
		    if(userId == null){
			    userId = String.valueOf(loginId);
		    }
		    Context ctx = new Context();
		    ctx.set("userId", loginId);
		   
	        List<AdResource> dbAdResources = adResourceServiceImpl.getAdResourceByPath(ctx, null, Long.valueOf(userId));
	        String resourceIds = "-1,";
	        if(dbAdResources!= null && dbAdResources.size()>0){
	        	for(AdResource adResource:dbAdResources){
	        		/*if(adResource.getCreator() == loginId.longValue()){*/
	        			resourceIds = resourceIds+adResource.getId()+",";
	        		/*}*/
	        	}
	        }/*else{
	        	res.setCode(2);
        		return res;
	        }*/
	        
	        if(!resourceIds.equals("")){
	        	resourceIds = resourceIds.substring(0, resourceIds.length()-1);}
	        	List<AdDevice> ads = adDeviceServiceImpl.getAdDeviceByCode(ctx, Long.valueOf(userId), null);
	        	if(!resourceIds.equals("-1")){
	        	AdVersionResource dbAdVersionResource = adVersionResourceServiceImpl.getAdVersionResourceByResId(resourceIds,Long.valueOf(userId));
	        	if(dbAdVersionResource != null){//已经发布过的
	        		List<AdPublishDevice> list = new ArrayList<AdPublishDevice>();
	        		boolean isAdd=false;
	        		for(AdDevice adDevice : ads){
	        			if(adDevice.getStatus()==5){//资源没有变，但是设备有新增
	        				AdPublishDevice adres = new AdPublishDevice();
			            	adres.setDeviceId(adDevice.getId());
			            	adres.setStatus(2);//等待下载
			            	adres.setVersion(dbAdVersionResource.getVersion().intValue());
			            	list.add(adres);
			            	adDevice.setStatus(2);//等待下载
			            	adDeviceServiceImpl.save(ctx, adDevice);
			            	isAdd=true;
	        			}
	        		}
	        		adPublishResourceServiceImpl.save(ctx, list);
	        		if(!isAdd){
		        		res.setCode(3);
	        		}
	        		return res;
	        	}}
	        	Long version = adVersionResourceServiceImpl.getLastVersion(Long.valueOf(userId));
	        	AdVersionResource adVersionResource = new AdVersionResource();
	        	adVersionResource.setStatus(0);
	        	adVersionResource.setResourceId(resourceIds);
	        	adVersionResource.setUserId(Long.valueOf(userId));
	        	adVersionResource.setVersion((version==null||version==0?1:version+1));
	        	adVersionResourceServiceImpl.save(ctx, adVersionResource);
	        	
	        	
	            if(ads != null && ads.size()>0){
	            	List<AdPublishDevice> list = new ArrayList<AdPublishDevice>();
	            	for(AdDevice adDevice : ads){
	            		AdPublishDevice adres = new AdPublishDevice();
		            	adres.setDeviceId(adDevice.getId());
		            	adres.setStatus(2);//等待下载
		            	adres.setVersion(adVersionResource.getVersion().intValue());
		            	list.add(adres);
		            	adDevice.setStatus(2);//等待下载
		            	adDeviceServiceImpl.save(ctx, adDevice);
	            	}
	            	adPublishResourceServiceImpl.save(ctx, list);
	            }
		}catch(Exception e){
			log.error("publish error", e);
	    	res.setCode(0);
			res.setMessage("发布文件错误！");
		}
		return res;
	}
	
 	
	private String getFilePath(String type,String fileName,String userId){  
		final String FILE_SUBFIXX = "";
		return filePath + type + File.separator  + fileName + FILE_SUBFIXX;
	}
	
	public String getType(String type){
		List<String> images = new ArrayList<String>();
		images.add("jpg");
		images.add("jpeg");
		images.add("png");
		images.add("bmp");
		List<String> videos = new ArrayList<String>();
		videos.add("3gp");
		videos.add("mp4");
		videos.add("rmvb");
		videos.add("avi");
		if(images.contains(type.toLowerCase())){
			return "image";
		}else if(videos.contains(type.toLowerCase())){
			return "video";
		}else{
			return null;
		}
	}
	
	@RequestMapping("/downLoad")
	public Object downLoad(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
            // path是指欲下载的文件的路径。
			String path = request.getSession().getServletContext().getRealPath("");
            File file = new File(path+"/deviceListTemplate.xls");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.error("downLoad error",ex);
        }
        return response;
	}
	
	
}
