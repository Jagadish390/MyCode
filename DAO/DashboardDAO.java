package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.java.DTO.GoodTonsDTO;
import com.java.DTO.PackedVialsReportGraphDTO;
import com.java.DTO.ProductionReportDTO;
import com.java.DTO.YieldGraphDTO;
import com.java.util.CommonRef;

public class DashboardDAO {

	public List<PackedVialsReportGraphDTO> getPackedVialsDetails(String value)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = new ArrayList<PackedVialsReportGraphDTO>();

		try {
			conn = CommonRef.getConnection();
			String fromDate = "";
			String toDate = "";
			if (value.equals("mtd")) {
				List<String> monthToDate = CommonRef.monthToDate();

				fromDate = monthToDate.get(0);
				toDate = monthToDate.get(1);
			} else {
				List<String> yearToDate = CommonRef.yearToDate();
				fromDate = yearToDate.get(0);
				toDate = yearToDate.get(1);
			}
			String query = "select sum(GoodVials) as GoodVials,PDate from packdata where BatchNumber not in ('Holiday') and pdate between ? and ? group by PDate";
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				{
					PackedVialsReportGraphDTO packedVialsReportGraphDTO = new PackedVialsReportGraphDTO();
					packedVialsReportGraphDTO.setPackingDate(CommonRef
							.dbToUiDate(String.valueOf(resultSet.getDate(2)))
							.substring(0, 5));
					packedVialsReportGraphDTO.setGoodVials(resultSet
							.getString(1));

					packedVialsReportGraphDTOs.add(packedVialsReportGraphDTO);
				}
			}

			return packedVialsReportGraphDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return packedVialsReportGraphDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public List<GoodTonsDTO> getGoodTonsGraph(String value) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<GoodTonsDTO> goodTonsDTOs = new ArrayList<GoodTonsDTO>();
		try {

			conn = CommonRef.getConnection();
			String fromDate = "";
			String toDate = "";
			if (value.equals("mtd")) {
				List<String> monthToDate = CommonRef.monthToDate();

				fromDate = monthToDate.get(0);
				toDate = monthToDate.get(1);
			} else {
				List<String> yearToDate = CommonRef.yearToDate();
				fromDate = yearToDate.get(0);
				toDate = yearToDate.get(1);
			}
			String query = "select pd.Pdate,(am.Weight*pd.GoodVials)/1000 as 'GoodKg' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.BatchNumber not in ('Holiday') and pd.PDate between ? and ? group by pd.Pdate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				GoodTonsDTO goodDTO = new GoodTonsDTO();
				goodDTO.setPdate(CommonRef.dbToUiDate(
						String.valueOf(resultSet.getDate(1))).substring(0, 5));
				goodDTO.setGoodKg(resultSet.getString(2));

				goodTonsDTOs.add(goodDTO);

			}

			return goodTonsDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return goodTonsDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public List<YieldGraphDTO> getYieldDetails(String value)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<YieldGraphDTO> yieldGraphDTOs = new ArrayList<YieldGraphDTO>();
		try {
			conn = CommonRef.getConnection();
			String fromDate = "";
			String toDate = "";
			if (value.equals("mtd")) {
				List<String> monthToDate = CommonRef.monthToDate();

				fromDate = monthToDate.get(0);
				toDate = monthToDate.get(1);
			} else {
				List<String> yearToDate = CommonRef.yearToDate();
				fromDate = yearToDate.get(0);
				toDate = yearToDate.get(1);
			}
		//	System.out.println("From Date" + fromDate);
		//	System.out.println("To Date:" + toDate);
			String query = "SELECT pd.Pdate,ROUND((sum(am.Weight*pd.GoodVials)/1000/SUM(`Tubes_Used`)*100),2) as 'yield' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on prd.PDate=pd.Pdate and prd.Shift=pd.Shift and prd.BatchNumber=pd.BatchNumber where prd.BatchNumber!='Holiday' and pd.PDate between ? and ? group by pd.Pdate";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(fromDate));
			pstmt.setString(2, CommonRef.formatDate(toDate));

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				YieldGraphDTO yieldGraphDTO2 = new YieldGraphDTO();
				yieldGraphDTO2.setYieldDate(CommonRef.dbToUiDate(
						String.valueOf(resultSet.getDate(1))).substring(0, 5));
				yieldGraphDTO2.setYield(resultSet.getString(2));
				yieldGraphDTOs.add(yieldGraphDTO2);
			}

			return yieldGraphDTOs;

		} catch (Exception e) {
			e.printStackTrace();
			return yieldGraphDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public List<ProductionReportDTO> getOAEGraph(String value) throws Exception {

		Connection conn = null;
		// ModelAndView modelAndView = new ModelAndView("OAEGraph");
		PreparedStatement pstmt = null;
		conn = CommonRef.getConnection();
		String fromDate = "";
		String toDate = "";
		if (value.equals("mtd")) {
			List<String> monthToDate = CommonRef.monthToDate();

			fromDate = monthToDate.get(0);
			toDate = monthToDate.get(1);
		} else {
			List<String> yearToDate = CommonRef.yearToDate();
			fromDate = yearToDate.get(0);
			toDate = yearToDate.get(1);
		}
		String query;
		int noOfHolidays = CommonRef.getHolidays(fromDate, toDate);
	//	System.out.println("In GetOAE");

		if (!(fromDate.equals(toDate))) {
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT sum(Tubes_Used) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',(DATEDIFF(?,?)+1-?)*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
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
			query = "select a.UsedKg,a.MachineNumber,a.NovisProduced,a.NovisAccepted,c.TimeLoss,b.GoodKg,b.TotalTime,b.TotalPacked from (SELECT sum(Tubes_Used) as 'UsedKg', bm.Mc_Num as 'MachineNumber', sum(Novis_Production) as 'NovisProduced',sum(Novis_Good)as 'NovisAccepted', PDate as 'ProductionDate' FROM batchmaster bm JOIN productiondata pd ON pd.BatchNumber=bm.BatchNumber where pd.PDate between ? and ? group  by bm.Mc_Num) as a inner join (select bm.Mc_Num as 'MachineNumber',sum((am.Weight*pd.GoodVials)/1000) as 'GoodKg',1*3*480 as 'TotalTime',sum(GoodVials) as 'TotalPacked' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? group by bm.Mc_Num) as b on a.MachineNumber=b.MachineNumber join (select sum(Time_Lost) as 'TimeLoss',bm.Mc_Num as 'MachineNumber' from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber where md.Mdate between ? and ?  group  by bm.Mc_Num)as c on a.MachineNumber=c.MachineNumber where a.MachineNumber not in ('00','0') ";
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
				.getPerformanceDetails(fromDate, toDate);

		List<ProductionReportDTO> productionReportDTOs = ProductionReportExcel
				.convertEntityToDTO(listProdData, performanceActual);
		return productionReportDTOs;

	}

}
