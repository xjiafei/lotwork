/**   
* @Title: IUserMessageReplyDao.java 
* @Package com.winterframework.firefrog.user.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-5-6 下午5:25:36 
* @version V1.0   
*/
package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.dao.vo.VipActivityVo;

/** 
* @ClassName: IUserMessageReplyDao 
* @Description: 站内信消息回复相关的DAO层接口 
* @author Denny 
* @date 2013-5-6 下午5:25:36 
*  
*/

public interface IVipActivityDao {

	
	/**
	 * 新增一条回复消息
	 * 
	 * @param messageReply
	 * 
	 * @return
	 */
	public void insertMessageReply(VipActivityVo messageReply);

	public VipActivityVo queryByID(Long id);
	
	public List<VipActivityVo> queryByActivityVo(VipActivityVo vo);
}
