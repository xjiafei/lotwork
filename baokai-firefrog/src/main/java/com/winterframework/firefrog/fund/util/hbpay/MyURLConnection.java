package com.winterframework.firefrog.fund.util.hbpay;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyURLConnection {

    public static byte[] postBinResource(String urlStr, String request, String encode,int timeOutInSeconds) throws Exception {
        HttpURLConnection http = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            URL url = new URL(urlStr);
            http = (HttpURLConnection)url.openConnection();
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false);
            http.setConnectTimeout(timeOutInSeconds*1000);//设置连接超时
            //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setReadTimeout(timeOutInSeconds*1000);//设置读取超时
            //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset="+encode);
            http.connect();

            outputStream = http.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream, encode);
            osw.write(request);
            osw.flush();
            osw.close();

            if (http.getResponseCode() == 200) {
                inputStream = http.getInputStream();
                byte[] returnValue1 = IOUtils.toByteArray(inputStream);
                return returnValue1;
            }else{
                throw new RuntimeException("http read ["+http.getResponseCode()+"]");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (http != null) http.disconnect();
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
