/**   
* @Title: ImgUploadOperater.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-7 下午1:56:07 
* @version V1.0   
*/
package com.winterframework.firefrog.common.upload;

/** 
* @ClassName: ImgUploadOperater 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-7 下午1:56:07 
*  
*/
public class ImgUploadOperater {

	/** 
	* 图片的原始名称
	*/
	private String oriName;
	/** 
	* 图片的名称
	*/
	private String fileUrl;
	/** 
	* 图片大小
	*/
	private Long imgSize;
	/** 
	* 图片高度
	*/
	private Long height;
	/** 
	*  图片宽度
	*/
	private Long width;
	/** 
	*  处理信息
	*/
	private String message;
	/** 
	*  处理结果0：未知错误；1：文件类型错误，2：文件大小错误，3：文件宽高不一致；  100：处理成功
	*/
	private int status;

	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	public Long getImgSize() {
		return imgSize;
	}

	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
