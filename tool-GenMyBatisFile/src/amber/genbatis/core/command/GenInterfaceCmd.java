package amber.genbatis.core.command;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.core.IGenFIleCmd;
import amber.genbatis.core.MybatisConverter;
import amber.genbatis.vo.TableColumnVO;

public class GenInterfaceCmd implements IGenFIleCmd{
	
	@Override
	public String genFileName(String tableName) {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		String fileName = "I" + javaName + "Dao.java";
		return fileName;
	}

	@Override
	public List<String> genContents(String tableName, String packageName,
			List<TableColumnVO> columns) throws Exception {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		List<String> contents = new ArrayList<String>();
		String content = initContent();
		content = content.replace("[[packageName]]",packageName+".dao");
		content = content.replace("[[javaName]]",javaName);
		contents.add(content);
		return contents;
	}

	private String initContent() {
		StringBuffer contents = new StringBuffer();
		contents.append("package [[packageName]];\n");
		contents.append("\n");
		contents.append("public interface I[[javaName]]Dao {\n");
		contents.append("\n");
		contents.append("}\n");
		return contents.toString();
	}

}
