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

import com.java.DTO.BatchDTO;
import com.java.DTO.ProductionDTO;
import com.java.util.CommonRef;

public class ProductionDAO {

	private static final Logger logger = Logger.getLogger(ProductionDAO.class
			.getName());

	public String addProductionMaster(ProductionDTO productionDTO)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String uiDate = productionDTO.getProductionDate();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String dbDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate);

			if (productionDTO.getBatchNumber().equals("Holiday")) {
				String displayMessage = CommonRef.setHoliday(dbDate,
						productionDTO.getUserId());
				return displayMessage;
			} else {

				conn = CommonRef.getConnection();
				String query = "insert into productiondata(Pdate,Shift,BatchNumber,Novis_Production,Novis_Good,Novis_Rot_Rej,Novis_Lien_Rej,Tubes_Used,Glass_Waste,Engineer_Prd,UserId,type,VialsPerTube,ProductionMaster,clockrate) values( ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)";

				pstmt = conn.prepareStatement(query); // create a statement

				pstmt.setString(1, dbDate);
				pstmt.setString(2, productionDTO.getShift());
				pstmt.setString(3, productionDTO.getBatchNumber());
				pstmt.setString(4, productionDTO.getNovisProd());
				pstmt.setString(5, productionDTO.getNovisGood());
				pstmt.setString(6, productionDTO.getNovisRotRej());
				pstmt.setString(7, productionDTO.getNovisLienRej());
				pstmt.setString(8, productionDTO.getTubesUsed());
				pstmt.setString(9, productionDTO.getGlassWaste());
				// pstmt.setString(10, productionDTO.getActivityTime());
				pstmt.setString(10, productionDTO.getEngineerPrd());
				pstmt.setString(11, productionDTO.getUserId());
				pstmt.setString(12, productionDTO.getType());
				pstmt.setString(13, productionDTO.getVialsPerTube());
				pstmt.setString(14, productionDTO.getProductionMaster());
				pstmt.setString(15, productionDTO.getClockRate());
				pstmt.executeUpdate(); // execute insert statement
				logger.info(BaseDAO.getCurrentTime()
						+ "Added Production details successfully");
				return "Production details added Successfully.";
			}

		} catch (Exception e) {
			int i = BaseDAO.getLogId();
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + i);
			if (e.getMessage().contains("Duplicate entry")) {
				return "Trying to add duplicate entry";
			} else {
				logger.info("Unable to add the production details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the production details, report the log Id with admins :-"
						+ i;

			}

		} finally {

		}

	}

	public List<ProductionDTO> getProductionDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<ProductionDTO> productionDTOs = new ArrayList<ProductionDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM productiondata order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "productiondata-";

			while (resultSet.next()) {

				{

					ProductionDTO productionDTO = new ProductionDTO();
					productionDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					productionDTO.setProductionDate(CommonRef.dbToUiDate(String
							.valueOf(resultSet.getDate(2))));
					productionDTO.setShift(resultSet.getString(3));
					productionDTO.setBatchNumber(resultSet.getString(4));
					productionDTO.setNovisProd(String.valueOf(resultSet
							.getInt(5)));
					productionDTO.setNovisGood(String.valueOf(resultSet
							.getInt(6)));
					productionDTO.setNovisRotRej(String.valueOf(resultSet
							.getInt(7)));
					productionDTO.setNovisLienRej(String.valueOf(resultSet
							.getInt(8)));
					productionDTO.setTubesUsed(String.valueOf(resultSet
							.getDouble(9)));
					productionDTO.setTimeLost(String.valueOf(resultSet
							.getInt(10)));
					productionDTO.setClockRate(resultSet.getString(11));
					productionDTO.setProductionMaster(resultSet.getString(12));
					productionDTO.setGlassWaste(String.valueOf(resultSet
							.getDouble(13)));
					// productionDTO.setActivityTime(String.valueOf(resultSet.getInt(14)));
					productionDTO.setEngineerPrd(resultSet.getString(15));
					productionDTO.setUserId(resultSet.getString(16));
					productionDTO.setVialsPerTube(resultSet.getString(19));
					productionDTOs.add(productionDTO);
				}
			}
			logger.info("Getting Production details successfully");
			return productionDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the production details due to following reasons:-"
					+ e.getMessage());
			return productionDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	
	
	
	
	public List<ProductionDTO> getProductionDetails1() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<ProductionDTO> productionDTOs = new ArrayList<ProductionDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM productiondata order by time_stamp desc limit 50";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "productiondata-";

			while (resultSet.next()) {

				{

					ProductionDTO productionDTO = new ProductionDTO();
					productionDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					productionDTO.setProductionDate(CommonRef.dbToUiDate(String
							.valueOf(resultSet.getDate(2))));
					productionDTO.setShift(resultSet.getString(3));
					productionDTO.setBatchNumber(resultSet.getString(4));
					productionDTO.setNovisProd(String.valueOf(resultSet
							.getInt(5)));
					productionDTO.setNovisGood(String.valueOf(resultSet
							.getInt(6)));
					productionDTO.setNovisRotRej(String.valueOf(resultSet
							.getInt(7)));
					productionDTO.setNovisLienRej(String.valueOf(resultSet
							.getInt(8)));
					productionDTO.setTubesUsed(String.valueOf(resultSet
							.getDouble(9)));
					productionDTO.setTimeLost(String.valueOf(resultSet
							.getInt(10)));
					productionDTO.setClockRate(resultSet.getString(11));
					productionDTO.setProductionMaster(resultSet.getString(12));
					productionDTO.setGlassWaste(String.valueOf(resultSet
							.getDouble(13)));
					// productionDTO.setActivityTime(String.valueOf(resultSet.getInt(14)));
					productionDTO.setEngineerPrd(resultSet.getString(15));
					productionDTO.setUserId(resultSet.getString(16));
					productionDTO.setVialsPerTube(resultSet.getString(19));
					productionDTOs.add(productionDTO);
				}
			}
			logger.info("Getting Production details successfully");
			return productionDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the production details due to following reasons:-"
					+ e.getMessage());
			return productionDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	
	
	
	
	
	
	public String updatePackingMaster(ProductionDTO productionDTO, String userId)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			String uiDate = productionDTO.getProductionDate();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String dbDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate);

			conn = CommonRef.getConnection();
			String query = "update productiondata set Pdate=?,Shift=?,Novis_Production=?,Novis_Good=?,Novis_Rot_Rej=?,Novis_Lien_Rej=?,Tubes_Used=?,Glass_Waste=?,Engineer_Prd=?,ProductionMaster=?,clockrate=?,userId=? where ID=?";
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, dbDate);
			pstmt.setString(2, productionDTO.getShift());
			pstmt.setString(3, productionDTO.getNovisProd());
			pstmt.setString(4, productionDTO.getNovisGood());
			pstmt.setString(5, productionDTO.getNovisRotRej());
			pstmt.setString(6, productionDTO.getNovisLienRej());
			pstmt.setString(7, productionDTO.getTubesUsed());
			pstmt.setString(8, productionDTO.getGlassWaste());
			// pstmt.setString(9, productionDTO.getActivityTime());
			pstmt.setString(9, productionDTO.getEngineerPrd());
			pstmt.setString(13, productionDTO.getId());
			pstmt.setString(12, userId);
			pstmt.setString(10, productionDTO.getProductionMaster());
			pstmt.setString(11, productionDTO.getClockRate());
			pstmt.executeUpdate(); // execute insert statement
			logger.info("Updated Production details successfully");
			return "Updated production details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to update the production details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating production details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}
}