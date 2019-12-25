package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.MaterialApply;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.MaterialInventory;
import com.tmri.rfid.bean.MaterialWarehouse;
import com.tmri.rfid.bean.PackageCase;
import com.tmri.rfid.bean.WarehouseCount;
import com.tmri.rfid.common.MaterialWarehouseStatus;
import com.tmri.rfid.service.MaterialApplyService;
import com.tmri.rfid.service.MaterialWarehouseService;
import com.tmri.rfid.util.DateUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.JsonView;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by st on 2015/11/2.
 */
@Controller
@RequestMapping("/material-warehouse.rfid")
public class MaterialWarehouseCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(MaterialWarehouseCtrl.class);

    @Resource
    private MaterialWarehouseService materialWarehouseService;
    @Resource
    private MaterialApplyService materialApplyService;
    
    @RequestMapping(params = "method=list")
    public ModelAndView queryMaterialsWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "type", required = true) String type,
    		@RequestParam(value = "rkdh", required = false) String rkdh,
            @RequestParam(value = "jbrxm", required = false) String jbrxm,
            @RequestParam(value = "rkrqks", required = false) String rkrqks,
            @RequestParam(value = "rkrqjs", required = false) String rkrqjs,
            @RequestParam(value = "jfdw", required = false) String jfdw,
            @RequestParam(value = "zt", required = false) String zt,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
            Model model){

        LOG.info("------> display material-warehouse.rfid?method=query-materials-warehouse");
		try {
			model.addAttribute("type",type);//1-原料入库 2-入库删除
        	
			UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
			/*String userDept = this.gDepartmentService.getZdGlbm(userSession
					.getDepartment());
			List<Department> xjbmList = this.gDepartmentService
					.getXjDepartments(userDept, true);
			request.setAttribute("xjbmList", xjbmList);*/
            
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("type", type);
            if (StringUtils.isNotEmpty(rkdh)) {
                condition.put("rkdh", rkdh.trim());
            }
            if (StringUtils.isNotEmpty(jbrxm)) {
                condition.put("jbrxm", jbrxm);
            }
            if (StringUtils.isNotEmpty(rkrqks)) {
                condition.put("rkrqks", rkrqks);
            }
            if (StringUtils.isNotEmpty(rkrqjs)) {
                condition.put("rkrqjs", rkrqjs);
            }
            if (StringUtils.isNotEmpty(jfdw)) {
                condition.put("jfdw", jfdw);
            }
            if (StringUtils.isNotEmpty(zt)) {
                condition.put("zt", zt);
            }
			
			PageList<MaterialWarehouse> queryList = materialWarehouseService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));

            model.addAttribute("materialWarehouseStatus", MaterialWarehouseStatus.values());
            model.addAttribute("condition", condition);
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query inventory result fail ", e);
			e.printStackTrace();
		}
		
		return redirectMav("main");
	}
    
    @RequestMapping(params = "method=create")
    public ModelAndView create(Model model, HttpServletRequest request) {
        LOG.info("------> display /material-warehouse.rfid?method=create");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("materialWarehouseStatus", MaterialWarehouseStatus.values());
        } catch (Exception e) {
            LOG.error("display create material warehouse page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("create");
    }
    
	@RequestMapping(params = "method=save", method = RequestMethod.POST)
	@ResponseBody
    public String save(MaterialWarehouse materialWarehouse,
    		@RequestParam(value = "materialEris") String materialEris, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-warehouse.rfid?method=save(bean=%s,materialEris=%s)",
                ToStringBuilder.reflectionToString(materialWarehouse),materialEris));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            
            String dgdh = materialWarehouse.getDgdh();
            //先验证该订单号是否存在
            String flag = materialApplyService.RKCheck(dgdh);
            if(flag != null){
            	result = GsonHelper.getBaseResultGson("99", flag);
            }else{
            	
            	SysUser sysUser = userSession.getSysuser();
                String rkdh = materialWarehouseService.generateOrderNumber();
                
                materialWarehouse.setRkdh(rkdh);
                materialWarehouse.setJbr(sysUser.getYhdh());
                materialWarehouse.setZt(MaterialWarehouseStatus.COMPLETE.getStatus());
                
                JSONArray jsonArray = JSONArray.fromObject(materialEris);   
                List<MaterialEri> list = JSONArray.toList(jsonArray, MaterialEri.class);   
                List<MaterialInventory> materialInventorys = materialWarehouseService.getMaterialInventorys(list,rkdh);

                //入库片数是否小于等于该订单号还未入库的片数
                String f = materialApplyService.RKCheck(dgdh,list);
                if(f!= null){
                	result = GsonHelper.getBaseResultGson("99", f);
                }else{
                    materialWarehouseService.create(materialWarehouse);
                    materialWarehouseService.insertBatchEri(list);
                    materialWarehouseService.insertBatchInventory(materialInventorys);
                    materialApplyService.updateYrksl(dgdh);
                    
                    result = GsonHelper.getBaseResultGson("00", "创建成功");
                }
            }
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(params = "method=view")
    public ModelAndView view(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = true) String rkdh){
    	LOG.info("------> display /material-warehouse.rfid?method=view");
    	
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

			if (rkdh != null && rkdh.length() > 0) {
				MaterialWarehouse materialWarehouse = this.materialWarehouseService.fetchByRKDH(rkdh);
				
				List<PackageCase> packageCases = this.materialWarehouseService.queryWarehouseDetails(rkdh);
				
				Collections.sort(packageCases, new Comparator<PackageCase>() {
	                public int compare(PackageCase pc1, PackageCase pc2) {
	                    return pc1.getBzxh().compareTo(pc2.getBzxh());
	                }
	            });
				
				request.setAttribute("bean", materialWarehouse);
				request.setAttribute("materialWarehouseStatus", MaterialWarehouseStatus.values());
				request.setAttribute("packageCases", packageCases);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("view");
	}
    
    @RequestMapping(params = "method=edit")
    public ModelAndView editMaterialsWarehouse(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "rkdh", required = true) String rkdh){
    	LOG.info("------> display /material-warehouse.rfid?method=edit");
    	
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

			if (rkdh != null && rkdh.length() > 0) {
				MaterialWarehouse materialWarehouse = this.materialWarehouseService.fetchByRKDH(rkdh);
				request.setAttribute("bean", materialWarehouse);
				request.setAttribute("materialWarehouseStatus", MaterialWarehouseStatus.values());
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("edit");
	}
    
    @RequestMapping(params = "method=edit", method = RequestMethod.POST)
    public ModelAndView edit(MaterialWarehouse materialWarehouse, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-warehouse.rfid?method=edit(bean=%s)",
                ToStringBuilder.reflectionToString(materialWarehouse)));
        ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            materialWarehouseService.update(materialWarehouse);
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "更新成功"));
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=save exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("99", e.getMessage()));
        }
        return view;
    }
    
    @RequestMapping(params = "method=upload", method = RequestMethod.POST)
    public ModelAndView upload(Model model, HttpServletRequest request,HttpServletResponse response,
    		MaterialWarehouse materialWarehouse
    		/*@RequestParam(value = "rkdh", required = true) String rkdh*/) {
        LOG.info("------> display /material-warehouse.rfid?method=upload");
        //ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 得到上传的文件
            MultipartFile mFile = multipartRequest.getFile("file_upload");
            // 得到上传服务器的路径
            //String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
            String myPath = request.getSession().getServletContext().getRealPath("/");
            //System.getProperty(key)
            // 得到上传的文件的文件名
            String filename = mFile.getOriginalFilename();
            String suffix = filename.substring(filename.indexOf("."));
            
            File f = new File(myPath+"transferTo.xls");
    		mFile.transferTo(f);
    		
    		if(mFile.getSize() == 0){
    			CommonResponse.setAlerts("没有该订购单号", request);
    			return redirectMav("create");
    		}
    		
    		Map<String,Object> map = materialWarehouseService.getMaterialEris(f);
    		List<MaterialEri> materialEris = (List<MaterialEri>) map.get("materialEris");
    		
    		//System.out.println(JSONArray.fromObject(materialEris).toString());
            model.addAttribute("materialEris", JSONArray.fromObject(materialEris).toString());
        	
            String dgdh = (String) map.get("dgdh");
            String jfdw = (String) map.get("jfdw");
            
            if(dgdh != null)materialWarehouse.setDgdh(dgdh);
            if(jfdw != null)materialWarehouse.setJfdw(jfdw);
    		model.addAttribute("user", userSession.getSysuser());
    		model.addAttribute("bean", materialWarehouse);
    		
    		MaterialApply materialApply = materialApplyService.fetchByDGDH(dgdh);
    		model.addAttribute("materialApply", materialApply);
    		if(materialApply == null){
    			CommonResponse.setAlerts("", request);
    			return redirectMav("create");
    		}else{
    			String flag = materialApplyService.RKCheck(dgdh,materialEris);//校验入库数量与订购数量
    			if(flag != null){
    				model.addAttribute("errMsg", flag);
    				return redirectMav("create");
    			}else{
    				List<PackageCase> packageCases = materialWarehouseService.getPackageCases(materialEris);
    	            Collections.sort(packageCases, new Comparator<PackageCase>() {
    	                public int compare(PackageCase pc1, PackageCase pc2) {
    	                    return pc1.getBzxh().compareTo(pc2.getBzxh());
    	                }
    	            });
    	            model.addAttribute("packageCases", packageCases);
    			}
    		}
            
        } catch (Exception e) {
            LOG.error("display upload material warehouse page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("create2");
    }
    
    
    @RequestMapping(params = "method=delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "rkdh") String rkdh, HttpServletRequest request) {
        LOG.info(String.format("------> post /material-warehouse.rfid?method=delete(rkdh=%s)", rkdh));
        ModelAndView view = new ModelAndView(JsonView.instance);
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            materialWarehouseService.delete(rkdh);
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("00", "取消成功"));
        } catch (Exception e) {
            LOG.error("------> post /material-apply.rfid?method=delete exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            view.addObject(JsonView.JSON_ROOT, GsonHelper.getBaseResultGson("99", e.getMessage()));
        }
        return view;
    }
    
    protected final String jspPath = "jsp_core/rfid/materialWarehouse/";
    public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}

    /**
     * 原料入库，只计数
     * @param request
     * @param response
     * @param page
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(params = "method=countlist")
    public ModelAndView queryWarehouseCount(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "xh", required = false) String xh,
            @RequestParam(value = "czrxm", required = false) String czrxm,
            @RequestParam(value = "rkrqks", required = false) String rkrqks,
            @RequestParam(value = "rkrqjs", required = false) String rkrqjs,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
            Model model){

        LOG.info("------> display material-warehouse.rfid?method=countlist");
		try {
        	
			UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(xh)) {
                condition.put("xh", xh);
            }
            if (StringUtils.isNotEmpty(czrxm)) {
                condition.put("czrxm", czrxm);
            }
            if (StringUtils.isNotEmpty(rkrqks)) {
                condition.put("rkrqks", rkrqks);
            }
            if (StringUtils.isNotEmpty(rkrqjs)) {
                condition.put("rkrqjs", rkrqjs);
            }
			
			PageList<WarehouseCount> queryList = materialWarehouseService.queryCountList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));

            model.addAttribute("condition", condition);
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("query inventory result fail ", e);
			e.printStackTrace();
		}
		
		return redirectMav("countMain");
	}
    
    @RequestMapping(params = "method=createCount")
    public ModelAndView createCount(Model model, HttpServletRequest request) {
        LOG.info("------> display /material-warehouse.rfid?method=createCount");
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return new ModelAndView("login.frm?method=logout");
            }
            model.addAttribute("user", userSession.getSysuser());
            model.addAttribute("materialWarehouseStatus", MaterialWarehouseStatus.values());
        } catch (Exception e) {
            LOG.error("display create material warehouse page fail", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            CommonResponse.setErrors(e, request);
        }
        return redirectMav("createCount");
    }
    
    @RequestMapping(params = "method=saveCount", method = RequestMethod.POST)
	@ResponseBody
    public String saveCount(WarehouseCount warehouseCount, 
            @RequestParam(value = "rkrqStr", required = false) String rkrqStr,HttpServletRequest request) {
        LOG.info(String.format("------> post /material-warehouse.rfid?method=saveCount(bean=%s)",
                ToStringBuilder.reflectionToString(warehouseCount)));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
        	SysUser sysUser = userSession.getSysuser();
        	warehouseCount.setCzr(sysUser.getYhdh());
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(DateUtil.isValidDate(rkrqStr,dateFormat)){
				Date date = dateFormat.parse(rkrqStr);
				warehouseCount.setRkrq(date);
			}
            materialWarehouseService.createCount(warehouseCount);
            
            result = GsonHelper.getBaseResultGson("00", "创建成功");
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=saveCount exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(params = "method=queryCount", method = RequestMethod.POST)
	@ResponseBody
    public String queryCount(
    		@RequestParam(value = "xh", required = false) String xh,
            @RequestParam(value = "czrxm", required = false) String czrxm,
            @RequestParam(value = "rkrqks", required = false) String rkrqks,
            @RequestParam(value = "rkrqjs", required = false) String rkrqjs,HttpServletRequest request) {
        LOG.info(String.format("------> post /material-warehouse.rfid?method=queryCount(xh=%s,czrxm=%s,rkrqks=%s,rkrqjs=%s)",
        		xh,czrxm,rkrqks,rkrqjs));
        String result = "";
        try {
            UserSession userSession = (UserSession) WebUtils
                    .getSessionAttribute(request, "userSession");
            if (userSession == null) {
                return GsonHelper.getBaseResultGson("99", "请重新登录！");
            }
            
            Map<String, Object> condition = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(xh)) {
                condition.put("xh", xh);
            }
            if (StringUtils.isNotEmpty(czrxm)) {
                condition.put("czrxm", czrxm);
            }
            if (StringUtils.isNotEmpty(rkrqks)) {
                condition.put("rkrqks", rkrqks);
            }
            if (StringUtils.isNotEmpty(rkrqjs)) {
                condition.put("rkrqjs", rkrqjs);
            }
            int count = materialWarehouseService.queryCount(condition);
            result = GsonHelper.getBaseResultGson("00", count+"");
            
        } catch (Exception e) {
            LOG.error("------> post /material-warehouse.rfid?method=saveCount exception", e);
            logService.saveErrLog(request, getClass().getName(), "", FuncUtil
                    .transException(e));
            result = GsonHelper.getBaseResultGson("99", e.getMessage());
        }
        return result;
    }
    
    @RequestMapping(params = "method=viewCount")
    public ModelAndView viewCount(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "xh", required = true) String xh){
    	LOG.info("------> display /material-warehouse.rfid?method=viewCount");
    	
		try {
	    	UserSession userSession = (UserSession) WebUtils
					.getSessionAttribute(request, "userSession");
			if (userSession == null){
				return new ModelAndView("login.frm?method=logout");
			}

			if (xh != null && xh.length() > 0) {
				WarehouseCount warehouseCount = this.materialWarehouseService.fetchByXH(xh);
				request.setAttribute("bean", warehouseCount);
			}
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil
					.transException(e));
			CommonResponse.setErrors(e, request);
		}
		
		return redirectMav("viewCount");
	}
}
