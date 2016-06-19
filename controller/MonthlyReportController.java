package com.java.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




import com.java.DAO.BaseDAO;
import com.java.DAO.MonthlyReportDAO;
import com.java.DTO.BatchDTO;
import com.java.DTO.DateDTO;
import com.java.DTO.MonthlyReportDTO;


@Controller
@Scope("session")
public class MonthlyReportController {
	MonthlyReportDAO monthlyReportDAO = new MonthlyReportDAO();
	private static final Logger logger = Logger
			.getLogger(MonthlyReportController.class.getName());

	@RequestMapping("/openMonthlyReport.htm")
	public ModelAndView openMonthlyReport() throws SQLException {
		ModelAndView model1 = new ModelAndView("ViewMonthlyReport");
		return model1;
	}
	
	@RequestMapping("/viewMonthlyReport.htm")
	public ModelAndView viewMonthlyReport(HttpServletRequest request) throws SQLException {
		ModelAndView model1 = new ModelAndView("ViewMonthlyReport");
		MonthlyReportDAO monthlyReportDAO = new MonthlyReportDAO();
		List<MonthlyReportDTO> monthlyReportDTOs = monthlyReportDAO.getMonthlyReportDetails(request);
		model1.addObject("monthlyReportDTOs", monthlyReportDTOs);
		return model1;
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleApplication(Exception e) {

		ModelAndView modelAndView = new ModelAndView("Failure");
		String displayMessage = "Error occured while performing this operation.";
		modelAndView.addObject("displayMessage", displayMessage);
		logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
		logger.info("LogId:-"+BaseDAO.getLogId());
		logger.info("Unknown exception :-"
				+ e.getMessage());
		return modelAndView;
	}

}
