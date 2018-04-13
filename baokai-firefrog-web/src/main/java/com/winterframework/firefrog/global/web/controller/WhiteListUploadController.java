package com.winterframework.firefrog.global.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value = "/globeAdmin")
public class WhiteListUploadController extends BaseUploadController {

	@PropertyConfig(value = "userFileUpload.fileSize")
	private String fileSize;

	@PropertyConfig(value = "userFileUpload.fileType")
	private String fileType;

	@RequestMapping(value = "/uploadWhiteListFile")
	public @ResponseBody
	void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException {
		//新增代码，如果文件过大，返回0错误信息
		String result = "";
		String[] filsTypeList = {"txt","TXT","csv","CSV"}; 
		String fileType = "";
		String filsNM = file.getOriginalFilename();
		for(String a : filsTypeList){
			if(filsNM.indexOf(a) >=0){
				fileType = a;
				break;
			}
		}
		/*fileType.add("TXT");
		fileType.add("CSV");*/
		try {
			if( ( file.getSize()<= 0 ) && fileType == ""){
				return;
			}
			StringBuilder sb = new StringBuilder(1024);

			InputStreamReader read = new InputStreamReader(file.getInputStream(), "gbk");//"gbk"
			BufferedReader bufferedReader = new BufferedReader(read);
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				byte[] t = line.getBytes();
				String b = new String(t,"utf-8");
				String rs = "";
				String[] bb = null;
				if(fileType == "CSV" || fileType == "csv"){
					bb = b.split(",");					
				}else if(fileType == "txt" || fileType == "TXT"){
					bb = b.split(";");
				}
				for(String r : bb){
					if(null != r && !r.isEmpty()){
						r = r.replace(";", "");
						rs += (rs == "")? r : ";" + r;
					}
				}
				sb.append(rs);
				sb.append(";");
			}	
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
			result= sb.toString();

			read.close();
		} catch (Exception e) {
			return;
		}
		String str = result;
		response.setContentType("text/html;charset=utf-8");
		 response.setHeader("Cache-Control", "no-cache");
		 PrintWriter pw=response.getWriter(); //输出中文，这一句一定要放到
		 pw.write(str);		
		return ;
	}
}
