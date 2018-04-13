package amber.genbatis.core.command;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.core.IGenFIleCmd;
import amber.genbatis.core.MybatisConverter;
import amber.genbatis.vo.TableColumnVO;

public class GenVoCmd implements IGenFIleCmd{
	
	@Override
	public String genFileName(String tableName) {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		String fileName = javaName + "Vo.java";
		return fileName;
	}

	@Override
	public List<String> genContents(String tableName, String packageName,
			List<TableColumnVO> columns) throws Exception {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		List<String> contents = new ArrayList<String>();

		boolean hasDate = false;
		
		for (TableColumnVO column : columns) {
			if(column.isDate()){
				hasDate = true;
				break;
			}
		}
		contents.add("package " + packageName + ".vo;\n");
		contents.add("import java.io.Serializable;");
		if (hasDate) {
			contents.add("import java.util.Date;\n");
		}
		contents.add("public class " + javaName + "Vo implements Serializable{\n");
		contents.add("\tprivate static final long serialVersionUID = 1L;\n");
		for (TableColumnVO column : columns) {
			contents.add(MybatisConverter.getJavaAttribute(column));
			contents.add("");
		}
		for (TableColumnVO column : columns) {
			contents.add(getJavaSetter(column));
			contents.add(getJavaGetter(column));
		}
		contents.add("}");
		return contents;
	}

	private String getJavaGetter(TableColumnVO column) {
		String name = column.getName();
		String dbType = column.getType();
		Integer size = column.getSize(); 
		Integer scale = column.getScale();
		String attribute = "";
		attribute += "\tpublic " + MybatisConverter.typeConvert(dbType, size, scale) + " ";
		String[] tokens = name.toLowerCase().split("_");
		String columnName = "";
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (i == 0) {
				columnName += token;
			} else {
				columnName += token.substring(0, 1).toUpperCase()
						+ token.substring(1);
			}

		}
		attribute += "get"
				+ columnName.replaceFirst("(.)", columnName.substring(0, 1)
						.toUpperCase()) + "(){\n";
		attribute += "\t\treturn this." + columnName + ";\n";
		attribute += "\t}\n";
		return attribute;
	}

	private String getJavaSetter(TableColumnVO column) {
		String name = column.getName();
		String dbType = column.getType();
		Integer size = column.getSize(); 
		Integer scale = column.getScale();
		String attribute = "";
		attribute += "\tpublic void ";
		String[] tokens = name.toLowerCase().split("_");
		String columnName = "";
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (i == 0) {
				columnName += token;
			} else {
				columnName += token.substring(0, 1).toUpperCase()
						+ token.substring(1);
			}

		}
		attribute += "set"
				+ columnName.replaceFirst("(.)", columnName.substring(0, 1)
						.toUpperCase()) + "("
				+ MybatisConverter.typeConvert(dbType, size, scale) + " " + columnName + "){\n";
		attribute += "\t\t this." + columnName + "= " + columnName + ";\n";
		attribute += "\t}\n";
		return attribute;
	}


}
