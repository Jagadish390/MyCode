package com.java.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.LookUpDTO;
import com.java.util.CommonRef;

public class LookupDAO {
	private static final Logger logger = Logger
			.getLogger(EmployeeDAO.class.getName());
	public static List<LookUpDTO> getLookUpDetails() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<LookUpDTO> lookUpDTOs = new ArrayList<LookUpDTO>();
		try {

			conn = CommonRef.getConnection();
			String query = "select param,data from lookuptable";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(1).equalsIgnoreCase("production data")) {
					LookUpDTO lookUpDTO = new LookUpDTO();
					lookUpDTO.setParam(resultSet.getString(1));
					lookUpDTO.setData(resultSet.getString(2));
					lookUpDTOs.add(lookUpDTO);
				} else if (resultSet.getString(1).equalsIgnoreCase(
						"maintenance data")) {
					LookUpDTO lookUpDTO = new LookUpDTO();
					lookUpDTO.setParam(resultSet.getString(1));
					lookUpDTO.setData(resultSet.getString(2));
					lookUpDTOs.add(lookUpDTO);
				} else if (resultSet.getString(1).equalsIgnoreCase(
						"packing data")) {
					LookUpDTO lookUpDTO = new LookUpDTO();
					lookUpDTO.setParam(resultSet.getString(1));
					lookUpDTO.setData(resultSet.getString(2));
					lookUpDTOs.add(lookUpDTO);
				} else if (resultSet.getString(1).equalsIgnoreCase(
						"Maintenance Reason")) {
					LookUpDTO lookUpDTO = new LookUpDTO();
					lookUpDTO.setParam(resultSet.getString(1));
					lookUpDTO.setData(resultSet.getString(2));
					lookUpDTOs.add(lookUpDTO);
				}

			}

			return lookUpDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return lookUpDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public static List<LookUpDTO> viewLookUpDetails() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<LookUpDTO> lookUpDTOs = new ArrayList<LookUpDTO>();
		try {

			conn = CommonRef.getConnection();
			String query = "select param,data,userId,id,maintainenceReasonId from lookuptable order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName="lookuptable-";
			while (resultSet.next()) {

				LookUpDTO lookUpDTO = new LookUpDTO();
				lookUpDTO.setParam(resultSet.getString(1));
				lookUpDTO.setData(resultSet.getString(2));
				lookUpDTO.setUserId(resultSet.getString(3));
				lookUpDTO.setId(tableName+resultSet.getString(4));
				lookUpDTO.setMaintainenceReasonId(resultSet.getString(5));
				lookUpDTOs.add(lookUpDTO);
			}

			return lookUpDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the lookup details due to following reasons:-"
					+ e.getMessage());
			return lookUpDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public String addLookUpMaster(LookUpDTO lookUpDTO) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "insert into lookuptable(param,data,userId,maintainenceReasonId) values(?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, lookUpDTO.getParam());
			pstmt.setString(2, lookUpDTO.getData());
			pstmt.setString(3, lookUpDTO.getUserId());
			pstmt.setString(4, lookUpDTO.getMaintainenceReasonId());
			pstmt.executeUpdate();
			return "LookUpMaster";

		} catch (Exception e) {
			e.printStackTrace();
			return "Failure";
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public String updateLookupDetails(LookUpDTO lookUpDTO,String userId) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update lookuptable set param=?,data=?,userId=? where id=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, lookUpDTO.getParam()); // set input
															// parameter 1
			pstmt.setString(2, lookUpDTO.getData());// set input parameter
															// 2
			pstmt.setString(3, userId);// set input parameter 3
			
			pstmt.setString(4, lookUpDTO.getId());// set input parameter 3
			
			pstmt.executeUpdate(); // execute insert statement
			logger.info(BaseDAO.getCurrentTime()+"Updated Employee successfully");
			return "Updated lookup details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the lookup details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating lookup details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}
}
