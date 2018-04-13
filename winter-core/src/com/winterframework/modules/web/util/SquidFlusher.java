package com.winterframework.modules.web.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SquidFlusher {
	private static final ExecutorService exec = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
	private static final Logger logger = LoggerFactory.getLogger(SquidFlusher.class);
	public static String squidUrl = "http://auth1.xa.ht.com/purge_club.php?url=";

	public static boolean flush(String url, boolean useProxy) {
		return flush(squidUrl, url, useProxy, "192.168.0.101", 3128);
	}

	public static boolean flush(String url) {
		return flush(url, false);
	}

	public static void asyncFlush(final String... url) {
		for (final String u : url)
			exec.execute(new Runnable() {
				@Override
				public void run() {
					flush(u);
				}

			});
	}

	public static boolean flush(String staticUrl, String url, boolean useProxy, String proxyHost, int port) {
		logger.debug("staticUrl={}", staticUrl);
		logger.debug("url={}", url);
		HttpClient httpclient = new DefaultHttpClient();

		try {
			String realUrl = staticUrl + URLEncoder.encode(url, "utf-8");
			logger.info("刷新url：{}", realUrl);
			HttpGet httpget = new HttpGet(realUrl);
			//HttpHost proxy = new HttpHost("192.168.0.101", 3128, "http");
			if (useProxy) {
				logger.debug("use proxy,host={} port={}", proxyHost, port);
				HttpHost proxy = new HttpHost(proxyHost, port, "http");
				httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			httpclient.execute(httpget);
			logger.info("恭喜刷新缓存成功 url={}", url);
			return true;
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
			return false;
		} catch (IOException e) {
			logger.error("IOException", e);
			e.printStackTrace();
			return false;
		} finally {
			httpclient.getConnectionManager().shutdown();

		}
	}

	public static void main(String[] s) {
		asyncFlush("aa", "bb");
	}

}
