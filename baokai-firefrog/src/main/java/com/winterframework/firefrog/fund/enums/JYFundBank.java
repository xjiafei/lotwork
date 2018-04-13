package com.winterframework.firefrog.fund.enums;


public class JYFundBank {
	public enum JYBank{
		ICBC(1L,"ICBC"),
		CMB(2L,"CMB"), 
		CCB(3L,"CCB"),
		ABC(4L,"ABC"), 
		BOC(5L,"BOC"),
		BOCOM(6L,"BOCO"), 
		CMBC(7L,"CMBC"),
		CITIC(8L,"CTTIC"),
		SPDB(9L,"SPDB"),
		PSBC(10L,"PSBS"),
		CEB(11L,"CEB"),
		PABC(12L,"PINGANBANK"),
		GDB(13L,"GDB"),
		HXB(14L,"HXB"),
		CIB(15L,"CIB");
		private Long value;
		private String text;
		
		private JYBank(long value,String text){
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
			JYBank[] types= JYBank.values();
			for(JYBank type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
	}

}
