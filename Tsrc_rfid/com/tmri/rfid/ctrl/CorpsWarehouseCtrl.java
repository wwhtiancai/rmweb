package com.tmri.rfid.ctrl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.property.RuntimeProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.CorpsWarehouse;
import com.tmri.rfid.bean.CorpsWarehouseDetail;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.bean.WarehouseEntry;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.PackageUnit;
import com.tmri.rfid.service.CorpsWarehouseService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.service.WarehouseService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.ReadExcel;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;

/**
 * 
 * @author stone
 * @date 2016-3-4 上午11:07:05
 */
@Controller
@RequestMapping("/corps-warehouse.frm")
public class CorpsWarehouseCtrl extends BaseCtrl {
	
	private final static Logger LOG = LoggerFactory.getLogger(InventoryCtrl.class);

	@Resource
	private WarehouseService warehouseService;
    //private CorpsWarehouseService warehouseService;
	@Resource
    private InventoryService inventoryService;
	@Resource
    private ProductCategoryService productCategoryService;

	@Resource
	private RuntimeProperty runtimeProperty;
	
	@RequestMapping(params = "method=query")
    public ModelAndView queryList(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = false) String rkdh,
            @RequestParam(value = "glbm", required = false) String glbm,
            @RequestParam(value = "jbr", required = false) String jbr,
            @RequestParam(value = "rkrqks", required = false) String rkrqks,
            @RequestParam(value = "rkrqjs", required = false) String rkrqjs,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){

        LOG.info("------> display corps-warehouse.frm?method=query-warehouse");
		try {
			UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);
			SysUser user = UserState.getUser();
            
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(rkdh)) {
                condition.put("rkdh", rkdh);
            }
            if (StringUtils.isNotEmpty(glbm)) {
                condition.put("glbm", glbm);
            }else{
            	condition.put("glbm", user.getGlbm());
            }
            if (StringUtils.isNotEmpty(jbr)) {
                condition.put("jbr", jbr);
            }
            if (StringUtils.isNotEmpty(rkrqks)) {
                condition.put("rkrqks", rkrqks);
            }
            if (StringUtils.isNotEmpty(rkrqjs)) {
                condition.put("rkrqjs", rkrqjs);
            }
			
			//this.setPageInfo(request);

			PageList<WarehouseEntry> queryList = warehouseService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query inventory result fail ", e);
			e.printStackTrace();
		}
		
		return redirectMav("warehouseMain");
	}
	
	@RequestMapping(params = "method=edit-warehouse")
    public ModelAndView editWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = false) String rkdh){
        LOG.info("------> display corps-warehouse.frm?method=edit-warehouse");
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);

			request.setAttribute("unitsString",PackageUnit.toJSONString());
			//request.setAttribute("statusString",InventoryStatus.toJSONString());
			
			request.setAttribute("productCategories", productCategoryService.fetchAll());
			if (rkdh != null && rkdh.length() > 0) {
				WarehouseEntry bean = this.warehouseService.fetchByRkdh(rkdh);
				String bmmc = gDepartmentService.getDepartmentBmmc(bean.getSsbm());
				bean.setSsbm(bmmc);
				request.setAttribute("bean", bean);

				List<WarehouseDetail> list = this.warehouseService.queryDetailListByRkdh(rkdh);
				request.setAttribute("list", list);
				request.setAttribute("units",PackageUnit.values());
				request.setAttribute("inventoryStatus",InventoryStatus.values());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("warehouseEdit");
	}
	
	@RequestMapping(params = "method=save-warehouse")
    @ResponseBody
    public String saveWarehouse(HttpServletRequest request,HttpServletResponse response,
    		//@RequestParam(value = "rkdh", required = true) String rkdh,
    		@RequestParam(value = "ssbm", required = false) String ssbm,
    		@RequestParam(value = "cplb", required = false) String cplb,
    		@RequestParam(value = "cpdm", required = false) String cpdm,
    		@RequestParam(value = "bz", required = false) String bz,
    		@RequestParam(value = "detailList", required = false) String detailList){
        LOG.info("------> display corps-warehouse.frm?method=save-warehouse");
		String result = "";
		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			
			WarehouseEntry warehouse = new WarehouseEntry();
			
			String rkdh = warehouseService.getRkdh(ssbm);
			warehouse.setRkdh(rkdh);
			warehouse.setSsbm(ssbm);
			warehouse.setCplb(cplb);
			warehouse.setCpdm(cpdm);
			warehouse.setBz(bz);
			warehouse.setJbr(userSession.getYhdh());
			warehouse.setRkrq(new Date());
			result = warehouseService.saveCorpsWarehouse(warehouse,detailList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory result fail ", e);
			e.printStackTrace();
			return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
		}
		
		return result;
	}
	
	@RequestMapping(params = "method=import-warehouse")
    public ModelAndView importWarehouse(HttpServletRequest request,HttpServletResponse response){
        LOG.info("------> display corps-warehouse.frm?method=import-warehouse");
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);
			
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("warehouseImport");
	}
	
	@RequestMapping(params = "method=upload-warehouse")
    public ModelAndView uploadWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bz", required = false) String bz){
        LOG.info("------> display corps-warehouse.frm?method=upload-warehouse");
		String result = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 得到上传的文件
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            // 得到上传服务器的路径
            String myPath = runtimeProperty.getFileFolder() + "/upload/";
			File folder = new File(myPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
            // 得到上传的文件的文件名
            String filename = mFile.getOriginalFilename();
            
            File f = new File(myPath+filename);
    		mFile.transferTo(f);
    		
    		if(mFile.getSize() == 0){
    			CommonResponse.setAlerts("您上传的文件为空", request);
    		}
    		warehouseService.importWarehouse(f,bz);

			CommonResponse.pageRedirect("success", "", response);
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}

        return redirectMav("warehouseImport");
	}
	
	
	@RequestMapping(params = "method=del-warehouse")
    @ResponseBody
    public String delWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = true) String rkdh){
		
		String result;
		try {
			int code = warehouseService.delWarehouse(rkdh);
			
			String msg = "删除成功";
			LOG.debug(msg);
			result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "resultMsg", msg));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("del inventory_out_detail result fail ", e);
			e.printStackTrace();
			result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "01", "resultMsg", e));
		}
		
		return result;
	}
	
	
	protected final String jspPath = "jsp_core/rfid/inventory/corpsWarehouse/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
