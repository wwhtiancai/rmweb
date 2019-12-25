package com.tmri.rfid.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.util.MapUtilities;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.mapper.InventoryMapper;
import com.tmri.rfid.mapper.WarehouseDetailMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductService;

@Service
public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService {

	private final static Logger LOG = LoggerFactory.getLogger(EriServiceImpl.class);

    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private EriMapper eriMapper;
    
    @Resource
    private ProductService productService;
	
	@Override
	public int saveInventory(Inventory inventory) throws Exception {
		// TODO Auto-generated method stub
		Inventory i = inventoryMapper.fetchByBzhh(inventory.getBzhh());
		int result;
		if(i != null){
			result = inventoryMapper.update(inventory);
		}else{
			result = inventoryMapper.create(inventory);
		}
		
		return result;
	}

	@Override
	public Inventory fetchByBzhh(String bzhh) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.fetchByBzhh(bzhh);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Inventory> queryList(int pageIndex, int pageSize, Map<String,Object> condition) throws Exception {
        return (PageList<Inventory>) getPageList(InventoryMapper.class, "queryList",
    		condition, pageIndex, pageSize);
	}

	/**
	 * 入库 入库到公安部库存
	 */
	@Override
	public int inventoryIn(WarehouseDetail whd, String glbm) throws Exception {
		// TODO Auto-generated method stub
		int dw = whd.getDw();
		String bzhm = whd.getBzhm();
		
		if(dw == 1){
			inventoryMapper.inventoryInByXh(bzhm, glbm, 4);
			eriMapper.updateByXh(bzhm, glbm);
		}else{
			inventoryMapper.inventoryInByHh(bzhm, glbm, 4);
			eriMapper.updateByInventoryId(bzhm, glbm);
		}
		return 0;
	}
	
	/**
	 * 出库 从公安部库存出库
	 */
	/*@Override
	public int inventoryOut(ExWarehouseDetail ewhd) throws Exception {
		// TODO Auto-generated method stub
		int dw = ewhd.getDw();
		String bzhm = ewhd.getBzhm();
		
		if(dw == 1){
			inventoryMapper.inventoryOutByXh(bzhm);
		}else{
			inventoryMapper.inventoryOutByHh(bzhm);
		}
		return 0;
	}*/
	
	@Override
	public int inventoryInCorps(WarehouseDetail whd, String glbm) throws Exception {
		// TODO Auto-generated method stub
		int dw = whd.getDw();
		String bzhm = whd.getBzhm();
		
		if(dw == 1){
			inventoryMapper.inventoryInByXh(bzhm, glbm, 6);
			eriMapper.updateByXh(bzhm, glbm);
		}else{
			inventoryMapper.inventoryInByHh(bzhm, glbm, 6);
			eriMapper.updateByInventoryId(bzhm, glbm);
		}
		return 0;
	}

    @Override
    public List<Inventory> fetchByCondition(Map condition) {
        return inventoryMapper.queryByCondition(condition);
    }

    @Override
    public List<Inventory> fetchByCondition(Map condition, PageBounds pageBounds) {
        return inventoryMapper.queryByCondition(condition, pageBounds);
    }

    @Override
	public int checkInventoryForIn(Inventory inventory) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public List<Inventory> checkInventory(String bzh, String ssbm) throws Exception {
        if (StringUtils.isEmpty(bzh)) throw new Exception("包装号为空");
        if (bzh.startsWith(BOX_NUMBER_PREFIX)) {
            return inventoryMapper.queryByCondition(MapUtilities.buildMap("bzhh", bzh, "zt",
                    InventoryStatus.STOREDCORPS.getStatus(), "ssbm", ssbm));
        } else if (bzh.startsWith(VEST_NUMBER_PREFIX)) {
            return inventoryMapper.queryByCondition(MapUtilities.buildMap("bzxh", bzh, "zt",
                    InventoryStatus.STOREDCORPS.getStatus(), "ssbm", ssbm));
        } else {
            throw new Exception("包装号不正确");
        }
    }

    @Override
    public List<String> findOccupiedKh(String sf, int qskh, int sl) throws Exception {
        return inventoryMapper.queryOccupied(sf, String.valueOf(qskh),
                String.valueOf(qskh + sl * VEST_CAPACITY * BOX_CAPACITY - 1));
    }

    @Override
    public void reserve(String sf, int qskh, int sl, String rwh, String cpdm) throws Exception {
    	String cpbm = productService.getCPBMByCPDM(cpdm);
        inventoryMapper.reserve(MapUtilities.buildMap("In_sf", sf, "In_qskh", qskh, "In_sl", sl,
                "In_vest_capacity", VEST_CAPACITY, "In_box_capacity", BOX_CAPACITY, "In_rwh", rwh,
                "In_cpdm", cpdm, "In_cpbm",cpbm));
    }

    @Override
    public boolean update(Inventory inventory) throws Exception {
        return inventoryMapper.update(inventory) > 0;
    }

    @Override
    public List<Map> findAvailableKh(String sf) throws RuntimeException {
        return inventoryMapper.queryAvailableKh(sf, VEST_CAPACITY * BOX_CAPACITY);
    }

	@Override
	public void inventoryOutByCkdh(String ckdh) {
		// TODO Auto-generated method stub
		inventoryMapper.inventoryOutByCkdh(ckdh);
	}

    @Override
    public String findLastFinished(String rwh) throws Exception {
        return inventoryMapper.queryLastFinished(rwh, BOX_CAPACITY);
    }

	@Override
	public int fetchDqkc(String cpdm, String ssbm) throws Exception {
		// TODO Auto-generated method stub
		return eriMapper.fetchDqkc(cpdm,ssbm);
	}

	@Override
	public int getSumByCondition(Inventory condition) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getSumByCondition(condition);
	}

	@Override
	public int getPrepareOutNum(String cpdm, String ssbm) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getPrepareOutNum(cpdm,ssbm);
	}

	@Override
	public List<Inventory> queryByCkdh(String ckdh) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.queryByCkdh(ckdh);
	}

	@Override
	public List<Inventory> getXhByKh(String qskh, String zzkh, String cpdm,String zt)
			throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getXhByKh(qskh,zzkh,cpdm,zt);
	}

	@Override
	public int queryKc(String glbm) throws Exception {
		return inventoryMapper.queryKc(glbm);
	}
}
