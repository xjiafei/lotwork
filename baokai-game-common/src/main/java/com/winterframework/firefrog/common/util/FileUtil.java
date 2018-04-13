package com.winterframework.firefrog.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Component;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Component("fileUtil")
public class FileUtil {
	public static final String DATE_FORMAT_PATTERN = "yyyyMMdd";
	public static final String FILE_SUBFIXX = ".txt";

	@PropertyConfig(value = "filePath")
	private String filePath;

	public String string2File(String res, String fileName, Date calculateDate) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(createFilepath(res, fileName, calculateDate));
			if (!distFile.getParentFile().exists()) {
				distFile.getParentFile().mkdirs();
			}
			bufferedReader = new BufferedReader(new StringReader(new String(res.getBytes("UTF-8"))));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; //字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
			return distFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return "";
	}

	public String createFilepath(String res, String fileName, Date calculateDate) {
		FastDateFormat df = FastDateFormat.getInstance(DATE_FORMAT_PATTERN);
		String str = df.format(calculateDate);
		return filePath + File.separator + str + File.separator  + fileName + FILE_SUBFIXX;

	}

	public boolean deleteFile(String fileName, Date calculateDate) {
		File distFile = new File(createFilepath("", fileName, calculateDate));
		return distFile.delete();
	}
}
