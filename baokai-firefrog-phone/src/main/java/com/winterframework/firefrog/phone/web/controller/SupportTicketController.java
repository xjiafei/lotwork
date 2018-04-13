package com.winterframework.firefrog.phone.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.sup.order.controller.dto.FrontTicketCloseRequest;
import com.winterframework.sup.order.controller.dto.FrontTicketDetailRequest;
import com.winterframework.sup.order.controller.dto.FrontTicketListRequest;
import com.winterframework.sup.order.controller.dto.FrontTicketSaveRequest;

@Controller("supportTicketController")
@RequestMapping(value = "/phone/support")
public class SupportTicketController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SupportTicketController.class);
	
	@PropertyConfig(value="url.support.createTicket")
	private String createTicket;
	
	@PropertyConfig(value="url.support.saveTicket")
	private String saveTicket;
	
	@PropertyConfig(value="url.support.ticketList")
	private String ticketList;
	
	@PropertyConfig(value="url.user.queryUserDetailInfoByAccount")
	private String queryUserByNameUrl;
	
	@PropertyConfig(value="url.support.uploadFiles")
	private String uploadFiles;
	
	@PropertyConfig(value="url.support.ticketDetail")
	private String ticketDetail;
	
	@PropertyConfig(value="url.support.secondSaveTicket")
	private String secondSaveTicket;
	
	@PropertyConfig(value="url.support.closeTicket")
	private String closeTicket;
	
	@PropertyConfig(value="url.support.queryUserUnReadTicket")
	private String queryUserUnReadTicket;
	
	@PropertyConfig(value="upload.support.rootPath")
	private String uploadSupportPath;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private static <T> T getRequestData(Request<T> request){
		return request.getBody().getParam();
	}
	
	private static <T> T getResponseData(Response<T> response){
		return response.getBody().getResult();
	}
	
	private Integer getUserLvl(UserToken ut) throws Exception{
		UserStruc userRequestData = new UserStruc();
		userRequestData.setAccount(ut.getUserName());
		log.debug("username : " + ut.getUserName());
		//userRequestData.setAccount("suhern5");
		
		Response<UserDetailResponse> userDataResponse = httpClient.invokeHttp(firefrogUrl + queryUserByNameUrl, 
				userRequestData, 
				new TypeReference<Response<UserDetailResponse>>() {});
		return userDataResponse.getBody().getResult().getUserStruc().getVipLvl();
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private <T> String getAccountByToken(Request<T> request) throws Exception{
		String token = request.getHead().getSessionId();
		return getUserNameByToken(token);
	}
	
	@RequestMapping(value = "/createTicket")
	@ResponseBody
	public Response<String> createTicket(@RequestBody Request<FrontTicketListRequest> request) throws Exception {
		
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("createTicket 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		UserToken ut = getUserToken(account);
		//根据用户名找用信息。
		int userLvl = getUserLvl(ut);
		
		FrontTicketListRequest frontTicketListRequest = getRequestData(request);
		frontTicketListRequest.setLvl(String.valueOf(userLvl));
		frontTicketListRequest.setPlatformUserId(ut.getUserId());
		frontTicketListRequest.setAccount(ut.getUserName());
		
		
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + createTicket, 
				frontTicketListRequest, 
				new TypeReference<Response<String>>(){});
		
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + createTicket, result);
		response.setResult(result);
		
		return clientResponse;
	}
	
	/**
	 * 檔案上傳  uploadFile.js
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/uploadFiles")
	@ResponseBody
	public Response<String> uploadTicketFile(HttpServletRequest request) throws Exception {
		log.debug(request.getContentType());
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String token = multipartRequest.getParameter("sessionId");
		String account = getUserNameByToken(token);
		
		Response<String> response = new Response<String>();
		
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			response.setResult("upload error");
			log.error("uploadTicketFile 客服工單, 檔案上傳發生錯誤 , 無法對應帳號, token : {}", token);
			return response;
		}
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<File> needDeleteFiles = new ArrayList<File>();
		MultipartEntity entity = new MultipartEntity();
		
		for(String key : fileMap.keySet()){
			MultipartFile file = fileMap.get(key);
//			String originalName = file.getOriginalFilename();
			String fileKey = key.replaceAll("filedata", "filename");
			String originalName = multipartRequest.getParameter(fileKey);
			log.debug("產生temp檔案 : {}{}", uploadSupportPath, originalName);
			String fileName = uploadSupportPath + originalName;
			File somethingFile = new File(fileName);
			
			OutputStream outputStream = new FileOutputStream(somethingFile);
			InputStream is = file.getInputStream();
			
			IOUtils.copy(is, outputStream);
			is.close();
			outputStream.close();
			
			ContentBody cbFile = new FileBody(somethingFile);
			entity.addPart(key, cbFile);
			
			needDeleteFiles.add(somethingFile);
		}

	    HttpPost post = new HttpPost(supportUrl + uploadFiles);
	    post.setEntity(entity);
	    HttpClient client = new DefaultHttpClient();
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    
	    String result = client.execute(post, responseHandler);
		response = mapper.readValue(result, new TypeReference<Response<String>>(){});
		
		for(File tempFile : needDeleteFiles){
			if(tempFile.delete()){
				log.debug(tempFile.getName() + " is deleted!");
			}else{
				log.debug("Delete operation is failed.");
			}
		}

		log.debug("phone {} : {}", supportUrl + uploadFiles, response.getBody().getResult());
		return response;
	}
	
	/**
	 * 儲存問題
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTicket")
	@ResponseBody
	public Response<String> saveTicket(@RequestBody Request<FrontTicketSaveRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("saveTicket 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		UserToken ut = getUserToken(account);
		//根据用户名找用信息。
		int userLvl = getUserLvl(ut);
		
		FrontTicketSaveRequest frontTicketSaveRequest = getRequestData(request);
		frontTicketSaveRequest.setLvl(String.valueOf(userLvl));
		frontTicketSaveRequest.setPlatformUserId(ut.getUserId());
		frontTicketSaveRequest.setAccount(ut.getUserName());
		frontTicketSaveRequest.setPlatformId(1L);  //1 : FF4
		frontTicketSaveRequest.setIsRegister(true);
		
//		getPlatformCode
//		getFiles
//		getAccount
//		getLvl
//		getPlatformUserId
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + saveTicket, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + saveTicket, result);
		response.setResult(result);
		
		return response;
	}
	
	/**
	 * 問題列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ticketList")
	@ResponseBody
	public Response<String> ticketList(@RequestBody Request<FrontTicketListRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("ticketList 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		UserToken ut = getUserToken(account);
		//根据用户名找用信息。
		int userLvl = getUserLvl(ut);
		
		FrontTicketListRequest frontTicketListRequest = getRequestData(request);
		frontTicketListRequest.setLvl(String.valueOf(userLvl));
		frontTicketListRequest.setPlatformUserId(ut.getUserId());
		frontTicketListRequest.setAccount(ut.getUserName());
		
//		getPlatformCode
//		getPage
//		getPageSize
//		getAccount
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + ticketList, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
//		view.addObject("reqParam",request);
//		view.addObject("notices",notices);
//		view.addObject("tickets",pages.getResult());
//		view.addObject("pages",pages);
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + ticketList, result);
		response.setResult(result);
		return response;
	}
	
	@RequestMapping(value = "/ticketDetail")
	@ResponseBody
	public Response<String> ticketDetail(@RequestBody Request<FrontTicketDetailRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("ticketDetail 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + ticketDetail, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + ticketDetail, result);
		
		return clientResponse;
	}
	
	@RequestMapping(value = "/secondSaveTicket")
	@ResponseBody
	public Response<String> secondSaveTicket(@RequestBody Request<FrontTicketSaveRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("secondSaveTicket 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + secondSaveTicket, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + secondSaveTicket, result);
		
		return clientResponse;
	}
	
	@RequestMapping(value = "/closeTicket")
	@ResponseBody
	public Response<String> closeTicket(@RequestBody Request<FrontTicketCloseRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("closeTicket 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + closeTicket, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + closeTicket, result);
		
		return clientResponse;
	}
	
	@RequestMapping(value = "/queryUserUnReadTicket")
	@ResponseBody
	public Response<String> queryUserUnReadTicket(@RequestBody Request<FrontTicketListRequest> request) throws Exception {
		Response<String> response = new Response<String>();
		String account = getAccountByToken(request);
		if(StringUtils.isBlank(account)){
			response.getHead().setStatus(7L);
			log.error("queryUserUnReadTicket 客服工單, 無法對應帳號, token : {}", request.getHead().getSessionId());
			return response;
		}
		
		UserToken ut = getUserToken(account);
		//根据用户名找用信息。
		int userLvl = getUserLvl(ut);
		
		FrontTicketListRequest frontTicketListRequest = getRequestData(request);
		frontTicketListRequest.setLvl(String.valueOf(userLvl));
		frontTicketListRequest.setPlatformUserId(ut.getUserId());
		frontTicketListRequest.setAccount(ut.getUserName());
		
		Response<String> clientResponse = httpClient.invokeHttp(supportUrl + queryUserUnReadTicket, 
				getRequestData(request), 
				new TypeReference<Response<String>>(){});
		
		String result = getResponseData(clientResponse);
		log.debug("phone {} : {}", supportUrl + queryUserUnReadTicket, result);
		
		return clientResponse;
	}
	
}
