package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.DTO.EmployeeDTO;
import com.java.controller.EmployeeController;
import com.java.util.CommonRef;

public class EmployeeDAO {

	private static final Logger logger = Logger
			.getLogger(EmployeeDAO.class.getName());

	public String addEmployee(EmployeeDTO employeeDTO) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "insert into usermaster(UserName, Password, EmpId, UserId, role,department) values(?, ?, ?, ?, ?,?)";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, employeeDTO.getUserName()); // set input
															// parameter 1
			pstmt.setString(2, employeeDTO.getPassword());// set input parameter
															// 2
			pstmt.setString(3, employeeDTO.getEmpId());// set input parameter 3
			pstmt.setString(4, employeeDTO.getUserId());
			pstmt.setString(5, employeeDTO.getRole());
			pstmt.setString(6, employeeDTO.getDepartment());
			pstmt.executeUpdate(); // execute insert statement
			logger.info(BaseDAO.getCurrentTime()+"Employee added Successfully :-"+employeeDTO.getUserId());
			return "user details added Successfully.";
		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the user details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the user details, report the log Id with admins :-"+i;
				
			}
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public static List<EmployeeDTO> getProductionEngineers()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select UserId from usermaster where department='Production'";
			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setUserId(resultSet.getString(1));
					employeeDTOs.add(employeeDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Getting Production engineers successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the production engineers due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	public static List<EmployeeDTO> getProductionMasters()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select UserId from usermaster where department='ProductionMaster'";
			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setUserId(resultSet.getString(1));
					employeeDTOs.add(employeeDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Getting Production engineers successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the production engineers due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	public static List<EmployeeDTO> getIPQCUsers()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select UserId from usermaster where department='Quality'";
			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setUserId(resultSet.getString(1));
					employeeDTOs.add(employeeDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Getting IPQC list successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the IPQC due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	public static List<EmployeeDTO> getPackingUsers()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select UserId from usermaster where department='Packing'";
			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setUserId(resultSet.getString(1));
					employeeDTOs.add(employeeDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Getting Pakcing Supervisors list successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the Packing Supervisors due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	public static List<EmployeeDTO> getMaintenanceEngineers()
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select UserId from usermaster where department='Maintenance'";
			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setUserId(resultSet.getString(1));
					employeeDTOs.add(employeeDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Getting Maintenance engineers successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the Maintenance engineers due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	

	public static List<EmployeeDTO> viewEmployees() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM usermaster order by time_stamp desc";
			String tableName = "usermaster-";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				{

					EmployeeDTO employeeDTO = new EmployeeDTO();
					employeeDTO.setId(tableName
							+ String.valueOf(resultSet.getInt(1)));
					employeeDTO.setUserName(resultSet.getString(2));
					employeeDTO.setPassword(resultSet.getString(3));
					employeeDTO.setEmpId(String.valueOf(resultSet.getInt(4)));
					employeeDTO.setUserId(resultSet.getString(5));
					employeeDTO.setDepartment(resultSet.getString(8));
					employeeDTOs.add(employeeDTO);

				}
			}
			logger.info(BaseDAO.getCurrentTime()+"Viewing  Employee details successfully");
			return employeeDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to view the employees due to following reasons:-"
					+ e.getMessage());
			return employeeDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public ResultSet getDetails(String tableName, String primaryKey)
			throws SQLException {

		ResultSet resultSet = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM " + tableName + " where ID = "
					+ primaryKey;

			pstmt = conn.prepareStatement(query); // create a statement
			resultSet = pstmt.executeQuery();
			logger.info(BaseDAO.getCurrentTime()+"Getting details successfully");
			return resultSet;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to get the details due to following reasons:-"
					+ e.getMessage());
			return resultSet;
		} finally {
			pstmt = null;
			conn = null;
		}
	}

	public ResultSet editEmployee(String userId) throws SQLException {

		ResultSet resultSet = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT * FROM usermaster where userId = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			resultSet = pstmt.executeQuery();
			logger.info(BaseDAO.getCurrentTime()+"Edited Employee successfully");
			return resultSet;
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to edit employee details due to following reasons:-"
					+ e.getMessage());
			return resultSet;
		} finally {
			pstmt = null;
			conn = null;
		}
	}

	public String deleteDetails(String tableName, String primaryKey) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "delete FROM " + tableName + " where ID = "
					+ primaryKey;

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.executeUpdate();
			logger.info(BaseDAO.getCurrentTime()+"Deleted  successfully");
			return "Deleted details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to delete due to following reasons:-"
					+ e.getMessage());
			return "Error in deleting details. Please contact Admin";
		} finally {
			pstmt = null;
			conn = null;
		}
	}

	public String updateEmployeeMaster(EmployeeDTO employeeDTO)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update usermaster set UserName=?,Password=?,EmpId=? where UserId=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, employeeDTO.getUserName()); // set input
															// parameter 1
			pstmt.setString(2, employeeDTO.getPassword());// set input parameter
															// 2
			pstmt.setString(3, employeeDTO.getEmpId());// set input parameter 3
			pstmt.setString(4, employeeDTO.getUserId());
			pstmt.executeUpdate(); // execute insert statement
			logger.info(BaseDAO.getCurrentTime()+"Updated Employee successfully");
			return "Updated employee details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the employees due to following reasons:-"
					+ e.getMessage());
			return "Error in updating employee details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public ResultSet validateLogin(EmployeeDTO employeeDTO) throws Exception {
		ResultSet resultSet = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = CommonRef.getConnection();
		String query = "SELECT * FROM usermaster where UserId = ?";

		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, employeeDTO.getUserId());
		resultSet = pstmt.executeQuery();

		return resultSet;
	}
	public String updateLoginCount(String userId) throws Exception{

	//System.out.println("In update login");
//	System.out.println("Used ID"+userId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = CommonRef.getConnection();
		String query = "update usermaster set Login_Count=Login_Count+1 where UserId=?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, userId);
		pstmt.executeUpdate();
	// System.out.println("After execute");
	 return "login";

		
	}

	public String enableUser(String primaryKey) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update usermaster set Login_Count=? where id=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setInt(1,0);
			pstmt.setString(2,primaryKey);
			pstmt.executeUpdate();
			logger.info(BaseDAO.getCurrentTime()+"Updated Employee successfully");
			return "Employee details have been unlocked.";
		} catch (Exception e) {
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+BaseDAO.getLogId());
			logger.info("Unable to update the employees due to following reasons:-"
					+ e.getMessage());
			return "Error in activating employee details.";
		} finally {
			pstmt.close();
			conn.close();
		}

	}
}