package amber.genbatis.core;

import java.util.List;

import amber.genbatis.vo.TableColumnVO;

public interface IGenFIleCmd {
	
	public String genFileName(String tableName);

	public List<String> genContents(String tableName,String packageName, List<TableColumnVO> columns)throws Exception;
	
}
