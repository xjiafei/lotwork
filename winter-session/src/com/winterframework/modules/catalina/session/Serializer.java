package com.winterframework.modules.catalina.session;

import java.io.IOException;

import javax.servlet.http.HttpSession;

public interface Serializer {
  void setClassLoader(ClassLoader loader);

  byte[] serializeFrom(HttpSession session) throws IOException;

  HttpSession deserializeInto(byte[] data, HttpSession session) throws IOException, ClassNotFoundException;
}
