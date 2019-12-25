package com.tmri.rfid.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Department;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.ora.bean.DbResult;

/**
 * Created by Stone on 2015/9/23.
 */
@Controller
@RequestMapping("/inventory.frm")
public class InventoryCtrl extends BaseCtrl  {
	
	private final static Logger LOG = LoggerFactory.getLogger(InventoryCtrl.class);
	
	@Resource
    private InventoryService inventoryService;
	@Resource
    private EriService eriService;

	@RequestMapping(params = "method=edit-inventory")
    public ModelAndView editInventory(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = false) String bzhh){
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
			
			if (bzhh != null && bzhh.length() > 0) {
				Inventory bean = this.inventoryService.fetchByBzhh(bzhh);
				request.setAttribute("bean", bean);
				
				Map<String, Object> condition = new HashMap<String, Object>();
                condition.put("bzhh", bzhh.trim());
                List<Eri> eris = eriService.fetchByCondition(condition);
				request.setAttribute("eris", eris);
	            request.setAttribute("eriStatus", EriStatus.values());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("inventory/inventoryEdit");
	}
	
	@RequestMapping(params = "method=save-inventory")
    public ModelAndView saveInventory(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = true) String bzhh,
    		@RequestParam(value = "qskh", required = false) String qskh,
    		@RequestParam(value = "zzkh", required = false) String zzkh,
    		@RequestParam(value = "ssbm", required = false) String ssbm){
		
		ModelAndView view;
		try {
			Inventory inventory = new Inventory();
			inventory.setBzhh(bzhh);
			inventory.setQskh(qskh);
			inventory.setZzkh(zzkh);
			inventory.setSsbm(ssbm);
			inventoryService.saveInventory(inventory);
			
			DbResult result = new DbResult();
			result.setCode(1);
			result.setMsg("保存成功");
			view = CommonResponse.JSON(result);
			
			LOG.debug("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
	}
	
	@RequestMapping(params = "method=query-inventory")
    public ModelAndView queryInventory(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = false) String bzhh,
    		@RequestParam(value = "bzxh", required = false) String bzxh,
    		@RequestParam(value = "kh", required = false) String kh,
    		@RequestParam(value = "ssbm", required = false) String ssbm,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){
		
		PageList<Inventory> queryList = null;
		
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
			
			Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(bzhh)) {
                condition.put("bzhh", bzhh.trim());
            }
            if (StringUtils.isNotEmpty(bzxh)) {
                condition.put("bzxh", bzxh.trim());
            }
            if (StringUtils.isNotEmpty(kh)) {
                condition.put("kh", kh.trim());
            }
            if (StringUtils.isNotEmpty(ssbm)) {
                condition.put("ssbm", ssbm.trim());
            }
			
			queryList = inventoryService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query inventory result fail ", e);
			e.printStackTrace();
		}

		return redirectMav("inventory/inventoryMain");
	}

    @RequestMapping(params = "method=check-inventory")
    @ResponseBody
    public String queryStock(@RequestParam(value = "bzh", required = true) String bzh,
                             HttpServletRequest request) {
        LOG.info(String.format("------> call inventory.frm?method=check-inventory(bzh=%s)", bzh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            Department dept = userSession.getDepartment();
            List<Inventory> inventories = inventoryService.checkInventory(bzh, dept.getGlbm());
            if (inventories == null || inventories.isEmpty()) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "不存在");
            } else {
                resultMap.put("resultId", "00");
                resultMap.put("inventories", inventories);
            }
        } catch (Exception e) {
            LOG.error("query stock fail ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> call inventory.frm?method=check-inventory result="+result);
        return result;
    }

    @RequestMapping(params = "method=check-kh")
    @ResponseBody
    public String checkKh(@RequestParam("sf") String sf, @RequestParam("qskh") int qskh,
                          @RequestParam("sl") int sl, HttpServletRequest request) {

        LOG.info(String.format("------> call /inventory.frm?method=check-kh(sf=%s,qskh=%s,sl=%s", sf, qskh, sl));
        try {
            List<String> occupiedKh = inventoryService.findOccupiedKh(sf, qskh, sl);
            String result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "khs", occupiedKh));
            LOG.info("------> call inventory.frm?method=check-kh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("查找已被占用的卡号失败", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }
    
    /**
     * 
     * @param bzxh
     * @param type 标记是入库warehouse  出库exwarehouse  总队入库corps-warehouse
     * @param request
     * @return
     */
    @RequestMapping(params = "method=check-by-bzxh")
    @ResponseBody
    public String checkByBzxh(@RequestParam(value = "bzxh", required = true) String bzxh,
    		@RequestParam(value = "type", required = true) String type
    		, HttpServletRequest request) {

        LOG.info(String.format("------> call /inventory.frm?method=check-by-bzxh(bzxh=%s", bzxh));
        try {
        	Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(bzxh)) {
                condition.put("bzxh", bzxh);
            }
        	
            List<Inventory> inventoryList = inventoryService.fetchByCondition(condition);
            String result;
            int rightZt;
            String ztMsg = "";//状态不对时的提示信息
            if(type.equals("warehouse")){//公安部入库
            	rightZt = InventoryStatus.PRODUCED.getStatus();
            	ztMsg = "该包装箱号未生产完成或已入库";
            }else if(type.equals("exwarehouse")){//公安部出库
            	rightZt = InventoryStatus.STORED.getStatus();
            	ztMsg = "该包装箱号未入库或已出库";
            }else/* if(type.equals("corps-warehouse"))*/{//总队入库
            	rightZt = InventoryStatus.SENDOUT.getStatus();
            	ztMsg = "该包装箱号还未从公安部出库或已入库总队";
            }
            
            WarehouseDetail warehouseDetail = new WarehouseDetail();
            warehouseDetail.setBzxh(bzxh);
            warehouseDetail.setDw(1);

            //warehouseDetail.setQskh(qskh);
            //warehouseDetail.setZzkh(zzkh);
            String cplb, cpdm;
            if(inventoryList.size() == 0){
            	result = GsonHelper.getGson().toJson(
                        MapUtilities.buildMap("resultId", "01", "resultMsg", "根据包装箱号查询的库存不存在"));
            }else {
            	boolean flag = true;
            	cplb = inventoryList.get(0).getCplb();
            	cpdm = inventoryList.get(0).getCpdm();
            	for(int i = 0;i < inventoryList.size() ;i++){
            		Inventory inventory = inventoryList.get(i);
            		int zt = inventory.getZt();
            		if(zt != rightZt){
            			flag = false;
            		}
            		
            		String xQskh = warehouseDetail.getQskh();
            		String xZzkh = warehouseDetail.getZzkh();
            		String hQskh = inventory.getQskh();
            		String hZzkh = inventory.getZzkh();
            		//如果该盒起始卡号比该箱目前的起始卡号小 或者 箱起始卡号为空 ，将盒起始卡号置为箱起始卡号
            		if(xQskh == null || hQskh.compareTo(xQskh) < 0 ){
            			warehouseDetail.setQskh(hQskh);
            		}
            		//如果该盒终止卡号比该箱目前的终止卡号大 或者 箱终止卡号为空 ，将盒终止卡号置为箱终止卡号
            		if(xZzkh == null || hZzkh.compareTo(xZzkh) > 0 ){
            			warehouseDetail.setZzkh(hZzkh);
            		}
            	}
            	if(!flag){
            		result = GsonHelper.getGson().toJson(
                            MapUtilities.buildMap("resultId", "01", "resultMsg", ztMsg));
            	}else{
                	result = GsonHelper.getGson().toJson(
                            MapUtilities.buildMap("resultId", "00", "bean", warehouseDetail, "cplb", cplb, "cpdm", cpdm));
            	}
            }
            
            LOG.info("------> call inventory.frm?method=check-by-bzxh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("根据包装箱号查询的库存不存在或已被占用", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }
    
    @RequestMapping(params = "method=query-by-bzxh")
    @ResponseBody
    public String queryByBzxh(@RequestParam(value = "bzxh", required = true) String bzxh
    		, HttpServletRequest request) {

        LOG.info(String.format("------> call /inventory.frm?method=query-by-bzxh(bzxh=%s", bzxh));
        try {
        	Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(bzxh)) {
                condition.put("bzxh", bzxh);
            }
        	
            List<Inventory> inventoryList = inventoryService.fetchByCondition(condition);
            String result;
            
            result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "inventoryList", inventoryList));
            LOG.info("------> call inventory.frm?method=query-by-bzxh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("根据包装箱号查询的库存不存在或已被占用", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }
    
    /**
     * 
     * @param bzhh 
     * @param checkType 标记是入库1 还出库2
     * @param request
     * @return
     */
    @RequestMapping(params = "method=check-query-by-bzhh")
    @ResponseBody
    public String checkQueryByBzhh(@RequestParam(value = "bzhh", required = true) String bzhh,
    		@RequestParam(value = "checkType", required = true) String checkType
    		, HttpServletRequest request) {

        LOG.info(String.format("------> call /inventory.frm?method=check-query-by-bzhh(bzhh=%s", bzhh));
        try {
        	Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(bzhh)) {
                condition.put("bzhh", bzhh);
            }
        	
            List<Inventory> inventoryList = inventoryService.fetchByCondition(condition);
            String result;
            
            int zt;
            String ztMsg = "";//状态不对时的提示信息
            if(checkType.equals("warehouse")){
            	zt = InventoryStatus.PRODUCED.getStatus();
            	ztMsg = "该包装盒号未生产完成或已入库";
            }else if(checkType.equals("exwarehouse")){
            	zt = InventoryStatus.STORED.getStatus();
            	ztMsg = "该包装盒号未入库或已出库";
            }else/* if(type.equals("corps-warehouse"))*/{//总队入库
            	zt = InventoryStatus.SENDOUT.getStatus();
            	ztMsg = "该包装盒号还未从公安部出库或已入库总队";
            }
            if(inventoryList.size() == 0){
            	result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "01", "resultMsg", "根据包装盒号查询的库存不存在"));
            }else if(inventoryList.get(0).getZt() != zt){
            	result = GsonHelper.getGson().toJson(
                        MapUtilities.buildMap("resultId", "01", "resultMsg", ztMsg));
            }else {
            	result = GsonHelper.getGson().toJson(
                        MapUtilities.buildMap("resultId", "00", "inventoryList", inventoryList));
            }
            LOG.info("------> call inventory.frm?method=query-by-bzhh result = " + result);
            return result;
        } catch (Exception e) {
            LOG.error("根据包装盒号查询的库存不存在或已被占用", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", e.getMessage()));
        }
    }
    
    /**
     * 获取当前库存
     * @param cpdm
     * @param request
     * @return
     */
    @RequestMapping(params = "method=fetchDqkc", method = RequestMethod.POST)
	@ResponseBody
    public String fetchDqkc(@RequestParam(value = "cpdm") String cpdm, HttpServletRequest request) {
        LOG.info(String.format("------> post /inventory.frm?method=fetchDqkc(cpdm=%s)", cpdm));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            String ssbm = userSession.getDepartment().getGlbm();
            int dqkc = inventoryService.fetchDqkc(cpdm,ssbm);
            result = GsonHelper.getBaseResultGson("00", dqkc+"");
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(params = "method=getXhByKh", method = RequestMethod.GET)
	@ResponseBody
    public String getXhByKh(@RequestParam(value = "qskh") String qskh,
    		@RequestParam(value = "zzkh") String zzkh,
    		@RequestParam(value = "cpdm", required = false) String cpdm,
    		@RequestParam(value = "zt", required = false) String zt,
    		HttpServletRequest request) {
        LOG.info(String.format("------> post /inventory.frm?method=getXhByKh(qskh=%s,zzkh=%s,cpdm=%s)", qskh,zzkh,cpdm));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            String ssbm = userSession.getDepartment().getGlbm();
            List<Inventory> inventorys = inventoryService.getXhByKh(qskh,zzkh,cpdm,zt);
        	result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "resultMsg", inventorys));
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    
    
    protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
