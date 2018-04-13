package com.winterframework.modules.spring.console;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jUtil {
	public static String getRootLoggerLevel() {
		Logger root = Logger.getRootLogger();
		return root.getLevel().toString();
	}

	/**
	 * 设置Root Logger的日志级别.
	 */
	public static  void setRootLoggerLevel(String newLevel) {
		Logger root = Logger.getRootLogger();
		Level level = Level.toLevel(newLevel);
		root.setLevel(level);
	}

	/**
	 * 获取Logger的日志级别.
	 */
	public  static String getLoggerLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getEffectiveLevel().toString();
	}

	/**
	 * 设置Logger的日志级别.
	 */
	public  static void setLoggerLevel(String loggerName, String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
	}

	/**
	 * 获取Root Logger的所有Appender的名称.
	 */
	public static List<String> getRootLoggerAppenders() {
		Logger root = Logger.getRootLogger();
		return getLoggerAppenders(root);
	}

	/**
	 * 获取Logger的所有Appender的名称.
	 * 继承而来的Appender名称后将有(parent)标识.
	 */
	public  static List<String> getLoggerAppenders(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return getLoggerAppenders(logger);

	}

	/**
	 * 获取Logger的所有Appender的名称.
	 */
	@SuppressWarnings("unchecked")
	private  static List<String> getLoggerAppenders(Logger logger) {
		List<String> appenderNameList = new ArrayList<String>();
		//循环加载logger及其parent的appenders
		for (Category c = logger; c != null; c = c.getParent()) {
			Enumeration e = c.getAllAppenders();
			while (e.hasMoreElements()) {
				Appender appender = (Appender) e.nextElement();
				String appenderName = appender.getName();
				if (c != logger) {//NOSONAR
					appenderName += "(parent)";
				}
				appenderNameList.add(appenderName);
			}

			//如果logger不继承parent的appender,则停止向上循环.
			if (!c.getAdditivity()) {
				break;
			}
		}

		return appenderNameList;
	}

}
