package com.tmri.rfid.ctrl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.Product;
import com.tmri.rfid.bean.ProductCategory;
import com.tmri.rfid.common.ProductStatus;
import com.tmri.rfid.ctrl.view.ProductView;
import com.tmri.rfid.service.ProductCategoryService;
import com.tmri.rfid.service.ProductService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.ctrl.BaseCtrl;
import com.tmri.share.frm.util.CommonResponse;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/23.
 */
@Controller
@RequestMapping("product.frm")
public class ProductCtrl extends BaseCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(ProductCtrl.class);

    @Resource
    private ProductService productService;

    @Resource
    private ProductCategoryService productCategoryService;
    
    @RequestMapping(params = "method=fetch-by-category")
    @ResponseBody
    public String listByCategory(@RequestParam(value = "category", required = true) int category) {
        LOG.info(String.format("------> call /product.frm?method=fetch-by-category(%s)", category));
        List<Product> productList = productService.listByCategory(category);
        return GsonHelper.getGson().toJson(productList);
    }
    
    @RequestMapping(params = "method=query-product")
	public ModelAndView queryProduct(
            HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "cpdm", required = false) String cpdm,
			@RequestParam(value = "cpmc", required = false) String cpmc,
			@RequestParam(value = "cplb", required = false) String cplb,
			@RequestParam(value = "zt", required = false) String zt,
			@RequestParam(value = "gysmc", required = false) String gysmc,
			@RequestParam(value = "tzz", required = false) String tzz,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {

        LOG.info(String.format("------> calling /product.frm?method=query-product(%s,%s,%s,%s,%s,%s,%s,%s)",
        		cpdm, cpmc, cplb, zt, gysmc, tzz ,page, pageSize));
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}

			Map<String, Object> condition = new HashMap<String, Object>();
			if (StringUtil.checkBN(cpdm)) {
				condition.put("cpdm", cpdm);
			}
			if (StringUtil.checkBN(cpmc)) {
				condition.put("cpmc", cpmc);
			}
			if (StringUtil.checkBN(cplb)) {
				condition.put("cplb", cplb);
			}
			if (StringUtil.checkBN(zt)) {
				condition.put("zt", zt);
			}
			if (StringUtil.checkBN(gysmc)) {
				condition.put("gysmc", gysmc);
			}
			if (StringUtil.checkBN(tzz)) {
				condition.put("tzz", tzz);
			}
			PageList<Product> queryList = productService.queryList(page, pageSize, condition);
			Paginator paginator = queryList.getPaginator();
			List<ProductCategory>  cplbList= productCategoryService.fetchAll();
			request.setAttribute("queryList", queryList);
			request.setAttribute("controller", getPageInfo(paginator, request));
			request.setAttribute("command", condition);
			request.setAttribute("cplbStatus", cplbList);
			request.setAttribute("productTypes", ProductStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("product/productMain");
	}
    
    
	@RequestMapping(params = "method=edit-product")
	public ModelAndView editProduct(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "cpdm", required = false) String cpdm) {
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			if (StringUtil.checkBN(cpdm)) {
				ProductView productView = this.productService.fetchByCPDM(cpdm);
				request.setAttribute("bean", productView);
			}
			List<ProductCategory>  cplbList= productCategoryService.fetchAll();
			request.setAttribute("cplbStatus", cplbList);
			request.setAttribute("productTypes", ProductStatus.values());
		} catch (Exception e) {
			logService.saveErrLog(request, getClass().getName(), "", FuncUtil.transException(e));
			CommonResponse.setErrors(e, request);
		}

		return redirectMav("product/productEdit");
	}


	@RequestMapping(params = "method=save-product")
	public ModelAndView saveProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "cpdm", required = false) String cpdm, 
			@RequestParam(value = "cpmc", required = false) String cpmc, 	
			@RequestParam(value = "cplb", required = false) Integer cplb,
			@RequestParam(value = "zt", required = false) Integer zt,
			@RequestParam(value = "gysmc", required = false) String gysmc,
			@RequestParam(value = "flag", required = false) boolean flag,
			@RequestParam(value = "tzz", required = false) String tzz) {
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			DbResult result = new DbResult();
			if (!flag) {
				int code = productService.create(cpdm, cpmc, cplb,zt,gysmc,tzz);
				result.setCode(code);
				if (code == 0) {
					result.setMsg("提交失败");
				} else {
					result.setMsg("提交成功");
				}
			}
			if (flag) {
				int code = productService.update(cpdm, cpmc, cplb,zt,gysmc,tzz);
				result.setCode(code);
				if (code == 0) {
					result.setMsg("提交失败");
				} else {
					result.setMsg("提交成功");
				}
			}
			view = CommonResponse.JSON(result);
		} catch (Exception e) {
			LOG.error("save customizeTask result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}

		return view;
	}
	
	@RequestMapping(params = "method=del-product")
	public ModelAndView delProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "cpdm", required = false) String cpdm, 
			@RequestParam(value = "cplb", required = false) Integer cplb) {
		ModelAndView view;
		try {
			UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			if (userSession == null) {
				return new ModelAndView("login.frm?method=logout");
			}
			DbResult result = new DbResult();
			int code = productService.deleteProduct(cpdm,cplb);
			result.setCode(code);
			if (code == 0) {
				result.setMsg("删除失败");
			} else {
				result.setMsg("删除成功");
			}
			
			view = CommonResponse.JSON(result);
		} catch (Exception e) {
			LOG.error("delete customizeTask result fail ", e);
			e.printStackTrace();
			view = CommonResponse.JSON(e);
		}
		return view;
	}
	
    protected final String jspPath = "jsp_core/rfid/";

	public ModelAndView redirectMav(String mav) {
		return new ModelAndView(this.jspPath + mav);
	}
}
