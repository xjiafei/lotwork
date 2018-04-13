package amber.genbatis.core.command;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.core.IGenFIleCmd;
import amber.genbatis.core.MybatisConverter;
import amber.genbatis.vo.TableColumnVO;

public class GenDaoCmd implements IGenFIleCmd {

	@Override
	public String genFileName(String tableName) {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		String fileName = javaName + "DaoImpl.java";
		return fileName;
	}

	@Override
	public List<String> genContents(String tableName, String packageName,
			List<TableColumnVO> columns) throws Exception {

		String javaName = MybatisConverter.getJavaName(tableName, "class");
		String repositoryName = javaName.replaceFirst("(.)", javaName.substring(0, 1).toLowerCase());
		List<String> contents = new ArrayList<String>();
		String content = initContent();
		content = content.replace("[[packageName]]", packageName+".dao");
		content = content.replace("[[javaName]]", javaName);
		content = content.replace("[[repositoryName]]", repositoryName);
		contents.add(content);

		return contents;
	}

	private String initContent() {
		StringBuffer contents = new StringBuffer();
		contents.append("package [[packageName]].impl;\n");
		contents.append("\n");
		contents.append("import org.springframework.stereotype.Repository;\n");
		contents.append("import [[packageName]].I[[javaName]]Dao;\n");
		contents.append("import [[packageName]].entity.[[javaName]];\n");
		contents.append("import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;\n");
		contents.append("\n");
		contents.append("@Repository(\"[[repositoryName]]DaoImpl\")\n");
		contents.append("public class [[javaName]]DaoImpl extends BaseIbatis3Dao<[[javaName]]> implements I[[javaName]]Dao {\n");
		contents.append("\n");
		contents.append("}\n");
		return contents.toString();
	}

}
