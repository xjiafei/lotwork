package com.winterframework.firefrog.common.httpjsonclient.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: IHttpJsonClientInvoker 
* @Description:  
* @author 你的名字 
* @date 2013-9-24 下午4:55:47 
* 
* @param <R>
* @param <T> 
*/
public interface IHttpJsonClientInvoker<R, T> {

	public abstract Response<T> postJsonObject(String url, Request<R> params) throws ClientProtocolException,
			IOException, SecurityException, NoSuchMethodException;

}