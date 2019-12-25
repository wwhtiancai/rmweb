package com.tmri.rfid.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.ExWarehouseDetail;
import com.tmri.rfid.bean.ExWarehouseEntry;
import com.tmri.rfid.bean.ExcelModel;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.mapper.ExWarehouseDetailMapper;
import com.tmri.rfid.mapper.ExWarehouseMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.ExWarehouseService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.OrderApplicationService;
import com.tmri.rfid.util.ExcelOperator;
import com.tmri.rfid.util.FileUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;

@Service
public class ExWarehouseServiceImpl extends BaseServiceImpl implements ExWarehouseService {

	private final static Logger LOG = LoggerFactory.getLogger(EriServiceImpl.class);
	
    @Autowired
    private ExWarehouseMapper exWarehouseMapper;
    @Autowired
    private ExWarehouseDetailMapper exWarehouseDetailMapper;

	@Resource
    private InventoryService inventoryService;
    @Resource
    private OrderApplicationService orderApplicationService;

	@Override
	public String saveExWarehouse(ExWarehouseEntry exWarehouse,String detailList) throws Exception {
		// TODO Auto-generated method stub
		ExWarehouseEntry ewh = this.fetchByCkdh(exWarehouse.getCkdh());
		int code = 0;
		if(ewh != null){
		}else{
			exWarehouseMapper.create(exWarehouse);
			code = 1;
		}
		
		String ckdh = exWarehouse.getCkdh();
		String sqdh = exWarehouse.getSqdh();
		String cpdm = exWarehouse.getCpdm();
		String ssbm = exWarehouse.getSsbm();
		
		String result = "";
		String msg = null;
		int sysl=0,ycksl=0;
		if(code == 1){
			msg = "保存成功";
			int cksl = 0;
			if(detailList != ""){
				JSONArray ja = JSONArray.fromObject(detailList);
				for(int i = 0 ; i < ja.size(); i++){
					JSONObject jo  = (JSONObject) ja.get(i);
					ExWarehouseDetail ewhd = new ExWarehouseDetail();
					ewhd.setCkdh(ckdh);
					ewhd.setDw(Integer.parseInt(jo.get("dw").toString()));
					ewhd.setBzhm(jo.get("bzhm").toString());
					ewhd.setSjsl(Integer.parseInt(jo.get("num").toString()));
					cksl = cksl + Integer.parseInt(jo.get("num").toString());
					int detailCode = this.saveExWarehouseDetail(ewhd);//保存出库详情
					//inventoryService.inventoryOut(ewhd);库存出库
					if(detailCode == 0){
						code = 0;
						msg = "入库单详情入库不成功";
					}
				}
				//更新该订购单的已出库数量
				orderApplicationService.updateYcksl(sqdh);
				//获取该产品剩余量，前端提示
				Inventory condition = new Inventory();
				condition.setCpdm(cpdm);
				condition.setSsbm(ssbm);
				condition.setZt(4);
				sysl = inventoryService.getSumByCondition(condition);
				//获取该产品预出库（已在出库单还未出库）数量
				ycksl = inventoryService.getPrepareOutNum(cpdm,ssbm);
			}
		}else{
			msg = "此出库单号已存在";
		}
		
		if(code == 0){
			LOG.debug(msg);
			result = GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "01", "resultMsg", msg));
		}else{
			result = GsonHelper.getGson().toJson(MapUtilities.buildMap(
                    "resultId", "00", "sysl", sysl,"ycksl",ycksl));
		}
		return result;
	}
	@Override
	public ExWarehouseEntry fetchByCkdh(String ckdh) throws Exception {
		// TODO Auto-generated method stub
		return exWarehouseMapper.fetchByCkdh(ckdh);
	}
	
	@Override
	public int saveExWarehouseDetail(ExWarehouseDetail exWarehouseDetail) throws Exception {
		// TODO Auto-generated method stub
		ExWarehouseDetail whd = this.fetchByDetailId(exWarehouseDetail.getDw(),exWarehouseDetail.getBzhm(),exWarehouseDetail.getCkdh());
		if(whd != null){
			//warehouseDetailMapper.update(warehouseDetail);
			return 0;
		}else{
			exWarehouseDetailMapper.create(exWarehouseDetail);
			return 1;
		}
	}
	
	@Override
	public ExWarehouseDetail fetchByDetailId(int dw,String detailId,String ckdh) throws Exception {
		// TODO Auto-generated method stub
		return exWarehouseDetailMapper.fetchByDetailId(String.valueOf(dw),detailId,ckdh);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<ExWarehouseEntry> queryList(int pageIndex, int pageSize, Map<String,Object> map)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<ExWarehouseEntry>) getPageList(ExWarehouseMapper.class, "queryList",
    		map, pageIndex, pageSize);
	}
	
	@Override
	public List<ExWarehouseDetail> queryDetailListByCkdm(String ckdh)
			throws Exception {
		// TODO Auto-generated method stub
        return exWarehouseDetailMapper.queryListByCkdh(ckdh);
	}
	
	@Override
	public int delDetailList(String bzhh) throws Exception {
		// TODO Auto-generated method stub
        return exWarehouseDetailMapper.deleteByBzhh(bzhh);
	}
	@Override
	public int delExWarehouse(String ckdh) throws Exception {
		// TODO Auto-generated method stub

		exWarehouseDetailMapper.deleteByCkdh(ckdh);
		exWarehouseMapper.deleteByCkdh(ckdh);
		
		return 1;
	}
	
	@Override
	public String getCkdh(String ssbm) throws Exception {
		// TODO Auto-generated method stub
		Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置你想要的格式
		String dateStr = df.format(now.getTime());
		
		String dhqz = ssbm + dateStr;
		String maxdh = exWarehouseMapper.getMaxCkdh(dhqz);
		String newdh = "";
		if(maxdh != null){
			newdh = maxdh;
		}else{
			newdh = dhqz + "000";
		}
		return newdh;
	}
	@Override
	public void examine(String ckdh, int zt) throws Exception {
		exWarehouseMapper.examine(ckdh,zt+"");
		// TODO Auto-generated method stub
	}
	
	@Override
	public void exportExwarehouse(String ckdh) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int queryCount(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		return exWarehouseMapper.queryCount(condition);
	}

}
