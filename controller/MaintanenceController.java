package com.java.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.java.DAO.BatchMasterDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.LookupDAO;
import com.java.DAO.MaintainanceDAO;
import com.java.DAO.PackedVialsReportGraphDAO;
import com.java.DAO.TimeLossGraphDAO;
import com.java.DTO.BatchDTO;
import com.java.DTO.DateDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.LookUpDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.MaintainanceDTO;
import com.java.DTO.MaintenanceActivityDTO;
import com.java.DTO.PackedVialsReportGraphDTO;
import com.java.DTO.TimeLossGraphDTO;

@Controller
@Scope("session")
public class MaintanenceController {

	MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
	private static final Logger logger = Logger
			.getLogger(MaintanenceController.class.getName());

	@RequestMapping("/openMaintainanceMaster.htm")
	public ModelAndView openMaintainanceMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("maintenanceData");

		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> batchNumbers = new TreeSet<String>();
		Set<String> types = new TreeSet<String>();
		Set<String> types1 = new TreeSet<String>();
		Set<String> maintenanceEngineersList = new HashSet<String>();
		Set<MaintenanceActivityDTO> maintenanceActivityList=new HashSet<MaintenanceActivityDTO>();
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getMaintenanceEngineers();
		for (EmployeeDTO employeeDTO1 : employeeDTOs1) {
			maintenanceEngineersList.add(employeeDTO1.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}

		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails2();
		for (BatchDTO batchDTO : batchDTOs) {
			batchNumbers.add(batchDTO.getBatchNumber());
		}
		List<MaintenanceActivityDTO> maintenanceActivityDTOs=MaintainanceDAO.getMaintenanceActivityDetails();
		for(MaintenanceActivityDTO maintenanceActivityDTO:maintenanceActivityDTOs){
			maintenanceActivityList.add(maintenanceActivityDTO);
		}
		List<LookUpDTO> lookUpDTOs = LookupDAO.getLookUpDetails();
		for (LookUpDTO lookUpDTO : lookUpDTOs) {
			if (lookUpDTO.getParam().equalsIgnoreCase("Maintenance data")) {
				types.add(lookUpDTO.getData());
			}
		}
		List<LookUpDTO> maintenanceReasons = LookupDAO.getLookUpDetails();
		for (LookUpDTO maintenanceReason : maintenanceReasons) {
			if (maintenanceReason.getParam().equalsIgnoreCase(
					"Maintenance Reason")) {
				types1.add(maintenanceReason.getData());
			}

		}

		List<Integer> divCount=new ArrayList<Integer>();
		for (int i=1;i<=20;i++) {
			divCount.add(i);
			
		}
		model1.addObject("divCount", divCount);
		List<MaintainanceDTO> maintainanceDTOs = null;
		maintainanceDTOs = getMaintenanceMasterDetails1();
		model1.addObject("maintainanceDTOs", maintainanceDTOs);

		model1.addObject("batchNumbers", batchNumbers);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("types", types);
		model1.addObject("types1", types1);
		model1.addObject("maintenanceEngineersList", maintenanceEngineersList);
		model1.addObject("maintenanceActivityList", maintenanceActivityList);
		
		return model1;
	}

	@RequestMapping("/viewMaintenanceMaster.htm")
	public ModelAndView viewMaintenanceMaster() throws SQLException

	{
		List<MaintainanceDTO> maintainanceDTOs = null;
		maintainanceDTOs = getMaintenanceMasterDetails();
		ModelAndView model1 = new ModelAndView("viewMaintainanceMaster");
		model1.addObject("maintainanceDTOs", maintainanceDTOs);
		return model1;
	}

	public static List<MaintainanceDTO> getMaintenanceMasterDetails()
			throws SQLException {
		List<MaintainanceDTO> maintainanceDTOs = null;
		MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
		maintainanceDTOs = (List<MaintainanceDTO>) maintainanceDAO
				.getMaintenanceMasterDetails();

		return maintainanceDTOs;
	}
	
	public static List<MaintainanceDTO> getMaintenanceMasterDetails1()
			throws SQLException {
		List<MaintainanceDTO> maintainanceDTOs = null;
		MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
		maintainanceDTOs = (List<MaintainanceDTO>) maintainanceDAO
				.getMaintenanceMasterDetails1();

		return maintainanceDTOs;
	}

	@RequestMapping("/openTimeLossGraph.htm")
	public ModelAndView openTimeLossGraph() throws SQLException

	{
		List<TimeLossGraphDTO> timeLossGraphDTOs = null;
		TimeLossGraphDAO timeLossGraphDAO=new TimeLossGraphDAO();
		
	/*	Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}*/
		
		/*packedVialsReportGraphDTOs = packedVialsReportGraphDAO
				.getPackedVialsDetails();*/
		ModelAndView model1 = new ModelAndView("TimeLossGraph");
	//	model1.addObject("machineNumbers", machineNumbers);
		/*model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);*/
		return model1;
	}
	
	
	@RequestMapping("/viewTimeLossGraph.htm")
	public ModelAndView viewTimeLossGraph(@ModelAttribute ("DateDTO") DateDTO dateDTO) throws Exception

	{
		List<TimeLossGraphDTO> timeLossGraphDTOs = null;
		TimeLossGraphDAO timeLossGraphDAO=new TimeLossGraphDAO();
		timeLossGraphDTOs=timeLossGraphDAO.getTimeLossDetails(dateDTO);
	
		ModelAndView model1 = new ModelAndView("TimeLossGraph");
	model1.addObject("timeLossGraphDTOs",timeLossGraphDTOs);
		return model1;
	}
	
	
	
	
	
	
	
	@RequestMapping("/updateMaintainanceData.htm")
	public ModelAndView updateMaintenanceMaster(
			@ModelAttribute("MaintainanceDTO") MaintainanceDTO maintainanceDTO, HttpServletRequest request)
			throws SQLException, ParseException {
		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = maintainanceDAO
				.updateMaintenanceMaster(maintainanceDTO,userId);
		String status = "viewMaintainanceMaster";
		ModelAndView model1 = new ModelAndView(status);

		List<MaintainanceDTO> maintainanceDTOs = null;
		maintainanceDTOs = getMaintenanceMasterDetails();
		model1.addObject("maintainanceDTOs", maintainanceDTOs);
		model1.addObject("displayMessage", displayMessage);

		return model1;

	}

	@RequestMapping("/addMaintainanceMaster.htm")
	public ModelAndView addMaintainanceMaster(
			@ModelAttribute("MaintainanceDTO") MaintainanceDTO maintainanceDTO)
			throws SQLException, ParseException {

		String displayMessage = maintainanceDAO
				.addMaintainanceMaster(maintainanceDTO);
		String status = "maintenanceData";
		ModelAndView model1 = new ModelAndView(status);
		Set<MaintenanceActivityDTO> maintenanceActivityList=new HashSet<MaintenanceActivityDTO>();
		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> batchNumbers = new TreeSet<String>();
		Set<String> maintenanceEngineersList = new HashSet<String>();
		Set<String> types1 = new TreeSet<String>();
		Set<String> types = new TreeSet<String>();
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getMaintenanceEngineers();
		for (EmployeeDTO employeeDTO1 : employeeDTOs1) {
			maintenanceEngineersList.add(employeeDTO1.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}

		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails2();
		for (BatchDTO batchDTO : batchDTOs) {
			batchNumbers.add(batchDTO.getBatchNumber());
		}
		List<LookUpDTO> lookUpDTOs = LookupDAO.getLookUpDetails();
		for (LookUpDTO lookUpDTO : lookUpDTOs) {
			if (lookUpDTO.getParam().equalsIgnoreCase("Maintenance data")) {
				types.add(lookUpDTO.getData());
			}
		}
		List<MaintenanceActivityDTO> maintenanceActivityDTOs=MaintainanceDAO.getMaintenanceActivityDetails();
		for(MaintenanceActivityDTO maintenanceActivityDTO:maintenanceActivityDTOs){
			maintenanceActivityList.add(maintenanceActivityDTO);
		}
		List<LookUpDTO> maintenanceReasons = LookupDAO.getLookUpDetails();
		for (LookUpDTO maintenanceReason : maintenanceReasons) {
			if (maintenanceReason.getParam().equalsIgnoreCase(
					"Maintenance Reason")) {
				types1.add(maintenanceReason.getData());
			}
		}
		List<MaintainanceDTO> maintainanceDTOs = null;
		maintainanceDTOs = getMaintenanceMasterDetails1();
		List<Integer> divCount=new ArrayList<Integer>();
		for (int i=1;i<=20;i++) {
			divCount.add(i);
			
		}
		model1.addObject("divCount", divCount);
		
		model1.addObject("maintainanceDTOs", maintainanceDTOs);

		model1.addObject("batchNumbers", batchNumbers);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("displayMessage", displayMessage);
		model1.addObject("types1", types1);
		model1.addObject("types", types);
		model1.addObject("maintenanceEngineersList", maintenanceEngineersList);
		model1.addObject("maintenanceActivityList", maintenanceActivityList);
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
