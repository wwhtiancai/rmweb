package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Product;
import com.tmri.rfid.ctrl.view.ProductView;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2015/9/24.
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {

	List<Product> queryList(Map<String, Object> map);

    ProductView queryProductForViewByCPDM(String cpdm);

    int deleteProduct(Map<String, Object> map);
    
}
