package com.winterframework.firefrog.fund.enums;

public class DDBFundBank {
	public enum DDBBank{
		ICBC(1L,"ICBC"),
		CMB(2L,"CMB"), 
		CCB(3L,"CCB"),
		ABC(4L,"ABC"), 
		BOC(5L,"BOC"),
		BCOM(6L,"BCOM"), 
		CMBC(7L,"CMBC"),
		CITIC(8L,"ECITIC"),
		SPDB(9L,"SPDB"),
		PSBC(10L,"PSBC"),
		CEB(11L,"CEBB"),
		PABC(12L,"SPABANK"),
		GDB(13L,"GDB"),
		HXB(14L,"HXB"),
		CIB(15L,"CIB"),
		BOB(16L,"BOB"),
		SHB(17L,"SHB"),
		NBB(19L,"NBB"),
		HZB(20L,"HZB");
		private Long value;
		private String text;
		
		private DDBBank(long value,String text){
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
			DDBBank[] types= DDBBank.values();
			for(DDBBank type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
	}
	public enum DDBBankWithDraw{
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
		
		private DDBBankWithDraw(long value,String text,String name){
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
			DDBBankWithDraw[] types= DDBBankWithDraw.values();
			for(DDBBankWithDraw type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
		public static String mappingName(Long key){
			DDBBankWithDraw[] types= DDBBankWithDraw.values();
			for(DDBBankWithDraw type:types){
			    if(type.getValue().equals(key)){
		          return type.getName();
		        }
			}
			return "";
		}
	}

}
