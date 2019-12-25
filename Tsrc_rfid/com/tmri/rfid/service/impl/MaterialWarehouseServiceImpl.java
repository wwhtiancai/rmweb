package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.MaterialInventory;
import com.tmri.rfid.bean.MaterialWarehouse;
import com.tmri.rfid.bean.PackageCase;
import com.tmri.rfid.bean.WarehouseCount;
import com.tmri.rfid.mapper.MaterialEriMapper;
import com.tmri.rfid.mapper.MaterialInventoryMapper;
import com.tmri.rfid.mapper.MaterialWarehouseMapper;
import com.tmri.rfid.mapper.WarehouseCountMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.MaterialWarehouseService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.ReadExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by st on 2015/11/3.
 */
@Service
public class MaterialWarehouseServiceImpl extends BaseServiceImpl implements MaterialWarehouseService {

    @Autowired
    private MaterialWarehouseMapper materialWarehouseMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private MaterialEriMapper materialEriMapper;
    @Autowired
    private WarehouseCountMapper warehouseCountMapper;
	
    @Override
    public void create(MaterialWarehouse materialWarehouse) throws Exception {
    	materialWarehouse.setRkrq(new Date());
    	materialWarehouseMapper.create(materialWarehouse);
    }
    
    @Override
    public String generateOrderNumber() throws Exception {
    	Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置你想要的格式
		String dateStr = df.format(now.getTime());
		
		String dhqz = dateStr;
		String maxdh = materialWarehouseMapper.getMaxRkdh(dhqz);
		String newdh = "";
		if(maxdh != null){
			newdh = maxdh;
		}else{
			newdh = dhqz + "000";
		}
		return newdh;
    }

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MaterialWarehouse> queryList(int pageIndex, int pageSize, Map condition)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<MaterialWarehouse>) getPageList(MaterialWarehouseMapper.class, "queryList",
        		condition, pageIndex, pageSize);
	}

    @Override
    public MaterialWarehouse fetchByRKDH(String rkdh) throws Exception {
        List<MaterialWarehouse> materialWarehouses = 
        		queryList(1,20,MapUtilities.buildMap("rkdh", rkdh));
        if (materialWarehouses == null || materialWarehouses.isEmpty()) return null;
        else return materialWarehouses.get(0);
    }
    
    @Override
    public void update(MaterialWarehouse materialWarehouse) throws Exception {
    	materialWarehouseMapper.update(materialWarehouse);
    }

    @Override
    public void delete(String rkdh) throws Exception {
    	materialWarehouseMapper.deleteByRkdh(rkdh);
    }
    
    
    @Override
    public Map<String, Object> getMaterialEris(File file,String rkdh) throws Exception {
        Map<String, Object> map = this.getList(file,rkdh);
    	return map;
    }
    
    /**
     * 根据excel文件获取半成品库存list和半成品list
     * @param file
     * @return
     * @throws Exception 
     */
    public static Map<String, Object> getList(File file,String rkdh) throws Exception{
		List<MaterialEri> materialEris = new ArrayList<MaterialEri>();
		List<MaterialInventory> materialInventorys = new ArrayList<MaterialInventory>();
		String bzhh = "";
		String bzxh = "";

		try {
			String[][] arrs = ReadExcel.getData(file, 1);
			for (int i = 0; i < arrs.length; i++) {
				String[] arr = arrs[i];
				MaterialEri testEri = new MaterialEri();
				testEri.setTid(arr[0]);

				if (arr[2] != null && arr[2] !="") {
					testEri.setBzxh(arr[2]);
					bzxh = arr[2];
				} else {
					testEri.setBzxh(bzxh);
				}
				if (arr[1] != null && arr[1] !="") {
					testEri.setBzhh(arr[1]);
					bzhh = arr[1];
					
					//生成以盒为基础单位的list
					MaterialInventory materialInventory = new MaterialInventory();
					materialInventory.setBzhh(bzhh);
					materialInventory.setBzxh(bzxh);
					materialInventory.setRkdh(rkdh);
					materialInventorys.add(materialInventory);
				} else {
					testEri.setBzhh(bzhh);
				}
				materialEris.add(testEri);
				System.out.println("tid:"+testEri.getTid()+"===bzhh:"+testEri.getBzhh()+"===bzxh:"+testEri.getBzxh());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("materialEris", materialEris);
		map.put("materialInventorys", materialInventorys);
		
		return map;
	}
    
    @Override
    public void insertBatchEri(List<MaterialEri> materialEris) throws Exception {
    	materialEriMapper.insertBatchEri(materialEris);
    }
    
    @Override
    public void insertBatchInventory(List<MaterialInventory> materialInventorys) throws Exception {
    	materialInventoryMapper.insertBatchInventory(materialInventorys);
    }

	@Override
	public Map getMaterialEris(File file) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String, Object>();
		List<MaterialEri> materialEris = new ArrayList<MaterialEri>();
		String bzhh = "";
		String bzxh = "";

		try {
			String[][] arrs = ReadExcel.getData(file, 0);
			String dgdh = arrs[0][1];
			String jfdw = arrs[0][3];
			for (int i = 2; i < arrs.length; i++) {
				String[] arr = arrs[i];
				MaterialEri materialEri = new MaterialEri();
				materialEri.setTid(arr[0]);

				if (arr[2] != null && arr[2] !="") {
					materialEri.setBzxh(arr[2]);
					bzxh = arr[2];
				} else {
					materialEri.setBzxh(bzxh);
				}
				if (arr[1] != null && arr[1] !="") {
					materialEri.setBzhh(arr[1]);
					bzhh = arr[1];
				} else {
					materialEri.setBzhh(bzhh);
				}
				materialEris.add(materialEri);
			}
			
			map.put("dgdh", dgdh);
			map.put("jfdw", jfdw);
			map.put("materialEris", materialEris);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 根据原料列表提取页面显示的以箱号、起始盒号，终止盒号为单位行的列表
	 */
	@Override
	public List<PackageCase> getPackageCases(List<MaterialEri> materialEris) {
		List<PackageCase> packageCases = new ArrayList<PackageCase>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(materialEris.size()>0){
			for(int i = 0; i < materialEris.size() ; i++){
				MaterialEri materialEri = materialEris.get(i);
				String bzxh = materialEri.getBzxh();
				String bzhh = materialEri.getBzhh();
				if(map.get(bzxh) == null){
					PackageCase pc = new PackageCase();
					pc.setBzxh(bzxh);
					pc.setQshh(bzhh);
					pc.setZzhh(bzhh);
					map.put(bzxh, pc);
				}else{
					PackageCase pc = (PackageCase) map.get(bzxh);
					if(pc.getQshh().compareTo(bzhh) > 0 ){//如果qshh比包装盒号大，这条记录的包装盒号成为起始盒号
						pc.setQshh(bzhh);
					}
					if(pc.getZzhh().compareTo(bzhh) < 0 ){
						pc.setZzhh(bzhh);
					}
					map.put(bzxh, pc);
				}
			}
			
			for (Entry<String, Object> entry : map.entrySet()) {  
				packageCases.add((PackageCase) entry.getValue());
			}  
		}
		
		return packageCases;
	}

	@Override
	public List<MaterialInventory> getMaterialInventorys(List<MaterialEri> list,String rkdh) {
		// TODO Auto-generated method stub
		List<MaterialInventory> materialInventorys = new ArrayList<MaterialInventory>();
		String bzhh = "";
		String bzxh = "";
		
		if(list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				MaterialEri bean = list.get(i);
				if (!bean.getBzhh().equals(bzhh) ) {
					bzhh = bean.getBzhh();
					bzxh = bean.getBzxh();
					
					//生成以盒为基础单位的list
					MaterialInventory materialInventory = new MaterialInventory();
					materialInventory.setBzhh(bzhh);
					materialInventory.setBzxh(bzxh);
					materialInventory.setRkdh(rkdh);
					materialInventorys.add(materialInventory);
				} else {
					
				}
			}
		}
		
		return materialInventorys;
	}

	@Override
	public List<PackageCase> queryWarehouseDetails(String rkdh) {
		// TODO Auto-generated method stub
		return materialWarehouseMapper.queryWarehouseDetails(rkdh);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<WarehouseCount> queryCountList(int pageIndex, int pageSize, Map condition)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<WarehouseCount>) getPageList(WarehouseCountMapper.class, "queryList",
        		condition, pageIndex, pageSize);
	}

	@Override
	public void createCount(WarehouseCount warehouseCount) throws Exception {
		// TODO Auto-generated method stub
		warehouseCountMapper.create(warehouseCount);
	}

	@Override
	public WarehouseCount fetchByXH(String xh) throws Exception {
		// TODO Auto-generated method stub
		return warehouseCountMapper.fetchByXH(xh);
	}

	@Override
	public int queryCount(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		return warehouseCountMapper.queryCount(condition);
	}
}
