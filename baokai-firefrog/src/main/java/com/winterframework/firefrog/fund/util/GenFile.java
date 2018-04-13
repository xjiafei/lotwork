package com.winterframework.firefrog.fund.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.orm.dal.DynamicDataSource;


public class GenFile{
	
	private DynamicDataSource datasource;
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		ApplicationContext context =new ClassPathXmlApplicationContext(new String[]{"applicationContext-dao.xml",
				"applicationContext-resource.xml"});
		DynamicDataSource source= (DynamicDataSource) context.getBean("dataSource");
		GenFile gen = new GenFile();
		gen.setDatasource(source);
		gen.genMapper();
	}
	
	/**
	 * 根據DB產生XML Mapper
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genMapper() throws ClassNotFoundException, SQLException, IOException{
		Connection conn = datasource.getConnection();
		//取得DB中介資料
		DatabaseMetaData metaData =  conn.getMetaData();

		String tableName = "FUND_WITHDRAW_LOG";//輸入Table Name 產生MapperFile
		String mapperName = getJavaName(tableName,"class");
		String namespace = FundWithdrawLog.class.getName();
		List<String> contents = new ArrayList<String>();
		List<String> columns = new ArrayList<String>();
		List<String> types = new ArrayList<String>();		
		List<Integer> sizes = new ArrayList<Integer>();
		List<Integer> scales = new ArrayList<Integer>();				
		ResultSet rs = metaData.getColumns(null, "FIREFOG", tableName.toUpperCase() , null);
		while(rs.next()){
				String name =  rs.getString("COLUMN_NAME");//欄位名稱
				String type =  rs.getString("TYPE_NAME");//屬性類別
				Integer size = rs.getInt("COLUMN_SIZE");//欄位大小
				Integer scale = rs.getInt("DECIMAL_DIGITS");//小數點位數
				columns.add(name);
				types.add(type);
				sizes.add(size);
				scales.add(scale);
		}
		rs.close();
		conn.close();
		
		String fileds ="";
		int index=0;
		for(String str :columns){
			index++;
			if(index == columns.size()){
				fileds += str;					
			}else{
				fileds += str+",";
			}
		}
		if(!StringUtils.isEmpty(tableName)){
			contents.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			contents.add("<!DOCTYPE mapper");								
			contents.add("PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\"");
			contents.add("\"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">");
			contents.add("");
			contents.add("");
			contents.add("<mapper namespace=\"$\">".replace("$", namespace));				
			contents.add(" <resultMap id=\"result\" type=\"$\">".replace("$", namespace));	
			contents = genResult(contents,columns);
			contents.add(" </resultMap>");					
			
			
			contents.add("	<sql id=\"fields\">"+fileds);					
			contents.add("	</sql>");													
			contents.add("");												
			contents.add("");												
			contents.add("	<sql id=\"whereCondition\">");												
			contents.add(genSqlCondition(columns));	
			contents.add("	</sql>");
			contents = genFindAll(contents, tableName , namespace);
			contents = genCountByCondition(contents, tableName);
			contents = genCount(contents, tableName);
			contents =genInsert(contents, tableName,columns,types,sizes,scales,namespace);
		}
		
		contents.add("</mapper>");

		File javaFile = new File("src\\main\\java\\com\\winterframework\\firefrog\\fund\\util\\"+mapperName+"Mapper"+".xml");
		System.out.println(1);
		if(javaFile.exists()){
			System.out.println(2);
			javaFile.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile));
		for(String str : contents){
			writer.write(str);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * 根據DB產生VO
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void genVo() throws ClassNotFoundException, SQLException, IOException{
		Connection conn = datasource.getConnection();		
		//取得DB中介資料
		DatabaseMetaData metaData =  conn.getMetaData();

		String tableName = "FUND_WITHDRAW_TIPS";//輸入Table Name 產生JavaFile

		//產生Java File
		String javaName = getJavaName(tableName, "class");
		List<String> contents = new ArrayList<String>();
		
			if(!StringUtils.isEmpty(tableName)){
				contents.add(	"package ;");
				contents.add(	"");								
				contents.add("public class "+javaName+" ");
				contents.add(" extends BaseEntity{");
				contents.add("");
				ResultSet rs = metaData.getColumns(null, "FIREFOG", tableName.toUpperCase() , null);
				while(rs.next()){
						String name =  rs.getString("COLUMN_NAME");//欄位名稱
						String type =  rs.getString("TYPE_NAME");//屬性類別
						Integer size = rs.getInt("COLUMN_SIZE");//欄位大小
						Integer scale = rs.getInt("DECIMAL_DIGITS");//小數點位數
						contents.add(getJavaAttribute( name , type ,  size,   scale));
						contents.add("");
				}
				rs.close();
			}
		conn.close();
		contents.add("}");

		File javaFile = new File("src\\main\\java\\com\\winterframework\\firefrog\\fund\\util\\"+javaName+".java");
		System.out.println(1);
		if(javaFile.exists()){
			System.out.println(2);
			javaFile.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile));
		for(String str : contents){
			writer.write(str);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}
	
	//取得Java屬性字串
	public static String getJavaAttribute(String name ,String dbType , Integer size,  Integer scale){
		String attribute = "";
		attribute+= "\tprivate "+typeConvert(dbType,size,scale)+" ";
		String[] tokens = name.toLowerCase().split("_");
		String columnName = "";
		for(int i = 0 ; i<tokens.length;i++){
			String token = tokens[i];
			if(i==0){
				columnName+=token;							
			}else{
				columnName+=token.substring(0, 1).toUpperCase()+token.substring(1);							
			}

		}
		attribute+= columnName+";";
		return attribute;
	}
	
	/**
	 * 資料庫轉換Java型態
	 * @param dbType
	 * @param size
	 * @param scale
	 * @return
	 */
	public static String  typeConvert(String dbType , Integer size,  Integer scale){
		String javaType = "";
		if(dbType.equals("VARCHAR2") || dbType.equals("NVARCHAR2")){
			javaType =  "String";
		}else if(dbType.equals("CHAR")){
			javaType =  "String";		
		}else if(dbType.equals("DATE") || dbType.startsWith("TIMESTAMP")){
			javaType =  "Date";			
		}else if(dbType.equals("NUMBER")){
			if(size!=null){
				if(scale!=null && scale>0){
					javaType =  "Double";
				}else if(size>10){
					javaType =  "Double";					
				}else{
					javaType =  "Integer";										
				}
			}
		}else if(dbType.equals("FLOAT")){
			javaType =  "Double";			
		}
		return javaType;
	}
	
	/**
	 * 根據欄位產生where條件SQL
	 * @param columns
	 * @return
	 */
	private String genSqlCondition(List<String> columns){
		StringBuffer sb = new StringBuffer();
		sb.append("		<where>").append("\n");
		for(String column:columns){
			addCommline(sb.append("		<if test="+"\""+getJavaName(column,"var")+" != null\">"));
			addCommline(sb.append("			And "+column+ " = "+"#{"+getJavaName(column,"var")+"}"));
			addCommline(sb.append("		</if>"));
			addCommline(sb.append(""));
		}
		
		sb.append("		</where>").append("\n");
		
		return sb.toString();
		
	}
	
	/**
	 * 建立查詢所有資料
	 * @param data
	 * @return
	 */
	private List<String> genFindAll(List<String> data,String tableName,String namespace){
		StringBuffer sb = new StringBuffer();
		addCommline(sb.append("	<select id=\"findAll\" resultMap=\"result\" parameterType=\""+namespace+"\">"));
		addCommline(sb.append("		Select <include refid=\"fields\"/>"));
		addCommline(sb.append("				From "+tableName));		
		addCommline(sb.append("		<include refid=\"whereCondition\"/>"));
		addCommline(sb.append("	</select>"));		
		data.add(sb.toString());
		return data;
	}
	
	/**
	 * 建立查詢所有資料
	 * @param data
	 * @return
	 */
	private List<String> genCountByCondition(List<String> data,String tableName){
		StringBuffer sb = new StringBuffer();
		addCommline(sb.append("	<select id=\"genCountByCondition\" resultType=\"Long\">"));
		addCommline(sb.append("		Select count(1) "));
		addCommline(sb.append("				From "+tableName));		
		addCommline(sb.append("		<include refid=\"whereCondition\"/>"));
		addCommline(sb.append("	</select>"));		
		data.add(sb.toString());
		return data;
	}
	
	/**
	 * 建立查詢所有資料
	 * @param data
	 * @return
	 */
	private List<String> genCount(List<String> data,String tableName){
		StringBuffer sb = new StringBuffer();
		addCommline(sb.append("	<select id=\"getCount\" resultType=\"Long\">"));
		addCommline(sb.append("		Select count(1) "));
		addCommline(sb.append("				From "+tableName));		
		addCommline(sb.append("	</select>"));		
		data.add(sb.toString());
		return data;
	}
	
	/**
	 * 建立Insert Sql
	 * @param data
	 * @return
	 */
	private List<String> genResult(List<String> data,List<String> columns){
		StringBuffer sb = new StringBuffer();
		for(String column:columns){
			addCommline(sb.append("			<result column="+"\""+column+"\""+" "+"property="+"\""+getJavaName(column, "attr")+"\""+"/>"));
		}
		data.add(sb.toString());
		return data;
	}
	
	/**
	 * 建立Insert Sql
	 * @param data
	 * @return
	 */
	private List<String> genInsert(List<String> data,String tableName,List<String> columns,List<String> types ,List<Integer> sizes , List<Integer> sclaes,String namespace){
		StringBuffer sb = new StringBuffer(); 
		addCommline(sb.append("	<insert id=\"insert\" parameterType=\""+namespace+"\""));
		addCommline(sb.append("		useGeneratedKeys=\"false\" keyProperty=\"id\" flushCache=\"true\">"));		
		addCommline(sb.append("		<selectKey resultType=\"long\" order=\"BEFORE\" keyProperty=\"id\">"));
		addCommline(sb.append("			SELECT ####.Nextval from dual"));		
		addCommline(sb.append("		</selectKey>"));		
		addCommline(sb.append("	        INSERT INTO"));
		addCommline(sb.append("	        "+tableName+" ("));	
		addCommline(sb.append("	        <include refid=\"fields\"/>)"));	
		addCommline(sb.append("	        Values ("));	
		
		int index = 0;
		for(String column:columns){
			
			sb.append(getInsertType(column,types.get(index),sizes.get(index),sclaes.get(index)));
			if(index!=columns.size()-1){
				addCommline(sb.append(","));	
			}else{
				addCommline(sb);	
			}
			index++;
		}
		addCommline(sb.append("	        )"));
		
		addCommline(sb.append("	</insert>"));
		data.add(sb.toString());
		return data;
	}

	
	private StringBuffer getInsertType(String column , String type ,Integer size ,Integer sclae){
		
		StringBuffer sb = new StringBuffer();
		sb.append("	         #{"+getJavaName(column,"var")).append(",javaType=").append(getMyBatisType("java",type,size,sclae));
		sb.append(",jdbcType=").append(getMyBatisType("jdbc",type,size,sclae)).append("}");
		return sb;
	}
	
	private String getMyBatisType(String ibatisType , String dbtype ,Integer size ,Integer sclae){
		String targetType="";
		if("java".equals(ibatisType)){
			if(dbtype.equals("VARCHAR2") || dbtype.equals("NVARCHAR2")||dbtype.equals("CHAR")){
				targetType =  "string";
			}else if(dbtype.equals("DATE") || dbtype.startsWith("TIMESTAMP")){
				targetType =  "object";			
			}else if(dbtype.equals("NUMBER")){
				if(sclae==0){
					targetType = "long";
				}else{
					targetType = "float";
				}
			}
		}else{
			if(dbtype.equals("VARCHAR2") || dbtype.equals("NVARCHAR2")||dbtype.equals("CHAR")){
				targetType =  "VARCHAR";
			}else if(dbtype.equals("DATE") || dbtype.startsWith("TIMESTAMP")){
				targetType =  "TIMESTAMP";			
			}else if(dbtype.equals("NUMBER")){
				if(sclae==0){
					targetType = "DECIMAL";
				}else{
					targetType = "DECIMAL";
				}
			}
		}
		
		return targetType;
	}
	
	
	/**
	 * 
	 * @param columnName
	 * @param attr
	 * @return
	 */
	private String getJavaName(String columnName,String attr){
		String[] mapperNameTokens = columnName.toLowerCase().split("_");
		String mapperName = "";
		int i =0;
		for(String token :mapperNameTokens){
			i++;
			if("class".equals(attr)){
				mapperName+=token.substring(0, 1).toUpperCase()+token.substring(1);				
			}else{
				if(i==1){
					mapperName+=token.substring(0, 1)+token.substring(1);									
				}else{
					mapperName+=token.substring(0, 1).toUpperCase()+token.substring(1);									
				}
			}
		}
		return mapperName;
	}
	
	/**
	 * 自動換行
	 * @param sb
	 * @return
	 */
	private StringBuffer addCommline(StringBuffer sb){
		sb.append("\n");
		return sb;
	}

	public DynamicDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DynamicDataSource datasource) {
		this.datasource = datasource;
	}
}
