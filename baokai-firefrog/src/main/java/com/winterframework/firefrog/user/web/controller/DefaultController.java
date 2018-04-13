package com.winterframework.firefrog.user.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winterframework.firefrog.common.exception.UnknownResourceException;

/**
 * Default controller that exists to return a proper REST response for unmapped requests.
 */
@Controller
public class DefaultController {

	@RequestMapping("/**")
	public void unmappedRequest(HttpServletRequest request) throws Exception {
		String uri = request.getRequestURI();
		throw new UnknownResourceException("There is no resource for path " + uri);
	}
}
