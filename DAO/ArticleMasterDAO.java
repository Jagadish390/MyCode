package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.PerformanceDTO;
import com.java.util.CommonRef;

public class ArticleMasterDAO {

	private static final Logger logger = Logger
			.getLogger(ArticleMasterDAO.class.getName());

	public String addArticleMaster(ArticleMasterDTO articleMasterDTO)
			throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "insert into articlemaster(Article_Name, Focus_Code, Dwg_No, Spec, Weight, ProductTargetSpeed,userId) values(?, ?, ?, ?, ?, ?,?)";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, articleMasterDTO.getArticleName()); // set input
																	// parameter
																	// 1
			pstmt.setString(2, articleMasterDTO.getFocusCode());// set input
																// parameter 2
			pstmt.setString(3, articleMasterDTO.getDrawingNumber());// set input
																	// parameter
																	// 3
			pstmt.setString(4, articleMasterDTO.getSpecification());
			pstmt.setString(5, articleMasterDTO.getWeight());
			pstmt.setInt(6, articleMasterDTO.getProductTargetSpeed());
			pstmt.setString(7, articleMasterDTO.getUserId());

			pstmt.executeUpdate(); // execute insert statement
			logger.info(BaseDAO.getCurrentTime()
					+ "Added article details successfully");
			return "Article details added Successfully.";
		} catch (Exception e) {
			int i=BaseDAO.getLogId();
			logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
			logger.info("LogId:-"+i);
			if(e.getMessage().contains("Duplicate entry")){
				return "Trying to add duplicate entry";
			}
			else{
				logger.info("Unable to add the article details due to following reasons:-"
						+ e.getMessage());
				return "Unable to add the article details, report the log Id with admins :-"+i;
				
			}
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public List<ArticleMasterDTO> getArticleMasterDetails() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<ArticleMasterDTO> articleMasterDTOs = new ArrayList<ArticleMasterDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "SELECT ID,Article_Name,Focus_Code,Dwg_No,Spec,ROUND(Weight,2) as Weight,UserId,TIme_stamp,ProductTargetSpeed FROM articlemaster order by time_stamp desc";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			String tableName = "articlemaster-";

			while (resultSet.next()) {

				{

					ArticleMasterDTO articleMasterDTO = new ArticleMasterDTO();
					articleMasterDTO.setId(tableName
							+ String.valueOf(resultSet.getString(1)));
					articleMasterDTO.setArticleName(resultSet.getString(2));
					articleMasterDTO.setFocusCode(resultSet.getString(3));
					articleMasterDTO.setDrawingNumber(resultSet.getString(4));
					articleMasterDTO.setSpecification(resultSet.getString(5));
					articleMasterDTO.setWeight(resultSet.getString(6));
					articleMasterDTO.setUserId(resultSet.getString(7));
					articleMasterDTO.setProductTargetSpeed(resultSet.getInt(9));
					articleMasterDTOs.add(articleMasterDTO);
				}
			}
			logger.info(BaseDAO.getCurrentTime()
					+ "Getting article details successfully");
			return articleMasterDTOs;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the article details due to following reasons:-"
					+ e.getMessage());
			return articleMasterDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public String updateArticleMaster(ArticleMasterDTO articleMasterDTO,String userId)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = CommonRef.getConnection();
			String query = "update articlemaster set Article_Name=?,Dwg_No=?,Spec=?,Weight=?,ProductTargetSpeed=?,UserId=? where Focus_Code=?";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, articleMasterDTO.getArticleName()); // set input
			pstmt.setString(2, articleMasterDTO.getDrawingNumber());// set input
			pstmt.setString(3, articleMasterDTO.getSpecification());
			pstmt.setString(4, articleMasterDTO.getWeight());
			pstmt.setString(6, articleMasterDTO.getFocusCode());
			pstmt.setInt(5, articleMasterDTO.getProductTargetSpeed());
			pstmt.setString(6, userId);
			pstmt.setString(7, articleMasterDTO.getFocusCode());
			pstmt.executeUpdate(); // execute insert statement
			logger.info(BaseDAO.getCurrentTime()
					+ "Updated article details successfully");
			return "Updated article details Successfully.";
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info(BaseDAO.getCurrentTime()
					+ "Unable to update the article details due to following reasons:-"
					+ e.getMessage());
			return "Error in updating article details. Please contact Admin";
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	public List<Double> getPerformanceDetails(String fromDate,String toDate) throws SQLException {
		//System.out.println("In performance details");
				List<Double> performanceActual = new ArrayList<Double>();
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet resultSet = null;
				//DateDTO dateDTO=new DateDTO();
				
				try {
					conn = CommonRef.getConnection();
					String query = "select pd.clockrate as ActualSpeed,bm.Mc_Num,bm.ArticleId,sum(md.total_time)-sum(md.Time_Lost) as runningTime,am.ProductTargetSpeed from maintenancedata md join batchmaster bm on md.Batch_Num=bm.BatchNumber join  articlemaster am on bm.ArticleId=am.Focus_Code join productiondata pd on pd.BatchNumber=md.Batch_num and pd.pdate=md.mdate and pd.shift=md.shift where Mdate between ? and ? and pd.clockrate !=0 group by pd.clockrate,am.Article_Name,bm.mc_num";
		//System.out.println("Test");
				//	System.out.println("this is performance date" +fromDate);
					pstmt = conn.prepareStatement(query); // create a statement
					pstmt.setString(1,CommonRef.formatDate(fromDate));
					pstmt.setString(2, CommonRef.formatDate(toDate));
					resultSet = pstmt.executeQuery(); // execute insert statement
					Map<String, List<PerformanceDTO>> performanceMap = new TreeMap<String, List<PerformanceDTO>>();

					while (resultSet.next()) {
						PerformanceDTO performanceDTO = new PerformanceDTO();
					
							performanceDTO.setClockRate(resultSet.getInt(1));
						
						performanceDTO.setMachineNumber(resultSet.getString(2));
						performanceDTO.setArticleId(resultSet.getString(3));
						performanceDTO.setRunningTime((resultSet.getInt(4)));
						//performanceDTO.setArticleName(resultSet.getInt(5));
						if(resultSet.getString(2).equalsIgnoreCase("S2")){
						performanceDTO.setTargetSpeed(resultSet.getInt(5)/2);
						}
						else{
							performanceDTO.setTargetSpeed(resultSet.getInt(5));
						}

						if (!performanceMap.containsKey(performanceDTO
								.getMachineNumber())) {
							List<PerformanceDTO> performanceDTOs = new ArrayList<PerformanceDTO>();
							performanceDTOs.add(performanceDTO);

							performanceMap.put(performanceDTO.getMachineNumber(),
									performanceDTOs);
						} else {
							performanceMap.get(performanceDTO.getMachineNumber()).add(
									performanceDTO);
						}
					}

					for (String machineNumber : performanceMap.keySet()) {
						List<PerformanceDTO> performanceDTOs = new ArrayList<PerformanceDTO>();
						performanceDTOs = performanceMap.get(machineNumber);
						//int totalTime = 480 * 3 * 29;
		//System.out.println("**********************"+totalTime);
						double totalPossibleVailsAsPerActualSpeed = 0;
						double totalPossibleVailsAsPerTargetSpeed = 0;
						for (PerformanceDTO performanceDTO : performanceDTOs) {
							double possibleVailsAsPerActualSpeed = performanceDTO
									.getClockRate()
									* ( performanceDTO.getRunningTime());
							double possibleVailsAsPerTargetSpeed = performanceDTO.getTargetSpeed()
									* ( performanceDTO.getRunningTime());
						
							
							
							
							
							System.out.println("Article:"+performanceDTO.getArticleId());
				System.out.println("Target Speed:"+performanceDTO.getTargetSpeed());
							totalPossibleVailsAsPerActualSpeed = totalPossibleVailsAsPerActualSpeed
									+ possibleVailsAsPerActualSpeed;
							totalPossibleVailsAsPerTargetSpeed = totalPossibleVailsAsPerTargetSpeed
									+ possibleVailsAsPerTargetSpeed;

						}
						double performance = CommonRef.roundTwoDecimals((totalPossibleVailsAsPerActualSpeed / totalPossibleVailsAsPerTargetSpeed) * 100);
						System.out.println("Performance:"+performance);
						System.out.println("Actual Vials: "+totalPossibleVailsAsPerActualSpeed);
						System.out.println("Target Vials: "+totalPossibleVailsAsPerTargetSpeed);
						System.out.println("Performance"+performance);
						
						performanceActual.add(performance);

					}

					return performanceActual;
		} catch (Exception e) {
			logger.info("Incident occured at:-" + BaseDAO.getCurrentTime());
			logger.info("LogId:-" + BaseDAO.getLogId());
			logger.info("Unable to get the article details due to following reasons:-"
					+ e.getMessage());
		} finally {
			pstmt.close();
			conn.close();
		}
		return performanceActual;
	}
}
