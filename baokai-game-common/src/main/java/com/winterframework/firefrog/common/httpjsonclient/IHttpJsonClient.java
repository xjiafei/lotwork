package com.winterframework.firefrog.common.httpjsonclient;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;



/** 
* @ClassName: httpClient 
* @Description: 调用json over http协议接口客户端
* @author page
* @date 2013-7-9 上午11:14:26 
*  
*/
public interface IHttpJsonClient {

	public <T,R> Response<T> invokeHttp(String url, Class... dataClass) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, Class... dataClass) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount, Class... dataClass) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, Pager pager, Class... dataClass) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount, Class... dataClass) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, TypeReference typeReference) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, TypeReference typeReference) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount, TypeReference typeReference) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, Pager pager, TypeReference typeReference) throws Exception;
	public <T,R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount, TypeReference typeReference) throws Exception;
	public Response invokeHttpWithoutResultType(String url) throws Exception;
	public <R> Response invokeHttpWithoutResultType(String url, R requestData) throws Exception;
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, long userId, String userAccount) throws Exception;
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager) throws Exception;
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager, long userId, String userAccount) throws Exception;
	public String invokeHttpWithoutResultTypeForEC(String url, String params)  throws Exception ;
	public String invokeHttpWithoutResultTypeForEC(String url, String params,String referer) throws Exception ;
	public String invokeHttpXml(String url, Map<String, Object> params);
	public String invokeHttpPost(String url, Map<String, Object> params);
	public <T, R> T invokeHttpGet(String url, Map<String, Object> params, TypeReference<T> typeReference);
	public String postHttpJson(String url, String jsonData) throws IOException, Exception;
}
