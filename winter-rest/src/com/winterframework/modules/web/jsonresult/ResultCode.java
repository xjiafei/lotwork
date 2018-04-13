/**
* Copyright (C) Administrator, 2010
*/
package com.winterframework.modules.web.jsonresult;

/**
* 
* @author Administrator(xuhengbiao@gmail.com) in 2010-7-2
* @since 1.0
*/
public enum ResultCode {
	Success(200, "success"), ParamError(400, "参数错误"), NotSupport(401, "系统不支持"), Rejected(403, "未授权"), MethodNotFind(404,
			"方法未找到"), System_Error(500, "系统错误");
	ResultCode(int code, String desc) {
		this.code = code;
		this.message = desc;
	}

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
