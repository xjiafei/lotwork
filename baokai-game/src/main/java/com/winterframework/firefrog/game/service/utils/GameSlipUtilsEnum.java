package com.winterframework.firefrog.game.service.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.service.wincaculate.amount.LotteryWinAmountCaculator;

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
	public String[] getBetDetai(GameContext ctx, GameOrder order, GameSlip slip, String[] contents) throws Exception {
		if (slip.getFileMode() == 1) {
			File file = new File(slip.getBetDetail());
			try {
				log.debug("slip.getId()="+slip.getId() + "slip.getFileMode() == 1");
				boolean is115 = order.getLotteryid().longValue() == 99301L
						|| order.getLotteryid().longValue() == 99302L
						|| order.getLotteryid().longValue() == 99303L
						|| order.getLotteryid().longValue() == 99304L
						|| order.getLotteryid().longValue() == 99305L
						|| order.getLotteryid().longValue() == 99306L
						|| order.getLotteryid().longValue() == 99307L;
				boolean isSSQ = order.getLotteryid().longValue() == 99401L;
				
				//3获取投注内容，
				String content = FileUtils.readFileToString(file);
				String methodCode = "";
				try {
					methodCode = slip.getBetTypeCode().split("_")[2];
				} catch (Exception e) {
					log.error("获取methodCode失败slipid" + slip.getId());
				}
				
				log.debug("成功获取单式上传信息。 content = " + content);
				//获取文件
				if((!is115) && (!isSSQ)){
					content =content.replaceAll(";", " ");
					contents = StringUtils.split(content, " ");
				}else if(is115 && methodCode.equals("11")){	//115单式才拆				
					content =content.replaceAll(";", ",");
					contents = StringUtils.split(content, ",");					
				}else if(isSSQ && methodCode.equals("68")){	//SSQ单式才拆				
					content =content.replaceAll(";", " ");
					contents = StringUtils.split(content, " ");					
				}else{
					contents[0] = content;
				}
				
			} catch (IOException e) { 
				String msg="获取文件投注信息错误，文件路径=" + slip.getBetDetail();
				log.error(msg, e);
				//throw new Exception(msg,e);
				contents[0] = slip.getBetDetail();
			}
		} else {
			contents[0] = slip.getBetDetail();
		}
		log.debug("slip.getId()="+slip.getId() +"contents.length == "+contents.length +"----"+contents[0]);
		return contents;
	}
	
	
	/** 
	* @Title: getBetDetai 
	* @Description:  获取投注内容
	* @param result
	* @param order
	* @param slip
	* @param contents
	* @return
	*/
	public String[] getBetDetai_BYHAND(GameContext ctx, GameOrder order, GameSlip slip, String[] contents) throws Exception {
		if (slip.getFileMode() == 1 && slip.getId()!=148438430) {
			String[] fileName = slip.getBetDetail().split("/");
			File file = new File("C:\\Users\\Ami.Tsai\\Desktop\\filepacke\\filepacke\\"+fileName[fileName.length-1]);
			try {
				log.debug("slip.getId()="+slip.getId() + "slip.getFileMode() == 1");
				boolean is115 = order.getLotteryid().longValue() == 99301L
						|| order.getLotteryid().longValue() == 99302L
						|| order.getLotteryid().longValue() == 99303L
						|| order.getLotteryid().longValue() == 99304L
						|| order.getLotteryid().longValue() == 99305L
						|| order.getLotteryid().longValue() == 99306L
						|| order.getLotteryid().longValue() == 99307L;
				boolean isSSQ = order.getLotteryid().longValue() == 99401L;
				
				//3获取投注内容，
				String content = FileUtils.readFileToString(file);
				String methodCode = "";
				try {
					methodCode = slip.getBetTypeCode().split("_")[2];
				} catch (Exception e) {
					log.error("获取methodCode失败slipid" + slip.getId());
				}
				
				log.debug("成功获取单式上传信息。 content = " + content);
				//获取文件
				if((!is115) && (!isSSQ)){
					content =content.replaceAll(";", " ");
					contents = StringUtils.split(content, " ");
				}else if(is115 && methodCode.equals("11")){	//115单式才拆				
					content =content.replaceAll(";", ",");
					contents = StringUtils.split(content, ",");					
				}else if(isSSQ && methodCode.equals("68")){	//SSQ单式才拆				
					content =content.replaceAll(";", " ");
					contents = StringUtils.split(content, " ");					
				}else{
					contents[0] = content;
				}
				
			} catch (IOException e) { 
				String msg="获取文件投注信息错误，文件路径=" + slip.getBetDetail();
				log.error(msg, e);
				//throw new Exception(msg,e);
				contents[0] = slip.getBetDetail();
			}
		} else {
			contents[0] = slip.getBetDetail();
		}
		log.debug("slip.getId()="+slip.getId() +"contents.length == "+contents.length +"----"+contents[0]);
		return contents;
	}
	
	public static void main(String[] args) {
		System.out.println("24_10_11".split("_")[2]);
		System.out.println("24_10_11".split("_")[2]);
	}
	
	public String[] getBetDetai_bk(ProcessResult result, GameOrder order, GameSlip slip, String[] contents) {
		if (slip.getFileMode() == 1) {
			File file = new File(slip.getBetDetail());
			try {
				//3获取投注内容，
				String content = FileUtils.readFileToString(file);
				log.info("成功获取单式上传信息。 content = " + content);
				//获取文件
				contents = StringUtils.split(content, " ");
			} catch (IOException e) {
				result.fail();
				log.error("获取文件投注信息错误，文件路径=" + slip.getBetDetail(), e);
			}
		} else {
			contents[0] = slip.getBetDetail();
		}
		return contents;
	}

	/** 
	* @Title: getWinAmount 
	* @Description: 获取中奖金额
	* @param bean
	* @param gameAssistProcess
	* @param slip
	* @param contents
	* @param isWin
	 * @throws Exception 
	*/
	public void getWinAmount(GameWinPropertyBean bean, LotteryWinAmountCaculator lotteryWinAmountCaculator,
			GameSlip slip, String contents, IWinResultBean winResultBean,String resultCode) throws Exception {

		bean.setIsWinFlag(true); //中奖标识
		//中奖金额
		Long tmpWinAmount = lotteryWinAmountCaculator.getEvalWinAmount(slip, winResultBean, contents,resultCode);
		//中奖金额
		bean.setWinAmout(tmpWinAmount);
		bean.setTotalOrderBouns(bean.getTotalOrderBouns() + tmpWinAmount);

	}
	
	/** 
	* @Title: getWinAmount 
	* @Description: 获取中奖金额
	* @param bean
	* @param gameAssistProcess
	* @param slip
	* @param contents
	* @param isWin
	 * @throws Exception 
	*/
	public void getWinAmount_byhand(GameWinPropertyBean bean, LotteryWinAmountCaculator lotteryWinAmountCaculator,
			GameSlip slip, String contents, IWinResultBean winResultBean,String resultCode) throws Exception {

		bean.setIsWinFlag(true); //中奖标识
		//中奖金额
		Long tmpWinAmount = lotteryWinAmountCaculator.getEvalWinAmount_ByHand(slip, winResultBean, contents, resultCode);
		//中奖金额
		bean.setWinAmout(tmpWinAmount);
		bean.setTotalOrderBouns(bean.getTotalOrderBouns() + tmpWinAmount);

	}
}
