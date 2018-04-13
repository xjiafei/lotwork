package com.winterframework.firefrog.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ImgUtil {
	/** 
	 * 计算图片尺寸大小等信息：w宽、h高、s大小。异常时返回null。 
	 * 
	 * @param imgpath 图片路径 
	 * @return 图片信息map 
	 */
	public static Map<String, Long> getImgInfo(String imgpath) throws Exception {
		if (isPicture(getpostfix(imgpath))) {

			Map<String, Long> map = new HashMap<String, Long>(3);
			File imgfile = new File(imgpath);
			try {
				FileInputStream fis = new FileInputStream(imgfile);
				BufferedImage buff = ImageIO.read(imgfile);
				map.put("w", buff.getWidth() * 1L);
				map.put("h", buff.getHeight() * 1L);
				map.put("s", imgfile.length());
				fis.close();
			} catch (FileNotFoundException e) {
				System.err.println("所给的图片文件" + imgfile.getPath() + "不存在！计算图片尺寸大小信息失败！");
				map = null;
			} catch (IOException e) {
				System.err.println("计算图片" + imgfile.getPath() + "尺寸大小信息失败！");
				map = null;
			}
			return map;
		}else{
			return null;
		}
	}

	public static String getpostfix(String fname) throws Exception {
		String postfix = null;
		if (fname == null)
			return "";
		if (fname.indexOf(".") != -1) {
			postfix = fname.substring(fname.lastIndexOf("."));
		} else {
			throw new Exception();
		}
		return postfix;
	}

	public static boolean isPicture(String pInput) throws Exception {
		// 文件名称为空的场合 
		if (StringUtils.isEmpty(pInput)) {
			// 返回不和合法 
			return false;
		}
		// 获得文件后缀名 
		String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1, pInput.length());
		// 声明图片后缀名数组 
		String imgeArray[] = { "bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico" };
		// 遍历名称数组 
		if (ArrayUtils.contains(imgeArray, pInput.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		String p = "c:\\111.png";
		Map<String, Long> m = getImgInfo(p);
		for (Map.Entry<String, Long> entry : m.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}