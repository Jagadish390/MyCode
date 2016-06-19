package com.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.java.DAO.BaseDAO;
import com.java.DAO.ProductionDAO;
import com.java.DTO.ProductionDTO;

public class CommonRef {

	private static final Logger logger = Logger.getLogger(CommonRef.class
			.getName());

	public static Connection getConnection() throws Exception {

		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/Cogent";
		String username = "root";
		String password = "root";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public static Map<String, Integer> getMachinewiseTargetSpeedDetails() {
		Map<String, Integer> targetSpeed = new HashMap<String, Integer>();
		targetSpeed.put("S1", 58);
		targetSpeed.put("S2", 15);
		targetSpeed.put("S3", 58);
		targetSpeed.put("S4", 48);
		targetSpeed.put("S5", 80);
		targetSpeed.put("S6", 80);

		return targetSpeed;
	}

	public static boolean findDuplication(String tableName, String primaryKey,
			String value) throws Exception {
		Boolean isValid = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = getConnection();
		String query = "SELECT * FROM " + tableName + " where " + primaryKey
				+ "='" + value + "'";

		pstmt = conn.prepareStatement(query); // create a statement
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()) {
			isValid = true;
		}

		return isValid;
	}

	public static int getHolidays(String fromDate, String toDate)
			throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int noOfHolidays = 0;
		conn = getConnection();

		String query = "select round(count(1)/3) from productiondata where batchnumber='Holiday' and Pdate between ? and ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, CommonRef.formatDate(fromDate));
		pstmt.setString(2, CommonRef.formatDate(toDate));
		// create a statement
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()) {
			noOfHolidays = resultSet.getInt(1);

		}
	//	System.out.println("Holidays :" + noOfHolidays);
		return noOfHolidays;
	}

	public static String formatDate(String fromDate) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = dateFormat.parse(fromDate);
		String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);

		return dbDate;

	}

	public static String dbToUiDate(String DBDate) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(DBDate);
		String UIDate = new SimpleDateFormat("dd-MM-yyyy").format(parsedDate);

		return UIDate;

	}

	public static String getMachineNumber(String batchNumber) throws Exception {
		String machineNumber = "";
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = getConnection();
		String query = "select mc_num from batchmaster where batchnumber=?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, batchNumber);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()) {
			machineNumber = resultSet.getString(1);

		}
		return machineNumber;
	}

	public static String getRejectedVials(String packingScrap,
			String batchNumber) throws Exception {
		int rejectedVials = (int) 0.0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		Double vialWeight = 0.0;
		conn = getConnection();
		String query = "select am.Weight from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code where bm.BatchNumber=?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, batchNumber);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()) {
			vialWeight = resultSet.getDouble(1);

		}
		/*System.out.println("Packing Scrap:" + packingScrap);
		System.out.println("Vial Weight:" + vialWeight);
		System.out.println("Rejected Vials:" + rejectedVials);*/

		rejectedVials = (int) ((Double.parseDouble(packingScrap))
				/ (vialWeight) * (1000));

		return rejectedVials + "";
	}

	public static String setHoliday(String date, String userId)
			throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = CommonRef.getConnection();
			// Production data
			String query = "insert into productiondata(Pdate, Shift, BatchNumber, Novis_Production, Novis_Good, Novis_Rot_Rej, Novis_Lien_Rej, Tubes_Used, Time_Lost, clockrate, ProductionMaster, Glass_Waste, Activity_Time, Engineer_Prd, UserId, type, VialsPerTube) values(?, 'A', 'Holiday', '0', '0', '0', '0', '0', NULL, '0', 'mani', '0', NULL, 'mani',?, NULL, '0')";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query1 = "insert into productiondata(Pdate, Shift, BatchNumber, Novis_Production, Novis_Good, Novis_Rot_Rej, Novis_Lien_Rej, Tubes_Used, Time_Lost, clockrate, ProductionMaster, Glass_Waste, Activity_Time, Engineer_Prd, UserId, type, VialsPerTube) values(?, 'B', 'Holiday', '0', '0', '0', '0', '0', NULL, '0', 'mani', '0', NULL, 'mani',?, NULL, '0')";
			pstmt = conn.prepareStatement(query1);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query2 = "insert into productiondata(Pdate, Shift, BatchNumber, Novis_Production, Novis_Good, Novis_Rot_Rej, Novis_Lien_Rej, Tubes_Used, Time_Lost, clockrate, ProductionMaster, Glass_Waste, Activity_Time, Engineer_Prd, UserId, type, VialsPerTube) values(?, 'C', 'Holiday', '0', '0', '0', '0', '0', NULL, '0', 'mani', '0', NULL, 'mani',?, NULL, '0')";
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();

			// Maintenance data

			String query3 = "insert into maintenancedata(Batch_Num, Mdate, Shift, Mc_Num, Time_Lost, Maint_Reason, Detail, Eng_Maint, UserID, type, total_time) values('Holiday', ?, 'A', '0', '0', '', '', '', ?, NULL, '480')";
			pstmt = conn.prepareStatement(query3);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query4 = "insert into maintenancedata(Batch_Num, Mdate, Shift, Mc_Num, Time_Lost, Maint_Reason, Detail, Eng_Maint, UserID, type, total_time) values('Holiday', ?, 'B', '0', '0', '', '', '', ?, NULL, '480')";
			pstmt = conn.prepareStatement(query4);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query5 = "insert into maintenancedata(Batch_Num, Mdate, Shift, Mc_Num, Time_Lost, Maint_Reason, Detail, Eng_Maint, UserID, type, total_time) values('Holiday', ?, 'C', '0', '0', '', '', '', ?, NULL, '480')";
			pstmt = conn.prepareStatement(query5);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();

			// Packing Data
			String query6 = "insert into packdata(BatchNumber, PDate, Shift, McNumber, GoodVials, Rej_Vials, Packing_Scrap, Mcn_No, Mcn_Qty, Pack_Supervisior, UserID, type,IPQC) values('Holiday', ?, 'A', '0', '0', '0', '0', '0', '0', '', ?, NULL,'')";
			pstmt = conn.prepareStatement(query6);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query7 = "insert into packdata(BatchNumber, PDate, Shift, McNumber, GoodVials, Rej_Vials, Packing_Scrap, Mcn_No, Mcn_Qty, Pack_Supervisior, UserID, type,IPQC) values('Holiday', ?, 'B', '0', '0', '0', '0', '0', '0', '', ?, NULL,'')";
			pstmt = conn.prepareStatement(query7);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			String query8 = "insert into packdata(BatchNumber, PDate, Shift, McNumber, GoodVials, Rej_Vials, Packing_Scrap, Mcn_No, Mcn_Qty, Pack_Supervisior, UserID, type,IPQC) values('Holiday', ?, 'C', '0', '0', '0', '0', '0', '0', '', ?, NULL,'')";
			pstmt = conn.prepareStatement(query8);
			pstmt.setString(1, date);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			return "Production,maintenance,packing details added Successfully.";
		} catch (Exception e) {
			int i = BaseDAO.getLogId();
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + i);
			if (e.getMessage().contains("Duplicate entry")) {
				return "Trying to add duplicate entry";
			} else {
				logger.info("Unable to add the production details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add theProduction,maintenance,packing details report the log Id with admins :-"
						+ i;

			}

		}
	}

	public static List<String> yearToDate() {

		List<String> yearToDate = new ArrayList<String>();

		Calendar now = Calendar.getInstance();

		int currentYear = now.get(Calendar.YEAR);
		Integer currentMonth = (now.get(Calendar.MONTH) + 1);
		Integer currentDay = now.get(Calendar.DATE);
		String strCurrentMonth = "";
		String strCurrentDay = "";
		if (currentDay < 10) {
			strCurrentDay = "0" + currentDay;
		} else {
			strCurrentDay = currentDay.toString();
		}
		if (currentMonth < 10) {
			strCurrentMonth = "0" + currentMonth;
		} else {
			strCurrentMonth = currentMonth.toString();
		}

		String startDayOfYear = "01";
		String startMonthOfYear = "01";

		String currentDate = strCurrentDay + "-" + strCurrentMonth + "-"
				+ currentYear;
		String startDateOfYear = startDayOfYear + "-" + startMonthOfYear + "-"
				+ currentYear;

	/*	System.out.println("Current date of year:-" + currentDate);
		System.out.println("Start date of year:-" + startDateOfYear);*/

		yearToDate.add(startDateOfYear);
		yearToDate.add(currentDate);

		return yearToDate;
	}

	public static List<String> monthToDate() {

		List<String> monthToDate = new ArrayList<String>();

		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		Integer currentMonth = (now.get(Calendar.MONTH) + 1);
		Integer currentDay = now.get(Calendar.DATE);

		String strCurrentMonth = "";
		String strCurrentDay = "";
		if (currentDay < 10) {
			strCurrentDay = "0" + currentDay;
		} else {
			strCurrentDay = currentDay.toString();
		}
		if (currentMonth < 10) {
			strCurrentMonth = "0" + currentMonth;
		} else {
			strCurrentMonth = currentMonth.toString();
		}

		String startDayOfYear = "01";

		String currentDate = strCurrentDay + "-" + strCurrentMonth + "-"
				+ currentYear;
		String startDateOfMonth = startDayOfYear + "-" + strCurrentMonth + "-"
				+ currentYear;

/*		System.out.println("Current date of year:-" + currentDate);
		System.out.println("Start date of year:-" + startDateOfMonth);*/

		monthToDate.add(startDateOfMonth);
		monthToDate.add(currentDate);

		return monthToDate;
	}

	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

}
