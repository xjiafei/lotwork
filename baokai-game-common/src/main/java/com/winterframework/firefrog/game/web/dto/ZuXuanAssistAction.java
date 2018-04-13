package com.winterframework.firefrog.game.web.dto;


/**
 * 
* @ClassName: ZuXuanAssistAction 
* @Description: 组选图表辅助类
* @author Richard
* @date 2013-8-23 下午3:48:38 
*
 */
public class ZuXuanAssistAction extends AssistAction {

	private static final long serialVersionUID = 3054963027580133296L;

	public ZuXuanAssistAction(Integer numberRecord) {
		super(numberRecord);
	}

	/**0-9辅助表*/
	private Integer[] zxArray;
	/**组选辅助数组*/
	private Integer[] zuxuanArray;
	
	//五星组选号码
	private final static Integer[] WX_ZUXUAN_ARRAY = {43,44,45,46,47,48};
	//四星组选类型
	private final static Integer[] SX_ZUXUAN_ARRAY = {49,50,51,52};
	//三星组选， 组三，组六，豹子
	private final static Integer[] SANXING_ZUXUAN_ARRAY = {35,36,38} ;
	//二星组选类型
	private final static Integer[] EX_ZUXUAN_ARRAY = {64};
		
	
	public Integer[] getZxArray() {
		return zxArray;
	}

	public void setZxArray(Integer[] _zxArray, Integer gameGroupCode) {
		
		if(null == _zxArray){			
			_zxArray = initArray(_zxArray, COUNTS);
		}
		
		if (gameGroupCode == 10) {
			this.zxArray =  setWuXingArray(_zxArray);
		} else if (gameGroupCode == 11) {
			this.zxArray = setSiXingArray(_zxArray);
		} else if (gameGroupCode == 12) {
			this.zxArray = setQianSanArray(_zxArray);
		} else if (gameGroupCode == 13) {
			this.zxArray = setHouSanArray(_zxArray);
		} else if (gameGroupCode == 14) {
			this.zxArray = setHouErArray(_zxArray);
		} else if (gameGroupCode == 15) {
			this.zxArray = setQianErArray(_zxArray);
		}
		
	}

	public Integer[] getZuxuanArray() {
		return zuxuanArray;
	}

	public void setZuxuanArray(Integer[] _zuxuanArray, int num, String recordNumber, int zuxuanNum) {
		
		if(null == _zuxuanArray){
			_zuxuanArray = initArray(_zuxuanArray, num);
		}
		
		if(num == 6){
			this.zuxuanArray = setWuXingZuXuanArray(_zuxuanArray,zuxuanNum); // 五星组选
		}else if(num == 4){
			this.zuxuanArray = setSiXingZuXuanArray(_zuxuanArray, zuxuanNum); //四星组选
		}else if(num == 3){
			this.zuxuanArray = setSanXingZuXuanArray(_zuxuanArray, zuxuanNum);
		}else if(num == 1){
			this.zuxuanArray = setErXingZuXuanArray(_zuxuanArray, zuxuanNum);
		}
	}
	
	/** 
	* @Title: setErXingZuXuanArray 
	* @Description: 二星组选号码
	* @param _zuxuanArray
	* @param zuxuanNum
	* @return Integer[]    返回类型 
	* @throws 
	*/
	private Integer[] setErXingZuXuanArray(Integer[] _zuxuanArray, int zuxuanNum) {
		
		for (int i = 0; i < _zuxuanArray.length; i++) {
			if(EX_ZUXUAN_ARRAY[i] == zuxuanNum){
				_zuxuanArray[i] = 0;
				continue;
			}
			_zuxuanArray[i] = _zuxuanArray[i] +1; 
		}
		
		return _zuxuanArray;
	}

	/**
	 * 
	* @Title: setWuXingZuXuanArray 
	* @Description: 五星组选号码历史遗留数组
	* @param arrays
	* @param zuxuanType
	* @return
	 */
	protected Integer[] setWuXingZuXuanArray(Integer[] arrays, int zuxuanType){
		
		for (int i = 0; i < arrays.length; i++) {
			
			if(WX_ZUXUAN_ARRAY[i] == zuxuanType){
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1; 
			
		}
		
		return arrays;
	}
	
	/**
	 * 
	* @Title: setSiXinZuXuanArray 
	* @Description: 四星组选号码历史遗留数组
	* @param arrays
	* @param zuxuanType
	* @return
	 */
	protected Integer[] setSiXingZuXuanArray(Integer[] arrays, int zuxuanType){
		
		for (int i = 0; i < arrays.length; i++) {
				
			if(SX_ZUXUAN_ARRAY[i] == zuxuanType){
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1; 
			
		}
		
		return arrays;
	}
	
	/**
	 * 
	* @Title: setShanXinZuXuanArray 
	* @Description: 组三、组六、豹子 辅助号码显示 
	* @param _zuxuanArray
	* @param zuxuanNum
	* @return
	 */
	protected Integer[] setSanXingZuXuanArray(Integer[] _zuxuanArray, int zuxuanNum) {
		
		for (int i = 0; i < _zuxuanArray.length; i++) {
					
				
			if(SANXING_ZUXUAN_ARRAY[i] == zuxuanNum){
				_zuxuanArray[i] = 0;
				continue;
			}
			_zuxuanArray[i] = _zuxuanArray[i] +1; 
			
		}
		
		return _zuxuanArray;
	}

	
	
}
