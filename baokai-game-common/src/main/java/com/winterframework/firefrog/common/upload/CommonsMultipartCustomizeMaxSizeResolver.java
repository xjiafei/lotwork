package com.winterframework.firefrog.common.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.UrlPathHelper;

/** 
* @ClassName: CommonsMultipartCustomizeMaxSizeResolver 
* @Description: 为不同的url定制最大的内容限制。 
* @author page
* @date 2013-9-16 下午4:41:18 
*  
*/
public class CommonsMultipartCustomizeMaxSizeResolver extends CommonsMultipartResolver {
	private Map<String,String> maxUploadSizeUrlMap;

	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding, request);
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			return parseFileItems(fileItems, encoding);
		}
		catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		}
		catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}

	public Map<String, String> getMaxUploadSizeUrlMap() {
		return maxUploadSizeUrlMap;
	}

	public void setMaxUploadSizeUrlMap(Map<String, String> maxUploadSizeUrlMap) {
		this.maxUploadSizeUrlMap = maxUploadSizeUrlMap;
	}

	/** 
	* @Title: prepareFileUpload 
	* @Description: set max upload size for different url.
	* @param encoding
	* @param request
	* @return
	*/
	private FileUpload prepareFileUpload(String encoding, HttpServletRequest request) {
		FileUpload upload = prepareFileUpload(encoding);
		
		UrlPathHelper helper = new UrlPathHelper();
		String servletPath = helper.getServletPath(request);
		if(maxUploadSizeUrlMap!=null){
			String maxUploadSize = maxUploadSizeUrlMap.get(servletPath);
			long maxSize = Long.parseLong(maxUploadSize);
			upload.setSizeMax(maxSize);
		}
		
		return upload;
	} 
	
	
}
