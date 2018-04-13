package com.winterframework.modules.spring.console;

import java.io.PrintWriter;
import java.util.List;

public class GetLogsConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "getLogs".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			out.print("[" + args[0] + "]' Log Level:");
			List<String> strs = Log4jUtil.getRootLoggerAppenders();
			for (String str : strs) {
				out.println(str);
			}
		} catch (Exception e) {
			out.println(e);
		}
		out.flush();
	}

	@Override
	public void help(PrintWriter out) {
		out.println("getLogs");
	}

}
