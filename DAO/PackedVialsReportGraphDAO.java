package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.java.DTO.PackedVialsReportGraphDTO;
import com.java.DTO.TubeMasterDTO;
import com.java.util.CommonRef;

public class PackedVialsReportGraphDAO {



	public List<PackedVialsReportGraphDTO> getPackedVialsDetails(PackedVialsReportGraphDTO packedVialsReportGraphDTO2) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<PackedVialsReportGraphDTO> packedVialsReportGraphDTOs = new ArrayList<PackedVialsReportGraphDTO>();
		CommonRef commonRef = new CommonRef();
		//String fromDate = commonRef.formatDate(packedVialsReportGraphDTO2.getFromDate());
		
		try {
			conn = CommonRef.getConnection();
			if(packedVialsReportGraphDTO2.getMachineNumber().equalsIgnoreCase("All")&packedVialsReportGraphDTO2.getShift().equalsIgnoreCase("All"))
			{
				String query = "select sum(GoodVials) as GoodVials,PDate from packdata where BatchNumber not in ('Holiday') and pdate between ? and ? group by PDate";
				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, CommonRef.formatDate(packedVialsReportGraphDTO2.getFromDate()));
				pstmt.setString(2, CommonRef.formatDate(packedVialsReportGraphDTO2.getToDate()));
				
				
				//ResultSet resultSet = pstmt.executeQuery();
			}
			else if(packedVialsReportGraphDTO2.getShift().equalsIgnoreCase("All"))
			{
			String query = "select sum(GoodVials) as GoodVials,PDate from packdata where BatchNumber not in ('Holiday') and mcnumber = ? and pdate between ? and ? group by PDate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(2, CommonRef.formatDate(packedVialsReportGraphDTO2.getFromDate()));
			pstmt.setString(3, CommonRef.formatDate(packedVialsReportGraphDTO2.getToDate()));
			pstmt.setString(1, packedVialsReportGraphDTO2.getMachineNumber());
			
			}
			else if(packedVialsReportGraphDTO2.getMachineNumber().equalsIgnoreCase("All"))
			{
			String query = "select sum(GoodVials) as GoodVials,PDate from packdata where BatchNumber not in ('Holiday') and shift = ? and pdate between ? and ? group by PDate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(2, CommonRef.formatDate(packedVialsReportGraphDTO2.getFromDate()));
			pstmt.setString(3, CommonRef.formatDate(packedVialsReportGraphDTO2.getToDate()));
			pstmt.setString(1, packedVialsReportGraphDTO2.getShift());
			
			}
			else{
				String query = "select sum(GoodVials) as GoodVials,PDate from packdata where BatchNumber not in ('Holiday') and mcnumber = ? and shift =? and pdate between ? and ? group by PDate";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, packedVialsReportGraphDTO2.getMachineNumber());
				pstmt.setString(2, packedVialsReportGraphDTO2.getShift());
				pstmt.setString(3, CommonRef.formatDate(packedVialsReportGraphDTO2.getFromDate()));
				pstmt.setString(4, CommonRef.formatDate(packedVialsReportGraphDTO2.getToDate()));
			
			}
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					PackedVialsReportGraphDTO packedVialsReportGraphDTO=new PackedVialsReportGraphDTO();
					packedVialsReportGraphDTO.setPackingDate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(2))).substring(0, 5));
					packedVialsReportGraphDTO.setGoodVials(resultSet.getString(1));
					packedVialsReportGraphDTOs.add(packedVialsReportGraphDTO);
				}
			}

			return packedVialsReportGraphDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return packedVialsReportGraphDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}


}

