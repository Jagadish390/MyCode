package com.java.controller;

import java.sql.SQLException;
import java.util.HashSet;
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
import com.java.DAO.EmployeeDAO;
import com.java.DAO.MachineMasterDAO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.MachineMasterDTO;

@Controller
@Scope("session")
public class MachineMasterController {

	EmployeeDAO employeeDAO = new EmployeeDAO();
	MachineMasterDAO machineMasterDAO = new MachineMasterDAO();
	private static final Logger logger = Logger
			.getLogger(MachineMasterController.class.getName());

	public static List<MachineMasterDTO> getMachineMasterDetails()
			throws SQLException {
		List<MachineMasterDTO> machineMasterDTOs = null;
		MachineMasterDAO machineMasterDAO = new MachineMasterDAO();
		machineMasterDTOs = (List<MachineMasterDTO>) machineMasterDAO
				.getMachineDetails();

		return machineMasterDTOs;
	}

	@RequestMapping("/viewMachineMaster.htm")
	public ModelAndView viewMachineMaster() throws SQLException

	{
		List<MachineMasterDTO> machineMasterDTOs = null;
		machineMasterDTOs = getMachineMasterDetails();
		ModelAndView model1 = new ModelAndView("viewMachineMaster");
		model1.addObject("machineMasterDTOs", machineMasterDTOs);
		return model1;
	}

	@RequestMapping("/openMachineMaster.htm")
	public ModelAndView openMachineMaster() throws SQLException {
		Set<String> userIdList = new TreeSet<String>();
		ModelAndView model1 = new ModelAndView("MachineMaster");
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = null;
		machineMasterDTOs = getMachineMasterDetails();
		model1.addObject("machineMasterDTOs", machineMasterDTOs);
		model1.addObject("userIdList", userIdList);
		return model1;
	}

	@RequestMapping("/addMachineMaster.htm")
	public ModelAndView addMachineMaster(
			@ModelAttribute("MachineMasterDTO") MachineMasterDTO machineMasterDTO)
			throws SQLException {
		String displayMessage = machineMasterDAO.addMachine(machineMasterDTO);
		String status = "MachineMaster";

		ModelAndView model1 = new ModelAndView(status);

		Set<String> userIdList = new TreeSet<String>();
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = null;
		machineMasterDTOs = getMachineMasterDetails();
		model1.addObject("machineMasterDTOs", machineMasterDTOs);
		model1.addObject("userIdList", userIdList);

		model1.addObject("displayMessage", displayMessage);

		return model1;
	}

	@RequestMapping("/updateMachineData.htm")
	public ModelAndView updateMachineMaster(
			@ModelAttribute("MachineMasterDTO") MachineMasterDTO machineMasterDTO, HttpServletRequest request)
			throws SQLException {
		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = machineMasterDAO
				.updateMachineMaster(machineMasterDTO,userId);
		String status = "viewMachineMaster";
		ModelAndView model1 = new ModelAndView(status);

		List<MachineMasterDTO> machineMasterDTOs = null;
		machineMasterDTOs = getMachineMasterDetails();
		model1.addObject("machineMasterDTOs", machineMasterDTOs);
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
