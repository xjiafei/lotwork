package com.winterframework.firefrog.common.upload;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.firefrog.help.web.controller.AdminHelpManagerController;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
 * 邮件模板中的图片上传处理
*/
@Controller
@RequestMapping(value = "/xheditorAdmin")
public class XheditorImgUploadController extends BaseUploadController {

	private static final Logger logger = LoggerFactory.getLogger(AdminHelpManagerController.class);
	@PropertyConfig(value = "url.imageserver.visit")
	private String urlViewPic;

	/** 
	 * 上传模板图片
	 * @throws JSONException 
	*/
	@RequestMapping(value = "/uploadTemplateImg")
	@ResponseBody
	public Object uploadTemplateImg(@RequestParam(value = "filedata", required = false) MultipartFile file,
			HttpServletRequest request) {
		ImgUploadOperater operater = null;
		try {
			operater = this.upload(file, request.getSession().getServletContext().getRealPath("imgUpload"), null, null,null);
		} catch (Exception e) {
			logger.error("update uploadSpaceImg error", e);
		}
		StringBuffer resultStr = new StringBuffer("{");
		//"{\"err\":\"\",\"msg\":{\"url\":\"http://192.168.1.111:8091/e1c2fbf1-a7ef-4148-a2c8-d54f72792fda_aa.jpg\",\"localfile\":\"test.jpg\",\"id\":\"1\"}}";
		//拼装字符串，格式固定，不能更改，否则会报错
		if (operater.getStatus() == 100) {
			resultStr.append("\"err\":\"\",").append("\"msg\":{\"url\":").append("\"" + urlViewPic)
					.append(operater.getFileUrl()).append("\"}}");
		}
		
		// TODO异常情况暂时处理不了
		return resultStr.toString();
	}

}
