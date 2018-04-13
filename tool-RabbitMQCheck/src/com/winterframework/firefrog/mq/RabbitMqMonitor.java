package com.winterframework.firefrog.mq;


public class RabbitMqMonitor {

	public static void main(String[] args) throws Exception {
		LogUtil logger = new LogUtil(RabbitMqMonitor.class);
		logger.doLog("RabbitMqMonitor start");
		try {
			RabbitMqService service = null;
			if (args.length > 0){
				service = new RabbitMqService(args[0],args[1],args[2],args[3]);
				
			}else{
				service = new RabbitMqService();
			}
			
			service.sendMsg();
			service.recevieMsg();
		} catch (Exception e) {
			logger.doLog(e);
		}
		logger.doLog("RabbitMqMonitor end");
	}

}
