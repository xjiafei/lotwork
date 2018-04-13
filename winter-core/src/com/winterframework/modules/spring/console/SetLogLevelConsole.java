package com.winterframework.modules.spring.console;

import java.io.PrintWriter;


public class SetLogLevelConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "setPkgLog".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			if (args.length > 1) {
				Log4jUtil.setLoggerLevel(args[0], args[1]);
				out.println("success");
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
		out.println("setPkgLog path [debug|info|warn|error|fatal|off]");
	}

}
