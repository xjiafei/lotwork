package com.winterframework.modules.utils;

import java.util.Map;

/**
* 使用 Java 远程代码生成 ThreadDump. 适用于 JDK 1.5+.
* @author abba
*/
public class ThreadDumpBuilder {
	/**
	 * 生成并返回 Thread Dump.
	 * @return
	 */
	public String build() {
		StringBuilder output = new StringBuilder(1000);
		for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
			appendThreadStackTrace(output, (Thread) stackTrace.getKey(), (StackTraceElement[]) stackTrace.getValue());
		}
		return output.toString();
	}

	/**
	 * 处理并输出堆栈信息.
	 * @param output
	 *            输出内容
	 * @param thread
	 *            线程
	 * @param stack
	 *            线程堆栈
	 */
	private void appendThreadStackTrace(StringBuilder output, Thread thread, StackTraceElement[] stack) {
		// 忽略当前线程的堆栈信息
		if (thread.equals(Thread.currentThread())) {
			return;
		}

		output.append(getFullName(thread)).append("\n<br/>");
		for (StackTraceElement element : stack) {
			output.append("&nbsp;&nbsp;&nbsp;&nbsp; ").append(element).append("\n<br/>");
		}
	}

	public String getFullName(Thread thread) {
		String str = "'";
		str += thread.getName();
		str += "'";
		str += "&nbsp;id=" + thread.getId();
		str += "&nbsp;group=" + thread.getThreadGroup().getName();
		str += "&nbsp;prio=" + thread.getPriority();
		str += "&nbsp;alive=" + thread.isAlive();
		str += "&nbsp;daemon=" + thread.isDaemon();
		str += "&nbsp;state=" + thread.getState();
		return str;
	}

}