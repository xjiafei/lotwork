package com.winterframework.firefrog.game.web.dto;

/***
 * 
* @ClassName: HZAssistAction 
* @Description:和值号码辅助分析
* @author Richard
* @date 2013-8-23 下午3:36:04 
*
 */
public class HZAssistAction extends AssistAction {

	private static final long serialVersionUID = 998822330305900118L;
	
	/**和值辅助走势*/
	private Integer[] hzArray = null;
	/**号码辅助走势*/
	private Integer[] numberArray = null;
	
	public HZAssistAction(Integer numberRecord) {
		super(numberRecord);		
	}
	
	/**
	 * 
	* @Title: getHsHz 
	* @Description: 后三和值 
	* @return
	 */
	public int getHsHz(){
		
		return hbit() + dbit() + ubit();
	}
	
	/**
	 * 
	* @Title: getQsHz 
	* @Description: 前三和值
	* @return
	 */
	public int getQsHz(){
		return mbit() + kbit() + hbit();
	}
	
	/**
	 * 
	* @Title: getQeHz 
	* @Description: 前二和值
	* @return
	 */
	public int getQeHz(){
		return mbit() + kbit();
	}
	
	/**
	 * 
	* @Title: getHeHz 
	* @Description:后二和值 
	* @return
	 */
	public int getHeHz(){
		return dbit() + ubit();
	}

	public Integer[] getHzArray() {
		return hzArray;
	}

	public void setHzArray(Integer[] _hzArray, int number, int count) {
		
		if(null == _hzArray){
			_hzArray = initArray(_hzArray, count);
		}
		this.hzArray = setArray(_hzArray, number);
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
