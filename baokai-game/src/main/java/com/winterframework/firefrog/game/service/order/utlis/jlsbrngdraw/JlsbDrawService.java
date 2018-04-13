package com.winterframework.firefrog.game.service.order.utlis.jlsbrngdraw;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("jlsbDrawService")
public class JlsbDrawService extends AbstractJlsbDrawService {
	
	@PropertyConfig(value = "jlsb.url")
	private String url;

	@PropertyConfig(value = "jlsb.service")
	private String service;
	
	@PropertyConfig(value = "jlsb.namespace")
	private String namespace;
	
	@Resource(name="rngSoapDrawNumber")
	private RngDrawNumber rngSoapDrawNumber;
	
	@Override
	protected String parseResult(String result) {
		String numb = result.replace("number=", "").replaceAll(",", "");
		if (numb.length() != 3) {
			return null;
		} else {
			return numb;
		}
	}
	
	@Override
	protected String getParam(String lotName, String issue, String time) {
		return "lot_name="+lotName+"&issue="+issue+"&time="+time;
	}


	@Override
	protected String doRngGetNumber(String param) throws Exception{
		return rngSoapDrawNumber.getDrawNumber(param, url, namespace, service);
	}
	
}
