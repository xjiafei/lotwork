package amber.genbatis.core;

import amber.genbatis.vo.TableColumnVO;

public class MybatisConverter {
	

	public static String typeConvert(String dbType, Integer size, Integer scale) {
		String javaType = "";
		if (dbType.equals("VARCHAR2") || dbType.equals("NVARCHAR2")) {
			javaType = "String";
		} else if (dbType.equals("CHAR")) {
			javaType = "String";
		} else if (dbType.equals("DATE") || dbType.startsWith("TIMESTAMP")) {
			javaType = "Date";
		} else if (dbType.equals("NUMBER")) {
			if (size != null) {
				if (scale != null && scale > 0) {
					javaType = "Long";
				} else if (size > 10) {
					javaType = "Long";
				} else {
					javaType = "Long";
				}
			}
		} else if (dbType.equals("FLOAT")) {
			javaType = "Double";
		}
		return javaType;
	}

	public static StringBuffer getInsertType(TableColumnVO column) {
		String name = column.getName();
		String type = column.getType();
		Integer size = column.getSize();
		Integer scale = column.getScale();
		StringBuffer sb = new StringBuffer();
		sb.append("	         #{" + getJavaName(name, "var"))
				.append(",javaType=")
				.append(getMyBatisType("java", type, size, scale));
		sb.append(",jdbcType=")
				.append(getMyBatisType("jdbc", type, size, scale)).append("}");
		return sb;
	}

	public static String getMyBatisType(String ibatisType, String dbtype,
			Integer size, Integer sclae) {
		String targetType = "";
		if ("java".equals(ibatisType)) {
			if (dbtype.equals("VARCHAR2") || dbtype.equals("NVARCHAR2")
					|| dbtype.equals("CHAR")) {
				targetType = "string";
			} else if (dbtype.equals("DATE") || dbtype.startsWith("TIMESTAMP")) {
				targetType = "object";
			} else if (dbtype.equals("NUMBER")) {
				if (sclae == 0) {
					targetType = "long";
				} else {
					targetType = "long";
				}
			}
		} else {
			if (dbtype.equals("VARCHAR2") || dbtype.equals("NVARCHAR2")
					|| dbtype.equals("CHAR")) {
				targetType = "VARCHAR";
			} else if (dbtype.equals("DATE") || dbtype.startsWith("TIMESTAMP")) {
				targetType = "TIMESTAMP";
			} else if (dbtype.equals("NUMBER")) {
				if (sclae == 0) {
					targetType = "DECIMAL";
				} else {
					targetType = "DECIMAL";
				}
			}
		}

		return targetType;
	}

	public static String getJavaName(String columnName, String attr) {
		String[] mapperNameTokens = columnName.toLowerCase().split("_");
		String mapperName = "";
		int i = 0;
		for (String token : mapperNameTokens) {
			i++;
			if ("class".equals(attr)) {
				mapperName += token.substring(0, 1).toUpperCase()
						+ token.substring(1);
			} else {
				if (i == 1) {
					mapperName += token.substring(0, 1) + token.substring(1);
				} else {
					mapperName += token.substring(0, 1).toUpperCase()
							+ token.substring(1);
				}
			}
		}
		return mapperName;
	}
	
	public static String getJavaAttribute(TableColumnVO column) {
		String name = column.getName();
		String dbType = column.getType();
		Integer size = column.getSize(); 
		Integer scale = column.getScale();
		String attribute = "";
		attribute += "\tprivate " + typeConvert(dbType, size, scale) + " ";
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
		attribute += columnName + ";";
		return attribute;
	}
}
