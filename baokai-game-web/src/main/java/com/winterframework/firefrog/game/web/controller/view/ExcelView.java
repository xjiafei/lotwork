package com.winterframework.firefrog.game.web.controller.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/** 
* @ClassName: ExcelView 
* @Description: 导出excel 
* @author Alan
* @date 2013-10-25 下午1:56:25 
*  
*/
public class ExcelView extends AbstractExcelView {
	
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExportViewDataModel dataModel = (ExportViewDataModel) model.get("dataModel");
		
		String excelName = dataModel.getFileName();
		String sheetName = dataModel.getSheetName();
		String[] titles = dataModel.getHeader();
		String[] columns = dataModel.getColumns();
		List<?> rowList = dataModel.getDataList();
		
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(excelName.concat(".xls").getBytes("utf-8"), "ISO8859-1"));

		// 产生Excel表头
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow header = sheet.createRow(0); // 第0行
		// 产生标题列
		String titleName = null;
		for (int i = 0;i < titles.length; i++) {
			titleName = titles[i];
			if (null == titleName || "".equals(titleName))  {
				titleName = "";
			}
			header.createCell(i).setCellValue(titleName);
		}
		
        // 填充数据  
		Object value = null;
		for (int j = 0;j < rowList.size(); j++) {
			Object classType = rowList.get(j);
			
			HSSFRow row = sheet.createRow(j+1);
			
			for (int k = 0; k < columns.length; k++) {
				value = PropertyUtils.getProperty(classType, columns[k]);
				
				row.createCell(k).setCellValue(String.valueOf(value));  
			}
		}
		
	}
	
}
