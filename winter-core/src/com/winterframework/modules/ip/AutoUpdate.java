package com.winterframework.modules.ip;


// Referenced classes of package common.util.ip:
//            IPSeeker

public class AutoUpdate {

	/*	public AutoUpdate() {
			ipFile = "/opt/resin-pro-3.0.14/webapps/ip/WEB-INF/classes/QQWry.Dat";
			ipLibFile = "http://shdev.pconline.com.cn/qqwry.rar";
			logFile = "/opt/resin-pro-3.0.14/webapps/ip/update.log";
			updateDir = "/opt/resin-pro-3.0.14/webapps/ip/update";
			unRarCmd = "unrar";
		}

		public void doUpdate() {
			StringBuffer logMessage = new StringBuffer();
			String date = (new SimpleDateFormat("yyyyMMddHHmm")).format(new Date());
			logMessage.append("\u5F00\u59CBIP\u5E93\u66F4\u65B0.\u66F4\u65B0\u76EE\u5F55:" + date
					+ "\u66F4\u65B0\u65F6\u95F4" + new Date() + "\n");
			try {
				URL url = new URL(getIpLibFile());
				HttpURLConnection connect = (HttpURLConnection) url.openConnection();
				InputStream is = connect.getInputStream();
				File file = new File(getUpdateDir());
				if (!file.exists())
					file.mkdirs();
				String updateFile = getUpdateDir() + "/" + date + ".rar";
				FileOutputStream outStream = new FileOutputStream(updateFile);
				int readLength = 1024;
				long length = 0L;
				byte buff[] = new byte[readLength];
				for (int rc = 0; (rc = is.read(buff, 0, readLength)) > 0;) {
					outStream.write(buff, 0, rc);
					length += rc;
				}

				outStream.close();
				is.close();
				connect.disconnect();
				logMessage.append("\u4E0B\u8F7D\u5B8C\u6210\uFF01\u6587\u4EF6\u4FDD\u5B58\u4E3A:" + updateFile
						+ "\u5927\u5C0F:" + length / 1000L + "K \n");
				logMessage.append("\u5F00\u59CB\u89E3\u538B....\n");
				runCmd(new String[] { getUnRarCmd(), "x", "-o+", "-p-", updateFile, getUpdateDir() + "/" });
				logMessage.append("\u89E3\u538B\u5B8C\u6210,\u51C6\u5907\u66F4\u65B0...\n");
				File nowIp = new File(getIpFile());
				File updateIp = new File(getUpdateDir() + "/QQWry.Dat");
				if (updateIp.exists() && nowIp.exists()) {
					logMessage
							.append("\u5224\u65AD\u5E93\u662F\u5426\u6709\u66F4\u65B0\uFF0C\u5F53\u524D\u5E93\u5927\u5C0F:"
									+ nowIp.length() + "\u6700\u540E\u65F6\u95F4:" + nowIp.lastModified()
									+ " \u66F4\u65B0\u5E93\u5927\u5C0F:" + updateIp.length() + "\u6700\u540E\u65F6\u95F4:"
									+ updateIp.lastModified() + "\n");
					if (updateIp.length() > nowIp.length()) {
						logMessage.append("\u5E93\u6709\u66F4\u65B0\uFF0C\u8986\u76D6\u539F\u6709IP\u5E93\n");
						copyFile(getUpdateDir() + "/QQWry.Dat", getIpFile());
						logMessage
								.append("\u66F4\u65B0\u6210\u529F\uFF01\u901A\u77E5\u5E94\u7528\u542F\u7528\u65B0\u7684IP\u5E93\u3002\n");
						IPSeeker.getInstance().load();
						logMessage
								.append("\u91CD\u65B0\u52A0\u8F7D\u6210\u529F\uFF01\u5DF2\u7ECF\u542F\u7528\u65B0\u7684IP\u5E93\u3002\n");
					}
				} else {
					logMessage
							.append("\u4E0D\u7B26\u5408\u66F4\u65B0\u6761\u4EF6\uFF01\u66F4\u65B0\u5E93\u4E0D\u5B58\u5728\uFF01");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logMessage.append("\u66F4\u65B0\u53D1\u751F\u5F02\u5E38:" + e.getMessage() + "\n");
			}
			FileWriter log = null;
			try {
				log = new FileWriter(getLogFile(), true);
				log.write(logMessage.toString());
				log.flush();
				log.close();
			} catch (Exception e) {
				System.out.println("\u5199\u65E5\u5FD7\u53D1\u751F\u5F02\u5E38");
				e.printStackTrace();
				System.out.println(logMessage.toString());
			} finally {
				try {
					if (log != null)
						log.close();
				} catch (Exception e) {
				}
			}
		}

		public boolean copyFile(String org, String dest) throws Exception {
			OutputStream outStream = null;
			InputStream inStream = null;
			try {
				boolean flag;
				try {
					outStream = new FileOutputStream(dest);
					inStream = new FileInputStream(org);
					byte buffer[] = new byte[8192];
					int bytesRead;
					while ((bytesRead = inStream.read(buffer)) != -1)
						outStream.write(buffer, 0, bytesRead);
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return flag;
			} finally {
				try {
					if (inStream != null)
						inStream.close();
					if (inStream != null)
						outStream.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}

		private String runCmd(String cmd[]) throws Exception {
			System.out.println("Run Command\uFF1A" + ReflectionToStringBuilder.toString(cmd));
			StringBuffer outBuffer = new StringBuffer();
			Process p = null;
			try {
				p = Runtime.getRuntime().exec(cmd);
				InputStream out = p.getInputStream();
				int i;
				while ((i = out.read()) != -1)
					outBuffer.append((char) i);
				if (p.exitValue() != 0)
					throw new RuntimeException(
							"\u56FE\u7247\u8FDB\u64CD\u4F5C\u51FA\u9519,\u53C2\u6570\u5904\u7406\u51FA\u9519\uFF01");
			} catch (Exception ex) {
				outBuffer.append(ex.getMessage());
				throw new RuntimeException(
						"\u547D\u4EE4\u8C03\u7528\u51FA\u9519\uFF01\u64CD\u4F5C\u6587\u4EF6\u8DEF\u5F84\u4E0D\u5B58\u5728\uFF0C\u914D\u5236\u53C2\u6570\u9519\u8BEF!");
			} finally {
				if (p != null)
					p.destroy();
				return outBuffer.toString();
			}
		}

		public static void main(String args[]) {
			AutoUpdate test = new AutoUpdate();
			test.doUpdate();
		}

		public String getIpFile() {
			return ipFile;
		}

		public void setIpFile(String ipFile) {
			this.ipFile = ipFile;
		}

		public String getIpLibFile() {
			return ipLibFile;
		}

		public void setIpLibFile(String ipLibFile) {
			this.ipLibFile = ipLibFile;
		}

		public String getLogFile() {
			return logFile;
		}

		public void setLogFile(String logFile) {
			this.logFile = logFile;
		}

		public String getUpdateDir() {
			return updateDir;
		}

		public void setUpdateDir(String updateDir) {
			this.updateDir = updateDir;
		}

		public String getUnRarCmd() {
			return unRarCmd;
		}

		public void setUnRarCmd(String unRarCmd) {
			this.unRarCmd = unRarCmd;
		}

		private String ipFile;
		private String ipLibFile;
		private String logFile;
		private String updateDir;
		private String unRarCmd;*/
}
