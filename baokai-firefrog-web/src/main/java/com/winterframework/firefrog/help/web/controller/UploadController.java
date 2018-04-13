package com.winterframework.firefrog.help.web.controller;

import com.winterframework.modules.spring.exetend.PropertyConfig;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping(value = "/helpAdmin")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private final static DefaultFileSystemManager mgr = getDefaultFileSystemManager();

	@PropertyConfig(value = "url.imageserver.upload")
	private String imageServerUploadUrl;

	@PropertyConfig(value = "userFileUpload.fileSize")
	private String fileSize;

	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public String upload(@RequestParam(value = "tempFile", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		//新增代码，如果文件过大，返回0错误信息
		if (file.getSize() >= Long.valueOf(fileSize)) {
            return "{error:0}";
        }
		String path = request.getSession().getServletContext().getRealPath("imgUpload");
		String oriName = file.getOriginalFilename();
        String extName = getExtension(oriName);
        if ("JPG、GIF、PNG".indexOf(extName.toUpperCase()) == -1) {
            return "{error:1}";
        }
		//		String fileName = String.valueOf(System.currentTimeMillis()) + new Random().nextLong() + ".jpg";
		String fileName = org.apache.commons.codec.digest.DigestUtils.md5Hex(file.getBytes()) + "."
				+ extName;
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
			FileObject ftpFile = mgr.resolveFile(imageServerUploadUrl, opts);
			if (!ftpFile.exists()) {
				ftpFile.createFolder();
			}
			ftpFile.copyFrom(localFile, Selectors.SELECT_ALL);
		} catch (Exception e) {
			logger.error("upload file error.", e);
            return "{error:2}";
		}
		targetFile.delete();
		return "{name:\"" + fileName + "\"}";
	}

	public static String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1);
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
			// 此处应该改为log
			e.printStackTrace();
		}
		return mgr;
	}

}
