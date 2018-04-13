package com.winterframework.firefrog.sample.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.exception.ServerException;
import com.winterframework.firefrog.common.exception.ValidExcetion;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

public class DefaultController {
	/**
	 * client exception
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(ValidExcetion.class)
	@ResponseBody
	public Object handleValidExceptionException(Exception e, HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return createResp(request, 903);
	}

	/**
	 * server exception
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 */
	@ExceptionHandler(ServerException.class)
	@ResponseBody
	public Object handleServerExceptionException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		logger.error(e.getMessage(), e);
		return createResp(request, 901);
	}

	@SuppressWarnings("rawtypes")
	private Response createResp(HttpServletRequest request, int errorCode) {
		try {
			String tt = IOUtils.toString(request.getInputStream());
			Request req = JsonMapper.nonEmptyMapper().fromJson(tt, Request.class);
			Response res = new Response(req);
			res.getHead().setStatus(errorCode);
			return res;
		} catch (IOException e1) {
			return null;
		}
	}
}
