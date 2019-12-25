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
 * @date 2016-3-31 下午1:49:12
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
    
	List<EriPastedLog> eriPastedLogs = new ArrayList<EriPastedLog>();//根据号牌找到多条个性化信息的数据
	List<EriPastedLog> eriPasteds = new ArrayList<EriPastedLog>();//数据库已有该TID发放记录
	List<EriPastedLog> eriCustomizeErrors = new ArrayList<EriPastedLog>();//根据TID找到多条个性化数据，或没有个性化的记录
	
	
	
	/**
	 * 公安网发卡现在无法做验证，直接上传
	 * @param f
	 * @param lyrq
	 * @throws Exception 
	 */
	public void updatePastedErisDirect(File f, String lyrq) throws Exception {
		// TODO Auto-generated method stub
		String[][] arrs = ReadExcel.getData(f, 1, true, 0);//读取第一个sheet
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
     * 根据Excel文件获取需要更新的已贴标签
     * @param f
     * @return
     * @throws Exception 
     */
    @Override
    public void updatePastedEris(File f,String lyrq) throws Exception{
		String[][] arrs = ReadExcel.getData(f, 1, true, 0);//读取第一个sheet
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
		System.out.println("已导入的数量"+PastedCount);
		for(int j = 0; j < PastedCount ; j++){
			String tid = eriPasteds.get(j).getTid();
			System.out.println("已贴标签TID："+tid);
			System.out.println("BZ："+eriPasteds.get(j).getBz());
		}
		if(eriPasteds.size() > 0){
			throw new Exception("有已贴的标签");
		}
		
		int customizeErrByHphmCount = eriPastedLogs.size();
		System.out.println("根据号牌找到多条个性化数据或无个性化数据的个数："+customizeErrByHphmCount);
		for(int k = 0; k < customizeErrByHphmCount ; k++){
			String hphm = eriPastedLogs.get(k).getHphm();
			System.out.println("根据号牌找到多条个性化数据或无个性化数据HPHM："+hphm);
			System.out.println("BZ："+eriPastedLogs.get(k).getBz());
		}
		int customizeErrByTidCount = eriCustomizeErrors.size();
		System.out.println("根据TID找到多条个性化数据或无个性化数据的个数："+customizeErrByTidCount);
		for(int l = 0; l < customizeErrByTidCount ; l++){
			String tid = eriCustomizeErrors.get(l).getTid();
			System.out.println("根据TID找到多条个性化数据或无个性化数据Tid："+tid);
			System.out.println("BZ："+eriCustomizeErrors.get(l).getBz());
		}
		if(customizeErrByHphmCount > 0 || customizeErrByTidCount > 0){
			throw new Exception("个性化数据有误");
		}
		
    }
    
    /**
     * 检查该标签是否被正常初始化了一次，
     * @param eriPastedLog
     * @throws Exception
     */
    @Override
    public void checkPastedEri(EriPastedLog eriPastedLog) throws Exception{
    	String tid = eriPastedLog.getTid();
    	String kh = eriPastedLog.getKh();
    	String hphm = eriPastedLog.getHphm();
    	
    	if(tid == ""){//tid为空
			if(kh == ""){//tid卡号都为空，根据车牌号获取tid、卡号
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
			}else{//仅tid为空，根据卡号获取tid
				HashMap map = new HashMap<String, Object>();
				map.put("kh", kh);
				map.put("zt", 1);
				List<Eri> eris = eriMapper.queryByCondition(map);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					eriPastedLog.setTid(tid);
				}else{
					if(eris.size() > 1){
						throw new Exception("该卡号("+kh+")对应多个标签！");
					}else{
						throw new Exception("该卡号("+kh+")未找到对应标签！");
					}
				}
				
			}
		}else{//tid 卡号都齐全的
			HashMap<String, Object> condition = new HashMap<String, Object>();
			condition.put("tid", tid);
			condition.put("zt", 1);
			List<EriCustomizeRecord> eriCustomizeRecords = eriCustomizeRecordMapper.queryByCondition(condition);
			
			int customizeCount = eriCustomizeRecords.size();
			if(customizeCount != 1){
				boolean flag = false;//若为false，加入ArrayList
				if(customizeCount > 1){//根据TID找到多个个性化数据时，比对号牌号码，有对应的正确，没有对应的加入ArrayList
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
    	LOG.debug(String.format("保存已贴标签 bean：%s", ToStringBuilder.reflectionToString(eriPastedLog)));
    	EriPastedLog epl = eriPastedMapper.getByTid(eriPastedLog.getTid());
    	if(epl != null){
    		//throw new Exception("此标签已贴");
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
		System.out.println("已导入的数量"+eriPastedLogs.size());
		for(int j = 0; j < eriPastedLogs.size() ; j++){
			String cfhphm = eriPastedLogs.get(j).getHphm();
			System.out.println("重复号牌号码："+cfhphm);
			System.out.println("重复号牌个数："+eriPastedLogs.get(j).getBz());
		}
		if(eriPastedLogs.size() > 0){
			throw new Exception("有重复个性化的车牌信息");
		}
    }
    
    /**
     * 保存不规范的已贴标签数据
     * @param eriPastedLog
     * @throws Exception
     */
	@Override
	public void saveUnStandard(EriPastedLog eriPastedLog) throws Exception {
		// TODO Auto-generated method stub
		String tid = eriPastedLog.getTid();
		String kh = eriPastedLog.getKh();
		String hphm = eriPastedLog.getHphm();
		
		if(tid == ""){//tid为空
			if(kh == ""){//tid卡号都为空，根据车牌号获取tid、卡号
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
						throw new Exception("该车("+hphm+")个性化多个标签！");
					}else{
						throw new Exception("该车("+hphm+")未个性化标签！");
					}*/
				}
				
			}else{//仅tid为空，根据卡号获取tid
				HashMap map = new HashMap<String, Object>();
				map.put("kh", kh);
				map.put("zt", 1);
				List<Eri> eris = eriMapper.queryByCondition(map);
				if(eris.size() == 1){
					tid = eris.get(0).getTid();
					eriPastedLog.setTid(tid);
				}else{
					if(eris.size() > 1){
						throw new Exception("该卡号("+kh+")对应多个标签！");
					}else{
						throw new Exception("该卡号("+kh+")未找到对应标签！");
					}
				}
				
			}
			
		}
		savePasted(eriPastedLog);
		
	}

	
}
