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

import com.java.DTO.GoodTonsDTO;
import com.java.DTO.VialsReportDetailDTO;
import com.java.util.CommonRef;

public class GoodTonsDAO {
	
	
	public List<GoodTonsDTO> getGoodTonsGraph(GoodTonsDTO goodTonsDTO) throws SQLException{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	List<GoodTonsDTO> goodTonsDTOs=new ArrayList<GoodTonsDTO>();
	try {

	
		conn = CommonRef.getConnection();
		
		if(goodTonsDTO.getMachineNumber().equalsIgnoreCase("All")&goodTonsDTO.getShift().equalsIgnoreCase("All")){

			String query = "select pd.Pdate,round((am.Weight*pd.GoodVials)/1000,2) as 'GoodKg' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? and pd.batchnumber not in ('Holiday')  group by pd.Pdate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(goodTonsDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(goodTonsDTO.getToDate()));
		//	pstmt.setString(3, goodTonsDTO.getMachineNumber());
		}
		else if(goodTonsDTO.getShift().equalsIgnoreCase("All")){
			String query = "select pd.Pdate,round((am.Weight*pd.GoodVials)/1000,2) as 'GoodKg' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? and bm.Mc_Num=? and pd.batchnumber not in ('Holiday') group by pd.Pdate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(goodTonsDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(goodTonsDTO.getToDate()));
			pstmt.setString(3, goodTonsDTO.getMachineNumber());
		}
		else if(goodTonsDTO.getMachineNumber().equalsIgnoreCase("All")){
			String query = "select pd.Pdate,round((am.Weight*pd.GoodVials)/1000,2) as 'GoodKg' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? and pd.shift=? and pd.batchnumber not in ('Holiday')  group by pd.Pdate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(goodTonsDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(goodTonsDTO.getToDate()));
			pstmt.setString(3, goodTonsDTO.getShift());
		}
		else{
			String query = "select pd.Pdate,round((am.Weight*pd.GoodVials)/1000,2) as 'GoodKg' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code where pd.PDate between ? and ? and pd.shift=? and bm.Mc_Num=? and pd.batchnumber not in ('Holiday') group by pd.Pdate";

			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, CommonRef.formatDate(goodTonsDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(goodTonsDTO.getToDate()));
			pstmt.setString(3, goodTonsDTO.getShift());
			pstmt.setString(4, goodTonsDTO.getMachineNumber());
			
		}
		ResultSet resultSet = pstmt.executeQuery();

		while (resultSet.next()) {

			GoodTonsDTO goodDTO=new GoodTonsDTO();
			goodDTO.setPdate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(1))).substring(0, 5));
			goodDTO.setGoodKg(resultSet.getString(2));

			goodTonsDTOs.add(goodDTO);
		
		}

		return goodTonsDTOs;
	} catch (Exception e) {
		e.printStackTrace();
		return goodTonsDTOs;
	} finally {
		pstmt.close();
		conn.close();
	}
}

}
