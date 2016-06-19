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
import java.util.StringTokenizer;

import com.java.DTO.BatchDTO;
import com.java.DTO.BatchDetailsDTO;
import com.java.DTO.BatchSummaryDTO;
import com.java.util.CommonRef;

public class BatchSummaryDAO {
	public static List<BatchSummaryDTO> getBatchSummary() throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchSummaryDTO> batchSummaryDTOs = new ArrayList<BatchSummaryDTO>();
		try {
			conn = CommonRef.getConnection();

			String query = "select bm.BatchNumber,bm.ArticleId,bm.Mc_Num,bm.Tube_ID,bm.Start_Time,am.Article_Name,sum(prd.Novis_Production),sum(prd.Novis_Good),sum(prd.Novis_Rot_Rej),sum(prd.Novis_Lien_Rej),ROUND(sum(prd.Tubes_Used),2) as Tubes_Used,sum(prd.Glass_Waste),sum(prd.Activity_Time),sum(pcd.GoodVials),sum(pcd.Rej_Vials),sum(pcd.Packing_Scrap),sum(md.Time_Lost) from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on bm.BatchNumber=prd.BatchNumber join packdata pcd on prd.BatchNumber=pcd.BatchNumber and prd.shift=pcd.shift and prd.pdate=pcd.pdate join maintenancedata md on prd.BatchNumber=md.Batch_Num and prd.shift=md.shift and prd.pdate=md.mdate  group by bm.BatchNumber ";

			pstmt = conn.prepareStatement(query); // create a statement
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				BatchSummaryDTO batchSummaryDTO = new BatchSummaryDTO();
				batchSummaryDTO.setBatchNumber(resultSet.getString(1));
				String batchNumberNew = resultSet.getString(1).substring(1);

				batchSummaryDTO.setBatchNumberNew(batchNumberNew);
				batchSummaryDTO.setArticleId(resultSet.getString(2));
				batchSummaryDTO.setMachineNumber(resultSet.getString(3));
				batchSummaryDTO.setTubeId(resultSet.getString(4));
				batchSummaryDTO.setStartTime(resultSet.getString(5));
				batchSummaryDTO.setArticleName(resultSet.getString(6));
				batchSummaryDTO.setNovisProduction(resultSet.getString(7));
				batchSummaryDTO.setNovisGood(resultSet.getString(8));
				batchSummaryDTO.setNovisRotRej(resultSet.getString(9));
				batchSummaryDTO.setNovisLienRej(resultSet.getString(10));
				batchSummaryDTO.setTubesUsed(resultSet.getString(11));
				batchSummaryDTO.setGlassWaste(resultSet.getString(12));
				batchSummaryDTO.setActivityTime(resultSet.getString(13));
				batchSummaryDTO.setGoodVials(resultSet.getString(14));
				batchSummaryDTO.setRejVials(resultSet.getString(15));
				batchSummaryDTO.setPackingScrap(resultSet.getString(16));
				batchSummaryDTO.setTimeLost(resultSet.getString(17));
				batchSummaryDTOs.add(batchSummaryDTO);

			}

			return batchSummaryDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return batchSummaryDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}

	public List<BatchSummaryDTO> getDetails(String batchNumber) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchSummaryDTO> batchSummaryDTOs = new ArrayList<BatchSummaryDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select bm.BatchNumber,bm.ArticleId,bm.Mc_Num,bm.Tube_ID,bm.Start_Time,am.Article_Name,sum(prd.Novis_Production),sum(prd.Novis_Good),sum(prd.Novis_Rot_Rej),sum(prd.Novis_Lien_Rej),round(sum(prd.Tubes_Used),2),sum(prd.Glass_Waste),sum(prd.Activity_Time),sum(pcd.GoodVials),sum(pcd.Rej_Vials),sum(pcd.Packing_Scrap),sum(md.Time_Lost) from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on bm.BatchNumber=prd.BatchNumber join packdata pcd on prd.BatchNumber=pcd.BatchNumber and prd.shift=pcd.shift and prd.pdate=pcd.pdate join maintenancedata md on prd.BatchNumber=md.Batch_Num and prd.shift=md.shift and prd.pdate=md.mdate where bm.BatchNumber='"+ batchNumber + "'group by bm.BatchNumber";

			pstmt = conn.prepareStatement(query); // create a statement

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				BatchSummaryDTO batchSummaryDTO = new BatchSummaryDTO();
				batchSummaryDTO.setBatchNumber(resultSet.getString(1));
				batchSummaryDTO.setArticleId(resultSet.getString(2));
				batchSummaryDTO.setMachineNumber(resultSet.getString(3));
				batchSummaryDTO.setTubeId(resultSet.getString(4));
				batchSummaryDTO.setStartTime(resultSet.getString(5));
				batchSummaryDTO.setArticleName(resultSet.getString(6));
				batchSummaryDTO.setNovisProduction(resultSet.getString(7));
				batchSummaryDTO.setNovisGood(resultSet.getString(8));
				batchSummaryDTO.setNovisRotRej(resultSet.getString(9));
				batchSummaryDTO.setNovisLienRej(resultSet.getString(10));
				batchSummaryDTO.setTubesUsed(resultSet.getString(11));
				batchSummaryDTO.setGlassWaste(resultSet.getString(12));
				batchSummaryDTO.setActivityTime(resultSet.getString(13));
				batchSummaryDTO.setGoodVials(resultSet.getString(14));
				batchSummaryDTO.setRejVials(resultSet.getString(15));
				batchSummaryDTO.setPackingScrap(resultSet.getString(16));
				batchSummaryDTO.setTimeLost(resultSet.getString(17));
				batchSummaryDTOs.add(batchSummaryDTO);

			}

			return batchSummaryDTOs;

		} catch (Exception e) {
			e.printStackTrace();
			return batchSummaryDTOs;
		} finally {
			pstmt = null;
			conn = null;
		}
		// TODO Auto-generated method stub

	}

	public static List<BatchSummaryDTO> getBatchSummary1(
			BatchDetailsDTO batchDetailsDTO) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<BatchSummaryDTO> batchSummaryDTOs = new ArrayList<BatchSummaryDTO>();
		try {
			
			String uiDate = batchDetailsDTO.getFromDate();
			String startTime=batchDetailsDTO.getToDate();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String fromDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate);
			Date parsedDate1 = dateFormat.parse(startTime);
			String toDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate1);
			
			conn = CommonRef.getConnection();
			String articleId = "";
			if (batchDetailsDTO != null) {
				if (batchDetailsDTO.getArticleId().contains(",")) {
					StringTokenizer articleid = new StringTokenizer(
							batchDetailsDTO.getArticleId(), ",");
					List<String> str = new ArrayList<String>();
					while (articleid.hasMoreTokens()) {

						str.add(articleid.nextToken());

					}

					articleId = str.get(0);
				}
			}

			if ((batchDetailsDTO.getMachineNumber().equalsIgnoreCase("All"))
					&& (articleId.equalsIgnoreCase("All"))) {

				String query = "select bm.BatchNumber,bm.ArticleId,bm.Mc_Num,bm.Tube_ID,bm.Start_Time,am.Article_Name,sum(prd.Novis_Production),sum(prd.Novis_Good),sum(prd.Novis_Rot_Rej),sum(prd.Novis_Lien_Rej),ROUND(sum(prd.Tubes_Used),2),sum(prd.Glass_Waste),sum(prd.Activity_Time),sum(pcd.GoodVials),sum(pcd.Rej_Vials),sum(pcd.Packing_Scrap),sum(md.Time_Lost) from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on bm.BatchNumber=prd.BatchNumber join packdata pcd on prd.BatchNumber=pcd.BatchNumber and prd.shift=pcd.shift and prd.pdate=pcd.pdate join maintenancedata md on prd.BatchNumber=md.Batch_Num and prd.shift=md.shift and prd.pdate=md.mdate where prd.Pdate between ? and ? group by bm.BatchNumber ";
				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
			}else if ((!(batchDetailsDTO.getMachineNumber().equalsIgnoreCase("All")))
					&& (articleId.equalsIgnoreCase("All"))) {
				
				String query = "select bm.BatchNumber,bm.ArticleId,bm.Mc_Num,bm.Tube_ID,bm.Start_Time,am.Article_Name,sum(prd.Novis_Production),sum(prd.Novis_Good),sum(prd.Novis_Rot_Rej),sum(prd.Novis_Lien_Rej),ROUND(sum(prd.Tubes_Used),2),sum(prd.Glass_Waste),sum(prd.Activity_Time),sum(pcd.GoodVials),sum(pcd.Rej_Vials),sum(pcd.Packing_Scrap),sum(md.Time_Lost) from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on bm.BatchNumber=prd.BatchNumber join packdata pcd on prd.BatchNumber=pcd.BatchNumber and prd.shift=pcd.shift and prd.pdate=pcd.pdate join maintenancedata md on prd.BatchNumber=md.Batch_Num and prd.shift=md.shift and prd.pdate=md.mdate where prd.Pdate between ? and ?  and bm.Mc_Num =?  group by bm.BatchNumber ";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
				pstmt.setString(3, batchDetailsDTO.getMachineNumber());
				
				
			} else {

				String query = "select bm.BatchNumber,bm.ArticleId,bm.Mc_Num,bm.Tube_ID,bm.Start_Time,am.Article_Name,sum(prd.Novis_Production),sum(prd.Novis_Good),sum(prd.Novis_Rot_Rej),sum(prd.Novis_Lien_Rej),ROUND(sum(prd.Tubes_Used),2),sum(prd.Glass_Waste),sum(prd.Activity_Time),sum(pcd.GoodVials),sum(pcd.Rej_Vials),sum(pcd.Packing_Scrap),sum(md.Time_Lost) from batchmaster bm join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on bm.BatchNumber=prd.BatchNumber join packdata pcd on prd.BatchNumber=pcd.BatchNumber and prd.shift=pcd.shift and prd.pdate=pcd.pdate join maintenancedata md on prd.BatchNumber=md.Batch_Num and prd.shift=md.shift and prd.pdate=md.mdate where prd.Pdate between ? and ? and bm.ArticleId =?  and bm.Mc_Num =?  group by bm.BatchNumber ";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
				pstmt.setString(3, articleId);
				pstmt.setString(4, batchDetailsDTO.getMachineNumber());
			}

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				BatchSummaryDTO batchSummaryDTO = new BatchSummaryDTO();
				batchSummaryDTO.setBatchNumber(resultSet.getString(1));
				String batchNumberNew = resultSet.getString(1).substring(1);

				batchSummaryDTO.setBatchNumberNew(batchNumberNew);
				batchSummaryDTO.setArticleId(resultSet.getString(2));
				batchSummaryDTO.setMachineNumber(resultSet.getString(3));
				batchSummaryDTO.setTubeId(resultSet.getString(4));
				batchSummaryDTO.setStartTime(resultSet.getString(5));
				batchSummaryDTO.setArticleName(resultSet.getString(6));
				batchSummaryDTO.setNovisProduction(resultSet.getString(7));
				batchSummaryDTO.setNovisGood(resultSet.getString(8));
				batchSummaryDTO.setNovisRotRej(resultSet.getString(9));
				batchSummaryDTO.setNovisLienRej(resultSet.getString(10));
				batchSummaryDTO.setTubesUsed(resultSet.getString(11));
				batchSummaryDTO.setGlassWaste(resultSet.getString(12));
				batchSummaryDTO.setActivityTime(resultSet.getString(13));
				batchSummaryDTO.setGoodVials(resultSet.getString(14));
				batchSummaryDTO.setRejVials(resultSet.getString(15));
				batchSummaryDTO.setPackingScrap(resultSet.getString(16));
				batchSummaryDTO.setTimeLost(resultSet.getString(17));
				batchSummaryDTOs.add(batchSummaryDTO);

			}

			return batchSummaryDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return batchSummaryDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}

	}
}
