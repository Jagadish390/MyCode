package com.java.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.ArticleMasterDAO;
import com.java.DAO.BaseDAO;
import com.java.DAO.BatchMasterDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.LookupDAO;
import com.java.DAO.ProductionDAO;
import com.java.DAO.ProductionReportExcel;
import com.java.DTO.BatchDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.LookUpDTO;
import com.java.DTO.ProductionDTO;
import com.java.DTO.ProductionReportDTO;
import com.java.util.CommonRef;

@Controller
@Scope("session")
public class ProductionController {

	ProductionDAO productionDAO = new ProductionDAO();
	private static final Logger logger = Logger
			.getLogger(ProductionController.class.getName());

	@RequestMapping("/openProductionMaster.htm")
	public ModelAndView openProductionMaster() throws SQLException {
		ModelAndView model1 = new ModelAndView("productionData");
		Set<String> batchNumbers = new TreeSet<String>();
		Set<String> userIdList = new TreeSet<String>();
		Set<String> types = new TreeSet<String>();
		Set<String> productionEngineersList=new HashSet<String>();
		Set<String> productionMasterList=new HashSet<String>();

		List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
		for (EmployeeDTO employeeDTO : employeeDTOs) {

			userIdList.add(employeeDTO.getUserId());
		}
		List<EmployeeDTO> employeeDTOs2 = EmployeeDAO.getProductionMasters();
		for(EmployeeDTO employeeDTO1:employeeDTOs2){
			productionMasterList.add(employeeDTO1.getUserId());
		}
		List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getProductionEngineers();
		for(EmployeeDTO employeeDTO1:employeeDTOs1){
			productionEngineersList.add(employeeDTO1.getUserId());
		}

		List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails2();
		for (BatchDTO batchDTO : batchDTOs) {
			batchNumbers.add(batchDTO.getBatchNumber());
		}
		List<LookUpDTO> lookUpDTOs = LookupDAO.getLookUpDetails();
		for (LookUpDTO lookUpDTO : lookUpDTOs) {
			if(lookUpDTO.getParam().equalsIgnoreCase("production data")){
				types.add(lookUpDTO.getData());
			}
			
		}
		model1.addObject("productionMasterList", productionMasterList);
		model1.addObject("batchNumbers", batchNumbers);
		model1.addObject("userIdList", userIdList);
		model1.addObject("types", types);
		model1.addObject("productionEngineersList", productionEngineersList);

		List<ProductionDTO> productionDTOs = null;
		productionDTOs = getProductionDetails1();
		model1.addObject("productionDTOs", productionDTOs);

		return model1;
	}

	public static List<ProductionDTO> getProductionDetails()
			throws SQLException {

		List<ProductionDTO> productionDTOs = null;
		ProductionDAO productionDAO = new ProductionDAO();
		productionDTOs = (List<ProductionDTO>) productionDAO
				.getProductionDetails();

		return productionDTOs;
	}
	
	public static List<ProductionDTO> getProductionDetails1()
			throws SQLException {

		List<ProductionDTO> productionDTOs = null;
		ProductionDAO productionDAO = new ProductionDAO();
		productionDTOs = (List<ProductionDTO>) productionDAO
				.getProductionDetails1();

		return productionDTOs;
	}

	@RequestMapping("/viewProducitonMaster.htm")
	public ModelAndView viewProductionMaster() throws SQLException

	{
		List<ProductionDTO> productionDTOs = null;
		productionDTOs = getProductionDetails();
		ModelAndView model1 = new ModelAndView("viewProductionMaster");
		model1.addObject("productionDTOs", productionDTOs);
		return model1;
	}

	@RequestMapping("/updateProductionData.htm")
	public ModelAndView updateProductionMaster(
			@ModelAttribute("ProductionDTO") ProductionDTO productionDTO, HttpServletRequest request)
			throws SQLException {

		String userId= (String) request.getSession().getAttribute("UserName");
		String displayMessage = productionDAO.updatePackingMaster(productionDTO,userId);
		String status="viewProductionMaster";
		ModelAndView model1 = new ModelAndView(status);
		
		List<ProductionDTO> productionDTOs = null;
		productionDTOs = getProductionDetails();
		model1.addObject("productionDTOs", productionDTOs);
		model1.addObject("displayMessage", displayMessage);
		
		return model1;

	}

	@RequestMapping("/addProductionMaster.htm")
	public ModelAndView addProductionMaster(
			@ModelAttribute("ProductionDTO") ProductionDTO productionDTO)
			throws SQLException {

		String displayMessage = productionDAO.addProductionMaster(productionDTO);
		String status="productionData";
		ModelAndView model1 = new ModelAndView(status);
		Set<String> types = new TreeSet<String>();
			Set<String> batchNumbers = new TreeSet<String>();
			Set<String> userIdList = new TreeSet<String>();
			Set<String> productionEngineersList=new HashSet<String>();
			Set<String> productionMasterList=new HashSet<String>();
			List<EmployeeDTO> employeeDTOs = EmployeeDAO.viewEmployees();
			for (EmployeeDTO employeeDTO : employeeDTOs) {

				userIdList.add(employeeDTO.getUserId());
			}
			List<EmployeeDTO> employeeDTOs2 = EmployeeDAO.getProductionMasters();
			for(EmployeeDTO employeeDTO1:employeeDTOs2){
				productionMasterList.add(employeeDTO1.getUserId());
			}

			List<BatchDTO> batchDTOs = BatchMasterDAO.getBatchMasterDetails2();
			for (BatchDTO batchDTO : batchDTOs) {
				batchNumbers.add(batchDTO.getBatchNumber());
			}
			List<EmployeeDTO> employeeDTOs1 = EmployeeDAO.getProductionEngineers();
			for(EmployeeDTO employeeDTO1:employeeDTOs1){
				productionEngineersList.add(employeeDTO1.getUserId());
			}
			List<LookUpDTO> lookUpDTOs = LookupDAO.getLookUpDetails();
			for (LookUpDTO lookUpDTO : lookUpDTOs) {
				if(lookUpDTO.getParam().equalsIgnoreCase("production data")){
					types.add(lookUpDTO.getData());
				}
				
			}
			model1.addObject("productionMasterList", productionMasterList);
			model1.addObject("productionEngineersList", productionEngineersList);
			model1.addObject("batchNumbers", batchNumbers);
			model1.addObject("userIdList", userIdList);
			
			model1.addObject("types", types);
			model1.addObject("displayMessage", displayMessage);
			List<ProductionDTO> productionDTOs = null;
			productionDTOs = getProductionDetails1();
			model1.addObject("productionDTOs", productionDTOs);


		return model1;
	}
	
	

	@RequestMapping(value = "/generateOAEReport.htm", method = RequestMethod.POST)
	public ModelAndView generateOAEReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("OAEGraph");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
		
		CommonRef commonRef = new CommonRef();

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			// create a statement
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {

			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);

		modelAndView.addObject("productionReportDTOs", productionReportDTOs);

		return modelAndView;
	}

	@RequestMapping(value = "/generateOEEReport.htm", method = RequestMethod.POST)
	public ModelAndView generateOEEReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("OEEGraphs");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
		
		CommonRef commonRef = new CommonRef();

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			// create a statement
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {

			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);

		modelAndView.addObject("productionReportDTOs", productionReportDTOs);

		return modelAndView;
	}

	@RequestMapping(value = "/generateNovisYieldGraph.htm", method = RequestMethod.POST)
	public ModelAndView generateNovisYieldGraph(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("NovisYieldGraph");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
		
		

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			// create a statement
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {

			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);

		modelAndView.addObject("productionReportDTOs", productionReportDTOs);

		return modelAndView;
	}

	@RequestMapping(value = "/generateUtilizationGraph.htm", method = RequestMethod.POST)
	public ModelAndView generateUtilizationGraph(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("UtilisationGraph");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
		

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			// create a statement
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {

			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);

		modelAndView.addObject("productionReportDTOs", productionReportDTOs);

		return modelAndView;
	}
	
	@RequestMapping(value = "/KPIReport.htm", method = RequestMethod.GET)
	public ModelAndView KPIReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("OpenProductionGraph");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
		
		CommonRef commonRef = new CommonRef();

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,ROUND(b.GoodKg,2),b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			// create a statement
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,ROUND(b.GoodKg,2),b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {

			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);

		modelAndView.addObject("productionReportDTOs", productionReportDTOs);
		modelAndView.addObject("size", productionReportDTOs.size());

		return modelAndView;
	}

	@RequestMapping(value = "/productionReport1.htm", method = RequestMethod.GET)
	public ModelAndView productionReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		ModelAndView modelAndView = new ModelAndView("excelReportView");
		PreparedStatement pstmt = null;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String query = null;
		int noOfHolidays=CommonRef.getHolidays(fromDate,toDate);
	
		if (!(fromDate.equals(toDate))) {

			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";

			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(fromDate));
			pstmt.setInt(5, noOfHolidays);
			pstmt.setString(6, CommonRef.formatDate(fromDate));
			pstmt.setString(7, CommonRef.formatDate(toDate));
			pstmt.setString(8, CommonRef.formatDate(fromDate));
			pstmt.setString(9, CommonRef.formatDate(toDate));
		} else {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT ROUND(sum(Tubes_Used),2) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0')  ";
			conn = CommonRef.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));
			pstmt.setString(4, CommonRef.formatDate(toDate));
			pstmt.setString(3, CommonRef.formatDate(fromDate));
			pstmt.setString(5, CommonRef.formatDate(fromDate));
			pstmt.setString(6, CommonRef.formatDate(toDate));
		}

		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionReportDTO> listProdData = new ArrayList<ProductionReportDTO>();
		while (resultSet.next()) {
			
			String totalTime = resultSet.getString(7);
			String usedKg = resultSet.getString(1);
			String novisProduced = resultSet.getString(3);
			String novisAccepted = resultSet.getString(4);
			String timeLoss = resultSet.getString(5);
			String machineNumber = resultSet.getString(2);
			String totalPacked = resultSet.getString(8);
			String goodKg = resultSet.getString(6);

			listProdData.add(new ProductionReportDTO(machineNumber, usedKg,
					goodKg, novisProduced, novisAccepted, totalPacked,
					timeLoss, totalTime));
		}

		ArticleMasterDAO articleMasterDAO = new ArticleMasterDAO();
		List<Double> performanceActual = articleMasterDAO
				.getPerformanceDetails(fromDate,toDate);

		modelAndView.addObject("listProdData", listProdData);
		modelAndView.addObject("performanceActual", performanceActual);

		return modelAndView;
	}

	@RequestMapping(value = "/downloadExcel.htm", method = RequestMethod.GET)
	public ModelAndView downloadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// create some sample data
		Connection conn = null;
		PreparedStatement pstmt = null;
		

		String query = "select Pdate,Shift,BatchNumber,Novis_Production,Novis_Good,Novis_Rot_Rej,Novis_Lien_Rej,Tubes_Used,Time_Lost,Prd_Reason,Reason_Detail,Glass_Waste,Activity_Time,Engineer_Prd,UserId from productiondata";
		conn = CommonRef.getConnection();
		pstmt = conn.prepareStatement(query); // create a statement
		ResultSet resultSet = pstmt.executeQuery();
		List<ProductionDTO> listProdData = new ArrayList<ProductionDTO>();
		while (resultSet.next()) {
			
			String pDate = resultSet.getString(1);
			String shift = resultSet.getString(2);
			String batchNumber = resultSet.getString(3);
			String novisProduction = resultSet.getString(4);
			String novisGood = resultSet.getString(5);
			String novisRotRej = resultSet.getString(6);
			String novisLienRej = resultSet.getString(7);
			String tubesUsed = resultSet.getString(8);
			String timeLost = resultSet.getString(9);
			String prdReason = resultSet.getString(10);
			String reasonDetail = resultSet.getString(11);
			String glassWaste = resultSet.getString(12);
			String activityTime = resultSet.getString(13);
			String engineerPrd = resultSet.getString(14);
			String userId = resultSet.getString(15);

			listProdData.add(new ProductionDTO(pDate, shift, batchNumber,
					novisProduction, novisGood, novisRotRej, novisLienRej,
					tubesUsed, timeLost, prdReason, reasonDetail, glassWaste,
					activityTime, engineerPrd, userId));
		}
		return new ModelAndView("excelView", "listProdData", listProdData);
	}

	@RequestMapping("/openOAEReport.htm")
	public ModelAndView openOAEReport() {
		ModelAndView model1 = new ModelAndView("OAEGraph");
		return model1;
	}

	@RequestMapping("/openOEEReport.htm")
	public ModelAndView openOEEReport() {
		ModelAndView model1 = new ModelAndView("OEEGraphs");
		return model1;
	}

	@RequestMapping("/openNovisReport.htm")
	public ModelAndView openNovisReport() {
		ModelAndView model1 = new ModelAndView("NovisYieldGraph");
		return model1;
	}

	@RequestMapping("/openUtilizationReport.htm")
	public ModelAndView openUtilizationReport() {
		ModelAndView model1 = new ModelAndView("UtilisationGraph");
		return model1;
	}

	@RequestMapping("/openProductionReport.htm")
	public ModelAndView openProductionReport() {
		ModelAndView model1 = new ModelAndView("OpenProductionGraph");
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
