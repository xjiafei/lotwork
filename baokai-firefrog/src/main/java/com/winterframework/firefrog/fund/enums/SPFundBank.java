package com.winterframework.firefrog.fund.enums;

import com.winterframework.firefrog.fund.enums.HBFundBank.HBBank;

public class SPFundBank {
	public enum SPBank{
		ICBC(1L,"ICBC"),
		CMB(2L,"CMBCHINA"), 
		CCB(3L,"CCB"),
		ABC(4L,"ABC"), 
		BOC(5L,"BOC"),
		BOCOM(6L,"BOCO"), 
		CMBC(7L,"CMBC"),
		CITIC(8L,"ECITIC"),
		SPDB(9L,"SPDB"),
		PSBC(10L,"POST"),
		CEB(11L,"CEB"),
		PABC(12L,"PINGAN"),
		GDB(13L,"CGB"),
		HXB(14L,"HXB"),
		CIB(15L,"CIB"),
		BCCB(16L,"BCCB"),
		SHB(17L,"SHB"),
		BJRCB(18L,"BJRCB");
		private Long value;
		private String text;
		
		private SPBank(long value,String text){
			this.value = value;
			this.text =text;
		}

		public Long getValue() {
			return value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
		
		public static String mapping(Long key){
			SPBank[] types= SPBank.values();
			for(SPBank type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
	}
	public enum SPBankWithDraw{
		ICBC(1L,"ICBC","工商银行"),
		CMB(2L,"CMB","招商银行"), 
		CCB(3L,"CCB","建设银行"),
		ABC(4L,"ABC","农业银行"), 
		BOC(5L,"BOC","中国银行"),
		BOCOM(6L,"BCOM","交通银行"), 
		CMBC(7L,"CMBC","民生银行"),
		CITIC(8L,"CITIC","中信银行"),
		SPDB(9L,"SPDB","浦发银行"),
		PSBC(10L,"PSBC","邮政储蓄银行"),
		CEB(11L,"CEB","光大银行"),
		PABC(12L,"PABC","平安银行"),
		GDB(13L,"GDB","广发银行"),
		HXB(14L,"HXB","华夏银行"),
		CIB(15L,"FIB","兴业银行");
		private Long value;
		private String text;
		private String name;
		
		private SPBankWithDraw(long value,String text,String name){
			this.value = value;
			this.text =text;
			this.name=name;
		}

		public Long getValue() {
			return value;
		}

		public String getText() {
			return text;
		}

		public String getName() {
			return name;
		}
		
		public static String mapping(Long key){
			SPBankWithDraw[] types= SPBankWithDraw.values();
			for(SPBankWithDraw type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
		public static String mappingName(Long key){
			SPBankWithDraw[] types= SPBankWithDraw.values();
			for(SPBankWithDraw type:types){
			    if(type.getValue().equals(key)){
		          return type.getName();
		        }
			}
			return "";
		}
	}

}
