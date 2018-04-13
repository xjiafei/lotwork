package com.winterframework.firefrog.game.web.dto;

/**
 * 
* @ClassName: TrendAssistAction 
* @Description: 走势分析 
* @author Richard
* @date 2013-9-5 下午3:34:47 
*
 */
public class TrendAssistAction extends AssistAction {

	public TrendAssistAction(Integer numberRecord) {
		super(numberRecord);
	}

	private static final long serialVersionUID = -402556562935401805L;

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
	/**号码分布*/
	private Integer[] fbNumber;
	/**号码跨度*/
	private Integer[] kdNumber;
	/**大小比*/
	private Integer[] dxbNumber;
	/**单双比*/
	private Integer[] dsbNumber;
	/**质合比*/
	private Integer[] zhbNumber;
	
	public Integer[] getMyriabit() {
		return myriabit;
	}
	
	public void setMyriabit(Integer[] _myriabit) {
		
		if(null == _myriabit){
			_myriabit = initArray(_myriabit, COUNTS);
		}
		this.myriabit = setArray(_myriabit);
	}
	
	public Integer[] getKilobit() {
		return kilobit;
	}
	
	public void setKilobit(Integer[] _kilobit) {
		
		if(null == _kilobit){
			_kilobit = initArray(_kilobit, COUNTS);
		}
		this.kilobit = setArray(_kilobit);
	}
	
	public Integer[] getHundred() {
		return hundred;
	}
	
	public void setHundred(Integer[] _hundred) {
		
		if(null == _hundred){
			_hundred = initArray(_hundred, COUNTS);
		}
		this.hundred = setArray(_hundred);
	}
	public Integer[] getDecade() {
		return decade;
	}
	
	public void setDecade(Integer[] _decade) {
		
		if(null == _decade){
			_decade = initArray(_decade, COUNTS);
		}
		this.decade = setArray(_decade);
	}
	
	public Integer[] getUnits() {
		return units;
	}
	
	public void setUnits(Integer[] _units) {
		
		if(null == _units){
			_units = initArray(_units, COUNTS);
		}
		this.units = setArray(_units);
	}
	
	public Integer[] getFbNumber() {
		return fbNumber;
	}
	
	public void setFbNumber(Integer[] _fbNumber) {
		
		if(null == _fbNumber){
			_fbNumber = initArray(_fbNumber, COUNTS);
		}
		
		this.fbNumber = setArray(_fbNumber);
	}

	public Integer[] getKdNumber() {
		return kdNumber;
	}

	public void setKdNumber(Integer[] _kdNumber) {
		
		if(null == _kdNumber){
			_kdNumber = initArray(_kdNumber, 9); //1-9
		}
		this.kdNumber = setArray(_kdNumber);
	}

	public Integer[] getDxbNumber() {
		return dxbNumber;
	}

	public void setDxbNumber(Integer[] _dxbNumber) {
		
		if(null == _dxbNumber){
			_dxbNumber = initArray(_dxbNumber, 6);
		}
		this.dxbNumber = setArray(_dxbNumber);
	}

	public Integer[] getDsbNumber() {
		return dsbNumber;
	}

	public void setDsbNumber(Integer[] _dsbNumber) {
		
		if(null == _dsbNumber){
			_dsbNumber = initArray(_dsbNumber, 6);
		}
		this.dsbNumber = setArray(_dsbNumber);
	}

	public Integer[] getZhbNumber() {
		return zhbNumber;
	}

	public void setZhbNumber(Integer[] _zhbNumber) {
		
		if(null == _zhbNumber){
			_zhbNumber = initArray(_zhbNumber, 6);
		}
		this.zhbNumber = setArray(_zhbNumber);
	}
	
}
