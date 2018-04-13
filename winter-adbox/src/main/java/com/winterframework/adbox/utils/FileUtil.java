package com.winterframework.adbox.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Component("fileUtil")
public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	 
	public static final String FILE_SUBFIXX = "";

	@PropertyConfig(value = "filePath")
	private String filePath;
	
	public String save(String filePath,String content){
		if(null==filePath) return null;
		if(null==content){
			content="";
		}
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File destFile = new File(filePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			bufferedWriter = new BufferedWriter(new FileWriter(destFile));
			char buf[] = new char[1024]; //字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
			return destFile.getAbsolutePath();
		} catch (IOException e) {
			log.error("Error occurs when save file",e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return null;
	}
	public String save(String filePath,byte[] content){
		if(null==filePath) return null;
		try {
			File destFile = new File(filePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			FileUtils.writeByteArrayToFile(destFile, content);
			return destFile.getAbsolutePath();
		} catch (IOException e) {
			log.error("Error occurs when save file",e);
		} finally {
		}
		return null;
	}
	public String save(byte[] content){
		return save(createFilePath(DateUtils.currentDate(), System.currentTimeMillis()+""),content);
	}
	/**
	 * @param content
	 * @return file path
	 */
	public String save(String content) {
		return save(createFilePath(DateUtils.currentDate(), System.currentTimeMillis()+""),content);
	}
	public String save(MultipartFile file) {
		return save(createFilePath(DateUtils.currentDate(), System.currentTimeMillis()+""),file);
	}
	public String save(String filePath,MultipartFile multifile) { 
		if(null!=filePath && null!=multifile){
			try{ 
				 File destFile = new File(filePath);
				 if (!destFile.exists()){
					 destFile.mkdirs();
		         }
				 multifile.transferTo(destFile);
				 return destFile.getAbsolutePath();
			}catch (IOException e) {
				log.error("Error occurs when save file",e);
			} finally {
			}
		}
		return null;
	}
	
	

	private String createFilePath(Date date,String fileName) { 
		String strDate = DateUtils.format(date,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
		return filePath + File.separator + strDate + File.separator  + fileName + FILE_SUBFIXX;
	}

	public boolean deleteFile( Date date,String fileName) {
		File distFile = new File(createFilePath(date,fileName));
		return distFile.delete();
	}
	
	public String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1);
		}
		return ext;
	}
}
