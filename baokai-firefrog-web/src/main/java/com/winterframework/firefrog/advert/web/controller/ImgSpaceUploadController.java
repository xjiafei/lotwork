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

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.upload.BaseUploadController;
import com.winterframework.firefrog.common.upload.ImgSize;
import com.winterframework.firefrog.common.upload.ImgUploadOperater;
import com.winterframework.modules.spring.exetend.PropertyConfig;



/** 
* @ClassName: ImgUploadController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-7 下午1:47:44 
*  
*/
@Controller
@RequestMapping(value = "/adAdmin")
public class ImgSpaceUploadController extends BaseUploadController {
	
	@PropertyConfig(value = "imgUpload.imgSize")
	private String imgSize;
	
	@PropertyConfig(value = "imgUpload.imgType")
	private String imgType;

	private static final Logger logger = LoggerFactory.getLogger(ImgSpaceUploadController.class);
	@RequestMapping(value = "/uploadSpaceImg")
	@ResponseBody
	public ModelAndView uploadSpaceImg(@RequestParam(value = "tempFile", required = false) MultipartFile file, HttpServletRequest request) {
		ModelAndView mv =new ModelAndView("/advert/uploadAdSpaceImg");
		ImgUploadOperater operater = null;
		try {
			List<String> fileType = Arrays.asList(imgType.split(","));
			/*fileType.add("JPG");
			fileType.add("GIF");
			fileType.add("PNG");*/
			Long size = Long.valueOf(imgSize);//1024*100L;
			operater= this.upload(file, request.getSession().getServletContext().getRealPath("imgUpload"),fileType,size,null);
		} catch (Exception e) {
			logger.error("update uploadSpaceImg error",e);
		}
		mv.addObject("operater", operater);
		return mv;
	}

	@RequestMapping(value = "/uploadSpacePlaceHolderImg")
	@ResponseBody
	public ModelAndView uploadPalceHolder(@RequestParam(value = "tempFilePlaceHolder", required = false) MultipartFile file,@RequestParam(value="isDirectForward",required=false) Boolean isDirectForward,@RequestParam(value="imgWidth",required=false) Long imgWidth,@RequestParam(value="imgHeigh",required=false)  Long imgHeigh, HttpServletRequest request) {
		ModelAndView mv =new ModelAndView("/advert/uploadAdSpaceHolderImg");
		if(isDirectForward!=null && isDirectForward){
			return mv;
		}
		ImgUploadOperater operater = new ImgUploadOperater();
		try {
			List<ImgSize> imgSize =new ArrayList<ImgSize>();
			ImgSize size = new ImgSize();
			size.setHeight(imgHeigh);
			size.setWidth(imgWidth);
			imgSize.add(size);
			operater = this.upload(file, request.getSession().getServletContext().getRealPath("imgUpload"),null,null,imgSize);
		} catch (Exception e) {
			logger.error("update uploadPalceHolder error",e);
		}
		if(operater.getStatus() == 3){
			operater.setMessage("图片尺寸必须和广告位尺寸一致。");
		}
		mv.addObject("isAddImg", 1);
		mv.addObject("operater", operater);
		return mv;
	}
	
	@RequestMapping(value = "/forwardSpaceHolder")
	public ModelAndView forwardSpaceHolder(@RequestParam(value = "placeHolderImgs", required = false) String placeHolderImgs,HttpServletRequest request) {
		ModelAndView mv =new ModelAndView("/advert/uploadAdSpaceHolderImg");
		mv.addObject("isAddImg", 0);
		return mv;
	}
}
