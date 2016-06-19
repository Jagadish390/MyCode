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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.ArticleMasterDAO;
import com.java.DAO.BaseDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.EmployeeDTO;
import com.java.util.CommonRef;

@Controller
@Scope("session")
public class ArticleController {

	EmployeeDAO employeeDAO = new EmployeeDAO();
	ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
	
	private static final Logger logger = Logger
			.getLogger(ArticleController.class.getName());

	@RequestMapping("/openArticleMaster.htm")
	public ModelAndView openArticleMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("articleMaster");

		Set<String> userIdList = new TreeSet<String>();

		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}

		// view Article Details

		List<ArticleMasterDTO> articleMasterDTOs = null;
		articleMasterDTOs = ArticleController.getArticleMasterDetails();
		model1.addObject("articleMasterDTOs", articleMasterDTOs);

		model1.addObject("userIdList", userIdList);
		return model1;
	}

	public static List<ArticleMasterDTO> getArticleMasterDetails()
			throws SQLException {
		List<ArticleMasterDTO> articleMasterDTOs = null;
		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		articleMasterDTOs = (List<ArticleMasterDTO>) articleMasterDAO
				.getArticleMasterDetails();

		return articleMasterDTOs;
	}

	@RequestMapping("/viewArticleMaster.htm")
	public ModelAndView viewArticleMaster() throws SQLException

	{
		List<ArticleMasterDTO> articleMasterDTOs = null;
		articleMasterDTOs = ArticleController.getArticleMasterDetails();
		ModelAndView model1 = new ModelAndView("viewArticleMaster");
		model1.addObject("articleMasterDTOs", articleMasterDTOs);
		return model1;
	}

	@RequestMapping("/addArticleMaster.htm")
	public ModelAndView addArticleMaster(
			@ModelAttribute("ArticleMasterDTO") ArticleMasterDTO articleMasterDTO)
			throws Exception {

		String status = "articleMaster";
		String displayMessage = null;
		ModelAndView model1 = new ModelAndView();

		displayMessage = articleMasterDAO.addArticleMaster(articleMasterDTO);
		model1.setViewName(status);

		model1.addObject("displayMessage", displayMessage);

		// view Article Details

		List<ArticleMasterDTO> articleMasterDTOs = null;
		articleMasterDTOs = ArticleController.getArticleMasterDetails();
		model1.addObject("articleMasterDTOs", articleMasterDTOs);

		return model1;
	}

	@RequestMapping("/updateArticleData.htm")
	public ModelAndView updateArticleData(
			@ModelAttribute("ArticleMasterDTO") ArticleMasterDTO articleMasterDTO,HttpServletRequest request)
			throws SQLException, ParseException {

		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = articleMasterDAO
				.updateArticleMaster(articleMasterDTO,userId);
		String status = "viewArticleMaster";
		ModelAndView model1 = new ModelAndView(status);

		List<ArticleMasterDTO> articleMasterDTOs = null;
		articleMasterDTOs = ArticleController.getArticleMasterDetails();
		model1.addObject("articleMasterDTOs", articleMasterDTOs);
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
