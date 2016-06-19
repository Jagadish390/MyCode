package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.MaintainanceDTO;
import com.java.DTO.MaintenanceActivityDTO;
import com.java.DTO.MaintenanceUpdateTimeLost;
import com.java.util.CommonRef;

public class MaintainanceDAO {

	private static final Logger logger = Logger.getLogger(MaintainanceDAO.class
			.getName());

	@SuppressWarnings("resource")
	public String addMaintainanceMaster(MaintainanceDTO maintainanceDTO)
			throws SQLException, ParseException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		int totalTimeLost = 0;

		for (int i : maintainanceDTO.getTimeLosts()) {
			totalTimeLost = totalTimeLost + i;
		}

		String uiDate = maintainanceDTO.getMaintenanceDate();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = dateFormat.parse(uiDate);
		String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
		
		MaintenanceUpdateTimeLost maintenanceUpdateTimeLost = new MaintenanceUpdateTimeLost();

		try {
			if(maintainanceDTO.getBatchNumber().equals("Holiday")){
				String displayMessage=CommonRef.setHoliday(dbDate, maintainanceDTO.getUserId());
				return displayMessage;
			}else{
			
			
			conn = CommonRef.getConnection();
			String maintainenceReason = "";
			String details = "";

			for (int i = 0; i <= maintainanceDTO.getTimeLosts().size() - 1; i++) {

				if (maintainanceDTO.getTimeLosts().get(i) != 0) {

					maintainenceReason = maintainenceReason
							+ maintainanceDTO.getMaintenanceReasons().get(i)
							+ ",";
					details = details + maintainanceDTO.getDetails().get(i)
							+ ",";
				}
			}
			String queryForSelect = "select batch_num,mdate,shift,mc_num,id from maintenancedata where mc_num = '"
					+ CommonRef.getMachineNumber(maintainanceDTO
							.getBatchNumber())
					+ "' and id in(select max(id) from maintenancedata group by mc_num)";
			pstmt = conn.prepareStatement(queryForSelect);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				maintenanceUpdateTimeLost
						.setBatchNumber(resultSet.getString(1));
				maintenanceUpdateTimeLost.setMaintenanceDate(resultSet
						.getString(2));
				maintenanceUpdateTimeLost.setShift(resultSet.getString(3));
				maintenanceUpdateTimeLost.setMachineNumber(resultSet
						.getString(4));
				maintenanceUpdateTimeLost.setMaxId(resultSet.getString(5));

			}

			if (!(maintainanceDTO.getBatchNumber()
					.equals(maintenanceUpdateTimeLost.getBatchNumber()))) {

				String queryForStartTime = "select start_time from batchmaster where batchnumber = '"
						+ maintainanceDTO.getBatchNumber() + "'";
				PreparedStatement pstmt1 = conn
						.prepareStatement(queryForStartTime);

				// pstmt.setString(1, maintainanceDTO.getBatchNumber());
				ResultSet resultSetForStartTime = pstmt1.executeQuery();

				while (resultSetForStartTime.next()) {

					maintenanceUpdateTimeLost.setBatchStartTime(String
							.valueOf(resultSetForStartTime.getString(1)));
				}
			//	System.out.println(maintenanceUpdateTimeLost
					//	.getBatchStartTime() + "this is batchstart time1");

				if (maintainanceDTO.getShift().equalsIgnoreCase("A")) {
					maintenanceUpdateTimeLost.setShiftStartTime("6");
					maintenanceUpdateTimeLost.setShiftEndTime("14");
				} else if (maintainanceDTO.getShift().equalsIgnoreCase("B")) {
					maintenanceUpdateTimeLost.setShiftStartTime("14");
					maintenanceUpdateTimeLost.setShiftEndTime("22");
				} else if (maintainanceDTO.getShift().equalsIgnoreCase("C")) {
					maintenanceUpdateTimeLost.setShiftStartTime("22");
					maintenanceUpdateTimeLost.setShiftEndTime("6");
				}
				String batchStartTimeTemp = maintenanceUpdateTimeLost
						.getBatchStartTime();
				String[] hourMin = batchStartTimeTemp.split(":");
				int hour = Integer.parseInt(hourMin[0]);
				int mins = Integer.parseInt(hourMin[1]);
				double minsInHours = mins / 60;
				double batchChangeTime = hour + minsInHours;

				double workingTime = funciton1(batchChangeTime);

				double batchChangeTime1 = workingTime * 60;
				double batchChangeTime2 = 480 - batchChangeTime1;

				String totalTime = Double.toString(batchChangeTime1);

				String queryUpdate = " update maintenancedata set total_time = '"
						+ totalTime
						+ "' where id = '"
						+ maintenanceUpdateTimeLost.getMaxId() + "';";
				pstmt = conn.prepareStatement(queryUpdate);
				pstmt.executeUpdate();
				String totalTime1 = Double.toString(batchChangeTime2);

				String query = "insert into maintenancedata(Batch_Num,Mdate,Shift,Eng_Maint,UserID,type,Time_Lost,total_time,Mc_Num,maint_reason,detail) values(?,?,?,?,?,?,?,?,?,?,?)";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, maintainanceDTO.getBatchNumber());
				pstmt.setString(2, dbDate);
				pstmt.setString(3, maintainanceDTO.getShift());
				pstmt.setString(4, maintainanceDTO.getMaintenanceEngineer());
				pstmt.setString(5, maintainanceDTO.getUserId());
				pstmt.setString(6, maintainanceDTO.getType());
				pstmt.setInt(7, totalTimeLost);
				pstmt.setString(8, totalTime1);
				// pstmt.setString(9, maintainanceDTO.getMachineNumber());
				pstmt.setString(9, CommonRef.getMachineNumber(maintainanceDTO
						.getBatchNumber()));
				pstmt.setString(10, maintainenceReason);
				pstmt.setString(11, details);
				pstmt.executeUpdate();

			} else {

				String query = "insert into maintenancedata(Batch_Num,Mdate,Shift,Eng_Maint,UserID,type,Time_Lost,total_time,Mc_Num,maint_reason,detail) values(?,?,?,?,?,?,?,?,?,?,?)";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, maintainanceDTO.getBatchNumber());
				pstmt.setString(2, dbDate);
				pstmt.setString(3, maintainanceDTO.getShift());
				pstmt.setString(4, maintainanceDTO.getMaintenanceEngineer());
				pstmt.setString(5, maintainanceDTO.getUserId());
				pstmt.setString(6, maintainanceDTO.getType());
				pstmt.setInt(7, totalTimeLost);
				pstmt.setString(8, "480");
				pstmt.setString(9, CommonRef.getMachineNumber(maintainanceDTO
						.getBatchNumber()));
				pstmt.setString(10, maintainenceReason);
				pstmt.setString(11, details);

				pstmt.executeUpdate(); // execute insert statement
			}
			for (int i = 0; i <= maintainanceDTO.getTimeLosts().size() - 1; i++) {

				if (maintainanceDTO.getTimeLosts().get(i) != 0) {
					String query1 = "insert into maintenancedetail(batchNumber,mDate,shift,maintReason,detail,timelost) values(?,?,?,?,?,?)";

					pstmt = conn.prepareStatement(query1); // create a statement
					pstmt.setString(1, maintainanceDTO.getBatchNumber());
					pstmt.setString(2, dbDate);
					pstmt.setString(3, maintainanceDTO.getShift());
					pstmt.setString(4, maintainanceDTO.getMaintenanceReasons()
							.get(i));
					pstmt.setString(5, maintainanceDTO.getDetails().get(i));
					pstmt.setInt(6, maintainanceDTO.getTimeLosts().get(i));

					pstmt.executeUpdate(); // execute insert statement
				}
			}

			logger.info("Added maintenance details successfully");
			return "Maintenance details added Successfully.";
			}
		} catch (Exception e) {/*
								 * int i = BaseDAO.getLogId();
								 * logger.info("Incident occured at:-" +
								 * BaseDAO.getCurrentTime());
								 * logger.info("LogId:-" + i);
								 * System.out.println("Test ");
								 * System.out.println(e.getMessage());
								 * 
								 * if
								 * (e.getMessage().contains("Duplicate entry"))
								 * { return "Trying to add duplicate entry"; }
								 * else { logger.info(
								 * "Unable to add the maintenance details due to following reasons:-"
								 * + e.getMessage()); return
								 * "Unable to add the maintenance details, report the log Id with admins :-"
								 * + i;
								 * 
								 * }
								 */
			return "Unable to add the maintenance details";
		} finally {

		}

	}

	public double funciton1(double batchChangeTime) {

		double workingTime = 0;

		// A shift logic
		if (batchChangeTime > 6 && batchChangeTime < 14) {
			workingTime = batchChangeTime - 6;
		}

		// B shift logic
		if (batchChangeTime > 14 && batchChangeTime < 22) {
			workingTime = batchChangeTime - 14;
		}

		// C shift logic
		if (batchChangeTime > 22 && batchChangeTime < 24) {
			workingTime = batchChangeTime - 22;
		}

		if (batchChangeTime < 6) {
			workingTime = 2 + batchChangeTime;
		}

		return workingTime;
	}

	public static List<MaintenanceActivityDTO> getMaintenanceActivityDetails()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MaintenanceActivityDTO> maintenanceActivityDTOs = new ArrayList<MaintenanceActivityDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = " SELECT distinct maintainenceReasonId,data FROM lookuptable where param=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,"Activity Reason");// create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				{
					MaintenanceActivityDTO maintenanceActivityDTO = new MaintenanceActivityDTO();
					maintenanceActivityDTO
							.setActivityId(resultSet.getString(1));
					maintenanceActivityDTO.setActivity(resultSet.getString(2));
					maintenanceActivityDTOs.add(maintenanceActivityDTO);
				}
			}
			return maintenanceActivityDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the maintenance activity details due to following reasons:-"
					+ e.getMessage());
			return maintenanceActivityDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public List<MaintainanceDTO> getMaintenanceMasterDetails()
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MaintainanceDTO> maintainanceDTOs = new ArrayList<MaintainanceDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM maintenancedata order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "maintenancedata-";

			while (resultSet.next()) {

				{

					MaintainanceDTO maintainanceDTO = new MaintainanceDTO();
					maintainanceDTO.setId(tableName
							+ String.valueOf(resultSet.getString(1)));
					maintainanceDTO.setBatchNumber(resultSet.getString(2));
					maintainanceDTO.setMaintenanceDate(CommonRef.dbToUiDate(String.valueOf(resultSet
							.getDate(3))));
					maintainanceDTO.setShift(resultSet.getString(4));
					maintainanceDTO.setMachineNumber(resultSet.getString(5));
					maintainanceDTO.setTimeLost(String.valueOf(resultSet
							.getInt(6)));
					maintainanceDTO
							.setMaintenanceReason(resultSet.getString(7));
					maintainanceDTO.setDetail(resultSet.getString(8));
					maintainanceDTO.setMaintenanceEngineer(resultSet
							.getString(9));
					maintainanceDTO.setUserId(resultSet.getString(10));
					maintainanceDTOs.add(maintainanceDTO);
				}
			}
			logger.info("Getting maintenance details successfully");
			return maintainanceDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the maintenance details due to following reasons:-"
					+ e.getMessage());
			return maintainanceDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
	
	public List<MaintainanceDTO> getMaintenanceMasterDetails1()
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MaintainanceDTO> maintainanceDTOs = new ArrayList<MaintainanceDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM maintenancedata order by time_stamp desc limit 50";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "maintenancedata-";

			while (resultSet.next()) {

				{

					MaintainanceDTO maintainanceDTO = new MaintainanceDTO();
					maintainanceDTO.setId(tableName
							+ String.valueOf(resultSet.getString(1)));
					maintainanceDTO.setBatchNumber(resultSet.getString(2));
					maintainanceDTO.setMaintenanceDate(CommonRef.dbToUiDate(String.valueOf(resultSet
							.getDate(3))));
					maintainanceDTO.setShift(resultSet.getString(4));
					maintainanceDTO.setMachineNumber(resultSet.getString(5));
					maintainanceDTO.setTimeLost(String.valueOf(resultSet
							.getInt(6)));
					maintainanceDTO
							.setMaintenanceReason(resultSet.getString(7));
					maintainanceDTO.setDetail(resultSet.getString(8));
					maintainanceDTO.setMaintenanceEngineer(resultSet
							.getString(9));
					maintainanceDTO.setUserId(resultSet.getString(10));
					maintainanceDTOs.add(maintainanceDTO);
				}
			}
			logger.info("Getting maintenance details successfully");
			return maintainanceDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the maintenance details due to following reasons:-"
					+ e.getMessage());
			return maintainanceDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
	
	public static MaintainanceDTO getMaintenanceMasterDetails1(String id)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		MaintainanceDTO maintainanceDTO = new MaintainanceDTO();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM maintenancedata where id=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1,id);// create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

					maintainanceDTO.setBatchNumber(resultSet.getString(2));
					maintainanceDTO.setMaintenanceDate(String.valueOf(resultSet
							.getDate(3)));
					maintainanceDTO.setShift(resultSet.getString(4));
			}
			logger.info("Getting maintenance details successfully");
			return maintainanceDTO;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the maintenance details due to following reasons:-"
					+ e.getMessage());
			return maintainanceDTO;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	

	public String updateMaintenanceMaster(MaintainanceDTO maintainanceDTO, String userId)
			throws SQLException, ParseException {
		Connection conn = null;
		PreparedStatement pstmt = null;


		try {
			conn = CommonRef.getConnection();
			String query = "update maintenancedata set Mdate=?,Shift=?,Time_Lost=?,Maint_Reason=?,Detail=?,Eng_Maint=?,userId=? where ID=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(maintainanceDTO.getMaintenanceDate()));
			pstmt.setString(2, maintainanceDTO.getShift());
			pstmt.setString(3, maintainanceDTO.getTimeLost());
			pstmt.setString(4, maintainanceDTO.getMaintenanceReason());
			pstmt.setString(5, maintainanceDTO.getDetail());
			pstmt.setString(6, maintainanceDTO.getMaintenanceEngineer());
			pstmt.setString(7, userId);
			pstmt.setString(8, maintainanceDTO.getId());

			pstmt.executeUpdate(); // execute insert statement
			logger.info("Updated maintenance details successfully");
			return "Updated maintenance details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to update the maintenance details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating maintenance details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}

	}

}
