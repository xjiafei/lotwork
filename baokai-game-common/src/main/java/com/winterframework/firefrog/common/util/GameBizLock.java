package com.winterframework.firefrog.common.util;


 
/**
 * 业务锁实体
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月21日
 */
public class GameBizLock { 
	private final String bizType;
	private final String bizObj;
	private final String user; 
	
	public GameBizLock(String bizType,String bizObj,String user){
		this.bizType=bizType;
		this.bizObj=bizObj;
		this.user=user;
	}	 
	
	public String getBizType() {
		return bizType;
	} 

	public String getBizObj() {
		return bizObj;
	} 
	public String getUser() {
		return user;
	} 

	@Override
	public boolean equals(Object obj) { 
		if(obj instanceof GameBizLock) {
			GameBizLock bizLock=(GameBizLock)obj;
			if(bizLock.getBizType().equals(this.getBizType())
					&& bizLock.getBizObj().equals(this.getBizObj())
					&& bizLock.getUser().equals(this.getUser())){
				return true;
			} 
		}
		return false;
	}
	
	
	 
}
