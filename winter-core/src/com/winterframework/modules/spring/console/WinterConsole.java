package com.winterframework.modules.spring.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service
public class WinterConsole {
	private static AtomicBoolean canRun = new AtomicBoolean(Boolean.TRUE);
	@PropertyConfig(required = false)
	private String consolePort;
	private static List<Consoleable> consoles = new ArrayList<Consoleable>();
	static {
		WinterConsole.addConsoles(new SetRootLogConsole());
		WinterConsole.addConsoles(new GetRootLogConsole());
		WinterConsole.addConsoles(new SetLogLevelConsole());
		WinterConsole.addConsoles(new GetLogLevelConsole());
		WinterConsole.addConsoles(new SetLogTraceConsole());
		WinterConsole.addConsoles(new GetLogsConsole());
	}
	private static Logger log = Logger.getLogger(WinterConsole.class);
	private static Command command;

	public static void addConsoles(Consoleable console) {
		consoles.add(console);
	}

	@PostConstruct
	public void init() {
		canRun.set(Boolean.TRUE);
		command = new Command(consolePort);
		if (consolePort != null) {
			new Thread(command).start();

			log.info("Winter command console started,listen on " + consolePort == null ? 12345
					: Integer.valueOf(consolePort));
		}
	}

	@PreDestroy
	public void destroy() throws IOException {
		canRun.set(Boolean.FALSE);
		log.info("Winter command console shutdown");
	}

	private static class Command implements Runnable {
		private ServerSocket ss = null;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		private String port;

		public Command(String consolePort) {
			this.port = consolePort;
		}

		public void run() {

			try {
				ss = new ServerSocket(port == null ? 12345
						: Integer.valueOf(port));
				while (canRun.get()) {
					try {
						socket = ss.accept();
						in = new BufferedReader(new InputStreamReader(
								socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream(), true);
						out.println("*************Welcome to Winter command console*********");
						out.println("support commands>>");
						for (Consoleable c : consoles) {
							c.help(out);
						}
						out.println();
						out.flush();
						String line = "";
						while ((line = in.readLine()) != null) {
							log.debug("Console get command:" + line);
							if (line.trim().equals(""))
								continue;
							String[] args = line.split(" ");
							if (args[0].equalsIgnoreCase("quit")) {
								break;
							} else if (args[0].equalsIgnoreCase("help")) {
								if (args.length > 1) {
									for (Consoleable c : consoles) {
										if (c.supportCommand(args[1])) {
											c.help(out);
										}
									}
								}

							} else {
								tradeCommand(line, out);
							}
							out.flush();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (out != null)
							out.close();
						if (in != null)
							in.close();
						if (socket != null)
							socket.close();

					}
				}
			} catch (IOException e) {
			}
		}

		private void tradeCommand(String request, PrintWriter out) {
			String[] args = request.trim().split(" ", -1);
			if (args.length == 0) {
				return;
			} else {
				boolean getCommand = false;
				for (Consoleable c : consoles) {
					if (c.supportCommand(args[0])) {
						getCommand = true;
						String[] bb = new String[args.length - 1];
						if (args.length > 1) {
							System.arraycopy(args, 1, bb, 0, bb.length);
						}
						c.response(bb, out);

					}
				}
				if (!getCommand) {
					out.println("not supported command");
				}
			}
		}
	}

}
