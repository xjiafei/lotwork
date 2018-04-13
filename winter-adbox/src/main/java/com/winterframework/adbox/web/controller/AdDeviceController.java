package com.winterframework.adbox.web.controller;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.entity.AdDevice;
import com.winterframework.adbox.entity.AdUser;
import com.winterframework.adbox.service.IAdDeviceService;
import com.winterframework.adbox.service.IAdUserService;
import com.winterframework.adbox.utils.PageResult;

import jxl.Sheet;
import jxl.Workbook;
 



 
/**
 * @author 登陆ip分发
 *
 */
@Controller("adDeviceController")
@RequestMapping("/device")
public class AdDeviceController {
	
	
	private static final Logger log = LoggerFactory.getLogger(DeviceInterfaceController.class);
	@Resource(name = "adUserServiceImpl")
	private IAdUserService adUserServiceImpl;

	@Resource(name = "adDeviceServiceImpl")
	private IAdDeviceService adDeviceServiceImpl;
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	@Resource(name = "localeResolver")
	private LocaleResolver localeResolver;
	@RequestMapping("/userList")
	@ResponseBody
	public Object userList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<AdUser> adList = new ArrayList<AdUser>();
		AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
		if(adUser.getType().intValue()==1){
			adList = adUserServiceImpl.getUserList(2,null,null);
		}else{
			adList.add(adUser);
			if(adUser.getType().intValue()==2){
				adList.addAll(adUserServiceImpl.getUserList(3,null,adUser.getId()));
			}
		}
		return adList;
	}
	
	@RequestMapping("/toDeviceList")
	public ModelAndView toDeviceList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("deviceList");
		String userId = request.getParameter("userId");
		view.addObject("userId", userId==null?null:Long.valueOf(userId));
		return view;
	}
	
	
	@RequestMapping("/deviceList")
	@ResponseBody
	public Object deviceList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String code = request.getParameter("code");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		String currentPage = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		List<AdDevice> list = new ArrayList<AdDevice>();
		Integer count = 0;
		
		try{
			if(userId == null||userId.equals("")){
				List<AdUser> listU = (List<AdUser>)request.getSession().getAttribute("userList");
				if(listU!=null&&listU.size()>0){
					userId = String.valueOf(listU.get(0).getId());
				}else{
					return null;
				}
			}
			int start = (Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize);
			if(sort != null&&sort != ""){
				if(sort.equals("onfflineName")){ //battery updateTimeString statusName
					sort = "heartbeat_time ";
				}else if(sort.equals("battery")){
					sort = "battery ";
				}else if(sort.equals("updateTimeString")){
					sort = "update_time ";
				}else if(sort.equals("statusName")){
					sort = "status ";
				}else if(sort.equals("createTimeString")){
					sort = "create_time ";
				}
				sort = sort + order;
			}
			list = adDeviceServiceImpl.getAdDeviceList(null, Long.valueOf(userId), code, sort, start, Integer.valueOf(pageSize));
			
			if(list!= null && list.size()>0){
				for(AdDevice adDevice:list){
					adDevice.setUpdateName(adDevice.getModifier()==null?null:adUserServiceImpl.get(adDevice.getModifier()).getName());
					String onfflineName = "offline";
					if(adDevice.getHeartbeatTime()!= null && DateUtils.addMinutes(adDevice.getHeartbeatTime(), 6).after(new Date())){
						onfflineName="online";
					}
					String statusName =null; 
					if(adDevice.getStatus() == null){
						statusName = null;
					}else if(adDevice.getStatus().intValue() ==1){
						statusName="status_success";
					}else if(adDevice.getStatus().intValue() == 0){
						statusName="status_failed";
					}else if(adDevice.getStatus().intValue() == 2){
						statusName="status_load_wait";
					}else if(adDevice.getStatus().intValue() ==3){
						statusName="status_publish_wait";
					}
					
					Locale locale=localeResolver.resolveLocale(request);
					if(onfflineName!=null){
						adDevice.setOnfflineName(messageSource.getMessage(onfflineName, null, locale));
					}
					if(statusName!=null){
						adDevice.setStatusName(messageSource.getMessage(statusName, null, locale));
					}
				}
				
				count = adDeviceServiceImpl.getAdDeviceList(null, Long.valueOf(userId), code, sort, 0, Integer.MAX_VALUE).size();
			}
			
		}catch(Exception e){
			log.error("deviceList error", e);
		}
		PageResult<AdDevice> pageResult = new PageResult<AdDevice>();
		
		pageResult.setRows(list);
		pageResult.setTotal(count);
//		view.addObject("data",JsonUtils.toJson(pageResult));
		return pageResult;
	}
	
	
	/*@RequestMapping("/addDevice")
	public ModelAndView addDevice(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView view = null;
		try{
			view = new ModelAndView("deviceList");
			String userId = request.getParameter("userId");
			AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
			Context ctx = new Context();
			ctx.set("userId", adUser.getId());
			if(userId == null){
				userId = String.valueOf(adUser.getId());
			}
			
			AdDevice adDevice = new AdDevice();
			adDevice.setCode(request.getParameter("code"));
			adDevice.setAddress(request.getParameter("adress"));
			adDevice.setUserId(Long.valueOf(userId));
			adDevice.setStatus(5);//新增
			adDeviceServiceImpl.save(ctx, adDevice);
			
		}catch(Exception e){
			log.error("addDevice error", e);
			throw new Exception();
		}
		return this.deviceList(request, response);
	}*/
	
	
	@RequestMapping("/deleteDevice")
	@ResponseBody
	public Object deleteDevice(String ids) throws Exception {
		try{
			String[] id = ids.split(",");
			Context ctx = new Context();
	        ctx.set("userId", -1);
			for(String iiid:id){
				if(iiid!=null&&!iiid.equals("")){
				AdDevice ad = adDeviceServiceImpl.get(Long.valueOf(iiid));
				ad.setStatus(4);//删除
				adDeviceServiceImpl.save(ctx, ad);
				}
			}
		}catch(Exception e){
			log.error("deleteDevice error", e);
			return 0;
		}
		return 1;
	}

	
	@RequestMapping("/batchAddDevice")
	public ModelAndView batchAddDevice(@RequestParam("uploadExcel") CommonsMultipartFile uploadExcel,HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
	        String userId = request.getParameter("userId");
	        AdUser adUser = (AdUser)request.getSession().getAttribute("loginUser");
	        Long loginId = adUser.getId();
	        if(userId == null){
	        	return this.toDeviceList(request, response);
	        }
	        Context ctx = new Context();
	        ctx.set("userId", loginId);
	        MultipartFile multiFile = uploadExcel;
	        List<AdDevice> list = this.readExcel(multiFile.getInputStream());
	        if(list !=null){
	        	List<AdDevice> removeList = new ArrayList<AdDevice>();
	        	for(AdDevice adDevice:list){
	            	adDevice.setUserId(Long.valueOf(userId));
	            	adDevice.setStatus(5);//新增
	            	List<AdDevice> dbList = adDeviceServiceImpl.getAdDeviceByCode(ctx, null, adDevice.getCode());
	            	if(dbList!=null && dbList.size()>0){
	            		removeList.add(adDevice);
	            	}
	            }
	        	list.removeAll(removeList);
	        	adDeviceServiceImpl.save(ctx, list);
	        }
	    } catch (Exception e) {
	    	log.error("upload error", e);
		}
		return this.toDeviceList(request, response);
	}
	
	public List<AdDevice> readExcel(InputStream  inputStream ) throws Exception{
		Workbook wb = null;
		List<AdDevice> list = null;
		try {    
		       //构造Workbook（工作薄）对象    
		       wb=Workbook.getWorkbook(inputStream);    
		   } catch (Exception e) {    
		       log.error("readExcel error", e) ;
		       throw e;
		   }
		Sheet[] sheet = wb.getSheets();
		if(sheet!=null&&sheet.length>0){
			list = new ArrayList<AdDevice>();
			int rowNum = sheet[0].getRows();
			for(int j=1;j<rowNum;j++){
				String code = sheet[0].getCell(0, j).getContents();
				String address = null;
				if(sheet[0].getCell(1, j)!=null){
					address = sheet[0].getCell(1, j).getContents();
				}
				AdDevice adDevice = new AdDevice();
				adDevice.setAddress(address);
				adDevice.setCode(code);
				list.add(adDevice);
			}
		}
		return list;
	}
	
}
