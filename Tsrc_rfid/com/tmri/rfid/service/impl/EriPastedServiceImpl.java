package com.tmri.rfid.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.EriCustomizeRecord;
import com.tmri.rfid.bean.EriPastedLog;
import com.tmri.rfid.mapper.EriCustomizeRecordMapper;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.mapper.EriPastedMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.EriPastedService;
import com.tmri.rfid.util.DateUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.ReadExcel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author stone
 * @date 2016-3-31 ����1:49:12
 */
@Service
public class EriPastedServiceImpl extends BaseServiceImpl implements EriPastedService {

    private final static Logger LOG = LoggerFactory.getLogger(EriPastedServiceImpl.class);
    
    @Autowired
    private EriPastedMapper eriPastedMapper;
    @Autowired
    private EriMapper eriMapper;
    @Autowired
    private EriCustomizeRecordMapper eriCustomizeRecordMapper;
    
	List<EriPastedLog> eriPastedLogs = new ArrayList<EriPastedLog>();//���ݺ����ҵ��������Ի���Ϣ������
	List<EriPastedLog> eriPasteds = new ArrayList<EriPastedLog>();//���ݿ����и�TID���ż�¼
	List<EriPastedLog> eriCustomizeErrors = new ArrayList<EriPastedLog>();//����TID�ҵ��������Ի����ݣ���û�и��Ի��ļ�¼
	
	
	
	/**
	 * ���������������޷�����֤��ֱ���ϴ�
	 * @param f
	 * @param lyrq
	 * @throws Exception 
	 */
	public void updatePastedErisDirect(File f, String lyrq) throws Exception {
		// TODO Auto-generated method stub
		String[][] arrs = ReadExcel.getData(f, 1, true, 0);//��ȡ��һ��sheet
		for (int i = 0; i < arrs.length; i++) {
			String[] arr = arrs[i];
			EriPastedLog eriPastedLog = new EriPastedLog();
			eriPastedLog.setTid(arr[0]);
			eriPastedLog.setKh(arr[1]);
			eriPastedLog.setHphm(arr[2]);
			eriPastedLog.setBz(arr[7]);
			eriPastedLog.setLyr(arr[8]);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(DateUtil.isValidDate(lyrq,dateFormat)){
				Date date = dateFormat.parse(lyrq);
				eriPastedLog.setLyrq(date);
			}
			savePasted(eriPastedLog);
		}
	}
	
    /**
     * ����Excel�ļ���ȡ��Ҫ���µ�������ǩ
     * @param f
     * @return
     * @throws Exception 
     */
    @Override
    public void updatePastedEris(File f,String lyrq) throws Exception{
		String[][] arrs = ReadExcel.getData(f, 1, true, 0);//��ȡ��һ��sheet
		for (int i = 0; i < arrs.length; i++) {
			String[] arr = arrs[i];
			EriPastedLog eriPastedLog = new EriPastedLog();
			eriPastedLog.setTid(arr[0]);
			eriPastedLog.setKh(arr[1]);
			eriPastedLog.setHphm(arr[2]);
			eriPastedLog.setBz(arr[7]);
			eriPastedLog.setLyr(arr[8]);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(DateUtil.isValidDate(lyrq,dateFormat)){
				Date date = dateFormat.parse(lyrq);
				eriPastedLog.setLyrq(date);
			}
			checkPastedEri(eriPastedLog);
			savePasted(eriPastedLog);
		}
		
		System.out.println("------> readExcel count:"+ arrs.length);
		int PastedCount = eriPasteds.size();
		System.out.println("�ѵ��������"+PastedCount);
		for(int j = 0; j < PastedCount ; j++){
			String tid = eriPasteds.get(j).getTid();
			System.out.println("������ǩTID��"+tid);
			System.out.println("BZ��"+eriPasteds.get(j).getBz());
		}
		if(eriPasteds.size() > 0){
			throw new Exception("�������ı�ǩ");
		}
		
		int customizeErrByHphmCount = eriPastedLogs.size();
		System.out.println("���ݺ����ҵ��������Ի����ݻ��޸��Ի����ݵĸ�����"+customizeErrByHphmCount);
		for(int k = 0; k < customizeErrByHphmCount ; k++){
			String hphm = eriPastedLogs.get(k).getHphm();
			System.out.println("���ݺ����ҵ��������Ի����ݻ��޸��Ի�����HPHM��"+hphm);
			System.out.println("BZ��"+eriPastedLogs.get(k).getBz());
		}
		int customizeErrByTidCount = eriCustomizeErrors.size();
		System.out.println("����TID�ҵ��������Ի����ݻ��޸��Ի����ݵĸ�����"+customizeErrByTidCount);
		for(int l = 0; l < customizeErrByTidCount ; l++){
			String tid = eriCustomizeErrors.get(l).getTid();
			System.out.println("����TID�ҵ��������Ի����ݻ��޸��Ի�����Tid��"+tid);
			System.out.println("BZ��"+eriCustomizeErrors.get(l).getBz());
		}
		if(customizeErrByHphmCount > 0 || customizeErrByTidCount > 0){
			throw new Exception("���Ի���������");
		}
		
    }
    
    /**
     * ���ñ�ǩ�Ƿ�������ʼ����һ�Σ�
     * @param eriPastedLog
     * @throws Exception
     */
    @Override
    public void checkPastedEri(EriPastedLog eriPastedLog) throws Exception{
    	String tid = eriPastedLog.getTid();
    	String kh = eriPastedLog.getKh();
    	String hphm = eriPastedLog.getHphm();
    	
    	if(tid == ""){//tidΪ��
			if(kh == ""){//tid���Ŷ�Ϊ�գ����ݳ��ƺŻ�ȡtid������
				List<Eri> eris = eriMapper.queryByHphm(hphm);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					kh = eris.get(0).getKh();
					eriPastedLog.setTid(tid);
					eriPastedLog.setKh(kh);
				}else{
					eriPastedLog.setBz(eris.size()+ "==="+ eriPastedLog.getBz());
					eriPastedLogs.add(eriPastedLog);
				}
			}else{//��tidΪ�գ����ݿ��Ż�ȡtid
				HashMap map = new HashMap<String, Object>();
				map.put("kh", kh);
				map.put("zt", 1);
				List<Eri> eris = eriMapper.queryByCondition(map);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					eriPastedLog.setTid(tid);
				}else{
					if(eris.size() > 1){
						throw new Exception("�ÿ���("+kh+")��Ӧ�����ǩ��");
					}else{
						throw new Exception("�ÿ���("+kh+")δ�ҵ���Ӧ��ǩ��");
					}
				}
				
			}
		}else{//tid ���Ŷ���ȫ��
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("tid", tid);
			condition.put("zt", 1);
			List<EriCustomizeRecord> eriCustomizeRecords = eriCustomizeRecordMapper.queryByCondition(condition);
			
			int customizeCount = eriCustomizeRecords.size();
			if(customizeCount != 1){
				boolean flag = false;//��Ϊfalse������ArrayList
				if(customizeCount > 1){//����TID�ҵ�������Ի�����ʱ���ȶԺ��ƺ��룬�ж�Ӧ����ȷ��û�ж�Ӧ�ļ���ArrayList
					for(int m = 0 ; m < customizeCount; m++){
						String fzjg = eriCustomizeRecords.get(m).getFzjg();
						String cusHphm = eriCustomizeRecords.get(m).getHphm();
						String thisHphm = fzjg.substring(0, 1) + cusHphm;
						if(thisHphm.equals(hphm)){
							flag = true;
						}
					}
				}
				
				if(!flag){
					eriPastedLog.setBz(customizeCount+"==="+eriPastedLog.getBz());
					eriCustomizeErrors.add(eriPastedLog);
				}
			}
			
		}
    }
    
    /**
     * 
     * @param eriPastedLog
     * @throws Exception
     */
    @Transactional
    public void savePasted(EriPastedLog eriPastedLog) throws Exception {
    	LOG.debug(String.format("����������ǩ bean��%s", ToStringBuilder.reflectionToString(eriPastedLog)));
    	EriPastedLog epl = eriPastedMapper.getByTid(eriPastedLog.getTid());
    	if(epl != null){
    		//throw new Exception("�˱�ǩ����");
    		eriPasteds.add(eriPastedLog);
    	}else{
        	eriPastedMapper.save(eriPastedLog);
    	}
    }

    
    @Override
    public void saveUnStandards(File f) throws Exception{
    	String[][] arrs = ReadExcel.getData(f, 1, true);
		for(int i = 0; i < arrs.length ;i++){
			String[] arr = arrs[i];
			EriPastedLog eriPastedLog = new EriPastedLog();
			eriPastedLog.setTid(arr[1]);
			eriPastedLog.setKh(arr[2]);
			eriPastedLog.setHphm(arr[3]);
			eriPastedLog.setBz(arr[9]);
			
			saveUnStandard(eriPastedLog);
		}

    	LOG.info("------> readExcel count:"+ arrs.length);
		System.out.println("�ѵ��������"+eriPastedLogs.size());
		for(int j = 0; j < eriPastedLogs.size() ; j++){
			String cfhphm = eriPastedLogs.get(j).getHphm();
			System.out.println("�ظ����ƺ��룺"+cfhphm);
			System.out.println("�ظ����Ƹ�����"+eriPastedLogs.get(j).getBz());
		}
		if(eriPastedLogs.size() > 0){
			throw new Exception("���ظ����Ի��ĳ�����Ϣ");
		}
    }
    
    /**
     * ���治�淶��������ǩ����
     * @param eriPastedLog
     * @throws Exception
     */
	@Override
	public void saveUnStandard(EriPastedLog eriPastedLog) throws Exception {
		// TODO Auto-generated method stub
		String tid = eriPastedLog.getTid();
		String kh = eriPastedLog.getKh();
		String hphm = eriPastedLog.getHphm();
		
		if(tid == ""){//tidΪ��
			if(kh == ""){//tid���Ŷ�Ϊ�գ����ݳ��ƺŻ�ȡtid������
				List<Eri> eris = eriMapper.queryByHphm(hphm);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					kh = eris.get(0).getKh();
					eriPastedLog.setTid(tid);
					eriPastedLog.setKh(kh);
				}else{
					eriPastedLog.setBz(eris.size()+ "==="+ eriPastedLog.getBz());
					eriPastedLogs.add(eriPastedLog);
					/*if(eris.size() > 1){
						throw new Exception("�ó�("+hphm+")���Ի������ǩ��");
					}else{
						throw new Exception("�ó�("+hphm+")δ���Ի���ǩ��");
					}*/
				}
				
			}else{//��tidΪ�գ����ݿ��Ż�ȡtid
				HashMap map = new HashMap<String, Object>();
				map.put("kh", kh);
				map.put("zt", 1);
				List<Eri> eris = eriMapper.queryByCondition(map);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					eriPastedLog.setTid(tid);
				}else{
					if(eris.size() > 1){
						throw new Exception("�ÿ���("+kh+")��Ӧ�����ǩ��");
					}else{
						throw new Exception("�ÿ���("+kh+")δ�ҵ���Ӧ��ǩ��");
					}
				}
				
			}
			
		}
		savePasted(eriPastedLog);
		
	}

	
}
