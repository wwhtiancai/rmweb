package com.tmri.rfid.mapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.ctrl.view.ProductApplyView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/9.
 */
@Repository
public interface ProductApplyMapper extends BaseMapper<ProductApply> {

    List<ProductApplyView> queryForView(Map condition, PageBounds pageBounds);

}
