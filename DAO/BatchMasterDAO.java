package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.BatchDTO;
import com.java.util.CommonRef;

public class BatchMasterDAO {

	private static final Logger logger = Logger.getLogger(BatchMasterDAO.class
			.getName());

	public String addBatchMaster(BatchDTO batchDTO) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String uiDate = batchDTO.getBatchDate();
			String startTime=batchDTO.getStartTime();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String dbDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate);
			//Date parsedDate1 = dateFormat.parse(startTime);
			//String dbDate1 = new SimpleDateFormat("yyyy-MM-dd")
				//	.format(parsedDate1);
			conn = CommonRef.getConnection();
			String query = "insert into batchmaster(BatchNumber,BatchDate,ArticleId,Mc_Num,Tube_ID,Start_Time,BatchQuantity,Type,BatchStatus,UserId) values( ?, ?, ?, ?, ?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, batchDTO.getBatchNumber().toUpperCase()); // set input
															// parameter
															// 1
			pstmt.setString(2, dbDate);

			pstmt.setString(3, batchDTO.getArticleId());// set input
														// parameter
														// 3
			pstmt.setString(4, batchDTO.getMachineNumber());
		//	pstmt.setString(5, batchDTO.getClockRate());
		//	pstmt.setString(5, batchDTO.getMaxSpeed());
			pstmt.setString(5, batchDTO.getTubeId());
			pstmt.setString(6, batchDTO.getStartTime());
			pstmt.setString(7, batchDTO.getBatchQuantity());
			pstmt.setString(8, batchDTO.getType());
			pstmt.setString(10, batchDTO.getUserId());
			pstmt.setString(9, "Running");

			pstmt.executeUpdate(); // execute insert statement

			updateRecentlyClosedStatus(batchDTO.getMachineNumber());
			updateRunningStatus(batchDTO.getMachineNumber(),
					batchDTO.getBatchNumber());
			logger.info(BaseDAO.getCurrentTime()
					+ "Added batch details successfully");
			return "Batch details added Successfully.";
		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the batch details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the batch details, report the log Id with admins :-"+i;
				
			}
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	private void updateRecentlyClosedStatus(String machineNumber)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update batchmaster set BatchStatus='Closed' where Mc_Num=? AND BatchStatus='Recently Closed'";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, machineNumber);

			logger.info(BaseDAO.getCurrentTime()
					+ "updated recently closed status batch details successfully");
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to update the recently closed batch status details due to following reasons:-"
					+ e.getMessage());

		} finally {
			pstmt.close();
			conn.close();
		}

	}

	private void updateRunningStatus(String machineNumber, String batchNumber)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update batchmaster set BatchStatus='Recently Closed' where Mc_Num=? AND BatchStatus='Running' AND batchNumber!=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, machineNumber);
			pstmt.setString(2, batchNumber);

			pstmt.executeUpdate();
			logger.info(BaseDAO.getCurrentTime()
					+ "Updated running batch status details successfully");
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to update the running batch status details due to following reasons:-"
					+ e.getMessage());

		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public String updateBatchMaster(BatchDTO batchDTO,String userId) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			
		
			
			conn = CommonRef.getConnection();
			String query = "update batchmaster set BatchDate=?,Start_Time=?,BatchQuantity=?,Type=?,userId=? where BatchNumber=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(6, batchDTO.getBatchNumber()); // set input
			pstmt.setString(1, CommonRef.formatDate(batchDTO.getBatchDate()));
		//	pstmt.setString(2, batchDTO.getClockRate());
		//	pstmt.setString(2, batchDTO.getMaxSpeed());
			pstmt.setString(2, batchDTO.getStartTime());
			pstmt.setString(3, batchDTO.getBatchQuantity());
			pstmt.setString(4, batchDTO.getType());
			pstmt.setString(5, userId);

			pstmt.executeUpdate();
			logger.info(BaseDAO.getCurrentTime()
					+ "Updated batch details successfully");
			return "Updated batch details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to update the batch details due to following reasons:-"
					+ e.getMessage());

			return "Error in updating batch details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public static List<BatchDTO> getBatchMasterDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchDTO> batchDTOs = new ArrayList<BatchDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM batchmaster order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "batchmaster-";

			while (resultSet.next()) {

				BatchDTO batchDTO = new BatchDTO();

				batchDTO.setBatchId(tableName
						+ String.valueOf(resultSet.getInt(1)));
				batchDTO.setBatchNumber(resultSet.getString(2));
				batchDTO.setBatchDate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(3))));
				batchDTO.setArticleId(resultSet.getString(4));
				batchDTO.setMachineNumber(resultSet.getString(5));
				//batchDTO.setClockRate(String.valueOf(resultSet.getInt(6)));
				//batchDTO.setMaxSpeed(String.valueOf(resultSet.getInt(7)));
				batchDTO.setTubeId(resultSet.getString(8));
				batchDTO.setStartTime(resultSet.getString(9));
				//System.out.println(resultSet.getString(9));
				batchDTO.setBatchQuantity(String.valueOf(resultSet.getInt(10)));
				batchDTO.setType(resultSet.getString(11));
				batchDTO.setUserId(resultSet.getString(12));
				batchDTO.setBatchStatus(resultSet.getString(14));
				batchDTOs.add(batchDTO);

			}
			logger.info(BaseDAO.getCurrentTime()
					+ "Getting batch details successfully");
			return batchDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			e.printStackTrace();
			return batchDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public static List<BatchDTO> getBatchMasterDetails1() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchDTO> batchDTOs = new ArrayList<BatchDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select distinct mc_num,articleId from batchmaster order by mc_num,articleId ";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				BatchDTO batchDTO = new BatchDTO();

				batchDTO.setArticleId(resultSet.getString(2));
				batchDTO.setMachineNumber(resultSet.getString(1));
				batchDTOs.add(batchDTO);

			}
			logger.info(BaseDAO.getCurrentTime()
					+ "Getting batch details successfully");
			return batchDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			e.printStackTrace();
			return batchDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	// to get batch details required for production, maintenance and packing
	public static List<BatchDTO> getBatchMasterDetails2() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchDTO> batchDTOs = new ArrayList<BatchDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select batchNumber from batchmaster where batchStatus ='Recently Closed' or batchStatus ='Running' or BatchNumber='Holiday' or BatchNumber='No Production'";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				BatchDTO batchDTO = new BatchDTO();
				batchDTO.setBatchNumber(resultSet.getString(1));
				batchDTOs.add(batchDTO);

			}
			logger.info(BaseDAO.getCurrentTime()
					+ "Getting batch details successfully");
			return batchDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			e.printStackTrace();
			return batchDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

}
