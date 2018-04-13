package com.winterframework.firefrog.common.util;

import org.springframework.stereotype.Component;

import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: SecurityUtil 
* @Description:  用户资金表安全字段生成器 
* @author david
* @date 2013-7-4 上午10:08:18 
*  
*/
@Component("securityUtil")
public class SecurityUtil {
	@PropertyConfig(value = "factors")
	private String factors;

	//	private final static String FLAG = "-";

	public String createSecurity(Long bal, Long amt, Long userId) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(userId);
		sb.append(bal);
		sb.append(amt);

		return MultMd5.dmd(sb.toString(), factors);

	}

	public static void main(String[] args) {
		//		System.out.println(createSecurity(1000L, 200L, 1008L));
	}
}
