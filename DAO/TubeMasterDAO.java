package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.TubeMasterDTO;
import com.java.controller.EmployeeController;
import com.java.util.CommonRef;

public class TubeMasterDAO {
	
	private static final Logger logger = Logger
			.getLogger(TubeMasterDAO.class.getName());

	public String addTubeMaster(TubeMasterDTO tubeMasterDTO)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "insert into tubeMaster(Tube_ID, Focus_Code, Spec, Bundle_Weight, TubesInBundle, Make, tube_weight, userId) values(?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, tubeMasterDTO.getTubeId()); // set input
															// parameter 1
			pstmt.setString(2, tubeMasterDTO.getFocusCode());// set input
																// parameter 2
			pstmt.setString(3, tubeMasterDTO.getSpecification());// set input
																	// parameter
																	// 3
			pstmt.setString(4, tubeMasterDTO.getBundleWeight());
			pstmt.setString(5, tubeMasterDTO.getTubesInBundle());
			pstmt.setString(6, tubeMasterDTO.getMake());
			pstmt.setString(7, tubeMasterDTO.getTubeWeight());
			pstmt.setString(8, tubeMasterDTO.getUserId());

			pstmt.executeUpdate(); // execute insert statement
			logger.info("Added tube details successfully");
			return "Tube details added Successfully.";
		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the tube details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the tube details, report the log Id with admins :-"+i;
				
			}
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public List<TubeMasterDTO> getTubeMasterDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<TubeMasterDTO> tubeMasterDTOs = new ArrayList<TubeMasterDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT ID,Tube_ID,Focus_Code,Spec,Bundle_Weight,TubesInBundle,Make,ROUND(tube_weight,2) as tube_weight,UserID,Time_stamp FROM tubemaster order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "tubemaster-";

			while (resultSet.next()) {

				{

					TubeMasterDTO tubeMasterDTO = new TubeMasterDTO();
					tubeMasterDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					tubeMasterDTO.setTubeId(resultSet.getString(2));
					tubeMasterDTO.setFocusCode(resultSet.getString(3));
					tubeMasterDTO.setSpecification(resultSet.getString(4));
					tubeMasterDTO.setBundleWeight(resultSet.getString(5));
					tubeMasterDTO.setTubesInBundle(resultSet.getString(6));
					tubeMasterDTO.setMake(resultSet.getString(7));
					tubeMasterDTO.setTubeWeight(resultSet.getString(8));
					tubeMasterDTO.setUserId(resultSet.getString(9));
					tubeMasterDTOs.add(tubeMasterDTO);
				}
			}
			logger.info("Getting tube details successfully");
			return tubeMasterDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the tube details due to following reasons:-"
					+ e.getMessage());
			return tubeMasterDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public String updateTubeMaster(TubeMasterDTO tubeMasterDTO, String userId)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update tubemaster set Spec=?,Bundle_Weight=?,TubesInBundle=?,Make=?,tube_weight=?,userId=? where Tube_ID=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, tubeMasterDTO.getSpecification());// set input
			pstmt.setString(2, tubeMasterDTO.getBundleWeight());
			pstmt.setString(3, tubeMasterDTO.getTubesInBundle());
			pstmt.setString(4, tubeMasterDTO.getMake());
			pstmt.setString(5, tubeMasterDTO.getTubeWeight());
			pstmt.setString(6, userId);
			pstmt.setString(7, tubeMasterDTO.getTubeId()); // set input
			pstmt.executeUpdate(); // execute insert statement
			logger.info("Updated tube details successfully");
			return "Updated tube details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the tube details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating tube details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}

}
