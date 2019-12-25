package com.tmri.rfid.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.CorpsWarehouse;
import com.tmri.rfid.bean.CorpsWarehouseDetail;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.ctrl.view.ProductView;
import com.tmri.rfid.mapper.CorpsWarehouseDetailMapper;
import com.tmri.rfid.mapper.CorpsWarehouseMapper;
import com.tmri.rfid.mapper.ProductMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.CorpsWarehouseService;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.ReadExcel;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.util.DateUtil;

@Service
public class CorpsWarehouseServiceImpl extends BaseServiceImpl implements CorpsWarehouseService {

	private final static Logger LOG = LoggerFactory.getLogger(EriServiceImpl.class);
	
	@Resource
    private InventoryService inventoryService;
	@Resource
    private EriService eriService;
    @Autowired
    private CorpsWarehouseMapper warehouseMapper;
    @Autowired
    private CorpsWarehouseDetailMapper warehouseDetailMapper;
    @Autowired
    private ProductMapper productMapper;

	@Override
	public String saveWarehouse(CorpsWarehouse warehouse,String detailList) throws Exception {
		// TODO Auto-generated method stub
		CorpsWarehouse wh = this.fetchByRkdh(warehouse.getRkdh());
		int code;
		if(wh != null){
			code = 0;
		}else{
			warehouseMapper.create(warehouse);
			code = 1;
		}
		
		String msg;
		if(code == 1){
			msg = "保存成功";

			if(detailList != ""){
				JSONArray ja = JSONArray.fromObject(detailList);
				String rkdh = warehouse.getRkdh();
				String ssbm = warehouse.getSsbm();
				for(int i = 0 ; i < ja.size(); i++){
					JSONObject jo  = (JSONObject) ja.get(i);
					CorpsWarehouseDetail whd = new CorpsWarehouseDetail();
					whd.setRkdh(rkdh);
					whd.setDw(Integer.parseInt(jo.get("dw").toString()));
					whd.setBzhm(jo.get("bzhm").toString());
					int detailCode = this.saveWarehouseDetail(whd);
					inventoryService.inventoryInCorps(whd, ssbm);
					if(detailCode == 0){
						code = 0;
						msg = "入库单详情入库不成功";
					}
				}
			}
			
		}else{
			msg = "此入库单号已存在";
		}
		
		String result = "";
		if(code == 0){
			result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "01", "resultMsg", msg));
		}else{
			result = GsonHelper.getGson().toJson(
                    MapUtilities.buildMap("resultId", "00", "resultMsg", msg));
		}
		return result;
	}
	@Override
	public CorpsWarehouse fetchByRkdh(String rkdh) throws Exception {
		// TODO Auto-generated method stub
		return warehouseMapper.fetchByRkdh(rkdh);
	}
	
	@Override
	public int saveWarehouseDetail(CorpsWarehouseDetail warehouseDetail) throws Exception {
		// TODO Auto-generated method stub
		WarehouseDetail whd = this.fetchByDetailId(warehouseDetail);
		if(whd != null){
			//warehouseDetailMapper.update(warehouseDetail);
			return 0;
		}else{
			warehouseDetailMapper.create(warehouseDetail);
			return 1;
		}
	}
	
	@Override
	public CorpsWarehouseDetail fetchByDetailId(CorpsWarehouseDetail warehouseDetail) throws Exception {
		// TODO Auto-generated method stub
		return warehouseDetailMapper.fetchByDetailId(warehouseDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<CorpsWarehouse> queryList(int pageIndex, int pageSize, Map<String,Object> condition)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<CorpsWarehouse>) getPageList(CorpsWarehouseMapper.class, "queryList",
        		condition, pageIndex, pageSize);
	}
	
	@Override
	public List<CorpsWarehouseDetail> queryDetailListByRkdh(String rkdh)
			throws Exception {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rkdh",rkdh);
		
        return warehouseDetailMapper.queryListByRkdh(map);
	}
	
	@Override
	public int delDetailList(String bzhh) throws Exception {
		// TODO Auto-generated method stub
        return warehouseDetailMapper.deleteByBzhh(bzhh);
	}
	@Override
	public int delWarehouse(String rkdh) throws Exception {
		// TODO Auto-generated method stub
		warehouseDetailMapper.deleteByRkdh(rkdh);
		warehouseMapper.deleteByRkdh(rkdh);
		
		return 1;
	}
	@Override
	public String getRkdh(String ssbm) throws Exception {
		// TODO Auto-generated method stub
		Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置你想要的格式
		String dateStr = df.format(now.getTime());
		
		String dhqz = ssbm + dateStr;
		String maxdh = warehouseMapper.getMaxRkdh(dhqz);
		String newdh = "";
		if(maxdh != null){
			newdh = maxdh;
		}else{
			newdh = dhqz + "000";
		}
		return newdh;
	}
	
	/**
	 * 通过Excel导入入库信息
	 * 包括入库信息、入库详情信息、库存信息和标签信息
	 * @param f 上传的excel文件
	 */
	@Override
	public void importWarehouse(File f,String bz) throws Exception {
		String[][] inventorysArr = ReadExcel.getData(f, 1, true, 0);
		String[][] erisArr = ReadExcel.getData(f, 1, true, 1);
		
		if(inventorysArr.length <= 0){
			throw new Exception("您导入的Excel没有库存信息");
		} else {
			SysUser sysUser = UserState.getUser();
			String glbm = sysUser.getGlbm();
			
			//保存总队入库信息
			CorpsWarehouse warehouse = new CorpsWarehouse();
			String rkdh = this.getRkdh(glbm);
			warehouse.setRkdh(rkdh);
			warehouse.setJbr(sysUser.getXm());
			String cpdm = inventorysArr[0][4];
			warehouse.setCpdm(cpdm);
			ProductView product = productMapper.queryProductForViewByCPDM(cpdm);
			int cplb = product.getCplb();
			warehouse.setCplb(String.valueOf(cplb));
			warehouse.setRkrq(new Date());
			warehouse.setBz(bz);
			warehouse.setSsbm(glbm);
			warehouseMapper.create(warehouse);
			
			
			//保存库存信息
			//保存总队入库详情信息
			for(int i = 0; i< inventorysArr.length ; i++){
				String[] inventoryArr = inventorysArr[i];

				CorpsWarehouseDetail warehouseDetail = new CorpsWarehouseDetail();
				warehouseDetail.setRkdh(rkdh);
				warehouseDetail.setDw(2);
				warehouseDetail.setBzhm(inventoryArr[0]);
				warehouseDetailMapper.create(warehouseDetail);
				
				Inventory inventory = new Inventory();
				inventory.setBzhh(inventoryArr[0]);
				inventory.setQskh(inventoryArr[1]);
				inventory.setZzkh(inventoryArr[2]);
				inventory.setSsbm(glbm);
				inventory.setCpdm(inventoryArr[4]);
				inventory.setZt(6);
				inventory.setBzxh(inventoryArr[6]);
				inventoryService.saveInventory(inventory);
			}
			
			//保存卡信息
			for(int j = 0; j< erisArr.length ; j++){
				String[] eriArr = erisArr[j];
				Eri eri = new Eri();
				eri.setTid(eriArr[0]);
				eri.setKh(eriArr[1]);
				eri.setBzhh(eriArr[2]);
				eri.setSf(eriArr[3]);
				if(eriArr[4] != null && !eriArr[4].equals(""))
				eri.setZt(Integer.parseInt(eriArr[4]));
				eri.setPh(eriArr[5]);
				eri.setCshrq(DateUtil.praseDate(eriArr[6],"yyyy-MM-dd HH:mm:ss"));
				eri.setScgxhrq(DateUtil.praseDate(eriArr[7],"yyyy-MM-dd HH:mm:ss"));
				eri.setGlbm(glbm);
				if(eriArr[9] != null && !eriArr[9].equals(""))
				eri.setKlx(Integer.parseInt(eriArr[9]));
				eri.setBz(eriArr[10]);
				if(eriArr[11] != null && !eriArr[11].equals(""))
				eri.setClxxbfid(Long.parseLong(eriArr[11]));
				eriService.warehouse(eri);
			}
		}
		
		
		
	}

}
