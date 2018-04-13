package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 獎期封鎖資料(來源存在 Radis，Key:"GAME_LOCK_"lotteryid_key_issueCode)
 * @author Pogi.Lin
 */
@Document(collection = "locks")
public class LockIssue {
	/**ID；等同 Redis key:"GAME_LOCK_"lotteryid_key_issueCode*/
	@Id
	private String id;
	/**当前的總投注金额(也可以理解为当前的封锁值)*/
	private Long betTotal=0L;
	/**紅球封鎖值；對應雙色球*/
	private Long redLimit;
	/**藍球封鎖值；對應雙色球*/
	private Long blueLimmit;
	/**返点值*/
	private Long retPoint = 0L;
	/**封鎖值；對應3D、P3、六合彩(特碼)*/
	@JsonIgnore
	private Long upValue;
	/**封鎖值；對應P5、六合彩(正特碼一肖)*/
	@JsonIgnore
	private Long upValue2;
	/**封鎖值；對應六合彩(其他玩法)*/
	@JsonIgnore
	private Long upValue3;
	/**所有變價方案階段*/
	@JsonIgnore
	private Map<Integer, PhaseStatus> allPhases = new HashMap<Integer, PhaseStatus>();
	/**目前變價方案階段*/
	@JsonIgnore
	private Map<Integer, PhaseStatus> currPhases = new HashMap<Integer, PhaseStatus>();
	/**所有封鎖点当前的状态*/
	private Map<String, PontStatus> points = new HashMap<String, PontStatus>();
	/**六合彩紀錄該號碼上次出現之盈虧值*/
	private Map<String,Long> preLockValueMap = new HashMap<String, Long>();
	/**六合彩封鎖數字*/
	private List<String> lockNumbers = new ArrayList<String>();
	
	/**
	 * 取得ID；等同 Redis key:"GAME_LOCK_"lotteryid_key_issueCode。
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 取得封鎖值；對應3D、P3、六合彩(特碼)。
	 * @return
	 */
	public Long getUpValue() {
		return upValue;
	}

	/**
	 * 設定封鎖值；對應3D、P3、六合彩(特碼)。
	 * @param upValue
	 */
	public void setUpValue(Long upValue) {
		this.upValue = upValue;
	}
	
	/**
	 * 取得封鎖值；對應P5、六合彩(正特碼一肖)。
	 * @return 
	 */
	public Long getUpValue2() {
		return upValue2;
	}

	/**
	 * 設定封鎖值；對應P5、六合彩(正特碼一肖)。
	 * @param upValue2 
	 */
	public void setUpValue2(Long upValue2) {
		this.upValue2 = upValue2;
	}

	/**
	 * 取得封鎖值；對應六合彩(其他玩法)。
	 * @return 
	 */
	public Long getUpValue3() {
		return upValue3;
	}

	/**
	 * 設定封鎖值；對應六合彩(其他玩法)。
	 * @param upValue3 
	 */
	public void setUpValue3(Long upValue3) {
		this.upValue3 = upValue3;
	}

	/**
	 * 設定ID；等同 Redis key:"GAME_LOCK_"lotteryid_key_issueCode。
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 取得当前的總投注金额(也可以理解为当前的封锁值)。
	 * @return
	 */
	public Long getBetTotal() {
		return betTotal;
	}

	/**
	 * 設定当前的總投注金额(也可以理解为当前的封锁值)。
	 * @param betTotal
	 */
	public void setBetTotal(Long betTotal) {
		this.betTotal = betTotal;
	}

	/**
	 * 取得目前變價方案階段。
	 * @return
	 */
	public Map<Integer, PhaseStatus> getCurrPhases() {
		return currPhases;
	}

	/**
	 * 設定目前變價方案階段。
	 * @param currPhases
	 */
	public void setCurrPhases(Map<Integer, PhaseStatus> currPhases) {
		this.currPhases = currPhases;
	}

	/**
	 * 当前封鎖點的信息
	 * @author Pogi.Lin
	 */
	public static class PontStatus {
		/**目前號碼中奖時要支付的金额(变价的值)*/
		private Long total = 0L;
		/**当前阶段值 1-5*/
		private Integer currPhase = 0;
		/**主要用于双色球*/
		private Integer baishu=0;

		/**
		 * 取得主要用于双色球。
		 * @return
		 */
		public Integer getBaishu() {
			return baishu;
		}

		/**
		 * 設定主要用于双色球。
		 * @param baishu
		 */
		public void setBaishu(Integer baishu) {
			this.baishu = baishu;
		}

		/**
		 * 取得目前號碼中奖時要支付的金额(变价的值)。
		 * @return
		 */
		public Long getTotal() {
			return total;
		}

		/**
		 * 設定目前號碼中奖時要支付的金额(变价的值)。
		 * @param total
		 */
		public void setTotal(Long total) {
			this.total = total;
		}

		/**
		 * 取得当前阶段值 1-5。
		 * @return
		 */
		public Integer getCurrPhase() {
			return currPhase;
		}

		/**
		 * 設定当前阶段值 1-5。
		 * @param currPhase
		 */
		public void setCurrPhase(Integer currPhase) {
			this.currPhase = currPhase;
		}
		public String toString(){
			return ToStringBuilder.reflectionToString(this);
		}
	}

	/**
	 * 各个阶段的值(From gameLockAppraise.change_struc)
	 * @author Pogi.Lin
	 */
	public static class PhaseStatus {
		/**changeStruc 的 rate*/
		Long rate;
		/**變價最小值*/
		Long minVal;
		/**changeStruc 的 To*/
		Long upLimit;
		/**changeStruc 的 From*/
		Long minLimit;

		/**
		 * 取得changeStruc 的 rate。
		 * @return
		 */
		public Long getRate() {
			return rate;
		}

		/**
		 * 設定changeStruc 的 rate。
		 * @param rate
		 */
		public void setRate(Long rate) {
			this.rate = rate;
		}

		/**
		 * 取得變價最小值。
		 * @return
		 */
		public Long getMinVal() {
			return minVal;
		}

		/**
		 * 設定變價最小值。
		 * @param minVal
		 */
		public void setMinVal(Long minVal) {
			this.minVal = minVal;
		}

		/**
		 * 取得changeStruc 的 To。
		 * @return
		 */
		public Long getUpLimit() {
			return upLimit;
		}

		/**
		 * 設定changeStruc 的 To。
		 * @param upLimit
		 */
		public void setUpLimit(Long upLimit) {
			this.upLimit = upLimit;
		}

		/**
		 * 取得changeStruc 的 From。
		 * @return
		 */
		public Long getMinLimit() {
			return minLimit;
		}

		/**
		 * 設定changeStruc 的 From。
		 * @param minLimit
		 */
		public void setMinLimit(Long minLimit) {
			this.minLimit = minLimit;
		}
		public String toString(){
			return ToStringBuilder.reflectionToString(this);
		}


	}

	/**
	 * 取得所有變價方案階段。
	 * @return
	 */
	public Map<Integer, PhaseStatus> getAllPhases() {
		return allPhases;
	}

	/**
	 * 取得紅球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getRedLimit() {
		return redLimit;
	}

	/**
	 * 設定紅球封鎖值；對應雙色球。
	 * @param redLimit
	 */
	public void setRedLimit(Long redLimit) {
		this.redLimit = redLimit;
	}
	
	/**
	 * 取得藍球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getBlueLimmit() {
		return blueLimmit;
	}

	/**
	 * 設定藍球封鎖值；對應雙色球。
	 * @param blueLimmit
	 */
	public void setBlueLimmit(Long blueLimmit) {
		this.blueLimmit = blueLimmit;
	}

	/**
	 * 設定所有變價方案階段。
	 * @param allPhases
	 */
	public void setAllPhases(Map<Integer, PhaseStatus> allPhases) {
		this.allPhases = allPhases;
	}

	/**
	 * 取得所有封鎖点当前的状态。
	 * @return
	 */
	public Map<String, PontStatus> getPoints() {
		return points;
	}

	/**
	 * 設定所有封鎖点当前的状态。
	 * @param points
	 */
	public void setPoints(Map<String, PontStatus> points) {
		this.points = points;
	}

	/**
	 * 取得返点值。
	 * @return
	 */
	public Long getRetPoint() {
		return retPoint;
	}

	/**
	 * 設定返点值。
	 * @param retPoint
	 */
	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 取得六合彩紀錄該號碼上次出現之盈虧值。
	 * @return
	 */
	public Map<String, Long> getPreLockValueMap() {
		return preLockValueMap;
	}

	/**
	 * 設定六合彩紀錄該號碼上次出現之盈虧值。
	 * @param preLockValueMap
	 */
	public void setPreLockValueMap(Map<String, Long> preLockValueMap) {
		this.preLockValueMap = preLockValueMap;
	}

	/**
	 * 取得六合彩封鎖數字。
	 * @return
	 */
	public List<String> getLockNumbers() {
		return lockNumbers;
	}

	/**
	 * 設定六合彩封鎖數字。
	 * @param lockNumbers
	 */
	public void setLockNumbers(List<String> lockNumbers) {
		this.lockNumbers = lockNumbers;
	}
}