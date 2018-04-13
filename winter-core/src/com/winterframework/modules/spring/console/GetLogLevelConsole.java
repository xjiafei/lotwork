package com.winterframework.modules.spring.console;

import java.io.PrintWriter;


public class GetLogLevelConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "getPkgLog".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			if (args.length > 0) {
				out.println("["+args[0]+"]' Log Level:"+Log4jUtil.getLoggerLevel(args[0]));
				
						} else {
				out.println("args error");
			}
		} catch (Exception e) {
			out.println(e);
		}
		out.flush();
	}

	@Override
	public void help(PrintWriter out) {
		out.println( "getPkgLog path");
	}

}
