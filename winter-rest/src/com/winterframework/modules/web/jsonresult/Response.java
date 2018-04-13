package com.winterframework.modules.web.jsonresult;

import com.winterframework.modules.page.Page;

public class Response<T> implements java.io.Serializable {
	private ResponseHeader head;
	private ResponseBody<T> body;

	public Response() {

	}

	public Response(Request req) {
		this.head = ResponseHeader.createReponseHeader(req.getHead());
		this.body = new ResponseBody();
		if (req.getBody() != null) {
			if (req.getBody().getPager() != null) {
				this.setResultPage(new ResultPager(0, 0, 0));
			}
		}

	}

	public ResponseHeader getHead() {
		return head;
	}

	public void setHead(ResponseHeader head) {
		this.head = head;
	}

	public void setResult(T result) {
		if (body == null) {
			ResponseBody<T> body = new ResponseBody<T>();
			body.setResult(result);
			this.body = body;
		} else {
			body.setResult(result);
		}

	}

	/**
	 * 当设置page的时候，往往result都是List<T>
	 * @param result
	 */
	public void setPageResult(Page result) {
		body.setResult((T) result.getResult());
		ResultPager resultPager = new ResultPager(result.getThisPageFirstElementNumber(),
				result.getThisPageLastElementNumber(), result.getTotalCount());
		if (result.getOtherCount() != null && result.getOtherCount().size()>0)
			resultPager.setOtherMap(result.getOtherCount());
		setResultPage(resultPager);

	}

	public void setResultPage(ResultPager pager) {
		this.body.setPager(pager);
	}

	public ResponseBody<T> getBody() {
		return body;
	}

}
