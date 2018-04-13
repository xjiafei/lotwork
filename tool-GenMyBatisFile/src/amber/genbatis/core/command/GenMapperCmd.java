package amber.genbatis.core.command;

import java.util.ArrayList;
import java.util.List;

import amber.genbatis.core.IGenFIleCmd;
import amber.genbatis.core.MybatisConverter;
import amber.genbatis.vo.TableColumnVO;

public class GenMapperCmd implements IGenFIleCmd{

	@Override
	public String genFileName(String tableName) {
		String mapperName = MybatisConverter.getJavaName(tableName, "class");
		String fileName = mapperName + "Mapper.xml";
		return fileName;
	}

	@Override
	public List<String> genContents(String tableName,String packageName,List<TableColumnVO> columns) throws Exception {
		String mapperName = MybatisConverter.getJavaName(tableName, "class");
		String namespace = packageName + ".dao.entity." + mapperName;
		List<String> contents = new ArrayList<String>();
		
		String fileds = "";
		int index = 0;
		for (TableColumnVO column : columns) {
			String str = column.getName();
			index++;
			if (index == columns.size()) {
				fileds += "\t\t" + str;
			} else {
				fileds += "\t\t" + str ;
				fileds += ",\n";
			}
		}
		contents.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		contents.add("<!DOCTYPE mapper");
		contents.add("PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\"");
		contents.add("\"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">");
		contents.add("");
		contents.add("");
		contents.add("<mapper namespace=\"$\">".replace("$", namespace));
		contents.add("\t<resultMap id=\"result\" type=\"$\">".replace("$",
				namespace));
		contents = genResult(contents, columns);
		contents.add("\t</resultMap>");

		contents.add("\t<sql id=\"fields\">\n" + fileds);
		contents.add("\t</sql>");
		contents.add("");
		contents.add("");
		contents.add("\t<sql id=\"whereCondition\">");
		contents.add(genSqlCondition(getTargetColumns(columns)));
		contents.add("\t</sql>");
		contents = genFindAll(contents, tableName, namespace);
		contents = genCountByCondition(contents, tableName);
		contents = genCount(contents, tableName);
		contents = genInsert(contents, tableName, columns,namespace);
		contents.add("</mapper>");
		return contents;
	}
	

	/**
	 * 根據欄位產生where條件SQL
	 * 
	 * @param columns
	 * @return
	 */
	private String genSqlCondition(List<TableColumnVO> columns) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\t<where>").append("\n");
		for (TableColumnVO column : columns) {
			if(column.getTargetName()!=null){
				String name=column.getName();
				String targetName = column.getTargetName();
				addCommline(sb.append("\t\t\t<if test=" + "\""
						+ MybatisConverter.getJavaName(targetName, "var") + " != null\">"));
				if(column.isDate()){
					if(column.getTargetName().endsWith("Start")){
						addCommline(sb.append("\t\t\t\tAND " + name + " >= " + "#{"
								+ MybatisConverter.getJavaName(targetName, "var") + "}"));
					}else if(column.getTargetName().endsWith("End")){
						addCommline(sb.append("\t\t\t<![CDATA["));
						addCommline(sb.append("\t\t\t\tAND " + name + " < " + "#{"
								+ MybatisConverter.getJavaName(targetName, "var") + "}"));
						addCommline(sb.append("\t\t\t]]>"));
					}else{
						addCommline(sb.append("\t\t\t\tAND " + name + " = " + "#{"
								+ MybatisConverter.getJavaName(targetName, "var") + "}"));
					}
				}else{
					addCommline(sb.append("\t\t\t\tAND " + name + " = " + "#{"
							+ MybatisConverter.getJavaName(targetName, "var") + "}"));
				}
				addCommline(sb.append("\t\t\t</if>"));
			}
		}

		sb.append("\t\t</where>");

		return sb.toString();

	}

	/**
	 * 建立查詢所有資料
	 * 
	 * @param data
	 * @return
	 */
	private List<String> genFindAll(List<String> data, String tableName,
			String namespace) {
		StringBuffer sb = new StringBuffer();
		addCommline(sb
				.append("	<select id=\"findAll\" resultMap=\"result\" parameterType=\""
						+ namespace + "\">"));
		addCommline(sb.append("		SELECT <include refid=\"fields\"/>"));
		addCommline(sb.append("				FROM " + tableName));
		addCommline(sb.append("		<include refid=\"whereCondition\"/>"));
		addCommline(sb.append("	</select>"));
		data.add(sb.toString());
		return data;
	}

	/**
	 * 建立查詢所有資料
	 * 
	 * @param data
	 * @return
	 */
	private List<String> genCountByCondition(List<String> data, String tableName) {
		StringBuffer sb = new StringBuffer();
		addCommline(sb
				.append("	<select id=\"genCountByCondition\" resultType=\"Long\">"));
		addCommline(sb.append("		SELECT COUNT(1) "));
		addCommline(sb.append("				FROM " + tableName));
		addCommline(sb.append("		<include refid=\"whereCondition\"/>"));
		addCommline(sb.append("	</select>"));
		data.add(sb.toString());
		return data;
	}

	/**
	 * 建立查詢所有資料
	 * 
	 * @param data
	 * @return
	 */
	private List<String> genCount(List<String> data, String tableName) {
		StringBuffer sb = new StringBuffer();
		addCommline(sb.append("	<select id=\"getCount\" resultType=\"Long\">"));
		addCommline(sb.append("		SELECT COUNT(1) "));
		addCommline(sb.append("				FROM " + tableName));
		addCommline(sb.append("	</select>"));
		data.add(sb.toString());
		return data;
	}

	/**
	 * 建立Insert Sql
	 * 
	 * @param data
	 * @return
	 */
	private List<String> genResult(List<String> data, List<TableColumnVO> columns) {
		StringBuffer sb = new StringBuffer();
		for (TableColumnVO column : columns) {
			String name= column.getName();
			addCommline(sb.append("\t\t<result column=" + "\"" + name + "\""
					+ " " + "property=" + "\"" + MybatisConverter.getJavaName(name, "attr")
					+ "\"" + "/>"));
		}
		data.add(sb.toString());
		return data;
	}

	/**
	 * 建立Insert Sql
	 * 
	 * @param data
	 * @return
	 */
	private List<String> genInsert(List<String> data, String tableName,
			List<TableColumnVO> columns, String namespace) {
		StringBuffer sb = new StringBuffer();
		addCommline(sb.append("	<insert id=\"insert\" parameterType=\""
				+ namespace + "\""));
		addCommline(sb
				.append("		useGeneratedKeys=\"false\" keyProperty=\"id\" flushCache=\"true\">"));
		addCommline(sb
				.append("		<selectKey resultType=\"long\" order=\"BEFORE\" keyProperty=\"id\">"));
		addCommline(sb.append("			SELECT SEQ_" + tableName
				+ "_ID.Nextval from dual"));
		addCommline(sb.append("		</selectKey>"));
		addCommline(sb.append("	        INSERT INTO"));
		addCommline(sb.append("	        " + tableName + " ("));
		addCommline(sb.append("	        <include refid=\"fields\"/>)"));
		addCommline(sb.append("	        VALUES ("));

		int index = 0;
		for (TableColumnVO column : columns) {
			sb.append(MybatisConverter.getInsertType(column));
			if (index != columns.size() - 1) {
				addCommline(sb.append(","));
			} else {
				addCommline(sb);
			}
			index++;
		}
		addCommline(sb.append("	        )"));

		addCommline(sb.append("	</insert>"));
		data.add(sb.toString());
		return data;
	}

	/**
	 * 自動換行
	 * 
	 * @param sb
	 * @return
	 */
	private StringBuffer addCommline(StringBuffer sb) {
		sb.append("\n");
		return sb;
	}
	
	private List<TableColumnVO> getTargetColumns(List<TableColumnVO> columns){
		List<TableColumnVO> targetColumns = new ArrayList<>();
		for (TableColumnVO column : columns) {
			String name = column.getName();
			if(column.isDate()){
				String startName="TARGET_"+name.replaceFirst("(.)", name.substring(0, 1).toUpperCase())+"_Start";
				TableColumnVO targetColumnStart= new TableColumnVO(name,column.getType(),column.getSize(),column.getScale());
				targetColumnStart.setTargetName(startName);
				String endName="TARGET_"+name.replaceFirst("(.)", name.substring(0, 1).toUpperCase())+"_End";
				TableColumnVO targetColumnEnd= new TableColumnVO(name,column.getType(),column.getSize(),column.getScale());
				targetColumnEnd.setTargetName(endName);
				targetColumns.add(targetColumnStart);
				targetColumns.add(targetColumnEnd);
			}else{
				String targetName ="TARGET_"+name.replaceFirst("(.)", name.substring(0, 1).toUpperCase());
				TableColumnVO targetColumn= new TableColumnVO(name,column.getType(),column.getSize(),column.getScale());
				targetColumn.setTargetName(targetName);
				targetColumns.add(targetColumn);
			}
		}
		return targetColumns;
	}

}
