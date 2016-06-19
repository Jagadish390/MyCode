package com.java.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BaseDAO;
import com.java.DAO.GoodTonsDAO;
import com.java.DAO.VialsReportDAO;
import com.java.DTO.GoodTonsDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.VialsReportDTO;
import com.java.DTO.VialsReportDetailDTO;

@Controller
@Scope("session")
public class GoodTonsGraphController {

	GoodTonsDAO goodTonsDAO=new GoodTonsDAO(); 
	private static final Logger logger = Logger
			.getLogger(GoodTonsGraphController.class.getName());

	@RequestMapping("/openGoodTons.htm")
	public ModelAndView openGoodTons() throws SQLException {
		ModelAndView model1 = new ModelAndView("openGoodTonsGraph");

		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());

		}
		model1.addObject("machineNumbers", machineNumbers);

		return model1;
	}

	@RequestMapping("/viewGoodTonsGraph.htm")
	public ModelAndView viewGoodTonsGraph(
			@ModelAttribute("GoodTonsDTO") GoodTonsDTO goodTonsDTO)
			throws SQLException {
		ModelAndView model1 = new ModelAndView("openGoodTonsGraph");
		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());

		}
		model1.addObject("machineNumbers", machineNumbers);
		List<GoodTonsDTO> goodTonsDTOs=goodTonsDAO.getGoodTonsGraph(goodTonsDTO);
		
		
		
		model1.addObject("goodTonsDTOs", goodTonsDTOs);
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
