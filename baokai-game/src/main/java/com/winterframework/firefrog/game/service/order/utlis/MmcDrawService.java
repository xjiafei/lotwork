package com.winterframework.firefrog.game.service.order.utlis;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.GetBallRequest;
import com.winterframework.firefrog.game.web.dto.GetBallResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Service("mmcDrawService")
public class MmcDrawService {
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	@PropertyConfig(value = "mmc.url")
	private String url;
	@PropertyConfig(value = "mmc.key")
	private String mmcKey;
	@PropertyConfig(value = "mmc.iv")
	private String mmcIv;
	private Encrypt cy;
	
	private Encrypt cysb;
	@PropertyConfig(value = "mmc115.url") 
	private String url115;

	@PropertyConfig(value = "jlsb.count") 
	private String sbcount;
	
	@PropertyConfig(value = "jlsbmmc.url")
	private String jidburl;
	@PropertyConfig(value = "mmc.getBallUrl")
	private String getBallUrl;
	
	private static final Logger logger = LoggerFactory.getLogger(MmcDrawService.class);
	
	public String getMmc(Long lotteryId,String mmc, Date dt) throws Exception {
		int i = 3;
		String num = "";
		while (i-- > 0) {
			num = getMmc(lotteryId,mmc, dt, i--);
			if (num != null) {
				return num;
			} else {
				Thread.sleep(10);
			}
		}
		return null;
	}

	private String getMmc(Long lotteryId,String mmc, Date dt, int turn) throws Exception {
		if (turn < 0) {
			return "";
		}
		String queryUrl = mmc.contains("DSLC")?url:mmc.contains("DSL5")?url115:mmc.contains("DMMC")?url:null;
		String str = postStrReturnStr(queryUrl, URLEncoder.encode(cy.encryptRijndael(new MM(mmc).toMMC(dt)), "UTF-8"));
		str = str.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
		str = str.replace("\r\n<string xmlns=\"http://tempuri.org/\">", "");
		str = str.replace("</string>", "");
		String result = (cy.decryptRijndael(str));
		if(lotteryId==(99112)||lotteryId==(99113)){
			String numb = result.replace("result=", "").replaceAll(",", "");
			if (numb.length() != 5) {
				return null;
			} else {
				return numb;
			}
		}else{
			String numb = result.replace("result=", "");
			if (numb.length() !=14) {
				return null;
			} else {
				return numb;
			}
		}

	}

	public String postStrReturnStr(String url, String params) throws ClientProtocolException, IOException,
			SecurityException, NoSuchMethodException {
		HttpClient httpclient = new DefaultHttpClient();

		HttpParams httpParams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);// 
		HttpConnectionParams.setSoTimeout(httpParams, 3000);//

		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (params != null) {
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

				ByteArrayEntity entity = new ByteArrayEntity(("param=" + params).getBytes("UTF-8"));
				httpPost.setEntity(entity);
			}

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String result = httpclient.execute(httpPost, responseHandler);
			return result;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}

	private static final class MM {
		private String platform = "4.0";
		private String project_key;

		public MM(String project_key) {
			this.project_key = project_key;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		public String getProject_key() {
			return project_key;
		}

		public void setProject_key(String project_key) {
			this.project_key = project_key;
		}

		public String toMMC(Date dt) {
			return "platform=" + this.platform + "&project_key=" + this.project_key + "&project_time="
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
		}
	}
	
	public String getSB (String mmc, Date dt) throws Exception {
		int i = 3;
		String num = "";
		while (i-- > 0) {
			num = getSB(mmc, dt, i--);
			if (num != null) {
				return num;
			} else {
				Thread.sleep(10);
			}
		}
		return null;
	}
	
	private String getSB (String mmc, Date dt, int turn) throws Exception {
		if (turn < 0) {
			return "";
		}
		//http://10.3.200.20/rng/retrieve.asmx/JLSB
		String queryUrl = jidburl + mmc;
		String str = postStrReturnStr(queryUrl, URLEncoder.encode(cy.encryptRijndael(new SB(mmc).toSB(dt,sbcount)), "UTF-8"));
		str = str.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
		str = str.replace("\r\n<string xmlns=\"http://tempuri.org/\">", "");
		str = str.replace("</string>", "");
		String result = (cy.decryptRijndael(str));
		String numb = result.replace("result=", "").replaceAll(",", "");
		
		return numb;
		
	}
	
	private static final class SB {
		private String platform = "4.0";
		private String project_key;

		public SB(String project_key) {
			this.project_key = project_key;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		public String getProject_key() {
			return project_key;
		}

		public void setProject_key(String project_key) {
			this.project_key = project_key;
		}
		
		public String toSB(Date dt,String draw_count) {
			return "platform=" + this.platform + "&project_key=" + this.project_key + "&project_time="
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt) + "&draw_count="+draw_count;

		}
	}
	@PostConstruct
	public void init() {
		cy = Encrypt.getInstance(mmcKey, mmcIv);
	}
	
	public String getBall(Long lotteryId, String orderCode) throws Exception {
		GetBallRequest request=new GetBallRequest();
		request.setLotteryId(lotteryId);
		request.setOrderCode(orderCode);
		Response<GetBallResponse> response=httpClient.invokeHttp(getBallUrl, request, GetBallResponse.class);
		return response.getBody().getResult().getBall();
	}


}
