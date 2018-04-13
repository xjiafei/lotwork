package com.winterframework.firefrog.notice.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.firefrog.common.upload.BaseUploadController;
import com.winterframework.firefrog.common.upload.ImgUploadOperater;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;


@Controller
@RequestMapping(value = "/noticeAdmin")
public class NoticeUploadController extends BaseUploadController {

	@PropertyConfig(value = "userFileUpload.fileSize")
	private String fileSize;

	@PropertyConfig(value = "userFileUpload.fileType")
	private String fileType;

	@RequestMapping(value = "/uploadUserFile")
	public @ResponseBody
	void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException {
		//新增代码，如果文件过大，返回0错误信息
		OperateStatusResponse result = new OperateStatusResponse();
		List<String> fileTypes = Arrays.asList(fileType.split(","));
		/*fileType.add("TXT");
		fileType.add("CSV");*/
		ImgUploadOperater operater = new ImgUploadOperater();
		try {
			if (!checkFile(file, fileTypes, Long.valueOf(fileSize), operater)) {
				result.setMessage(operater.getMessage());
				result.setStatus(0);
				//return DataConverterUtil.convertObject2Json(result);
				return;
			}
			StringBuilder sb = new StringBuilder(1024);

			InputStreamReader read = new InputStreamReader(file.getInputStream(), "gbk");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				byte[] t = line.getBytes();
				String b = new String(t,"utf-8");
				sb.append(b);	
				sb.append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
			result.setMessage(sb.toString());
			result.setStatus(1);
			read.close();
		} catch (Exception e) {
			result.setMessage("文件上传错误");
			result.setStatus(0);
		}
		String str = result.getMessage();
		response.setContentType("text/html;charset=utf-8");
		 response.setHeader("Cache-Control", "no-cache");
		 PrintWriter pw=response.getWriter(); //输出中文，这一句一定要放到
		 pw.write(str);
		
		return ;
	}
}
