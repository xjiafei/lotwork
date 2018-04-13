package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

/** 
* @ClassName: ExcelViewTemplates 
* @Description: 导出excel模板类
* @author Alan
* @date 2013-10-25 下午4:46:38 
*  
*/
public class ExportViewDataModel {

	//excel文件名
	private String fileName;
	
	//sheet名称
	private String sheetName = "exportDatas";
	
	//数据集列
	String[] header = null;
	
	//数据集内容(每列对应的属性名称)
	String[] columns = null;
	
	//数据集合
	List<?> dataList = null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

}
