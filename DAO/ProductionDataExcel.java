package com.java.DAO;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.java.DTO.ProductionDTO;

public class ProductionDataExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=sample.xls");
		@SuppressWarnings("unchecked")
		List<ProductionDTO> prodData = (List<ProductionDTO>) model
				.get("listProdData");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Production Data");
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		// create header row
		HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("Production Date");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Shift");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Batch Number");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Novis Production");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Novis Good");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Novis Rot Rej");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Novis Lien Rej");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Tubes Used");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Time Lost");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Prd Reason");
		header.getCell(9).setCellStyle(style);

		header.createCell(10).setCellValue("Reason Detail");
		header.getCell(10).setCellStyle(style);

		header.createCell(11).setCellValue("Glass Waste");
		header.getCell(11).setCellStyle(style);

		header.createCell(12).setCellValue("Activity Time");
		header.getCell(12).setCellStyle(style);

		header.createCell(13).setCellValue("Engineer Prod");
		header.getCell(13).setCellStyle(style);

		header.createCell(14).setCellValue("User ID");
		header.getCell(14).setCellStyle(style);

		// create data rows
		int rowCount = 1;

		for (ProductionDTO productionDTO : prodData) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(productionDTO.getProductionDate());
			aRow.createCell(1).setCellValue(productionDTO.getShift());
			aRow.createCell(2).setCellValue(productionDTO.getBatchNumber());
			aRow.createCell(3).setCellValue(productionDTO.getNovisProd());
			aRow.createCell(4).setCellValue(productionDTO.getNovisGood());
			aRow.createCell(5).setCellValue(productionDTO.getNovisRotRej());
			aRow.createCell(6).setCellValue(productionDTO.getNovisLienRej());
			aRow.createCell(7).setCellValue(productionDTO.getTubesUsed());
			aRow.createCell(8).setCellValue(productionDTO.getTimeLost());
			aRow.createCell(9)
					.setCellValue(productionDTO.getProductionReason());
			aRow.createCell(10).setCellValue(productionDTO.getReasonDetail());
			aRow.createCell(11).setCellValue(productionDTO.getGlassWaste());
			aRow.createCell(12).setCellValue(productionDTO.getActivityTime());
			aRow.createCell(13).setCellValue(productionDTO.getEngineerPrd());
			aRow.createCell(14).setCellValue(productionDTO.getUserId());

		}

	}
}
