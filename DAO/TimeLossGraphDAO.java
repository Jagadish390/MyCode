package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java.DTO.DateDTO;
import com.java.DTO.TimeLossGraphDTO;
import com.java.util.CommonRef;

public class TimeLossGraphDAO {
	
	public List<TimeLossGraphDTO> getTimeLossDetails(DateDTO dateDTO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		

		List<TimeLossGraphDTO> timeLossGraphDTOs=new ArrayList<TimeLossGraphDTO>();
		
		String fromDate = CommonRef.formatDate(dateDTO.getFromDate());
		String toDate = CommonRef.formatDate(dateDTO.getToDate());
		
		try {
			conn = CommonRef.getConnection();
			String query = "select maintReason,timeLost from maintenancedetail where mDate between ? and ? group by maintReason";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fromDate);
			pstmt.setString(2, toDate);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				{
					TimeLossGraphDTO timeLossGraphDTO2=new TimeLossGraphDTO();
					timeLossGraphDTO2.setTimeLossReason(resultSet.getString(1));
					timeLossGraphDTO2.setTimeLost(resultSet.getInt(2));
					timeLossGraphDTOs.add(timeLossGraphDTO2);
				}
	}
return timeLossGraphDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return timeLossGraphDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}


}

