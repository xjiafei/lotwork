package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 
* @ClassName: SellingStatusQueryResponse 
* @Description: 销售状态查询响应参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:55:12 
*  
*/
public class SellingStatusQueryResponse implements Serializable {

	private static final long serialVersionUID = 4035573069192458350L;

	/** 销售状态列表 */
	private List<BetMethodStatusStruc> betMethodStatusStruc;
	/** 彩种销售状态：0，停售；1，可销售 */
	private Integer status;
	/** 彩种审核状态：2，进行中；3，待审核；4，待发布 */
	private Integer checkStatus;
	/** 彩種下架時間 **/
	private Date takeOffTime;

	public List<BetMethodStatusStruc> getBetMethodStatusStruc() {
		return betMethodStatusStruc;
	}

	public void setBetMethodStatusStruc(List<BetMethodStatusStruc> betMethodStatusStruc) {
		this.betMethodStatusStruc = betMethodStatusStruc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

}
