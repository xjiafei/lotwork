package com.winterframework.firefrog.common.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 
* @ClassName: ProcessResult 
* @Description: 
* @author Richard
* @date 2013-11-1 下午1:46:19 
*
 */
public class ProcessResult {

	public static final boolean PROCESS_STATUS_SUCCESS = true;
	public static final boolean PROCESS_STATUS_FAIL = false;

	private boolean processStatus = true; // 状态码
	private String retCode = ""; // 返回代码
	private String retMsg = ""; // 返回描述
	private String errCode = ""; // 错误代码
	private String errMsg = ""; // 错误描述
	private Map<String,Object> errParaMap = new HashMap<String,Object>(); //错误信息map
	private Map<String,Object> retParaMap = new HashMap<String,Object>();//返回信息map
	
	public ProcessResult() {
		setProcessStatus(PROCESS_STATUS_SUCCESS);
	}

	public ProcessResult(boolean processStatus) {
		setProcessStatus(processStatus);
	}
	
	public void setRetCodeAndMsg(String retCode,String retMsg){
		setRetCode(retCode);
		setRetMsg(retMsg);
	}
	
	public void setErrCodeAndMsg(String errCode,String errMsg){
		setErrCode(errCode);
		setErrMsg(errMsg);
	}
	
	public boolean isSuccess(){
		return processStatus;
	}
	
	public boolean isFail(){
		return !processStatus;
	}

	public void success(){
		setProcessStatus(PROCESS_STATUS_SUCCESS);
	}

	public void success(String retCode,String retMsg){
		setProcessStatus(PROCESS_STATUS_SUCCESS);
		setRetCode(retCode);
		setRetMsg(retMsg);
	}
	
	public void fail(){
		setProcessStatus(PROCESS_STATUS_FAIL);
	}
	
	public void fail(String retCode,String retMsg){
		setProcessStatus(PROCESS_STATUS_FAIL);
		setRetCode(retCode);
		setRetMsg(retMsg);
	}
	
	public void fail(String retCode,String retMsg,String errCode,String errMsg){
		setProcessStatus(PROCESS_STATUS_FAIL);
		setRetCode(retCode);
		setRetMsg(retMsg);
		setErrCode(errCode);
		setErrMsg(errMsg);
	}

	public void setToRetParaMap(String aKey, Object aValue) {
		retParaMap.put(aKey, aValue);
	}

	public Object getFromRetParaMap(String aKey, Object aDefault) {
		Object objRtn = retParaMap.get(aKey);
		if (objRtn == null) {
			objRtn = aDefault;
		}
		return objRtn;
	}

	public Object getFromRetParaMap(String aKey) {
		return getFromRetParaMap(aKey, null);
	}
	
	public String getStringFromRetParaMap(String aKey, String aDefault) {
		Object obj = retParaMap.get(aKey);
		if(obj!=null){
			return String.valueOf(obj);
		}else{
			return aDefault;
		}
	}

	public String getStringFromRetParaMap(String aKey) {
		return getStringFromRetParaMap(aKey, null);
	}
	
	
	public void setToErrParaMap(String aKey, Object aValue) {
		errParaMap.put(aKey, aValue);
	}

	public Object getFromErrParaMap(String aKey, Object aDefault) {
		Object objRtn = errParaMap.get(aKey);
		if (objRtn == null) {
			objRtn = aDefault;
		}
		return objRtn;
	}

	public Object getFromErrParaMap(String aKey) {
		return getFromErrParaMap(aKey, null);
	}
	
	
	public boolean getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(boolean processStatus) {
		this.processStatus = processStatus;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public void setRetParaMap(Map<String,Object> retParaMap) {
		this.retParaMap = retParaMap;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
