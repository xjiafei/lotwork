package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: SellingStatusModifyRequest 
* @Description: 销售状态修改请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午5:00:48 
*  
*/
public class SellingStatusModifyRequest implements Serializable {

	private static final long serialVersionUID = 3713749572270833873L;

	/** 彩种 */
	@NotNull
	private Long lotteryid;
	/** 彩种销售状态：0，停售；1，可销售 */
	@NotNull
	private Integer status;
	/** 彩种审核状态：2，进行中；3，待审核；4，待发布 */
	@NotNull
	private Integer checkStatus;
	/** 彩種下架時間 **/
	private String takeOffTime;
	/** 彩种 */
	@NotNull
	private List<BetMethodStatusStruc> betMethodStatusStruc;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public List<BetMethodStatusStruc> getBetMethodStatusStruc() {
		return betMethodStatusStruc;
	}

	public void setBetMethodStatusStruc(List<BetMethodStatusStruc> betMethodStatusStruc) {
		this.betMethodStatusStruc = betMethodStatusStruc;
	}

	public String getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

}
