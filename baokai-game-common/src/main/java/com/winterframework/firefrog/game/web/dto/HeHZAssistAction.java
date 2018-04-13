package com.winterframework.firefrog.game.web.dto;

/**
 * 
* @ClassName: HeHZAssistAction 
* @Description: 后二组选、直选和值 
* @author Richard
* @date 2013-8-23 下午5:52:00 
*
 */
public class HeHZAssistAction extends HZAssistAction {

	private static final long serialVersionUID = -337491913433495273L;

	public HeHZAssistAction(Integer numberRecord) {
		super(numberRecord);
	}
	
	/**后二直选、组选和值*/
	private Integer[] heHzArray;

	public Integer[] getHeHzArray() {
		return heHzArray;
	}

	public void setHeHzArray(Integer[] heHzArray) {
		this.heHzArray = heHzArray;
	}

}
