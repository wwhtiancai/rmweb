package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Product;
import com.tmri.rfid.bean.ProductCategory;
import com.tmri.rfid.ctrl.view.ProductView;
import com.tmri.share.frm.bean.Code;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/23.
 */
public interface ProductService {

    int create(String cpdm, String cpmc, int cplb,int zt,String gysmc,String tzz);

    int update(String cpdm, String cpmc, int cplb,int zt,String gysmc,String tzz);
    
    List<Product> listByCategory(int productCategory);

    Map<String, List<Product>> listAllByCategory();

	Collection<Product> listAll();

    ProductView fetchByCPDM(String cpdm);

    PageList<Product> queryList(int pageIndex, int pageSize,
			Map condition) throws Exception;
    int deleteProduct(String cpdm,  int cplb);
    
	public String getCPBMByCPDM(String cpdm) throws Exception;
}
