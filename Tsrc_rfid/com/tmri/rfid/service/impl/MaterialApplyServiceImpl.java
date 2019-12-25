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
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// ��������Ҫ�ĸ�ʽ
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
    	
    	//��ȷ�ϸö��������Ƿ����
    	MaterialApply m = materialApplyMapper.fetchByDGDH(dgdh);
    	if(null == m){
    		flag = "����д�Ķ����������ڣ�";
		}
		return flag;
    };
    
    

    /**
     * У����������Ƿ������ض�����������
     * У��ÿ����ǩ��tid��ͷ�Ƿ��������ֵ
     */
    @Override
    public String RKCheck(String dgdh, List<MaterialEri> materialEris) throws Exception {
    	int rksl = materialEris.size();
    	MaterialApply materialApply = materialApplyMapper.fetchByDGDH(dgdh);
    	int wrksl = materialApply.getSl() - materialApply.getYrksl();
    	String flag = null;
    	if(wrksl < 0){
    		flag = "��������Ϊ"+dgdh+"�Ķ����������������ѳ�������������Ҫ��������";
    	}else if(wrksl == 0){
    		flag = "��������Ϊ"+dgdh+"�Ķ���������Ҫ����������⣡";
    	}else if(rksl > wrksl){
    		flag = "�˴�����������ڸö�����δ�������";
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
    		flag = "����tid��ǩ��������ض������еĲ�Ʒ��</br>" + list.toString();
    	}
    	
		return flag;
    }

	@Override
	public void updateYrksl(String dgdh) throws Exception {
		// TODO Auto-generated method stub
		materialApplyMapper.updateYrksl(dgdh);
	}
    
}
