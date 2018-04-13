/**   
* @Title: McwClient.java 
* @Package com.winterframework.firefrog.common.mcw 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-9 上午10:10:18 
* @version V1.0   
*/
package com.winterframework.firefrog.common.http;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: HttpJsonClient 
* @Description: 用get方式发起一个http请求，结果为json格式，方法返回java对象。 
* @author 你的名字 
* @date 2013-7-9 上午10:10:18 
*  
*/
@Service("httpJsonClientImpl")
public class HttpJsonClientImpl implements IHttpJsonClient {

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, dataClass);
		return t;
	}

	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response<T> t = this.invokeHttp(url, request, dataClass);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param r
	* @param pager
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, dataClass);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, long, java.lang.String) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount, Class... dataClass)
			throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, dataClass);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, long, java.lang.String) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount,
			Class... dataClass) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, dataClass);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param params
	* @return 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, com.winterframework.modules.web.jsonresult.Request) 
	*/
	private <T, R> Response<T> invokeHttp(String url, Request<R> request, Class... classType) throws Exception {
		return HttpJsonClientExt.postJsonObject(url, request, classType);
	}

	private <T, R> Response<T> invokeHttp(String url, Request<R> request, TypeReference typeReference) throws Exception {
		return HttpJsonClientExt.postJsonObject(url, request, typeReference);
	}

	private <R> Response invokeHttp(String url, Request<R> request) throws Exception {
		return HttpJsonClientExt.postJsonObject(url, request);
	}

	private RequestHeader createHeader(long userId, String userAccount) {
		RequestHeader header = new RequestHeader();
		header.setUserId(userId);
		header.setUserAccount(userAccount);

		//TODO
		//header.setMsn(100);
		//header.setMsnsn(0);

		return header;
	}

	private RequestHeader createHeader() {
		RequestHeader header = new RequestHeader();

		//TODO
		//header.setMsn(100);
		//header.setMsnsn(0);

		return header;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response<T> t = this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param userId
	* @param userAccount
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, long, java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, long userId, String userAccount,
			TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, TypeReference typeReference)
			throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param userId
	* @param userAccount
	* @param typeReference
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, long, java.lang.String, com.fasterxml.jackson.core.type.TypeReference) 
	*/
	@Override
	public <T, R> Response<T> invokeHttp(String url, R requestData, Pager pager, long userId, String userAccount,
			TypeReference typeReference) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response<T> t = (Response<T>) this.invokeHttp(url, request, typeReference);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String) 
	*/
	@Override
	public Response invokeHttpWithoutResultType(String url) throws Exception {
		Request request = new Request();
		request.setHead(createHeader());
		RequestBody body = new RequestBody();
		request.setBody(body);
		Response t = (Response) this.invokeHttp(url, request);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response t = this.invokeHttp(url, request);
		return t;
	}
	@Override
	public <R> String invokeHttpWithStringResult(String url, R requestData) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		return HttpJsonClientExt.postJsonObjectReturnStr(url, request);
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, long, java.lang.String) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, long userId, String userAccount)
			throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		request.setBody(body);
		Response t = (Response) this.invokeHttp(url, request);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader());
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response t = (Response) this.invokeHttp(url, request);
		return t;
	}

	/**
	* Title: invokeHttp
	* Description:
	* @param url
	* @param requestData
	* @param pager
	* @param userId
	* @param userAccount
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClient#invokeHttp(java.lang.String, java.lang.Object, com.winterframework.modules.web.jsonresult.Pager, long, java.lang.String) 
	*/
	@Override
	public <R> Response invokeHttpWithoutResultType(String url, R requestData, Pager pager, long userId,
			String userAccount) throws Exception {
		Request<R> request = new Request<R>();
		request.setHead(createHeader(userId, userAccount));
		RequestBody<R> body = new RequestBody<R>();
		body.setParam(requestData);
		body.setPager(pager);
		request.setBody(body);
		Response t = (Response) this.invokeHttp(url, request);
		return t;
	}
}
