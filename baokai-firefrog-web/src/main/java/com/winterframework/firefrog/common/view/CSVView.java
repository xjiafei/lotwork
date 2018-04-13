package com.winterframework.firefrog.common.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.servlet.view.AbstractView;

/** 
* @ClassName: CSVView 
* @Description: 导出CSV
* @author Alan
* @date 2013-10-25 上午11:48:59 
*  
*/
public class CSVView extends AbstractView {

	/**
	* Title: renderMergedOutputModel
	* Description:
	* @param model
	* @param request
	* @param response
	* @throws Exception 
	* @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) 
	*/
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		File file = new File("fileName");
//		FileInputStream in = new FileInputStream(file);
//		byte[] content = new byte[(int) file.length()];
//		in.read(content);
//		ServletContext sc = request.getSession().getServletContext();
//		String mimetype = sc.getMimeType(file.getName());
//		response.reset();
//		response.setContentType(mimetype);
//		response.setContentLength(content.length);
//		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//		org.springframework.util.FileCopyUtils.copy(content, response.getOutputStream());
		
		
		
		
		
		
		
		
		
		
		
		
		
		ExportViewDataModel dataModel = (ExportViewDataModel) model.get("dataModel");
		
		String fileName = dataModel.getFileName();
		String[] titles = dataModel.getHeader();
		String[] columns = dataModel.getColumns();
		List<?> rowList = dataModel.getDataList();
		
		//response.setContentType("text/csv;charset=utf-8");
		//response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName.concat(".txt"), "UTF-8"));   
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				fileName+".txt");
		response.setHeader(headerKey, headerValue);
		
		
		StringBuffer exportCSV = new StringBuffer();
		
		//设置列头
		String titleName = null;
		for (int i = 0;i < titles.length; i++) {
			titleName = titles[i];
			if (null == titleName || "".equals(titleName))  {
				titleName = "";
			}
			
			if(i == 0){
				exportCSV.append(titleName);
			}else{
				exportCSV.append(",").append(titleName);
			}
		}
		
		 // 填充数据  
		Object value = null;
		for (int j = 0;j < rowList.size(); j++) {
			Object classType = rowList.get(j);
			
			for (int k = 0; k < columns.length; k++) {
				value = PropertyUtils.getProperty(classType, columns[k]);
				
				if(k == 0){
					exportCSV.append("\r\n");
					exportCSV.append(value);
				}else{
					exportCSV.append("  ").append(value);
				}
			}
		}
		
		response.getWriter().write(exportCSV.toString());
		response.getWriter().flush();
	}

}
