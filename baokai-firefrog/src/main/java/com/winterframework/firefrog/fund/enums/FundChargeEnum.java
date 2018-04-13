package com.winterframework.firefrog.fund.enums;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class FundChargeEnum {

	/**
	 * 充值渠道
	 * @author Ami.Tsai
	 *
	 */
	public enum ChargeMode{
		/**DP*/
		DP(1l),
		/**通匯*/
		THPAY(2l),
		/**滙潮(銀聯)*/
		ECPSS(3l),
		/**匯博(快捷)*/
		HBPAY(4l),
		/**華勢(微信)*/
		WORTH(7l),
		/**秒付*/
		SP(8l),
		/**多得宝*/
		DDB(9l),
		/**网富通*/
		WFT(10l),
		/**智付*/
		DIN(11l),
		/**华银*/
		HUAYIN(12l),
		/**银邦*/
		YINBANG(13l),
		/**金阳*/
		JINYANG(14l);
		
		private long value;
		
		private ChargeMode(long value){
			this.value =value;
		}

		public long getValue() {
			return value;
		}

		public void setValue(long value) {
			this.value = value;
		}
	}
	
	/**
	 * 充值方式
	 * @author Ami.Tsai
	 *
	 */
	public enum DepositeMode{
		/**手Q*/
		QQ(9l),
		/**微信*/
		WECHAT(7l),
		/**支付寶*/
		ALIPAY(6l),
		/**銀聯*/
		UNIPAY(5l),
		/**財富通*/
		THIRDBANK(3l),
		/**快捷*/
		QUICK(2l),
		/**網銀*/
		NETBANK(1l);
		
		private long value;
		
		private DepositeMode(long value){
			this.value =value;
		}

		public long getValue() {
			return value;
		}

		public void setValue(long value) {
			this.value = value;
		}
	}
	
}
