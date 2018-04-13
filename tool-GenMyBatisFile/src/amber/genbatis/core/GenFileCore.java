package amber.genbatis.core;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.dao.GetTableDao;
import amber.genbatis.util.FileUtil;
import amber.genbatis.util.LogUtil;
import amber.genbatis.vo.TableColumnVO;

public class GenFileCore {
	
	protected LogUtil logger = new LogUtil(getClass());

	private String tableName;

	private String packageName;
	
	private GetTableDao tableDao;
	
	private List<TableColumnVO> columns;
	
	private List<IGenFIleCmd> cmds; 
	
	public GenFileCore(String tableName,String packageName) throws Exception{
		this.tableName= tableName;
		this.packageName = packageName;
		this.cmds = new ArrayList<IGenFIleCmd>();
		this.tableDao = new GetTableDao(); 
		columns = tableDao.queryTableAttributes(tableName);
	}
	
	public void addCmd(IGenFIleCmd cmd){
		this.cmds.add(cmd);
	}
	
	public void executeGenFile(){
		for(IGenFIleCmd cmd:cmds){
			try {
				List<String> contents = cmd.genContents(tableName,packageName,columns);
				String fileName = cmd.genFileName(tableName);
				FileUtil.writeFile(cmd.getClass().getSimpleName(),fileName, contents);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
