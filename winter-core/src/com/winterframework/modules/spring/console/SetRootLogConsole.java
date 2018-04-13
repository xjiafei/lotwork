package com.winterframework.modules.spring.console;

import java.io.PrintWriter;


public class SetRootLogConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "setRootLog".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			Log4jUtil.setRootLoggerLevel(args[0]);
			out.println("success");
		} catch (Exception e) {
			out.println(e);
		}
		out.flush();
	}

	@Override
	public void help(PrintWriter out) {
		out.println( "setRootLog [debug|info|warn|error|fatal|off]");
	}

}
