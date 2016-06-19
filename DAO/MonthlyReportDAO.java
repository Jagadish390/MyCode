package com.java.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.java.DTO.DateDTO;
import com.java.DTO.MonthlyReportDTO;
import com.java.DTO.ProductionDTO;
import com.java.util.CommonRef;

public class MonthlyReportDAO {
	public List<MonthlyReportDTO> getMonthlyReportDetails(HttpServletRequest request) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String frmDate = null;
		String toDate = null;
		List<MonthlyReportDTO> monthlyReportDTOs = new ArrayList<MonthlyReportDTO>();
		try {
			conn = CommonRef.getConnection();
			int iYear =Integer.parseInt(request.getParameter("year"));
			int iMonth = Integer.parseInt(request.getParameter("month"));
			int iDay = 1;

			// Create a calendar object and set year and month
			Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

			// Get the number of days in that month
			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
			iMonth=iMonth+1;
			frmDate=String.valueOf(iYear)+'-'+String.valueOf(iMonth)+'-'+String.valueOf(iDay);
			toDate=String.valueOf(iYear)+'-'+String.valueOf(iMonth)+'-'+String.valueOf(daysInMonth);
			
			String query = "select a.UsedKg, a.NovisProduction,a.NovisGood,a.TimeLost,b.GoodKg,b.GoodVials,b.RejectedVials,a.Glasswaste from  (select sum(tubes_used)as 'UsedKg',sum(novis_production) as 'NovisProduction',sum(novis_good) as 'NovisGood', sum(md.time_lost) as 'TimeLost', prd.PDate as 'Productiondate',sum(Glass_Waste) as 'Glasswaste' from productiondata prd  join maintenancedata md on prd.BatchNumber=md.batch_num and prd.pdate=md.mdate and prd.shift=md.shift where  prd.pdate between ? and ?) as a  inner join  (select sum((am.Weight*pck.GoodVials)/1000) as 'GoodKg',sum(goodvials) as 'GoodVials', sum(rej_vials) as 'RejectedVials', pck.pdate as 'PackingDate'  from packdata pck   join batchmaster bm on bm.BatchNumber=pck.BatchNumber   join articlemaster am on bm.ArticleId=am.Focus_Code  where pck.pdate between ? and ?) as b ";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, frmDate);
			pstmt.setString(2, toDate);
			pstmt.setString(3, frmDate);
			pstmt.setString(4, toDate);
			ResultSet resultSet = pstmt.executeQuery();
			int noOfHolidays=CommonRef.getHolidays(frmDate,toDate);
			int workingDays=daysInMonth-noOfHolidays;
			
			while (resultSet.next()) {

				{

					MonthlyReportDTO monthlyReportDTO = new MonthlyReportDTO();
					monthlyReportDTO.setTotalTubesUsed(resultSet.getString(1));
					monthlyReportDTO.setTotalTimeLoss(resultSet.getString(4));
					monthlyReportDTO.setTotalGoodVialsPacked(resultSet.getString(6));
					monthlyReportDTO.setGoodTons(resultSet.getString(3));
					monthlyReportDTO.setTotalRejected(resultSet.getString(7));
					monthlyReportDTO.setTotalWaste(resultSet.getString(8));
					monthlyReportDTO.setTotalWorkingDays(String.valueOf(workingDays));
					monthlyReportDTO.setTotalTime(String.valueOf(workingDays*480*3));
					monthlyReportDTOs.add(monthlyReportDTO);
				}
			}

			return monthlyReportDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return monthlyReportDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}
}
