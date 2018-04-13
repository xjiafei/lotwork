/**   
* @Title: ImgUploadController.java 
* @Package com.winterframework.firefrog.advert.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-7 下午1:47:44 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.common.upload.BaseUploadController;
import com.winterframework.firefrog.common.upload.ImgSize;
import com.winterframework.firefrog.common.upload.ImgUploadOperater;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;


/** 
* @ClassName: ImgUploadController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-7 下午1:47:44 
*  
*/
@Controller
@RequestMapping(value = "/adAdmin")
public class ImgUploadController extends BaseUploadController {

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "url.allAdSpace.query")
	private String queryAllAdSpaceUrl;
	
	@PropertyConfig(value = "url.imageserver.visit")
	protected String imageServerVisit;
	
	@PropertyConfig(value = "imgUpload.imgSize")
	private String imgSize;
	
	@PropertyConfig(value = "imgUpload.imgType")
	private String imgType;
	
	

	@RequestMapping(value = "/uploadImg")
	public String upload(@RequestParam(value = "tempFile", required = false) MultipartFile file,
			@RequestParam(required = false) String ids, HttpServletRequest request,Model model) throws Exception{
		ImgUploadOperater operater = new ImgUploadOperater();
		try {
			List<AdSpaceStruc> cateList = (List<AdSpaceStruc>) httpClient
					.invokeHttp(urlPath + queryAllAdSpaceUrl, new AdSpaceStruc(), new TypeReference<Response<List<AdSpaceStruc>>>() {
					}).getBody().getResult();
			
			//文件宽高
			List<ImgSize> imgSizes = new ArrayList<ImgSize>();
			for (String id : ids.split(",")) {
				for (AdSpaceStruc space : cateList) {
					if (space.getId().longValue() == Long.valueOf(id)) {
						ImgSize imgSize = new ImgSize();
						imgSize.setHeight(space.getHeight());
						imgSize.setWidth(space.getWidth());
						imgSizes.add(imgSize);
						break;
					}
				}
			}
			
			//文件类型
			List<String> fileType = Arrays.asList(imgType.split(","));
			//文件大小
			Long size = Long.valueOf(imgSize);//1024*100L;
			operater = this.upload(file, request.getSession().getServletContext().getRealPath("imgUpload"),fileType,size,imgSizes);
			model.addAttribute("width", operater.getMessage() == null?operater.getWidth():0);
			model.addAttribute("height", operater.getMessage() == null?operater.getHeight():0);
			model.addAttribute("fileUrl", operater.getMessage() == null?operater.getFileUrl():"22222");
			model.addAttribute("imageServerVisit", imageServerVisit);
			model.addAttribute("status", operater.getMessage() == null?1:0);
			model.addAttribute("message", operater.getMessage());
		} catch (Exception e) {
			model.addAttribute("width", 1);
			model.addAttribute("height", 1);
			model.addAttribute("fileUrl", "22222");
			model.addAttribute("imageServerVisit", imageServerVisit);
			model.addAttribute("message", e.getMessage());
			model.addAttribute("status", 0);
		}
		return "/advert/uploadAdvertImg";
	}

}
