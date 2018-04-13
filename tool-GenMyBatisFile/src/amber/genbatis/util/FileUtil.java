package amber.genbatis.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {
	
	private static LogUtil logger = new LogUtil(FileUtil.class);

	public static void writeFile(String path,String fileName,List<String> contents){
		String filePath = "result\\"+path+"\\";
		File file = new File(filePath+fileName);
		logger.doLog("writeFile:"+fileName+",isExists:"+file.exists());
		BufferedWriter writer = null;
		try {
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
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
