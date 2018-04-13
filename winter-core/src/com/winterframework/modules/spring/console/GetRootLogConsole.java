package com.winterframework.modules.spring.console;

import java.io.PrintWriter;


public class GetRootLogConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "getRootLog".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			out.println("root level:"+Log4jUtil.getRootLoggerLevel());
		} catch (Exception e) {
			out.println(e);
		}
	}

	@Override
	public void help(PrintWriter out) {
		out.println("getRootLog");
	}

}
