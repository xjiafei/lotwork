package com.winterframework.firefrog.common.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.cache.DefaultFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.zip.ZipFileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
 * 上传图片的基类
* @ClassName: BaseUploadController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-14 上午10:00:48 
*  
*/
public abstract class BaseUploadController {

	private Logger logger = LoggerFactory.getLogger(BaseUploadController.class);
	private final static DefaultFileSystemManager mgr = getDefaultFileSystemManager();
	//文件服务器地址
	@PropertyConfig(value = "url.imageserver.upload")
	protected String imageServerUploadUrl;

	/** 
	 * 校验文件的类型
	 * @param fileType 当为空时，则不校验该文件的类型
	*/
	private boolean checkFileType(MultipartFile file, List<String> fileType) {
		String fileName = file.getOriginalFilename();
		boolean isFileType = true;
		if (!CollectionUtils.isEmpty(fileType)) {
			//将文件名改为大写，防止因为大小写造成的文件类型不一致
			List<String> upperFileType = new ArrayList<String>();
			for (String string : fileType) {
				upperFileType.add(string.toUpperCase());
			}
			if (StringUtils.isEmpty(fileName)) {
				isFileType = false;
			} else {
				String postfix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase();
				if (!fileType.contains(postfix)) {
					isFileType = false;
				}
			}
		}
		return isFileType;
	}
	
	/** 
	 * 校验文件大小
	* @Title: checkFileSize 
	* @param file
	* @param size 当为null时，则不校验文件大小
	* @param result
	* @return
	*/
	private boolean checkFileSize(MultipartFile file, Long size, ImgUploadOperater result) {
		boolean isFileSize = true;
		if (size != null) {
			long fileSize = file.getSize();
			if (fileSize > size.longValue()) {
				isFileSize = false;
			}
		}
		if (result != null) {
			result.setImgSize(file.getSize());
		}
		return isFileSize;
	}

	/** 
	 * 校验文件的宽和高
	* @Title: checkImgSize 
	* @param file
	* @param imgSizes 当为空时，则不校验文件的宽和高
	* @param result
	* @return
	*/
	private boolean checkImgSize(MultipartFile file, List<ImgSize> imgSizes, ImgUploadOperater result) {
		InputStream in = null;
		boolean isWithHeight = true;
		try {
			in = file.getInputStream();
			if (imgSizes != null) {
				BufferedImage buff = ImageIO.read(in);
				Long width = Long.valueOf(buff.getWidth());
				Long height = Long.valueOf(buff.getHeight());
				result.setWidth(width);
				result.setHeight(height);
				logger.error ("dasdasdasd" +  width.toString() + "dasdasd  " + height.toString() );
				for (ImgSize imgSize : imgSizes) {
					if (imgSize.getHeight().longValue() != height.longValue()
							|| imgSize.getWidth().longValue() != width.longValue()) {
						isWithHeight = false;
						logger.error ("dasdasdasd" +  imgSize.getHeight().longValue() + "dasdasd  " + imgSize.getWidth().longValue() );
						break;
					}
				}
			}
		} catch (IOException e) {
			logger.error("checkImgWidthAndHeight error", e);
			isWithHeight = false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("checkImgWidthAndHeight error", e);
			}
		}
		return isWithHeight;
	}

	/** 
	 * 校验文件类型，大小以及宽高
	* @Title: checkFile 
	* @param file
	* @param fileType
	* @param size
	* @param imgSizes
	* @param result
	* @return
	* @throws Exception
	*/
	protected boolean checkFile(MultipartFile file, List<String> fileType, Long size, List<ImgSize> imgSizes,
			ImgUploadOperater result) throws Exception {
		if (!checkFileType(file, fileType)) {
			result.setStatus(1);
			result.setMessage("图片格式不正确");
			return false;
		}
		if (!checkFileSize(file, size, result)) {
			result.setStatus(2);
			result.setMessage("图片太大无法上传");
			return false;
		}

		if (!checkImgSize(file, imgSizes, result)) {
			result.setStatus(3);
			result.setMessage("与投放位置尺寸不符合");
			return false;
		}
		return true;
	}

	protected boolean checkFile(MultipartFile file, List<String> fileType, Long size, ImgUploadOperater result)
			throws Exception {
		return this.checkFile(file, fileType, size, null, result);
	}

	protected ImgUploadOperater upload(MultipartFile file, String path) throws Exception {
		return this.upload(file, path, null, null, null);
	}

	protected ImgUploadOperater upload(MultipartFile file, String path, List<String> fileType) throws Exception {
		return this.upload(file, path, fileType, null, null);
	}

	protected ImgUploadOperater upload(MultipartFile file, String path, List<String> fileType, Long size)
			throws Exception {
		return this.upload(file, path, fileType, size, null);
	}

	/** 
	 * 上传图片
	* @Title: upload 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param file
	* @param path
	* @param fileType
	* @param size
	* @param imgSizes
	* @return
	* @throws Exception
	*/
	protected ImgUploadOperater upload(MultipartFile file, String path, List<String> fileType, Long size,
			List<ImgSize> imgSizes) throws Exception {
		ImgUploadOperater operater = new ImgUploadOperater();
		if (!checkFile(file, fileType, size, imgSizes, operater)) {
			return operater;
		}
		String oriName =getExtension(file.getOriginalFilename());
		String fileName = org.apache.commons.codec.digest.DigestUtils.md5Hex(file.getBytes()) + "." + oriName;
		operater.setOriName(oriName);
		operater.setFileUrl(fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			//临时保存图片
			file.transferTo(targetFile);
			// 上传到文件服务器
			FileObject localFile = mgr.resolveFile(path);
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder sscb = SftpFileSystemConfigBuilder.getInstance();
			sscb.setStrictHostKeyChecking(opts, "no");
			sscb.setUserDirIsRoot(opts, false);
			logger.info("开始文件上传:", imageServerUploadUrl);
			FileObject ftpFile = mgr.resolveFile(imageServerUploadUrl, opts);
			logger.info("上传成功", ftpFile);
			if (!ftpFile.exists()) {
				ftpFile.createFolder();
			}
			ftpFile.copyFrom(localFile, Selectors.SELECT_ALL);
			operater.setStatus(100);
		} catch (Exception e) {
			operater.setStatus(0);
			logger.error("文件上传错误", e);
		}
		targetFile.delete();
		return operater;
	}

	public static String getExtension(String s) {
        String ext = null;
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1);
        }
        return ext;
    }
	private static DefaultFileSystemManager getDefaultFileSystemManager() {

		DefaultFileSystemManager mgr = new DefaultFileSystemManager();
		// SFTP 供应者
		SftpFileProvider fp = new SftpFileProvider();

		// ZIP 供应者
		ZipFileProvider zp = new ZipFileProvider();
		// 缺省本地文件供应者
		DefaultLocalFileProvider lf = new DefaultLocalFileProvider();

		try {
			// common-vfs 中 文件管理器的使用范例
			mgr.addProvider("sftp", fp);
			mgr.addProvider("zip", zp);
			mgr.addProvider("file", lf);
			mgr.setFilesCache(new DefaultFilesCache());
			mgr.init();

		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		return mgr;
	}
}
