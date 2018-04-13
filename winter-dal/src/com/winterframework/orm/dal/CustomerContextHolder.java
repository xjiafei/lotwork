package com.winterframework.orm.dal;

public class CustomerContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		return contextHolder.get();
	}

	/**
	 * 
	 * setDefaultType:设置contextHolder为空，这样dateSource为默认值
	 * @param      
	 * @return void    
	 * @throws 
	 * @since  CodingExample　Ver 1.0
	 */
	public static void setDefaultType() {
		contextHolder.set(null);
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}
