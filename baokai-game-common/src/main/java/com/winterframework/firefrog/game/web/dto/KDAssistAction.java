package com.winterframework.firefrog.game.web.dto;


/**
 * 
* @ClassName: KDAssistAction 
* @Description: 跨度辅助分析类 
* @author Richard
* @date 2013-9-5 上午10:04:44 
*
 */
public class KDAssistAction extends AssistAction {

	private static final long serialVersionUID = 7558948317496639673L;
	
	/**和值辅助走势*/
	private Integer[] kdArray = null;
	/**号码辅助走势*/
	private Integer[] numberArray = null;
	
	public KDAssistAction(Integer numberRecord) {
		super(numberRecord);
	}

	/**
	 * 
	* @Title: hekd 
	* @Description: 后二跨度
	* @return
	 */
	public int hekd(){
		
		return Math.abs(dbit() - ubit());
	}

	/**
	 * 
	* @Title: qekd 
	* @Description: 前二跨度 
	* @return
	 */
	public int qekd(){
		return Math.abs(mbit() - kbit());
	}
	
	/**
	 * 
	* @Title: qskd 
	* @Description: 前三跨度
	* @return
	 */
	public int qskd(){
		
		int max = Math.max(mbit(), kbit());
		max = Math.max(max, hbit());
		
		int min = Math.min(mbit(), kbit());
		min = Math.min(min, hbit());
		return Math.abs(max - min);
	}
	
	/**
	 * 
	* @Title: hskd 
	* @Description:后三跨度
	* @return
	 */
	public int hskd(){
		
		int max = Math.max(hbit(), dbit());
		max = Math.max(max, ubit());
		
		int min = Math.min(hbit(), dbit());
		min = Math.min(min, ubit());
		return Math.abs(max - min);
	}

	public Integer[] getKdArray() {
		return kdArray;
	}

	public void setKdArray(Integer[] _kdArray, Integer gameGroupCode) {
		if(null == _kdArray){
			_kdArray = initArray(_kdArray, COUNTS);
		}
		if (gameGroupCode == 12) {
			this.kdArray = setArray(_kdArray, qskd());
		} else if (gameGroupCode == 13) {
			this.kdArray = setArray(_kdArray, hskd());
		} else if (gameGroupCode == 14) {
			this.kdArray = setArray(_kdArray, hekd());
		} else if (gameGroupCode == 15) {
			this.kdArray = setArray(_kdArray, qekd());
		}
	}

	public Integer[] getNumberArray() {
		return numberArray;
	}

	public void setNumberArray(Integer[] _numberArray, Integer gameGroupCode) {
		
		if(null == _numberArray){
			_numberArray = initArray(_numberArray, COUNTS);
		}
		if (gameGroupCode == 12) {
			this.numberArray = setQianSanArray(_numberArray);
		} else if (gameGroupCode == 13) {
			this.numberArray = setHouSanArray(_numberArray);
		} else if (gameGroupCode == 14) {
			this.numberArray = setHouErArray(_numberArray);
		} else if (gameGroupCode == 15) {
			this.numberArray = setQianErArray(_numberArray);
		}
	}
}
