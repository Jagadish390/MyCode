package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.DTO.DateDTO;
import com.java.DTO.TubesUsedDTO;
import com.java.util.CommonRef;

public class TubesUsedDAO {

	public List<TubesUsedDTO> getUsedTubeDetails(DateDTO dateDTO) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<TubesUsedDTO> tubesUsedDTOs=new ArrayList<TubesUsedDTO>();
		try {
			conn = CommonRef.getConnection();
			String query = "select prd.Pdate,bm.Mc_Num,tm.Tube_ID,bm.BatchNumber,tm.Spec,ROUND(sum(prd.Tubes_Used),2),tm.Make from tubemaster tm join batchmaster bm on tm.Tube_ID=bm.Tube_ID join productiondata prd on bm.BatchNumber=prd.BatchNumber where prd.Pdate between ? and ? group by tm.Tube_ID order by prd.Pdate ASC";
			
			pstmt = conn.prepareStatement(query); // create a statement
			
			pstmt.setString(1, CommonRef.formatDate(dateDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(dateDTO.getToDate()));
			
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					TubesUsedDTO tubesUsedDTO=new TubesUsedDTO();
					tubesUsedDTO.setProductionDate(resultSet.getDate(1).toString());
					tubesUsedDTO.setMachineNumber(resultSet.getString(2));
					tubesUsedDTO.setTubeId(resultSet.getString(3));
					tubesUsedDTO.setBatchNumber(resultSet.getString(4));
					tubesUsedDTO.setSpecification(resultSet.getString(5));
					tubesUsedDTO.setTubesUsed(String.valueOf(resultSet.getString(6)));
					tubesUsedDTO.setMake(resultSet.getString(7));
tubesUsedDTOs.add(tubesUsedDTO);

				}	
				
		}
			return tubesUsedDTOs;
	}
		
		catch (Exception e) {
			e.printStackTrace();
			return tubesUsedDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
}
	public List<TubesUsedDTO> getCompleteTubeDetails(String tubeId, String fromDate, String toDate) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<TubesUsedDTO> tubesUsedDTOs=new ArrayList<TubesUsedDTO>();
		try {
			conn = CommonRef.getConnection();
			fromDate=CommonRef.formatDate(fromDate);
			toDate=CommonRef.formatDate(toDate);
			String query = "select prd.Pdate,bm.Mc_Num,tm.Tube_ID,bm.ArticleId,tm.Spec,prd.Tubes_Used,tm.Make from tubemaster tm join batchmaster bm on tm.Tube_ID=bm.Tube_ID join productiondata prd on bm.BatchNumber=prd.BatchNumber where tm.Tube_ID = '" +tubeId + "' and prd.Pdate between '"+fromDate+"' and '"+toDate+"'";

			pstmt = conn.prepareStatement(query); // create a statement
			
			
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					TubesUsedDTO tubesUsedDTO=new TubesUsedDTO();
					tubesUsedDTO.setProductionDate(resultSet.getDate(1).toString());
					tubesUsedDTO.setMachineNumber(resultSet.getString(2));
					tubesUsedDTO.setTubeId(resultSet.getString(3));
					tubesUsedDTO.setFocusCode(resultSet.getString(4));
					tubesUsedDTO.setSpecification(resultSet.getString(5));
					tubesUsedDTO.setTubesUsed(String.valueOf(resultSet.getString(6)));
					tubesUsedDTO.setMake(resultSet.getString(7));
tubesUsedDTOs.add(tubesUsedDTO);

				}	
				
		}
			return tubesUsedDTOs;
	}
		
		catch (Exception e) {
			e.printStackTrace();
			return tubesUsedDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
}
}
