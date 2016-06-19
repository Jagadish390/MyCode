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

import com.java.DTO.BatchSummaryDTO;
import com.java.DTO.VialsReportDTO;
import com.java.DTO.VialsReportDetailDTO;
import com.java.util.CommonRef;

public class VialsReportDAO {

	public List<VialsReportDetailDTO> getVialReportDetails(
			VialsReportDTO vialsReportDTO) throws SQLException {

		List<VialsReportDetailDTO> vialsReportDetailDTOs = new ArrayList<VialsReportDetailDTO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			String uiDate = vialsReportDTO.getFromDate();
			String startTime = vialsReportDTO.getToDate();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedDate = dateFormat.parse(uiDate);
			String fromDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate);
			Date parsedDate1 = dateFormat.parse(startTime);
			String toDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(parsedDate1);

			conn = CommonRef.getConnection();

			if ((vialsReportDTO.getMachineNumber().equalsIgnoreCase("All"))
					&& (vialsReportDTO.getShift().equalsIgnoreCase("All"))) {

				String query = "select pd.Pdate,pd.shift,bm.mc_num as Machine_Number,pd.Novis_Production,pd.VialsPertube as Vials_Per_Tube,pd.Tubes_Used,ROUND(am.weight,2) as Vial_Weight,ROUND(tb.tube_weight,2) as Tube_Weight,pc.GoodVials as Packed_Good_Vials,pd.Glass_Waste as Production_Scrap,pc.Packing_Scrap,pd.Novis_Rot_Rej+pd.Novis_Lien_Rej as Novis_Rejected,pc.GoodVials as Novis_Accepted,ROUND(((pd.VialsPertube*am.weight*100)/tb.tube_weight/1000),2) as Consumed_Ideal from productiondata pd join batchmaster bm on pd.BatchNumber=bm.BatchNumber join articleMaster am on am.Focus_Code=bm.ArticleID join tubeMaster tb on tb.tube_ID =bm.Tube_ID join packdata pc on pd.BatchNumber=pc.batchNumber and pd.shift=pc.shift and pd.Pdate=pc.Pdate where pd.pdate between  ? and ?";
				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
			} else if ((!(vialsReportDTO.getMachineNumber()
					.equalsIgnoreCase("All")))
					&& (vialsReportDTO.getShift().equalsIgnoreCase("All"))) {

				String query = "select pd.Pdate,pd.shift,bm.mc_num as Machine_Number,pd.Novis_Production,pd.VialsPertube as Vials_Per_Tube,pd.Tubes_Used,ROUND(am.weight,2) as Vial_Weight,ROUND(tb.tube_weight,2) as Tube_Weight,pc.GoodVials as Packed_Good_Vials,pd.Glass_Waste as Production_Scrap,pc.Packing_Scrap,pd.Novis_Rot_Rej+pd.Novis_Lien_Rej as Novis_Rejected,pc.GoodVials as Novis_Accepted,ROUND(((pd.VialsPertube*am.weight*100)/tb.tube_weight/1000),2) as Consumed_Ideal from productiondata pd join batchmaster bm on pd.BatchNumber=bm.BatchNumber join articleMaster am on am.Focus_Code=bm.ArticleID join tubeMaster tb on tb.tube_ID =bm.Tube_ID join packdata pc on pd.BatchNumber=pc.batchNumber and pd.shift=pc.shift and pd.Pdate=pc.Pdate where pd.pdate between ? and ? and bm.mc_num=?";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
				pstmt.setString(3, vialsReportDTO.getMachineNumber());
			} else if (((vialsReportDTO.getMachineNumber()
					.equalsIgnoreCase("All")))
					&& (!vialsReportDTO.getShift().equalsIgnoreCase("All"))) {

				String query = "select pd.Pdate,pd.shift,bm.mc_num as Machine_Number,pd.Novis_Production,pd.VialsPertube as Vials_Per_Tube,pd.Tubes_Used,ROUND(am.weight,2) as Vial_Weight,ROUND(tb.tube_weight,2) as Tube_Weight,pc.GoodVials as Packed_Good_Vials,pd.Glass_Waste as Production_Scrap,pc.Packing_Scrap,pd.Novis_Rot_Rej+pd.Novis_Lien_Rej as Novis_Rejected,pc.GoodVials as Novis_Accepted,ROUND(((pd.VialsPertube*am.weight*100)/tb.tube_weight/1000),2) as Consumed_Ideal from productiondata pd join batchmaster bm on pd.BatchNumber=bm.BatchNumber join articleMaster am on am.Focus_Code=bm.ArticleID join tubeMaster tb on tb.tube_ID =bm.Tube_ID join packdata pc on pd.BatchNumber=pc.batchNumber and pd.shift=pc.shift and pd.Pdate=pc.Pdate where pd.pdate between ? and ? and  pd.shift=?";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
				pstmt.setString(3, vialsReportDTO.getShift());
			}

			else {

				String query = "select pd.Pdate,pd.shift,bm.mc_num as Machine_Number,pd.Novis_Production,pd.VialsPertube as Vials_Per_Tube,pd.Tubes_Used,ROUND(am.weight,2) as Vial_Weight,ROUND(tb.tube_weight,2) as Tube_Weight,pc.GoodVials as Packed_Good_Vials,pd.Glass_Waste as Production_Scrap,pc.Packing_Scrap,pd.Novis_Rot_Rej+pd.Novis_Lien_Rej as Novis_Rejected,pc.GoodVials as Novis_Accepted,ROUND(((pd.VialsPertube*am.weight*100)/tb.tube_weight/1000),2) as Consumed_Ideal from productiondata pd join batchmaster bm on pd.BatchNumber=bm.BatchNumber join articleMaster am on am.Focus_Code=bm.ArticleID join tubeMaster tb on tb.tube_ID =bm.Tube_ID join packdata pc on pd.BatchNumber=pc.batchNumber and pd.shift=pc.shift and pd.Pdate=pc.Pdate where pd.pdate between ? and ? and bm.mc_num=? and  pd.shift=?";

				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, fromDate);
				pstmt.setString(2, toDate);
				pstmt.setString(3, vialsReportDTO.getMachineNumber());
				pstmt.setString(4, vialsReportDTO.getShift());
			}

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				VialsReportDetailDTO vialsReportDetailDTO = new VialsReportDetailDTO();
				vialsReportDetailDTO.setProductionDate(resultSet.getString(1));
				vialsReportDetailDTO.setShift(resultSet.getString(2));
				vialsReportDetailDTO.setVialsPerTube(resultSet.getString(5));
				vialsReportDetailDTO.setNovisTubeUsed(resultSet.getString(6));
				vialsReportDetailDTO.setProductionScrap(Double
						.parseDouble(resultSet.getString(10)));
				vialsReportDetailDTO.setVialWeight(resultSet.getString(7));
				vialsReportDetailDTO.setTubeWeight(resultSet.getString(8));
				vialsReportDetailDTO.setPackedGoodVials(resultSet.getString(9));
				vialsReportDetailDTO.setPackedScrap(resultSet.getString(11));
				vialsReportDetailDTO.setNovisAccepted(Double
						.parseDouble(resultSet.getString(13)));
				vialsReportDetailDTO.setNovisRejected(Double
						.parseDouble(resultSet.getString(12)));
				vialsReportDetailDTO.setConsumedIdeal(Double.parseDouble(resultSet.getString(14)));
				vialsReportDetailDTO=VialsReportDAO
				.calculateDetails(vialsReportDetailDTO);
				vialsReportDetailDTOs.add(vialsReportDetailDTO);
			}

			return vialsReportDetailDTOs;
		} catch (Exception e) {
			e.printStackTrace();
			return vialsReportDetailDTOs;
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	private static VialsReportDetailDTO calculateDetails(
			VialsReportDetailDTO vialsReportDetailDTO) {

		double consumedIdeal = vialsReportDetailDTO.getConsumedIdeal();
	//	System.out.println("consumed ideal:-"+consumedIdeal);
		double scrapIdeal = CommonRef.roundTwoDecimals((100 - consumedIdeal));
	//	System.out.println("Scrap ideal:-"+scrapIdeal);
		double productionScrapIdeal = CommonRef.roundTwoDecimals((Double.parseDouble(vialsReportDetailDTO
				.getNovisTubeUsed())*0.01) * scrapIdeal);
	//	System.out.println("Production scrap ideal:-"+productionScrapIdeal);
		double scrapDifference = CommonRef.roundTwoDecimals((vialsReportDetailDTO.getProductionScrap())
				- productionScrapIdeal);
	//	System.out.println("scrapDifference:-"+scrapDifference);
		int packingRejection = (int) (Double.parseDouble(vialsReportDetailDTO
				.getPackedScrap())*1000
				/ (Double.parseDouble(vialsReportDetailDTO.getVialWeight()) ));
	//	System.out.println("packingRejection:-"+packingRejection);

		int actualProductionInPacking = (int) ((Double.parseDouble(vialsReportDetailDTO.getPackedGoodVials()))
				-packingRejection);
		
		int vialsFallen = (int) (((scrapDifference*1000/ Double.parseDouble(vialsReportDetailDTO.getVialWeight())))
				- vialsReportDetailDTO.getNovisRejected());
		int missingVials = (int) (vialsReportDetailDTO.getNovisAccepted()-actualProductionInPacking-vialsFallen);
	//	System.out.println(vialsFallen);
		vialsReportDetailDTO.setScrapIdeal(scrapIdeal);
		vialsReportDetailDTO.setProductionScrapIdeal(productionScrapIdeal);
		vialsReportDetailDTO.setScrapDifference(scrapDifference);
		vialsReportDetailDTO
				.setActualProductionInPacking(actualProductionInPacking);
		vialsReportDetailDTO.setPackingRejection(packingRejection);
		vialsReportDetailDTO.setVialsFallen(vialsFallen);
		vialsReportDetailDTO.setMissingVials(missingVials);

		return vialsReportDetailDTO;
	}

}
