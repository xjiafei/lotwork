package com.winterframework.adbox.enums;
public enum StatusCode{
		/*
		 * 系统 个位
		 * 基础 1xx
		 * 
		 */
		OK(0),	//请求成功
		FAILED(1),
		DEVICE_UNEXISTS(101);	//设备不存在
		private int _value=1;
		StatusCode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}