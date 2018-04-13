package com.winterframework.modules.web.jsonresult;

public class RequestBody<T> {
	private T param;
	private Pager pager;
	
	public RequestBody(){
		this.pager=new Pager(1,10000);
	}
	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	

}
