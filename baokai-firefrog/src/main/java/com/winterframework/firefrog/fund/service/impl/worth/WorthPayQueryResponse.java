package com.winterframework.firefrog.fund.service.impl.worth;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ebank")
public class WorthPayQueryResponse implements Serializable{
	/**交易是否成功*/
	@XmlElement(name = "is_success")
	private String is_success;
	/**错误码*/
	@XmlElement(name = "result_code")
	private String result_code;
	/**时间戳*/
	@XmlElement(name = "timestamp")
	private String timestamp;
	/**業務參數*/
	@XmlElement(name = "trade")
	private WorthPayQueryResponseDetail worthPayQueryResponseDetail;	
	
	/**
	 * 取得交易是否成功。
	 * @return is_success
	 */
	public String getIs_success() {
		return is_success;
	}
	/**
	 * 取得错误码。
	 * @return result_code
	 */
	public String getResult_code() {
		return result_code;
	}
	/**
	 * 取得时间戳。
	 * @return timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * 取得業務參數。
	 * @return timestamp
	 */
	public WorthPayQueryResponseDetail getWorthPayQueryResponseDetail(){
		return worthPayQueryResponseDetail;
	}

}
