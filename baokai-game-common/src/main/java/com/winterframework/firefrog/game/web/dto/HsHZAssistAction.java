package com.winterframework.firefrog.game.web.dto;

/**
 * 
* @ClassName: HsHZAssistAction 
* @Description: 后三组选、直选和值
* @author Richard
* @date 2013-8-23 下午5:49:45 
*
 */
public class HsHZAssistAction extends HZAssistAction {

	private static final long serialVersionUID = 8445228865941640801L;

	public HsHZAssistAction(Integer numberRecord) {
		super(numberRecord);
	}

	/**后三组选,直选和值*/
	private Integer[] hsHzArray;

	public Integer[] getHsHzArray() {
		return hsHzArray;
	}

	public void setHsHzArray(Integer[] hsHzArray) {
		this.hsHzArray = hsHzArray;
	}
	
	
}
