package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Product;
import com.tmri.rfid.bean.ProductCategory;
import com.tmri.rfid.common.ProductStatus;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;
import com.tmri.rfid.ctrl.view.ProductView;
import com.tmri.rfid.mapper.CustomizeTaskMapper;
import com.tmri.rfid.mapper.ProductMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.ProductService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.dao.GSysparaCodeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/24.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

	private Map<String, Product> productCache;

    @Override
    public int create(String cpdm, String cpmc, int cplb,int zt,String gysmc,String tzz) {
    	Product product = new Product();
    	product.setCpdm(cpdm);
    	product.setCpmc(cpmc);
    	product.setCplb(cplb);
    	product.setZt(zt);
    	product.setGysmc(gysmc);
    	product.setTzz(tzz);
		int result = productMapper.create(product);
        if (result > 0) {
        	resetProductCache();
		}
		return result;
    }
    
	@Override
	public PageList<Product> queryList(int pageIndex, int pageSize,
			Map condition) throws Exception {
		// TODO Auto-generated method stub
		   return (PageList<Product>) getPageList(ProductMapper.class, "queryByCondition",
	        		condition, pageIndex, pageSize);
	}
    
    @Override
    public Collection<Product> listAll() {
    	if (productCache == null) {
    		resetProductCache();
		}
		return productCache.values();
    }

    @Override
    public List<Product> listByCategory(int category) {
        return productMapper.queryByCondition(MapUtilities.buildMap("cplb", category, "zt", ProductStatus.VALID.getStatus()));
    }

    @Override
    public Map<String, List<Product>> listAllByCategory() {
        return null;
    }

    public ProductView fetchByCPDM(String cpdm) {
        return productMapper.queryProductForViewByCPDM(cpdm);
    }

	@Override
	public int deleteProduct(String cpdm, int cplb) {
		Map<String ,Object> condition = new HashMap<String ,Object>();
		condition.put("cpdm", cpdm);
		condition.put("cplb", cplb);
		int result = productMapper.deleteProduct(condition);
		if (result > 0) {
			resetProductCache();
		}
		return result;
	}

	@Override
	public int update(String cpdm, String cpmc, int cplb, int zt, String gysmc, String tzz) {
		// TODO Auto-generated method stub
		Map<String ,Object> condition = new HashMap<String ,Object>();
		condition.put("cpdm", cpdm);
		condition.put("cpmc", cpmc);
		condition.put("cplb", cplb);
		condition.put("zt", zt);
		condition.put("gysmc", gysmc);
		condition.put("tzz", tzz);
		int result = productMapper.updateByCondition(condition);
		if (result > 0) {
			resetProductCache();
		}
		return result;
	}

	private void resetProductCache() {
		productCache = new HashMap<String, Product>();
		List<Product> products = productMapper.queryByCondition(null);
		for(Product product : products) {
			productCache.put(product.getCpdm(), product);
		}
	}
	
	@Override
	public String getCPBMByCPDM(String cpdm) throws Exception{
		if (productCache == null) {
			resetProductCache();
		}
		Product product = productCache.get(cpdm);
		if (product == null) throw new RuntimeException("未找到对应的产品（" + cpdm + "）");
		else return product.getCpbm();
	}
}
