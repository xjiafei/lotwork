package com.winterframework.firefrog.common.upload;

/** 
* @ClassName: UploadUrlConfig 
* @Description: config for different url 
* @author page
* @date 2013-9-18 下午4:58:42 
*  
*/
public class UploadUrlConfig {
	/**
	 * upload url, for example in help module, upload lottery icon.
	 */
	private String url;

	/**
	 * whether need persistence, for example user upload image file and need to show later, this field will be true.
	 */
	private boolean isPersistent;

	/**
	 * path of persistent server
	 */
	private String path;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isPersistent() {
		return isPersistent;
	}

	public void setPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
