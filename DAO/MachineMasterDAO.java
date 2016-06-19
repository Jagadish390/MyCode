package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.MachineMasterDTO;
import com.java.util.CommonRef;

public class MachineMasterDAO {
	
	private static final Logger logger = Logger
			.getLogger(MachineMasterDAO.class.getName());

	public List<MachineMasterDTO> getMachineDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<MachineMasterDTO> machineMasterDTOs = new ArrayList<MachineMasterDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM machinemaster where Mc_Num!='0' order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "machinemaster-";

			while (resultSet.next()) {

				{

					MachineMasterDTO machineMasterDTO = new MachineMasterDTO();
					machineMasterDTO.setId(tableName
							+ String.valueOf(resultSet.getString(1)));
					machineMasterDTO.setMake(resultSet.getString(2));
					machineMasterDTO.setMachineNumber(resultSet.getString(3));
					machineMasterDTO.setUserId(resultSet.getString(4));
					machineMasterDTO.setDescription(resultSet.getString(5));
					machineMasterDTO.setRange(resultSet.getString(6));
					machineMasterDTO.setCommisionDate(String.valueOf(resultSet
							.getDate(7)));
					machineMasterDTOs.add(machineMasterDTO);
				}
			}
			logger.info("Getting  machine details successfully");
			return machineMasterDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the machine details due to following reasons:-"
					+ e.getMessage());
			return machineMasterDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public String addMachine(MachineMasterDTO machineMasterDTO)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "insert into machinemaster(Make,Mc_Num,UserId,Description,Range1,Commision_Date) values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, machineMasterDTO.getMake());
			pstmt.setString(2, machineMasterDTO.getMachineNumber());
			pstmt.setString(3, machineMasterDTO.getUserId());
			pstmt.setString(4, machineMasterDTO.getDescription());
			pstmt.setString(5, machineMasterDTO.getRange());
			pstmt.setString(6, machineMasterDTO.getCommisionDate());

			pstmt.executeUpdate(); // execute insert statement
			logger.info("Added machine details successfully");
			return "Machine details added Successfully.";
		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the machine details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the machine details, report the log Id with admins :-"+i;
				
			}
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public String updateMachineMaster(MachineMasterDTO machineMasterDTO, String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update machinemaster set make=?,description=?,range1=?,Commision_Date=?,userId=? where Mc_Num=?";
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, machineMasterDTO.getMake());
			pstmt.setString(6, machineMasterDTO.getMachineNumber());
			pstmt.setString(2, machineMasterDTO.getDescription());
			pstmt.setString(3, machineMasterDTO.getRange());
			pstmt.setString(4, machineMasterDTO.getCommisionDate());
			pstmt.setString(5, userId);
			pstmt.executeUpdate(); // execute insert statement
			logger.info("Updated machine details successfully");
			return "Updated machine details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the machine details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating machine details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}

}
