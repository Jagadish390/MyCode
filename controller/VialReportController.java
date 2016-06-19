package com.java.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BatchMasterDAO;
import com.java.DAO.VialsReportDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.BatchDTO;
import com.java.DTO.BatchDetailsDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.VialsReportDTO;
import com.java.DTO.VialsReportDetailDTO;

@Controller
@Scope("session")
public class VialReportController {
	
	VialsReportDAO vialsReportDAO=new VialsReportDAO();

	@RequestMapping("/openVialReport.htm")
	public ModelAndView openMonthlyReport() throws SQLException {
		ModelAndView model1 = new ModelAndView("viewVialReport");

		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());

		}
		model1.addObject("machineNumbers", machineNumbers);

		return model1;
	}

	@RequestMapping("/viewVialReport.htm")
	public ModelAndView viewVialReport(@ModelAttribute("VialsReportDTO")VialsReportDTO vialsReportDTO) throws SQLException {
		ModelAndView model1 = new ModelAndView("viewVialReport");

		Set<String> machineNumbers = new TreeSet<String>();
		List<MachineMasterDTO> machineMasterDTOs = MachineMasterController
				.getMachineMasterDetails();

		for (MachineMasterDTO machineMasterDTO : machineMasterDTOs) {
			machineNumbers.add(machineMasterDTO.getMachineNumber());
		}

		List<VialsReportDetailDTO> vialsReportDetailDTOs=vialsReportDAO.getVialReportDetails(vialsReportDTO);
	//	System.out.println("vialsReopersdvn"+vialsReportDetailDTOs.size());
		model1.addObject("machineNumbers", machineNumbers);
		model1.addObject("vialsReportDetailDTOs", vialsReportDetailDTOs);

		return model1;
	}

}
