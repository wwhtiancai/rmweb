package com.tmri.rfid.mapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.OrderApplication;
import com.tmri.rfid.ctrl.view.OrderApplicationView;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/28.
 */
@Repository
public interface OrderApplicationMapper extends BaseMapper<OrderApplication> {

    List<OrderApplicationView> queryForView(Map condition, PageBounds pageBounds);

	void updateYcksl(@Param("sqdh") String sqdh);

}
