package com.tmri.rfid.mapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ProductionTask;
import com.tmri.rfid.ctrl.view.ProductionTaskView;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/16.
 */
public interface ProductionTaskMapper extends BaseMapper<ProductionTask> {

    List<ProductionTaskView> queryForView(Map condition);

    List<ProductionTaskView> queryForView(Map condition, PageBounds pageBounds);

}
