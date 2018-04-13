package amber.genbatis;

import amber.genbatis.core.GenFileCore;
import amber.genbatis.core.command.GenConvertorCmd;
import amber.genbatis.core.command.GenDaoCmd;
import amber.genbatis.core.command.GenEntityCmd;
import amber.genbatis.core.command.GenInterfaceCmd;
import amber.genbatis.core.command.GenMapperCmd;
import amber.genbatis.core.command.GenVoCmd;
import amber.genbatis.util.LogUtil;

public class Main {
	
	LogUtil logger = new LogUtil(Main.class);
	
	public static void main(String[] args) throws Exception{
		String packageName = "com.winterframework.webim.im";
		genFile("IM_USER",packageName);
		genFile("IM_GROUP",packageName);
		genFile("IM_GROUP_USER",packageName);
		genFile("IM_GROUP_MESSAGE",packageName);
	}
	
	public static void genFile(String tableName,String packageName)throws Exception{
		GenFileCore core = new GenFileCore(tableName, packageName);
		core.addCmd(new GenDaoCmd());
		core.addCmd(new GenInterfaceCmd());
		core.addCmd(new GenMapperCmd());
		core.addCmd(new GenVoCmd());
		core.addCmd(new GenConvertorCmd());
		core.addCmd(new GenEntityCmd());
		core.executeGenFile();
	}

}
