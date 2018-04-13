/**
* Copyright (C) abba, 2010
*/
package com.winterframework.modules.web.util;

/**
* 
* @author abba(xuhengbiao@gmail.com) in 2010-6-8
* @since 1.0
*/
public interface IUser {
	public static final Long PASSANGER_ID=0L;
	public static final byte ROLE_GENERAL = Byte.MAX_VALUE;
	Long getId();
	String getUserName();
	boolean IsBlocked();
}
