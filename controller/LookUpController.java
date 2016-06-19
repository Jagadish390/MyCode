package com.java.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BaseDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.LookupDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.LookUpDTO;
import com.java.DTO.ProductionDTO;
@Controller
@Scope("session")
public class LookUpController {
	LookupDAO lookupDAO = new LookupDAO();
	private static final Logger logger = Logger
			.getLogger(LookUpController.class.getName());
	
	@RequestMapping("/openLookUpMaster.htm")
	public ModelAndView openLookUpMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("LookUpMaster");
		Set<String> userIdList = new TreeSet<String>();
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		
		List<LookUpDTO> lookUpDTOs = null;
		lookUpDTOs = LookupDAO.viewLookUpDetails();
		model1.addObject("lookUpDTOs", lookUpDTOs);	
		
		return model1;
		
	}
	@RequestMapping("/addLookUpMaster.htm")
	public ModelAndView addLookUpMaster(@ModelAttribute("LookUpDTO") LookUpDTO lookUpDTO) throws SQLException {
		
		
		String status = lookupDAO.addLookUpMaster(lookUpDTO);
		ModelAndView model1 = new ModelAndView(status);
		String displayMessage = null;
		List<LookUpDTO> lookUpDTOs = null;
		lookUpDTOs = LookupDAO.viewLookUpDetails();
		model1.addObject("lookUpDTOs", lookUpDTOs);
		if(status.equalsIgnoreCase("LookUpMaster"))
		{
			displayMessage = "Details added Successfully.";
			Set<String> userIdList = new TreeSet<String>();
			List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
			for (EmployeeDTO employeeDTO : employeeDTOs) {

				userIdList.add(employeeDTO.getUserId());
			}
			model1.addObject("userIdList", userIdList);
			model1.addObject("displayMessage", displayMessage);
		}
		else {
			displayMessage = "Error in adding details. Please contact Admin";
			model1.addObject("displayMessage", displayMessage);
		}
		
		return model1;
		
	}
	
	@RequestMapping("/viewLookupDetails.htm")
	public ModelAndView viewLookupDetails() throws SQLException

	{
		List<LookUpDTO> lookUpDTOs = null;
		lookUpDTOs = LookupDAO.viewLookUpDetails();
		ModelAndView model1 = new ModelAndView("viewLookupDetails");
		model1.addObject("lookUpDTOs", lookUpDTOs);
		return model1;
	}
	
	
	
	@RequestMapping("/updateLookupDetails.htm")
	public ModelAndView updateLookupDetails(
			@ModelAttribute("LookUpDTO") LookUpDTO lookUpDTO,
			HttpServletRequest request) throws SQLException {
		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = lookupDAO.updateLookupDetails(lookUpDTO,userId);
		String status = "viewLookupDetails";
		ModelAndView model1 = new ModelAndView(status);

		List<LookUpDTO> lookUpDTOs = null;
		lookUpDTOs = LookupDAO.viewLookUpDetails();
		model1.addObject("lookUpDTOs", lookUpDTOs);
		model1.addObject("displayMessage", displayMessage);
		
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
