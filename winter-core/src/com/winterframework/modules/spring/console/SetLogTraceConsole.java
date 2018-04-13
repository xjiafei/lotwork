package com.winterframework.modules.spring.console;

import java.io.PrintWriter;


public class SetLogTraceConsole implements Consoleable {
	@Override
	public boolean supportCommand(String command) {
		return "setLogTrace".equals(command);
	}

	@Override
	public void response(String[] args, PrintWriter out) {
		try {
			if (args.length > 0) {
				if ("on".equals(args[0])) {
					TraceUtils.beginTrace();
					out.println(" begin trrace success");
				}else  {
					TraceUtils.endTrace();
					out.println("end trace success");
				}
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
		out.println("setLogTrace [on|off]");
	}

}
