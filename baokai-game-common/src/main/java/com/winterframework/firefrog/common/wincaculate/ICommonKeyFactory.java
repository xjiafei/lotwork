/**   
* @Title: ICommonKeyFactory.java 
* @Package com.winterframework.firefrog.common.validate.business 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 上午11:05:00 
* @version V1.0   
*/
package com.winterframework.firefrog.common.wincaculate;

import com.winterframework.firefrog.common.validate.business.IKeyGenerator;

/** 
* @ClassName: ICommonKeyFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-18 上午11:05:00 
*  
*/
public interface ICommonKeyFactory<T> {
	T getObject(IKeyGenerator keyGenerator);
}
