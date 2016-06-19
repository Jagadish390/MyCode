package com.java.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java.DAO.BaseDAO;
import com.java.DAO.EmployeeDAO;
import com.java.DAO.LookupDAO;
import com.java.DAO.MaintainanceDAO;
import com.java.DTO.ArticleMasterDTO;
import com.java.DTO.BatchDTO;
import com.java.DTO.EmployeeDTO;
import com.java.DTO.LookUpDTO;
import com.java.DTO.MachineMasterDTO;
import com.java.DTO.MaintainanceDTO;
import com.java.DTO.PackingDTO;
import com.java.DTO.ProductionDTO;
import com.java.DTO.TubeMasterDTO;
import com.java.util.CommonRef;

@Controller
@Scope("session")
public class EmployeeController {

	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private static final Logger logger = Logger
			.getLogger(EmployeeController.class.getName());

	@RequestMapping("/add.htm")
	public ModelAndView openEmployeePage(HttpSession session)
			throws SQLException {
		List<EmployeeDTO> employeeList = viewEmployeesForUser();
		ModelAndView model1 = new ModelAndView("userMaster");
		logger.info("Employee:" + session.getAttribute("UserName")
				+ " trying add an employee");
		model1.addObject("employeeList", employeeList);
		return model1;
	}

	@RequestMapping("/notAuthPage.htm")
	public ModelAndView notAuthPage(HttpSession session) {
		ModelAndView model1 = new ModelAndView("AccessDenied");
		String displayMessage = "You are not authorize to view this page. ";
		logger.info("Employee:" + session.getAttribute("UserName")
				+ " denied due lack of previlages");
		model1.addObject("displayMessage", displayMessage);
		return model1;
	}

	@RequestMapping("/openLoginPage.htm")
	public ModelAndView openLoginPage() {
		logger.info("Welcome to cogent application");
		ModelAndView model1 = new ModelAndView("login");
		return model1;
	}

	@RequestMapping("/logout.htm")
	public ModelAndView logout(HttpServletRequest request, HttpSession session) {
		logger.info("Employee:" + session.getAttribute("UserName")
				+ " has logged out.");
		request.getSession().removeAttribute("UserName");
		request.getSession().invalidate();
		ModelAndView model1 = new ModelAndView("login");
		return model1;
	}

	@RequestMapping("/validateLogin.htm")
	public ModelAndView validateLogin(
			@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			HttpServletRequest request) throws Exception {

		EmployeeDAO employeeDAO = new EmployeeDAO();
		ResultSet resultSet = employeeDAO.validateLogin(employeeDTO);
		String status = "login";
		String displayMessage = "User does not exist";

		while (resultSet.next()) {
			int loginCount=resultSet.getInt(9);
			String userId=resultSet.getString(5);
			//System.out.println("Count:"+loginCount);
			if (employeeDTO.getPassword().equals(resultSet.getString(3))&&loginCount<4) {
				
				//System.out.println("1");
	
				request.getSession().setAttribute("UserName",
						resultSet.getString(5));
				request.getSession().setAttribute("role",
						resultSet.getString(7));
				status = "index";
				logger.info(employeeDTO.getUserName() + " has logged in at: "
						+ new Date());
			} 
			
			else if(loginCount>3){
				
			//	System.out.println("2");
			//	System.out.println("Login count:"+loginCount);
				status = "login";
				displayMessage = "Account Locked. Please contact admin to unlock your account";
				employeeDAO.updateLoginCount(userId);
			}
			
			else{
				
			//	System.out.println("3");
				
			//	System.out.println("In invalid credentials");
		
			//	System.out.println("Status:"+status);
				displayMessage = "Invalid credantials";
				status = employeeDAO.updateLoginCount(userId);
				
				logger.info("Employee " + employeeDTO.getUserName()
						+ " has entered invalid credentails at " + new Date());
			}

		}

		ModelAndView model1 = new ModelAndView(status);
		model1.addObject("displayMessage", displayMessage);
		return model1;
	}

	@RequestMapping("/view.htm")
	public ModelAndView viewEmployees(HttpSession session) throws SQLException {
		List<EmployeeDTO> employeeList = null;
		employeeList = EmployeeDAO.viewEmployees();
		logger.info("Employee:" + session.getAttribute("UserName")
				+ " accessing list of employees.");
		ModelAndView model1 = new ModelAndView("ViewEmployees");
		model1.addObject("employeeList", employeeList);
		return model1;
	}

	public List<EmployeeDTO> viewEmployeesForUser() throws SQLException {
		List<EmployeeDTO> employeeList = null;
		employeeList = EmployeeDAO.viewEmployees();
		return employeeList;
	}

	@RequestMapping("/addEmployee.htm")
	public ModelAndView addEmployee(
			@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			HttpSession session) throws Exception {
		String status = "userMaster";
		String displayMessage = "";
		ModelAndView model1 = new ModelAndView();
		displayMessage = employeeDAO.addEmployee(employeeDTO);
		model1.setViewName(status);
		List<EmployeeDTO> employeeList = viewEmployeesForUser();
		model1.addObject("employeeList", employeeList);
		model1.addObject("displayMessage", displayMessage);
		return model1;
	}

	@RequestMapping("/updateEmployeeData.htm")
	public ModelAndView updateEmployeeData(
			@ModelAttribute("EmployeeDTO") EmployeeDTO employeeDTO,
			HttpSession session) throws SQLException {

		String displayMessage = employeeDAO.updateEmployeeMaster(employeeDTO);
		String status = "ViewEmployees";
		ModelAndView model1 = new ModelAndView(status);

		List<EmployeeDTO> employeeList = null;
		employeeList = EmployeeDAO.viewEmployees();
		model1.addObject("employeeList", employeeList);
		model1.addObject("displayMessage", displayMessage);

		return model1;
	}

	@RequestMapping("/delete.htm")
	public ModelAndView delete(@RequestParam("id") String id,
			HttpSession session) throws SQLException {

		ModelAndView model1 = new ModelAndView();

		StringTokenizer stringTokenizer = new StringTokenizer(id, "-");
		List<String> str = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {

			str.add(stringTokenizer.nextToken());

		}

		String tableName = str.get(0);
		String primaryKey = str.get(1);

		EmployeeDAO employeeDAO = new EmployeeDAO();
		String status = employeeDAO.deleteDetails(tableName, primaryKey);
		String viewPage = null;

		switch (tableName) {
		case "lookuptable":
			viewPage = "viewLookupDetails";
			List<LookUpDTO> lookUpDTOs = null;
			lookUpDTOs = LookupDAO.viewLookUpDetails();
			model1.addObject("lookUpDTOs", lookUpDTOs);
			break;
		
		case "usermaster":
			viewPage = "ViewEmployees";
			List<EmployeeDTO> employeeList = null;
			employeeList = EmployeeDAO.viewEmployees();
			model1.addObject("employeeList", employeeList);
			break;
		case "articlemaster":
			viewPage = "viewArticleMaster";
			List<ArticleMasterDTO> articleMasterDTOs = null;
			articleMasterDTOs = ArticleController.getArticleMasterDetails();
			model1.addObject("articleMasterDTOs", articleMasterDTOs);
			if (status
					.equals("Error in deleting details. Please contact Admin")) {
				status = "Corresponding records are present in Batch details";
			}
			break;
		case "machinemaster":
			viewPage = "viewMachineMaster";
			List<MachineMasterDTO> machineMasterDTOs = null;
			machineMasterDTOs = MachineMasterController
					.getMachineMasterDetails();
			model1.addObject("machineMasterDTOs", machineMasterDTOs);
			if (status
					.equals("Error in deleting details. Please contact Admin")) {
				status = "Corresponding records are present in Batch details";
			}
			break;
		case "batchmaster":
			viewPage = "viewBatchMaster";
			List<BatchDTO> batchDTOs = null;
			batchDTOs = BatchController.getBatchMasterDetails();
			model1.addObject("batchDTOs", batchDTOs);
			if (status
					.equals("Error in deleting details. Please contact Admin")) {
				status = "Corresponding records are present in production details";
			}
			break;
		case "maintenancedata":
			viewPage = "viewMaintainanceMaster";
			List<MaintainanceDTO> maintainanceDTOs = null;
			MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
			maintainanceDTOs = maintainanceDAO.getMaintenanceMasterDetails();
			model1.addObject("maintainanceDTOs", maintainanceDTOs);
			break;
		case "productiondata":
			viewPage = "viewProductionMaster";
			List<ProductionDTO> productionDTOs = null;
			productionDTOs = ProductionController.getProductionDetails();
			model1.addObject("productionDTOs", productionDTOs);
			break;
		case "packdata":
			viewPage = "viewPackingMaster";
			List<PackingDTO> packingDTOs = null;
			packingDTOs = PackingController.getPackingMasterDetails();
			model1.addObject("packingDTOs", packingDTOs);
			break;
		case "tubemaster":
			viewPage = "viewTubeMaster";
			List<TubeMasterDTO> tubeMasterDTOs = null;
			tubeMasterDTOs = TubeController.getTubeMasterDetails();
			model1.addObject("tubeMasterDTOs", tubeMasterDTOs);
			break;

		default:
			break;
		}

		model1.addObject("displayMessage", status);
		model1.setViewName(viewPage);

		return model1;

	}

	@RequestMapping("/editEmployee.htm")
	public ModelAndView editEmployee(HttpServletRequest request)
			throws SQLException {

		ModelAndView modelAndView = new ModelAndView();

		String userId = (String) request.getSession().getAttribute("UserName");
		ResultSet resultSet = employeeDAO.editEmployee(userId);
		EmployeeDTO employeeDTO = new EmployeeDTO();
		while (resultSet.next()) {

			employeeDTO.setId(String.valueOf(resultSet.getInt(1)));
			employeeDTO.setUserName(resultSet.getString(2));
			employeeDTO.setPassword(resultSet.getString(3));
			employeeDTO.setEmpId(String.valueOf(resultSet.getInt(4)));
			employeeDTO.setUserId(resultSet.getString(5));
		}
		modelAndView.addObject("employeeDTO", employeeDTO);
		modelAndView.setViewName("editEmployee");

		return modelAndView;
	}

	@RequestMapping("/edit.htm")
	public ModelAndView edit(@RequestParam("id1") String id)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		StringTokenizer stringTokenizer = new StringTokenizer(id, "-");
		List<String> str = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {

			str.add(stringTokenizer.nextToken());

		}

		String tableName = str.get(0);
		String primaryKey = str.get(1);

		EmployeeDAO employeeDAO = new EmployeeDAO();
		ResultSet resultSet = employeeDAO.getDetails(tableName, primaryKey);

		switch (tableName) {
		
		case "lookuptable":
			while (resultSet.next()) {

				LookUpDTO lookUpDTO=new LookUpDTO();
				lookUpDTO.setId(resultSet.getString(1));
				lookUpDTO.setParam(resultSet.getString(2));
				lookUpDTO.setData(resultSet.getString(3));
				lookUpDTO.setUserId(resultSet.getString(4));
				modelAndView.addObject("lookUpDTO", lookUpDTO);
				modelAndView.setViewName("editLookUpDetails");
				break;
			}

		case "usermaster":
			while (resultSet.next()) {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				employeeDTO.setId(tableName
						+ String.valueOf(resultSet.getInt(1)));
				employeeDTO.setUserName(resultSet.getString(2));
				employeeDTO.setPassword(resultSet.getString(3));
				employeeDTO.setEmpId(String.valueOf(resultSet.getInt(4)));
				employeeDTO.setUserId(resultSet.getString(5));
				employeeDTO.setDepartment(resultSet.getString(8));
				modelAndView.addObject("employeeDTO", employeeDTO);
				modelAndView.setViewName("editEmployee");
				break;
			}

		case "articlemaster":
			while (resultSet.next()) {
				ArticleMasterDTO articleMasterDTO = new ArticleMasterDTO();
				articleMasterDTO.setId(resultSet.getString(1));
				articleMasterDTO.setArticleName(resultSet.getString(2));
				articleMasterDTO.setFocusCode(resultSet.getString(3));
				articleMasterDTO.setDrawingNumber(resultSet.getString(4));
				articleMasterDTO.setSpecification(resultSet.getString(5));
				articleMasterDTO.setWeight(resultSet.getString(6));
				articleMasterDTO.setUserId(resultSet.getString(7));
				articleMasterDTO.setProductTargetSpeed(resultSet.getInt(9));
				modelAndView.addObject("articleMasterDTO", articleMasterDTO);
				modelAndView.setViewName("editArticleMaster");
				break;
			}

		case "machinemaster":
			while (resultSet.next()) {
				MachineMasterDTO machineMasterDTO = new MachineMasterDTO();
				machineMasterDTO.setId(String.valueOf(resultSet.getString(1)));
				machineMasterDTO.setMake(resultSet.getString(2));
				machineMasterDTO.setMachineNumber(resultSet.getString(3));
				machineMasterDTO.setUserId(resultSet.getString(4));
				machineMasterDTO.setDescription(resultSet.getString(5));
				machineMasterDTO.setRange(resultSet.getString(6));
				machineMasterDTO.setCommisionDate(String.valueOf(resultSet
						.getDate(7)));
				modelAndView.addObject("machineMasterDTO", machineMasterDTO);
				modelAndView.setViewName("editMachineMaster");
				break;
			}

		case "batchmaster":
			while (resultSet.next()) {

				BatchDTO batchDTO = new BatchDTO();
				batchDTO.setBatchNumber(resultSet.getString(2));
				batchDTO.setBatchDate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(3))));
				batchDTO.setArticleId(resultSet.getString(4));
				batchDTO.setMachineNumber(resultSet.getString(5));
				//batchDTO.setClockRate(String.valueOf(resultSet.getInt(6)));
				//batchDTO.setMaxSpeed(String.valueOf(resultSet.getInt(7)));
				batchDTO.setTubeId(resultSet.getString(8));
				batchDTO.setStartTime(resultSet.getString(9));
				batchDTO.setBatchQuantity(String.valueOf(resultSet.getInt(10)));
				batchDTO.setType(resultSet.getString(11));
				batchDTO.setUserId(resultSet.getString(12));
				modelAndView.addObject("batchDTO", batchDTO);
				modelAndView.setViewName("editBatchMaster");
				break;
			}

		case "maintenancedata":
			while (resultSet.next())

			{

				MaintainanceDTO maintainanceDTO = new MaintainanceDTO();
				maintainanceDTO.setId(String.valueOf(resultSet.getString(1)));
				maintainanceDTO.setBatchNumber(resultSet.getString(2));
				maintainanceDTO.setMaintenanceDate(String.valueOf(resultSet
						.getDate(3)));
				maintainanceDTO.setShift(resultSet.getString(4));
				maintainanceDTO.setMachineNumber(resultSet.getString(5));
				maintainanceDTO
						.setTimeLost(String.valueOf(resultSet.getInt(6)));
				maintainanceDTO.setMaintenanceReason(resultSet.getString(7));
				maintainanceDTO.setDetail(resultSet.getString(8));
				maintainanceDTO.setMaintenanceEngineer(resultSet.getString(9));
				maintainanceDTO.setUserId(resultSet.getString(10));
				modelAndView.addObject("maintainanceDTO", maintainanceDTO);
				modelAndView.setViewName("editMaintainanceMaster");
				break;
			}

		case "packdata":
			while (resultSet.next()) {

				{

					PackingDTO packingDTO = new PackingDTO();

					packingDTO.setId(String.valueOf(resultSet.getInt(1)));
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
					modelAndView.addObject("packDTO", packingDTO);
					modelAndView.setViewName("editPackingMaster");
					break;
				}
			}

		case "productiondata":
			while (resultSet.next()) {

				{

					ProductionDTO productionDTO = new ProductionDTO();
					productionDTO.setId(String.valueOf(resultSet.getInt(1)));
					productionDTO.setProductionDate(CommonRef.dbToUiDate(String.valueOf(resultSet.getDate(2))));
					productionDTO.setShift(resultSet.getString(3));
					productionDTO.setBatchNumber(resultSet.getString(4));
					productionDTO.setNovisProd(String.valueOf(resultSet
							.getInt(5)));
					productionDTO.setNovisGood(String.valueOf(resultSet
							.getInt(6)));
					productionDTO.setNovisRotRej(String.valueOf(resultSet
							.getInt(7)));
					productionDTO.setNovisLienRej(String.valueOf(resultSet
							.getInt(8)));
					productionDTO.setTubesUsed(String.valueOf(resultSet
							.getInt(9)));
					productionDTO.setTimeLost(String.valueOf(resultSet
							.getInt(10)));
					productionDTO.setClockRate(resultSet.getString(11));
					productionDTO.setProductionMaster(resultSet.getString(12));
					productionDTO.setGlassWaste(String.valueOf(resultSet
							.getInt(13)));
					//productionDTO.setActivityTime(String.valueOf(resultSet.getInt(14)));
					productionDTO.setEngineerPrd(resultSet.getString(15));
					productionDTO.setUserId(resultSet.getString(16));
					modelAndView.addObject("productionDTO", productionDTO);
					modelAndView.setViewName("editProductionMaster");
					break;
				}

			}
		case "tubemaster":
			while (resultSet.next()) {

				{

					TubeMasterDTO tubeMasterDTO = new TubeMasterDTO();
					tubeMasterDTO.setId(String.valueOf(resultSet.getInt(1)));
					tubeMasterDTO.setTubeId(resultSet.getString(2));
					tubeMasterDTO.setFocusCode(resultSet.getString(3));
					tubeMasterDTO.setSpecification(resultSet.getString(4));
					tubeMasterDTO.setBundleWeight(resultSet.getString(5));
					tubeMasterDTO.setTubesInBundle(resultSet.getString(6));
					tubeMasterDTO.setMake(resultSet.getString(7));
					tubeMasterDTO.setTubeWeight(resultSet.getString(8));
					tubeMasterDTO.setUserId(resultSet.getString(9));
					modelAndView.addObject("tubeMasterDTO", tubeMasterDTO);
					modelAndView.setViewName("editTubeMaster");
					break;
				}

			}

		default:

		}

		return modelAndView;
	}
	
	@RequestMapping("/enable.htm")
	public ModelAndView enable(@RequestParam("id2") String id,HttpSession session)
			throws Exception {

		StringTokenizer stringTokenizer = new StringTokenizer(id, "-");
		List<String> str = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {

			str.add(stringTokenizer.nextToken());

		}

		String primaryKey = str.get(1);

		EmployeeDAO employeeDAO = new EmployeeDAO();
		String displayMessage= employeeDAO.enableUser(primaryKey);
		List<EmployeeDTO> employeeList=new ArrayList<EmployeeDTO>();
		employeeList = EmployeeDAO.viewEmployees();
		logger.info("Employee:" + session.getAttribute("UserName")
				+ " accessing list of employees.");
		ModelAndView model1 = new ModelAndView("ViewEmployees");
		model1.addObject("employeeList", employeeList);
		model1.addObject("displayMessage", displayMessage);
		

		return model1;
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleApplication(Exception e) {

		ModelAndView modelAndView = new ModelAndView("Failure");
		String displayMessage = "Error occured while performing this operation.";
		modelAndView.addObject("displayMessage", displayMessage);
		logger.info("Incident occured at:-"+BaseDAO.getCurrentTime());
		logger.info("LogId:-"+BaseDAO.getLogId());
		logger.info("Unknown exception :-"
				+ e.getMessage());
		return modelAndView;
	}
}
