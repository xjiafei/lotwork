/**   
* @Title: McwClient.java 
* @Package com.winterframework.firefrog.common.mownecum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-9 上午11:14:26 
* @version V1.0   
*/
package com.winterframework.firefrog.common.http;

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
	public <R> String invokeHttpWithStringResult(String url, R requestData) throws Exception;
}
