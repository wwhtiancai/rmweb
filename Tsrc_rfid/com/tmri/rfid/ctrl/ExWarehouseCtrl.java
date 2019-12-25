package com.tmri.rfid.ctrl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.ExWarehouseDetail;
import com.tmri.rfid.bean.ExWarehouseEntry;
import com.tmri.rfid.bean.ExcelModel;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.common.ExWarehouseStatus;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.PackageUnit;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.ExWarehouseService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.util.ExcelOperator;
import com.tmri.rfid.util.FileUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.JsonView;
import com.tmri.share.ora.bean.DbResult;

/**
 * Created by Stone on 2015/9/28.
 */
@Controller
@RequestMapping("/exwarehouse.frm")
public class ExWarehouseCtrl extends BaseCtrl {
	
	private final static Logger LOG = LoggerFactory.getLogger(InventoryCtrl.class);

	@Resource
    private ExWarehouseService exWarehouseService;
	@Resource
    private InventoryService inventoryService;
	@Resource
    private ProductCategoryService productCategoryService;
	@Resource
    private EriService eriService;
	@Resource
	private RuntimeProperty runtimeProperty;
	
	@RequestMapping(params = "method=query-exwarehouse")
    public ModelAndView queryWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "type", required = true) int type,
    		@RequestParam(value = "ckdh", required = false) String ckdh,
            @RequestParam(value = "glbm", required = false) String glbm,
            @RequestParam(value = "jbr", required = false) String jbr,
            @RequestParam(value = "ckrqks", required = false) String ckrqks,
            @RequestParam(value = "ckrqjs", required = false) String ckrqjs,
            @RequestParam(value = "zt", required = false) String zt,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize){
		PageList<ExWarehouseEntry> queryList = null;
		
		try {
			request.setAttribute("type", type);//1-出库单申请  2-出库审核
        	
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");

			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);
			
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", type);
            if (StringUtils.isNotEmpty(ckdh)) {
                condition.put("ckdh", ckdh);
            }
            if (StringUtils.isNotEmpty(glbm)) {
                condition.put("glbm", glbm);
            }
            if (StringUtils.isNotEmpty(jbr)) {
                condition.put("jbr", jbr);
            }
            if (StringUtils.isNotEmpty(ckrqks)) {
                condition.put("ckrqks", ckrqks);
            }
            if (StringUtils.isNotEmpty(ckrqjs)) {
                condition.put("ckrqjs", ckrqjs);
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", zt);
            }
			
			queryList = exWarehouseService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			/*for(int i=0 ; i < queryList.size() ; i++){
				ExWarehouseEntry exWarehouse = queryList.get(i);
				String bmmc = gDepartmentService.getDepartmentBmmc(exWarehouse.getSsbm());
				exWarehouse.setSsbm(bmmc);
			}*/
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			request.setAttribute("exWarehouseStatus", ExWarehouseStatus.values());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query inventory_out result fail ", e);
			e.printStackTrace();
		}
		
		return redirectMav("inventory/exWarehouseMain");
	}
	
	/**
	 * 根据条件获取总计
	 * @param request
	 * @param response
	 * @param ckdh
	 * @param glbm
	 * @param jbr
	 * @param ckrqks
	 * @param ckrqjs
	 * @param zt
	 * @return
	 */
	@RequestMapping(params = "method=getSum")
	@ResponseBody
    public String getSum(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "ckdh", required = false) String ckdh,
            @RequestParam(value = "glbm", required = false) String glbm,
            @RequestParam(value = "jbr", required = false) String jbr,
            @RequestParam(value = "ckrqks", required = false) String ckrqks,
            @RequestParam(value = "ckrqjs", required = false) String ckrqjs,
            @RequestParam(value = "zt", required = false) String zt){
		String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(ckdh)) {
                condition.put("ckdh", ckdh);
            }
            if (StringUtils.isNotEmpty(glbm)) {
                condition.put("glbm", glbm);
            }
            if (StringUtils.isNotEmpty(jbr)) {
                condition.put("jbr", jbr);
            }
            if (StringUtils.isNotEmpty(ckrqks)) {
                condition.put("ckrqks", ckrqks);
            }
            if (StringUtils.isNotEmpty(ckrqjs)) {
                condition.put("ckrqjs", ckrqjs);
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", zt);
            }
            int count = exWarehouseService.queryCount(condition);
            result = GsonHelper.getBaseResultGson("00", count+"");
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=saveCount exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
	}
	
	@RequestMapping(params = "method=edit-exwarehouse")
    public ModelAndView editExWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "type", required = true) int type,
    		@RequestParam(value = "ckdh", required = false) String ckdh){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
	    	
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}
			
			request.setAttribute("type",type);
			
			request.setAttribute("unitsString",PackageUnit.toJSONString());
			//request.setAttribute("statusString",InventoryStatus.toJSONString());
			
			request.setAttribute("productCategories", productCategoryService.fetchAll());
			String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);
			request.setAttribute("unitsString",PackageUnit.toJSONString());
			
			if (ckdh != null && ckdh.length() > 0) {
				ExWarehouseEntry bean = this.exWarehouseService.fetchByCkdh(ckdh);
				String bmmc = gDepartmentService.getDepartmentBmmc(bean.getSsbm());
				bean.setSsbm(bmmc);
				request.setAttribute("bean", bean);
				
				List<ExWarehouseDetail> list = this.exWarehouseService.queryDetailListByCkdm(ckdh);
				request.setAttribute("list", list);
				request.setAttribute("units",PackageUnit.values());
				request.setAttribute("inventoryStatus",InventoryStatus.values());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("inventory/exWarehouseEdit");
	}
	
	@RequestMapping(params = "method=save-exwarehouse")
    @ResponseBody
    public String saveExWarehouse(HttpServletRequest request,HttpServletResponse response,
    		//@RequestParam(value = "ckdh", required = false) String ckdh,
    		@RequestParam(value = "sqdh", required = false) String sqdh,
    		@RequestParam(value = "ssbm", required = true) String ssbm,
    		@RequestParam(value = "cplb", required = false) String cplb,
    		@RequestParam(value = "cpdm", required = false) String cpdm,
    		@RequestParam(value = "cksl", required = false) String cksl,
    		@RequestParam(value = "bz", required = false) String bz,
    		@RequestParam(value = "detailList", required = false) String detailList){
		String result = "";
		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			
			ExWarehouseEntry exWarehouse = new ExWarehouseEntry();
			
			String ckdh = exWarehouseService.getCkdh(ssbm);
			exWarehouse.setCkdh(ckdh);
			exWarehouse.setSqdh(sqdh);
			exWarehouse.setSsbm(ssbm);
			exWarehouse.setCplb(cplb);
			exWarehouse.setCpdm(cpdm);
			exWarehouse.setCksl(Integer.parseInt(cksl));
			exWarehouse.setBz(bz);
			exWarehouse.setJbr(userSession.getYhdh());
			exWarehouse.setCkrq(new Date());
			result = exWarehouseService.saveExWarehouse(exWarehouse,detailList);//保存出库单
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory_out result fail ", e);
			e.printStackTrace();
			result = GsonHelper.getGson().toJson(MapUtilities.buildMap(
		                    "resultId", "01", "resultMsg", e.getMessage()));
		}
		
		return result;
	}
	
	
	@RequestMapping(params = "method=examine")
    public ModelAndView examine(HttpServletRequest request,HttpServletResponse response,
								@RequestParam(value = "ckdh", required = false) String ckdh,
								@RequestParam(value = "zt", required = false) int zt){
		
		ModelAndView view = new ModelAndView(JsonView.instance);
		try {
			UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			
			exWarehouseService.examine(ckdh,zt);//根据参数修改出库状态
			if(zt == 2){
				inventoryService.inventoryOutByCkdh(ckdh);//库存出库
			}
			
			view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "审核通过"));
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			LOG.error("save inventory_out result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		return view;
	}
	
	
	
	@RequestMapping(params = "method=del-exwarehouse")
    public ModelAndView delExWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "ckdh", required = true) String ckdh){
		
		ModelAndView view;
		try {
			int code = exWarehouseService.delExWarehouse(ckdh);
			
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
	
	
	@RequestMapping(params = "method=edit-exwarehouse-detail")
    public ModelAndView editExWarehouseDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "ckdh", required = false) String ckdh,
    		@RequestParam(value = "dw", required = false) int dw,
    		@RequestParam(value = "detailId", required = false) String detailId){
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			//Department dept = userSession.getDepartment();
			//String selfbmjb = dept.getBmjb();
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			request.setAttribute("ckdh", ckdh);
			if (detailId != null && detailId.length() > 0) {
				ExWarehouseDetail bean = this.exWarehouseService.fetchByDetailId(dw,detailId,ckdh);
				request.setAttribute("bean", bean);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("inventory/exWarehouseDetailEdit");
	}
	
	@RequestMapping(params = "method=save-exwarehouse-detail")
    public ModelAndView saveWarehouseDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = true) String bzhh,
    		@RequestParam(value = "bzxh", required = false) String bzxh,
    		@RequestParam(value = "ckdh", required = false) String ckdh){
		
		ModelAndView view;
		try {
			ExWarehouseDetail exWarehouseDetail = new ExWarehouseDetail();
			exWarehouseDetail.setBzhh(bzhh);
			exWarehouseDetail.setBzxh(bzxh);
			exWarehouseDetail.setCkdh(ckdh);
			int code = exWarehouseService.saveExWarehouseDetail(exWarehouseDetail);
			
			DbResult result = new DbResult();
			result.setCode(code);
			String msg = null;
			if(code == 1){
				msg = "保存成功";
			}else{
				msg = "此盒号已存在";
			}
			result.setMsg(msg);
			LOG.debug(msg);
			view = CommonResponse.JSON(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory_out_detail result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		
		return view;
	}
	
	@RequestMapping(params = "method=del-exwarehouse-detail")
    public ModelAndView delExWarehouseDetail(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "bzhh", required = true) String bzhh){
		
		ModelAndView view;
		try {
			int code = exWarehouseService.delDetailList(bzhh);
			
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
	
	@RequestMapping(params = "method=export", method = RequestMethod.GET)
    public void exportExWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "ckdh", required = true) String ckdh){
		try {
			String myPath = runtimeProperty.getFileFolder();
	        String folderPath = myPath+"download/";
	        FileUtil.createFolder(folderPath);
	        
	        ArrayList data = new ArrayList();
			ArrayList header = new ArrayList();
			header.add("包装盒号");
			header.add("起始卡号");
			header.add("终止卡号");
			header.add("所属部门");
			header.add("产品代码");
			header.add("状态");
			header.add("包装箱号");
			data.add(header);
			List<Inventory> inventorys = inventoryService.queryByCkdh(ckdh);
			for (int i = 0; i < inventorys.size(); i++) {
				Inventory inventory = inventorys.get(i);
				ArrayList data1 = new ArrayList();
				
				data1.add(inventory.getBzhh());
				data1.add(inventory.getQskh());
				data1.add(inventory.getZzkh());
				data1.add(inventory.getSsbm());
				data1.add(inventory.getCpdm());
				data1.add(inventory.getZt());
				data1.add(inventory.getBzxh());
				data.add(data1);
			}
			HashMap mapData = new HashMap();
			mapData.put("inventorys", data);
			
			ArrayList dataEri = new ArrayList();
			ArrayList header2 = new ArrayList();
			header2.add("TID");
			header2.add("卡号");
			header2.add("包装盒号");
			header2.add("所属省份");
			header2.add("状态");
			header2.add("批号");
			header2.add("初始化日期");
			header2.add("上次个性化日期");
			header2.add("管理部门");
			header2.add("卡类型");
			header2.add("备注");
			header2.add("车辆信息备份序号");
			dataEri.add(header2);
			List<Eri> eris = eriService.queryByCkdh(ckdh);
			for (int j = 0; j < eris.size(); j++) {
				Eri eri = eris.get(j);
				ArrayList data2 = new ArrayList();
				
				data2.add(eri.getTid());
				data2.add(eri.getKh());
				data2.add(eri.getBzhh());
				data2.add(eri.getSf());
				data2.add(eri.getZt());
				data2.add(eri.getPh());
				data2.add(DateUtil.formatDate(eri.getCshrq()));
				data2.add(DateUtil.formatDate(eri.getScgxhrq()));
				data2.add(eri.getGlbm());
				data2.add(eri.getKlx());
				data2.add(eri.getBz());
				data2.add(eri.getClxxbfid());
				dataEri.add(data2);
			}

			mapData.put("eris", dataEri);
			
			ExcelModel model = new ExcelModel();
			String path = folderPath + ckdh + ".xls";
			model.setPath(path);
			model.setDataMap(mapData);
			ExcelOperator eo = new ExcelOperator();
			eo.WriteExcel(model);
			
			response.setContentType("application/vnd.ms-excel");
			
			//最好加上下载文件的扩展名。
			response.setHeader("Content-disposition","attachment; filename="+ckdh+".xls");
	  
			BufferedInputStream buffInput=new BufferedInputStream(new FileInputStream(path));
			BufferedOutputStream buffout=new BufferedOutputStream(response.getOutputStream());
			int length=-1;
			byte[] buff=new byte[1024];
			while((length=buffInput.read(buff))!=-1)
			{
				buffout.write(buff, 0, length);
			}
			buffout.flush();
			buffInput.close();
	      	buffout.close();
			File file = new File(path);
			file.delete();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("save inventory_out result fail ", e);
			e.printStackTrace();
		}
		
	}
	
	protected final String jspPath = "jsp_core/rfid/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
