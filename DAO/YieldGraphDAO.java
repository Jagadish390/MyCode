package com.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.DTO.YieldGraphDTO;
import com.java.util.CommonRef;

public class YieldGraphDAO {
public List<YieldGraphDTO> getYieldDetails(YieldGraphDTO yieldGraphDTO)throws SQLException{
	Connection conn = null;
	PreparedStatement pstmt = null;
	List<YieldGraphDTO> yieldGraphDTOs=new ArrayList<YieldGraphDTO>();
	try{
		conn = CommonRef.getConnection();
		if(yieldGraphDTO.getMachineNumber().equalsIgnoreCase("All")&yieldGraphDTO.getShift().equalsIgnoreCase("ALL")){
		String query="SELECT pd.Pdate,ROUND((sum(am.Weight*pd.GoodVials)/1000/SUM(`Tubes_Used`)*100),2) as 'yield' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on prd.PDate=pd.Pdate and prd.Shift=pd.Shift and prd.BatchNumber=pd.BatchNumber where prd.BatchNumber!='Holiday' and pd.PDate between ? and ? group by pd.Pdate";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, CommonRef.formatDate(yieldGraphDTO.getFromDate()));
		pstmt.setString(2, CommonRef.formatDate(yieldGraphDTO.getToDate()));
		
		}
		else if((yieldGraphDTO.getShift().equalsIgnoreCase("All"))){
			String query="SELECT pd.Pdate,ROUND((sum(am.Weight*pd.GoodVials)/1000/SUM(`Tubes_Used`)*100),2) as 'yield' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on prd.PDate=pd.Pdate and prd.Shift=pd.Shift and prd.BatchNumber=pd.BatchNumber where prd.BatchNumber!='Holiday' and pd.PDate between ? and ? and  bm.Mc_Num=? group by pd.Pdate";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(yieldGraphDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(yieldGraphDTO.getToDate()));
			pstmt.setString(3, yieldGraphDTO.getMachineNumber());
		}
		else if((yieldGraphDTO.getMachineNumber().equalsIgnoreCase("All"))){
			String query="SELECT pd.Pdate,ROUND((sum(am.Weight*pd.GoodVials)/1000/SUM(`Tubes_Used`)*100),2) as 'yield' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on prd.PDate=pd.Pdate and prd.Shift=pd.Shift and prd.BatchNumber=pd.BatchNumber where prd.BatchNumber!='Holiday' and pd.PDate between ? and ? and  prd.shift=? group by pd.Pdate";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(yieldGraphDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(yieldGraphDTO.getToDate()));
			pstmt.setString(3, yieldGraphDTO.getShift());
		}
		else {
			String query="SELECT pd.Pdate,ROUND((sum(am.Weight*pd.GoodVials)/1000/SUM(`Tubes_Used`)*100),2) as 'yield' from batchmaster bm join  packdata pd on bm.BatchNumber=pd.BatchNumber join articlemaster am on bm.ArticleId=am.Focus_Code join productiondata prd on prd.PDate=pd.Pdate and prd.Shift=pd.Shift and prd.BatchNumber=pd.BatchNumber where prd.BatchNumber!='Holiday' and pd.PDate between ? and ?  and prd.shift=? and bm.Mc_Num=? group by pd.Pdate";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, CommonRef.formatDate(yieldGraphDTO.getFromDate()));
			pstmt.setString(2, CommonRef.formatDate(yieldGraphDTO.getToDate()));
			pstmt.setString(3, yieldGraphDTO.getShift());
			pstmt.setString(4, yieldGraphDTO.getMachineNumber());
		}
		ResultSet resultSet = pstmt.executeQuery();
		while (resultSet.next()) {
			YieldGraphDTO yieldGraphDTO2=new YieldGraphDTO();
			yieldGraphDTO2.setYieldDate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(1))).substring(0, 5));
			yieldGraphDTO2.setYield(resultSet.getString(2));
			yieldGraphDTOs.add(yieldGraphDTO2);
		}

			
	return yieldGraphDTOs;
	
}
	catch (Exception e){
		e.printStackTrace();
		return yieldGraphDTOs;
	}
	finally{
		pstmt.close();
		conn.close();
	}
}
}