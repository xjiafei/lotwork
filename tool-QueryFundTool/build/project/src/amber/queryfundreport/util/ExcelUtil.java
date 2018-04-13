package amber.queryfundreport.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.PropertyUtils;

import amber.queryfundreport.vo.ExcelColumnVo;

public class ExcelUtil<T> {

	public void exportToExcel(String fileName, List<ExcelColumnVo> fields,
			List<T> datas) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(
					fileName));
			WritableSheet sheet = workbook.createSheet("report", 0);
			createTitle(sheet, fields);
			// 填充数据
			Iterator<T> it = datas.iterator();
			Object value = null;
			int row = 1;
			int sheetIndex = 0;
			while (it.hasNext()) {
				T data = it.next();
				if(row>=65536){
					sheetIndex++;
					row = 1;
					sheet = workbook.createSheet("report"+sheetIndex, sheetIndex);
					createTitle(sheet, fields);
				}
				for (int cell = 0; cell < fields.size(); cell++) {
					ExcelColumnVo column = fields.get(cell);
					value = getCellValue(data, column.getJavaParamName());
					sheet.addCell(new Label(cell, row, value == null ? null
							: String.valueOf(value)));
				}
				row++;
			}
			
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createTitle(WritableSheet sheet,List<ExcelColumnVo> fields) throws Exception{
		// 产生标题列
		String columnName = null;
		for (int cell = 0; cell < fields.size(); cell++) {
			ExcelColumnVo column = fields.get(cell);
			columnName = column.getFieldName();
			if (null == columnName || "".equals(columnName)) {
				columnName = "";
			}
			sheet.addCell(new Label(cell, 0, columnName));
		}
	}
	
	/**
	 * 深入取值
	 * 
	 * @param classType
	 * @param column
	 * @return
	 * @throws Exception
	 */
	private Object getCellValue(Object classType, String column)
			throws Exception {
		Object result = null;
		if (column.indexOf(".") > -1) {
			String columnFirst = column.substring(0, column.indexOf("."));
			String columnSecond = column.substring(column.indexOf(".") + 1);
			Object obj = PropertyUtils.getProperty(classType, columnFirst);
			if (obj != null) {
				result = getCellValue(obj, columnSecond);
			}
		} else {
			result = PropertyUtils.getProperty(classType, column);
		}
		return result;
	}

}
