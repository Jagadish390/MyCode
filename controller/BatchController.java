package com.java.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BaseDAO;
import com.java.DAO.BatchMasterDAO;
import com.java.DAO.BatchSummaryDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.BatchDTO;
import com.java.DTO.BatchDetailsDTO;
import com.java.DTO.BatchSummaryDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.TubeMasterDTO;

@Controller
@Scope("session")
public class BatchController {

	EmployeeDAO employeeDAO = new EmployeeDAO();
	BatchMasterDAO batchMasterDAO = new BatchMasterDAO();
	Set<String> articleIdList = new TreeSet<String>();

	private static final Logger logger = Logger
			.getLogger(BatchController.class.getName());
	@RequestMapping("/openBatchMaster.htm")
	public ModelAndView openBatchMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("BatchMaster");

		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> tubeIdList = new TreeSet<String>();

		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}

		List<ArticleMasterDTO> articleIdList = ArticleController
				.getArticleMasterDetails();

		List<TubeMasterDTO> tubeMasterDTOs = TubeController
				.getTubeMasterDetails();

		List<BatchDTO> batchDTOs = null;
		batchDTOs = getBatchMasterDetails();

		model1.addObject("batchDTOs", batchDTOs);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("articleIdList", articleIdList);
		model1.addObject("tubeMasterDTOs", tubeMasterDTOs);

		return model1;
	}

	public static List<BatchDTO> getBatchMasterDetails() throws SQLException {

		List<BatchDTO> batchDTOs = null;
		batchDTOs = (List<BatchDTO>) BatchMasterDAO.getBatchMasterDetails();

		return batchDTOs;

	}

	@RequestMapping("/updateBatchData.htm")
	public ModelAndView updateBatchMaster(
			@ModelAttribute("BatchDTO") BatchDTO batchDTO, HttpServletRequest request) throws SQLException {
		
		String userId= (String) request.getSession().getAttribute("UserName");

		String displayMessage = batchMasterDAO.updateBatchMaster(batchDTO,userId);
		String status = "viewBatchMaster";
		ModelAndView model1 = new ModelAndView(status);

		List<BatchDTO> batchDTOs = null;
		batchDTOs = getBatchMasterDetails();
		model1.addObject("batchDTOs", batchDTOs);
		model1.addObject("displayMessage", displayMessage);

		return model1;
	}

	@RequestMapping("/BatchMaster.htm")
	public ModelAndView viewBatchDetails() throws SQLException

	{
		List<BatchDTO> batchDTOs = null;
		batchDTOs = getBatchMasterDetails();
		ModelAndView model1 = new ModelAndView("viewBatchMaster");
		model1.addObject("batchDTOs", batchDTOs);
		return model1;
	}

	@RequestMapping("/addBatchMaster.htm")
	public ModelAndView addBatchMaster(
			@ModelAttribute("BatchDTO") BatchDTO batchDTO) throws Exception {

		String status = "BatchMaster";
		String displayMessage = "";
		ModelAndView model1 = new ModelAndView();

		displayMessage = batchMasterDAO.addBatchMaster(batchDTO);
		model1.setViewName(status);

		Set<String> machineNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		articleIdList = new TreeSet<String>();
		Set<String> tubeIdList = new TreeSet<String>();

		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}

		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}

		List<ArticleMasterDTO> articleIdList = ArticleController
				.getArticleMasterDetails();

		List<TubeMasterDTO> tubeMasterDTOs = TubeController
				.getTubeMasterDetails();

		for (TubeMasterDTO tubeMasterDTO : tubeMasterDTOs) {
			tubeIdList.add(tubeMasterDTO.getTubeId());
		}

		List<BatchDTO> batchDTOs = null;
		batchDTOs = getBatchMasterDetails();

		model1.addObject("batchDTOs", batchDTOs);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("articleIdList", articleIdList);
		model1.addObject("tubeIdList", tubeIdList);
		model1.addObject("tubeMasterDTOs", tubeMasterDTOs);

		model1.addObject("displayMessage", displayMessage);

		return model1;

	}

	@RequestMapping("/openBatchSummary.htm")
	public ModelAndView openBatchSummary() throws SQLException {

		Set<String> machineNumbers = new TreeSet<String>();
		articleIdList = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());

		}

		List<ArticleMasterDTO> articleMasterDTOs = ArticleController
				.getArticleMasterDetails();

		for (ArticleMasterDTO articleMasterDTO : articleMasterDTOs) {
			articleIdList.add(articleMasterDTO.getFocusCode());
		}
		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails1();
		ModelAndView model1 = new ModelAndView("ViewBatchSummary");
		model1.addObject("batchDTOs", batchDTOs);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("articleIdList", articleIdList);
		return model1;

	}

	@RequestMapping("/{batchNumber}/batchDetails1.htm")
	public ModelAndView batchDetails1(@PathVariable String batchNumber)
			throws SQLException {

		ModelAndView modelAndView = new ModelAndView();
		/*
		 * StringTokenizer stringTokenizer = new StringTokenizer(batchNumber,
		 * "@"); List<String> str = new ArrayList<String>(); while
		 * (stringTokenizer.hasMoreTokens()) {
		 * 
		 * str.add(stringTokenizer.nextToken());
		 * 
		 * } tableName = str.get(0); String batchNumber1 = str.get(1);
		 */
		String batchNumberNew = "#" + batchNumber;
		BatchSummaryDAO batchSummaryDAO = new BatchSummaryDAO();
		List<BatchSummaryDTO> batchSummaryDTOs = batchSummaryDAO
				.getDetails(batchNumberNew);
		modelAndView.addObject("batchSummaryDTO", batchSummaryDTOs);
		modelAndView.setViewName("ViewBatchDetails");

		return modelAndView;
	}

	@RequestMapping("/viewBatchSummary.htm")
	public ModelAndView getBatchSummary(
			@ModelAttribute("BatchDetailsDTO") BatchDetailsDTO batchDetailsDTO)
			throws SQLException

	{
		Set<String> machineNumbers = new TreeSet<String>();
		articleIdList = new TreeSet<String>();

		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());

		}

		List<ArticleMasterDTO> articleMasterDTOs = ArticleController
				.getArticleMasterDetails();

		for (ArticleMasterDTO articleMasterDTO : articleMasterDTOs) {
			articleIdList.add(articleMasterDTO.getFocusCode());
		}

		List<BatchSummaryDTO> batchSummaryDTOs = null;
		batchSummaryDTOs = BatchSummaryDAO.getBatchSummary1(batchDetailsDTO);
		ModelAndView model1 = new ModelAndView("ViewBatchSummary");
		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails1();
		model1.addObject("batchDTOs", batchDTOs);
		model1.addObject("batchSummaryDTOs", batchSummaryDTOs);
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("articleIdList", articleIdList);
		return model1;
	}

	@RequestMapping(value = "/getArticleDetails.htm", method = RequestMethod.POST)
	public @ResponseBody
	String getArticleDetails(

	@ModelAttribute("BatchDetailsDTO") BatchDetailsDTO batchDetailsDTO,
			BindingResult result) throws SQLException

	{

		Set<String> articleIdLists = new TreeSet<String>();
		String machineNumber = batchDetailsDTO.getMachineNumber();
		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails();
		articleIdLists.clear();
		for (BatchDTO batchDTO : batchDTOs) {

			if (batchDTO.getMachineNumber().equalsIgnoreCase(machineNumber)) {
				articleIdLists.add(batchDTO.getArticleId());

			}

		}

		return "Success";

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
