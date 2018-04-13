package com.winterframework.firefrog.fund.enums;

/**
 * 
 * @author Marco.Huang
 *
 */
public class FundChargeQueueEnum {

	/**
	 * 充值參數接收
	 * @author Marco.Huang
	 *
	 */
	public enum QueueStatus{
		/**處理中*/
		PROCESS("process",1),
		/**未處理*/
		UNTREAT("untreat",2),
		/**訂單成功*/
		ORDSUCCESS("success",3),
		/**訂單異常*/
		ORDABNORMAL("failed",4),
		/**加幣完成*/
		COMPLETE("complete",5),
		/**加幣異常*/
		DISPATCHERROR("dispatcherror",6),
		/**人工確認*/
		MANCHECK("mancheck",7),
		/**加幣處理中*/
		DISPATCHPROCESS("disptachprocess",8);
		private QueueStatus(String key, long value) {
			this.key = key;
			this.value = value;
		};

		private String key;
		private Long value;

		public Long getValue() {
			return value;
		};

		public static QueueStatus getStatusByValue(Long value) {
			if(value==null) return null;
			for (QueueStatus c : QueueStatus.values()) {
				if (c.getValue() == value) {
					return c;
				}
			}
			return null;
		}
	}
	
}
