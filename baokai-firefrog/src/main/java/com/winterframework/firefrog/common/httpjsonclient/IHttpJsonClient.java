/**   
* @Title: McwClient.java 
* @Package com.winterframework.firefrog.common.mownecum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-9 上午11:14:26 
* @version V1.0   
*/
package com.winterframework.firefrog.common.httpjsonclient;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.fund.service.impl.mow.MowReq;

/** 
* @ClassName: McwClient 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-9 上午11:14:26 
*  
*/
public interface IHttpJsonClient {

	public <T, R> T invokeHttp(String url, Map<String, Object> params, TypeReference<T> typeReference);
	public <T> T invokeHttp(String url,MowReq parames,Class<T> claz);
	public String invokeHttpXml(String url, Map<String, Object> params);
	public String postHttpJson(String url,String jsonData) throws IOException, Exception;
	public String postHttpForm(String url,Map<String, String> params,String charset) throws IOException, Exception;
	public String invokeHttpGet(String url, Map<String, Object> params);
}
