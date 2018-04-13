package com.winterframework.modules.web.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * @author abba email:
 * @version 1.0
 * @since 1.0
 */
@Service
public class HttpJsonClient {
	private static final Logger logger = LoggerFactory.getLogger(HttpJsonClient.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	/**
	 * 
	 * @param url
	 *            URL
	 * @param params
	 *            参数
	 * @param second
	 *            秒
	 * @return
	 */
	public static String getJsonData(String url, Map<String, Object> params, int second) {
		try {
			return getJsonData(url, params, false, second);
		} catch (Exception e) {
			logger.error("get data from [" + url + "] error", e);
			return "";
			// throw new
			// WoyoHttpClientException("get data from ["+url+"] error",e);
		}
	}

	public static String getJsonData(String url, Map<String, Object> params) {
		try {
			return getJsonData(url, params, false, 0);
		} catch (Exception e) {
			logger.error("get data from [" + url + "] error", e);
			return "";
			// throw new
			// WoyoHttpClientException("get data from ["+url+"] error",e);
		}
	}

	public static String getJsonData(String url, Map<String, Object> params, boolean except, int second,
			Header[] headers) {
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			logger.debug("begin to get url:" + url);
			if (params != null && !(params.isEmpty())) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entity : params.entrySet()) {
					if (entity.getValue() == null) {
						continue;
					}
					BasicNameValuePair pare = new BasicNameValuePair(entity.getKey(), entity.getValue().toString());
					values.add(pare);

				}
				String str = URLEncodedUtils.format(values, "UTF-8");
				if (url.indexOf("?") > -1) {
					url += "&" + str;
				} else {
					url += "?" + str;
				}
			}
			logger.debug("after url:" + url);
			// System.out.print(url);
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			httpget.setHeaders(headers);

			String returnData = httpclient.execute(httpget, responseHandler);
			if (returnData != null) {
				Matcher m = Pattern.compile("<\\/script>", Pattern.CASE_INSENSITIVE).matcher(returnData);
				returnData = m.replaceAll("<\\\\/script>");
			}
			return returnData;
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException,get data from [" + url + "] error", e);
			return "";
		} catch (IOException e) {
			logger.error("IOException,get data from [" + url + "] error", e);
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String readImg(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		int seconde = 10;
		if (seconde > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, seconde * 1000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, seconde * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		BufferedInputStream inputStream = null;
		try {
			logger.debug("begin to get url:" + url);
			logger.debug("after url:" + url);
			// System.out.print(url);
			HttpGet httpget = new HttpGet(url);
			HttpResponse returnData = httpclient.execute(httpget);
			inputStream = new BufferedInputStream(returnData.getEntity().getContent());

			try {
				StringBuffer buffer = new StringBuffer();
				int ch;
				while ((ch = inputStream.read()) != -1) {
					buffer.append(ch);
					buffer.append(",");
				}
				return buffer.toString();
			} finally {

				if (inputStream != null) {
					inputStream.close();
				}
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException,get data from [" + url + "] error", e);
			return "";
		} catch (IOException e) {
			logger.error("IOException,get data from [" + url + "] error", e);
			return "";
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String readImg2(String url) {
		InputStream in = null;
		String str = null;
		try {
			in = new URL(url).openStream();
			str = IOUtils.toString(in, "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return str;
	}

	public static String getJsonData(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		int seconde = 10;
		if (seconde > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, seconde * 1000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, seconde * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			logger.debug("begin to get url:" + url);
			logger.debug("after url:" + url);
			// System.out.print(url);
			HttpGet httpget = new HttpGet(url);
			HttpResponse returnData = httpclient.execute(httpget);
			return returnData.getFirstHeader("location").getValue();
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException,get data from [" + url + "] error", e);
			return "";
		} catch (IOException e) {
			logger.error("IOException,get data from [" + url + "] error", e);
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private static String getJsonData(String url, Map<String, Object> params, boolean except, int second) {
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间3秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			logger.debug("begin to get url:" + url);
			if (params != null && !(params.isEmpty())) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entity : params.entrySet()) {
					BasicNameValuePair pare = new BasicNameValuePair(entity.getKey(), entity.getValue().toString());
					values.add(pare);

				}
				String str = URLEncodedUtils.format(values, "UTF-8");
				if (url.indexOf("?") > -1) {
					url += "&" + str;
				} else {
					url += "?" + str;
				}
			}
			logger.debug("after url:" + url);
			// System.out.print(url);
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String returnData = httpclient.execute(httpget, responseHandler);
			if (returnData != null) {
				Matcher m = Pattern.compile("<\\/script>", Pattern.CASE_INSENSITIVE).matcher(returnData);
				returnData = m.replaceAll("<\\\\/script>");
			}
			return returnData;
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException,get data from [" + url + "] error", e);
			return "";
		} catch (IOException e) {
			logger.error("IOException,get data from [" + url + "] error", e);
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String deleteIndexData(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			logger.debug("begin to get url:" + url);
			HttpDelete httpget = new HttpDelete(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String postJsonDataByJson(String url, Map<String, ?> params) throws ClientProtocolException,
			IOException {
		return postJsonDataByJson(url, params, 0);
	}

	public static <T, R> Response<T> postJsonObject(String url, Request<R> params, Class... classes)
			throws ClientProtocolException, IOException, SecurityException, NoSuchMethodException {
		if (params.getHead() == null) {
			throw new NullPointerException();
		}
		int second = 10;
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 连接超时时间
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间20秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (params != null) {
				String str = jmapper.toJson(params);
				logger.info("request:" + str);
				ByteArrayEntity mult = new ByteArrayEntity(str.getBytes("UTF-8"));
				mult.setContentType("application/firefrog");
				httpPost.setEntity(mult);
			}

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			ParameterizedType pt = (ParameterizedType) HttpJsonClient.class.getDeclaredMethod("postJsonObject",
					String.class, Request.class, Class[].class).getGenericReturnType();
			String result = httpclient.execute(httpPost, responseHandler);
			logger.info("response:" + result);
			if (classes != null && classes.length > 0) {
				return (Response)jmapper.fromJson(result, jmapper.createCollectionType(Response.class, classes));
			} else {
				return (Response)jmapper.fromJson(result, (Class) pt.getRawType());
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}

	public static String postJsonDataByJson(String url, Object params, int second) throws ClientProtocolException,
			IOException {
		return postJsonDataByJson(url, params, null, second);
	}

	public static String postJsonDataByJson(String url, Object params, String head, int second)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 连接超时时间
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间20秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (params != null) {
				String str = "";
				if (params instanceof String) {
					str = (String) params;
				} else {
					str = mapper.writeValueAsString(params);
				}
				ByteArrayEntity mult = new ByteArrayEntity(str.getBytes("UTF-8"));
				if (head != null) {
					mult.setContentType(head);
				}
				httpPost.setEntity(mult);
			}

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(httpPost, responseHandler);
			return response;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}

	public static String postMowDataByJson(String url, String params, int second) throws ClientProtocolException,
			IOException {
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);// 连接超时时间
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);// 获取数据超时时间
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);// 连接超时时间20秒
			HttpConnectionParams.setSoTimeout(httpParams, 20000);// 获取数据超时时间
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);

			ByteArrayEntity mult = new ByteArrayEntity(params.getBytes("UTF-8"));
			httpPost.setEntity(mult);

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(httpPost, responseHandler);
			return response;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}

	public static String postJsonData(String url, Map<String, ?> params, boolean except) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpPost httpPost = new HttpPost(url);

			if (params != null) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for (Map.Entry<String, ?> entity : params.entrySet()) {
					if (entity.getValue() == null) {
						continue;
					}
					BasicNameValuePair pare = new BasicNameValuePair(entity.getKey(), entity.getValue().toString());
					values.add(pare);

				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
				httpPost.setEntity(entity);
			}

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httpPost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException!Post data to url:" + url + "error!", e);
			return "";
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException!Post data to url:" + url + "error!", e);
			return "";
		} catch (IOException e) {
			logger.error("IOException!Post data to url:" + url + "error!", e);
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String postJsonData(String url, Map<String, ?> params) {
		try {
			return postJsonData(url, params, false);
		} catch (Exception e) {
			logger.error("get data from [" + url + "] error", e);
			return "";
		}
	}

	public static <T> T postJsonObjectData(String url, HashMap<String, Object> params, Class<T> cls)
			throws ClientProtocolException, IOException {
		String str = postJsonData(url, params, false);
		return mapper.readValue(str, cls);
	}

	public static String postJsonObjectData(String url, HashMap<String, Object> params) throws ClientProtocolException,
			IOException {
		return postJsonData(url, params, false);
	}

	public static void main(String[] s) throws Exception {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><upomp application=\"TransNotify.Req\"  version=\"1.0.0\" ><transType>01</transType><merchantId>104440148990011</merchantId><merchantOrderId>201204241341307804</merchantOrderId><merchantOrderAmt>100</merchantOrderAmt><settleDate>0424</settleDate><setlAmt>100</setlAmt><setlCurrency>156</setlCurrency><converRate></converRate><cupsQid>201204241340479258912</cupsQid><cupsTraceNum>925891</cupsTraceNum><cupsTraceTime>0424134047</cupsTraceTime><cupsRespCode>00</cupsRespCode><cupsRespDesc>Success!</cupsRespDesc><sign>Y67rdGDCudx67kCcKskgFFWojbM521P1o01cZB3cvrM1A5xXXRRaChez82RS+ZnrubCTR4f7AJ9HEG9DYI7pfoK4iM8mb60Sd47Bm9LFonfYCLisMCkTwO5EG762l89+8uIbXQNaWH8fp1Dsy07rYQ6hjytUjbSXO6MBd31Jav8=</sign><respCode></respCode></upomp>";
		HttpJsonClient.postJsonDataByJson(
				"https://paycenter.hubs1.net/Hubs1Pay/pay/mobile/chinapay/wap/ReturnResvInfo.do", str, 1000);

	}
}
