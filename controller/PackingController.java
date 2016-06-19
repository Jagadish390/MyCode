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
import com.java.DAO.BatchMasterDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.LookupDAO;
import com.java.DAO.PackedVialsReportGraphDAO;
import com.java.DAO.PackingDAO;
import com.java.DAO.YieldGraphDAO;
import com.java.DTO.BatchDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.LookUpDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.PackedVialsReportGraphDTO;
import com.java.DTO.PackingDTO;
import com.java.DTO.YieldGraphDTO;

@Controller
@Scope("session")
public class PackingController {

	PackingDAO packingDAO = new PackingDAO();
	private static final Logger logger = Logger
			.getLogger(PackingController.class.getName());

	@RequestMapping("/openPackingMaster.htm")
	public ModelAndView openPackingMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("packingData");

		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> batchNumbers = new TreeSet<String>();
		Set<String> types = new TreeSet<String>();
		Set<String> packingUsersList = new HashSet<String>();
		Set<String> ipqcUsersList = new HashSet<String>();
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getPackingUsers();
		for (EmployeeDTO employeeDTO1 : employeeDTOs1) {
			packingUsersList.add(employeeDTO1.getUserId());
		}
		List<EmployeeDTO> employeeDTOs2 = EmployeeDAO.getIPQCUsers();
		for (EmployeeDTO employeeDTO2 : employeeDTOs2) {
			ipqcUsersList.add(employeeDTO2.getUserId());
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
			if (lookUpDTO.getParam().equalsIgnoreCase("packing data")) {
				types.add(lookUpDTO.getData());
			}

		}

		List<PackingDTO> packingDTOs = null;
		packingDTOs = getPackingMasterDetails1();
		model1.addObject("packingDTOs", packingDTOs);
		model1.addObject("batchNumbers", batchNumbers);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("types", types);
		model1.addObject("packingUsersList", packingUsersList);
		model1.addObject("ipqcUsersList", ipqcUsersList);

		return model1;
	}
	public static List<PackingDTO> getPackingMasterDetails()
			throws SQLException {
		List<PackingDTO> packingDTOs = null;
		PackingDAO packingDAO = new PackingDAO();
		packingDTOs = (List<PackingDTO>) packingDAO.getPackingMasterDetails();

		return packingDTOs;
	}
	public static List<PackingDTO> getPackingMasterDetails1()
			throws SQLException {
		List<PackingDTO> packingDTOs = null;
		PackingDAO packingDAO = new PackingDAO();
		packingDTOs = (List<PackingDTO>) packingDAO.getPackingMasterDetails1();

		return packingDTOs;
	}

	@RequestMapping("/viewPackData.htm")
	public ModelAndView viewPackDetails() throws SQLException

	{
		List<PackingDTO> packingDTOs = null;
		packingDTOs = getPackingMasterDetails();
		ModelAndView model1 = new ModelAndView("viewPackingMaster");
		model1.addObject("packingDTOs", packingDTOs);
		return model1;
	}
	@RequestMapping("/openYieldGraph.htm")
	public ModelAndView openYieldGraph() throws SQLException

	{
		List<YieldGraphDTO> yieldGraphDTOs = null;
		YieldGraphDAO yieldGraphDAO = new YieldGraphDAO();
		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}
		
		/*packedVialsReportGraphDTOs = packedVialsReportGraphDAO
				.getPackedVialsDetails();*/
		ModelAndView model1 = new ModelAndView("YieldGraph");
		model1.addObject("machineNumbers", machineNumbers);
		/*model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);*/
		return model1;
	}
	@RequestMapping("/viewYieldGraph.htm")
	public ModelAndView viewPackDetailsGraph(@ModelAttribute ("YieldGraphDTO") YieldGraphDTO yieldGraphDTO) throws SQLException

	{
		List<YieldGraphDTO> yieldGraphDTOs = null;
		YieldGraphDAO yieldGraphDAO = new YieldGraphDAO();
		yieldGraphDTOs = yieldGraphDAO
				.getYieldDetails(yieldGraphDTO);
		ModelAndView model1 = new ModelAndView("YieldGraph");
		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("yieldGraphDTOs",
				yieldGraphDTOs);
		return model1;
	}

	@RequestMapping("/openDailyVials.htm")
	public ModelAndView openPackDetailsGraph() throws SQLException

	{
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = null;
		PackedVialsReportGraphDAO packedVialsReportGraphDAO = new PackedVialsReportGraphDAO();
		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}
		
		/*packedVialsReportGraphDTOs = packedVialsReportGraphDAO
				.getPackedVialsDetails();*/
		ModelAndView model1 = new ModelAndView("packedVialsDailyGraph");
		model1.addObject("machineNumbers", machineNumbers);
		/*model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);*/
		return model1;
	}
	@RequestMapping("/viewDailyVials.htm")
	public ModelAndView viewPackDetailsGraph(@ModelAttribute ("PackedVialsReportGraphDTO") PackedVialsReportGraphDTO packedVialsReportGraphDTO) throws Exception

	{
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = null;
		PackedVialsReportGraphDAO packedVialsReportGraphDAO = new PackedVialsReportGraphDAO();
		packedVialsReportGraphDTOs = packedVialsReportGraphDAO
				.getPackedVialsDetails(packedVialsReportGraphDTO);
		ModelAndView model1 = new ModelAndView("packedVialsDailyGraph");
		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("packedVialsReportGraphDTOs",
				packedVialsReportGraphDTOs);
		return model1;
	}

	@RequestMapping("/updatePackingdata.htm")
	public ModelAndView updatePackingMaster(
			@ModelAttribute("PackingDTO") PackingDTO packingDTO, HttpServletRequest request)
			throws SQLException {

		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = packingDAO.updatePackingMaster(packingDTO,userId);
		String status = "viewPackingMaster";
		ModelAndView model = new ModelAndView(status);
		List<PackingDTO> packingDTOs = null;
		packingDTOs = getPackingMasterDetails();
		model.addObject("packingDTOs", packingDTOs);
		model.addObject("displayMessage", displayMessage);

		return model;
	}

	@RequestMapping("/addPackingMaster.htm")
	public ModelAndView addPackingMaster(
			@ModelAttribute("PackingDTO") PackingDTO packingDTO)
			throws SQLException {

		String displayMessage = packingDAO.addPackingMaster(packingDTO);
		String status = "packingData";
		ModelAndView model1 = new ModelAndView(status);

		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> batchNumbers = new TreeSet<String>();
		Set<String> packingUsersList = new HashSet<String>();
		Set<String> ipqcUsersList = new HashSet<String>();
		Set<String> types = new TreeSet<String>();
		List<LookUpDTO> lookUpDTOs = LookupDAO.getLookUpDetails();
		for (LookUpDTO lookUpDTO : lookUpDTOs) {
			if (lookUpDTO.getParam().equalsIgnoreCase("packing data")) {
				types.add(lookUpDTO.getData());
			}

		}
		model1.addObject("types", types);
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<EmployeeDTO> employeeDTOs2 = EmployeeDAO.getIPQCUsers();
		for (EmployeeDTO employeeDTO2 : employeeDTOs2) {
			ipqcUsersList.add(employeeDTO2.getUserId());
		}
		List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getPackingUsers();
		for (EmployeeDTO employeeDTO1 : employeeDTOs1) {
			packingUsersList.add(employeeDTO1.getUserId());
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

		List<PackingDTO> packingDTOs = null;
		packingDTOs = getPackingMasterDetails1();
		model1.addObject("packingDTOs", packingDTOs);
		model1.addObject("batchNumbers", batchNumbers);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("packingUsersList", packingUsersList);
		model1.addObject("displayMessage", displayMessage);
		model1.addObject("ipqcUsersList", ipqcUsersList);

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
