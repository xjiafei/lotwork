/**   
* @Title: HttpJsonClientExt.java 
* @Package com.winterframework.spring.unit 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-25 上午11:22:06 
* @version V1.0   
*/
package com.winterframework.firefrog.common.http;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: HttpJsonClientExt 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author page
* @date 2013-9-25 上午11:22:06 
*  
*/
public class HttpJsonClientExt {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	private static final Logger logger = LoggerFactory.getLogger(HttpJsonClientExt.class);

	public static <T, R> Response<T> postJsonObject(String url, Request<R> params, Class... classNames)
			throws ClientProtocolException, IOException, SecurityException, NoSuchMethodException {
		String result = postJsonObjectReturnStr(url, params);
		
		ParameterizedType pt = (ParameterizedType) HttpJsonClientExt.class.getDeclaredMethod("postJsonObject",
				String.class, Request.class, Class[].class).getGenericReturnType();
		if (classNames != null) {
			return  (Response)jmapper.fromJson(result, jmapper.createCollectionType(Response.class, classNames));
		} else {
			return (Response)jmapper.fromJson(result, (Class) pt.getRawType());
		}
	}

	public static <T, R> Response<T> postJsonObject(String url, Request<R> params, TypeReference typeReference)
			throws ClientProtocolException, IOException, SecurityException, NoSuchMethodException {
		
		String result = postJsonObjectReturnStr(url, params);
		
		ParameterizedType pt = (ParameterizedType) HttpJsonClientExt.class.getDeclaredMethod("postJsonObject",
				String.class, Request.class, TypeReference.class).getGenericReturnType();
		if (typeReference != null) {
			return (Response)mapper.readValue(result, typeReference);
		} else {
			return (Response)jmapper.fromJson(result, (Class) pt.getRawType());
		}
	}

	public static Response postJsonObject(String url, Request params) throws ClientProtocolException,
			IOException, SecurityException, NoSuchMethodException {
		
		String result = postJsonObjectReturnStr(url, params);
		
		Type pt = HttpJsonClientExt.class.getDeclaredMethod("postJsonObject",
				String.class, Request.class).getGenericReturnType();
		return (Response)jmapper.fromJson(result, Response.class);
	}

	public static String postJsonObjectReturnStr(String url, Request params) throws ClientProtocolException,
			IOException, SecurityException, NoSuchMethodException {
		if (params.getHead() == null) {
			throw new NullPointerException();
		}
		int second = 10;
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 杩炴帴瓒呮椂鏃堕棿
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 鑾峰彇鏁版嵁瓒呮椂鏃堕棿
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 杩炴帴瓒呮椂鏃堕棿20绉�
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 鑾峰彇鏁版嵁瓒呮椂鏃堕棿
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (params != null) {
				String str = jmapper.toJson(params);
				logger.debug("request:" + str);
				ByteArrayEntity mult = new ByteArrayEntity(str.getBytes("UTF-8"));
				mult.setContentType("application/firefrog");
				httpPost.setEntity(mult);
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

}
