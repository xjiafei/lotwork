package com.winterframework.modules.spring.console;

import java.io.PrintWriter;

public interface Consoleable {
  boolean supportCommand(String command);
  void response(String[] args, PrintWriter out);
  void help(PrintWriter out);
}
