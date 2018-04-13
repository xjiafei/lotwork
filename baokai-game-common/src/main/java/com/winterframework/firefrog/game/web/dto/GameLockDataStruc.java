package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/**
 * 封鎖資料結構體。
 * @author Pogi.Lin
 */
public class GameLockDataStruc {

	/**全体号码假设盈亏值均数(div id = p3)*/
	private Double totalNumberValueAvg;
	/**截止目前总销售额(div id = p3)*/
	private Double totalSaleValue;
	/**理论利润率(div id = p3)*/
	private Double theoryProfitRate;
	/**全体号码假设盈亏值标准差(div id = p3)*/
	private Double totalNumberGrantProfitBZC;
	/**全体号码假设盈亏值变异系数(div id = p3)*/
	private Double totalNumberGrantProfitBYXS;
	/**當期开奖号码(div id = p3)*/
	private String currentGameResult;
	/**盈亏(div id = p3)*/
	private Double theoryProfitValue;
	/**頁面P3區段所需資料*/
	private List<List<GameNumberShares>> gameSharesStruc;
	
	/**全体号码假设盈亏值均数(div id = p5)*/
	private Double totalNumberValueAvg2;
	/**截止目前总销售额(div id = p5)*/
	private Double totalSaleValue2;
	/**理论利润率(div id = p5)*/
	private Double theoryProfitRate2;
	/**全体号码假设盈亏值标准差(div id = p5)*/
	private Double totalNumberGrantProfitBZC2;
	/**全体号码假设盈亏值变异系数(div id = p5)*/
	private Double totalNumberGrantProfitBYXS2;
	/**當期开奖号码(div id = p5)*/
	private String currentGameResult2;
	/**盈亏(div id = p5)*/
	private Double theoryProfitValue2;
	/**頁面P5區段所需資料*/
	private List<List<GameNumberShares>> gameSharesStruc2;

	/**
	 * 取得全体号码假设盈亏值均数(div id = p3)。
	 * @return
	 */
	public Double getTotalNumberValueAvg() {
		return totalNumberValueAvg;
	}
	/**
	 * 設定全体号码假设盈亏值均数(div id = p3)。
	 * @param totalNumberValueAvg
	 */
	public void setTotalNumberValueAvg(Double totalNumberValueAvg) {
		this.totalNumberValueAvg = totalNumberValueAvg;
	}
	/**
	 * 取得截止目前总销售额(div id = p3)。
	 * @return
	 */
	public Double getTotalSaleValue() {
		return totalSaleValue;
	}
	/**
	 * 設定截止目前总销售额(div id = p3)。
	 * @param totalSaleValue
	 */
	public void setTotalSaleValue(Double totalSaleValue) {
		this.totalSaleValue = totalSaleValue;
	}
	/**
	 * 取得理论利润率(div id = p3)。
	 * @return
	 */
	public Double getTheoryProfitRate() {
		return theoryProfitRate;
	}
	/**
	 * 設定理论利润率(div id = p3)。
	 * @param theoryProfitRate
	 */
	public void setTheoryProfitRate(Double theoryProfitRate) {
		this.theoryProfitRate = theoryProfitRate;
	}
	/**
	 * 取得全体号码假设盈亏值标准差(div id = p3)。
	 * @return
	 */
	public Double getTotalNumberGrantProfitBZC() {
		return totalNumberGrantProfitBZC;
	}
	/**
	 * 設定全体号码假设盈亏值标准差(div id = p3)。
	 * @param totalNumberGrantProfitBZC
	 */
	public void setTotalNumberGrantProfitBZC(Double totalNumberGrantProfitBZC) {
		this.totalNumberGrantProfitBZC = totalNumberGrantProfitBZC;
	}
	/**
	 * 取得全体号码假设盈亏值变异系数(div id = p3)。
	 * @return
	 */
	public Double getTotalNumberGrantProfitBYXS() {
		return totalNumberGrantProfitBYXS;
	}
	/**
	 * 設定全体号码假设盈亏值变异系数(div id = p3)。
	 * @param totalNumberGrantProfitBYXS
	 */
	public void setTotalNumberGrantProfitBYXS(Double totalNumberGrantProfitBYXS) {
		this.totalNumberGrantProfitBYXS = totalNumberGrantProfitBYXS;
	}
	/**
	 * 取得頁面P3區段所需資料。
	 * @return
	 */
	public List<List<GameNumberShares>> getGameSharesStruc() {
		return gameSharesStruc;
	}
	/**
	 * 設定頁面P3區段所需資料。
	 * @param gameSharesStruc
	 */
	public void setGameSharesStruc(List<List<GameNumberShares>> gameSharesStruc) {
		this.gameSharesStruc = gameSharesStruc;
	}
	/**
	 * 取得頁面P5區段所需資料。
	 * @return
	 */
	public List<List<GameNumberShares>> getGameSharesStruc2() {
		return gameSharesStruc2;
	}
	/**
	 * 設定頁面P5區段所需資料。
	 * @param gameSharesStruc2
	 */
	public void setGameSharesStruc2(List<List<GameNumberShares>> gameSharesStruc2) {
		this.gameSharesStruc2 = gameSharesStruc2;
	}

	/**
	 * 取得全体号码假设盈亏值均数(div id = p5)。
	 * @return
	 */
	public Double getTotalNumberValueAvg2() {
		return totalNumberValueAvg2;
	}
	/**
	 * 設定全体号码假设盈亏值均数(div id = p5)。
	 * @param totalNumberValueAvg2
	 */
	public void setTotalNumberValueAvg2(Double totalNumberValueAvg2) {
		this.totalNumberValueAvg2 = totalNumberValueAvg2;
	}
	/**
	 * 取得截止目前总销售额(div id = p5)。
	 * @return
	 */
	public Double getTotalSaleValue2() {
		return totalSaleValue2;
	}
	/**
	 * 設定截止目前总销售额(div id = p5)。
	 * @param totalSaleValue2
	 */
	public void setTotalSaleValue2(Double totalSaleValue2) {
		this.totalSaleValue2 = totalSaleValue2;
	}
	/**
	 * 取得理论利润率(div id = p5)。
	 * @return
	 */
	public Double getTheoryProfitRate2() {
		return theoryProfitRate2;
	}
	/**
	 * 設定理论利润率(div id = p5)。
	 * @param theoryProfitRate2
	 */
	public void setTheoryProfitRate2(Double theoryProfitRate2) {
		this.theoryProfitRate2 = theoryProfitRate2;
	}
	/**
	 * 取得全体号码假设盈亏值标准差(div id = p5)。
	 * @return
	 */
	public Double getTotalNumberGrantProfitBZC2() {
		return totalNumberGrantProfitBZC2;
	}
	/**
	 * 設定全体号码假设盈亏值标准差(div id = p5)。
	 * @param totalNumberGrantProfitBZC2
	 */
	public void setTotalNumberGrantProfitBZC2(Double totalNumberGrantProfitBZC2) {
		this.totalNumberGrantProfitBZC2 = totalNumberGrantProfitBZC2;
	}
	/**
	 * 取得全体号码假设盈亏值变异系数(div id = p5)。
	 * @return
	 */
	public Double getTotalNumberGrantProfitBYXS2() {
		return totalNumberGrantProfitBYXS2;
	}
	/**
	 * 設定全体号码假设盈亏值变异系数(div id = p5)。
	 * @param totalNumberGrantProfitBYXS2
	 */
	public void setTotalNumberGrantProfitBYXS2(Double totalNumberGrantProfitBYXS2) {
		this.totalNumberGrantProfitBYXS2 = totalNumberGrantProfitBYXS2;
	}

	/**
	 * 取得當期开奖号码(div id = p3)。
	 * @return
	 */
	public String getCurrentGameResult() {
		return currentGameResult;
	}

	/**
	 * 設定當期开奖号码(div id = p3)。
	 * @param currentGameResult
	 */
	public void setCurrentGameResult(String currentGameResult) {
		this.currentGameResult = currentGameResult;
	}
	/**
	 * 取得盈亏(div id = p3)。
	 * @return
	 */
	public Double getTheoryProfitValue() {
		return theoryProfitValue;
	}
	/**
	 * 設定盈亏(div id = p3)。
	 * @param theoryProfitValue
	 */
	public void setTheoryProfitValue(Double theoryProfitValue) {
		this.theoryProfitValue = theoryProfitValue;
	}
	/**
	 * 取得當期开奖号码(div id = p5)。
	 * @return
	 */
	public String getCurrentGameResult2() {
		return currentGameResult2;
	}
	/**
	 * 設定當期开奖号码(div id = p5)。
	 * @param currentGameResult2
	 */
	public void setCurrentGameResult2(String currentGameResult2) {
		this.currentGameResult2 = currentGameResult2;
	}
	/**
	 * 取得盈亏(div id = p5)。
	 * @return
	 */
	public Double getTheoryProfitValue2() {
		return theoryProfitValue2;
	}
	/**
	 * 設定盈亏(div id = p5)。
	 * @param theoryProfitValue2
	 */
	public void setTheoryProfitValue2(Double theoryProfitValue2) {
		this.theoryProfitValue2 = theoryProfitValue2;
	}
	
	/**
	 * 計算出 TotalNumberValueAvg, TotalNumberValueAvg2, TotalNumberGrantProfitBZC, TotalNumberGrantProfitBZC2, TotalNumberGrantProfitBYXS, TotalNumberGrantProfitBYXS2, TheoryProfitRate, TheoryProfitRate2 的值。 
	 * @throws Exception
	 */
	public void caculateValue() throws Exception {
		setTotalNumberValueAvg(caculateAvg(getGameSharesStruc()));
		setTotalNumberValueAvg2(caculateAvg(getGameSharesStruc2()));
		setTotalNumberGrantProfitBZC(caculateBzc(getTotalNumberValueAvg(), getGameSharesStruc()));

		setTotalNumberGrantProfitBZC2(caculateBzc(getTotalNumberValueAvg2(),
				getGameSharesStruc2()));
		setTotalNumberGrantProfitBYXS(getTotalNumberValueAvg().doubleValue() != 0 ? getTotalNumberGrantProfitBZC() * 100 / getTotalNumberValueAvg() : 0);
		setTotalNumberGrantProfitBYXS2(getTotalNumberValueAvg2().doubleValue() != 0 ? getTotalNumberGrantProfitBZC2() * 100 / getTotalNumberValueAvg2() : 0);
		setTheoryProfitRate(getTotalSaleValue()!=null?(getTotalSaleValue().doubleValue() != 0 ? getTotalNumberValueAvg()
				/ getTotalSaleValue() : 0):0);
		setTheoryProfitRate2(getTotalSaleValue2()!=null?(getTotalSaleValue2().doubleValue() != 0 ? getTotalNumberValueAvg2()
				/ getTotalSaleValue2() : 0):0);
	}
	
	private Double caculateAvg(List<List<GameNumberShares>> list1) throws Exception {
		Double avgValue = 0d;
		if (list1 != null && !list1.isEmpty()) {
			int num = 0;
			for (List<GameNumberShares> list : list1) {
				for (GameNumberShares shares : list) {
					avgValue += shares.getProfitLoss() / 10000;
					num++;
				}
			}
			avgValue = avgValue / num;
		}
		return avgValue;
	}
	
	private Double caculateBzc(Double avgVal, List<List<GameNumberShares>> list1) throws Exception {
		Double bzcValue = 0d;
		if (list1 != null && !list1.isEmpty()) {
			int num = 0;
			for (List<GameNumberShares> list : list1) {
				for (GameNumberShares shares : list) {
					Double val = (shares.getProfitLoss() / 10000 - avgVal) * (shares.getProfitLoss() / 10000 - avgVal);
					bzcValue += val;
					num++;
				}
			}
			bzcValue = Math.sqrt(bzcValue / num);
		}
		return bzcValue;
	}
	
}
