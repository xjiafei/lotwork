package com.winterframework.firefrog.schedule;


import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("FundChargeQueueTask")
public class FundChargeQueueTask {
	
	private Logger logger = LoggerFactory.getLogger(FundChargeQueueTask.class);
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	
	@PropertyConfig(value = "url.baseFundUrl")
	private String baseUrl;
	
	@PropertyConfig(value = "url.fund.chargeCheck")
	private String chargeCheck;
	
	public void execute() throws Exception {
		logger.info("----------chargeDispatch start --------- ");
		//查詢ThPay 訂單處理中的
		
		mcwClient.invokeHttpPost(baseUrl+chargeCheck, null);
		
	}
}
