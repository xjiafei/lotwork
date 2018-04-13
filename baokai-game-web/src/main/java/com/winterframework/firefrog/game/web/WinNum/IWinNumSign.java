/**   
* @Title: IWinNumSign.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-2 下午4:56:38 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: IWinNumSign 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-2 下午4:56:38 
*  
*/
public interface IWinNumSign {

	public void signSlips(List<SlipsStrucDTO> slips) throws Exception;

	public void setNumberRecordList(List<String> numberRecordList) throws Exception;

}
