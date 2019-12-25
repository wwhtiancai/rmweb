package com.tmri.rfid.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rfid.bean.DataExch;
import com.tmri.rfid.bean.MyDataEx;
import com.tmri.rfid.mapper.DataExZWMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.ZWService;
import com.tmri.rfid.util.SJNRUtil;
/**
 * 
 * @author stone
 * @date 2016-2-5 ����2:14:30
 */
@Service
public class ZWServiceImpl extends BaseServiceImpl implements ZWService {


	@Autowired
    private DataExZWMapper dataExZWMapper;

	public void saveData(String sj, String sjlx, String clfs) throws Exception {
		// TODO Auto-generated method stub		
		long sjLeng = sj.length();
		
		if(sjLeng > (long)SJNRUtil.SJNRSIZE*10*100000*100000){
			throw new Exception("too leng");
		}else if(sjLeng > SJNRUtil.SJNRSIZE*10){
			int count = (int) Math.ceil((float)sjLeng/(SJNRUtil.SJNRSIZE*10));
			
			//������������ȡ����ID
			String firstStr = sj.substring(0, SJNRUtil.SJNRSIZE*10); 
			sj = sj.substring(SJNRUtil.SJNRSIZE*10); 
			DataExch dataExch1 = SJNRUtil.parseSJNR(firstStr, sjlx, clfs);
			dataExch1.setClbj(0);
			dataExch1.setSjlx("1"+dataExch1.getSjlx());
			HashMap map1 = dataExch1.toMap();
			dataExZWMapper.selectSaveBH(map1);
			String mid = (String) map1.get("Out_BH");
			
			for(int i = 1 ; i < count ; i++){
				String otherStr = "";
				if(i == count-1){
					otherStr = sj;
				}else{
					otherStr = sj.substring(0, SJNRUtil.SJNRSIZE*10); 
					sj = sj.substring(SJNRUtil.SJNRSIZE*10);
				}
				DataExch dataExch = SJNRUtil.parseSJNR(otherStr, sjlx, clfs);
				dataExch.setClbj(0);
				String new_sjlx = mid + String.format("%010d",i) + "3" + dataExch.getSjlx();
				dataExch.setSjlx(new_sjlx);
				dataExch.setMid(mid);
				HashMap map = dataExch.toMap();
				dataExZWMapper.selectSaveBH(map);
			}
		}else{
			DataExch dataExch = SJNRUtil.parseSJNR(sj, sjlx, clfs);
			dataExch.setClbj(0);
			dataExch.setSjlx("1"+dataExch.getSjlx());
			HashMap map = dataExch.toMap();
			dataExZWMapper.selectSaveBH(map);
		}
	}

	public List<MyDataEx> getData(String sjlx) throws Exception {
		// TODO Auto-generated method stub
		List<MyDataEx> myDataExs = new ArrayList<MyDataEx>();
		
		//��������¼
		List<DataExch> dataExchs = dataExZWMapper.getLoneData(sjlx);
		for(int i = 0; i < dataExchs.size(); i++ ){
			MyDataEx myDataEx = SJNRUtil.parseDataEx(dataExchs.get(i));
			myDataExs.add(myDataEx);
		}
		
		//���������¼
		List<DataExch> fistExchs = dataExZWMapper.getFirstData(sjlx);
		for(int i = 0; i < fistExchs.size(); i++ ){
			DataExch firstExch = fistExchs.get(i);
			String mid = firstExch.getMid();
			MyDataEx myDataEx = SJNRUtil.parseDataEx(firstExch);
			String sj = myDataEx.getSj();
			
			//��ȡ�Ӽ�¼
			List<DataExch> sonExchs = dataExZWMapper.getSonData(sjlx, mid);
			for(int j = 0 ; j < sonExchs.size() ; j++){
				MyDataEx mySonDataEx = SJNRUtil.parseDataEx(sonExchs.get(j));
				sj += mySonDataEx.getSj();
			}
			myDataEx.setSj(sj);
			
			myDataExs.add(myDataEx);
		}
		return myDataExs;
	}
	
	/**
	 * ��ȡ�Ѿ����ݽ�����δͬ����ҵ��������(��������������Ϣ)
	 * @param sjlx �������ͣ�Լ�������� ��Ϊ��
	 */
	public List<MyDataEx> getDataWithTableInfo(String sjlx) throws Exception {
		// TODO Auto-generated method stub
		List<MyDataEx> myDataExs = new ArrayList<MyDataEx>();
		
		//��������¼
		List<DataExch> dataExchs = dataExZWMapper.getLoneData(sjlx);
		for(int i = 0; i < dataExchs.size(); i++ ){
			MyDataEx myDataEx = SJNRUtil.parseDataExWithTableInfo(dataExchs.get(i));
			myDataExs.add(myDataEx);
		}
		
		//���������¼
		List<DataExch> fistExchs = dataExZWMapper.getFirstData(sjlx);
		for(int i = 0; i < fistExchs.size(); i++ ){
			DataExch firstExch = fistExchs.get(i);
			String mid = firstExch.getMid();
			MyDataEx myDataEx = SJNRUtil.parseDataExWithTableInfo(firstExch);
			String sj = myDataEx.getSj();
			
			//��ȡ�Ӽ�¼
			List<DataExch> sonExchs = dataExZWMapper.getSonData(sjlx, mid);
			for(int j = 0 ; j < sonExchs.size() ; j++){
				MyDataEx mySonDataEx = SJNRUtil.parseDataExWithTableInfo(sonExchs.get(j));
				sj += mySonDataEx.getSj();
			}
			myDataEx.setSj(sj);
			
			myDataExs.add(myDataEx);
		}
		return myDataExs;
	}

	public void updateFlag(String bhs, String clbj) throws Exception {
		// TODO Auto-generated method stub
		String[] bhArr = bhs.split(",");
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("bhs", bhArr);
		map.put("clbj", clbj);
		dataExZWMapper.updateFlag(map);
	}
}
