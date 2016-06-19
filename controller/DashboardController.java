package com.java.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.DashboardDAO;
import com.java.DAO.PackedVialsReportGraphDAO;
import com.java.DTO.GoodTonsDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.PackedVialsReportGraphDTO;
import com.java.DTO.ProductionReportDTO;
import com.java.DTO.TubesUsedDTO;
import com.java.DTO.YieldGraphDTO;
import com.java.util.CommonRef;

@Controller
@Scope("session")
public class DashboardController {
	
	@RequestMapping("/openDashboard.htm")
	public ModelAndView openProductionReport() throws Exception {
	String value="mtd";
		ModelAndView model1 = new ModelAndView("Dashboard");
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = null;
		DashboardDAO dashboardDAO = new DashboardDAO();
		packedVialsReportGraphDTOs = dashboardDAO.getPackedVialsDetails(value);		
		
		List<GoodTonsDTO> goodTonsDTOs=dashboardDAO.getGoodTonsGraph(value);
		List<YieldGraphDTO> yieldGraphDTOs=dashboardDAO.getYieldDetails(value);
		List<ProductionReportDTO> productionReportDTOs = dashboardDAO.getOAEGraph(value);
		Double sumOfGoodKg=0.0;
		for (ProductionReportDTO productionReportDTO : productionReportDTOs) {
			sumOfGoodKg=CommonRef.roundTwoDecimals(sumOfGoodKg+Double.parseDouble(productionReportDTO.getGoodKg()));
		}
		model1.addObject("sumOfGoodKg", sumOfGoodKg);
		model1.addObject("productionReportDTOs", productionReportDTOs);
		model1.addObject("yieldGraphDTOs", yieldGraphDTOs);
		model1.addObject("goodTonsDTOs", goodTonsDTOs);
		model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);
		return model1;
		
		
	}
	
	@RequestMapping("/openDashboardYTD.htm")
	public ModelAndView openDashboardYTD() throws Exception {
		String value="ytd";
		ModelAndView model1 = new ModelAndView("DashboardYTD");
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = null;
		DashboardDAO dashboardDAO = new DashboardDAO();
		packedVialsReportGraphDTOs = dashboardDAO.getPackedVialsDetails(value);		
		
		List<GoodTonsDTO> goodTonsDTOs=dashboardDAO.getGoodTonsGraph(value);
		List<YieldGraphDTO> yieldGraphDTOs=dashboardDAO.getYieldDetails(value);
		List<ProductionReportDTO> productionReportDTOs = dashboardDAO.getOAEGraph(value);
		model1.addObject("productionReportDTOs", productionReportDTOs);
		model1.addObject("yieldGraphDTOs", yieldGraphDTOs);
		model1.addObject("goodTonsDTOs", goodTonsDTOs);
		model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);
		return model1;
		
		
	}
}
