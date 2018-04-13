package amber.queryfundreport.util;

import java.io.Serializable;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class LogUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BLANK = "";
	public static final String LOG_BEGIN = " Start ";
	public static final String LOG_END = " Start Finished ";

	/**
	 * Log Level
	 */
	public enum LogLevel {
		DEBUG, INFO, ERROR
	}

	private Logger logger;

	private Class clazz;

	public LogUtil(Class clazz) {
		logger = Logger.getLogger(clazz);
		this.clazz = clazz;
	}

	/**
	 *
	 * @param message
	 */
	public void doLog(Object obj) {
		logger.info(obj);
	}

	public void doLog(Exception e) {
		logger.error(e);
	}

	/**
	 * 
	 * 
	 * @param message
	 * @param logLevel
	 */
	public void doLog(LogLevel logLevel, Object message) {
		switch (logLevel) {
		case DEBUG:
			if (logger.isDebugEnabled()) {
				logger.debug(message);
			}
			break;
		case INFO:
			logger.info(message);
			break;
		case ERROR:
			logger.error(message);
			break;
		}
	}

	public void doEndLog() {
		doLog("================" + clazz.getName() + LOG_END
				+ "================");
	}

	public void beginLog() {
		doLog("================" + clazz.getName() + LOG_BEGIN
				+ "================");
	}

}