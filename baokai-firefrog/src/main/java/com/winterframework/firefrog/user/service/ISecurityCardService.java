/**   
* @Title: ISecurityCardService.java 
* @Package com.winterframework.firefrog.user.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-18 下午1:45:06 
* @version V1.0   
*/
package com.winterframework.firefrog.user.service;

import com.winterframework.firefrog.user.web.dto.QuerySecurityCardRequest;

/** 
* @ClassName: ISecurityCardService 
* @Description: 手机安全密码卡绑定功能接口 
* @author Page
* @date 2014-2-18 下午1:45:06 
*  
*/
public interface ISecurityCardService {

	/** 
	* @Title: 绑定安全中心  
	* @Description:  
	* @param userId 用户id
	* @param sercuritySerilizeNumber  序列号
	* @param phoneType 手机类型
	 * @throws Exception 
	*/
	public void bindSecurityCard(Long userId, String sercuritySerilizeNumber, Long phoneType) throws Exception;

	/** 
	 * 修改绑定火蛙安全中心
	* @Title: updateSecurityCard 
	* @Description: 
	* @param queryRequest
	 * @throws Exception 
	*/
	public void updateSecurityCard(QuerySecurityCardRequest queryRequest) throws Exception;

	/** 
	* @Title: unbindSecurityCard 
	* @Description: 解除火蛙安全中心绑定
	* @param queryRequest
	*/
	public void unbindSecurityCard(QuerySecurityCardRequest queryRequest) throws Exception;

	/** 
	* @Title: getSecurityCardNumber 
	* @Description: 获取火蛙安全中心验证码
	* @param userId
	* @return
	*/
	public String getSecurityCardNumber(Long userId) throws Exception;

	/** 
	* 校验生成的安全码与传入的安全码是否一致 
	* @param checkCard 要校验的安全码
	* @param snCode 客户的序列号
	* @return
	*/
	public boolean checkSecurityCard(String snCode);


}
