package com.winterframework.firefrog.game.service.order.utlis.jlsbrngdraw;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.service.order.utlis.Encrypt;
import com.winterframework.firefrog.game.service.order.utlis.MmcDrawService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

public abstract class AbstractJlsbDrawService{
	
	private Logger log = LoggerFactory.getLogger(AbstractJlsbDrawService.class);
	
	@PropertyConfig(value = "jlsb.key")
	protected String key;
	@PropertyConfig(value = "jlsb.iv")
	protected String iv;
	
	protected Encrypt cy;
	@Resource(name="mmcDrawService")
	private MmcDrawService mmcDrawService;
	
	/**
	 * 取得傳到RNG參數
	 * @param lotName
	 * @param issue
	 * @param drawTime
	 * @return
	 */
	protected abstract String getParam(String lotName,String issue, String drawTime);
	/**
	 * 進行RNG取號
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract String doRngGetNumber(String param) throws Exception;
	/**
	 * parse返回開獎號
	 * @param result
	 * @return
	 */
	protected abstract String parseResult(String result);

	@PostConstruct
	public void init() {
		cy = Encrypt.getInstance(key, iv);
	}
	
	/**
	 * 
	 * @param briefCode 彩種代號
	 * @param issue 期號
	 * @param drawTime 開獎時間
	 * @return
	 * @throws Exception
	 */
	public String getDrawResult(String briefCode, String issue, String drawTime) throws Exception {
		
		log.info("start get rng number briefcode={},issue={},drawTime={}",new Object[]{briefCode,issue,drawTime});
		String param = getParam(briefCode,issue,drawTime);
		param = cy.encryptRijndael(param);
		String rngResultNumbr = doRngGetNumber(param);
		rngResultNumbr = cy.decryptRijndael(rngResultNumbr);
		rngResultNumbr = parseResult(rngResultNumbr);
		log.info("end get rng number briefcode={},issue={},drawTime={}",new Object[]{briefCode,issue,drawTime});
		return rngResultNumbr;
	}
	public String getDrawResult(Long lotteryId, Long issueCode) throws Exception {
		log.error("start get draw number lotteryId={},issueCode={}",new Object[]{lotteryId,issueCode});
		return  mmcDrawService.getBall(lotteryId, issueCode+"");
	}
	
}
