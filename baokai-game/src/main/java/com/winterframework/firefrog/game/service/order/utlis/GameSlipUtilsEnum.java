package com.winterframework.firefrog.game.service.order.utlis;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;

/** 
* @ClassName: GameSlipUtilsEnum 
* @Description: 用枚举方式实现单例
*  
*/
public enum GameSlipUtilsEnum {

	INSTANSE;
	private final Logger log = LoggerFactory.getLogger(GameSlipUtilsEnum.class);

	/** 
	* @Title: getBetDetai 
	* @Description:  获取投注内容
	* @param result
	* @param order
	* @param slip
	* @param contents
	* @return
	*/
	public String[] getBetDetai(int fileMode, String betDetail) {
		String[] contents = new String[] { "" };
		if (fileMode == 1) {
			
			File file = new File(betDetail);
			try {
				//3获取投注内容，
				String content = FileUtils.readFileToString(file);
				log.info("成功获取单式上传信息。 content = " + content);
				//获取文件
				contents = StringUtils.split(content, " ");
			} catch (IOException e) {
				log.error("获取文件投注信息错误，文件路径=" + betDetail, e);
				contents[0] = betDetail;
			}
		} else {
			contents[0] = betDetail;
		}
		return contents;
	}

	
}
