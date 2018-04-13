package com.winterframework.firefrog.fund.enums;


public class THFundBank {
	public enum THBank{
		ICBC(1L,"ICBC"),
		CMBC(2L,"CMBC"), 
		CCB(3L,"CCB"),
		ABC(4L,"ABC"), 
		BOC(5L,"BOC"),
		BOCOM(6L,"BOCOM"), 
		CMBCS(7L,"CMBCS"),
		ECITIC(8L,"ECITIC"),
		SPDB(9L,"SPDB"),
		PSBC(10L,"PSBC"),
		CEBBANK(11L,"CEBBANK"),
		PINGAN(12L,"PINGAN"),
		CGB(13L,"CGB"),
		HXB(14L,"HXB"),
		CIB(15L,"CIB");
		private Long value;
		private String text;
		
		private THBank(long value,String text){
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
			THBank[] types= THBank.values();
			for(THBank type:types){
			    if(type.getValue().equals(key)){
		          return type.getText();
		        }
			}
			return "";
		}
	}

}
