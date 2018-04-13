package com.winterframework.firefrog.fund.enums;


public class HBFundBank {
	public enum HBBank{
		ICBC(1L,"ICBC"),
		CMB(2L,"CMB"), 
		CCB(3L,"CCB"),
		ABC(4L,"ABC"), 
		BOC(5L,"BOC"),
		BOCOM(6L,"BOCOM"), 
		CMBC(7L,"CMBC"),
		CITIC(8L,"CITIC"),
		SPDB(9L,"SPDB"),
		PSBC(10L,"PSBC"),
		CEB(11L,"CEB"),
		PABC(12L,"PABC"),
		GDB(13L,"GDB"),
		HXB(14L,"HXB"),
		CIB(15L,"CIB");
		private Long value;
		private String text;
		
		private HBBank(long value,String text){
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
			HBBank[] types= HBBank.values();
			for(HBBank type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
	}

}
