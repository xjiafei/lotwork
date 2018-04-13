/**   
* @Title: McwClient.java 
* @Package com.winterframework.firefrog.common.mcw 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-9 上午10:10:18 
* @version V1.0   
*/
package com.winterframework.firefrog.common.httpjsonclient.impl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.HttpJsonClientExt;
import com.winterframework.firefrog.common.util.KeyUtil;
import com.winterframework.firefrog.fund.service.impl.mow.MowReq;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.util.HttpJsonClient;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: HttpJsonClient 
* @Description: 用get方式发起一个http请求，结果为json格式，方法返回java对象。 
* @author 你的名字 
* @date 2013-7-9 上午10:10:18 
*  
*/
@Service("HttpJsonClientImpl")
public class HttpJsonClientImpl implements IHttpJsonClient {
	@PropertyConfig(value = "company_id")
	private Long companyId;
	
	private static final Logger logger = LoggerFactory.getLogger(HttpJsonClientImpl.class);
	@PropertyConfig(value = "config")
	private String config;

	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Override
	public <T> T invokeHttp(String url, MowReq req, Class<T> clazz) {
		try {
			req.setCompany_id(companyId);
			req.setKey(KeyUtil.createKey(config, "",req.createParam().replace("null","")));
			logger.debug("request:"+url+"|" + req);
			String result = HttpJsonClient.postMowDataByJson(url, JsonMapper.nonEmptyMapper().toJson(req), 20);
			logger.debug("result:" + result);
			JsonMapper mapper = new JsonMapper();
			T t = mapper.fromJson(result, clazz);
			return t;
		} catch (Exception e) {
			logger.error("invokeHttp is null url:"+url,e);
			return null;
		}
	}
	

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param params
	* @param clazz
	* @return 
	* @see com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient#invokeHttp(java.lang.String, java.util.Map, java.lang.Class) 
	*/
	@Override
	public <T, R> T invokeHttp(String url, Map<String, Object> params, TypeReference<T> typeReference) {
		logger.debug("request:" + params);
		try {
			String abc = "";
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				if (key.equals("class")) {
					continue;
				}
				Object value = entry.getValue();
				abc += ("&" + key + "=" + value);
			}
			logger.debug("request:"+url+"|" + abc);
			String result = HttpJsonClient.getJsonData(url, params);//McwClientMock.postMowDataByJson(url, abc, 2);
			logger.debug("result:" + result);
			ParameterizedType pt = (ParameterizedType) HttpJsonClientExt.class.getDeclaredMethod("postJsonObject",
					String.class, Request.class, TypeReference.class).getGenericReturnType();
			if (typeReference != null) {
				return mapper.readValue(result, typeReference);
			} else {
				return (T) jmapper.fromJson(result, (Class) pt.getRawType());
			}
		} catch (Exception e) {
			logger.error("invokeHttp is null url:"+url+" parma:"+params,e);
			return null;
		}
	}
	@Override
	public String invokeHttpGet(String url, Map<String, Object> params) {
		logger.debug("request:" + params);
		try {
			String abc = "";
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				if (key.equals("class")) {
					continue;
				}
				Object value = entry.getValue();
				abc += ("&" + key + "=" + value);
			}
			logger.debug("request:"+url+"|" + abc);
			String result = HttpJsonClient.getJsonData(url, params);//McwClientMock.postMowDataByJson(url, abc, 2);
			logger.debug("result:" + result);
			return result;
		} catch (Exception e) {
			logger.error("invokeHttp is null url:"+url+" parma:"+params,e);
			return null;
		}
	}
	@Override
	public String invokeHttpXml(String url, Map<String, Object> params) {
		logger.debug("request:" + params);
		try {
			String abc = "";
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				if (key.equals("class")) {
					continue;
				}
				Object value = entry.getValue();
				abc += ("&" + key + "=" + value);
			}
			logger.debug("request:"+url+"|" + abc);
			String result = HttpJsonClient.getJsonData(url, params);//McwClientMock.postMowDataByJson(url, abc, 2);
			logger.debug("result:" + result);
			ParameterizedType pt = (ParameterizedType) HttpJsonClientExt.class.getDeclaredMethod("postJsonObject",
					String.class, Request.class, TypeReference.class).getGenericReturnType();
				return result;
		} catch (Exception e) {
			logger.error("invokeHttpXml is null url:"+url+" parma:"+params,e);
			return null;
		}
	}


	@Override
	public String postHttpJson(String url, String jsonData) throws IOException, Exception {
		int second = 10;
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
			HttpConnectionParams.setSoTimeout(httpParams, 20000);
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (jsonData != null) {
				
				StringEntity params = new StringEntity(jsonData);
				httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
				httpPost.setEntity(params);
			}

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			
			
			
			String result = httpclient.execute(httpPost, responseHandler);
			logger.debug("response:" + result);
			return result;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}
	
	public String postHttpForm(String url,Map<String,String> map,String charset){
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			//���ò���
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String,String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
			}
			if(list.size() > 0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	
	
	
}
