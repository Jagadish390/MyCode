package com.java.controller;

import java.sql.SQLException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BaseDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.TubeMasterDAO;
import com.java.DAO.TubesUsedDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.DateDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.TubeMasterDTO;
import com.java.DTO.TubesUsedDTO;
import com.java.util.CommonRef;

@Controller
@Scope("session")
public class TubeController {
	
	EmployeeDAO employeeDAO=new EmployeeDAO();
	TubeMasterDAO tubeMasterDAO=new TubeMasterDAO();
	private String fromDate;
	private String toDate;
	
	private static final Logger logger = Logger
			.getLogger(TubeController.class.getName());
	
	public static List<TubeMasterDTO> getTubeMasterDetails() throws SQLException {
		List<TubeMasterDTO> tubeMasterDTOs = null;
		TubeMasterDAO tubeMasterDAO = new TubeMasterDAO();
		tubeMasterDTOs = (List<TubeMasterDTO>) tubeMasterDAO
				.getTubeMasterDetails();

		return tubeMasterDTOs;
	}
	
	@RequestMapping("/viewTubeMaster.htm")
	public ModelAndView viewTubeMaster() throws SQLException

	{
		List<TubeMasterDTO> tubeMasterDTOs = null;
		tubeMasterDTOs = getTubeMasterDetails();
		ModelAndView model1 = new ModelAndView("viewTubeMaster");
		model1.addObject("tubeMasterDTOs", tubeMasterDTOs);
		return model1;
	}

	
	@RequestMapping("/openTubesUsed.htm")
	public ModelAndView openTubesUsed() throws SQLException

	{
		//List<TubesUsedDTO> tubesUsedDTOs = null;
		TubesUsedDAO tubesUsedDAO=new TubesUsedDAO();
		//tubesUsedDTOs = (List<TubesUsedDTO>) tubesUsedDAO.getUsedTubeDetails();
		ModelAndView model1 = new ModelAndView("TubesUsedReport");
		//model1.addObject("tubesUsedDTOs", tubesUsedDTOs);
		return model1;
	}
	@RequestMapping("/viewTubesUsed.htm")
	public ModelAndView viewTubesUsed(@ModelAttribute DateDTO dateDTO) throws SQLException

	{
		List<TubesUsedDTO> tubesUsedDTOs = null;
		TubesUsedDAO tubesUsedDAO=new TubesUsedDAO();
		fromDate=dateDTO.getFromDate();
		toDate=dateDTO.getToDate();
		tubesUsedDTOs = (List<TubesUsedDTO>) tubesUsedDAO.getUsedTubeDetails(dateDTO);
		Double sumOfTubeUsed=0.0;
		for (TubesUsedDTO tubesUsedDTO : tubesUsedDTOs) {
			sumOfTubeUsed=CommonRef.roundTwoDecimals(sumOfTubeUsed+Double.parseDouble(tubesUsedDTO.getTubesUsed()));
		}
		ModelAndView model1 = new ModelAndView("TubesUsedReport");
		model1.addObject("sumOfTubeUsed", sumOfTubeUsed);
		model1.addObject("tubesUsedDTOs", tubesUsedDTOs);
		return model1;
	}
	
	@RequestMapping("/{tubeId}/viewTubesUsedDetails.htm")
	public ModelAndView viewTubesUsedDetails(@PathVariable String tubeId) throws SQLException

	{
		List<TubesUsedDTO> tubesUsedDTOs = null;
		TubesUsedDAO tubesUsedDAO=new TubesUsedDAO();
		tubesUsedDTOs = (List<TubesUsedDTO>) tubesUsedDAO.getCompleteTubeDetails(tubeId,fromDate,toDate);
		ModelAndView model1 = new ModelAndView("ViewTubesUsedDetails");
		model1.addObject("tubesUsedDTOs", tubesUsedDTOs);
		return model1;
	}
	
	@RequestMapping("/openTubeMaster.htm")
	public ModelAndView openTubeMaster() throws SQLException {
		Set<String> focusCodes = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		ModelAndView model1 = new ModelAndView("tubeMaster");
		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<ArticleMasterDTO> articleMasterDTOs = ArticleController.getArticleMasterDetails();
		for (ArticleMasterDTO articleMasterDTO : articleMasterDTOs) {
			focusCodes.add(articleMasterDTO.getFocusCode());
		}
		
		
		List<TubeMasterDTO> tubeMasterDTOs = null;
		tubeMasterDTOs = getTubeMasterDetails();
		model1.addObject("tubeMasterDTOs", tubeMasterDTOs);
		model1.addObject("focusCodes", focusCodes);
		model1.addObject("userIdList", userIdList);
		return model1;
	}
	
	@RequestMapping("/addTubeMaster.htm")
	public ModelAndView addTubeMaster(
			@ModelAttribute("TubeMasterDTO") TubeMasterDTO tubeMasterDTO)
			throws SQLException {

		String status = "tubeMaster";
		ModelAndView model1 = new ModelAndView(status);

		String displayMessage = tubeMasterDAO.addTubeMaster(tubeMasterDTO);

			Set<String> focusCodes = new TreeSet<String>();
			Set<String> userIdList = new TreeSet<String>();
			List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
			for (EmployeeDTO employeeDTO : employeeDTOs) {

				userIdList.add(employeeDTO.getUserId());
			}
			List<ArticleMasterDTO> articleMasterDTOs = ArticleController.getArticleMasterDetails();
			for (ArticleMasterDTO articleMasterDTO : articleMasterDTOs) {
				focusCodes.add(articleMasterDTO.getFocusCode());
			}

			List<TubeMasterDTO> tubeMasterDTOs = null;
			tubeMasterDTOs = getTubeMasterDetails();
			model1.addObject("tubeMasterDTOs", tubeMasterDTOs);
			model1.addObject("focusCodes", focusCodes);
			model1.addObject("userIdList", userIdList);
			model1.addObject("displayMessage", displayMessage);

		return model1;
	}
	
	@RequestMapping("/updateTubeData.htm")
	public ModelAndView updateTubeData(
			@ModelAttribute("TubeMasterDTO") TubeMasterDTO tubeMasterDTO, HttpServletRequest request)
			throws SQLException, ParseException {

		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage= tubeMasterDAO.updateTubeMaster(tubeMasterDTO,userId);
		String status="viewTubeMaster";
		ModelAndView model1 = new ModelAndView(status);
		
		List<TubeMasterDTO> tubeMasterDTOs = null;
		tubeMasterDTOs = getTubeMasterDetails();
		model1.addObject("tubeMasterDTOs", tubeMasterDTOs);
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
