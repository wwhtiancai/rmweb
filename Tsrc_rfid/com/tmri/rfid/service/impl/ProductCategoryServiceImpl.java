package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.ProductCategory;
import com.tmri.rfid.mapper.ProductCategoryMapper;
import com.tmri.rfid.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Joey on 2015/9/28.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private List<ProductCategory> categories;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> fetchAll() {
        if (categories == null) {
            categories = productCategoryMapper.queryAll();
        }
        return categories;
    }
}
