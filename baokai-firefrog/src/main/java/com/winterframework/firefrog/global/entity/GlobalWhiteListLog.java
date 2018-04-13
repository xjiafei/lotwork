package com.winterframework.firefrog.global.entity;

import java.util.Date;

public class GlobalWhiteListLog {

	//columns START
		private Long id;
		private String whiteListIp;
		private String acunt;
		private String cuntry;
		private String operator;
		private Date operationTime;
		private String status;
		private int pageSize;
		//columns END
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getWhiteListIp() {
			return whiteListIp;
		}
		public void setWhiteListIp(String whiteListIp) {
			this.whiteListIp = whiteListIp;
		}
		public String getAcunt() {
			return acunt;
		}
		public void setAcunt(String acunt) {
			this.acunt = acunt;
		}
		public String getCuntry() {
			return cuntry;
		}
		public void setCuntry(String cuntry) {
			this.cuntry = cuntry;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public Date getOperationTime() {
			return operationTime;
		}
		public void setOperationTime(Date operationTime) {
			this.operationTime = operationTime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
	
}
