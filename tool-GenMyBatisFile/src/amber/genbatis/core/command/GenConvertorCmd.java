package amber.genbatis.core.command;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.core.IGenFIleCmd;
import amber.genbatis.core.MybatisConverter;
import amber.genbatis.vo.TableColumnVO;

public class GenConvertorCmd implements IGenFIleCmd{
	
	@Override
	public String genFileName(String tableName) {
		String javaName = MybatisConverter.getJavaName(tableName, "class");
		String fileName = javaName + "Convertor.java";
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
		contents.add("package " + packageName + ".dao.entity;\n");
		if (hasDate) {
			contents.add("import java.util.Date;\n");
		}
		contents.add("import "+packageName+"."+javaName+";\n");
		contents.add("import "+packageName+"."+javaName+"Vo;\n");
		contents.add("public class " + javaName + "Convertor{\n");
		contents.addAll(genConvertToVoMethod(javaName, columns));
		contents.add("\n");
		contents.addAll(genConvertToEntityMethod(javaName, columns));
		contents.add("}");
		return contents;
	}
	
	private List<String> genConvertToVoMethod(String javaName,List<TableColumnVO> columns){
		List<String> contents = new ArrayList<String>();
		contents.add("\tpublic " + javaName + "Vo convert"+javaName+"ToVo("+javaName+" entity){\n");
		contents.add("\t\t" + javaName + "Vo vo = new "+ javaName + "Vo();");
		for (TableColumnVO column : columns) {
			String methodName = convertMethodName(column.getName());
			contents.add("\t\tvo.set"+methodName+"(entity.get"+methodName+"());");
		}
		contents.add("\t\treturn vo;");
		contents.add("\t}");
		return contents;
	}
	
	private List<String> genConvertToEntityMethod(String javaName,List<TableColumnVO> columns){
		List<String> contents = new ArrayList<String>();
		contents.add("\tpublic " + javaName + " convert"+javaName+"ToEntity("+javaName+"Vo vo){\n");
		contents.add("\t\t" + javaName + " entity = new "+ javaName + "();");
		for (TableColumnVO column : columns) {
			String methodName = convertMethodName(column.getName());
			contents.add("\t\tentity.set"+methodName+"(vo.get"+methodName+"());");
		}
		contents.add("\t\treturn entity;");
		contents.add("\t}");
		return contents;
	}
	
	private String convertMethodName(String name){
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
		return columnName.replaceFirst("(.)", columnName.substring(0, 1)
				.toUpperCase());
	}


}
