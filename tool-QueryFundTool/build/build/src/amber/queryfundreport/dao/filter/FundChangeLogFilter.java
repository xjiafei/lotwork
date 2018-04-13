package amber.queryfundreport.dao.filter;

import java.util.List;

import amber.queryfundreport.enums.FundType;

public class FundChangeLogFilter {

	private String account;

	private String startDate;

	private String endDate;

	private List<FundType> types;

	private String orderby;

	public FundChangeLogFilter() {
		super();
	}

	public FundChangeLogFilter(String account, String startDate,
			String endDate, List<FundType> types) {
		super();
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setTypes(types);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public List<FundType> getTypes() {
		return types;
	}

	public void setTypes(List<FundType> types) {
		this.types = types;
	}

}
