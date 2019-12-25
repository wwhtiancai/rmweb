package com.tmri.rfid.service;

import com.tmri.rfid.bean.ProductCategory;

import java.util.List;

/**
 * Created by Joey on 2015/9/28.
 */
public interface ProductCategoryService {

    List<ProductCategory> fetchAll();

}
