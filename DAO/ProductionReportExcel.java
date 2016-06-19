package com.java.DAO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.java.DTO.ProductionReportDTO;
import com.java.util.CommonRef;

public class ProductionReportExcel extends AbstractExcelView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=ProductionReport.xls");
		List<ProductionReportDTO> prodData = (List<ProductionReportDTO>) model
				.get("listProdData");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("KPI Report");
		sheet.setDefaultColumnWidth(30);
		HSSFDataFormat format = workbook.createDataFormat();
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		CellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.MAROON.index);
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style2.setFont(font);

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		List<ProductionReportDTO> productionReportDTOs = new ArrayList<ProductionReportDTO>();
		List<Double> performanceActual = (List<Double>) model
				.get("performanceActual");

		int j = 0;
		int k = 0;
		for (ProductionReportDTO productionReportDTO : prodData) {

			// Novis Yield Actual
			productionReportDTO
					.setNovisYieldActual(((Double
							.parseDouble(productionReportDTO.getNovisAccepted())) / (Double
							.parseDouble(productionReportDTO.getNovisProduced()))) * 100);

			// Utilization Actual
			productionReportDTO.setUtilizationActual(((Double
					.parseDouble(productionReportDTO.getTotalTime()) - Double
					.parseDouble(productionReportDTO.getTimeLoss())) / (Double
					.parseDouble(productionReportDTO.getTotalTime()))) * 100);
			// Glass Yield Actual
			productionReportDTO.setGlassYieldActual(((Double
					.parseDouble(productionReportDTO.getGoodKg())) / (Double
					.parseDouble(productionReportDTO.getUsedKg()))) * 100);

			// Loading Actual
			productionReportDTO.setLoadingActual(((Double
					.parseDouble(productionReportDTO.getTotalTime()) - Double
					.parseDouble("0")) / (Double
					.parseDouble(productionReportDTO.getTotalTime()))) * 100);

			// Performance Actual

			productionReportDTO.setNovisYieldTarget(98);
			productionReportDTO.setUtilizationTarget(82);
			productionReportDTO.setLoadingTarget(100);
			productionReportDTO.setGlassYieldTarget(80);
			productionReportDTO.setPerformanceTarget(89);
			//productionReportDTO.setOaeActual(58.384);
			//productionReportDTO.setOeeActual(58.384);
			productionReportDTO.setGlassYieldActual(((Double
					.parseDouble(productionReportDTO.getGoodKg())) / (Double
					.parseDouble(productionReportDTO.getUsedKg()))) * 100);
			productionReportDTO
					.setOeeTarget((productionReportDTO.getUtilizationTarget()
							* productionReportDTO.getGlassYieldTarget() * productionReportDTO
							.getPerformanceTarget()) / 1000);

			double oeeActual = (productionReportDTO.getUtilizationActual()
					* productionReportDTO.getGlassYieldActual() * performanceActual
					.get(k)) / 10000.0;
			productionReportDTO.setOeeActual(oeeActual);

			productionReportDTO.setOaeTarget(58.384);

			productionReportDTO.setOaeActual((oeeActual * productionReportDTO
					.getLoadingActual()) / 100);

			productionReportDTOs.add(productionReportDTO);
			k++;
		}

		// create header row
		HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("Machine Number");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Used Kg");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Good Kg");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Novis Produced");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Novis Accepted");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Total Packed");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Time Loss");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Total Time");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Novis Yield Actual");
		header.getCell(8).setCellStyle(style2);

		header.createCell(9).setCellValue("Novis Yield Target");
		header.getCell(9).setCellStyle(style2);

		header.createCell(10).setCellValue("Loading Actual");
		header.getCell(10).setCellStyle(style2);

		header.createCell(11).setCellValue("Loading Target");
		header.getCell(11).setCellStyle(style2);

		header.createCell(12).setCellValue("Utilization Actual");
		header.getCell(12).setCellStyle(style2);

		header.createCell(13).setCellValue("Utilization Target");
		header.getCell(13).setCellStyle(style2);

		header.createCell(14).setCellValue("Glass Yield Actual");
		header.getCell(14).setCellStyle(style2);

		header.createCell(15).setCellValue("Glass Yield Target");
		header.getCell(15).setCellStyle(style2);

		header.createCell(16).setCellValue("Performance Actual");
		header.getCell(16).setCellStyle(style2);

		header.createCell(17).setCellValue("Performance Target");
		header.getCell(17).setCellStyle(style2);

		header.createCell(18).setCellValue("OEE Actual");
		header.getCell(18).setCellStyle(style2);

		header.createCell(19).setCellValue("OEE Target");
		header.getCell(19).setCellStyle(style2);

		header.createCell(20).setCellValue("OAE Actual");
		header.getCell(20).setCellStyle(style2);

		header.createCell(21).setCellValue("OAE Target");
		header.getCell(21).setCellStyle(style2);

		// create data rows
		int rowCount = 1;
		for (ProductionReportDTO productionReportDTO : productionReportDTOs) {
			
			HSSFRow aRow = sheet.createRow(rowCount++);
			HSSFCell cell = aRow.createCell(0);
			cell.setCellStyle(style);
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			aRow.createCell(0).setCellValue(
					productionReportDTO.getMachineNumber());
			aRow.createCell(1).setCellValue(productionReportDTO.getUsedKg());
			style.setDataFormat(format.getFormat("0.0"));
			
			cell.setCellStyle(style);
			aRow.createCell(2).setCellValue(
					decimalFormat.format(Double.parseDouble(productionReportDTO.getGoodKg())));
			aRow.createCell(3).setCellValue(
					productionReportDTO.getNovisProduced());
			aRow.createCell(4).setCellValue(
					productionReportDTO.getNovisAccepted());
			aRow.createCell(5).setCellValue(
					productionReportDTO.getTotalPacked());
			aRow.createCell(6).setCellValue(productionReportDTO.getTimeLoss());
			aRow.createCell(7).setCellValue(productionReportDTO.getTotalTime());

			aRow.createCell(8).setCellValue(
					decimalFormat.format(productionReportDTO
							.getNovisYieldActual()) + "%");
			aRow.createCell(9).setCellValue(
					productionReportDTO.getNovisYieldTarget() + "%");

			aRow.createCell(10)
					.setCellValue(
							decimalFormat.format(productionReportDTO
									.getLoadingActual()) + "%");
			aRow.createCell(11).setCellValue(
					productionReportDTO.getLoadingTarget() + "%");

			aRow.createCell(12).setCellValue(
					decimalFormat.format(productionReportDTO
							.getUtilizationActual()) + "%");
			aRow.createCell(13).setCellValue(
					productionReportDTO.getUtilizationTarget() + "%");

			aRow.createCell(14).setCellValue(
					decimalFormat.format(productionReportDTO
							.getGlassYieldActual()) + "%");
			aRow.createCell(15).setCellValue(
					productionReportDTO.getGlassYieldTarget() + "%");

			aRow.createCell(16).setCellValue(
					decimalFormat.format(performanceActual.get(j)) + "%");

			aRow.createCell(17).setCellValue(
					productionReportDTO.getPerformanceTarget() + "%");

			aRow.createCell(18).setCellValue(
					decimalFormat.format(productionReportDTO.getOeeActual())
							+ "%");
			aRow.createCell(19).setCellValue(
					productionReportDTO.getOeeTarget() + "%");

			aRow.createCell(20).setCellValue(
					decimalFormat.format(productionReportDTO.getOaeActual())
							+ "%");
			aRow.createCell(21).setCellValue(58.384 + "%");
			j++;
			
		}
		
		for (int i = 0; i < 50; i++) {
			sheet.autoSizeColumn(i);
		}

	}

	public static List<ProductionReportDTO> convertEntityToDTO(
			List<ProductionReportDTO> prodData1, List<Double> performanceActual1)

	{

		List<ProductionReportDTO> prodData = prodData1;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		// create a new Excel sheet
		List<ProductionReportDTO> productionReportDTOs = new ArrayList<ProductionReportDTO>();
		List<Double> performanceActual = performanceActual1;

		int k = 0;
		for (ProductionReportDTO productionReportDTO : prodData) {

			// Novis Yield Actual
			productionReportDTO
					.setNovisYieldActual(((Double
							.parseDouble(productionReportDTO.getNovisAccepted())) / (Double
							.parseDouble(productionReportDTO.getNovisProduced()))) * 100);

			// Utilization Actual
			productionReportDTO.setUtilizationActual(((Double
					.parseDouble(productionReportDTO.getTotalTime()) - Double
					.parseDouble(productionReportDTO.getTimeLoss())) / (Double
					.parseDouble(productionReportDTO.getTotalTime()))) * 100);
			// Glass Yield Actual
			productionReportDTO.setGlassYieldActual((CommonRef.roundTwoDecimals((Double
					.parseDouble(productionReportDTO.getGoodKg())) / (Double
					.parseDouble(productionReportDTO.getUsedKg()))) * 100));

			// Loading Actual
			productionReportDTO.setLoadingActual(((Double
					.parseDouble(productionReportDTO.getTotalTime()) - Double
					.parseDouble("0")) / (Double
					.parseDouble(productionReportDTO.getTotalTime()))) * 100);

			// Performance Actual

			productionReportDTO.setNovisYieldTarget(98);
			productionReportDTO.setUtilizationTarget(82);
			productionReportDTO.setLoadingTarget(100);
			productionReportDTO.setGlassYieldTarget(80);
			productionReportDTO.setPerformanceActual(performanceActual.get(k));
			productionReportDTO.setPerformanceTarget(89);
			//productionReportDTO.setOaeActual(58.384);
			//productionReportDTO.setOeeActual(58.384);
			
			productionReportDTO.setGlassYieldActual(((Double
					.parseDouble(productionReportDTO.getGoodKg())) / (Double
					.parseDouble(productionReportDTO.getUsedKg()))) * 100);
			productionReportDTO
					.setOeeTarget((productionReportDTO.getUtilizationTarget()
							* productionReportDTO.getGlassYieldTarget() * productionReportDTO
							.getPerformanceTarget()) / 1000);

			double oeeActual = (productionReportDTO.getUtilizationActual()
					* productionReportDTO.getGlassYieldActual() * performanceActual
					.get(k)) / 10000.0;
			productionReportDTO.setOeeActual(Double.parseDouble(numberFormat.format(oeeActual)));

			productionReportDTO.setOaeTarget((productionReportDTO.getUtilizationTarget()
					* productionReportDTO.getGlassYieldTarget() * productionReportDTO
					.getPerformanceTarget()*productionReportDTO.getLoadingTarget()) / 1000);

			productionReportDTO.setOaeActual((oeeActual * productionReportDTO
					.getLoadingActual()) / 100);

			productionReportDTOs.add(productionReportDTO);
			k++;
		}
		return productionReportDTOs;
	}

}
