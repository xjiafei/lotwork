package com.winterframework.firefrog.game.web.dto;

/**
 * 
* @ClassName: ZXAssistAction 
* @Description: 直选图表辅助类 
* @author Richard
* @date 2013-8-23 下午3:45:59 
*
 */
public class ZXAssistAction extends AssistAction {

	private static final long serialVersionUID = -3998789624384444439L;
	
	public ZXAssistAction(Integer numberRecord) {
		super(numberRecord);
	}
	
	/**万位 中的0-9*/
	private  Integer[] myriabit;
	/**千位*/
	private Integer[] kilobit;
	/**百位*/
	private Integer[] hundred;
	/**十位*/
	private Integer[] decade; 
	/**个位*/
	private Integer[] units;
	

	public Integer[] getMyriabit() {
		return myriabit;
	}
	
	public void setMyriabit(Integer[] _myriabit) {
		
		int m = mbit();  //获取开奖号码万位数的值
		
		if(null == _myriabit){
			_myriabit =initArray(_myriabit, COUNTS);
		}
		
		this.myriabit = setArray(_myriabit, m);
		
	}
	
	
	
	public Integer[] getKilobit() {
		return kilobit;
	}
	
	public void setKilobit(Integer[] _kilobit) {
		
		int k = kbit(); //获取开奖号码千位数的值
		
		if(null == _kilobit){
			_kilobit =initArray(_kilobit, COUNTS);
		}
		
		this.kilobit = setArray(_kilobit, k);;
		
	}
	
	public Integer[] getHundred() {
		return hundred;
	}
	
	public void setHundred(Integer[] _hundred) {
		
		int h = hbit(); //获取开奖号码百位数的值
		
		if(null == _hundred){
			_hundred = initArray(_hundred, COUNTS);
		}
		
		this.hundred = setArray(_hundred, h);;
		
	}
	
	public Integer[] getDecade() {
		return decade;
	}
	
	public void setDecade(Integer[] _decade) {
		
		int d = dbit(); //获取开奖号码十位数的值
		
		if(null == _decade){
			_decade = initArray(_decade, COUNTS);
		}
		
		this.decade = setArray(_decade, d);
	}
	
	public Integer[] getUnits() {
		return units;
	}
	
	public void setUnits(Integer[] _units) {
		
		int u = ubit(); //获取开奖号码个位数的值
		
		if(null == _units){
			_units = initArray(_units, COUNTS);
		}
		
		this.units = setArray(_units, u);
	}

}
