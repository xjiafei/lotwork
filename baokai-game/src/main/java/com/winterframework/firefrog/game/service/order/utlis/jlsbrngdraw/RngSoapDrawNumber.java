package com.winterframework.firefrog.game.service.order.utlis.jlsbrngdraw;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.tempuri.RngSoap;

/**
 * Soap開獎中心取號
 * @author Ami.Tsai
 *
 */
@org.springframework.stereotype.Service("rngSoapDrawNumber")
public class RngSoapDrawNumber implements RngDrawNumber {

	@Override
	public String getDrawNumber(String param, String url, String nameSpace, String serviceName) throws Exception {
		  URL rngUrl = new URL(url);
		  QName qname = new QName(nameSpace,serviceName);
		  Service service = Service.create(rngUrl,qname);
		  RngSoap rngSoap = service.getPort(RngSoap.class);
		  String resutlNumber = rngSoap.grabRandomNumber(param);
		  return resutlNumber;
	}


}
