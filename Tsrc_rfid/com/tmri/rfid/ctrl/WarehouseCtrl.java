package com.tmri.rfid.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.bean.WarehouseEntry;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.PackageUnit;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.service.WarehouseService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.ora.bean.DbResult;

/**
 * Created by Stone on 2015/9/28.
 */
@Controller
@RequestMapping("/warehouse.frm")
public class WarehouseCtrl extends BaseCtrl {
	
	private final static Logger LOG = LoggerFactory.getLogger(InventoryCtrl.class);

	@Resource
    private WarehouseService warehouseService;
	@Resource
    private InventoryService inventoryService;
	@Resource
    private ProductCategoryService productCategoryService;
	
	@RequestMapping(params = "method=query-warehouse")
    public ModelAndView queryWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = false) String rkdh,
            @RequestParam(value = "glbm", required = false) String glbm,
            @RequestParam(value = "jbr", required = false) String jbr,
            @RequestParam(value = "rkrqks", required = false) String rkrqks,
            @RequestParam(value = "rkrqjs", required = false) String rkrqjs,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){

        LOG.info("------> display warehouse.frm?method=query-warehouse");
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
		
		return redirectMav("inventory/warehouseMain");
	}
	
	@RequestMapping(params = "method=edit-warehouse")
    public ModelAndView editWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = false) String rkdh){
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
		
		return redirectMav("inventory/warehouseEdit");
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
		LOG.info(String.format("------> calling /warehouse.frm?method=save-warehouse ssbm = %s, cplb = %s, cpdm = %s, bz = %s, detailList = %s",
				ssbm, cplb, cpdm, bz, detailList));
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
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
			int code = warehouseService.saveWarehouse(warehouse);
			
			DbResult result = new DbResult();
			String msg = null;
			if(code == 1){
				msg = "保存成功";

				if(detailList != ""){
					JSONArray ja = JSONArray.fromObject(detailList);
					for(int i = 0 ; i < ja.size(); i++){
						JSONObject jo  = (JSONObject) ja.get(i);
						WarehouseDetail whd = new WarehouseDetail();
						whd.setRkdh(rkdh);
						whd.setDw(Integer.parseInt(jo.get("dw").toString()));
						whd.setBzhm(jo.get("bzhm").toString());
						int detailCode = warehouseService.saveWarehouseDetail(whd);
						inventoryService.inventoryIn(whd, userSession.getDepartment().getGlbm());
						if(detailCode == 0){
							code = 0;
							msg = "入库单详情入库不成功";
						}
					}
				}
				
			}else{
				msg = "此入库单号已存在";
			}

			resultMap.put("code", code);
			resultMap.put("msg", msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory result fail ", e);
			resultMap.put("code", "99");
			resultMap.put("msg", e.getMessage());
		}
		String result = GsonHelper.getGson().toJson(resultMap);

		LOG.info("------> calling warehouse.frm?method=save-warehouse result = " + result);
		return result;
	}
	
	@RequestMapping(params = "method=del-warehouse")
    public ModelAndView delWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = true) String rkdh){
		
		ModelAndView view;
		try {
			int code = warehouseService.delWarehouse(rkdh);
			
			DbResult result = new DbResult();
			result.setCode(code);
			String msg = "删除成功";
			result.setMsg(msg);
			LOG.debug(msg);
			view = CommonResponse.JSON(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("del inventory_out_detail result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
	}
	
	
	@RequestMapping(params = "method=edit-warehouse-detail")
    public ModelAndView editWarehouseDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = false) String rkdh,
    		@RequestParam(value = "dw", required = false) int dw,
    		@RequestParam(value = "detailId", required = false) String detailId){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			request.setAttribute("rkdh", rkdh);
			if (detailId != null && detailId.length() > 0) {
				WarehouseDetail whd = new WarehouseDetail();
				whd.setDw(dw);
				whd.setBzhm(detailId);
				whd.setRkdh(rkdh);
				WarehouseDetail bean = this.warehouseService.fetchByDetailId(whd);
				request.setAttribute("bean", bean);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("inventory/warehouseDetailEdit");
	}
	
	@RequestMapping(params = "method=del-warehouse-detail")
    public ModelAndView delWarehouseDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = true) String bzhh){
		
		ModelAndView view;
		try {
			int code = warehouseService.delDetailList(bzhh);
			
			DbResult result = new DbResult();
			result.setCode(code);
			String msg = "删除成功";
			result.setMsg(msg);
			LOG.debug(msg);
			view = CommonResponse.JSON(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("del inventory_in_detail result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
	}
	

    @RequestMapping(params = "method=add-inventory-in-detail")
    @ResponseBody
    public String getHhxx(@RequestParam(value = "rkdh", required = true) String rkdh,
    		@RequestParam(value = "rkdw", required = true) int rkdw,
    		@RequestParam(value = "bzhm", required = true) String bzhm,
                          HttpServletRequest request) {
        LOG.info(String.format("------> call /warehouse.frm?method=add-inventory-in-detail(%s)(%s)", rkdw, bzhm));
        int result = 0;
		try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
			WarehouseDetail whd = new WarehouseDetail();
			whd.setRkdh(rkdh);
			whd.setDw(rkdw);
			whd.setBzhm(bzhm);
			
			result = warehouseService.saveWarehouseDetail(whd);
			
			inventoryService.inventoryIn(whd, userSession.getDepartment().getGlbm());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
            LOG.error("失败", e);

			return "此箱号或盒号已存在";
		}
        
		if(result == 0){
			StringBuffer msg = new StringBuffer();
			msg.append("此");
			PackageUnit[] units = PackageUnit.values();
			for(int i = 0; i< units.length; i++){
				PackageUnit u = units[i];
				if(u.getCode() == rkdw){
					msg.append(u.getName());
				}
			}
			msg.append("号(");
			msg.append(bzhm);
			msg.append(")已存在");
			
			return msg.toString();
		}else{
			return "";
		}
    }
	
	protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
