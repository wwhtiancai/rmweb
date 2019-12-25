package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.MaterialApply;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.ctrl.view.ProductView;
import com.tmri.rfid.mapper.MaterialApplyMapper;
import com.tmri.rfid.mapper.ProductMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.MaterialApplyService;
import com.tmri.rfid.util.MapUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by st on 2015/11/3.
 */
@Service
public class MaterialApplyServiceImpl extends BaseServiceImpl implements MaterialApplyService {

    @Autowired
    private MaterialApplyMapper materialApplyMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void create(MaterialApply materialApply) throws Exception {
    	materialApply.setDgrq(new Date());
        materialApplyMapper.create(materialApply);
    }
    
    @Override
    public String generateOrderNumber(String cpdm) throws Exception {
    	Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置你想要的格式
		String dateStr = df.format(now.getTime());
		
		String dhqz = cpdm + dateStr;
		String maxdh = materialApplyMapper.getMaxDgdh(dhqz);
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
	public PageList<MaterialApply> queryList(int pageIndex, int pageSize, Map condition)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<MaterialApply>) getPageList(MaterialApplyMapper.class, "queryList",
        		condition, pageIndex, pageSize);
	}

    @Override
    public MaterialApply fetchByDGDH(String dgdh) throws Exception {
        /*List<MaterialApply> materialApplys = 
        		queryList(1,20,MapUtilities.buildMap("dgdh", dgdh));
        if (materialApplys == null || materialApplys.isEmpty()) return null;
        else return materialApplys.get(0);*/
        
        MaterialApply materialApply = materialApplyMapper.fetchByDGDH(dgdh);
        return materialApply;
    }
    
    @Override
    public void update(MaterialApply materialApply) throws Exception {
    	materialApplyMapper.update(materialApply);
    }

    @Override
    public void delete(String dgdh) throws Exception {
    	materialApplyMapper.deleteByDgdh(dgdh);
    }
    
    @Override
    public void delivery(String dgdh) throws Exception {
    	materialApplyMapper.deliveryByDgdh(dgdh);
    }
    
    @Override
    public String RKCheck(String dgdh) throws Exception {
    	String flag = null;
    	
    	//先确认该订购单号是否存在
    	MaterialApply m = materialApplyMapper.fetchByDGDH(dgdh);
    	if(null == m){
    		flag = "您填写的订购单不存在！";
		}
		return flag;
    };
    
    

    /**
     * 校验入库数量是否符合相关订购单的数量
     * 校验每个标签的tid开头是否符合特征值
     */
    @Override
    public String RKCheck(String dgdh, List<MaterialEri> materialEris) throws Exception {
    	int rksl = materialEris.size();
    	MaterialApply materialApply = materialApplyMapper.fetchByDGDH(dgdh);
    	int wrksl = materialApply.getSl() - materialApply.getYrksl();
    	String flag = null;
    	if(wrksl < 0){
    		flag = "订购单号为"+dgdh+"的订购单已入库的箱数已超出订购单中需要的箱数！";
    	}else if(wrksl == 0){
    		flag = "订购单号为"+dgdh+"的订购单中需要的箱数已入库！";
    	}else if(rksl > wrksl){
    		flag = "此次入库箱数大于该订购单未入库箱数";
    	}
    	
    	if(flag == null){
    		ProductView product = productMapper.queryProductForViewByCPDM(materialApply.getCpdm());
    		String tzz = product.getTzz();
        	ArrayList list = new ArrayList(); 
    		for(int i = 0; i < materialEris.size() ; i++){
    			MaterialEri materialEri = materialEris.get(i);
    			if(materialEri.getTid().indexOf(tzz) != 0){
    				list.add(materialEri.getTid());
    			}
    		}
    		flag = "以下tid标签不符合相关订购单中的产品：</br>" + list.toString();
    	}
    	
		return flag;
    }

	@Override
	public void updateYrksl(String dgdh) throws Exception {
		// TODO Auto-generated method stub
		materialApplyMapper.updateYrksl(dgdh);
	}
    
}
