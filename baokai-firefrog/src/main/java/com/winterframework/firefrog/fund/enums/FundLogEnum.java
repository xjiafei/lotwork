package com.winterframework.firefrog.fund.enums;


public class FundLogEnum {

	
	/**
	 * withdraw:提現,apeal:申訴
	 * @ClassName FundLogEnum.java
	 * @Description 
	 * @author Ami.Tsai
	 * @date 2015年12月25日
	 *
	 */
	public enum LogModel implements EnumModel{
		WITHDRAW("0"),APEAL("1");	
		private String code;
		private LogModel(String code){
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * RECHARGE:充值,WITHDRAW:提現,APEAL:申訴
	 * @ClassName FundLogEnum.java
	 * @Description 
	 * @author Ami.Tsai
	 * @date 2015年12月25日
	 *
	 */
	public enum TipsModel implements EnumModel{
		RECHARGE("0"),WITHDRAW("1"),APEAL("2");
		private String code;
		private TipsModel(String code) {
			this.code = code;
		}
		@Override
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * UNPASS :未處裡, UNDER_CHEKC:待審核
	 * @ClassName FundLogEnum.java
	 * @Description 
	 * @author Ami.Tsai
	 * @date 2015年12月25日
	 *
	 */
	public enum TipsGroupA implements EnumModel{
		UNPASS("0") , UNDER_CHEKC("1") ;
		private String code;
		private TipsGroupA(String code) {
			this.code = code;
		}
		@Override
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * UNPASS:未通過,PASS:通過,UNDER_CHEKC:待複審;
	 * @ClassName FundLogEnum.java
	 * @Description 
	 * @author Ami.Tsai
	 * @date 2015年12月25日
	 *
	 */
	public enum TipsGroupB implements EnumModel{
		UNPASS("0"),PASS("1"),UNDER_CHEKC("2"),WAIT("3");
		private String code;
		private TipsGroupB(String code) {
			this.code = code;
		}
		@Override
		public String getCode() {
			return code;
		}
	}
	
	public interface STATUS{
		public String getValue();
		public String getText();
	}
	
	public enum WITHDRAW_STATUS implements STATUS{
		START("0","您的提现申请已经发起成功"),
		CHECK("1","您的提现申请目前正在排队审核中"),
		CHECK_PASS("2","您的提现申请已经通过审核"),
		UN_CHECK("3","您的注单异常提现进入待定状态，请您耐心等待相关部门审核处理"),
		CHECK_FAIL("4","您的提现未通过审核"),
		DRAW_CHECK("5","您的提现目前正在排队出款中"),
		DRAW_SUCCESS("6","您的提现已经出款成功"),
		//默認退款
		DRAW_RETURN_START("7","您的提现金额正在退回平台账户中"),	
		DRAW_RETURN_FAIL("8","您的提现出款失败"),			
		DRAW_RETURN("9","您的提现金额已经退款成功"),
		//按下退款
		DRAW_RETURN_START1("10","您的提款处理失败，系统将为您退回平台，请您重新发起提现。");
		String value;
		String text;		
		private WITHDRAW_STATUS(String value,String text) {
			this.value = value;
			this.text = text;
		}
		
		public String getValue(){
			return value;
		}
		
		public String getText(){
			return text;
		}
		
	}
	
	public enum APPEAL_STATUS implements STATUS{
		START("0","您的申诉已经发起成功，目前正在处理中，请您耐心等待"),
		CHECK_PASS("1","您的申诉已经通过审核，相关部门稍后为您处理提现"),
		CHECK_FAIL("2","您的申诉未通过审核，由于您提交的回执单不正确，请您重新发起申诉");
		String value;
		String text;		
		private APPEAL_STATUS(String value,String text) {
			this.value = value;
			this.text = text;
		}
		
		public String getValue(){
			return value;
		}
		
		public String getText(){
			return text;
		}
	}
	
	
}
