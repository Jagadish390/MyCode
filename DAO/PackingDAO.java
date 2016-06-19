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

import com.java.DTO.PackingDTO;
import com.java.util.CommonRef;

public class PackingDAO {

	private static final Logger logger = Logger
			.getLogger(PackingDAO.class.getName());

	public String addPackingMaster(PackingDTO packingDTO) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String uiDate=packingDTO.getProductionDate();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
			if(packingDTO.getBatchNumber().equals("Holiday")){
				String displayMessage=CommonRef.setHoliday(dbDate, packingDTO.getUserId());
				return displayMessage;
			}else{
			
			conn = CommonRef.getConnection();
			String query = "insert into packdata(BatchNumber,PDate,Shift,McNumber,GoodVials,Rej_Vials,Packing_Scrap,Mcn_No,Mcn_Qty,Pack_Supervisior,UserID,type,IPQC) values(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, packingDTO.getBatchNumber());
			pstmt.setString(2, dbDate);
			pstmt.setString(3, packingDTO.getShift());
			pstmt.setString(4, CommonRef.getMachineNumber(packingDTO.getBatchNumber()));
			pstmt.setString(5, packingDTO.getGoodVials());
			pstmt.setString(6, CommonRef.getRejectedVials(packingDTO.getPackingScrap(), packingDTO.getBatchNumber()));
			pstmt.setString(7, packingDTO.getPackingScrap());
			pstmt.setString(8, packingDTO.getMcnNo());
			pstmt.setString(9, packingDTO.getMcnQty());
			pstmt.setString(10, packingDTO.getPackingSupervisior());
		//	System.out.println("Packing super visior"+packingDTO.getPackingSupervisior());
			pstmt.setString(11, packingDTO.getUserId());
			pstmt.setString(12, packingDTO.getType());
			pstmt.setString(13, packingDTO.getIpqc());

			pstmt.executeUpdate(); // execute insert statement
			logger.info("Added Packing details successfully");
			return "Packing details added Successfully.";
			}

		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the packing details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the packing details, report the log Id with admins :-"+i;
				
			}
		} finally {

		}
	}

	public List<PackingDTO> getPackingMasterDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<PackingDTO> packingDTOs = new ArrayList<PackingDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM packdata order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "packdata-";

			while (resultSet.next()) {

				{

					PackingDTO packingDTO = new PackingDTO();

					packingDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					packingDTO.setBatchNumber(resultSet.getString(2));
					packingDTO.setProductionDate(CommonRef.dbToUiDate(String.valueOf(resultSet
							.getDate(3))));
					packingDTO.setShift(resultSet.getString(4));
					packingDTO.setMachineNumber(resultSet.getString(5));
					packingDTO
							.setGoodVials(String.valueOf(resultSet.getInt(6)));
					packingDTO.setRejectedVials(String.valueOf(resultSet
							.getInt(7)));
					packingDTO.setPackingScrap(String.valueOf(resultSet
							.getDouble(8)));
					packingDTO.setMcnNo(resultSet.getString(9));
					packingDTO.setMcnQty(String.valueOf(resultSet.getInt(10)));
					packingDTO.setPackingSupervisior(resultSet.getString(11));
					packingDTO.setUserId(resultSet.getString(12));
					packingDTO.setIpqc(resultSet.getString(15));
					packingDTOs.add(packingDTO);
				}
			}
			
			
			logger.info("Getting Packing details successfully");
			return packingDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the pack details due to following reasons:-"
					+ e.getMessage());
			return packingDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
	
	
	
	
	
	
	
	public List<PackingDTO> getPackingMasterDetails1() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<PackingDTO> packingDTOs = new ArrayList<PackingDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM packdata order by time_stamp desc limit 50";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "packdata-";

			while (resultSet.next()) {

				{

					PackingDTO packingDTO = new PackingDTO();

					packingDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					packingDTO.setBatchNumber(resultSet.getString(2));
					packingDTO.setProductionDate(CommonRef.dbToUiDate(String.valueOf(resultSet
							.getDate(3))));
					packingDTO.setShift(resultSet.getString(4));
					packingDTO.setMachineNumber(resultSet.getString(5));
					packingDTO
							.setGoodVials(String.valueOf(resultSet.getInt(6)));
					packingDTO.setRejectedVials(String.valueOf(resultSet
							.getInt(7)));
					packingDTO.setPackingScrap(String.valueOf(resultSet
							.getDouble(8)));
					packingDTO.setMcnNo(resultSet.getString(9));
					packingDTO.setMcnQty(String.valueOf(resultSet.getInt(10)));
					packingDTO.setPackingSupervisior(resultSet.getString(11));
					packingDTO.setUserId(resultSet.getString(12));
					packingDTO.setIpqc(resultSet.getString(15));
					packingDTOs.add(packingDTO);
				}
			}
			
			
			logger.info("Getting Packing details successfully");
			return packingDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the pack details due to following reasons:-"
					+ e.getMessage());
			return packingDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	
	
	

	public String updatePackingMaster(PackingDTO packingDTO,String userId)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
	
		
			conn = CommonRef.getConnection();
			String query = "update packdata set PDate=?,Shift=?,GoodVials=?,Rej_Vials=?,Packing_Scrap=?,Mcn_No=?,Mcn_Qty=?,Pack_Supervisior=?,userId=? where ID=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(packingDTO.getProductionDate()));
			pstmt.setString(2, packingDTO.getShift());
			pstmt.setString(3, packingDTO.getGoodVials());
			pstmt.setString(4, packingDTO.getRejectedVials());
			pstmt.setString(5, packingDTO.getPackingScrap());
			pstmt.setString(6, packingDTO.getMcnNo());
			pstmt.setString(7, packingDTO.getMcnQty());
			pstmt.setString(8, packingDTO.getPackingSupervisior());
			pstmt.setString(9, userId);
			pstmt.setString(10, packingDTO.getId());

			pstmt.executeUpdate(); // execute insert statement
			logger.info("Updated Packing details successfully");
			return "Updated pack details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the pack details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating pack details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}

}
