package com.winterframework.firefrog.game.web.controller.view;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;

/** 
* @ClassName: PdfView 
* @Description: 导出excel 
* @author Alan
* @date 2013-10-25 下午1:56:25 
*  
*/
public class PdfView extends AbstractPdfView {
	private static Font titlefont;// 设置字体大小 
	private static Font headfont;// 设置字体大小 
	private static Font keyfont;// 设置字体大小 
	private static Font textfont;// 设置字体大小 

	static {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			titlefont = new Font(bfChinese, 16, Font.BOLD);// 设置字体大小 
			headfont = new Font(bfChinese, 14, Font.NORMAL);// 设置字体大小 
			keyfont = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小 
			textfont = new Font(bfChinese, 12, Font.NORMAL);// 设置字体大小 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter pdfWriter,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExportViewDataModel dataModel = (ExportViewDataModel) model.get("dataModel");
		//String fileName = dataModel.getFileName();
		String issue = dataModel.getHeader()[0];
		String userName = dataModel.getColumns()[0];
		List list = dataModel.getDataList();
		//doc.addTitle(issue + "期双色球方案下载文档");
		//pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream("双色球方案第" + issue + "期.pdf"));
		String fileName = "双色球方案第" + issue + "期";
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.concat(".pdf").getBytes("utf-8"), "ISO8859-1"));
		
		doc.open();
		float space1=20;
		Paragraph paragraph0 = new Paragraph(issue + "期双色球方案下载文档",titlefont);
		paragraph0.setSpacingAfter(40);
		paragraph0.setAlignment(1);
		doc.add(paragraph0);
		
		Paragraph paragraph1 = new Paragraph("奖期:" + issue, headfont);
		paragraph1.setSpacingAfter(space1);
		doc.add(paragraph1);
		
		Paragraph paragraph2 = new Paragraph("总方案数目:" + list.size(), headfont);
		paragraph2.setSpacingAfter(space1);
		doc.add(paragraph2);
		
		Paragraph paragraph3=new Paragraph("该文档生成时间:" + DateUtils.format(DateUtils.currentDate(), DateUtils.DATE_FORMAT_PATTERN),
				headfont);
		paragraph3.setSpacingAfter(space1);
		doc.add(paragraph3);
		
		Paragraph paragraph4=new Paragraph("下载人:" + userName, headfont);
		paragraph4.setSpacingAfter(space1);
		doc.add(paragraph4);
		
		Paragraph paragraph5=new Paragraph("下载时间:" + DateUtils.format(DateUtils.currentDate(), DateUtils.DATE_FORMAT_PATTERN), headfont);
		paragraph4.setSpacingAfter(space1);
		doc.add(paragraph5);
		doc.newPage();
		PdfPTable table = createTable(5);
		table.addCell(createCell("方案编号", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("投注时间", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("投注人", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("方案金额", keyfont, Element.ALIGN_CENTER));
		table.addCell(createCell("方案详情", keyfont, Element.ALIGN_CENTER));
		for (int i = 0; i < list.size(); i++) {
			GameOrderDetailQueryResponse queryResponse = (GameOrderDetailQueryResponse) list.get(i);
			table.addCell(createCell(queryResponse.getOrdersStruc().getOrderCode(), textfont, Element.ALIGN_CENTER));
			table.addCell(createCell(DateUtils.format(
					DataConverterUtil.convertLong2Date(queryResponse.getOrdersStruc().getSaleTime()),
					DateUtils.DATE_FORMAT_PATTERN), textfont, Element.ALIGN_CENTER));
			table.addCell(createCell(queryResponse.getOrdersStruc().getAccount(), textfont, Element.ALIGN_CENTER));
			table.addCell(createCell(String.valueOf(queryResponse.getOrdersStruc().getTotamount() / 10000), textfont,
					Element.ALIGN_CENTER));
			StringBuffer slipDetail = new StringBuffer();
			List<SlipsStruc> sss = queryResponse.getSlipsStruc();
			for (int j = 0; j < sss.size(); j++) {
				slipDetail.append(j+1+"."+sss.get(j).getBetDetail()+"\n");
			}
			table.addCell(createCell(slipDetail.toString(), textfont, Element.ALIGN_CENTER));

		}
		doc.add(table);
	}

	public PdfPTable createTable(int colNumber) {
		PdfPTable table = new PdfPTable(colNumber);
		try {
			table.setTotalWidth(520);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	public PdfPCell createCell(String value, com.lowagie.text.Font font, int align, int colspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		}
		return cell;
	}

	public PdfPCell createCell(String value, com.lowagie.text.Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	public PdfPCell createCell(String value, com.lowagie.text.Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	public PdfPCell createCell(String value, com.lowagie.text.Font font, int align, int colspan) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	/*protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
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
		
	}*/

}
