package com.winterframework.modules.web.jsonresult;

public class  Request<T>{
	public RequestHeader head;
	public RequestBody<T> body;
	public RequestHeader getHead() {
		return head;
	}
	public void setHead(RequestHeader head) {
		this.head = head;
	}
	public RequestBody<T> getBody() {
		return body;
	}
	public void setBody(RequestBody<T> body) {
		this.body = body;
	}
		

}
