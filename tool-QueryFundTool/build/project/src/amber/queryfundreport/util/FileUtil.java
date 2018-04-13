package amber.queryfundreport.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {
	
	private static LogUtil logger = new LogUtil(FileUtil.class);

	public static void writeFile(String fileName,List<String> contents){
		File file = new File("result\\"+fileName);
		logger.doLog("writeFile:"+fileName+",isExists:"+file.exists());
		BufferedWriter writer = null;
		try {
			if (file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));
			for (String str : contents) {
				writer.write(str);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.doLog(e);
		}
	}
	
}
