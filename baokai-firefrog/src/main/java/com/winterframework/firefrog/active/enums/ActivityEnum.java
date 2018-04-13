package com.winterframework.firefrog.active.enums;

public class ActivityEnum {

	/**
	 * 9月活動第二波代碼轉換
	 * @author Ami.Tsai
	 *
	 */
	public enum StepActivity2CodeTransfer{
		OrignLv("",8),
		SevenLv("9-7",7),
		SixLv("9-6",6),
		FiveLv("9-5",5),
		FourLv("9-4",4),
		ThreeLv("9-3",3),
		TwoLv("9-2",2),
		OneLv("9-1",1),
		TopLv("9-0",0);
		
		private String code;
		private Integer lv;
		
		private StepActivity2CodeTransfer(String code,Integer lv){
			this.code=code;
			this.lv=lv;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Integer getLv() {
			return lv;
		}

		public void setLv(Integer lv) {
			this.lv = lv;
		}
	}
	
	public static String getCodeByLv(Integer lv){
		for(StepActivity2CodeTransfer transfer:StepActivity2CodeTransfer.values()){
			if(transfer.getLv().equals(lv)){
				return transfer.getCode();				
			}
		}
		return "";
	}
	
	public static Integer getLvByCode(String code){
		for(StepActivity2CodeTransfer transfer:StepActivity2CodeTransfer.values()){
			if(transfer.getCode().equals(code)){
				return transfer.getLv();				
			}
		}
		return -1;
	}
}
